package org.ccsds.moims.mo.mal.structures;

/**
 * IdBooleanPair shall be a simple pair type of an identifier and Boolean
 * value.
 */
public final class IdBooleanPair implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 281475027043308L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475027043308L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The Identifier value.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier id;

    /**
     * The Boolean value.
     */
    private Boolean value;

    /**
     * Default constructor for IdBooleanPair.
     * 
     */
    public IdBooleanPair() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param id The Identifier value.
     * @param value The Boolean value.
     */
    public IdBooleanPair(org.ccsds.moims.mo.mal.structures.Identifier id,
            Boolean value) {
        this.id = id;
        this.value = value;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param id The Identifier value.
     */
    public IdBooleanPair(org.ccsds.moims.mo.mal.structures.Identifier id) {
        this.id = id;
        this.value = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mal.structures.IdBooleanPair();
    }

    /**
     * Returns the field id.
     * 
     * @return The field id
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getId() {
        return id;
    }

    /**
     * Returns the field value.
     * 
     * @return The field value
     */
    public Boolean getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IdBooleanPair) {
            IdBooleanPair other = (IdBooleanPair) obj;
            if (id == null) {
                if (other.id != null) {
                    return false;
                }
            } else {
                if (! id.equals(other.id)) {
                    return false;
                }
            }
            if (value == null) {
                if (other.value != null) {
                    return false;
                }
            } else {
                if (! value.equals(other.value)) {
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
        hash = 83 * hash + (id != null ? id.hashCode() : 0);
        hash = 83 * hash + (value != null ? value.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(IdBooleanPair: ");
        buf.append("id=").append(id);
        buf.append(", value=").append(value);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (id == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'id' cannot be null!");
        }
        encoder.encodeIdentifier(id);
        encoder.encodeNullableBoolean(value);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        id = decoder.decodeIdentifier();
        value = decoder.decodeNullableBoolean();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
