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
import esa.mo.apigen.generators.java.JavaTypes;
import esa.mo.apigen.model.Area;
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
 * Writes the consumer adapter of a service: one empty method per message a consumer can
 * receive, for the consumer to override, and one dispatch method per kind of message, which
 * reads the operation number off the header and calls the right one.
 * <p>
 * The dispatch methods are final: what arrives is decided by the specification, so a
 * consumer overrides what it wants to hear about, never how the message is routed.
 */
public final class ConsumerAdapterWriter {

    private static final String HEADER = JavaNaming.MAL + "transport.MALMessageHeader";

    private static final String BODY = JavaNaming.MAL + "transport.MALMessageBody";

    private static final String NOTIFY_BODY = JavaNaming.MAL + "transport.MALNotifyBody";

    private static final String ERROR_BODY = JavaNaming.MAL + "transport.MALErrorBody";

    private static final String MAL_EXCEPTION = JavaNaming.MAL + "MALException";

    private static final String HEADER_COMMENT = "msgHeader The header of the received message";

    private static final String BODY_COMMENT = "body The body of the received message";

    private static final String QOS_COMMENT =
            "qosProperties The QoS properties associated with the message";

    private static final String THROWN = "if an error is detected processing the message.";

    /**
     * The step an argument of a dispatched call takes past the line it starts on.
     */
    private static final String ARGUMENT = ",\n                ";

    private ConsumerAdapterWriter() {
    }

