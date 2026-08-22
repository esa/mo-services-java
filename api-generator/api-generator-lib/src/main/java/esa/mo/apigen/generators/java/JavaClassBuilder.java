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
package esa.mo.apigen.generators.java;

import java.util.ArrayList;
import java.util.List;

/**
 * One Java class, from its package statement to its closing brace.
 * <p>
 * The outermost of the three builders. {@link #open} writes the package statement, the
 * javadoc and the declaration and hands back the source to write the members into;
 * {@link #close} writes the brace that ends the class and returns the finished text. The
 * members in between are written with {@link JavaFieldBuilder} and
 * {@link JavaMethodBuilder}, which is why the class does not hold them itself: a writer
 * decides what goes in and in what order, and this decides how the class around them reads.
 * <p>
 * Every class this generator writes is public, so there is nothing to say about scope.
 */
public final class JavaClassBuilder {

    private final String name;

    private final List<String> interfaces = new ArrayList<String>();

    private final JavaSource out = new JavaSource();

    private String packageName = null;

    private String comment = null;

    private String superClass = null;

    private boolean isFinal = false;

    private boolean isAbstract = false;

    private boolean isInterface = false;

    private JavaClassBuilder(String name) {
        this.name = name;
    }

    /**
     * @param name The name of the class.
     * @return a public class, extending nothing and implementing nothing.
     */
    public static JavaClassBuilder named(String name) {
        return new JavaClassBuilder(name);
    }

    /**
     * Sets the package the class belongs to.
     *
     * @param name The package name.
     * @return this class.
     */
    public JavaClassBuilder inPackage(String name) {
        this.packageName = name;
        return this;
    }

    /**
     * Sets the comment of the class.
     *
     * @param text The comment, may be null.
     * @return this class.
     */
    public JavaClassBuilder comment(String text) {
        this.comment = text;
        return this;
    }

    /**
     * Sets the class this one extends.
     *
     * @param type The super class.
     * @return this class.
     */
    public JavaClassBuilder extending(String type) {
        this.superClass = type;
        return this;
    }

    /**
     * Adds an interface the class implements, in order.
     *
     * @param type The interface.
     * @return this class.
     */
    public JavaClassBuilder implementing(String type) {
        interfaces.add(type);
        return this;
    }

    /**
     * @return this class, marked final.
     */
    public JavaClassBuilder asFinal() {
        this.isFinal = true;
        return this;
    }

    /**
     * @return this class, marked abstract.
     */
    public JavaClassBuilder asAbstract() {
        this.isAbstract = true;
        return this;
    }

    /**
     * Marks this as an interface rather than a class. Its members are declarations, so they
     * are written packed together rather than a blank line apart, and the blank line that
     * separates them from the declaration is written here instead.
     *
     * @return this class.
     */
    public JavaClassBuilder asInterface() {
        this.isInterface = true;
        return this;
    }

    /**
     * Writes everything down to the brace that opens the class.
     *
     * @return the source to write the members into.
     */
    public JavaSource open() {
        out.line("package " + packageName + ";");
        out.blank();

        List<String> lines = JavaComment.normaliseInClass(comment);
        if (!lines.isEmpty()) {
            out.line("/**");
            for (String line : lines) {
                out.line(" * " + line);
            }
            out.line(" */");
        }

        StringBuilder buf = new StringBuilder("public ");
        if (isFinal) {
            buf.append("final ");
        }
        if (isAbstract) {
            buf.append("abstract ");
        }
        buf.append(isInterface ? "interface " : "class ").append(name);
        if (superClass != null) {
            buf.append(" extends ").append(superClass);
        }
        for (int i = 0; i < interfaces.size(); i++) {
            // An interface extends what a class implements.
            buf.append(i == 0 ? (isInterface ? " extends " : " implements ") : ", ")
                    .append(interfaces.get(i));
        }
        out.line(buf.append(" {").toString());
        if (isInterface) {
            out.blank();
        }
        return out;
    }

    /**
     * Writes the brace that ends the class, under the blank line that separates it from the
     * last member. An interface has no such line: its declarations are packed.
     *
     * @return the finished source.
     */
    public String close() {
        if (!isInterface) {
            out.blank();
        }
        out.line("}");
        return out.toString();
    }
}
