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

import esa.mo.tools.stubgen.java.JavaClassWriter;
import esa.mo.tools.stubgen.java.JavaCompositeFields;
import esa.mo.tools.stubgen.java.JavaLists;
import esa.mo.tools.stubgen.specification.CompositeField;
import esa.mo.tools.stubgen.specification.NativeTypeDetails;
import esa.mo.tools.stubgen.specification.StdStrings;
import esa.mo.tools.stubgen.specification.TypeUtils;
import esa.mo.tools.stubgen.writers.AbstractLanguageWriter;
import esa.mo.tools.stubgen.writers.ClassWriter;
import esa.mo.tools.stubgen.writers.InterfaceWriter;
import esa.mo.tools.stubgen.writers.LanguageWriter;
import esa.mo.tools.stubgen.writers.MethodWriter;
import esa.mo.tools.stubgen.writers.TargetWriter;
import esa.mo.xsd.AreaType;
import esa.mo.xsd.MessageBodyType;
import esa.mo.xsd.NamedElementReferenceWithCommentType;
import esa.mo.xsd.ServiceType;
import esa.mo.xsd.TypeReference;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Generates stubs and skeletons for CCSDS MO Service specifications for the
 * Java language.
 */
public class GeneratorJava extends GeneratorLangs {

    /**
     * The file extension for Java files.
     */
    public static final String JAVA_FILE_EXT = "java";
    /**
     * The file name for package level comments.
     */
    public static final String JAVA_PACKAGE_COMMENT_FILE_NAME = "package-info";

    /**
     * Constructor.
     *
     * @param logger The logger to use.
     */
    public GeneratorJava(org.apache.maven.plugin.logging.Log logger) {
        super(logger, true, true, false, true, false,
                new GeneratorConfiguration("org.ccsds.moims.mo.",
                        "structures",
                        "factory",
                        "body",
                        ".",
                        "(Object[]) null",
                        "MALSendOperation",
                        "MALSubmitOperation",
                        "MALRequestOperation",
                        "MALInvokeOperation",
                        "MALProgressOperation",
                        "MALPubSubOperation"));
    }

    @Override
    public String getShortName() {
        return "Java";
    }

    @Override
    public String getDescription() {
        return "Generates a Java language mapping.";
    }

    @Override
    public void init(String destinationFolderName,
            boolean generateStructures,
            boolean generateCOM,
            Map<String, String> packageBindings,
            Map<String, String> extraProperties) throws IOException {
        super.init(destinationFolderName, generateStructures, generateCOM, packageBindings, extraProperties);

        setRequiresDefaultConstructors(Boolean.valueOf(extraProperties.get("java.requiresDefaultConstructors")));

        addAttributeType(StdStrings.MAL, StdStrings.BLOB, false, "Blob", "");
        addAttributeType(StdStrings.MAL, StdStrings.BOOLEAN, true, "Boolean", "Boolean.FALSE");
        addAttributeType(StdStrings.MAL, StdStrings.DOUBLE, true, "Double", "Double.MAX_VALUE");
        addAttributeType(StdStrings.MAL, StdStrings.DURATION, false, "Duration", "");
        addAttributeType(StdStrings.MAL, StdStrings.FLOAT, true, "Float", "Float.MAX_VALUE");
        addAttributeType(StdStrings.MAL, StdStrings.INTEGER, true, "Integer", "Integer.MAX_VALUE");
        addAttributeType(StdStrings.MAL, StdStrings.IDENTIFIER, false, "Identifier", "");
        addAttributeType(StdStrings.MAL, StdStrings.LONG, true, "Long", "Long.MAX_VALUE");
        addAttributeType(StdStrings.MAL, StdStrings.OCTET, true, "Byte", "Byte.MAX_VALUE");
        addAttributeType(StdStrings.MAL, StdStrings.SHORT, true, "Short", "Short.MAX_VALUE");
        addAttributeType(StdStrings.MAL, StdStrings.UINTEGER, false, "UInteger", "");
        addAttributeType(StdStrings.MAL, StdStrings.ULONG, false, "ULong", "");
        addAttributeType(StdStrings.MAL, StdStrings.UOCTET, false, "UOctet", "");
        addAttributeType(StdStrings.MAL, StdStrings.USHORT, false, "UShort", "");
        addAttributeType(StdStrings.MAL, StdStrings.STRING, true, "String", "\"\"");
        addAttributeType(StdStrings.MAL, StdStrings.TIME, false, "Time", "");
        addAttributeType(StdStrings.MAL, StdStrings.FINETIME, false, "FineTime", "");
        addAttributeType(StdStrings.MAL, StdStrings.URI, false, "URI", "");
        addAttributeType(StdStrings.MAL, StdStrings.OBJECTREF, false, "ObjectRef", "");

        super.addNativeType("boolean", new NativeTypeDetails("boolean", false, false, null));
        super.addNativeType("_String", new NativeTypeDetails("String", false, false, null));
        super.addNativeType("byte", new NativeTypeDetails("byte", false, false, null));
        super.addNativeType("short", new NativeTypeDetails("short", false, false, null));
        super.addNativeType("int", new NativeTypeDetails("int", false, false, null));
        super.addNativeType("long", new NativeTypeDetails("long", false, false, null));
        super.addNativeType("_Integer", new NativeTypeDetails("Integer", true, false, null));
        super.addNativeType("Class", new NativeTypeDetails("Class", true, false, null));
        super.addNativeType("Map", new NativeTypeDetails("java.util.Map", true, false, null));
        super.addNativeType("Vector", new NativeTypeDetails("java.util.Vector", true, false, null));
    }

