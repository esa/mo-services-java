/* ----------------------------------------------------------------------------
 * Copyright (C) 2015      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO SPP Transport Framework
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
package esa.mo.mal.encoder.zmtp.header;

import esa.mo.mal.encoder.binary.base.BinaryTimeHandler;
import esa.mo.mal.encoder.binary.fixed.FixedBinaryElementInputStream;
import esa.mo.mal.transport.zmtp.ZMTPStringMappingDirectory;

/**
 * Implements the MALElementInputStream interface for a binary encoding used in
 * ZMTP header.
 */
public class ZMTPHeaderElementInputStream extends FixedBinaryElementInputStream {

    /**
     * Constructor.
     *
     * @param is Input stream to read from.
     * @param mapping The parent mapping.
     * @param timeHandler The time handler.
     */
    public ZMTPHeaderElementInputStream(final java.io.InputStream is,
            ZMTPStringMappingDirectory mapping, final BinaryTimeHandler timeHandler) {
        super(new ZMTPHeaderDecoder(is, mapping, timeHandler));
    }

    /**
     * Constructor.
     *
     * @param buf Byte buffer to read from
     * @param offset Offset into buffer to start from
     * @param mapping The parent mapping
     * @param timeHandler Implementation of the time encoding to use
     */
    public ZMTPHeaderElementInputStream(final byte[] buf,
            final int offset, ZMTPStringMappingDirectory mapping,
            final BinaryTimeHandler timeHandler) {
        super(new ZMTPHeaderDecoder(buf, offset, mapping, timeHandler));
    }
}
