/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java API
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
package org.ccsds.moims.mo.mal.transport.chain;

import java.nio.ByteBuffer;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * The in-flight unit threaded through the chain: one per message. It flows
 * <em>down</em> the stages on send and <em>up</em> the stages on receive. A
 * stage transforms it in place and forwards it to the next stage (see
 * {@link Stage}).
 *
 * <p>
 * On the way down, the body is progressively turned into bytes; on the way up,
 * the bytes are turned back into a body. Between a stage and the next, either
 * {@link #body()} or {@link #bytes()} is the live representation, and the other
 * may be null.
 *
 * <p>
 * The MAL message header is frozen by the standard and must not be rebuilt here;
 * only the {@link #supplements() supplements} are mutable, which is where
 * extensibility information such as priority is carried.
 */
public interface Exchange {

    /**
     * @return The MAL message header. The standard header fields are read-only;
     * only the {@link #supplements()} may be changed. Never null.
     */
    MALMessageHeader header();

    /**
     * @return The decoded message body, or null once the body has been encoded
     * into {@link #bytes()}.
     */
    Object body();

    /**
     * @param body The decoded body to set, or null to clear it.
     */
    void setBody(Object body);

    /**
     * @return The encoded / framed bytes, or null before the body has been
     * encoded.
     */
    ByteBuffer bytes();

    /**
     * @param bytes The encoded / framed bytes to set, or null to clear them.
     */
    void setBytes(ByteBuffer bytes);

    /**
     * @return The MAL QoS level carried in the header. Never null.
     */
    QoSLevel qos();

    /**
     * @return The resolved message priority. When no priority was carried on the
     * wire the default ({@code NORMAL}) is returned, so callers never see null.
     */
    UOctet priority();

    /**
     * @return The header supplements, the mutable extensibility channel. Never
     * null.
     */
    NamedValueList supplements();

    /**
     * @return The capabilities the {@link Link} carrying this exchange actually
     * provides. Never null.
     */
    CapabilitySet channelCapabilities();

    /**
     * Reads a per-exchange scratch attribute previously set by an earlier stage.
     *
     * @param <T> The value type bound to the key.
     * @param key The attribute key. Must not be null.
     * @return The value, or null if the key was not set on this exchange.
     */
    <T> T attr(Attr<T> key);

    /**
     * Sets a per-exchange scratch attribute, to be read by a later stage on the
     * same message.
     *
     * @param <T> The value type bound to the key.
     * @param key The attribute key. Must not be null.
     * @param value The value to store, or null to clear it.
     */
    <T> void setAttr(Attr<T> key, T value);
}
