package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ObjectRef;

/**
 * E5: The simple resource constraint must be satisfied for the duration of
 * the planning activity to which the constraint applies.
 */
public final class SimpleResourceConstraint extends ResourceConstraint {

    private static final long serialVersionUID = 1407374900330533L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330533L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Value (of same type as the referenced Resource) to be compared against.
     * MAL Attribute type must match the dataType of the Resource definition.
     */
    private Attribute value;

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
            ObjectRef<Resource> resourceRef,
            ExpressionOperatorEnum comparator,
            Attribute value) {
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
    public SimpleResourceConstraint(ObjectRef<Resource> resourceRef,
            ExpressionOperatorEnum comparator,
            Attribute value) {
        super(resourceRef,
            comparator);
        this.value = value;
    }

    @Override
    public Element createElement() {
        return new SimpleResourceConstraint();
    }

    /**
     * Returns the field value.
     * 
     * @return The field value
     */
    public Attribute getValue() {
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
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (value == null) {
            throw new MALException("The field 'value' cannot be null!");
        }
        encoder.encodeAttribute(value);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        value = (Attribute) decoder.decodeAttribute();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
