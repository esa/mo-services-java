package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * E2: A separation constraint specifies that the parent planning activity
 * (the activity for which the constraint is defined) must be separate in
 * time from another planning activity or planning event, the opponent.
 */
public final class SeparationConstraint extends Constraint {

    private static final long serialVersionUID = 1407374900330542L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330542L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Object Type: ActivityDefinition | EventDefinition Specifies the definition
     * (class) of the opponent planning activity or planning event.
     */
    private Element opponent;

    /**
     * Identifies the point in the duration of the parent activity with respect
     * to which to place the start of its constraint window. Default = 0.
     */
    private Slider startRef;

    /**
     * Offset with respect to startRef of the start of the parent activity constraint
     * window.  A positive offset implies a shift later in time. Default is no
     * offset.
     */
    private Element startOffset;

    /**
     * Identifies the point in the duration of the parent activity with respect
     * to which to place the end of its constraint window. Default = 1.
     */
    private Slider endRef;

    /**
     * Offset with respect to endRef of the end of the parent activity constraint
     * window.  A positive offset implies a shift later in time. Default is no
     * offset.
     */
    private Element endOffset;

    /**
     * Identifies the point in the duration of the opponent with respect to which
     * to place the start of its constraint window.  This field will be ignored
     * in case the opponent is a planning event. Default = 0.
     */
    private Slider opponentStartRef;

    /**
     * Offset with respect to opponentStartRef of the start of the opponent constraint
     * window.  A positive offset implies a shift later in time. Default is no
     * offset.
     */
    private Element opponentStartOffset;

    /**
     * Identifies the point in the duration of the opponent with respect to which
     * to place the end of its constraint window.  This field will be ignored
     * in case the opponent is a planning event. Default = 1.
     */
    private Slider opponentEndRef;

    /**
     * Offset with respect to opponentEndRef of the end of the opponent constraint
     * window.  A positive offset implies a shift later in time. Default is no
     * offset.
     */
    private Element opponentEndOffset;