    /**
     * Writes the adapter a consumer subclasses to receive what a provider sends back.
     *
     * @param model Every loaded specification.
     * @param area The area the service belongs to.
     * @param service The service to write the adapter of.
     * @return the source of the service's consumer adapter.
     */
    public static String write(MOModel model, Area area, Service service) {
        String name = service.getName();

        JavaClassBuilder clazz = JavaClassBuilder.named(name + "Adapter").asAbstract()
                .inPackage(JavaNaming.packageOf(service, JavaNaming.CONSUMER))
                .extending(JavaNaming.MAL + "consumer.MALInteractionAdapter")
                .comment("Consumer adapter for " + name + " service.");
        JavaSource out = clazz.open();

        boolean submits = false;
        boolean requests = false;
        boolean invokes = false;
        boolean progresses = false;
        boolean pubsubs = false;

        for (Operation operation : service.getOperations()) {
            String opName = operation.getName();
            switch (operation.getPattern()) {
                case SUBMIT:
                    callback(out, opName + "AckReceived")
                            .comment("Called by the MAL when a SUBMIT acknowledgement is received"
                                    + " from a provider for the operation " + opName)
                            .write(out);
                    error(out, opName + "ErrorReceived",
                            "Called by the MAL when a SUBMIT acknowledgement error is received"
                            + " from a provider for the operation " + opName);
                    submits = true;
                    break;
                case REQUEST:
                    received(out, model, operation, "ResponseReceived", InteractionStage.RESPONSE,
                            "Called by the MAL when a REQUEST response is received from a"
                            + " provider for the operation " + opName);
                    error(out, opName + "ErrorReceived",
                            "Called by the MAL when a REQUEST response error is received from a"
                            + " provider for the operation " + opName);
                    requests = true;
                    break;
                case INVOKE:
                    received(out, model, operation, "AckReceived", InteractionStage.ACK,
                            "Called by the MAL when an INVOKE acknowledgement is received from a"
                            + " provider for the operation " + opName);
                    received(out, model, operation, "ResponseReceived", InteractionStage.RESPONSE,
                            "Called by the MAL when an INVOKE response is received from a"
                            + " provider for the operation " + opName);
                    error(out, opName + "AckErrorReceived",
                            "Called by the MAL when an INVOKE acknowledgement error is received"
                            + " from a provider for the operation " + opName);
                    error(out, opName + "ResponseErrorReceived",
                            "Called by the MAL when an INVOKE response error is received from a"
                            + " provider for the operation " + opName);
                    invokes = true;
                    break;
                case PROGRESS:
                    received(out, model, operation, "AckReceived", InteractionStage.ACK,
                            "Called by the MAL when a PROGRESS acknowledgement is received from a"
                            + " provider for the operation " + opName);
                    received(out, model, operation, "UpdateReceived", InteractionStage.UPDATE,
                            "Called by the MAL when a PROGRESS update is received from a provider"
                            + " for the operation " + opName);
                    received(out, model, operation, "ResponseReceived", InteractionStage.RESPONSE,
                            "Called by the MAL when a PROGRESS response is received from a"
                            + " provider for the operation " + opName);
                    error(out, opName + "AckErrorReceived",
                            "Called by the MAL when a PROGRESS acknowledgement error is received"
                            + " from a provider for the operation " + opName);
                    error(out, opName + "UpdateErrorReceived",
                            "Called by the MAL when a PROGRESS update error is received from a"
                            + " provider for the operation " + opName);
                    error(out, opName + "ResponseErrorReceived",
                            "Called by the MAL when a PROGRESS response error is received from a"
                            + " provider for the operation " + opName);
                    progresses = true;
                    break;
                case PUBSUB:
                    callback(out, opName + "RegisterAckReceived")
                            .comment("Called by the MAL when a PubSub register acknowledgement is"
                                    + " received from a broker for the operation " + opName)
                            .write(out);
                    error(out, opName + "RegisterErrorReceived",
                            "Called by the MAL when a PubSub register acknowledgement error is"
                            + " received from a broker for the operation " + opName);
                    callback(out, opName + "DeregisterAckReceived")
                            .comment("Called by the MAL when a PubSub deregister acknowledgement"
                                    + " is received from a broker for the operation " + opName)
                            .write(out);
                    writeNotifyCallback(out, model, operation);
                    error(out, opName + "NotifyErrorReceived",
                            "Called by the MAL when a PubSub update error is received from a"
                            + " broker for the operation " + opName);
                    pubsubs = true;
                    break;
                default:
                    break;
            }
        }

        if (submits) {
            dispatch(out, model, service, InteractionPattern.SUBMIT, "submitAck", "Ack", null, false,
                    "Called by the MAL when a SUBMIT acknowledgement is received from a provider.");
            dispatchError(out, service, InteractionPattern.SUBMIT, "submit", "",
                    "Called by the MAL when a SUBMIT acknowledgement error is received from a provider.");
        }
        if (requests) {
            dispatch(out, model, service, InteractionPattern.REQUEST, "requestResponse", "Response",
                    InteractionStage.RESPONSE, true,
                    "Called by the MAL when a REQUEST response is received from a provider.");
            dispatchError(out, service, InteractionPattern.REQUEST, "request", "",
                    "Called by the MAL when a REQUEST response error is received from a provider.");
        }
        if (invokes) {
            dispatch(out, model, service, InteractionPattern.INVOKE, "invokeAck", "Ack",
                    InteractionStage.ACK, true,
                    "Called by the MAL when an INVOKE acknowledgement is received from a provider.");
            dispatchError(out, service, InteractionPattern.INVOKE, "invokeAck", "Ack",
                    "Called by the MAL when an INVOKE acknowledgement error is received from a provider.");
            dispatch(out, model, service, InteractionPattern.INVOKE, "invokeResponse", "Response",
                    InteractionStage.RESPONSE, true,
                    "Called by the MAL when an INVOKE response is received from a provider.");
            dispatchError(out, service, InteractionPattern.INVOKE, "invokeResponse", "Response",
                    "Called by the MAL when an INVOKE response error is received from a provider.");
        }
        if (progresses) {
            dispatch(out, model, service, InteractionPattern.PROGRESS, "progressAck", "Ack",
                    InteractionStage.ACK, true,
                    "Called by the MAL when a PROGRESS acknowledgement is received from a provider.");
            dispatchError(out, service, InteractionPattern.PROGRESS, "progressAck", "Ack",
                    "Called by the MAL when a PROGRESS acknowledgement error is received from a provider.");
            dispatch(out, model, service, InteractionPattern.PROGRESS, "progressUpdate", "Update",
                    InteractionStage.UPDATE, true,
                    "Called by the MAL when a PROGRESS update is received from a provider.");
            dispatchError(out, service, InteractionPattern.PROGRESS, "progressUpdate", "Update",
                    "Called by the MAL when a PROGRESS update error is received from a provider.");
            dispatch(out, model, service, InteractionPattern.PROGRESS, "progressResponse", "Response",
                    InteractionStage.RESPONSE, true,
                    "Called by the MAL when a PROGRESS response is received from a provider.");
            dispatchError(out, service, InteractionPattern.PROGRESS, "progressResponse", "Response",
                    "Called by the MAL when a PROGRESS response error is received from a provider.");
        }
        if (pubsubs) {
            dispatch(out, model, service, InteractionPattern.PUBSUB, "registerAck", "RegisterAck",
                    InteractionStage.ACK, false,
                    "Called by the MAL when a PubSub register acknowledgement is received from a broker.");
            dispatchError(out, service, InteractionPattern.PUBSUB, "register", "Register",
                    "Called by the MAL when a PubSub register acknowledgement error is received from a broker.");
            writeNotifyDispatch(out, model, area, service);
            dispatchError(out, service, InteractionPattern.PUBSUB, "notify", "Notify",
                    "Called by the MAL when a PubSub update error is received from a broker.");
            dispatch(out, model, service, InteractionPattern.PUBSUB, "deregisterAck", "DeregisterAck",
                    InteractionStage.ACK, false,
                    "Called by the MAL when a PubSub deregister acknowledgement is received from a broker.");

            JavaMethodBuilder.named("notifyReceivedFromOtherService")
                    .comment("Called by the MAL when a PubSub update from another service is"
                            + " received from a broker.")
                    .argument(HEADER, "msgHeader", HEADER_COMMENT)
                    .argument(NOTIFY_BODY, "body", BODY_COMMENT)
                    .argument("java.util.Map", "qosProperties", QOS_COMMENT)
                    .throwing(MAL_EXCEPTION, THROWN)
                    .write(out);
        }

        return clazz.close();
    }

