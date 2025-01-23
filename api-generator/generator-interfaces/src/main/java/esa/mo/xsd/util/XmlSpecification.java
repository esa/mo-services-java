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
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * The XML Specification.
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

    /**
     * Returns the XML file.
     *
     * @return the XML file.
     */
    public File getFile() {
        return file;
    }

    /**
     * Returns the Root Element.
     *
     * @return the Root Element.
     */
    public JAXBElement getRootElement() {
        return rootElement;
    }

    /**
     * Returns the Specification Type.
     *
     * @return the Specification Type.
     */
    public SpecificationType getSpecType() {
        return specType;
    }

    /**
     * Modifies the content from a XML file and returns it. Note that the file
     * is not saved with these changes. This method is a hack to have the new
     * "ServiceSchema-v003" specifications, to be compatible with the old code.
     * This method will have to be removed after the mo-services-java codebase
     * fully deprecate support for the old MAL.
     *
     * @param file The file with the specification.
     * @return An input stream with the modified content.
     * @throws IOException if something went while modifying the content.
     */
    private static InputStream modifyFileToBeBackwardsCompatible(File file) throws IOException {
        // Read the file content into a StringBuilder
        StringBuilder fileContent = new StringBuilder();
        try ( BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append(System.lineSeparator());
            }
        }

        // Replace the text "ServiceSchema-v003" with "ServiceSchema"
        String modifiedContent = fileContent.toString().replace("ServiceSchema-v003", "ServiceSchema");

        // Convert the modified content to an InputStream
        return new ByteArrayInputStream(modifiedContent.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Loads an XML Specification.
     *
     * @param file The file with the specification.
     * @return The representation of the XML specification.
     * @throws IOException if something went while modifying the content.
     * @throws JAXBException if the xml could not be parsed.
     */
    public synchronized static XmlSpecification loadSpecification(final File file) throws IOException, JAXBException {
        if (jc == null) {
            jc = JAXBContext.newInstance("esa.mo.xsd");
        }

        final Unmarshaller unmarshaller = jc.createUnmarshaller();
        final InputStream is = XmlSpecification.modifyFileToBeBackwardsCompatible(file);
        final JAXBElement rootElement = (JAXBElement) unmarshaller.unmarshal(is);
        SpecificationType specType = (SpecificationType) rootElement.getValue();
        return new XmlSpecification(file, rootElement, specType);
    }
}
