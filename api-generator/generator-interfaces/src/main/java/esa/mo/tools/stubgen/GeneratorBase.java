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
import esa.mo.tools.stubgen.specification.FieldInfo;
import esa.mo.tools.stubgen.specification.InteractionPatternEnum;
import esa.mo.tools.stubgen.specification.NativeTypeDetails;
import esa.mo.tools.stubgen.specification.OperationSummary;
import esa.mo.tools.stubgen.specification.ServiceSummary;
import esa.mo.tools.stubgen.specification.StdStrings;
import esa.mo.tools.stubgen.specification.TypeUtils;
import esa.mo.tools.stubgen.writers.TargetWriter;
import esa.mo.xsd.*;
import esa.mo.xsd.util.XmlSpecification;
import esa.mo.xsd.util.XsdSpecification;
import java.io.IOException;
import java.util.*;
import javax.xml.bind.JAXBException;
import w3c.xsd.Schema;

/**
 * This class provides the generators with the basic type processing required.
 */
public abstract class GeneratorBase implements Generator {

    /**
     * The configuration of the generator.
     */
    private final GeneratorConfiguration config;
    /**
     * The neutral registry of all parsed MO types.
     */
    protected final MOTypeRegistry registry = new MOTypeRegistry();
    /**
     * The type information (classification + name rendering), composing the
     * registry. Generators and their helpers use this for all type queries.
     */
    protected final MOTypeInformation typeInformation;
    private boolean generateCOM;

    /**
     * Constructor.
     *
     * @param config The configuration to use.
     */
    protected GeneratorBase(GeneratorConfiguration config) {
        this.config = config;
        this.typeInformation = new MOTypeInformation(registry, config);
    }

    @Override
    public void init(String destinationFolderName,
            boolean generateStructures,
            boolean generateCOM,
            Map<String, String> packageBindings,
            Map<String, String> extraProperties) throws IOException {
        this.generateCOM = generateCOM;

        if (packageBindings != null) {
            for (Map.Entry<String, String> entry : packageBindings.entrySet()) {
                String area = entry.getKey();
                String pack = entry.getValue();

                this.config.addAreaPackage(area.toUpperCase(), pack);
            }
        }
    }

    @Override
    public void postinit(String destinationFolderName,
            boolean generateStructures,
            boolean generateCOM,
            Map<String, String> packageBindings,
            Map<String, String> extraProperties) throws IOException {
    }

    @Override
    public void loadXML(XmlSpecification xml) throws IOException, JAXBException {
        SpecificationType spec = xml.getSpecType();
        // load in types and error definitions
        for (AreaType area : spec.getArea()) {
            if (null != area.getDataTypes()) {
                registry.loadTypesFromObjectList(area.getName(), null,
                        area.getDataTypes().getFundamentalOrAttributeOrComposite());
            }

            if ((null != area.getErrors()) && (null != area.getErrors().getError())) {
                for (ErrorDefinitionType error : area.getErrors().getError()) {
                    registry.registerError(area.getName(), error);
                }
            }

            for (ServiceType service : area.getService()) {
                if (service.getDataTypes() != null) {
                    registry.loadTypesFromObjectList(area.getName(), service.getName(),
                            service.getDataTypes().getCompositeOrEnumeration());
                }

                if ((service.getErrors() != null) && (service.getErrors().getError() != null)) {
                    // Held under the area, not the service: the schema requires an error
                    // name to be unique across a whole specification, and a reference names
                    // only the area.
                    for (ErrorDefinitionType error : service.getErrors().getError()) {
                        registry.registerError(area.getName(), error);
                    }
                }
            }
        }
    }

    @Override
    public void loadXSD(XsdSpecification xsd) throws IOException, JAXBException {
        Schema spec = xsd.getSchema();
        // load in types
        if (spec.getSimpleTypeOrComplexTypeOrGroup() != null) {
            registry.loadTypesFromXsdList(StdStrings.XML, spec.getTargetNamespace(),
                    spec.getSimpleTypeOrComplexTypeOrGroup());
        }
    }

    @Override
    public void close(String destinationFolderName) throws IOException {
    }

    @Override
    public void reset() {
        registry.reset();
        config.resetAreaPackages();
    }

    /**
     * Returns true is the COM operations should be generated.
     *
     * @return true if COM required.
     */
    public boolean generateCOM() {
        return generateCOM;
    }

    /**
     * To be used by derived generators to add an entry to the attribute type
     * details map.
     *
     * @param area The area of the type.
     * @param name The name of the type.
     * @param details The new details.
     */
    protected void addAttributeType(final String area, final String name, AttributeTypeDetails details) {
        registry.registerAttributeType(area, name, details);
    }

