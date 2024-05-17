/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * The XML Specification
 */
public final class XmlSpecification {

    /**
     * Holds the source file object.
     */
    private final File file;
    /**
     * Holds the XML root element.
     */
    private final JAXBElement rootElement;
    /**
     * Holds the SpecificationType.
     */
    private final SpecificationType specType;

    private static JAXBContext jc = null;

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

    public File getFile() {
        return file;
    }

    public JAXBElement getRootElement() {
        return rootElement;
    }

    public SpecificationType getSpecType() {
        return specType;
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
}
