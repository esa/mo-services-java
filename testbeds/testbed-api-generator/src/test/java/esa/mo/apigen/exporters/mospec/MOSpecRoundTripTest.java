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
package esa.mo.apigen.exporters.mospec;

import esa.mo.apigen.exporters.xml.ModelComparison;
import esa.mo.apigen.exporters.xml.XmlExporter;
import esa.mo.apigen.importers.mospec.MOSpecImporter;
import esa.mo.apigen.importers.xml.XmlImporter;
import esa.mo.apigen.model.SourceRef;
import esa.mo.apigen.model.Specification;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * The closure test the format is held to: the grammar must accept everything the exporter
 * writes, and what comes back must be what went in.
 * <p>
 * Run over every specification in the repository, in each mode that claims to preserve the
 * model:
 * <pre>
 * xml -&gt; model -&gt; text -&gt; model' -&gt; text'
 *       model == model'   nothing was lost
 *       text  == text'    the formatting has settled
 * </pre>
 * With this, "I do not like how the output reads" is a change to the formatter and a run of
 * this test, rather than a change to the writer and a patch to the parser.
 */
public class MOSpecRoundTripTest {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private List<File> files;

    @Before
    public void findCorpus() {
        files = corpus();
        Assume.assumeFalse("specifications not found", files.isEmpty());
    }

    /**
     * Every specification, in every mode that keeps the model.
     */
    @Test
    public void everySpecificationSurvivesTheRoundTrip() throws Exception {
        List<String> failures = new ArrayList<String>();
        int checked = 0;

        for (DocMode mode : Arrays.asList(DocMode.BULK, DocMode.INLINE)) {
            MOSpecExporter exporter = new MOSpecExporter(mode);
            for (File file : files) {
                Specification original = readXml(file);

                // Written to a directory and read back from it, because that is what a
                // MOSpec specification is: the text names its diagrams and the drawings sit
                // beside it. Reading the text alone would compare a model that had lost them.
                File directory = writeToTemporaryDirectory(exporter, original);
                String text = exporter.toText(original);

                Specification reread;
                try {
                    reread = readMOSpec(directory, original);
                } catch (Exception ex) {
                    failures.add(mode + " " + file.getName() + ": did not parse - "
                            + ex.getMessage());
                    continue;
                }

                ModelComparison comparison = new ModelComparison();
                comparison.compare(original, reread);
                if (!comparison.isEqual()) {
                    failures.add(mode + " " + file.getName() + ": the model changed - "
                            + first(comparison.getDifferences()));
                }

                String again = exporter.toText(reread);
                if (!text.equals(again)) {
                    failures.add(mode + " " + file.getName() + ": the text changed - "
                            + firstDifference(text, again));
                }
                checked++;
            }
        }

        assertTrue("no specifications were checked", checked > 0);
        if (!failures.isEmpty()) {
            fail(failures.size() + " of " + checked + " round trip(s) failed:\n    "
                    + join(failures));
        }
    }

    /**
     * The mode that strips documentation cannot preserve the model, so it is held to what
     * it does claim: the text still parses, and everything that is not documentation comes
     * back unchanged.
     */
    @Test
    public void suppressingDocumentationKeepsEverythingElse() throws Exception {
        List<String> failures = new ArrayList<String>();
        MOSpecExporter exporter = new MOSpecExporter(DocMode.SUPPRESS);

        for (File file : files) {
            Specification original = readXml(file);
            File directory = writeToTemporaryDirectory(exporter, original);

            Specification reread;
            try {
                reread = readMOSpec(directory, original);
            } catch (Exception ex) {
                failures.add(file.getName() + ": did not parse - " + ex.getMessage());
                continue;
            }

            // Compared against the original with its documentation taken away, which is
            // what this mode says it produces.
            ModelComparison comparison = new ModelComparison();
            comparison.compare(undocumented(original), reread);
            if (!comparison.isEqual()) {
                failures.add(file.getName() + ": " + comparison.getDifferences().get(0));
            }
        }
        if (!failures.isEmpty()) {
            fail(failures.size() + " specification(s) lost something other than"
                    + " documentation:\n    " + join(failures));
        }
    }

