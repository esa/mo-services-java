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

import esa.mo.tools.stubgen.specification.AttributeTypeDetails;
import esa.mo.tools.stubgen.specification.CompositeField;
import esa.mo.tools.stubgen.specification.InteractionPatternEnum;
import esa.mo.tools.stubgen.specification.MultiReturnType;
import esa.mo.tools.stubgen.specification.OperationSummary;
import esa.mo.tools.stubgen.specification.ServiceSummary;
import esa.mo.tools.stubgen.specification.StdStrings;
import esa.mo.tools.stubgen.specification.TypeInfo;
import esa.mo.tools.stubgen.specification.TypeInformation;
import esa.mo.tools.stubgen.specification.TypeUtils;
import esa.mo.tools.stubgen.writers.ClassWriter;
import esa.mo.tools.stubgen.writers.InterfaceWriter;
import esa.mo.tools.stubgen.writers.LanguageWriter;
import esa.mo.tools.stubgen.writers.MethodWriter;
import esa.mo.tools.stubgen.writers.TargetWriter;
import esa.mo.xsd.*;
import esa.mo.xsd.EnumerationType.Item;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import org.apache.maven.plugin.logging.Log;

/**
 * Main generator class for programming languages. Iterates over the
 * specification model drilling down into the parts of the model and inspecting
 * them. Generates stubs and skeletons appropriate to an object orientated
 * language.
 */
public abstract class GeneratorLangs extends GeneratorBase {

    /**
     * The bit shift value for the area part of a type short form.
     */
    public static final int AREA_BIT_SHIFT = 48;
    /**
     * The bit shift value for the service part of a type short form.
     */
    public static final int SERVICE_BIT_SHIFT = 32;
    /**
     * The bit shift value for the version part of a type short form.
     */
    public static final int VERSION_BIT_SHIFT = 24;
    /**
     * The folder that consumer interfaces are created.
     */
    public static final String CONSUMER_FOLDER = "consumer";
    /**
     * The folder that provider interfaces are created.
     */
    public static final String PROVIDER_FOLDER = "provider";
    /**
     * The folder that transport interfaces are held.
     */
    public static final String TRANSPORT_FOLDER = "transport";
    private final Map<TypeKey, ModelObjectType> comObjectMap = new HashMap<>();
    private final Map<String, MultiReturnType> multiReturnTypeMap = new HashMap<>();
    private final Map<String, String> reservedWordsMap = new HashMap<>();
    private final Map<String, RequiredPublisher> requiredPublishers = new HashMap<>();
    private boolean supportsToString;
    private boolean supportsEquals;
    private boolean supportsToValue;
    private boolean supportsAsync;
    private boolean requiresDefaultConstructors;
    private boolean generateStructures;
    private boolean supportPolymorphic;

    /**
     * Constructor.
     *
     * @param logger The logger to use.
     * @param supportsToString True if should generate to string methods in
     * types.
     * @param supportsEquals True if should generate equals methods in types.
     * @param supportsToValue True if should generate generic get value methods
     * in types.
     * @param supportsAsync True if should generate async consumer methods.
     * @param requiresDefaultConstructors True if type require a default
     * constructor.
     * @param config The generator configuration.
     */
    public GeneratorLangs(Log logger, boolean supportsToString, boolean supportsEquals, 
            boolean supportsToValue, boolean supportsAsync, 
            boolean requiresDefaultConstructors, GeneratorConfiguration config) {
        super(logger, config);

        this.supportsToString = supportsToString;
        this.supportsEquals = supportsEquals;
        this.supportsToValue = supportsToValue;
        this.supportsAsync = supportsAsync;
        this.requiresDefaultConstructors = requiresDefaultConstructors;
        this.supportPolymorphic = false;
    }

    @Override
    public void init(String destinationFolderName,
            boolean generateStructures,
            boolean generateCOM,
            Map<String, String> packageBindings,
            Map<String, String> extraProperties) throws IOException {
        super.init(destinationFolderName, generateStructures, generateCOM, packageBindings, extraProperties);

        this.generateStructures = generateStructures;
    }

    @Override
    public void postinit(String destinationFolderName,
            boolean generateStructures,
            boolean generateCOM,
            Map<String, String> packageBindings,
            Map<String, String> extraProperties) throws IOException {
        super.postinit(destinationFolderName, generateStructures, generateCOM, packageBindings, extraProperties);

        addAttributeType(StdStrings.XML, "hexBinary", getAttributeDetails(StdStrings.MAL, StdStrings.BLOB));
        addAttributeType(StdStrings.XML, "boolean", getAttributeDetails(StdStrings.MAL, StdStrings.BOOLEAN));
        addAttributeType(StdStrings.XML, "double", getAttributeDetails(StdStrings.MAL, StdStrings.DOUBLE));
        addAttributeType(StdStrings.XML, "duration", getAttributeDetails(StdStrings.MAL, StdStrings.DURATION));
        addAttributeType(StdStrings.XML, "float", getAttributeDetails(StdStrings.MAL, StdStrings.FLOAT));
        addAttributeType(StdStrings.XML, "int", getAttributeDetails(StdStrings.MAL, StdStrings.INTEGER));
        addAttributeType(StdStrings.XML, "NCName", getAttributeDetails(StdStrings.MAL, StdStrings.IDENTIFIER));
        addAttributeType(StdStrings.XML, "long", getAttributeDetails(StdStrings.MAL, StdStrings.LONG));
        addAttributeType(StdStrings.XML, "byte", getAttributeDetails(StdStrings.MAL, StdStrings.OCTET));
        addAttributeType(StdStrings.XML, "short", getAttributeDetails(StdStrings.MAL, StdStrings.SHORT));
        addAttributeType(StdStrings.XML, "unsignedInt", getAttributeDetails(StdStrings.MAL, StdStrings.UINTEGER));
        addAttributeType(StdStrings.XML, "unsignedLong", getAttributeDetails(StdStrings.MAL, StdStrings.ULONG));
        addAttributeType(StdStrings.XML, "unsignedByte", getAttributeDetails(StdStrings.MAL, StdStrings.UOCTET));
        addAttributeType(StdStrings.XML, "unsignedShort", getAttributeDetails(StdStrings.MAL, StdStrings.USHORT));
        addAttributeType(StdStrings.XML, "string", getAttributeDetails(StdStrings.MAL, StdStrings.STRING));
        addAttributeType(StdStrings.XML, "dateTime", getAttributeDetails(StdStrings.MAL, StdStrings.TIME));
        addAttributeType(StdStrings.XML, "dateTime", getAttributeDetails(StdStrings.MAL, StdStrings.FINETIME));
        addAttributeType(StdStrings.XML, "anyURI", getAttributeDetails(StdStrings.MAL, StdStrings.URI));

        addAttributeType(StdStrings.XML, "Element", new AttributeTypeDetails(this, "Element", true, "Object", ""));
    }

    @Override
    public void preProcess(SpecificationType spec) throws IOException, JAXBException {
        super.preProcess(spec);

        // load in COM object/event definitions
        for (AreaType area : spec.getArea()) {
            for (ServiceType service : area.getService()) {
                if (service instanceof ExtendedServiceType) {
                    ExtendedServiceType eService = (ExtendedServiceType) service;

                    SupportedFeatures features = eService.getFeatures();

                    if (null != features) {
                        if (null != features.getObjects()) {
                            for (ModelObjectType obj : features.getObjects().getObject()) {
                                TypeKey key = new TypeKey(area.getName(), service.getName(), String.valueOf(obj.getNumber()));
                                comObjectMap.put(key, obj);
                            }
                        }

                        if (null != features.getEvents()) {
                            for (ModelObjectType obj : features.getEvents().getEvent()) {
                                TypeKey key = new TypeKey(area.getName(), service.getName(), String.valueOf(obj.getNumber()));
                                comObjectMap.put(key, obj);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void compile(String destinationFolderName, SpecificationType spec, JAXBElement rootNode) throws IOException, JAXBException {
        for (AreaType area : spec.getArea()) {
            processArea(destinationFolderName, area, requiredPublishers);
        }

        for (Map.Entry<String, MultiReturnType> entry : multiReturnTypeMap.entrySet()) {
            String string = entry.getKey();
            MultiReturnType rt = entry.getValue();

            createMultiReturnType(destinationFolderName, string, rt);
        }
    }

    @Override
    public void close(String destinationFolderName) throws IOException {
        // create any extra classes
        for (Map.Entry<String, RequiredPublisher> ele : requiredPublishers.entrySet()) {
            String string = ele.getKey();

            if (!string.contains(".com.com.provider.") || generateCOM()) {
                createRequiredPublisher(destinationFolderName, string, ele.getValue());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();

        comObjectMap.clear();
        multiReturnTypeMap.clear();
        reservedWordsMap.clear();
        requiredPublishers.clear();
    }

    /**
     * Does the generator support a string generator.
     *
     * @return True if it supports ToString.
     */
    public boolean isSupportsToString() {
        return supportsToString;
    }

    /**
     * Sets the generator support a string generator value.
     *
     * @param supportsToString the supportsToString to set
     */
    public void setSupportsToString(boolean supportsToString) {
        this.supportsToString = supportsToString;
    }

    /**
     * Does the generator support an equals method on structures.
     *
     * @return the supportsEquals
     */
    public boolean isSupportsEquals() {
        return supportsEquals;
    }

    /**
     * Sets the generator support equals value.
     *
     * @param supportsEquals the supportsEquals to set
     */
    public void setSupportsEquals(boolean supportsEquals) {
        this.supportsEquals = supportsEquals;
    }

    /**
     * Does the generator support a getMALValue method on structures.
     *
     * @return the supportsToValue
     */
    public boolean isSupportsToValue() {
        return supportsToValue;
    }

    /**
     * Sets the generator support value generator value.
     *
     * @param supportsToValue the supportsToValue to set
     */
    public void setSupportsToValue(boolean supportsToValue) {
        this.supportsToValue = supportsToValue;
    }

    /**
     * Does the generator need to generate async operation methods.
     *
     * @return the supportsAsync
     */
    public boolean isSupportsAsync() {
        return supportsAsync;
    }

    /**
     * Sets the generator async support value.
     *
     * @param supportsAsync the supportsAsync to set
     */
    public void setSupportsAsync(boolean supportsAsync) {
        this.supportsAsync = supportsAsync;
    }

    /**
     * Does the generator need to generate default constructors on structures
     * and enumerations.
     *
     * @return the requiresDefaultConstructors
     */
    public boolean isRequiresDefaultConstructors() {
        return requiresDefaultConstructors;
    }

    /**
     * Sets the generator default constructor value.
     *
     * @param requiresDefaultConstructors the requiresDefaultConstructors to set
     */
    public void setRequiresDefaultConstructors(boolean requiresDefaultConstructors) {
        this.requiresDefaultConstructors = requiresDefaultConstructors;
    }

    /**
     * Does the generator need to generate structures.
     *
     * @return the generateStructures
     */
    public boolean isGenerateStructures() {
        return generateStructures;
    }

    /**
     * Sets the generate structures value.
     *
     * @param generateStructures the generateStructures to set
     */
    public void setGenerateStructures(boolean generateStructures) {
        this.generateStructures = generateStructures;
    }

    /**
     * Does the generator need to support fully polymorphic types.
     *
     * @return the supportPolymorphic
     */
    public boolean isFullyPolymorphic() {
        return supportPolymorphic;
    }

    /**
     * Sets the support polymorphic value.
     *
     * @param supportPolymorphic the supportPolymorphic to set
     */
    public void setSupportFullyPolymorphicTypes(boolean supportPolymorphic) {
        this.supportPolymorphic = supportPolymorphic;
    }

    /**
     * To be used by derived generators to add an entry to the reserved word
     * map.
     *
     * @param word The word to look for.
     * @param replacement The replacement to use.
     */
    protected void addReservedWord(String word, String replacement) {
        reservedWordsMap.put(word, replacement);
    }

    protected void processArea(String destinationFolderName, AreaType area, Map<String, RequiredPublisher> requiredPublishers) throws IOException {
        if ((!area.getName().equalsIgnoreCase(StdStrings.COM)) || (generateCOM())) {
            getLog().info("Processing area: " + area.getName());

            // create folder
            File destinationFolder = StubUtils.createFolder(new File(destinationFolderName), getConfig().getAreaPackage(area.getName()).replace('.', '/'));
            final File areaFolder = StubUtils.createFolder(destinationFolder, area.getName());

            // create a comment for the area folder if supported
            createAreaFolderComment(areaFolder, area);

            // create area helper
            createAreaHelperClass(areaFolder, area);

            // if area level types exist
            if (generateStructures && (null != area.getDataTypes()) && !area.getDataTypes().getFundamentalOrAttributeOrComposite().isEmpty()) {
                // create area structure folder
                File structureFolder = StubUtils.createFolder(areaFolder, getConfig().getStructureFolder());
                // create a comment for the structure folder if supported
                createAreaStructureFolderComment(structureFolder, area);

                ConcurrentLinkedQueue<Exception> errors = new ConcurrentLinkedQueue<>();
                
                // create area level data types
                area.getDataTypes().getFundamentalOrAttributeOrComposite().parallelStream().forEach(oType -> {
                    try {
                        if (oType instanceof FundamentalType) {
                            createFundamentalClass(structureFolder, area, null, (FundamentalType) oType);
                        } else if (oType instanceof AttributeType) {
                            String aName = ((AttributeType) oType).getName();
                            createListClass(structureFolder, area, null, aName, false, ((AttributeType) oType).getShortFormPart());
                            CompositeField fld = createCompositeElementsDetails(null, false, "fld", TypeUtils.createTypeReference(area.getName(), null, aName, false), true, true, "cmt");
                            createFactoryClass(structureFolder, area, null, aName, fld, true, false);
                        } else if (oType instanceof CompositeType) {
                            createCompositeClass(structureFolder, area, null, (CompositeType) oType);
                        } else if (oType instanceof EnumerationType) {
                            createEnumerationClass(structureFolder, area, null, (EnumerationType) oType);
                        } else {
                            throw new IllegalArgumentException("Unexpected area (" + area.getName() + ") level datatype of " + oType.getClass().getName());
                        }
                    } catch (Exception ex) {
                        errors.add(ex);
                    }
                });
                
                if(!errors.isEmpty()) {
                    throw (IOException) errors.poll();
                }
            }
            // create services
            for (ServiceType service : area.getService()) {
                processService(areaFolder, area, service, requiredPublishers);
            }
        }
    }

    protected void processService(File areaFolder, AreaType area, ServiceType service, Map<String, RequiredPublisher> requiredPublishers) throws IOException {
        // create service folders
        File serviceFolder = StubUtils.createFolder(areaFolder, service.getName());
        // load service operation details
        ServiceSummary summary = createOperationElementList(service);
        // create a comment for the service folder if supported
        createServiceFolderComment(serviceFolder, area, service);
        // create service helper
        createServiceHelperClass(serviceFolder, area, service, summary);

        // create consuer classes
        createServiceConsumerClasses(serviceFolder, area, service, summary);
        // create provider classes
        createServiceProviderClasses(serviceFolder, area, service, summary, requiredPublishers);

        // if service level types exist
        if (generateStructures && (null != service.getDataTypes()) && !service.getDataTypes().getCompositeOrEnumeration().isEmpty()) {
            // create structure folder
            File structureFolder = StubUtils.createFolder(serviceFolder, getConfig().getStructureFolder());
            // create a comment for the structure folder if supported
            createServiceStructureFolderComment(structureFolder, area, service);

            for (Object oType : service.getDataTypes().getCompositeOrEnumeration()) {
                if (oType instanceof EnumerationType) {
                    createEnumerationClass(structureFolder, area, service, (EnumerationType) oType);
                } else if (oType instanceof CompositeType) {
                    createCompositeClass(structureFolder, area, service, (CompositeType) oType);
                } else {
                    throw new IllegalArgumentException("Unexpected service (" + area.getName() + ":" + service.getName() + ") level datatype of " + oType.getClass().getName());
                }
            }
        }
    }

    protected void createServiceConsumerClasses(File serviceFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException {
        getLog().info("Creating consumer classes: " + service.getName());
        File consumerFolder = StubUtils.createFolder(serviceFolder, CONSUMER_FOLDER);
        // create a comment for the consumer folder if supported
        createServiceConsumerFolderComment(consumerFolder, area, service);
        createServiceConsumerInterface(consumerFolder, area, service, summary);
        createServiceConsumerAdapter(consumerFolder, area, service, summary);
        createServiceConsumerStub(consumerFolder, area, service, summary);
    }

    protected void createServiceProviderClasses(File serviceFolder, AreaType area, ServiceType service, ServiceSummary summary, Map<String, RequiredPublisher> requiredPublishers) throws IOException {
        getLog().info("Creating provider classes: " + service.getName());
        File providerFolder = StubUtils.createFolder(serviceFolder, PROVIDER_FOLDER);
        // create a comment for the provider folder if supported
        createServiceProviderFolderComment(providerFolder, area, service);
        createServiceProviderHandler(providerFolder, area, service, summary);
        createServiceProviderSkeleton(providerFolder, area, service, summary, requiredPublishers);
        createServiceProviderDelegation(providerFolder, area, service, summary);
        createServiceProviderInheritance(providerFolder, area, service, summary);
        createServiceProviderInteractions(providerFolder, area, service, summary);
    }

    protected void createServiceProviderDelegation(File providerFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException {
        getLog().info("Creating provider delegate class: " + service.getName());
        createServiceProviderSkeletonHandler(providerFolder, area, service, summary, true);
    }

    protected void createServiceProviderInheritance(File providerFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException {
        getLog().info("Creating provider inheritance class: " + service.getName());
        createServiceProviderSkeletonHandler(providerFolder, area, service, summary, false);
    }

    protected void createServiceProviderInteractions(File providerFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException {
        for (OperationSummary op : summary.getOperations()) {
            if (op.getPattern() == InteractionPatternEnum.INVOKE_OP) {
                createServiceProviderInvokeInteractionClass(providerFolder, area, service, op);
            } else if (op.getPattern() == InteractionPatternEnum.PROGRESS_OP) {
                createServiceProviderProgressInteractionClass(providerFolder, area, service, op);
            }
        }
    }

    protected void createServiceConsumerAdapter(File consumerFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException {
        getLog().info("Creating consumer adapter: " + service.getName());

        String areaName = area.getName();
        String serviceName = service.getName();
        String className = serviceName + "Adapter";

        ClassWriter file = createClassFile(consumerFolder, className);

        file.addPackageStatement(area, service, CONSUMER_FOLDER);

        String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String areaHelper = createElementType(file, areaName, null, null, areaName + "Helper");
        String serviceHelper = createElementType(file, areaName, serviceName, null, serviceName + "Helper");

        CompositeField stdHeaderArg = createCompositeElementsDetails(file, false, "msgHeader", TypeUtils.createTypeReference(StdStrings.MAL, TRANSPORT_FOLDER, "MALMessageHeader", false), false, true, "msgHeader The header of the received message");
        CompositeField stdBodyArg = createCompositeElementsDetails(file, false, "body", TypeUtils.createTypeReference(StdStrings.MAL, TRANSPORT_FOLDER, "MALMessageBody", false), false, true, "body The body of the received message");
        CompositeField stdNotifyBodyArg = createCompositeElementsDetails(file, false, "body", TypeUtils.createTypeReference(StdStrings.MAL, TRANSPORT_FOLDER, "MALNotifyBody", false), false, true, "body The body of the received message");
        CompositeField stdErrorBodyArg = createCompositeElementsDetails(file, false, "body", TypeUtils.createTypeReference(StdStrings.MAL, TRANSPORT_FOLDER, "MALErrorBody", false), false, true, "body The body of the received message");
        CompositeField stdQosArg = createCompositeElementsDetails(file, false, "qosProperties", TypeUtils.createTypeReference(null, null, "Map<_String;_String>", false), false, true, "qosProperties The QoS properties associated with the message");
        CompositeField stdErrorArg = createCompositeElementsDetails(file, false, "error", TypeUtils.createTypeReference(StdStrings.MAL, null, "MALStandardError", false), false, true, "error The received error message");
        List<CompositeField> stdNoBodyArgs = StubUtils.concatenateArguments(stdHeaderArg, stdQosArg);
        List<CompositeField> stdBodyArgs = StubUtils.concatenateArguments(stdHeaderArg, stdBodyArg, stdQosArg);
        List<CompositeField> stdNotifyBodyArgs = StubUtils.concatenateArguments(stdHeaderArg, stdNotifyBodyArg, stdQosArg);
        List<CompositeField> stdErrorBodyArgs = StubUtils.concatenateArguments(stdHeaderArg, stdErrorBodyArg, stdQosArg);
        List<CompositeField> stdErrorArgs = StubUtils.concatenateArguments(stdHeaderArg, stdErrorArg, stdQosArg);

        file.addClassOpenStatement(className, false, true, createElementType(file, StdStrings.MAL, null, CONSUMER_FOLDER, "MALInteractionAdapter"), null, "Consumer adapter for " + serviceName + " service.");

        // Implement the generation of the adapter
        boolean submitRequired = false;
        boolean requestRequired = false;
        boolean invokeRequired = false;
        boolean progressRequired = false;
        boolean pubsubRequired = false;

        if (supportsToValue) {
            file.addConstructor(StdStrings.PUBLIC, className, createCompositeElementsDetails(file, false, "consumer", TypeUtils.createTypeReference(StdStrings.MAL, CONSUMER_FOLDER, "MALConsumer", false), false, true, null), true, null, null, null).addMethodCloseStatement();
        }

        for (OperationSummary op : summary.getOperations()) {
            switch (op.getPattern()) {
                case SUBMIT_OP: {
                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, null, op.getName() + "AckReceived", Arrays.asList(stdHeaderArg, stdQosArg), null, "Called by the MAL when a SUBMIT acknowledgement is received from a provider for the operation " + op.getName(), null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, null, op.getName() + "ErrorReceived", stdErrorArgs, null, "Called by the MAL when a SUBMIT acknowledgement error is received from a provider for the operation " + op.getName(), null, null).addMethodCloseStatement();
                    submitRequired = true;
                    break;
                }
                case REQUEST_OP: {
                    List<CompositeField> opArgs = StubUtils.concatenateArguments(stdHeaderArg, StubUtils.concatenateArguments(createOperationArguments(getConfig(), file, op.getRetTypes()), stdQosArg));

                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, null, op.getName() + "ResponseReceived", opArgs, null, "Called by the MAL when a REQUEST response is received from a provider for the operation " + op.getName(), null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, null, op.getName() + "ErrorReceived", stdErrorArgs, null, "Called by the MAL when a REQUEST response error is received from a provider for the operation " + op.getName(), null, null).addMethodCloseStatement();
                    requestRequired = true;
                    break;
                }
                case INVOKE_OP: {
                    List<CompositeField> opArgsA = StubUtils.concatenateArguments(stdHeaderArg, StubUtils.concatenateArguments(createOperationArguments(getConfig(), file, op.getAckTypes()), stdQosArg));
                    List<CompositeField> opArgsR = StubUtils.concatenateArguments(stdHeaderArg, StubUtils.concatenateArguments(createOperationArguments(getConfig(), file, op.getRetTypes()), stdQosArg));

                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, null, op.getName() + "AckReceived", opArgsA, null, "Called by the MAL when an INVOKE acknowledgement is received from a provider for the operation " + op.getName(), null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, null, op.getName() + "ResponseReceived", opArgsR, null, "Called by the MAL when an INVOKE response is received from a provider for the operation " + op.getName(), null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, null, op.getName() + "AckErrorReceived", stdErrorArgs, null, "Called by the MAL when an INVOKE acknowledgement error is received from a provider for the operation " + op.getName(), null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, null, op.getName() + "ResponseErrorReceived", stdErrorArgs, null, "Called by the MAL when an INVOKE response error is received from a provider for the operation " + op.getName(), null, null).addMethodCloseStatement();
                    invokeRequired = true;
                    break;
                }
                case PROGRESS_OP: {
                    List<CompositeField> opArgsA = StubUtils.concatenateArguments(stdHeaderArg, StubUtils.concatenateArguments(createOperationArguments(getConfig(), file, op.getAckTypes()), stdQosArg));
                    List<CompositeField> opArgsU = StubUtils.concatenateArguments(stdHeaderArg, StubUtils.concatenateArguments(createOperationArguments(getConfig(), file, op.getUpdateTypes()), stdQosArg));
                    List<CompositeField> opArgsR = StubUtils.concatenateArguments(stdHeaderArg, StubUtils.concatenateArguments(createOperationArguments(getConfig(), file, op.getRetTypes()), stdQosArg));

                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, null, op.getName() + "AckReceived", opArgsA, null, "Called by the MAL when a PROGRESS acknowledgement is received from a provider for the operation " + op.getName(), null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, null, op.getName() + "UpdateReceived", opArgsU, null, "Called by the MAL when a PROGRESS update is received from a provider for the operation " + op.getName(), null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, null, op.getName() + "ResponseReceived", opArgsR, null, "Called by the MAL when a PROGRESS response is received from a provider for the operation " + op.getName(), null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, null, op.getName() + "AckErrorReceived", stdErrorArgs, null, "Called by the MAL when a PROGRESS acknowledgement error is received from a provider for the operation " + op.getName(), null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, null, op.getName() + "UpdateErrorReceived", stdErrorArgs, null, "Called by the MAL when a PROGRESS update error is received from a provider for the operation " + op.getName(), null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, null, op.getName() + "ResponseErrorReceived", stdErrorArgs, null, "Called by the MAL when a PROGRESS response error is received from a provider for the operation " + op.getName(), null, null).addMethodCloseStatement();
                    progressRequired = true;
                    break;
                }
                case PUBSUB_OP: {
                    List<TypeInfo> retTypes = new LinkedList<>();
                    retTypes.add(0, TypeUtils.convertTypeReference(this, TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, false)));
                    retTypes.add(1, TypeUtils.convertTypeReference(this, TypeUtils.createTypeReference(StdStrings.MAL, null, "UpdateHeader", true)));
                    for (TypeInfo ti : op.getRetTypes()) {
                        retTypes.add(TypeUtils.convertTypeReference(this, TypeUtils.createTypeReference(ti.getSourceType().getArea(), ti.getSourceType().getService(), ti.getSourceType().getName(), true)));
                    }

                    List<CompositeField> opArgsU = StubUtils.concatenateArguments(stdHeaderArg, StubUtils.concatenateArguments(createOperationArguments(getConfig(), file, retTypes), stdQosArg));

                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, null, op.getName() + "RegisterAckReceived", StubUtils.concatenateArguments(stdHeaderArg, stdQosArg), null, "Called by the MAL when a PubSub register acknowledgement is received from a broker for the operation " + op.getName(), null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, null, op.getName() + "RegisterErrorReceived", stdErrorArgs, null, "Called by the MAL when a PubSub register acknowledgement error is received from a broker for the operation " + op.getName(), null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, null, op.getName() + "DeregisterAckReceived", StubUtils.concatenateArguments(stdHeaderArg, stdQosArg), null, "Called by the MAL when a PubSub deregister acknowledgement is received from a broker for the operation " + op.getName(), null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, null, op.getName() + "NotifyReceived", opArgsU, null, "Called by the MAL when a PubSub update is received from a broker for the operation " + op.getName(), null, null).addMethodCloseStatement();
                    file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, null, op.getName() + "NotifyErrorReceived", stdErrorArgs, null, "Called by the MAL when a PubSub update error is received from a broker for the operation " + op.getName(), null, null).addMethodCloseStatement();
                    pubsubRequired = true;
                    break;
                }
            }
        }

        if (submitRequired || supportsToValue) {
            createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.SUBMIT_OP, "submitAck", "Ack", 0, stdNoBodyArgs, serviceHelper, throwsMALException, summary, "Called by the MAL when a SUBMIT acknowledgement is received from a provider.");
            createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.SUBMIT_OP, "submit", "", stdErrorBodyArgs, serviceHelper, throwsMALException, summary, "Called by the MAL when a SUBMIT acknowledgement error is received from a provider.");
        }

        if (requestRequired || supportsToValue) {
            createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.REQUEST_OP, "requestResponse", "Response", 3, stdBodyArgs, serviceHelper, throwsMALException, summary, "Called by the MAL when a REQUEST response is received from a provider.");
            createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.REQUEST_OP, "request", "", stdErrorBodyArgs, serviceHelper, throwsMALException, summary, "Called by the MAL when a REQUEST response error is received from a provider.");
        }

        if (invokeRequired || supportsToValue) {
            createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.INVOKE_OP, "invokeAck", "Ack", 1, stdBodyArgs, serviceHelper, throwsMALException, summary, "Called by the MAL when an INVOKE acknowledgement is received from a provider.");
            createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.INVOKE_OP, "invokeAck", "Ack", stdErrorBodyArgs, serviceHelper, throwsMALException, summary, "Called by the MAL when an INVOKE acknowledgement error is received from a provider.");
            createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.INVOKE_OP, "invokeResponse", "Response", 3, stdBodyArgs, serviceHelper, throwsMALException, summary, "Called by the MAL when an INVOKE response is received from a provider.");
            createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.INVOKE_OP, "invokeResponse", "Response", stdErrorBodyArgs, serviceHelper, throwsMALException, summary, "Called by the MAL when an INVOKE response error is received from a provider.");
        }

