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
package esa.mo.apigen.validation;

import esa.mo.apigen.importers.xml.XmlImporter;
import esa.mo.apigen.link.Linker;
import esa.mo.apigen.model.MOModel;
import esa.mo.apigen.model.SourceRef;
import esa.mo.apigen.model.Specification;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

/**
 * The validator run over the specifications in this repository.
 * <p>
 * Every specification is loaded together, as a build does, so that references across
 * areas resolve. What is left is either a defect in a specification or a defect in a rule,
 * and the difference is worth knowing: this test exists because the validator was for a
 * while reporting a hundred problems that were all its own, which buried the two that
 * were real.
 */
public class ValidatorCorpusTest {

    private static final String NL = System.getProperty("line.separator");

    /**
     * The one reference that names a type no specification declares: a field of the
     * Mission Data Product Distribution area names {@code MC::Check.CheckSummary}, and the
     * Check service declares {@code CheckLinkSummary} and {@code CheckResultSummary}. It
     * is left as it is until someone decides what it was meant to say, and pinned here so
     * that fixing it makes this test fail rather than passing unnoticed.
     */
    private static final String KNOWN_DANGLING_TYPE = "MC.1::Check.CheckSummary";

    /**
     * Returns true if the issue says a specification is being built against a MAL of
     * another generation.
     * <p>
     * A specification declares its generation by its namespace and names its errors to
     * match: v001 raises {@code UNKNOWN}, v003 raises {@code Unknown}, and the two also
     * carry different numbers. Paired with the MAL of its own generation a specification
     * resolves completely; the six v001 specifications are built against MAL v3 because
     * that is the only MAL with a Java API, and this says so once for each of them.
     */
    private static boolean isMalGeneration(ValidationIssue issue) {
        return "specification.malGeneration".equals(issue.getRule());
    }

    /**
     * What each specification is checked alongside.
     * <p>
     * The corpus cannot be loaded as one set. It holds two versions of the MAL area and
     * three areas called MC, and a reference carries no version, so loading all of them
     * together makes almost every reference ambiguous. A build never does that: it loads
     * one target and the specifications it names. These are those sets.
     */
    private static final String[][] SETS = {
        {"area001-v003-MAL.xml"},
        {"area002-v001-COM.xml", "area001-v003-MAL.xml"},
        {"area003-v001-Common.xml", "area001-v003-MAL.xml", "area002-v001-COM.xml"},
        {"area004-v001-Monitor-and-Control.xml", "area001-v003-MAL.xml", "area002-v001-COM.xml"},
        {"area004-v002-Monitor-and-Control.xml", "area001-v003-MAL.xml"},
        {"area005-v001-Mission-Planning-and-Scheduling.xml", "area001-v003-MAL.xml"},
        {"area007-v001-Software-Management.xml", "area001-v003-MAL.xml", "area002-v001-COM.xml"},
        {"area009-v001-Mission-Product-Distribution.xml", "area001-v003-MAL.xml"},
        {"area020-v001-File-Management.xml", "area001-v003-MAL.xml"},
        {"area051-v001-Mission-Data-Product.xml", "area001-v003-MAL.xml", "area002-v001-COM.xml",
            "area004-v001-Monitor-and-Control.xml"},
        {"area052-v001-Mission-Data-Product-Distribution.xml", "area001-v003-MAL.xml",
            "area002-v001-COM.xml", "area004-v001-Monitor-and-Control.xml"},
    };

    @Before
    public void findCorpus() {
        Assume.assumeNotNull(dir());
    }

    private static File dir() {
        File here = new File(System.getProperty("user.dir")).getAbsoluteFile();
        while (here != null) {
            File d = new File(here,
                    "xml-service-specifications/xml-ccsds-mo-prototypes/src/main/resources/xml");
            if (d.isDirectory()) {
                return d;
            }
            here = here.getParentFile();
        }
        return null;
    }

    private static MOModel load(String[] set) throws Exception {
        MOModel model = new MOModel();
        // References first, then the target, as a build loads them.
        for (int i = set.length - 1; i >= 0; i--) {
            File file = new File(dir(), set[i]);
            if (!file.isFile()) {
                return null;
            }
            Reader in = new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8"));
            try {
                model.add(new XmlImporter().read(in, new SourceRef(file.getName(), file.getPath())));
            } finally {
                in.close();
            }
        }
        new Linker().link(model);
        return model;
    }

