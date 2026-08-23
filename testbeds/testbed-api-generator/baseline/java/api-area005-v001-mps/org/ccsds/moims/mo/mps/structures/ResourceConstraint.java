package org.ccsds.moims.mo.mps.structures;

/**
 * E5: ResourceConstraint is an abstract type that represents a constraint
 * expressed in terms of the value of a given Resource.
 */
public abstract class ResourceConstraint extends org.ccsds.moims.mo.mps.structures.Constraint {

    /**
     * Identifies the planning resource that is constrained for the duration of
     * the planning activity.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Resource> resourceRef;

    /**
     * Comparison operator, which may be one of: =, !=, _, _=, _, _=, contains,
     * icontains. The contains operator only applies to strings and may be case
     * sensitive or insensitive.
     */
    private org.ccsds.moims.mo.mps.structures.ExpressionOperatorEnum comparator;

    /**
     * Default constructor for ResourceConstraint.
     * 
     */
    public ResourceConstraint() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param negate Specifies whether the result of combining the Constraints is to be inverted (NOT function). Default = False.
     * @param resourceRef Identifies the planning resource that is constrained for the duration of the planning activity.
     * @param comparator Comparison operator, which may be one of: =, !=, _, _=, _, _=, contains, icontains. The contains operator only applies to strings and may be case sensitive or insensitive.
     */
    public ResourceConstraint(Boolean negate,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Resource> resourceRef,
            org.ccsds.moims.mo.mps.structures.ExpressionOperatorEnum comparator) {
        super(negate);
        this.resourceRef = resourceRef;
        this.comparator = comparator;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param resourceRef Identifies the planning resource that is constrained for the duration of the planning activity.
     * @param comparator Comparison operator, which may be one of: =, !=, _, _=, _, _=, contains, icontains. The contains operator only applies to strings and may be case sensitive or insensitive.
     */
    public ResourceConstraint(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Resource> resourceRef,
            org.ccsds.moims.mo.mps.structures.ExpressionOperatorEnum comparator) {
        this.resourceRef = resourceRef;
        this.comparator = comparator;
    }

    /**
     * Returns the field resourceRef.
     * 
     * @return The field resourceRef
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Resource> getResourceRef() {
        return resourceRef;
    }

    /**
     * Returns the field comparator.
     * 
     * @return The field comparator
     */
    public org.ccsds.moims.mo.mps.structures.ExpressionOperatorEnum getComparator() {
        return comparator;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ResourceConstraint) {
            if (! super.equals(obj)) {
                return false;
            }
            ResourceConstraint other = (ResourceConstraint) obj;
            if (resourceRef == null) {
                if (other.resourceRef != null) {
                    return false;
                }
            } else {
                if (! resourceRef.equals(other.resourceRef)) {
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
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 83 * hash + (resourceRef != null ? resourceRef.hashCode() : 0);
        hash = 83 * hash + (comparator != null ? comparator.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ResourceConstraint: ");
        buf.append(super.toString());
        buf.append(", resourceRef=").append(resourceRef);
        buf.append(", comparator=").append(comparator);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (resourceRef == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'resourceRef' cannot be null!");
        }
        if (comparator == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'comparator' cannot be null!");
        }
        encoder.encodeElement(resourceRef);
        encoder.encodeElement(comparator);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        resourceRef = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Resource>) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Resource>());
        comparator = (org.ccsds.moims.mo.mps.structures.ExpressionOperatorEnum) decoder.decodeElement(org.ccsds.moims.mo.mps.structures.ExpressionOperatorEnum.EQUAL);
        return this;
    }

}
