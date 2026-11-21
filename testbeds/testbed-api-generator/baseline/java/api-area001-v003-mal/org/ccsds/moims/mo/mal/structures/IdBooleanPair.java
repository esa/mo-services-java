package org.ccsds.moims.mo.mal.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;

/**
 * IdBooleanPair shall be a simple pair type of an identifier and Boolean
 * value.
 */
public final class IdBooleanPair implements Composite {

    private static final long serialVersionUID = 281475027043308L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475027043308L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The Identifier value.
     */
    private Identifier id;

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
    public IdBooleanPair(Identifier id,
            Boolean value) {
        this.id = id;
        this.value = value;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param id The Identifier value.
     */
    public IdBooleanPair(Identifier id) {
        this.id = id;
        this.value = null;
    }

    @Override
    public Element createElement() {
        return new IdBooleanPair();
    }

    /**
     * Returns the field id.
     * 
     * @return The field id
     */
    public Identifier getId() {
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
    public void encode(MALEncoder encoder) throws MALException {
        if (id == null) {
            throw new MALException("The field 'id' cannot be null!");
        }
        encoder.encodeIdentifier(id);
        encoder.encodeNullableBoolean(value);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        id = decoder.decodeIdentifier();
        value = decoder.decodeNullableBoolean();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
