package org.ccsds.moims.mo.comprototype.eventtest.structures;

/**
 * Holds object creation details.
.
 */
public final class ObjectCreation implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 56295003948843009L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 56295003948843009L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The success result of the creation.
.
     */
    private Boolean success;

    /**
     * The description field of the created object.
.
     */
    private String description;

    /**
     * Default constructor for ObjectCreation.
     * 
     */
    public ObjectCreation() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param success The success result of the creation.

     * @param description The description field of the created object.

     */
    public ObjectCreation(Boolean success,
            String description) {
        this.success = success;
        this.description = description;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectCreation();
    }

    /**
     * Returns the field success.
     * 
     * @return The field success
     */
    public Boolean getSuccess() {
        return success;
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
        if (obj instanceof ObjectCreation) {
            ObjectCreation other = (ObjectCreation) obj;
            if (success == null) {
                if (other.success != null) {
                    return false;
                }
            } else {
                if (! success.equals(other.success)) {
                    return false;
                }
            }
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
        hash = 83 * hash + (success != null ? success.hashCode() : 0);
        hash = 83 * hash + (description != null ? description.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ObjectCreation: ");
        buf.append("success=").append(success);
        buf.append(", description=").append(description);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (success == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'success' cannot be null!");
        }
        if (description == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'description' cannot be null!");
        }
        encoder.encodeBoolean(success);
        encoder.encodeString(description);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        success = decoder.decodeBoolean();
        description = decoder.decodeString();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
