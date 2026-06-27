/* ----------------------------------------------------------------------------
 * Copyright (C) 2022      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Service Stub Generator
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
package esa.mo.tools.stubgen.java;

import esa.mo.tools.stubgen.GeneratorLangs;
import esa.mo.tools.stubgen.StubUtils;
import static esa.mo.tools.stubgen.GeneratorLangs.CONSUMER_FOLDER;
import static esa.mo.tools.stubgen.GeneratorLangs.TRANSPORT_FOLDER;
import esa.mo.tools.stubgen.specification.CompositeField;
import esa.mo.tools.stubgen.specification.FieldInfo;
import esa.mo.tools.stubgen.specification.InteractionPatternEnum;
import esa.mo.tools.stubgen.specification.OperationSummary;
import esa.mo.tools.stubgen.specification.ServiceSummary;
import esa.mo.tools.stubgen.specification.StdStrings;
import esa.mo.tools.stubgen.specification.TypeUtils;
import esa.mo.tools.stubgen.specification.AttributeTypeDetails;
import esa.mo.tools.stubgen.writers.ClassWriter;
import esa.mo.tools.stubgen.writers.LanguageWriter;
import esa.mo.tools.stubgen.writers.MethodWriter;
import esa.mo.xsd.MessageBodyType;
import esa.mo.xsd.NamedElementReferenceWithCommentType;
import esa.mo.xsd.OperationErrorList;
import esa.mo.xsd.TypeReference;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class JavaConsumer {

    private final GeneratorLangs generator;
    private final boolean supportsToValue;
    private final boolean supportsAsync;

    public JavaConsumer(GeneratorLangs generator, boolean supportsToValue, boolean supportsAsync) {
        this.generator = generator;
        this.supportsToValue = supportsToValue;
        this.supportsAsync = supportsAsync;
    }

    public void createServiceConsumerAdapter(File consumerFolder, String areaName,
            String serviceName, ServiceSummary summary) throws IOException {
        String className = serviceName + "Adapter";

        ClassWriter file = generator.createClassFile(consumerFolder, className);

        file.addPackageStatement(areaName, serviceName, CONSUMER_FOLDER);

        String throwsMALException = generator.createElementType(StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String areaHelper = generator.createElementType(areaName, null, null, areaName + "Helper");
        String serviceInfoName = generator.createElementType(areaName, serviceName, null, serviceName + JavaServiceInfo.SERVICE_INFO);

        CompositeField stdHeaderArg = generator.createCompositeElementsDetails(file, false, "msgHeader",
                TypeUtils.createTypeReference(StdStrings.MAL, TRANSPORT_FOLDER, "MALMessageHeader", false),
                false, true, "msgHeader The header of the received message");
        CompositeField stdBodyArg = generator.createCompositeElementsDetails(file, false, "body",
                TypeUtils.createTypeReference(StdStrings.MAL, TRANSPORT_FOLDER, "MALMessageBody", false),
                false, true, "body The body of the received message");
        CompositeField stdNotifyBodyArg = generator.createCompositeElementsDetails(file, false, "body",
                TypeUtils.createTypeReference(StdStrings.MAL, TRANSPORT_FOLDER, "MALNotifyBody", false),
                false, true, "body The body of the received message");
        CompositeField stdErrorBodyArg = generator.createCompositeElementsDetails(file, false, "body",
                TypeUtils.createTypeReference(StdStrings.MAL, TRANSPORT_FOLDER, "MALErrorBody", false),
                false, true, "body The body of the received message");
        CompositeField stdQosArg = generator.createCompositeElementsDetails(file, false, "qosProperties",
                TypeUtils.createTypeReference(null, null, "Map<_String;_String>", false),
                false, true, "qosProperties The QoS properties associated with the message");
        CompositeField stdErrorArg = generator.createCompositeElementsDetails(file, false, "error",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "MOErrorException", false),
                false, true, "error The received error message");
        CompositeField stdSelectedKeysArg = generator.createCompositeElementsDetails(file, false, "selectedKeys",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "Identifier", true),
                true, true, "selectedKeys The selected Subscription Key names, or null if trimming was not enabled");
        List<CompositeField> stdNoBodyArgs = StubUtils.concatenateArguments(stdHeaderArg, stdQosArg);
        List<CompositeField> stdBodyArgs = StubUtils.concatenateArguments(stdHeaderArg, stdBodyArg, stdQosArg);
        List<CompositeField> stdNotifyBodyArgs = StubUtils.concatenateArguments(stdHeaderArg, stdNotifyBodyArg, stdQosArg);
        // The generated notifyReceived overrides the four-argument variant on
        // MALInteractionAdapter so it receives the subscription's selectedKeys.
        List<CompositeField> stdNotifyDispatchArgs = StubUtils.concatenateArguments(stdHeaderArg,
                StubUtils.concatenateArguments(stdNotifyBodyArg,
                        StubUtils.concatenateArguments(stdSelectedKeysArg, stdQosArg)));
        List<CompositeField> stdErrorBodyArgs = StubUtils.concatenateArguments(stdHeaderArg, stdErrorBodyArg, stdQosArg);
        List<CompositeField> stdErrorArgs = StubUtils.concatenateArguments(stdHeaderArg, stdErrorArg, stdQosArg);

        file.addClassOpenStatement(className, false, true,
                generator.createElementType(StdStrings.MAL, null, CONSUMER_FOLDER, "MALInteractionAdapter"),
                null, "Consumer adapter for " + serviceName + " service.");

        // Implement the generation of the adapter
        boolean submitRequired = false;
        boolean requestRequired = false;
        boolean invokeRequired = false;
        boolean progressRequired = false;
        boolean pubsubRequired = false;

        if (supportsToValue) {
            file.addConstructor(StdStrings.PUBLIC, className,
                    generator.createCompositeElementsDetails(file, false, "consumer",
                            TypeUtils.createTypeReference(StdStrings.MAL, CONSUMER_FOLDER, "MALConsumer", false),
                            false, true, null),
                    true, null, null, null).addMethodCloseStatement();
        }

        for (OperationSummary op : summary.getOperations()) {
            switch (op.getPattern()) {
                case SUBMIT_OP: {
                    file.method(op.getName() + "AckReceived").asVirtual().returnActual()
                            .addArgument(stdHeaderArg).addArgument(stdQosArg)
                            .comment("Called by the MAL when a SUBMIT acknowledgement is received from a provider for the operation " + op.getName())
                            .open().addMethodCloseStatement();
                    file.method(op.getName() + "ErrorReceived").asVirtual().returnActual()
                            .addArguments(stdErrorArgs)
                            .comment("Called by the MAL when a SUBMIT acknowledgement error is received from a provider for the operation " + op.getName())
                            .open().addMethodCloseStatement();
                    submitRequired = true;
                    break;
                }
                case REQUEST_OP: {
                    List<CompositeField> opArgs = StubUtils.concatenateArguments(stdHeaderArg,
                            StubUtils.concatenateArguments(generator.createOperationArguments(generator.getConfig(), file, op.getRetTypes()), stdQosArg));

                    file.method(op.getName() + "ResponseReceived").asVirtual().returnActual()
                            .addArguments(opArgs)
                            .comment("Called by the MAL when a REQUEST response is received from a provider for the operation " + op.getName())
                            .open().addMethodCloseStatement();
                    file.method(op.getName() + "ErrorReceived").asVirtual().returnActual()
                            .addArguments(stdErrorArgs)
                            .comment("Called by the MAL when a REQUEST response error is received from a provider for the operation " + op.getName())
                            .open().addMethodCloseStatement();
                    requestRequired = true;
                    break;
                }
                case INVOKE_OP: {
                    List<CompositeField> opArgsA = StubUtils.concatenateArguments(stdHeaderArg,
                            StubUtils.concatenateArguments(generator.createOperationArguments(generator.getConfig(), file, op.getAckTypes()), stdQosArg));
                    List<CompositeField> opArgsR = StubUtils.concatenateArguments(stdHeaderArg,
                            StubUtils.concatenateArguments(generator.createOperationArguments(generator.getConfig(), file, op.getRetTypes()), stdQosArg));

                    file.method(op.getName() + "AckReceived").asVirtual().returnActual()
                            .addArguments(opArgsA)
                            .comment("Called by the MAL when an INVOKE acknowledgement is received from a provider for the operation " + op.getName())
                            .open().addMethodCloseStatement();
                    file.method(op.getName() + "ResponseReceived").asVirtual().returnActual()
                            .addArguments(opArgsR)
                            .comment("Called by the MAL when an INVOKE response is received from a provider for the operation " + op.getName())
                            .open().addMethodCloseStatement();
                    file.method(op.getName() + "AckErrorReceived").asVirtual().returnActual()
                            .addArguments(stdErrorArgs)
                            .comment("Called by the MAL when an INVOKE acknowledgement error is received from a provider for the operation " + op.getName())
                            .open().addMethodCloseStatement();
                    file.method(op.getName() + "ResponseErrorReceived").asVirtual().returnActual()
                            .addArguments(stdErrorArgs)
                            .comment("Called by the MAL when an INVOKE response error is received from a provider for the operation " + op.getName())
                            .open().addMethodCloseStatement();
                    invokeRequired = true;
                    break;
                }
                case PROGRESS_OP: {
                    List<CompositeField> opArgsA = StubUtils.concatenateArguments(stdHeaderArg,
                            StubUtils.concatenateArguments(generator.createOperationArguments(generator.getConfig(), file, op.getAckTypes()), stdQosArg));
                    List<CompositeField> opArgsU = StubUtils.concatenateArguments(stdHeaderArg,
                            StubUtils.concatenateArguments(generator.createOperationArguments(generator.getConfig(), file, op.getUpdateTypes()), stdQosArg));
                    List<CompositeField> opArgsR = StubUtils.concatenateArguments(stdHeaderArg,
                            StubUtils.concatenateArguments(generator.createOperationArguments(generator.getConfig(), file, op.getRetTypes()), stdQosArg));

                    file.method(op.getName() + "AckReceived").asVirtual().returnActual()
                            .addArguments(opArgsA)
                            .comment("Called by the MAL when a PROGRESS acknowledgement is received from a provider for the operation " + op.getName())
                            .open().addMethodCloseStatement();
                    file.method(op.getName() + "UpdateReceived").asVirtual().returnActual()
                            .addArguments(opArgsU)
                            .comment("Called by the MAL when a PROGRESS update is received from a provider for the operation " + op.getName())
                            .open().addMethodCloseStatement();
                    file.method(op.getName() + "ResponseReceived").asVirtual().returnActual()
                            .addArguments(opArgsR)
                            .comment("Called by the MAL when a PROGRESS response is received from a provider for the operation " + op.getName())
                            .open().addMethodCloseStatement();
                    file.method(op.getName() + "AckErrorReceived").asVirtual().returnActual()
                            .addArguments(stdErrorArgs)
                            .comment("Called by the MAL when a PROGRESS acknowledgement error is received from a provider for the operation " + op.getName())
                            .open().addMethodCloseStatement();
                    file.method(op.getName() + "UpdateErrorReceived").asVirtual().returnActual()
                            .addArguments(stdErrorArgs)
                            .comment("Called by the MAL when a PROGRESS update error is received from a provider for the operation " + op.getName())
                            .open().addMethodCloseStatement();
                    file.method(op.getName() + "ResponseErrorReceived").asVirtual().returnActual()
                            .addArguments(stdErrorArgs)
                            .comment("Called by the MAL when a PROGRESS response error is received from a provider for the operation " + op.getName())
                            .open().addMethodCloseStatement();
                    progressRequired = true;
                    break;
                }
                case PUBSUB_OP: {
                    List<FieldInfo> retTypes = new LinkedList<>();
                    boolean nullableField = false; // Just for subscriptionId, and updateHeader

                    retTypes.add(0, TypeUtils.convertTypeReference(generator,
                            TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, false),
                            "subscriptionId", "The subscriptionId of the subscription.", nullableField));
                    retTypes.add(1, TypeUtils.convertTypeReference(generator,
                            TypeUtils.createTypeReference(StdStrings.MAL, null, "UpdateHeader", false),
                            "updateHeader", "The Update header.", nullableField));

                    for (FieldInfo ti : op.getRetTypes()) {
                        retTypes.add(ti);
                    }

                    List<CompositeField> opArgsU = new ArrayList<>();
                    opArgsU.add(stdHeaderArg);
                    opArgsU.addAll(StubUtils.concatenateArguments(generator.createOperationArguments(generator.getConfig(), file, retTypes)));
                    opArgsU.add(stdQosArg);

                    // Insert the typed Subscription Key accessors right after the
                    // updateHeader argument (header, subscriptionId, updateHeader, keys, ...).
                    CompositeField keysArg = file.field(subscriptionKeysClassName(op), "keys",
                            "The typed Subscription Key accessors for this update");
                    opArgsU.add(3, keysArg);

                    file.method(op.getName() + "RegisterAckReceived").asVirtual().returnActual()
                            .addArgument(stdHeaderArg).addArgument(stdQosArg)
                            .comment("Called by the MAL when a PubSub register acknowledgement is received from a broker for the operation " + op.getName())
                            .open().addMethodCloseStatement();
                    file.method(op.getName() + "RegisterErrorReceived").asVirtual().returnActual()
                            .addArguments(stdErrorArgs)
                            .comment("Called by the MAL when a PubSub register acknowledgement error is received from a broker for the operation " + op.getName())
                            .open().addMethodCloseStatement();
                    file.method(op.getName() + "DeregisterAckReceived").asVirtual().returnActual()
                            .addArgument(stdHeaderArg).addArgument(stdQosArg)
                            .comment("Called by the MAL when a PubSub deregister acknowledgement is received from a broker for the operation " + op.getName())
                            .open().addMethodCloseStatement();
                    file.method(op.getName() + "NotifyReceived").asVirtual().returnActual()
                            .addArguments(opArgsU)
                            .comment("Called by the MAL when a PubSub update is received from a broker for the operation " + op.getName())
                            .open().addMethodCloseStatement();
                    file.method(op.getName() + "NotifyErrorReceived").asVirtual().returnActual()
                            .addArguments(stdErrorArgs)
                            .comment("Called by the MAL when a PubSub update error is received from a broker for the operation " + op.getName())
                            .open().addMethodCloseStatement();
                    pubsubRequired = true;
                    break;
                }
            }
        }

        if (submitRequired || supportsToValue) {
            createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.SUBMIT_OP,
                    "submitAck", "Ack", 0, stdNoBodyArgs, serviceInfoName, throwsMALException,
                    summary, "Called by the MAL when a SUBMIT acknowledgement is received from a provider.");
            createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.SUBMIT_OP,
                    "submit", "", stdErrorBodyArgs, serviceInfoName, throwsMALException,
                    summary, "Called by the MAL when a SUBMIT acknowledgement error is received from a provider.");
        }

        if (requestRequired || supportsToValue) {
            createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.REQUEST_OP,
                    "requestResponse", "Response", 3, stdBodyArgs, serviceInfoName, throwsMALException,
                    summary, "Called by the MAL when a REQUEST response is received from a provider.");
            createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.REQUEST_OP,
                    "request", "", stdErrorBodyArgs, serviceInfoName, throwsMALException,
                    summary, "Called by the MAL when a REQUEST response error is received from a provider.");
        }

        if (invokeRequired || supportsToValue) {
            createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.INVOKE_OP,
                    "invokeAck", "Ack", 1, stdBodyArgs, serviceInfoName, throwsMALException,
                    summary, "Called by the MAL when an INVOKE acknowledgement is received from a provider.");
            createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.INVOKE_OP,
                    "invokeAck", "Ack", stdErrorBodyArgs, serviceInfoName, throwsMALException,
                    summary, "Called by the MAL when an INVOKE acknowledgement error is received from a provider.");
            createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.INVOKE_OP,
                    "invokeResponse", "Response", 3, stdBodyArgs, serviceInfoName, throwsMALException,
                    summary, "Called by the MAL when an INVOKE response is received from a provider.");
            createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.INVOKE_OP,
                    "invokeResponse", "Response", stdErrorBodyArgs, serviceInfoName, throwsMALException,
                    summary, "Called by the MAL when an INVOKE response error is received from a provider.");
        }

        if (progressRequired || supportsToValue) {
            createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.PROGRESS_OP,
                    "progressAck", "Ack", 1, stdBodyArgs, serviceInfoName, throwsMALException,
                    summary, "Called by the MAL when a PROGRESS acknowledgement is received from a provider.");
            createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.PROGRESS_OP,
                    "progressAck", "Ack", stdErrorBodyArgs, serviceInfoName, throwsMALException,
                    summary, "Called by the MAL when a PROGRESS acknowledgement error is received from a provider.");
            createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.PROGRESS_OP,
                    "progressUpdate", "Update", 2, stdBodyArgs, serviceInfoName, throwsMALException,
                    summary, "Called by the MAL when a PROGRESS update is received from a provider.");
            createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.PROGRESS_OP,
                    "progressUpdate", "Update", stdErrorBodyArgs, serviceInfoName, throwsMALException,
                    summary, "Called by the MAL when a PROGRESS update error is received from a provider.");
            createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.PROGRESS_OP,
                    "progressResponse", "Response", 3, stdBodyArgs, serviceInfoName, throwsMALException,
                    summary, "Called by the MAL when a PROGRESS response is received from a provider.");
            createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.PROGRESS_OP,
                    "progressResponse", "Response", stdErrorBodyArgs, serviceInfoName, throwsMALException,
                    summary, "Called by the MAL when a PROGRESS response error is received from a provider.");
        }

        if (pubsubRequired || supportsToValue) {
            createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.PUBSUB_OP,
                    "registerAck", "RegisterAck", 1, stdNoBodyArgs, serviceInfoName, throwsMALException,
                    summary, "Called by the MAL when a PubSub register acknowledgement is received from a broker.");
            createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.PUBSUB_OP,
                    "register", "Register", stdErrorBodyArgs, serviceInfoName, throwsMALException,
                    summary, "Called by the MAL when a PubSub register acknowledgement error is received from a broker.");
            createServiceConsumerAdapterNotifyMethod(file, InteractionPatternEnum.PUBSUB_OP,
                    "notify", "Notify", 2, stdNotifyDispatchArgs, areaHelper, areaName, serviceInfoName, serviceName, throwsMALException,
                    summary, "Called by the MAL when a PubSub update is received from a broker.");
            createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.PUBSUB_OP,
                    "notify", "Notify", stdErrorBodyArgs, serviceInfoName, throwsMALException,
                    summary, "Called by the MAL when a PubSub update error is received from a broker.");
            createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.PUBSUB_OP,
                    "deregisterAck", "DeregisterAck", 1, stdNoBodyArgs, serviceInfoName, throwsMALException,
                    summary, "Called by the MAL when a PubSub deregister acknowledgement is received from a broker.");

            file.method("notifyReceivedFromOtherService").asVirtual().returnActual()
                    .addArguments(stdNotifyBodyArgs)
                    .comment("Called by the MAL when a PubSub update from another service is received from a broker.")
                    .addThrows(throwsMALException, "if an error is detected processing the message.")
                    .open().addMethodCloseStatement();
        }

        file.addClassCloseStatement();

        file.flush();

        // Generate a typed Subscription Key accessor class per PubSub operation
        for (OperationSummary op : summary.getOperations()) {
            if (op.getPattern() == InteractionPatternEnum.PUBSUB_OP) {
                createSubscriptionKeysClass(consumerFolder, areaName, serviceName, op);
            }
        }
    }

    /**
     * Returns the name of the generated Subscription Keys accessor class for the
     * given PubSub operation.
     *
     * @param op The PubSub operation.
     * @return The class name.
     */
    private String subscriptionKeysClassName(OperationSummary op) {
        String n = op.getName();
        return Character.toUpperCase(n.charAt(0)) + n.substring(1) + "SubscriptionKeys";
    }

    /**
     * Returns the Subscription Key fields defined by the operation, or an empty
     * list if none are defined.
     *
     * @param op The PubSub operation.
     * @return The ordered list of Subscription Key fields.
     */
    private List<NamedElementReferenceWithCommentType> subscriptionKeysOf(OperationSummary op) {
        MessageBodyType keys = op.getSubscriptionKeys();
        if (keys == null || keys.getField() == null) {
            return new LinkedList<>();
        }
        return keys.getField();
    }

    /**
     * Generates a typed Subscription Key accessor class for a single PubSub
     * operation. The class wraps the NullableAttributeList received in the
     * UpdateHeader and resolves each key by name, so that a trimmed key value
     * list (see CCSDS 521.0-B-3, section 3.6.6.5) is interpreted correctly. The
     * UpdateHeader itself is never modified.
     *
     * @param consumerFolder The consumer folder to write the class into.
     * @param areaName The area name.
     * @param serviceName The service name.
     * @param op The PubSub operation.
     * @throws IOException If there is an IO error.
     */
    protected void createSubscriptionKeysClass(File consumerFolder, String areaName,
            String serviceName, OperationSummary op) throws IOException {
        String className = subscriptionKeysClassName(op);
        List<NamedElementReferenceWithCommentType> keys = subscriptionKeysOf(op);

        ClassWriter file = generator.createClassFile(consumerFolder, className);
        file.addPackageStatement(areaName, serviceName, CONSUMER_FOLDER);
        file.addClassOpenStatement(className, true, false, null, null,
                "Typed accessors for the Subscription Keys of the " + op.getName() + " PubSub operation.");

        CompositeField keyValuesField = generator.createCompositeElementsDetails(file, false, "keyValues",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "NullableAttributeList", false),
                true, true, "The key values as received in the UpdateHeader");
        CompositeField keyNamesField = generator.createCompositeElementsDetails(file, false, "keyNames",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "Identifier", true),
                true, true, "The effective key names for the received key values");
        file.addClassVariable(false, false, StdStrings.PRIVATE, keyValuesField, false, (String) null);
        file.addClassVariable(false, false, StdStrings.PRIVATE, keyNamesField, false, (String) null);

        // The Subscription Key names defined by the operation, in order.
        StringBuilder canon = new StringBuilder(
                "new org.ccsds.moims.mo.mal.structures.IdentifierList(new java.util.ArrayList<>(java.util.Arrays.asList(");
        for (int i = 0; i < keys.size(); i++) {
            canon.append((i == 0) ? "" : ", ");
            canon.append("new org.ccsds.moims.mo.mal.structures.Identifier(\"").append(keys.get(i).getName()).append("\")");
        }
        canon.append(")))");
        CompositeField canonField = generator.createCompositeElementsDetails(file, false, "CANONICAL_KEY_NAMES",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "Identifier", true),
                true, false, "The Subscription Key names defined by the operation, in order");
        file.addClassVariableNewInit(true, true, StdStrings.PRIVATE, canonField, false, false, canon.toString(), false);

        // Constructor
        CompositeField updateHeaderArg = generator.createCompositeElementsDetails(file, false, "updateHeader",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "UpdateHeader", false),
                true, true, "The UpdateHeader received in the NOTIFY message");
        CompositeField selectedKeysArg = generator.createCompositeElementsDetails(file, false, "selectedKeys",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "Identifier", true),
                true, true, "The selectedKeys of the subscription, or null if trimming was not enabled");
        List<CompositeField> ctorArgs = new LinkedList<>();
        ctorArgs.add(updateHeaderArg);
        ctorArgs.add(selectedKeysArg);
        MethodWriter ctor = file.addConstructor(StdStrings.PUBLIC, className, ctorArgs, null, null,
                "Creates an instance from the received UpdateHeader and the subscription selectedKeys.", null);
        ctor.addLine("this.keyValues = (updateHeader == null) ? null : updateHeader.getKeyValues();");
        ctor.addLine("this.keyNames = (selectedKeys != null) ? selectedKeys : CANONICAL_KEY_NAMES;");
        ctor.addMethodCloseStatement();

        // Typed getters
        for (NamedElementReferenceWithCommentType key : keys) {
            createSubscriptionKeyGetter(file, key);
        }

        CompositeField attrReturn = generator.createCompositeElementsDetails(file, false, "_return",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "Attribute", false), true, true, null);
        CompositeField nameArg = generator.createCompositeElementsDetails(file, false, "name",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.STRING, false), false, true,
                "The Subscription Key name");

        MethodWriter byName = file.method("getByName").returnActual().returns(attrReturn).addArgument(nameArg)
                .comment("Returns the Subscription Key value with the given name, or null if it is "
                        + "not present (for example when it was trimmed away or is a custom key that is "
                        + "not part of this subscription).")
                .returnComment("The key value, or null if not present").open();
        byName.addLine("return valueByName(name);");
        byName.addMethodCloseStatement();

        MethodWriter vbn = file.method("valueByName").scope(StdStrings.PRIVATE).returnActual()
                .returns(attrReturn).addArgument(nameArg).open();
        vbn.addLine("if (keyNames == null || keyValues == null) {");
        vbn.addLine("    return null;");
        vbn.addLine("}");
        vbn.addLine("for (int i = 0; i < keyNames.size(); i++) {");
        vbn.addLine("    if (name.equals(keyNames.get(i).getValue())) {");
        vbn.addLine("        if (i >= keyValues.size()) {");
        vbn.addLine("            return null;");
        vbn.addLine("        }");
        vbn.addLine("        org.ccsds.moims.mo.mal.structures.NullableAttribute na = keyValues.get(i);");
        vbn.addLine("        return (na == null) ? null : na.getValue();");
        vbn.addLine("    }");
        vbn.addLine("}");
        vbn.addLine("return null;");
        vbn.addMethodCloseStatement();

        file.addClassCloseStatement();
        file.flush();
    }

    /**
     * Generates a single typed getter for a Subscription Key. The value is
     * resolved by name and converted to a Java-friendly type: native MAL
     * attributes are unwrapped to their Java type, other Attribute types are
     * returned as-is, and Enumeration keys are returned as the UShort numeric
     * value used to transmit them.
     *
     * @param file The class writer.
     * @param key The Subscription Key field.
     * @throws IOException If there is an IO error.
     */
    private void createSubscriptionKeyGetter(ClassWriter file,
            NamedElementReferenceWithCommentType key) throws IOException {
        String name = key.getName();
        String getterName = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
        TypeReference type = key.getType();
        String comment = "Returns the value of the \"" + name + "\" Subscription Key, or null if not present.";

        if (generator.isEnum(type)) {
            CompositeField returnType = generator.createCompositeElementsDetails(file, false, name,
                    TypeUtils.createTypeReference(StdStrings.MAL, null, "UShort", false), false, true, null);
            MethodWriter m = file.method(getterName).returnActual().returns(returnType)
                    .comment(comment + " Enumeration keys are transmitted as their UShort numeric value.")
                    .returnComment("The key value, or null if not present").open();
            m.addLine("return (org.ccsds.moims.mo.mal.structures.UShort) valueByName(\"" + name + "\");");
            m.addMethodCloseStatement();
            return;
        }

        AttributeTypeDetails details = generator.getAttributeDetails(type);
        if (details == null) {
            // The key type could not be resolved to a known Attribute type (for
            // example a malformed type in the service specification). Fall back
            // to the generic Attribute type so the accessor still compiles.
            CompositeField returnType = generator.createCompositeElementsDetails(file, false, name,
                    TypeUtils.createTypeReference(StdStrings.MAL, null, "Attribute", false), true, true, null);
            MethodWriter m = file.method(getterName).returnActual().returns(returnType)
                    .comment(comment).returnComment("The key value, or null if not present").open();
            m.addLine("return valueByName(\"" + name + "\");");
            m.addMethodCloseStatement();
            return;
        }

        CompositeField returnType = generator.createCompositeElementsDetails(file, false, name,
                type, true, true, null);
        MethodWriter m = file.method(getterName).returnActual().returns(returnType)
                .comment(comment).returnComment("The key value, or null if not present").open();
        if (details.isNativeType()) {
            m.addLine("org.ccsds.moims.mo.mal.structures.Attribute v = valueByName(\"" + name + "\");");
            m.addLine("return (v == null) ? null : (" + details.getTargetType()
                    + ") org.ccsds.moims.mo.mal.structures.Attribute.attribute2JavaType(v);");
        } else {
            m.addLine("return (" + details.getTargetType() + ") valueByName(\"" + name + "\");");
        }
        m.addMethodCloseStatement();
    }

    protected void createServiceConsumerAdapterMessageMethod(ClassWriter file, InteractionPatternEnum optype,
            String opname, String subopPostname, int opTypeIndex, List<CompositeField> args,
            String serviceInfoName, String throwsMALException, ServiceSummary summary, String comment) throws IOException {
        MethodWriter method = file.method(opname + "Received").asFinal().asVirtual().returnActual()
                .addArguments(args).comment(comment)
                .addThrows(throwsMALException, "if an error is detected processing the message.").open();
        method.addLine("switch (msgHeader.getOperation().getValue()) {");

        for (OperationSummary op : summary.getOperations()) {
            if (optype == op.getPattern()) {
                String ns = generator.convertToNamespace(serviceInfoName + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
                method.addLine("  case " + ns);
                List<FieldInfo> opTypes = null;
                switch (opTypeIndex) {
                    case 1:
                        opTypes = op.getAckTypes();
                        break;
                    case 2:
                        opTypes = op.getUpdateTypes();
                        break;
                    case 3:
                        opTypes = op.getRetTypes();
                        break;
                    default:
                        break;
                }
                String opArgs = generator.createAdapterMethodsArgs(opTypes, "body", true, false);
                method.addLine("    " + op.getName() + subopPostname + "Received(msgHeader" + opArgs + ", qosProperties);");
                method.addLine("    break;");
            }
        }
        method.addLine("  default:");
        method.addLine("    throw new " + throwsMALException + "(\"Consumer adapter was not expecting operation number \" + msgHeader.getOperation().getValue());");
        method.addLine("}");
        method.addMethodCloseStatement();
    }

    protected void createServiceConsumerAdapterNotifyMethod(ClassWriter file, InteractionPatternEnum optype,
            String opname, String subopPostname, int opTypeIndex, List<CompositeField> args,
            String areaHelper, String areaName, String serviceInfoName, String serviceName,
            String throwsMALException, ServiceSummary summary, String comment) throws IOException {
        MethodWriter method = file.method(opname + "Received").asFinal().asVirtual().returnActual()
                .addArguments(args).comment(comment)
                .addThrows(throwsMALException, "if an error is detected processing the message.").open();

        method.addLine("if ((" + areaHelper + "." + areaName.toUpperCase() + "_AREA_NUMBER.equals(msgHeader.getServiceArea()))"
                + " && "
                + "(" + serviceInfoName + "." + serviceName.toUpperCase() + "_SERVICE_NUMBER.equals(msgHeader.getService()))) {");
        method.addLine("  switch (msgHeader.getOperation().getValue()) {");

        for (OperationSummary op : summary.getOperations()) {
            if (optype == op.getPattern()) {
                String ns = generator.convertToNamespace(serviceInfoName + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
                method.addLine("    case " + ns);

                // The subscriptionId (index 0) and the UpdateHeader (index 1).
                List<FieldInfo> headTypes = new LinkedList<>();
                headTypes.add(TypeUtils.convertTypeReference(generator,
                        TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, false)));
                headTypes.add(TypeUtils.convertTypeReference(generator,
                        TypeUtils.createTypeReference(StdStrings.MAL, null, "UpdateHeader", false)));
                String headArgs = generator.createAdapterMethodsArgs(headTypes, "body", true, false);

                // The typed Subscription Key accessors, built from the same
                // UpdateHeader and the subscription's selectedKeys. The
                // UpdateHeader itself is never modified.
                String keysArg = ",\n                new " + subscriptionKeysClassName(op)
                        + "((org.ccsds.moims.mo.mal.structures.UpdateHeader) body.getBodyElement(1, "
                        + "new org.ccsds.moims.mo.mal.structures.UpdateHeader()), selectedKeys)";

                // The publishNotify body fields start at index 2.
                StringBuilder retArgs = new StringBuilder();
                for (int i = 0; i < op.getRetTypes().size(); i++) {
                    retArgs.append(generator.createAdapterMethodsArgs(op.getRetTypes().get(i), "body", i + 2, true, false));
                }

                method.addLine("      " + op.getName() + subopPostname + "Received(msgHeader"
                        + headArgs + keysArg + retArgs.toString() + ", qosProperties);");
                method.addLine("      break;");
            }
        }
        method.addLine("    default:");
        method.addLine("      throw new " + throwsMALException + "(\"Consumer adapter was not expecting operation number \" + msgHeader.getOperation().getValue());");
        method.addLine("  }");
        method.addLine("}");
        method.addLine("else {");
        method.addLine("  notifyReceivedFromOtherService(msgHeader, body, qosProperties);");
        method.addLine("}");
        method.addMethodCloseStatement();
    }

    protected void createServiceConsumerAdapterErrorMethod(ClassWriter file, InteractionPatternEnum optype,
            String opname, String subopPostname, List<CompositeField> args, String serviceInfoName,
            String throwsMALException, ServiceSummary summary, String comment) throws IOException {
        MethodWriter method = file.method(opname + "ErrorReceived").asFinal().asVirtual().returnActual()
                .addArguments(args).comment(comment)
                .addThrows(throwsMALException, "if an error is detected processing the message.").open();
        method.addLine("switch (msgHeader.getOperation().getValue()) {");

        for (OperationSummary op : summary.getOperations()) {
            if (optype == op.getPattern()) {
                String ns = generator.convertToNamespace(serviceInfoName + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
                method.addLine("  case " + ns);
                method.addLine("    " + op.getName() + subopPostname + "ErrorReceived(msgHeader, body.getError(), qosProperties);");
                method.addLine("    break;");
            }
        }
        method.addLine("  default:");
        method.addLine("    throw new " + throwsMALException + "(\"Consumer adapter was not expecting operation number \" + msgHeader.getOperation().getValue());");
        method.addLine("}");
        method.addMethodCloseStatement();
    }

    public void createServiceConsumerStub(File consumerFolder, String area,
            String service, ServiceSummary summary) throws IOException {
        String className = service + "Stub";

        ClassWriter file = generator.createClassFile(consumerFolder, className);

        file.addPackageStatement(area, service, CONSUMER_FOLDER);

        CompositeField serviceAdapterArg = generator.createCompositeElementsDetails(file, false, "adapter",
                TypeUtils.createTypeReference(area, service + "." + CONSUMER_FOLDER, service + "Adapter", false),
                false, true, "adapter Listener in charge of receiving the messages from the service provider");

        CompositeField lastInteractionStage = generator.createCompositeElementsDetails(file, false, "lastInteractionStage",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UOCTET, false),
                true, true, "lastInteractionStage The last stage of the interaction to continue");
        CompositeField initiationTimestamp = generator.createCompositeElementsDetails(file, false, "initiationTimestamp",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.TIME, false),
                true, true, "initiationTimestamp Timestamp of the interaction initiation message");
        CompositeField transactionId = generator.createCompositeElementsDetails(file, false, "transactionId",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.LONG, false),
                true, true, "transactionId Transaction identifier of the interaction to continue");
        List<CompositeField> continueOpArgs = StubUtils.concatenateArguments(lastInteractionStage, initiationTimestamp, transactionId, serviceAdapterArg);

        String throwsMALException = generator.createElementType(StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String throwsInteractionException = generator.createElementType(StdStrings.MAL, null, null, StdStrings.MALINTERACTIONEXCEPTION);
        String throwsInteractionAndMALException = throwsInteractionException + ", " + throwsMALException;

        CompositeField msgType = generator.createCompositeElementsDetails(file, false, "return",
                TypeUtils.createTypeReference(StdStrings.MAL, TRANSPORT_FOLDER, StdStrings.MALMESSAGE, false),
                false, true, null);
        CompositeField msgBodyType = generator.createCompositeElementsDetails(file, false, "return",
                TypeUtils.createTypeReference(StdStrings.MAL, TRANSPORT_FOLDER, "MALMessageBody", false),
                false, true, null);
        CompositeField uriType = generator.createCompositeElementsDetails(file, false, "return",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.URI, false),
                true, true, null);
        String helperType = generator.createElementType(area, service, null, service + "Helper") + generator.getConfig().getNamingSeparator();
        String serviceInfoType = generator.createElementType(area, service, null, service + JavaServiceInfo.SERVICE_INFO) + generator.getConfig().getNamingSeparator();
        CompositeField consumerType = generator.createCompositeElementsDetails(file, false, "return",
                TypeUtils.createTypeReference(StdStrings.MAL, CONSUMER_FOLDER, "MALConsumer", false),
                false, true, null);
        String consumerMethodCall = "consumer.";
        CompositeField consumerTypeVar = generator.createCompositeElementsDetails(file, false, "consumer",
                TypeUtils.createTypeReference(StdStrings.MAL, CONSUMER_FOLDER, "MALConsumer", false),
                false, true, null);

        file.addClassOpenStatement(className, false, false, null,
                null,
                "Consumer stub for " + service + " service.");
        /*
        file.addClassOpenStatement(className, false, false, null,
                generator.createElementType(area, service, CONSUMER_FOLDER, service),
                "Consumer stub for " + service + " service.");
         */
        file.addClassVariable(false, true, StdStrings.PRIVATE, consumerTypeVar, false, (String) null);

        MethodWriter method = file.addConstructor(StdStrings.PUBLIC, className,
                generator.createCompositeElementsDetails(file, false, "consumer",
                        TypeUtils.createTypeReference(StdStrings.MAL, CONSUMER_FOLDER, "MALConsumer", false),
                        false, true, "consumer The MALConsumer to use in this stub."),
                false, null,
                "Wraps a MALconsumer connection with service specific methods that map from the high level service API to the generic MAL API.", null);
        method.addLine("this.consumer = consumer;");
        method.addMethodCloseStatement();

        method = file.method("getConsumer").returns(generator.createReturnReference(consumerType))
                .comment("Returns the internal MAL consumer object used for sending of messages from this interface")
                .returnComment("The MAL consumer object.").open();
        method.addLine("return consumer;");
        method.addMethodCloseStatement();

        if (supportsToValue) {
            method = file.method("getURI").returns(uriType).open();
            method.addLine("return consumer.getUri();");
            method.addMethodCloseStatement();
        }

        for (OperationSummary op : summary.getOperations()) {
            ArrayList<String> throwsComment = new ArrayList<>();
            throwsComment.add(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.");
            throwsComment.add(throwsMALException + " if there is an implementation exception");
            String operationInstanceVar = serviceInfoType + op.getName().toUpperCase() + "_OP";

            String throwsText = throwsInteractionAndMALException;
            OperationErrorList errors = op.getErrors();

            // This code enables dedicated exceptions on the consumer stubs
            // TBD: The Stubs need to be updated to be able to extract the error and throw it correctly!
            /*
            if (errors != null) {
                String additionalErr = "";
                for (Object e : errors.getErrorOrErrorRef()) {
                    ErrorReferenceType error = (ErrorReferenceType) e;
                    String camelCase = JavaExceptions.convertErrorToClassname(error.getType().getName());
                    String errorArea = error.getType().getArea().toLowerCase();
                    String fullyQualifiedError = "org.ccsds.moims.mo." + errorArea + "." + camelCase;
                    additionalErr += fullyQualifiedError + ", ";
                    // Also add the comments:
                    String comment = (error.getComment() == null) ? "when something goes wrong" : error.getComment();
                    throwsComment.add(fullyQualifiedError + " " + comment);
                }
                throwsText = additionalErr + throwsText;
            }
             */
            switch (op.getPattern()) {
                case SEND_OP: {
                    List<CompositeField> opArgs = generator.createOperationArguments(generator.getConfig(), file, op.getArgTypes());
                    method = file.method(op.getName()).returns(msgType).returnActual()
                            .addArguments(opArgs)
                            .comment(op.getComment()).returnComment("the MAL message sent to initiate the interaction")
                            .addThrows(throwsInteractionException, "if there is a problem during the interaction as defined by the MAL specification.")
                            .addThrows(throwsMALException, "if there is an implementation exception").open();
                    //method.addLine("try {");
                    method.addLine("return " + consumerMethodCall
                            + generator.createConsumerPatternCall(op) + "(" + operationInstanceVar
                            + ", " + generator.createArgNameOrNull(op.getArgTypes()) + ");");
                    //this.appendCatchClauses(method);
                    method.addMethodCloseStatement();
                    break;
                }
                case SUBMIT_OP:
                case REQUEST_OP: {
                    List<CompositeField> opArgs = generator.createOperationArguments(generator.getConfig(), file, op.getArgTypes());
                    CompositeField opRetType = generator.createOperationReturnType(file, area, service, op);
                    String opRetComment = null;
                    String rv = "";
                    if (null != opRetType) {
                        rv = msgBodyType.getTypeName() + " body = ";
                        opRetComment = "The return value of the interaction";
                    }
                    String opGet = rv + consumerMethodCall + generator.createConsumerPatternCall(op)
                            + "(" + operationInstanceVar + ", " + generator.createArgNameOrNull(op.getArgTypes()) + ");";
                    method = file.method(op.getName()).returns(opRetType)
                            .addArguments(opArgs)
                            .comment(op.getComment()).returnComment(opRetComment)
                            .addThrows(throwsInteractionException, "if there is a problem during the interaction as defined by the MAL specification.")
                            .addThrows(throwsMALException, "if there is an implementation exception").open();
                    //method.addLine("try {");
                    method.addLine(opGet);
                    createOperationReturn(file, method, op, opRetType);
                    //this.appendCatchClauses(method);
                    method.addMethodCloseStatement();

                    if (supportsAsync) {
                        method = file.method("async" + StubUtils.preCap(op.getName())).returns(msgType).returnActual()
                                .addArguments(opArgs).addArgument(serviceAdapterArg)
                                .comment("Asynchronous version of method " + op.getName()).returnComment("the MAL message sent to initiate the interaction")
                                .addThrows(throwsInteractionException, "if there is a problem during the interaction as defined by the MAL specification.")
                                .addThrows(throwsMALException, "if there is an implementation exception").open();
                        method.addLine("return " + consumerMethodCall + "async" + StubUtils.preCap(generator.createConsumerPatternCall(op))
                                + "(" + operationInstanceVar + ", adapter, " + generator.createArgNameOrNull(op.getArgTypes()) + ");");
                        method.addMethodCloseStatement();
                    }

                    method = file.method("continue" + StubUtils.preCap(op.getName())).returnActual()
                            .addArguments(continueOpArgs)
                            .comment("Continues a previously started interaction")
                            .addThrows(throwsInteractionException, "if there is a problem during the interaction as defined by the MAL specification.")
                            .addThrows(throwsMALException, "if there is an implementation exception").open();
                    method.addLine(consumerMethodCall + "continueInteraction(" + operationInstanceVar
                            + ", lastInteractionStage, initiationTimestamp, transactionId, adapter);");
                    method.addMethodCloseStatement();
                    break;
                }
                case INVOKE_OP:
                case PROGRESS_OP: {
                    List<CompositeField> opArgs = StubUtils.concatenateArguments(generator.createOperationArguments(generator.getConfig(), file, op.getArgTypes()), serviceAdapterArg);
                    CompositeField opRetType = generator.createOperationReturnType(file, area, service, op);
                    String opRetComment = null;
                    String rv = "";
                    if (null != opRetType) {
                        rv = msgBodyType.getTypeName() + " body = ";
                        opRetComment = "The acknowledge value of the interaction";
                    }
                    String opGet = rv + consumerMethodCall + generator.createConsumerPatternCall(op) + "("
                            + operationInstanceVar + ", adapter, " + generator.createArgNameOrNull(op.getArgTypes()) + ");";
                    method = file.method(op.getName()).returns(opRetType)
                            .addArguments(opArgs)
                            .comment(op.getComment()).returnComment(opRetComment)
                            .addThrows(throwsInteractionException, "if there is a problem during the interaction as defined by the MAL specification.")
                            .addThrows(throwsMALException, "if there is an implementation exception").open();
                    method.addLine(opGet);
                    createOperationReturn(file, method, op, opRetType);
                    method.addMethodCloseStatement();

                    if (supportsAsync) {
                        method = file.method("async" + StubUtils.preCap(op.getName())).returns(msgType).returnActual()
                                .addArguments(opArgs)
                                .comment("Asynchronous version of method " + op.getName()).returnComment("the MAL message sent to initiate the interaction")
                                .addThrows(throwsInteractionException, "if there is a problem during the interaction as defined by the MAL specification.")
                                .addThrows(throwsMALException, "if there is an implementation exception").open();
                        method.addLine("return " + consumerMethodCall + "async" + StubUtils.preCap(generator.createConsumerPatternCall(op))
                                + "(" + operationInstanceVar + ", adapter, " + generator.createArgNameOrNull(op.getArgTypes()) + ");");
                        method.addMethodCloseStatement();
                    }

                    method = file.method("continue" + StubUtils.preCap(op.getName())).returnActual()
                            .addArguments(continueOpArgs)
                            .comment("Continues a previously started interaction")
                            .addThrows(throwsInteractionException, "if there is a problem during the interaction as defined by the MAL specification.")
                            .addThrows(throwsMALException, "if there is an implementation exception").open();
                    method.addLine(consumerMethodCall + "continueInteraction(" + operationInstanceVar + ", lastInteractionStage, initiationTimestamp, transactionId, adapter);");
                    method.addMethodCloseStatement();
                    break;
                }
                case PUBSUB_OP: {
                    CompositeField subStr = generator.createCompositeElementsDetails(file, false, "subscription",
                            TypeUtils.createTypeReference(StdStrings.MAL, null, "Subscription", false),
                            true, true, "subscription the subscription to register for");
                    CompositeField idStr = generator.createCompositeElementsDetails(file, false, "identifierList",
                            TypeUtils.createTypeReference(StdStrings.MAL, null, "Identifier", true),
                            true, true, "identifierList the subscription identifiers to deregister");

                    method = file.method(op.getName() + "Register").returnActual()
                            .addArgument(subStr).addArgument(serviceAdapterArg)
                            .comment("Register method for the " + op.getName() + " PubSub interaction")
                            .addThrows(throwsInteractionException, "if there is a problem during the interaction as defined by the MAL specification.")
                            .addThrows(throwsMALException, "if there is an implementation exception").open();
                    method.addLine(consumerMethodCall + "register(" + operationInstanceVar + ", subscription, adapter);");
                    method.addMethodCloseStatement();

                    if (supportsAsync) {
                        method = file.method("async" + StubUtils.preCap(op.getName()) + "Register").returns(msgType).returnActual()
                                .addArgument(subStr).addArgument(serviceAdapterArg)
                                .comment("Asynchronous version of method " + op.getName() + "Register").returnComment("the MAL message sent to initiate the interaction")
                                .addThrows(throwsInteractionException, "if there is a problem during the interaction as defined by the MAL specification.")
                                .addThrows(throwsMALException, "if there is an implementation exception").open();
                        method.addLine("return " + consumerMethodCall + "asyncRegister(" + operationInstanceVar + ", subscription, adapter);");
                        method.addMethodCloseStatement();
                    }

                    method = file.method(op.getName() + "Deregister").returnActual()
                            .addArgument(idStr)
                            .comment("Deregister method for the " + op.getName() + " PubSub interaction")
                            .addThrows(throwsInteractionException, "if there is a problem during the interaction as defined by the MAL specification.")
                            .addThrows(throwsMALException, "if there is an implementation exception").open();
                    method.addLine(consumerMethodCall + "deregister(" + operationInstanceVar + ", identifierList);");
                    method.addMethodCloseStatement();

                    if (supportsAsync) {
                        method = file.method("async" + StubUtils.preCap(op.getName()) + "Deregister").returns(msgType).returnActual()
                                .addArgument(idStr).addArgument(serviceAdapterArg)
                                .comment("Asynchronous version of method " + op.getName() + "Deregister").returnComment("the MAL message sent to initiate the interaction")
                                .addThrows(throwsInteractionException, "if there is a problem during the interaction as defined by the MAL specification.")
                                .addThrows(throwsMALException, "if there is an implementation exception").open();
                        method.addLine("return " + consumerMethodCall + "asyncDeregister(" + operationInstanceVar + ", identifierList, adapter);");
                        method.addMethodCloseStatement();
                    }
                    break;
                }
            }
        }

        file.addClassCloseStatement();
        file.flush();
    }

    private void appendCatchClauses(MethodWriter method) throws IOException {
        method.addLine("} catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {");
        method.addLine("    throw ex;");
        method.addLine("}");
    }

    private void createOperationReturn(LanguageWriter file, MethodWriter method,
            OperationSummary op, CompositeField opRetType) throws IOException {
        List<FieldInfo> targetTypes = op.getRetTypes();

        if ((InteractionPatternEnum.INVOKE_OP == op.getPattern())
                || (InteractionPatternEnum.PROGRESS_OP == op.getPattern())) {
            targetTypes = op.getAckTypes();
        }

        if ((targetTypes != null) && (!targetTypes.isEmpty())) {
            if (targetTypes.size() == 1) {
                method.addLine(
                        "return "
                        + generator.createOperationArgReturn(file, method, targetTypes.get(0), "body", 0)
                        + ";");
            } else {
                StringBuilder buf = new StringBuilder();

                for (int i = 0; i < targetTypes.size(); i++) {
                    FieldInfo ti = targetTypes.get(i);
                    if (i > 0) {
                        buf.append(", ");
                    }
                    buf.append(generator.createOperationArgReturn(file, method, ti, "body", i));
                }

                method.addLine("return new " + opRetType.getTypeName() + "(" + buf.toString() + ");");
            }
        }
    }
}