    @Override
    public void createRequiredPublisher(String destinationFolderName, String fqPublisherName, RequiredPublisher publisher) throws IOException {
        logger.info(" > Creating Publisher class: " + fqPublisherName);

        String publisherName = fqPublisherName.substring(fqPublisherName.lastIndexOf('.') + 1);
        ClassWriter file = createClassFile(destinationFolderName, fqPublisherName.replace('.', '/'));

        file.addPackageStatement(publisher.getArea(), publisher.getService(), PROVIDER_FOLDER);

        String throwsMALException = createElementType(StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String throwsInteractionException = createElementType(StdStrings.MAL, null, null, StdStrings.MALINTERACTIONEXCEPTION);
        String throwsInteractionAndMALException = throwsInteractionException + ", " + throwsMALException;
        String throwsExceptions = "java.lang.IllegalArgumentException, " + throwsInteractionAndMALException;
        CompositeField publisherSetType = createCompositeElementsDetails(file, false, "publisherSet",
                TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALPublisherSet", false),
                false, true, null);

        file.addClassOpenStatement(publisherName, true, false, null, null,
                "Publisher class for the " + publisher.getOperation().getName() + " operation.");

        file.addClassVariable(false, false, StdStrings.PRIVATE, publisherSetType, false, (String) null);

        MethodWriter method = file.addConstructor(StdStrings.PUBLIC, publisherName,
                createCompositeElementsDetails(file, false, "publisherSet",
                        TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALPublisherSet", false),
                        false, true, "The set of broker connections to use when registering and publishing."),
                false, null, "Creates an instance of this class using the supplied publisher set.", null);
        method.addLine("this.publisherSet = publisherSet;");
        method.addMethodCloseStatement();

        CompositeField keyNamesList = createCompositeElementsDetails(file, false, "keyNames",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "Identifier", true),
                true, true, "The key names to use in the method");
        CompositeField keyTypesList = createCompositeElementsDetails(file, false, "keyTypes",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "AttributeType", true),
                true, true, "The key types to use in the method");
        CompositeField psListener = createCompositeElementsDetails(file, false, "listener",
                TypeUtils.createTypeReference(StdStrings.MAL, PROVIDER_FOLDER, "MALPublishInteractionListener", false),
                false, true,
                "The listener object to use for callback from the publisher");
        List<CompositeField> argPSListenerList = new LinkedList<>();
        argPSListenerList.add(keyNamesList);
        argPSListenerList.add(keyTypesList);
        argPSListenerList.add(psListener);

