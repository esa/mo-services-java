package org.ccsds.moims.mo.mps.structures;

/**
 * E8: Function constraints make use of an external custom function to determine
 * whether or not a constraint is satisfied.  The function must return True
 * for the constraint to be met. As for complex resource constraints, the
 * period over which the function constraint applies is specified relative
 * to the planning activity to which the constraint applies.
 */
public final class FunctionConstraint extends org.ccsds.moims.mo.mps.structures.Constraint {

    private static final long serialVersionUID = 1407374900330543L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330543L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Identifies the point in the duration of the applicable planning activity
     * to which the start of the constraint period relates. Default is the start
     * of the planning activity.
     */
    private org.ccsds.moims.mo.mps.structures.Slider startRef;

    /**
     * Identifies the point in the duration of the applicable planning activity
     * to which the end of the constraint period relates. Default is the end of
     * the planning activity.
     */
    private org.ccsds.moims.mo.mps.structures.Slider endRef;

    /**
     * Offset from startRef that specifies the start of the constraint period.
     * A positive offset implies a shift later in time. Default is no offset.
     */
    private org.ccsds.moims.mo.mal.structures.Element startOffset;

    /**
     * Offset from endRef that specifies the end of the constraint period.  A
     * positive offset implies a shift later in time. Default is no offset.
     */
    private org.ccsds.moims.mo.mal.structures.Element endOffset;

    /**
     * Specifies the Function to be applied and its set of input arguments.
     */
    private org.ccsds.moims.mo.mps.structures.FunctionDetails function;

    /**
     * Default constructor for FunctionConstraint.
     * 
     */
    public FunctionConstraint() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param negate Specifies whether the result of combining the Constraints is to be inverted (NOT function). Default = False.
     * @param startRef Identifies the point in the duration of the applicable planning activity to which the start of the constraint period relates. Default is the start of the planning activity.
     * @param endRef Identifies the point in the duration of the applicable planning activity to which the end of the constraint period relates. Default is the end of the planning activity.
     * @param startOffset Offset from startRef that specifies the start of the constraint period.  A positive offset implies a shift later in time. Default is no offset.
     * @param endOffset Offset from endRef that specifies the end of the constraint period.  A positive offset implies a shift later in time. Default is no offset.
     * @param function Specifies the Function to be applied and its set of input arguments.
     */
    public FunctionConstraint(Boolean negate,
            org.ccsds.moims.mo.mps.structures.Slider startRef,
            org.ccsds.moims.mo.mps.structures.Slider endRef,
            org.ccsds.moims.mo.mal.structures.Element startOffset,
            org.ccsds.moims.mo.mal.structures.Element endOffset,
            org.ccsds.moims.mo.mps.structures.FunctionDetails function) {
        super(negate);
        this.startRef = startRef;
        this.endRef = endRef;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.function = function;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param function Specifies the Function to be applied and its set of input arguments.
     */
    public FunctionConstraint(org.ccsds.moims.mo.mps.structures.FunctionDetails function) {
        this.startRef = null;
        this.endRef = null;
        this.startOffset = null;
        this.endOffset = null;
        this.function = function;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.FunctionConstraint();
    }

    /**
     * Returns the field startRef.
     * 
     * @return The field startRef
     */
    public org.ccsds.moims.mo.mps.structures.Slider getStartRef() {
        return startRef;
    }

    /**
     * Returns the field endRef.
     * 
     * @return The field endRef
     */
    public org.ccsds.moims.mo.mps.structures.Slider getEndRef() {
        return endRef;
    }

    /**
     * Returns the field startOffset.
     * 
     * @return The field startOffset
     */
    public org.ccsds.moims.mo.mal.structures.Element getStartOffset() {
        return startOffset;
    }

    /**
     * Returns the field endOffset.
     * 
     * @return The field endOffset
     */
    public org.ccsds.moims.mo.mal.structures.Element getEndOffset() {
        return endOffset;
    }

    /**
     * Returns the field function.
     * 
     * @return The field function
     */
    public org.ccsds.moims.mo.mps.structures.FunctionDetails getFunction() {
        return function;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FunctionConstraint) {
            if (! super.equals(obj)) {
                return false;
            }
            FunctionConstraint other = (FunctionConstraint) obj;
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
            if (function == null) {
                if (other.function != null) {
                    return false;
                }
            } else {
                if (! function.equals(other.function)) {
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
        hash = 83 * hash + (function != null ? function.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(FunctionConstraint: ");
        buf.append(super.toString());
        buf.append(", startRef=").append(startRef);
        buf.append(", endRef=").append(endRef);
        buf.append(", startOffset=").append(startOffset);
        buf.append(", endOffset=").append(endOffset);
        buf.append(", function=").append(function);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (function == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'function' cannot be null!");
        }
        encoder.encodeNullableElement(startRef);
        encoder.encodeNullableElement(endRef);
        encoder.encodeNullableAbstractElement(startOffset);
        encoder.encodeNullableAbstractElement(endOffset);
        encoder.encodeElement(function);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        startRef = (org.ccsds.moims.mo.mps.structures.Slider) decoder.decodeNullableElement(new org.ccsds.moims.mo.mps.structures.Slider());
        endRef = (org.ccsds.moims.mo.mps.structures.Slider) decoder.decodeNullableElement(new org.ccsds.moims.mo.mps.structures.Slider());
        startOffset = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeNullableAbstractElement();
        endOffset = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeNullableAbstractElement();
        function = (org.ccsds.moims.mo.mps.structures.FunctionDetails) decoder.decodeElement(new org.ccsds.moims.mo.mps.structures.FunctionDetails());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
