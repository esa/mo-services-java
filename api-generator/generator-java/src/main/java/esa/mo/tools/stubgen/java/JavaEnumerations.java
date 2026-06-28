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
import java.util.LinkedList;
import java.util.List;

/**
 * Generates the Java code for the MO Enumeration types.
 */
public class JavaEnumerations {

    private final GeneratorLangs generator;

    public JavaEnumerations(GeneratorLangs generator) {
        this.generator = generator;
    }

    public void createEnumerationClass(File folder, AreaType area,
            ServiceType service, EnumerationType enumeration) throws IOException {
        String enumName = enumeration.getName();
        long enumSize = enumeration.getItem().size();
        ClassWriter file = generator.createClassFile(folder, enumName);

        String serviceName = (service == null) ? null : service.getName();
        file.addPackageStatement(area.getName(), serviceName, generator.getConfig().getStructureFolder());

        file.addClassOpenStatement(enumName, true, false,
                generator.createElementType(StdStrings.MAL, null, StdStrings.ENUMERATION),
                null, "Enumeration class for " + enumName + ".");

        String fqEnumName = generator.createElementType(area, service, enumName);
        CompositeField enumType = generator.createCompositeElementsDetails(file, false, "return",
                TypeUtils.createTypeReference(area.getName(), serviceName, enumName, false),
                true, true, null);

        generator.addTypeShortFormDetails(file, area, service, enumeration.getShortFormPart());

        // Create variables
        for (int i = 0; i < enumSize; i++) {
            EnumerationType.Item item = enumeration.getItem().get(i);
            String value = item.getValue();

            CompositeField _eNewValue = file.field("int", value + "_VALUE", "Enumeration value for " + value);
            CompositeField eInstVar = generator.createCompositeElementsDetails(file, false, value,
                    TypeUtils.createTypeReference(area.getName(), serviceName, enumName, false),
                    true, false, "Enumeration singleton for value " + value);

            file.addClassVariable(true, true, StdStrings.PUBLIC, _eNewValue, false, "" + String.valueOf(item.getNvalue()));
            file.addClassVariable(true, true, StdStrings.PUBLIC, eInstVar, true, "(" + generator.convertToNamespace(fqEnumName + "." + value + "_VALUE)"));
        }

        // create arrays
        List<String> opStr = new LinkedList<>();

        for (int i = 0; i < enumSize; i++) {
            opStr.add(enumeration.getItem().get(i).getValue());
        }
        CompositeField eInstArrVar = generator.createCompositeElementsDetails(file, false, "_ENUMERATIONS",
                TypeUtils.createTypeReference(area.getName(), serviceName, enumName, false),
                true, true, "Set of enumeration instances.");
        file.addClassVariable(true, true, StdStrings.PRIVATE, eInstArrVar, true, true, opStr);

        // Generate constructors
        generateConstructors(file, enumeration);

        // Generate methods
        generateToString(file, enumeration);
        generateFromString(file, enumeration, enumType);
        generateFromValue(file, enumeration);
        generateCreateElement(file);
        generateGetEnum(file, enumSize);
        generator.addTypeIdGetterMethod(file, area, service);

        file.addClassCloseStatement();
        file.flush();

        generator.createListClass(folder, area, service, enumName, false, enumeration.getShortFormPart());
    }

    private void generateConstructors(ClassWriter file, EnumerationType enumeration) throws IOException {
        String enumName = enumeration.getName();
        // Adds Constructor without a start value
        file.addStatement("    /**");
        file.addStatement("     * " + enumeration.getComment());
        file.addStatement("     */");
        file.addStatement("    public " + enumName + "() {");
        file.addStatement("        super(-1);");
        file.addStatement("    }");
        file.addStatement("");

        // Adds Constructor with a start value
        String enumComment = enumeration.getComment();
        if (enumComment == null || enumComment.isEmpty()) {
            enumComment = "Creates an instance of the " + enumName + " Enumeration.";
        }
        MethodWriter method = file.addConstructor(StdStrings.PUBLIC, enumName,
                file.field("int", "value", "The value of the Enumeration."),
                true, null, enumComment, null);
        method.addMethodCloseStatement();
    }

    private void generateToString(ClassWriter file, EnumerationType enumeration) throws IOException {
        MethodWriter method = file.method("toString").returns("_String").asOverride().open();
        method.addLine("switch (getValue()) {");

        for (EnumerationType.Item item : enumeration.getItem()) {
            method.addLine("    case " + item.getValue() + "_VALUE:");
            method.addLine("        return \"" + item.getValue() + "\";");
        }
        method.addLine("    default:");
        method.addLine("        throw new RuntimeException(\"Unknown ordinal!\");");
        method.addLine("}");
        method.addMethodCloseStatement();
    }

    private void generateFromString(ClassWriter file, EnumerationType enumeration, CompositeField enumType) throws IOException {
        CompositeField strType = generator.createCompositeElementsDetails(file, false, "s",
                TypeUtils.createTypeReference(null, null, "_String", false),
                false, true, "s The string to search for.");

        MethodWriter method = file.method("fromString").asStatic().returns(enumType).returnActual()
                .addArgument(strType)
                .comment("Returns the enumeration element represented by the supplied string, or null if not matched.")
                .returnComment("The matched enumeration element, or null if not matched.").open();
        method.addLine("switch (s) {");

        for (EnumerationType.Item item : enumeration.getItem()) {
            method.addLine("    case \"" + item.getValue() + "\":");
            method.addLine("        return " + enumeration.getName() + "." + item.getValue() + ";");
        }
        method.addLine("    default:");
        method.addLine("        throw new RuntimeException(\"Unknown Enumeration for the provided string: \" + s);");
        method.addLine("}");
        method.addMethodCloseStatement();
    }

    private void generateFromValue(ClassWriter file, EnumerationType enumeration) throws IOException {
        CompositeField enumType = generator.createCompositeElementsDetails(file, false, "value",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.ENUMERATION, false),
                true, false, null);
        CompositeField intType = generator.createCompositeElementsDetails(file, false, "value",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.INTEGER, false),
                true, false, "value The value of the Enumeration.");

        MethodWriter method = file.method("fromValue").returns(enumType).addArgument(intType).asOverride().open();
        method.addLine("switch (value) {");

        for (EnumerationType.Item item : enumeration.getItem()) {
            method.addLine("    case " + item.getValue() + "_VALUE:");
            method.addLine("        return " + enumeration.getName() + "." + item.getValue() + ";");
        }
        method.addLine("    default:");
        method.addLine("        throw new RuntimeException(\"Unknown Enumeration for the provided value: \" + value);");
        method.addLine("}");
        method.addMethodCloseStatement();
    }

    private void generateCreateElement(ClassWriter file) throws IOException {
        CompositeField elementType = generator.createCompositeElementsDetails(file, false, "return",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.ELEMENT, false),
                true, true, null);
        MethodWriter method = file.method("createElement").returns(elementType).asOverride().open();
        method.addLine("return _ENUMERATIONS[0];");
        method.addMethodCloseStatement();
    }

    private void generateGetEnum(ClassWriter file, long enumSize) throws IOException {
        MethodWriter method = file.method("getEnumSize").returns("int").asOverride().open();
        method.addLine("return " + enumSize + ";");
        method.addMethodCloseStatement();
    }

    protected String getEnumValueCompare(String lhs, String rhs) {
        return lhs + ".equals(" + rhs + ")";
    }

}
