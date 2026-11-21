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

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tests the rules that decide which qualified names are shortened and imported.
 */
public class JavaImportsTest {

    private static String unit(String... lines) {
        StringBuilder buf = new StringBuilder("package org.a;\n\n");
        for (String line : lines) {
            buf.append(line).append('\n');
        }
        return buf.toString();
    }

    /**
     * A type from another package is imported; one from the file's own package is
     * shortened without an import.
     */
    @Test
    public void importsOtherPackagesAndShortensOwn() {
        assertEquals(unit("import org.x.Bar;", "", "public class Foo {",
                "    Bar bar;", "    Baz baz;", "}"),
                JavaImports.organise(unit("public class Foo {",
                        "    org.x.Bar bar;", "    org.a.Baz baz;", "}")));
    }

    /**
     * Imports are sorted, and a nested type or static member keeps its outer class.
     */
    @Test
    public void sortsImportsAndKeepsMembers() {
        assertEquals(unit("import java.util.Map;", "import org.x.Bar;", "", "public class Foo {",
                "    Map.Entry e = Bar.CONSTANT;", "}"),
                JavaImports.organise(unit("public class Foo {",
                        "    java.util.Map.Entry e = org.x.Bar.CONSTANT;", "}")));
    }

    /**
     * Of two types sharing a simple name, the one in the file's own package is shortened
     * and the other stays qualified.
     */
    @Test
    public void prefersOwnPackageOnCollision() {
        assertEquals(unit("public class Foo {", "    Key k;", "    org.x.Key j;", "}"),
                JavaImports.organise(unit("public class Foo {",
                        "    org.a.Key k;", "    org.x.Key j;", "}")));
    }

    /**
     * Otherwise the type named more often is imported.
     */
    @Test
    public void prefersMoreFrequentOnCollision() {
        assertEquals(unit("import org.q.Key;", "", "public class Foo {",
                "    org.x.Key k;", "    Key j;", "    Key i;", "}"),
                JavaImports.organise(unit("public class Foo {",
                        "    org.x.Key k;", "    org.q.Key j;", "    org.q.Key i;", "}")));
    }

    /**
     * A field named like a type would obscure it in an expression, so the type stays
     * qualified.
     */
    @Test
    public void leavesNamesUsedUnqualified() {
        String source = unit("public class Foo {",
                "    int Counter;", "    long c = org.x.Counter.SHORT_FORM;", "}");
        assertEquals(source, JavaImports.organise(source));
    }

    /**
     * A type is not imported under the name of the class being declared, but the class's
     * own qualified name is shortened, also next to unqualified references to itself.
     */
    @Test
    public void handlesOwnClassName() {
        assertEquals(unit("public class Foo {", "    org.x.Foo other;",
                "    Foo self = Foo.FIRST;", "    public Foo() {", "    }", "}"),
                JavaImports.organise(unit("public class Foo {", "    org.x.Foo other;",
                        "    org.a.Foo self = Foo.FIRST;", "    public Foo() {", "    }", "}")));
    }

    /**
     * A variable named like the class being declared blocks the shortening of its name.
     */
    @Test
    public void leavesOwnClassNameDeclaredAsVariable() {
        String source = unit("public class Foo {",
                "    String Foo;", "    long c = org.a.Foo.SHORT_FORM;", "}");
        assertEquals(source, JavaImports.organise(source));
    }

    /**
     * java.lang stays qualified, and string literals are never touched.
     */
    @Test
    public void leavesJavaLangAndLiterals() {
        String source = unit("public class Foo {",
                "    String s = \"org.x.Bar\";",
                "    void f() throws java.lang.IllegalArgumentException {", "    }", "}");
        assertEquals(source, JavaImports.organise(source));
    }

    /**
     * A comment is shortened where the code imports the type, and left qualified where
     * nothing else names it, so that no import exists for the documentation alone.
     */
    @Test
    public void commentsFollowTheCode() {
        assertEquals(unit("import org.x.Bar;", "", "/**", " * {@link Bar} {@link org.x.Other}", " */",
                "public class Foo {", "    Bar bar;", "}"),
                JavaImports.organise(unit("/**", " * {@link org.x.Bar} {@link org.x.Other}", " */",
                        "public class Foo {", "    org.x.Bar bar;", "}")));
    }
}
