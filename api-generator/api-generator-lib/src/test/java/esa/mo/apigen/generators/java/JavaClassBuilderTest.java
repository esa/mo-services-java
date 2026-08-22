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
 * Tests the shape a class comes out in, held against the reference output line for line.
 * <p>
 * As with {@link JavaMethodBuilderTest}, the examples are taken from the captured output of
 * the generator this replaces.
 */
public class JavaClassBuilderTest {

    /**
     * A class that implements an interface, and holds nothing at all.
     */
    @Test
    public void anEmptyClassIsAPackageAJavadocAndTwoBraces() {
        JavaClassBuilder clazz = JavaClassBuilder.named("MPDElementFactory")
                .inPackage("org.ccsds.moims.mo.mpd").asFinal()
                .implementing("org.ccsds.moims.mo.mal.AreaElementFactory")
                .comment("Creates the Elements of the MPD area.");
        clazz.open();

        assertEquals("package org.ccsds.moims.mo.mpd;\n"
                + "\n"
                + "/**\n"
                + " * Creates the Elements of the MPD area.\n"
                + " */\n"
                + "public final class MPDElementFactory implements "
                + "org.ccsds.moims.mo.mal.AreaElementFactory {\n"
                + "\n"
                + "}\n", clazz.close());
    }

    /**
     * A class can both extend and implement, which is how a list reaches an ArrayList while
     * still answering as an Element.
     */
    @Test
    public void aClassCanBothExtendAndImplement() {
        JavaClassBuilder clazz = JavaClassBuilder.named("ProductList")
                .inPackage("org.ccsds.moims.mo.mpd.structures").asFinal()
                .extending("java.util.ArrayList<org.ccsds.moims.mo.mpd.structures.Product>")
                .implementing("org.ccsds.moims.mo.mal.structures.HomogeneousList"
                        + "<org.ccsds.moims.mo.mpd.structures.Product>")
                .comment("List class for Product.");
        JavaSource out = clazz.open();
        out.line("    // members");

        assertEquals("package org.ccsds.moims.mo.mpd.structures;\n"
                + "\n"
                + "/**\n"
                + " * List class for Product.\n"
                + " */\n"
                + "public final class ProductList extends "
                + "java.util.ArrayList<org.ccsds.moims.mo.mpd.structures.Product> implements "
                + "org.ccsds.moims.mo.mal.structures.HomogeneousList"
                + "<org.ccsds.moims.mo.mpd.structures.Product> {\n"
                + "    // members\n"
                + "\n"
                + "}\n", clazz.close());
    }

    /**
     * An abstract composite is declared abstract, and its comment out of the specification
     * is wrapped the same as any other comment in a class.
     */
    @Test
    public void anAbstractClassSaysSoAndItsCommentIsNormalised() {
        JavaClassBuilder clazz = JavaClassBuilder.named("ObjectAttribute")
                .inPackage("org.ccsds.moims.mo.mal.structures").asAbstract()
                .implementing("org.ccsds.moims.mo.mal.structures.Composite")
                .comment("The base of every attribute that carries an <Object> rather than"
                        + " a value of its own");
        clazz.open();

        assertEquals("package org.ccsds.moims.mo.mal.structures;\n"
                + "\n"
                + "/**\n"
                + " * The base of every attribute that carries an _Object_ rather than a value\n"
                + " * of its own.\n"
                + " */\n"
                + "public abstract class ObjectAttribute implements "
                + "org.ccsds.moims.mo.mal.structures.Composite {\n"
                + "\n"
                + "}\n", clazz.close());
    }

    /**
     * A helper is neither final nor abstract and extends nothing, so the declaration is
     * left as bare as it can be.
     */
    @Test
    public void aPlainClassCarriesNoModifiersOfItsOwn() {
        JavaClassBuilder clazz = JavaClassBuilder.named("MPDHelper")
                .inPackage("org.ccsds.moims.mo.mpd")
                .comment("Helper class for MPD area.");
        clazz.open();

        assertEquals("package org.ccsds.moims.mo.mpd;\n"
                + "\n"
                + "/**\n"
                + " * Helper class for MPD area.\n"
                + " */\n"
                + "public class MPDHelper {\n"
                + "\n"
                + "}\n", clazz.close());
    }

    /**
     * A class with nothing to say is written without javadoc, the same as a method or a
     * field.
     */
    @Test
    public void aClassWithNothingToSayIsWrittenWithoutJavadoc() {
        JavaClassBuilder clazz = JavaClassBuilder.named("Anonymous").inPackage("a.b");
        clazz.open();

        assertEquals("package a.b;\n"
                + "\n"
                + "public class Anonymous {\n"
                + "\n"
                + "}\n", clazz.close());
    }
}
