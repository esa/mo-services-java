/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Service Stub Generator
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
package esa.mo.tools.stubgen;

import esa.mo.xsd.SpecificationType;
import java.io.IOException;
import java.util.Map;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import w3c.xsd.Schema;

/**
 * Interface class for a language generator.
 */
public interface Generator {

    /**
     * Returns the short name of the generator, this is used on the command line
     * when specifying generators.
     *
     * @return the generator short name.
     */
    String getShortName();

    /**
     * Returns the description of the generator.
     *
     * @return the generator description.
     */
    String getDescription();

    /**
     * Initialises the generator.
     *
     * @param destinationFolderName The folder to generate into too.
     * @param generateStructures Whether to generate any data types in the
     * specification.
     * @param generateCOM Whether to generate COM information.
     * @param packageBindings Sets any binding information for when services are
     * specified packages other than the default for that language. Held in
     * AREA/package pairs, or URI/package for JAXB.
     * @param extraProperties Any generator specific properties.
     * @throws IOException If there is a problem initialising the generator.
     */
    void init(String destinationFolderName,
            boolean generateStructures,
            boolean generateCOM,
            Map<String, String> packageBindings,
            Map<String, String> extraProperties) throws IOException;

    /**
     * Extra initialisation the generator called after init.
     *
     * @param destinationFolderName The folder to generate into too.
     * @param generateStructures Whether to generate any data types in the
     * specification.
     * @param generateCOM Whether to generate COM information.
     * @param packageBindings Sets any binding information for when services are
     * specified packages other than the default for that language. Held in
     * AREA/package pairs, or URI/package for JAXB.
     * @param extraProperties Any generator specific properties.
     * @throws IOException If there is a problem initialising the generator.
     */
    void postinit(String destinationFolderName,
            boolean generateStructures,
            boolean generateCOM,
            Map<String, String> packageBindings,
            Map<String, String> extraProperties) throws IOException;

    /**
     * Pre process a specification to load in the type definitions.
     *
     * @param spec The specification to process.
     * @throws IOException If there are problems reading the file.
     * @throws JAXBException If there are problems reading any XML Schema
     * definitions.
     */
    void preProcess(SpecificationType spec) throws IOException, JAXBException;

    /**
     * Pre process an XSD specification to load in the type definitions.
     *
     * @param spec The specification to process.
     * @throws IOException If there are problems reading the file.
     * @throws JAXBException If there are problems reading any XML Schema
     * definitions.
     */
    void preProcess(Schema spec) throws IOException, JAXBException;

    /**
     * Compiles the specification into the appropriate form for the generator.
     *
     * @param destinationFolderName The folder to generate in to.
     * @param spec The specification to process.
     * @param rootNode The JAXB root node for the specification
     * @throws IOException If there are problems writing the files.
     * @throws JAXBException If there are problems reading any XML Schema
     * definitions.
     */
    void compile(String destinationFolderName, SpecificationType spec, JAXBElement rootNode) throws IOException, JAXBException;

    /**
     * Closes the generator allowing it to write out any final files or
     * processing.
     *
     * @param destinationFolderName The folder name that the output should be
     * sent too.
     * @throws IOException If there are problems writing the files.
     */
    void close(String destinationFolderName) throws IOException;

    /**
     * Resets the generator allowing it to be reused.
     */
    void reset();
}
