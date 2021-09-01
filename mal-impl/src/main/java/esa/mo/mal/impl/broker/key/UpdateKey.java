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
package esa.mo.mal.impl.broker.key;

import org.ccsds.moims.mo.mal.structures.EntityKey;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Simple class that represents a MAL update key.
 */
public final class UpdateKey extends PublisherKey {

    /**
     * The domain of the update.
     */
    private final String domain;
    /**
     * The area of the update.
     */
    private final UShort area;
    /**
     * The service of the update.
     */
    private final UShort service;
    /**
     * The operation of the update.
     */
    private final UShort operation;

    /**
     * Constructor.
     *
     * @param srcHdr Update message header.
     * @param domainId Update domain.
     * @param key Entity key.
     */
    public UpdateKey(final MALMessageHeader srcHdr, final String domainId, final EntityKey key) {
        super(key);

        this.domain = domainId;
        this.area = srcHdr.getServiceArea();
        this.service = srcHdr.getService();
        this.operation = srcHdr.getOperation();
    }

    @Override
    public boolean equals(final Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        final UpdateKey other = (UpdateKey) obj;
        return this.domain == null ? other.domain == null : this.domain.equals(other.domain);
    }

    @Override
    public int hashCode() {
        return HASH_MAGIC_NUMBER * super.hashCode() + (this.domain != null ? this.domain.hashCode() : 0);
    }

    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append('[');
        buf.append(this.domain);
        buf.append(':');
        buf.append(this.area);
        buf.append(':');
        buf.append(this.service);
        buf.append(':');
        buf.append(this.operation);
        buf.append(':');
        buf.append(super.toString());
        buf.append(']');
        return buf.toString();
    }

    /**
     * Returns the domain string.
     *
     * @return the domain.
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Returns the area number.
     *
     * @return the area.
     */
    public UShort getArea() {
        return area;
    }

    /**
     * Returns the service number.
     *
     * @return the service.
     */
    public UShort getService() {
        return service;
    }

    /**
     * Returns the operation number.
     *
     * @return the operation.
     */
    public UShort getOperation() {
        return operation;
    }
}
