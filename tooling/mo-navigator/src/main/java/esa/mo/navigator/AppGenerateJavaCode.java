/* ----------------------------------------------------------------------------
 * Copyright (C) 2023      European Space Agency
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

import esa.mo.tools.stubgen.GeneratorJava;
import esa.mo.xsd.util.XmlHelper;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Cesar Coelho
 */
public class AppGenerateJavaCode {

    private final static String DEFAULT_XMLS_DIR = "_xmls";
    private final static String DEFAULT_JAVA_API_DIR = "_java";

    /**
     * The main method.
     *
     * @param args The arguments
     */
    public static void main(String[] args) {
        long timestamp = System.currentTimeMillis();
        org.apache.maven.plugin.logging.SystemStreamLog logger = new org.apache.maven.plugin.logging.SystemStreamLog();
        GeneratorJava generator = new GeneratorJava(logger);
        String sourFolder = DEFAULT_XMLS_DIR;
        String destFolder = DEFAULT_JAVA_API_DIR;
        HashMap<String, String> packageBindings = new HashMap();
        HashMap<String, String> extraProperties = new HashMap();

        try {
            generator.init(destFolder, true, true, packageBindings, extraProperties);
            File xmlRefDirectory = new File(sourFolder);
            List<Map.Entry<esa.mo.xsd.SpecificationType, XmlHelper.XmlSpecification>> specs = XmlHelper.loadSpecifications(xmlRefDirectory);

            // now generator from each specification
            for (Map.Entry<esa.mo.xsd.SpecificationType, XmlHelper.XmlSpecification> spec : specs) {
                try {
                    generator.preProcess(spec.getKey());
                    generator.compile(destFolder, spec.getKey(), spec.getValue().rootElement);
                } catch (Exception ex) {
                    Logger.getLogger(AppGenerateJavaCode.class.getName()).log(Level.INFO,
                            "Exception thrown during the processing of XML file: "
                            + spec.getValue().file.getPath(), ex);
                }
            }

            JButton openButton = new JButton("Open Folder");
            openButton.addActionListener((new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Desktop.getDesktop().open(new File(destFolder));
                    } catch (IOException ex) {
                        Logger.getLogger(AppGenerateJavaCode.class.getName()).log(
                                Level.SEVERE, "The folder could not be opened!", ex);
                    }
                }
            }));
            timestamp = System.currentTimeMillis() - timestamp;
            Logger.getLogger(AppGenerateJavaCode.class.getName()).log(Level.INFO,
                    "Success! Generated the code in " + timestamp + " miliseconds!");
        } catch (IOException | JAXBException ex) {
            Logger.getLogger(AppGenerateJavaCode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
