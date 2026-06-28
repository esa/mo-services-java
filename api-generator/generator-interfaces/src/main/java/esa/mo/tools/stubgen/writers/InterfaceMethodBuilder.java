/* ----------------------------------------------------------------------------
 * Copyright (C) 2026      European Space Agency
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Fluent builder for an interface method declaration on an
 * {@link InterfaceWriter}.
 * <p>
 * It accumulates the method name, return type, arguments, throws clause
 * and comments, then on {@link #declare()} delegates to the writer's canonical
 * {@link InterfaceWriter#addInterfaceMethodDeclaration} method. Unlike a class
 * method, an interface declaration has no body and no modifiers, so this builder
 * deliberately exposes only what a declaration needs.
 * <p>
 * Defaults: {@code void} return type, no arguments, no throws clause.
 */
public final class InterfaceMethodBuilder {

    private final InterfaceWriter writer;
    private final String methodName;
    private CompositeField returnType = null;
    private List<CompositeField> args = null;
    private String throwsSpec = null;
    private String comment = null;
    private String returnComment = null;
    private List<String> throwsComment = null;

    /**
     * Constructor. Prefer {@link InterfaceWriter#interfaceMethod(String)} over
     * calling this directly.
     *
     * @param writer The interface writer the method will be declared on.
     * @param methodName The method name.
     */
    public InterfaceMethodBuilder(InterfaceWriter writer, String methodName) {
        this.writer = writer;
        this.methodName = methodName;
    }

    /**
     * Sets the return type. When unset the method returns void.
     *
     * @param returnType The return type.
     * @return This builder.
     */
    public InterfaceMethodBuilder returns(CompositeField returnType) {
        this.returnType = returnType;
        return this;
    }

    /**
     * Adds a single argument, in declaration order. The first call creates the
     * list; if no argument is ever added the arguments remain unset.
     *
     * @param arg The argument to add.
     * @return This builder.
     */
    public InterfaceMethodBuilder addArgument(CompositeField arg) {
        if (this.args == null) {
            this.args = new ArrayList<>();
        }
        this.args.add(arg);
        return this;
    }

    /**
     * Adds all the given arguments, in order, after any already added. The first
     * call creates the list; if none are ever added the arguments remain unset.
     *
     * @param args The arguments to add.
     * @return This builder.
     */
    public InterfaceMethodBuilder addArguments(List<CompositeField> args) {
        if (this.args == null) {
            this.args = new ArrayList<>();
        }
        this.args.addAll(args);
        return this;
    }

    /**
     * Adds a thrown type to the throws clause without an {@code @throws}
     * comment, joined with any previously added types.
     *
     * @param type The thrown type, as it should appear in the throws clause.
     * @return This builder.
     */
    public InterfaceMethodBuilder addThrows(String type) {
        if (this.throwsSpec == null) {
            this.throwsSpec = type;
        } else {
            this.throwsSpec += ", " + type;
        }
        return this;
    }

    /**
     * Adds a thrown type together with its description in a single call. The
     * type is appended to the throws clause and a matching
     * {@code "<type> <description>"} entry is appended to the throws comments,
     * keeping the clause and the documentation in step.
     *
     * @param type The thrown type, as it should appear in the throws clause.
     * @param description The description of when the type is thrown, without the
     * leading type name.
     * @return This builder.
     */
    public InterfaceMethodBuilder addThrows(String type, String description) {
        addThrows(type);
        if (this.throwsComment == null) {
            this.throwsComment = new ArrayList<>();
        }
        this.throwsComment.add(type + " " + description);
        return this;
    }

    /**
     * Sets the method comment.
     *
     * @param comment The comment.
     * @return This builder.
     */
    public InterfaceMethodBuilder comment(String comment) {
        this.comment = comment;
        return this;
    }

    /**
     * Sets the return value comment.
     *
     * @param returnComment The return comment.
     * @return This builder.
     */
    public InterfaceMethodBuilder returnComment(String returnComment) {
        this.returnComment = returnComment;
        return this;
    }

    /**
     * Emits the interface method declaration (no body, terminated with a
     * semicolon).
     *
     * @throws IOException If there is an IO error.
     */
    public void declare() throws IOException {
        writer.addInterfaceMethodDeclaration(returnType, methodName, args,
                throwsSpec, comment, returnComment, throwsComment);
    }
}
