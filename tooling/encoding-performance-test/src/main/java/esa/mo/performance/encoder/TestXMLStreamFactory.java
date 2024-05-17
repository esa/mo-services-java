/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Encoder performance test
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
package esa.mo.performance.encoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import javax.xml.bind.*;
import javax.xml.namespace.QName;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

public class TestXMLStreamFactory extends MALElementStreamFactory {

    @Override
    protected void init(String protocol, Map properties) throws IllegalArgumentException, MALException {
    }

    @Override
    public MALElementInputStream createInputStream(InputStream is) throws IllegalArgumentException, MALException {
        return new TestXMLInputStream(is);
    }

    @Override
    public MALElementOutputStream createOutputStream(OutputStream os) throws IllegalArgumentException, MALException {
        return new TestXMLOutputStream(os);
    }

    protected static class TestXMLOutputStream implements MALElementOutputStream {

        private final OutputStream os;

        public TestXMLOutputStream(OutputStream os) {
            this.os = os;
        }

        @Override
        public void writeHeader(MALMessageHeader header) throws IllegalArgumentException, MALException {
            try {
                String schemaURN = "http://www.ccsds.org/schema/PerfTestServiceSchema";
                String schemaEle = "report";
                JAXBContext jc = JAXBContext.newInstance(header.getClass().getPackage().getName());
                Marshaller marshaller = jc.createMarshaller();
                marshaller.marshal(new JAXBElement(new QName(schemaURN, schemaEle), header.getClass(), null, header), os);
            } catch (JAXBException ex) {
                throw new MALException("XML Encoding error", ex);
            }
        }

        @Override
        public void writeElement(Element o, OperationField field) throws IllegalArgumentException, MALException {
            try {
                String schemaURN = "http://www.ccsds.org/schema/PerfTestServiceSchema";
                String schemaEle = "report";
                JAXBContext jc = JAXBContext.newInstance(o.getClass().getPackage().getName());
                Marshaller marshaller = jc.createMarshaller();
                marshaller.marshal(new JAXBElement(new QName(schemaURN, schemaEle), o.getClass(), null, o), os);
            } catch (JAXBException ex) {
                throw new MALException("XML Encoding error", ex);
            }
        }

        @Override
        public void flush() throws MALException {
            try {
                os.flush();
            } catch (IOException ex) {
                throw new MALException("XML Encoding error", ex);
            }
        }

        @Override
        public void close() throws MALException {
            try {
                os.close();
            } catch (IOException ex) {
                throw new MALException("XML Encoding error", ex);
            }
        }
    }

    protected static class TestXMLInputStream implements MALElementInputStream {

        private final InputStream is;

        public TestXMLInputStream(InputStream is) {
            this.is = is;
        }

        @Override
        public MALMessageHeader readHeader(MALMessageHeader header) throws IllegalArgumentException, MALException {
            try {
                JAXBContext jc = JAXBContext.newInstance(header.getClass().getPackage().getName());
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                JAXBElement rootElement = (JAXBElement) unmarshaller.unmarshal(is);
                return (MALMessageHeader) rootElement.getValue();
            } catch (JAXBException ex) {
                throw new MALException("XML Decoding error", ex);
            }
        }

        @Override
        public Element readElement(Element element, OperationField field) throws IllegalArgumentException, MALException {
            try {
                JAXBContext jc = JAXBContext.newInstance(element.getClass().getPackage().getName());
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                JAXBElement rootElement = (JAXBElement) unmarshaller.unmarshal(is);
                return (Element) rootElement.getValue();
            } catch (JAXBException ex) {
                throw new MALException("XML Decoding error", ex);
            }
        }

        @Override
        public void close() throws MALException {
            try {
                is.close();
            } catch (IOException ex) {
                throw new MALException("XML Encoding error", ex);
            }
        }
    }
}
