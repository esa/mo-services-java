package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * E7: Specifies a range of distances between two physical objects (the observer
 * and the target).
 */
public final class DistanceConstraint extends GeometricConstraint {

    private static final long serialVersionUID = 1407374900330539L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330539L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Position of the observer [Object1].
     */
    private Element observer;

    /**
     * Position of the target [Object2].
     */
    private Element target;

    /**
     * Minimum distance between observer and target.
     */
    private Element minDistance;

    /**
     * Maximum distance between observer and target.
     */
    private Element maxDistance;

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
            Slider startRef,
            Slider endRef,
            Element startOffset,
            Element endOffset,
            Element observer,
            Element target,
            Element minDistance,
            Element maxDistance) {
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
    public DistanceConstraint(Element observer,
            Element target,
            Element minDistance,
            Element maxDistance) {
        this.observer = observer;
        this.target = target;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }

    @Override
    public Element createElement() {
        return new DistanceConstraint();
    }

    /**
     * Returns the field observer.
     * 
     * @return The field observer
     */
    public Element getObserver() {
        return observer;
    }

    /**
     * Returns the field target.
     * 
     * @return The field target
     */
    public Element getTarget() {
        return target;
    }

    /**
     * Returns the field minDistance.
     * 
     * @return The field minDistance
     */
    public Element getMinDistance() {
        return minDistance;
    }

    /**
     * Returns the field maxDistance.
     * 
     * @return The field maxDistance
     */
    public Element getMaxDistance() {
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
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (observer == null) {
            throw new MALException("The field 'observer' cannot be null!");
        }
        if (target == null) {
            throw new MALException("The field 'target' cannot be null!");
        }
        if (minDistance == null) {
            throw new MALException("The field 'minDistance' cannot be null!");
        }
        if (maxDistance == null) {
            throw new MALException("The field 'maxDistance' cannot be null!");
        }
        encoder.encodeAbstractElement(observer);
        encoder.encodeAbstractElement(target);
        encoder.encodeAbstractElement(minDistance);
        encoder.encodeAbstractElement(maxDistance);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        observer = (Element) decoder.decodeAbstractElement();
        target = (Element) decoder.decodeAbstractElement();
        minDistance = (Element) decoder.decodeAbstractElement();
        maxDistance = (Element) decoder.decodeAbstractElement();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
