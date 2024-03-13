/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
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
package esa.mo.tools.stubgen;

import static esa.mo.tools.stubgen.GeneratorLangs.PROVIDER_FOLDER;
import esa.mo.tools.stubgen.specification.CompositeField;
import esa.mo.tools.stubgen.specification.OperationSummary;
import esa.mo.tools.stubgen.specification.ServiceSummary;
import esa.mo.tools.stubgen.specification.StdStrings;
import esa.mo.tools.stubgen.specification.TypeUtils;
import esa.mo.tools.stubgen.writers.ClassWriter;
import esa.mo.tools.stubgen.writers.InterfaceWriter;
import esa.mo.tools.stubgen.writers.MethodWriter;
import esa.mo.xsd.AreaType;
import esa.mo.xsd.ServiceType;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Generates stubs and skeletons for CCSDS MO Service specifications for the
 * Java Google Web Toolkit API. Experimental.
 */
public class GeneratorGwt extends GeneratorJava {

    /**
     * Constructor.
     *
     * @param logger The logger to use.
     */
    public GeneratorGwt(org.apache.maven.plugin.logging.Log logger) {
        super(logger);
    }

    @Override
    public void init(String destinationFolderName,
            boolean generateStructures,
            boolean generateCOM,
            Map<String, String> packageBindings,
            Map<String, String> extraProperties) throws IOException {
        super.init(destinationFolderName, generateStructures, generateCOM, packageBindings, extraProperties);

        addAttributeType(StdStrings.MAL, StdStrings.BOOLEAN, false, "Boolean", "Boolean.FALSE");
    }

    @Override
    public String getShortName() {
        return "GWT";
    }

    @Override
    public String getDescription() {
        return "Experimental: Generates a GWT compatible Java language mapping.";
    }

