/* ----------------------------------------------------------------------------
 * Copyright (C) 2026      European Space Agency
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

import org.ccsds.moims.mo.mal.MALException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TCPIPFixedBinaryDecoderTest {

    @Test
    public void arrayInputIsDecoded() throws MALException {
        TCPIPFixedBinaryDecoder decoder = new TCPIPFixedBinaryDecoder(new byte[]{0, 0, 0, 7, 42}, 0);
        assertEquals(Integer.valueOf(7), decoder.decodeInteger());
        assertEquals(Byte.valueOf((byte) 42), decoder.decodeOctet());
    }

    @Test
    public void arrayInputWithOffsetIsDecoded() throws MALException {
        TCPIPFixedBinaryDecoder decoder = new TCPIPFixedBinaryDecoder(new byte[]{99, 0, 0, 0, 7}, 1);
        assertEquals(Integer.valueOf(7), decoder.decodeInteger());
    }

    @Test(expected = MALException.class)
    public void truncatedArrayInputIsRejected() throws MALException {
        new TCPIPFixedBinaryDecoder(new byte[]{0, 0, 7}, 0).decodeInteger();
    }
}
