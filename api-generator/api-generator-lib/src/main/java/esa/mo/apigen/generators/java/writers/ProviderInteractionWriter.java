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
 * Writes the interaction class of an operation that reports back over time.
 * <p>
 * A provider handling an invoke or a progress is handed one of these instead of the plain
 * MAL interaction, so that the messages it can send back are named and typed: it sends an
 * acknowledgement, then updates, then a response, and the fields of each are the ones the
 * operation declared.
 */
public final class ProviderInteractionWriter {

    private static final String MESSAGE = JavaNaming.MAL + "transport.MALMessage";

    private static final String INTERACTION_EXCEPTION = JavaNaming.MAL + "MALInteractionException";

    private static final String MAL_EXCEPTION = JavaNaming.MAL + "MALException";

    private static final String INTERACTION_THROWN =
            "if there is a problem during the interaction as defined by the MAL specification.";

    private static final String EXCEPTION_THROWN = "if there is an implementation exception";

    private ProviderInteractionWriter() {
    }

    /**
     * @return the name of the class, which is the operation's with the kind appended.
     */
    public static String classNameOf(Operation operation) {
        String name = operation.getName();
        return Character.toUpperCase(name.charAt(0)) + name.substring(1) + "Interaction";
    }

    /**
     * @return the source of the operation's interaction class.
     */
    public static String write(MOModel model, Service service, Operation operation) {
        boolean isProgress = operation.getPattern() == InteractionPattern.PROGRESS;
        String pattern = isProgress ? "PROGRESS" : "INVOKE";
        String malType = JavaNaming.MAL + "provider." + (isProgress ? "MALProgress" : "MALInvoke");
        String className = classNameOf(operation);

        JavaClassBuilder clazz = JavaClassBuilder.named(className)
                .inPackage(JavaNaming.packageOf(service, JavaNaming.PROVIDER))
                .comment("Provider " + pattern + " interaction class for " + service.getName()
                        + "::" + operation.getName() + " operation.");
        JavaSource out = clazz.open();

        JavaFieldBuilder.named("interaction").scope("private").ofType(malType)
                .comment("The interaction field.").write(out);

        JavaMethodBuilder.constructor(className)
                .comment("Wraps the provided MAL interaction object with methods for sending"
                        + " responses to an " + pattern + " interaction from a provider.")
                .argument(malType, "interaction", "The MAL interaction action object to use.")
                .line("this.interaction = interaction;")
                .write(out);

        JavaMethodBuilder.named("getInteraction")
                .returns(malType, "The MAL interaction object provided in the constructor")
                .comment("Returns the MAL interaction object used for returning messages from"
                        + " the provider.")
                .line("return interaction;")
                .write(out);

        send(out, model, operation, "sendAcknowledgement", InteractionStage.ACK,
                "Sends a " + pattern + " acknowledge to the consumer",
                "Returns the MAL message created by the acknowledge");
        if (isProgress) {
            send(out, model, operation, "sendUpdate", InteractionStage.UPDATE,
                    "Sends a " + pattern + " update to the consumer",
                    "Returns the MAL message created by the update");
        }
        send(out, model, operation, "sendResponse", InteractionStage.RESPONSE,
                "Sends a " + pattern + " response to the consumer",
                "Returns the MAL message created by the response");

        // The invoke class describes the error it is handed, the progress class repeats the
        // name in front of it. The difference is in the existing generator and is kept.
        String errorComment = isProgress ? "error The MAL error to send to the consumer."
                : "The MAL error to send to the consumer.";
        sendError(out, "sendError", "Sends an error to the consumer", errorComment);
        if (isProgress) {
            sendError(out, "sendUpdateError", "Sends an update error to the consumer",
                    errorComment);
        }

        return clazz.close();
    }

    /**
     * One of the messages a provider can send back, carrying the fields of that stage.
     */
    private static void send(JavaSource out, MOModel model, Operation operation, String name,
            InteractionStage stage, String comment, String returnComment) {
        List<Field> fields = fieldsOf(operation.getMessage(stage));
        JavaMethodBuilder method = JavaMethodBuilder.named(name)
                .returns(MESSAGE, returnComment).comment(comment);

        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            String fieldComment = field.getComment() != null ? field.getComment()
                    : field.getName() + " Argument number " + i
                    + " as defined by the service operation";
            method.argument(JavaTypeName.of(model, field.getType()), field.getName(), fieldComment);
        }

        method.throwing(INTERACTION_EXCEPTION, INTERACTION_THROWN)
                .throwing(MAL_EXCEPTION, EXCEPTION_THROWN)
                .line("return interaction." + name + "(" + arguments(model, fields) + ");")
                .write(out);
    }

    /**
     * The error a provider sends when it cannot go on.
     */
    private static void sendError(JavaSource out, String name, String comment,
            String errorComment) {
        JavaMethodBuilder.named(name)
                .returns(MESSAGE, "Returns the MAL message created by the error")
                .comment(comment)
                .argument(JavaNaming.MAL + "MOErrorException", "error", errorComment)
                .throwing(INTERACTION_EXCEPTION, INTERACTION_THROWN)
                .throwing(MAL_EXCEPTION, EXCEPTION_THROWN)
                .line("return interaction." + name + "(error);")
                .write(out);
    }

    /**
     * @return the arguments handed to the MAL, with a native attribute wrapped in the Union
     * that carries it, or a cast null where there are none.
     */
    private static String arguments(MOModel model, List<Field> fields) {
        if (fields.isEmpty()) {
            return "(Object[]) null";
        }
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            buf.append(i == 0 ? "" : ", ");
            if (JavaTypeName.isNativeAttribute(model, field.getType())) {
                buf.append("(").append(field.getName()).append(" == null) ? null : new ")
                        .append(JavaNaming.MAL_STRUCTURES).append("Union(")
                        .append(field.getName()).append(")");
            } else {
                buf.append(field.getName());
            }
        }
        return buf.toString();
    }

    private static List<Field> fieldsOf(MessageBody body) {
        return body == null ? new ArrayList<Field>() : body.getFields();
    }
}
