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
import esa.mo.apigen.model.InteractionStage;
import esa.mo.apigen.model.MOModel;
import esa.mo.apigen.model.MessageBody;
import esa.mo.apigen.model.Operation;
import esa.mo.apigen.model.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes the consumer stub of a service: one method per operation, taking the fields the
 * operation sends and answering with the ones it sends back.
 * <p>
 * The stub is a thin mapping onto the generic MAL consumer - it names the operation and
 * hands over the arguments - so every method is a single call, plus the unpacking of
 * whatever came back.
 */
public final class ConsumerStubWriter {

    private static final String CONSUMER = JavaNaming.MAL + "consumer.MALConsumer";

    private static final String MESSAGE = JavaNaming.MAL + "transport.MALMessage";

    private static final String MESSAGE_BODY = JavaNaming.MAL + "transport.MALMessageBody";

    private static final String INTERACTION_EXCEPTION = JavaNaming.MAL + "MALInteractionException";

    private static final String MAL_EXCEPTION = JavaNaming.MAL + "MALException";

    private static final String INTERACTION_THROWN =
            "if there is a problem during the interaction as defined by the MAL specification.";

    private static final String EXCEPTION_THROWN = "if there is an implementation exception";

    private static final String SENT = "the MAL message sent to initiate the interaction";

    private ConsumerStubWriter() {
    }

    /**
     * @return the source of the service's consumer stub.
     */
    public static String write(MOModel model, Service service) {
        String name = service.getName();
        String adapterType = JavaNaming.packageOf(service, JavaNaming.CONSUMER) + "." + name + "Adapter";

        JavaClassBuilder clazz = JavaClassBuilder.named(name + "Stub")
                .inPackage(JavaNaming.packageOf(service, JavaNaming.CONSUMER))
                .comment("Consumer stub for " + name + " service.");
        JavaSource out = clazz.open();

        JavaFieldBuilder.named("consumer").scope("private").asFinal().ofType(CONSUMER)
                .comment("The consumer field.").write(out);

        JavaMethodBuilder.constructor(name + "Stub")
                .comment("Wraps a MALconsumer connection with service specific methods that"
                        + " map from the high level service API to the generic MAL API.")
                .argument(CONSUMER, "consumer", "consumer The MALConsumer to use in this stub.")
                .line("this.consumer = consumer;")
                .write(out);

        JavaMethodBuilder.named("getConsumer").returns(CONSUMER, "The MAL consumer object.")
                .comment("Returns the internal MAL consumer object used for sending of messages"
                        + " from this interface")
                .line("return consumer;")
                .write(out);

        for (Operation operation : service.getOperations()) {
            switch (operation.getPattern()) {
                case SEND:
                    writeSend(out, model, service, operation);
                    break;
                case SUBMIT:
                case REQUEST:
                    writeRequest(out, model, service, operation, adapterType);
                    break;
                case INVOKE:
                case PROGRESS:
                    writeInvoke(out, model, service, operation, adapterType);
                    break;
                case PUBSUB:
                    writePubSub(out, service, operation, adapterType);
                    break;
                default:
                    break;
            }
        }

        return clazz.close();
    }

    /**
     * A send has nothing to wait for, so it answers with the message it sent.
     */
    private static void writeSend(JavaSource out, MOModel model, Service service,
            Operation operation) {
        JavaMethodBuilder method = JavaMethodBuilder.named(operation.getName())
                .returns(MESSAGE, SENT).comment(operation.getComment());
        addArguments(method, model, requestBodyOf(operation));
        addThrows(method);
        method.line("return consumer.send(" + operationOf(service, operation) + ", "
                + arguments(model, requestBodyOf(operation)) + ");");
        method.write(out);
    }

    /**
     * A submit or a request is answered in one go, so the stub waits for the answer and
     * unpacks it. Both also get an asynchronous form and a way to pick an interaction back
     * up.
     */
    private static void writeRequest(JavaSource out, MOModel model, Service service,
            Operation operation, String adapterType) {
        List<Field> sent = requestBodyOf(operation);
        List<Field> received = fieldsOf(operation.getMessage(InteractionStage.RESPONSE));
        String returnType = returnTypeOf(model, service, operation, received, "Response");

        JavaMethodBuilder method = JavaMethodBuilder.named(operation.getName())
                .comment(operation.getComment());
        if (returnType != null) {
            method.returns(returnType, "The return value of the interaction");
        }
        addArguments(method, model, sent);
        addThrows(method);
        method.line((returnType == null ? "" : MESSAGE_BODY + " body = ") + "consumer."
                + patternCallOf(operation) + "(" + operationOf(service, operation) + ", "
                + arguments(model, sent) + ");");
        addReturn(method, model, service, operation, received, returnType);
        method.write(out);

        JavaMethodBuilder async = JavaMethodBuilder.named("async" + capitalise(operation.getName()))
                .returns(MESSAGE, SENT)
                .comment("Asynchronous version of method " + operation.getName());
        addArguments(async, model, sent);
        async.argument(adapterType, "adapter",
                "adapter Listener in charge of receiving the messages from the service provider");
        addThrows(async);
        async.line("return consumer.async" + capitalise(patternCallOf(operation)) + "("
                + operationOf(service, operation) + ", adapter, " + arguments(model, sent) + ");");
        async.write(out);

        writeContinue(out, service, operation, adapterType);
    }

