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
package esa.mo.apigen.generators.xhtml;

import esa.mo.apigen.importers.xml.XmlImporter;
import esa.mo.apigen.link.Linker;
import esa.mo.apigen.model.Area;
import esa.mo.apigen.model.MOModel;
import esa.mo.apigen.model.SourceRef;
import esa.mo.apigen.model.Specification;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.Assume;
import org.junit.Test;
import org.xml.sax.InputSource;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * The XHTML generator over every specification in the repository.
 * <p>
 * There is nothing to hold this output against - nothing in the build has ever invoked the
 * generator it replaces, so the Phase 0 capture holds no page to compare with - so what is
 * checked here is what the output has to be true of rather than what it happens to say:
 * every area produces a page, every page parses, and every link on every page arrives
 * somewhere. Those three between them caught what the old generator got wrong.
 */
public class XhtmlGeneratorCorpusTest {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private static final Pattern ANCHOR = Pattern.compile("id=\"([^\"]*)\"");

    private static final Pattern LINK = Pattern.compile("(?:xlink:)?href=\"([^\"]*)\"");

    @Test
    public void everyAreaOfTheStandardsIsDescribed() throws Exception {
        check("standards");
    }

    @Test
    public void everyAreaOfThePrototypesIsDescribed() throws Exception {
        check("prototypes");
    }

    /**
     * Mission Planning declares six composites that contain themselves - a Plan names a
     * precursor Plan, an ActivityInstance holds its children - and the generator this
     * replaces expanded them until the stack ran out, leaving an empty file behind. The
     * page has to describe them.
     */
    @Test
    public void aTypeThatContainsItselfIsDescribed() throws Exception {
        File specs = specifications("prototypes");
        Assume.assumeTrue("specifications not present", specs.isDirectory());

        Path out = Files.createTempDirectory("apigen-xhtml-recursive");
        try {
            generate(specs, out);
            File page = new File(out.toFile(), "area005-v001-MPS.xhtml");
            assertTrue("Mission Planning produced no page at all", page.isFile());

            String text = new String(Files.readAllBytes(page.toPath()), UTF8);
            for (String type : Arrays.asList("Plan", "PlanRevision", "ActivityInstance",
                    "EventDefinition", "EventInstance")) {
                assertTrue(type + " is not described", text.contains("id=\"_" + type + "\"")
                        || text.contains("_" + type + "\""));
            }
            parse(page, text);
            assertWidthsAreBounded(page, text);
        } finally {
            delete(out.toFile());
        }
    }

    private void check(String set) throws Exception {
        File specs = specifications(set);
        Assume.assumeTrue("specifications not present", specs.isDirectory());

        Path out = Files.createTempDirectory("apigen-xhtml-" + set);
        try {
            List<Area> written = generate(specs, out);
            assertFalse("no area was generated", written.isEmpty());

            Map<String, Set<String>> anchors = new HashMap<String, Set<String>>();
            Map<String, String> pages = new HashMap<String, String>();
            for (Area area : written) {
                String name = XhtmlLink.pageOf(area);
                File page = new File(out.toFile(), name);
                assertTrue(area.getName() + " produced no page", page.isFile());

                String text = new String(Files.readAllBytes(page.toPath()), UTF8);
                parse(page, text);
                assertWidthsAreBounded(page, text);
                pages.put(name, text);
                anchors.put(name, anchorsOf(text));
            }
            // An area published twice under different versions gets a page of each: the two
            // say different things, and the name of the file has to keep them apart.
            assertTrue("MAL 1 and MAL 3 share a page",
                    pages.containsKey("area001-v001-MAL.xhtml")
                    && pages.containsKey("area001-v003-MAL.xhtml"));

            List<String> broken = new ArrayList<String>();
            for (Map.Entry<String, String> page : pages.entrySet()) {
                checkLinks(page.getKey(), page.getValue(), anchors, broken);
            }
            if (!broken.isEmpty()) {
                fail(broken.size() + " link(s) arrive nowhere:\n    " + join(broken));
            }
        } finally {
            delete(out.toFile());
        }
    }

    /**
     * Every link either stays on its page or names another page of the same set, and in
     * both cases names something that page defines.
     */
    private static void checkLinks(String from, String text,
            Map<String, Set<String>> anchors, List<String> broken) {
        Matcher matcher = LINK.matcher(text);
        while (matcher.find()) {
            String href = matcher.group(1);
            int hash = href.indexOf('#');
            if (hash < 0) {
                continue;
            }
            String page = hash == 0 ? from : href.substring(0, hash);
            String anchor = href.substring(hash + 1);
            if (!anchors.containsKey(page)) {
                broken.add(from + " -> " + href + " (no such page)");
            } else if (!anchors.get(page).contains(anchor)) {
                broken.add(from + " -> " + href + " (no such anchor)");
            }
        }
    }

    /**
     * A composite opened out inside another multiplies rather than adds, so a message of
     * nested composites can reach a size at which the drawing has stopped being one. The
     * generator stops opening types out before that happens, and this is what says it did.
     */
    private static void assertWidthsAreBounded(File page, String text) {
        Matcher matcher = Pattern.compile("<svg:svg version=\"1.1\" width=\"(\\d+)px\"")
                .matcher(text);
        int widest = 0;
        while (matcher.find()) {
            widest = Math.max(widest, Integer.parseInt(matcher.group(1)));
        }
        assertTrue(page.getName() + " holds a drawing " + widest + "px wide",
                widest <= 20000);
    }

    private static Set<String> anchorsOf(String text) {
        Set<String> found = new HashSet<String>();
        Matcher matcher = ANCHOR.matcher(text);
        while (matcher.find()) {
            found.add(matcher.group(1));
        }
        return found;
    }

    /**
     * A page is XHTML, so it has to parse as XML. The document type is not fetched - the
     * point is that the markup is well formed, and reaching the network to find that out
     * would make the test depend on being online.
     */
    private static void parse(File page, String text) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd",
                false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setEntityResolver(new org.xml.sax.EntityResolver() {
            @Override
            public InputSource resolveEntity(String publicId, String systemId) {
                return new InputSource(new StringReader(""));
            }
        });
        try {
            builder.parse(new ByteArrayInputStream(text.getBytes(UTF8)));
        } catch (Exception ex) {
            fail(page.getName() + " is not well-formed XHTML: " + ex.getMessage());
        }
    }

    private static List<Area> generate(File specs, Path out) throws Exception {
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
        new XhtmlGenerator().generate(model, targets, out);
        return targets;
    }

    private static File specifications(String set) {
        File root = repoRoot();
        Assume.assumeNotNull("not inside the repository", root);
        return new File(root, "xml-service-specifications/xml-ccsds-mo-" + set
                + "/src/main/resources/xml");
    }

    private static Specification read(File file) throws Exception {
        Reader in = new InputStreamReader(new FileInputStream(file), UTF8);
        try {
            return new XmlImporter().read(in, new SourceRef(file.getName(), file.getPath()));
        } finally {
            in.close();
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

    private static String join(List<String> lines) {
        StringBuilder buf = new StringBuilder();
        for (String line : lines.subList(0, Math.min(20, lines.size()))) {
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

    private static void delete(File file) throws IOException {
        File[] entries = file.listFiles();
        if (entries != null) {
            for (File entry : entries) {
                delete(entry);
            }
        }
        Files.deleteIfExists(file.toPath());
    }
}
