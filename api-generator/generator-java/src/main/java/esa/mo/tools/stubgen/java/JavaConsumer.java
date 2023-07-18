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
import esa.mo.tools.stubgen.specification.InteractionPatternEnum;
import esa.mo.tools.stubgen.specification.OperationSummary;
import esa.mo.tools.stubgen.specification.ServiceSummary;
import esa.mo.tools.stubgen.specification.StdStrings;
import esa.mo.tools.stubgen.specification.TypeInfo;
import esa.mo.tools.stubgen.specification.TypeUtils;
import esa.mo.tools.stubgen.writers.ClassWriter;
import esa.mo.tools.stubgen.writers.LanguageWriter;
import esa.mo.tools.stubgen.writers.MethodWriter;
import esa.mo.xsd.AreaType;
import esa.mo.xsd.ServiceType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

    public void createServiceConsumerAdapter(File consumerFolder, AreaType area,
            ServiceType service, ServiceSummary summary) throws IOException {
        generator.getLog().info(" > Creating consumer adapter: " + service.getName());

        String areaName = area.getName();
        String serviceName = service.getName();
        String className = serviceName + "Adapter";

        ClassWriter file = generator.createClassFile(consumerFolder, className);

        file.addPackageStatement(area, service, CONSUMER_FOLDER);

        String throwsMALException = generator.createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String areaHelper = generator.createElementType(file, areaName, null, null, areaName + "Helper");
        String serviceHelper = generator.createElementType(file, areaName, serviceName, null, serviceName + "Helper");
        String serviceInfoName = generator.createElementType(file, areaName, serviceName, null, serviceName + JavaServiceInfo.SERVICE_INFO);

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
        List<CompositeField> stdNoBodyArgs = StubUtils.concatenateArguments(stdHeaderArg, stdQosArg);
        List<CompositeField> stdBodyArgs = StubUtils.concatenateArguments(stdHeaderArg, stdBodyArg, stdQosArg);
        List<CompositeField> stdNotifyBodyArgs = StubUtils.concatenateArguments(stdHeaderArg, stdNotifyBodyArg, stdQosArg);
        List<CompositeField> stdErrorBodyArgs = StubUtils.concatenateArguments(stdHeaderArg, stdErrorBodyArg, stdQosArg);
        List<CompositeField> stdErrorArgs = StubUtils.concatenateArguments(stdHeaderArg, stdErrorArg, stdQosArg);

        file.addClassOpenStatement(className, false, true,
                generator.createElementType(file, StdStrings.MAL, null, CONSUMER_FOLDER, "MALInteractionAdapter"),
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
                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC,
                            false, true, null, op.getName() + "AckReceived",
                            Arrays.asList(stdHeaderArg, stdQosArg), null,
                            "Called by the MAL when a SUBMIT acknowledgement is received from a provider for the operation " + op.getName(),
                            null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC,
                            false, true, null, op.getName() + "ErrorReceived", stdErrorArgs, null,
                            "Called by the MAL when a SUBMIT acknowledgement error is received from a provider for the operation " + op.getName(),
                            null, null).addMethodCloseStatement();
                    submitRequired = true;
                    break;
                }
                case REQUEST_OP: {
                    List<CompositeField> opArgs = StubUtils.concatenateArguments(stdHeaderArg,
                            StubUtils.concatenateArguments(generator.createOperationArguments(generator.getConfig(), file, op.getRetTypes()), stdQosArg));

                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC,
                            false, true, null, op.getName() + "ResponseReceived",
                            opArgs, null, "Called by the MAL when a REQUEST response is received from a provider for the operation " + op.getName(),
                            null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC,
                            false, true, null, op.getName() + "ErrorReceived", stdErrorArgs, null,
                            "Called by the MAL when a REQUEST response error is received from a provider for the operation " + op.getName(),
                            null, null).addMethodCloseStatement();
                    requestRequired = true;
                    break;
                }
                case INVOKE_OP: {
                    List<CompositeField> opArgsA = StubUtils.concatenateArguments(stdHeaderArg,
                            StubUtils.concatenateArguments(generator.createOperationArguments(generator.getConfig(), file, op.getAckTypes()), stdQosArg));
                    List<CompositeField> opArgsR = StubUtils.concatenateArguments(stdHeaderArg,
                            StubUtils.concatenateArguments(generator.createOperationArguments(generator.getConfig(), file, op.getRetTypes()), stdQosArg));

                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC,
                            false, true, null, op.getName() + "AckReceived", opArgsA, null,
                            "Called by the MAL when an INVOKE acknowledgement is received from a provider for the operation " + op.getName(),
                            null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC,
                            false, true, null, op.getName() + "ResponseReceived", opArgsR, null,
                            "Called by the MAL when an INVOKE response is received from a provider for the operation " + op.getName(),
                            null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC,
                            false, true, null, op.getName() + "AckErrorReceived", stdErrorArgs, null,
                            "Called by the MAL when an INVOKE acknowledgement error is received from a provider for the operation " + op.getName(),
                            null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC,
                            false, true, null, op.getName() + "ResponseErrorReceived", stdErrorArgs, null,
                            "Called by the MAL when an INVOKE response error is received from a provider for the operation " + op.getName(),
                            null, null).addMethodCloseStatement();
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

                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC,
                            false, true, null, op.getName() + "AckReceived", opArgsA, null,
                            "Called by the MAL when a PROGRESS acknowledgement is received from a provider for the operation " + op.getName(),
                            null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC,
                            false, true, null, op.getName() + "UpdateReceived", opArgsU, null,
                            "Called by the MAL when a PROGRESS update is received from a provider for the operation " + op.getName(),
                            null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC,
                            false, true, null, op.getName() + "ResponseReceived", opArgsR, null,
                            "Called by the MAL when a PROGRESS response is received from a provider for the operation " + op.getName(),
                            null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC,
                            false, true, null, op.getName() + "AckErrorReceived", stdErrorArgs, null,
                            "Called by the MAL when a PROGRESS acknowledgement error is received from a provider for the operation " + op.getName(),
                            null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC,
                            false, true, null, op.getName() + "UpdateErrorReceived", stdErrorArgs, null,
                            "Called by the MAL when a PROGRESS update error is received from a provider for the operation " + op.getName(),
                            null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC,
                            false, true, null, op.getName() + "ResponseErrorReceived", stdErrorArgs, null,
                            "Called by the MAL when a PROGRESS response error is received from a provider for the operation " + op.getName(),
                            null, null).addMethodCloseStatement();
                    progressRequired = true;
                    break;
                }
                case PUBSUB_OP: {
                    List<TypeInfo> retTypes = new LinkedList<>();
                    retTypes.add(0, TypeUtils.convertTypeReference(generator,
                            TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, false),
                            "subscriptionId", "The subscriptionId of the subscription."));
                    retTypes.add(1, TypeUtils.convertTypeReference(generator,
                            TypeUtils.createTypeReference(StdStrings.MAL, null, "UpdateHeader", false),
                            "updateHeader", "The Update header."));

                    for (TypeInfo ti : op.getRetTypes()) {
                        retTypes.add(ti);
                    }

                    List<CompositeField> opArgsU = new ArrayList<>();
                    opArgsU.add(stdHeaderArg);
                    opArgsU.addAll(StubUtils.concatenateArguments(generator.createOperationArguments(generator.getConfig(), file, retTypes)));
                    opArgsU.add(stdQosArg);

                    file.addMethodOpenStatement(true, false, false,
                            StdStrings.PUBLIC, false, true, null, op.getName() + "RegisterAckReceived",
                            StubUtils.concatenateArguments(stdHeaderArg, stdQosArg), null,
                            "Called by the MAL when a PubSub register acknowledgement is received from a broker for the operation " + op.getName(),
                            null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false,
                            StdStrings.PUBLIC, false, true, null, op.getName() + "RegisterErrorReceived",
                            stdErrorArgs, null,
                            "Called by the MAL when a PubSub register acknowledgement error is received from a broker for the operation " + op.getName(),
                            null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false,
                            StdStrings.PUBLIC, false, true, null, op.getName() + "DeregisterAckReceived",
                            StubUtils.concatenateArguments(stdHeaderArg, stdQosArg), null,
                            "Called by the MAL when a PubSub deregister acknowledgement is received from a broker for the operation " + op.getName(),
                            null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false,
                            StdStrings.PUBLIC, false, true, null, op.getName() + "NotifyReceived",
                            opArgsU, null, "Called by the MAL when a PubSub update is received from a broker for the operation " + op.getName(),
                            null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false,
                            StdStrings.PUBLIC, false, true, null, op.getName() + "NotifyErrorReceived",
                            stdErrorArgs, null, "Called by the MAL when a PubSub update error is received from a broker for the operation " + op.getName(),
                            null, null).addMethodCloseStatement();
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
                    "notify", "Notify", 2, stdNotifyBodyArgs, areaHelper, areaName, serviceInfoName, serviceName, throwsMALException,
                    summary, "Called by the MAL when a PubSub update is received from a broker.");
            createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.PUBSUB_OP,
                    "notify", "Notify", stdErrorBodyArgs, serviceInfoName, throwsMALException,
                    summary, "Called by the MAL when a PubSub update error is received from a broker.");
            createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.PUBSUB_OP,
                    "deregisterAck", "DeregisterAck", 1, stdNoBodyArgs, serviceInfoName, throwsMALException,
                    summary, "Called by the MAL when a PubSub deregister acknowledgement is received from a broker.");

            file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false,
                    true, null, "notifyReceivedFromOtherService", stdNotifyBodyArgs, throwsMALException,
                    "Called by the MAL when a PubSub update from another service is received from a broker.", null,
                    Arrays.asList(throwsMALException + " if an error is detected processing the message.")).addMethodCloseStatement();
        }

        file.addClassCloseStatement();

        file.flush();
    }

    protected void createServiceConsumerAdapterMessageMethod(ClassWriter file, InteractionPatternEnum optype,
            String opname, String subopPostname, int opTypeIndex, List<CompositeField> args,
            String serviceInfoName, String throwsMALException, ServiceSummary summary, String comment) throws IOException {
        MethodWriter method = file.addMethodOpenStatement(true, true, false, false, StdStrings.PUBLIC,
                false, true, null, opname + "Received", args, throwsMALException, comment, null,
                Arrays.asList(throwsMALException + " if an error is detected processing the message."));
        method.addLine("switch (" + generator.createMethodCall("msgHeader.getOperation().getValue()") + ") {", false);

        for (OperationSummary op : summary.getOperations()) {
            if (optype == op.getPattern()) {
                String ns = generator.convertToNamespace(serviceInfoName + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
                method.addMethodWithDependencyStatement("  case " + ns, ns, false);
                List<TypeInfo> opTypes = null;
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
                method.addLine("    " + op.getName() + subopPostname + "Received(msgHeader" + opArgs + ", qosProperties)");
                method.addLine("    break");
            }
        }
        method.addLine("  default:", false);
        method.addLine("    throw new " + throwsMALException + "(\"Consumer adapter was not expecting operation number \" + msgHeader.getOperation().getValue())");
        method.addLine("}", false);
        method.addMethodCloseStatement();
    }

    protected void createServiceConsumerAdapterNotifyMethod(ClassWriter file, InteractionPatternEnum optype,
            String opname, String subopPostname, int opTypeIndex, List<CompositeField> args,
            String areaHelper, String areaName, String serviceInfoName, String serviceName,
            String throwsMALException, ServiceSummary summary, String comment) throws IOException {
        MethodWriter method = file.addMethodOpenStatement(true, true, false, false, StdStrings.PUBLIC,
                false, true, null, opname + "Received", args, throwsMALException, comment, null,
                Arrays.asList(throwsMALException + " if an error is detected processing the message."));

        method.addLine("if ((" + areaHelper + "." + areaName.toUpperCase() + "_AREA_NUMBER.equals(msgHeader.getServiceArea()))"
                + " && "
                + "(" + serviceInfoName + "." + serviceName.toUpperCase() + "_SERVICE_NUMBER.equals(msgHeader.getService()))) {", false);
        method.addLine("  switch (" + generator.createMethodCall("msgHeader.getOperation().getValue()") + ") {", false);

        for (OperationSummary op : summary.getOperations()) {
            if (optype == op.getPattern()) {
                String ns = generator.convertToNamespace(serviceInfoName + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
                method.addMethodWithDependencyStatement("    case " + ns, ns, false);
                List<TypeInfo> opTypes = new LinkedList<>();
                opTypes.add(0, TypeUtils.convertTypeReference(generator,
                        TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, false)));
                opTypes.add(1, TypeUtils.convertTypeReference(generator,
                        TypeUtils.createTypeReference(StdStrings.MAL, null, "UpdateHeader", false)));

                for (TypeInfo ti : op.getRetTypes()) {
                    opTypes.add(TypeUtils.convertTypeReference(generator,
                            TypeUtils.createTypeReference(ti.getSourceType().getArea(), ti.getSourceType().getService(), ti.getSourceType().getName(), ti.getSourceType().isList())));
                }

                String opArgs = generator.createAdapterMethodsArgs(opTypes, "body", true, false);
                method.addLine("      " + op.getName() + subopPostname + "Received(msgHeader" + opArgs + ", qosProperties)");
                method.addLine("      break");
            }
        }
        method.addLine("    default:", false);
        method.addLine("      throw new " + throwsMALException + "(\"Consumer adapter was not expecting operation number \" + msgHeader.getOperation().getValue())");
        method.addLine("  }", false);
        method.addLine("}", false);
        method.addLine("else {", false);
        method.addLine("  notifyReceivedFromOtherService(msgHeader, body, qosProperties)");
        method.addLine("}", false);
        method.addMethodCloseStatement();
    }

    protected void createServiceConsumerAdapterErrorMethod(ClassWriter file, InteractionPatternEnum optype,
            String opname, String subopPostname, List<CompositeField> args, String serviceInfoName,
            String throwsMALException, ServiceSummary summary, String comment) throws IOException {
        MethodWriter method = file.addMethodOpenStatement(true, true, false, false, StdStrings.PUBLIC,
                false, true, null, opname + "ErrorReceived", args, throwsMALException, comment, null,
                Arrays.asList(throwsMALException + " if an error is detected processing the message."));
        method.addLine("switch (" + generator.createMethodCall("msgHeader.getOperation().getValue()") + ") {", false);

        for (OperationSummary op : summary.getOperations()) {
            if (optype == op.getPattern()) {
                String ns = generator.convertToNamespace(serviceInfoName + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
                method.addMethodWithDependencyStatement("  case " + ns, ns, false);
                method.addLine("    " + op.getName() + subopPostname + "ErrorReceived(msgHeader, body.getError(), qosProperties)");
                method.addLine("    break");
            }
        }
        method.addLine("  default:", false);
        method.addLine("    throw new " + throwsMALException + "(\"Consumer adapter was not expecting operation number \" + msgHeader.getOperation().getValue())");
        method.addLine("}", false);
        method.addMethodCloseStatement();
    }

    public void createServiceConsumerStub(File consumerFolder, AreaType area,
            ServiceType service, ServiceSummary summary) throws IOException {
        generator.getLog().info(" > Creating consumer stub: " + service.getName());

        String serviceName = service.getName();
        String className = serviceName + "Stub";

        ClassWriter file = generator.createClassFile(consumerFolder, className);

        file.addPackageStatement(area, service, CONSUMER_FOLDER);

        CompositeField serviceAdapterArg = generator.createCompositeElementsDetails(file, false, "adapter",
                TypeUtils.createTypeReference(area.getName(), service.getName() + "." + CONSUMER_FOLDER, serviceName + "Adapter", false),
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

        String throwsMALException = generator.createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String throwsInteractionException = generator.createElementType(file, StdStrings.MAL, null, null, StdStrings.MALINTERACTIONEXCEPTION);
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
        String helperType = generator.createElementType(file, area.getName(), service.getName(), null, serviceName + "Helper") + generator.getConfig().getNamingSeparator();
        String serviceInfoType = generator.createElementType(file, area.getName(), service.getName(), null, serviceName + JavaServiceInfo.SERVICE_INFO) + generator.getConfig().getNamingSeparator();
        CompositeField consumerType = generator.createCompositeElementsDetails(file, false, "return",
                TypeUtils.createTypeReference(StdStrings.MAL, CONSUMER_FOLDER, "MALConsumer", false),
                false, true, null);
        String consumerMethodCall = generator.createMethodCall("consumer.");
        CompositeField consumerTypeVar = generator.createCompositeElementsDetails(file, false, "consumer",
                TypeUtils.createTypeReference(StdStrings.MAL, CONSUMER_FOLDER, "MALConsumer", false),
                false, true, null);

        file.addClassOpenStatement(className, false, false, null,
                generator.createElementType(file, area.getName(), serviceName, CONSUMER_FOLDER, serviceName),
                "Consumer stub for " + serviceName + " service.");

        file.addClassVariable(false, true, StdStrings.PRIVATE, consumerTypeVar, false, (String) null);

        MethodWriter method = file.addConstructor(StdStrings.PUBLIC, className,
                generator.createCompositeElementsDetails(file, false, "consumer",
                        TypeUtils.createTypeReference(StdStrings.MAL, CONSUMER_FOLDER, "MALConsumer", false),
                        false, true, "consumer The MALConsumer to use in this stub."),
                false, null,
                "Wraps a MALconsumer connection with service specific methods that map from the high level service API to the generic MAL API.", null);
        method.addLine(generator.createMethodCall("this.consumer = consumer"));
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, false,
                generator.createReturnReference(consumerType), "getConsumer", null, null,
                "Returns the internal MAL consumer object used for sending of messages from this interface",
                "The MAL consumer object.", null);
        method.addLine(generator.createMethodCall("return consumer"));
        method.addMethodCloseStatement();

        if (supportsToValue) {
            method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, false, uriType, "getURI", null, null);
            method.addLine(generator.createMethodCall("return consumer.getUri()"));
            method.addMethodCloseStatement();
        }

        for (OperationSummary op : summary.getOperations()) {
            //String operationInstanceVar = generator.addressOf(helperType) + op.getName().toUpperCase() + "_OP";
            String operationInstanceVar = generator.addressOf(serviceInfoType) + op.getName().toUpperCase() + "_OP";
            switch (op.getPattern()) {
                case SEND_OP: {
                    List<CompositeField> opArgs = generator.createOperationArguments(generator.getConfig(), file, op.getArgTypes());
                    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true,
                            msgType, op.getName(), opArgs, throwsInteractionAndMALException,
                            op.getOriginalOp().getComment(), "the MAL message sent to initiate the interaction",
                            Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                    method.addMethodWithDependencyStatement("return " + consumerMethodCall
                            + generator.createConsumerPatternCall(op) + "(" + operationInstanceVar
                            + ", " + generator.createArgNameOrNull(op.getArgTypes()) + ")", helperType, true);
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
                            + "(" + operationInstanceVar + ", " + generator.createArgNameOrNull(op.getArgTypes()) + ")";
                    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, false, opRetType,
                            op.getName(), opArgs, throwsInteractionAndMALException, op.getOriginalOp().getComment(), opRetComment,
                            Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                    method.addMethodWithDependencyStatement(opGet, helperType, true);
                    createOperationReturn(file, method, op, opRetType);
                    method.addMethodCloseStatement();

                    if (supportsAsync) {
                        List<CompositeField> asyncOpArgs = StubUtils.concatenateArguments(opArgs, serviceAdapterArg);
                        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, msgType,
                                "async" + StubUtils.preCap(op.getName()), asyncOpArgs, throwsInteractionAndMALException,
                                "Asynchronous version of method " + op.getName(), "the MAL message sent to initiate the interaction",
                                Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                        method.addLine("return " + consumerMethodCall + "async" + StubUtils.preCap(generator.createConsumerPatternCall(op)) + "(" + operationInstanceVar + ", adapter, " + generator.createArgNameOrNull(op.getArgTypes()) + ")");
                        method.addMethodCloseStatement();
                    }

                    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, null,
                            "continue" + StubUtils.preCap(op.getName()), continueOpArgs, throwsInteractionAndMALException,
                            "Continues a previously started interaction", null,
                            Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                    method.addLine(consumerMethodCall + "continueInteraction(" + operationInstanceVar + ", lastInteractionStage, initiationTimestamp, transactionId, adapter)");
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
                    String opGet = rv + consumerMethodCall + generator.createConsumerPatternCall(op) + "(" + operationInstanceVar + ", adapter, " + generator.createArgNameOrNull(op.getArgTypes()) + ")";
                    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                            false, false, opRetType, op.getName(), opArgs,
                            throwsInteractionAndMALException, op.getOriginalOp().getComment(), opRetComment,
                            Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.",
                                    throwsMALException + " if there is an implementation exception"));
                    method.addMethodWithDependencyStatement(opGet, helperType, true);
                    createOperationReturn(file, method, op, opRetType);
                    method.addMethodCloseStatement();

                    if (supportsAsync) {
                        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, msgType,
                                "async" + StubUtils.preCap(op.getName()), opArgs, throwsInteractionAndMALException,
                                "Asynchronous version of method " + op.getName(), "the MAL message sent to initiate the interaction",
                                Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                        method.addLine("return " + consumerMethodCall + "async" + StubUtils.preCap(generator.createConsumerPatternCall(op)) + "(" + operationInstanceVar + ", adapter, " + generator.createArgNameOrNull(op.getArgTypes()) + ")");
                        method.addMethodCloseStatement();
                    }

                    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                            false, true, null, "continue" + StubUtils.preCap(op.getName()), continueOpArgs, throwsInteractionAndMALException, "Continues a previously started interaction", null,
                            Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                    method.addLine(consumerMethodCall + "continueInteraction(" + operationInstanceVar + ", lastInteractionStage, initiationTimestamp, transactionId, adapter)");
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

                    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                            false, true, null, op.getName() + "Register",
                            StubUtils.concatenateArguments(subStr, serviceAdapterArg), throwsInteractionAndMALException, "Register method for the " + op.getName() + " PubSub interaction", null,
                            Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                    method.addMethodWithDependencyStatement(consumerMethodCall + generator.getRegisterMethodName() + "(" + operationInstanceVar + ", subscription, adapter)", helperType, true);
                    method.addMethodCloseStatement();

                    if (supportsAsync) {
                        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                                false, true, msgType,
                                "async" + StubUtils.preCap(op.getName()) + "Register",
                                StubUtils.concatenateArguments(subStr, serviceAdapterArg), throwsInteractionAndMALException,
                                "Asynchronous version of method " + op.getName() + "Register", "the MAL message sent to initiate the interaction",
                                Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                        method.addLine("return " + consumerMethodCall + "async" + StubUtils.preCap(generator.getRegisterMethodName()) + "(" + operationInstanceVar + ", subscription, adapter)");
                        method.addMethodCloseStatement();
                    }

                    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                            false, true, null, op.getName() + "Deregister",
                            Arrays.asList(idStr), throwsInteractionAndMALException,
                            "Deregister method for the " + op.getName() + " PubSub interaction", null,
                            Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                    method.addLine(consumerMethodCall + generator.getDeregisterMethodName() + "(" + operationInstanceVar + ", identifierList)");
                    method.addMethodCloseStatement();

                    if (supportsAsync) {
                        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                                false, true, msgType, "async" + StubUtils.preCap(op.getName()) + "Deregister",
                                StubUtils.concatenateArguments(idStr, serviceAdapterArg),
                                throwsInteractionAndMALException, "Asynchronous version of method " + op.getName() + "Deregister",
                                "the MAL message sent to initiate the interaction",
                                Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                        method.addLine("return " + consumerMethodCall + "async" + StubUtils.preCap(generator.getDeregisterMethodName()) + "(" + operationInstanceVar + ", identifierList, adapter)");
                        method.addMethodCloseStatement();
                    }
                    break;
                }
            }
        }

        file.addClassCloseStatement();
        file.flush();
    }

    private void createOperationReturn(LanguageWriter file, MethodWriter method, OperationSummary op, CompositeField opRetType) throws IOException {
        List<TypeInfo> targetTypes = op.getRetTypes();

        if ((InteractionPatternEnum.INVOKE_OP == op.getPattern()) || (InteractionPatternEnum.PROGRESS_OP == op.getPattern())) {
            targetTypes = op.getAckTypes();
        }

        if ((null != targetTypes) && (!targetTypes.isEmpty())) {
            if (targetTypes.size() == 1) {
                method.addLine("return " + generator.createOperationArgReturn(file, method, targetTypes.get(0), "body", 0));
            } else {
                StringBuilder buf = new StringBuilder();

                for (int i = 0; i < targetTypes.size(); i++) {
                    TypeInfo ti = targetTypes.get(i);
                    if (i > 0) {
                        buf.append(", ");
                    }
                    buf.append(generator.createOperationArgReturn(file, method, ti, "body", i));
                }

                method.addLine("return new " + opRetType.getTypeName() + "(" + buf.toString() + ")");
            }
        }
    }
}
