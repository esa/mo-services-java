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
 * Tests the shape a method comes out in, held against the reference output line for line.
 * <p>
 * The examples are taken from the captured output of the generator this replaces, so that
 * the rules the emitter follows are the ones the existing API was written with, and not
 * whatever looked reasonable while it was being written.
 */
public class JavaMethodBuilderTest {

    private static String write(JavaMethodBuilder method) {
        JavaSource out = new JavaSource();
        method.write(out);
        return out.toString();
    }

    /**
     * The plainest method there is: a blank line to separate it from what came before, no
     * javadoc because there is nothing to say, and the body a step in.
     */
    @Test
    public void aMethodWithNothingToSayIsWrittenWithoutJavadoc() {
        assertEquals("\n"
                + "    private static void hide() {\n"
                + "        return;\n"
                + "    }\n",
                write(JavaMethodBuilder.named("hide").scope("private").asStatic().line("return;")));
    }

    /**
     * Arguments past the first are carried onto their own line, however short the
     * signature is, and the throws clause stays on the line of the last of them.
     */
    @Test
    public void argumentsPastTheFirstAreCarriedOntoTheirOwnLine() {
        String source = write(JavaMethodBuilder.named("getProducts")
                .argument("org.ccsds.moims.mo.mal.structures.ObjectRefList", "productRefs",
                        "The references to the products to be retrieved.")
                .argument("org.ccsds.moims.mo.mpd.productretrieval.consumer."
                        + "ProductRetrievalAdapter", "adapter",
                        "adapter Listener in charge of receiving the messages from the"
                        + " service provider")
                .throwing("org.ccsds.moims.mo.mal.MALInteractionException",
                        "if there is a problem during the interaction as defined by the"
                        + " MAL specification.")
                .throwing("org.ccsds.moims.mo.mal.MALException",
                        "if there is an implementation exception")
                .comment("The getProducts operation retrieves the selected mission data"
                        + " products from the provider")
                .line("consumer.progress(GETPRODUCTS_OP, adapter, productRefs);"));

        assertEquals("\n"
                + "    /**\n"
                + "     * The getProducts operation retrieves the selected mission data products\n"
                + "     * from the provider.\n"
                + "     * \n"
                + "     * @param productRefs The references to the products to be retrieved.\n"
                + "     * @param adapter adapter Listener in charge of receiving the messages from the service provider\n"
                + "     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.\n"
                + "     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception\n"
                + "     */\n"
                + "    public void getProducts(org.ccsds.moims.mo.mal.structures.ObjectRefList productRefs,\n"
                + "            org.ccsds.moims.mo.mpd.productretrieval.consumer.ProductRetrievalAdapter adapter) "
                + "throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {\n"
                + "        consumer.progress(GETPRODUCTS_OP, adapter, productRefs);\n"
                + "    }\n", source);
    }

    /**
     * An argument nobody described is still documented, by name.
     */
    @Test
    public void anUndescribedArgumentIsDocumentedByItsName() {
        assertEquals("\n"
                + "    /**\n"
                + "     * Creates an Element declared by the area itself.\n"
                + "     * \n"
                + "     * @param typeNumber The typeNumber field.\n"
                + "     */\n"
                + "    private static Element createAreaElement(int typeNumber) {\n"
                + "        return null;\n"
                + "    }\n",
                write(JavaMethodBuilder.named("createAreaElement").scope("private").asStatic()
                        .comment("Creates an Element declared by the area itself.")
                        .returns("Element", null)
                        .argument("int", "typeNumber", null)
                        .line("return null;")));
    }

    /**
     * The blank line between the description and the tags is written even when there is no
     * description above it.
     */
    @Test
    public void theTagsAreSeparatedEvenWithNothingToSeparateThemFrom() {
        assertEquals("\n"
                + "    /**\n"
                + "     * \n"
                + "     * @param name The Subscription Key name\n"
                + "     */\n"
                + "    public void add(String name) {\n"
                + "    }\n",
                write(JavaMethodBuilder.named("add")
                        .argument("String", "name", "The Subscription Key name")));
    }

    /**
     * An override says the same thing as the method it overrides, so it is left
     * undocumented however much is known about its arguments.
     */
    @Test
    public void anOverrideIsWrittenWithoutJavadoc() {
        assertEquals("\n"
                + "    @Override\n"
                + "    public int getAreaNumber() {\n"
                + "        return 9;\n"
                + "    }\n",
                write(JavaMethodBuilder.named("getAreaNumber").asOverride().returns("int", null)
                        .line("return 9;")));
    }

    /**
     * An override is public whatever the scope it was given, because the method it
     * overrides is.
     */
    @Test
    public void anOverrideIsPublic() {
        assertEquals("\n"
                + "    @Override\n"
                + "    public void run() {\n"
                + "    }\n",
                write(JavaMethodBuilder.named("run").scope("protected").asOverride()));
    }

    /**
     * A constructor is written without a return type, void or otherwise.
     */
    @Test
    public void aConstructorHasNoReturnType() {
        assertEquals("\n"
                + "    /**\n"
                + "     * Constructs a new UnknownException exception.\n"
                + "     * \n"
                + "     * @param extraInformation The extraInformation of the exception.\n"
                + "     */\n"
                + "    public UnknownException(Object extraInformation) {\n"
                + "        super(MO_ERROR_NAME, UNKNOWN_ERROR_NUMBER, extraInformation);\n"
                + "    }\n",
                write(JavaMethodBuilder.constructor("UnknownException")
                        .comment("Constructs a new UnknownException exception.")
                        .argument("Object", "extraInformation",
                                "The extraInformation of the exception.")
                        .line("super(MO_ERROR_NAME, UNKNOWN_ERROR_NUMBER, extraInformation);")));
    }

    /**
     * A method of an interface is a declaration: no scope in front of it, a semicolon
     * instead of a body.
     */
    @Test
    public void aDeclarationEndsAtTheSemicolon() {
        // No blank line in front of it: an interface packs its declarations together, and
        // the one that separates the first from the declaration is written by the class.
        assertEquals("    /**\n"
                + "     * \n"
                + "     * @param interaction The interaction field.\n"
                + "     * @return The returned value.\n"
                + "     */\n"
                + "    Long listProducts(MALInteraction interaction) throws MALException;\n",
                write(JavaMethodBuilder.named("listProducts").asDeclaration()
                        .returns("Long", "The returned value.")
                        .argument("MALInteraction", "interaction", null)
                        .throwing("MALException", null)));
    }

    /**
     * Angle brackets would be read as markup, so they are taken out of every line of the
     * javadoc and not only out of the description.
     */
    @Test
    public void angleBracketsAreTakenOutOfTheWholeJavadoc() {
        assertEquals("\n"
                + "    /**\n"
                + "     * \n"
                + "     * @param names The _Identifier_ names.\n"
                + "     */\n"
                + "    public void setNames(java.util.List names) {\n"
                + "    }\n",
                write(JavaMethodBuilder.named("setNames")
                        .argument("java.util.List", "names", "The <Identifier> names.")));
    }

    /**
     * A deprecated method carries the annotation, under its javadoc and above whatever
     * else it is.
     */
    @Test
    public void aDeprecatedMethodCarriesTheAnnotation() {
        assertEquals("\n"
                + "    @Deprecated\n"
                + "    public final void gone() {\n"
                + "    }\n",
                write(JavaMethodBuilder.named("gone").asDeprecated().asFinal()));
    }
}
