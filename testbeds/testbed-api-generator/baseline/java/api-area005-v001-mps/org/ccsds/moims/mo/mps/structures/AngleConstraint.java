package org.ccsds.moims.mo.mps.structures;

/**
 * E7: Specifies a range of values for the angle subtended between three physical
 * objects.  .
 */
public final class AngleConstraint extends org.ccsds.moims.mo.mps.structures.GeometricConstraint {

    private static final long serialVersionUID = 1407374900330540L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330540L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Position of the center object.
     */
    private org.ccsds.moims.mo.mal.structures.Element centerObject;

    /**
     * Position of target object 1.
     */
    private org.ccsds.moims.mo.mal.structures.Element targetObject1;

    /**
     * Position of target object 2.
     */
    private org.ccsds.moims.mo.mal.structures.Element targetObject2;

    /**
     * Minimum angle subtended at the center object by target objects 1 and 2.
     */
    private org.ccsds.moims.mo.mal.structures.Element minAngle;

    /**
     * Maximum angle subtended at the center object by target objects 1 and 2.
     */
    private org.ccsds.moims.mo.mal.structures.Element maxAngle;

    /**
     * Default constructor for AngleConstraint.
     * 
     */
    public AngleConstraint() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param negate Specifies whether the result of combining the Constraints is to be inverted (NOT function). Default = False.
     * @param startRef Identifies the point in the duration of the applicable planning activity to which the start of the constraint period relates. Default is the start of the planning activity.
     * @param endRef Identifies the point in the duration of the applicable planning activity to which the end of the constraint period relates. Default is the end of the planning activity.
     * @param startOffset Offset from startRef that specifies the start of the constraint period.  A positive offset implies a shift later in time. Default is no offset.
     * @param endOffset Offset from endRef that specifies the end of the constraint period.  A positive offset implies a shift later in time. Default is no offset.
     * @param centerObject Position of the center object.
     * @param targetObject1 Position of target object 1.
     * @param targetObject2 Position of target object 2.
     * @param minAngle Minimum angle subtended at the center object by target objects 1 and 2.
     * @param maxAngle Maximum angle subtended at the center object by target objects 1 and 2.
     */
    public AngleConstraint(Boolean negate,
            org.ccsds.moims.mo.mps.structures.Slider startRef,
            org.ccsds.moims.mo.mps.structures.Slider endRef,
            org.ccsds.moims.mo.mal.structures.Element startOffset,
            org.ccsds.moims.mo.mal.structures.Element endOffset,
            org.ccsds.moims.mo.mal.structures.Element centerObject,
            org.ccsds.moims.mo.mal.structures.Element targetObject1,
            org.ccsds.moims.mo.mal.structures.Element targetObject2,
            org.ccsds.moims.mo.mal.structures.Element minAngle,
            org.ccsds.moims.mo.mal.structures.Element maxAngle) {
        super(negate,
            startRef,
            endRef,
            startOffset,
            endOffset);
        this.centerObject = centerObject;
        this.targetObject1 = targetObject1;
        this.targetObject2 = targetObject2;
        this.minAngle = minAngle;
        this.maxAngle = maxAngle;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param centerObject Position of the center object.
     * @param targetObject1 Position of target object 1.
     * @param targetObject2 Position of target object 2.
     * @param minAngle Minimum angle subtended at the center object by target objects 1 and 2.
     * @param maxAngle Maximum angle subtended at the center object by target objects 1 and 2.
     */
    public AngleConstraint(org.ccsds.moims.mo.mal.structures.Element centerObject,
            org.ccsds.moims.mo.mal.structures.Element targetObject1,
            org.ccsds.moims.mo.mal.structures.Element targetObject2,
            org.ccsds.moims.mo.mal.structures.Element minAngle,
            org.ccsds.moims.mo.mal.structures.Element maxAngle) {
        this.centerObject = centerObject;
        this.targetObject1 = targetObject1;
        this.targetObject2 = targetObject2;
        this.minAngle = minAngle;
        this.maxAngle = maxAngle;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.AngleConstraint();
    }

    /**
     * Returns the field centerObject.
     * 
     * @return The field centerObject
     */
    public org.ccsds.moims.mo.mal.structures.Element getCenterObject() {
        return centerObject;
    }

    /**
     * Returns the field targetObject1.
     * 
     * @return The field targetObject1
     */
    public org.ccsds.moims.mo.mal.structures.Element getTargetObject1() {
        return targetObject1;
    }

    /**
     * Returns the field targetObject2.
     * 
     * @return The field targetObject2
     */
    public org.ccsds.moims.mo.mal.structures.Element getTargetObject2() {
        return targetObject2;
    }

    /**
     * Returns the field minAngle.
     * 
     * @return The field minAngle
     */
    public org.ccsds.moims.mo.mal.structures.Element getMinAngle() {
        return minAngle;
    }

    /**
     * Returns the field maxAngle.
     * 
     * @return The field maxAngle
     */
    public org.ccsds.moims.mo.mal.structures.Element getMaxAngle() {
        return maxAngle;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AngleConstraint) {
            if (! super.equals(obj)) {
                return false;
            }
            AngleConstraint other = (AngleConstraint) obj;
            if (centerObject == null) {
                if (other.centerObject != null) {
                    return false;
                }
            } else {
                if (! centerObject.equals(other.centerObject)) {
                    return false;
                }
            }
            if (targetObject1 == null) {
                if (other.targetObject1 != null) {
                    return false;
                }
            } else {
                if (! targetObject1.equals(other.targetObject1)) {
                    return false;
                }
            }
            if (targetObject2 == null) {
                if (other.targetObject2 != null) {
                    return false;
                }
            } else {
                if (! targetObject2.equals(other.targetObject2)) {
                    return false;
                }
            }
            if (minAngle == null) {
                if (other.minAngle != null) {
                    return false;
                }
            } else {
                if (! minAngle.equals(other.minAngle)) {
                    return false;
                }
            }
            if (maxAngle == null) {
                if (other.maxAngle != null) {
                    return false;
                }
            } else {
                if (! maxAngle.equals(other.maxAngle)) {
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
        hash = 83 * hash + (centerObject != null ? centerObject.hashCode() : 0);
        hash = 83 * hash + (targetObject1 != null ? targetObject1.hashCode() : 0);
        hash = 83 * hash + (targetObject2 != null ? targetObject2.hashCode() : 0);
        hash = 83 * hash + (minAngle != null ? minAngle.hashCode() : 0);
        hash = 83 * hash + (maxAngle != null ? maxAngle.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(AngleConstraint: ");
        buf.append(super.toString());
        buf.append(", centerObject=").append(centerObject);
        buf.append(", targetObject1=").append(targetObject1);
        buf.append(", targetObject2=").append(targetObject2);
        buf.append(", minAngle=").append(minAngle);
        buf.append(", maxAngle=").append(maxAngle);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (centerObject == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'centerObject' cannot be null!");
        }
        if (targetObject1 == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'targetObject1' cannot be null!");
        }
        if (targetObject2 == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'targetObject2' cannot be null!");
        }
        if (minAngle == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'minAngle' cannot be null!");
        }
        if (maxAngle == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'maxAngle' cannot be null!");
        }
        encoder.encodeAbstractElement(centerObject);
        encoder.encodeAbstractElement(targetObject1);
        encoder.encodeAbstractElement(targetObject2);
        encoder.encodeAbstractElement(minAngle);
        encoder.encodeAbstractElement(maxAngle);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        centerObject = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        targetObject1 = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        targetObject2 = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        minAngle = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        maxAngle = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
