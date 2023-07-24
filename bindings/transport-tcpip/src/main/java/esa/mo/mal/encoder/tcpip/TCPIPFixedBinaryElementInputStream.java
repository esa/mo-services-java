/* ----------------------------------------------------------------------------
 * Copyright (C) 2014      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO TCP/IP Transport Framework
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
package esa.mo.mal.encoder.tcpip;

import esa.mo.mal.encoder.binary.base.BinaryTimeHandler;
import esa.mo.mal.encoder.binary.fixed.FixedBinaryElementInputStream;
import esa.mo.mal.transport.tcpip.TCPIPMessageHeader;
import org.ccsds.moims.mo.mal.encoding.MALEncodingContext;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Manage the decoding of an incoming TCPIP Message. Separate decoders are used
 * for the message header and body. The header uses a custom implementation
 * according to MAL TCPIP Transport Binding specifications, and the body is
 * split binary decoded.
 *
 * @author Rian van Gijlswijk
 *
 */
@Deprecated
public class TCPIPFixedBinaryElementInputStream extends FixedBinaryElementInputStream {

    public TCPIPFixedBinaryElementInputStream(final java.io.InputStream is,
            final BinaryTimeHandler timeHandler) {
        super(new TCPIPFixedBinaryDecoder(is, timeHandler));
    }

    protected TCPIPFixedBinaryElementInputStream(final byte[] src, final int offset,
            final BinaryTimeHandler timeHandler) {
        super(new TCPIPFixedBinaryDecoder(src, offset, timeHandler));
    }

    @Override
    public MALMessageHeader readHeader(final MALMessageHeader header) throws MALException {
        // header is decoded using custom tcpip decoder
        return ((TCPIPMessageHeader) header).decode(dec);
    }

    @Override
    public Element readElement(final Element element, final MALEncodingContext ctx)
            throws IllegalArgumentException, MALException {
        if (element == ctx.getHeader()) {
            throw new MALException("The header is no longer read here! Use: readHeader()");
        } else {
            // body is not decoded
            return null;
        }
    }
}
