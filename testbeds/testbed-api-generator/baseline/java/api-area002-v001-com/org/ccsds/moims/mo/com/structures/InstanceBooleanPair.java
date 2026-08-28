package org.ccsds.moims.mo.com.structures;

/**
 * Simple pair of an object instance identifier and a Boolean value.
 */
public final class InstanceBooleanPair implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 562949970198533L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 562949970198533L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The object instance identifier.
     */
    private Long id;

    /**
     * An associated Boolean value.
     */
    private Boolean value;

    /**
     * Default constructor for InstanceBooleanPair.
     * 
     */
    public InstanceBooleanPair() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param id The object instance identifier.
     * @param value An associated Boolean value.
     */
    public InstanceBooleanPair(Long id,
            Boolean value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.com.structures.InstanceBooleanPair();
    }

    /**
     * Returns the field id.
     * 
     * @return The field id
     */
    public Long getId() {
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
        if (obj instanceof InstanceBooleanPair) {
            InstanceBooleanPair other = (InstanceBooleanPair) obj;
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
        buf.append("(InstanceBooleanPair: ");
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
        if (value == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'value' cannot be null!");
        }
        encoder.encodeLong(id);
        encoder.encodeBoolean(value);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        id = decoder.decodeLong();
        value = decoder.decodeBoolean();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
