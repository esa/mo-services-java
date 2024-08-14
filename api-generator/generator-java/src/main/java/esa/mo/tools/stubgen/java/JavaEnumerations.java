/* ----------------------------------------------------------------------------
 * Copyright (C) 2023      European Space Agency
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
import esa.mo.tools.stubgen.specification.StdStrings;
import esa.mo.tools.stubgen.specification.TypeUtils;
import esa.mo.tools.stubgen.writers.ClassWriter;
import esa.mo.tools.stubgen.writers.MethodWriter;
import esa.mo.xsd.AreaType;
import esa.mo.xsd.EnumerationType;
import esa.mo.xsd.ServiceType;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class JavaEnumerations {

    private final GeneratorLangs generator;

    public JavaEnumerations(GeneratorLangs generator) {
        this.generator = generator;
    }

    public void createEnumerationClass(File folder, AreaType area, ServiceType service, EnumerationType enumeration) throws IOException {
        String enumName = enumeration.getName();
        long enumSize = enumeration.getItem().size();
        ClassWriter file = generator.createClassFile(folder, enumName);

        file.addPackageStatement(area, service, generator.getConfig().getStructureFolder());

        file.addClassOpenStatement(enumName, true, false,
                generator.createElementType(StdStrings.MAL, null, StdStrings.ENUMERATION),
                null, "Enumeration class for " + enumName + ".");

        String fqEnumName = generator.createElementType(area, service, enumName);
        CompositeField elementType = generator.createCompositeElementsDetails(file, false, "return",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.ELEMENT, false),
                true, true, null);
        CompositeField uintType = generator.createCompositeElementsDetails(file, false, "return",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UINTEGER, false),
                true, true, null);
        CompositeField enumType = generator.createCompositeElementsDetails(file, false, "return",
                TypeUtils.createTypeReference(area.getName(), service == null ? null : service.getName(), enumName, false),
                true, true, null);

        generator.addTypeShortFormDetails(file, area, service, enumeration.getShortFormPart());

        // create attributes
        String highestIndex = "";
        for (int i = 0; i < enumSize; i++) {
            EnumerationType.Item item = enumeration.getItem().get(i);
            String value = item.getValue();

            highestIndex = "_" + value + "_INDEX";
            CompositeField _eNumberVar = generator.createCompositeElementsDetails(file, false, highestIndex,
                    TypeUtils.createTypeReference(null, null, "int", false), false, false,
                    "Enumeration ordinal index for value " + value);
            CompositeField eValueVar = generator.createCompositeElementsDetails(file, false, value + "_NUM_VALUE",
                    TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UINTEGER, false), true, false,
                    "Enumeration numeric value for value " + value);
            CompositeField eInstVar = generator.createCompositeElementsDetails(file, false, value,
                    TypeUtils.createTypeReference(area.getName(), service == null ? null : service.getName(), enumName, false),
                    true, false, "Enumeration singleton for value " + value);
            file.addClassVariable(true, true, StdStrings.PUBLIC, _eNumberVar, false, String.valueOf(i));
            file.addClassVariable(true, true, StdStrings.PUBLIC, eValueVar, false, "(" + item.getNvalue() + ")");
            file.addClassVariable(true, true, StdStrings.PUBLIC, eInstVar, true, "(" + generator.convertToNamespace(fqEnumName + "._" + value + "_INDEX)"));
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
        CompositeField eInstArrVar = generator.createCompositeElementsDetails(file, false, "_ENUMERATIONS",
                TypeUtils.createTypeReference(area.getName(), service == null ? null : service.getName(), enumName, false),
                true, true, "Set of enumeration instances.");
        file.addClassVariable(true, true, StdStrings.PRIVATE, eInstArrVar, true, true, opStr);
        if (generator.supportsToString) {
            CompositeField eStrArrVar = generator.createCompositeElementsDetails(file, false, "_ENUMERATION_NAMES",
                    TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.STRING, false),
                    true, true, "Set of enumeration string values.");
            file.addClassVariable(true, true, StdStrings.PRIVATE, eStrArrVar, true, true, stStr);
        }
        CompositeField eValueArrVar = generator.createCompositeElementsDetails(file, false, "_ENUMERATION_NUMERIC_VALUES",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UINTEGER, false),
                true, false, "Set of enumeration values.");
        file.addClassVariable(true, true, StdStrings.PRIVATE, eValueArrVar, true, true, vaStr);

        file.addStatement("    public " + enumName + "() {");
        file.addStatement("        super(0);");
        file.addStatement("    }");

        // create private constructor
        MethodWriter method = file.addConstructor(StdStrings.PUBLIC, enumName,
                generator.createCompositeElementsDetails(file, false, "ordinal",
                        TypeUtils.createTypeReference(null, null, "int", false),
                        false, false, null), true, null, null, null);
        method.addMethodCloseStatement();

        // add getters and setters
        if (generator.supportsToString) {
            CompositeField strType = generator.createCompositeElementsDetails(file, false, "s",
                    TypeUtils.createTypeReference(null, null, "_String", false),
                    false, true, "s The string to search for.");

            method = file.addMethodOpenStatementOverride(strType, "toString", null, null);
            method.addLine("switch (getOrdinal()) {", false);

            for (EnumerationType.Item item : enumeration.getItem()) {
                method.addLine("  case _" + item.getValue() + "_INDEX:", false);
                method.addLine("    return \"" + item.getValue() + "\"");
            }
            method.addLine("  default:", false);
            method.addLine("    throw new RuntimeException(\"Unknown ordinal!\")");
            method.addLine("}", false);
            method.addMethodCloseStatement();

            method = file.addMethodOpenStatement(false, true, StdStrings.PUBLIC,
                    false, true, enumType, "fromString", Arrays.asList(strType), null,
                    "Returns the enumeration element represented by the supplied string, or null if not matched.",
                    "The matched enumeration element, or null if not matched.", null);
            method.addLine("for (int i = 0; i < _ENUMERATION_NAMES.length; i++) {", false);
            method.addLine("  if (_ENUMERATION_NAMES[i].equals(s)) {", false);
            method.addLine("    return _ENUMERATIONS[i]");
            method.addLine("  }", false);
            method.addLine("}", false);
            method.addLine("return null");
            method.addMethodCloseStatement();
        }

        // create getMALValue method
        if (generator.supportsToValue) {
            method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                    false, false, elementType, "clone", null, null);
            method.addLine("return this");
            method.addMethodCloseStatement();
        }

        CompositeField ordType = generator.createCompositeElementsDetails(file, false, "ordinal",
                TypeUtils.createTypeReference(null, null, "int", false),
                false, false, "ordinal The index of the enumeration element to return.");
        method = file.addMethodOpenStatement(false, false, false, false, StdStrings.PUBLIC,
                false, false, enumType, "fromOrdinal", Arrays.asList(ordType), null,
                "Returns the nth element of the enumeration", "The matched enumeration element", null);
        method.addArrayMethodStatement("_ENUMERATIONS", "ordinal", highestIndex);
        method.addMethodCloseStatement();

        CompositeField nvType = generator.createCompositeElementsDetails(file, false, "value",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UINTEGER, false),
                true, false, "value The numeric value to search for.");
        method = file.addMethodOpenStatement(false, false, false, true, StdStrings.PUBLIC,
                false, false, enumType, "fromNumericValue", Arrays.asList(nvType), null,
                "Returns the enumeration element represented by the supplied numeric value, or null if not matched.",
                "The matched enumeration value, or null if not matched.", null);
        method.addLine("for (int i = 0; i < _ENUMERATION_NUMERIC_VALUES.length; i++) {", false);
        method.addLine("  if (" + getEnumValueCompare("_ENUMERATION_NUMERIC_VALUES[i]", "value") + ") {", false);
        method.addLine("    return _ENUMERATIONS[i]");
        method.addLine("  }", false);
        method.addLine("}", false);
        method.addLine("return " + generator.getNullValue());
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
            CompositeField encodedType = generator.createCompositeElementsDetails(file, false, "return",
                    TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.UOCTET, false),
                    true, true, null);
            method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                    false, false, encodedType, "getOrdinalUOctet", null, null,
                    "Returns the index of the enumerated item as a {@code UOctet}.",
                    "the index of the enumerated item as a {@code UOctet}.", null);
            method.addLine("return " + enumEncoderValue);
            method.addMethodCloseStatement();
        }

        method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC,
                false, true, uintType, "getNumericValue", null, null,
                "Returns the numeric value of the enumeration element.", "The numeric value", null);
        method.addArrayMethodStatement("_ENUMERATION_NUMERIC_VALUES", "ordinal", highestIndex);
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatementOverride(elementType, "createElement", null, null);
        method.addLine("return _ENUMERATIONS[0]");
        method.addMethodCloseStatement();

        // Generate the getEnumSize() operation
        file.addStatement("    @Override");
        file.addStatement("    public int getEnumSize() {");
        file.addStatement("        return " + enumSize + ";");
        file.addStatement("    }");

        /*
        // create encode method
        method = generator.encodeMethodOpen(file);
        method.addLine(generator.createMethodCall("encoder.encode") + enumOrdinalType + "(" + enumEncoderValue + ")");
        method.addMethodCloseStatement();

        // create decode method
        method = generator.decodeMethodOpen(file, elementType);
        method.addLine("return fromOrdinal(" + generator.createMethodCall("decoder.decode" + enumOrdinalType + "()" + enumDecoderValue + ")"));
        method.addMethodCloseStatement();
         */
        generator.addTypeIdGetterMethod(file, area, service);

        file.addClassCloseStatement();

        file.flush();

        generator.createListClass(folder, area, service, enumName, false, enumeration.getShortFormPart());
        CompositeField fld = generator.createCompositeElementsDetails(file, false, "fld",
                TypeUtils.createTypeReference(area.getName(), service == null ? null : service.getName(), enumName, false),
                true, true, "cmt");
    }

    protected String getEnumEncoderValue(long maxValue) {
        String enumEncoderValue = "new org.ccsds.moims.mo.mal.structures.UInteger(ordinal.longValue())";
        if (maxValue < 256) {
            enumEncoderValue = "new org.ccsds.moims.mo.mal.structures.UOctet(ordinal.shortValue())";
        } else if (maxValue < 65536) {
            enumEncoderValue = "new org.ccsds.moims.mo.mal.structures.UShort(ordinal.intValue())";
        }

        return enumEncoderValue;
    }

    protected String getEnumDecoderValue(long maxValue) {
        return ".getValue()";
    }

    protected String getEnumValueCompare(String lhs, String rhs) {
        return lhs + ".equals(" + rhs + ")";
    }

}
