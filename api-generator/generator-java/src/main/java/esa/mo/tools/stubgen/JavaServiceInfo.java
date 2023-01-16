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
package esa.mo.tools.stubgen;

import esa.mo.tools.stubgen.specification.AttributeTypeDetails;
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
import esa.mo.xsd.AnyTypeReference;
import esa.mo.xsd.AreaType;
import esa.mo.xsd.ErrorDefinitionType;
import esa.mo.xsd.ExtendedServiceType;
import esa.mo.xsd.ModelObjectType;
import esa.mo.xsd.NamedElementReferenceWithCommentType;
import esa.mo.xsd.PubSubOperationType;
import esa.mo.xsd.ServiceType;
import esa.mo.xsd.SupportedFeatures;
import esa.mo.xsd.TypeReference;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class JavaServiceInfo {

    public final static String SERVICE_INFO = "ServiceInfo";
    private final GeneratorLangs generator;

    public JavaServiceInfo(GeneratorLangs generator) {
        this.generator = generator;
    }

    public void createServiceInfoClass(File serviceFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException {
        generator.getLog().info("Creating service helper class: " + service.getName());
        ClassWriterProposed file = generator.createClassFile(serviceFolder, service.getName() + SERVICE_INFO);

        String serviceName = service.getName();
        String serviceVar = serviceName.toUpperCase();

        file.addPackageStatement(area, service, null);
        String identifierType = generator.createElementType(file, StdStrings.MAL, null, StdStrings.IDENTIFIER);

        // Appends the class name
        if (service instanceof ExtendedServiceType) {
            file.addClassOpenStatement(serviceName + SERVICE_INFO, false, false, "org.ccsds.moims.mo.com.COMService",
                    null, "Helper class for " + serviceName + " service.");
        } else {
            file.addClassOpenStatement(serviceName + SERVICE_INFO, false, false, "org.ccsds.moims.mo.mal.MALService",
                    null, "Helper class for " + serviceName + " service.");
        }

        // COM service should not have its operations generated, these are generated as part of the specific services
        CompositeField _serviceNumberVar = generator.createCompositeElementsDetails(file, false, "_" + serviceVar + "_SERVICE_NUMBER",
                TypeUtils.createTypeReference(null, null, "int", false),
                false, false, "Service number literal.");
        CompositeField serviceNumberVar = generator.createCompositeElementsDetails(file, false, serviceVar + "_SERVICE_NUMBER",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.USHORT, false),
                true, false, "Service number instance.");
        CompositeField serviceNameVar = generator.createCompositeElementsDetails(file, false, serviceVar + "_SERVICE_NAME",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, false),
                true, true, "Service name constant.");

        file.addClassVariable(true, true, StdStrings.PUBLIC, _serviceNumberVar, false, String.valueOf(service.getNumber()));
        file.addClassVariable(true, true, StdStrings.PUBLIC, serviceNumberVar, false, "(_" + serviceVar + "_SERVICE_NUMBER)");
        file.addClassVariable(true, true, StdStrings.PUBLIC, serviceNameVar, false, "(\"" + serviceName + "\")");

        // Generate the operations:
        String operations = "";

        for (OperationSummary op : summary.getOperations()) {
            String operationInstanceVar = op.getName().toUpperCase();
            CompositeField _opNumberVar = generator.createCompositeElementsDetails(file, false, "_" + operationInstanceVar + "_OP_NUMBER",
                    TypeUtils.createTypeReference(null, null, "int", false), false, false, "Operation number literal for operation " + operationInstanceVar);
            CompositeField opNumberVar = generator.createCompositeElementsDetails(file, false, operationInstanceVar + "_OP_NUMBER",
                    TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.USHORT, false), true, false, "Operation number instance for operation " + operationInstanceVar);
            file.addClassVariable(true, true, StdStrings.PUBLIC, _opNumberVar, false, op.getNumber().toString());
            file.addClassVariable(true, true, StdStrings.PUBLIC, opNumberVar, false, "(_" + operationInstanceVar + "_OP_NUMBER)");

            List<String> opArgs = this.generateOperationArgs(file, op);
            String operationName = operationInstanceVar + "_OP";
            CompositeField opInstVar = generator.createCompositeElementsDetails(file, false, operationName,
                    TypeUtils.createTypeReference(StdStrings.MAL, null, generator.getOperationInstanceType(op), false),
                    false, true, "Operation instance for operation " + operationInstanceVar);
            file.addClassVariable(true, true, StdStrings.PUBLIC, opInstVar, false, false, opArgs);

            if (op.getPattern() == InteractionPatternEnum.PUBSUB_OP) {
                StringBuilder arrayList = new StringBuilder("{");
                PubSubOperationType lop = (PubSubOperationType) op.getOriginalOp();
                AnyTypeReference subsKeys = lop.getMessages().getSubscriptionKeys();

                if (null != subsKeys) {
                    List<TypeUtils.TypeRef> types = TypeUtils.getTypeListViaXSDAny(subsKeys.getAny());
                    if (null != types && !types.isEmpty()) {
                        String prefix = "";
                        for (TypeUtils.TypeRef type : types) {
                            if (type.isField()) {
                                NamedElementReferenceWithCommentType field = type.getFieldRef();
                                arrayList.append(prefix);
                                prefix = ",";
                                arrayList = arrayList.append("new org.ccsds.moims.mo.mal.structures.Identifier(\"").append(field.getName()).append("\")");
                            }
                        }
                    }
                }
                arrayList.append("}");

                CompositeField _opKeyNamesVar = generator.createCompositeElementsDetails(file, false, "_" + operationInstanceVar + "_OP_KEY_NAMES",
                        TypeUtils.createTypeReference(null, null, "org.ccsds.moims.mo.mal.structures.Identifier []", false),
                        false, false, "Key names instance for " + operationInstanceVar + " operation of pubsub interaction pattern");
                file.addClassVariableNewInit(true, true, StdStrings.PUBLIC, _opKeyNamesVar, false, false, arrayList.toString(), false);

                CompositeField opKeyNamesVar = generator.createCompositeElementsDetails(file, false, operationInstanceVar + "_OP_KEY_NAMES",
                        TypeUtils.createTypeReference(null, null, "org.ccsds.moims.mo.mal.structures.IdentifierList", false),
                        false, false, "Key names instance for " + operationInstanceVar + " operation of pubsub interaction pattern");
                file.addClassVariableNewInit(true, true, StdStrings.PUBLIC, opKeyNamesVar, false, false,
                        "new org.ccsds.moims.mo.mal.structures.IdentifierList(new java.util.ArrayList<>(java.util.Arrays.asList(_" + operationInstanceVar + "_OP_KEY_NAMES)))", false);
            }

            // Prepare the list of operations...
            operations += (operations.isEmpty()) ? "" : ",\n        ";
            operations += operationName;
        }

        // Generate ServiceKey:
        CompositeField serviceKeyType = generator.createCompositeElementsDetails(file, false, "SERVICE_KEY",
                TypeUtils.createTypeReference(null, null, "org.ccsds.moims.mo.mal.ServiceKey", false),
                false, false, "The service key of this service.");

        String args = "new org.ccsds.moims.mo.mal.structures.UShort(" + area.getNumber() + "), "
                + "new org.ccsds.moims.mo.mal.structures.UShort(" + service.getNumber() + "), "
                + "new org.ccsds.moims.mo.mal.structures.UOctet(" + area.getVersion() + ")";
        file.addClassVariableNewInit(true, true, StdStrings.PUBLIC, serviceKeyType, false, false,
                "new org.ccsds.moims.mo.mal.ServiceKey(" + args + ")", false);

        // Generate the MALOperation list
        CompositeField operationsType = generator.createCompositeElementsDetails(file, false, "OPERATIONS",
                TypeUtils.createTypeReference(null, null, "org.ccsds.moims.mo.mal.MALOperation[]", false),
                false, false, "The set of operations for this service.");
        file.addClassVariableNewInit(true, true, StdStrings.PUBLIC, operationsType, false, false,
                "new org.ccsds.moims.mo.mal.MALOperation[]{" + operations + "}", false);

        // construct area helper class name and variable
        String hlp = generator.createElementType(file, area.getName(), null, null, area.getName() + "Helper");
        String ns = generator.convertToNamespace(hlp + "." + area.getName().toUpperCase() + "_AREA");

        List<String> comObjectCalls = new ArrayList();

        // auto-generate helper object for the COM extra features
        if (service instanceof ExtendedServiceType) {
            generator.getLog().info("Added extended COM service feature for service helper class: " + service.getName());
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

        // Constructor - shouldn't it be started with the constructor method?
        MethodWriter method = file.addConstructor(StdStrings.PUBLIC, serviceName + SERVICE_INFO, null, null, null, null, null);
        method.addLine("super(SERVICE_KEY, " + serviceVar + "_SERVICE_NAME" + ", OPERATIONS)");

        if (!comObjectCalls.isEmpty()) {
            for (String objectCall : comObjectCalls) {
                method.addLine(generator.createMethodCall("this.addCOMObject(" + objectCall + "_OBJECT)"));
            }
        }

        method.addMethodCloseStatement();
        file.addClassCloseStatement();
        file.flush();
    }

    protected void createComObjectHelperDetails(ClassWriterProposed file, List<String> comObjectCalls, String areaHelperObject, String serviceVar, ModelObjectType obj, boolean isEvent) throws IOException {
        String objNameCaps = obj.getName().toUpperCase();
        comObjectCalls.add(objNameCaps);

        CompositeField _objNumberVar = generator.createCompositeElementsDetails(file, false, "_" + objNameCaps + "_OBJECT_NUMBER", TypeUtils.createTypeReference(null, null, "int", false), false, true, "Literal for object " + objNameCaps);
        CompositeField objNumberVar = generator.createCompositeElementsDetails(file, false, objNameCaps + "_OBJECT_NUMBER", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.USHORT, false), true, true, "Instance for object " + objNameCaps);
        CompositeField objectNameVar = generator.createCompositeElementsDetails(file, false, objNameCaps + "_OBJECT_NAME", TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, false), true, true, "Object name constant.");
        CompositeField objectTypeVar = generator.createCompositeElementsDetails(file, false, objNameCaps + "_OBJECT_TYPE", TypeUtils.createTypeReference(StdStrings.COM, null, "ObjectType", false), true, true, "Object type constant.");

        file.addClassVariableProposed(true, true, StdStrings.PUBLIC, _objNumberVar, false, String.valueOf(obj.getNumber()));
        file.addClassVariableProposed(true, true, StdStrings.PUBLIC, objNumberVar, false, "(_" + objNameCaps + "_OBJECT_NUMBER)");
        file.addClassVariableProposed(true, true, StdStrings.PUBLIC, objectNameVar, false, "(\"" + obj.getName() + "\")");
        file.addClassVariableProposed(true, true, StdStrings.PUBLIC, objectTypeVar, false, "(" + areaHelperObject + "_NUMBER, " + serviceVar + "_SERVICE_NUMBER, " + areaHelperObject + "_VERSION, " + objNameCaps + "_OBJECT_NUMBER)");

        boolean hasRelated = null != obj.getRelatedObject();
        boolean hasSource = null != obj.getSourceObject();

        String bodyShortForm = generator.getReferenceShortForm(obj.getObjectType());
        String relatedShortForm = generator.getReferenceShortForm(file, obj.getRelatedObject());
        String sourceShortForm = generator.getReferenceShortForm(file, obj.getSourceObject());

        CompositeField objectInstVar = generator.createCompositeElementsDetails(file, false, objNameCaps + "_OBJECT", TypeUtils.createTypeReference(StdStrings.COM, null, "COMObject", false), false, true, "Object instance.");
        file.addClassVariableProposed(true, false, StdStrings.PUBLIC, objectInstVar, true,
                "(" + objNameCaps + "_OBJECT_TYPE, " + objNameCaps + "_OBJECT_NAME, " + bodyShortForm + ", " + hasRelated + ", " + relatedShortForm + ", " + hasSource + ", " + sourceShortForm + ", " + isEvent + ")");
    }

    private List<String> generateOperationArgs(LanguageWriter file, OperationSummary op) {
        List<String> opArgs = new LinkedList<>();
        // Operation Number
        opArgs.add(op.getName().toUpperCase() + "_OP_NUMBER");
        opArgs.add("new " + generator.createElementType(file, StdStrings.MAL, null, StdStrings.IDENTIFIER) + "(\"" + op.getName() + "\")");
        opArgs.add("" + op.getReplay());
        opArgs.add("new " + generator.createElementType(file, StdStrings.MAL, null, StdStrings.USHORT) + "(" + op.getSet() + ")");

        switch (op.getPattern()) {
            case SEND_OP:
                opArgs.add(addMalTypes(1, op.getArgTypes(), false));
                break;
            case SUBMIT_OP:
                opArgs.add(addMalTypes(1, op.getArgTypes(), false));
                break;
            case REQUEST_OP:
                opArgs.add(addMalTypes(1, op.getArgTypes(), false));
                opArgs.add(addMalTypes(2, op.getRetTypes(), false));
                break;
            case INVOKE_OP:
                opArgs.add(addMalTypes(1, op.getArgTypes(), false));
                opArgs.add(addMalTypes(2, op.getAckTypes(), false));
                opArgs.add(addMalTypes(3, op.getRetTypes(), false));
                break;
            case PROGRESS_OP:
                opArgs.add(addMalTypes(1, op.getArgTypes(), false));
                opArgs.add(addMalTypes(2, op.getAckTypes(), false));
                opArgs.add(addMalTypes(3, op.getUpdateTypes(), false));
                opArgs.add(addMalTypes(4, op.getRetTypes(), false));
                break;
            case PUBSUB_OP:
                opArgs.add(addMalTypes(1, op.getRetTypes(), true));
                break;
        }

        return opArgs;
    }

    // Generates the MALOperationStage(...)
    private String addMalTypes(int index, List<TypeInfo> ti, boolean isPubSub) {
        ArrayList<String> typeArgs = new ArrayList<>();
        boolean needXmlSchema = false;
        boolean needMalTypes = false;
        boolean finalTypeIsAttribute = false;
        boolean finalTypeIsList = false;

        for (TypeInfo typeInfo : ti) {
            TypeReference type = typeInfo.getSourceType();

            if (StdStrings.XML.equals(type.getArea())) {
                needXmlSchema = true;
            } else {
                needMalTypes = true;
            }

            if (generator.isAbstract(type)) {
                typeArgs.add("null");

                if (StdStrings.ATTRIBUTE.equals(type.getName())) {
                    finalTypeIsAttribute = true;
                    finalTypeIsList = type.isList();
                }
            } else {
                finalTypeIsAttribute = false;

                if (isPubSub) {
                    // this is a bit of a hack for now
                    if (generator.isAttributeNativeType(type) || generator.isAttributeType(type)) {
                        type.setList(true);
                        TypeInfo lti = TypeUtils.convertTypeReference(generator, type);
                        typeArgs.add(lti.getMalShortFormField());
                        type.setList(false);
                    } else {
                        String field = typeInfo.getMalShortFormField();
                        int length = field.length() - 11;
                        typeArgs.add(field.substring(0, length) + "List.SHORT_FORM");
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

            for (Map.Entry<GeneratorBase.TypeKey, AttributeTypeDetails> val : generator.getAttributeTypesMap().entrySet()) {
                TypeReference tr = new TypeReference();
                tr.setArea(StdStrings.MAL);
                tr.setName(val.getValue().getMalType());
                if (!generator.isAbstract(tr)) {
                    tr.setList(finalTypeIsList);
                    TypeInfo lti = TypeUtils.convertTypeReference(generator, tr);
                    attribArgs.add(lti.getMalShortFormField());
                }
            }
            polyArgs = StubUtils.concatenateStringArguments(false, attribArgs.toArray(new String[0]));
        }

        if (isPubSub) {
            return "new " + shortFormType + "[] {" + arrayArgs + "}, new " + shortFormType + "[0]";
        } else {
            return "new org.ccsds.moims.mo.mal.MALOperationStage("
                    + "new org.ccsds.moims.mo.mal.structures.UOctet((short) " + index + "), "
                    + "new " + shortFormType + "[] {" + arrayArgs + "}, "
                    + "new " + shortFormType + "[] {" + polyArgs + "})";
        }
    }
}
