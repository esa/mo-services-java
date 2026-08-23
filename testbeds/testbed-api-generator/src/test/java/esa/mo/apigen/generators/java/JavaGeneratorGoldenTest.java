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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.Test;

/**
 * The new Java generator held against the output of the existing one.
 * <p>
 * Only the files it claims to produce are compared: the point at this stage is that what
 * it writes is right, not that it writes everything. As categories are added they are
 * added here, and the count in each test says how far along the module is.
 */
public class JavaGeneratorGoldenTest {

    /**
     * How many files each module still renders differently from the reference output.
     * <p>
     * A budget rather than a pass mark: it must only ever go down, and the test fails both
     * when a module regresses and when a budget is left standing after the difference has
     * been fixed. Zero everywhere is the end of Phase 3's structures work.
     */
    private static final java.util.Map<String, Integer> REMAINING
            = new java.util.HashMap<String, Integer>();

    static {
        REMAINING.put("api-area001-v003-mal", 0);
        REMAINING.put("api-area002-v001-com", 0);
        REMAINING.put("api-area003-v001-common", 0);
        REMAINING.put("api-area004-v001-mc", 0);
        REMAINING.put("api-area004-v002-mc", 0);
        REMAINING.put("api-area005-v001-mps", 0);
        REMAINING.put("api-area009-v001-mpd", 0);
    }

    /**
     * How many files the new generator writes byte for byte as the old one did, and how
     * many the old one wrote in all. Printed once the modules have all run, so that the
     * distance still to go is visible without reading every test.
     */
    private static int matching = 0;

    private static int expected = 0;

    @AfterClass
    public static void reportProgress() {
        if (expected != 0) {
            System.out.println("Golden tree: " + matching + " of " + expected
                    + " file(s) are identical to the reference output");
        }
    }

    private void check(String module, String target, String... references) throws Exception {
        File baseline = GoldenTree.baselineFor(module);
        Assume.assumeNotNull("baseline not captured; run api-generator/golden/golden.sh capture",
                baseline);
        Assume.assumeNotNull(GoldenTree.spec(target));

        GoldenTree.Result loaded = GoldenTree.load(target, references);
        Path out = Files.createTempDirectory("apigen-" + module);
        try {
            new JavaGenerator().generate(loaded.model, loaded.targets, out);
            List<String> differences = GoldenTree.compare(out, baseline);
            int budget = REMAINING.containsKey(module) ? REMAINING.get(module) : 0;
            int differing = countDifferingFiles(differences);
            matching += GoldenTree.countGenerated(out) - differing;
            expected += GoldenTree.countBaseline(baseline);
            if (differing > budget) {
                StringBuilder message = new StringBuilder(module + ": " + differing
                        + " file(s) differ from the baseline, budget is " + budget + "\n");
                for (String difference : differences) {
                    message.append("    ").append(difference).append('\n');
                }
                fail(message.toString());
            }
            assertEquals(module + ": only " + differing + " file(s) now differ - lower the"
                    + " budget in REMAINING to " + differing, budget, differing);
        } finally {
            delete(out.toFile());
        }
    }

    private static int countDifferingFiles(List<String> differences) {
        int count = 0;
        for (String difference : differences) {
            if (difference.startsWith("differs: ") || difference.startsWith("only generated: ")) {
                count++;
            }
        }
        return count;
    }

    private static void delete(File file) {
        File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                delete(child);
            }
        }
        file.delete();
    }

    @Test
    public void mpd() throws Exception {
        check("api-area009-v001-mpd", "area009-v001-Mission-Product-Distribution.xml",
                "area001-v003-MAL.xml");
    }

    @Test
    public void mal() throws Exception {
        check("api-area001-v003-mal", "area001-v003-MAL.xml");
    }

    /**
     * COM names no references of its own, but its pom sets {@code ref-skip} to false and
     * so inherits the parent's default of MAL v3 - which it needs, since its fields are
     * typed with MAL attributes.
     */
    @Test
    public void com() throws Exception {
        check("api-area002-v001-com", "area002-v001-COM.xml", "area001-v003-MAL.xml");
    }

    @Test
    public void common() throws Exception {
        check("api-area003-v001-common", "area003-v001-Common.xml",
                "area001-v003-MAL.xml", "area002-v001-COM.xml");
    }

    @Test
    public void monitorAndControlV1() throws Exception {
        check("api-area004-v001-mc", "area004-v001-Monitor-and-Control.xml",
                "area001-v003-MAL.xml", "area002-v001-COM.xml");
    }

    @Test
    public void monitorAndControlV2() throws Exception {
        check("api-area004-v002-mc", "area004-v002-Monitor-and-Control.xml",
                "area001-v003-MAL.xml");
    }

    @Test
    public void missionPlanningAndScheduling() throws Exception {
        check("api-area005-v001-mps", "area005-v001-Mission-Planning-and-Scheduling.xml",
                "area001-v003-MAL.xml");
    }
}
