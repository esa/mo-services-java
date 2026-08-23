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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * The numbering part of a document, held against the reference output.
 * <p>
 * A document that starts no numbered list still carries the two numberings the preamble
 * defines, so the MAL specifications - which have no such list - pin the fixed part exactly.
 */
public class DocxNumberingTest {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    /**
     * Where the captured output of the existing generator lives, relative to the root of
     * the repository.
     */
    private static final String BASELINE = "testbeds/testbed-api-generator/baseline";

    /**
     * @return the captured numbering part of a document, or null if the baseline has not
     * been taken.
     */
    private static File baseline(String document) {
        File here = new File(System.getProperty("user.dir")).getAbsoluteFile();
        while (here != null && !new File(here, "xml-service-specifications").isDirectory()) {
            here = here.getParentFile();
        }
        if (here == null) {
            return null;
        }
        File file = new File(here, BASELINE + "/docx/prototypes/"
                + document + "/word/numbering.xml");
        return file.isFile() ? file : null;
    }

    @Test
    public void aDocumentWithNoListsCarriesOnlyTheFixedNumbering() throws Exception {
        File expected = baseline("Service_Specification_area001-v001-MAL");
        Assume.assumeNotNull("baseline not captured; run api-generator/golden/golden.sh capture",
                expected);

        String reference = new String(Files.readAllBytes(expected.toPath()), UTF8);
        assertEquals(reference, new DocxNumbering().toXml());
    }

    /**
     * Each list asked for gets a numbering of its own, so that one list does not carry on
     * counting where the last one stopped.
     */
    @Test
    public void everyListAskedForGetsItsOwnNumbering() throws Exception {
        DocxNumbering numbering = new DocxNumbering();
        assertEquals(2, numbering.nextInstance());
        assertEquals(3, numbering.nextInstance());

        String xml = numbering.toXml();
        assertTrue("the definition of the second list has to be there",
                xml.contains("<w:abstractNum w:abstractNumId=\"3\">"));
        assertTrue("and the instance that refers to it",
                xml.contains("<w:num w:numId=\"3\"><w:abstractNumId w:val=\"3\"/></w:num>"));
    }
}
