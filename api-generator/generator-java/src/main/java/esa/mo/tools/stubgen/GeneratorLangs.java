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

import esa.mo.tools.stubgen.java.JavaServiceInfo;
import esa.mo.tools.stubgen.java.JavaExceptions;
import esa.mo.tools.stubgen.java.JavaConsumer;
import esa.mo.tools.stubgen.java.JavaEnumerations;
import esa.mo.tools.stubgen.java.JavaHelpers;
import esa.mo.tools.stubgen.specification.AttributeTypeDetails;
import esa.mo.tools.stubgen.specification.CompositeField;
import esa.mo.tools.stubgen.specification.InteractionPatternEnum;
import esa.mo.tools.stubgen.specification.MultiReturnType;
import esa.mo.tools.stubgen.specification.OperationSummary;
import esa.mo.tools.stubgen.specification.ServiceSummary;
import esa.mo.tools.stubgen.specification.StdStrings;
import esa.mo.tools.stubgen.specification.TypeInfo;
import esa.mo.tools.stubgen.specification.TypeUtils;
import esa.mo.tools.stubgen.writers.ClassWriter;
import esa.mo.tools.stubgen.writers.InterfaceWriter;
import esa.mo.tools.stubgen.writers.LanguageWriter;
import esa.mo.tools.stubgen.writers.MethodWriter;
import esa.mo.tools.stubgen.writers.TargetWriter;
import esa.mo.xsd.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
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
     * The bit shift value for the area part of a Type Id.
     */
    public static final int AREA_BIT_SHIFT = 48;
    /**
     * The bit shift value for the service part of a Type Id.
     */
    public static final int SERVICE_BIT_SHIFT = 32;
    /**
     * The bit shift value for the version part of a Type Id.
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

    public boolean supportsToString;
    public boolean supportsEquals;
    public boolean requiresDefaultConstructors;
    public boolean supportsToValue;
    private boolean supportsAsync;
    private boolean generateStructures;
    protected final Log logger;

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
        super(config);

        this.supportsToString = supportsToString;
        this.supportsEquals = supportsEquals;
        this.supportsToValue = supportsToValue;
        this.supportsAsync = supportsAsync;
        this.requiresDefaultConstructors = requiresDefaultConstructors;
        this.logger = logger;
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
        addAttributeType(StdStrings.XML, "ObjectRef", getAttributeDetails(StdStrings.MAL, StdStrings.OBJECTREF));

        AttributeTypeDetails att = new AttributeTypeDetails(this, "Element", true, "Object", "");
        addAttributeType(StdStrings.XML, "Element", att);

        AttributeTypeDetails att1 = new AttributeTypeDetails(this, "Object", true, "Object", "");
        addAttributeType(StdStrings.XML, "Element", att1);
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
    public void compile(String destinationFolderName, SpecificationType spec,
            JAXBElement rootNode) throws IOException, JAXBException {
        long totalTime = System.currentTimeMillis();

        for (AreaType area : spec.getArea()) {
            long timestamp = System.currentTimeMillis();
            processArea(destinationFolderName, area, requiredPublishers);
            timestamp = System.currentTimeMillis() - timestamp;
            logger.info("-----------");
            logger.info("Processed " + area.getName() + " area in " + timestamp + " ms");
            logger.info("-----------");
        }

        for (Map.Entry<String, MultiReturnType> entry : multiReturnTypeMap.entrySet()) {
            String string = entry.getKey();
            MultiReturnType rt = entry.getValue();
            createMultiReturnType(destinationFolderName, string, rt);
        }

        logger.info("-----------");
        totalTime = System.currentTimeMillis() - totalTime;
        logger.info("Processed all Areas in " + totalTime + " ms");
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
     * Does the generator support an equals method on structures.
     *
     * @return the supportsEquals
     */
    public boolean isSupportsEquals() {
        return supportsEquals;
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
     * Does the generator need to generate async operation methods.
     *
     * @return the supportsAsync
     */
    public boolean isSupportsAsync() {
        return supportsAsync;
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
     * To be used by derived generators to add an entry to the reserved word
     * map.
     *
     * @param word The word to look for.
     * @param replacement The replacement to use.
     */
    protected void addReservedWord(String word, String replacement) {
        reservedWordsMap.put(word, replacement);
    }

    protected void processArea(String destinationFolderName, AreaType area,
            Map<String, RequiredPublisher> requiredPublishers) throws IOException {
        if ((!area.getName().equalsIgnoreCase(StdStrings.COM)) || (generateCOM())) {
            logger.info("Processing area: " + area.getName());

            // create folder
            File destinationFolder = StubUtils.createFolder(new File(destinationFolderName),
                    getConfig().getAreaPackage(area.getName()).replace('.', '/'));
            final File areaFolder = StubUtils.createFolder(destinationFolder, area.getName());

            ConcurrentLinkedQueue<Exception> errors_2 = new ConcurrentLinkedQueue<>();
            Thread t1 = new Thread() {
                @Override
                public void run() {
                    // create services
                    for (ServiceType service : area.getService()) {
                        try {
                            processService(areaFolder, area, service, requiredPublishers);
                        } catch (IOException ex) {
                            errors_2.add(ex);
                        }
                    }
                }
            };

            t1.start();

            // Create a comment for the area folder if supported
            createAreaFolderComment(areaFolder, area);

            // Create Area Helper
            JavaHelpers helper = new JavaHelpers(this);
            logger.info(" > Creating Area Helper class: " + area.getName());
            helper.createAreaHelperClass(areaFolder, area);

            // Create Area Exceptions
            JavaExceptions exceptions = new JavaExceptions(this);
            logger.info(" > Creating Area Exceptions for area: " + area.getName());
            exceptions.createAreaExceptions(areaFolder, area);

            // if area level types exist
            if (generateStructures && (area.getDataTypes() != null)
                    && !area.getDataTypes().getFundamentalOrAttributeOrComposite().isEmpty()) {
                // create area structure folder
                File structureFolder = StubUtils.createFolder(areaFolder, getConfig().getStructureFolder());
                // create a comment for the structure folder if supported
                createAreaStructureFolderComment(structureFolder, area);

                ConcurrentLinkedQueue<Exception> errors_1 = new ConcurrentLinkedQueue<>();

                // create area level data types
                area.getDataTypes().getFundamentalOrAttributeOrComposite().parallelStream().forEach(oType -> {
                    try {
                        if (oType instanceof FundamentalType) {
                            createFundamentalClass(structureFolder, area, null, (FundamentalType) oType);
                        } else if (oType instanceof AttributeType) {
                            String aName = ((AttributeType) oType).getName();
                            createListClass(structureFolder, area, null, aName,
                                    false, ((AttributeType) oType).getShortFormPart());
                            CompositeField fld = createCompositeElementsDetails(null, false, "fld",
                                    TypeUtils.createTypeReference(area.getName(), null, aName, false),
                                    true, true, "cmt");
                        } else if (oType instanceof CompositeType) {
                            createCompositeClass(structureFolder, area, null, (CompositeType) oType);
                        } else if (oType instanceof EnumerationType) {
                            JavaEnumerations enumerations = new JavaEnumerations(this);
                            EnumerationType enumType = (EnumerationType) oType;
                            logger.info(" > Creating Enumeration class: " + enumType.getName());
                            enumerations.createEnumerationClass(structureFolder, area, null, enumType);
                        } else {
                            throw new IllegalArgumentException("Unexpected area (" + area.getName() + ") level datatype of " + oType.getClass().getName());
                        }
                    } catch (Exception ex) {
                        errors_1.add(ex);
                    }
                });

                if (!errors_1.isEmpty()) {
                    throw (IOException) new IOException(errors_1.poll());
                }
            }

            try {
                t1.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(GeneratorLangs.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (!errors_2.isEmpty()) {
                throw (IOException) new IOException(errors_2.poll());
            }
        }
    }

    protected void processService(File areaFolder, AreaType area, ServiceType service,
            Map<String, RequiredPublisher> requiredPublishers) throws IOException {
        logger.info("Processing service: " + service.getName());
        // create service folders
        File serviceFolder = StubUtils.createFolder(areaFolder, service.getName());
        // load service operation details
        ServiceSummary summary = createOperationElementList(service);
        // create a comment for the service folder if supported
        createServiceFolderComment(serviceFolder, area, service);
        // create service helper
        JavaHelpers helper = new JavaHelpers(this);
        logger.info(" > Creating service Helper class: " + service.getName());
        helper.createServiceHelperClass(serviceFolder, area, service, summary);

        // create service info
        JavaServiceInfo serviceInfo = new JavaServiceInfo(this);
        logger.info(" > Creating ServiceInfo class: " + service.getName());
        serviceInfo.createServiceInfoClass(serviceFolder, area, service, summary);

        // create consumer classes
        createServiceConsumerClasses(serviceFolder, area, service, summary);
        // create provider classes
        createServiceProviderClasses(serviceFolder, area, service, summary, requiredPublishers);

        // if service level types exist
        if (generateStructures && (service.getDataTypes() != null) && !service.getDataTypes().getCompositeOrEnumeration().isEmpty()) {
            // create structure folder
            File structureFolder = StubUtils.createFolder(serviceFolder, getConfig().getStructureFolder());
            // create a comment for the structure folder if supported
            createServiceStructureFolderComment(structureFolder, area, service);
            String name;

            for (Object oType : service.getDataTypes().getCompositeOrEnumeration()) {
                if (oType instanceof EnumerationType) {
                    JavaEnumerations enumerations = new JavaEnumerations(this);
                    EnumerationType enumType = (EnumerationType) oType;
                    logger.info(" > Creating Enumeration class: " + enumType.getName());
                    enumerations.createEnumerationClass(structureFolder, area, service, (EnumerationType) oType);
                    name = ((EnumerationType) oType).getName();
                } else if (oType instanceof CompositeType) {
                    createCompositeClass(structureFolder, area, service, (CompositeType) oType);
                    name = ((CompositeType) oType).getName();
                } else {
                    throw new IllegalArgumentException("Unexpected service (" + area.getName()
                            + ":" + service.getName() + ") level datatype of " + oType.getClass().getName());
                }

                logger.warn("Warning! The data structure " + name
                        + " is set at Service-level in the " + service.getName()
                        + " service! Please move this data structure to Area-level"
                        + " in order to be compatible with the latest MO Standard.");
            }
        }
    }

    protected void createServiceConsumerClasses(File serviceFolder, AreaType area,
            ServiceType service, ServiceSummary summary) throws IOException {
        logger.info(" > Creating consumer classes: " + service.getName());
        File consumerFolder = StubUtils.createFolder(serviceFolder, CONSUMER_FOLDER);
        // create a comment for the consumer folder if supported
        createServiceConsumerFolderComment(consumerFolder, area, service);
        createServiceConsumerInterface(consumerFolder, area, service, summary);
        JavaConsumer consumer = new JavaConsumer(this, supportsToValue, supportsAsync);
        logger.info(" > Creating consumer adapter: " + service.getName());
        consumer.createServiceConsumerAdapter(consumerFolder, area, service, summary);
        logger.info(" > Creating consumer stub: " + service.getName());
        consumer.createServiceConsumerStub(consumerFolder, area, service, summary);
    }

    protected void createServiceProviderClasses(File serviceFolder, AreaType area, ServiceType service,
            ServiceSummary summary, Map<String, RequiredPublisher> requiredPublishers) throws IOException {
        logger.info(" > Creating provider classes: " + service.getName());
        File providerFolder = StubUtils.createFolder(serviceFolder, PROVIDER_FOLDER);
        // create a comment for the provider folder if supported
        createServiceProviderFolderComment(providerFolder, area, service);
        createServiceProviderHandler(providerFolder, area, service, summary);
        createServiceProviderSkeleton(providerFolder, area, service, summary, requiredPublishers);
        //createServiceProviderDelegation(providerFolder, area, service, summary);
        createServiceProviderInheritance(providerFolder, area, service, summary);
        createServiceProviderInteractions(providerFolder, area, service, summary);
    }

    @Deprecated
    private void createServiceProviderDelegation(File providerFolder, AreaType area,
            ServiceType service, ServiceSummary summary) throws IOException {
        logger.info(" > Creating provider delegate class: " + service.getName());
        createServiceProviderSkeletonHandler(providerFolder, area, service, summary, true);
    }

    protected void createServiceProviderInheritance(File providerFolder, AreaType area,
            ServiceType service, ServiceSummary summary) throws IOException {
        logger.info(" > Creating provider inheritance class: " + service.getName());
        createServiceProviderSkeletonHandler(providerFolder, area, service, summary, false);
    }

    protected void createServiceProviderInteractions(File providerFolder, AreaType area,
            ServiceType service, ServiceSummary summary) throws IOException {
        for (OperationSummary op : summary.getOperations()) {
            if (op.getPattern() == InteractionPatternEnum.INVOKE_OP) {
                createServiceProviderInvokeInteractionClass(providerFolder, area, service, op);
            } else if (op.getPattern() == InteractionPatternEnum.PROGRESS_OP) {
                createServiceProviderProgressInteractionClass(providerFolder, area, service, op);
            }
        }
    }

    protected void createServiceConsumerInterface(File consumerFolder, AreaType area,
            ServiceType service, ServiceSummary summary) throws IOException {
        String serviceName = service.getName();

        logger.info(" > Creating consumer interface: " + serviceName);

        InterfaceWriter file = createInterfaceFile(consumerFolder, serviceName);

        file.addPackageStatement(area, service, CONSUMER_FOLDER);

        file.addInterfaceOpenStatement(serviceName, null, "Consumer interface for " + serviceName + " service.");

        CompositeField serviceAdapterArg = createCompositeElementsDetails(file, false, "adapter",
                TypeUtils.createTypeReference(area.getName(), service.getName() + "." + CONSUMER_FOLDER, serviceName + "Adapter", false),
                false, true, "Listener in charge of receiving the messages from the service provider");
        CompositeField lastInteractionStage = createCompositeElementsDetails(file, false, "lastInteractionStage",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UOCTET, false),
                true, true, "The last stage of the interaction to continue");
        CompositeField initiationTimestamp = createCompositeElementsDetails(file, false, "initiationTimestamp",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.TIME, false),
                true, true, "Timestamp of the interaction initiation message");
        CompositeField transactionId = createCompositeElementsDetails(file, false, "transactionId",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.LONG, false),
                true, true, "Transaction identifier of the interaction to continue");
        List<CompositeField> continueOpArgs = StubUtils.concatenateArguments(lastInteractionStage, initiationTimestamp, transactionId, serviceAdapterArg);

        String throwsMALException = createElementType(StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String throwsInteractionException = createElementType(StdStrings.MAL, null, null, StdStrings.MALINTERACTIONEXCEPTION);
        String throwsInteractionAndMALException = throwsInteractionException + ", " + throwsMALException;
        CompositeField msgType = createCompositeElementsDetails(file, false, "return",
                TypeUtils.createTypeReference(StdStrings.MAL, TRANSPORT_FOLDER, StdStrings.MALMESSAGE, false),
                false, true, null);
        CompositeField malConsumer = createCompositeElementsDetails(file, false, "return",
                TypeUtils.createTypeReference(StdStrings.MAL, CONSUMER_FOLDER, "MALConsumer", false),
                false, true, null);

        file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, malConsumer, "getConsumer", null, null,
                "Returns the internal MAL consumer object used for sending of messages from this interface",
                "The MAL consumer object.", null);

        for (OperationSummary op : summary.getOperations()) {
            switch (op.getPattern()) {
                case SEND_OP: {
                    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, msgType, op.getName(),
                            createOperationArguments(getConfig(), file, op.getArgTypes()),
                            throwsInteractionAndMALException, op.getOriginalOp().getComment(),
                            "the MAL message sent to initiate the interaction",
                            Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.",
                                    throwsMALException + " if there is an implementation exception"));
                    break;
                }
                case SUBMIT_OP:
                case REQUEST_OP: {
                    List<CompositeField> opArgs = createOperationArguments(getConfig(), file, op.getArgTypes());
                    CompositeField opRetType = createOperationReturnType(file, area, service, op);
                    String opRetComment = (opRetType == null) ? null : "The return value of the interaction";
                    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, opRetType, op.getName(), opArgs,
                            throwsInteractionAndMALException, op.getOriginalOp().getComment(), opRetComment,
                            Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.",
                                    throwsMALException + " if there is an implementation exception"));
                    if (supportsAsync) {
                        List<CompositeField> asyncOpArgs = StubUtils.concatenateArguments(opArgs, serviceAdapterArg);
                        file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, msgType,
                                "async" + StubUtils.preCap(op.getName()), asyncOpArgs, throwsInteractionAndMALException,
                                "Asynchronous version of method " + op.getName(), "the MAL message sent to initiate the interaction",
                                Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.",
                                        throwsMALException + " if there is an implementation exception"));
                    }
                    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, null, "continue" + StubUtils.preCap(op.getName()),
                            continueOpArgs, throwsInteractionAndMALException, "Continues a previously started interaction", null,
                            Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.",
                                    throwsMALException + " if there is an implementation exception"));
                    break;
                }
                case INVOKE_OP:
                case PROGRESS_OP: {
                    List<CompositeField> opArgs = StubUtils.concatenateArguments(createOperationArguments(getConfig(), file, op.getArgTypes()), serviceAdapterArg);
                    CompositeField opRetType = createOperationReturnType(file, area, service, op);
                    String opRetComment = (opRetType == null) ? null : "The acknowledge value of the interaction";
                    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, opRetType, op.getName(), opArgs,
                            throwsInteractionAndMALException, op.getOriginalOp().getComment(), opRetComment,
                            Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.",
                                    throwsMALException + " if there is an implementation exception"));
                    if (supportsAsync) {
                        file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, msgType, "async" + StubUtils.preCap(op.getName()), opArgs,
                                throwsInteractionAndMALException, "Asynchronous version of method " + op.getName(), "the MAL message sent to initiate the interaction",
                                Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.",
                                        throwsMALException + " if there is an implementation exception"));
                    }
                    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, null, "continue" + StubUtils.preCap(op.getName()), continueOpArgs,
                            throwsInteractionAndMALException, "Continues a previously started interaction", null,
                            Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.",
                                    throwsMALException + " if there is an implementation exception"));
                    break;
                }
                case PUBSUB_OP: {
                    CompositeField subStr = createCompositeElementsDetails(file, false, "subscription",
                            TypeUtils.createTypeReference(StdStrings.MAL, null, "Subscription", false),
                            true, true, "The subscription to register for");
                    CompositeField idStr = createCompositeElementsDetails(file, false, "identifierList",
                            TypeUtils.createTypeReference(StdStrings.MAL, null, "Identifier", true),
                            true, true, "The subscription identifiers to deregister");

                    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, null, op.getName() + "Register", StubUtils.concatenateArguments(subStr, serviceAdapterArg),
                            throwsInteractionAndMALException, "Register method for the " + op.getName() + " PubSub interaction", null,
                            Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.",
                                    throwsMALException + " if there is an implementation exception"));
                    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, null, op.getName() + "Deregister", Arrays.asList(idStr),
                            throwsInteractionAndMALException, "Deregister method for the " + op.getName() + " PubSub interaction", null,
                            Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.",
                                    throwsMALException + " if there is an implementation exception"));
                    if (supportsAsync) {
                        file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, msgType, "async" + StubUtils.preCap(op.getName()) + "Register",
                                StubUtils.concatenateArguments(subStr, serviceAdapterArg),
                                throwsInteractionAndMALException, "Asynchronous version of method " + op.getName() + "Register", "the MAL message sent to initiate the interaction",
                                Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.",
                                        throwsMALException + " if there is an implementation exception"));
                        file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, msgType, "async" + StubUtils.preCap(op.getName()) + "Deregister",
                                StubUtils.concatenateArguments(idStr, serviceAdapterArg),
                                throwsInteractionAndMALException, "Asynchronous version of method " + op.getName() + "Deregister", "the MAL message sent to initiate the interaction",
                                Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.",
                                        throwsMALException + " if there is an implementation exception"));
                    }
                    break;
                }
            }
        }

        file.addInterfaceCloseStatement();

        file.flush();
    }

    protected void createServiceProviderHandler(File providerFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException {
        logger.info(" > Creating provider handler interface: " + service.getName());

        String handlerName = service.getName() + "Handler";
        InterfaceWriter file = createInterfaceFile(providerFolder, handlerName);

        file.addPackageStatement(area, service, PROVIDER_FOLDER);

        file.addInterfaceOpenStatement(handlerName, null,
                "Interface that providers of the " + service.getName() + " service must implement to handle the operations of that service.");

        CompositeField intHandler = createCompositeElementsDetails(file, false, "interaction",
                TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, StdStrings.MALINTERACTION, false),
                false, true, "The MAL object representing the interaction in the provider.");
        String throwsMALException = createElementType(StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String throwsInteractionException = createElementType(StdStrings.MAL, null, null, StdStrings.MALINTERACTIONEXCEPTION);
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
                    serviceHandler = createCompositeElementsDetails(file, false, "interaction",
                            TypeUtils.createTypeReference(area.getName(),
                                    service.getName() + "." + PROVIDER_FOLDER, StubUtils.preCap(op.getName()) + "Interaction", false),
                            false, true, "The MAL object representing the interaction in the provider.");
                }

                file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, opRetType, op.getName(),
                        StubUtils.concatenateArguments(opArgs, serviceHandler), throwsInteractionAndMALException,
                        "Implements the operation " + op.getName(), opRetComment,
                        Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.",
                                throwsMALException + " if there is an implementation exception"));
            }
        }

        CompositeField skel = createCompositeElementsDetails(file, false, "skeleton",
                TypeUtils.createTypeReference(area.getName(), service.getName() + "." + PROVIDER_FOLDER, service.getName() + "Skeleton", false),
                false, true, "The skeleton to be used.");
        file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, null, "setSkeleton", Arrays.asList(skel), null,
                "Sets the skeleton to be used for creation of publishers.", null, null);

        file.addInterfaceCloseStatement();

        file.flush();
    }

    protected void createServiceProviderInvokeInteractionClass(File providerFolder, AreaType area, ServiceType service, OperationSummary op) throws IOException {
        String className = StubUtils.preCap(op.getName()) + "Interaction";
        logger.info(" > Creating provider invoke interaction class: " + className);

        ClassWriter file = createClassFile(providerFolder, className);

        file.addPackageStatement(area, service, PROVIDER_FOLDER);

        String throwsMALException = createElementType(StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String throwsInteractionException = createElementType(StdStrings.MAL, null, null, StdStrings.MALINTERACTIONEXCEPTION);
        String throwsInteractionAndMALException = throwsInteractionException + ", " + throwsMALException;
        CompositeField msgType = createCompositeElementsDetails(file, false, "return",
                TypeUtils.createTypeReference(StdStrings.MAL, TRANSPORT_FOLDER, StdStrings.MALMESSAGE, false),
                false, true, null);
        CompositeField opType = createCompositeElementsDetails(file, false, "return",
                TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALInvoke", false),
                false, true, null);
        CompositeField opTypeVar = createCompositeElementsDetails(file, false, "interaction",
                TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALInvoke", false),
                false, false, null);
        CompositeField errType = createCompositeElementsDetails(file, false, "error",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "MOErrorException", false),
                false, true, "The MAL error to send to the consumer.");

        file.addClassOpenStatement(className, false, false, null, null,
                "Provider INVOKE interaction class for " + service.getName() + "::" + op.getName() + " operation.");

        file.addClassVariable(false, false, StdStrings.PRIVATE, opTypeVar, false, (String) null);

        MethodWriter method = file.addConstructor(StdStrings.PUBLIC, className,
                createCompositeElementsDetails(file, false, "interaction",
                        TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALInvoke", false),
                        false, true, "The MAL interaction action object to use."), false, null,
                "Wraps the provided MAL interaction object with methods for sending responses to an INVOKE interaction from a provider.", null);
        method.addLine(createMethodCall("this.interaction = interaction"));
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                false, true, opType, "getInteraction", null, null,
                "Returns the MAL interaction object used for returning messages from the provider.",
                "The MAL interaction object provided in the constructor", null);
        method.addLine("return interaction");
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                false, true, msgType, "sendAcknowledgement",
                createOperationArguments(getConfig(), file, op.getAckTypes()), throwsInteractionAndMALException,
                "Sends a INVOKE acknowledge to the consumer", "Returns the MAL message created by the acknowledge",
                Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.",
                        throwsMALException + " if there is an implementation exception"));
        method.addLine(createMethodCall("return interaction.sendAcknowledgement(") + createArgNameOrNull(op.getAckTypes()) + ")");
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                false, true, msgType, "sendResponse",
                createOperationArguments(getConfig(), file, op.getRetTypes()), throwsInteractionAndMALException,
                "Sends a INVOKE response to the consumer", "Returns the MAL message created by the response",
                Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.",
                        throwsMALException + " if there is an implementation exception"));
        method.addLine(createMethodCall("return interaction.sendResponse(") + createArgNameOrNull(op.getRetTypes()) + ")");
        method.addMethodCloseStatement();

        createServiceProviderInteractionErrorHandlers(file, false, msgType, errType, throwsInteractionException, throwsMALException);

        file.addClassCloseStatement();

        file.flush();
    }

    protected void createServiceProviderProgressInteractionClass(File providerFolder,
            AreaType area, ServiceType service, OperationSummary op) throws IOException {
        String className = StubUtils.preCap(op.getName()) + "Interaction";
        logger.info(" > Creating provider progress interaction class: " + className);

        ClassWriter file = createClassFile(providerFolder, className);

        file.addPackageStatement(area, service, PROVIDER_FOLDER);

        String throwsMALException = createElementType(StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String throwsInteractionException = createElementType(StdStrings.MAL, null, null, StdStrings.MALINTERACTIONEXCEPTION);
        String throwsInteractionAndMALException = throwsInteractionException + ", " + throwsMALException;
        CompositeField msgType = createCompositeElementsDetails(file, false, "return",
                TypeUtils.createTypeReference(StdStrings.MAL, TRANSPORT_FOLDER, StdStrings.MALMESSAGE, false),
                false, true, null);
        CompositeField opType = createCompositeElementsDetails(file, false, "return",
                TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALProgress", false),
                false, true, null);
        CompositeField opTypeVar = createCompositeElementsDetails(file, false, "interaction",
                TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALProgress", false),
                false, false, null);
        CompositeField errType = createCompositeElementsDetails(file, false, "error",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "MOErrorException", false),
                false, true, "error The MAL error to send to the consumer.");

        file.addClassOpenStatement(className, false, false, null, null,
                "Provider PROGRESS interaction class for " + service.getName() + "::" + op.getName() + " operation.");

        file.addClassVariable(false, false, StdStrings.PRIVATE, opTypeVar,
                false, (String) null);

        MethodWriter method = file.addConstructor(StdStrings.PUBLIC, className,
                createCompositeElementsDetails(file, false, "interaction",
                        TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALProgress", false),
                        false, true, "The MAL interaction action object to use."), false, null,
                "Wraps the provided MAL interaction object with methods for sending responses to an PROGRESS interaction from a provider.", null);
        method.addLine(createMethodCall("this.interaction = interaction"));
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                false, true, opType, "getInteraction", null, null,
                "Returns the MAL interaction object used for returning messages from the provider.",
                "The MAL interaction object provided in the constructor", null);
        method.addLine("return interaction");
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                false, true, msgType, "sendAcknowledgement",
                createOperationArguments(getConfig(), file, op.getAckTypes()), throwsInteractionAndMALException,
                "Sends a PROGRESS acknowledge to the consumer", "Returns the MAL message created by the acknowledge",
                Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
        method.addLine(createMethodCall("return interaction.sendAcknowledgement(") + createArgNameOrNull(op.getAckTypes()) + ")");
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                false, true, msgType, "sendUpdate",
                createOperationArguments(getConfig(), file, op.getUpdateTypes()), throwsInteractionAndMALException,
                "Sends a PROGRESS update to the consumer", "Returns the MAL message created by the update",
                Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
        method.addLine(createMethodCall("return interaction.sendUpdate(") + createArgNameOrNull(op.getUpdateTypes()) + ")");
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                false, true, msgType, "sendResponse",
                createOperationArguments(getConfig(), file, op.getRetTypes()), throwsInteractionAndMALException,
                "Sends a PROGRESS response to the consumer", "Returns the MAL message created by the response",
                Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
        method.addLine(createMethodCall("return interaction.sendResponse(") + createArgNameOrNull(op.getRetTypes()) + ")");
        method.addMethodCloseStatement();

        createServiceProviderInteractionErrorHandlers(file, true, msgType, errType, throwsInteractionException, throwsMALException);

        file.addClassCloseStatement();

        file.flush();
    }

    protected void createServiceProviderInteractionErrorHandlers(ClassWriter file, boolean withUpdate, CompositeField msgType,
            CompositeField errType, String throwsInteractionException, String throwsMALException) throws IOException {
        MethodWriter method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true,
                msgType, "sendError", Arrays.asList(errType), throwsInteractionException + ", " + throwsMALException,
                "Sends an error to the consumer", "Returns the MAL message created by the error",
                Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
        method.addLine(createMethodCall("return interaction.sendError(error)"));
        method.addMethodCloseStatement();

        if (withUpdate) {
            method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true,
                    msgType, "sendUpdateError", Arrays.asList(errType), throwsInteractionException + ", " + throwsMALException,
                    "Sends an update error to the consumer", "Returns the MAL message created by the error",
                    Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
            method.addLine(createMethodCall("return interaction.sendUpdateError(error)"));
            method.addMethodCloseStatement();
        }
    }

    protected void createServiceProviderSkeleton(File providerFolder, AreaType area, ServiceType service,
            ServiceSummary summary, Map<String, RequiredPublisher> requiredPublishers) throws IOException {
        logger.info(" > Creating provider skeleton interface: " + service.getName());

        String skeletonName = service.getName() + "Skeleton";
        InterfaceWriter file = createInterfaceFile(providerFolder, skeletonName);
        String throwsMALException = createElementType(StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        CompositeField malDomId = createCompositeElementsDetails(file, false, "domain",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, true),
                true, true, "The domain used for publishing");
        CompositeField malNetworkZone = createCompositeElementsDetails(file, false, "networkZone",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, false),
                true, true, "~The network zone used for publishing");
        CompositeField malSession = createCompositeElementsDetails(file, false, "sessionType",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "SessionType", false),
                true, true, "The session used for publishing");
        CompositeField malSessionName = createCompositeElementsDetails(file, false, "sessionName",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER,
                        false), true, true, "The session name used for publishing");
        CompositeField malqos = createCompositeElementsDetails(file, false, "qos",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "QoSLevel", false),
                true, true, "The QoS used for publishing");
        CompositeField malqosprops = createCompositeElementsDetails(file, false, "qosProps",
                TypeUtils.createTypeReference(null, null, "Map<_String;_String>", false),
                false, true, "The QoS properties used for publishing");
        CompositeField malPriority = createCompositeElementsDetails(file, false, "priority",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UINTEGER, false),
                true, true, "The priority used for publishing");

        file.addPackageStatement(area, service, PROVIDER_FOLDER);

        file.addInterfaceOpenStatement(skeletonName, null, "The skeleton interface for the " + service.getName() + " service.");

        for (OperationSummary op : summary.getOperations()) {
            switch (op.getPattern()) {
                case PUBSUB_OP: {
                    String updateType = getConfig().getAreaPackage(area.getName())
                            + area.getName().toLowerCase() + "." + service.getName().toLowerCase()
                            + "." + PROVIDER_FOLDER + "." + StubUtils.preCap(op.getName()) + "Publisher";
                    requiredPublishers.put(updateType, new RequiredPublisher(area, service, op));
                    CompositeField updateTypeField = createCompositeElementsDetails(file, false, "publisher",
                            TypeUtils.createTypeReference(area.getName(),
                                    service.getName() + "." + PROVIDER_FOLDER, StubUtils.preCap(op.getName()) + "Publisher", false),
                            false, true, null);
                    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, updateTypeField, "create" + StubUtils.preCap(op.getName()) + "Publisher",
                            StubUtils.concatenateArguments(malDomId, malNetworkZone, malSession, malSessionName, malqos, malqosprops, malPriority), throwsMALException,
                            "Creates a publisher object using the current registered provider set for the PubSub operation " + op.getName(),
                            "The new publisher object.", Arrays.asList(throwsMALException + " if a problem is detected during creation of the publisher"));
                    break;
                }
            }
        }

        file.addInterfaceCloseStatement();

        file.flush();
    }

    protected void createServiceProviderSkeletonHandler(File providerFolder, AreaType area,
            ServiceType service, ServiceSummary summary, boolean isDelegate) throws IOException {
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

        String throwsMALException = createElementType(StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String throwsInteractionException = createElementType(StdStrings.MAL, null, null, StdStrings.MALINTERACTIONEXCEPTION);
        String throwsMALAndInteractionException = throwsInteractionException + ", " + throwsMALException;
        String malHelper = createElementType(StdStrings.MAL, null, null, "MALHelper");
        String helperName = createElementType(area.getName(), service.getName(), null, service.getName() + "Helper");
        String serviceInfoName = createElementType(area.getName(), service.getName(), null, service.getName() + JavaServiceInfo.SERVICE_INFO);
        String malString = malStringAsElement(file);
        String malInteger = createElementType(StdStrings.MAL, null, StdStrings.INTEGER);
        String stdError = createElementType(StdStrings.MAL, null, null, "MOErrorException");
        CompositeField stdBodyArg = createCompositeElementsDetails(file, false, "body",
                TypeUtils.createTypeReference(StdStrings.MAL, TRANSPORT_FOLDER, "MALMessageBody", false),
                false, true, "The message body");
        String stdErrorNs = convertToNamespace("," + stdError + ".," + malString + ".," + malInteger + ".");
        CompositeField malDomId = createCompositeElementsDetails(file, false, "domain",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, true),
                true, true, "The domain used for publishing");
        CompositeField malNetworkZone = createCompositeElementsDetails(file, false, "networkZone",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, false),
                true, true, "The network zone used for publishing");
        CompositeField malSession = createCompositeElementsDetails(file, false, "sessionType",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "SessionType", false),
                true, true, "The session used for publishing");
        CompositeField malSessionName = createCompositeElementsDetails(file, false, "sessionName",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, false),
                true, true, "The session name used for publishing");
        CompositeField malqos = createCompositeElementsDetails(file, false, "qos",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "QoSLevel", false),
                true, true, "The QoS used for publishing");
        CompositeField malqosprops = createCompositeElementsDetails(file, false, "qosProps",
                TypeUtils.createTypeReference(null, null, "Map<_String;_String>", false),
                false, true, "The QoS properties used for publishing");
        CompositeField malPriority = createCompositeElementsDetails(file, false, "priority",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UINTEGER, false),
                true, true, "The priority used for publishing");
        CompositeField proviedrSetVar = createCompositeElementsDetails(file, false, "providerSet",
                TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALProviderSet", false),
                false, true, null);
        List<CompositeField> psArgs = StubUtils.concatenateArguments(malDomId, malNetworkZone,
                malSession, malSessionName, malqos, malqosprops, malPriority);

        String implementsList = createElementType(StdStrings.MAL, null, PROVIDER_FOLDER, "MALInteractionHandler")
                + ", " + createElementType(area.getName(), service.getName(), PROVIDER_FOLDER, service.getName() + "Skeleton");
        if (!isDelegate) {
            implementsList += ", " + createElementType(area.getName(), service.getName(), PROVIDER_FOLDER, service.getName() + "Handler");
        }
        file.addClassOpenStatement(className, false, !isDelegate, null, implementsList, comment);

        file.addClassVariable(false, false, StdStrings.PRIVATE, proviedrSetVar, false,
                "(" + helperName + getConfig().getNamingSeparator() + service.getName().toUpperCase() + "_SERVICE)");

        if (isDelegate) {
            CompositeField handlerName = createCompositeElementsDetails(file, false, "delegate",
                    TypeUtils.createTypeReference(area.getName(), service.getName() + "." + PROVIDER_FOLDER, service.getName() + "Handler", false),
                    false, true, null);
            file.addClassVariable(false, false, StdStrings.PRIVATE, handlerName, false, (String) null);
        }

        if (isDelegate) {
            MethodWriter method = file.addConstructor(StdStrings.PUBLIC, className,
                    createCompositeElementsDetails(file, false, "delegate",
                            TypeUtils.createTypeReference(area.getName(), service.getName().toLowerCase() + "." + PROVIDER_FOLDER, service.getName() + "Handler", false),
                            false, true, "The interaction handler used for delegation"), false, null,
                    "Creates a delegation skeleton using the supplied delegate.", null);
            method.addLine(createMethodCall("this.delegate = delegate"));
            method.addLine(createMethodCall("delegate.setSkeleton(this)"));
            method.addMethodCloseStatement();
        } else {
            // Connection object method
            CompositeField connectionName = createCompositeElementsDetails(file, false, "connection",
                TypeUtils.createTypeReference(StdStrings.MAL, "helpertools.connections", "ConnectionProvider", false),
                false, true, "Returns the connection object for this provider.");

            ArrayList throwsList = new ArrayList();
            throwsList.add("java.io.IOException if the method was not implemented yet.");
            MethodWriter method1 = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                    false, true, connectionName, "getConnection", null, "java.io.IOException",
                    "Returns the connection object for this provider.",
                    "the connection object for this provider", throwsList);
            method1.addLine("throw new java.io.IOException(\"This method needs to be overridden!\")");
            method1.addMethodCloseStatement();

            // SetSkeleton method
            CompositeField skeletonName = createCompositeElementsDetails(file, false, "skeleton",
                    TypeUtils.createTypeReference(area.getName(), service.getName() + "." + PROVIDER_FOLDER, service.getName() + "Skeleton", false),
                    false, true, "Not used in the inheritance pattern (the skeleton is 'this'");
            MethodWriter method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                    false, true, null, "setSkeleton", Arrays.asList(skeletonName), null,
                    "Implements the setSkeleton method of the handler interface but does nothing as this is the skeleton.",
                    null, null);
            method.addLine("// Not used in the inheritance pattern (the skeleton is 'this')");
            method.addMethodCloseStatement();
        }

        CompositeField providerType = createCompositeElementsDetails(file, false, "provider",
                TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALProvider", false),
                false, true, "The provider to be added.");
        MethodWriter method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                false, true, null, "malInitialize", Arrays.asList(providerType), throwsMALException,
                "Adds the supplied MAL provider to the internal list of providers used for PubSub",
                null, Arrays.asList(throwsMALException + " If an error is detected."));
        method.addLine(createMethodCall("providerSet.addProvider(provider)"));
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                false, true, null, "malFinalize", Arrays.asList(providerType), throwsMALException,
                "Removes the supplied MAL provider from the internal list of providers used for PubSub", null,
                Arrays.asList(throwsMALException + " If an error is detected."));
        method.addLine(createMethodCall("providerSet.removeProvider(provider)"));
        method.addMethodCloseStatement();

        // add publisher handler code
        for (OperationSummary op : summary.getOperations()) {
            switch (op.getPattern()) {
                case PUBSUB_OP: {
                    CompositeField updateType = createCompositeElementsDetails(file, false, "publisher",
                            TypeUtils.createTypeReference(area.getName(), service.getName() + "." + PROVIDER_FOLDER, StubUtils.preCap(op.getName()) + "Publisher", false),
                            false, true, null);
                    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, false,
                            updateType, "create" + StubUtils.preCap(op.getName()) + "Publisher", psArgs, throwsMALException,
                            "Creates a publisher object using the current registered provider set for the PubSub operation " + op.getName(),
                            "The new publisher object.", Arrays.asList(throwsMALException + " if a problem is detected during creation of the publisher"));
                    String ns = convertToNamespace(serviceInfoName + "." + op.getName().toUpperCase() + "_OP");
                    method.addMethodWithDependencyStatement("return new " + updateType.getTypeName()
                            + createMethodCall("(providerSet.createPublisherSet(") + ns + ", domain, sessionType, sessionName, qos, qosProps, null))", ns, true);
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
        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, null, "handleSend",
                StubUtils.concatenateArguments(createServiceProviderSkeletonSendHandler(file, "interaction", "The interaction object"), stdBodyArg),
                throwsMALAndInteractionException,
                "Called by the provider MAL layer on reception of a message to handle the interaction", null,
                Arrays.asList(throwsMALException + " if there is a internal error", throwsInteractionException + " if there is a operation interaction error"));

        String operationNumberGetter = createProviderSkeletonHandlerSwitch();
        method.addLine("int opNumber = " + operationNumberGetter);
        method.addLine(createMethodCall("switch (opNumber) {"), false);

        //String msg = "Unknown operation number: \" + opNumber + \" - className: " + className + " - method: ";
        String msg = "org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber";
        String unkErrorMsg;

        for (OperationSummary op : summary.getOperations()) {
            if (op.getPattern() == InteractionPatternEnum.SEND_OP) {
                String opArgs = createAdapterMethodsArgs(op.getArgTypes(), "body", false, true);
                String ns = convertToNamespace(serviceInfoName + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
                method.addMethodWithDependencyStatement("  case " + ns, ns, false);
                method.addLine("    " + delegateCall + op.getName() + "(" + opArgs + "interaction)");
                method.addLine("    break");
            }
        }
        method.addLine("  default:", false);
        String ns = convertToNamespace(malHelper + ".UNSUPPORTED_OPERATION_ERROR_NUMBER");
        unkErrorMsg = "(\"" + msg + "Send\")";
        method.addMethodWithDependencyStatement("    throw new " + throwsInteractionException
                + "(new org.ccsds.moims.mo.mal.UnsupportedOperationException(\n                    "
                + msg + "))", ns + stdErrorNs, true);
        method.addLine("}", false);
        method.addMethodCloseStatement();

        // SUBMIT handler
        CompositeField submitInt = createCompositeElementsDetails(file, false, "interaction",
                TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALSubmit", false),
                false, true, "The interaction object");
        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, null, "handleSubmit",
                StubUtils.concatenateArguments(submitInt, stdBodyArg), throwsMALAndInteractionException,
                "Called by the provider MAL layer on reception of a message to handle the interaction", null,
                Arrays.asList(throwsMALException + " if there is a internal error", throwsInteractionException + " if there is a operation interaction error"));
        method.addLine("int opNumber = " + operationNumberGetter);
        method.addLine(createMethodCall("switch (opNumber) {"), false);

        for (OperationSummary op : summary.getOperations()) {
            if (op.getPattern() == InteractionPatternEnum.SUBMIT_OP) {
                String opArgs = createAdapterMethodsArgs(op.getArgTypes(), "body", false, true);
                ns = convertToNamespace(serviceInfoName + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
                method.addMethodWithDependencyStatement("  case " + ns, ns, false);
                method.addLine("    " + delegateCall + op.getName() + "(" + opArgs + "interaction)");
                method.addLine(createMethodCall("    interaction.sendAcknowledgement()"));
                method.addLine("    break");
            }
        }
        method.addLine("  default:", false);
        ns = convertToNamespace(malHelper + ".UNSUPPORTED_OPERATION_ERROR_NUMBER");
        unkErrorMsg = "(\"" + msg + "Submit\")";
        method.addMethodWithDependencyStatement(createMethodCall("    interaction.sendError"
                + "(new org.ccsds.moims.mo.mal.UnsupportedOperationException(\n                    "
                + msg + "))"), ns + stdErrorNs, true);
        method.addMethodWithDependencyStatement("    throw new " + throwsInteractionException
                + "(new org.ccsds.moims.mo.mal.UnsupportedOperationException(\n                    "
                + msg + "))", ns + stdErrorNs, true);
        method.addLine("}", false);
        method.addMethodCloseStatement();

        // REQUEST handler
        CompositeField requestInt = createCompositeElementsDetails(file, false, "interaction",
                TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALRequest", false),
                false, true, "The interaction object");
        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, null,
                "handleRequest", StubUtils.concatenateArguments(requestInt, stdBodyArg), throwsMALAndInteractionException,
                "Called by the provider MAL layer on reception of a message to handle the interaction", null,
                Arrays.asList(throwsMALException + " if there is a internal error", throwsInteractionException + " if there is a operation interaction error"));
        method.addLine("int opNumber = " + operationNumberGetter);
        method.addLine(createMethodCall("switch (opNumber) {"), false);

        for (OperationSummary op : summary.getOperations()) {
            if (op.getPattern() == InteractionPatternEnum.REQUEST_OP) {
                String opArgs = createAdapterMethodsArgs(op.getArgTypes(), "body", false, true);
                String opResp = delegateCall + op.getName() + "(" + opArgs + "interaction)";
                ns = convertToNamespace(serviceInfoName + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
                method.addMethodWithDependencyStatement("  case " + ns, ns, false);
                createRequestResponseDecompose(
                        method, op,
                        opResp,
                        createReturnType(file, area, service, op.getName(), "Response", op.getRetTypes())
                );
                method.addLine("    break");
            }
        }
        method.addLine("  default:", false);
        ns = convertToNamespace(malHelper + ".UNSUPPORTED_OPERATION_ERROR_NUMBER");
        unkErrorMsg = "(\"" + msg + "Request\")";
        method.addMethodWithDependencyStatement(createMethodCall("    interaction.sendError"
                + "(new org.ccsds.moims.mo.mal.UnsupportedOperationException(\n                    "
                + msg + "))"), ns + stdErrorNs, true);
        method.addMethodWithDependencyStatement("    throw new " + throwsInteractionException
                + "(new org.ccsds.moims.mo.mal.UnsupportedOperationException(\n                    "
                + msg + "))", ns + stdErrorNs, true);
        method.addLine("}", false);
        method.addMethodCloseStatement();

        // INVOKE handler
        CompositeField invokeInt = createCompositeElementsDetails(file, false, "interaction",
                TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALInvoke", false),
                false, true, "The interaction object");
        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, null, "handleInvoke",
                StubUtils.concatenateArguments(invokeInt, stdBodyArg), throwsMALAndInteractionException,
                "Called by the provider MAL layer on reception of a message to handle the interaction", null,
                Arrays.asList(throwsMALException + " if there is a internal error", throwsInteractionException + " if there is a operation interaction error"));
        method.addLine("int opNumber = " + operationNumberGetter);
        method.addLine(createMethodCall("switch (opNumber) {"), false);

        for (OperationSummary op : summary.getOperations()) {
            if (op.getPattern() == InteractionPatternEnum.INVOKE_OP) {
                String opArgs = createAdapterMethodsArgs(op.getArgTypes(), "body", false, true);
                ns = convertToNamespace(serviceInfoName + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
                method.addMethodWithDependencyStatement("  case " + ns, ns, false);
                method.addLine("    " + delegateCall + op.getName() + "(" + opArgs
                        + "new " + StubUtils.preCap(op.getName()) + "Interaction" + "(interaction))");
                method.addLine("    break");
            }
        }
        method.addLine("  default:", false);
        ns = convertToNamespace(malHelper + ".UNSUPPORTED_OPERATION_ERROR_NUMBER");
        unkErrorMsg = "(\"" + msg + "Invoke\")";
        method.addMethodWithDependencyStatement(createMethodCall("    interaction.sendError"
                + "(new org.ccsds.moims.mo.mal.UnsupportedOperationException(\n                    "
                + msg + "))"), ns + stdErrorNs, true);
        method.addMethodWithDependencyStatement("    throw new " + throwsInteractionException
                + "(new org.ccsds.moims.mo.mal.UnsupportedOperationException(\n                    "
                + msg + "))", ns + stdErrorNs, true);
        method.addLine("}", false);
        method.addMethodCloseStatement();

        // PROGRESS handler
        CompositeField progressInt = createCompositeElementsDetails(file, false, "interaction",
                TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALProgress", false),
                false, true, "The interaction object");
        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, null, "handleProgress",
                StubUtils.concatenateArguments(progressInt, stdBodyArg), throwsMALAndInteractionException,
                "Called by the provider MAL layer on reception of a message to handle the interaction", null,
                Arrays.asList(throwsMALException + " if there is a internal error", throwsInteractionException + " if there is a operation interaction error"));
        method.addLine("int opNumber = " + operationNumberGetter);
        method.addLine(createMethodCall("switch (opNumber) {"), false);

        for (OperationSummary op : summary.getOperations()) {
            if (op.getPattern() == InteractionPatternEnum.PROGRESS_OP) {
                String opArgs = createAdapterMethodsArgs(op.getArgTypes(), "body", false, true);
                ns = convertToNamespace(serviceInfoName + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
                method.addMethodWithDependencyStatement("  case " + ns, ns, false);
                method.addLine("    " + delegateCall + op.getName() + "(" + opArgs + "new " + StubUtils.preCap(op.getName()) + "Interaction" + "(interaction))");
                method.addLine("    break");
            }
        }
        method.addLine("  default:", false);
        ns = convertToNamespace(malHelper + ".UNSUPPORTED_OPERATION_ERROR_NUMBER");
        unkErrorMsg = "(\"" + msg + "Progress\")";
        method.addMethodWithDependencyStatement(createMethodCall("    interaction.sendError"
                + "(new org.ccsds.moims.mo.mal.UnsupportedOperationException(\n                    "
                + msg + "))"), ns + stdErrorNs, true);
        method.addMethodWithDependencyStatement("    throw new " + throwsInteractionException
                + "(new org.ccsds.moims.mo.mal.UnsupportedOperationException(\n                    "
                + msg + "))", ns + stdErrorNs, true);
        method.addLine("}", false);
        method.addMethodCloseStatement();

        file.addClassCloseStatement();

        file.flush();
    }

    private void createRequestResponseDecompose(MethodWriter method, OperationSummary op,
            String opCall, CompositeField opRetType) throws IOException {
        List<TypeInfo> targetTypes = op.getRetTypes();

        if ((targetTypes != null) && (!targetTypes.isEmpty())) {
            if (targetTypes.size() == 1) {
                if ((op.getRetTypes().get(0).isNativeType())) {
                    String arg = op.getName() + "Rt";
                    method.addLine("    " + opRetType.getTypeName() + " " + arg + " = " + opCall);
                    StringBuilder buf = new StringBuilder();
                    buf.append("(").append(arg).append(" == null) ? null : new ").append(getConfig().getAreaPackage(StdStrings.MAL));
                    buf.append("mal.").append(getConfig().getStructureFolder()).append(".").append(StdStrings.UNION);
                    buf.append("(").append(arg).append(")");
                    method.addLine(createMethodCall("    interaction.sendResponse(" + buf.toString() + ")"));
                } else {
                    method.addLine(createMethodCall("    interaction.sendResponse(" + opCall + ")"));
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
                        buf.append("(").append(arg).append(".getBodyElement").append(i).append("()").append(" == null) ? null : new ");
                        buf.append(getConfig().getAreaPackage(StdStrings.MAL)).append("mal.").append(getConfig().getStructureFolder());
                        buf.append(".").append(StdStrings.UNION).append("(").append(arg).append(".getBodyElement").append(i).append("()").append(")");
                    } else {
                        buf.append(arg).append(".getBodyElement").append(i).append("()");
                    }
                }

                method.addLine("    " + opRetType.getTypeName() + " " + arg + " = " + opCall);
                method.addLine(createMethodCall("    interaction.sendResponse(" + buf.toString() + ")"));
            }
        } else {
            // operation has an empty response
            method.addLine(createMethodCall("    " + opCall));
            method.addLine(createMethodCall("    interaction.sendResponse()"));
        }
    }

    public String getReferenceShortForm(AnyTypeReference ref) {
        String rv = null;

        if ((null != ref) && (null != ref.getAny()) && (!ref.getAny().isEmpty())) {
            List<TypeInfo> refs = TypeUtils.convertTypeReferences(this, TypeUtils.getTypeListViaXSDAny(ref.getAny()));
            rv = refs.get(0).getMalShortFormField();
        }

        return rv;
    }

    public String getReferenceShortForm(TargetWriter file, OptionalObjectReference oor) {
        if (oor == null || oor.getObjectType() == null) {
            return null;
        }

        ObjectReference any = oor.getObjectType();
        String service = any.getService();
        TypeKey key = new TypeKey(any.getArea(), service, String.valueOf(any.getNumber()));

        if (!comObjectMap.containsKey(key)) {
            logger.warn("Unknown COM object referenced: " + key);
            return null;
        }

        ModelObjectType refObj = comObjectMap.get(key);
        return convertToNamespace(createElementType(any.getArea(), service, null, service + JavaServiceInfo.SERVICE_INFO)
                + "." + refObj.getName().toUpperCase() + "_OBJECT_TYPE");
    }

    protected void createFundamentalClass(File folder, AreaType area, ServiceType service, FundamentalType enumeration) throws IOException {
        // fundamental types are usually hand created as part of a language mapping, but we have this here in case this
        // is not the case for a particular language
    }

    protected void createCompositeClass(File folder, AreaType area, ServiceType service, CompositeType composite) throws IOException {
        String className = composite.getName();
        logger.info(" > Creating Composite class: " + className);

        ClassWriter file = createClassFile(folder, className);
        String parentClass = null;
        TypeReference parentType = null;
        String parentInterface = createElementType(StdStrings.MAL, null, StdStrings.COMPOSITE);

        // Check if it is an extended Composite Type:
        if (composite.getExtends() != null) {
            parentType = composite.getExtends().getType();

            if (!StdStrings.MAL.equals(composite.getExtends().getType().getArea())
                    && !StdStrings.COMPOSITE.equals(composite.getExtends().getType().getName())) {
                parentClass = createElementType(parentType, true);
                parentInterface = null;
            }

            // Check if it is an MO Object:
            if (StdStrings.MAL.equals(composite.getExtends().getType().getArea())
                    && StdStrings.MOOBJECT.equals(composite.getExtends().getType().getName())) {
                parentClass = createElementType(parentType, true);
                parentInterface = null;
            }
        }

        file.addPackageStatement(area, service, getConfig().getStructureFolder());

        CompositeField elementType = createCompositeElementsDetails(file, false, "return",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.ELEMENT, false),
                true, true, null);

        List<CompositeField> compElements = createCompositeElementsList(file, composite);
        List<CompositeField> superCompElements = createCompositeSuperElementsList(file, parentType);

        boolean abstractComposite = (composite.getShortFormPart() == null);
        file.addClassOpenStatement(className, !abstractComposite, abstractComposite,
                parentClass, parentInterface, composite.getComment());
        String fqName = createElementType(area, service, className);

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
        file.addConstructorDefault(className);

        // if we or our parents have attributes then we need a typed constructor
        if (!compElements.isEmpty() || !superCompElements.isEmpty()) {
            List<CompositeField> superArgs = new LinkedList<>();
            List<CompositeField> args = new LinkedList<>();
            List<CompositeField> superArgsNonNullable = new LinkedList<>();
            List<CompositeField> argsNonNullable = new LinkedList<>();

            for (CompositeField element : superCompElements) {
                superArgs.add(element);
                args.add(element);

                if (!element.isCanBeNull()) {
                    superArgsNonNullable.add(element);
                    argsNonNullable.add(element);
                }
            }

            args.addAll(compElements);

            for (CompositeField element : compElements) {
                if (!element.isCanBeNull()) {
                    argsNonNullable.add(element);
                }
            }

            // Creates constructor with all arguments
            MethodWriter method = file.addConstructor(StdStrings.PUBLIC, className, args,
                    superArgs, null, "Constructor that initialises the values of the structure.", null);

            for (CompositeField element : compElements) {
                String call = createMethodCall("this." + element.getFieldName() + " = " + element.getFieldName());
                method.addLine(call);
            }

            method.addMethodCloseStatement();

            // Add a contructor that has only the non-nullable fields. Sets the nullable fields to null.
            boolean hasNonNullable = !argsNonNullable.isEmpty() || !superArgsNonNullable.isEmpty();
            boolean allArgsNonNullable = argsNonNullable.size() == args.size(); // All args are non-nullable?
            // Note: If all method arguments are non-nullable, then the 
            // contructor will look the same as the one with all arguments. So, 
            // same type signature and therefore that needs to be avoided.
            if (hasNonNullable && !allArgsNonNullable) {
                MethodWriter method2 = file.addConstructor(StdStrings.PUBLIC, className,
                        argsNonNullable, superArgsNonNullable, null,
                        "Constructor that initialises the non-nullable values of the structure.", null);

                for (CompositeField element : compElements) {
                    String ending = (!element.isCanBeNull()) ? element.getFieldName() : "null";
                    String call = createMethodCall("this." + element.getFieldName() + " = " + ending);
                    method.addLine(call);
                }

                method2.addMethodCloseStatement();
            }

            // create copy constructor
            if (supportsToValue && !abstractComposite) {
                file.addConstructorCopy(fqName, compElements);
            }
        }

        if (!abstractComposite) {
            MethodWriter method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                    false, false, elementType, "createElement", null, null,
                    "Creates an instance of this type using the default constructor. It is a generic factory method.",
                    "A new instance of this type with default field values.", null);
            method.addLine("return new " + fqName + "()");
            method.addMethodCloseStatement();
        }

        // add getters and setters
        for (CompositeField element : compElements) {
            addGetter(file, element, null);
            addSetter(file, element, null);
        }

        // create equals method
        if (supportsEquals) {
            CompositeField boolType = createCompositeElementsDetails(file, false, "return",
                    TypeUtils.createTypeReference(null, null, "boolean", false),
                    false, true, "return value");
            CompositeField intType = createCompositeElementsDetails(file, false, "return",
                    TypeUtils.createTypeReference(null, null, "int", false),
                    false, true, "return value");
            CompositeField objType = createCompositeElementsDetails(file, false, "obj",
                    TypeUtils.createTypeReference(null, null, "Object", false),
                    false, true, "The object to compare with.");
            MethodWriter method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                    false, true, boolType, "equals", Arrays.asList(objType), null,
                    "Compares this object to the specified object. The result is true if and only if the "
                    + "argument is not null and is the same type that contains the same value as this object.",
                    "true if the objects are the same; false otherwise.", null);
            method.addLine("if (obj instanceof " + className + ") {", false);

            if (null != parentClass) {
                method.addLine("    if (! super.equals(obj)) {", false);
                method.addLine("        return false");
                method.addLine("    }", false);
            }
            if (!compElements.isEmpty()) {
                method.addLine("    " + className + " other = (" + className + ") obj");
                for (CompositeField element : compElements) {
                    method.addLine("    if (" + element.getFieldName() + " == null) {", false);
                    method.addLine("        if (other." + element.getFieldName() + " != null) {", false);
                    method.addLine("            return false");
                    method.addLine("        }", false);
                    method.addLine("    } else {", false);
                    method.addLine("        if (! " + element.getFieldName() + ".equals(other." + element.getFieldName() + ")) {", false);
                    method.addLine("            return false");
                    method.addLine("        }", false);
                    method.addLine("    }", false);
                }
            }
            method.addLine("    return true");
            method.addLine("}", false);
            method.addLine("return false");
            method.addMethodCloseStatement();

            method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                    false, true, intType, "hashCode", null, null,
                    "Returns a hash code for this object", "a hash code value for this object.", null);
            if (null != parentClass) {
                method.addLine("int hash = super.hashCode()");
            } else {
                method.addLine("int hash = 7");
            }
            for (CompositeField element : compElements) {
                method.addLine("hash = 83 * hash + (" + element.getFieldName() + " != null ? " + element.getFieldName() + ".hashCode() : 0)");
            }
            method.addLine("return hash");
            method.addMethodCloseStatement();
        }

        // create toString method
        if (supportsToString) {
            CompositeField strType = createCompositeElementsDetails(file, false, "return",
                    TypeUtils.createTypeReference(null, null, "_String", false),
                    false, true, "return value");
            MethodWriter method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                    false, true, strType, "toString", null, null,
                    "Returns a String object representing this type's value.",
                    "a string representation of the value of this object", null);
            method.addLine("StringBuilder buf = new StringBuilder()");
            method.addLine("buf.append(\"(" + className + ": \")");

            String prefixSeparator = "";

            if (parentClass != null) {
                method.addLine("buf.append(super.toString())");
                prefixSeparator = ", ";
            }
            for (CompositeField element : compElements) {
                method.addLine("buf.append(\"" + prefixSeparator + element.getFieldName() + "=\")");
                method.addLine("buf.append(" + element.getFieldName() + ")");
                prefixSeparator = ", ";
            }
            method.addLine("buf.append(')')");
            method.addLine("return buf.toString()");
            method.addMethodCloseStatement();
        }

        // create getMALValue method
        if (supportsToValue && !abstractComposite) {
            addCompositeCloneMethod(file, fqName);
        }

        // create encode method
        MethodWriter method = encodeMethodOpen(file);
        if (parentClass != null) {
            method.addSuperMethodStatement("encode", "encoder");
        }

        // Add the if condition to check if there are null fields for non-nullable fields!
        for (int i = 0; i < compElements.size(); i++) {
            CompositeField element = compElements.get(i);
            String fieldName = element.getFieldName();

            if (!element.isCanBeNull()) {
                method.addLine("if (" + fieldName + " == null) {", false);
                method.addLine("    throw new org.ccsds.moims.mo.mal.MALException(\"The field '" + fieldName + "' cannot be null!\")");
                method.addLine("}", false);
            }
        }

        for (CompositeField element : compElements) {
            boolean isAbstract = isAbstract(element.getTypeReference()) && !element.getTypeReference().getName().contentEquals(StdStrings.ATTRIBUTE);
            String canBeNullStr = element.isCanBeNull() ? "Nullable" : "";
            String fieldName = element.getFieldName();

            if (isAbstract) {
                if (element.isList()) { // Abstract List?
                    // The Abstract Lists do not not need an SPF because we know what we will get!
                    method.addLine(createMethodCall("encoder.encode" + canBeNullStr + "Element(" + fieldName + ")"));
                } else {
                    method.addLine(createMethodCall("encoder.encode" + canBeNullStr + "AbstractElement(" + fieldName + ")"));
                }
            } else {
                if (element.getEncodeCall() != null) {
                    method.addLine(createMethodCall("encoder.encode" + canBeNullStr + element.getEncodeCall() + "(" + fieldName + ")"));
                } else {
                    // This is when the Element is set as the abstract Attribute type
                    method.addLine(createMethodCall("encoder.encode" + canBeNullStr + "Element(" + fieldName + ")"));
                }
            }
        }
        method.addMethodCloseStatement();

        // create decode method
        method = decodeMethodOpen(file, elementType);
        if (parentClass != null) {
            method.addSuperMethodStatement("decode", "decoder");
        }
        for (CompositeField element : compElements) {
            boolean isAbstract = isAbstract(element.getTypeReference())
                    && !element.getTypeReference().getName().contentEquals(StdStrings.ATTRIBUTE);
            String canBeNullStr = element.isCanBeNull() ? "Nullable" : "";
            String castString = element.getDecodeCast();

            if (isAbstract) {
                if (element.isList()) { // Abstract List?
                    // Strip the parenthesis around the cast: "(abc) " -> "abc"
                    // Note: Yes, the string has a space at the end... that's why we have: length() - 2
                    String classPath = castString.substring(1, castString.length() - 2);
                    method.addLine(element.getFieldName() + " = " + castString
                            + createMethodCall("decoder.decode" + canBeNullStr
                                    + "Element(new " + classPath + "())"));
                } else {
                    method.addLine(element.getFieldName() + " = " + castString
                            + createMethodCall("decoder.decode" + canBeNullStr + "AbstractElement()"));
                }
            } else {
                // Needs the "." before the AttributeList because of the NullableAttributeList
                if (castString.contains(".AttributeList")) {
                    // This is when the Element is set as the abstract AttributeList type
                    String attNew = "new org.ccsds.moims.mo.mal.structures.AttributeList()";
                    method.addLine(element.getFieldName() + " = " + castString
                            + createMethodCall("decoder.decode" + canBeNullStr + element.getDecodeCall() + "(" + attNew + ")"));
                } else {
                    method.addLine(element.getFieldName() + " = " + castString
                            + createMethodCall("decoder.decode" + canBeNullStr + element.getDecodeCall()
                                    + "(" + (element.isDecodeNeedsNewCall() ? element.getNewCall() : "") + ")"));
                }
            }
        }
        method.addLine("return this");
        method.addMethodCloseStatement();

        if (!abstractComposite) {
            addTypeIdGetterMethod(file, area, service);
        }

        file.addClassCloseStatement();
        file.flush();

        createListClass(folder, area, service, className, abstractComposite, composite.getShortFormPart());
    }

    public abstract void createListClass(File folder, AreaType area, ServiceType service, String srcTypeName, boolean isAbstract, Long shortFormPart) throws IOException;

    protected final void createMultiReturnType(String destinationFolderName, String returnTypeFqName, MultiReturnType returnTypeInfo) throws IOException {
        logger.info(" > Creating multiple return class class " + returnTypeFqName);

        // create a comment for the body folder if supported
        createServiceMessageBodyFolderComment(destinationFolderName, returnTypeInfo.getArea(), returnTypeInfo.getService());

        ClassWriter file = createClassFile(destinationFolderName, returnTypeFqName.replace('.', '/'));

        file.addPackageStatement(returnTypeInfo.getArea(), returnTypeInfo.getService(), getConfig().getBodyFolder());

        file.addClassOpenStatement(returnTypeInfo.getShortName(), true, false, null, null,
                "Multi body return class for " + returnTypeInfo.getShortName() + ".");

        List<CompositeField> argsList = createOperationArguments(getConfig(), file, returnTypeInfo.getReturnTypes());

        // create attributes
        for (int i = 0; i < argsList.size(); i++) {
            CompositeField argType = argsList.get(i);
            CompositeField memType = createCompositeElementsDetails(file, true, argType.getFieldName(),
                    argType.getTypeReference(), true, true, argType.getFieldName() + ": " + argType.getComment());
            file.addClassVariable(false, false, StdStrings.PRIVATE, memType, false, (String) null);
        }

        // create blank constructor
        file.addConstructorDefault(returnTypeInfo.getShortName());

        // if we or our parents have attributes then we need a typed constructor
        MethodWriter method = file.addConstructor(StdStrings.PUBLIC, returnTypeInfo.getShortName(), argsList, null, null,
                "Constructs an instance of this type using provided values.", null);

        for (int i = 0; i < argsList.size(); i++) {
            CompositeField argType = argsList.get(i);
            method.addLine(createMethodCall("this." + argType.getFieldName() + " = " + argType.getFieldName()));
        }

        method.addMethodCloseStatement();

        // add getters and setters
        for (int i = 0; i < argsList.size(); i++) {
            CompositeField argType = createCompositeElementsDetails(file, true, argsList.get(i).getFieldName(),
                    returnTypeInfo.getReturnTypes().get(i).getSourceType(), true, true, "The new value.");
            addGetter(file, argType, null);
            addSetter(file, argType, null);
        }
        // add deprecated getters and setters
        for (int i = 0; i < argsList.size(); i++) {
            CompositeField argType = createCompositeElementsDetails(file, true, argsList.get(i).getFieldName(),
                    returnTypeInfo.getReturnTypes().get(i).getSourceType(), true, true, "The new value.");
            addGetter(file, argType, "BodyElement" + i);
            addSetter(file, argType, "BodyElement" + i);
        }

        file.addClassCloseStatement();
        file.flush();
    }

    public void addTypeShortFormDetails(ClassWriter file, AreaType area, ServiceType service, long sf) throws IOException {
        //addTypeShortForm(file, sf);

        long asf = ((long) area.getNumber()) << AREA_BIT_SHIFT;
        asf += ((long) area.getVersion()) << VERSION_BIT_SHIFT;

        if (service != null) {
            asf += ((long) service.getNumber()) << SERVICE_BIT_SHIFT;
        }

        if (sf >= 0) {
            asf += sf;
        } else {
            asf += Long.parseLong(Integer.toHexString((int) sf).toUpperCase().substring(2), 16);
        }

        addShortForm(file, asf);
        addTypeId(file, asf);
    }

    protected abstract void addShortForm(ClassWriter file, long sf) throws IOException;

    protected void addTypeId(ClassWriter file, long sf) throws IOException {
        CompositeField var = createCompositeElementsDetails(file, false, "TYPE_ID",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "TypeId", false),
                true, false, "The TypeId of this Element.");
        file.addClassVariable(true, true, StdStrings.PUBLIC, var, false, "(SHORT_FORM)");
    }

    public void addTypeIdGetterMethod(ClassWriter file, AreaType area, ServiceType service) throws IOException {
        CompositeField typeIdType = createCompositeElementsDetails(file, false, "return",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "TypeId", false),
                true, true, null);

        MethodWriter method = file.addMethodOpenStatement(true, false, StdStrings.PUBLIC,
                false, true, typeIdType, "getTypeId", null, null,
                "Returns the TypeId of this element.",
                "The TypeId of this element.", null);
        method.addLine("return TYPE_ID");
        method.addMethodCloseStatement();
    }

    protected static void addGetter(ClassWriter file, CompositeField element, String backwardCompatibility) throws IOException {
        String getOpPrefix = "get";
        String attributeName = element.getFieldName();
        boolean isDeprecated = (backwardCompatibility != null);
        String getOpName = (backwardCompatibility == null) ? StubUtils.preCap(attributeName) : backwardCompatibility;

        MethodWriter method = file.addMethodOpenStatement(false, false, true, false, StdStrings.PUBLIC,
                !element.isCanBeNull(), !element.isCanBeNull() && element.isActual(), element,
                getOpPrefix + getOpName, null, null, "Returns the field " + attributeName,
                "The field " + attributeName, null, isDeprecated);
        method.addLine("return " + attributeName);
        method.addMethodCloseStatement();
    }

    @Deprecated
    protected static void addSetter(ClassWriter file, CompositeField element, String backwardCompatibility) throws IOException {
        String setOpPrefix = "set";
        String attributeName = element.getFieldName();
        //boolean isDeprecated = (backwardCompatibility != null);
        boolean isDeprecated = true;
        String getOpName = (backwardCompatibility == null) ? StubUtils.preCap(attributeName) : backwardCompatibility;

        if (StdStrings.BOOLEAN.equals(element.getTypeName()) && getOpName.startsWith("Is")) {
            getOpName = getOpName.substring(2);
        }

        CompositeField fld = new CompositeField(element, "__newValue", "The new value.");
        MethodWriter method = file.addMethodOpenStatement(false, false, false, false,
                StdStrings.PUBLIC, false, true, null,
                setOpPrefix + getOpName, Arrays.asList(fld), null, "Sets the field " + attributeName,
                null, null, isDeprecated);
        method.addLine(attributeName + " = __newValue");
        method.addMethodCloseStatement();
    }

    protected void addCompositeCloneMethod(ClassWriter file, String fqName) throws IOException {
    }

    protected CompositeField createServiceProviderSkeletonSendHandler(ClassWriter file, String argumentName, String argumentComment) {
        return createCompositeElementsDetails(file, false, argumentName,
                TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, StdStrings.MALINTERACTION, false),
                false, true, argumentComment);
    }

    public String createAdapterMethodsArgs(List<TypeInfo> typeInfos, String argNamePrefix, boolean precedingArgs, boolean moreArgs) {
        if (typeInfos == null) {
            return "";
        }

        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < typeInfos.size(); i++) {
            TypeInfo ti = typeInfos.get(i);

            boolean morePrecedingArgs = precedingArgs || (i > 0);
            boolean evenMoreArgs = moreArgs && i == (typeInfos.size() - 1);
            buf.append(createAdapterMethodsArgs(ti, argNamePrefix, i, morePrecedingArgs, evenMoreArgs));
        }

        return buf.toString();
    }

    public String createAdapterMethodsArgs(TypeInfo typeInfo, String argName, int argIndex, boolean precedingArgs, boolean moreArgs) {
        String retStr = "";

        if ((typeInfo.getTargetType() != null) && !(StdStrings.VOID.equals(typeInfo.getTargetType()))) {
            if (precedingArgs) {
                retStr = ",\n                ";
            }

            if (typeInfo.isNativeType()) {
                // If is is Java native (Short, Long, etc), then needs to be wrapped into a Union type!
                String unionType = getConfig().getAreaPackage(StdStrings.MAL)
                        + "mal." + getConfig().getStructureFolder() + "." + StdStrings.UNION;

                AttributeTypeDetails details = getAttributeDetails(typeInfo.getSourceType());
                String av = argName + createMethodCall(".getBodyElement(") + argIndex + ", "
                        + "new " + unionType + "(" + details.getDefaultValue() + "))";
                retStr += "(" + av + " == null) ? null : ((" + unionType + ") " + av + ").get" + details.getMalType() + "Value()";
            } else {
                // Not Java native...
                String expectedType = generateExpectedType(typeInfo);
                String cast = typeInfo.getTargetType();
                cast = cast.replace(".ElementList", ".HeterogeneousList");
                String av = argName + createMethodCall(".getBodyElement(") + argIndex + ", " + expectedType + ")";
                retStr += "(" + cast + ") " + av;
            }

            if (moreArgs) {
                retStr += ",\n                ";
            }
        }

        return retStr;
    }

    private String generateExpectedType(TypeInfo ti) {
        // Not Java native...
        String cast = ti.getTargetType();
        String at = null;

        if (!isAbstract(ti.getSourceType())) {
            CompositeField ce = createCompositeElementsDetails(null, false,
                    "", ti.getSourceType(), true, true, null);
            at = ce.getNewCall();
        }
        if (at == null && cast.contains("List") && !cast.contains(".Element")) {
            at = "new " + cast + "()";
        }
        if (cast.contains(".MOObject")) {
            at = "null";
        }

        return at;
    }

    public String checkForReservedWords(String arg) {
        if (arg == null) {
            return arg;
        }

        String replacementWord = reservedWordsMap.get(arg);
        return (replacementWord != null) ? replacementWord : arg;
    }

    public String createConsumerPatternCall(OperationSummary op) {
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

    public String getOperationInstanceType(OperationSummary op) {
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

    public CompositeField createOperationReturnType(LanguageWriter file, AreaType area, ServiceType service, OperationSummary op) {
        switch (op.getPattern()) {
            case REQUEST_OP: {
                if (op.getRetTypes() != null) {
                    return createReturnReference(createReturnType(file, area, service, op.getName(), "Response", op.getRetTypes()));
                }
                break;
            }
            case INVOKE_OP:
            case PROGRESS_OP: {
                if ((op.getAckTypes() != null) && (!op.getAckTypes().isEmpty())) {
                    return createReturnReference(createReturnType(file, area, service, op.getName(), "Ack", op.getAckTypes()));
                }
                break;
            }
        }

        return null;
    }

    public List<CompositeField> createOperationArguments(GeneratorConfiguration config,
            LanguageWriter file, List<TypeInfo> opArgs) {
        if (opArgs == null) {
            return new LinkedList<>();
        }

        List<CompositeField> rv = new LinkedList<>();

        for (int i = 0; i < opArgs.size(); i++) {
            TypeInfo ti = opArgs.get(i);
            TypeReference tir = ti.getSourceType();
            String argName = ti.getFieldName();

            if (argName == null) {
                String shortName = TypeUtils.shortTypeName(config.getNamingSeparator(), ti.getTargetType());
                // Remove any ">" from the ObjectRef types
                shortName = shortName.replace(">", "_");
                argName = "_" + shortName + i;

                // Give a Warning here!!
                logger.warn("Warning! The field name is not set in the xml file! "
                        + "The autogenerated value is: " + argName);
            }

            String cmt = argName + " Argument number " + i + " as defined by the service operation";
            if ((null != ti.getFieldName()) && (null != ti.getFieldComment())) {
                cmt = ti.getFieldComment();
            }

            CompositeField argType = createCompositeElementsDetails(file, true,
                    argName, tir, true, true, cmt);

            rv.add(argType);
        }

        return rv;
    }

    public String createOperationArgReturn(LanguageWriter file, MethodWriter method,
            TypeInfo typeInfo, String argName, int argIndex) throws IOException {
        if ((typeInfo.getTargetType() != null) && !(StdStrings.VOID.equals(typeInfo.getTargetType()))) {
            String eleType = "Object";
            String tv = argName + argIndex;
            String av;
            String returnParameter;

            if (typeInfo.isNativeType()) {
                AttributeTypeDetails details = getAttributeDetails(typeInfo.getSourceType());
                String elementType = createElementType(StdStrings.MAL, null, StdStrings.UNION);
                av = argName + ".getBodyElement(" + argIndex + ", new " + elementType + "(" + details.getDefaultValue() + "))";
                returnParameter = "(" + tv + " == null) ? null : ((" + elementType + ") " + tv + ").get" + details.getMalType() + "Value()";
            } else {
                String cast = typeInfo.getTargetType();
                cast = cast.replace(".ElementList", ".HeterogeneousList");
                String expectedType = generateExpectedType(typeInfo);
                av = argName + ".getBodyElement(" + argIndex + ", " + expectedType + ")";
                returnParameter = "(" + cast + ") " + tv;
            }

            method.addLine(eleType + " " + tv + " = (" + eleType + ") " + av);
            return returnParameter;
        }

        return "";
    }

    protected CompositeField createReturnType(LanguageWriter file, AreaType area,
            ServiceType service, String opName, String messageType, List<TypeInfo> returnTypes) {
        if (returnTypes == null || returnTypes.isEmpty()) {
            return null;
        }

        if (returnTypes.size() == 1) {
            return createCompositeElementsDetails(file, false, "return",
                    returnTypes.get(0).getSourceType(), true, true, null);
        }

        String shortName = StubUtils.preCap(opName) + messageType;
        String rt = getConfig().getAreaPackage(area.getName()) + area.getName().toLowerCase() + "." + service.getName().toLowerCase() + "." + getConfig().getBodyFolder() + "." + shortName;
        if (!multiReturnTypeMap.containsKey(rt)) {
            multiReturnTypeMap.put(rt, new MultiReturnType(rt, area, service, shortName, returnTypes));
        }

        return createCompositeElementsDetails(file, false, "return",
                TypeUtils.createTypeReference(area.getName().toLowerCase(), service.getName().toLowerCase() + "." + getConfig().getBodyFolder(), shortName, false),
                false, true, null);
    }

    /**
     * Creates a set of argument names based on the type, wrapping the type in a
     * Union if a native type.
     *
     * @param typeNames The list of arguments.
     * @return The argument string.
     */
    public String createArgNameOrNull(List<TypeInfo> typeNames) {
        if (typeNames == null || typeNames.isEmpty()) {
            return getConfig().getNullValue();
        }

        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < typeNames.size(); i++) {
            TypeInfo ti = typeNames.get(i);
            if (i > 0) {
                buf.append(", ");
            }

            String argName = ti.getFieldName();

            if (argName == null) {
                argName = "_" + TypeUtils.shortTypeName(getConfig().getNamingSeparator(), ti.getTargetType()) + i;
            }

            if (ti.isNativeType()) {
                buf.append("(").append(argName).append(" == null)");
                buf.append(" ? null : new ");
                buf.append(getConfig().getAreaPackage(StdStrings.MAL));
                buf.append("mal.").append(getConfig().getStructureFolder()).append(".").append(StdStrings.UNION);
                buf.append("(").append(argName).append(")");
            } else {
                buf.append(argName);
            }
        }

        String ret = buf.toString();
        ret = ret.replaceAll("<", "_");
        ret = ret.replaceAll(">", "_");
        return ret;
    }

    public MethodWriter encodeMethodOpen(ClassWriter file) throws IOException {
        String throwsMALException = createElementType(StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        CompositeField fld = createCompositeElementsDetails(file, false, "encoder",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "MALEncoder", false),
                false, true, "The encoder to use for encoding.");
        return file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                false, true, null, "encode", Arrays.asList(fld), throwsMALException,
                "Encodes the value of this object using the provided MALEncoder.", null,
                Arrays.asList(throwsMALException + " if any encoding errors are detected."));
    }

    public MethodWriter decodeMethodOpen(ClassWriter file, CompositeField returnType) throws IOException {
        String throwsMALException = createElementType(StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        CompositeField fld = createCompositeElementsDetails(file, false, "decoder",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "MALDecoder", false),
                false, true, "The decoder to use for decoding.");
        return file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                false, false, returnType, "decode", Arrays.asList(fld),
                throwsMALException, "Decodes the value of this object using the provided MALDecoder.", "Returns this object.",
                Arrays.asList(throwsMALException + " if any decoding errors are detected."));
    }

    protected String createProviderSkeletonHandlerSwitch() {
        return "interaction.getOperation().getNumber().getValue()";
    }

    public CompositeField createReturnReference(CompositeField targetType) {
        return targetType;
    }

    public String createMethodCall(String call) {
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

    public abstract String createAreaHelperClassInitialValue(String areaVar, short areaVersion);

    protected abstract void createRequiredPublisher(String destinationFolderName, String fqPublisherName, RequiredPublisher op) throws IOException;

    protected abstract void addVectorAddStatement(LanguageWriter file, MethodWriter method, String variable, String parameter) throws IOException;

    protected abstract void addVectorRemoveStatement(LanguageWriter file, MethodWriter method, String variable, String parameter) throws IOException;

    protected abstract String createStaticClassReference(String type);

    public abstract String addressOf(String object);

    protected abstract String createArraySize(boolean isActual, String type, String variable);

    protected abstract String malStringAsElement(LanguageWriter file);

    protected abstract String errorCodeAsReference(LanguageWriter file, String ref);

    protected abstract String getIntCallMethod();

    protected abstract String getOctetCallMethod();

    public abstract String getRegisterMethodName();

    public abstract String getDeregisterMethodName();

    public abstract String getNullValue();

    public abstract ClassWriter createClassFile(File folder, String className) throws IOException;

    public abstract ClassWriter createClassFile(String destinationFolderName, String className) throws IOException;

    protected abstract InterfaceWriter createInterfaceFile(File folder, String className) throws IOException;

    protected abstract InterfaceWriter createInterfaceFile(String destinationFolderName, String className) throws IOException;
}
