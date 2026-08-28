package org.ccsds.moims.mo.mps.structures;

/**
 * E1: Multiple planning constraints can be combined using a ConstraintNode.
 * The ConstraintNode specifies the logical operation (AND or OR) to be used
 * when combining a set of constraints together.  As the ConstraintNode is
 * itself defined as a sub-type of Constraint, it is possible to construct
 * a tree of ConstraintNodes using different logical operators.
 */
public final class ConstraintNode extends org.ccsds.moims.mo.mps.structures.Constraint {

    private static final long serialVersionUID = 1407374900330525L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330525L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Enumeration specifying the logic for combining multiple Boolean conditions
     * together.  One of {AND, OR}. Default = AND.
     */
    private org.ccsds.moims.mo.mps.structures.LogicOpEnum operator;

    /**
     * The set of Constraints to be combined.  Must contain at least one element.
     */
    private org.ccsds.moims.mo.mps.structures.ConstraintList constraints;

    /**
     * Default constructor for ConstraintNode.
     * 
     */
    public ConstraintNode() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param negate Specifies whether the result of combining the Constraints is to be inverted (NOT function). Default = False.
     * @param operator Enumeration specifying the logic for combining multiple Boolean conditions together.  One of {AND, OR}. Default = AND.
     * @param constraints The set of Constraints to be combined.  Must contain at least one element.
     */
    public ConstraintNode(Boolean negate,
            org.ccsds.moims.mo.mps.structures.LogicOpEnum operator,
            org.ccsds.moims.mo.mps.structures.ConstraintList constraints) {
        super(negate);
        this.operator = operator;
        this.constraints = constraints;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param constraints The set of Constraints to be combined.  Must contain at least one element.
     */
    public ConstraintNode(org.ccsds.moims.mo.mps.structures.ConstraintList constraints) {
        this.operator = null;
        this.constraints = constraints;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.ConstraintNode();
    }

    /**
     * Returns the field operator.
     * 
     * @return The field operator
     */
    public org.ccsds.moims.mo.mps.structures.LogicOpEnum getOperator() {
        return operator;
    }

    /**
     * Returns the field constraints.
     * 
     * @return The field constraints
     */
    public org.ccsds.moims.mo.mps.structures.ConstraintList getConstraints() {
        return constraints;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ConstraintNode) {
            if (! super.equals(obj)) {
                return false;
            }
            ConstraintNode other = (ConstraintNode) obj;
            if (operator == null) {
                if (other.operator != null) {
                    return false;
                }
            } else {
                if (! operator.equals(other.operator)) {
                    return false;
                }
            }
            if (constraints == null) {
                if (other.constraints != null) {
                    return false;
                }
            } else {
                if (! constraints.equals(other.constraints)) {
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
        hash = 83 * hash + (operator != null ? operator.hashCode() : 0);
        hash = 83 * hash + (constraints != null ? constraints.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ConstraintNode: ");
        buf.append(super.toString());
        buf.append(", operator=").append(operator);
        buf.append(", constraints=").append(constraints);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (constraints == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'constraints' cannot be null!");
        }
        encoder.encodeNullableElement(operator);
        encoder.encodeElement(constraints);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        operator = (org.ccsds.moims.mo.mps.structures.LogicOpEnum) decoder.decodeNullableElement(org.ccsds.moims.mo.mps.structures.LogicOpEnum.AND);
        constraints = (org.ccsds.moims.mo.mps.structures.ConstraintList) decoder.decodeElement(new org.ccsds.moims.mo.mps.structures.ConstraintList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
