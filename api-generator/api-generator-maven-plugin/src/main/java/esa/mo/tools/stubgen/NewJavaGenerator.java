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
package esa.mo.tools.stubgen;

import esa.mo.apigen.generators.java.JavaGenerator;
import esa.mo.apigen.importers.xml.XmlImporter;
import esa.mo.apigen.link.Linker;
import esa.mo.apigen.model.Area;
import esa.mo.apigen.model.MOModel;
import esa.mo.apigen.model.SourceRef;
import esa.mo.apigen.model.Specification;
import esa.mo.xsd.util.XmlSpecification;
import esa.mo.xsd.util.XsdSpecification;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.maven.plugin.logging.Log;

/**
 * Reaches the new generator library through the interface this plugin expects.
 * <p>
 * Temporary, for running the two generators side by side through the same build: it answers
 * to a name of its own so that nothing has to be taken off the classpath to try it. At v15.0
 * the library replaces the old generator outright and this goes away with the interface.
 * <p>
 * The library reads XML itself rather than being handed a parsed tree, so what arrives here
 * is used for the file it names and nothing else. Generation is deferred to {@code close}:
 * the library links every specification before generating any of them, which is what a
 * reference across two files needs.
 */
public class NewJavaGenerator implements Generator {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private final Log logger;

    /**
     * Every file handed over, by path, so that a file named twice is read once.
     */
    private final Map<String, File> loaded = new LinkedHashMap<String, File>();

    /**
     * The files to generate, which are the ones named by {@code generate} rather than by
     * {@code loadXML}.
     */
    private final List<File> targets = new ArrayList<File>();

    public NewJavaGenerator(Log logger) {
        this.logger = logger;
    }

    @Override
    public String getShortName() {
        return "Java2";
    }

    @Override
    public String getDescription() {
        return "Generates a Java language mapping, using the new generator library.";
    }

    @Override
    public void init(String destinationFolderName, boolean generateStructures,
            boolean generateCOM, Map<String, String> packageBindings,
            Map<String, String> extraProperties) throws IOException {
        // The library takes no configuration: everything the old generator could be told
        // here it now always does.
    }

    @Override
    public void postinit(String destinationFolderName, boolean generateStructures,
            boolean generateCOM, Map<String, String> packageBindings,
            Map<String, String> extraProperties) throws IOException {
    }

    @Override
    public void loadXML(XmlSpecification xml) {
        remember(xml);
    }

    @Override
    public void loadXSD(XsdSpecification xsd) {
        // The library has no use for XML Schema types.
    }

    @Override
    public void generate(String destinationFolderName, XmlSpecification xml,
            javax.xml.bind.JAXBElement rootNode) {
        File file = remember(xml);
        if (!targets.contains(file)) {
            targets.add(file);
        }
    }

    @Override
    public void close(String destinationFolderName) throws IOException {
        if (targets.isEmpty()) {
            return;
        }

        MOModel model = new MOModel();
        List<Area> areas = new ArrayList<Area>();

        for (Map.Entry<String, File> entry : loaded.entrySet()) {
            Specification specification = read(entry.getValue());
            model.add(specification);
            if (targets.contains(entry.getValue())) {
                areas.addAll(specification.getAreas());
            }
        }

        // Timed and reported the way the existing generator reports itself, so the two can
        // be compared from the same build log.
        long started = System.currentTimeMillis();
        new Linker().link(model);
        new JavaGenerator().generate(model, areas, Paths.get(destinationFolderName));
        logger.info("Processed all Areas in " + (System.currentTimeMillis() - started)
                + " ms (" + areas.size() + " area(s), new generator library)");
    }

    @Override
    public void reset() {
        loaded.clear();
        targets.clear();
    }

    private File remember(XmlSpecification xml) {
        File file = xml.getFile();
        loaded.put(file.getAbsolutePath(), file);
        return file;
    }

    private static Specification read(File file) throws IOException {
        Reader in = new InputStreamReader(new FileInputStream(file), UTF8);
        try {
            return new XmlImporter().read(in, new SourceRef(file.getName(), file.getPath()));
        } catch (esa.mo.apigen.importers.ImportException ex) {
            // The build has no way to report an unreadable specification other than as an
            // IO failure, which is what the interface allows for.
            throw new IOException("Could not read " + file.getPath() + ": " + ex.getMessage(), ex);
        } finally {
            in.close();
        }
    }
}
