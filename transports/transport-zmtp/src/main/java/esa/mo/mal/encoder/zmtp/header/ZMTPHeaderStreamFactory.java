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
import esa.mo.mal.transport.zmtp.ZMTPStringMappingDirectory;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;

/**
 * Implements the MALElementStreamFactory interface for ZMTP header binary
 * encoding.
 */
public class ZMTPHeaderStreamFactory extends esa.mo.mal.encoder.binary.fixed.FixedBinaryStreamFactory {

    /**
     * The mapping
     */
    ZMTPStringMappingDirectory mapping;

    public ZMTPHeaderStreamFactory(ZMTPStringMappingDirectory mapping) {
        this.mapping = mapping;
        this.timeHandler = new BinaryTimeHandler();
    }

    @Override
    protected void init(final Map properties) throws IllegalArgumentException, MALException {
        super.init(properties);
    }

    @Override
    public MALElementInputStream createInputStream(final java.io.InputStream is) throws MALException {
        return new ZMTPHeaderElementInputStream(is, mapping, timeHandler);
    }

    @Override
    public MALElementOutputStream createOutputStream(final java.io.OutputStream os) throws MALException {
        return new ZMTPHeaderElementOutputStream(os, mapping, timeHandler);
    }

    public ZMTPHeaderEncoder getHeaderEncoder(final java.io.OutputStream os) {
        return new ZMTPHeaderEncoder(os, mapping, timeHandler);
    }

    public ZMTPHeaderDecoder getHeaderDecoder(final java.io.InputStream is) {
        return new ZMTPHeaderDecoder(is, mapping, timeHandler);
    }
}