    /**
     * An invoke or a progress reports back over time, so the adapter is handed over with
     * the call and the stub answers only with the acknowledgement.
     */
    private static void writeInvoke(JavaSource out, MOModel model, Service service,
            Operation operation, String adapterType) {
        List<Field> sent = requestBodyOf(operation);
        List<Field> acknowledged = fieldsOf(operation.getMessage(InteractionStage.ACK));
        String returnType = returnTypeOf(model, service, operation, acknowledged, "Ack");

        JavaMethodBuilder method = JavaMethodBuilder.named(operation.getName())
                .comment(operation.getComment());
        if (returnType != null) {
            method.returns(returnType, "The acknowledge value of the interaction");
        }
        addArguments(method, model, sent);
        method.argument(adapterType, "adapter",
                "adapter Listener in charge of receiving the messages from the service provider");
        addThrows(method);
        method.line((returnType == null ? "" : MESSAGE_BODY + " body = ") + "consumer."
                + patternCallOf(operation) + "(" + operationOf(service, operation)
                + ", adapter, " + arguments(model, sent) + ");");
        addReturn(method, model, service, operation, acknowledged, returnType);
        method.write(out);

        JavaMethodBuilder async = JavaMethodBuilder.named("async" + capitalise(operation.getName()))
                .returns(MESSAGE, SENT)
                .comment("Asynchronous version of method " + operation.getName());
        addArguments(async, model, sent);
        async.argument(adapterType, "adapter",
                "adapter Listener in charge of receiving the messages from the service provider");
        addThrows(async);
        async.line("return consumer.async" + capitalise(patternCallOf(operation)) + "("
                + operationOf(service, operation) + ", adapter, " + arguments(model, sent) + ");");
        async.write(out);

        writeContinue(out, service, operation, adapterType);
    }

    /**
     * Registering says what to listen for; deregistering says to stop.
     */
    private static void writePubSub(JavaSource out, Service service, Operation operation,
            String adapterType) {
        String name = operation.getName();
        String subscription = JavaNaming.MAL_STRUCTURES + "Subscription";
        String identifiers = JavaNaming.MAL_STRUCTURES + "IdentifierList";
        String adapterComment =
                "adapter Listener in charge of receiving the messages from the service provider";

        JavaMethodBuilder register = JavaMethodBuilder.named(name + "Register")
                .comment("Register method for the " + name + " PubSub interaction")
                .argument(subscription, "subscription", "subscription the subscription to register for")
                .argument(adapterType, "adapter", adapterComment);
        addThrows(register);
        register.line("consumer.register(" + operationOf(service, operation)
                + ", subscription, adapter);");
        register.write(out);

        JavaMethodBuilder asyncRegister = JavaMethodBuilder.named("async" + capitalise(name) + "Register")
                .returns(MESSAGE, SENT)
                .comment("Asynchronous version of method " + name + "Register")
                .argument(subscription, "subscription", "subscription the subscription to register for")
                .argument(adapterType, "adapter", adapterComment);
        addThrows(asyncRegister);
        asyncRegister.line("return consumer.asyncRegister(" + operationOf(service, operation)
                + ", subscription, adapter);");
        asyncRegister.write(out);

        JavaMethodBuilder deregister = JavaMethodBuilder.named(name + "Deregister")
                .comment("Deregister method for the " + name + " PubSub interaction")
                .argument(identifiers, "identifierList",
                        "identifierList the subscription identifiers to deregister");
        addThrows(deregister);
        deregister.line("consumer.deregister(" + operationOf(service, operation)
                + ", identifierList);");
        deregister.write(out);

        JavaMethodBuilder asyncDeregister = JavaMethodBuilder.named("async" + capitalise(name) + "Deregister")
                .returns(MESSAGE, SENT)
                .comment("Asynchronous version of method " + name + "Deregister")
                .argument(identifiers, "identifierList",
                        "identifierList the subscription identifiers to deregister")
                .argument(adapterType, "adapter", adapterComment);
        addThrows(asyncDeregister);
        asyncDeregister.line("return consumer.asyncDeregister(" + operationOf(service, operation)
                + ", identifierList, adapter);");
        asyncDeregister.write(out);
    }

