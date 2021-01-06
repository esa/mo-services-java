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
package esa.mo.mal.encoder.binary.fixed;

import esa.mo.mal.encoder.binary.base.BinaryTimeHandler;

/**
 * Implements the MALElementOutputStream interface for a fixed length binary
 * encoding.
 */
public class FixedBinaryElementOutputStream extends esa.mo.mal.encoder.binary.base.BaseBinaryElementOutputStream {

    /**
     * 16-bit length field encoding enabled
     */
    protected final boolean shortLengthField;

    /**
     * Constructor.
     *
     * @param os Output stream to write to.
     * @param timeHandler Time handler to use.
     * @param shortLengthField True if length field is 16-bit wide, otherwise
     * assumed to be 32-bit.
     */
    public FixedBinaryElementOutputStream(final java.io.OutputStream os,
            final BinaryTimeHandler timeHandler,
            final boolean shortLengthField) {
        super(os, timeHandler);
        this.shortLengthField = shortLengthField;
    }

    @Override
    protected esa.mo.mal.encoder.gen.GENEncoder createEncoder(java.io.OutputStream os) {
        return new FixedBinaryEncoder(os, timeHandler, shortLengthField);
    }
}
