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
package esa.mo.apigen.importers.xml;

import esa.mo.apigen.importers.ImportException;
import esa.mo.apigen.model.SourceRef;
import esa.mo.apigen.model.Specification;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Locates the specifications in the repository, so the importer can be tested against the
 * documents it actually has to read rather than against fixtures written to suit it.
 */
public final class Corpus {

    private Corpus() {
    }

    /**
     * @return the directory holding a set of specifications, or null if the repository
     * layout cannot be found from here.
     */
    public static File dir(String set) {
        File here = new File(System.getProperty("user.dir")).getAbsoluteFile();
        while (here != null) {
            File candidate = new File(here,
                    "xml-service-specifications/xml-ccsds-mo-" + set + "/src/main/resources/xml");
            if (candidate.isDirectory()) {
                return candidate;
            }
            here = here.getParentFile();
        }
        return null;
    }

    public static List<File> files(String set) {
        File dir = dir(set);
        if (dir == null) {
            return Collections.emptyList();
        }
        File[] found = dir.listFiles();
        if (found == null) {
            return Collections.emptyList();
        }
        List<File> xml = new ArrayList<File>();
        for (File f : found) {
            if (f.isFile() && f.getName().endsWith(".xml")) {
                xml.add(f);
            }
        }
        Collections.sort(xml);
        return xml;
    }

    /**
     * @return every specification in the repository, prototypes and standards together.
     */
    public static List<File> all() {
        List<File> all = new ArrayList<File>();
        all.addAll(files("prototypes"));
        all.addAll(files("standards"));
        return all;
    }

    public static Specification read(File file) throws ImportException, IOException {
        Reader in = new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8"));
        try {
            return new XmlImporter().read(in, new SourceRef(file.getName(), file.getPath()));
        } finally {
            in.close();
        }
    }

    public static File file(String name) {
        for (File f : all()) {
            if (f.getName().equals(name)) {
                return f;
            }
        }
        return null;
    }

    public static List<String> names(List<File> files) {
        List<String> names = new ArrayList<String>();
        for (File f : files) {
            names.add(f.getName());
        }
        return names;
    }

    static {
        // keep Arrays imported for readability of failure messages in the tests
        Arrays.asList();
    }
}
