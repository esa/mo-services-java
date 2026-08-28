/* ----------------------------------------------------------------------------
 * Copyright (C) 2026      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO API Generator
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
package esa.mo.apigen.exporters.xml;

import esa.mo.apigen.model.SchemaVersion;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.EnumMap;
import java.util.Map;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Validates exported XML against the normative schema, using JAXP - part of the JDK, so
 * this adds no dependency.
 * <p>
 * The schema encodes constraints a hand-written validator would otherwise have to
 * duplicate, so anything the XSD can state is left to it. Each specification is checked
 * against its own version rather than against the backwards-compatible hybrid, which
 * accepts documents neither version does.
 * <p>
 * The schemas are unpacked into this library's resources at build time from
 * {@code xml-ccsds-mo-standards}. Copying rather than keeping a hand-maintained copy
 * matters: a hand-kept copy is how a divergent snapshot of this schema came to be shipped
 * elsewhere.
 */
public final class SchemaValidation {

    private static final Map<SchemaVersion, Schema> CACHE
            = new EnumMap<SchemaVersion, Schema>(SchemaVersion.class);

    private SchemaValidation() {
    }

    /**
     * Validates a document.
     *
     * @param xml The document.
     * @param version The schema version to check it against.
     * @return null if the document is valid, otherwise a description of the first problem.
     */
    public static synchronized String check(String xml, SchemaVersion version) {
        Schema schema;
        try {
            schema = schemaFor(version);
        } catch (SAXException ex) {
            return "the " + version + " schema could not be loaded: " + ex.getMessage();
        } catch (IOException ex) {
            return "the " + version + " schema could not be read: " + ex.getMessage();
        }
        if (schema == null) {
            return null;                       // no schema available; nothing to check against
        }
        final StringBuilder first = new StringBuilder();
        try {
            Validator validator = schema.newValidator();
            validator.setErrorHandler(new ErrorHandler() {
                @Override
                public void warning(SAXParseException ex) {
                    // not a failure
                }

                @Override
                public void error(SAXParseException ex) throws SAXException {
                    record(ex);
                }

                @Override
                public void fatalError(SAXParseException ex) throws SAXException {
                    record(ex);
                }

                private void record(SAXParseException ex) throws SAXException {
                    if (first.length() == 0) {
                        first.append("line ").append(ex.getLineNumber())
                                .append(", column ").append(ex.getColumnNumber())
                                .append(": ").append(ex.getMessage());
                    }
                    throw ex;
                }
            });
            validator.validate(new StreamSource(new StringReader(xml)));
        } catch (SAXException ex) {
            return first.length() > 0 ? first.toString() : ex.getMessage();
        } catch (IOException ex) {
            return "could not read the document: " + ex.getMessage();
        }
        return null;
    }

    private static Schema schemaFor(SchemaVersion version) throws SAXException, IOException {
        Schema cached = CACHE.get(version);
        if (cached != null) {
            return cached;
        }
        // v001 documents may carry COM features; no v003 specification does, which is
        // consistent with COMSchema importing the v001 service schema.
        String[] resources = version == SchemaVersion.V001
                ? new String[]{"/xsd/ServiceSchema.xsd", "/xsd/COMSchema.xsd"}
                : new String[]{"/xsd/ServiceSchema-v003.xsd"};

        Source[] sources = new Source[resources.length];
        for (int i = 0; i < resources.length; i++) {
            InputStream in = SchemaValidation.class.getResourceAsStream(resources[i]);
            if (in == null) {
                return null;                   // schemas not on the classpath in this build
            }
            StreamSource source = new StreamSource(in);
            // A system id so that COMSchema's relative import of the service schema
            // resolves against its sibling.
            source.setSystemId(SchemaValidation.class.getResource(resources[i]).toExternalForm());
            sources[i] = source;
        }
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(sources);
        CACHE.put(version, schema);
        return schema;
    }
}
