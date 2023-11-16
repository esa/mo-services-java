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
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;

/**
 * Implements the MALElementStreamFactory interface for a fixed length binary
 * encoding.
 */
public class FixedBinaryStreamFactory extends esa.mo.mal.encoder.binary.base.BaseBinaryStreamFactory {

    public static final String SHORT_LENGTH_FIELD = "esa.mo.mal.encoder.binary.fixed.shortLengthField";

    /**
     * 16-bit length field encoding enabled
     */
    protected boolean shortLengthField = false;

    @Override
    protected void init(final String protocol, final Map properties) throws IllegalArgumentException, MALException {
        super.init(protocol, properties);
        if (properties != null) {
            if (properties.containsKey(SHORT_LENGTH_FIELD)
                    && Boolean.parseBoolean(properties.get(SHORT_LENGTH_FIELD).toString())) {
                shortLengthField = true;
            }
        }
    }

    public FixedBinaryStreamFactory() {
        super(FixedBinaryElementInputStream.class, FixedBinaryElementOutputStream.class,
                new BinaryTimeHandler());
    }

    @Override
    public MALElementInputStream createInputStream(final byte[] bytes, final int offset) throws MALException {
        return new FixedBinaryElementInputStream(bytes, offset, timeHandler, shortLengthField);
    }

    @Override
    public MALElementInputStream createInputStream(final java.io.InputStream is) throws MALException {
        return new FixedBinaryElementInputStream(is, timeHandler, shortLengthField);
    }

    @Override
    public MALElementOutputStream createOutputStream(final java.io.OutputStream os) throws MALException {
        return new FixedBinaryElementOutputStream(os, timeHandler, shortLengthField);
    }
}
