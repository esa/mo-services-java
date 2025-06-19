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
import esa.mo.xsd.ErrorDefinitionType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 */
public class JavaExceptions {

    public final static String EXCEPTION = "Exception";
    private final GeneratorLangs generator;

    public JavaExceptions(GeneratorLangs generator) {
        this.generator = generator;
    }

    /*
    public void createServiceExceptions(File serviceFolder, AreaType area,
            ServiceType service, ServiceSummary summary) throws IOException {
        generator.getLog().warn("The service Exceptions must be moved to Area "
                + "level! This is just supported for backward compatibility. "
                + "Check the Errors defined in service: " + service.getName());
        generator.getLog().info(" > Creating service Exceptions for service: " + service.getName());

        if (summary.getService().getErrors() != null && summary.getService().getErrors().getError() != null) {
            for (ErrorDefinitionType error : summary.getService().getErrors().getError()) {
                this.generateException(serviceFolder, area, service, error);
            }
        }
    }
     */
    public void createAreaExceptions(File areaFolder, AreaType area) throws IOException {
        if (area.getErrors() != null && area.getErrors().getError() != null) {
            for (ErrorDefinitionType error : area.getErrors().getError()) {
                this.generateException(areaFolder, area.getName(), null, error);
            }
        }
    }

    public static String convertErrorToClassname(String name) {
        String inCamelCase = convertToCamelCase(name);
        return inCamelCase + EXCEPTION;
    }

    public void generateException(File folder, String area,
            String service, ErrorDefinitionType error) throws IOException {
        String errorName = error.getName();
        String className = convertErrorToClassname(errorName);
        ClassWriter file = generator.createClassFile(folder, className);
        file.addPackageStatement(area, service, null);

        // Appends the class name
        String extendsClass = "org.ccsds.moims.mo.mal.MOErrorException";
        file.addClassOpenStatement(className, true, false, extendsClass,
                null, "The " + className + " exception. " + error.getComment());

        String errorDescription = "\"\"";
        if (error.getExtraInformation() != null) {
            errorDescription = "\"" + error.getExtraInformation().getComment() + "\"";
        }

        // Add the variables here:
        file.addStatement("    private static final String MO_ERROR_NAME = \"" + errorName + "\";");
        file.addStatement("");

        // Construct path to Error in the Helper
        String errorNameCaps = convertToUppercaseWithUnderscores(errorName);
        String errorPath = area + "Helper." + errorNameCaps + "_ERROR_NUMBER";

        // Constructor without parameters
        MethodWriter method_1 = file.addConstructor(StdStrings.PUBLIC, className,
                null, null, null, "Constructs a new " + className + " exception.", null);
        method_1.addLine("super(MO_ERROR_NAME, " + errorPath + ", " + errorDescription + ");");
        method_1.addMethodCloseStatement();

        // Constructor with an Object for the extraInformation
        ArrayList<CompositeField> args = new ArrayList<>();
        CompositeField field = generator.createCompositeElementsDetails(file, false, "extraInformation",
                TypeUtils.createTypeReference(null, null, "Object", false),
                false, true, "The extraInformation of the exception.");
        args.add(field);

        MethodWriter method_2 = file.addConstructor(StdStrings.PUBLIC, className,
                args, null, null, "Constructs a new " + className + " exception.", null);
        method_2.addLine("super(MO_ERROR_NAME, " + errorPath + ", " + "extraInformation);");
        method_2.addMethodCloseStatement();

        file.addClassCloseStatement();
        file.flush();
    }

    public static String convertToCamelCase(String text) {
        // Is the Error in the old style? With all Upper Case and underscores?
        if (text.equals(text.toUpperCase())) {
            StringBuilder all = new StringBuilder();
            // Split by underscore:
            for (String part : text.split("_")) {
                // Convert to Camel case:
                StringBuilder camelCase = new StringBuilder(part.toLowerCase());
                camelCase.setCharAt(0, part.charAt(0));
                all.append(camelCase.toString());
            }

            return all.toString();
        }

        return text.replace(" ", "").replace("_", "");
    }

    public static String convertToUppercaseWithUnderscores(String text) {
        String out = text.toUpperCase();
        return out.replace(" ", "_");
    }
}
