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
package org.ccsds.moims.mo.mal.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;

/**
 * Class representing MAL ObjectRef type.
 *
 * @param <T> The type of the MO Object.
 */
public class ObjectRef<T extends Element> implements Attribute {

    private final IdentifierList domain;
    private final Identifier area;
    private final Identifier type;
    private final Identifier key;
    private final UInteger objectVersion;

    /**
     * Default constructor.
     */
    public ObjectRef() {
        this.domain = new IdentifierList();
        this.area = new Identifier();
        this.type = new Identifier();
        this.key = new Identifier();
        this.objectVersion = new UInteger();
    }

    /**
     * Constructor.
     *
     * @param domain The domain.
     * @param area The area.
     * @param type The type.
     * @param key The key.
     * @param objectVersion The object version.
     */
    public ObjectRef(final IdentifierList domain, final Identifier area,
            final Identifier type, final Identifier key, final UInteger objectVersion) {
        this.domain = domain;
        this.area = area;
        this.type = type;
        this.key = key;
        this.objectVersion = objectVersion;
    }

    @Override
    public Element createElement() {
        return new ObjectRef();
    }

    /**
     * Returns the domain.
     *
     * @return the domain.
     */
    public IdentifierList getDomain() {
        return domain;
    }

    /**
     * Returns the area.
     *
     * @return the area.
     */
    public Identifier getArea() {
        return area;
    }

    /**
     * Returns the type.
     *
     * @return the type.
     */
    public Identifier getType() {
        return type;
    }

    /**
     * Returns the key.
     *
     * @return the key.
     */
    public Identifier getKey() {
        return key;
    }

    /**
     * Returns the object version.
     *
     * @return the object version.
     */
    public UInteger getObjectVersion() {
        return objectVersion;
    }

    @Override
    public Long getShortForm() {
        return Attribute.OBJECTREF_SHORT_FORM;
    }

    @Override
    public Integer getTypeShortForm() {
        return Attribute.OBJECTREF_TYPE_SHORT_FORM;
    }

    @Override
    public UShort getAreaNumber() {
        return UShort.ATTRIBUTE_AREA_NUMBER;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.UOctet getAreaVersion() {
        return UOctet.AREA_VERSION;
    }

    @Override
    public UShort getServiceNumber() {
        return UShort.ATTRIBUTE_SERVICE_NUMBER;
    }

    @Override
    public void encode(final MALEncoder encoder) throws MALException {
        encoder.encodeObjectRef(this);
    }

    @Override
    public Element decode(final MALDecoder decoder) throws MALException {
        return decoder.decodeObjectRef();

    }

    @Override
    public boolean equals(final Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ObjectRef)) {
            return false;
        }
        if (!domain.equals(((ObjectRef) obj).getDomain())) {
            return false;
        }
        if (!area.equals(((ObjectRef) obj).getArea())) {
            return false;
        }
        if (!type.equals(((ObjectRef) obj).getType())) {
            return false;
        }
        if (!key.equals(((ObjectRef) obj).getKey())) {
            return false;
        }
        if (!objectVersion.equals(((ObjectRef) obj).getObjectVersion())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return domain.hashCode() + area.hashCode() + type.hashCode()
                + key.hashCode() + objectVersion.hashCode();
    }

    @Override
    public String toString() {
        return String.valueOf(domain) + ":" + String.valueOf(area) + ":"
                + String.valueOf(type) + ":" + String.valueOf(key) + ":"
                + String.valueOf(objectVersion) + ":";
    }
    private static final long serialVersionUID = Attribute.OBJECTREF_SHORT_FORM;
}
