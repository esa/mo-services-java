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

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NamedValue;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.NullableAttribute;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * The UpdateKeyValues class holds the keyValues with the respective domain and
 * area/service/operation numbers.
 */
public final class UpdateKeyValues {

    private final NamedValueList keyValues;
    private final IdentifierList domain;
    private final UShort area;
    private final UShort service;
    private final UShort operation;

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

    public NullableAttributeList generateNotifyKeyValues(IdentifierList selectedKeys) throws MALException {
        NullableAttributeList newKeyValues = new NullableAttributeList();

        // NULL means that All Keys were selected!
        if (selectedKeys == null) {
            for (NamedValue namedValue : this.getKeyValues()) {
                newKeyValues.add(new NullableAttribute(namedValue.getValue()));
            }

            return newKeyValues;
        }

        for (Identifier selectedKey : selectedKeys) {
            boolean found = false;

            for (NamedValue namedValue : this.getKeyValues()) {
                if (selectedKey.equals(namedValue.getName())) {
                    newKeyValues.add(new NullableAttribute(namedValue.getValue()));
                    found = true;
                    break;
                }
            }

            if (!found) {
                throw new MALException("The selectedKey was not found! selectedKey name: " + selectedKey);
            }
        }

        return newKeyValues;
    }
}
