/* ----------------------------------------------------------------------------
 * Copyright (C) 2017      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO ZMTP Transport Framework
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
import esa.mo.mal.transport.zmtp.ZMTPTransport;
import java.util.List;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALListDecoder;

public class ZMTPHeaderListDecoder extends ZMTPHeaderDecoder implements MALListDecoder {

    private final int listSize;
    private final List list;

    /**
     * MALListDecoder setup. It needs special handling, because most of the
     * header fields are fixed length fields. But list length is not.
     *
     * @param list List to decode to
     * @param srcBuffer Source buffer shared with the parent decoder
     * @param transport Parent ZMTP transport
     * @param timeHandler Implementation of the time encoding to use
     * @throws MALException If cannot decode list size.
     */
    protected ZMTPHeaderListDecoder(List list, final FixedBinaryBufferHolder srcBuffer,
            final ZMTPTransport transport, final BinaryTimeHandler timeHandler) throws MALException {
        super(srcBuffer, transport, timeHandler);
        this.list = list;
        this.listSize = (int) getVariableUnsigned();
    }

    @Override
    public boolean hasNext() {
        return list.size() < listSize;
    }

    @Override
    public int size() {
        return listSize;
    }

}
