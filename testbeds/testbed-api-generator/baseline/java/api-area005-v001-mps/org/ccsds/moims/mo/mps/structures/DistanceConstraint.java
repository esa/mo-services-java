package org.ccsds.moims.mo.mps.structures;

/**
 * E7: Specifies a range of distances between two physical objects (the observer
 * and the target).
 */
public final class DistanceConstraint extends org.ccsds.moims.mo.mps.structures.GeometricConstraint {

    private static final long serialVersionUID = 1407374900330539L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330539L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Position of the observer [Object1].
     */
    private org.ccsds.moims.mo.mal.structures.Element observer;

    /**
     * Position of the target [Object2].
     */
    private org.ccsds.moims.mo.mal.structures.Element target;

    /**
     * Minimum distance between observer and target.
     */
    private org.ccsds.moims.mo.mal.structures.Element minDistance;

    /**
     * Maximum distance between observer and target.
     */
    private org.ccsds.moims.mo.mal.structures.Element maxDistance;

    /**
     * Default constructor for DistanceConstraint.
     * 
     */
    public DistanceConstraint() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param negate Specifies whether the result of combining the Constraints is to be inverted (NOT function). Default = False.
     * @param startRef Identifies the point in the duration of the applicable planning activity to which the start of the constraint period relates. Default is the start of the planning activity.
     * @param endRef Identifies the point in the duration of the applicable planning activity to which the end of the constraint period relates. Default is the end of the planning activity.
     * @param startOffset Offset from startRef that specifies the start of the constraint period.  A positive offset implies a shift later in time. Default is no offset.
     * @param endOffset Offset from endRef that specifies the end of the constraint period.  A positive offset implies a shift later in time. Default is no offset.
     * @param observer Position of the observer [Object1].
     * @param target Position of the target [Object2].
     * @param minDistance Minimum distance between observer and target.
     * @param maxDistance Maximum distance between observer and target.
     */
    public DistanceConstraint(Boolean negate,
            org.ccsds.moims.mo.mps.structures.Slider startRef,
            org.ccsds.moims.mo.mps.structures.Slider endRef,
            org.ccsds.moims.mo.mal.structures.Element startOffset,
            org.ccsds.moims.mo.mal.structures.Element endOffset,
            org.ccsds.moims.mo.mal.structures.Element observer,
            org.ccsds.moims.mo.mal.structures.Element target,
            org.ccsds.moims.mo.mal.structures.Element minDistance,
            org.ccsds.moims.mo.mal.structures.Element maxDistance) {
        super(negate,
            startRef,
            endRef,
            startOffset,
            endOffset);
        this.observer = observer;
        this.target = target;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param observer Position of the observer [Object1].
     * @param target Position of the target [Object2].
     * @param minDistance Minimum distance between observer and target.
     * @param maxDistance Maximum distance between observer and target.
     */
    public DistanceConstraint(org.ccsds.moims.mo.mal.structures.Element observer,
            org.ccsds.moims.mo.mal.structures.Element target,
            org.ccsds.moims.mo.mal.structures.Element minDistance,
            org.ccsds.moims.mo.mal.structures.Element maxDistance) {
        this.observer = observer;
        this.target = target;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.DistanceConstraint();
    }

    /**
     * Returns the field observer.
     * 
     * @return The field observer
     */
    public org.ccsds.moims.mo.mal.structures.Element getObserver() {
        return observer;
    }

    /**
     * Returns the field target.
     * 
     * @return The field target
     */
    public org.ccsds.moims.mo.mal.structures.Element getTarget() {
        return target;
    }

    /**
     * Returns the field minDistance.
     * 
     * @return The field minDistance
     */
    public org.ccsds.moims.mo.mal.structures.Element getMinDistance() {
        return minDistance;
    }

    /**
     * Returns the field maxDistance.
     * 
     * @return The field maxDistance
     */
    public org.ccsds.moims.mo.mal.structures.Element getMaxDistance() {
        return maxDistance;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DistanceConstraint) {
            if (! super.equals(obj)) {
                return false;
            }
            DistanceConstraint other = (DistanceConstraint) obj;
            if (observer == null) {
                if (other.observer != null) {
                    return false;
                }
            } else {
                if (! observer.equals(other.observer)) {
                    return false;
                }
            }
            if (target == null) {
                if (other.target != null) {
                    return false;
                }
            } else {
                if (! target.equals(other.target)) {
                    return false;
                }
            }
            if (minDistance == null) {
                if (other.minDistance != null) {
                    return false;
                }
            } else {
                if (! minDistance.equals(other.minDistance)) {
                    return false;
                }
            }
            if (maxDistance == null) {
                if (other.maxDistance != null) {
                    return false;
                }
            } else {
                if (! maxDistance.equals(other.maxDistance)) {
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
        hash = 83 * hash + (observer != null ? observer.hashCode() : 0);
        hash = 83 * hash + (target != null ? target.hashCode() : 0);
        hash = 83 * hash + (minDistance != null ? minDistance.hashCode() : 0);
        hash = 83 * hash + (maxDistance != null ? maxDistance.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(DistanceConstraint: ");
        buf.append(super.toString());
        buf.append(", observer=").append(observer);
        buf.append(", target=").append(target);
        buf.append(", minDistance=").append(minDistance);
        buf.append(", maxDistance=").append(maxDistance);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (observer == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'observer' cannot be null!");
        }
        if (target == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'target' cannot be null!");
        }
        if (minDistance == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'minDistance' cannot be null!");
        }
        if (maxDistance == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'maxDistance' cannot be null!");
        }
        encoder.encodeAbstractElement(observer);
        encoder.encodeAbstractElement(target);
        encoder.encodeAbstractElement(minDistance);
        encoder.encodeAbstractElement(maxDistance);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        observer = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        target = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        minDistance = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        maxDistance = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
