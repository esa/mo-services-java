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

import java.util.List;

/**
 * One Java field, from its javadoc to its semicolon.
 * <p>
 * The counterpart of {@link JavaMethodBuilder}, and built the same way: the comment is held
 * with the declaration it documents, and {@code write} is the only thing that produces
 * text. A value spanning more than one line is written as it is given, so the caller that
 * lays out an array lays out its own continuation, as it does for the body of a method.
 */
public final class JavaFieldBuilder {

    /**
     * The step a field takes past the class.
     */
    private static final String INDENT = "    ";

    private final String name;

    private String scope = "public";

    private String type = null;

    private String value = null;

    private String comment = null;

    private boolean isStatic = false;

    private boolean isFinal = false;

    private boolean isDeprecated = false;

    private boolean joined = false;

    private JavaFieldBuilder(String name) {
        this.name = name;
    }

    /**
     * @param name The name of the field.
     * @return a public field, with no type yet and no value.
     */
    public static JavaFieldBuilder named(String name) {
        return new JavaFieldBuilder(name);
    }

    /**
     * Sets the scope. Public unless said otherwise.
     *
     * @param scope The scope keyword.
     * @return this field.
     */
    public JavaFieldBuilder scope(String scope) {
        this.scope = scope;
        return this;
    }

    /**
     * Sets the declared type.
     *
     * @param typeName The type of the field.
     * @return this field.
     */
    public JavaFieldBuilder ofType(String typeName) {
        this.type = typeName;
        return this;
    }

    /**
     * Sets what the field is initialised to. A field with no value is declared and left
     * unset.
     *
     * @param expression The initialiser, which may span several lines.
     * @return this field.
     */
    public JavaFieldBuilder value(String expression) {
        this.value = expression;
        return this;
    }

    /**
     * Sets the comment of the field.
     *
     * @param text The comment, may be null.
     * @return this field.
     */
    public JavaFieldBuilder comment(String text) {
        this.comment = text;
        return this;
    }

    /**
     * @return this field, marked static.
     */
    public JavaFieldBuilder asStatic() {
        this.isStatic = true;
        return this;
    }

    /**
     * @return this field, marked final.
     */
    public JavaFieldBuilder asFinal() {
        this.isFinal = true;
        return this;
    }

    /**
     * @return this field, marked deprecated.
     */
    public JavaFieldBuilder asDeprecated() {
        this.isDeprecated = true;
        return this;
    }

    /**
     * Writes the field directly under the one before it, with no blank line between them.
     * The reference output keeps the fields that carry a type's identity together as one
     * block, and separates everything else.
     *
     * @return this field.
     */
    public JavaFieldBuilder joinedToPrevious() {
        this.joined = true;
        return this;
    }

    /**
     * Writes the field, preceded by the blank line that separates it from what came before
     * it unless it was asked to stay with it.
     *
     * @param out The source to write to.
     */
    public void write(JavaSource out) {
        if (!joined) {
            out.blank();
        }

        List<String> lines = JavaComment.normaliseInClass(comment);
        if (!lines.isEmpty()) {
            out.line(INDENT + "/**");
            for (String line : lines) {
                out.line(INDENT + " * " + line);
            }
            out.line(INDENT + " */");
        }

        if (isDeprecated) {
            out.line(INDENT + "@Deprecated");
        }

        StringBuilder buf = new StringBuilder(INDENT);
        buf.append(scope).append(' ');
        if (isStatic) {
            buf.append("static ");
        }
        if (isFinal) {
            buf.append("final ");
        }
        buf.append(type).append(' ').append(name);
        if (value != null) {
            buf.append(" = ").append(value);
        }
        buf.append(';');
        out.line(buf.toString());
    }
}
