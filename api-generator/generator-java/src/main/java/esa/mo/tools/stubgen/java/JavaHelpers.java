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
import esa.mo.tools.stubgen.specification.AttributeTypeDetails;
import esa.mo.tools.stubgen.specification.CompositeField;
import esa.mo.tools.stubgen.specification.ServiceSummary;
import esa.mo.tools.stubgen.specification.StdStrings;
import esa.mo.tools.stubgen.specification.TypeUtils;
import esa.mo.tools.stubgen.writers.ClassWriter;
import esa.mo.xsd.AreaType;
import esa.mo.xsd.AttributeType;
import esa.mo.xsd.CompositeType;
import esa.mo.xsd.EnumerationType;
import esa.mo.xsd.ErrorDefinitionType;
import esa.mo.xsd.ServiceType;
import java.io.File;
import java.io.IOException;
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

    public void createServiceHelperClass(File serviceFolder, String area,
            ServiceType service, ServiceSummary summary) throws IOException {
        ClassWriter file = generator.createClassFile(serviceFolder, service.getName() + "Helper");

        String serviceName = service.getName();
        String serviceCAPS = serviceName.toUpperCase();

        file.addPackageStatement(area, service.getName(), null);
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

                file.addClassVariable(true, true, StdStrings.PUBLIC,
                        _errorNumberVar, false, String.valueOf(error.getNumber()));
                file.addClassVariable(true, true, StdStrings.PUBLIC,
                        errorNumberVar, false, "(_" + errorNameCaps + "_ERROR_NUMBER)");
            }
        }

        CompositeField serviceInstVar = generator.createCompositeElementsDetails(file, false, serviceCAPS + "_SERVICE",
                TypeUtils.createTypeReference(area, service.getName(), serviceName + JavaServiceInfo.SERVICE_INFO, false),
                false, true, "Service singleton instance.");

        file.addClassVariable(true, true, StdStrings.PUBLIC, serviceInstVar, true, "()");
        file.addClassCloseStatement();
        file.flush();
    }

    public void createAreaHelperClass(File areaFolder, AreaType area) throws IOException {
        ClassWriter file = generator.createClassFile(areaFolder, area.getName() + "Helper");

        String areaName = area.getName();
        String areaNameCAPS = area.getName().toUpperCase();
        String areaNumber = areaNameCAPS + "_AREA_NUMBER";

        file.addPackageStatement(area.getName(), null, null);

        CompositeField _areaNumberVar = generator.createCompositeElementsDetails(file, false, "_" + areaNumber,
                TypeUtils.createTypeReference(null, null, "int", false),
                false, false, "Area number literal.");
        CompositeField areaNumberVar = generator.createCompositeElementsDetails(file, false, areaNumber,
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.USHORT, false),
                true, false, "Area number instance.");
        CompositeField areaNameVar = generator.createCompositeElementsDetails(file, false, areaNameCAPS + "_AREA_NAME",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, false),
                true, false, "Area name constant.");
        CompositeField _areaVersionVar = generator.createCompositeElementsDetails(file, false, "_" + areaNameCAPS + "_AREA_VERSION",
                TypeUtils.createTypeReference(null, null, "short", false),
                false, false, "Area version literal.");
        CompositeField areaVersionVar = generator.createCompositeElementsDetails(file, false, areaNameCAPS + "_AREA_VERSION",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UOCTET, false),
                true, false, "Area version instance.");
        CompositeField areaVar = generator.createCompositeElementsDetails(file, false, areaNameCAPS + "_AREA",
                TypeUtils.createTypeReference(StdStrings.MAL, null, "MALArea", false),
                false, true, "Area singleton instance.");

        file.addClassOpenStatement(areaName + "Helper", false, false, null, null, "Helper class for " + areaName + " area.");

        file.addClassVariable(true, true, StdStrings.PUBLIC, _areaNumberVar, false, String.valueOf(area.getNumber()));
        file.addClassVariable(true, true, StdStrings.PUBLIC, areaNumberVar, false, "(_" + areaNumber + ")");
        file.addClassVariable(true, true, StdStrings.PUBLIC, areaNameVar, false, "(\"" + areaName + "\")");
        file.addClassVariable(true, true, StdStrings.PUBLIC, _areaVersionVar, false, String.valueOf(area.getVersion()));
        file.addClassVariable(true, true, StdStrings.PUBLIC, areaVersionVar, false, "(_" + areaNameCAPS + "_AREA_VERSION)");

        List<String> elementList = new LinkedList<>();

        if ((area.getDataTypes() != null) && !area.getDataTypes().getFundamentalOrAttributeOrComposite().isEmpty()) {
            for (Object oType : area.getDataTypes().getFundamentalOrAttributeOrComposite()) {
                if (oType instanceof AttributeType) {
                    AttributeType dt = (AttributeType) oType;
                    AttributeTypeDetails details = generator.getAttributeDetails(area.getName(), dt.getName());
                    String theType;

                    if (details.isNativeType()) {
                        theType = generator.createElementType(StdStrings.MAL, null, StdStrings.UNION) + "(" + details.getDefaultValue() + ")";
                    } else {
                        theType = generator.createElementType(area.getName(), null, dt.getName()) + "()";
                    }

                    String lclsName = generator.createElementType(area.getName(), null, dt.getName() + "List");
                    elementList.add("new " + theType);
                    elementList.add("new " + lclsName + "()");
                } else if (oType instanceof CompositeType) {
                    CompositeType dt = (CompositeType) oType;

                    if (dt.getShortFormPart() != null) {
                        String clsName = generator.createElementType(area.getName(), null, dt.getName());
                        String lclsName = generator.createElementType(area.getName(), null, dt.getName() + "List");
                        elementList.add("new " + clsName + "()");
                        elementList.add("new " + lclsName + "()");
                    }
                } else if (oType instanceof EnumerationType) {
                    EnumerationType dt = (EnumerationType) oType;
                    String clsName = generator.createElementType(area.getName(), null, dt.getName());
                    String lclsName = generator.createElementType(area.getName(), null, dt.getName() + "List");
                    elementList.add("new " + clsName + "()");
                    elementList.add("new " + lclsName + "()");
                    // Old code for Enumerations
                    //elementList.add(clsName + ".fromOrdinal(0)");
                    //elementList.add("new " + lclsName + "()");
                }
            }
        }

        StringBuilder buf = new StringBuilder();
        for (String objectCall : elementList) {
            buf.append("\n        ").append(objectCall).append(",");
        }
        CompositeField objectInstVar = generator.createCompositeElementsDetails(file, false, areaNameCAPS + "_AREA_ELEMENTS",
                TypeUtils.createTypeReference(null, null, "org.ccsds.moims.mo.mal.structures.Element", false),
                false, true, "Area Elements.");
        file.addClassVariableNewInit(true, true, StdStrings.PUBLIC, objectInstVar,
                false, true, buf.toString(), false);

        StringBuilder buf_2 = new StringBuilder();
        for (ServiceType service : area.getService()) {
            String helperType = generator.createElementType(area.getName(),
                    service.getName(), null, service.getName() + "Helper");
            String ns = generator.convertToNamespace(helperType) + "." + service.getName().toUpperCase() + "_SERVICE";
            buf_2.append("\n        ").append(ns).append(",");
        }
        CompositeField areaServices = generator.createCompositeElementsDetails(file, false, areaNameCAPS + "_AREA_SERVICES",
                TypeUtils.createTypeReference(null, null, "org.ccsds.moims.mo.mal.ServiceInfo", false),
                false, true, "Services in this Area.");
        file.addClassVariableNewInit(true, true, StdStrings.PUBLIC, areaServices,
                false, true, buf_2.toString(), false);

        String areaObjectInitialValue = createAreaHelperClassInitialValue(areaNameCAPS, area.getVersion());
        file.addClassVariable(true, true, StdStrings.PUBLIC, areaVar, true, areaObjectInitialValue);

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

        file.addClassCloseStatement();
        file.flush();
    }

    public String createAreaHelperClassInitialValue(String areaVar, short areaVersion) {
        return "(" + areaVar + "_AREA_NUMBER, " + areaVar + "_AREA_NAME, "
                + areaVar + "_AREA_VERSION, " + areaVar + "_AREA_ELEMENTS, " + areaVar + "_AREA_SERVICES)";
    }
}
