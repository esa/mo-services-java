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
import java.util.Collections;
import java.util.List;

/**
 * Reaches the output of the existing generators, captured by the Phase 0 harness, so the
 * new generator can be held against it file by file.
 * <p>
 * The baseline is committed, beside the tests that read it, so that a change to what the
 * generators produce shows up as a diff rather than only as a failing assertion. Anything
 * here still degrades to a skipped test if it is absent, which is what happens to someone
 * who has cleared it to re-capture.
 */
public final class GoldenTree {

    /**
     * Where the captured output of the existing generators lives, relative to the root of
     * the repository. {@code api-generator/golden/golden.sh} writes it here.
     */
    static final String BASELINE = "testbeds/testbed-api-generator/baseline";

    private GoldenTree() {
    }

    public static File repoRoot() {
        File here = new File(System.getProperty("user.dir")).getAbsoluteFile();
        while (here != null) {
            if (new File(here, "xml-service-specifications").isDirectory()) {
                return here;
            }
            here = here.getParentFile();
        }
        return null;
    }

    /**
     * @return the captured output for a module, or null if the baseline has not been taken.
     */
    public static File baselineFor(String module) {
        File root = repoRoot();
        if (root == null) {
            return null;
        }
        File dir = new File(root, BASELINE + "/java/" + module);
        return dir.isDirectory() ? dir : null;
    }

    public static File spec(String fileName) {
        File root = repoRoot();
        if (root == null) {
            return null;
        }
        for (String set : new String[]{"prototypes", "standards"}) {
            File f = new File(root, "xml-service-specifications/xml-ccsds-mo-" + set
                    + "/src/main/resources/xml/" + fileName);
            if (f.isFile()) {
                return f;
            }
        }
        return null;
    }

    public static Specification read(File file) throws Exception {
        Reader in = new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8"));
        try {
            return new XmlImporter().read(in, new SourceRef(file.getName(), file.getPath()));
        } finally {
            in.close();
        }
    }

    /**
     * Builds a model the way an api module's build does: one target specification, plus
     * the references it names.
     *
     * @param target The specification to generate.
     * @param references The specifications loaded only so that references resolve.
     * @return the target's areas, with the model behind them linked.
     */
    public static Result load(String target, String... references) throws Exception {
        MOModel model = new MOModel();
        for (String reference : references) {
            model.add(read(spec(reference)));
        }
        Specification spec = read(spec(target));
        model.add(spec);
        new Linker().link(model);
        return new Result(model, spec.getAreas());
    }

    public static final class Result {

        final MOModel model;
        final List<Area> targets;

        private Result(MOModel model, List<Area> targets) {
            this.model = model;
            this.targets = targets;
        }
    }

    /**
     * Compares generated files against the baseline, considering only the files the new
     * generator claims to produce. Everything it does not yet write is simply not looked
     * at - the point is that what it does write is right, not that it is complete.
     *
     * @param generated The directory just written.
     * @param baseline The captured output.
     * @return a description of every difference, empty if there are none.
     */
    public static List<String> compare(Path generated, File baseline) throws IOException {
        List<String> differences = new ArrayList<String>();
        List<Path> produced = new ArrayList<Path>();
        collect(generated, produced);
        Collections.sort(produced);

        for (Path file : produced) {
            String relative = generated.relativize(file).toString();
            File expected = new File(baseline, relative);
            if (!expected.isFile()) {
                differences.add("only generated: " + relative);
                continue;
            }
            String actualText = new String(Files.readAllBytes(file), Charset.forName("UTF-8"));
            String expectedText = new String(
                    Files.readAllBytes(expected.toPath()), Charset.forName("UTF-8"));
            if (!expectedText.equals(actualText)) {
                differences.add("differs: " + relative + describe(expectedText, actualText));
            }
        }
        return differences;
    }

    private static String describe(String expected, String actual) {
        List<String> a = Arrays.asList(expected.split("\n", -1));
        List<String> b = Arrays.asList(actual.split("\n", -1));
        for (int i = 0; i < Math.max(a.size(), b.size()); i++) {
            String x = i < a.size() ? a.get(i) : "<end of file>";
            String y = i < b.size() ? b.get(i) : "<end of file>";
            if (!x.equals(y)) {
                return "\n      line " + (i + 1) + " expected: " + x + "\n"
                        + "      line " + (i + 1) + "   actual: " + y;
            }
        }
        return "";
    }

    private static void collect(Path dir, List<Path> into) throws IOException {
        File[] entries = dir.toFile().listFiles();
        if (entries == null) {
            return;
        }
        for (File entry : entries) {
            if (entry.isDirectory()) {
                collect(entry.toPath(), into);
            } else {
                into.add(entry.toPath());
            }
        }
    }

    /**
     * @return how many files were just written, for reporting progress.
     */
    public static int countGenerated(Path generated) throws IOException {
        List<Path> all = new ArrayList<Path>();
        collect(generated, all);
        return all.size();
    }

    /**
     * @return how many files the baseline holds, for reporting progress.
     */
    public static int countBaseline(File baseline) {
        List<Path> all = new ArrayList<Path>();
        try {
            collect(baseline.toPath(), all);
        } catch (IOException ex) {
            return 0;
        }
        return all.size();
    }
}
