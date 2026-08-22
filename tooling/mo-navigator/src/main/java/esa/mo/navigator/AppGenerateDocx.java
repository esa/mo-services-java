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

import esa.mo.tools.stubgen.GeneratorDocx;
import esa.mo.xsd.util.XmlHelper;
import esa.mo.xsd.util.XmlSpecification;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 * Headless CLI for generating Word (.docx) documents from MO XML service
 * specifications. Mirrors the "Generate Documents" action in the MO Navigator
 * GUI but runs without a display, making it suitable for CI pipelines.
 *
 * <p>Usage: AppGenerateDocx [xmlDir [outputDir]]
 * <ul>
 *   <li>xmlDir    - directory containing MO XML files (default: {@value #DEFAULT_XMLS_DIR})</li>
 *   <li>outputDir - directory where .docx files are written (default: {@value #DEFAULT_DOCX_DIR})</li>
 * </ul>
 *
 * @author Cesar Coelho
 */
public class AppGenerateDocx {

    private static final String DEFAULT_XMLS_DIR = "_xmls";
    private static final String DEFAULT_DOCX_DIR = "_docx";

    /**
     * The main method.
     *
     * @param args Optional: args[0] = XML input directory, args[1] = output directory
     */
    public static void main(String[] args) {
        String sourFolder = args.length > 0 ? args[0] : DEFAULT_XMLS_DIR;
        String destFolder = args.length > 1 ? args[1] : DEFAULT_DOCX_DIR;

        long timestamp = System.currentTimeMillis();
        org.apache.maven.plugin.logging.SystemStreamLog logger
                = new org.apache.maven.plugin.logging.SystemStreamLog();
        GeneratorDocx generator = new GeneratorDocx(logger);
        HashMap<String, String> packageBindings = new HashMap<>();
        HashMap<String, String> extraProperties = new HashMap<>();

        try {
            generator.init(destFolder, true, true, packageBindings, extraProperties);
            File xmlRefDirectory = new File(sourFolder);
            List<XmlSpecification> specs = XmlHelper.loadSpecifications(xmlRefDirectory);

            // Every specification is loaded before any is generated. An area may name a
            // type of another area, and the type is only known once the area that
            // declares it has been loaded, so generating while still loading resolves a
            // reference only when the file that declares it happens to be read first.
            // The order files are read in is the order the file system lists them, which
            // is why the Mission Data Product area could not find a type of the COM area.
            for (XmlSpecification spec : specs) {
                try {
                    generator.loadXML(spec);
                } catch (Exception ex) {
                    Logger.getLogger(AppGenerateDocx.class.getName()).log(Level.SEVERE,
                            "Exception thrown while loading the XML file: "
                            + spec.getFile().getPath(), ex);
                }
            }

            for (XmlSpecification spec : specs) {
                try {
                    generator.generate(destFolder, spec, spec.getRootElement());
                } catch (Exception ex) {
                    Logger.getLogger(AppGenerateDocx.class.getName()).log(Level.SEVERE,
                            "Exception thrown during the processing of XML file: "
                            + spec.getFile().getPath(), ex);
                }
            }

            timestamp = System.currentTimeMillis() - timestamp;
            Logger.getLogger(AppGenerateDocx.class.getName()).log(Level.INFO,
                    "Success! Generated the documents in {0} milliseconds! Location: {1}",
                    new Object[]{timestamp, new File(destFolder).getAbsolutePath()});
        } catch (IOException | JAXBException ex) {
            Logger.getLogger(AppGenerateDocx.class.getName()).log(Level.SEVERE,
                    "Document generation failed!", ex);
            System.exit(1);
        }
    }
}
