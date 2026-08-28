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
import esa.mo.apigen.generators.java.JavaComment;
import esa.mo.apigen.generators.java.JavaFieldBuilder;
import esa.mo.apigen.generators.java.JavaMethodBuilder;
import esa.mo.apigen.generators.java.JavaNaming;
import esa.mo.apigen.generators.java.JavaSource;
import esa.mo.apigen.generators.java.ShortForm;
import esa.mo.apigen.model.Area;
import esa.mo.apigen.model.Service;
import esa.mo.apigen.model.types.EnumerationItem;
import esa.mo.apigen.model.types.EnumerationType;
import java.util.List;

/**
 * Writes the Java class for an enumeration.
 */
public final class EnumerationWriter {


    private EnumerationWriter() {
    }

    /**
     * Renders the class.
     *
     * @param area The area defining the enumeration.
     * @param service The service defining it, or null for an area-level type.
     * @param type The enumeration.
     * @return the source of the class.
     */
    public static String write(Area area, Service service, EnumerationType type) {
        String name = type.getName();
        String pkg = service == null
                ? JavaNaming.packageOf(area, JavaNaming.STRUCTURES)
                : JavaNaming.packageOf(service, JavaNaming.STRUCTURES);
        String fq = pkg + "." + name;
        long shortForm = ShortForm.of(area, service, type.getShortFormPart());
        List<EnumerationItem> items = type.getItems();

        JavaClassBuilder clazz = JavaClassBuilder.named(name).inPackage(pkg).asFinal()
                .extending(JavaNaming.MAL + "structures.Enumeration")
                .comment("Enumeration class for " + name + ".");
        JavaSource out = clazz.open();
        ShortForm.writeIdentity(out, shortForm);

        for (EnumerationItem item : items) {
            String value = item.getValue();
            JavaFieldBuilder.named(value + "_VALUE").asStatic().asFinal()
                    .ofType("int").value(String.valueOf(item.getNumericValue()))
                    .comment("Enumeration value for " + value + ".")
                    .write(out);
            JavaFieldBuilder.named(value).asStatic().asFinal()
                    .ofType(fq).value("new " + fq + "(" + fq + "." + value + "_VALUE)")
                    .comment("Enumeration singleton for value " + value + ".")
                    .write(out);
        }

        JavaFieldBuilder.named("_ENUMERATIONS").scope("private").asStatic().asFinal()
                .ofType(fq + "[]").value(enumerationsOf(items))
                .comment("Set of enumeration instances.")
                .write(out);

        // The no-argument constructor takes the comment verbatim - unwrapped, with no
        // trailing period and no blank line under it - where every other method wraps it.
        // The difference is in the existing generator and is reproduced here, which is why
        // this one method is written out rather than built like the rest.
        out.blank();
        out.line("    /**");
        out.line("     * " + type.getComment());
        out.line("     */");
        out.line("    public " + name + "() {");
        out.line("        super(-1);");
        out.line("    }");

        String comment = type.getComment();
        if (comment == null || comment.isEmpty()) {
            comment = "Creates an instance of the " + name + " Enumeration.";
        }
        JavaMethodBuilder.constructor(name).comment(comment)
                .argument("int", "value", "The value of the Enumeration.")
                .line("super(value);")
                .write(out);

        JavaMethodBuilder toString = JavaMethodBuilder.named("toString").asOverride()
                .returns("String", null);
        toString.line("switch (getValue()) {");
        for (EnumerationItem item : items) {
            toString.line("    case " + item.getValue() + "_VALUE:");
            toString.line("        return \"" + item.getValue() + "\";");
        }
        toString.line("    default:");
        toString.line("        throw new RuntimeException(\"Unknown ordinal!\");");
        toString.line("}");
        toString.write(out);

        JavaMethodBuilder fromString = JavaMethodBuilder.named("fromString").asStatic()
                .comment("Returns the enumeration element represented by the supplied string,"
                        + " or null if not matched.")
                .returns(fq, "The matched enumeration element, or null if not matched.")
                .argument("String", "s", "s The string to search for.");
        fromString.line("switch (s) {");
        for (EnumerationItem item : items) {
            fromString.line("    case \"" + item.getValue() + "\":");
            fromString.line("        return " + name + "." + item.getValue() + ";");
        }
        fromString.line("    default:");
        fromString.line("        throw new RuntimeException(\"Unknown Enumeration for the "
                + "provided string: \" + s);");
        fromString.line("}");
        fromString.write(out);

        JavaMethodBuilder fromValue = JavaMethodBuilder.named("fromValue").asOverride()
                .returns(JavaNaming.MAL_STRUCTURES + "Enumeration", null)
                .argument("Integer", "value", null);
        fromValue.line("switch (value) {");
        for (EnumerationItem item : items) {
            fromValue.line("    case " + item.getValue() + "_VALUE:");
            fromValue.line("        return " + name + "." + item.getValue() + ";");
        }
        fromValue.line("    default:");
        fromValue.line("        throw new RuntimeException(\"Unknown Enumeration for the "
                + "provided value: \" + value);");
        fromValue.line("}");
        fromValue.write(out);

        JavaMethodBuilder.named("createElement").asOverride()
                .returns(JavaNaming.MAL_STRUCTURES + "Element", null)
                .line("return _ENUMERATIONS[0];")
                .write(out);

        JavaMethodBuilder.named("getEnumSize").asOverride().returns("int", null)
                .line("return " + items.size() + ";")
                .write(out);

        JavaMethodBuilder.named("getTypeId").asOverride()
                .returns(JavaNaming.MAL + "TypeId", null)
                .line("return TYPE_ID;")
                .write(out);

        return clazz.close();
    }
    /**
     * The instances are listed in one run that wraps at the same threshold as a comment,
     * each line indented by eight spaces, and the array closes on the line of the last of
     * them.
     *
     * @param items The items of the enumeration.
     * @return the initialiser of the array of instances.
     */
    private static String enumerationsOf(List<EnumerationItem> items) {
        StringBuilder values = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            values.append(i == 0 ? "" : ", ").append(items.get(i).getValue());
        }

        StringBuilder buf = new StringBuilder("{");
        List<String> lines = JavaComment.wrap(values.toString());
        for (int i = 0; i < lines.size(); i++) {
            buf.append("\n        ").append(lines.get(i));
            if (i == lines.size() - 1) {
                buf.append('}');
            }
        }
        return buf.toString();
    }

}
