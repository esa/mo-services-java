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

import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Simple class that represents a MAL update key.
 */
public final class UpdateKeyValues {

    /**
     * The domain of the update.
     */
    private final String domain;
    /**
     * The keyValues of the update.
     */
    private final AttributeList keyValues;
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
     * Hash function magic number.
     */
    protected static final int HASH_MAGIC_NUMBER = 47;

    /**
     * Constructor.
     *
     * @param srcHdr Update message header.
     * @param domainId Update domain.
     * @param key Entity key.
     */
    public UpdateKeyValues(final MALMessageHeader srcHdr, final String domainId, final AttributeList keyValues) {
        this.domain = domainId;
        this.keyValues = keyValues;
        this.area = srcHdr.getServiceArea();
        this.service = srcHdr.getService();
        this.operation = srcHdr.getOperation();
    }

    /*
    @Override
    public boolean equals(final Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        final UpdateKeyValues other = (UpdateKeyValues) obj;
        if (this.domain == null ? other.domain != null : !this.domain.equals(other.domain)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return HASH_MAGIC_NUMBER * super.hashCode() + (this.domain != null ? this.domain.hashCode() : 0);
    }
    */

    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append('[');
        buf.append(this.domain).append(':');
        buf.append(this.area).append(':');
        buf.append(this.service).append(':');
        buf.append(this.operation).append(':');
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
     * Returns the keyValues string.
     *
     * @return the keyValues.
     */
    public AttributeList getKeyValues() {
        return keyValues;
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
