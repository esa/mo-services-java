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
 * One Java method, from its javadoc to its closing brace.
 * <p>
 * Written out in the order the pieces are declared here rather than in the order they end
 * up on the page: an argument is given with the description that documents it, so the
 * {@code @param} block and the signature cannot drift apart, and the body is held with the
 * signature so that a method is one expression from end to end.
 * <p>
 * Everything a method can be is set through the same object, so a writer never has to
 * decide between "the one with the comment" and "the one that overrides". A method the
 * language has no word for - a static override, say - is simply never asked for.
 */
public final class JavaMethodBuilder {

    /**
     * The step a method takes past the class, and the step its body takes past it.
     */
    private static final String INDENT = "    ";

    private static final String BODY_INDENT = INDENT + INDENT;

    /**
     * Arguments past the first are carried onto their own line, at the step the reference
     * output uses regardless of how long the signature is.
     */
    private static final String ARGUMENT_INDENT = "            ";

    private final String name;

    private final List<Argument> arguments = new ArrayList<Argument>();

    private final List<Thrown> thrown = new ArrayList<Thrown>();

    private final List<String> body = new ArrayList<String>();

    private String scope = "public";

    private String returnType = null;

    private String returnComment = null;

    private String comment = null;

    private boolean constructor = false;

    private boolean isStatic = false;

    private boolean isFinal = false;

    private boolean isOverride = false;

    private boolean isDeprecated = false;

    private boolean declarationOnly = false;

    private JavaMethodBuilder(String name) {
        this.name = name;
    }

    /**
     * @param name The name of the method.
     * @return a method that returns nothing, is public, and has no arguments.
     */
    public static JavaMethodBuilder named(String name) {
        return new JavaMethodBuilder(name);
    }

    /**
     * @param className The name of the class being constructed.
     * @return a constructor, which is written without a return type.
     */
    public static JavaMethodBuilder constructor(String className) {
        JavaMethodBuilder method = new JavaMethodBuilder(className);
        method.constructor = true;
        return method;
    }

    /**
     * Sets the scope. Public unless said otherwise.
     *
     * @param scope The scope keyword.
     * @return this method.
     */
    public JavaMethodBuilder scope(String scope) {
        this.scope = scope;
        return this;
    }

    /**
     * Sets what the method returns. Void unless said otherwise.
     *
     * @param type The returned type.
     * @param description What is returned, or null to leave it undocumented.
     * @return this method.
     */
    public JavaMethodBuilder returns(String type, String description) {
        this.returnType = type;
        this.returnComment = description;
        return this;
    }

    /**
     * Adds an argument, in declaration order, with the description that documents it.
     *
     * @param type The type of the argument.
     * @param argumentName The name of the argument.
     * @param description What the argument is, or null for a plain description of it.
     * @return this method.
     */
    public JavaMethodBuilder argument(String type, String argumentName,
            String description) {
        arguments.add(new Argument(type, argumentName, description));
        return this;
    }

    /**
     * Adds a thrown type, in order, with the description that documents it.
     *
     * @param type The thrown type.
     * @param description When it is thrown, or null to leave it undocumented.
     * @return this method.
     */
    public JavaMethodBuilder throwing(String type, String description) {
        thrown.add(new Thrown(type, description));
        return this;
    }

    /**
     * Sets the comment of the method.
     *
     * @param text The comment, may be null.
     * @return this method.
     */
    public JavaMethodBuilder comment(String text) {
        this.comment = text;
        return this;
    }

    /**
     * @return this method, marked static.
     */
    public JavaMethodBuilder asStatic() {
        this.isStatic = true;
        return this;
    }

    /**
     * @return this method, marked final.
     */
    public JavaMethodBuilder asFinal() {
        this.isFinal = true;
        return this;
    }

    /**
     * Marks the method as overriding another, which carries the annotation.
     *
     * @return this method.
     */
    public JavaMethodBuilder asOverride() {
        this.isOverride = true;
        return this;
    }

    /**
     * @return this method, marked deprecated.
     */
    public JavaMethodBuilder asDeprecated() {
        this.isDeprecated = true;
        return this;
    }

    /**
     * Marks the method as a declaration without a body, for an interface. The scope goes
     * unwritten: an interface method has no keyword in front of it.
     *
     * @return this method.
     */
    public JavaMethodBuilder asDeclaration() {
        this.declarationOnly = true;
        return this;
    }

