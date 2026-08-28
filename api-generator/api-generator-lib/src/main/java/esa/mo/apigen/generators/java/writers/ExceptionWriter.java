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

/**
 * Writes the Java exception class for an error an area declares.
 * <p>
 * Every error becomes an exception in the area's own package, whether the area or one of
 * its services declared it. The name it was declared under is kept verbatim in the class,
 * and the number is read from the area's helper, so an error renamed between MAL
 * generations still says which name it was raised under.
 */
public final class ExceptionWriter {

    private ExceptionWriter() {
    }

    /**
     * @return the source of the exception class.
     */
    public static String write(Area area, ErrorDefinition error) {
        String className = classNameOf(error.getName());
        String pkg = JavaNaming.packageOf(area);

        JavaClassBuilder clazz = JavaClassBuilder.named(className).inPackage(pkg).asFinal()
                .extending(JavaNaming.MAL + "MOErrorException")
                .comment("The " + className + " exception."
                        + (error.getComment() == null || error.getComment().isEmpty()
                                ? "" : " " + error.getComment()));
        JavaSource out = clazz.open();
        JavaFieldBuilder.named("MO_ERROR_NAME").scope("private").asStatic().asFinal()
                .ofType("String").value("\"" + error.getName() + "\"")
                .write(out);

        String number = area.getName() + "Helper." + constantOf(error.getName()) + "_ERROR_NUMBER";

        JavaMethodBuilder.constructor(className)
                .comment("Constructs a new " + className + " exception.")
                .line("super(MO_ERROR_NAME, " + number + ", \"\");")
                .write(out);

        JavaMethodBuilder.constructor(className)
                .comment("Constructs a new " + className + " exception.")
                .argument("Object", "extraInformation", "The extraInformation of the exception.")
                .line("super(MO_ERROR_NAME, " + number + ", extraInformation);")
                .write(out);

        return clazz.close();
    }

    /**
     * Returns the class name for an error: the words of its name run together, however it
     * was spelled. DUPLICATE and "Delivery Failed" give DuplicateException and
     * DeliveryFailedException.
     *
     * @param errorName The declared name.
     * @return the class name.
     */
    public static String classNameOf(String errorName) {
        StringBuilder buf = new StringBuilder();
        for (String word : errorName.split("[ _]")) {
            if (!word.isEmpty()) {
                buf.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase());
            }
        }
        return buf + "Exception";
    }

    /**
     * Returns the name of the helper constant holding the error's number.
     *
     * @param errorName The declared name.
     * @return the constant name, without the trailing _ERROR_NUMBER.
     */
    public static String constantOf(String errorName) {
        return errorName.replace(' ', '_').toUpperCase();
    }
}
