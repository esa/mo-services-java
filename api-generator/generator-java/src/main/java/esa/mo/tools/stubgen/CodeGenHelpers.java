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
import esa.mo.tools.stubgen.specification.TypeUtils;
import esa.mo.tools.stubgen.writers.ClassWriter;
import esa.mo.tools.stubgen.writers.MethodWriter;
import esa.mo.xsd.AnyTypeReference;
import esa.mo.xsd.AreaType;
import esa.mo.xsd.AttributeType;
import esa.mo.xsd.CompositeType;
import esa.mo.xsd.EnumerationType;
import esa.mo.xsd.ErrorDefinitionType;
import esa.mo.xsd.ExtendedServiceType;
import esa.mo.xsd.ModelObjectType;
import esa.mo.xsd.NamedElementReferenceWithCommentType;
import esa.mo.xsd.PubSubOperationType;
import esa.mo.xsd.ServiceType;
import esa.mo.xsd.SupportedFeatures;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class CodeGenHelpers {

    private final GeneratorLangs generator;

    public CodeGenHelpers(GeneratorLangs generator) {
        this.generator = generator;
    }

    public void createServiceHelperClass(File serviceFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException {
        generator.getLog().info("Creating service helper class: " + service.getName());
        ClassWriterProposed file = generator.createClassFile(serviceFolder, service.getName() + "Helper");

        String serviceName = service.getName();
        String serviceVar = serviceName.toUpperCase();

        file.addPackageStatement(area, service, null);

        String throwsMALException = generator.createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String identifierType = generator.createElementType(file, StdStrings.MAL, null, StdStrings.IDENTIFIER);
        CompositeField eleFactory = generator.createCompositeElementsDetails(file, false, "bodyElementFactory", 
                TypeUtils.createTypeReference(StdStrings.MAL, null, "MALElementsRegistry", false), 
                false, true, "bodyElementFactory The element factory registry to initialise with this helper.");

        file.addClassOpenStatement(serviceName + "Helper", false, false, null, 
                null, "Helper class for " + serviceName + " service.");

        // create error numbers
        if ((null != service.getErrors()) && !service.getErrors().getError().isEmpty()) {
            for (ErrorDefinitionType error : service.getErrors().getError()) {
                String errorNameCaps = error.getName().toUpperCase();
                CompositeField _errorNumberVar = generator.createCompositeElementsDetails(file, false, "_" + errorNameCaps + "_ERROR_NUMBER", 
                        TypeUtils.createTypeReference(null, null, "long", false), 
                        false, true, "Error literal for error " + errorNameCaps);
                CompositeField errorNumberVar = generator.createCompositeElementsDetails(file, false, errorNameCaps + "_ERROR_NUMBER", 
                        TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UINTEGER, false), 
                        true, true, "Error instance for error " + errorNameCaps);

                file.addClassVariable(true, true, StdStrings.PUBLIC, _errorNumberVar, false, String.valueOf(error.getNumber()));
                file.addClassVariable(true, true, StdStrings.PUBLIC, errorNumberVar, false, "(_" + errorNameCaps + "_ERROR_NUMBER)");
            }
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
        CompositeField serviceInstVar;
        if (service instanceof ExtendedServiceType) {
            serviceInstVar = generator.createCompositeElementsDetails(file, false, serviceVar + "_SERVICE", 
                    TypeUtils.createTypeReference(StdStrings.COM, null, "COMService", false), 
                    false, true, "Service singleton instance.");
        } else {
            serviceInstVar = generator.createCompositeElementsDetails(file, false, serviceVar + "_SERVICE", 
                    TypeUtils.createTypeReference(StdStrings.MAL, null, "MALService", false), 
                    false, true, "Service singleton instance.");
        }

        file.addClassVariable(true, true, StdStrings.PUBLIC, _serviceNumberVar, false, String.valueOf(service.getNumber()));
        file.addClassVariable(true, true, StdStrings.PUBLIC, serviceNumberVar, false, "(_" + serviceVar + "_SERVICE_NUMBER)");
        file.addClassVariable(true, true, StdStrings.PUBLIC, serviceNameVar, false, "(\"" + serviceName + "\")");

        String serviceObjectInitialValue = generator.createServiceHelperClassInitialValue(serviceVar);
        file.addClassVariable(true, false, StdStrings.PUBLIC, serviceInstVar, true, serviceObjectInitialValue);

        for (OperationSummary op : summary.getOperations()) {
            String operationInstanceVar = op.getName().toUpperCase();
            CompositeField _opNumberVar = generator.createCompositeElementsDetails(file, false, "_" + operationInstanceVar + "_OP_NUMBER", 
                    TypeUtils.createTypeReference(null, null, "int", false), false, false, "Operation number literal for operation " + operationInstanceVar);
            CompositeField opNumberVar = generator.createCompositeElementsDetails(file, false, operationInstanceVar + "_OP_NUMBER", 
                    TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.USHORT, false), true, false, "Operation number instance for operation " + operationInstanceVar);
            file.addClassVariable(true, true, StdStrings.PUBLIC, _opNumberVar, false, op.getNumber().toString());
            file.addClassVariable(true, true, StdStrings.PUBLIC, opNumberVar, false, "(_" + operationInstanceVar + "_OP_NUMBER)");

            List<String> opArgs = new LinkedList<>();
            generator.addServiceHelperOperationArgs(file, op, opArgs);
            CompositeField opInstVar = generator.createCompositeElementsDetails(file, false, operationInstanceVar + "_OP", 
                    TypeUtils.createTypeReference(StdStrings.MAL, null, generator.getOperationInstanceType(op), false), false, true, "Operation instance for operation " + operationInstanceVar);
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
        }

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

        MethodWriter method = file.addMethodOpenStatement(false, true, StdStrings.PUBLIC,
                false, true, null, "init", Arrays.asList(eleFactory), throwsMALException,
                "Registers all aspects of this service with the provided element factory",
                null, Arrays.asList(throwsMALException + " If cannot initialise this helper."));

        // Add the if condition to check if it has already been registered!
        method.addLine("if (org.ccsds.moims.mo.mal.MALContextFactory.lookupArea(", false);
        method.addLine("   " + ns + "_NAME,", false);
        method.addLine("   " + ns + "_VERSION) == null) {", false);
        method.addLine("  " + hlp + ".init(bodyElementFactory);", false);
        method.addLine("}", false);

        generator.addServiceConstructor(method, serviceVar, String.valueOf(area.getVersion()), summary);

        if (!comObjectCalls.isEmpty()) {
            for (String objectCall : comObjectCalls) {
                method.addLine(generator.createMethodCall(serviceVar + "_SERVICE.addCOMObject(" + objectCall + "_OBJECT)"));
            }
        }

        method.addMethodWithDependencyStatement(generator.createMethodCall(ns + ".addService(" + serviceVar + "_SERVICE)"), ns, true);

        List<String> typeCalls = new LinkedList<>();
        List<String> callableHashMap = new LinkedList<>();

        if ((null != service.getDataTypes()) && !service.getDataTypes().getCompositeOrEnumeration().isEmpty()) {
            for (Object oType : service.getDataTypes().getCompositeOrEnumeration()) {
                String typeName = "";
                boolean isAbstract = false;
                if (oType instanceof EnumerationType) {
                    typeName = ((EnumerationType) oType).getName();
                } else if (oType instanceof CompositeType) {
                    typeName = ((CompositeType) oType).getName();
                    isAbstract = (null == ((CompositeType) oType).getShortFormPart());
                }

                if (!isAbstract) {
                    String clsName = generator.convertClassName(generator.createElementType(file, area.getName(), service.getName(), typeName));
                    String text = "new " + clsName + "()";
                    if (oType instanceof EnumerationType) {
                        text = clsName + ".fromOrdinal(0)";
                    }

                    GeneratorConfiguration config = generator.getConfig();
                    String factoryName = generator.convertClassName(generator.createElementType(file, area.getName(), service.getName(), config.getStructureFolder() + config.getNamingSeparator() + config.getFactoryFolder(), typeName + "Factory"));
                    String lclsName = generator.convertClassName(generator.createElementType(file, area.getName(), service.getName(), typeName + "List"));
                    String lfactoryName = generator.convertClassName(generator.createElementType(file, area.getName(), service.getName(), config.getStructureFolder() + config.getNamingSeparator() + config.getFactoryFolder(), typeName + "ListFactory"));
                    typeCalls.add(clsName + config.getNamingSeparator() + "SHORT_FORM, new " + factoryName + "()");
                    typeCalls.add(lclsName + config.getNamingSeparator() + "SHORT_FORM, new " + lfactoryName + "()");
                    callableHashMap.add(clsName + config.getNamingSeparator() + "SHORT_FORM, () -> " + text);
                    callableHashMap.add(lclsName + config.getNamingSeparator() + "SHORT_FORM, () -> new " + lclsName + "()");
                }
            }
        }

        /*
        if (typeCalls.size() > 0) {
            method.addLine("long timestamp_1 = System.currentTimeMillis()");

            for (String typeCall : typeCalls) {
                method.addLine(createMethodCall("bodyElementFactory.registerElementFactory(" + typeCall + ")"));
            }

            method.addLine("timestamp_1 = System.currentTimeMillis() - timestamp_1");
        }
         */
        if (!callableHashMap.isEmpty()) {
            // method.addLine("long timestamp_2 = System.currentTimeMillis()");

            for (String typeCall : callableHashMap) {
                method.addLine("bodyElementFactory.addCallableElement(" + typeCall + ")");
            }

            // method.addLine("timestamp_2 = System.currentTimeMillis() - timestamp_2");
        }

        /*
        // Measure performance of Factories vs. callables
        if (typeCalls.size() > 0 && callableHashMap.size() > 0) {
            method.addLine("java.util.logging.Logger.getLogger(" + service.getName()
                    + "Helper.class.getName()).log(java.util.logging.Level.INFO, \"\\nTime 1: \" + timestamp_1 + \"\\nTime 2: \" + timestamp_2 + \"\\nHow many: \" + bodyElementFactory.howMany())");
        }
         */
        // register error numbers
        if ((null != service.getErrors()) && !service.getErrors().getError().isEmpty()) {
            String factoryType = generator.createElementType(file, StdStrings.MAL, null, null, "MALContextFactory");

            for (ErrorDefinitionType error : service.getErrors().getError()) {
                String errorNameCaps = error.getName().toUpperCase();
                method.addLine(generator.convertToNamespace(factoryType + ".registerError(" + errorNameCaps + "_ERROR_NUMBER, new " + identifierType + "(\"" + error.getName() + "\"))"));
            }
        }

        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, true, StdStrings.PUBLIC, false, 
                true, null, "deepInit", Arrays.asList(eleFactory), throwsMALException, 
                "Registers all aspects of this service with the provided element factory and any referenced areas/services.", 
                null, Arrays.asList(throwsMALException + " If cannot initialise this helper."));
        method.addLine("init(bodyElementFactory)");
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

    protected void createAreaHelperClass(File areaFolder, AreaType area) throws IOException {
        generator.getLog().info("Creating area helper class: " + area.getName());
        ClassWriter file = generator.createClassFile(areaFolder, area.getName() + "Helper");

        String areaName = area.getName();
        String areaNameCaps = area.getName().toUpperCase();

        file.addPackageStatement(area, null, null);

        String throwsMALException = generator.createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String identifierType = generator.createElementType(file, StdStrings.MAL, null, StdStrings.IDENTIFIER);
        CompositeField eleFactory = generator.createCompositeElementsDetails(file, false, "bodyElementFactory", 
                TypeUtils.createTypeReference(StdStrings.MAL, null, "MALElementsRegistry", false), 
                false, true, "bodyElementFactory The element factory registry to initialise with this helper.");
        CompositeField _areaNumberVar = generator.createCompositeElementsDetails(file, false, "_" + areaNameCaps + "_AREA_NUMBER", 
                TypeUtils.createTypeReference(null, null, "int", false), 
                false, false, "Area number literal.");
        CompositeField areaNumberVar = generator.createCompositeElementsDetails(file, false, areaNameCaps + "_AREA_NUMBER", 
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.USHORT, false), 
                true, false, "Area number instance.");
        CompositeField areaNameVar = generator.createCompositeElementsDetails(file, false, areaNameCaps + "_AREA_NAME", 
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, false), 
                true, false, "Area name constant.");
        CompositeField _areaVersionVar = generator.createCompositeElementsDetails(file, false, "_" + areaNameCaps + "_AREA_VERSION", 
                TypeUtils.createTypeReference(null, null, "short", false), 
                false, false, "Area version literal.");
        CompositeField areaVersionVar = generator.createCompositeElementsDetails(file, false, areaNameCaps + "_AREA_VERSION", 
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UOCTET, false), 
                true, false, "Area version instance.");
        CompositeField areaVar = generator.createCompositeElementsDetails(file, false, areaNameCaps + "_AREA", 
                TypeUtils.createTypeReference(StdStrings.MAL, null, "MALArea", false), 
                false, true, "Area singleton instance.");

        file.addClassOpenStatement(areaName + "Helper", false, false, null, null, "Helper class for " + areaName + " area.");

        file.addClassVariable(true, true, StdStrings.PUBLIC, _areaNumberVar, false, String.valueOf(area.getNumber()));
        file.addClassVariable(true, true, StdStrings.PUBLIC, areaNumberVar, false, "(_" + areaNameCaps + "_AREA_NUMBER)");

        file.addClassVariable(true, true, StdStrings.PUBLIC, areaNameVar, false, "(\"" + areaName + "\")");

        file.addClassVariable(true, true, StdStrings.PUBLIC, _areaVersionVar, false, String.valueOf(area.getVersion()));
        file.addClassVariable(true, true, StdStrings.PUBLIC, areaVersionVar, false, "(_" + areaNameCaps + "_AREA_VERSION)");

        String areaObjectInitialValue = generator.createAreaHelperClassInitialValue(areaNameCaps, area.getVersion());
        file.addClassVariable(true, false, StdStrings.PUBLIC, areaVar, true, areaObjectInitialValue);

        // create error numbers
        if ((null != area.getErrors()) && !area.getErrors().getError().isEmpty()) {
            for (ErrorDefinitionType error : area.getErrors().getError()) {
                String errorNameCaps = error.getName().toUpperCase();
                CompositeField _errorNumberVar = generator.createCompositeElementsDetails(file, false, "_" + errorNameCaps + "_ERROR_NUMBER", 
                        TypeUtils.createTypeReference(null, null, "long", false), false, false, "Error literal for error " + errorNameCaps);
                CompositeField errorNumberVar = generator.createCompositeElementsDetails(file, false, errorNameCaps + "_ERROR_NUMBER", 
                        TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UINTEGER, false), true, false, "Error instance for error " + errorNameCaps);

                file.addClassVariable(true, true, StdStrings.PUBLIC, _errorNumberVar, false, String.valueOf(error.getNumber()));
                file.addClassVariable(true, true, StdStrings.PUBLIC, errorNumberVar, false, "(_" + errorNameCaps + "_ERROR_NUMBER)");
            }
        }

        List<String> typeCalls = new LinkedList<>();
        List<String> callableHashMap = new LinkedList<>();

        if ((null != area.getDataTypes()) && !area.getDataTypes().getFundamentalOrAttributeOrComposite().isEmpty()) {
            for (Object oType : area.getDataTypes().getFundamentalOrAttributeOrComposite()) {
                GeneratorConfiguration config = generator.getConfig();

                if (oType instanceof AttributeType) {
                    AttributeType dt = (AttributeType) oType;

                    String clsName = generator.convertClassName(generator.createElementType(file, area.getName(), null, StdStrings.ATTRIBUTE));
                    String factoryName = generator.convertClassName(generator.createElementType(file, area.getName(), null, config.getStructureFolder() + config.getNamingSeparator() + config.getFactoryFolder(), dt.getName() + "Factory"));

                    AttributeTypeDetails details = generator.getAttributeDetails(area.getName(), dt.getName());
                    String theType;

                    if (details.isNativeType()) {
                        theType = generator.convertClassName(generator.createElementType(file, StdStrings.MAL, null, StdStrings.UNION)) + "(" + details.getDefaultValue() + ")";
                    } else {
                        theType = generator.convertClassName(generator.createElementType(file, area.getName(), null, dt.getName()) + "()");
                    }

                    String attributeClsName = generator.convertClassName(generator.createElementType(file, area.getName(), null, dt.getName()));
                    String lclsName = generator.convertClassName(generator.createElementType(file, area.getName(), null, dt.getName() + "List"));
                    String lfactoryName = generator.convertClassName(generator.createElementType(file, area.getName(), null, config.getStructureFolder() + config.getNamingSeparator() + config.getFactoryFolder(), dt.getName() + "ListFactory"));
                    typeCalls.add(clsName + config.getNamingSeparator() + dt.getName().toUpperCase() + "_SHORT_FORM, new " + attributeClsName + "()");
                    typeCalls.add(lclsName + config.getNamingSeparator() + "SHORT_FORM, new " + lfactoryName + "()");

                    callableHashMap.add(clsName + config.getNamingSeparator() + dt.getName().toUpperCase() + "_SHORT_FORM, () -> new " + theType);
                    callableHashMap.add(lclsName + config.getNamingSeparator() + "SHORT_FORM, () -> new " + lclsName + "()");
                } else if (oType instanceof CompositeType) {
                    CompositeType dt = (CompositeType) oType;

                    if (null != dt.getShortFormPart()) {
                        String clsName = generator.convertClassName(generator.createElementType(file, area.getName(), null, dt.getName()));
                        String factoryName = generator.convertClassName(generator.createElementType(file, area.getName(), null, config.getStructureFolder() + config.getNamingSeparator() + config.getFactoryFolder(), dt.getName() + "Factory"));
                        String lclsName = generator.convertClassName(generator.createElementType(file, area.getName(), null, dt.getName() + "List"));
                        String lfactoryName = generator.convertClassName(generator.createElementType(file, area.getName(), null, config.getStructureFolder() + config.getNamingSeparator() + config.getFactoryFolder(), dt.getName() + "ListFactory"));
                        typeCalls.add(clsName + config.getNamingSeparator() + "SHORT_FORM, new " + factoryName + "()");
                        typeCalls.add(lclsName + config.getNamingSeparator() + "SHORT_FORM, new " + lfactoryName + "()");
                        callableHashMap.add(clsName + config.getNamingSeparator() + "SHORT_FORM, () -> new " + clsName + "()");
                        callableHashMap.add(lclsName + config.getNamingSeparator() + "SHORT_FORM, () -> new " + lclsName + "()");
                    }
                } else if (oType instanceof EnumerationType) {
                    EnumerationType dt = (EnumerationType) oType;
                    String clsName = generator.convertClassName(generator.createElementType(file, area.getName(), null, dt.getName()));
                    String factoryName = generator.convertClassName(generator.createElementType(file, area.getName(), null, config.getStructureFolder() + config.getNamingSeparator() + config.getFactoryFolder(), dt.getName() + "Factory"));
                    String lclsName = generator.convertClassName(generator.createElementType(file, area.getName(), null, dt.getName() + "List"));
                    String lfactoryName = generator.convertClassName(generator.createElementType(file, area.getName(), null, config.getStructureFolder() + config.getNamingSeparator() + config.getFactoryFolder(), dt.getName() + "ListFactory"));
                    typeCalls.add(clsName + config.getNamingSeparator() + "SHORT_FORM, new " + factoryName + "()");
                    typeCalls.add(lclsName + config.getNamingSeparator() + "SHORT_FORM, new " + lfactoryName + "()");
                    callableHashMap.add(clsName + config.getNamingSeparator() + "SHORT_FORM, () -> " + clsName + ".fromOrdinal(0)");
                    callableHashMap.add(lclsName + config.getNamingSeparator() + "SHORT_FORM, () -> new " + lclsName + "()");
                }
            }
        }

        String factoryType = generator.createElementType(file, StdStrings.MAL, null, null, "MALContextFactory");
        MethodWriter method = file.addMethodOpenStatement(false, true, StdStrings.PUBLIC, false, 
                true, null, "init", Arrays.asList(eleFactory), throwsMALException, 
                "Registers all aspects of this area with the provided element factory", null, 
                Arrays.asList(throwsMALException + " If cannot initialise this helper."));
        method.addLine(generator.convertToNamespace(factoryType + ".registerArea(" + areaNameCaps + "_AREA)"));

        /*
        if (0 < typeCalls.size()) {
            for (String typeCall : typeCalls) {
                method.addLine(createMethodCall("bodyElementFactory.registerElementFactory(" + typeCall + ")"));
            }
        }
         */
        if (!callableHashMap.isEmpty()) {
            for (String typeCall : callableHashMap) {
                method.addLine("bodyElementFactory.addCallableElement(" + typeCall + ")");
            }
        }

        // register error numbers
        if ((null != area.getErrors()) && !area.getErrors().getError().isEmpty()) {
            for (ErrorDefinitionType error : area.getErrors().getError()) {
                String errorNameCaps = error.getName().toUpperCase();
                method.addLine(generator.convertToNamespace(factoryType + ".registerError(" + errorNameCaps + "_ERROR_NUMBER, new " + identifierType + "(\"" + error.getName() + "\"))"));
            }
        }

        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, true, StdStrings.PUBLIC, false, 
                true, null, "deepInit", Arrays.asList(eleFactory), throwsMALException, 
                "Registers all aspects of this area with the provided element factory and any referenced areas and contained services.", 
                null, Arrays.asList(throwsMALException + " If cannot initialise this helper."));
        method.addLine("init(bodyElementFactory)");

        for (ServiceType service : area.getService()) {
            String helperType = generator.createElementType(file, area.getName(), service.getName(), null, service.getName() + "Helper");
            String ns = generator.convertToNamespace(helperType + ".deepInit(bodyElementFactory)");
            method.addMethodWithDependencyStatement(ns, ns, true);
        }

        method.addMethodCloseStatement();
        file.addClassCloseStatement();
        file.flush();
    }
}
