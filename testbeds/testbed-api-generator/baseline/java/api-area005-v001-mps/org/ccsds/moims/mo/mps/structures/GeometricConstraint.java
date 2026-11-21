package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * E7: Geometric constraints restrict the planning of the planning activity
 * by imposing a geometric condition that must be valid during some constraint
 * period.
 */
public abstract class GeometricConstraint extends Constraint {

    /**
     * Identifies the point in the duration of the applicable planning activity
     * to which the start of the constraint period relates. Default is the start
     * of the planning activity.
     */
    private Slider startRef;

    /**
     * Identifies the point in the duration of the applicable planning activity
     * to which the end of the constraint period relates. Default is the end of
     * the planning activity.
     */
    private Slider endRef;

    /**
     * Offset from startRef that specifies the start of the constraint period.
     * A positive offset implies a shift later in time. Default is no offset.
     */
    private Element startOffset;

    /**
     * Offset from endRef that specifies the end of the constraint period.  A
     * positive offset implies a shift later in time. Default is no offset.
     */
    private Element endOffset;

    /**
     * Default constructor for GeometricConstraint.
     * 
     */
    public GeometricConstraint() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param negate Specifies whether the result of combining the Constraints is to be inverted (NOT function). Default = False.
     * @param startRef Identifies the point in the duration of the applicable planning activity to which the start of the constraint period relates. Default is the start of the planning activity.
     * @param endRef Identifies the point in the duration of the applicable planning activity to which the end of the constraint period relates. Default is the end of the planning activity.
     * @param startOffset Offset from startRef that specifies the start of the constraint period.  A positive offset implies a shift later in time. Default is no offset.
     * @param endOffset Offset from endRef that specifies the end of the constraint period.  A positive offset implies a shift later in time. Default is no offset.
     */
    public GeometricConstraint(Boolean negate,
            Slider startRef,
            Slider endRef,
            Element startOffset,
            Element endOffset) {
        super(negate);
        this.startRef = startRef;
        this.endRef = endRef;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
    }

    /**
     * Returns the field startRef.
     * 
     * @return The field startRef
     */
    public Slider getStartRef() {
        return startRef;
    }

    /**
     * Returns the field endRef.
     * 
     * @return The field endRef
     */
    public Slider getEndRef() {
        return endRef;
    }

    /**
     * Returns the field startOffset.
     * 
     * @return The field startOffset
     */
    public Element getStartOffset() {
        return startOffset;
    }

    /**
     * Returns the field endOffset.
     * 
     * @return The field endOffset
     */
    public Element getEndOffset() {
        return endOffset;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GeometricConstraint) {
            if (! super.equals(obj)) {
                return false;
            }
            GeometricConstraint other = (GeometricConstraint) obj;
            if (startRef == null) {
                if (other.startRef != null) {
                    return false;
                }
            } else {
                if (! startRef.equals(other.startRef)) {
                    return false;
                }
            }
            if (endRef == null) {
                if (other.endRef != null) {
                    return false;
                }
            } else {
                if (! endRef.equals(other.endRef)) {
                    return false;
                }
            }
            if (startOffset == null) {
                if (other.startOffset != null) {
                    return false;
                }
            } else {
                if (! startOffset.equals(other.startOffset)) {
                    return false;
                }
            }
            if (endOffset == null) {
                if (other.endOffset != null) {
                    return false;
                }
            } else {
                if (! endOffset.equals(other.endOffset)) {
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
        hash = 83 * hash + (startRef != null ? startRef.hashCode() : 0);
        hash = 83 * hash + (endRef != null ? endRef.hashCode() : 0);
        hash = 83 * hash + (startOffset != null ? startOffset.hashCode() : 0);
        hash = 83 * hash + (endOffset != null ? endOffset.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(GeometricConstraint: ");
        buf.append(super.toString());
        buf.append(", startRef=").append(startRef);
        buf.append(", endRef=").append(endRef);
        buf.append(", startOffset=").append(startOffset);
        buf.append(", endOffset=").append(endOffset);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        encoder.encodeNullableElement(startRef);
        encoder.encodeNullableElement(endRef);
        encoder.encodeNullableAbstractElement(startOffset);
        encoder.encodeNullableAbstractElement(endOffset);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        startRef = (Slider) decoder.decodeNullableElement(new Slider());
        endRef = (Slider) decoder.decodeNullableElement(new Slider());
        startOffset = (Element) decoder.decodeNullableAbstractElement();
        endOffset = (Element) decoder.decodeNullableAbstractElement();
        return this;
    }

}
