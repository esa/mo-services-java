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

import esa.mo.apigen.importers.xml.XmlImporter;
import esa.mo.apigen.link.Linker;
import esa.mo.apigen.model.Area;
import esa.mo.apigen.model.MOModel;
import esa.mo.apigen.model.SourceRef;
import esa.mo.apigen.model.Specification;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * The new document generator held against the documents the existing one produces.
 * <p>
 * Every specification of a set is loaded, every document is written, and each part of each
 * package is compared byte for byte with the captured one.
 */
public class DocxGeneratorGoldenTest {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    /**
     * How many parts of each document may still differ, keyed by set and document.
     * <p>
     * Empty, and meant to stay that way. It held two entries while the existing generator
     * documented an error under another area's number; that was fixed rather than
     * reproduced, and the baseline re-captured.
     */
    private static final Map<String, Integer> REMAINING = new HashMap<String, Integer>();

    private static int matching = 0;

    private static int expected = 0;

    @AfterClass
    public static void reportProgress() {
        if (expected != 0) {
            System.out.println("Golden documents: " + matching + " of " + expected
                    + " part(s) are identical to the reference output");
        }
    }

    @Test
    public void prototypes() throws Exception {
        check("prototypes");
    }

    @Test
    public void standards() throws Exception {
        check("standards");
    }

    /**
     * Writes every document of a set and compares it with the captured one.
     */
    private void check(String set) throws Exception {
        File root = repoRoot();
        Assume.assumeNotNull("not inside the repository", root);
        File specs = new File(root, "xml-service-specifications/xml-ccsds-mo-" + set
                + "/src/main/resources/xml");
        File baseline = new File(root, "api-generator/golden/baseline/docx/" + set);
        Assume.assumeTrue("baseline not captured; run api-generator/golden/golden.sh capture",
                specs.isDirectory() && baseline.isDirectory());

        Path out = Files.createTempDirectory("apigen-docx-" + set);
        try {
            generate(specs, out);
            List<String> differences = new ArrayList<String>();

            for (File document : sorted(baseline)) {
                int differing = compare(document, new File(out.toFile(), document.getName()),
                        differences);
                String key = set + "/" + document.getName();
                int budget = REMAINING.containsKey(key) ? REMAINING.get(key) : 0;
                if (differing != budget) {
                    fail(document.getName() + ": " + differing + " part(s) differ from the"
                            + " baseline, budget is " + budget + "\n    "
                            + join(differences));
                }
            }
        } finally {
            delete(out.toFile());
        }
    }

    /**
     * Loads every specification of a directory and writes a document for each.
     */
    private static void generate(File specs, Path out) throws Exception {
        MOModel model = new MOModel();
        List<Area> targets = new ArrayList<Area>();
        for (File file : sorted(specs)) {
            if (!file.getName().endsWith(".xml")) {
                continue;
            }
            Specification spec = read(file);
            model.add(spec);
            targets.addAll(spec.getAreas());
        }
        new Linker().link(model);
        new DocxGenerator().generate(model, targets, out);
    }

    /**
     * @return how many parts of the document differ from the captured one.
     */
    private static int compare(File captured, File written, List<String> differences)
            throws IOException {
        int differing = 0;
        for (File part : parts(captured)) {
            String relative = captured.toPath().relativize(part.toPath()).toString();
            File other = new File(written, relative);
            if (!other.isFile()) {
                differences.add("missing: " + captured.getName() + "/" + relative);
                differing++;
                continue;
            }
            if (!Arrays.equals(Files.readAllBytes(part.toPath()),
                    Files.readAllBytes(other.toPath()))) {
                differences.add("differs: " + captured.getName() + "/" + relative
                        + describe(part, other));
                differing++;
            }
        }
        matching += parts(captured).size() - differing;
        expected += parts(captured).size();
        return differing;
    }

    /**
     * @return the first line that differs, which is usually enough to say what changed.
     */
    private static String describe(File captured, File written) throws IOException {
        if (captured.getName().endsWith(".png")) {
            return " (image, " + captured.length() + " vs " + written.length() + " bytes)";
        }
        List<String> a = Arrays.asList(new String(Files.readAllBytes(captured.toPath()), UTF8)
                .split("\r?\n", -1));
        List<String> b = Arrays.asList(new String(Files.readAllBytes(written.toPath()), UTF8)
                .split("\r?\n", -1));
        for (int i = 0; i < Math.max(a.size(), b.size()); i++) {
            String x = i < a.size() ? a.get(i) : "<end of file>";
            String y = i < b.size() ? b.get(i) : "<end of file>";
            if (!x.equals(y)) {
                int cut = 160;
                return "\n      line " + (i + 1) + " expected: "
                        + x.substring(0, Math.min(cut, x.length()))
                        + "\n      line " + (i + 1) + "   actual: "
                        + y.substring(0, Math.min(cut, y.length()));
            }
        }
        return "";
    }

    private static List<File> parts(File document) {
        List<File> found = new ArrayList<File>();
        collect(document, found);
        java.util.Collections.sort(found);
        return found;
    }

    private static void collect(File dir, List<File> into) {
        File[] entries = dir.listFiles();
        if (entries == null) {
            return;
        }
        Arrays.sort(entries);
        for (File entry : entries) {
            if (entry.isDirectory()) {
                collect(entry, into);
            } else {
                into.add(entry);
            }
        }
    }

    private static List<File> sorted(File dir) {
        File[] entries = dir.listFiles();
        if (entries == null) {
            return new ArrayList<File>();
        }
        Arrays.sort(entries);
        return Arrays.asList(entries);
    }

    private static Specification read(File file) throws Exception {
        Reader in = new InputStreamReader(new FileInputStream(file), UTF8);
        try {
            return new XmlImporter().read(in, new SourceRef(file.getName(), file.getPath()));
        } finally {
            in.close();
        }
    }

    private static String join(List<String> lines) {
        StringBuilder buf = new StringBuilder();
        for (String line : lines) {
            buf.append(line).append("\n    ");
        }
        return buf.toString();
    }

    private static File repoRoot() {
        File here = new File(System.getProperty("user.dir")).getAbsoluteFile();
        while (here != null) {
            if (new File(here, "xml-service-specifications").isDirectory()) {
                return here;
            }
            here = here.getParentFile();
        }
        return null;
    }

    private static void delete(File file) {
        File[] entries = file.listFiles();
        if (entries != null) {
            for (File entry : entries) {
                delete(entry);
            }
        }
        file.delete();
    }
}
