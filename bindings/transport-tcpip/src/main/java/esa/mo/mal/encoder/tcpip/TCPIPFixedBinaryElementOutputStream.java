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
import esa.mo.mal.encoder.binary.fixed.FixedBinaryElementOutputStream;
import esa.mo.mal.transport.tcpip.TCPIPMessageHeader;
import java.io.OutputStream;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.encoding.MALEncodingContext;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.encoding.Encoder;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Encode a TCPIP Message
 *
 * @author Rian van Gijlswijk
 *
 */
@Deprecated
public class TCPIPFixedBinaryElementOutputStream extends FixedBinaryElementOutputStream {

    /**
     * Logger
     */
    public static final java.util.logging.Logger RLOGGER = Logger.getLogger(
            "org.ccsds.moims.mo.mal.encoding.tcpip");

    public TCPIPFixedBinaryElementOutputStream(OutputStream os,
            final BinaryTimeHandler timeHandler) {
        super(os, timeHandler, false);
    }

    @Override
    protected Encoder createEncoder(OutputStream os) {
        return new TCPIPFixedBinaryEncoder(os, timeHandler);
    }

    @Override
    public void writeHeader(final MALMessageHeader header) throws MALException {
        if (enc == null) {
            enc = createEncoder(this.dos);
        }

        // header is encoded using tcpip custom encoder
        ((TCPIPMessageHeader) header).encode(enc);
    }

    @Override
    public void writeElement(final Element element, final MALEncodingContext ctx) throws MALException {
        if (enc == null) {
            enc = createEncoder(this.dos);
        }

        if (element == ctx.getHeader()) {
            throw new MALException("The header is no longer read here! Use: writeHeader()");
        }

        // body is not encoded
    }
}
