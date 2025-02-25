/* ----------------------------------------------------------------------------
 * Copyright (C) 2023      European Space Agency
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
import org.ccsds.moims.mo.mal.TypeId;

/**
 * Class representing MAL ObjectRef type.
 *
 * @param <T> The type of the MO Object.
 */
public class ObjectRef<T extends Element> implements Attribute {

    private static final long serialVersionUID = Attribute.OBJECTREF_SHORT_FORM;
    private final IdentifierList domain;
    private final Long absoluteSFP;
    private final Identifier key;
    private final UInteger objectVersion;

    /**
     * Default constructor.
     */
    public ObjectRef() {
        this(new IdentifierList(), 0L, new Identifier(), new UInteger());
    }

    /**
     * Constructor.
     *
     * @param domain The domain.
     * @param typeId The Type Id of the object.
     * @param key The key.
     * @param objectVersion The object version.
     */
    public ObjectRef(final IdentifierList domain, final Long typeId,
            final Identifier key, final UInteger objectVersion) {
        this.domain = domain;
        this.absoluteSFP = typeId;
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
     * Returns the absoluteSFP.
     *
     * @return the absoluteSFP.
     */
    public Long getabsoluteSFP() {
        return absoluteSFP;
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
    public TypeId getTypeId() {
        return new TypeId(Attribute.OBJECTREF_SHORT_FORM);
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
        if (obj == null) {
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
        if (!absoluteSFP.equals(((ObjectRef) obj).getabsoluteSFP())) {
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
        return domain.hashCode() + absoluteSFP.hashCode()
                + key.hashCode() + objectVersion.hashCode();
    }

    @Override
    public String toString() {
        return String.valueOf(domain) + ":" + String.valueOf(absoluteSFP) + ":"
                + String.valueOf(key) + ":" + String.valueOf(objectVersion);
    }
}
