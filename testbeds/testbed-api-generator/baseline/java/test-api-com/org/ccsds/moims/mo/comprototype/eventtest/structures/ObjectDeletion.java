package org.ccsds.moims.mo.comprototype.eventtest.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * Holds object deletion details.
.
 */
public final class ObjectDeletion implements Composite {

    private static final long serialVersionUID = 56295003948843010L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 56295003948843010L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Description field for object to be deleted.
.
     */
    private String description;

    /**
     * Default constructor for ObjectDeletion.
     * 
     */
    public ObjectDeletion() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param description Description field for object to be deleted.

     */
    public ObjectDeletion(String description) {
        this.description = description;
    }

    @Override
    public Element createElement() {
        return new ObjectDeletion();
    }

    /**
     * Returns the field description.
     * 
     * @return The field description
     */
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ObjectDeletion) {
            ObjectDeletion other = (ObjectDeletion) obj;
            if (description == null) {
                if (other.description != null) {
                    return false;
                }
            } else {
                if (! description.equals(other.description)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (description != null ? description.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ObjectDeletion: ");
        buf.append("description=").append(description);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (description == null) {
            throw new MALException("The field 'description' cannot be null!");
        }
        encoder.encodeString(description);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        description = decoder.decodeString();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
