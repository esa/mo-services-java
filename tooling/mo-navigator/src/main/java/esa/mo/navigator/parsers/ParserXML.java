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
package esa.mo.navigator.parsers;

import de.dlr.gsoc.mcds.mosdl.loaders.XmlSpecLoader;
import java.io.StringReader;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.ccsds.schema.serviceschema.SpecificationType;
import org.xml.sax.SAXException;

/**
 * The ParserXML class parses the XML from a text field.
 *
 * @author Cesar Coelho
 */
public class ParserXML {

    private static final String SERVICE_SCHEMA_RESOURCE = "/COMSchema.xsd";

    /**
     * The parseXML method parses a file and returns the set of ParsedLines.
     *
     * @param text The text
     * @return The parsed Data
     * @throws javax.xml.bind.JAXBException if the text could not be parsed.
     * @throws org.xml.sax.SAXException if the Schema could not be parsed.
     */
    public static SpecificationType parseXML(String text) throws JAXBException, SAXException {
        JAXBContext jaxbContext = JAXBContext.newInstance(SpecificationType.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        boolean validate = false;

        if (validate) {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(XmlSpecLoader.class.getResource(SERVICE_SCHEMA_RESOURCE));
            jaxbUnmarshaller.setSchema(schema);
        }

        StreamSource streamSource = new StreamSource(new StringReader(text));
        return (SpecificationType) ((JAXBElement) jaxbUnmarshaller.unmarshal(streamSource)).getValue();
    }

}
