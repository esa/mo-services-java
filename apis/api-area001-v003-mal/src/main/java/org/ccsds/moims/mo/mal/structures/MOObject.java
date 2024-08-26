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

/**
 * The MOObject abstract class represents the MO Object type.
 */
public abstract class MOObject implements Composite {

    private ObjectIdentity objectIdentity;

    /**
     * Default Constructor.
     */
    public MOObject() {
        this.objectIdentity = new ObjectIdentity();
    }

    /**
     * Constructor.
     *
     * @param objectIdentity The identity of the MO Object.
     */
    public MOObject(ObjectIdentity objectIdentity) {
        this.objectIdentity = objectIdentity;
    }

    /**
     * Returns the Object Identity.
     *
     * @return The object identity.
     */
    public ObjectIdentity getObjectIdentity() {
        return objectIdentity;
    }

    /**
     * Returns a reference to this MO Object.
     *
     * @return The Object reference to this MO Object.
     */
    public ObjectRef getObjectRef() {
        if (objectIdentity == null) {
            throw new NullPointerException("The objectIdentity cannot be null!");
        }
        if (objectIdentity.getDomain() == null) {
            throw new NullPointerException("The objectIdentity.domain cannot be null!");
        }
        if (objectIdentity.getKey() == null) {
            throw new NullPointerException("The objectIdentity.key cannot be null!");
        }
        if (objectIdentity.getVersion() == null) {
            throw new NullPointerException("The objectIdentity.version cannot be null!");
        }

        return new ObjectRef(objectIdentity.getDomain(),
                this.getTypeId().getTypeId(),
                objectIdentity.getKey(),
                objectIdentity.getVersion()
        );
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        encoder.encodeElement(objectIdentity);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        objectIdentity = (ObjectIdentity) decoder.decodeElement(new ObjectIdentity());
        return this;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("objectIdentity=");
        buf.append(objectIdentity);
        return buf.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MOObject)) {
            return false;
        }
        return this.objectIdentity.equals(((MOObject) obj).getObjectIdentity());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (objectIdentity == null ? 0 : objectIdentity.hashCode());
        return hash;
    }
}
