/* ----------------------------------------------------------------------------
 * Copyright (C) 2026      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO API Generator
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ----------------------------------------------------------------------------
 */
package esa.mo.apigen.generators.java.writers;

import esa.mo.apigen.generators.java.JavaClassBuilder;
import esa.mo.apigen.generators.java.JavaMethodBuilder;
import esa.mo.apigen.generators.java.JavaNaming;
import esa.mo.apigen.generators.java.JavaSource;
import esa.mo.apigen.generators.java.JavaTypeName;
import esa.mo.apigen.model.ErrorDefinition;
import esa.mo.apigen.model.ErrorReference;
import esa.mo.apigen.model.Field;
import esa.mo.apigen.model.InteractionStage;
import esa.mo.apigen.model.MOModel;
import esa.mo.apigen.model.MessageBody;
import esa.mo.apigen.model.Operation;
import esa.mo.apigen.model.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes the handler interface of a service: the one method a provider has to implement per
 * operation, and nothing else.
 * <p>
 * The errors an operation declares are on the throws clause, so a provider raises them
 * directly and the layer above wraps them into an interaction exception.
 */
public final class ProviderHandlerWriter {

    private static final String INTERACTION_EXCEPTION = JavaNaming.MAL + "MALInteractionException";

    private static final String MAL_EXCEPTION = JavaNaming.MAL + "MALException";

    private static final String INTERACTION_COMMENT =
            "The MAL object representing the interaction in the provider.";

    private ProviderHandlerWriter() {
    }

    /**
     * @return the source of the service's handler interface.
     */
    public static String write(MOModel model, Service service) {
        String name = service.getName();
        JavaClassBuilder clazz = JavaClassBuilder.named(name + "Handler").asInterface()
                .inPackage(JavaNaming.packageOf(service, JavaNaming.PROVIDER))
                .comment("Interface that providers of the " + name + " service must implement"
                        + " to handle the operations of that service.");
        JavaSource out = clazz.open();

        for (Operation operation : service.getOperations()) {
            if (operation.getPattern() == esa.mo.apigen.model.InteractionPattern.PUBSUB) {
                continue; // A provider publishes rather than answers, so there is nothing to implement
            }
            writeOperation(out, model, service, operation);
        }

        JavaMethodBuilder.named("setSkeleton").asDeclaration()
                .comment("Sets the skeleton to be used for creation of publishers.")
                .argument(JavaNaming.packageOf(service, JavaNaming.PROVIDER) + "." + name
                        + "Skeleton", "skeleton", "The skeleton to be used.")
                .write(out);

        return clazz.close();
    }

    /**
     * One operation, taking what the consumer sent and, where the interaction reports back
     * over time, the object the provider reports through.
     */
    private static void writeOperation(JavaSource out, MOModel model, Service service,
            Operation operation) {
        JavaMethodBuilder method = JavaMethodBuilder.named(operation.getName()).asDeclaration()
                .comment("Implements the operation " + operation.getName());

        List<Field> sent = fieldsOf(operation.getMessage(requestStageOf(operation)));
        for (int i = 0; i < sent.size(); i++) {
            Field field = sent.get(i);
            String comment = field.getComment() != null ? field.getComment()
                    : field.getName() + " Argument number " + i
                    + " as defined by the service operation";
            method.argument(JavaTypeName.of(model, field.getType()), field.getName(), comment);
        }

        if (operation.getPattern() == esa.mo.apigen.model.InteractionPattern.REQUEST) {
            List<Field> answered = fieldsOf(operation.getMessage(InteractionStage.RESPONSE));
            String returnType = returnTypeOf(model, service, operation, answered);
            if (returnType != null) {
                method.returns(returnType, "The return value of the operation");
            }
        }

        method.argument(interactionTypeOf(service, operation), "interaction", INTERACTION_COMMENT);
        addErrors(method, model, operation);
        method.throwing(INTERACTION_EXCEPTION,
                "if there is a problem during the interaction as defined by the MAL specification.")
                .throwing(MAL_EXCEPTION, "if there is an implementation exception")
                .write(out);
    }

    /**
     * The errors of the operation, each with the description that says when it is raised.
     * A reference rarely carries its own, so the definition it names answers for it.
     */
    private static void addErrors(JavaMethodBuilder method, MOModel model, Operation operation) {
        for (ErrorReference reference : operation.getErrors()) {
            String comment = reference.getComment();
            if (comment == null || comment.isEmpty()) {
                ErrorDefinition definition = model.resolveError(reference.getError());
                comment = definition != null && definition.getComment() != null
                        && !definition.getComment().isEmpty()
                        ? definition.getComment() : "if the corresponding MO error occurs";
            }
            method.throwing(JavaNaming.ROOT + reference.getError().getArea().toLowerCase() + "."
                    + ExceptionWriter.classNameOf(reference.getError().getName()), comment);
        }
    }

    /**
     * @return the type of the interaction object: the plain one where the provider answers
     * in one go, and the operation's own where it reports back over time.
     */
    private static String interactionTypeOf(Service service, Operation operation) {
        switch (operation.getPattern()) {
            case INVOKE:
            case PROGRESS:
                return JavaNaming.packageOf(service, JavaNaming.PROVIDER) + "."
                        + capitalise(operation.getName()) + "Interaction";
            default:
                return JavaNaming.MAL + "provider.MALInteraction";
        }
    }

    /**
     * @return the type the provider answers with, or null when it answers with nothing.
     */
    private static String returnTypeOf(MOModel model, Service service, Operation operation,
            List<Field> fields) {
        if (fields.isEmpty()) {
            return null;
        }
        if (fields.size() == 1) {
            return JavaTypeName.of(model, fields.get(0).getType());
        }
        return JavaNaming.packageOf(service, "body") + "."
                + capitalise(operation.getName()) + "Response";
    }

    /**
     * @return the stage that carries what the consumer sent to start the interaction.
     */
    private static InteractionStage requestStageOf(Operation operation) {
        switch (operation.getPattern()) {
            case SEND:
                return InteractionStage.SEND;
            case SUBMIT:
                return InteractionStage.SUBMIT;
            case REQUEST:
                return InteractionStage.REQUEST;
            case INVOKE:
                return InteractionStage.INVOKE;
            case PROGRESS:
                return InteractionStage.PROGRESS;
            default:
                return null;
        }
    }

    private static List<Field> fieldsOf(MessageBody body) {
        return body == null ? new ArrayList<Field>() : body.getFields();
    }

    private static String capitalise(String text) {
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }
}