    /**
     * A callback that carries nothing but the header.
     */
    private static JavaMethodBuilder callback(JavaSource out, String name) {
        return JavaMethodBuilder.named(name)
                .argument(HEADER, "msgHeader", HEADER_COMMENT)
                .argument("java.util.Map", "qosProperties", QOS_COMMENT);
    }

    /**
     * A callback that carries the fields of one stage of the interaction.
     */
    private static void received(JavaSource out, MOModel model, Operation operation,
            String suffix, InteractionStage stage, String comment) {
        JavaMethodBuilder method = JavaMethodBuilder.named(operation.getName() + suffix)
                .comment(comment)
                .argument(HEADER, "msgHeader", HEADER_COMMENT);
        addFields(method, model, fieldsOf(operation.getMessage(stage)));
        method.argument("java.util.Map", "qosProperties", QOS_COMMENT).write(out);
    }

    /**
     * A callback that carries an error instead of a body.
     */
    private static void error(JavaSource out, String name, String comment) {
        JavaMethodBuilder.named(name).comment(comment)
                .argument(HEADER, "msgHeader", HEADER_COMMENT)
                .argument(JavaNaming.MAL + "MOErrorException", "error",
                        "error The received error message")
                .argument("java.util.Map", "qosProperties", QOS_COMMENT)
                .write(out);
    }

    /**
     * The notify callback carries what every update carries - which subscription it answers
     * and the header of the update - then the typed keys, then the published fields.
     */
    private static void writeNotifyCallback(JavaSource out, MOModel model, Operation operation) {
        JavaMethodBuilder method = JavaMethodBuilder.named(operation.getName() + "NotifyReceived")
                .comment("Called by the MAL when a PubSub update is received from a broker for"
                        + " the operation " + operation.getName())
                .argument(HEADER, "msgHeader", HEADER_COMMENT)
                .argument(JavaNaming.MAL_STRUCTURES + "Identifier", "subscriptionId",
                        "The subscriptionId of the subscription.")
                .argument(JavaNaming.MAL_STRUCTURES + "UpdateHeader", "updateHeader",
                        "The Update header.")
                .argument(SubscriptionKeysWriter.classNameOf(operation), "keys",
                        "The typed Subscription Key accessors for this update");
        // The published fields are numbered after the two the MAL always sends first.
        addFields(method, model,
                fieldsOf(operation.getMessage(InteractionStage.PUBLISH_NOTIFY)), 2);
        method.argument("java.util.Map", "qosProperties", QOS_COMMENT).write(out);
    }