    /**
     * @return the specification with every comment and documentation section removed, read
     * afresh so that the original is left alone.
     */
    private static Specification undocumented(Specification spec) throws Exception {
        Specification stripped = readXml(new File(spec.getSource().getLocation()));
        stripped.setComment(null);
        for (esa.mo.apigen.model.Area area : stripped.getAreas()) {
            area.setComment(null);
            area.getDocumentation().getSections().clear();
            stripComments(area.getDataTypes());
            for (esa.mo.apigen.model.ErrorDefinition error : area.getErrors()) {
                error.setComment(null);
            }
            for (esa.mo.apigen.model.Service service : area.getServices()) {
                service.setComment(null);
                service.getDocumentation().getSections().clear();
                stripComments(service.getDataTypes());
                for (esa.mo.apigen.model.ErrorDefinition error : service.getErrors()) {
                    error.setComment(null);
                }
                for (esa.mo.apigen.model.CapabilitySet set : service.getCapabilitySets()) {
                    set.setComment(null);
                    for (esa.mo.apigen.model.Operation operation : set.getOperations()) {
                        operation.setComment(null);
                        operation.getDocumentation().getSections().clear();
                        for (esa.mo.apigen.model.MessageBody body
                                : operation.getMessages().values()) {
                            body.setComment(null);
                            for (esa.mo.apigen.model.Field field : body.getFields()) {
                                field.setComment(null);
                            }
                        }
                        for (esa.mo.apigen.model.ErrorReference error : operation.getErrors()) {
                            error.setComment(null);
                            if (error.getExtraInformation() != null) {
                                error.getExtraInformation().setComment(null);
                            }
                        }
                    }
                }
                if (service.getCom() != null) {
                    service.getCom().setObjectsComment(null);
                    service.getCom().setEventsComment(null);
                    service.getCom().setArchiveUsage(null);
                    service.getCom().setActivityUsage(null);
                    service.getCom().getDocumentation().getSections().clear();
                    stripComments(service.getCom().getObjects());
                    stripComments(service.getCom().getEvents());
                }
            }
        }
        return stripped;
    }

    private static void stripComments(List<? extends Object> things) {
        for (Object thing : things) {
            if (thing instanceof esa.mo.apigen.model.types.TypeDefinition) {
                esa.mo.apigen.model.types.TypeDefinition type
                        = (esa.mo.apigen.model.types.TypeDefinition) thing;
                type.setComment(null);
                if (type instanceof esa.mo.apigen.model.types.CompositeType) {
                    for (esa.mo.apigen.model.Field field
                            : ((esa.mo.apigen.model.types.CompositeType) type).getFields()) {
                        field.setComment(null);
                    }
                }
                if (type instanceof esa.mo.apigen.model.types.EnumerationType) {
                    for (esa.mo.apigen.model.types.EnumerationItem item
                            : ((esa.mo.apigen.model.types.EnumerationType) type).getItems()) {
                        item.setComment(null);
                    }
                }
            }
            if (thing instanceof esa.mo.apigen.model.com.COMObject) {
                esa.mo.apigen.model.com.COMObject object
                        = (esa.mo.apigen.model.com.COMObject) thing;
                object.setComment(null);
                if (object.getRelated() != null) {
                    object.getRelated().setComment(null);
                }
                if (object.getSource() != null) {
                    object.getSource().setComment(null);
                }
            }
        }
    }

    /**
     * The longer trip: out through the text format and back to XML.
     * <p>
     * This is what makes MOSpec a format the specifications can actually be kept in rather
     * than a view of them - what comes back has to be the file that went out.
     */
    @Test
    public void everySpecificationSurvivesTheTripBackToXml() throws Exception {
        List<String> failures = new ArrayList<String>();
        MOSpecExporter exporter = new MOSpecExporter(DocMode.BULK);

        for (File file : files) {
            Specification original = readXml(file);
            String before = new XmlExporter().toXml(original);

            File directory = writeToTemporaryDirectory(exporter, original);
            Specification reread = readMOSpec(directory, original);
            String after = new XmlExporter().toXml(reread);

            if (!modernised(before).equals(after)) {
                failures.add(file.getName() + ": " + firstDifference(modernised(before), after)
                        .replace("\n        ", "\n            "));
            }
        }
        if (!failures.isEmpty()) {
            fail(failures.size() + " specification(s) did not survive:\n    " + join(failures));
        }
    }

