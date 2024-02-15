/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Service XML loaders
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
package esa.mo.xsd.util;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 * Small helper class to load in MO XML specifications via JAXB
 */
public class XmlHelper {

    public static final java.util.logging.Logger LOGGER = Logger.getLogger("esa.mo.xsd");

    public static List<XmlSpecification> loadSpecifications(final File directory) throws IOException, JAXBException {
        final List<XmlSpecification> specList = new LinkedList<>();

        if (!directory.exists()) {
            return specList;
        }

        final File[] xmlFiles = directory.listFiles();

        for (File file : xmlFiles) {
            if (file.isFile()) {
                try {
                    specList.add(XmlSpecification.loadSpecification(file));
                } catch (IOException ex) {
                    LOGGER.log(Level.WARNING,
                            "(1) Exception thrown during the processing of XML file: {0}",
                            file.getAbsolutePath());
                    throw ex;
                } catch (JAXBException ex) {
                    LOGGER.log(Level.WARNING,
                            "(2) Exception thrown during the processing of XML file: {0}",
                            file.getAbsolutePath());
                    throw ex;
                } catch (RuntimeException ex) {
                    LOGGER.log(Level.WARNING,
                            "(3) Exception thrown during the processing of XML file: {0}",
                            file.getAbsolutePath());
                    throw ex;
                } catch (Exception ex) {
                    LOGGER.log(Level.WARNING,
                            "(4) Exception thrown during the processing of XML file: {0}",
                            file.getAbsolutePath());
                    throw ex;
                }
            }
        }

        return specList;
    }
}
