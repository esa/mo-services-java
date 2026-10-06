package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * E1: All types of constraint can be considered conditions that are either
 * met or not met when a planning activity is placed in a Plan.  They can
 * therefore be specified as a potentially complex Boolean expression that
 * combines references to the arguments and fields of objects in the MPS information
 * model using operators of various types (arithmetic, comparative, logical,
 * string, temporal, and geometric).  The expression must evaluate to True
 * for the constraint to be met. As introduced in 1.2, this Recommended Standard
 * does not define a full expression language capable of supporting such complex
 * Boolean expressions.  It does, however, support the use of externally defined
 * expression languages.  The ConstraintExpression type allows for the use
 * of such an expression language to define any type of constraint, providing
 * communicating entities all have the capability to evaluate that expression
 * language.
 */
public final class ConstraintExpression extends Constraint {

    private static final long serialVersionUID = 1407374900330527L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330527L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Potentially complex conditional expression that must evaluate to True for
     * the constraint to be met.
     */
    private Element constraint;

    /**
     * Default constructor for ConstraintExpression.
     * 
     */
    public ConstraintExpression() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param negate Specifies whether the result of combining the Constraints is to be inverted (NOT function). Default = False.
     * @param constraint Potentially complex conditional expression that must evaluate to True for the constraint to be met.
     */
    public ConstraintExpression(Boolean negate,
            Element constraint) {
        super(negate);
        this.constraint = constraint;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param constraint Potentially complex conditional expression that must evaluate to True for the constraint to be met.
     */
    public ConstraintExpression(Element constraint) {
        this.constraint = constraint;
    }

    @Override
    public Element createElement() {
        return new ConstraintExpression();
    }

    /**
     * Returns the field constraint.
     * 
     * @return The field constraint
     */
    public Element getConstraint() {
        return constraint;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ConstraintExpression) {
            if (! super.equals(obj)) {
                return false;
            }
            ConstraintExpression other = (ConstraintExpression) obj;
            if (constraint == null) {
                if (other.constraint != null) {
                    return false;
                }
            } else {
                if (! constraint.equals(other.constraint)) {
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
        hash = 83 * hash + (constraint != null ? constraint.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ConstraintExpression: ");
        buf.append(super.toString());
        buf.append(", constraint=").append(constraint);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (constraint == null) {
            throw new MALException("The field 'constraint' cannot be null!");
        }
        encoder.encodeAbstractElement(constraint);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        constraint = (Element) decoder.decodeAbstractElement();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
