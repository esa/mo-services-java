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
import esa.mo.apigen.model.Area;
import esa.mo.apigen.model.ErrorDefinition;
import esa.mo.apigen.model.Field;
import esa.mo.apigen.model.InteractionStage;
import esa.mo.apigen.model.MOModel;
import esa.mo.apigen.model.MessageBody;
import esa.mo.apigen.model.Operation;
import esa.mo.apigen.model.Service;
import esa.mo.apigen.model.com.COMObject;
import esa.mo.apigen.model.com.ObjectLink;
import esa.mo.apigen.model.com.ObjectReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes the ServiceInfo class of a service: what the service is called and numbered, one
 * constant per operation describing the messages it exchanges, and the errors it can raise.
 * <p>
 * This is what the consumer stubs and provider skeletons name their operations through, so
 * it is written before either of them.
 */
public final class ServiceInfoWriter {

    private static final String OPERATION_FIELD = JavaNaming.MAL + "OperationField";

    /**
     * The step the arguments of an operation take, and the step the fields of a message
     * take past them.
     */
    private static final String ARGUMENT = "\n            ";

    private static final String FIELD = "\n                ";

    private ServiceInfoWriter() {
    }

    /**
     * @return the source of the service's ServiceInfo class.
     */
    public static String write(MOModel model, Area area, Service service) {
        String name = service.getName();
        String upper = name.toUpperCase();
        boolean isCom = service.isExtended();

        JavaClassBuilder clazz = JavaClassBuilder.named(name + "ServiceInfo")
                .inPackage(JavaNaming.packageOf(service))
                .extending(isCom ? JavaNaming.ROOT + "com.COMService" : JavaNaming.MAL + "ServiceInfo")
                .comment("Helper class for " + name + " service.");
        JavaSource out = clazz.open();

        constant("_" + upper + "_SERVICE_NUMBER", "int", String.valueOf(service.getNumber()))
                .comment("Service number literal.").write(out);
        constant(upper + "_SERVICE_NUMBER", JavaNaming.MAL_STRUCTURES + "UShort",
                "new " + JavaNaming.MAL_STRUCTURES + "UShort(_" + upper + "_SERVICE_NUMBER)")
                .comment("Service number instance.").write(out);
        constant(upper + "_SERVICE_NAME", JavaNaming.MAL_STRUCTURES + "Identifier",
                "new " + JavaNaming.MAL_STRUCTURES + "Identifier(\"" + name + "\")")
                .comment("Service name constant.").write(out);

        // The key is what a message is addressed by, so it is built from the numbers rather
        // than from the names.
        constant("SERVICE_KEY", JavaNaming.MAL + "ServiceKey",
                "new " + JavaNaming.MAL + "ServiceKey(" + ARGUMENT + area.getNumber() + ", "
                + area.getVersion() + ", " + upper + "_SERVICE_NUMBER)")
                .scope("private").comment("The service key of this service.").write(out);

        List<Operation> operations = service.getOperations();
        for (Operation operation : operations) {
            writeOperation(out, model, operation);
        }

        // Left empty on purpose: the Area factory creates the elements on demand, so that
        // the class of a type is only loaded once a message carries it.
        constant(upper + "_SERVICE_ELEMENTS", JavaNaming.MAL_STRUCTURES + "Element[]", "{}")
                .comment("Area elements.").write(out);

        constant("OPERATIONS", JavaNaming.MAL + "MALOperation[]",
                "new " + JavaNaming.MAL + "MALOperation[]{" + operationList(operations) + "}")
                .comment("The set of operations for this service.").write(out);

        List<String> comObjects = writeComObjects(out, model, area, service);
        boolean hasComObjects = !comObjects.isEmpty();

        if (hasComObjects) {
            StringBuilder buf = new StringBuilder("{");
            for (String object : comObjects) {
                buf.append("\n        ").append(object).append("_OBJECT,");
            }
            constant("COM_OBJECTS", JavaNaming.ROOT + "com.COMObject[]", buf.append('}').toString())
                    .comment("Object instance.").write(out);
        }

        JavaMethodBuilder.constructor(name + "ServiceInfo")
                .comment("Creates an instance of the " + name + " ServiceInfo.")
                .line("super(SERVICE_KEY, " + upper + "_SERVICE_NAME, " + upper
                        + "_SERVICE_ELEMENTS, OPERATIONS" + (hasComObjects ? ", COM_OBJECTS" : "")
                        + ");")
                .write(out);

        JavaMethodBuilder.named("getArea").asOverride().returns(JavaNaming.MAL + "MALArea", null)
                .line("return " + JavaNaming.packageOf(area) + "." + area.getName() + "Helper."
                        + area.getName().toUpperCase() + "_AREA;")
                .write(out);

        writeGenerateMOError(out, area);
        return clazz.close();
    }