    /**
     * Rewrites the older spelling of an object reference into the one MOSpec writes.
     * <p>
     * A type named {@code ObjectRef(Product)} and a type named {@code Product} carrying the
     * objectRef flag mean the same thing, and MOSpec writes the second. Three fields in
     * Mission Product Distribution use the first, so their XML comes back modernised - a
     * decision taken deliberately, not an accident to be papered over. Applying the same
     * rewrite to the original is what lets the test still insist on equality everywhere
     * else: any other change to the XML fails.
     */
    private static String modernised(String xml) {
        return xml.replaceAll("<mal:type([^>]*?)name=\"ObjectRef\\(([^)]*)\\)\"([^>]*?)/>",
                "<mal:type$1name=\"$2\"$3 objectRef=\"true\"/>");
    }

    /**
     * Writes a specification into a directory of its own, sidecars and all.
     */
    private static File writeToTemporaryDirectory(MOSpecExporter exporter, Specification spec)
            throws Exception {
        File directory = java.nio.file.Files.createTempDirectory("mospec").toFile();
        directory.deleteOnExit();
        exporter.write(spec, directory.toPath());
        return directory;
    }

    /**
     * @return the specification read back out of the directory it was written to.
     */
    private static Specification readMOSpec(File directory, Specification original)
            throws Exception {
        String name = original.getAreas().isEmpty() ? "specification"
                : original.getAreas().get(0).getName();
        File file = new File(directory, name + ".mospec");
        Reader in = new InputStreamReader(new FileInputStream(file), UTF8);
        try {
            return new MOSpecImporter().read(in, new SourceRef(file.getName(), file.getPath()));
        } finally {
            in.close();
        }
    }

    /**
     * @return the first line the two texts disagree on, which is enough to say what moved.
     */
    private static String firstDifference(String one, String other) {
        String[] a = one.split("\n", -1);
        String[] b = other.split("\n", -1);
        for (int i = 0; i < Math.max(a.length, b.length); i++) {
            String x = i < a.length ? a[i] : "<end of file>";
            String y = i < b.length ? b[i] : "<end of file>";
            if (!x.equals(y)) {
                return "line " + (i + 1) + "\n        was: " + x + "\n        now: " + y;
            }
        }
        return "";
    }

    private static Specification readXml(File file) throws Exception {
        Reader in = new InputStreamReader(new FileInputStream(file), UTF8);
        try {
            return new XmlImporter().read(in, new SourceRef(file.getName(), file.getPath()));
        } finally {
            in.close();
        }
    }

    private static List<File> corpus() {
        List<File> all = new ArrayList<File>();
        File here = new File(System.getProperty("user.dir")).getAbsoluteFile();
        while (here != null && !new File(here, "xml-service-specifications").isDirectory()) {
            here = here.getParentFile();
        }
        if (here == null) {
            return all;
        }
        for (String set : new String[]{"prototypes", "standards"}) {
            File dir = new File(here,
                    "xml-service-specifications/xml-ccsds-mo-" + set + "/src/main/resources/xml");
            File[] found = dir.listFiles();
            if (found == null) {
                continue;
            }
            Arrays.sort(found);
            for (File file : found) {
                if (file.getName().endsWith(".xml")) {
                    all.add(file);
                }
            }
        }
        return all;
    }

    private static String first(List<String> differences) {
        return differences.isEmpty() ? "" : differences.get(0);
    }

    private static String join(List<String> lines) {
        StringBuilder buf = new StringBuilder();
        for (String line : lines) {
            buf.append(line).append("\n    ");
        }
        return buf.toString();
    }
}
