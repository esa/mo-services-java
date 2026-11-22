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
import java.util.IdentityHashMap;
import java.util.Map;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.chain.Attr;
import org.ccsds.moims.mo.mal.transport.chain.CapabilitySet;
import org.ccsds.moims.mo.mal.transport.chain.Exchange;

/**
 * The framework's mutable {@link Exchange} implementation. A single instance is
 * threaded through the stages of one message, down on send and up on receive.
 *
 * <p>
 * On an inbound message the header is not yet known when the bytes arrive, so an
 * exchange minted for the inbound path carries a null header until a decode
 * stage sets the body; such an exchange is only exposed to the MAL once fully
 * decoded. This class is not thread-safe: a single exchange is processed on one
 * thread at a time, which the scheduler guarantees.
 */
final class ExchangeImpl implements Exchange {

    /**
     * The default priority, NORMAL, used when none is carried on the wire. Kept
     * here so the chain never returns a null priority.
     */
    static final UOctet NORMAL_PRIORITY = new UOctet((short) 128);

    private final MALMessageHeader header;
    private final CapabilitySet channelCapabilities;
    private final QoSLevel qos;
    private final UOctet priority;
    private final NamedValueList supplements;
    private Object body;
    private ByteBuffer bytes;
    private Map<Attr<?>, Object> attributes;

    ExchangeImpl(final MALMessageHeader header, final CapabilitySet channelCapabilities,
            final QoSLevel qos, final UOctet priority, final NamedValueList supplements,
            final Object body, final ByteBuffer bytes) {
        this.header = header;
        this.channelCapabilities = channelCapabilities;
        this.qos = (qos != null) ? qos : QoSLevel.BESTEFFORT;
        this.priority = (priority != null) ? priority : NORMAL_PRIORITY;
        this.supplements = (supplements != null) ? supplements : new NamedValueList();
        this.body = body;
        this.bytes = bytes;
    }

    @Override
    public MALMessageHeader header() {
        return header;
    }

    @Override
    public Object body() {
        return body;
    }

    @Override
    public void setBody(final Object body) {
        this.body = body;
    }

    @Override
    public ByteBuffer bytes() {
        return bytes;
    }

    @Override
    public void setBytes(final ByteBuffer bytes) {
        this.bytes = bytes;
    }

    @Override
    public QoSLevel qos() {
        return qos;
    }

    @Override
    public UOctet priority() {
        return priority;
    }

    @Override
    public NamedValueList supplements() {
        return supplements;
    }

    @Override
    public CapabilitySet channelCapabilities() {
        return channelCapabilities;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T attr(final Attr<T> key) {
        if (attributes == null) {
            return null;
        }
        return (T) attributes.get(key);
    }

    @Override
    public <T> void setAttr(final Attr<T> key, final T value) {
        if (attributes == null) {
            attributes = new IdentityHashMap<Attr<?>, Object>();
        }
        if (value == null) {
            attributes.remove(key);
        } else {
            attributes.put(key, value);
        }
    }
}
