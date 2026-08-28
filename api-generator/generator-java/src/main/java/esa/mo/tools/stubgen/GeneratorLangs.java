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

import esa.mo.tools.stubgen.java.JavaCompositeClass;
import esa.mo.tools.stubgen.java.JavaElementFactory;
import esa.mo.tools.stubgen.java.JavaServiceInfo;
import esa.mo.tools.stubgen.java.JavaExceptions;
import esa.mo.tools.stubgen.java.JavaConsumer;
import esa.mo.tools.stubgen.java.JavaEnumerations;
import esa.mo.tools.stubgen.java.JavaHelpers;
import esa.mo.tools.stubgen.specification.AttributeTypeDetails;
import esa.mo.tools.stubgen.specification.CompositeField;
import esa.mo.tools.stubgen.specification.FieldInfo;
import esa.mo.tools.stubgen.specification.InteractionPatternEnum;
import esa.mo.tools.stubgen.specification.MultiReturnType;
import esa.mo.tools.stubgen.specification.OperationSummary;
import esa.mo.tools.stubgen.specification.ServiceSummary;
import esa.mo.tools.stubgen.specification.StdStrings;
import esa.mo.tools.stubgen.specification.TypeUtils;
import esa.mo.tools.stubgen.writers.ClassWriter;
import esa.mo.tools.stubgen.writers.InterfaceMethodBuilder;
import esa.mo.tools.stubgen.writers.InterfaceWriter;
import esa.mo.tools.stubgen.writers.LanguageWriter;
import esa.mo.tools.stubgen.writers.MethodWriter;
import esa.mo.tools.stubgen.writers.TargetWriter;
import esa.mo.xsd.*;
import esa.mo.xsd.util.XmlSpecification;
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

        addAttributeType(StdStrings.XML, "hexBinary", typeInformation.getAttributeDetails(StdStrings.MAL, StdStrings.BLOB));
        addAttributeType(StdStrings.XML, "boolean", typeInformation.getAttributeDetails(StdStrings.MAL, StdStrings.BOOLEAN));
        addAttributeType(StdStrings.XML, "double", typeInformation.getAttributeDetails(StdStrings.MAL, StdStrings.DOUBLE));
        addAttributeType(StdStrings.XML, "duration", typeInformation.getAttributeDetails(StdStrings.MAL, StdStrings.DURATION));
        addAttributeType(StdStrings.XML, "float", typeInformation.getAttributeDetails(StdStrings.MAL, StdStrings.FLOAT));
        addAttributeType(StdStrings.XML, "int", typeInformation.getAttributeDetails(StdStrings.MAL, StdStrings.INTEGER));
        addAttributeType(StdStrings.XML, "NCName", typeInformation.getAttributeDetails(StdStrings.MAL, StdStrings.IDENTIFIER));
        addAttributeType(StdStrings.XML, "long", typeInformation.getAttributeDetails(StdStrings.MAL, StdStrings.LONG));
        addAttributeType(StdStrings.XML, "byte", typeInformation.getAttributeDetails(StdStrings.MAL, StdStrings.OCTET));
        addAttributeType(StdStrings.XML, "short", typeInformation.getAttributeDetails(StdStrings.MAL, StdStrings.SHORT));
        addAttributeType(StdStrings.XML, "unsignedInt", typeInformation.getAttributeDetails(StdStrings.MAL, StdStrings.UINTEGER));
        addAttributeType(StdStrings.XML, "unsignedLong", typeInformation.getAttributeDetails(StdStrings.MAL, StdStrings.ULONG));
        addAttributeType(StdStrings.XML, "unsignedByte", typeInformation.getAttributeDetails(StdStrings.MAL, StdStrings.UOCTET));
        addAttributeType(StdStrings.XML, "unsignedShort", typeInformation.getAttributeDetails(StdStrings.MAL, StdStrings.USHORT));
        addAttributeType(StdStrings.XML, "string", typeInformation.getAttributeDetails(StdStrings.MAL, StdStrings.STRING));
        addAttributeType(StdStrings.XML, "dateTime", typeInformation.getAttributeDetails(StdStrings.MAL, StdStrings.TIME));
        addAttributeType(StdStrings.XML, "dateTime", typeInformation.getAttributeDetails(StdStrings.MAL, StdStrings.FINETIME));
        addAttributeType(StdStrings.XML, "anyURI", typeInformation.getAttributeDetails(StdStrings.MAL, StdStrings.URI));
        addAttributeType(StdStrings.XML, "ObjectRef", typeInformation.getAttributeDetails(StdStrings.MAL, StdStrings.OBJECTREF));

        AttributeTypeDetails att = new AttributeTypeDetails(typeInformation, "Element", true, "Object", "");
        addAttributeType(StdStrings.XML, "Element", att);

        AttributeTypeDetails att1 = new AttributeTypeDetails(typeInformation, "Object", true, "Object", "");
        addAttributeType(StdStrings.XML, "Element", att1);
    }

    @Override
    public void loadXML(XmlSpecification xml) throws IOException, JAXBException {
        super.loadXML(xml);
        SpecificationType spec = xml.getSpecType();

        // load in COM object/event definitions
        for (AreaType area : spec.getArea()) {
            for (ServiceType service : area.getService()) {
                if (service instanceof ExtendedServiceType) {
                    ExtendedServiceType eService = (ExtendedServiceType) service;
                    SupportedFeatures features = eService.getFeatures();

                    if (features == null) {
                        continue;
                    }

                    if (features.getObjects() != null) {
                        for (ModelObjectType obj : features.getObjects().getObject()) {
                            String name = String.valueOf(obj.getNumber());
                            TypeKey key = new TypeKey(area.getName(), service.getName(), name);
                            comObjectMap.put(key, obj);
                        }
                    }

                    if (features.getEvents() != null) {
                        for (ModelObjectType obj : features.getEvents().getEvent()) {
                            String name = String.valueOf(obj.getNumber());
                            TypeKey key = new TypeKey(area.getName(), service.getName(), name);
                            comObjectMap.put(key, obj);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void generate(String destinationFolderName, XmlSpecification xml,
            JAXBElement rootNode) throws IOException, JAXBException {
        long totalTime = System.currentTimeMillis();
        SpecificationType spec = xml.getSpecType();

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
            JavaHelpers helper = new JavaHelpers(this, typeInformation);
            logger.info(" > Creating Area Helper class: " + area.getName());
            helper.createAreaHelperClass(areaFolder, area);

            logger.info(" > Creating Area Element Factory class: " + area.getName());
            JavaElementFactory elementFactory = new JavaElementFactory(this, typeInformation);
            elementFactory.createAreaElementFactoryClass(areaFolder, area);

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
                createAreaStructureFolderComment(structureFolder, area.getName());

                ConcurrentLinkedQueue<Exception> errors_1 = new ConcurrentLinkedQueue<>();

                // create area level data types
                area.getDataTypes().getFundamentalOrAttributeOrComposite().parallelStream().forEach(oType -> {
                    try {
                        if (oType instanceof FundamentalType) {
                            createFundamentalClass(structureFolder, area.getName(), null, (FundamentalType) oType);
                        } else if (oType instanceof AttributeType) {
                            String aName = ((AttributeType) oType).getName();
                            createListClass(structureFolder, area, null, aName,
                                    false, ((AttributeType) oType).getShortFormPart());
                            CompositeField fld = createCompositeElementsDetails(null, false, "fld",
                                    TypeUtils.createTypeReference(area.getName(), null, aName, false),
                                    true, true, "cmt");
                        } else if (oType instanceof CompositeType) {
                            CompositeType compType = (CompositeType) oType;
                            logger.info(" > Creating Composite class: " + compType.getName());
                            JavaCompositeClass compositeClass = new JavaCompositeClass(this, typeInformation);
                            compositeClass.createCompositeClass(structureFolder, area, null, compType);
                        } else if (oType instanceof EnumerationType) {
                            JavaEnumerations enumerations = new JavaEnumerations(this, typeInformation);
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
        createServiceFolderComment(serviceFolder, area.getName(), service);
        // create service helper
        JavaHelpers helper = new JavaHelpers(this, typeInformation);
        logger.info(" > Creating service Helper class: " + service.getName());
        helper.createServiceHelperClass(serviceFolder, area.getName(), service, summary);

        // create service info
        JavaServiceInfo serviceInfo = new JavaServiceInfo(this, typeInformation);
        logger.info(" > Creating ServiceInfo class: " + service.getName());
        serviceInfo.createServiceInfoClass(serviceFolder, area, service, summary);

        // create consumer classes
        createServiceConsumerClasses(serviceFolder, area.getName(), service.getName(), summary);
        // create provider classes
        createServiceProviderClasses(serviceFolder, area.getName(), service.getName(), summary, requiredPublishers);

        // if service level types exist
        if (generateStructures && (service.getDataTypes() != null) && !service.getDataTypes().getCompositeOrEnumeration().isEmpty()) {
            // create structure folder
            File structureFolder = StubUtils.createFolder(serviceFolder, getConfig().getStructureFolder());
            // create a comment for the structure folder if supported
            createServiceStructureFolderComment(structureFolder, area.getName(), service.getName());
            String name;

            for (Object oType : service.getDataTypes().getCompositeOrEnumeration()) {
                if (oType instanceof EnumerationType) {
                    JavaEnumerations enumerations = new JavaEnumerations(this, typeInformation);
                    EnumerationType enumType = (EnumerationType) oType;
                    logger.info(" > Creating Enumeration class: " + enumType.getName());
                    enumerations.createEnumerationClass(structureFolder, area, service, (EnumerationType) oType);
                    name = ((EnumerationType) oType).getName();
                } else if (oType instanceof CompositeType) {
                    CompositeType compType = (CompositeType) oType;
                    logger.info(" > Creating Composite class: " + compType.getName());
                    JavaCompositeClass compositeClass = new JavaCompositeClass(this, typeInformation);
                    compositeClass.createCompositeClass(structureFolder, area, service, compType);
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

    protected void createServiceConsumerClasses(File serviceFolder, String area,
            String service, ServiceSummary summary) throws IOException {
        logger.info(" > Creating consumer classes: " + service);
        File consumerFolder = StubUtils.createFolder(serviceFolder, CONSUMER_FOLDER);
        // create a comment for the consumer folder if supported
        createServiceConsumerFolderComment(consumerFolder, area, service);
        JavaConsumer consumer = new JavaConsumer(this, typeInformation, supportsToValue, true);
        logger.info(" > Creating consumer adapter: " + service);
        consumer.createServiceConsumerAdapter(consumerFolder, area, service, summary);
        logger.info(" > Creating consumer stub: " + service);
        consumer.createServiceConsumerStub(consumerFolder, area, service, summary);
    }

    protected void createServiceProviderClasses(File serviceFolder, String area, String service,
            ServiceSummary summary, Map<String, RequiredPublisher> requiredPublishers) throws IOException {
        logger.info(" > Creating provider classes: " + service);
        File providerFolder = StubUtils.createFolder(serviceFolder, PROVIDER_FOLDER);
        // create a comment for the provider folder if supported
        createServiceProviderFolderComment(providerFolder, area, service);
        createServiceProviderHandler(providerFolder, area, service, summary);
        createServiceProviderSkeleton(providerFolder, area, service, summary, requiredPublishers);
        createServiceProviderInheritance(providerFolder, area, service, summary);
        createServiceProviderInteractions(providerFolder, area, service, summary);
    }

    protected void createServiceProviderInheritance(File providerFolder, String area,
            String service, ServiceSummary summary) throws IOException {
        logger.info(" > Creating provider inheritance class: " + service);
        createServiceProviderSkeletonHandler(providerFolder, area, service, summary, false);
    }

    protected void createServiceProviderInteractions(File providerFolder, String area,
            String service, ServiceSummary summary) throws IOException {
        for (OperationSummary op : summary.getOperations()) {
            if (op.getPattern() == InteractionPatternEnum.INVOKE_OP) {
                createServiceProviderInvokeInteractionClass(providerFolder, area, service, op);
            } else if (op.getPattern() == InteractionPatternEnum.PROGRESS_OP) {
                createServiceProviderProgressInteractionClass(providerFolder, area, service, op);
            }
        }
    }

    /**
     * Adds the dedicated MO error exceptions declared by the operation to the
     * supplied interface method builder, each with its description (taken from
     * the error reference, or the referenced error definition, falling back to a
     * generic message). Does nothing if the operation declares no errors.
     *
     * @param op The operation.
     * @param builder The interface method builder to add the thrown errors to.
     */
    protected void addOperationErrorsThrows(OperationSummary op, InterfaceMethodBuilder builder) {
        OperationErrorList errors = op.getErrors();
        if (errors == null) {
            return;
        }
        for (Object e : errors.getErrorOrErrorRef()) {
            if (!(e instanceof ErrorReferenceType)) {
                continue;
            }
            ErrorReferenceType error = (ErrorReferenceType) e;
            String className = JavaExceptions.convertErrorToClassname(error.getType().getName());
            String fullyQualified = "org.ccsds.moims.mo." + error.getType().getArea().toLowerCase() + "." + className;
            String comment = error.getComment();
            if (comment == null || comment.isEmpty()) {
                // The error reference rarely carries its own comment, so fall
                // back to the comment of the referenced error definition.
                ErrorDefinitionType def = typeInformation.getErrorDefinition(
                        error.getType().getArea(), error.getType().getName());
                comment = (def != null && def.getComment() != null && !def.getComment().isEmpty())
                        ? def.getComment() : "if the corresponding MO error occurs";
            }
            builder.addThrows(fullyQualified, comment);
        }
    }

    /**
     * Returns true if at least one operation of the given interaction pattern
     * declares one or more MO errors. Used to decide whether a dispatch handler
     * needs to catch and re-wrap MOErrorExceptions (a catch for an exception
     * that can never be thrown is a compile error).
     *
     * @param summary The service summary.
     * @param pattern The interaction pattern.
     * @return true if any operation of that pattern declares an error.
     */
    protected boolean hasDeclaredErrors(ServiceSummary summary, InteractionPatternEnum pattern) {
        for (OperationSummary op : summary.getOperations()) {
            if (op.getPattern() != pattern) {
                continue;
            }
            OperationErrorList errors = op.getErrors();
            if (errors != null) {
                for (Object e : errors.getErrorOrErrorRef()) {
                    if (e instanceof ErrorReferenceType) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    protected void createServiceProviderHandler(File providerFolder, String area,
            String service, ServiceSummary summary) throws IOException {
        logger.info(" > Creating provider handler interface: " + service);

        String handlerName = service + "Handler";
        InterfaceWriter file = createInterfaceFile(providerFolder, handlerName);

        file.addPackageStatement(area, service, PROVIDER_FOLDER);

        file.addInterfaceOpenStatement(handlerName, null,
                "Interface that providers of the " + service + " service must implement to handle the operations of that service.");

        CompositeField intHandler = createCompositeElementsDetails(file, false, "interaction",
                TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, StdStrings.MALINTERACTION, false),
                false, true, "The MAL object representing the interaction in the provider.");
        String throwsMALException = typeInformation.createElementType(StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String throwsInteractionException = typeInformation.createElementType(StdStrings.MAL, null, null, StdStrings.MALINTERACTIONEXCEPTION);

        for (OperationSummary op : summary.getOperations()) {
            if (InteractionPatternEnum.PUBSUB_OP == op.getPattern()) {
                continue;
            }

            List<CompositeField> opArgs = createOperationArguments(getConfig(), file, op.getArgTypes());
            CompositeField opRetType = null;
            String opRetComment = null;
            CompositeField serviceHandler = intHandler;

            if (InteractionPatternEnum.REQUEST_OP == op.getPattern()) {
                opRetType = createOperationReturnType(file, area, service, op);
                opRetComment = "The return value of the operation";
            } else if ((InteractionPatternEnum.INVOKE_OP == op.getPattern()) || (InteractionPatternEnum.PROGRESS_OP == op.getPattern())) {
                serviceHandler = createCompositeElementsDetails(file, false, "interaction",
                        TypeUtils.createTypeReference(area,
                                service + "." + PROVIDER_FOLDER, StubUtils.preCap(op.getName()) + "Interaction", false),
                        false, true, "The MAL object representing the interaction in the provider.");
            }

            // Prepend the operation's dedicated MO error exceptions to the
            // throws clause so providers can throw them directly (the layer
            // above re-wraps them into a MALInteractionException).
            InterfaceMethodBuilder operation = file.interfaceMethod(op.getName()).returns(opRetType)
                    .addArguments(opArgs).addArgument(serviceHandler);
            addOperationErrorsThrows(op, operation);
            operation.addThrows(throwsInteractionException,
                    "if there is a problem during the interaction as defined by the MAL specification.")
                    .addThrows(throwsMALException, "if there is an implementation exception")
                    .comment("Implements the operation " + op.getName())
                    .returnComment(opRetComment)
                    .declare();
        }

        CompositeField skel = createCompositeElementsDetails(file, false, "skeleton",
                TypeUtils.createTypeReference(area, service + "." + PROVIDER_FOLDER, service + "Skeleton", false),
                false, true, "The skeleton to be used.");
        file.interfaceMethod("setSkeleton").addArgument(skel)
                .comment("Sets the skeleton to be used for creation of publishers.")
                .declare();

        file.addInterfaceCloseStatement();
        file.flush();
    }

    protected void createServiceProviderInvokeInteractionClass(File providerFolder,
            String area, String service, OperationSummary op) throws IOException {
        String className = StubUtils.preCap(op.getName()) + "Interaction";
        logger.info(" > Creating provider invoke interaction class: " + className);

        ClassWriter file = createClassFile(providerFolder, className);

        file.addPackageStatement(area, service, PROVIDER_FOLDER);

        String throwsMALException = typeInformation.createElementType(StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String throwsInteractionException = typeInformation.createElementType(StdStrings.MAL, null, null, StdStrings.MALINTERACTIONEXCEPTION);
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
                "Provider INVOKE interaction class for " + service + "::" + op.getName() + " operation.");

        file.addClassVariable(false, false, StdStrings.PRIVATE, opTypeVar, false, (String) null);

        MethodWriter method = file.addConstructor(StdStrings.PUBLIC, className,
                createCompositeElementsDetails(file, false, "interaction",
                        TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALInvoke", false),
                        false, true, "The MAL interaction action object to use."), false, null,
                "Wraps the provided MAL interaction object with methods for sending responses to an INVOKE interaction from a provider.", null);
        method.addLine("this.interaction = interaction;");
        method.addMethodCloseStatement();

        method = file.method("getInteraction").returns(opType)
                .comment("Returns the MAL interaction object used for returning messages from the provider.")
                .returnComment("The MAL interaction object provided in the constructor").open();
        method.addLine("return interaction;");
        method.addMethodCloseStatement();

        method = file.method("sendAcknowledgement").returns(msgType)
                .addArguments(createOperationArguments(getConfig(), file, op.getAckTypes()))
                .comment("Sends a INVOKE acknowledge to the consumer")
                .returnComment("Returns the MAL message created by the acknowledge")
                .addThrows(throwsInteractionException, "if there is a problem during the interaction as defined by the MAL specification.")
                .addThrows(throwsMALException, "if there is an implementation exception").open();
        method.addLine("return interaction.sendAcknowledgement(" + createArgNameOrNull(op.getAckTypes()) + ");");
        method.addMethodCloseStatement();

        method = file.method("sendResponse").returns(msgType)
                .addArguments(createOperationArguments(getConfig(), file, op.getRetTypes()))
                .comment("Sends a INVOKE response to the consumer")
                .returnComment("Returns the MAL message created by the response")
                .addThrows(throwsInteractionException, "if there is a problem during the interaction as defined by the MAL specification.")
                .addThrows(throwsMALException, "if there is an implementation exception").open();
        method.addLine("return interaction.sendResponse(" + createArgNameOrNull(op.getRetTypes()) + ");");
        method.addMethodCloseStatement();

        createServiceProviderInteractionErrorHandlers(file, false, msgType, errType, throwsInteractionException, throwsMALException);

        file.addClassCloseStatement();

        file.flush();
    }

    protected void createServiceProviderProgressInteractionClass(File providerFolder,
            String area, String service, OperationSummary op) throws IOException {
        String className = StubUtils.preCap(op.getName()) + "Interaction";
        logger.info(" > Creating provider progress interaction class: " + className);

        ClassWriter file = createClassFile(providerFolder, className);

        file.addPackageStatement(area, service, PROVIDER_FOLDER);

        String throwsMALException = typeInformation.createElementType(StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String throwsInteractionException = typeInformation.createElementType(StdStrings.MAL, null, null, StdStrings.MALINTERACTIONEXCEPTION);
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
                "Provider PROGRESS interaction class for " + service + "::" + op.getName() + " operation.");

        file.addClassVariable(false, false, StdStrings.PRIVATE, opTypeVar, false, (String) null);

        MethodWriter method = file.addConstructor(StdStrings.PUBLIC, className,
                createCompositeElementsDetails(file, false, "interaction",
                        TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALProgress", false),
                        false, true, "The MAL interaction action object to use."), false, null,
                "Wraps the provided MAL interaction object with methods for sending responses to an PROGRESS interaction from a provider.", null);
        method.addLine("this.interaction = interaction;");
        method.addMethodCloseStatement();

        method = file.method("getInteraction").returns(opType)
                .comment("Returns the MAL interaction object used for returning messages from the provider.")
                .returnComment("The MAL interaction object provided in the constructor").open();
        method.addLine("return interaction;");
        method.addMethodCloseStatement();

        method = file.method("sendAcknowledgement").returns(msgType)
                .addArguments(createOperationArguments(getConfig(), file, op.getAckTypes()))
                .comment("Sends a PROGRESS acknowledge to the consumer")
                .returnComment("Returns the MAL message created by the acknowledge")
                .addThrows(throwsInteractionException, "if there is a problem during the interaction as defined by the MAL specification.")
                .addThrows(throwsMALException, "if there is an implementation exception").open();
        method.addLine("return interaction.sendAcknowledgement(" + createArgNameOrNull(op.getAckTypes()) + ");");
        method.addMethodCloseStatement();

        method = file.method("sendUpdate").returns(msgType)
                .addArguments(createOperationArguments(getConfig(), file, op.getUpdateTypes()))
                .comment("Sends a PROGRESS update to the consumer")
                .returnComment("Returns the MAL message created by the update")
                .addThrows(throwsInteractionException, "if there is a problem during the interaction as defined by the MAL specification.")
                .addThrows(throwsMALException, "if there is an implementation exception").open();
        method.addLine("return interaction.sendUpdate(" + createArgNameOrNull(op.getUpdateTypes()) + ");");
        method.addMethodCloseStatement();

        method = file.method("sendResponse").returns(msgType)
                .addArguments(createOperationArguments(getConfig(), file, op.getRetTypes()))
                .comment("Sends a PROGRESS response to the consumer")
                .returnComment("Returns the MAL message created by the response")
                .addThrows(throwsInteractionException, "if there is a problem during the interaction as defined by the MAL specification.")
                .addThrows(throwsMALException, "if there is an implementation exception").open();
        method.addLine("return interaction.sendResponse(" + createArgNameOrNull(op.getRetTypes()) + ");");
        method.addMethodCloseStatement();

        createServiceProviderInteractionErrorHandlers(file, true, msgType, errType, throwsInteractionException, throwsMALException);

        file.addClassCloseStatement();

        file.flush();
    }

    protected void createServiceProviderInteractionErrorHandlers(ClassWriter file, boolean withUpdate, CompositeField msgType,
            CompositeField errType, String throwsInteractionException, String throwsMALException) throws IOException {
        MethodWriter method = file.method("sendError").returns(msgType)
                .addArgument(errType)
                .comment("Sends an error to the consumer")
                .returnComment("Returns the MAL message created by the error")
                .addThrows(throwsInteractionException, "if there is a problem during the interaction as defined by the MAL specification.")
                .addThrows(throwsMALException, "if there is an implementation exception").open();
        method.addLine("return interaction.sendError(error);");
        method.addMethodCloseStatement();

        if (withUpdate) {
            method = file.method("sendUpdateError").returns(msgType)
                    .addArgument(errType)
                    .comment("Sends an update error to the consumer")
                    .returnComment("Returns the MAL message created by the error")
                    .addThrows(throwsInteractionException, "if there is a problem during the interaction as defined by the MAL specification.")
                    .addThrows(throwsMALException, "if there is an implementation exception").open();
            method.addLine("return interaction.sendUpdateError(error);");
            method.addMethodCloseStatement();
        }
    }

    protected void createServiceProviderSkeleton(File providerFolder, String area, String service,
            ServiceSummary summary, Map<String, RequiredPublisher> requiredPublishers) throws IOException {
        logger.info(" > Creating provider skeleton interface: " + service);

        String skeletonName = service + "Skeleton";
        InterfaceWriter file = createInterfaceFile(providerFolder, skeletonName);
        String throwsMALException = typeInformation.createElementType(StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
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

        file.addInterfaceOpenStatement(skeletonName, null, "The skeleton interface for the " + service + " service.");

        for (OperationSummary op : summary.getOperations()) {
            switch (op.getPattern()) {
                case PUBSUB_OP: {
                    String updateType = getConfig().getAreaPackage(area)
                            + area.toLowerCase() + "." + service.toLowerCase()
                            + "." + PROVIDER_FOLDER + "." + StubUtils.preCap(op.getName()) + "Publisher";
                    requiredPublishers.put(updateType, new RequiredPublisher(area, service, op));
                    CompositeField updateTypeField = createCompositeElementsDetails(file, false, "publisher",
                            TypeUtils.createTypeReference(area,
                                    service + "." + PROVIDER_FOLDER, StubUtils.preCap(op.getName()) + "Publisher", false),
                            false, true, null);
                    file.interfaceMethod("create" + StubUtils.preCap(op.getName()) + "Publisher").returns(updateTypeField)
                            .addArgument(malDomId).addArgument(malNetworkZone).addArgument(malSession)
                            .addArgument(malSessionName).addArgument(malqos).addArgument(malqosprops)
                            .addArgument(malPriority)
                            .addThrows(throwsMALException, "if a problem is detected during creation of the publisher")
                            .comment("Creates a publisher object using the current registered provider set for the PubSub operation " + op.getName())
                            .returnComment("The new publisher object.")
                            .declare();
                    break;
                }
            }
        }

        file.addInterfaceCloseStatement();
        file.flush();
    }

    protected void createServiceProviderSkeletonHandler(File providerFolder, String area,
            String service, ServiceSummary summary, boolean isDelegate) throws IOException {
        String className = service;
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

        String throwsMALException = typeInformation.createElementType(StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String throwsInteractionException = typeInformation.createElementType(StdStrings.MAL, null, null, StdStrings.MALINTERACTIONEXCEPTION);
        String malHelper = typeInformation.createElementType(StdStrings.MAL, null, null, "MALHelper");
        String helperName = typeInformation.createElementType(area, service, null, service + "Helper");
        String serviceInfoName = typeInformation.createElementType(area, service, null, service + JavaServiceInfo.SERVICE_INFO);
        String malString = malStringAsElement(file);
        String malInteger = typeInformation.createElementType(StdStrings.MAL, null, StdStrings.INTEGER);
        String stdError = typeInformation.createElementType(StdStrings.MAL, null, null, "MOErrorException");
        CompositeField stdBodyArg = createCompositeElementsDetails(file, false, "body",
                TypeUtils.createTypeReference(StdStrings.MAL, TRANSPORT_FOLDER, "MALMessageBody", false),
                false, true, "The message body");
        String stdErrorNs = typeInformation.convertToNamespace("," + stdError + ".," + malString + ".," + malInteger + ".");
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

        String implementsList = typeInformation.createElementType(StdStrings.MAL, null, PROVIDER_FOLDER, "MALInteractionHandler")
                + ", " + typeInformation.createElementType(area, service, PROVIDER_FOLDER, service + "Skeleton");
        if (!isDelegate) {
            implementsList += ", " + typeInformation.createElementType(area, service, PROVIDER_FOLDER, service + "Handler");
        }
        file.addClassOpenStatement(className, false, !isDelegate, null, implementsList, comment);

        file.addClassVariable(false, false, StdStrings.PRIVATE, proviedrSetVar, false,
                "(" + helperName + getConfig().getNamingSeparator() + service.toUpperCase() + "_SERVICE)");

        if (isDelegate) {
            CompositeField handlerName = createCompositeElementsDetails(file, false, "delegate",
                    TypeUtils.createTypeReference(area, service + "." + PROVIDER_FOLDER, service + "Handler", false),
                    false, true, null);
            file.addClassVariable(false, false, StdStrings.PRIVATE, handlerName, false, (String) null);
        }

        if (isDelegate) {
            MethodWriter method = file.addConstructor(StdStrings.PUBLIC, className,
                    createCompositeElementsDetails(file, false, "delegate",
                            TypeUtils.createTypeReference(area, service.toLowerCase() + "." + PROVIDER_FOLDER, service + "Handler", false),
                            false, true, "The interaction handler used for delegation"), false, null,
                    "Creates a delegation skeleton using the supplied delegate.", null);
            method.addLine("this.delegate = delegate;");
            method.addLine("delegate.setSkeleton(this);");
            method.addMethodCloseStatement();
        } else {
            // Connection object method
            CompositeField connectionName = createCompositeElementsDetails(file, false, "connection",
                    TypeUtils.createTypeReference(StdStrings.MAL, "helpertools.connections", "ConnectionProvider", false),
                    false, true, "Returns the connection object for this provider.");

            MethodWriter method1 = file.method("getConnection").returns(connectionName)
                    .comment("Returns the connection object for this provider.")
                    .returnComment("the connection object for this provider")
                    .addThrows("java.io.IOException", "if the method was not implemented yet.").open();
            method1.addLine("throw new java.io.IOException(\"This method needs to be overridden!\");");
            method1.addMethodCloseStatement();

            // SetSkeleton method
            CompositeField skeletonName = createCompositeElementsDetails(file, false, "skeleton",
                    TypeUtils.createTypeReference(area, service + "." + PROVIDER_FOLDER, service + "Skeleton", false),
                    false, true, "The skeleton (not used)");
            MethodWriter method = file.method("setSkeleton").asOverride()
                    .addArgument(skeletonName)
                    .comment("Implements the setSkeleton method of the handler interface but does nothing as this is the skeleton.").open();
            method.addLine("// Not used in the inheritance pattern (the skeleton is 'this');");
            method.addMethodCloseStatement();
        }

        CompositeField providerType = createCompositeElementsDetails(file, false, "provider",
                TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALProvider", false),
                false, true, "The provider to be added.");
        MethodWriter method = file.method("malInitialize").asOverride()
                .addArgument(providerType)
                .comment("Adds the supplied MAL provider to the internal list of providers used for PubSub")
                .addThrows(throwsMALException, "If an error is detected.").open();
        method.addLine("providerSet.addProvider(provider);");
        method.addMethodCloseStatement();

        method = file.method("malFinalize").asOverride()
                .addArgument(providerType)
                .comment("Removes the supplied MAL provider from the internal list of providers used for PubSub")
                .addThrows(throwsMALException, "If an error is detected.").open();
        method.addLine("providerSet.removeProvider(provider);");
        method.addMethodCloseStatement();

        // add publisher handler code
        for (OperationSummary op : summary.getOperations()) {
            switch (op.getPattern()) {
                case PUBSUB_OP: {
                    CompositeField updateType = createCompositeElementsDetails(file, false, "publisher",
                            TypeUtils.createTypeReference(area, service + "." + PROVIDER_FOLDER, StubUtils.preCap(op.getName()) + "Publisher", false),
                            false, true, null);
                    method = file.method("create" + StubUtils.preCap(op.getName()) + "Publisher")
                            .asOverride().returns(updateType).addArguments(psArgs)
                            .comment("Creates a publisher object using the current registered provider set for the PubSub operation " + op.getName())
                            .returnComment("The new publisher object.")
                            .addThrows(throwsMALException, "if a problem is detected during creation of the publisher").open();
                    String ns = typeInformation.convertToNamespace(serviceInfoName + "." + op.getName().toUpperCase() + "_OP");
                    method.addLine("return new " + updateType.getTypeName()
                            + "(providerSet.createPublisherSet(" + ns + ", domain, sessionType, sessionName, qos, qosProps, null));");
                    method.addMethodCloseStatement();
                    break;
                }
            }
        }

        // for each IP type add handler code
        String delegateCall = "";
        if (isDelegate) {
            delegateCall = "delegate.";
        }

        // SEND handler
        boolean wrapErrors = hasDeclaredErrors(summary, InteractionPatternEnum.SEND_OP);
        method = file.method("handleSend").asOverride()
                .addArgument(createServiceProviderSkeletonSendHandler(file, "interaction", "The interaction object"))
                .addArgument(stdBodyArg)
                .comment("Called by the provider MAL layer on reception of a message to handle the interaction")
                .addThrows(throwsMALException, "if there is a internal error")
                .addThrows(throwsInteractionException, "if there is a operation interaction error").open();

        String operationNumberGetter = createProviderSkeletonHandlerSwitch();
        method.addLine("int opNumber = " + operationNumberGetter);
        if (wrapErrors) {
            method.addLine("try {");
        }
        method.addLine("switch (opNumber) {");

        //String msg = "Unknown operation number: \" + opNumber + \" - className: " + className + " - method: ";
        String msg = "org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber";
        String unkErrorMsg;

        for (OperationSummary op : summary.getOperations()) {
            if (op.getPattern() == InteractionPatternEnum.SEND_OP) {
                String opArgs = createAdapterMethodsArgs(op.getArgTypes(), "body", false, true);
                String ns = typeInformation.convertToNamespace(serviceInfoName + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
                method.addLine("  case " + ns);
                method.addLine("    " + delegateCall + op.getName() + "(" + opArgs + "interaction);");
                method.addLine("    break;");
            }
        }
        method.addLine("  default:");
        String ns = malHelper + ".UNSUPPORTED_OPERATION_ERROR_NUMBER";
        unkErrorMsg = "(\"" + msg + "Send\")";
        method.addLine("    throw new " + throwsInteractionException
                + "(new org.ccsds.moims.mo.mal.UnsupportedOperationException(\n                    "
                + msg + "));");
        method.addLine("}");
        if (wrapErrors) {
            method.addLine("} catch (org.ccsds.moims.mo.mal.MOErrorException error) {");
            method.addLine("  throw new org.ccsds.moims.mo.mal.MALInteractionException(error);");
            method.addLine("}");
        }
        method.addMethodCloseStatement();

        // SUBMIT handler
        CompositeField submitInt = createCompositeElementsDetails(file, false, "interaction",
                TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALSubmit", false),
                false, true, "The interaction object");
        wrapErrors = hasDeclaredErrors(summary, InteractionPatternEnum.SUBMIT_OP);
        method = file.method("handleSubmit").asOverride()
                .addArgument(submitInt).addArgument(stdBodyArg)
                .comment("Called by the provider MAL layer on reception of a message to handle the interaction")
                .addThrows(throwsMALException, "if there is a internal error")
                .addThrows(throwsInteractionException, "if there is a operation interaction error").open();
        method.addLine("int opNumber = " + operationNumberGetter);
        if (wrapErrors) {
            method.addLine("try {");
        }
        method.addLine("switch (opNumber) {");

        for (OperationSummary op : summary.getOperations()) {
            if (op.getPattern() == InteractionPatternEnum.SUBMIT_OP) {
                String opArgs = createAdapterMethodsArgs(op.getArgTypes(), "body", false, true);
                ns = typeInformation.convertToNamespace(serviceInfoName + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
                method.addLine("  case " + ns);
                method.addLine("    " + delegateCall + op.getName() + "(" + opArgs + "interaction);");
                method.addLine("    interaction.sendAcknowledgement();");
                method.addLine("    break;");
            }
        }
        method.addLine("  default:");
        unkErrorMsg = "(\"" + msg + "Submit\")";
        method.addLine("    interaction.sendError"
                + "(new org.ccsds.moims.mo.mal.UnsupportedOperationException(\n                    "
                + msg + "));");
        method.addLine("    throw new " + throwsInteractionException
                + "(new org.ccsds.moims.mo.mal.UnsupportedOperationException(\n                    "
                + msg + "));");
        method.addLine("}");
        if (wrapErrors) {
            method.addLine("} catch (org.ccsds.moims.mo.mal.MOErrorException error) {");
            method.addLine("  throw new org.ccsds.moims.mo.mal.MALInteractionException(error);");
            method.addLine("}");
        }
        method.addMethodCloseStatement();

        // REQUEST handler
        CompositeField requestInt = createCompositeElementsDetails(file, false, "interaction",
                TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALRequest", false),
                false, true, "The interaction object");
        wrapErrors = hasDeclaredErrors(summary, InteractionPatternEnum.REQUEST_OP);
        method = file.method("handleRequest").asOverride()
                .addArgument(requestInt).addArgument(stdBodyArg)
                .comment("Called by the provider MAL layer on reception of a message to handle the interaction")
                .addThrows(throwsMALException, "if there is a internal error")
                .addThrows(throwsInteractionException, "if there is a operation interaction error").open();
        method.addLine("int opNumber = " + operationNumberGetter);
        if (wrapErrors) {
            method.addLine("try {");
        }
        method.addLine("switch (opNumber) {");

        for (OperationSummary op : summary.getOperations()) {
            if (op.getPattern() == InteractionPatternEnum.REQUEST_OP) {
                String opArgs = createAdapterMethodsArgs(op.getArgTypes(), "body", false, true);
                String opResp = delegateCall + op.getName() + "(" + opArgs + "interaction)";
                ns = typeInformation.convertToNamespace(serviceInfoName + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
                method.addLine("  case " + ns);
                createRequestResponseDecompose(
                        method, op,
                        opResp,
                        createReturnType(file, area, service, op.getName(), "Response", op.getRetTypes())
                );
                method.addLine("    break;");
            }
        }
        method.addLine("  default:");
        unkErrorMsg = "(\"" + msg + "Request\")";
        method.addLine("    interaction.sendError"
                + "(new org.ccsds.moims.mo.mal.UnsupportedOperationException(\n                    "
                + msg + "));");
        method.addLine("    throw new " + throwsInteractionException
                + "(new org.ccsds.moims.mo.mal.UnsupportedOperationException(\n                    "
                + msg + "));");
        method.addLine("}");
        if (wrapErrors) {
            method.addLine("} catch (org.ccsds.moims.mo.mal.MOErrorException error) {");
            method.addLine("  throw new org.ccsds.moims.mo.mal.MALInteractionException(error);");
            method.addLine("}");
        }
        method.addMethodCloseStatement();

        // INVOKE handler
        CompositeField invokeInt = createCompositeElementsDetails(file, false, "interaction",
                TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALInvoke", false),
                false, true, "The interaction object");
        wrapErrors = hasDeclaredErrors(summary, InteractionPatternEnum.INVOKE_OP);
        method = file.method("handleInvoke").asOverride()
                .addArgument(invokeInt).addArgument(stdBodyArg)
                .comment("Called by the provider MAL layer on reception of a message to handle the interaction")
                .addThrows(throwsMALException, "if there is a internal error")
                .addThrows(throwsInteractionException, "if there is a operation interaction error").open();
        method.addLine("int opNumber = " + operationNumberGetter);
        if (wrapErrors) {
            method.addLine("try {");
        }
        method.addLine("switch (opNumber) {");

        for (OperationSummary op : summary.getOperations()) {
            if (op.getPattern() == InteractionPatternEnum.INVOKE_OP) {
                String opArgs = createAdapterMethodsArgs(op.getArgTypes(), "body", false, true);
                ns = typeInformation.convertToNamespace(serviceInfoName + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
                method.addLine("  case " + ns);
                method.addLine("    " + delegateCall + op.getName() + "(" + opArgs
                        + "new " + StubUtils.preCap(op.getName()) + "Interaction" + "(interaction));");
                method.addLine("    break;");
            }
        }
        method.addLine("  default:");
        unkErrorMsg = "(\"" + msg + "Invoke\")";
        method.addLine("    interaction.sendError"
                + "(new org.ccsds.moims.mo.mal.UnsupportedOperationException(\n                    "
                + msg + "));");
        method.addLine("    throw new " + throwsInteractionException
                + "(new org.ccsds.moims.mo.mal.UnsupportedOperationException(\n                    "
                + msg + "));");
        method.addLine("}");
        if (wrapErrors) {
            method.addLine("} catch (org.ccsds.moims.mo.mal.MOErrorException error) {");
            method.addLine("  throw new org.ccsds.moims.mo.mal.MALInteractionException(error);");
            method.addLine("}");
        }
        method.addMethodCloseStatement();

        // PROGRESS handler
        CompositeField progressInt = createCompositeElementsDetails(file, false, "interaction",
                TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALProgress", false),
                false, true, "The interaction object");
        wrapErrors = hasDeclaredErrors(summary, InteractionPatternEnum.PROGRESS_OP);
        method = file.method("handleProgress").asOverride()
                .addArgument(progressInt).addArgument(stdBodyArg)
                .comment("Called by the provider MAL layer on reception of a message to handle the interaction")
                .addThrows(throwsMALException, "if there is a internal error")
                .addThrows(throwsInteractionException, "if there is a operation interaction error").open();
        method.addLine("int opNumber = " + operationNumberGetter);
        if (wrapErrors) {
            method.addLine("try {");
        }
        method.addLine("switch (opNumber) {");

        for (OperationSummary op : summary.getOperations()) {
            if (op.getPattern() == InteractionPatternEnum.PROGRESS_OP) {
                String opArgs = createAdapterMethodsArgs(op.getArgTypes(), "body", false, true);
                ns = typeInformation.convertToNamespace(serviceInfoName + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
                method.addLine("  case " + ns);
                method.addLine("    " + delegateCall + op.getName() + "(" + opArgs
                        + "new " + StubUtils.preCap(op.getName()) + "Interaction" + "(interaction));");
                method.addLine("    break;");
            }
        }
        method.addLine("  default:");
        unkErrorMsg = "(\"" + msg + "Progress\")";
        method.addLine("    interaction.sendError"
                + "(new org.ccsds.moims.mo.mal.UnsupportedOperationException(\n                    "
                + msg + "));");
        method.addLine("    throw new " + throwsInteractionException
                + "(new org.ccsds.moims.mo.mal.UnsupportedOperationException(\n                    "
                + msg + "));");
        method.addLine("}");
        if (wrapErrors) {
            method.addLine("} catch (org.ccsds.moims.mo.mal.MOErrorException error) {");
            method.addLine("  throw new org.ccsds.moims.mo.mal.MALInteractionException(error);");
            method.addLine("}");
        }
        method.addMethodCloseStatement();

        file.addClassCloseStatement();
        file.flush();
    }

    private void createRequestResponseDecompose(MethodWriter method, OperationSummary op,
            String opCall, CompositeField opRetType) throws IOException {
        List<FieldInfo> targetTypes = op.getRetTypes();

        if ((targetTypes != null) && (!targetTypes.isEmpty())) {
            if (targetTypes.size() == 1) {
                if ((op.getRetTypes().get(0).isNativeType())) {
                    String arg = op.getName() + "Rt";
                    method.addLine("    " + opRetType.getTypeName() + " " + arg + " = " + opCall + ";");
                    StringBuilder buf = new StringBuilder();
                    buf.append("(").append(arg).append(" == null) ? null : new ").append(getConfig().getAreaPackage(StdStrings.MAL));
                    buf.append("mal.").append(getConfig().getStructureFolder()).append(".").append(StdStrings.UNION);
                    buf.append("(").append(arg).append(")");
                    method.addLine("    interaction.sendResponse(" + buf.toString() + ");");
                } else {
                    method.addLine("    interaction.sendResponse(" + opCall + ");");
                }
            } else {
                String arg = op.getName() + "Rt";
                StringBuilder buf = new StringBuilder();
                buf.append("\n                    ");

                for (int i = 0; i < targetTypes.size(); i++) {
                    FieldInfo ti = targetTypes.get(i);
                    if (i > 0) {
                        buf.append(",\n                    ");
                    }
                    String fieldName = ti.getFieldName();
                    String getter = arg + ".get" + StubUtils.preCap(fieldName) + "()";

                    if (ti.isNativeType()) {
                        buf.append("(").append(getter).append(" == null) ? null : new ");
                        buf.append(getConfig().getAreaPackage(StdStrings.MAL)).append("mal.").append(getConfig().getStructureFolder());
                        buf.append(".").append(StdStrings.UNION).append("(").append(getter).append(")");
                    } else {
                        buf.append(getter);
                    }
                }

                method.addLine("    " + opRetType.getTypeName() + " " + arg + " = " + opCall + ";");
                method.addLine("    interaction.sendResponse(" + buf.toString());
                method.addLine("    );");
            }
        } else {
            // operation has an empty response
            method.addLine("    " + opCall + ";");
            method.addLine("    interaction.sendResponse();");
        }
    }

    public String getReferenceShortForm(AnyTypeReference ref) {
        String rv = null;

        if ((null != ref) && (null != ref.getAny()) && (!ref.getAny().isEmpty())) {
            List<FieldInfo> refs = TypeUtils.convertTypeReferences(typeInformation, TypeUtils.getTypeListViaXSDAny(ref.getAny()));
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
        return typeInformation.convertToNamespace(typeInformation.createElementType(any.getArea(), service, null, service + JavaServiceInfo.SERVICE_INFO)
                + "." + refObj.getName().toUpperCase() + "_OBJECT_TYPE");
    }

    protected void createFundamentalClass(File folder, String area, String service, FundamentalType enumeration) throws IOException {
        // fundamental types are usually hand created as part of a language mapping, but we have this here in case this
        // is not the case for a particular language
    }

    public abstract void createListClass(File folder, AreaType area, ServiceType service,
            String srcTypeName, boolean isAbstract, Integer shortFormPart) throws IOException;

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
            method.addLine("this." + argType.getFieldName() + " = " + argType.getFieldName() + ";");
        }

        method.addMethodCloseStatement();

        // add Getters
        for (int i = 0; i < argsList.size(); i++) {
            CompositeField argType = createCompositeElementsDetails(file, true, argsList.get(i).getFieldName(),
                    returnTypeInfo.getReturnTypes().get(i).getSourceType(), true, true, "The new value.");
            addGetter(file, argType, null);
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

        MethodWriter method = file.method("getTypeId").returns(typeIdType).asOverride().open();
        method.addLine("return TYPE_ID;");
        method.addMethodCloseStatement();
    }

    public static void addGetter(ClassWriter file, CompositeField element, String backwardCompatibility) throws IOException {
        String getOpPrefix = "get";
        String attributeName = element.getFieldName();
        boolean isDeprecated = (backwardCompatibility != null);
        String getOpName = (backwardCompatibility == null) ? StubUtils.preCap(attributeName) : backwardCompatibility;

        MethodWriter method = file.method(getOpPrefix + getOpName)
                .returns(element)
                .comment("Returns the field " + attributeName)
                .returnComment("The field " + attributeName).deprecated(isDeprecated).open();
        method.addLine("return " + attributeName + ";");
        method.addMethodCloseStatement();
    }

    protected CompositeField createServiceProviderSkeletonSendHandler(ClassWriter file, String argumentName, String argumentComment) {
        return createCompositeElementsDetails(file, false, argumentName,
                TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, StdStrings.MALINTERACTION, false),
                false, true, argumentComment);
    }

    public String createAdapterMethodsArgs(List<FieldInfo> typeInfos,
            String argNamePrefix, boolean precedingArgs, boolean moreArgs) {
        if (typeInfos == null) {
            return "";
        }

        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < typeInfos.size(); i++) {
            FieldInfo ti = typeInfos.get(i);

            boolean morePrecedingArgs = precedingArgs || (i > 0);
            boolean evenMoreArgs = moreArgs && i == (typeInfos.size() - 1);
            buf.append(createAdapterMethodsArgs(ti, argNamePrefix, i, morePrecedingArgs, evenMoreArgs));
        }

        return buf.toString();
    }

    public String createAdapterMethodsArgs(FieldInfo typeInfo, String argName,
            int argIndex, boolean precedingArgs, boolean moreArgs) {
        String retStr = "";

        if ((typeInfo.getTargetType() != null) && !(StdStrings.VOID.equals(typeInfo.getTargetType()))) {
            if (precedingArgs) {
                retStr = ",\n                ";
            }

            if (typeInfo.isNativeType()) {
                // If is is Java native (Short, Long, etc), then needs to be wrapped into a Union type!
                String unionType = getConfig().getAreaPackage(StdStrings.MAL)
                        + "mal." + getConfig().getStructureFolder() + "." + StdStrings.UNION;

                AttributeTypeDetails details = typeInformation.getAttributeDetails(typeInfo.getSourceType());
                String av = argName + ".getBodyElement(" + argIndex + ", "
                        + "new " + unionType + "(" + details.getDefaultValue() + "))";
                retStr += "(" + av + " == null) ? null : ((" + unionType + ") " + av + ").get" + details.getMalType() + "Value()";
            } else {
                // Not Java native...
                String expectedType = generateExpectedType(typeInfo);
                String cast = typeInfo.getTargetType();
                cast = cast.replace(".ElementList", ".HeterogeneousList");
                String av = argName + ".getBodyElement(" + argIndex + ", " + expectedType + ")";
                retStr += "(" + cast + ") " + av;
            }

            if (moreArgs) {
                retStr += ",\n                ";
            }
        }

        return retStr;
    }

    private String generateExpectedType(FieldInfo ti) {
        // Not Java native...
        String cast = ti.getTargetType();
        String at = null;

        if (!typeInformation.isAbstract(ti.getSourceType())) {
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

    public CompositeField createOperationReturnType(LanguageWriter file,
            String area, String service, OperationSummary op) {
        switch (op.getPattern()) {
            case REQUEST_OP: {
                if (op.getRetTypes() != null) {
                    CompositeField ret = createReturnType(file, area, service, op.getName(), "Response", op.getRetTypes());
                    return createReturnReference(ret);
                }
                break;
            }
            case INVOKE_OP:
            case PROGRESS_OP: {
                if ((op.getAckTypes() != null) && (!op.getAckTypes().isEmpty())) {
                    CompositeField ret = createReturnType(file, area, service, op.getName(), "Ack", op.getAckTypes());
                    return createReturnReference(ret);
                }
                break;
            }
        }

        return null;
    }

    public List<CompositeField> createOperationArguments(GeneratorConfiguration config,
            LanguageWriter file, List<FieldInfo> opArgs) {
        if (opArgs == null) {
            return new LinkedList<>();
        }

        List<CompositeField> outputArgs = new LinkedList<>();

        for (int i = 0; i < opArgs.size(); i++) {
            FieldInfo ti = opArgs.get(i);
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

            outputArgs.add(argType);
        }

        return outputArgs;
    }

    public String createOperationArgReturn(LanguageWriter file, MethodWriter method,
            FieldInfo typeInfo, String argName, int argIndex) throws IOException {
        if ((typeInfo.getTargetType() != null) && !(StdStrings.VOID.equals(typeInfo.getTargetType()))) {
            String eleType = "Object";
            String tv = argName + argIndex;
            String av;
            String returnParameter;

            if (typeInfo.isNativeType()) {
                AttributeTypeDetails details = typeInformation.getAttributeDetails(typeInfo.getSourceType());
                String elementType = typeInformation.createElementType(StdStrings.MAL, null, StdStrings.UNION);
                av = argName + ".getBodyElement(" + argIndex + ", new " + elementType + "(" + details.getDefaultValue() + "))";
                returnParameter = "(" + tv + " == null) ? null : ((" + elementType + ") " + tv + ").get" + details.getMalType() + "Value()";
            } else {
                String cast = typeInfo.getTargetType();
                cast = cast.replace(".ElementList", ".HeterogeneousList");
                String expectedType = generateExpectedType(typeInfo);
                av = argName + ".getBodyElement(" + argIndex + ", " + expectedType + ")";
                returnParameter = "(" + cast + ") " + tv;
            }

            method.addLine(eleType + " " + tv + " = (" + eleType + ") " + av + ";");
            return returnParameter;
        }

        return "";
    }

    protected CompositeField createReturnType(LanguageWriter file, String area,
            String service, String opName, String messageType, List<FieldInfo> returnTypes) {
        if (returnTypes == null || returnTypes.isEmpty()) {
            return null;
        }

        if (returnTypes.size() == 1) {
            return createCompositeElementsDetails(file, false, "return",
                    returnTypes.get(0).getSourceType(), true, true, null);
        }

        String shortName = StubUtils.preCap(opName) + messageType;
        String rt = getConfig().getAreaPackage(area)
                + area.toLowerCase() + "."
                + service.toLowerCase() + "."
                + getConfig().getBodyFolder() + "."
                + shortName;

        if (!multiReturnTypeMap.containsKey(rt)) {
            multiReturnTypeMap.put(rt, new MultiReturnType(rt, area, service, shortName, returnTypes));
        }

        return createCompositeElementsDetails(file, false, "return",
                TypeUtils.createTypeReference(area.toLowerCase(),
                        service.toLowerCase() + "." + getConfig().getBodyFolder(), shortName, false),
                false, true, null);
    }

    /**
     * Creates a set of argument names based on the type, wrapping the type in a
     * Union if a native type.
     *
     * @param typeNames The list of arguments.
     * @return The argument string.
     */
    public String createArgNameOrNull(List<FieldInfo> typeNames) {
        if (typeNames == null || typeNames.isEmpty()) {
            return getConfig().getNullValue();
        }

        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < typeNames.size(); i++) {
            FieldInfo ti = typeNames.get(i);
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
        String throwsMALException = typeInformation.createElementType(StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        CompositeField fld = createCompositeElementsDetails(file, false, "encoder",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "MALEncoder", false),
                false, true, "The encoder to use for encoding.");

        return file.method("encode").addArgument(fld).addThrows(throwsMALException).asOverride().open();
    }

    public MethodWriter decodeMethodOpen(ClassWriter file, CompositeField returnType) throws IOException {
        String throwsMALException = typeInformation.createElementType(StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        CompositeField fld = createCompositeElementsDetails(file, false, "decoder",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "MALDecoder", false),
                false, true, "The decoder to use for decoding.");

        return file.method("decode").returns(returnType).addArgument(fld).addThrows(throwsMALException).asOverride().open();
    }

    protected String createProviderSkeletonHandlerSwitch() {
        return "interaction.getOperation().getNumber().getValue();";
    }

    public CompositeField createReturnReference(CompositeField targetType) {
        return targetType;
    }

    protected void createAreaFolderComment(File structureFolder, AreaType area) throws IOException {
    }

    protected void createServiceFolderComment(File structureFolder, String area, ServiceType service) throws IOException {
    }

    protected void createAreaStructureFolderComment(File structureFolder, String area) throws IOException {
    }

    protected void createServiceConsumerFolderComment(File structureFolder, String area, String service) throws IOException {
    }

    protected void createServiceProviderFolderComment(File structureFolder, String area, String service) throws IOException {
    }

    protected void createServiceMessageBodyFolderComment(String baseFolder, String area, String service) throws IOException {
    }

    protected void createServiceStructureFolderComment(File structureFolder, String area, String service) throws IOException {
    }

    protected abstract void createRequiredPublisher(String destinationFolderName, String fqPublisherName, RequiredPublisher op) throws IOException;

    protected abstract String malStringAsElement(LanguageWriter file);

    public abstract ClassWriter createClassFile(File folder, String className) throws IOException;

    public abstract ClassWriter createClassFile(String destinationFolderName, String className) throws IOException;

    protected abstract InterfaceWriter createInterfaceFile(File folder, String className) throws IOException;
}
