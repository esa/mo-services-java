package org.ccsds.moims.mo.mps.structures;

/**
 * E5: The simple resource constraint must be satisfied for the duration of
 * the planning activity to which the constraint applies.
 */
public final class SimpleResourceConstraint extends org.ccsds.moims.mo.mps.structures.ResourceConstraint {

    private static final long serialVersionUID = 1407374900330533L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330533L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Value (of same type as the referenced Resource) to be compared against.
     * MAL Attribute type must match the dataType of the Resource definition.
     */
    private org.ccsds.moims.mo.mal.structures.Attribute value;

    /**
     * Default constructor for SimpleResourceConstraint.
     * 
     */
    public SimpleResourceConstraint() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param negate Specifies whether the result of combining the Constraints is to be inverted (NOT function). Default = False.
     * @param resourceRef Identifies the planning resource that is constrained for the duration of the planning activity.
     * @param comparator Comparison operator, which may be one of: =, !=, _, _=, _, _=, contains, icontains. The contains operator only applies to strings and may be case sensitive or insensitive.
     * @param value Value (of same type as the referenced Resource) to be compared against.  MAL Attribute type must match the dataType of the Resource definition.
     */
    public SimpleResourceConstraint(Boolean negate,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Resource> resourceRef,
            org.ccsds.moims.mo.mps.structures.ExpressionOperatorEnum comparator,
            org.ccsds.moims.mo.mal.structures.Attribute value) {
        super(negate,
            resourceRef,
            comparator);
        this.value = value;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param resourceRef Identifies the planning resource that is constrained for the duration of the planning activity.
     * @param comparator Comparison operator, which may be one of: =, !=, _, _=, _, _=, contains, icontains. The contains operator only applies to strings and may be case sensitive or insensitive.
     * @param value Value (of same type as the referenced Resource) to be compared against.  MAL Attribute type must match the dataType of the Resource definition.
     */
    public SimpleResourceConstraint(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Resource> resourceRef,
            org.ccsds.moims.mo.mps.structures.ExpressionOperatorEnum comparator,
            org.ccsds.moims.mo.mal.structures.Attribute value) {
        super(resourceRef,
            comparator);
        this.value = value;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.SimpleResourceConstraint();
    }

    /**
     * Returns the field value.
     * 
     * @return The field value
     */
    public org.ccsds.moims.mo.mal.structures.Attribute getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SimpleResourceConstraint) {
            if (! super.equals(obj)) {
                return false;
            }
            SimpleResourceConstraint other = (SimpleResourceConstraint) obj;
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
        int hash = super.hashCode();
        hash = 83 * hash + (value != null ? value.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(SimpleResourceConstraint: ");
        buf.append(super.toString());
        buf.append(", value=").append(value);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (value == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'value' cannot be null!");
        }
        encoder.encodeAttribute(value);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        value = (org.ccsds.moims.mo.mal.structures.Attribute) decoder.decodeAttribute();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
