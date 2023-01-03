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

import de.dlr.gsoc.mcds.mosdl.generators.XmlGenerator;
import de.dlr.gsoc.mcds.mosdl.loaders.XmlSpecLoader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Marshaller.Listener;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.ccsds.schema.serviceschema.AreaType;
import org.ccsds.schema.serviceschema.InvokeOperationType;
import org.ccsds.schema.serviceschema.ProgressOperationType;
import org.ccsds.schema.serviceschema.PubSubOperationType;
import org.ccsds.schema.serviceschema.RequestOperationType;
import org.ccsds.schema.serviceschema.ServiceType;
import org.ccsds.schema.serviceschema.SpecificationType;
import org.ccsds.schema.serviceschema.SubmitOperationType;
import org.xml.sax.SAXException;

/**
 * The XMLParser class parses the XML from a text field.
 */
public class GeneratorXML extends XmlGenerator {

    private static final String SERVICE_SCHEMA_RESOURCE = "/COMSchema.xsd";

    public GeneratorXML(boolean isSkipValidation) {
        super(isSkipValidation);
    }

    /**
     * The generateXML method generates XML text from a specification.
     *
     * @param spec The specification
     * @return The parsed Data
     * @throws java.io.IOException
     */
    public static String generateXML(SpecificationType spec) throws IOException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(SpecificationType.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            org.ccsds.schema.serviceschema.ObjectFactory serviceSchemaObjectFactory = new org.ccsds.schema.serviceschema.ObjectFactory();
            JAXBElement element = serviceSchemaObjectFactory.createSpecification(spec);
            boolean validate = false;

            if (validate) {
                try {
                    SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                    Schema schema = schemaFactory.newSchema(XmlSpecLoader.class.getResource(SERVICE_SCHEMA_RESOURCE));
                    jaxbMarshaller.setSchema(schema);
                } catch (SAXException ex) {
                    Logger.getLogger(GeneratorXML.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            jaxbMarshaller.setListener(new CleanupListener());
            StringWriter writer = new StringWriter();
            jaxbMarshaller.marshal(element, writer);
            // The generator was not handling the '&' char correctly. It needs a correction: 
            String correctedText = writer.toString().replace("&amp;", "&");

            // Fix the new line problem: When there is an enter, the xml
            // must have an &#10; instead of the enter!
            Scanner scanner = new Scanner(correctedText);
            String previousLine = scanner.nextLine();
            StringBuilder newLineFix = new StringBuilder();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.contains("mal:")) {
                    previousLine += "&#10;" + line;
                } else {
                    newLineFix.append(previousLine).append("\n");
                    previousLine = line;
                }
            }
            newLineFix.append(previousLine);
            scanner.close();
            correctedText = newLineFix.toString();

            return correctedText;
            // logger.debug("Finished generation of XML file '{}'.", targetFile);
        } catch (JAXBException ex) {
            throw new IOException(ex);
        }

    }

    /**
     * This listener cleans up the model during marshalling.
     * <p>
     * Currently JAXB (or rather the XMLElementWrapper plugin) has a bug that
     * generates invalid XML documents for an optional element that must contain
     * at least one other element when no other element is present. In other
     * words, in this case an empty list must not be encoded as an empty wrapper
     * element but the wrapper element must be completely absent.
     * <p>
     * This class knows about the occurrences of this particular structure in
     * the service schema and cleans up the model before feeding it to the
     * marshaller.
     * <p>
     * Currently, only error lists exhibit this structure. They are used for
     * area, service and all operation types (except SEND).
     */
    private static class CleanupListener extends Listener {

        @Override
        public void beforeMarshal(Object source) {
            if (source instanceof AreaType && ((AreaType) source).getErrors().isEmpty()) {
                ((AreaType) source).setErrors(null);
            } else if (source instanceof ServiceType && ((ServiceType) source).getErrors().isEmpty()) {
                ((ServiceType) source).setErrors(null);
            } else if (source instanceof SubmitOperationType && ((SubmitOperationType) source).getErrors().isEmpty()) {
                ((SubmitOperationType) source).setErrors(null);
            } else if (source instanceof RequestOperationType && ((RequestOperationType) source).getErrors().isEmpty()) {
                ((RequestOperationType) source).setErrors(null);
            } else if (source instanceof InvokeOperationType && ((InvokeOperationType) source).getErrors().isEmpty()) {
                ((InvokeOperationType) source).setErrors(null);
            } else if (source instanceof ProgressOperationType && ((ProgressOperationType) source).getErrors().isEmpty()) {
                ((ProgressOperationType) source).setErrors(null);
            } else if (source instanceof PubSubOperationType && ((PubSubOperationType) source).getErrors().isEmpty()) {
                ((PubSubOperationType) source).setErrors(null);
            }
        }
    }

}
