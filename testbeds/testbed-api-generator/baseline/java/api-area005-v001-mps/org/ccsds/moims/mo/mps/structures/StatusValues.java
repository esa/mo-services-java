package org.ccsds.moims.mo.mps.structures;

/**
 * E1: Concrete sub-type of ValidationDetails that provides additional fields
 * to support data validation and interpretation for integer type arguments
 * that are effectively enumerated Statuses.
 */
public final class StatusValues extends org.ccsds.moims.mo.mps.structures.ValidationDetails {

    private static final long serialVersionUID = 1407374900330524L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330524L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Set of allowed State definitions (see 4.6.2.4.2), comprising the enumerated
     * value and an associated text label.
     */
    private org.ccsds.moims.mo.mps.structures.StateDefList allowedValues;

    /**
     * Default constructor for StatusValues.
     * 
     */
    public StatusValues() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param allowedValues Set of allowed State definitions (see 4.6.2.4.2), comprising the enumerated value and an associated text label.
     */
    public StatusValues(org.ccsds.moims.mo.mps.structures.StateDefList allowedValues) {
        this.allowedValues = allowedValues;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.StatusValues();
    }

    /**
     * Returns the field allowedValues.
     * 
     * @return The field allowedValues
     */
    public org.ccsds.moims.mo.mps.structures.StateDefList getAllowedValues() {
        return allowedValues;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StatusValues) {
            if (! super.equals(obj)) {
                return false;
            }
            StatusValues other = (StatusValues) obj;
            if (allowedValues == null) {
                if (other.allowedValues != null) {
                    return false;
                }
            } else {
                if (! allowedValues.equals(other.allowedValues)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 83 * hash + (allowedValues != null ? allowedValues.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(StatusValues: ");
        buf.append(super.toString());
        buf.append(", allowedValues=").append(allowedValues);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (allowedValues == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'allowedValues' cannot be null!");
        }
        encoder.encodeElement(allowedValues);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        allowedValues = (org.ccsds.moims.mo.mps.structures.StateDefList) decoder.decodeElement(new org.ccsds.moims.mo.mps.structures.StateDefList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