    /**
     * Writes the three constants that describe one operation: its number as a literal, its
     * number as a UShort, and the operation itself with every message it exchanges.
     */
    private static void writeOperation(JavaSource out, MOModel model, Operation operation) {
        String upper = operation.getName().toUpperCase();

        constant("_" + upper + "_OP_NUMBER", "int", String.valueOf(operation.getNumber()))
                .comment("Operation number literal for operation " + upper).write(out);
        constant(upper + "_OP_NUMBER", JavaNaming.MAL_STRUCTURES + "UShort",
                "new " + JavaNaming.MAL_STRUCTURES + "UShort(_" + upper + "_OP_NUMBER)")
                .scope("private").comment("Operation number instance for operation " + upper)
                .write(out);

        constant(upper + "_OP", JavaNaming.MAL + operationTypeOf(operation),
                operationValue(model, operation))
                .comment("Operation instance for operation " + upper).write(out);

        if (operation.getPattern() == esa.mo.apigen.model.InteractionPattern.PUBSUB) {
            writeSubscriptionKeys(out, operation, upper);
        }
    }

    /**
     * The names a subscription is keyed by, as an array and as the list built from it.
     */
    private static void writeSubscriptionKeys(JavaSource out, Operation operation, String upper) {
        String comment = "Key names instance for " + upper
                + " operation of pubsub interaction pattern";
        MessageBody keys = operation.getMessage(InteractionStage.SUBSCRIPTION_KEYS);
        StringBuilder buf = new StringBuilder("{");
        String separator = "";

        if (keys != null) {
            for (Field field : keys.getFields()) {
                buf.append(separator).append("new ").append(JavaNaming.MAL_STRUCTURES)
                        .append("Identifier(\"").append(field.getName()).append("\")");
                separator = ",\n            ";
            }
        }

        constant("_" + upper + "_OP_KEY_NAMES", JavaNaming.MAL_STRUCTURES + "Identifier []",
                buf.append('}').toString()).scope("private").comment(comment).write(out);
        constant(upper + "_OP_KEY_NAMES", JavaNaming.MAL_STRUCTURES + "IdentifierList",
                "new " + JavaNaming.MAL_STRUCTURES + "IdentifierList(new java.util.ArrayList<>("
                + "java.util.Arrays.asList(_" + upper + "_OP_KEY_NAMES)))")
                .scope("private").comment(comment).write(out);
    }

    /**
     * @return the expression that creates the operation, message by message.
     */
    private static String operationValue(MOModel model, Operation operation) {
        String upper = operation.getName().toUpperCase();
        StringBuilder buf = new StringBuilder("new ");
        buf.append(JavaNaming.MAL).append(operationTypeOf(operation)).append("(SERVICE_KEY, ");
        buf.append(ARGUMENT).append(upper).append("_OP_NUMBER, ");
        buf.append(ARGUMENT).append("new ").append(JavaNaming.MAL_STRUCTURES)
                .append("Identifier(\"").append(operation.getName()).append("\"), ");
        buf.append(ARGUMENT).append("new ").append(JavaNaming.MAL_STRUCTURES)
                .append("UShort(").append(operation.getParent().getNumber()).append("), ");

        for (InteractionStage stage : bodyStagesOf(operation)) {
            buf.append(ARGUMENT).append(messageBody(model, operation.getMessage(stage))).append(", ");
        }

        buf.append(ARGUMENT).append(literal(operation.getComment())).append(')');
        return buf.toString();
    }

