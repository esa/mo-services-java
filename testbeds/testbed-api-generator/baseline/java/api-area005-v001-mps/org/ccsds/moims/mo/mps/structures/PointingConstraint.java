package org.ccsds.moims.mo.mps.structures;

/**
 * E7: Pointing constraints impose a restriction on a planning activity appearing
 * in a Plan, based on the pointing direction of a physical object, such as
 * a spacecraft or instrument. As with the Direction data types (see 4.6.3.3),
 * pointing constraints are consistent with the pointing templates defined
 * for use within CCSDS Navigation data format Recommended Standards, and
 * specifically the Pointing Request Message (PRM) (reference [10]).  PointingConstraint
 * is a concrete sub-type of GeometricConstraint that includes fields common
 * to all pointing templates.  The pointing template itself is then identified
 * as a field and any additional arguments applicable to the template are
 * provided as a list of name-value pairs. NOTE – Pointing templates are common,
 * generic templates that describe pointing modes that may be followed by
 * spacecraft.
 */
public final class PointingConstraint extends org.ccsds.moims.mo.mps.structures.GeometricConstraint {

    private static final long serialVersionUID = 1407374900330537L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330537L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Optional.  Reference frame to which the pointing constraint applies (see
     * 4.4.2). Default frame is the spacecraft frame or any other mission specific
     * default frame.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier pointingFrame;

    /**
     * The primary axis to which the pointing constraints applies.  Direction
     * in any spacecraft frame.
     */
    private org.ccsds.moims.mo.mal.structures.Element boresight;

    /**
     * Defines an optional cone region around the boresight, allowing a margin
     * for application of the pointing constraint. Default = 0.0.
     */
    private org.ccsds.moims.mo.mal.structures.Element boresightMargin;

    /**
     * Defines an optional rotation around the boresight with respect to the default
     * phase angle, allowing a margin for application of the pointing constraint.
     * Default = 0.0.
     */
    private org.ccsds.moims.mo.mal.structures.Element phaseAngleMargin;

    /**
     * If True, no constraint will apply to the phaseAngle.  The phaseAngleMargin
     * field will be ignored in this case. Default = False.
     */
    private Boolean unconstrainedPhaseAngle;

    /**
     * One of the pointing templates defined in the PRM or a mission specific
     * pointing template (see 4.4.4).
     */
    private org.ccsds.moims.mo.mal.structures.Identifier pointingTemplate;

    /**
     * The argument list shall be consistent with the referenced template by name.
     * Each value type shall match the argument type according to table 4-6.
     */
    private org.ccsds.moims.mo.mps.structures.NamedElementList pointingArguments;