    /**
     * To be used by derived generators to add an entry to the attribute type
     * details map.
     *
     * @param area The area of the type.
     * @param name The name of the type.
     * @param isNativeType True if native type.
     * @param targetType The type to generate too.
     * @param defaultValue An example of a default value.
     */
    protected void addAttributeType(final String area, final String name,
            final boolean isNativeType, final String targetType, final String defaultValue) {
        registry.registerAttributeType(area, name,
                new AttributeTypeDetails(typeInformation, name, isNativeType, targetType, defaultValue));
    }

    /**
     * To be used by derived generators to add an entry to the native type
     * details map.
     *
     * @param name The name of the type.
     * @param details The new details.
     */
    protected void addNativeType(String name, NativeTypeDetails details) {
        registry.registerNativeType(name, details);
    }

    /**
     * Creates a list of composite element details for each field of the
     * composite, not including those of its super type.
     *
     * @param file Writer to add any type dependencies to.
     * @param composite the composite to inspect.
     * @return a list of the element details.
     */
    public List<CompositeField> createCompositeElementsList(TargetWriter file, CompositeType composite) {
        List<CompositeField> lst = new LinkedList<>();
        for (NamedElementReferenceWithCommentType element : composite.getField()) {
            CompositeField ele = createCompositeElementsDetails(file,
                    true,
                    element.getName(),
                    element.getType(),
                    true,
                    element.isCanBeNull(),
                    element.getComment());
            lst.add(ele);
        }
        return lst;
    }

    /**
     * Creates a list of composite element details for each field of the
     * composite, including those of its super type.
     *
     * @param file Writer to add any type dependencies to.
     * @param type the composite to inspect.
     * @return a list of the element details to populate.
     */
    public List<CompositeField> createCompositeSuperElementsList(TargetWriter file, TypeReference type) {
        List<CompositeField> lst = new LinkedList<>();

        if (type != null && !StdStrings.COMPOSITE.equals(type.getName())) {
            if (StdStrings.MOOBJECT.equals(type.getName())) {
                TypeReference typeReference = TypeUtils.createTypeReference("MAL", null, "ObjectIdentity", false);

                CompositeField ele = createCompositeElementsDetails(file,
                        true,
                        "objectIdentity",
                        typeReference,
                        true,
                        false,
                        "The identity of the MO Object.");
                lst.add(ele);
                return lst;
            }

            CompositeType theType = registry.getCompositeDetails(type);

            if (theType == null) {
                String typeName = type.getName();
                throw new IllegalStateException("Unknown composite super type: " + typeName);
            }

            // first looks for super types of this one and add their details
            if ((null != theType.getExtends())
                    && (!StdStrings.COMPOSITE.equals(theType.getExtends().getType().getName()))) {
                lst.addAll(createCompositeSuperElementsList(file, theType.getExtends().getType()));
            }

            // now add the details of this type
            for (NamedElementReferenceWithCommentType element : theType.getField()) {
                CompositeField ele = createCompositeElementsDetails(file,
                        true,
                        element.getName(),
                        element.getType(),
                        true,
                        element.isCanBeNull(),
                        element.getComment());
                lst.add(ele);
            }
        }
        return lst;
    }

    /**
     * Returns the super type of a composite.
     *
     * @param type the composite to look for.
     * @return The super type of the composite or null if extends fundamental
     * type Composite.
     */
    protected TypeReference getCompositeElementSuperType(TypeReference type) {
        if ((type != null) && (!StdStrings.COMPOSITE.equals(type.getName()))) {
            CompositeType theType = registry.getCompositeDetails(type);

            if ((theType != null) && (theType.getExtends() != null)
                    && (!StdStrings.COMPOSITE.equals(theType.getExtends().getType().getName()))) {
                return theType.getExtends().getType();
            }
        }

        return null;
    }

    /**
     * Creates a summary of the operations of a service.
     *
     * @param service The service to convert.
     * @return the operation summary.
     */
    protected ServiceSummary createOperationElementList(ServiceType service) {
        List<OperationSummary> operations = new LinkedList<>();

        // only load operations if this is not the COM service
        if (!StdStrings.COM.equalsIgnoreCase(service.getName())) {
            for (CapabilitySetType capabilitySet : service.getCapabilitySet()) {
                for (OperationType op : capabilitySet.getSendIPOrSubmitIPOrRequestIP()) {
                    operations.add(extractOperationSummary(op, capabilitySet.getNumber()));
                }
            }
        }

        return new ServiceSummary(service.getNumber(), operations);
    }

