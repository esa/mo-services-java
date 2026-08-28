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
import esa.mo.apigen.generators.java.JavaFieldBuilder;
import esa.mo.apigen.generators.java.JavaMethodBuilder;
import esa.mo.apigen.generators.java.JavaNaming;
import esa.mo.apigen.generators.java.JavaSource;
import esa.mo.apigen.generators.java.JavaTypeName;
import esa.mo.apigen.generators.java.JavaTypes;
import esa.mo.apigen.model.Field;
import esa.mo.apigen.model.InteractionPattern;
import esa.mo.apigen.model.InteractionStage;
import esa.mo.apigen.model.MOModel;
import esa.mo.apigen.model.MessageBody;
import esa.mo.apigen.model.Operation;
import esa.mo.apigen.model.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes the inheritance skeleton of a service: the class a provider extends, which turns
 * an incoming message into a call on the handler method that answers for it.
 * <p>
 * One dispatch method per interaction pattern, each switching on the operation number. An
 * operation that declares errors of its own is wrapped in a try, so that a provider can
 * raise them directly and have them arrive at the consumer as an interaction exception.
 */
public final class ProviderInheritanceSkeletonWriter {

    private static final String MAL_EXCEPTION = JavaNaming.MAL + "MALException";

    private static final String INTERACTION_EXCEPTION = JavaNaming.MAL + "MALInteractionException";

    private static final String BODY = JavaNaming.MAL + "transport.MALMessageBody";

    private static final String HANDLE_COMMENT =
            "Called by the provider MAL layer on reception of a message to handle the interaction";

    /**
     * The message of the error raised for an operation number the service does not have.
     */
    private static final String UNSUPPORTED = JavaNaming.MAL
            + "provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber";

    /**
     * The step the arguments of a dispatched call take past the line they start on.
     */
    private static final String ARGUMENT = ",\n                ";

    private ProviderInheritanceSkeletonWriter() {
    }

    /**
     * @return the source of the service's inheritance skeleton.
     */
    public static String write(MOModel model, Service service) {
        String name = service.getName();
        String className = name + "InheritanceSkeleton";
        String provider = JavaNaming.packageOf(service, JavaNaming.PROVIDER);

        JavaClassBuilder clazz = JavaClassBuilder.named(className).asAbstract()
                .inPackage(provider)
                .implementing(JavaNaming.MAL + "provider.MALInteractionHandler")
                .implementing(provider + "." + name + "Skeleton")
                .implementing(provider + "." + name + "Handler")
                .comment("Provider Inheritance skeleton for " + className + " service.");
        JavaSource out = clazz.open();

        JavaFieldBuilder.named("providerSet").scope("private")
                .ofType(JavaNaming.MAL + "provider.MALProviderSet")
                .value("new " + JavaNaming.MAL + "provider.MALProviderSet("
                        + JavaNaming.packageOf(service) + "." + name + "Helper."
                        + name.toUpperCase() + "_SERVICE)")
                .comment("The providerSet field.").write(out);

        JavaMethodBuilder.named("getConnection")
                .returns(JavaNaming.MAL + "helpertools.connections.ConnectionProvider",
                        "the connection object for this provider")
                .comment("Returns the connection object for this provider.")
                .throwing("java.io.IOException", "if the method was not implemented yet.")
                .line("throw new java.io.IOException(\"This method needs to be overridden!\");")
                .write(out);

        JavaMethodBuilder.named("setSkeleton").asOverride()
                .argument(provider + "." + name + "Skeleton", "skeleton", "The skeleton (not used)")
                .line("// Not used in the inheritance pattern (the skeleton is 'this');")
                .write(out);

        JavaMethodBuilder.named("malInitialize").asOverride()
                .argument(JavaNaming.MAL + "provider.MALProvider", "provider", "The provider to be added.")
                .throwing(MAL_EXCEPTION, "If an error is detected.")
                .line("providerSet.addProvider(provider);")
                .write(out);

        JavaMethodBuilder.named("malFinalize").asOverride()
                .argument(JavaNaming.MAL + "provider.MALProvider", "provider", "The provider to be added.")
                .throwing(MAL_EXCEPTION, "If an error is detected.")
                .line("providerSet.removeProvider(provider);")
                .write(out);

        for (Operation operation : service.getOperations()) {
            if (operation.getPattern() == InteractionPattern.PUBSUB) {
                writeCreatePublisher(out, service, operation);
            }
        }

        handle(out, model, service, InteractionPattern.SEND, "handleSend",
                JavaNaming.MAL + "provider.MALInteraction", "The interaction object", false);
        handle(out, model, service, InteractionPattern.SUBMIT, "handleSubmit",
                JavaNaming.MAL + "provider.MALSubmit", "The interaction object", true);
        handle(out, model, service, InteractionPattern.REQUEST, "handleRequest",
                JavaNaming.MAL + "provider.MALRequest", "The interaction object", true);
        handle(out, model, service, InteractionPattern.INVOKE, "handleInvoke",
                JavaNaming.MAL + "provider.MALInvoke", "The interaction object", true);
        handle(out, model, service, InteractionPattern.PROGRESS, "handleProgress",
                JavaNaming.MAL + "provider.MALProgress", "The interaction object", true);

        return clazz.close();
    }

