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
package esa.mo.mal.encoder.http;

import java.io.InputStream;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 *
 */
public class HTTPXMLElementInputStream implements MALElementInputStream {

    private MALDecoder dec;

    public HTTPXMLElementInputStream(InputStream is) {
        this.dec = new HTTPXMLStreamReader(is);
    }

    @Override
    public MALMessageHeader readHeader(MALMessageHeader header) throws MALException {
        return header.decode(dec);
    }

    @Override
    public Element readElement(Element element, OperationField field)
            throws IllegalArgumentException, MALException {
        return this.dec.decodeElement((Element) element);
    }

    @Override
    public void close() throws MALException {
    }

    public MALDecoder getDecoder() {
        return this.dec;
    }
}
