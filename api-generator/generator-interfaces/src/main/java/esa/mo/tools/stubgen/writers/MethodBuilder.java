/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
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
package esa.mo.tools.stubgen.writers;

import esa.mo.tools.stubgen.specification.CompositeField;
import esa.mo.tools.stubgen.specification.StdStrings;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Fluent builder for opening a method declaration on a {@link ClassWriter}.
 * <p>
 * This is a language-neutral description of a method: it accumulates the method
 * name, scope, return type, arguments, modifiers and comments, then on
 * {@link #open()} delegates to the writer's canonical
 * {@code addMethodOpenStatement} (or {@code addMethodOpenStatementOverride})
 * method. Because it depends only on the {@link ClassWriter} interface and holds
 * no language-specific rendering logic, any current or future writer
 * implementation gets it for free; each implementation decides how to render the
 * supplied description. Modifiers a language does not support (for example
 * {@code const} or {@code virtual} in Java) are simply ignored by that writer.
 * <p>
 * Defaults: {@code public} scope, {@code void} return type, no arguments, no
 * throws clause, and all modifier flags unset.
 */
public final class MethodBuilder {

    private final ClassWriter writer;
    private final String methodName;
    private String scope = StdStrings.PUBLIC;
    private CompositeField returnType = null;
    private List<CompositeField> args = null;
    private String throwsSpec = null;
    private String comment = null;
    private String returnComment = null;
    private List<String> throwsComment = null;
    private boolean isFinal = false;
    private boolean isVirtual = false;
    private boolean isConst = false;
    private boolean isStatic = false;
    private boolean isReturnConst = false;
    private boolean isReturnActual = false;
    private boolean isDeprecated = false;
    private boolean isOverride = false;

    /**
     * Constructor. Prefer {@link ClassWriter#method(String)} over calling this
     * directly.
     *
     * @param writer The class writer the method will be opened on.
     * @param methodName The method name.
     */
    public MethodBuilder(ClassWriter writer, String methodName) {
        this.writer = writer;
        this.methodName = methodName;
    }

    /**
     * Sets the method scope. Defaults to {@code public}.
     *
     * @param scope The scope.
     * @return This builder.
     */
    public MethodBuilder scope(String scope) {
        this.scope = scope;
        return this;
    }

    /**
     * Sets the return type. When unset the method returns void.
     *
     * @param returnType The return type.
     * @return This builder.
     */
    public MethodBuilder returns(CompositeField returnType) {
        this.returnType = returnType;
        return this;
    }

    /**
     * Sets the return type from a simple type name (for example {@code "int"}),
     * resolved via the writer.
     *
     * @param typeName The return type name.
     * @return This builder.
     */
    public MethodBuilder returns(String typeName) {
        return returns(writer.type(typeName));
    }

    /**
     * Sets the return type from an area/service qualified type name, resolved
     * via the writer.
     *
     * @param area The area the type belongs to, or null.
     * @param service The service the type belongs to, or null.
     * @param name The return type name.
     * @return This builder.
     */
    public MethodBuilder returns(String area, String service, String name) {
        return returns(writer.type(area, service, name));
    }

    /**
     * Adds a single argument, in declaration order. The first call creates the
     * list; if no argument is ever added the arguments remain unset.
     *
     * @param arg The argument to add.
     * @return This builder.
     */
    public MethodBuilder addArgument(CompositeField arg) {
        if (this.args == null) {
            this.args = new ArrayList<>();
        }
        this.args.add(arg);
        return this;
    }

    /**
     * Adds all the given arguments, in order, after any already added. Intended
     * for the cases where the arguments come pre-built from a helper. The first
     * call creates the list; if none are ever added the arguments remain unset.
     *
     * @param args The arguments to add.
     * @return This builder.
     */
    public MethodBuilder addArguments(List<CompositeField> args) {
        if (this.args == null) {
            this.args = new ArrayList<>();
        }
        this.args.addAll(args);
        return this;
    }

    /**
     * Adds a thrown type to the throws clause without an {@code @throws}
     * comment, joined with any previously added types. Use this when the method
     * carries no documentation for the type (for example overrides). Use
     * {@link #addThrows(String, String)} to add a type together with its
     * description.
     *
     * @param type The thrown type, as it should appear in the throws clause.
     * @return This builder.
     */
    public MethodBuilder addThrows(String type) {
        if (this.throwsSpec == null) {
            this.throwsSpec = type;
        } else {
            this.throwsSpec += ", " + type;
        }
        return this;
    }

    /**
     * Sets the method comment.
     *
     * @param comment The comment.
     * @return This builder.
     */
    public MethodBuilder comment(String comment) {
        this.comment = comment;
        return this;
    }

    /**
     * Sets the return value comment.
     *
     * @param returnComment The return comment.
     * @return This builder.
     */
    public MethodBuilder returnComment(String returnComment) {
        this.returnComment = returnComment;
        return this;
    }

    /**
     * Adds a thrown type together with its description in a single call. The
     * type is appended to the throws clause (joined with the previously added
     * types) and a matching {@code "<type> <description>"} entry is appended to
     * the throws comments, keeping the clause and the documentation in step.
     * The first call creates the throws-comment list; if no type is ever added
     * the throws clause and comments remain unset.
     *
     * @param type The thrown type, as it should appear in the throws clause.
     * @param description The description of when the type is thrown, without
     * the leading type name.
     * @return This builder.
     */
    public MethodBuilder addThrows(String type, String description) {
        addThrows(type);
        if (this.throwsComment == null) {
            this.throwsComment = new ArrayList<>();
        }
        this.throwsComment.add(type + " " + description);
        return this;
    }

    /**
     * Marks the method as final.
     *
     * @return This builder.
     */
    public MethodBuilder asFinal() {
        this.isFinal = true;
        return this;
    }

    /**
     * Marks the method as virtual (ignored by languages without the concept).
     *
     * @return This builder.
     */
    public MethodBuilder asVirtual() {
        this.isVirtual = true;
        return this;
    }

    /**
     * Marks the method as const (ignored by languages without the concept).
     *
     * @return This builder.
     */
    public MethodBuilder asConst() {
        this.isConst = true;
        return this;
    }

    /**
     * Marks the method as static.
     *
     * @return This builder.
     */
    public MethodBuilder asStatic() {
        this.isStatic = true;
        return this;
    }

    /**
     * Marks the method as an override. This routes {@link #open()} to the
     * writer's override-specific handling; conflicting modifiers may be ignored
     * by the writer.
     *
     * @return This builder.
     */
    public MethodBuilder asOverride() {
        this.isOverride = true;
        return this;
    }

    /**
     * Marks the return value as const (ignored by languages without the
     * concept).
     *
     * @param isReturnConst True if the return value is const.
     * @return This builder.
     */
    public MethodBuilder returnConst(boolean isReturnConst) {
        this.isReturnConst = isReturnConst;
        return this;
    }

    /**
     * Marks the return value as an actual instance type.
     *
     * @return This builder.
     */
    public MethodBuilder returnActual() {
        this.isReturnActual = true;
        return this;
    }

    /**
     * Sets whether the return value is an actual instance type.
     *
     * @param isReturnActual True if the return is an actual instance.
     * @return This builder.
     */
    public MethodBuilder returnActual(boolean isReturnActual) {
        this.isReturnActual = isReturnActual;
        return this;
    }

    /**
     * Marks the method as deprecated.
     *
     * @return This builder.
     */
    public MethodBuilder deprecated() {
        this.isDeprecated = true;
        return this;
    }

    /**
     * Sets whether the method is deprecated.
     *
     * @param isDeprecated True if the method is deprecated.
     * @return This builder.
     */
    public MethodBuilder deprecated(boolean isDeprecated) {
        this.isDeprecated = isDeprecated;
        return this;
    }

    /**
     * Emits the method declaration and returns the writer for its body.
     *
     * @return The method writer for adding the method body.
     * @throws IOException If there is an IO error.
     */
    public MethodWriter open() throws IOException {
        if (isOverride) {
            return writer.addMethodOpenStatementOverride(returnType, methodName, args, throwsSpec);
        }
        return writer.addMethodOpenStatement(isFinal, isVirtual, isConst, isStatic, scope,
                isReturnConst, isReturnActual, returnType, methodName, args, throwsSpec,
                comment, returnComment, throwsComment, isDeprecated);
    }
}