    /**
     * Adds a line to the body, indented past the method. A line that steps further in says
     * so itself, as the reference output does.
     *
     * @param text The line, without the indentation of the body.
     * @return this method.
     */
    public JavaMethodBuilder line(String text) {
        body.add(text);
        return this;
    }

    /**
     * Adds the lines of the body, in order.
     *
     * @param lines The lines, without the indentation of the body.
     * @return this method.
     */
    public JavaMethodBuilder lines(List<String> lines) {
        body.addAll(lines);
        return this;
    }

    /**
     * Writes the method, preceded by the blank line that separates it from what came
     * before it. A declaration has no such line.
     *
     * @param out The source to write to.
     */
    public void write(JavaSource out) {
        // A declaration follows the one before it without a gap, the way an interface reads
        // in the reference output.
        if (!declarationOnly) {
            out.blank();
        }
        writeComment(out);
        if (isDeprecated) {
            out.line(INDENT + "@Deprecated");
        }
        if (isOverride) {
            out.line(INDENT + "@Override");
        }
        out.line(INDENT + signature());
        if (declarationOnly) {
            return;
        }
        for (String line : body) {
            out.line(line.isEmpty() ? "" : BODY_INDENT + line);
        }
        out.line(INDENT + "}");
    }

    /**
     * Writes the javadoc, which is left out entirely when there would be nothing in it.
     * <p>
     * An override is never documented: it says the same thing as the method it overrides,
     * and javadoc carries that documentation down on its own.
     */
    private void writeComment(JavaSource out) {
        if (isOverride) {
            return;
        }

        List<String> lines = JavaComment.normaliseInClass(comment);
        List<String> tags = new ArrayList<String>();

        for (Argument argument : arguments) {
            tags.add("@param " + argument.name + " " + argument.describe());
        }
        if (returnComment != null && !returnComment.isEmpty()) {
            tags.add("@return " + returnComment);
        }
        for (Thrown item : thrown) {
            if (item.description != null && !item.description.isEmpty()) {
                tags.add("@throws " + item.type + " " + item.description);
            }
        }
        if (lines.isEmpty() && tags.isEmpty()) {
            return;
        }

        out.line(INDENT + "/**");
        for (String line : lines) {
            out.line(INDENT + " * " + line);
        }
        // The blank line stands between the description and the tags whether or not there
        // is a description above it, which is what the reference output does.
        out.line(INDENT + " * ");
        for (String tag : tags) {
            out.line(INDENT + " * " + JavaComment.escape(tag));
        }
        out.line(INDENT + " */");
    }

    /**
     * @return the signature, from the scope to the brace that opens the body.
     */
    private String signature() {
        StringBuilder buf = new StringBuilder();

        if (!declarationOnly) {
            // An override is public whatever else it is: so is the method it overrides
            buf.append(isOverride ? "public" : scope).append(' ');
        }
        if (isStatic) {
            buf.append("static ");
        }
        if (isFinal) {
            buf.append("final ");
        }
        if (!constructor) {
            buf.append(returnType == null ? "void" : returnType).append(' ');
        }

        buf.append(name).append('(');
        for (int i = 0; i < arguments.size(); i++) {
            if (i != 0) {
                buf.append(",\n").append(ARGUMENT_INDENT);
            }
            Argument argument = arguments.get(i);
            buf.append(argument.type).append(' ').append(argument.name);
        }
        buf.append(')');

        for (int i = 0; i < thrown.size(); i++) {
            buf.append(i == 0 ? " throws " : ", ").append(thrown.get(i).type);
        }

        buf.append(declarationOnly ? ";" : " {");
        return buf.toString();
    }

    /**
     * One argument, held with the description that documents it.
     */
    private static final class Argument {

        private final String type;
        private final String name;
        private final String description;

        private Argument(String type, String name, String description) {
            this.type = type;
            this.name = name;
            this.description = description;
        }

        /**
         * @return the description, or a plain one naming the argument.
         */
        private String describe() {
            return (description == null || description.isEmpty())
                    ? "The " + name + " field." : description;
        }
    }

    /**
     * One thrown type, held with the description that documents it.
     */
    private static final class Thrown {

        private final String type;
        private final String description;

        private Thrown(String type, String description) {
            this.type = type;
            this.description = description;
        }
    }
}
