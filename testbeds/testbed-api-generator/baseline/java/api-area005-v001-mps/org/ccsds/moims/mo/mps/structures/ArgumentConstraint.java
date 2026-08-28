package org.ccsds.moims.mo.mps.structures;

/**
 * E5: An argument constraint may be associated with a planning activity to
 * restrict when it can be planned, based on the value of an argument of the
 * planning activity itself or a related planning activity or event.
 */
public final class ArgumentConstraint extends org.ccsds.moims.mo.mps.structures.Constraint {

    private static final long serialVersionUID = 1407374900330531L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330531L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Object Type: ActivityDefinition | EventDefinition Specifies the definition
     * (class) of the planning activity or planning event whose argument is to
     * be referenced.  If omitted the activity containing the constraint is assumed.
     */
    private org.ccsds.moims.mo.mal.structures.Element objectRef;

    /**
     * Identifies the specific argument of the referenced Object whose value is
     * to be compared.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier argName;

    /**
     * Comparison operator, which may be one of: =, !=, _, _=, _, _=, contains,
     * icontains The contains operator only applies to strings and may be case
     * sensitive or insensitive.
     */
    private org.ccsds.moims.mo.mps.structures.ExpressionOperatorEnum comparator;

    /**
     * Value to be compared against.  The MAL Element subtype must match the argument
     * type supplied in the corresponding ArgDef of the given Argument.
     */
    private org.ccsds.moims.mo.mal.structures.Element value;

    /**
     * Default constructor for ArgumentConstraint.
     * 
     */
    public ArgumentConstraint() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param negate Specifies whether the result of combining the Constraints is to be inverted (NOT function). Default = False.
     * @param objectRef Object Type: ActivityDefinition | EventDefinition Specifies the definition (class) of the planning activity or planning event whose argument is to be referenced.  If omitted the activity containing the constraint is assumed.
     * @param argName Identifies the specific argument of the referenced Object whose value is to be compared
     * @param comparator Comparison operator, which may be one of: =, !=, _, _=, _, _=, contains, icontains The contains operator only applies to strings and may be case sensitive or insensitive.
     * @param value Value to be compared against.  The MAL Element subtype must match the argument type supplied in the corresponding ArgDef of the given Argument.
     */
    public ArgumentConstraint(Boolean negate,
            org.ccsds.moims.mo.mal.structures.Element objectRef,
            org.ccsds.moims.mo.mal.structures.Identifier argName,
            org.ccsds.moims.mo.mps.structures.ExpressionOperatorEnum comparator,
            org.ccsds.moims.mo.mal.structures.Element value) {
        super(negate);
        this.objectRef = objectRef;
        this.argName = argName;
        this.comparator = comparator;
        this.value = value;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param argName Identifies the specific argument of the referenced Object whose value is to be compared
     * @param comparator Comparison operator, which may be one of: =, !=, _, _=, _, _=, contains, icontains The contains operator only applies to strings and may be case sensitive or insensitive.
     * @param value Value to be compared against.  The MAL Element subtype must match the argument type supplied in the corresponding ArgDef of the given Argument.
     */
    public ArgumentConstraint(org.ccsds.moims.mo.mal.structures.Identifier argName,
            org.ccsds.moims.mo.mps.structures.ExpressionOperatorEnum comparator,
            org.ccsds.moims.mo.mal.structures.Element value) {
        this.objectRef = null;
        this.argName = argName;
        this.comparator = comparator;
        this.value = value;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.ArgumentConstraint();
    }

    /**
     * Returns the field objectRef.
     * 
     * @return The field objectRef
     */
    public org.ccsds.moims.mo.mal.structures.Element getObjectRef() {
        return objectRef;
    }

    /**
     * Returns the field argName.
     * 
     * @return The field argName
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getArgName() {
        return argName;
    }

    /**
     * Returns the field comparator.
     * 
     * @return The field comparator
     */
    public org.ccsds.moims.mo.mps.structures.ExpressionOperatorEnum getComparator() {
        return comparator;
    }

    /**
     * Returns the field value.
     * 
     * @return The field value
     */
    public org.ccsds.moims.mo.mal.structures.Element getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ArgumentConstraint) {
            if (! super.equals(obj)) {
                return false;
            }
            ArgumentConstraint other = (ArgumentConstraint) obj;
            if (objectRef == null) {
                if (other.objectRef != null) {
                    return false;
                }
            } else {
                if (! objectRef.equals(other.objectRef)) {
                    return false;
                }
            }
            if (argName == null) {
                if (other.argName != null) {
                    return false;
                }
            } else {
                if (! argName.equals(other.argName)) {
                    return false;
                }
            }
            if (comparator == null) {
                if (other.comparator != null) {
                    return false;
                }
            } else {
                if (! comparator.equals(other.comparator)) {
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
        int hash = super.hashCode();
        hash = 83 * hash + (objectRef != null ? objectRef.hashCode() : 0);
        hash = 83 * hash + (argName != null ? argName.hashCode() : 0);
        hash = 83 * hash + (comparator != null ? comparator.hashCode() : 0);
        hash = 83 * hash + (value != null ? value.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ArgumentConstraint: ");
        buf.append(super.toString());
        buf.append(", objectRef=").append(objectRef);
        buf.append(", argName=").append(argName);
        buf.append(", comparator=").append(comparator);
        buf.append(", value=").append(value);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (argName == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'argName' cannot be null!");
        }
        if (comparator == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'comparator' cannot be null!");
        }
        if (value == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'value' cannot be null!");
        }
        encoder.encodeNullableAbstractElement(objectRef);
        encoder.encodeIdentifier(argName);
        encoder.encodeElement(comparator);
        encoder.encodeAbstractElement(value);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        objectRef = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeNullableAbstractElement();
        argName = decoder.decodeIdentifier();
        comparator = (org.ccsds.moims.mo.mps.structures.ExpressionOperatorEnum) decoder.decodeElement(org.ccsds.moims.mo.mps.structures.ExpressionOperatorEnum.EQUAL);
        value = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
