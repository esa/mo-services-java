/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Binary encoder
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
import java.util.List;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALListDecoder;
import org.ccsds.moims.mo.mal.encoding.BufferHolder;

/**
 *
 * @author Dominik Marszk
 */
public class FixedBinaryListDecoder extends FixedBinaryDecoder implements MALListDecoder {

    private final int listSize;
    private final List list;

    /**
     * Constructor.
     *
     * @param list List to decode into.
     * @param sourceBuffer Buffer to reuse.
     * @param timeHandler Time handler to reuse.
     * @throws MALException If cannot decode size of list.
     */
    public FixedBinaryListDecoder(final List list, final BufferHolder sourceBuffer,
            final BinaryTimeHandler timeHandler) throws MALException {
        super(sourceBuffer, timeHandler);

        this.list = list;
        this.listSize = sourceBuffer.readUnsignedInt();
    }

    /**
     * MALListDecoder hasNext implementation.
     *
     * @return true if there is more list elements to decode
     */
    @Override
    public boolean hasNext() {
        return list.size() < listSize;
    }

    /**
     * MALListDecoder size implementation.
     *
     * @return total numbers of list elements
     */
    @Override
    public int size() {
        return listSize;
    }
}