        if (progressRequired || supportsToValue) {
            createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.PROGRESS_OP, "progressAck", "Ack", 1, stdBodyArgs, serviceHelper, throwsMALException, summary, "Called by the MAL when a PROGRESS acknowledgement is received from a provider.");
            createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.PROGRESS_OP, "progressAck", "Ack", stdErrorBodyArgs, serviceHelper, throwsMALException, summary, "Called by the MAL when a PROGRESS acknowledgement error is received from a provider.");
            createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.PROGRESS_OP, "progressUpdate", "Update", 2, stdBodyArgs, serviceHelper, throwsMALException, summary, "Called by the MAL when a PROGRESS update is received from a provider.");
            createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.PROGRESS_OP, "progressUpdate", "Update", stdErrorBodyArgs, serviceHelper, throwsMALException, summary, "Called by the MAL when a PROGRESS update error is received from a provider.");
            createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.PROGRESS_OP, "progressResponse", "Response", 3, stdBodyArgs, serviceHelper, throwsMALException, summary, "Called by the MAL when a PROGRESS response is received from a provider.");
            createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.PROGRESS_OP, "progressResponse", "Response", stdErrorBodyArgs, serviceHelper, throwsMALException, summary, "Called by the MAL when a PROGRESS response error is received from a provider.");
        }

        if (pubsubRequired || supportsToValue) {
            createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.PUBSUB_OP, "registerAck", "RegisterAck", 1, stdNoBodyArgs, serviceHelper, throwsMALException, summary, "Called by the MAL when a PubSub register acknowledgement is received from a broker.");
            createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.PUBSUB_OP, "register", "Register", stdErrorBodyArgs, serviceHelper, throwsMALException, summary, "Called by the MAL when a PubSub register acknowledgement error is received from a broker.");
            createServiceConsumerAdapterNotifyMethod(file, InteractionPatternEnum.PUBSUB_OP, "notify", "Notify", 2, stdNotifyBodyArgs, areaHelper, areaName, serviceHelper, serviceName, throwsMALException, summary, "Called by the MAL when a PubSub update is received from a broker.");
            createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.PUBSUB_OP, "notify", "Notify", stdErrorBodyArgs, serviceHelper, throwsMALException, summary, "Called by the MAL when a PubSub update error is received from a broker.");
            createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.PUBSUB_OP, "deregisterAck", "DeregisterAck", 1, stdNoBodyArgs, serviceHelper, throwsMALException, summary, "Called by the MAL when a PubSub deregister acknowledgement is received from a broker.");

            file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, null, "notifyReceivedFromOtherService", stdNotifyBodyArgs, throwsMALException, "Called by the MAL when a PubSub update from another service is received from a broker.", null, Arrays.asList(throwsMALException + " if an error is detected processing the message.")).addMethodCloseStatement();
        }

        file.addClassCloseStatement();

        file.flush();
    }

    protected void createServiceConsumerAdapterMessageMethod(ClassWriter file, InteractionPatternEnum optype, String opname, String subopPostname, int opTypeIndex, List<CompositeField> args, String serviceHelper, String throwsMALException, ServiceSummary summary, String comment) throws IOException {
        MethodWriter method = file.addMethodOpenStatement(true, true, false, false, StdStrings.PUBLIC, false, true, null, opname + "Received", args, throwsMALException, comment, null, Arrays.asList(throwsMALException + " if an error is detected processing the message."));
        method.addMethodStatement("switch (" + createMethodCall("msgHeader.getOperation().getValue()") + ")", false);
        method.addMethodStatement("{", false);
        for (OperationSummary op : summary.getOperations()) {
            if (optype == op.getPattern()) {
                String ns = convertToNamespace(serviceHelper + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
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
                String opArgs = createAdapterMethodsArgs(opTypes, "body", true, false);
                method.addMethodStatement("    " + op.getName() + subopPostname + "Received(msgHeader" + opArgs + ", qosProperties)");
                method.addMethodStatement("    break");
            }
        }
        method.addMethodStatement("  default:", false);
        method.addMethodStatement("    throw new " + throwsMALException + "(\"Consumer adapter was not expecting operation number \" + msgHeader.getOperation().getValue())");
        method.addMethodStatement("}", false);
        method.addMethodCloseStatement();
    }

    protected void createServiceConsumerAdapterNotifyMethod(ClassWriter file, InteractionPatternEnum optype, String opname, String subopPostname, int opTypeIndex, List<CompositeField> args, String areaHelper, String areaName, String serviceHelper, String serviceName, String throwsMALException, ServiceSummary summary, String comment) throws IOException {
        MethodWriter method = file.addMethodOpenStatement(true, true, false, false, StdStrings.PUBLIC, false, true, null, opname + "Received", args, throwsMALException, comment, null, Arrays.asList(throwsMALException + " if an error is detected processing the message."));

        method.addMethodStatement("if ((" + areaHelper + "." + areaName.toUpperCase() + "_AREA_NUMBER.equals(msgHeader.getServiceArea())) && (" + serviceHelper + "." + serviceName.toUpperCase() + "_SERVICE_NUMBER.equals(msgHeader.getService())))", false);
        method.addMethodStatement("{", false);
        method.addMethodStatement("  switch (" + createMethodCall("msgHeader.getOperation().getValue()") + ")", false);
        method.addMethodStatement("  {", false);
        for (OperationSummary op : summary.getOperations()) {
            if (optype == op.getPattern()) {
                String ns = convertToNamespace(serviceHelper + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
                method.addMethodWithDependencyStatement("    case " + ns, ns, false);
                List<TypeInfo> opTypes = new LinkedList<>();
                opTypes.add(0, TypeUtils.convertTypeReference(this, TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, false)));
                opTypes.add(1, TypeUtils.convertTypeReference(this, TypeUtils.createTypeReference(StdStrings.MAL, null, "UpdateHeader", true)));
                for (TypeInfo ti : op.getRetTypes()) {
                    opTypes.add(TypeUtils.convertTypeReference(this, TypeUtils.createTypeReference(ti.getSourceType().getArea(), ti.getSourceType().getService(), ti.getSourceType().getName(), true)));
                }

                String opArgs = createAdapterMethodsArgs(opTypes, "body", true, false);
                method.addMethodStatement("      " + op.getName() + subopPostname + "Received(msgHeader" + opArgs + ", qosProperties)");
                method.addMethodStatement("      break");
            }
        }
        method.addMethodStatement("    default:", false);
        method.addMethodStatement("      throw new " + throwsMALException + "(\"Consumer adapter was not expecting operation number \" + msgHeader.getOperation().getValue())");
        method.addMethodStatement("  }", false);
        method.addMethodStatement("}", false);
        method.addMethodStatement("else", false);
        method.addMethodStatement("{", false);
        method.addMethodStatement("  notifyReceivedFromOtherService(msgHeader, body, qosProperties)");
        method.addMethodStatement("}", false);
        method.addMethodCloseStatement();
    }

    protected void createServiceConsumerAdapterErrorMethod(ClassWriter file, InteractionPatternEnum optype, String opname, String subopPostname, List<CompositeField> args, String serviceHelper, String throwsMALException, ServiceSummary summary, String comment) throws IOException {
        MethodWriter method = file.addMethodOpenStatement(true, true, false, false, StdStrings.PUBLIC, false, true, null, opname + "ErrorReceived", args, throwsMALException, comment, null, Arrays.asList(throwsMALException + " if an error is detected processing the message."));
        method.addMethodStatement("switch (" + createMethodCall("msgHeader.getOperation().getValue()") + ")", false);
        method.addMethodStatement("{", false);
        for (OperationSummary op : summary.getOperations()) {
            if (optype == op.getPattern()) {
                String ns = convertToNamespace(serviceHelper + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
                method.addMethodWithDependencyStatement("  case " + ns, ns, false);
                method.addMethodStatement("    " + op.getName() + subopPostname + "ErrorReceived(msgHeader, body.getError(), qosProperties)");
                method.addMethodStatement("    break");
            }
        }
        method.addMethodStatement("  default:", false);
        method.addMethodStatement("    throw new " + throwsMALException + "(\"Consumer adapter was not expecting operation number \" + msgHeader.getOperation().getValue())");
        method.addMethodStatement("}", false);
        method.addMethodCloseStatement();

    }

    protected void createServiceConsumerInterface(File consumerFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException {
        String serviceName = service.getName();

        getLog().info("Creating consumer interface: " + serviceName);

        InterfaceWriter file = createInterfaceFile(consumerFolder, serviceName);

        file.addPackageStatement(area, service, CONSUMER_FOLDER);

        file.addInterfaceOpenStatement(serviceName, null, "Consumer interface for " + serviceName + " service.");

        CompositeField serviceAdapterArg = createCompositeElementsDetails(file, false, "adapter", TypeUtils.createTypeReference(area.getName(), service.getName() + "." + CONSUMER_FOLDER, serviceName + "Adapter", false), false, true, "adapter Listener in charge of receiving the messages from the service provider");
        CompositeField lastInteractionStage = createCompositeElementsDetails(file, false, "lastInteractionStage", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UOCTET, false), true, true, "lastInteractionStage The last stage of the interaction to continue");
        CompositeField initiationTimestamp = createCompositeElementsDetails(file, false, "initiationTimestamp", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.TIME, false), true, true, "initiationTimestamp Timestamp of the interaction initiation message");
        CompositeField transactionId = createCompositeElementsDetails(file, false, "transactionId", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.LONG, false), true, true, "transactionId Transaction identifier of the interaction to continue");
        List<CompositeField> continueOpArgs = StubUtils.concatenateArguments(lastInteractionStage, initiationTimestamp, transactionId, serviceAdapterArg);

        String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String throwsInteractionException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALINTERACTIONEXCEPTION);
        String throwsInteractionAndMALException = throwsInteractionException + ", " + throwsMALException;
        CompositeField msgType = createCompositeElementsDetails(file, false, "return", TypeUtils.createTypeReference(StdStrings.MAL, TRANSPORT_FOLDER, StdStrings.MALMESSAGE, false), false, true, null);
        CompositeField malConsumer = createCompositeElementsDetails(file, false, "return", TypeUtils.createTypeReference(StdStrings.MAL, CONSUMER_FOLDER, "MALConsumer", false), false, true, null);

        file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, malConsumer, "getConsumer", null, null, "Returns the internal MAL consumer object used for sending of messages from this interface", "The MAL consumer object.", null);

        for (OperationSummary op : summary.getOperations()) {
            switch (op.getPattern()) {
                case SEND_OP: {
                    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, msgType, op.getName(), createOperationArguments(getConfig(), file, op.getArgTypes()), throwsInteractionAndMALException, op.getOriginalOp().getComment(), "the MAL message sent to initiate the interaction", Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                    break;
                }
                case SUBMIT_OP:
                case REQUEST_OP: {
                    List<CompositeField> opArgs = createOperationArguments(getConfig(), file, op.getArgTypes());
                    CompositeField opRetType = createOperationReturnType(file, area, service, op);
                    String opRetComment = (null == opRetType) ? null : "The return value of the interaction";
                    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, opRetType, op.getName(), opArgs, throwsInteractionAndMALException, op.getOriginalOp().getComment(), opRetComment, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                    if (supportsAsync) {
                        List<CompositeField> asyncOpArgs = StubUtils.concatenateArguments(opArgs, serviceAdapterArg);
                        file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, msgType, "async" + StubUtils.preCap(op.getName()), asyncOpArgs, throwsInteractionAndMALException, "Asynchronous version of method " + op.getName(), "the MAL message sent to initiate the interaction", Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                    }
                    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, null, "continue" + StubUtils.preCap(op.getName()), continueOpArgs, throwsInteractionAndMALException, "Continues a previously started interaction", null, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                    break;
                }
                case INVOKE_OP:
                case PROGRESS_OP: {
                    List<CompositeField> opArgs = StubUtils.concatenateArguments(createOperationArguments(getConfig(), file, op.getArgTypes()), serviceAdapterArg);
                    CompositeField opRetType = createOperationReturnType(file, area, service, op);
                    String opRetComment = (null == opRetType) ? null : "The acknowledge value of the interaction";
                    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, opRetType, op.getName(), opArgs, throwsInteractionAndMALException, op.getOriginalOp().getComment(), opRetComment, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                    if (supportsAsync) {
                        file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, msgType, "async" + StubUtils.preCap(op.getName()), opArgs, throwsInteractionAndMALException, "Asynchronous version of method " + op.getName(), "the MAL message sent to initiate the interaction", Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                    }
                    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, null, "continue" + StubUtils.preCap(op.getName()), continueOpArgs, throwsInteractionAndMALException, "Continues a previously started interaction", null, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                    break;
                }
                case PUBSUB_OP: {
                    CompositeField subStr = createCompositeElementsDetails(file, false, "subscription", TypeUtils.createTypeReference(StdStrings.MAL, null, "Subscription", false), true, true, "subscription the subscription to register for");
                    CompositeField idStr = createCompositeElementsDetails(file, false, "identifierList", TypeUtils.createTypeReference(StdStrings.MAL, null, "Identifier", true), true, true, "identifierList the subscription identifiers to deregister");

                    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, null, op.getName() + "Register", StubUtils.concatenateArguments(subStr, serviceAdapterArg), throwsInteractionAndMALException, "Register method for the " + op.getName() + " PubSub interaction", null, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, null, op.getName() + "Deregister", Arrays.asList(idStr), throwsInteractionAndMALException, "Deregister method for the " + op.getName() + " PubSub interaction", null, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                    if (supportsAsync) {
                        file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, msgType, "async" + StubUtils.preCap(op.getName()) + "Register", StubUtils.concatenateArguments(subStr, serviceAdapterArg), throwsInteractionAndMALException, "Asynchronous version of method " + op.getName() + "Register", "the MAL message sent to initiate the interaction", Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                        file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, msgType, "async" + StubUtils.preCap(op.getName()) + "Deregister", StubUtils.concatenateArguments(idStr, serviceAdapterArg), throwsInteractionAndMALException, "Asynchronous version of method " + op.getName() + "Deregister", "the MAL message sent to initiate the interaction", Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                    }
                    break;
                }
            }
        }

        file.addInterfaceCloseStatement();

        file.flush();
    }

    protected void createServiceConsumerStub(File consumerFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException {
        getLog().info("Creating consumer stub: " + service.getName());

        String serviceName = service.getName();
        String className = serviceName + "Stub";

        ClassWriter file = createClassFile(consumerFolder, className);

        file.addPackageStatement(area, service, CONSUMER_FOLDER);

        CompositeField serviceAdapterArg = createCompositeElementsDetails(file, false, "adapter", TypeUtils.createTypeReference(area.getName(), service.getName() + "." + CONSUMER_FOLDER, serviceName + "Adapter", false), false, true, "adapter Listener in charge of receiving the messages from the service provider");

        CompositeField lastInteractionStage = createCompositeElementsDetails(file, false, "lastInteractionStage", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UOCTET, false), true, true, "lastInteractionStage The last stage of the interaction to continue");
        CompositeField initiationTimestamp = createCompositeElementsDetails(file, false, "initiationTimestamp", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.TIME, false), true, true, "initiationTimestamp Timestamp of the interaction initiation message");
        CompositeField transactionId = createCompositeElementsDetails(file, false, "transactionId", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.LONG, false), true, true, "transactionId Transaction identifier of the interaction to continue");
        List<CompositeField> continueOpArgs = StubUtils.concatenateArguments(lastInteractionStage, initiationTimestamp, transactionId, serviceAdapterArg);

        String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String throwsInteractionException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALINTERACTIONEXCEPTION);
        String throwsInteractionAndMALException = throwsInteractionException + ", " + throwsMALException;

        CompositeField msgType = createCompositeElementsDetails(file, false, "return", TypeUtils.createTypeReference(StdStrings.MAL, TRANSPORT_FOLDER, StdStrings.MALMESSAGE, false), false, true, null);
        CompositeField msgBodyType = createCompositeElementsDetails(file, false, "return", TypeUtils.createTypeReference(StdStrings.MAL, TRANSPORT_FOLDER, "MALMessageBody", false), false, true, null);
        CompositeField uriType = createCompositeElementsDetails(file, false, "return", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.URI, false), true, true, null);
        String helperType = createElementType(file, area.getName(), service.getName(), null, serviceName + "Helper") + getConfig().getNamingSeparator();
        CompositeField consumerType = createCompositeElementsDetails(file, false, "return", TypeUtils.createTypeReference(StdStrings.MAL, CONSUMER_FOLDER, "MALConsumer", false), false, true, null);
        String consumerMethodCall = createMethodCall("consumer.");
        CompositeField consumerTypeVar = createCompositeElementsDetails(file, false, "consumer", TypeUtils.createTypeReference(StdStrings.MAL, CONSUMER_FOLDER, "MALConsumer", false), false, true, null);

        file.addClassOpenStatement(className, false, false, null, createElementType(file, area.getName(), serviceName, CONSUMER_FOLDER, serviceName), "Consumer stub for " + serviceName + " service.");

        file.addClassVariable(false, true, StdStrings.PRIVATE, consumerTypeVar, false, (String) null);

        MethodWriter method = file.addConstructor(StdStrings.PUBLIC, className, createCompositeElementsDetails(file, false, "consumer", TypeUtils.createTypeReference(StdStrings.MAL, CONSUMER_FOLDER, "MALConsumer", false), false, true, "consumer The MALConsumer to use in this stub."), false, null, "Wraps a MALconsumer connection with service specific methods that map from the high level service API to the generic MAL API.", null);
        method.addMethodStatement(createMethodCall("this.consumer = consumer"));
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, false, createReturnReference(consumerType), "getConsumer", null, null, "Returns the internal MAL consumer object used for sending of messages from this interface", "The MAL consumer object.", null);
        method.addMethodStatement(createMethodCall("return consumer"));
        method.addMethodCloseStatement();

        if (supportsToValue) {
            method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, false, uriType, "getURI", null, null);
            method.addMethodStatement(createMethodCall("return consumer.getUri()"));
            method.addMethodCloseStatement();
        }

        for (OperationSummary op : summary.getOperations()) {
            String operationInstanceVar = addressOf(helperType) + op.getName().toUpperCase() + "_OP";
            switch (op.getPattern()) {
                case SEND_OP: {
                    List<CompositeField> opArgs = createOperationArguments(getConfig(), file, op.getArgTypes());
                    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, msgType, op.getName(), opArgs, throwsInteractionAndMALException, op.getOriginalOp().getComment(), "the MAL message sent to initiate the interaction", Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                    method.addMethodWithDependencyStatement("return " + consumerMethodCall + createConsumerPatternCall(op) + "(" + operationInstanceVar + ", " + createArgNameOrNull(op.getArgTypes()) + ")", helperType, true);
                    method.addMethodCloseStatement();
                    break;
                }
                case SUBMIT_OP:
                case REQUEST_OP: {
                    List<CompositeField> opArgs = createOperationArguments(getConfig(), file, op.getArgTypes());
                    CompositeField opRetType = createOperationReturnType(file, area, service, op);
                    String opRetComment = null;
                    String rv = "";
                    if (null != opRetType) {
                        rv = msgBodyType.getTypeName() + " body = ";
                        opRetComment = "The return value of the interaction";
                    }
                    String opGet = rv + consumerMethodCall + createConsumerPatternCall(op) + "(" + operationInstanceVar + ", " + createArgNameOrNull(op.getArgTypes()) + ")";
                    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, false, opRetType, op.getName(), opArgs, throwsInteractionAndMALException, op.getOriginalOp().getComment(), opRetComment, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                    method.addMethodWithDependencyStatement(opGet, helperType, true);
                    createOperationReturn(file, method, op, opRetType);
                    method.addMethodCloseStatement();

                    if (supportsAsync) {
                        List<CompositeField> asyncOpArgs = StubUtils.concatenateArguments(opArgs, serviceAdapterArg);
                        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, msgType, "async" + StubUtils.preCap(op.getName()), asyncOpArgs, throwsInteractionAndMALException, "Asynchronous version of method " + op.getName(), "the MAL message sent to initiate the interaction", Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                        method.addMethodStatement("return " + consumerMethodCall + "async" + StubUtils.preCap(createConsumerPatternCall(op)) + "(" + operationInstanceVar + ", adapter, " + createArgNameOrNull(op.getArgTypes()) + ")");
                        method.addMethodCloseStatement();
                    }

                    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, null, "continue" + StubUtils.preCap(op.getName()), continueOpArgs, throwsInteractionAndMALException, "Continues a previously started interaction", null, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                    method.addMethodStatement(consumerMethodCall + "continueInteraction(" + operationInstanceVar + ", lastInteractionStage, initiationTimestamp, transactionId, adapter)");
                    method.addMethodCloseStatement();
                    break;
                }
                case INVOKE_OP:
                case PROGRESS_OP: {
                    List<CompositeField> opArgs = StubUtils.concatenateArguments(createOperationArguments(getConfig(), file, op.getArgTypes()), serviceAdapterArg);
                    CompositeField opRetType = createOperationReturnType(file, area, service, op);
                    String opRetComment = null;
                    String rv = "";
                    if (null != opRetType) {
                        rv = msgBodyType.getTypeName() + " body = ";
                        opRetComment = "The acknowledge value of the interaction";
                    }
                    String opGet = rv + consumerMethodCall + createConsumerPatternCall(op) + "(" + operationInstanceVar + ", adapter, " + createArgNameOrNull(op.getArgTypes()) + ")";
                    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, false, opRetType, op.getName(), opArgs, throwsInteractionAndMALException, op.getOriginalOp().getComment(), opRetComment, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                    method.addMethodWithDependencyStatement(opGet, helperType, true);
                    createOperationReturn(file, method, op, opRetType);
                    method.addMethodCloseStatement();

                    if (supportsAsync) {
                        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, msgType, "async" + StubUtils.preCap(op.getName()), opArgs, throwsInteractionAndMALException, "Asynchronous version of method " + op.getName(), "the MAL message sent to initiate the interaction", Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                        method.addMethodStatement("return " + consumerMethodCall + "async" + StubUtils.preCap(createConsumerPatternCall(op)) + "(" + operationInstanceVar + ", adapter, " + createArgNameOrNull(op.getArgTypes()) + ")");
                        method.addMethodCloseStatement();
                    }

                    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, null, "continue" + StubUtils.preCap(op.getName()), continueOpArgs, throwsInteractionAndMALException, "Continues a previously started interaction", null, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                    method.addMethodStatement(consumerMethodCall + "continueInteraction(" + operationInstanceVar + ", lastInteractionStage, initiationTimestamp, transactionId, adapter)");
                    method.addMethodCloseStatement();
                    break;
                }
                case PUBSUB_OP: {
                    CompositeField subStr = createCompositeElementsDetails(file, false, "subscription", TypeUtils.createTypeReference(StdStrings.MAL, null, "Subscription", false), true, true, "subscription the subscription to register for");
                    CompositeField idStr = createCompositeElementsDetails(file, false, "identifierList", TypeUtils.createTypeReference(StdStrings.MAL, null, "Identifier", true), true, true, "identifierList the subscription identifiers to deregister");

                    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, null, op.getName() + "Register", StubUtils.concatenateArguments(subStr, serviceAdapterArg), throwsInteractionAndMALException, "Register method for the " + op.getName() + " PubSub interaction", null, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                    method.addMethodWithDependencyStatement(consumerMethodCall + getRegisterMethodName() + "(" + operationInstanceVar + ", subscription, adapter)", helperType, true);
                    method.addMethodCloseStatement();

                    if (supportsAsync) {
                        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, msgType, "async" + StubUtils.preCap(op.getName()) + "Register", StubUtils.concatenateArguments(subStr, serviceAdapterArg), throwsInteractionAndMALException, "Asynchronous version of method " + op.getName() + "Register", "the MAL message sent to initiate the interaction", Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                        method.addMethodStatement("return " + consumerMethodCall + "async" + StubUtils.preCap(getRegisterMethodName()) + "(" + operationInstanceVar + ", subscription, adapter)");
                        method.addMethodCloseStatement();
                    }

                    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, null, op.getName() + "Deregister", Arrays.asList(idStr), throwsInteractionAndMALException, "Deregister method for the " + op.getName() + " PubSub interaction", null, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                    method.addMethodStatement(consumerMethodCall + getDeregisterMethodName() + "(" + operationInstanceVar + ", identifierList)");
                    method.addMethodCloseStatement();

                    if (supportsAsync) {
                        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, msgType, "async" + StubUtils.preCap(op.getName()) + "Deregister", StubUtils.concatenateArguments(idStr, serviceAdapterArg), throwsInteractionAndMALException, "Asynchronous version of method " + op.getName() + "Deregister", "the MAL message sent to initiate the interaction", Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
                        method.addMethodStatement("return " + consumerMethodCall + "async" + StubUtils.preCap(getDeregisterMethodName()) + "(" + operationInstanceVar + ", identifierList, adapter)");
                        method.addMethodCloseStatement();
                    }
                    break;
                }
            }
        }

        file.addClassCloseStatement();

        file.flush();
    }

    protected void createServiceProviderHandler(File providerFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException {
        getLog().info("Creating provider handler interface: " + service.getName());

        String handlerName = service.getName() + "Handler";
        InterfaceWriter file = createInterfaceFile(providerFolder, handlerName);

        file.addPackageStatement(area, service, PROVIDER_FOLDER);

        file.addInterfaceOpenStatement(handlerName, null, "Interface that providers of the " + service.getName() + " service must implement to handle the operations of that service.");

        CompositeField intHandler = createCompositeElementsDetails(file, false, "interaction", TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, StdStrings.MALINTERACTION, false), false, true, "interaction The MAL object representing the interaction in the provider.");
        String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String throwsInteractionException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALINTERACTIONEXCEPTION);
        String throwsInteractionAndMALException = throwsInteractionException + ", " + throwsMALException;

        for (OperationSummary op : summary.getOperations()) {
            if (InteractionPatternEnum.PUBSUB_OP != op.getPattern()) {
                List<CompositeField> opArgs = createOperationArguments(getConfig(), file, op.getArgTypes());
                CompositeField opRetType = null;
                String opRetComment = null;
                CompositeField serviceHandler = intHandler;

                if (InteractionPatternEnum.REQUEST_OP == op.getPattern()) {
                    opRetType = createOperationReturnType(file, area, service, op);
                    opRetComment = "The return value of the operation";
                } else if ((InteractionPatternEnum.INVOKE_OP == op.getPattern()) || (InteractionPatternEnum.PROGRESS_OP == op.getPattern())) {
                    serviceHandler = createCompositeElementsDetails(file, false, "interaction", TypeUtils.createTypeReference(area.getName(), service.getName() + "." + PROVIDER_FOLDER, StubUtils.preCap(op.getName()) + "Interaction", false), false, true, "interaction The MAL object representing the interaction in the provider.");
                }

                file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, opRetType, op.getName(), StubUtils.concatenateArguments(opArgs, serviceHandler), throwsInteractionAndMALException, "Implements the operation " + op.getName(), opRetComment, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
            }
        }

        CompositeField skel = createCompositeElementsDetails(file, false, "skeleton", TypeUtils.createTypeReference(area.getName(), service.getName() + "." + PROVIDER_FOLDER, service.getName() + "Skeleton", false), false, true, "skeleton The skeleton to be used");
        file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, null, "setSkeleton", Arrays.asList(skel), null, "Sets the skeleton to be used for creation of publishers.", null, null);

        file.addInterfaceCloseStatement();

        file.flush();
    }

    protected void createServiceProviderInvokeInteractionClass(File providerFolder, AreaType area, ServiceType service, OperationSummary op) throws IOException {
        String className = StubUtils.preCap(op.getName()) + "Interaction";
        getLog().info("Creating provider invoke interaction class: " + className);

        ClassWriter file = createClassFile(providerFolder, className);

        file.addPackageStatement(area, service, PROVIDER_FOLDER);

        String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String throwsInteractionException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALINTERACTIONEXCEPTION);
        String throwsInteractionAndMALException = throwsInteractionException + ", " + throwsMALException;
        CompositeField msgType = createCompositeElementsDetails(file, false, "return", TypeUtils.createTypeReference(StdStrings.MAL, TRANSPORT_FOLDER, StdStrings.MALMESSAGE, false), false, true, null);
        CompositeField opType = createCompositeElementsDetails(file, false, "return", TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALInvoke", false), false, true, null);
        CompositeField opTypeVar = createCompositeElementsDetails(file, false, "interaction", TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALInvoke", false), false, false, null);
        CompositeField errType = createCompositeElementsDetails(file, false, "error", TypeUtils.createTypeReference(StdStrings.MAL, null, "MALStandardError", false), false, true, "error The MAL error to send to the consumer.");

        file.addClassOpenStatement(className, false, false, null, null, "Provider INVOKE interaction class for " + service.getName() + "::" + op.getName() + " operation.");

        file.addClassVariable(false, false, StdStrings.PRIVATE, opTypeVar, false, (String) null);

        MethodWriter method = file.addConstructor(StdStrings.PUBLIC, className, createCompositeElementsDetails(file, false, "interaction", TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALInvoke", false), false, true, "interaction The MAL interaction action object to use."), false, null, "Wraps the provided MAL interaction object with methods for sending responses to an INVOKE interaction from a provider.", null);
        method.addMethodStatement(createMethodCall("this.interaction = interaction"));
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, opType, "getInteraction", null, null, "Returns the MAL interaction object used for returning messages from the provider.", "The MAL interaction object provided in the constructor", null);
        method.addMethodStatement("return interaction");
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, msgType, "sendAcknowledgement", createOperationArguments(getConfig(), file, op.getAckTypes()), throwsInteractionAndMALException,
                "Sends a INVOKE acknowledge to the consumer", "Returns the MAL message created by the acknowledge", Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
        method.addMethodStatement(createMethodCall("return interaction.sendAcknowledgement(") + createArgNameOrNull(op.getAckTypes()) + ")");
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, msgType, "sendResponse", createOperationArguments(getConfig(), file, op.getRetTypes()), throwsInteractionAndMALException,
                "Sends a INVOKE response to the consumer", "Returns the MAL message created by the response", Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
        method.addMethodStatement(createMethodCall("return interaction.sendResponse(") + createArgNameOrNull(op.getRetTypes()) + ")");
        method.addMethodCloseStatement();

        createServiceProviderInteractionErrorHandlers(file, false, msgType, errType, throwsInteractionException, throwsMALException);

        file.addClassCloseStatement();

        file.flush();
    }

    protected void createServiceProviderProgressInteractionClass(File providerFolder, AreaType area, ServiceType service, OperationSummary op) throws IOException {
        String className = StubUtils.preCap(op.getName()) + "Interaction";
        getLog().info("Creating provider progress interaction class: " + className);

        ClassWriter file = createClassFile(providerFolder, className);

        file.addPackageStatement(area, service, PROVIDER_FOLDER);

        String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String throwsInteractionException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALINTERACTIONEXCEPTION);
        String throwsInteractionAndMALException = throwsInteractionException + ", " + throwsMALException;
        CompositeField msgType = createCompositeElementsDetails(file, false, "return", TypeUtils.createTypeReference(StdStrings.MAL, TRANSPORT_FOLDER, StdStrings.MALMESSAGE, false), false, true, null);
        CompositeField opType = createCompositeElementsDetails(file, false, "return", TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALProgress", false), false, true, null);
        CompositeField opTypeVar = createCompositeElementsDetails(file, false, "interaction", TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALProgress", false), false, false, null);
        CompositeField errType = createCompositeElementsDetails(file, false, "error", TypeUtils.createTypeReference(StdStrings.MAL, null, "MALStandardError", false), false, true, "error The MAL error to send to the consumer.");

        file.addClassOpenStatement(className, false, false, null, null, "Provider PROGRESS interaction class for " + service.getName() + "::" + op.getName() + " operation.");

        file.addClassVariable(false, false, StdStrings.PRIVATE, opTypeVar, false, (String) null);

        MethodWriter method = file.addConstructor(StdStrings.PUBLIC, className, createCompositeElementsDetails(file, false, "interaction", TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALProgress", false), false, true, "interaction The MAL interaction action object to use."), false, null, "Wraps the provided MAL interaction object with methods for sending responses to an PROGRESS interaction from a provider.", null);
        method.addMethodStatement(createMethodCall("this.interaction = interaction"));
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, opType, "getInteraction", null, null, "Returns the MAL interaction object used for returning messages from the provider.", "The MAL interaction object provided in the constructor", null);
        method.addMethodStatement("return interaction");
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, msgType, "sendAcknowledgement", createOperationArguments(getConfig(), file, op.getAckTypes()), throwsInteractionAndMALException,
                "Sends a PROGRESS acknowledge to the consumer", "Returns the MAL message created by the acknowledge", Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
        method.addMethodStatement(createMethodCall("return interaction.sendAcknowledgement(") + createArgNameOrNull(op.getAckTypes()) + ")");
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, msgType, "sendUpdate", createOperationArguments(getConfig(), file, op.getUpdateTypes()), throwsInteractionAndMALException,
                "Sends a PROGRESS update to the consumer", "Returns the MAL message created by the update", Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
        method.addMethodStatement(createMethodCall("return interaction.sendUpdate(") + createArgNameOrNull(op.getUpdateTypes()) + ")");
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, msgType, "sendResponse", createOperationArguments(getConfig(), file, op.getRetTypes()), throwsInteractionAndMALException,
                "Sends a PROGRESS response to the consumer", "Returns the MAL message created by the response", Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
        method.addMethodStatement(createMethodCall("return interaction.sendResponse(") + createArgNameOrNull(op.getRetTypes()) + ")");
        method.addMethodCloseStatement();

        createServiceProviderInteractionErrorHandlers(file, true, msgType, errType, throwsInteractionException, throwsMALException);

        file.addClassCloseStatement();

        file.flush();
    }

    protected void createServiceProviderInteractionErrorHandlers(ClassWriter file, boolean withUpdate, CompositeField msgType, CompositeField errType, String throwsInteractionException, String throwsMALException) throws IOException {
        MethodWriter method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, msgType, "sendError", Arrays.asList(errType), throwsInteractionException + ", " + throwsMALException,
                "Sends an error to the consumer", "Returns the MAL message created by the error", Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
        method.addMethodStatement(createMethodCall("return interaction.sendError(error)"));
        method.addMethodCloseStatement();

        if (withUpdate) {
            method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, msgType, "sendUpdateError", Arrays.asList(errType), throwsInteractionException + ", " + throwsMALException,
                    "Sends an update error to the consumer", "Returns the MAL message created by the error", Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
            method.addMethodStatement(createMethodCall("return interaction.sendUpdateError(error)"));
            method.addMethodCloseStatement();
        }
    }

    protected void createServiceProviderSkeleton(File providerFolder, AreaType area, ServiceType service, ServiceSummary summary, Map<String, RequiredPublisher> requiredPublishers) throws IOException {
        getLog().info("Creating provider skeleton interface: " + service.getName());

        String skeletonName = service.getName() + "Skeleton";
        InterfaceWriter file = createInterfaceFile(providerFolder, skeletonName);
        String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        CompositeField malDomId = createCompositeElementsDetails(file, false, "domain", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, true), true, true, "domain the domain used for publishing");
        CompositeField malNetworkZone = createCompositeElementsDetails(file, false, "networkZone", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, false), true, true, "networkZone the network zone used for publishing");
        CompositeField malSession = createCompositeElementsDetails(file, false, "sessionType", TypeUtils.createTypeReference(StdStrings.MAL, null, "SessionType", false), true, true, "sessionType the session used for publishing");
        CompositeField malSessionName = createCompositeElementsDetails(file, false, "sessionName", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, false), true, true, "sessionName the session name used for publishing");
        CompositeField malqos = createCompositeElementsDetails(file, false, "qos", TypeUtils.createTypeReference(StdStrings.MAL, null, "QoSLevel", false), true, true, "qos the QoS used for publishing");
        CompositeField malqosprops = createCompositeElementsDetails(file, false, "qosProps", TypeUtils.createTypeReference(null, null, "Map<_String;_String>", false), false, true, "qosProps the QoS properties used for publishing");
        CompositeField malPriority = createCompositeElementsDetails(file, false, "priority", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UINTEGER, false), true, true, "priority the priority used for publishing");

        file.addPackageStatement(area, service, PROVIDER_FOLDER);

        file.addInterfaceOpenStatement(skeletonName, null, "The skeleton interface for the " + service.getName() + " service.");

        for (OperationSummary op : summary.getOperations()) {
            switch (op.getPattern()) {
                case PUBSUB_OP: {
                    String updateType = getConfig().getAreaPackage(area.getName()) + area.getName().toLowerCase() + "." + service.getName().toLowerCase() + "." + PROVIDER_FOLDER + "." + StubUtils.preCap(op.getName()) + "Publisher";
                    requiredPublishers.put(updateType, new RequiredPublisher(area, service, op));
                    file.addTypeDependency("Map<_String;_String>");
                    CompositeField updateTypeField = createCompositeElementsDetails(file, false, "publisher", TypeUtils.createTypeReference(area.getName(), service.getName() + "." + PROVIDER_FOLDER, StubUtils.preCap(op.getName()) + "Publisher", false), false, true, null);
                    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, updateTypeField, "create" + StubUtils.preCap(op.getName()) + "Publisher", StubUtils.concatenateArguments(malDomId, malNetworkZone, malSession, malSessionName, malqos, malqosprops, malPriority), throwsMALException,
                            "Creates a publisher object using the current registered provider set for the PubSub operation " + op.getName(), "The new publisher object.", Arrays.asList(throwsMALException + " if a problem is detected during creation of the publisher"));
                    break;
                }
            }
        }

        file.addInterfaceCloseStatement();

        file.flush();
    }

    protected void createServiceProviderSkeletonHandler(File providerFolder, AreaType area, ServiceType service, ServiceSummary summary, boolean isDelegate) throws IOException {
        String className = service.getName();
        String comment;
        if (isDelegate) {
            className += "DelegationSkeleton";
            comment = "Provider Delegation skeleton for " + className + " service.";
        } else {
            className += "InheritanceSkeleton";
            comment = "Provider Inheritance skeleton for " + className + " service.";
        }

        ClassWriter file = createClassFile(providerFolder, className);

        file.addPackageStatement(area, service, PROVIDER_FOLDER);

        String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String throwsInteractionException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALINTERACTIONEXCEPTION);
        String throwsMALAndInteractionException = throwsInteractionException + ", " + throwsMALException;
        String malHelper = createElementType(file, StdStrings.MAL, null, null, "MALHelper");
        String helperName = createElementType(file, area.getName(), service.getName(), null, service.getName() + "Helper");
        String malString = malStringAsElement(file);
        String malInteger = createElementType(file, StdStrings.MAL, null, StdStrings.INTEGER);
        String stdError = createElementType(file, StdStrings.MAL, null, null, "MALStandardError");
        CompositeField stdBodyArg = createCompositeElementsDetails(file, false, "body", TypeUtils.createTypeReference(StdStrings.MAL, TRANSPORT_FOLDER, "MALMessageBody", false), false, true, "body the message body");
        String stdErrorNs = convertToNamespace("," + stdError + ".," + malString + ".," + malInteger + ".");
        CompositeField malDomId = createCompositeElementsDetails(file, false, "domain", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, true), true, true, "domain the domain used for publishing");
        CompositeField malNetworkZone = createCompositeElementsDetails(file, false, "networkZone", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, false), true, true, "networkZone the network zone used for publishing");
        CompositeField malSession = createCompositeElementsDetails(file, false, "sessionType", TypeUtils.createTypeReference(StdStrings.MAL, null, "SessionType", false), true, true, "sessionType the session used for publishing");
        CompositeField malSessionName = createCompositeElementsDetails(file, false, "sessionName", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, false), true, true, "sessionName the session name used for publishing");
        CompositeField malqos = createCompositeElementsDetails(file, false, "qos", TypeUtils.createTypeReference(StdStrings.MAL, null, "QoSLevel", false), true, true, "qos the QoS used for publishing");
        CompositeField malqosprops = createCompositeElementsDetails(file, false, "qosProps", TypeUtils.createTypeReference(null, null, "Map<_String;_String>", false), false, true, "qosProps the QoS properties used for publishing");
        CompositeField malPriority = createCompositeElementsDetails(file, false, "priority", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UINTEGER, false), true, true, "priority the priority used for publishing");
        CompositeField proviedrSetVar = createCompositeElementsDetails(file, false, "providerSet", TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALProviderSet", false), false, true, null);
        List<CompositeField> psArgs = StubUtils.concatenateArguments(malDomId, malNetworkZone, malSession, malSessionName, malqos, malqosprops, malPriority);

        String implementsList = createElementType(file, StdStrings.MAL, null, PROVIDER_FOLDER, "MALInteractionHandler") + ", " + createElementType(file, area.getName(), service.getName(), PROVIDER_FOLDER, service.getName() + "Skeleton");
        if (!isDelegate) {
            implementsList += ", " + createElementType(file, area.getName(), service.getName(), PROVIDER_FOLDER, service.getName() + "Handler");
        }
        file.addClassOpenStatement(className, false, !isDelegate, null, implementsList, comment);

        file.addClassVariable(false, false, StdStrings.PRIVATE, proviedrSetVar, false, "(" + helperName + getConfig().getNamingSeparator() + service.getName().toUpperCase() + "_SERVICE)");

        if (isDelegate) {
            CompositeField handlerName = createCompositeElementsDetails(file, false, "delegate", TypeUtils.createTypeReference(area.getName(), service.getName() + "." + PROVIDER_FOLDER, service.getName() + "Handler", false), false, true, null);
            file.addClassVariable(false, false, StdStrings.PRIVATE, handlerName, false, (String) null);
        }

        if (isDelegate) {
            MethodWriter method = file.addConstructor(StdStrings.PUBLIC, className, createCompositeElementsDetails(file, false, "delegate", TypeUtils.createTypeReference(area.getName(), service.getName().toLowerCase() + "." + PROVIDER_FOLDER, service.getName() + "Handler", false), false, true, "delegate The interaction handler used for delegation"), false, null, "Creates a delegation skeleton using the supplied delegate.", null);
            method.addMethodStatement(createMethodCall("this.delegate = delegate"));
            method.addMethodStatement(createMethodCall("delegate.setSkeleton(this)"));
            method.addMethodCloseStatement();
        } else {
            CompositeField skeletonName = createCompositeElementsDetails(file, false, "skeleton", TypeUtils.createTypeReference(area.getName(), service.getName() + "." + PROVIDER_FOLDER, service.getName() + "Skeleton", false), false, true, "skeleton Not used in the inheritance pattern (the skeleton is 'this'");
            MethodWriter method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, null, "setSkeleton", Arrays.asList(skeletonName), null, "Implements the setSkeleton method of the handler interface but does nothing as this is the skeleton.", null, null);
            method.addMethodStatement("// Not used in the inheritance pattern (the skeleton is 'this')");
            method.addMethodCloseStatement();

        }
        CompositeField providerType = createCompositeElementsDetails(file, false, "provider", TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALProvider", false), false, true, "provider The provider to add");
        MethodWriter method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, null, "malInitialize", Arrays.asList(providerType), throwsMALException, "Adds the supplied MAL provider to the internal list of providers used for PubSub", null, Arrays.asList(throwsMALException + " If an error is detected."));
        method.addMethodStatement(createMethodCall("providerSet.addProvider(provider)"));
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, null, "malFinalize", Arrays.asList(providerType), throwsMALException, "Removes the supplied MAL provider from the internal list of providers used for PubSub", null, Arrays.asList(throwsMALException + " If an error is detected."));
        method.addMethodStatement(createMethodCall("providerSet.removeProvider(provider)"));
        method.addMethodCloseStatement();

        // add publisher handler code
        for (OperationSummary op : summary.getOperations()) {
            switch (op.getPattern()) {
                case PUBSUB_OP: {
                    CompositeField updateType = createCompositeElementsDetails(file, false, "publisher", TypeUtils.createTypeReference(area.getName(), service.getName() + "." + PROVIDER_FOLDER, StubUtils.preCap(op.getName()) + "Publisher", false), false, true, null);
                    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, false, updateType, "create" + StubUtils.preCap(op.getName()) + "Publisher", psArgs, throwsMALException, "Creates a publisher object using the current registered provider set for the PubSub operation " + op.getName(), "The new publisher object.", Arrays.asList(throwsMALException + " if a problem is detected during creation of the publisher"));
                    String ns = convertToNamespace(helperName + "." + op.getName().toUpperCase() + "_OP");
                    method.addMethodWithDependencyStatement("return new " + updateType.getTypeName() + createMethodCall("(providerSet.createPublisherSet(") + ns + ", domain, networkZone, sessionType, sessionName, qos, qosProps, priority))", ns, true);
                    method.addMethodCloseStatement();
                    break;
                }
            }
        }

        // for each IP type add handler code
        String delegateCall = "";
        if (isDelegate) {
            delegateCall = createMethodCall("delegate.");
        }

        // SEND handler
        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, null, "handleSend", StubUtils.concatenateArguments(createServiceProviderSkeletonSendHandler(file, "interaction", "interaction the interaction object"), stdBodyArg), throwsMALAndInteractionException, "Called by the provider MAL layer on reception of a message to handle the interaction", null, Arrays.asList(throwsMALException + " if there is a internal error", throwsInteractionException + " if there is a operation interaction error"));
        method.addMethodStatement(createMethodCall("switch (" + createProviderSkeletonHandlerSwitch() + ")"), false);
        method.addMethodStatement("{", false);
        for (OperationSummary op : summary.getOperations()) {
            if (op.getPattern() == InteractionPatternEnum.SEND_OP) {
                String opArgs = createAdapterMethodsArgs(op.getArgTypes(), "body", false, true);
                String ns = convertToNamespace(helperName + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
                method.addMethodWithDependencyStatement("  case " + ns, ns, false);
                method.addMethodStatement("    " + delegateCall + op.getName() + "(" + opArgs + "interaction)");
                method.addMethodStatement("    break");
            }
        }
        method.addMethodStatement("  default:", false);
        String ns = convertToNamespace(malHelper + ".UNSUPPORTED_OPERATION_ERROR_NUMBER");
        method.addMethodWithDependencyStatement("    throw new " + throwsInteractionException + "(new " + convertToNamespace(stdError + "(" + errorCodeAsReference(file, ns) + ", new " + malString + "(\"Unknown operation\"") + ")))", ns + stdErrorNs, true);
        method.addMethodStatement("}", false);
        method.addMethodCloseStatement();

        // SUBMIT handler
        CompositeField submitInt = createCompositeElementsDetails(file, false, "interaction", TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALSubmit", false), false, true, "interaction the interaction object");
        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, null, "handleSubmit", StubUtils.concatenateArguments(submitInt, stdBodyArg), throwsMALAndInteractionException, "Called by the provider MAL layer on reception of a message to handle the interaction", null, Arrays.asList(throwsMALException + " if there is a internal error", throwsInteractionException + " if there is a operation interaction error"));
        method.addMethodStatement(createMethodCall("switch (" + createProviderSkeletonHandlerSwitch() + ")"), false);
        method.addMethodStatement("{", false);
        for (OperationSummary op : summary.getOperations()) {
            if (op.getPattern() == InteractionPatternEnum.SUBMIT_OP) {
                String opArgs = createAdapterMethodsArgs(op.getArgTypes(), "body", false, true);
                ns = convertToNamespace(helperName + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
                method.addMethodWithDependencyStatement("  case " + ns, ns, false);
                method.addMethodStatement("    " + delegateCall + op.getName() + "(" + opArgs + "interaction)");
                method.addMethodStatement(createMethodCall("    interaction.sendAcknowledgement()"));
                method.addMethodStatement("    break");
            }
        }
        method.addMethodStatement("  default:", false);
        ns = convertToNamespace(malHelper + ".UNSUPPORTED_OPERATION_ERROR_NUMBER");
        method.addMethodWithDependencyStatement(createMethodCall("    interaction.sendError(new " + convertToNamespace(stdError + "(" + errorCodeAsReference(file, ns) + ", new " + malString + "(\"Unknown operation\"") + ")))"), ns + stdErrorNs, true);
        method.addMethodStatement("}", false);
        method.addMethodCloseStatement();

        // REQUEST handler
        CompositeField requestInt = createCompositeElementsDetails(file, false, "interaction", TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALRequest", false), false, true, "interaction the interaction object");
        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, null, "handleRequest", StubUtils.concatenateArguments(requestInt, stdBodyArg), throwsMALAndInteractionException, "Called by the provider MAL layer on reception of a message to handle the interaction", null, Arrays.asList(throwsMALException + " if there is a internal error", throwsInteractionException + " if there is a operation interaction error"));
        method.addMethodStatement(createMethodCall("switch (" + createProviderSkeletonHandlerSwitch() + ")"), false);
        method.addMethodStatement("{", false);
        for (OperationSummary op : summary.getOperations()) {
            if (op.getPattern() == InteractionPatternEnum.REQUEST_OP) {
                String opArgs = createAdapterMethodsArgs(op.getArgTypes(), "body", false, true);
                String opResp = delegateCall + op.getName() + "(" + opArgs + "interaction)";
                ns = convertToNamespace(helperName + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
                method.addMethodWithDependencyStatement("  case " + ns, ns, false);
                createRequestResponseDecompose(method, op, opResp, createReturnType(file, area, service, op.getName(), "Response", op.getRetTypes()));
                method.addMethodStatement("    break");
            }
        }
        method.addMethodStatement("  default:", false);
        ns = convertToNamespace(malHelper + ".UNSUPPORTED_OPERATION_ERROR_NUMBER");
        method.addMethodWithDependencyStatement(createMethodCall("    interaction.sendError(new " + convertToNamespace(stdError + "(" + errorCodeAsReference(file, ns) + ", new " + malString + "(\"Unknown operation\"") + ")))"), ns + stdErrorNs, true);
        method.addMethodStatement("}", false);
        method.addMethodCloseStatement();

        // INVOKE handler
        CompositeField invokeInt = createCompositeElementsDetails(file, false, "interaction", TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALInvoke", false), false, true, "interaction the interaction object");
        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, null, "handleInvoke", StubUtils.concatenateArguments(invokeInt, stdBodyArg), throwsMALAndInteractionException, "Called by the provider MAL layer on reception of a message to handle the interaction", null, Arrays.asList(throwsMALException + " if there is a internal error", throwsInteractionException + " if there is a operation interaction error"));
        method.addMethodStatement(createMethodCall("switch (" + createProviderSkeletonHandlerSwitch() + ")"), false);
        method.addMethodStatement("{", false);
        for (OperationSummary op : summary.getOperations()) {
            if (op.getPattern() == InteractionPatternEnum.INVOKE_OP) {
                String opArgs = createAdapterMethodsArgs(op.getArgTypes(), "body", false, true);
                ns = convertToNamespace(helperName + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
                method.addMethodWithDependencyStatement("  case " + ns, ns, false);
                method.addMethodStatement("    " + delegateCall + op.getName() + "(" + opArgs + "new " + convertClassName(StubUtils.preCap(op.getName()) + "Interaction") + "(interaction))");
                method.addMethodStatement("    break");
            }
        }
        method.addMethodStatement("  default:", false);
        ns = convertToNamespace(malHelper + ".UNSUPPORTED_OPERATION_ERROR_NUMBER");
        method.addMethodWithDependencyStatement(createMethodCall("    interaction.sendError(new " + convertToNamespace(stdError + "(" + errorCodeAsReference(file, ns) + ", new " + malString + "(\"Unknown operation\"") + ")))"), ns + stdErrorNs, true);
        method.addMethodStatement("}", false);
        method.addMethodCloseStatement();

        // PROGRESS handler
        CompositeField progressInt = createCompositeElementsDetails(file, false, "interaction", TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALProgress", false), false, true, "interaction the interaction object");
        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, null, "handleProgress", StubUtils.concatenateArguments(progressInt, stdBodyArg), throwsMALAndInteractionException, "Called by the provider MAL layer on reception of a message to handle the interaction", null, Arrays.asList(throwsMALException + " if there is a internal error", throwsInteractionException + " if there is a operation interaction error"));
        method.addMethodStatement(createMethodCall("switch (" + createProviderSkeletonHandlerSwitch() + ")"), false);
        method.addMethodStatement("{", false);
        for (OperationSummary op : summary.getOperations()) {
            if (op.getPattern() == InteractionPatternEnum.PROGRESS_OP) {
                String opArgs = createAdapterMethodsArgs(op.getArgTypes(), "body", false, true);
                ns = convertToNamespace(helperName + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
                method.addMethodWithDependencyStatement("  case " + ns, ns, false);
                method.addMethodStatement("    " + delegateCall + op.getName() + "(" + opArgs + "new " + convertClassName(StubUtils.preCap(op.getName()) + "Interaction") + "(interaction))");
                method.addMethodStatement("    break");
            }
        }
        method.addMethodStatement("  default:", false);
        ns = convertToNamespace(malHelper + ".UNSUPPORTED_OPERATION_ERROR_NUMBER");
        method.addMethodWithDependencyStatement(createMethodCall("    interaction.sendError(new " + convertToNamespace(stdError + "(" + errorCodeAsReference(file, ns) + ", new " + malString + "(\"Unknown operation\"") + ")))"), ns + stdErrorNs, true);
        method.addMethodStatement("}", false);
        method.addMethodCloseStatement();

        file.addClassCloseStatement();

        file.flush();
    }

    private void createRequestResponseDecompose(MethodWriter method, OperationSummary op, String opCall, CompositeField opRetType) throws IOException {
        List<TypeInfo> targetTypes = op.getRetTypes();

        if ((null != targetTypes) && (0 < targetTypes.size())) {
            if (1 == targetTypes.size()) {
                if ((op.getRetTypes().get(0).isNativeType())) {
                    String arg = op.getName() + "Rt";
                    method.addMethodStatement("    " + opRetType.getTypeName() + " " + arg + " = " + opCall);
                    StringBuilder buf = new StringBuilder();
                    buf.append("(").append(arg).append(" == null) ? null : new ").append(getConfig().getAreaPackage(StdStrings.MAL)).append("mal.").append(getConfig().getStructureFolder()).append(".").append(StdStrings.UNION).append("(").append(arg).append(")");
                    method.addMethodStatement(createMethodCall("    interaction.sendResponse(" + buf.toString() + ")"));
                } else {
                    method.addMethodStatement(createMethodCall("    interaction.sendResponse(" + opCall + ")"));
                }
            } else {
                String arg = op.getName() + "Rt";
                StringBuilder buf = new StringBuilder();

                for (int i = 0; i < targetTypes.size(); i++) {
                    TypeInfo ti = targetTypes.get(i);
                    if (i > 0) {
                        buf.append(", ");
                    }
                    if (ti.isNativeType()) {
                        buf.append("(").append(arg).append(".getBodyElement").append(i).append("()").append(" == null) ? null : new ").append(getConfig().getAreaPackage(StdStrings.MAL)).append("mal.").append(getConfig().getStructureFolder()).append(".").append(StdStrings.UNION).append("(").append(arg).append(".getBodyElement").append(i).append("()").append(")");
                    } else {
                        buf.append(arg).append(".getBodyElement").append(i).append("()");
                    }
                }

                method.addMethodStatement("    " + opRetType.getTypeName() + " " + arg + " = " + opCall);
                method.addMethodStatement(createMethodCall("    interaction.sendResponse(" + buf.toString() + ")"));
            }
        } else {
            // operation has an empty response
            method.addMethodStatement(createMethodCall("    " + opCall));
            method.addMethodStatement(createMethodCall("    interaction.sendResponse()"));
        }
    }

    protected void createAreaHelperClass(File areaFolder, AreaType area) throws IOException {
        getLog().info("Creating area helper class: " + area.getName());
        ClassWriter file = createClassFile(areaFolder, area.getName() + "Helper");

        String areaName = area.getName();
        String areaNameCaps = area.getName().toUpperCase();

        file.addPackageStatement(area, null, null);

        String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String identifierType = createElementType(file, StdStrings.MAL, null, StdStrings.IDENTIFIER);
        CompositeField eleFactory = createCompositeElementsDetails(file, false, "bodyElementFactory", TypeUtils.createTypeReference(StdStrings.MAL, null, "MALElementFactoryRegistry", false), false, true, "bodyElementFactory The element factory registry to initialise with this helper.");
        CompositeField _areaNumberVar = createCompositeElementsDetails(file, false, "_" + areaNameCaps + "_AREA_NUMBER", TypeUtils.createTypeReference(null, null, "int", false), false, false, "Area number literal.");
        CompositeField areaNumberVar = createCompositeElementsDetails(file, false, areaNameCaps + "_AREA_NUMBER", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.USHORT, false), true, false, "Area number instance.");
        CompositeField areaNameVar = createCompositeElementsDetails(file, false, areaNameCaps + "_AREA_NAME", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, false), true, false, "Area name constant.");
        CompositeField _areaVersionVar = createCompositeElementsDetails(file, false, "_" + areaNameCaps + "_AREA_VERSION", TypeUtils.createTypeReference(null, null, "short", false), false, false, "Area version literal.");
        CompositeField areaVersionVar = createCompositeElementsDetails(file, false, areaNameCaps + "_AREA_VERSION", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UOCTET, false), true, false, "Area version instance.");
        CompositeField areaVar = createCompositeElementsDetails(file, false, areaNameCaps + "_AREA", TypeUtils.createTypeReference(StdStrings.MAL, null, "MALArea", false), false, true, "Area singleton instance.");

        file.addClassOpenStatement(areaName + "Helper", false, false, null, null, "Helper class for " + areaName + " area.");

        file.addClassVariable(true, true, StdStrings.PUBLIC, _areaNumberVar, false, String.valueOf(area.getNumber()));
        file.addClassVariable(true, true, StdStrings.PUBLIC, areaNumberVar, false, "(_" + areaNameCaps + "_AREA_NUMBER)");

        file.addClassVariable(true, true, StdStrings.PUBLIC, areaNameVar, false, "(\"" + areaName + "\")");

        file.addClassVariable(true, true, StdStrings.PUBLIC, _areaVersionVar, false, String.valueOf(area.getVersion()));
        file.addClassVariable(true, true, StdStrings.PUBLIC, areaVersionVar, false, "(_" + areaNameCaps + "_AREA_VERSION)");

        String areaObjectInitialValue = createAreaHelperClassInitialValue(areaNameCaps, area.getVersion());
        file.addClassVariable(true, false, StdStrings.PUBLIC, areaVar, true, areaObjectInitialValue);

        // create error numbers
        if ((null != area.getErrors()) && !area.getErrors().getError().isEmpty()) {
            for (ErrorDefinitionType error : area.getErrors().getError()) {
                String errorNameCaps = error.getName().toUpperCase();
                CompositeField _errorNumberVar = createCompositeElementsDetails(file, false, "_" + errorNameCaps + "_ERROR_NUMBER", TypeUtils.createTypeReference(null, null, "long", false), false, false, "Error literal for error " + errorNameCaps);
                CompositeField errorNumberVar = createCompositeElementsDetails(file, false, errorNameCaps + "_ERROR_NUMBER", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UINTEGER, false), true, false, "Error instance for error " + errorNameCaps);

                file.addClassVariable(true, true, StdStrings.PUBLIC, _errorNumberVar, false, String.valueOf(error.getNumber()));
                file.addClassVariable(true, true, StdStrings.PUBLIC, errorNumberVar, false, "(_" + errorNameCaps + "_ERROR_NUMBER)");
            }
        }

        List<String> typeCalls = new LinkedList<>();

        if ((null != area.getDataTypes()) && !area.getDataTypes().getFundamentalOrAttributeOrComposite().isEmpty()) {
            for (Object oType : area.getDataTypes().getFundamentalOrAttributeOrComposite()) {
                if (oType instanceof AttributeType) {
                    AttributeType dt = (AttributeType) oType;

                    String clsName = convertClassName(createElementType(file, area.getName(), null, StdStrings.ATTRIBUTE));
                    String factoryName = convertClassName(createElementType(file, area.getName(), null, getConfig().getStructureFolder() + getConfig().getNamingSeparator() + getConfig().getFactoryFolder(), dt.getName() + "Factory"));
                    String lclsName = convertClassName(createElementType(file, area.getName(), null, dt.getName() + "List"));
                    String lfactoryName = convertClassName(createElementType(file, area.getName(), null, getConfig().getStructureFolder() + getConfig().getNamingSeparator() + getConfig().getFactoryFolder(), dt.getName() + "ListFactory"));
                    typeCalls.add(clsName + getConfig().getNamingSeparator() + dt.getName().toUpperCase() + "_SHORT_FORM, new " + factoryName + "()");
                    typeCalls.add(lclsName + getConfig().getNamingSeparator() + "SHORT_FORM, new " + lfactoryName + "()");
                } else if (oType instanceof CompositeType) {
                    CompositeType dt = (CompositeType) oType;

                    if (null != dt.getShortFormPart()) {
                        String clsName = convertClassName(createElementType(file, area.getName(), null, dt.getName()));
                        String factoryName = convertClassName(createElementType(file, area.getName(), null, getConfig().getStructureFolder() + getConfig().getNamingSeparator() + getConfig().getFactoryFolder(), dt.getName() + "Factory"));
                        String lclsName = convertClassName(createElementType(file, area.getName(), null, dt.getName() + "List"));
                        String lfactoryName = convertClassName(createElementType(file, area.getName(), null, getConfig().getStructureFolder() + getConfig().getNamingSeparator() + getConfig().getFactoryFolder(), dt.getName() + "ListFactory"));
                        typeCalls.add(clsName + getConfig().getNamingSeparator() + "SHORT_FORM, new " + factoryName + "()");
                        typeCalls.add(lclsName + getConfig().getNamingSeparator() + "SHORT_FORM, new " + lfactoryName + "()");
                    }
                } else if (oType instanceof EnumerationType) {
                    EnumerationType dt = (EnumerationType) oType;
                    String clsName = convertClassName(createElementType(file, area.getName(), null, dt.getName()));
                    String factoryName = convertClassName(createElementType(file, area.getName(), null, getConfig().getStructureFolder() + getConfig().getNamingSeparator() + getConfig().getFactoryFolder(), dt.getName() + "Factory"));
                    String lclsName = convertClassName(createElementType(file, area.getName(), null, dt.getName() + "List"));
                    String lfactoryName = convertClassName(createElementType(file, area.getName(), null, getConfig().getStructureFolder() + getConfig().getNamingSeparator() + getConfig().getFactoryFolder(), dt.getName() + "ListFactory"));
                    typeCalls.add(clsName + getConfig().getNamingSeparator() + "SHORT_FORM, new " + factoryName + "()");
                    typeCalls.add(lclsName + getConfig().getNamingSeparator() + "SHORT_FORM, new " + lfactoryName + "()");
                }
            }
        }

        String factoryType = createElementType(file, StdStrings.MAL, null, null, "MALContextFactory");
        MethodWriter method = file.addMethodOpenStatement(false, true, StdStrings.PUBLIC, false, true, null, "init", Arrays.asList(eleFactory), throwsMALException, "Registers all aspects of this area with the provided element factory", null, Arrays.asList(throwsMALException + " If cannot initialise this helper."));
        method.addMethodStatement(convertToNamespace(factoryType + ".registerArea(" + areaNameCaps + "_AREA)"));

        if (0 < typeCalls.size()) {
            for (String typeCall : typeCalls) {
                method.addMethodStatement(createMethodCall("bodyElementFactory.registerElementFactory(" + typeCall + ")"));
            }
        }

        // register error numbers
        if ((null != area.getErrors()) && !area.getErrors().getError().isEmpty()) {
            for (ErrorDefinitionType error : area.getErrors().getError()) {
                String errorNameCaps = error.getName().toUpperCase();
                method.addMethodStatement(convertToNamespace(factoryType + ".registerError(" + errorNameCaps + "_ERROR_NUMBER, new " + identifierType + "(\"" + error.getName() + "\"))"));
            }
        }

        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, true, StdStrings.PUBLIC, false, true, null, "deepInit", Arrays.asList(eleFactory), throwsMALException, "Registers all aspects of this area with the provided element factory and any referenced areas and contained services.", null, Arrays.asList(throwsMALException + " If cannot initialise this helper."));
        method.addMethodStatement("init(bodyElementFactory)");
        for (ServiceType service : area.getService()) {
            String helperType = createElementType(file, area.getName(), service.getName(), null, service.getName() + "Helper");
            String ns = convertToNamespace(helperType + ".deepInit(bodyElementFactory)");
            method.addMethodWithDependencyStatement(ns, ns, true);
        }
        method.addMethodCloseStatement();

        file.addClassCloseStatement();

        file.flush();
    }

    protected void createServiceHelperClass(File serviceFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException {
        getLog().info("Creating service helper class: " + service.getName());
        ClassWriterProposed file = createClassFile(serviceFolder, service.getName() + "Helper");

        String serviceName = service.getName();
        String serviceVar = serviceName.toUpperCase();

        file.addPackageStatement(area, service, null);

        String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String identifierType = createElementType(file, StdStrings.MAL, null, StdStrings.IDENTIFIER);
        CompositeField eleFactory = createCompositeElementsDetails(file, false, "bodyElementFactory", TypeUtils.createTypeReference(StdStrings.MAL, null, "MALElementFactoryRegistry", false), false, true, "bodyElementFactory The element factory registry to initialise with this helper.");

        file.addClassOpenStatement(serviceName + "Helper", false, false, null, null, "Helper class for " + serviceName + " service.");

        // create error numbers
        if ((null != service.getErrors()) && !service.getErrors().getError().isEmpty()) {
            for (ErrorDefinitionType error : service.getErrors().getError()) {
                String errorNameCaps = error.getName().toUpperCase();
                CompositeField _errorNumberVar = createCompositeElementsDetails(file, false, "_" + errorNameCaps + "_ERROR_NUMBER", TypeUtils.createTypeReference(null, null, "long", false), false, true, "Error literal for error " + errorNameCaps);
                CompositeField errorNumberVar = createCompositeElementsDetails(file, false, errorNameCaps + "_ERROR_NUMBER", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UINTEGER, false), true, true, "Error instance for error " + errorNameCaps);

                file.addClassVariable(true, true, StdStrings.PUBLIC, _errorNumberVar, false, String.valueOf(error.getNumber()));
                file.addClassVariable(true, true, StdStrings.PUBLIC, errorNumberVar, false, "(_" + errorNameCaps + "_ERROR_NUMBER)");
            }
        }

        // COM service should not have its operations generated, these are generated as part of the specific services
        CompositeField _serviceNumberVar = createCompositeElementsDetails(file, false, "_" + serviceVar + "_SERVICE_NUMBER", TypeUtils.createTypeReference(null, null, "int", false), false, false, "Service number literal.");
        CompositeField serviceNumberVar = createCompositeElementsDetails(file, false, serviceVar + "_SERVICE_NUMBER", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.USHORT, false), true, false, "Service number instance.");
        CompositeField serviceNameVar = createCompositeElementsDetails(file, false, serviceVar + "_SERVICE_NAME", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, false), true, true, "Service name constant.");
        CompositeField serviceInstVar;
        if (service instanceof ExtendedServiceType) {
            serviceInstVar = createCompositeElementsDetails(file, false, serviceVar + "_SERVICE", TypeUtils.createTypeReference(StdStrings.COM, null, "COMService", false), false, true, "Service singleton instance.");
        } else {
            serviceInstVar = createCompositeElementsDetails(file, false, serviceVar + "_SERVICE", TypeUtils.createTypeReference(StdStrings.MAL, null, "MALService", false), false, true, "Service singleton instance.");
        }

        file.addClassVariable(true, true, StdStrings.PUBLIC, _serviceNumberVar, false, String.valueOf(service.getNumber()));
        file.addClassVariable(true, true, StdStrings.PUBLIC, serviceNumberVar, false, "(_" + serviceVar + "_SERVICE_NUMBER)");
        file.addClassVariable(true, true, StdStrings.PUBLIC, serviceNameVar, false, "(\"" + serviceName + "\")");

        String serviceObjectInitialValue = createServiceHelperClassInitialValue(serviceVar);
        file.addClassVariable(true, false, StdStrings.PUBLIC, serviceInstVar, true, serviceObjectInitialValue);

        for (OperationSummary op : summary.getOperations()) {
            String operationInstanceVar = op.getName().toUpperCase();
            CompositeField _opNumberVar = createCompositeElementsDetails(file, false, "_" + operationInstanceVar + "_OP_NUMBER", TypeUtils.createTypeReference(null, null, "int", false), false, false, "Operation number literal for operation " + operationInstanceVar);
            CompositeField opNumberVar = createCompositeElementsDetails(file, false, operationInstanceVar + "_OP_NUMBER", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.USHORT, false), true, false, "Operation number instance for operation " + operationInstanceVar);
            file.addClassVariable(true, true, StdStrings.PUBLIC, _opNumberVar, false, op.getNumber().toString());
            file.addClassVariable(true, true, StdStrings.PUBLIC, opNumberVar, false, "(_" + operationInstanceVar + "_OP_NUMBER)");

            List<String> opArgs = new LinkedList<>();
            addServiceHelperOperationArgs(file, op, opArgs);
            CompositeField opInstVar = createCompositeElementsDetails(file, false, operationInstanceVar + "_OP", TypeUtils.createTypeReference(StdStrings.MAL, null, getOperationInstanceType(op), false), false, true, "Operation instance for operation " + operationInstanceVar);
            file.addClassVariable(true, true, StdStrings.PUBLIC, opInstVar, false, false, opArgs);
        }

        // construct area helper class name and variable
        String hlp = createElementType(file, area.getName(), null, null, area.getName() + "Helper");
        String ns = convertToNamespace(hlp + "." + area.getName().toUpperCase() + "_AREA");

        List<String> comObjectCalls = new ArrayList();

        // auto-generate helper object for the COM extra features
        if (service instanceof ExtendedServiceType) {
            getLog().info("Added extended COM service feature for service helper class: " + service.getName());
            ExtendedServiceType eService = (ExtendedServiceType) service;

            SupportedFeatures features = eService.getFeatures();

            if (null != features) {
                if (null != features.getObjects()) {
                    for (ModelObjectType obj : features.getObjects().getObject()) {
                        createComObjectHelperDetails(file, comObjectCalls, ns, serviceVar, obj, false);
                    }
                }

                if (null != features.getEvents()) {
                    for (ModelObjectType obj : features.getEvents().getEvent()) {
                        createComObjectHelperDetails(file, comObjectCalls, ns, serviceVar, obj, true);
                    }
                }
            }
        }

        MethodWriter method = file.addMethodOpenStatement(false, true, StdStrings.PUBLIC, false, true, null, "init", Arrays.asList(eleFactory), throwsMALException, "Registers all aspects of this service with the provided element factory", null, Arrays.asList(throwsMALException + " If cannot initialise this helper."));
        addServiceConstructor(method, serviceVar, String.valueOf(area.getVersion()), summary);

        if (0 < comObjectCalls.size()) {
            for (String objectCall : comObjectCalls) {
                method.addMethodStatement(createMethodCall(serviceVar + "_SERVICE.addCOMObject(" + objectCall + "_OBJECT)"));
            }
        }

        method.addMethodWithDependencyStatement(createMethodCall(ns + ".addService(" + serviceVar + "_SERVICE)"), ns, true);

        List<String> typeCalls = new LinkedList<>();
        if ((null != service.getDataTypes()) && !service.getDataTypes().getCompositeOrEnumeration().isEmpty()) {
            for (Object oType : service.getDataTypes().getCompositeOrEnumeration()) {
                String typeName = "";
                boolean isAbstract = false;
                if (oType instanceof EnumerationType) {
                    typeName = ((EnumerationType) oType).getName();
                } else if (oType instanceof CompositeType) {
                    typeName = ((CompositeType) oType).getName();
                    isAbstract = null == ((CompositeType) oType).getShortFormPart();
                }

                if (!isAbstract) {
                    String clsName = convertClassName(createElementType(file, area.getName(), service.getName(), typeName));
                    String factoryName = convertClassName(createElementType(file, area.getName(), service.getName(), getConfig().getStructureFolder() + getConfig().getNamingSeparator() + getConfig().getFactoryFolder(), typeName + "Factory"));
                    String lclsName = convertClassName(createElementType(file, area.getName(), service.getName(), typeName + "List"));
                    String lfactoryName = convertClassName(createElementType(file, area.getName(), service.getName(), getConfig().getStructureFolder() + getConfig().getNamingSeparator() + getConfig().getFactoryFolder(), typeName + "ListFactory"));
                    typeCalls.add(clsName + getConfig().getNamingSeparator() + "SHORT_FORM, new " + factoryName + "()");
                    typeCalls.add(lclsName + getConfig().getNamingSeparator() + "SHORT_FORM, new " + lfactoryName + "()");
                }
            }
        }

        if (0 < typeCalls.size()) {
            for (String typeCall : typeCalls) {
                method.addMethodStatement(createMethodCall("bodyElementFactory.registerElementFactory(" + typeCall + ")"));
            }
        }

        // register error numbers
        if ((null != service.getErrors()) && !service.getErrors().getError().isEmpty()) {
            String factoryType = createElementType(file, StdStrings.MAL, null, null, "MALContextFactory");

            for (ErrorDefinitionType error : service.getErrors().getError()) {
                String errorNameCaps = error.getName().toUpperCase();
                method.addMethodStatement(convertToNamespace(factoryType + ".registerError(" + errorNameCaps + "_ERROR_NUMBER, new " + identifierType + "(\"" + error.getName() + "\"))"));
            }
        }

        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, true, StdStrings.PUBLIC, false, true, null, "deepInit", Arrays.asList(eleFactory), throwsMALException, "Registers all aspects of this service with the provided element factory and any referenced areas/services.", null, Arrays.asList(throwsMALException + " If cannot initialise this helper."));
        method.addMethodStatement("init(bodyElementFactory)");
        method.addMethodCloseStatement();

        file.addClassCloseStatement();

        file.flush();
    }

    protected void createComObjectHelperDetails(ClassWriterProposed file, List<String> comObjectCalls, String areaHelperObject, String serviceVar, ModelObjectType obj, boolean isEvent) throws IOException {
        String objNameCaps = obj.getName().toUpperCase();
        comObjectCalls.add(objNameCaps);

        CompositeField _objNumberVar = createCompositeElementsDetails(file, false, "_" + objNameCaps + "_OBJECT_NUMBER", TypeUtils.createTypeReference(null, null, "int", false), false, true, "Literal for object " + objNameCaps);
        CompositeField objNumberVar = createCompositeElementsDetails(file, false, objNameCaps + "_OBJECT_NUMBER", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.USHORT, false), true, true, "Instance for object " + objNameCaps);
        CompositeField objectNameVar = createCompositeElementsDetails(file, false, objNameCaps + "_OBJECT_NAME", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, false), true, true, "Object name constant.");
        CompositeField objectTypeVar = createCompositeElementsDetails(file, false, objNameCaps + "_OBJECT_TYPE", TypeUtils.createTypeReference(StdStrings.COM, null, "ObjectType", false), true, true, "Object type constant.");

        file.addClassVariableProposed(true, true, StdStrings.PUBLIC, _objNumberVar, false, String.valueOf(obj.getNumber()));
        file.addClassVariableProposed(true, true, StdStrings.PUBLIC, objNumberVar, false, "(_" + objNameCaps + "_OBJECT_NUMBER)");
        file.addClassVariableProposed(true, true, StdStrings.PUBLIC, objectNameVar, false, "(\"" + obj.getName() + "\")");
        file.addClassVariableProposed(true, true, StdStrings.PUBLIC, objectTypeVar, false, "(" + areaHelperObject + "_NUMBER, " + serviceVar + "_SERVICE_NUMBER, " + areaHelperObject + "_VERSION, " + objNameCaps + "_OBJECT_NUMBER)");

        boolean hasRelated = null != obj.getRelatedObject();
        boolean hasSource = null != obj.getSourceObject();

        String bodyShortForm = getReferenceShortForm(obj.getObjectType());
        String relatedShortForm = getReferenceShortForm(file, obj.getRelatedObject());
        String sourceShortForm = getReferenceShortForm(file, obj.getSourceObject());

        CompositeField objectInstVar = createCompositeElementsDetails(file, false, objNameCaps + "_OBJECT", TypeUtils.createTypeReference(StdStrings.COM, null, "COMObject", false), false, true, "Object instance.");
        file.addClassVariableProposed(true, false, StdStrings.PUBLIC, objectInstVar, true,
                "(" + objNameCaps + "_OBJECT_TYPE, " + objNameCaps + "_OBJECT_NAME, " + bodyShortForm + ", " + hasRelated + ", " + relatedShortForm + ", " + hasSource + ", " + sourceShortForm + ", " + isEvent + ")");
    }

    protected String getReferenceShortForm(AnyTypeReference ref) {
        String rv = null;

        if ((null != ref) && (null != ref.getAny()) && (!ref.getAny().isEmpty())) {
            List<TypeInfo> refs = TypeUtils.convertTypeReferences(this, TypeUtils.getTypeListViaXSDAny(ref.getAny()));
            rv = refs.get(0).getMalShortFormField();
        }

        return rv;
    }

    protected String getReferenceShortForm(TargetWriter file, OptionalObjectReference oor) {
        String rv = null;

        if ((null != oor) && (null != oor.getObjectType())) {
            ObjectReference any = oor.getObjectType();

            TypeKey key = new TypeKey(any.getArea(), any.getService(), String.valueOf(any.getNumber()));
            if (comObjectMap.containsKey(key)) {
                ModelObjectType refObj = comObjectMap.get(key);
                rv = convertToNamespace(createElementType(file, any.getArea(), any.getService(), null, any.getService() + "Helper")
                        + "." + refObj.getName().toUpperCase() + "_OBJECT_TYPE");
            } else {
                getLog().warn("Unknown COM object referenced: " + key);
            }
        }

        return rv;
    }

    protected void createFundamentalClass(File folder, AreaType area, ServiceType service, FundamentalType enumeration) throws IOException {
        // fundamental types are usually hand created as part of a language mapping, but we have this here in case this
        // is not the case for a particular language
    }

    protected void createEnumerationClass(File folder, AreaType area, ServiceType service, EnumerationType enumeration) throws IOException {
        String enumName = enumeration.getName();
        long enumSize = enumeration.getItem().size();

        getLog().info("Creating enumeration class " + enumName);

        ClassWriter file = createClassFile(folder, enumName);

        file.addPackageStatement(area, service, getConfig().getStructureFolder());

        file.addClassOpenStatement(enumName, true, false, createElementType(file, StdStrings.MAL, null, StdStrings.ENUMERATION), null, "Enumeration class for " + enumName + ".");

        String fqEnumName = createElementType(file, area, service, enumName);
        CompositeField elementType = createCompositeElementsDetails(file, false, "return", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.ELEMENT, false), true, true, null);
        CompositeField uintType = createCompositeElementsDetails(file, false, "return", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UINTEGER, false), true, true, null);
        CompositeField enumType = createCompositeElementsDetails(file, false, "return", TypeUtils.createTypeReference(area.getName(), null == service ? null : service.getName(), enumName, false), true, true, null);

        addTypeShortFormDetails(file, area, service, enumeration.getShortFormPart());

        if (supportsToValue) {
//      file.addStaticConstructor(createElementType(file, StdStrings.MAL, null, elementType), "create", createElementType(file, StdStrings.MAL, null, null, "MALDecoder") + " decoder", createMethodCall(convertClassName(fqEnumName) + getConfig().getNamingSeparator() + "fromInt(decoder.decodeOrdinal())"));
        } else {
//      file.addStaticConstructor(createElementType(file, StdStrings.MAL, null, elementType), "create", createElementType(file, StdStrings.MAL, null, null, "MALDecoder") + " decoder", createMethodCall(convertClassName(fqEnumName) + getConfig().getNamingSeparator() + "fromInt(decoder.decodeInteger()." + intCallMethod + "())"));
        }

        // create attributes
        String highestIndex = "";
        for (int i = 0; i < enumSize; i++) {
            Item item = enumeration.getItem().get(i);
            String value = item.getValue();

            highestIndex = "_" + value + "_INDEX";
            CompositeField _eNumberVar = createCompositeElementsDetails(file, false, highestIndex, TypeUtils.createTypeReference(null, null, "int", false), false, false, "Enumeration ordinal index for value " + value);
            CompositeField eValueVar = createCompositeElementsDetails(file, false, value + "_NUM_VALUE", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UINTEGER, false), true, false, "Enumeration numeric value for value " + value);
            CompositeField eInstVar = createCompositeElementsDetails(file, false, value, TypeUtils.createTypeReference(area.getName(), null == service ? null : service.getName(), enumName, false), true, false, "Enumeration singleton for value " + value);
            file.addClassVariable(true, true, StdStrings.PUBLIC, _eNumberVar, false, String.valueOf(i));
            file.addClassVariable(true, true, StdStrings.PUBLIC, eValueVar, false, "(" + item.getNvalue() + ")");
            file.addClassVariable(true, true, StdStrings.PUBLIC, eInstVar, true, "(" + convertToNamespace(convertClassName(fqEnumName) + "._" + value + "_INDEX)"));
        }

        // create arrays
        List<String> opStr = new LinkedList<>();
        List<String> stStr = new LinkedList<>();
        List<String> vaStr = new LinkedList<>();
        for (int i = 0; i < enumSize; i++) {
            opStr.add(enumeration.getItem().get(i).getValue());
            stStr.add("\"" + enumeration.getItem().get(i).getValue() + "\"");
            vaStr.add(enumeration.getItem().get(i).getValue() + "_NUM_VALUE");
        }
        CompositeField eInstArrVar = createCompositeElementsDetails(file, false, "_ENUMERATIONS", TypeUtils.createTypeReference(area.getName(), null == service ? null : service.getName(), enumName, false), true, true, "Set of enumeration instances.");
        file.addClassVariable(true, true, StdStrings.PRIVATE, eInstArrVar, true, true, opStr);
        if (supportsToString) {
            CompositeField eStrArrVar = createCompositeElementsDetails(file, false, "_ENUMERATION_NAMES", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.STRING, false), true, true, "Set of enumeration string values.");
            file.addClassVariable(true, true, StdStrings.PRIVATE, eStrArrVar, true, true, stStr);
        }
        CompositeField eValueArrVar = createCompositeElementsDetails(file, false, "_ENUMERATION_NUMERIC_VALUES", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UINTEGER, false), true, false, "Set of enumeration values.");
        file.addClassVariable(true, true, StdStrings.PRIVATE, eValueArrVar, true, true, vaStr);

        // create private constructor
        MethodWriter method = file.addConstructor(StdStrings.PRIVATE, enumName, createCompositeElementsDetails(file, false, "ordinal", TypeUtils.createTypeReference(null, null, "int", false), false, false, null), true, null, null, null);
        method.addMethodCloseStatement();

        if (requiresDefaultConstructors) {
            file.addConstructorDefault(enumName);
        }

        // add getters and setters
        if (supportsToString) {
            CompositeField strType = createCompositeElementsDetails(file, false, "s", TypeUtils.createTypeReference(null, null, "_String", false), false, true, "s The string to search for.");
            method = file.addMethodOpenStatement(true, false, StdStrings.PUBLIC, false, true, strType, "toString", null, null, "Returns a String object representing this type's value.", "a string representation of the value of this object", null);
            method.addMethodStatement("switch (getOrdinal())", false);
            method.addMethodStatement("{", false);
            for (Item item : enumeration.getItem()) {
                method.addMethodStatement("  case _" + item.getValue() + "_INDEX:", false);
                method.addMethodStatement("    return \"" + item.getValue() + "\"");
            }
            method.addMethodStatement("  default:", false);
            method.addMethodStatement("    throw new RuntimeException(\"Unknown ordinal!\")");
            method.addMethodStatement("}", false);
            method.addMethodCloseStatement();

            method = file.addMethodOpenStatement(false, true, StdStrings.PUBLIC, false, true, enumType, "fromString", Arrays.asList(strType), null, "Returns the enumeration element represented by the supplied string, or null if not matched.", "The matched enumeration element, or null if not matched.", null);
            method.addMethodStatement("for (int i = 0; i < _ENUMERATION_NAMES.length; i++)", false);
            method.addMethodStatement("{", false);
            method.addMethodStatement("  if (_ENUMERATION_NAMES[i].equals(s))", false);
            method.addMethodStatement("  {", false);
            method.addMethodStatement("    return _ENUMERATIONS[i]");
            method.addMethodStatement("  }", false);
            method.addMethodStatement("}", false);
            method.addMethodStatement("return null");
            method.addMethodCloseStatement();
        }

        // create getMALValue method
        if (supportsToValue) {
            method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, false, elementType, "clone", null, null);
            method.addMethodStatement("return this");
            method.addMethodCloseStatement();
        }

        CompositeField ordType = createCompositeElementsDetails(file, false, "ordinal", TypeUtils.createTypeReference(null, null, "int", false), false, false, "ordinal The index of the enumeration element to return.");
        method = file.addMethodOpenStatement(false, false, false, true, StdStrings.PUBLIC, false, false, enumType, "fromOrdinal", Arrays.asList(ordType), null, "Returns the nth element of the enumeration", "The matched enumeration element", null);
        method.addArrayMethodStatement("_ENUMERATIONS", "ordinal", highestIndex);
        method.addMethodCloseStatement();

        CompositeField nvType = createCompositeElementsDetails(file, false, "value", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UINTEGER, false), true, false, "value The numeric value to search for.");
        method = file.addMethodOpenStatement(false, false, false, true, StdStrings.PUBLIC, false, false, enumType, "fromNumericValue", Arrays.asList(nvType), null, "Returns the enumeration element represented by the supplied numeric value, or null if not matched.", "The matched enumeration value, or null if not matched.", null);
        method.addMethodStatement("for (int i = 0; i < _ENUMERATION_NUMERIC_VALUES.length; i++)", false);
        method.addMethodStatement("{", false);
        method.addMethodStatement("  if (" + getEnumValueCompare("_ENUMERATION_NUMERIC_VALUES[i]", "value") + ")", false);
        method.addMethodStatement("  {", false);
        method.addMethodStatement("    return _ENUMERATIONS[i]");
        method.addMethodStatement("  }", false);
        method.addMethodStatement("}", false);
        method.addMethodStatement("return " + getNullValue());
        method.addMethodCloseStatement();

        String enumOrdinalType = StdStrings.UINTEGER;
        if (enumSize < 256) {
            enumOrdinalType = StdStrings.UOCTET;
        } else if (enumSize < 65536) {
            enumOrdinalType = StdStrings.USHORT;
        }

        String enumEncoderValue = getEnumEncoderValue(enumSize);
        String enumDecoderValue = getEnumDecoderValue(enumSize);

        if (enumSize < 256) {
            CompositeField encodedType = createCompositeElementsDetails(file, false, "return", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UOCTET, false), true, true, null);
            method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, false, encodedType, "getOrdinalUOctet", null, null, "Returns the index of the enumerated item as a {@code UOctet}.", "the index of the enumerated item as a {@code UOctet}.", null);
            method.addMethodStatement("return " + enumEncoderValue);
            method.addMethodCloseStatement();
        }

        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, uintType, "getNumericValue", null, null, "Returns the numeric value of the enumeration element.", "The numeric value", null);
        method.addArrayMethodStatement("_ENUMERATION_NUMERIC_VALUES", "ordinal", highestIndex);
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, false, elementType, "createElement", null, null, "Returns an instance of this type using the first element of the enumeration. It is a generic factory method but just returns an existing element of the enumeration as new values of enumerations cannot be created at runtime.", "The first element of the enumeration.", null);
        method.addMethodStatement("return _ENUMERATIONS[0]");
        method.addMethodCloseStatement();

        // create encode method
        method = encodeMethodOpen(file);
        method.addMethodStatement(createMethodCall("encoder.encode") + enumOrdinalType + "(" + enumEncoderValue + ")");
        method.addMethodCloseStatement();

        // create decode method
        method = decodeMethodOpen(file, elementType);
        method.addMethodStatement("return fromOrdinal(" + createMethodCall("decoder.decode" + enumOrdinalType + "()" + enumDecoderValue + ")"));
        method.addMethodCloseStatement();

        addShortFormMethods(file);

        file.addClassCloseStatement();

        file.flush();

        createListClass(folder, area, service, enumName, false, enumeration.getShortFormPart());
        CompositeField fld = createCompositeElementsDetails(file, false, "fld", TypeUtils.createTypeReference(area.getName(), null == service ? null : service.getName(), enumName, false), true, true, "cmt");
        createFactoryClass(folder, area, service, enumName, fld, false, true);
    }

    protected void createCompositeClass(File folder, AreaType area, ServiceType service, CompositeType composite) throws IOException {
        String compName = composite.getName();

        getLog().info("Creating composite class " + compName);

        ClassWriter file = createClassFile(folder, compName);

        String parentClass = null;
        TypeReference parentType = null;
        String parentInterface = createElementType(file, StdStrings.MAL, null, StdStrings.COMPOSITE);
        if ((null != composite.getExtends())
                && (!StdStrings.MAL.equals(composite.getExtends().getType().getArea()))
                && (!StdStrings.COMPOSITE.equals(composite.getExtends().getType().getName()))) {
            parentClass = createElementType(file, composite.getExtends().getType(), true);
            parentType = composite.getExtends().getType();
            parentInterface = null;
        }

        file.addPackageStatement(area, service, getConfig().getStructureFolder());

        CompositeField elementType = createCompositeElementsDetails(file, false, "return", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.ELEMENT, false), true, true, null);

        List<CompositeField> compElements = createCompositeElementsList(file, composite);
        List<CompositeField> superCompElements = new LinkedList<>();
        createCompositeSuperElementsList(file, parentType, superCompElements);

        boolean abstractComposite = (null == composite.getShortFormPart());
        file.addClassOpenStatement(compName, !abstractComposite, abstractComposite, parentClass, parentInterface, composite.getComment());
        String fqName = createElementType(file, area, service, compName);

        if (!abstractComposite) {
            addTypeShortFormDetails(file, area, service, composite.getShortFormPart());
        }

        // create attributes
        if (!compElements.isEmpty()) {
            for (CompositeField element : compElements) {
                file.addClassVariable(false, false, StdStrings.PRIVATE, element, false, (String) null);
            }
        }

        // create blank constructor
        file.addConstructorDefault(compName);

        // if we or our parents have attributes then we need a typed constructor
        if (!compElements.isEmpty() || !superCompElements.isEmpty()) {
            List<CompositeField> superArgsList = new LinkedList<>();
            List<CompositeField> conArgsList = new LinkedList<>();

            for (CompositeField element : superCompElements) {
                superArgsList.add(element);
                conArgsList.add(element);
            }

            conArgsList.addAll(compElements);

            MethodWriter method = file.addConstructor(StdStrings.PUBLIC, compName, conArgsList, superArgsList, null, "Constructor that initialises the values of the structure.", null);

            for (CompositeField element : compElements) {
                method.addMethodStatement(createMethodCall("this." + element.getFieldName() + " = " + element.getFieldName()));
            }

            method.addMethodCloseStatement();

            // create copy constructor
            if (supportsToValue && !abstractComposite) {
                file.addConstructorCopy(fqName, compElements);
            }
        }

        if (!abstractComposite) {
            MethodWriter method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, false, elementType, "createElement", null, null, "Creates an instance of this type using the default constructor. It is a generic factory method.", "A new instance of this type with default field values.", null);
            method.addMethodStatement("return new " + convertClassName(fqName) + "()");
            method.addMethodCloseStatement();
        }

        // add getters and setters
        for (CompositeField element : compElements) {
            addGetter(file, element);
            addSetter(file, element);
        }

        // create equals method
        if (supportsEquals) {
            CompositeField boolType = createCompositeElementsDetails(file, false, "return", TypeUtils.createTypeReference(null, null, "boolean", false), false, true, "return value");
            CompositeField intType = createCompositeElementsDetails(file, false, "return", TypeUtils.createTypeReference(null, null, "int", false), false, true, "return value");
            CompositeField objType = createCompositeElementsDetails(file, false, "obj", TypeUtils.createTypeReference(null, null, "Object", false), false, true, "obj the object to compare with.");
            MethodWriter method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, boolType, "equals", Arrays.asList(objType), null, "Compares this object to the specified object. The result is true if and only if the argument is not null and is the same type that contains the same value as this object.", "true if the objects are the same; false otherwise.", null);
            method.addMethodStatement("if (obj instanceof " + compName + ")", false);
            method.addMethodStatement("{", false);
            if (null != parentClass) {
                method.addMethodStatement("  if (! super.equals(obj))", false);
                method.addMethodStatement("  {", false);
                method.addMethodStatement("    return false");
                method.addMethodStatement("  }", false);
            }
            if (!compElements.isEmpty()) {
                method.addMethodStatement("  " + compName + " other = (" + compName + ") obj");
                for (CompositeField element : compElements) {
                    method.addMethodStatement("  if (" + element.getFieldName() + " == null)", false);
                    method.addMethodStatement("  {", false);
                    method.addMethodStatement("    if (other." + element.getFieldName() + " != null)", false);
                    method.addMethodStatement("    {", false);
                    method.addMethodStatement("      return false");
                    method.addMethodStatement("    }", false);
                    method.addMethodStatement("  }", false);
                    method.addMethodStatement("  else", false);
                    method.addMethodStatement("  {", false);
                    method.addMethodStatement("    if (! " + element.getFieldName() + ".equals(other." + element.getFieldName() + "))", false);
                    method.addMethodStatement("    {", false);
                    method.addMethodStatement("      return false");
                    method.addMethodStatement("    }", false);
                    method.addMethodStatement("  }", false);
                }
            }
            method.addMethodStatement("  return true");
            method.addMethodStatement("}", false);
            method.addMethodStatement("return false");
            method.addMethodCloseStatement();

            method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, intType, "hashCode", null, null, "Returns a hash code for this object", "a hash code value for this object.", null);
            if (null != parentClass) {
                method.addMethodStatement("int hash = super.hashCode()");
            } else {
                method.addMethodStatement("int hash = 7");
            }
            for (CompositeField element : compElements) {
                method.addMethodStatement("hash = 83 * hash + (" + element.getFieldName() + " != null ? " + element.getFieldName() + ".hashCode() : 0)");
            }
            method.addMethodStatement("return hash");
            method.addMethodCloseStatement();
        }

        // create toString method
        if (supportsToString) {
            CompositeField strType = createCompositeElementsDetails(file, false, "return", TypeUtils.createTypeReference(null, null, "_String", false), false, true, "return value");
            MethodWriter method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, strType, "toString", null, null, "Returns a String object representing this type's value.", "a string representation of the value of this object", null);
            method.addMethodStatement("StringBuilder buf = new StringBuilder()");
            method.addMethodStatement("buf.append('(')");

            String prefixSeparator = "";

            if (null != parentClass) {
                method.addMethodStatement("buf.append(super.toString())");
                prefixSeparator = ", ";
            }
            for (CompositeField element : compElements) {
                method.addMethodStatement("buf.append(\"" + prefixSeparator + element.getFieldName() + "=\")");
                method.addMethodStatement("buf.append(" + element.getFieldName() + ")");
                prefixSeparator = ", ";
            }
            method.addMethodStatement("buf.append(')')");
            method.addMethodStatement("return buf.toString()");
            method.addMethodCloseStatement();
        }

        // create getMALValue method
        if (supportsToValue && !abstractComposite) {
            addCompositeCloneMethod(file, fqName);
        }

        // create encode method
        MethodWriter method = encodeMethodOpen(file);
        if (null != parentClass) {
            method.addSuperMethodStatement("encode", "encoder");
        }
        for (CompositeField element : compElements) {
            boolean isAbstract = isAbstract(element.getTypeReference()) && !element.getTypeReference().getName().contentEquals(StdStrings.ATTRIBUTE);
            if (isAbstract && !isFullyPolymorphic()) {
                getLog().error("Type " + fqName + " has field " + element.getFieldName() + " that is an abstract type, this is not supported in the current configuration.");
            } else if (isAbstract && !element.isList()) {
                method.addMethodStatement(createMethodCall("encoder.encode" + (element.isCanBeNull() ? "Nullable" : "") + "PolymorphicElement(" + element.getFieldName() + ")"));
            } else {
                if(element.getEncodeCall() != null) {
                    method.addMethodStatement(createMethodCall("encoder.encode" + (element.isCanBeNull() ? "Nullable" : "") + element.getEncodeCall() + "(" + element.getFieldName() + ")"));
                }else{
                    // This is when the Element is set as the abstract Attribute type
                    method.addMethodStatement(createMethodCall("encoder.encode" + (element.isCanBeNull() ? "Nullable" : "") + "Element(" + element.getFieldName() + ")"));
                }
            }
        }
        method.addMethodCloseStatement();

        // create decode method
        method = decodeMethodOpen(file, elementType);
        if (null != parentClass) {
            method.addSuperMethodStatement("decode", "decoder");
        }
        for (CompositeField element : compElements) {
            boolean isAbstract = isAbstract(element.getTypeReference()) && !element.getTypeReference().getName().contentEquals(StdStrings.ATTRIBUTE);
            if (isAbstract && !isFullyPolymorphic()) {
                // do nothing, already raised an error
            } else if (isAbstract && !element.isList()) {
                method.addMethodStatement(element.getFieldName() + " = " + element.getDecodeCast() + createMethodCall("decoder.decode" + (element.isCanBeNull() ? "Nullable" : "") + "PolymorphicElement()"));
            } else {
                String castString = element.getDecodeCast();
                if(castString.contains("AttributeList")) {
                    // This is when the Element is set as the abstract AttributeList type
                    method.addMethodStatement(element.getFieldName() + " = " + castString + createMethodCall("decoder.decode" + (element.isCanBeNull() ? "Nullable" : "") + element.getDecodeCall() + "(new AttributeList())"));
                }else{
                    method.addMethodStatement(element.getFieldName() + " = " + castString + createMethodCall("decoder.decode" + (element.isCanBeNull() ? "Nullable" : "") + element.getDecodeCall() + "(" + (element.isDecodeNeedsNewCall() ? element.getNewCall() : "") + ")"));
                }
            }
        }
        method.addMethodStatement("return this");
        method.addMethodCloseStatement();

        if (!abstractComposite) {
            addShortFormMethods(file);
        }

        file.addClassCloseStatement();

        file.flush();

        createListClass(folder, area, service, compName, abstractComposite, composite.getShortFormPart());

        if (!abstractComposite) {
            CompositeField fld = createCompositeElementsDetails(file, false, "fld", TypeUtils.createTypeReference(area.getName(), null == service ? null : service.getName(), compName, false), true, true, "cmt");
            createFactoryClass(folder, area, service, compName, fld, false, false);
        }
    }

    protected abstract void createListClass(File folder, AreaType area, ServiceType service, String srcTypeName, boolean isAbstract, Long shortFormPart) throws IOException;

    protected void createFactoryClass(File structureFolder, AreaType area, ServiceType service, String srcTypeName, CompositeField typeDetails, boolean isAttr, boolean isEnum) throws IOException {
        // create area structure folder
        File folder = StubUtils.createFolder(structureFolder, getConfig().getFactoryFolder());
        // create a comment for the structure factory folder if supported
        createStructureFactoryFolderComment(folder, area, service);

        String factoryName = srcTypeName + "Factory";

        getLog().info("Creating factory class " + factoryName);

        ClassWriter file = createClassFile(folder, factoryName);

        file.addPackageStatement(area, service, getConfig().getStructureFolder() + "." + getConfig().getFactoryFolder());

        file.addClassOpenStatement(factoryName, true, false, null, createElementType(file, StdStrings.MAL, null, null, "MALElementFactory"), "Factory class for " + srcTypeName + ".");

        CompositeField elementType = createCompositeElementsDetails(file, false, "return", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.ELEMENT, false), true, true, null);
        MethodWriter method = file.addMethodOpenStatement(true, false, StdStrings.PUBLIC, false, false, elementType, "createElement", null, null, "Creates an instance of the source type using the default constructor. It is a generic factory method.", "A new instance of the source type with default field values.", null);
        if (isAttr) {
            AttributeTypeDetails details = getAttributeDetails(area.getName(), srcTypeName);
            if (details.isNativeType()) {
                method.addMethodStatement("return new " + convertClassName(createElementType(file, StdStrings.MAL, null, StdStrings.UNION)) + "(" + details.getDefaultValue() + ")");
            } else {
                method.addMethodStatement("return new " + createElementType(file, area, service, srcTypeName) + "()");
            }
        } else {
            file.addTypeDependency(typeDetails.getTypeName());
            method.addMethodStatement("return " + typeDetails.getNewCall());
        }
        method.addMethodCloseStatement();

        file.addClassCloseStatement();

        file.flush();
    }

    protected final void createMultiReturnType(String destinationFolderName, String returnTypeFqName, MultiReturnType returnTypeInfo) throws IOException {
        getLog().info("Creating multiple return class class " + returnTypeFqName);

        // create a comment for the body folder if supported
        createServiceMessageBodyFolderComment(destinationFolderName, returnTypeInfo.getArea(), returnTypeInfo.getService());

        ClassWriter file = createClassFile(destinationFolderName, returnTypeFqName.replace('.', '/'));

        file.addPackageStatement(returnTypeInfo.getArea(), returnTypeInfo.getService(), getConfig().getBodyFolder());

        file.addClassOpenStatement(returnTypeInfo.getShortName(), true, false, null, null, "Multi body return class for " + returnTypeInfo.getShortName() + ".");

        List<CompositeField> argsList = createOperationArguments(getConfig(), file, returnTypeInfo.getReturnTypes());

        // create attributes
        for (int i = 0; i < argsList.size(); i++) {
            CompositeField argType = argsList.get(i);
            CompositeField memType = createCompositeElementsDetails(file, true, "bodyElement" + i, argType.getTypeReference(), true, true, argType.getFieldName() + ": " + argType.getComment());
            file.addClassVariable(false, false, StdStrings.PRIVATE, memType, false, (String) null);
        }

        // create blank constructor
        file.addConstructorDefault(returnTypeInfo.getShortName());

        // if we or our parents have attributes then we need a typed constructor
        MethodWriter method = file.addConstructor(StdStrings.PUBLIC, returnTypeInfo.getShortName(), argsList, null, null, "Constructs an instance of this type using provided values.", null);

        for (int i = 0; i < argsList.size(); i++) {
            CompositeField argType = argsList.get(i);
            method.addMethodStatement(createMethodCall("this.bodyElement" + i + " = " + argType.getFieldName()));
        }

        method.addMethodCloseStatement();

        // add getters and setters
        for (int i = 0; i < argsList.size(); i++) {
            CompositeField argType = createCompositeElementsDetails(file, true, "bodyElement" + i, returnTypeInfo.getReturnTypes().get(i).getSourceType(), true, true, "__newValue The new value");
            addGetter(file, argType);
            addSetter(file, argType);
        }

        file.addClassCloseStatement();

        file.flush();
    }

    protected void addTypeShortFormDetails(ClassWriter file, AreaType area, ServiceType service, long sf) throws IOException {
        addTypeShortForm(file, sf);
        CompositeField areaSfVar = createCompositeElementsDetails(file, false, "AREA_SHORT_FORM", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.USHORT, false), true, false, "Short form for area.");
        CompositeField areaVVar = createCompositeElementsDetails(file, false, "AREA_VERSION", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UOCTET, false), true, false, "Version for area.");
        file.addClassVariable(true, true, StdStrings.PUBLIC, areaSfVar, false, "(" + area.getNumber() + ")");
        file.addClassVariable(true, true, StdStrings.PUBLIC, areaVVar, false, "((short)" + area.getVersion() + ")");

        long asf = ((long) area.getNumber()) << AREA_BIT_SHIFT;
        asf += ((long) area.getVersion()) << VERSION_BIT_SHIFT;
        if (0 <= sf) {
            asf += sf;
        } else {
            asf += Long.parseLong(Integer.toHexString((int) sf).toUpperCase().substring(2), 16);
        }
        if (null != service) {
            CompositeField serviceSfVar = createCompositeElementsDetails(file, false, "SERVICE_SHORT_FORM", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.USHORT, false), true, false, "Short form for service.");
            file.addClassVariable(true, true, StdStrings.PUBLIC, serviceSfVar, false, "(" + service.getNumber() + ")");
            asf += ((long) service.getNumber()) << SERVICE_BIT_SHIFT;
        } else {
            CompositeField serviceSfVar = createCompositeElementsDetails(file, false, "SERVICE_SHORT_FORM", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.USHORT, false), true, false, "Short form for service.");
            file.addClassVariable(true, true, StdStrings.PUBLIC, serviceSfVar, false, "(0)");
        }

        addShortForm(file, asf);
    }

    protected void addTypeShortForm(ClassWriter file, long sf) throws IOException {
        CompositeField var = createCompositeElementsDetails(file, false, "TYPE_SHORT_FORM", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.INTEGER, false), true, false, "Short form for type.");
        file.addClassVariable(true, true, StdStrings.PUBLIC, var, false, "(" + sf + ")");
    }

    protected void addShortForm(ClassWriter file, long sf) throws IOException {
        CompositeField var = createCompositeElementsDetails(file, false, "SHORT_FORM", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.LONG, false), true, false, "Absolute short form for type.");
        file.addClassVariable(true, true, StdStrings.PUBLIC, var, false, "(" + sf + "L)");
    }

    protected void addShortFormMethods(ClassWriter file) throws IOException {
        CompositeField lonType = createCompositeElementsDetails(file, false, "return", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.LONG, false), true, true, null);
        CompositeField intType = createCompositeElementsDetails(file, false, "return", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.INTEGER, false), true, true, null);
        CompositeField ustType = createCompositeElementsDetails(file, false, "return", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.USHORT, false), true, true, null);
        CompositeField uocType = createCompositeElementsDetails(file, false, "return", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UOCTET, false), true, true, null);

        MethodWriter method = file.addMethodOpenStatement(true, false, StdStrings.PUBLIC, false, true, lonType, "getShortForm", null, null, "Returns the absolute short form of this type.", "The absolute short form of this type.", null);
        method.addMethodStatement("return SHORT_FORM");
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(true, false, StdStrings.PUBLIC, false, true, intType, "getTypeShortForm", null, null, "Returns the type short form of this type which is unique to the area/service it is defined in but not unique across all types.", "The type short form of this type.", null);
        method.addMethodStatement("return TYPE_SHORT_FORM");
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(true, false, StdStrings.PUBLIC, false, true, ustType, "getAreaNumber", null, null, "Returns the area number of this type.", "The area number of this type.", null);
        method.addMethodStatement("return AREA_SHORT_FORM");
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(true, false, StdStrings.PUBLIC, false, true, uocType, "getAreaVersion", null, null, "Returns the area version of this type.", "The area number of this type.", null);
        method.addMethodStatement("return AREA_VERSION");
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(true, false, StdStrings.PUBLIC, false, true, ustType, "getServiceNumber", null, null, "Returns the service number of this type.", "The service number of this type.", null);
        method.addMethodStatement("return SERVICE_SHORT_FORM");
        method.addMethodCloseStatement();
    }

    protected static void addGetter(ClassWriter file, CompositeField element) throws IOException {
        String getOpPrefix = "get";
        String attributeName = element.getFieldName();
        String getOpName = StubUtils.preCap(attributeName);

        MethodWriter method = file.addMethodOpenStatement(true, false, StdStrings.PUBLIC, !element.isCanBeNull(), !element.isCanBeNull() && element.isActual(), element, getOpPrefix + getOpName, null, null, "Returns the field " + attributeName, "The field " + attributeName, null);
        method.addMethodStatement("return " + attributeName);
        method.addMethodCloseStatement();
    }

    protected static void addSetter(ClassWriter file, CompositeField element) throws IOException {
        String attributeName = element.getFieldName();
        String getOpName = StubUtils.preCap(attributeName);

        if (StdStrings.BOOLEAN.equals(element.getTypeName()) && getOpName.startsWith("Is")) {
            getOpName = getOpName.substring(2);
        }

        CompositeField fld = new CompositeField(element, "__newValue", "__newValue The new value");
        MethodWriter method = file.addMethodOpenStatement(false, false, false, false, StdStrings.PUBLIC, false, true, null, "set" + getOpName, Arrays.asList(fld), null, "Sets the field " + attributeName, null, null);
        method.addMethodStatement(attributeName + " = __newValue");
        method.addMethodCloseStatement();
    }

    protected void addCompositeCloneMethod(ClassWriter file, String fqName) throws IOException {
    }

    protected CompositeField createServiceProviderSkeletonSendHandler(ClassWriter file, String argumentName, String argumentComment) {
        return createCompositeElementsDetails(file, false, argumentName, TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, StdStrings.MALINTERACTION, false), false, true, argumentComment);
    }

    protected void addServiceHelperOperationArgs(LanguageWriter file, OperationSummary op, List<String> opArgs) {
        opArgs.add(op.getName().toUpperCase() + "_OP_NUMBER");
        opArgs.add("new " + createElementType(file, StdStrings.MAL, null, StdStrings.IDENTIFIER) + "(\"" + op.getName() + "\")");
        opArgs.add("" + op.getReplay());
        opArgs.add("new " + createElementType(file, StdStrings.MAL, null, StdStrings.USHORT) + "(" + op.getSet() + ")");

        switch (op.getPattern()) {
            case SEND_OP:
                addMalTypes(this, opArgs, 1, op.getArgTypes(), false);
                break;
            case SUBMIT_OP:
                addMalTypes(this, opArgs, 1, op.getArgTypes(), false);
                break;
            case REQUEST_OP:
                addMalTypes(this, opArgs, 1, op.getArgTypes(), false);
                addMalTypes(this, opArgs, 2, op.getRetTypes(), false);
                break;
            case INVOKE_OP:
                addMalTypes(this, opArgs, 1, op.getArgTypes(), false);
                addMalTypes(this, opArgs, 2, op.getAckTypes(), false);
                addMalTypes(this, opArgs, 3, op.getRetTypes(), false);
                break;
            case PROGRESS_OP:
                addMalTypes(this, opArgs, 1, op.getArgTypes(), false);
                addMalTypes(this, opArgs, 2, op.getAckTypes(), false);
                addMalTypes(this, opArgs, 3, op.getUpdateTypes(), false);
                addMalTypes(this, opArgs, 4, op.getRetTypes(), false);
                break;
            case PUBSUB_OP:
                addMalTypes(this, opArgs, 1, op.getRetTypes(), true);
                break;
        }
    }

    protected void addMalTypes(TypeInformation tiSource, List<String> opArgs, int index, List<TypeInfo> ti, boolean isPubSub) {
        ArrayList<String> typeArgs = new ArrayList<>();
        boolean needXmlSchema = false;
        boolean needMalTypes = false;
        boolean finalTypeIsAttribute = false;
        boolean finalTypeIsList = false;

        for (TypeInfo typeInfo : ti) {
            if (StdStrings.XML.equals(typeInfo.getSourceType().getArea())) {
                needXmlSchema = true;
            } else {
                needMalTypes = true;
            }

            if (tiSource.isAbstract(typeInfo.getSourceType())) {
                typeArgs.add("null");

                if (StdStrings.ATTRIBUTE.equals(typeInfo.getSourceType().getName())) {
                    finalTypeIsAttribute = true;
                    finalTypeIsList = typeInfo.getSourceType().isList();
                }
            } else {
                finalTypeIsAttribute = false;

                if (isPubSub) {
                    // this is a bit of a hack for now
                    if (tiSource.isAttributeNativeType(typeInfo.getSourceType()) || tiSource.isAttributeType(typeInfo.getSourceType())) {
                        TypeReference tr = typeInfo.getSourceType();
                        tr.setList(true);
                        TypeInfo lti = TypeUtils.convertTypeReference(tiSource, tr);
                        typeArgs.add(lti.getMalShortFormField());
                        tr.setList(false);
                    } else {
                        typeArgs.add(typeInfo.getMalShortFormField().substring(0, typeInfo.getMalShortFormField().length() - 11) + "List.SHORT_FORM");
                    }
                } else {
                    typeArgs.add(typeInfo.getMalShortFormField());
                }
            }
        }

        if (needMalTypes && needXmlSchema) {
            throw new IllegalArgumentException("WARNING: Service specification uses multiple type specifications in the same message! This is not supported.");
        }

        String shortFormType = (needXmlSchema ? StdStrings.STRING : StdStrings.LONG);
        String arrayArgs = StubUtils.concatenateStringArguments(false, typeArgs.toArray(new String[0]));
        String polyArgs = "";
        if (finalTypeIsAttribute) {
            Set<String> attribArgs = new HashSet<>();

            for (Map.Entry<TypeKey, AttributeTypeDetails> val : getAttributeTypesMap().entrySet()) {
                TypeReference tr = new TypeReference();
                tr.setArea(StdStrings.MAL);
                tr.setName(val.getValue().getMalType());
                if (!isAbstract(tr)) {
                    tr.setList(finalTypeIsList);
                    TypeInfo lti = TypeUtils.convertTypeReference(this, tr);
                    attribArgs.add(lti.getMalShortFormField());
                }
            }
            polyArgs = StubUtils.concatenateStringArguments(false, attribArgs.toArray(new String[0]));
        }

        if (isPubSub) {
            opArgs.add("new " + shortFormType + "[] {" + arrayArgs + "}, new " + shortFormType + "[0]");
        } else {
            opArgs.add("new org.ccsds.moims.mo.mal.MALOperationStage(new org.ccsds.moims.mo.mal.structures.UOctet((short) " + index + "), new " + shortFormType + "[] {" + arrayArgs + "}, new " + shortFormType + "[] {" + polyArgs + "})");
        }
    }

    protected String createAdapterMethodsArgs(List<TypeInfo> typeInfos, String argNamePrefix, boolean precedingArgs, boolean moreArgs) {
        StringBuilder buf = new StringBuilder();

        if (null != typeInfos) {
            for (int i = 0; i < typeInfos.size(); i++) {
                TypeInfo ti = typeInfos.get(i);

                buf.append(createAdapterMethodsArgs(ti, argNamePrefix, i, precedingArgs || (i > 0), moreArgs && i == (typeInfos.size() - 1)));
            }
        }

        return buf.toString();
    }

    protected String createAdapterMethodsArgs(TypeInfo ti, String argName, int argIndex, boolean precedingArgs, boolean moreArgs) {
        String retStr = "";

        if ((null != ti.getTargetType()) && !(StdStrings.VOID.equals(ti.getTargetType()))) {
            if (precedingArgs) {
                retStr = ", ";
            }

            if (ti.isNativeType()) {
                AttributeTypeDetails details = getAttributeDetails(ti.getSourceType());
                String av = argName + createMethodCall(".getBodyElement(") + argIndex + ", new " + getConfig().getAreaPackage(StdStrings.MAL) + "mal." + getConfig().getStructureFolder() + "." + StdStrings.UNION + "(" + details.getDefaultValue() + "))";
                retStr += "(" + av + " == null) ? null : ((" + getConfig().getAreaPackage(StdStrings.MAL) + "mal." + getConfig().getStructureFolder() + "." + StdStrings.UNION + ") " + av + ").get" + details.getMalType() + "Value()";
            } else {
                String ct = ti.getTargetType();
                String at = null;
                if (!isAbstract(ti.getSourceType())) {
                    CompositeField ce = createCompositeElementsDetails(null, false, "", ti.getSourceType(), true, true, null);
                    at = ce.getNewCall();
                }

                String av = argName + createMethodCall(".getBodyElement(") + argIndex + ", " + at + ")";
                retStr += "(" + ct + ") " + av;
            }

            if (moreArgs) {
                retStr += ", ";
            }
        }

        return retStr;
    }

    protected String checkForReservedWords(String arg) {
        if (null != arg) {
            String replacementWord = reservedWordsMap.get(arg);
            if (null != replacementWord) {
                return replacementWord;
            }
        }

        return arg;
    }

    protected String createConsumerPatternCall(OperationSummary op) {
        switch (op.getPattern()) {
            case SEND_OP:
                return "send";
            case SUBMIT_OP:
                return "submit";
            case REQUEST_OP:
                return "request";
            case INVOKE_OP:
                return "invoke";
            case PROGRESS_OP:
                return "progress";
        }

        return null;
    }

    protected String getOperationInstanceType(OperationSummary op) {
        switch (op.getPattern()) {
            case SEND_OP:
                return getConfig().getSendOperationType();
            case SUBMIT_OP:
                return getConfig().getSubmitOperationType();
            case REQUEST_OP:
                return getConfig().getRequestOperationType();
            case INVOKE_OP:
                return getConfig().getInvokeOperationType();
            case PROGRESS_OP:
                return getConfig().getProgressOperationType();
            case PUBSUB_OP:
                return getConfig().getPubsubOperationType();
        }

        return null;
    }

    protected CompositeField createOperationReturnType(LanguageWriter file, AreaType area, ServiceType service, OperationSummary op) {
        switch (op.getPattern()) {
            case REQUEST_OP: {
                if (null != op.getRetTypes()) {
                    return createReturnReference(createReturnType(file, area, service, op.getName(), "Response", op.getRetTypes()));
                }
                break;
            }
            case INVOKE_OP:
            case PROGRESS_OP: {
                if ((null != op.getAckTypes()) && (0 < op.getAckTypes().size())) {
                    return createReturnReference(createReturnType(file, area, service, op.getName(), "Ack", op.getAckTypes()));
                }
                break;
            }
        }

        return null;
    }

    protected List<CompositeField> createOperationArguments(GeneratorConfiguration config, LanguageWriter file, List<TypeInfo> opArgs) {
        return createOperationArguments(config, file, opArgs, false);
    }

    protected List<CompositeField> createOperationArguments(GeneratorConfiguration config, LanguageWriter file, List<TypeInfo> opArgs, boolean forceList) {
        List<CompositeField> rv = new LinkedList<>();

        if (null != opArgs) {
            for (int i = 0; i < opArgs.size(); i++) {
                TypeInfo ti = opArgs.get(i);
                TypeReference tir = ti.getSourceType();
                if (forceList) {
                    TypeReference tir_new = new TypeReference();
                    tir_new.setArea(tir.getArea());
                    tir_new.setService(tir.getService());
                    tir_new.setName(tir.getName());
                    tir_new.setList(true);

                    tir = tir_new;
                }

                String argName;
                if (null != ti.getFieldName()) {
                    argName = ti.getFieldName();
                } else {
                    argName = "_" + TypeUtils.shortTypeName(config.getNamingSeparator(), ti.getTargetType()) + i;
                }

                String cmt = argName + " Argument number " + i + " as defined by the service operation";
                if ((null != ti.getFieldName()) && (null != ti.getFieldComment())) {
                    cmt = ti.getFieldComment();
                }

                CompositeField argType = createCompositeElementsDetails(file, true, argName, tir, true, true, cmt);

                rv.add(argType);
            }
        }

        return rv;
    }

    private void createOperationReturn(LanguageWriter file, MethodWriter method, OperationSummary op, CompositeField opRetType) throws IOException {
        List<TypeInfo> targetTypes = op.getRetTypes();

        if ((InteractionPatternEnum.INVOKE_OP == op.getPattern()) || (InteractionPatternEnum.PROGRESS_OP == op.getPattern())) {
            targetTypes = op.getAckTypes();
        }

        if ((null != targetTypes) && (0 < targetTypes.size())) {
            if (1 == targetTypes.size()) {
                if (targetTypes.get(0).isNativeType()) {
                    method.addMethodStatement("return " + createOperationArgReturn(file, method, targetTypes.get(0), "body", 0));
                } else {
                    method.addMethodStatement("return " + createOperationArgReturn(file, method, targetTypes.get(0), "body", 0));
                }
            } else {
                StringBuilder buf = new StringBuilder();

                for (int i = 0; i < targetTypes.size(); i++) {
                    TypeInfo ti = targetTypes.get(i);
                    if (i > 0) {
                        buf.append(", ");
                    }
                    buf.append(createOperationArgReturn(file, method, ti, "body", i));
                }

                method.addMethodStatement("return new " + opRetType.getTypeName() + "(" + buf.toString() + ")");
            }
        }
    }

    protected String createOperationArgReturn(LanguageWriter file, MethodWriter method, TypeInfo ti, String argName, int argIndex) throws IOException {
        if ((null != ti.getTargetType()) && !(StdStrings.VOID.equals(ti.getTargetType()))) {
            String eleType = "Object";
            String tv = argName + argIndex;
            String av;
            String rv;
            if (ti.isNativeType()) {
                AttributeTypeDetails details = getAttributeDetails(ti.getSourceType());
                av = argName + ".getBodyElement(" + argIndex + ", new " + createElementType(file, StdStrings.MAL, null, StdStrings.UNION) + "(" + details.getDefaultValue() + "))";
                rv = "(" + tv + " == null) ? null : ((" + createElementType(file, StdStrings.MAL, null, StdStrings.UNION) + ") " + tv + ").get" + details.getMalType() + "Value()";
            } else {
                String ct = ti.getTargetType();
                String at = null;
                if (!isAbstract(ti.getSourceType())) {
                    CompositeField ce = createCompositeElementsDetails(null, false, "", ti.getSourceType(), true, true, null);
                    at = ce.getNewCall();
                }

                av = argName + ".getBodyElement(" + argIndex + ", " + at + ")";
                rv = "(" + ct + ") " + tv;
            }

            method.addMethodStatement(eleType + " " + tv + " = (" + eleType + ") " + av);
            return rv;
        }

        return "";
    }

    protected CompositeField createReturnType(LanguageWriter file, AreaType area, ServiceType service, String opName, String messageType, List<TypeInfo> returnTypes) {
        if ((null != returnTypes) && (0 < returnTypes.size())) {
            if (1 == returnTypes.size()) {
                return createCompositeElementsDetails(file, false, "return", returnTypes.get(0).getSourceType(), true, true, null);
            } else {
                String shortName = StubUtils.preCap(opName) + messageType;
                String rt = getConfig().getAreaPackage(area.getName()) + area.getName().toLowerCase() + "." + service.getName().toLowerCase() + "." + getConfig().getBodyFolder() + "." + shortName;
                if (!multiReturnTypeMap.containsKey(rt)) {
                    multiReturnTypeMap.put(rt, new MultiReturnType(rt, area, service, shortName, returnTypes));
                }

                return createCompositeElementsDetails(file, false, "return", TypeUtils.createTypeReference(area.getName().toLowerCase(), service.getName().toLowerCase() + "." + getConfig().getBodyFolder(), shortName, false), false, true, null);
            }
        }

        return null;
    }

    /**
     * Creates a set of argument names based on the type, wrapping the type in a
     * Union if a native type.
     *
     * @param typeNames The list of arguments.
     * @return The argument string.
     */
    public String createArgNameOrNull(List<TypeInfo> typeNames) {
        if ((null != typeNames) && (!typeNames.isEmpty())) {
            StringBuilder buf = new StringBuilder();
            for (int i = 0; i < typeNames.size(); i++) {
                TypeInfo ti = typeNames.get(i);
                if (i > 0) {
                    buf.append(", ");
                }

                String argName;
                if (null != ti.getFieldName()) {
                    argName = ti.getFieldName();
                } else {
                    argName = "_" + TypeUtils.shortTypeName(getConfig().getNamingSeparator(), ti.getTargetType()) + i;
                }

                if (ti.isNativeType()) {
                    buf.append("(");
                    buf.append(argName);
                    buf.append(" == null) ? null : new ");
                    buf.append(getConfig().getAreaPackage(StdStrings.MAL));
                    buf.append("mal.");
                    buf.append(getConfig().getStructureFolder());
                    buf.append(".");
                    buf.append(StdStrings.UNION);
                    buf.append("(");
                    buf.append(argName);
                    buf.append(")");
                } else {
                    buf.append(argName);
                }
            }

            return buf.toString();
        }

        return getConfig().getNullValue();
    }

    protected MethodWriter encodeMethodOpen(ClassWriter file) throws IOException {
        String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        CompositeField fld = createCompositeElementsDetails(file, false, "encoder", TypeUtils.createTypeReference(StdStrings.MAL, null, "MALEncoder", false), false, true, "encoder - the encoder to use for encoding");
        return file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, null, "encode", Arrays.asList(fld), throwsMALException, "Encodes the value of this object using the provided MALEncoder.", null, Arrays.asList(throwsMALException + " if any encoding errors are detected."));
    }

    protected MethodWriter decodeMethodOpen(ClassWriter file, CompositeField elementType) throws IOException {
        String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        CompositeField fld = createCompositeElementsDetails(file, false, "decoder", TypeUtils.createTypeReference(StdStrings.MAL, null, "MALDecoder", false), false, true, "decoder - the decoder to use for decoding");
        return file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, false, elementType, "decode", Arrays.asList(fld), throwsMALException, "Decodes the value of this object using the provided MALDecoder.", "Returns this object.", Arrays.asList(throwsMALException + " if any decoding errors are detected."));
    }

    protected String createProviderSkeletonHandlerSwitch() {
        return "interaction.getOperation().getNumber().getValue()";
    }

    protected CompositeField createReturnReference(CompositeField targetType) {
        return targetType;
    }

    protected String createMethodCall(String call) {
        return call;
    }

    protected void createAreaFolderComment(File structureFolder, AreaType area) throws IOException {
    }

    protected void createServiceFolderComment(File structureFolder, AreaType area, ServiceType service) throws IOException {
    }

    protected void createAreaStructureFolderComment(File structureFolder, AreaType area) throws IOException {
    }

    protected void createServiceConsumerFolderComment(File structureFolder, AreaType area, ServiceType service) throws IOException {
    }

    protected void createServiceProviderFolderComment(File structureFolder, AreaType area, ServiceType service) throws IOException {
    }

    protected void createServiceMessageBodyFolderComment(String baseFolder, AreaType area, ServiceType service) throws IOException {
    }

    protected void createServiceStructureFolderComment(File structureFolder, AreaType area, ServiceType service) throws IOException {
    }

    protected void createStructureFactoryFolderComment(File structureFolder, AreaType area, ServiceType service) throws IOException {
    }

    protected abstract void addServiceConstructor(MethodWriter method, String serviceVar, String serviceVersion, ServiceSummary summary) throws IOException;

    protected abstract String createAreaHelperClassInitialValue(String areaVar, short areaVersion);

    protected abstract String createServiceHelperClassInitialValue(String serviceVar);

    protected abstract void createRequiredPublisher(String destinationFolderName, String fqPublisherName, RequiredPublisher op) throws IOException;

    protected abstract void addVectorAddStatement(LanguageWriter file, MethodWriter method, String variable, String parameter) throws IOException;

    protected abstract void addVectorRemoveStatement(LanguageWriter file, MethodWriter method, String variable, String parameter) throws IOException;

    protected abstract String createStaticClassReference(String type);

    protected abstract String addressOf(String object);

    protected abstract String createArraySize(boolean isActual, String type, String variable);

    protected abstract String malStringAsElement(LanguageWriter file);

    protected abstract String errorCodeAsReference(LanguageWriter file, String ref);

    protected abstract String getIntCallMethod();

    protected abstract String getOctetCallMethod();

    protected abstract String getRegisterMethodName();

    protected abstract String getDeregisterMethodName();

    protected abstract String getEnumValueCompare(String lhs, String rhs);

    protected abstract String getEnumEncoderValue(long maxValue);

    protected abstract String getEnumDecoderValue(long maxValue);

    protected abstract String getNullValue();

    protected abstract ClassWriterProposed createClassFile(File folder, String className) throws IOException;

    protected abstract ClassWriter createClassFile(String destinationFolderName, String className) throws IOException;

    protected abstract InterfaceWriter createInterfaceFile(File folder, String className) throws IOException;

    protected abstract InterfaceWriter createInterfaceFile(String destinationFolderName, String className) throws IOException;
}
