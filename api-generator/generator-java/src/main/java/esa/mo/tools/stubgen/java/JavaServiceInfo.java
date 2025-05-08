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
import esa.mo.tools.stubgen.specification.CompositeField;
import esa.mo.tools.stubgen.specification.FieldInfo;
import esa.mo.tools.stubgen.specification.InteractionPatternEnum;
import esa.mo.tools.stubgen.specification.OperationSummary;
import esa.mo.tools.stubgen.specification.ServiceSummary;
import esa.mo.tools.stubgen.specification.StdStrings;
import esa.mo.tools.stubgen.specification.TypeRef;
import esa.mo.tools.stubgen.specification.TypeUtils;
import esa.mo.tools.stubgen.writers.ClassWriter;
import esa.mo.tools.stubgen.writers.MethodWriter;
import esa.mo.xsd.AreaType;
import esa.mo.xsd.CompositeType;
import esa.mo.xsd.EnumerationType;
import esa.mo.xsd.ExtendedServiceType;
import esa.mo.xsd.MessageBodyType;
import esa.mo.xsd.ModelObjectType;
import esa.mo.xsd.NamedElementReferenceWithCommentType;
import esa.mo.xsd.ServiceType;
import esa.mo.xsd.SupportedFeatures;
import esa.mo.xsd.TypeReference;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class JavaServiceInfo {

    private final static String OP_FIELD = "org.ccsds.moims.mo.mal.OperationField";
    public final static String SERVICE_INFO = "ServiceInfo";
    private final GeneratorLangs generator;

    public JavaServiceInfo(GeneratorLangs generator) {
        this.generator = generator;
    }

    public void createServiceInfoClass(File serviceFolder, AreaType area,
            ServiceType service, ServiceSummary summary) throws IOException {
        ClassWriter file = generator.createClassFile(serviceFolder, service.getName() + SERVICE_INFO);

        // construct area helper class name and variable
        String hlp = generator.createElementType(area.getName(), null, null, area.getName() + "Helper");
        String namespace = generator.convertToNamespace(hlp + "." + area.getName().toUpperCase() + "_AREA");
        String serviceName = service.getName();
        String serviceCAPS = serviceName.toUpperCase();
        file.addPackageStatement(area.getName(), service.getName(), null);

        // Appends the class name
        if (service instanceof ExtendedServiceType) {
            file.addClassOpenStatement(serviceName + SERVICE_INFO, false, false, "org.ccsds.moims.mo.com.COMService",
                    null, "Helper class for " + serviceName + " service.");
        } else {
            file.addClassOpenStatement(serviceName + SERVICE_INFO, false, false, "org.ccsds.moims.mo.mal.ServiceInfo",
                    null, "Helper class for " + serviceName + " service.");
        }

        // COM service should not have its operations generated, these are generated as part of the specific services
        CompositeField _serviceNumberVar = generator.createCompositeElementsDetails(file, false, "_" + serviceCAPS + "_SERVICE_NUMBER",
                TypeUtils.createTypeReference(null, null, "int", false),
                false, false, "Service number literal.");
        CompositeField serviceNumberVar = generator.createCompositeElementsDetails(file, false, serviceCAPS + "_SERVICE_NUMBER",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.USHORT, false),
                true, false, "Service number instance.");
        CompositeField serviceNameVar = generator.createCompositeElementsDetails(file, false, serviceCAPS + "_SERVICE_NAME",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, false),
                true, true, "Service name constant.");

        file.addClassVariable(true, true, StdStrings.PUBLIC, _serviceNumberVar, false, String.valueOf(service.getNumber()));
        file.addClassVariable(true, true, StdStrings.PUBLIC, serviceNumberVar, false, "(_" + serviceCAPS + "_SERVICE_NUMBER)");
        file.addClassVariable(true, true, StdStrings.PUBLIC, serviceNameVar, false, "(\"" + serviceName + "\")");

        // Generate ServiceKey:
        CompositeField serviceKeyType = generator.createCompositeElementsDetails(file, false, "SERVICE_KEY",
                TypeUtils.createTypeReference(null, null, "org.ccsds.moims.mo.mal.ServiceKey", false),
                false, false, "The service key of this service.");
        String args = "\n            " + area.getNumber() + "," + area.getVersion() + "," + serviceCAPS + "_SERVICE_NUMBER";
        file.addClassVariableNewInit(true, true, StdStrings.PUBLIC, serviceKeyType, false,
                false, "new org.ccsds.moims.mo.mal.ServiceKey(" + args + ")", false);

        // Generate the operations:
        String operations = "";

        for (OperationSummary op : summary.getOperations()) {
            String operationInstanceVar = op.getName().toUpperCase();
            CompositeField _opNumberVar = generator.createCompositeElementsDetails(file, false, "_" + operationInstanceVar + "_OP_NUMBER",
                    TypeUtils.createTypeReference(null, null, "int", false),
                    false, false, "Operation number literal for operation " + operationInstanceVar);
            CompositeField opNumberVar = generator.createCompositeElementsDetails(file, false, operationInstanceVar + "_OP_NUMBER",
                    TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.USHORT, false),
                    true, false, "Operation number instance for operation " + operationInstanceVar);
            file.addClassVariable(true, true, StdStrings.PUBLIC, _opNumberVar, false, op.getNumber().toString());
            file.addClassVariable(true, true, StdStrings.PUBLIC, opNumberVar, false, "(_" + operationInstanceVar + "_OP_NUMBER)");

            List<String> opArgs = this.generateOperationArgs(op);
            String operationName = operationInstanceVar + "_OP";
            CompositeField opInstVar = generator.createCompositeElementsDetails(file, false, operationName,
                    TypeUtils.createTypeReference(StdStrings.MAL, null, generator.getOperationInstanceType(op), false),
                    false, true, "Operation instance for operation " + operationInstanceVar);
            file.addClassVariable(true, true, StdStrings.PUBLIC, opInstVar, false, false, opArgs);

            if (op.getPattern() == InteractionPatternEnum.PUBSUB_OP) {
                StringBuilder arrayList = new StringBuilder("{");
                MessageBodyType subsKeys = op.getSubscriptionKeys();

                if (subsKeys != null) {
                    List<TypeRef> types = TypeUtils.getTypeListViaField(subsKeys.getField());
                    if (types != null && !types.isEmpty()) {
                        String prefix = "";
                        for (TypeRef type : types) {
                            if (type.isField()) {
                                NamedElementReferenceWithCommentType field = type.getFieldRef();
                                arrayList.append(prefix);
                                prefix = ",\n            ";
                                arrayList.append("new org.ccsds.moims.mo.mal.structures.Identifier(\"").append(field.getName()).append("\")");
                            }
                        }
                    }
                }
                arrayList.append("}");

                CompositeField _opKeyNamesVar = generator.createCompositeElementsDetails(file, false, "_" + operationInstanceVar + "_OP_KEY_NAMES",
                        TypeUtils.createTypeReference(null, null, "org.ccsds.moims.mo.mal.structures.Identifier []", false),
                        false, false, "Key names instance for " + operationInstanceVar + " operation of pubsub interaction pattern");
                file.addClassVariableNewInit(true, true, StdStrings.PUBLIC, _opKeyNamesVar,
                        false, false, arrayList.toString(), false);

                CompositeField opKeyNamesVar = generator.createCompositeElementsDetails(file, false, operationInstanceVar + "_OP_KEY_NAMES",
                        TypeUtils.createTypeReference(null, null, "org.ccsds.moims.mo.mal.structures.IdentifierList", false),
                        false, false, "Key names instance for " + operationInstanceVar + " operation of pubsub interaction pattern");
                file.addClassVariableNewInit(true, true, StdStrings.PUBLIC, opKeyNamesVar, false, false,
                        "new org.ccsds.moims.mo.mal.structures.IdentifierList(new java.util.ArrayList<>(java.util.Arrays.asList(_"
                        + operationInstanceVar + "_OP_KEY_NAMES)))", false);
            }

            // Prepare the list of operations...
            operations += (operations.isEmpty()) ? "" : ",\n        ";
            operations += operationName;
        }

        List<String> elementInstantiations = new LinkedList<>();

        if ((service.getDataTypes() != null) && !service.getDataTypes().getCompositeOrEnumeration().isEmpty()) {
            for (Object oType : service.getDataTypes().getCompositeOrEnumeration()) {
                String typeName = "";
                boolean isAbstract = false;
                if (oType instanceof EnumerationType) {
                    typeName = ((EnumerationType) oType).getName();
                } else if (oType instanceof CompositeType) {
                    typeName = ((CompositeType) oType).getName();
                    isAbstract = (((CompositeType) oType).getShortFormPart() == null);
                }

                if (!isAbstract) {
                    String clsName = generator.createElementType(area.getName(), service.getName(), typeName);
                    String text = "new " + clsName + "()";
                    /* Old code for Enumerations
                    if (oType instanceof EnumerationType) {
                        text = clsName + ".fromOrdinal(0)";
                    }
                     */

                    String lclsName = generator.createElementType(area.getName(), service.getName(), typeName + "List");
                    elementInstantiations.add(text);
                    elementInstantiations.add("new " + lclsName + "()");
                }
            }
        }

        StringBuilder buf = new StringBuilder();
        for (String objectCall : elementInstantiations) {
            buf.append("\n        ").append(objectCall).append(",");
        }
        CompositeField objectInstVar = generator.createCompositeElementsDetails(file, false, serviceCAPS + "_SERVICE_ELEMENTS",
                TypeUtils.createTypeReference(null, null, "org.ccsds.moims.mo.mal.structures.Element", false),
                false, true, "Area elements.");
        file.addClassVariableNewInit(true, true, StdStrings.PUBLIC, objectInstVar,
                false, true, buf.toString(), false);

        // Generate the MALOperation list
        CompositeField operationsType = generator.createCompositeElementsDetails(file, false, "OPERATIONS",
                TypeUtils.createTypeReference(null, null, "org.ccsds.moims.mo.mal.MALOperation[]", false),
                false, false, "The set of operations for this service.");
        file.addClassVariableNewInit(true, true, StdStrings.PUBLIC, operationsType, false, false,
                "new org.ccsds.moims.mo.mal.MALOperation[]{" + operations + "}", false);

        List<String> comObjectCalls = new ArrayList();

        // auto-generate helper object for the COM extra features
        if (service instanceof ExtendedServiceType) {
            ExtendedServiceType eService = (ExtendedServiceType) service;
            SupportedFeatures features = eService.getFeatures();

            if (features != null) {
                if (features.getObjects() != null) {
                    for (ModelObjectType obj : features.getObjects().getObject()) {
                        createComObjectHelperDetails(file, comObjectCalls, serviceCAPS, obj, false, area);
                    }
                }

                if (features.getEvents() != null) {
                    for (ModelObjectType obj : features.getEvents().getEvent()) {
                        createComObjectHelperDetails(file, comObjectCalls, serviceCAPS, obj, true, area);
                    }
                }
            }
        }

        boolean hasCOMObjects = !comObjectCalls.isEmpty();

        if (hasCOMObjects) {
            StringBuilder buffer = new StringBuilder();
            for (String objectCall : comObjectCalls) {
                buffer.append("\n        ").append(objectCall).append("_OBJECT,");
            }

            CompositeField typeCOMObject = generator.createCompositeElementsDetails(file, false, "COM_OBJECTS",
                    TypeUtils.createTypeReference(StdStrings.COM, null, "COMObject", false),
                    false, true, "Object instance.");
            file.addClassVariableNewInit(true, true, StdStrings.PUBLIC, typeCOMObject,
                    false, true, buffer.toString(), false);
        }

        // Constructor - shouldn't it be started with the constructor method?
        MethodWriter constructor = file.addConstructor(StdStrings.PUBLIC, serviceName + SERVICE_INFO,
                null, null, null, null, null);
        String ending = hasCOMObjects ? ", COM_OBJECTS)" : ")";
        constructor.addLine("super(SERVICE_KEY, " + serviceCAPS + "_SERVICE_NAME, "
                + serviceCAPS + "_SERVICE_ELEMENTS" + ", OPERATIONS" + ending + ";");
        constructor.addMethodCloseStatement();

        // Add the MALArea getArea() method
        CompositeField opType = generator.createCompositeElementsDetails(file, false, "return",
                TypeUtils.createTypeReference(null, null, "org.ccsds.moims.mo.mal.MALArea", false),
                false, true, null);
        MethodWriter method = file.addMethodOpenStatementOverride(opType, "getArea", null, null);

        method.addLine("return " + namespace + ";");
        method.addMethodCloseStatement();

        file.addClassCloseStatement();
        file.flush();
    }

    private void createComObjectHelperDetails(ClassWriter file, List<String> comObjectCalls,
            String serviceVar, ModelObjectType obj, boolean isEvent, AreaType area) throws IOException {
        String objNameCaps = obj.getName().toUpperCase();
        comObjectCalls.add(objNameCaps);

        CompositeField _objNumberVar = generator.createCompositeElementsDetails(file, false, "_" + objNameCaps + "_OBJECT_NUMBER",
                TypeUtils.createTypeReference(null, null, "int", false),
                false, true, "Literal for object " + objNameCaps);
        CompositeField objNumberVar = generator.createCompositeElementsDetails(file, false, objNameCaps + "_OBJECT_NUMBER",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.USHORT, false),
                true, true, "Instance for object " + objNameCaps);
        CompositeField objectNameVar = generator.createCompositeElementsDetails(file, false, objNameCaps + "_OBJECT_NAME",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, false),
                true, true, "Object name constant.");
        CompositeField objectTypeVar = generator.createCompositeElementsDetails(file, false, objNameCaps + "_OBJECT_TYPE",
                TypeUtils.createTypeReference(StdStrings.COM, null, "ObjectType", false),
                true, true, "Object type constant.");

        file.addClassVariableDeprecated(true, true, StdStrings.PUBLIC, _objNumberVar, false,
                String.valueOf(obj.getNumber()));
        file.addClassVariableDeprecated(true, true, StdStrings.PUBLIC, objNumberVar, false,
                "(_" + objNameCaps + "_OBJECT_NUMBER)");
        file.addClassVariableDeprecated(true, true, StdStrings.PUBLIC, objectNameVar, false,
                "(\"" + obj.getName() + "\")");
        file.addClassVariableDeprecated(true, true, StdStrings.PUBLIC, objectTypeVar, false,
                "(new org.ccsds.moims.mo.mal.structures.UShort(" + area.getNumber() + "), "
                + serviceVar + "_SERVICE_NUMBER, "
                + "new org.ccsds.moims.mo.mal.structures.UOctet(" + area.getVersion() + "), "
                + objNameCaps + "_OBJECT_NUMBER)");

        boolean hasRelated = null != obj.getRelatedObject();
        boolean hasSource = null != obj.getSourceObject();

        String bodyShortForm = generator.getReferenceShortForm(obj.getObjectType());
        String relatedShortForm = generator.getReferenceShortForm(file, obj.getRelatedObject());
        String sourceShortForm = generator.getReferenceShortForm(file, obj.getSourceObject());

        CompositeField objectInstVar = generator.createCompositeElementsDetails(file, false, objNameCaps + "_OBJECT",
                TypeUtils.createTypeReference(StdStrings.COM, null, "COMObject", false),
                false, true, "Object instance.");
        file.addClassVariableDeprecated(true, false, StdStrings.PUBLIC, objectInstVar, true,
                "(" + objNameCaps + "_OBJECT_TYPE, " + objNameCaps + "_OBJECT_NAME, " + bodyShortForm + ", "
                + hasRelated + ", " + relatedShortForm + ", " + hasSource + ", " + sourceShortForm + ", " + isEvent + ")");
    }

    private List<String> generateOperationArgs(OperationSummary op) {
        String initNewLine = "\n            ";
        // Operation Number
        List<String> opArgs = new LinkedList<>();
        opArgs.add("SERVICE_KEY");
        opArgs.add(initNewLine + op.getName().toUpperCase() + "_OP_NUMBER");
        opArgs.add(initNewLine + "new " + generator.createElementType(StdStrings.MAL, null, StdStrings.IDENTIFIER) + "(\"" + op.getName() + "\")");
        // opArgs.add(initNewLine + "" + op.getReplay());
        opArgs.add(initNewLine + "new " + generator.createElementType(StdStrings.MAL, null, StdStrings.USHORT) + "(" + op.getSet() + ")");

        switch (op.getPattern()) {
            case SEND_OP:
                opArgs.add(addMalTypes(op.getArgTypes()));
                break;
            case SUBMIT_OP:
                opArgs.add(addMalTypes(op.getArgTypes()));
                break;
            case REQUEST_OP:
                opArgs.add(addMalTypes(op.getArgTypes()));
                opArgs.add(addMalTypes(op.getRetTypes()));
                break;
            case INVOKE_OP:
                opArgs.add(addMalTypes(op.getArgTypes()));
                opArgs.add(addMalTypes(op.getAckTypes()));
                opArgs.add(addMalTypes(op.getRetTypes()));
                break;
            case PROGRESS_OP:
                opArgs.add(addMalTypes(op.getArgTypes()));
                opArgs.add(addMalTypes(op.getAckTypes()));
                opArgs.add(addMalTypes(op.getUpdateTypes()));
                opArgs.add(addMalTypes(op.getRetTypes()));
                break;
            case PUBSUB_OP:
                opArgs.add(addMalTypes(op.getRetTypes()));
                break;
        }

        return opArgs;
    }

    // Generates the OperationField[] (...)
    private String addMalTypes(List<FieldInfo> ti) {
        boolean needXmlSchema = false;
        boolean needMalTypes = false;

        for (FieldInfo typeInfo : ti) {
            TypeReference type = typeInfo.getSourceType();

            if (StdStrings.XML.equals(type.getArea())) {
                needXmlSchema = true;
            } else {
                needMalTypes = true;
            }
        }

        if (needMalTypes && needXmlSchema) {
            throw new IllegalArgumentException("WARNING: Service specification uses multiple"
                    + " type specifications in the same message! This is not supported.");
        }

        String new_line_0 = "\n            ";
        String new_line_1 = "\n                ";

        String arrayArgs = this.generateOperationFieldsArray(ti, new_line_1);
        return new_line_0 + "new " + OP_FIELD + "[] {" + arrayArgs + "}";
    }

    private String generateOperationFieldsArray(List<FieldInfo> ti, String newLine) {
        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < ti.size(); i++) {
            FieldInfo typeInfo = ti.get(i);
            buffer.append(newLine);
            buffer.append("new ").append(OP_FIELD).append("(");
            String argName = typeInfo.getFieldName();

            if (argName == null) {
                String separator = generator.getConfig().getNamingSeparator();
                argName = "_" + TypeUtils.shortTypeName(separator, typeInfo.getTargetType()) + i;
            }

            buffer.append("\"").append(argName).append("\"");
            buffer.append(", ");
            buffer.append(typeInfo.getCanBeNull());
            buffer.append(", ");

            TypeReference type = typeInfo.getSourceType();

            if (generator.isAbstract(type)) {
                buffer.append("null");
            } else {
                buffer.append(typeInfo.getMalShortFormField());
            }

            buffer.append((i != ti.size() - 1) ? ")," : ")");
        }

        return buffer.toString();
    }
}
