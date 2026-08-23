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
package esa.mo.apigen.generators.docx;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import org.junit.Assume;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * The document body, held against the reference output.
 * <p>
 * Rather than restating what the XML should look like, each test builds a fragment and
 * checks that the captured document contains it word for word. A fragment that is right in
 * isolation but wrong in place would pass the first kind of test and fail this one.
 */
public class DocxBodyTest {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    /**
     * Where the captured output of the existing generator lives, relative to the root
     * of the repository.
     */
    private static final String BASELINE = "testbeds/testbed-api-generator/baseline";

    private static final String SHADE = "00CCFF";

    /**
     * @return a captured document, or null if the baseline has not been taken.
     */
    private static String reference(String document) throws Exception {
        File here = new File(System.getProperty("user.dir")).getAbsoluteFile();
        while (here != null && !new File(here, "xml-service-specifications").isDirectory()) {
            here = here.getParentFile();
        }
        if (here == null) {
            return null;
        }
        File file = new File(here, BASELINE + "/docx/prototypes/"
                + document + "/word/document.xml");
        return file.isFile() ? new String(Files.readAllBytes(file.toPath()), UTF8) : null;
    }

    /**
     * A captioned table with a shaded, centred header row - the shape almost every table in
     * these documents starts with.
     */
    @Test
    public void aCaptionedTableWithAHeaderRowMatchesTheReference() throws Exception {
        String document = reference("Service_Specification_area009-v001-MPD");
        Assume.assumeNotNull("baseline not captured; run api-generator/golden/golden.sh capture",
                document);

        DocxBody body = new DocxBody();
        DocxTable table = body.table(new int[]{2250, 2801, 1382, 1185, 1382},
                "ProductRetrieval Service Operations");
        table.row()
                .cell("Area Identifier").shaded(SHADE).centered().next()
                .cell("Service Identifier").shaded(SHADE).centered().next()
                .cell("Area Number").shaded(SHADE).centered().next()
                .cell("Service Number").shaded(SHADE).centered().next()
                .cell("Area Version").shaded(SHADE).centered().endRow();

        assertTrue("the caption, the table and its header row have to appear as written",
                document.contains(body.toXml()));
    }

    /**
     * A figure caption is the same construction as a table caption, with two words changed.
     * Software Management is the specification that carries diagrams.
     */
    @Test
    public void aFigureCaptionMatchesTheReference() throws Exception {
        String document = reference("Service_Specification_area007-v001-SoftwareManagement");
        Assume.assumeNotNull("baseline not captured", document);

        int start = document.indexOf("<w:name=\"F_");
        Assume.assumeTrue("this document should carry a figure",
                document.contains("F_") && document.contains("SEQ Figure"));

        DocxBody body = new DocxBody();
        body.figureCaption(firstFigureName(document));

        assertTrue("the figure caption has to appear as written",
                document.contains(body.toXml()));
    }

    /**
     * The headings of a document: a plain one, and one that can be linked to.
     */
    @Test
    public void headingsMatchTheReference() throws Exception {
        String document = reference("Service_Specification_area009-v001-MPD");
        Assume.assumeNotNull("baseline not captured", document);

        DocxBody plain = new DocxBody();
        plain.title(1, "Specification: MPD");
        assertTrue("a heading with nothing to link to",
                document.contains(plain.toXml()));

        DocxBody linked = new DocxBody();
        // The name is what the anchor is named after, and what the shown text ends with:
        // "SERVICE_ProductRetrieval" anchoring "Service: ProductRetrieval".
        linked.title(2, "Service: ", "ProductRetrieval", "SERVICE");
        assertTrue("a heading that can be linked to carries its anchor",
                document.contains(linked.toXml()));
    }

    /**
     * A numbered list takes a numbering of its own, so the first list of a document is
     * numbering 2 - the two the preamble defines being 0 and 1.
     */
    @Test
    public void aNumberedListMatchesTheReference() throws Exception {
        String document = reference("Service_Specification_area009-v001-MPD");
        Assume.assumeNotNull("baseline not captured", document);

        DocxBody body = new DocxBody(new DocxNumbering());
        body.numberedComment(java.util.Arrays.asList(
                "ProductRetrieval", "OrderManagement", "ProductOrderDelivery"));

        assertTrue("the list of services has to appear as written",
                document.contains(body.toXml()));
    }

    /**
     * A field whose type this document also defines is named with a link to the definition,
     * with the list wrapper around it and the field name after it.
     */
    @Test
    public void aLinkedFieldMatchesTheReference() throws Exception {
        String document = reference("Service_Specification_area009-v001-MPD");
        Assume.assumeNotNull("baseline not captured", document);

        esa.mo.apigen.model.Area area = new esa.mo.apigen.model.Area();
        area.setName("MPD");
        esa.mo.apigen.model.types.TypeRef type = new esa.mo.apigen.model.types.TypeRef(
                "MPD", 1, null, "ProductMetadata", true, false);
        esa.mo.apigen.model.Field field = new esa.mo.apigen.model.Field();
        field.setName("metadatas");
        field.setType(type);

        DocxBody body = new DocxBody();
        DocxTable table = body.table(new int[]{4300});
        table.row().markedUp(DocxTypeLink.forField(area, null, field)).centered().endRow();

        assertTrue("the linked field has to appear as written", document.contains(
                "<w:tc><w:tcPr><w:tcW w:w=\"4300\" w:type=\"dxa\"/></w:tcPr><w:p>"
                + "<w:pPr><w:jc w:val=\"center\"/></w:pPr>"
                + DocxTypeLink.forField(area, null, field) + "</w:p></w:tc>"));
    }

    /**
     * A name that links to something other than a type - an operation, say - goes through
     * the same construction with the bookmark named directly.
     */
    @Test
    public void aLinkToAnOperationMatchesTheReference() throws Exception {
        String document = reference("Service_Specification_area009-v001-MPD");
        Assume.assumeNotNull("baseline not captured", document);

        assertTrue("the operation link has to appear as written", document.contains(
                DocxTypeLink.hyperlink("", "listProducts", "",
                        "OPERATION_1_listProducts", true)));
    }

    /**
     * @return the name of the first figure in the document, read back out of its bookmark.
     */
    private static String firstFigureName(String document) {
        String marker = "<w:bookmarkStart w:id=\"0\" w:name=\"F_";
        int start = document.indexOf(marker) + marker.length();
        return document.substring(start, document.indexOf('"', start));
    }
}