    /**
     * Default constructor for PointingConstraint.
     * 
     */
    public PointingConstraint() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param negate Specifies whether the result of combining the Constraints is to be inverted (NOT function). Default = False.
     * @param startRef Identifies the point in the duration of the applicable planning activity to which the start of the constraint period relates. Default is the start of the planning activity.
     * @param endRef Identifies the point in the duration of the applicable planning activity to which the end of the constraint period relates. Default is the end of the planning activity.
     * @param startOffset Offset from startRef that specifies the start of the constraint period.  A positive offset implies a shift later in time. Default is no offset.
     * @param endOffset Offset from endRef that specifies the end of the constraint period.  A positive offset implies a shift later in time. Default is no offset.
     * @param pointingFrame Optional.  Reference frame to which the pointing constraint applies (see 4.4.2). Default frame is the spacecraft frame or any other mission specific default frame.
     * @param boresight The primary axis to which the pointing constraints applies.  Direction in any spacecraft frame.
     * @param boresightMargin Defines an optional cone region around the boresight, allowing a margin for application of the pointing constraint. Default = 0.0.
     * @param phaseAngleMargin Defines an optional rotation around the boresight with respect to the default phase angle, allowing a margin for application of the pointing constraint. Default = 0.0.
     * @param unconstrainedPhaseAngle If True, no constraint will apply to the phaseAngle.  The phaseAngleMargin field will be ignored in this case. Default = False.
     * @param pointingTemplate One of the pointing templates defined in the PRM or a mission specific pointing template (see 4.4.4).
     * @param pointingArguments The argument list shall be consistent with the referenced template by name.  Each value type shall match the argument type according to table 4-6.
     */
    public PointingConstraint(Boolean negate,
            org.ccsds.moims.mo.mps.structures.Slider startRef,
            org.ccsds.moims.mo.mps.structures.Slider endRef,
            org.ccsds.moims.mo.mal.structures.Element startOffset,
            org.ccsds.moims.mo.mal.structures.Element endOffset,
            org.ccsds.moims.mo.mal.structures.Identifier pointingFrame,
            org.ccsds.moims.mo.mal.structures.Element boresight,
            org.ccsds.moims.mo.mal.structures.Element boresightMargin,
            org.ccsds.moims.mo.mal.structures.Element phaseAngleMargin,
            Boolean unconstrainedPhaseAngle,
            org.ccsds.moims.mo.mal.structures.Identifier pointingTemplate,
            org.ccsds.moims.mo.mps.structures.NamedElementList pointingArguments) {
        super(negate,
            startRef,
            endRef,
            startOffset,
            endOffset);
        this.pointingFrame = pointingFrame;
        this.boresight = boresight;
        this.boresightMargin = boresightMargin;
        this.phaseAngleMargin = phaseAngleMargin;
        this.unconstrainedPhaseAngle = unconstrainedPhaseAngle;
        this.pointingTemplate = pointingTemplate;
        this.pointingArguments = pointingArguments;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param boresight The primary axis to which the pointing constraints applies.  Direction in any spacecraft frame.
     * @param pointingTemplate One of the pointing templates defined in the PRM or a mission specific pointing template (see 4.4.4).
     */
    public PointingConstraint(org.ccsds.moims.mo.mal.structures.Element boresight,
            org.ccsds.moims.mo.mal.structures.Identifier pointingTemplate) {
        this.pointingFrame = null;
        this.boresight = boresight;
        this.boresightMargin = null;
        this.phaseAngleMargin = null;
        this.unconstrainedPhaseAngle = null;
        this.pointingTemplate = pointingTemplate;
        this.pointingArguments = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.PointingConstraint();
    }

    /**
     * Returns the field pointingFrame.
     * 
     * @return The field pointingFrame
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getPointingFrame() {
        return pointingFrame;
    }

    /**
     * Returns the field boresight.
     * 
     * @return The field boresight
     */
    public org.ccsds.moims.mo.mal.structures.Element getBoresight() {
        return boresight;
    }

    /**
     * Returns the field boresightMargin.
     * 
     * @return The field boresightMargin
     */
    public org.ccsds.moims.mo.mal.structures.Element getBoresightMargin() {
        return boresightMargin;
    }

    /**
     * Returns the field phaseAngleMargin.
     * 
     * @return The field phaseAngleMargin
     */
    public org.ccsds.moims.mo.mal.structures.Element getPhaseAngleMargin() {
        return phaseAngleMargin;
    }

    /**
     * Returns the field unconstrainedPhaseAngle.
     * 
     * @return The field unconstrainedPhaseAngle
     */
    public Boolean getUnconstrainedPhaseAngle() {
        return unconstrainedPhaseAngle;
    }

    /**
     * Returns the field pointingTemplate.
     * 
     * @return The field pointingTemplate
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getPointingTemplate() {
        return pointingTemplate;
    }

    /**
     * Returns the field pointingArguments.
     * 
     * @return The field pointingArguments
     */
    public org.ccsds.moims.mo.mps.structures.NamedElementList getPointingArguments() {
        return pointingArguments;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PointingConstraint) {
            if (! super.equals(obj)) {
                return false;
            }
            PointingConstraint other = (PointingConstraint) obj;
            if (pointingFrame == null) {
                if (other.pointingFrame != null) {
                    return false;
                }
            } else {
                if (! pointingFrame.equals(other.pointingFrame)) {
                    return false;
                }
            }
            if (boresight == null) {
                if (other.boresight != null) {
                    return false;
                }
            } else {
                if (! boresight.equals(other.boresight)) {
                    return false;
                }
            }
            if (boresightMargin == null) {
                if (other.boresightMargin != null) {
                    return false;
                }
            } else {
                if (! boresightMargin.equals(other.boresightMargin)) {
                    return false;
                }
            }
            if (phaseAngleMargin == null) {
                if (other.phaseAngleMargin != null) {
                    return false;
                }
            } else {
                if (! phaseAngleMargin.equals(other.phaseAngleMargin)) {
                    return false;
                }
            }
            if (unconstrainedPhaseAngle == null) {
                if (other.unconstrainedPhaseAngle != null) {
                    return false;
                }
            } else {
                if (! unconstrainedPhaseAngle.equals(other.unconstrainedPhaseAngle)) {
                    return false;
                }
            }
            if (pointingTemplate == null) {
                if (other.pointingTemplate != null) {
                    return false;
                }
            } else {
                if (! pointingTemplate.equals(other.pointingTemplate)) {
                    return false;
                }
            }
            if (pointingArguments == null) {
                if (other.pointingArguments != null) {
                    return false;
                }
            } else {
                if (! pointingArguments.equals(other.pointingArguments)) {
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
        hash = 83 * hash + (pointingFrame != null ? pointingFrame.hashCode() : 0);
        hash = 83 * hash + (boresight != null ? boresight.hashCode() : 0);
        hash = 83 * hash + (boresightMargin != null ? boresightMargin.hashCode() : 0);
        hash = 83 * hash + (phaseAngleMargin != null ? phaseAngleMargin.hashCode() : 0);
        hash = 83 * hash + (unconstrainedPhaseAngle != null ? unconstrainedPhaseAngle.hashCode() : 0);
        hash = 83 * hash + (pointingTemplate != null ? pointingTemplate.hashCode() : 0);
        hash = 83 * hash + (pointingArguments != null ? pointingArguments.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(PointingConstraint: ");
        buf.append(super.toString());
        buf.append(", pointingFrame=").append(pointingFrame);
        buf.append(", boresight=").append(boresight);
        buf.append(", boresightMargin=").append(boresightMargin);
        buf.append(", phaseAngleMargin=").append(phaseAngleMargin);
        buf.append(", unconstrainedPhaseAngle=").append(unconstrainedPhaseAngle);
        buf.append(", pointingTemplate=").append(pointingTemplate);
        buf.append(", pointingArguments=").append(pointingArguments);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (boresight == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'boresight' cannot be null!");
        }
        if (pointingTemplate == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'pointingTemplate' cannot be null!");
        }
        encoder.encodeNullableIdentifier(pointingFrame);
        encoder.encodeAbstractElement(boresight);
        encoder.encodeNullableAbstractElement(boresightMargin);
        encoder.encodeNullableAbstractElement(phaseAngleMargin);
        encoder.encodeNullableBoolean(unconstrainedPhaseAngle);
        encoder.encodeIdentifier(pointingTemplate);
        encoder.encodeNullableElement(pointingArguments);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        pointingFrame = decoder.decodeNullableIdentifier();
        boresight = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        boresightMargin = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeNullableAbstractElement();
        phaseAngleMargin = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeNullableAbstractElement();
        unconstrainedPhaseAngle = decoder.decodeNullableBoolean();
        pointingTemplate = decoder.decodeIdentifier();
        pointingArguments = (org.ccsds.moims.mo.mps.structures.NamedElementList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mps.structures.NamedElementList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
