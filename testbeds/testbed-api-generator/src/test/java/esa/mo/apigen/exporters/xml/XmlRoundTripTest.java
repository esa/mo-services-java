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
package esa.mo.apigen.exporters.xml;

import esa.mo.apigen.importers.xml.XmlImporter;
import esa.mo.apigen.model.SourceRef;
import esa.mo.apigen.model.Specification;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

/**
 * The Phase 2 acceptance criterion: every specification survives
 * {@code xml -> model -> xml -> model} unchanged, and what comes out satisfies the schema.
 * <p>
 * The comparison is between the two models rather than between the two documents.
 * Comparing text would test the writer's layout, which is not what has to be preserved;
 * comparing models tests that nothing was lost, which is.
 */
public class XmlRoundTripTest {

    private List<File> files;

    @Before
    public void findCorpus() {
        files = corpus();
        Assume.assumeFalse("specifications not found", files.isEmpty());
    }

    private static List<File> corpus() {
        List<File> all = new ArrayList<File>();
        for (String set : new String[]{"prototypes", "standards"}) {
            File here = new File(System.getProperty("user.dir")).getAbsoluteFile();
            while (here != null) {
                File dir = new File(here,
                        "xml-service-specifications/xml-ccsds-mo-" + set + "/src/main/resources/xml");
                if (dir.isDirectory()) {
                    File[] found = dir.listFiles();
                    if (found != null) {
                        for (File f : found) {
                            if (f.isFile() && f.getName().endsWith(".xml")) {
                                all.add(f);
                            }
                        }
                    }
                    break;
                }
                here = here.getParentFile();
            }
        }
        Collections.sort(all);
        return all;
    }

    private static Specification read(File file) throws Exception {
        Reader in = new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8"));
        try {
            return new XmlImporter().read(in, new SourceRef(file.getName(), file.getPath()));
        } finally {
            in.close();
        }
    }

    @Test
    public void everySpecificationSurvivesTheRoundTrip() throws Exception {
        List<String> failures = new ArrayList<String>();
        int checked = 0;
        for (File file : files) {
            Specification original = read(file);
            String xml = new XmlExporter().toXml(original);
            Specification reread = new XmlImporter().read(new StringReader(xml),
                    new SourceRef(file.getName(), null));

            ModelComparison comparison = new ModelComparison();
            comparison.compare(original, reread);
            if (!comparison.isEqual()) {
                failures.add(file.getName() + ":");
                for (String difference : comparison.getDifferences()) {
                    failures.add("    " + difference);
                }
            }
            checked++;
        }
        assertEquals("every specification should round trip", files.size(), checked);
        if (!failures.isEmpty()) {
            StringBuilder message = new StringBuilder("round trip lost information:\n");
            for (String line : failures) {
                message.append(line).append('\n');
            }
            fail(message.toString());
        }
    }

    @Test
    public void exportedDocumentsSatisfyTheirSchema() throws Exception {
        // toXml validates before returning, so reaching the end is the assertion.
        for (File file : files) {
            assertNotNull(file.getName(), new XmlExporter().toXml(read(file)));
        }
    }

    /**
     * Every specification satisfies its own schema. This was not true until the
     * misspelled area attribute, the duplicate operation number and the duplicate short
     * form were fixed, each of which was found by turning this check on.
     */
    @Test
    public void everySpecificationSatisfiesItsSchema() throws Exception {
        for (File file : files) {
            assertNotNull(file.getName(), new XmlExporter().toXml(read(file)));
        }
    }

    @Test
    public void exportIsStable() throws Exception {
        for (File file : files) {
            Specification spec = read(file);
            assertEquals(file.getName(),
                    new XmlExporter().toXml(spec), new XmlExporter().toXml(spec));
        }
    }

    @Test
    public void reExportOfAReadModelIsIdempotent() throws Exception {
        for (File file : files) {
            Specification original = read(file);
            String once = new XmlExporter().toXml(original);
            Specification reread = new XmlImporter().read(new StringReader(once),
                    new SourceRef(file.getName(), null));
            String twice = new XmlExporter().toXml(reread);
            assertEquals(file.getName() + " is not idempotent", once, twice);
        }
    }
}
