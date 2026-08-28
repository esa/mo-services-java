package org.ccsds.moims.mo.mps.structures;

/**
 * E2: Sequential constraints impose a restriction on the order of planning
 * activities in a Plan with respect to other planning activities and planning
 * events. Two objects are identified: the parent activity and its opponent.
 * The parent activity is the activity for which the constraint is defined.
 * The opponent may be either a planning activity or a planning event and
 * must be placed in the Plan relative to the parent activity.
 */
public final class SequentialConstraint extends org.ccsds.moims.mo.mps.structures.Constraint {

    private static final long serialVersionUID = 1407374900330541L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330541L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Object Type: ActivityDefinition | EventDefinition Specifies the definition
     * (class) of the opponent planning activity or planning event.
     */
    private org.ccsds.moims.mo.mal.structures.Element opponent;

    /**
     * Point on the parent activity that must be followed by the opponent. Default
     * = 1.
     */
    private org.ccsds.moims.mo.mps.structures.Slider parentRef;

    /**
     * Point on the opponent that must follow the parent activity.  This field
     * will be ignored in case the opponent is a planning event. Default = 0.
     */
    private org.ccsds.moims.mo.mps.structures.Slider opponentRef;

    /**
     * Minimum offset between the specified points on the parent activity and
     * the opponent. Default is no offset.
     */
    private org.ccsds.moims.mo.mal.structures.Element minOffset;

    /**
     * Maximum offset between the specified points on the parent activity and
     * the opponent. Default is no offset.
     */
    private org.ccsds.moims.mo.mal.structures.Element maxOffset;

    /**
     * Default constructor for SequentialConstraint.
     * 
     */
    public SequentialConstraint() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param negate Specifies whether the result of combining the Constraints is to be inverted (NOT function). Default = False.
     * @param opponent Object Type: ActivityDefinition | EventDefinition Specifies the definition (class) of the opponent planning activity or planning event.
     * @param parentRef Point on the parent activity that must be followed by the opponent. Default = 1.
     * @param opponentRef Point on the opponent that must follow the parent activity.  This field will be ignored in case the opponent is a planning event. Default = 0.
     * @param minOffset Minimum offset between the specified points on the parent activity and the opponent. Default is no offset.
     * @param maxOffset Maximum offset between the specified points on the parent activity and the opponent. Default is no offset.
     */
    public SequentialConstraint(Boolean negate,
            org.ccsds.moims.mo.mal.structures.Element opponent,
            org.ccsds.moims.mo.mps.structures.Slider parentRef,
            org.ccsds.moims.mo.mps.structures.Slider opponentRef,
            org.ccsds.moims.mo.mal.structures.Element minOffset,
            org.ccsds.moims.mo.mal.structures.Element maxOffset) {
        super(negate);
        this.opponent = opponent;
        this.parentRef = parentRef;
        this.opponentRef = opponentRef;
        this.minOffset = minOffset;
        this.maxOffset = maxOffset;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param opponent Object Type: ActivityDefinition | EventDefinition Specifies the definition (class) of the opponent planning activity or planning event.
     */
    public SequentialConstraint(org.ccsds.moims.mo.mal.structures.Element opponent) {
        this.opponent = opponent;
        this.parentRef = null;
        this.opponentRef = null;
        this.minOffset = null;
        this.maxOffset = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.SequentialConstraint();
    }

    /**
     * Returns the field opponent.
     * 
     * @return The field opponent
     */
    public org.ccsds.moims.mo.mal.structures.Element getOpponent() {
        return opponent;
    }

    /**
     * Returns the field parentRef.
     * 
     * @return The field parentRef
     */
    public org.ccsds.moims.mo.mps.structures.Slider getParentRef() {
        return parentRef;
    }

    /**
     * Returns the field opponentRef.
     * 
     * @return The field opponentRef
     */
    public org.ccsds.moims.mo.mps.structures.Slider getOpponentRef() {
        return opponentRef;
    }

    /**
     * Returns the field minOffset.
     * 
     * @return The field minOffset
     */
    public org.ccsds.moims.mo.mal.structures.Element getMinOffset() {
        return minOffset;
    }

    /**
     * Returns the field maxOffset.
     * 
     * @return The field maxOffset
     */
    public org.ccsds.moims.mo.mal.structures.Element getMaxOffset() {
        return maxOffset;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SequentialConstraint) {
            if (! super.equals(obj)) {
                return false;
            }
            SequentialConstraint other = (SequentialConstraint) obj;
            if (opponent == null) {
                if (other.opponent != null) {
                    return false;
                }
            } else {
                if (! opponent.equals(other.opponent)) {
                    return false;
                }
            }
            if (parentRef == null) {
                if (other.parentRef != null) {
                    return false;
                }
            } else {
                if (! parentRef.equals(other.parentRef)) {
                    return false;
                }
            }
            if (opponentRef == null) {
                if (other.opponentRef != null) {
                    return false;
                }
            } else {
                if (! opponentRef.equals(other.opponentRef)) {
                    return false;
                }
            }
            if (minOffset == null) {
                if (other.minOffset != null) {
                    return false;
                }
            } else {
                if (! minOffset.equals(other.minOffset)) {
                    return false;
                }
            }
            if (maxOffset == null) {
                if (other.maxOffset != null) {
                    return false;
                }
            } else {
                if (! maxOffset.equals(other.maxOffset)) {
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
        hash = 83 * hash + (opponent != null ? opponent.hashCode() : 0);
        hash = 83 * hash + (parentRef != null ? parentRef.hashCode() : 0);
        hash = 83 * hash + (opponentRef != null ? opponentRef.hashCode() : 0);
        hash = 83 * hash + (minOffset != null ? minOffset.hashCode() : 0);
        hash = 83 * hash + (maxOffset != null ? maxOffset.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(SequentialConstraint: ");
        buf.append(super.toString());
        buf.append(", opponent=").append(opponent);
        buf.append(", parentRef=").append(parentRef);
        buf.append(", opponentRef=").append(opponentRef);
        buf.append(", minOffset=").append(minOffset);
        buf.append(", maxOffset=").append(maxOffset);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (opponent == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'opponent' cannot be null!");
        }
        encoder.encodeAbstractElement(opponent);
        encoder.encodeNullableElement(parentRef);
        encoder.encodeNullableElement(opponentRef);
        encoder.encodeNullableAbstractElement(minOffset);
        encoder.encodeNullableAbstractElement(maxOffset);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        opponent = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        parentRef = (org.ccsds.moims.mo.mps.structures.Slider) decoder.decodeNullableElement(new org.ccsds.moims.mo.mps.structures.Slider());
        opponentRef = (org.ccsds.moims.mo.mps.structures.Slider) decoder.decodeNullableElement(new org.ccsds.moims.mo.mps.structures.Slider());
        minOffset = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeNullableAbstractElement();
        maxOffset = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeNullableAbstractElement();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