    /**
     * How a provider reaches the publisher of one of its publish-subscribe operations.
     */
    private static void writeCreatePublisher(JavaSource out, Service service, Operation operation) {
        String publisher = JavaNaming.packageOf(service, JavaNaming.PROVIDER) + "."
                + ProviderPublisherWriter.classNameOf(operation);
        JavaMethodBuilder.named("create" + capitalise(operation.getName()) + "Publisher")
                .asOverride().returns(publisher, "The new publisher object.")
                .comment("Creates a publisher object using the current registered provider set"
                        + " for the PubSub operation " + operation.getName())
                .argument(JavaNaming.MAL_STRUCTURES + "IdentifierList", "domain",
                        "The domain used for publishing")
                .argument(JavaNaming.MAL_STRUCTURES + "Identifier", "networkZone",
                        "The network zone used for publishing")
                .argument(JavaNaming.MAL_STRUCTURES + "SessionType", "sessionType",
                        "The session used for publishing")
                .argument(JavaNaming.MAL_STRUCTURES + "Identifier", "sessionName",
                        "The session name used for publishing")
                .argument(JavaNaming.MAL_STRUCTURES + "QoSLevel", "qos",
                        "The QoS used for publishing")
                .argument("java.util.Map", "qosProps", "The QoS properties used for publishing")
                .argument(JavaNaming.MAL_STRUCTURES + "UInteger", "priority",
                        "The priority used for publishing")
                .throwing(MAL_EXCEPTION, "if a problem is detected during creation of the publisher")
                .line("return new " + publisher + "(providerSet.createPublisherSet("
                        + JavaNaming.packageOf(service) + "." + service.getName() + "ServiceInfo."
                        + operation.getName().toUpperCase()
                        + "_OP, domain, sessionType, sessionName, qos, qosProps, null));")
                .write(out);
    }

    /**
     * One dispatch method: it reads the operation number off the interaction and calls the
     * handler method of that operation, unpacking the body as it goes.
     *
     * @param answers True where the interaction can be told about an operation it does not
     * have. A send has nobody to tell.
     */
    private static void handle(JavaSource out, MOModel model, Service service,
            InteractionPattern pattern, String name, String interactionType,
            String interactionComment, boolean answers) {
        JavaMethodBuilder method = JavaMethodBuilder.named(name).asOverride()
                .comment(HANDLE_COMMENT)
                .argument(interactionType, "interaction", interactionComment)
                .argument(BODY, "body", "The message body")
                .throwing(MAL_EXCEPTION, "if there is a internal error")
                .throwing(INTERACTION_EXCEPTION, "if there is a operation interaction error");

        // An operation that declares errors of its own is wrapped, so that the provider can
        // raise them directly rather than wrapping each one itself.
        boolean wrapErrors = declaresErrors(service, pattern);

        method.line("int opNumber = interaction.getOperation().getNumber().getValue();");
        if (wrapErrors) {
            method.line("try {");
        }
        method.line("switch (opNumber) {");

        for (Operation operation : service.getOperations()) {
            if (operation.getPattern() != pattern) {
                continue;
            }
            method.line("  case " + JavaNaming.packageOf(service) + "." + service.getName()
                    + "ServiceInfo._" + operation.getName().toUpperCase() + "_OP_NUMBER:");
            writeCase(method, model, service, operation);
            method.line("    break;");
        }

        method.line("  default:");
        if (answers) {
            method.line("    interaction.sendError(new " + JavaNaming.MAL
                    + "UnsupportedOperationException(\n                    " + UNSUPPORTED + "));");
        }
        method.line("    throw new " + INTERACTION_EXCEPTION + "(new " + JavaNaming.MAL
                + "UnsupportedOperationException(\n                    " + UNSUPPORTED + "));");
        method.line("}");
        if (wrapErrors) {
            method.line("} catch (" + JavaNaming.MAL + "MOErrorException error) {");
            method.line("  throw new " + INTERACTION_EXCEPTION + "(error);");
            method.line("}");
        }
        method.write(out);
    }

