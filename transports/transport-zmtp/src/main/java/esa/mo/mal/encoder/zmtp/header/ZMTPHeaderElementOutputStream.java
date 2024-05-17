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
import esa.mo.mal.encoder.binary.fixed.FixedBinaryElementOutputStream;
import esa.mo.mal.transport.zmtp.ZMTPStringMappingDirectory;
import org.ccsds.moims.mo.mal.encoding.Encoder;

/**
 * Implements the MALElementOutputStream interface for a binary encoding used in
 * ZMTP header.
 */
public class ZMTPHeaderElementOutputStream extends FixedBinaryElementOutputStream {

    /**
     * Parent mapping
     */
    private final ZMTPStringMappingDirectory mapping;

    /**
     * Constructor.
     *
     * @param os Output stream to write to
     * @param mapping The parent mapping
     * @param timeHandler Implementation of the time encoding to use
     */
    public ZMTPHeaderElementOutputStream(final java.io.OutputStream os,
            final ZMTPStringMappingDirectory mapping, final BinaryTimeHandler timeHandler) {
        super(os, timeHandler, false);
        this.mapping = mapping;
    }

    @Override
    protected Encoder createEncoder(java.io.OutputStream os) {
        return new ZMTPHeaderEncoder(os, mapping, timeHandler);
    }
}
