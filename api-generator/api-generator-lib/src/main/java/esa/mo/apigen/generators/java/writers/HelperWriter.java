/* ----------------------------------------------------------------------------
 * Copyright (C) 2026      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO API Generator
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
package esa.mo.apigen.generators.java.writers;

import esa.mo.apigen.generators.java.JavaClassBuilder;
import esa.mo.apigen.generators.java.JavaFieldBuilder;
import esa.mo.apigen.generators.java.JavaMethodBuilder;
import esa.mo.apigen.generators.java.JavaNaming;
import esa.mo.apigen.generators.java.JavaSource;
import esa.mo.apigen.model.Area;
import esa.mo.apigen.model.ErrorDefinition;
import esa.mo.apigen.model.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes the helper classes: one for an area, holding its identity and the numbers of the
 * errors it declares, and one for each service, holding the service singleton.
 */
public final class HelperWriter {

    private static final String STRUCTURES = JavaNaming.MAL_STRUCTURES;

    private HelperWriter() {
    }

    /**
     * @return the source of the area helper.
     */
    public static String writeArea(Area area) {
        String name = area.getName();
        String upper = name.toUpperCase();
        JavaClassBuilder clazz = JavaClassBuilder.named(name + "Helper")
                .inPackage(JavaNaming.packageOf(area))
                .comment("Helper class for " + name + " area.");
        JavaSource out = clazz.open();

        constant("_" + upper + "_AREA_NUMBER", "int", String.valueOf(area.getNumber()))
                .comment("Area number literal.").write(out);
        constant(upper + "_AREA_NUMBER", STRUCTURES + "UShort",
                "new " + STRUCTURES + "UShort(_" + upper + "_AREA_NUMBER)")
                .comment("Area number instance.").write(out);
        constant(upper + "_AREA_NAME", STRUCTURES + "Identifier",
                "new " + STRUCTURES + "Identifier(\"" + name + "\")")
                .comment("Area name constant.").write(out);
        constant("_" + upper + "_AREA_VERSION", "short", String.valueOf(area.getVersion()))
                .comment("Area version literal.").write(out);
        constant(upper + "_AREA_VERSION", STRUCTURES + "UOctet",
                "new " + STRUCTURES + "UOctet(_" + upper + "_AREA_VERSION)")
                .comment("Area version instance.").write(out);

        // Always empty: the elements of an area are reached through its factory instead.
        constant(upper + "_AREA_ELEMENTS", STRUCTURES + "Element[]", "{}")
                .comment("Area Elements.").write(out);

        constant(upper + "_AREA_SERVICES", JavaNaming.MAL + "ServiceInfo[]",
                servicesOf(area)).comment("Services in this Area.").write(out);

        constant(upper + "_AREA", JavaNaming.MAL + "MALArea",
                "new " + JavaNaming.MAL + "MALArea(" + upper + "_AREA_NUMBER, " + upper
                + "_AREA_NAME, " + upper + "_AREA_VERSION, " + upper + "_AREA_ELEMENTS, "
                + upper + "_AREA_SERVICES, new " + name + "ElementFactory())")
                .comment("Area singleton instance.").write(out);

        for (ErrorDefinition error : errorsOf(area)) {
            String constant = ExceptionWriter.constantOf(error.getName());
            constant("_" + constant + "_ERROR_NUMBER", "long", String.valueOf(error.getNumber()))
                    .comment("Error literal for error " + constant + ".").write(out);
            constant(constant + "_ERROR_NUMBER", STRUCTURES + "UInteger",
                    "new " + STRUCTURES + "UInteger(_" + constant + "_ERROR_NUMBER)")
                    .comment("Error instance for error " + constant + ".").write(out);
        }

        JavaMethodBuilder.constructor(name + "Helper").scope("private")
                .line("// Utility class; not meant to be instantiated.")
                .write(out);
        return clazz.close();
    }

    /**
     * @param fieldName The name of the constant.
     * @param type The type of the constant.
     * @param value What it is initialised to.
     * @return a public static final field, which is all a helper holds.
     */
    private static JavaFieldBuilder constant(String fieldName, String type, String value) {
        return JavaFieldBuilder.named(fieldName).asStatic().asFinal()
                .ofType(type).value(value);
    }

    /**
     * The services are laid out one per line, and the array closes on the line of the last
     * of them, after its trailing comma.
     *
     * @param area The area being written.
     * @return the initialiser of the array of services.
     */
    private static String servicesOf(Area area) {
        if (area.getServices().isEmpty()) {
            return "{}";
        }
        StringBuilder buf = new StringBuilder("{");
        for (Service service : area.getServices()) {
            buf.append("\n        ").append(JavaNaming.packageOf(service)).append('.')
                    .append(service.getName()).append("Helper.")
                    .append(service.getName().toUpperCase()).append("_SERVICE,");
        }
        return buf.append('}').toString();
    }

    /**
     * @return the source of a service helper.
     */
    public static String writeService(Service service) {
        String name = service.getName();
        String pkg = JavaNaming.packageOf(service);
        JavaClassBuilder clazz = JavaClassBuilder.named(name + "Helper").inPackage(pkg)
                .comment("Helper class for " + name + " service.");
        JavaSource out = clazz.open();
        constant(name.toUpperCase() + "_SERVICE", pkg + "." + name + "ServiceInfo",
                "new " + pkg + "." + name + "ServiceInfo()")
                .comment("Service singleton instance.").write(out);
        JavaMethodBuilder.constructor(name + "Helper").scope("private")
                .line("// Utility class; not meant to be instantiated.")
                .write(out);
        return clazz.close();
    }

    /**
     * @return every error the area declares, its own and its services'.
     */
    public static List<ErrorDefinition> errorsOf(Area area) {
        List<ErrorDefinition> errors = new ArrayList<ErrorDefinition>(area.getErrors());
        for (Service service : area.getServices()) {
            errors.addAll(service.getErrors());
        }
        return errors;
    }
}