    /**
     * Writes one dispatch method: it reads the operation number off the header and calls the
     * callback of that operation, unpacking the body as it goes.
     */
    private static void dispatch(JavaSource out, MOModel model, Service service,
            InteractionPattern pattern, String name, String suffix, InteractionStage stage,
            boolean carriesBody, String comment) {
        JavaMethodBuilder method = JavaMethodBuilder.named(name + "Received").asFinal().asOverride()
                .comment(comment)
                .argument(HEADER, "msgHeader", HEADER_COMMENT);
        if (carriesBody) {
            method.argument(BODY, "body", BODY_COMMENT);
        }
        method.argument("java.util.Map", "qosProperties", QOS_COMMENT)
                .throwing(MAL_EXCEPTION, THROWN);

        method.line("switch (msgHeader.getOperation().getValue()) {");
        for (Operation operation : service.getOperations()) {
            if (operation.getPattern() != pattern) {
                continue;
            }
            method.line("  case " + operationNumberOf(service, operation) + ":");
            String arguments = stage == null ? ""
                    : unpacked(model, fieldsOf(operation.getMessage(stage)), 0);
            method.line("    " + operation.getName() + suffix + "Received(msgHeader"
                    + arguments + ", qosProperties);");
            method.line("    break;");
        }
        method.line("  default:");
        method.line("    throw new " + MAL_EXCEPTION + "(\"Consumer adapter was not expecting"
                + " operation number \" + msgHeader.getOperation().getValue());");
        method.line("}");
        method.write(out);
    }

    /**
     * Writes the dispatch method for errors, which have no body to unpack.
     */
    private static void dispatchError(JavaSource out, Service service, InteractionPattern pattern,
            String name, String suffix, String comment) {
        JavaMethodBuilder method = JavaMethodBuilder.named(name + "ErrorReceived")
                .asFinal().asOverride().comment(comment)
                .argument(HEADER, "msgHeader", HEADER_COMMENT)
                .argument(ERROR_BODY, "body", BODY_COMMENT)
                .argument("java.util.Map", "qosProperties", QOS_COMMENT)
                .throwing(MAL_EXCEPTION, THROWN);

        method.line("switch (msgHeader.getOperation().getValue()) {");
        for (Operation operation : service.getOperations()) {
            if (operation.getPattern() != pattern) {
                continue;
            }
            method.line("  case " + operationNumberOf(service, operation) + ":");
            method.line("    " + operation.getName() + suffix
                    + "ErrorReceived(msgHeader, body.getError(), qosProperties);");
            method.line("    break;");
        }
        method.line("  default:");
        method.line("    throw new " + MAL_EXCEPTION + "(\"Consumer adapter was not expecting"
                + " operation number \" + msgHeader.getOperation().getValue());");
        method.line("}");
        method.write(out);
    }

