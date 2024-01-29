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

import esa.mo.xsd.SpecificationType;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import w3c.xsd.Schema;

/**
 * Small helper class to load in MO XML specifications via JAXB
 */
public class XmlHelper {

    public static final java.util.logging.Logger LOGGER = Logger.getLogger("esa.mo.xsd");
    private static JAXBContext jc = null;

    public static List<XmlSpecification> loadSpecifications(final File directory) throws IOException, JAXBException {
        final List<XmlSpecification> specList = new LinkedList<>();

        if (!directory.exists()) {
            return specList;
        }

        final File[] xmlFiles = directory.listFiles();

        for (File file : xmlFiles) {
            if (file.isFile()) {
                try {
                    specList.add(loadSpecification(file));
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

    public synchronized static XmlSpecification loadSpecification(final File is) throws IOException, JAXBException {
        if (jc == null) {
            jc = JAXBContext.newInstance("esa.mo.xsd");
        }

        final Unmarshaller unmarshaller = jc.createUnmarshaller();
        final JAXBElement rootElement = (JAXBElement) unmarshaller.unmarshal(is);
        SpecificationType specType = (SpecificationType) rootElement.getValue();
        return new XmlSpecification(is, rootElement, specType);
    }

    public final static class XmlSpecification {

        /**
         * Holds the source file object.
         */
        public final File file;
        /**
         * Holds the XML root element.
         */
        public final JAXBElement rootElement;
        /**
         * Holds the SpecificationType.
         */
        public final SpecificationType specType;

        /**
         * Constructor.
         *
         * @param file The file.
         * @param rootElement The XML root element.
         * @param specType The specification type.
         */
        public XmlSpecification(File file, JAXBElement rootElement, SpecificationType specType) {
            this.file = file;
            this.rootElement = rootElement;
            this.specType = specType;
        }
    }

    public final static class XsdSpecification {

        /**
         * Holds the source file object.
         */
        public final File file;
        /**
         * Holds the XML root element.
         */
        public final JAXBElement rootElement;
        /**
         * Holds the SpecificationType.
         */
        public final Schema schema;

        /**
         * Constructor.
         *
         * @param file The file.
         * @param rootElement The XML root element.
         * @param schema The schema.
         */
        public XsdSpecification(File file, JAXBElement rootElement, Schema schema) {
            this.file = file;
            this.rootElement = rootElement;
            this.schema = schema;
        }
    }
}
