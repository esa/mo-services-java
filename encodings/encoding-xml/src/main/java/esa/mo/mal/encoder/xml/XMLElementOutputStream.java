/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Encoder - XML
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
package esa.mo.mal.encoder.xml;

import static esa.mo.mal.encoder.xml.XMLStreamFactory.RLOGGER;
import java.io.OutputStream;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 *
 */
public class XMLElementOutputStream implements MALElementOutputStream {

    protected final OutputStream dos;
    protected MALEncoder enc;

    public XMLElementOutputStream(OutputStream os) {
        this.dos = os;
        if (enc == null) {
            enc = new XMLStreamWriter(this.dos);
        }
    }

    @Override
    public void writeHeader(MALMessageHeader header) throws IllegalArgumentException, MALException {
        // Do nothing
    }

    @Override
    public void writeElement(final Element element, final OperationField field) throws MALException {
        if (enc == null) {
            enc = new XMLStreamWriter(this.dos);
        }

        if (element != null) {
            encodeBody(element);
        } else if (element == null) {
            ((XMLStreamWriter) this.enc).encodeNullableElement(null);
        }
    }

    private void encodeBody(final Object element) throws MALException {
        RLOGGER.finest("HTTPXMLElementOutputStream.encodeBody");

        try {
            String elementName = element.getClass().getSimpleName();
            if (element instanceof Element) {
                if (!elementName.isEmpty()) {
                    ((XMLStreamWriter) this.enc).encodeElement((Element) element, elementName);
                } else {
                    ((Element) element).encode(this.enc);
                }
            } else {
                ((XMLStreamWriter) this.enc).encode(element, element.getClass().getSimpleName());
            }

        } catch (Exception ex) {
            RLOGGER.log(Level.WARNING, "exception in HTTPXMLElementOutputStream.encodeBody: " + ex.getMessage(), ex);
            throw new MALException(ex.getMessage());
        }
    }

    @Override
    public void flush() throws MALException {
    }

    @Override
    public void close() throws MALException {
        try {
            this.enc.close();
        } catch (Exception ex) {
            RLOGGER.warning("exception in HTTPXMLElementOutputStream.close: " + ex.getMessage());
            throw new MALException(ex.getMessage());
        }
    }
}
