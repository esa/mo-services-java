package org.ccsds.moims.mo.mps.structures;

/**
 * E1: Status values may be represented as enumerated Integers, but the enumeration
 * is not defined by the Recommended Standard, but in the context of planning
 * configuration data.  StateDefs hold the definitions of the text labels
 * associated with specific status values.
 */
public final class StateDef implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1407374900330500L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330500L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Enumerated value of the Status.
     */
    private Integer value;

    /**
     * Text label associated with the enumerated value.
     */
    private String state;

    /**
     * Default constructor for StateDef.
     * 
     */
    public StateDef() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param value Enumerated value of the Status.
     * @param state Text label associated with the enumerated value.
     */
    public StateDef(Integer value,
            String state) {
        this.value = value;
        this.state = state;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.StateDef();
    }

    /**
     * Returns the field value.
     * 
     * @return The field value
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Returns the field state.
     * 
     * @return The field state
     */
    public String getState() {
        return state;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StateDef) {
            StateDef other = (StateDef) obj;
            if (value == null) {
                if (other.value != null) {
                    return false;
                }
            } else {
                if (! value.equals(other.value)) {
                    return false;
                }
            }
            if (state == null) {
                if (other.state != null) {
                    return false;
                }
            } else {
                if (! state.equals(other.state)) {
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
        hash = 83 * hash + (value != null ? value.hashCode() : 0);
        hash = 83 * hash + (state != null ? state.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(StateDef: ");
        buf.append("value=").append(value);
        buf.append(", state=").append(state);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (value == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'value' cannot be null!");
        }
        if (state == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'state' cannot be null!");
        }
        encoder.encodeInteger(value);
        encoder.encodeString(state);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        value = decoder.decodeInteger();
        state = decoder.decodeString();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