    /**
     * Every reported error is one of the two known dangling references, and each is
     * reported as often as it occurs.
     */
    @Test
    public void theOnlyErrorIsTheKnownDanglingType() throws Exception {
        List<String> unexpected = new ArrayList<String>();
        int dangling = 0;

        for (String[] set : SETS) {
            MOModel model = load(set);
            if (model == null) {
                continue;
            }
            for (ValidationIssue issue : new Validator().validate(model).getIssues(Severity.ERROR)) {
                if (KNOWN_DANGLING_TYPE.equals(named(issue.getMessage()))) {
                    dangling++;
                } else {
                    unexpected.add(set[0] + ": " + issue);
                }
            }
        }

        if (!unexpected.isEmpty()) {
            StringBuilder message = new StringBuilder(unexpected.size()
                    + " error(s) that are not the known dangling type:" + NL);
            for (String line : unexpected) {
                message.append("    ").append(line).append(NL);
            }
            fail(message.toString());
        }
        assertEquals("the known dangling type should still be reported", 1, dangling);
    }

    /**
     * Being built against another generation of the MAL is reported once for the
     * specification, not once for every name that does not match, and the only error
     * references left unresolved are the two that no generation explains.
     */
    @Test
    public void theMalGenerationGapIsReportedOncePerSpecification() throws Exception {
        java.util.Set<String> generationGaps = new java.util.TreeSet<String>();
        java.util.Set<String> unexplained = new java.util.TreeSet<String>();

        for (String[] set : SETS) {
            MOModel model = load(set);
            if (model == null) {
                continue;
            }
            ValidationResult result = new Validator().validate(model);
            for (ValidationIssue issue : result.getIssues(Severity.ERROR)) {
                assertFalse("an unresolved error should be a warning: " + issue,
                        "error.unresolved".equals(issue.getRule()));
            }
            for (ValidationIssue issue : result.getIssues(Severity.WARNING)) {
                if (isMalGeneration(issue)) {
                    generationGaps.add(String.valueOf(issue.getLocation().getSource()));
                } else if ("error.unresolved".equals(issue.getRule())) {
                    unexplained.add(named(issue.getMessage()));
                }
            }
        }

        // Every specification still on the v001 schema, each said once. That includes
        // File Management, which raises no MAL error at all: the gap is a fact about the
        // specification, not about whether it happens to name something that differs.
        assertEquals("one warning per specification of the older generation", 7,
                generationGaps.size());

        // FM::OTHER_ERROR names nothing at all - File Management declares OTHER.
        // MAL::AUTHENTICATION_FAIL is the one MAL error v003 renamed by word rather than
        // by spelling, to "Authentication Failed", and so the one whose two generations
        // would not even yield the same Java class name.
        java.util.Set<String> expected = new java.util.TreeSet<String>(
                java.util.Arrays.asList("FM.1::OTHER_ERROR", "MAL.3::AUTHENTICATION_FAIL"));
        assertEquals("only these are left unresolved", expected, unexplained);
    }

    /**
     * A specification paired with the MAL of its own generation resolves completely.
     */
    @Test
    public void eachGenerationResolvesAgainstItsOwnMal() throws Exception {
        MOModel v001 = load(new String[]{"area004-v001-Monitor-and-Control.xml",
            "area002-v001-COM.xml", "area001-v001-MAL.xml"});
        MOModel v003 = load(new String[]{"area004-v002-Monitor-and-Control.xml",
            "area001-v003-MAL.xml"});
        Assume.assumeNotNull(v001, v003);

        assertEquals("MC v1 with MAL v1 should resolve completely",
                "", unresolved(new Validator().validate(v001)));
        assertEquals("MC v2 with MAL v3 should resolve completely",
                "", unresolved(new Validator().validate(v003)));
    }

    /**
     * @return every issue that is about resolution, which is what this test is about.
     * MC v001 also reports eight diagrams that nothing renders any more - true, and
     * deliberate (design section 8.3), but not a failure to resolve anything.
     */
    private static String unresolved(esa.mo.apigen.validation.ValidationResult result) {
        StringBuilder buf = new StringBuilder();
        for (esa.mo.apigen.validation.ValidationIssue issue : result.getIssues()) {
            if (!"diagram.notRendered".equals(issue.getRule())) {
                buf.append(issue).append('\n');
            }
        }
        return buf.toString();
    }

    /**
     * An area referring to a type of another area must resolve, which is only true
     * because every specification is loaded before anything is checked.
     */
    @Test
    public void referencesAcrossAreasResolve() throws Exception {
        for (String[] set : SETS) {
            MOModel model = load(set);
            if (model == null) {
                continue;
            }
            for (ValidationIssue issue : new Validator().validate(model).getIssues(Severity.ERROR)) {
                assertFalse(set[0] + ": no reference should be left unlinked: " + issue,
                        "type.unlinked".equals(issue.getRule())
                        || "error.unlinked".equals(issue.getRule()));
            }
        }
    }

    private static String named(String message) {
        // "Cannot resolve type X used by ..." / "Cannot resolve error X raised by ..."
        for (String prefix : new String[]{"Cannot resolve type ", "Cannot resolve error "}) {
            int at = message.indexOf(prefix);
            if (at >= 0) {
                String rest = message.substring(at + prefix.length());
                int end = rest.indexOf(' ');
                return end < 0 ? rest : rest.substring(0, end);
            }
        }
        return null;
    }
}