    /**
     * Picks an interaction back up where it was left, which is how a consumer that was
     * restarted carries on.
     */
    private static void writeContinue(JavaSource out, Service service, Operation operation,
            String adapterType) {
        JavaMethodBuilder method = JavaMethodBuilder.named("continue" + capitalise(operation.getName()))
                .comment("Continues a previously started interaction")
                .argument(JavaNaming.MAL_STRUCTURES + "UOctet", "lastInteractionStage",
                        "lastInteractionStage The last stage of the interaction to continue")
                .argument(JavaNaming.MAL_STRUCTURES + "Time", "initiationTimestamp",
                        "initiationTimestamp Timestamp of the interaction initiation message")
                .argument("Long", "transactionId",
                        "transactionId Transaction identifier of the interaction to continue")
                .argument(adapterType, "adapter",
                        "adapter Listener in charge of receiving the messages from the service provider");
        addThrows(method);
        method.line("consumer.continueInteraction(" + operationOf(service, operation)
                + ", lastInteractionStage, initiationTimestamp, transactionId, adapter);");
        method.write(out);
    }

    /**
     * Unpacks what came back. Each field is taken out of the body under its own name before
     * it is answered with, so that the cast reads next to the type it is casting to.
     */
    private static void addReturn(JavaMethodBuilder method, MOModel model, Service service,
            Operation operation, List<Field> fields, String returnType) {
        if (returnType == null || fields.isEmpty()) {
            return;
        }
        List<String> answers = new ArrayList<String>();
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            if (JavaTypeName.isNativeAttribute(model, field.getType())) {
                String union = JavaNaming.MAL_STRUCTURES + "Union";
                method.line("Object body" + i + " = (Object) body.getBodyElement(" + i + ", new "
                        + union + "(" + JavaTypes.nativeDefault(field.getType().getName()) + "));");
                answers.add("(body" + i + " == null) ? null : ((" + union + ") body" + i + ").get"
                        + field.getType().getName() + "Value()");
            } else {
                method.line("Object body" + i + " = (Object) body.getBodyElement(" + i + ", "
                        + JavaTypeName.expectedTypeOf(model, field.getType()) + ");");
                answers.add("(" + JavaTypeName.of(model, field.getType()) + ") body" + i);
            }
        }
        if (answers.size() == 1) {
            method.line("return " + answers.get(0) + ";");
            return;
        }
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < answers.size(); i++) {
            buf.append(i == 0 ? "" : ", ").append(answers.get(i));
        }
        method.line("return new " + returnType + "(" + buf + ");");
    }


    /**
     * @return the type the stub answers with, or null when the operation answers with
     * nothing. More than one field is answered with the class that holds them together.
     */
    private static String returnTypeOf(MOModel model, Service service, Operation operation,
            List<Field> fields, String messageType) {
        if (fields.isEmpty()) {
            return null;
        }
        if (fields.size() == 1) {
            return JavaTypeName.of(model, fields.get(0).getType());
        }
        return JavaNaming.packageOf(service, "body") + "."
                + capitalise(operation.getName()) + messageType;
    }

    private static void addArguments(JavaMethodBuilder method, MOModel model, List<Field> fields) {
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            // A field the specification did not describe is documented by its place in the
            // message, which is the only thing there is to say about it.
            String comment = field.getComment() != null ? field.getComment()
                    : field.getName() + " Argument number " + i
                    + " as defined by the service operation";
            method.argument(JavaTypeName.of(model, field.getType()), field.getName(), comment);
        }
    }

    /**
     * @return the arguments handed to the MAL, with a native attribute wrapped in the Union
     * that carries it, or the literal null where there are none.
     */
    private static String arguments(MOModel model, List<Field> fields) {
        if (fields.isEmpty()) {
            // Cast so that the empty case picks the varargs overload rather than reading as
            // a single null array.
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

    private static void addThrows(JavaMethodBuilder method) {
        method.throwing(INTERACTION_EXCEPTION, INTERACTION_THROWN)
                .throwing(MAL_EXCEPTION, EXCEPTION_THROWN);
    }

    /**
     * @return the fields the consumer sends to start the interaction.
     */
    private static List<Field> requestBodyOf(Operation operation) {
        switch (operation.getPattern()) {
            case SEND:
                return fieldsOf(operation.getMessage(InteractionStage.SEND));
            case SUBMIT:
                return fieldsOf(operation.getMessage(InteractionStage.SUBMIT));
            case REQUEST:
                return fieldsOf(operation.getMessage(InteractionStage.REQUEST));
            case INVOKE:
                return fieldsOf(operation.getMessage(InteractionStage.INVOKE));
            case PROGRESS:
                return fieldsOf(operation.getMessage(InteractionStage.PROGRESS));
            default:
                return new ArrayList<Field>();
        }
    }

    private static List<Field> fieldsOf(MessageBody body) {
        return body == null ? new ArrayList<Field>() : body.getFields();
    }

    private static String operationOf(Service service, Operation operation) {
        return JavaNaming.packageOf(service) + "." + service.getName() + "ServiceInfo."
                + operation.getName().toUpperCase() + "_OP";
    }

    /**
     * @return the name of the MAL consumer method that starts this kind of interaction.
     */
    private static String patternCallOf(Operation operation) {
        switch (operation.getPattern()) {
            case SEND:
                return "send";
            case SUBMIT:
                return "submit";
            case REQUEST:
                return "request";
            case INVOKE:
                return "invoke";
            case PROGRESS:
                return "progress";
            default:
                return null;
        }
    }

    private static String capitalise(String text) {
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }
}