    /**
     * Default constructor for SeparationConstraint.
     * 
     */
    public SeparationConstraint() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param negate Specifies whether the result of combining the Constraints is to be inverted (NOT function). Default = False.
     * @param opponent Object Type: ActivityDefinition | EventDefinition Specifies the definition (class) of the opponent planning activity or planning event.
     * @param startRef Identifies the point in the duration of the parent activity with respect to which to place the start of its constraint window. Default = 0.
     * @param startOffset Offset with respect to startRef of the start of the parent activity constraint window.  A positive offset implies a shift later in time. Default is no offset.
     * @param endRef Identifies the point in the duration of the parent activity with respect to which to place the end of its constraint window. Default = 1.
     * @param endOffset Offset with respect to endRef of the end of the parent activity constraint window.  A positive offset implies a shift later in time. Default is no offset.
     * @param opponentStartRef Identifies the point in the duration of the opponent with respect to which to place the start of its constraint window.  This field will be ignored in case the opponent is a planning event. Default = 0.
     * @param opponentStartOffset Offset with respect to opponentStartRef of the start of the opponent constraint window.  A positive offset implies a shift later in time. Default is no offset.
     * @param opponentEndRef Identifies the point in the duration of the opponent with respect to which to place the end of its constraint window.  This field will be ignored in case the opponent is a planning event. Default = 1.
     * @param opponentEndOffset Offset with respect to opponentEndRef of the end of the opponent constraint window.  A positive offset implies a shift later in time. Default is no offset.
     */
    public SeparationConstraint(Boolean negate,
            Element opponent,
            Slider startRef,
            Element startOffset,
            Slider endRef,
            Element endOffset,
            Slider opponentStartRef,
            Element opponentStartOffset,
            Slider opponentEndRef,
            Element opponentEndOffset) {
        super(negate);
        this.opponent = opponent;
        this.startRef = startRef;
        this.startOffset = startOffset;
        this.endRef = endRef;
        this.endOffset = endOffset;
        this.opponentStartRef = opponentStartRef;
        this.opponentStartOffset = opponentStartOffset;
        this.opponentEndRef = opponentEndRef;
        this.opponentEndOffset = opponentEndOffset;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param opponent Object Type: ActivityDefinition | EventDefinition Specifies the definition (class) of the opponent planning activity or planning event.
     */
    public SeparationConstraint(Element opponent) {
        this.opponent = opponent;
        this.startRef = null;
        this.startOffset = null;
        this.endRef = null;
        this.endOffset = null;
        this.opponentStartRef = null;
        this.opponentStartOffset = null;
        this.opponentEndRef = null;
        this.opponentEndOffset = null;
    }

    @Override
    public Element createElement() {
        return new SeparationConstraint();
    }

    /**
     * Returns the field opponent.
     * 
     * @return The field opponent
     */
    public Element getOpponent() {
        return opponent;
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
     * Returns the field startOffset.
     * 
     * @return The field startOffset
     */
    public Element getStartOffset() {
        return startOffset;
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
     * Returns the field endOffset.
     * 
     * @return The field endOffset
     */
    public Element getEndOffset() {
        return endOffset;
    }

    /**
     * Returns the field opponentStartRef.
     * 
     * @return The field opponentStartRef
     */
    public Slider getOpponentStartRef() {
        return opponentStartRef;
    }

    /**
     * Returns the field opponentStartOffset.
     * 
     * @return The field opponentStartOffset
     */
    public Element getOpponentStartOffset() {
        return opponentStartOffset;
    }

    /**
     * Returns the field opponentEndRef.
     * 
     * @return The field opponentEndRef
     */
    public Slider getOpponentEndRef() {
        return opponentEndRef;
    }

    /**
     * Returns the field opponentEndOffset.
     * 
     * @return The field opponentEndOffset
     */
    public Element getOpponentEndOffset() {
        return opponentEndOffset;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SeparationConstraint) {
            if (! super.equals(obj)) {
                return false;
            }
            SeparationConstraint other = (SeparationConstraint) obj;
            if (opponent == null) {
                if (other.opponent != null) {
                    return false;
                }
            } else {
                if (! opponent.equals(other.opponent)) {
                    return false;
                }
            }
            if (startRef == null) {
                if (other.startRef != null) {
                    return false;
                }
            } else {
                if (! startRef.equals(other.startRef)) {
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
            if (endRef == null) {
                if (other.endRef != null) {
                    return false;
                }
            } else {
                if (! endRef.equals(other.endRef)) {
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
            if (opponentStartRef == null) {
                if (other.opponentStartRef != null) {
                    return false;
                }
            } else {
                if (! opponentStartRef.equals(other.opponentStartRef)) {
                    return false;
                }
            }
            if (opponentStartOffset == null) {
                if (other.opponentStartOffset != null) {
                    return false;
                }
            } else {
                if (! opponentStartOffset.equals(other.opponentStartOffset)) {
                    return false;
                }
            }
            if (opponentEndRef == null) {
                if (other.opponentEndRef != null) {
                    return false;
                }
            } else {
                if (! opponentEndRef.equals(other.opponentEndRef)) {
                    return false;
                }
            }
            if (opponentEndOffset == null) {
                if (other.opponentEndOffset != null) {
                    return false;
                }
            } else {
                if (! opponentEndOffset.equals(other.opponentEndOffset)) {
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
        hash = 83 * hash + (startRef != null ? startRef.hashCode() : 0);
        hash = 83 * hash + (startOffset != null ? startOffset.hashCode() : 0);
        hash = 83 * hash + (endRef != null ? endRef.hashCode() : 0);
        hash = 83 * hash + (endOffset != null ? endOffset.hashCode() : 0);
        hash = 83 * hash + (opponentStartRef != null ? opponentStartRef.hashCode() : 0);
        hash = 83 * hash + (opponentStartOffset != null ? opponentStartOffset.hashCode() : 0);
        hash = 83 * hash + (opponentEndRef != null ? opponentEndRef.hashCode() : 0);
        hash = 83 * hash + (opponentEndOffset != null ? opponentEndOffset.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(SeparationConstraint: ");
        buf.append(super.toString());
        buf.append(", opponent=").append(opponent);
        buf.append(", startRef=").append(startRef);
        buf.append(", startOffset=").append(startOffset);
        buf.append(", endRef=").append(endRef);
        buf.append(", endOffset=").append(endOffset);
        buf.append(", opponentStartRef=").append(opponentStartRef);
        buf.append(", opponentStartOffset=").append(opponentStartOffset);
        buf.append(", opponentEndRef=").append(opponentEndRef);
        buf.append(", opponentEndOffset=").append(opponentEndOffset);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (opponent == null) {
            throw new MALException("The field 'opponent' cannot be null!");
        }
        encoder.encodeAbstractElement(opponent);
        encoder.encodeNullableElement(startRef);
        encoder.encodeNullableAbstractElement(startOffset);
        encoder.encodeNullableElement(endRef);
        encoder.encodeNullableAbstractElement(endOffset);
        encoder.encodeNullableElement(opponentStartRef);
        encoder.encodeNullableAbstractElement(opponentStartOffset);
        encoder.encodeNullableElement(opponentEndRef);
        encoder.encodeNullableAbstractElement(opponentEndOffset);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        opponent = (Element) decoder.decodeAbstractElement();
        startRef = (Slider) decoder.decodeNullableElement(new Slider());
        startOffset = (Element) decoder.decodeNullableAbstractElement();
        endRef = (Slider) decoder.decodeNullableElement(new Slider());
        endOffset = (Element) decoder.decodeNullableAbstractElement();
        opponentStartRef = (Slider) decoder.decodeNullableElement(new Slider());
        opponentStartOffset = (Element) decoder.decodeNullableAbstractElement();
        opponentEndRef = (Slider) decoder.decodeNullableElement(new Slider());
        opponentEndOffset = (Element) decoder.decodeNullableAbstractElement();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
