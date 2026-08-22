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
 * Tests the shape a field comes out in, held against the reference output line for line.
 * <p>
 * As with {@link JavaMethodBuilderTest}, the examples are taken from the captured output of
 * the generator this replaces.
 */
public class JavaFieldBuilderTest {

    private static String write(JavaFieldBuilder field) {
        JavaSource out = new JavaSource();
        field.write(out);
        return out.toString();
    }

    /**
     * The plainest field there is: a blank line to separate it from what came before, and
     * no javadoc because there is nothing to say.
     */
    @Test
    public void aFieldWithNothingToSayIsWrittenWithoutJavadoc() {
        assertEquals("\n"
                + "    private static final long serialVersionUID = 2533274823950335L;\n",
                write(JavaFieldBuilder.named("serialVersionUID").scope("private")
                        .asStatic().asFinal().ofType("long").value("2533274823950335L")));
    }

    /**
     * A documented constant, which is what a helper is made of.
     */
    @Test
    public void aDocumentedConstantCarriesItsJavadoc() {
        assertEquals("\n"
                + "    /**\n"
                + "     * Area number literal.\n"
                + "     */\n"
                + "    public static final int _MPD_AREA_NUMBER = 9;\n",
                write(JavaFieldBuilder.named("_MPD_AREA_NUMBER").asStatic().asFinal()
                        .ofType("int").value("9").comment("Area number literal.")));
    }

    /**
     * A field with no value is declared and left unset, which is how a composite holds
     * what it carries.
     */
    @Test
    public void aFieldWithoutAValueIsOnlyDeclared() {
        assertEquals("\n"
                + "    /**\n"
                + "     * The product type definition.\n"
                + "     */\n"
                + "    private org.ccsds.moims.mo.mpd.structures.ProductType productType;\n",
                write(JavaFieldBuilder.named("productType").scope("private")
                        .ofType("org.ccsds.moims.mo.mpd.structures.ProductType")
                        .comment("The product type definition.")));
    }

    /**
     * A field that belongs with the one above it goes straight under it, with no blank
     * line between them.
     */
    @Test
    public void aJoinedFieldFollowsWithoutABlankLine() {
        assertEquals("    /**\n"
                + "     * The TypeId of this Element as a long.\n"
                + "     */\n"
                + "    public static final Long SHORT_FORM = 2533274823950335L;\n",
                write(JavaFieldBuilder.named("SHORT_FORM").asStatic().asFinal()
                        .ofType("Long").value("2533274823950335L")
                        .comment("The TypeId of this Element as a long.")
                        .joinedToPrevious()));
    }

    /**
     * A value spanning more than one line is written as it is given: the caller that lays
     * out an array lays out its own continuation.
     */
    @Test
    public void aMultiLineValueIsWrittenAsItIsGiven() {
        assertEquals("\n"
                + "    /**\n"
                + "     * Services in this Area.\n"
                + "     */\n"
                + "    public static final org.ccsds.moims.mo.mal.ServiceInfo[] MPD_AREA_SERVICES = {\n"
                + "        ProductRetrievalHelper.PRODUCTRETRIEVAL_SERVICE,\n"
                + "        OrderManagementHelper.ORDERMANAGEMENT_SERVICE,};\n",
                write(JavaFieldBuilder.named("MPD_AREA_SERVICES").asStatic().asFinal()
                        .ofType("org.ccsds.moims.mo.mal.ServiceInfo[]")
                        .value("{\n        ProductRetrievalHelper.PRODUCTRETRIEVAL_SERVICE,"
                                + "\n        OrderManagementHelper.ORDERMANAGEMENT_SERVICE,}")
                        .comment("Services in this Area.")));
    }

    /**
     * A comment out of a specification is wrapped and has its angle brackets taken out,
     * the same as everywhere else in a class.
     */
    @Test
    public void aCommentFromASpecificationIsNormalised() {
        assertEquals("\n"
                + "    /**\n"
                + "     * The references to the _Product_ objects that are held by this standing\n"
                + "     * order.\n"
                + "     */\n"
                + "    private java.util.List references;\n",
                write(JavaFieldBuilder.named("references").scope("private")
                        .ofType("java.util.List")
                        .comment("The references to the <Product> objects that are held by"
                                + " this standing order")));
    }
}
