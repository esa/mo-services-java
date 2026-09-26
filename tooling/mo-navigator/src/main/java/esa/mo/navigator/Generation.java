/* ----------------------------------------------------------------------------
 * Copyright (C) 2026      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : ESA MO Navigator
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
package esa.mo.navigator;

import esa.mo.apigen.generators.Generator;
import esa.mo.apigen.generators.docx.DocxGenerator;
import esa.mo.apigen.importers.ImportException;
import esa.mo.apigen.importers.xml.XmlImporter;
import esa.mo.apigen.link.Linker;
import esa.mo.apigen.model.Area;
import esa.mo.apigen.model.MOModel;
import esa.mo.apigen.model.SourceRef;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Runs a generator of the api-generator library over a directory of XML specifications.
 */
public final class Generation {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    /**
     * The part a Word document names its content types in, which goes first in the zip.
     */
    private static final String CONTENT_TYPES = "[Content_Types].xml";

    private Generation() {
    }

    /**
     * Reads every file directly in the source directory, links them into one model, and
     * generates every area of it.
     * <p>
     * All files are read before any is generated, so that a reference from one area to a
     * type of another resolves whichever file is read first.
     *
     * @param generator The generator to run.
     * @param sourceDir The directory holding the XML specifications.
     * @param destDir The directory to write into.
     * @throws IOException if a file cannot be read or the output cannot be written.
     */
    public static void generate(Generator generator, File sourceDir, File destDir)
            throws IOException {
        MOModel model = load(sourceDir);
        generator.generate(model, model.getAreas(), destDir.toPath());
    }

    /**
     * Generates the Word document of every area in the source directory, as one .docx file
     * per area.
     * <p>
     * The generator writes the parts of a document into a directory; each is zipped into a
     * .docx beside it, and the directory removed.
     *
     * @param sourceDir The directory holding the XML specifications.
     * @param destDir The directory to write into.
     * @throws IOException if a file cannot be read or the output cannot be written.
     */
    public static void generateDocuments(File sourceDir, File destDir) throws IOException {
        MOModel model = load(sourceDir);
        new DocxGenerator().generate(model, model.getAreas(), destDir.toPath());

        for (Area area : model.getAreas()) {
            String name = DocxGenerator.documentNameOf(area);
            File parts = new File(destDir, name);
            zip(parts, new File(destDir, name + ".docx"));
            delete(parts);
        }
    }

    private static MOModel load(File sourceDir) throws IOException {
        File[] files = sourceDir.listFiles();
        if (files == null) {
            throw new IOException("Not a directory: " + sourceDir.getPath());
        }
        Arrays.sort(files);

        MOModel model = new MOModel();
        for (File file : files) {
            if (file.isFile()) {
                model.add(read(file));
            }
        }

        new Linker().link(model);
        return model;
    }

    private static esa.mo.apigen.model.Specification read(File file) throws IOException {
        Reader in = new InputStreamReader(new FileInputStream(file), UTF8);
        try {
            return new XmlImporter().read(in, new SourceRef(file.getName(), file.getPath()));
        } catch (ImportException ex) {
            throw new IOException("Could not read " + file.getPath() + ": " + ex.getMessage(), ex);
        } finally {
            in.close();
        }
    }

    private static void zip(File dir, File target) throws IOException {
        List<String> names = new ArrayList<String>();
        collect(dir, "", names);
        if (names.remove(CONTENT_TYPES)) {
            names.add(0, CONTENT_TYPES);
        }

        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(target));
        try {
            byte[] buf = new byte[8192];
            for (String name : names) {
                out.putNextEntry(new ZipEntry(name));
                InputStream in = new FileInputStream(new File(dir, name));
                try {
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                } finally {
                    in.close();
                }
                out.closeEntry();
            }
        } finally {
            out.close();
        }
    }

    /**
     * Adds the path of every file under the directory, relative to the root and separated
     * by '/', in name order.
     */
    private static void collect(File dir, String prefix, List<String> names) {
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        Arrays.sort(files);
        for (File file : files) {
            if (file.isDirectory()) {
                collect(file, prefix + file.getName() + "/", names);
            } else {
                names.add(prefix + file.getName());
            }
        }
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
}
