/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java Implementation
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
package esa.mo.mal.impl.transport.chain;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.transport.chain.Capability;
import org.ccsds.moims.mo.mal.transport.chain.CapabilitySet;
import org.ccsds.moims.mo.mal.transport.chain.ConcurrencyClass;
import org.ccsds.moims.mo.mal.transport.chain.Downlink;
import org.ccsds.moims.mo.mal.transport.chain.Exchange;
import org.ccsds.moims.mo.mal.transport.chain.Stage;
import org.ccsds.moims.mo.mal.transport.chain.Uplink;

/**
 * A trivial codec stage used in tests: on the way down it encodes a String body
 * to UTF-8 bytes; on the way up it decodes the bytes back to a String. It stands
 * in for the real encoding stage so a chain can be exercised end to end without
 * the full MAL encoding machinery.
 */
final class StringCodecStage implements Stage {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private final CapabilitySet requires;

    StringCodecStage() {
        this(CapabilitySet.of(Capability.FRAMED));
    }

    StringCodecStage(final CapabilitySet requires) {
        this.requires = requires;
    }

    @Override
    public ConcurrencyClass concurrency() {
        return ConcurrencyClass.STATELESS;
    }

    @Override
    public CapabilitySet requires() {
        return requires;
    }

    @Override
    public CapabilitySet provides() {
        return CapabilitySet.EMPTY;
    }

    @Override
    public void down(final Exchange exchange, final Downlink next) throws MALException {
        final String text = (String) exchange.body();
        exchange.setBytes(ByteBuffer.wrap(text.getBytes(UTF8)));
        exchange.setBody(null);
        next.forward(exchange);
    }

    @Override
    public void up(final Exchange exchange, final Uplink next) throws MALException {
        final ByteBuffer bytes = exchange.bytes();
        final byte[] raw = new byte[bytes.remaining()];
        bytes.get(raw);
        exchange.setBody(new String(raw, UTF8));
        exchange.setBytes(null);
        next.deliver(exchange);
    }
}