        // register method
        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                false, true, null, "register", argPSListenerList, throwsExceptions,
                "Registers this provider implementation to the set of broker connections", null,
                Arrays.asList("java.lang.IllegalArgumentException If any supplied argument is invalid",
                        throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.",
                        throwsMALException + " if there is an implementation exception"));
        method.addLine("publisherSet.register(keyNames, keyTypes, listener);");
        method.addMethodCloseStatement();

        // registerWithDefaultKeys method
        List<CompositeField> listenerAuto = new LinkedList<>();
        listenerAuto.add(psListener);
        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                false, true, null, "registerWithDefaultKeys", listenerAuto, throwsInteractionAndMALException,
                "Registers this provider implementation to the set of broker connections with the default subscription keys", null,
                Arrays.asList("java.lang.IllegalArgumentException If any supplied argument is invalid",
                        throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.",
                        throwsMALException + " if there is an implementation exception"));
        method.addLine("org.ccsds.moims.mo.mal.structures.IdentifierList keyNames = new org.ccsds.moims.mo.mal.structures.IdentifierList();");
        method.addLine("org.ccsds.moims.mo.mal.structures.AttributeTypeList keyTypes = new org.ccsds.moims.mo.mal.structures.AttributeTypeList();");

        if (publisher.getOperation() != null) {
            MessageBodyType keys = publisher.getOperation().getSubscriptionKeys();

            if (keys != null) {
                for (NamedElementReferenceWithCommentType key : keys.getField()) {
                    method.addLine("keyNames.add(new org.ccsds.moims.mo.mal.structures.Identifier(\"" + key.getName() + "\"));");
                    method.addLine("keyTypes.add(org.ccsds.moims.mo.mal.structures.AttributeType." + key.getType().getName().toUpperCase() + ");");
                }
            }
        }
        method.addLine("publisherSet.register(keyNames, keyTypes, listener);");
        method.addMethodCloseStatement();

        // asyncRegister method
        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                false, true, null, "asyncRegister", argPSListenerList, throwsExceptions,
                "Asynchronously registers this provider implementation to the set of broker connections", null,
                Arrays.asList("java.lang.IllegalArgumentException If any supplied argument is invalid",
                        throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.",
                        throwsMALException + " if there is an implementation exception"));
        method.addLine("publisherSet.asyncRegister(keyNames, keyTypes, listener);");
        method.addMethodCloseStatement();

        List<CompositeField> argList = new LinkedList<>();
        argList.add(createCompositeElementsDetails(file, true, "updateHeader",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "UpdateHeader", false),
                true, true, "The headers of the updates being added"));
        argList.addAll(createOperationArguments(getConfig(), file, publisher.getOperation().getUpdateTypes()));

        String argNameList = "";

        if (argList.size() > 1) {
            List<String> strList = new LinkedList<>();

            for (int i = 1; i < argList.size(); i++) {
                strList.add(argList.get(i).getFieldName());
            }

            argNameList = StubUtils.concatenateStringArguments(true, strList.toArray(new String[0]));
        }

        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                false, true, null, "publish", argList, throwsExceptions,
                "Publishes updates to the set of registered broker connections", null,
                Arrays.asList("java.lang.IllegalArgumentException If any supplied argument is invalid",
                        throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.",
                        throwsMALException + " if there is an implementation exception"));
        method.addLine("publisherSet.publish(updateHeader" + argNameList + ");");
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                false, true, null, "deregister", null, throwsInteractionAndMALException,
                "Deregisters this provider implementation from the set of broker connections", null,
                Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.",
                        throwsMALException + " if there is an implementation exception"));
        method.addLine("publisherSet.deregister();");
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                false, true, null, "asyncDeregister", Arrays.asList(psListener), throwsExceptions,
                "Asynchronously deregisters this provider implementation from the set of broker connections", null,
                Arrays.asList("java.lang.IllegalArgumentException If any supplied argument is invalid",
                        throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.",
                        throwsMALException + " if there is an implementation exception"));
        method.addLine("publisherSet.asyncDeregister(listener);");
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                false, true, null, "close", null, throwsMALException,
                "Closes this publisher", null, Arrays.asList(throwsMALException + " if there is an implementation exception"));
        method.addLine("publisherSet.close();");
        method.addMethodCloseStatement();

        file.addClassCloseStatement();

        file.flush();
    }

    @Override
    public void createListClass(File folder, AreaType area, ServiceType service,
            String srcTypeName, boolean isAbstract, Integer shortFormPart) throws IOException {
        JavaLists javaLists = new JavaLists(this);
        String listName = srcTypeName + "List";

        if (isAbstract) {
            logger.info(" > Creating HeterogeneousList class: " + listName);
            javaLists.createHeterogeneousListClass(folder, area.getName(), service == null ? null : service.getName(), srcTypeName);
        } else {
            logger.info(" > Creating List class: " + listName);
            javaLists.createHomogeneousListClass(folder, area, service, srcTypeName, shortFormPart);
        }
    }

    @Override
    protected void addShortForm(ClassWriter file, long sf) throws IOException {
        file.addStatement("    private static final long serialVersionUID = " + sf + "L;");
        //file.addStatement("    @Deprecated");
        file.addMultilineComment("The TypeId of this Element as a long.");
        file.addStatement("    public static final Long SHORT_FORM = " + sf + "L;");
    }

    @Override
    protected void createAreaFolderComment(File structureFolder, AreaType area) throws IOException {
        String cmt = area.getComment();
        if (cmt == null) {
            cmt = "The " + area.getName() + " area.";
        }

        createFolderComment(structureFolder, area.getName(), null, null, cmt);
    }

    @Override
    protected void createServiceFolderComment(File structureFolder,
            String area, ServiceType service) throws IOException {
        createFolderComment(structureFolder, area, service.getName(), null, service.getComment());
    }

    @Override
    protected void createServiceConsumerFolderComment(File structureFolder,
            String area, String service) throws IOException {
        createFolderComment(structureFolder, area, service, CONSUMER_FOLDER,
                "Package containing the consumer stubs for the " + service + " service.");
    }

    @Override
    protected void createServiceProviderFolderComment(File structureFolder,
            String area, String service) throws IOException {
        createFolderComment(structureFolder, area, service, PROVIDER_FOLDER,
                "Package containing the provider skeletons for the " + service + " service.");
    }

    @Override
    protected void createServiceMessageBodyFolderComment(String baseFolder,
            String area, String service) throws IOException {
        String basePackageName = getConfig().getAreaPackage(area);
        String packageName = basePackageName + "." + area.toLowerCase();
        if (service != null) {
            packageName += "." + service.toLowerCase();
        }

        String className = packageName + "." + getConfig().getBodyFolder() + "." + JAVA_PACKAGE_COMMENT_FILE_NAME;
        className = className.replace('.', '/');
        ClassWriter file = createClassFile(baseFolder, className);

        createFolderComment(file, area, service, getConfig().getBodyFolder(),
                "Package containing the types for holding compound messages.");
    }

    @Override
    protected void createAreaStructureFolderComment(File structureFolder, String area) throws IOException {
        createFolderComment(structureFolder, area, null, getConfig().getStructureFolder(),
                "Package containing types defined in the " + area + " area.");
    }

    @Override
    protected void createServiceStructureFolderComment(File structureFolder,
            String area, String service) throws IOException {
        createFolderComment(structureFolder, area, service, getConfig().getStructureFolder(),
                "Package containing types defined in the " + service + " service.");
    }

    /**
     * Creates a java package file.
     *
     * @param structureFolder The folder containing the generated code.
     * @param area the area.
     * @param service the server.
     * @param extraPackage any extra package level.
     * @param comment the comment.
     * @throws IOException if there is a problem.
     */
    protected void createFolderComment(File structureFolder, String area,
            String service, String extraPackage, String comment) throws IOException {
        ClassWriter file = createClassFile(structureFolder, JAVA_PACKAGE_COMMENT_FILE_NAME);
        createFolderComment(file, area, service, extraPackage, comment);
    }

    /**
     * Creates a java package file.
     *
     * @param file The folder containing the generated code.
     * @param area the area.
     * @param service the server.
     * @param extraPackage any extra package level.
     * @param comment the comment.
     * @throws IOException if there is a problem.
     */
    protected void createFolderComment(ClassWriter file, String area,
            String service, String extraPackage, String comment) throws IOException {
        List<String> list = AbstractLanguageWriter.normaliseComment(new ArrayList(), comment);

        file.addStatement("/**");

        for (String line : list) {
            file.addStatement(" * " + line);
        }

        file.addStatement("*/");
        file.addPackageStatement(area, service, extraPackage);
        file.flush();
    }

    @Override
    public CompositeField createCompositeElementsDetails(TargetWriter file, boolean checkType,
            String fieldName, TypeReference elementType, boolean isStructure, boolean canBeNull, String comment) {
        JavaCompositeFields javaComposites = new JavaCompositeFields(this);
        return javaComposites.createCompositeElementsDetails((LanguageWriter) file,
                checkType, fieldName, elementType, isStructure, canBeNull, comment);
    }

    @Override
    protected String malStringAsElement(LanguageWriter file) {
        return createElementType(StdStrings.MAL, null, StdStrings.UNION);
    }

    @Override
    public ClassWriter createClassFile(File folder, String className) throws IOException {
        return new JavaClassWriter(folder, className, this);
    }

    @Override
    public ClassWriter createClassFile(String destinationFolderName, String className) throws IOException {
        return new JavaClassWriter(destinationFolderName, className, this);
    }

    @Override
    protected InterfaceWriter createInterfaceFile(File folder, String className) throws IOException {
        return new JavaClassWriter(folder, className, this);
    }
}
