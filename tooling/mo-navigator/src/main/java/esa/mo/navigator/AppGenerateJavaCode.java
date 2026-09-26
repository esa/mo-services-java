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

import esa.mo.apigen.generators.java.JavaGenerator;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Application that generates Java code from MO service specifications.
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
        String sourFolder = DEFAULT_XMLS_DIR;
        String destFolder = DEFAULT_JAVA_API_DIR;

        try {
            Generation.generate(new JavaGenerator(), new File(sourFolder), new File(destFolder));

            timestamp = System.currentTimeMillis() - timestamp;
            Logger.getLogger(AppGenerateJavaCode.class.getName()).log(Level.INFO,
                    "Success! Generated the code in " + timestamp + " miliseconds!");
        } catch (IOException ex) {
            Logger.getLogger(AppGenerateJavaCode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