    /**
     * The notify dispatch also checks the update belongs to this service: a broker may hold
     * subscriptions of several, and an update of another one is handed on rather than
     * misread.
     */
    private static void writeNotifyDispatch(JavaSource out, MOModel model, Area area,
            Service service) {
        String areaHelper = JavaNaming.packageOf(area) + "." + area.getName() + "Helper";
        String serviceInfo = JavaNaming.packageOf(service) + "." + service.getName() + "ServiceInfo";

        JavaMethodBuilder method = JavaMethodBuilder.named("notifyReceived").asFinal().asOverride()
                .comment("Called by the MAL when a PubSub update is received from a broker.")
                .argument(HEADER, "msgHeader", HEADER_COMMENT)
                .argument(NOTIFY_BODY, "body", BODY_COMMENT)
                .argument(JavaNaming.MAL_STRUCTURES + "IdentifierList", "selectedKeys",
                        "selectedKeys The selected Subscription Key names, or null if trimming"
                        + " was not enabled")
                .argument("java.util.Map", "qosProperties", QOS_COMMENT)
                .throwing(MAL_EXCEPTION, THROWN);

        method.line("if ((" + areaHelper + "." + area.getName().toUpperCase()
                + "_AREA_NUMBER.equals(msgHeader.getServiceArea())) && (" + serviceInfo + "."
                + service.getName().toUpperCase()
                + "_SERVICE_NUMBER.equals(msgHeader.getService()))) {");
        method.line("  switch (msgHeader.getOperation().getValue()) {");

        for (Operation operation : service.getOperations()) {
            if (operation.getPattern() != InteractionPattern.PUBSUB) {
                continue;
            }
            method.line("    case " + operationNumberOf(service, operation) + ":");
            // The subscription identifier and the update header come first, then the keys
            // built from that same header, then what was published.
            String head = ARGUMENT + "(" + JavaNaming.MAL_STRUCTURES
                    + "Identifier) body.getBodyElement(0, new " + JavaNaming.MAL_STRUCTURES
                    + "Identifier())" + ARGUMENT + "(" + JavaNaming.MAL_STRUCTURES
                    + "UpdateHeader) body.getBodyElement(1, new " + JavaNaming.MAL_STRUCTURES
                    + "UpdateHeader())";
            String keys = ARGUMENT + "new " + SubscriptionKeysWriter.classNameOf(operation)
                    + "((" + JavaNaming.MAL_STRUCTURES + "UpdateHeader) body.getBodyElement(1, new "
                    + JavaNaming.MAL_STRUCTURES + "UpdateHeader()), selectedKeys)";
            String published = unpacked(model,
                    fieldsOf(operation.getMessage(InteractionStage.PUBLISH_NOTIFY)), 2);
            method.line("      " + operation.getName() + "NotifyReceived(msgHeader"
                    + head + keys + published + ", qosProperties);");
            method.line("      break;");
        }
        method.line("    default:");
        method.line("      throw new " + MAL_EXCEPTION + "(\"Consumer adapter was not expecting"
                + " operation number \" + msgHeader.getOperation().getValue());");
        method.line("  }");
        method.line("}");
        method.line("else {");
        method.line("  notifyReceivedFromOtherService(msgHeader, body, qosProperties);");
        method.line("}");
        method.write(out);
    }

    /**
     * @return the fields taken out of the body, each on its own line, starting at the given
     * position in it.
     */
    private static String unpacked(MOModel model, List<Field> fields, int first) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            int index = first + i;
            buf.append(ARGUMENT);
            if (JavaTypeName.isNativeAttribute(model, field.getType())) {
                String union = JavaNaming.MAL_STRUCTURES + "Union";
                String element = "body.getBodyElement(" + index + ", new " + union + "("
                        + JavaTypes.nativeDefault(field.getType().getName()) + "))";
                buf.append("(").append(element).append(" == null) ? null : ((").append(union)
                        .append(") ").append(element).append(").get")
                        .append(field.getType().getName()).append("Value()");
            } else {
                buf.append("(").append(JavaTypeName.of(model, field.getType()))
                        .append(") body.getBodyElement(").append(index).append(", ")
                        .append(JavaTypeName.expectedTypeOf(model, field.getType())).append(")");
            }
        }
        return buf.toString();
    }


    private static void addFields(JavaMethodBuilder method, MOModel model, List<Field> fields) {
        addFields(method, model, fields, 0);
    }

    private static void addFields(JavaMethodBuilder method, MOModel model, List<Field> fields,
            int first) {
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            String comment = field.getComment() != null ? field.getComment()
                    : field.getName() + " Argument number " + (first + i)
                    + " as defined by the service operation";
            method.argument(JavaTypeName.of(model, field.getType()), field.getName(), comment);
        }
    }

    private static List<Field> fieldsOf(MessageBody body) {
        return body == null ? new ArrayList<Field>() : body.getFields();
    }

    private static String operationNumberOf(Service service, Operation operation) {
        return JavaNaming.packageOf(service) + "." + service.getName() + "ServiceInfo._"
                + operation.getName().toUpperCase() + "_OP_NUMBER";
    }
}