    /**
     * The stages an operation declares a body for, in the order the operation takes them as
     * arguments. A publish-subscribe operation is described by what it publishes alone.
     */
    private static List<InteractionStage> bodyStagesOf(Operation operation) {
        List<InteractionStage> stages = new ArrayList<InteractionStage>();
        switch (operation.getPattern()) {
            case SEND:
                stages.add(InteractionStage.SEND);
                break;
            case SUBMIT:
                stages.add(InteractionStage.SUBMIT);
                break;
            case REQUEST:
                stages.add(InteractionStage.REQUEST);
                stages.add(InteractionStage.RESPONSE);
                break;
            case INVOKE:
                stages.add(InteractionStage.INVOKE);
                stages.add(InteractionStage.ACK);
                stages.add(InteractionStage.RESPONSE);
                break;
            case PROGRESS:
                stages.add(InteractionStage.PROGRESS);
                stages.add(InteractionStage.ACK);
                stages.add(InteractionStage.UPDATE);
                stages.add(InteractionStage.RESPONSE);
                break;
            case PUBSUB:
                stages.add(InteractionStage.PUBLISH_NOTIFY);
                break;
            default:
                break;
        }
        return stages;
    }

    /**
     * @return the array of fields one message carries, empty if it carries none.
     */
    private static String messageBody(MOModel model, MessageBody body) {
        StringBuilder buf = new StringBuilder("new " + OPERATION_FIELD + "[] {");
        if (body != null) {
            List<Field> fields = body.getFields();
            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);
                buf.append(FIELD).append("new ").append(OPERATION_FIELD).append("(\"")
                        .append(field.getName()).append("\", ").append(field.isCanBeNull())
                        .append(", ").append(JavaTypeName.shortFormOf(model, field.getType()))
                        .append(", ").append(literal(field.getComment())).append(')');
                if (i != fields.size() - 1) {
                    buf.append(',');
                }
            }
        }
        return buf.append('}').toString();
    }

    /**
     * Writes the constants describing the COM objects and events of a service.
     *
     * @return the names of the objects written, in order.
     */
    private static List<String> writeComObjects(JavaSource out, MOModel model, Area area,
            Service service) {
        List<String> written = new ArrayList<String>();
        if (service.getCom() == null) {
            return written;
        }
        for (COMObject object : service.getCom().getObjects()) {
            written.add(writeComObject(out, model, area, service, object, false));
        }
        for (COMObject event : service.getCom().getEvents()) {
            written.add(writeComObject(out, model, area, service, event, true));
        }
        return written;
    }

    /**
     * Writes the five constants that describe one COM object. They are deprecated: a COM
     * object is reached through the service rather than through the ServiceInfo.
     *
     * @return the name of the object, in capitals.
     */
    private static String writeComObject(JavaSource out, MOModel model, Area area,
            Service service, COMObject object, boolean isEvent) {
        String upper = object.getName().toUpperCase();
        String serviceUpper = service.getName().toUpperCase();

        constant("_" + upper + "_OBJECT_NUMBER", "int", String.valueOf(object.getNumber()))
                .asDeprecated().comment("Literal for object " + upper).write(out);
        constant(upper + "_OBJECT_NUMBER", JavaNaming.MAL_STRUCTURES + "UShort",
                "new " + JavaNaming.MAL_STRUCTURES + "UShort(_" + upper + "_OBJECT_NUMBER)")
                .asDeprecated().comment("Instance for object " + upper).write(out);
        constant(upper + "_OBJECT_NAME", JavaNaming.MAL_STRUCTURES + "Identifier",
                "new " + JavaNaming.MAL_STRUCTURES + "Identifier(\"" + object.getName() + "\")")
                .asDeprecated().comment("Object name constant.").write(out);
        constant(upper + "_OBJECT_TYPE", JavaNaming.ROOT + "com.structures.ObjectType",
                "new " + JavaNaming.ROOT + "com.structures.ObjectType(new "
                + JavaNaming.MAL_STRUCTURES + "UShort(" + area.getNumber() + "), "
                + serviceUpper + "_SERVICE_NUMBER, new " + JavaNaming.MAL_STRUCTURES
                + "UOctet(" + area.getVersion() + "), " + upper + "_OBJECT_NUMBER)")
                .asDeprecated().comment("Object type constant.").write(out);

        String related = objectTypeOf(model, object.getRelated());
        String source = objectTypeOf(model, object.getSource());
        JavaFieldBuilder.named(upper + "_OBJECT").asStatic()
                .ofType(JavaNaming.ROOT + "com.COMObject")
                .value("new " + JavaNaming.ROOT + "com.COMObject(" + upper + "_OBJECT_TYPE, "
                        + upper + "_OBJECT_NAME, "
                        + JavaTypeName.shortFormOf(model, object.getBodyType()) + ", "
                        + (object.getRelated() != null) + ", " + related + ", "
                        + (object.getSource() != null) + ", " + source + ", " + isEvent + ")")
                .asDeprecated().comment("Object instance.").write(out);
        return upper;
    }

    /**
     * Names the object type constant a link points at. A link into an object this model
     * does not hold is left unresolved rather than named, which is what the reference does.
     *
     * @param model The model to look the object up in.
     * @param link The link, may be null.
     * @return the constant, or the literal null.
     */
    private static String objectTypeOf(MOModel model, ObjectLink link) {
        if (link == null || link.getTarget() == null) {
            return "null";
        }
        ObjectReference reference = link.getTarget();
        for (Area area : model.getAreas()) {
            if (!area.getName().equals(reference.getArea())) {
                continue;
            }
            for (Service service : area.getServices()) {
                if (!service.getName().equals(reference.getService()) || service.getCom() == null) {
                    continue;
                }
                for (COMObject object : allObjectsOf(service)) {
                    if (object.getNumber() == reference.getNumber()) {
                        return JavaNaming.packageOf(service) + "." + service.getName()
                                + "ServiceInfo." + object.getName().toUpperCase() + "_OBJECT_TYPE";
                    }
                }
            }
        }
        return "null";
    }

    private static List<COMObject> allObjectsOf(Service service) {
        List<COMObject> all = new ArrayList<COMObject>(service.getCom().getObjects());
        all.addAll(service.getCom().getEvents());
        return all;
    }

    /**
     * Writes the method that turns an error number back into the exception it stands for.
     * Only the errors of the area are answered for: a service's own errors are not reached
     * this way.
     */
    private static void writeGenerateMOError(JavaSource out, Area area) {
        JavaMethodBuilder method = JavaMethodBuilder.named("generateMOError").asOverride()
                .returns(JavaNaming.MAL + "MOErrorException", null)
                .argument("int", "errorNumber", null)
                .argument("Object", "extraInfo", null);

        method.line("switch (errorNumber) {");
        for (ErrorDefinition error : area.getErrors()) {
            method.line("    case " + error.getNumber() + ":");
            method.line("        return new " + JavaNaming.packageOf(area) + "."
                    + ExceptionWriter.classNameOf(error.getName()) + "(extraInfo);");
        }
        method.line("}");
        method.line("return null;");
        method.write(out);
    }

    /**
     * @return the names of the operations, one per line after the first.
     */
    private static String operationList(List<Operation> operations) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < operations.size(); i++) {
            buf.append(i == 0 ? "" : ",\n        ")
                    .append(operations.get(i).getName().toUpperCase()).append("_OP");
        }
        return buf.toString();
    }

    /**
     * @return the MAL class that stands for the operation's interaction pattern.
     */
    private static String operationTypeOf(Operation operation) {
        switch (operation.getPattern()) {
            case SEND:
                return "MALSendOperation";
            case SUBMIT:
                return "MALSubmitOperation";
            case REQUEST:
                return "MALRequestOperation";
            case INVOKE:
                return "MALInvokeOperation";
            case PROGRESS:
                return "MALProgressOperation";
            case PUBSUB:
                return "MALPubSubOperation";
            default:
                return null;
        }
    }

    /**
     * @return the comment as a Java string literal, or the literal null where there is none.
     */
    private static String literal(String comment) {
        if (comment == null) {
            return "null";
        }
        return "\"" + comment.replace("\\", "\\\\").replace("\"", "\\\"")
                .replace("\n", "\\n").replace("\r", "\\r") + "\"";
    }

    private static JavaFieldBuilder constant(String name, String type, String value) {
        return JavaFieldBuilder.named(name).asStatic().asFinal().ofType(type).value(value);
    }
}