    /**
     * The body of one case: the call on the handler, and whatever has to happen around it
     * for the interaction to be answered.
     */
    private static void writeCase(JavaMethodBuilder method, MOModel model, Service service,
            Operation operation) {
        String arguments = unpacked(model, fieldsOf(operation.getMessage(requestStageOf(operation))));

        switch (operation.getPattern()) {
            case SEND:
                method.line("    " + operation.getName() + "(" + arguments + "interaction);");
                break;
            case SUBMIT:
                method.line("    " + operation.getName() + "(" + arguments + "interaction);");
                method.line("    interaction.sendAcknowledgement();");
                break;
            case REQUEST:
                writeRequestCase(method, model, service, operation, arguments);
                break;
            case INVOKE:
            case PROGRESS:
                method.line("    " + operation.getName() + "(" + arguments + "new "
                        + ProviderInteractionWriter.classNameOf(operation) + "(interaction));");
                break;
            default:
                break;
        }
    }

    /**
     * A request is answered on the spot, so what the handler returns is taken apart and
     * handed straight back.
     */
    private static void writeRequestCase(JavaMethodBuilder method, MOModel model, Service service,
            Operation operation, String arguments) {
        List<Field> answered = fieldsOf(operation.getMessage(InteractionStage.RESPONSE));
        String call = operation.getName() + "(" + arguments + "interaction)";

        if (answered.isEmpty()) {
            method.line("    " + call + ";");
            method.line("    interaction.sendResponse();");
            return;
        }

        if (answered.size() == 1) {
            Field field = answered.get(0);
            if (JavaTypeName.isNativeAttribute(model, field.getType())) {
                // A native attribute has to be wrapped before it can be sent, so it is named
                // first rather than wrapped inside the call.
                String variable = operation.getName() + "Rt";
                method.line("    " + JavaTypeName.of(model, field.getType()) + " " + variable
                        + " = " + call + ";");
                method.line("    interaction.sendResponse((" + variable + " == null) ? null : new "
                        + JavaNaming.MAL_STRUCTURES + "Union(" + variable + "));");
            } else {
                method.line("    interaction.sendResponse(" + call + ");");
            }
            return;
        }

        String variable = operation.getName() + "Rt";
        String returnType = JavaNaming.packageOf(service, "body") + "."
                + capitalise(operation.getName()) + "Response";
        method.line("    " + returnType + " " + variable + " = " + call + ";");
        StringBuilder buf = new StringBuilder("\n                    ");
        for (int i = 0; i < answered.size(); i++) {
            Field field = answered.get(i);
            String getter = variable + ".get" + capitalise(field.getName()) + "()";
            if (i > 0) {
                buf.append(",\n                    ");
            }
            if (JavaTypeName.isNativeAttribute(model, field.getType())) {
                buf.append("(").append(getter).append(" == null) ? null : new ")
                        .append(JavaNaming.MAL_STRUCTURES).append("Union(").append(getter).append(")");
            } else {
                buf.append(getter);
            }
        }
        method.line("    interaction.sendResponse(" + buf);
        method.line("    );");
    }

    /**
     * @return the fields taken out of the body, each on its own line, and each followed by
     * the comma that separates it from the interaction that comes last.
     */
    private static String unpacked(MOModel model, List<Field> fields) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            if (i != 0) {
                buf.append(ARGUMENT);
            }
            if (JavaTypeName.isNativeAttribute(model, field.getType())) {
                String union = JavaNaming.MAL_STRUCTURES + "Union";
                String element = "body.getBodyElement(" + i + ", new " + union + "("
                        + JavaTypes.nativeDefault(field.getType().getName()) + "))";
                buf.append("(").append(element).append(" == null) ? null : ((").append(union)
                        .append(") ").append(element).append(").get")
                        .append(field.getType().getName()).append("Value()");
            } else {
                buf.append("(").append(JavaTypeName.of(model, field.getType()))
                        .append(") body.getBodyElement(").append(i).append(", ")
                        .append(JavaTypeName.expectedTypeOf(model, field.getType())).append(")");
            }
        }
        return fields.isEmpty() ? "" : buf.append(ARGUMENT).toString();
    }

    /**
     * @return true if any operation of this pattern declares an error of its own.
     */
    private static boolean declaresErrors(Service service, InteractionPattern pattern) {
        for (Operation operation : service.getOperations()) {
            if (operation.getPattern() == pattern && !operation.getErrors().isEmpty()) {
                return true;
            }
        }
        return false;
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
