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

import esa.mo.tools.stubgen.ClassWriterProposed;
import esa.mo.tools.stubgen.GeneratorConfiguration;
import esa.mo.tools.stubgen.GeneratorLangs;
import esa.mo.tools.stubgen.specification.AttributeTypeDetails;
import esa.mo.tools.stubgen.specification.CompositeField;
import esa.mo.tools.stubgen.specification.ServiceSummary;
import esa.mo.tools.stubgen.specification.StdStrings;
import esa.mo.tools.stubgen.specification.TypeUtils;
import esa.mo.tools.stubgen.writers.ClassWriter;
import esa.mo.tools.stubgen.writers.MethodWriter;
import esa.mo.xsd.AreaType;
import esa.mo.xsd.AttributeType;
import esa.mo.xsd.CompositeType;
import esa.mo.xsd.EnumerationType;
import esa.mo.xsd.ErrorDefinitionType;
import esa.mo.xsd.ServiceType;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class JavaHelpers {

    private final GeneratorLangs generator;

    public JavaHelpers(GeneratorLangs generator) {
        this.generator = generator;
    }

    public void createServiceHelperClass(File serviceFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException {
        generator.getLog().info(" > Creating service Helper class: " + service.getName());
        ClassWriterProposed file = generator.createClassFile(serviceFolder, service.getName() + "Helper");

        String serviceName = service.getName();
        String serviceVar = serviceName.toUpperCase();

        file.addPackageStatement(area, service, null);

        String throwsMALException = generator.createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String identifierType = generator.createElementType(file, StdStrings.MAL, null, StdStrings.IDENTIFIER);
        CompositeField eleFactory = generator.createCompositeElementsDetails(file, false, "elementsRegistry",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "MALElementsRegistry", false),
                false, true, "elementsRegistry The element factory registry to initialise with this helper.");

        file.addClassOpenStatement(serviceName + "Helper", false, false, null,
                null, "Helper class for " + serviceName + " service.");

        // create error numbers
        if ((service.getErrors() != null) && !service.getErrors().getError().isEmpty()) {
            for (ErrorDefinitionType error : service.getErrors().getError()) {
                String errorNameCaps = JavaExceptions.convertToUppercaseWithUnderscores(error.getName());
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

        CompositeField serviceInstVar = generator.createCompositeElementsDetails(file, false, serviceVar + "_SERVICE",
                TypeUtils.createTypeReference(area.getName(), service.getName(), service.getName() + JavaServiceInfo.SERVICE_INFO, false),
                false, true, "Service singleton instance.");

        file.addClassVariable(true, false, StdStrings.PUBLIC, serviceInstVar, true, "()");

        // construct area helper class name and variable
        String hlp = generator.createElementType(file, area.getName(), null, null, area.getName() + "Helper");
        String prefix = generator.convertToNamespace(hlp + "." + area.getName().toUpperCase() + "_AREA");

        MethodWriter method = file.addMethodOpenStatement(false, true, StdStrings.PUBLIC,
                false, true, null, "init", Arrays.asList(eleFactory), throwsMALException,
                "Registers all aspects of this service with the provided element factory",
                null, Arrays.asList(throwsMALException + " If cannot initialise this helper."));

        // Add the if condition to check if it has already been registered!
        method.addLine("if (org.ccsds.moims.mo.mal.MALContextFactory.lookupArea(", false);
        method.addLine("   " + prefix + "_NAME,", false);
        method.addLine("   " + prefix + "_VERSION) == null) {", false);
        method.addLine("  " + hlp + ".init(elementsRegistry);", false);
        method.addLine("}", false);

        method.addMethodWithDependencyStatement(generator.createMethodCall(prefix + ".addService(" + serviceVar + "_SERVICE)"), prefix, true);

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
                method.addLine(createMethodCall("elementsRegistry.registerElementFactory(" + typeCall + ")"));
            }

            method.addLine("timestamp_1 = System.currentTimeMillis() - timestamp_1");
        }
         */
        if (!callableHashMap.isEmpty()) {
            // method.addLine("long timestamp_2 = System.currentTimeMillis()");

            for (String typeCall : callableHashMap) {
                method.addLine("elementsRegistry.addCallableElement(" + typeCall + ")");
            }

            // method.addLine("timestamp_2 = System.currentTimeMillis() - timestamp_2");
        }

        /*
        // Measure performance of Factories vs. callables
        if (typeCalls.size() > 0 && callableHashMap.size() > 0) {
            method.addLine("java.util.logging.Logger.getLogger(" + service.getName()
                    + "Helper.class.getName()).log(java.util.logging.Level.INFO, \"\\nTime 1: \" + timestamp_1 + \"\\nTime 2: \" + timestamp_2 + \"\\nHow many: \" + elementsRegistry.howMany())");
        }
         */
        // register error numbers
        if ((null != service.getErrors()) && !service.getErrors().getError().isEmpty()) {
            String factoryType = generator.createElementType(file, StdStrings.MAL, null, null, "MALContextFactory");

            for (ErrorDefinitionType error : service.getErrors().getError()) {
                String errorNameCaps = JavaExceptions.convertToUppercaseWithUnderscores(error.getName());
                method.addLine(generator.convertToNamespace(factoryType + ".registerError("
                        + prefix + "_NUMBER, "
                        + prefix + "_VERSION, "
                        + errorNameCaps + "_ERROR_NUMBER, "
                        + "new " + identifierType + "(\"" + error.getName() + "\"))"));
            }
        }

        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, true, StdStrings.PUBLIC, false,
                true, null, "deepInit", Arrays.asList(eleFactory), throwsMALException,
                "Registers all aspects of this service with the provided element factory and any referenced areas/services.",
                null, Arrays.asList(throwsMALException + " If cannot initialise this helper."));
        method.addLine("init(elementsRegistry)");
        method.addMethodCloseStatement();

        file.addClassCloseStatement();
        file.flush();
    }

    public void createAreaHelperClass(File areaFolder, AreaType area) throws IOException {
        generator.getLog().info(" > Creating area helper class: " + area.getName());
        ClassWriter file = generator.createClassFile(areaFolder, area.getName() + "Helper");

        String areaName = area.getName();
        String areaNameCaps = area.getName().toUpperCase();
        String areaNumber = areaNameCaps + "_AREA_NUMBER";

        file.addPackageStatement(area, null, null);

        String throwsMALException = generator.createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
        String identifierType = generator.createElementType(file, StdStrings.MAL, null, StdStrings.IDENTIFIER);
        CompositeField eleFactory = generator.createCompositeElementsDetails(file, false, "elementsRegistry",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "MALElementsRegistry", false),
                false, true, "elementsRegistry The element factory registry to initialise with this helper.");
        CompositeField _areaNumberVar = generator.createCompositeElementsDetails(file, false, "_" + areaNumber,
                TypeUtils.createTypeReference(null, null, "int", false),
                false, false, "Area number literal.");
        CompositeField areaNumberVar = generator.createCompositeElementsDetails(file, false, areaNumber,
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
        file.addClassVariable(true, true, StdStrings.PUBLIC, areaNumberVar, false, "(_" + areaNumber + ")");
        file.addClassVariable(true, true, StdStrings.PUBLIC, areaNameVar, false, "(\"" + areaName + "\")");
        file.addClassVariable(true, true, StdStrings.PUBLIC, _areaVersionVar, false, String.valueOf(area.getVersion()));
        file.addClassVariable(true, true, StdStrings.PUBLIC, areaVersionVar, false, "(_" + areaNameCaps + "_AREA_VERSION)");

        String areaObjectInitialValue = generator.createAreaHelperClassInitialValue(areaNameCaps, area.getVersion());
        file.addClassVariable(true, false, StdStrings.PUBLIC, areaVar, true, areaObjectInitialValue);

        // create error numbers
        if ((area.getErrors() != null) && !area.getErrors().getError().isEmpty()) {
            for (ErrorDefinitionType error : area.getErrors().getError()) {
                String errorNameCaps = JavaExceptions.convertToUppercaseWithUnderscores(error.getName());
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
                method.addLine(createMethodCall("elementsRegistry.registerElementFactory(" + typeCall + ")"));
            }
        }
         */
        if (!callableHashMap.isEmpty()) {
            for (String typeCall : callableHashMap) {
                method.addLine("elementsRegistry.addCallableElement(" + typeCall + ")");
            }
        }

        // register error numbers
        if ((null != area.getErrors()) && !area.getErrors().getError().isEmpty()) {
            for (ErrorDefinitionType error : area.getErrors().getError()) {
                String errorNameCaps = JavaExceptions.convertToUppercaseWithUnderscores(error.getName());
                method.addLine(generator.convertToNamespace(
                        factoryType + ".registerError("
                        + areaNumber + ", "
                        + areaNameCaps + "_AREA_VERSION, "
                        + errorNameCaps + "_ERROR_NUMBER, "
                        + "new " + identifierType + "(\"" + error.getName() + "\"))"));
            }
        }

        method.addMethodCloseStatement();

        method = file.addMethodOpenStatement(false, true, StdStrings.PUBLIC, false,
                true, null, "deepInit", Arrays.asList(eleFactory), throwsMALException,
                "Registers all aspects of this area with the provided element factory and any referenced areas and contained services.",
                null, Arrays.asList(throwsMALException + " If cannot initialise this helper."));
        method.addLine("init(elementsRegistry)");

        for (ServiceType service : area.getService()) {
            String helperType = generator.createElementType(file, area.getName(), service.getName(), null, service.getName() + "Helper");
            String ns = generator.convertToNamespace(helperType + ".deepInit(elementsRegistry)");
            method.addMethodWithDependencyStatement(ns, ns, true);
        }

        method.addMethodCloseStatement();
        file.addClassCloseStatement();
        file.flush();
    }
}
