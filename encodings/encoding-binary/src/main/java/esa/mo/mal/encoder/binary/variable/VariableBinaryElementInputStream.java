/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Fixed Length Binary encoder
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
package esa.mo.mal.encoder.binary.variable;

import esa.mo.mal.encoder.binary.base.BinaryTimeHandler;

/**
 * Implements the MALElementInputStream interface for a fixed length binary
 * encoding.
 */
public class VariableBinaryElementInputStream extends esa.mo.mal.encoder.binary.base.BaseBinaryElementInputStream {

    /**
     * Constructor.
     *
     * @param is Input stream to read from.
     * @param timeHandler Time handler to use.
     */
    public VariableBinaryElementInputStream(final java.io.InputStream is,
            final BinaryTimeHandler timeHandler) {
        super(new VariableBinaryDecoder(is, timeHandler));
    }

    /**
     * Constructor.
     *
     * @param buf Byte buffer to read from.
     * @param offset Offset into buffer to start from.
     * @param timeHandler Time handler to use.
     */
    public VariableBinaryElementInputStream(final byte[] buf, final int offset,
            final BinaryTimeHandler timeHandler) {
        super(new VariableBinaryDecoder(buf, offset, timeHandler));
    }

    /**
     * Sub class constructor.
     *
     * @param pdec Decoder to use.
     */
    protected VariableBinaryElementInputStream(VariableBinaryDecoder pdec) {
        super(pdec);
    }
}