    private OperationSummary extractOperationSummary(OperationType op, int capNum) {
        if (op instanceof SendOperationType) {
            SendOperationType lop = (SendOperationType) op;
            return new OperationSummary(InteractionPatternEnum.SEND_OP, op, capNum,
                    TypeUtils.convertTypeReferences(typeInformation,
                            TypeUtils.getTypeListViaField(lop.getMessages().getSend().getField())),
                    lop.getMessages().getSend().getComment(),
                    null, "",
                    null, "",
                    null, "",
                    null);
        } else if (op instanceof SubmitOperationType) {
            SubmitOperationType lop = (SubmitOperationType) op;
            OperationErrorList errors = lop.getErrors();
            return new OperationSummary(InteractionPatternEnum.SUBMIT_OP, op, capNum,
                    TypeUtils.convertTypeReferences(typeInformation,
                            TypeUtils.getTypeListViaField(lop.getMessages().getSubmit().getField())),
                    lop.getMessages().getSubmit().getComment(),
                    null, "",
                    null, "",
                    null, "",
                    errors);
        } else if (op instanceof RequestOperationType) {
            RequestOperationType lop = (RequestOperationType) op;
            OperationErrorList errors = lop.getErrors();
            return new OperationSummary(InteractionPatternEnum.REQUEST_OP, op, capNum,
                    TypeUtils.convertTypeReferences(typeInformation,
                            TypeUtils.getTypeListViaField(lop.getMessages().getRequest().getField())),
                    lop.getMessages().getRequest().getComment(),
                    null, "",
                    null, "",
                    TypeUtils.convertTypeReferences(typeInformation,
                            TypeUtils.getTypeListViaField(lop.getMessages().getResponse().getField())),
                    lop.getMessages().getResponse().getComment(),
                    errors);
        } else if (op instanceof InvokeOperationType) {
            InvokeOperationType lop = (InvokeOperationType) op;
            OperationErrorList errors = lop.getErrors();
            return new OperationSummary(InteractionPatternEnum.INVOKE_OP, op, capNum,
                    TypeUtils.convertTypeReferences(typeInformation,
                            TypeUtils.getTypeListViaField(lop.getMessages().getInvoke().getField())),
                    lop.getMessages().getInvoke().getComment(),
                    TypeUtils.convertTypeReferences(typeInformation,
                            TypeUtils.getTypeListViaField(lop.getMessages().getAcknowledgement().getField())),
                    lop.getMessages().getAcknowledgement().getComment(),
                    null, "",
                    TypeUtils.convertTypeReferences(typeInformation,
                            TypeUtils.getTypeListViaField(lop.getMessages().getResponse().getField())),
                    lop.getMessages().getResponse().getComment(),
                    errors);
        } else if (op instanceof ProgressOperationType) {
            ProgressOperationType lop = (ProgressOperationType) op;
            OperationErrorList errors = lop.getErrors();
            return new OperationSummary(InteractionPatternEnum.PROGRESS_OP, op, capNum,
                    TypeUtils.convertTypeReferences(typeInformation,
                            TypeUtils.getTypeListViaField(lop.getMessages().getProgress().getField())),
                    lop.getMessages().getProgress().getComment(),
                    TypeUtils.convertTypeReferences(typeInformation,
                            TypeUtils.getTypeListViaField(lop.getMessages().getAcknowledgement().getField())),
                    lop.getMessages().getAcknowledgement().getComment(),
                    TypeUtils.convertTypeReferences(typeInformation,
                            TypeUtils.getTypeListViaField(lop.getMessages().getUpdate().getField())),
                    lop.getMessages().getUpdate().getComment(),
                    TypeUtils.convertTypeReferences(typeInformation,
                            TypeUtils.getTypeListViaField(lop.getMessages().getResponse().getField())),
                    lop.getMessages().getResponse().getComment(),
                    errors);
        } else if (op instanceof PubSubOperationType) {
            PubSubOperationType lop = (PubSubOperationType) op;
            OperationErrorList errors = lop.getErrors();
            MessageBodyType subs = lop.getMessages().getSubscriptionKeys();
            List<FieldInfo> subKeysList = (subs == null) ? null
                    : TypeUtils.convertTypeReferences(typeInformation, TypeUtils.getTypeListViaField(subs.getField()));
            List<FieldInfo> riList = TypeUtils.convertTypeReferences(typeInformation,
                    TypeUtils.getTypeListViaField(lop.getMessages().getPublishNotify().getField()));

            return new OperationSummary(InteractionPatternEnum.PUBSUB_OP, op, capNum,
                    subKeysList, "",
                    null, "",
                    riList, "",
                    riList, lop.getMessages().getPublishNotify().getComment(),
                    errors);
        }

        return null;
    }
    /**
     * Creates a composite element detail object for a field of a composite.
     *
     * @param file Writer to add any type dependencies to.
     * @param checkType True if the type of the field should be checked for
     * validity.
     * @param fieldName The field name in the composite.
     * @param elementType the type of the field.
     * @param isStructure True if field is a structure.
     * @param canBeNull True if the field is allowed to be null.
     * @param comment The comment with the field.
     * @return the element details.
     */
    public abstract CompositeField createCompositeElementsDetails(TargetWriter file,
            boolean checkType, String fieldName, TypeReference elementType,
            boolean isStructure, boolean canBeNull, String comment);

    /**
     * @return the configuration.
     */
    public GeneratorConfiguration getConfig() {
        return config;
    }
}
