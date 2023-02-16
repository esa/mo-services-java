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
package esa.mo.tools.stubgen;

import esa.mo.tools.stubgen.specification.ServiceSummary;
import esa.mo.tools.stubgen.specification.StdStrings;
import esa.mo.tools.stubgen.writers.MethodWriter;
import esa.mo.xsd.AreaType;
import esa.mo.xsd.ErrorDefinitionType;
import esa.mo.xsd.ServiceType;
import java.io.File;
import java.io.IOException;

/**
 *
 */
public class JavaExceptions {

    public final static String EXCEPTION = "Exception";
    private final GeneratorLangs generator;

    public JavaExceptions(GeneratorLangs generator) {
        this.generator = generator;
    }

    public void createServiceExceptions(File serviceFolder, AreaType area,
            ServiceType service, ServiceSummary summary) throws IOException {
        generator.getLog().info("Creating service Exceptions for service: " + service.getName());

        for (ErrorDefinitionType error : summary.getService().getErrors().getError()) {
            this.generateExceptions(serviceFolder, area, service, error);
        }
    }

    public void generateExceptions(File serviceFolder, AreaType area,
            ServiceType service, ErrorDefinitionType error) throws IOException {
        generator.getLog().info("Creating service Exception: " + error.getName());

        // Needs to be converted to Camel case in the future!
        String className = error.getName() + EXCEPTION;

        ClassWriterProposed file = generator.createClassFile(serviceFolder, className);
        file.addPackageStatement(area, service, null);

        // Appends the class name
        file.addClassOpenStatement(className, true, false, "Exception",
                null, "The " + className + " exception. " + error.getComment());

        // Constructor without parameters
        MethodWriter method_1 = file.addConstructor(StdStrings.PUBLIC, className,
                null, null, null, "Constructs a new " + className + " exception.", null);
        method_1.addLine("super()");
        method_1.addMethodCloseStatement();

        // Constructor with a String
        /*
        ArrayList<CompositeField> args = new ArrayList<>();
        args.add(new CompositeField(""));
        MethodWriter method_2 = file.addConstructor(StdStrings.PUBLIC, className,
                args, null, null, "Constructs a new " + className + " exception.", null);
        method_2.addLine("super(message)");
        method_2.addMethodCloseStatement();
         */
        file.addClassCloseStatement();
        file.flush();
    }
}