    @Override
    protected void createServiceConsumerInterface(File consumerFolder, AreaType area,
            ServiceType service, ServiceSummary summary) throws IOException {
        logger.info(" > Creating consumer interface: " + service.getName());

        InterfaceWriter file = createInterfaceFile(consumerFolder, service.getName() + "GWT");
        String serviceName = service.getName();
        file.addPackageStatement(area, service, CONSUMER_FOLDER);

        file.addStatement("@com.google.gwt.user.client.rpc.RemoteServiceRelativePath(\"" + service.getName() + "GWT\")");
        file.addInterfaceOpenStatement(serviceName + "GWT", "com.google.gwt.user.client.rpc.RemoteService", null);

        String throwsMALException = createElementType(StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        CompositeField msgType = createCompositeElementsDetails(file, false,
                "return", TypeUtils.createTypeReference(StdStrings.MAL,
                        TRANSPORT_FOLDER, StdStrings.MALMESSAGE, false),
                false, true, null);

        for (OperationSummary op : summary.getOperations()) {
            List<CompositeField> opArgs = createOperationArguments(getConfig(), file, op.getArgTypes());
            switch (op.getPattern()) {
                case SEND_OP: {
                    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, msgType,
                            op.getName(), opArgs, throwsMALException, null, null, null);
                    break;
                }
                case SUBMIT_OP: {
                    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, null,
                            op.getName(), opArgs, throwsMALException, null, null, null);
                    break;
                }
                case REQUEST_OP: {
                    CompositeField opRetType = createOperationReturnType(file, area, service, op);
                    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, opRetType,
                            op.getName(), opArgs, throwsMALException, null, null, null);
                    break;
                }
                case INVOKE_OP: {
                    break;
                }
                case PROGRESS_OP: {
                    break;
                }
                case PUBSUB_OP: {
                    break;
                }
            }
        }

        file.addInterfaceCloseStatement();

        file.flush();
    }

    protected void createServiceConsumerStub2(File consumerFolder, AreaType area,
            ServiceType service, ServiceSummary summary) throws IOException {
        logger.info(" > Creating consumer stub: " + service.getName());

        String serviceName = service.getName();

        InterfaceWriter file = createInterfaceFile(consumerFolder, serviceName + "GWTAsync");
        file.addPackageStatement(area, service, CONSUMER_FOLDER);
        file.addInterfaceOpenStatement(serviceName + "GWTAsync", null, null);

        for (OperationSummary op : summary.getOperations()) {
            List<CompositeField> opArgs = createOperationArguments(getConfig(), file, op.getArgTypes());
            switch (op.getPattern()) {
                case SEND_OP: {
                    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC,
                            null, op.getName(), opArgs, null, null, null, null);
                    break;
                }
                case SUBMIT_OP: {
                    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC,
                            null, op.getName(), opArgs, null, null, null, null);
                    break;
                }
                case REQUEST_OP: {
//          CompositeField opRetType = createOperationReturnType(file, area, service, op);
//          String asyncOpArgs = StubUtils.concatenateArguments(opArgs, "com.google.gwt.user.client.rpc.AsyncCallback<" + opRetType + "> _callback");
//          file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, StdStrings.VOID, op.getName(), asyncOpArgs, null, null, null, null);
                    break;
                }
                case INVOKE_OP: {
                    break;
                }
                case PROGRESS_OP: {
                    break;
                }
                case PUBSUB_OP: {
                    break;
                }
            }
        }

        file.addInterfaceCloseStatement();

        file.flush();
    }

    @Override
    protected void createServiceProviderHandler(File providerFolder, AreaType area,
            ServiceType service, ServiceSummary summary) throws IOException {
        logger.info(" > Creating provider handler interface: " + service.getName());

        String handlerName = service.getName() + "Handler";
        InterfaceWriter file = createInterfaceFile(providerFolder, handlerName);
        file.addPackageStatement(area, service, PROVIDER_FOLDER);
        file.addInterfaceOpenStatement(handlerName, null, null);

        CompositeField intHandlerStr = createCompositeElementsDetails(file, false,
                "interaction", TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, StdStrings.MALINTERACTION, false),
                false, true, "interaction The MAL object representing the interaction in the provider.");

        String throwsMALException = createElementType(StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        for (OperationSummary op : summary.getOperations()) {
            List<CompositeField> opArgs = createOperationArguments(getConfig(), file, op.getArgTypes());
            switch (op.getPattern()) {
                case SEND_OP: {
                    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, null,
                            op.getName(), StubUtils.concatenateArguments(opArgs, intHandlerStr),
                            throwsMALException, null, null, null);
                    break;
                }
                case SUBMIT_OP: {
                    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, null,
                            op.getName(), StubUtils.concatenateArguments(opArgs, intHandlerStr),
                            throwsMALException, null, null, null);
                    break;
                }
                case REQUEST_OP: {
                    CompositeField opRetType = createOperationReturnType(file, area, service, op);
                    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, opRetType, op.getName(),
                            StubUtils.concatenateArguments(opArgs, intHandlerStr), throwsMALException, null, null, null);
                    break;
                }
                case INVOKE_OP: {
                    CompositeField serviceHandlerStr = createCompositeElementsDetails(
                            file, false, "interaction",
                            TypeUtils.createTypeReference(area.getName(), service.getName()
                                    + "." + PROVIDER_FOLDER, StubUtils.preCap(op.getName())
                                    + "Interaction", false), false, true,
                            "interaction The MAL object representing the interaction in the provider.");
                    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, null,
                            op.getName(), StubUtils.concatenateArguments(opArgs, serviceHandlerStr),
                            throwsMALException, null, null, null);
                    break;
                }
                case PROGRESS_OP: {
                    CompositeField serviceHandlerStr = createCompositeElementsDetails(
                            file, false, "interaction",
                            TypeUtils.createTypeReference(area.getName(), service.getName()
                                    + "." + PROVIDER_FOLDER, StubUtils.preCap(op.getName())
                                    + "Interaction", false), false, true,
                            "interaction The MAL object representing the interaction in the provider.");
                    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, null,
                            op.getName(), StubUtils.concatenateArguments(opArgs, serviceHandlerStr),
                            throwsMALException, null, null, null);
                    break;
                }
                case PUBSUB_OP: {
                    break;
                }
            }
        }

        file.addInterfaceCloseStatement();

        file.flush();
    }

    @Override
    protected void createServiceProviderSkeleton(File providerFolder,
            AreaType area, ServiceType service, ServiceSummary summary,
            Map<String, RequiredPublisher> requiredPublishers) throws IOException {
    }

    @Override
    protected void createServiceProviderInteractions(File providerFolder,
            AreaType area, ServiceType service, ServiceSummary summary) throws IOException {
    }

    @Override
    protected void createServiceProviderSkeletonHandler(File providerFolder,
            AreaType area, ServiceType service, ServiceSummary summary,
            boolean isDelegate) throws IOException {
        String className = service.getName();
        String comment;
        if (isDelegate) {
            className += "DelegationSkeletonGWT";
            comment = "Provider Delegation skeleton for " + className + " service.";
        } else {
            className += "InheritanceSkeletonGWT";
            comment = "Provider Inheritance skeleton for " + className + " service.";
        }

        ClassWriter file = createClassFile(providerFolder, className);

        file.addPackageStatement(area, service, PROVIDER_FOLDER);

        String throwsMALException = createElementType(StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);

        String implementsList = createElementType(area.getName(),
                service.getName(), CONSUMER_FOLDER, service.getName() + "GWT");
        if (!isDelegate) {
            implementsList += ", " + createElementType(area.getName(),
                    service.getName(), PROVIDER_FOLDER, service.getName() + "Handler");
        }

        file.addClassOpenStatement(className, false, !isDelegate,
                "com.google.gwt.user.server.rpc.RemoteServiceServlet", implementsList, comment);

        if (isDelegate) {
            CompositeField handlerName = createCompositeElementsDetails(file, false, "delegate",
                    TypeUtils.createTypeReference(area.getName(), service.getName()
                            + "." + PROVIDER_FOLDER, service.getName() + "Handler", false),
                    false, true, null);
            file.addClassVariable(false, false, StdStrings.PRIVATE, handlerName, false, (String) null);
        }

        if (isDelegate) {
            MethodWriter method = file.addConstructor(StdStrings.PUBLIC, className,
                    createCompositeElementsDetails(file, false, "delegate",
                            TypeUtils.createTypeReference(area.getName(),
                                    service.getName().toLowerCase() + "." + PROVIDER_FOLDER, service.getName() + "Handler", false),
                            false, false, null), false, null, null, null);
            method.addLine(createMethodCall("this.delegate = delegate"));
            method.addMethodCloseStatement();
        } else {
//      CompositeField skeletonName = createCompositeElementsDetails(file, false, "skeleton", TypeUtils.createTypeReference(area.getName(), service.getName() + "." + PROVIDER_FOLDER, service.getName() + "Skeleton", false), false, true, "Not used in the inheritance pattern (the skeleton is 'this'");
//      MethodWriter method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, null, "setSkeleton", Arrays.asList(skeletonName), null);
//      method.addMethodStatement("// Not used in the inheritance pattern (the skeleton is 'this')");
//      method.addMethodCloseStatement();
        }

        // for each IP type add handler code
        String delegateCall = "";
        if (isDelegate) {
            delegateCall = createMethodCall("delegate.");
        }

        for (OperationSummary op : summary.getOperations()) {
            switch (op.getPattern()) {
                case SEND_OP: {
                    MethodWriter method = file.addMethodOpenStatement(false, false,
                            StdStrings.PUBLIC, false, true, null, op.getName(),
                            createOperationArguments(getConfig(), file,
                                    op.getArgTypes()), throwsMALException);

                    String opArgs = createArgNameOrNull(op.getArgTypes());
                    method.addLine(
                            createMethodCall(delegateCall + op.getName() + "(" + opArgs + ", null)"));

                    method.addMethodCloseStatement();
                    break;
                }
                case SUBMIT_OP: {
                    MethodWriter method = file.addMethodOpenStatement(false,
                            false, StdStrings.PUBLIC, false, true, null, op.getName(),
                            createOperationArguments(getConfig(), file, op.getArgTypes()), throwsMALException);

                    String opArgs = createArgNameOrNull(op.getArgTypes());
                    method.addLine(
                            createMethodCall(delegateCall + op.getName() + "(" + opArgs + ", null)"));

                    method.addMethodCloseStatement();
                    break;
                }
                case REQUEST_OP: {
                    CompositeField opRetType = createOperationReturnType(file, area, service, op);
                    MethodWriter method = file.addMethodOpenStatement(false, false,
                            StdStrings.PUBLIC, false, true, opRetType, op.getName(),
                            createOperationArguments(getConfig(), file, op.getArgTypes()), throwsMALException);

                    String opArgs = createArgNameOrNull(op.getArgTypes());
                    method.addLine(createMethodCall("return " + delegateCall + op.getName() + "(" + opArgs + ", null)"));

                    method.addMethodCloseStatement();
                    break;
                }
                case INVOKE_OP: {
                    break;
                }
                case PROGRESS_OP: {
                    break;
                }
                case PUBSUB_OP: {
                    break;
                }
            }
        }

        file.addClassCloseStatement();

        file.flush();
    }
}
