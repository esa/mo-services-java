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
package esa.mo.mal.impl.pubsub;

import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NamedValue;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.NullableAttribute;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Simple class that represents a MAL update key.
 */
public final class UpdateKeyValues {

    /**
     * The domain of the update.
     */
    private final IdentifierList domain;

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
     * The keyValues of the update.
     */
    private final NamedValueList keyValues;

    /**
     * Constructor.
     *
     * @param srcHdr Update message header.
     * @param domainId Update domain.
     * @param keyValues Key values.
     */
    public UpdateKeyValues(final MALMessageHeader srcHdr,
            final IdentifierList domainId, final NamedValueList keyValues) {
        this(domainId, srcHdr.getServiceArea(), srcHdr.getService(), srcHdr.getOperation(), keyValues);
    }

    /**
     * Constructor.
     *
     * @param domain domain provided by provider
     * @param area area provided by provider
     * @param service service provided by provider
     * @param operation operation provided by provider
     * @param keyValues key value provided by provider
     */
    public UpdateKeyValues(final IdentifierList domain, final UShort area,
            final UShort service, final UShort operation, final NamedValueList keyValues) {
        this.domain = domain;
        this.area = area;
        this.service = service;
        this.operation = operation;
        this.keyValues = keyValues;
    }

    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append('[');
        buf.append(this.domain).append(':');
        buf.append(this.area).append(':');
        buf.append(this.service).append(':');
        buf.append(this.operation).append(':');
        buf.append(keyValues.toString());
        buf.append(']');
        return buf.toString();
    }

    /**
     * Returns the domain string.
     *
     * @return the domain.
     */
    public IdentifierList getDomain() {
        return domain;
    }

    /**
     * Returns the keyValues string.
     *
     * @return the keyValues.
     */
    public NamedValueList getKeyValues() {
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

    public NullableAttributeList selectKeys(IdentifierList selectedKeys) {
        NullableAttributeList newKeyValues = new NullableAttributeList();

        // No keys were selected!
        if (selectedKeys == null) {
            for (NamedValue namedValue : this.getKeyValues()) {
                newKeyValues.add(new NullableAttribute(namedValue.getValue())); // Exact copy as of now...
            }

            return newKeyValues;
        }

        return newKeyValues;
    }
}
