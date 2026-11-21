package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * E1: Multiple planning constraints can be combined using a ConstraintNode.
 * The ConstraintNode specifies the logical operation (AND or OR) to be used
 * when combining a set of constraints together.  As the ConstraintNode is
 * itself defined as a sub-type of Constraint, it is possible to construct
 * a tree of ConstraintNodes using different logical operators.
 */
public final class ConstraintNode extends Constraint {

    private static final long serialVersionUID = 1407374900330525L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330525L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Enumeration specifying the logic for combining multiple Boolean conditions
     * together.  One of {AND, OR}. Default = AND.
     */
    private LogicOpEnum operator;

    /**
     * The set of Constraints to be combined.  Must contain at least one element.
     */
    private ConstraintList constraints;

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
            LogicOpEnum operator,
            ConstraintList constraints) {
        super(negate);
        this.operator = operator;
        this.constraints = constraints;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param constraints The set of Constraints to be combined.  Must contain at least one element.
     */
    public ConstraintNode(ConstraintList constraints) {
        this.operator = null;
        this.constraints = constraints;
    }

    @Override
    public Element createElement() {
        return new ConstraintNode();
    }

    /**
     * Returns the field operator.
     * 
     * @return The field operator
     */
    public LogicOpEnum getOperator() {
        return operator;
    }

    /**
     * Returns the field constraints.
     * 
     * @return The field constraints
     */
    public ConstraintList getConstraints() {
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
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (constraints == null) {
            throw new MALException("The field 'constraints' cannot be null!");
        }
        encoder.encodeNullableElement(operator);
        encoder.encodeElement(constraints);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        operator = (LogicOpEnum) decoder.decodeNullableElement(LogicOpEnum.AND);
        constraints = (ConstraintList) decoder.decodeElement(new ConstraintList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
