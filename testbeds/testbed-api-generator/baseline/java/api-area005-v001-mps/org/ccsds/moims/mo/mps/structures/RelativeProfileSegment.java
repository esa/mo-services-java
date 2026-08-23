package org.ccsds.moims.mo.mps.structures;

/**
 * E4: A RelativeResourceSegment defines the time range and interpolation
 * method for a set of RelativeProfileEntries.
 */
public final class RelativeProfileSegment implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1407374900330804L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330804L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Interpolation method to be applied for values lying between points defined
     * in the relative profile segment. Default = Step.
     */
    private org.ccsds.moims.mo.mps.structures.InterpolationTypeEnum interpolation;

    /**
     * Relative start of time range covered by the relative profile segment.
     */
    private org.ccsds.moims.mo.mal.structures.Element start;

    /**
     * Relative end of time range covered by the relative profile segment.
     */
    private org.ccsds.moims.mo.mal.structures.Element end;

    /**
     * Indicates whether the start time is included in the relative profile segment.
     * Default = True.
     */
    private Boolean startIncluded;

    /**
     * Indicates whether the end time is included in the relative profile segment.
     * This allows the same time to be used as the end of one segment and the
     * start of another. Default = False.
     */
    private Boolean endIncluded;

    /**
     * Set of relative profile entries (resource value points).
     */
    private org.ccsds.moims.mo.mps.structures.RelativeProfileEntryList profileEntries;

    /**
     * Default constructor for RelativeProfileSegment.
     * 
     */
    public RelativeProfileSegment() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param interpolation Interpolation method to be applied for values lying between points defined in the relative profile segment. Default = Step.
     * @param start Relative start of time range covered by the relative profile segment.
     * @param end Relative end of time range covered by the relative profile segment.
     * @param startIncluded Indicates whether the start time is included in the relative profile segment. Default = True.
     * @param endIncluded Indicates whether the end time is included in the relative profile segment.  This allows the same time to be used as the end of one segment and the start of another. Default = False.
     * @param profileEntries Set of relative profile entries (resource value points).
     */
    public RelativeProfileSegment(org.ccsds.moims.mo.mps.structures.InterpolationTypeEnum interpolation,
            org.ccsds.moims.mo.mal.structures.Element start,
            org.ccsds.moims.mo.mal.structures.Element end,
            Boolean startIncluded,
            Boolean endIncluded,
            org.ccsds.moims.mo.mps.structures.RelativeProfileEntryList profileEntries) {
        this.interpolation = interpolation;
        this.start = start;
        this.end = end;
        this.startIncluded = startIncluded;
        this.endIncluded = endIncluded;
        this.profileEntries = profileEntries;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param start Relative start of time range covered by the relative profile segment.
     * @param end Relative end of time range covered by the relative profile segment.
     * @param profileEntries Set of relative profile entries (resource value points).
     */
    public RelativeProfileSegment(org.ccsds.moims.mo.mal.structures.Element start,
            org.ccsds.moims.mo.mal.structures.Element end,
            org.ccsds.moims.mo.mps.structures.RelativeProfileEntryList profileEntries) {
        this.interpolation = null;
        this.start = start;
        this.end = end;
        this.startIncluded = null;
        this.endIncluded = null;
        this.profileEntries = profileEntries;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.RelativeProfileSegment();
    }

    /**
     * Returns the field interpolation.
     * 
     * @return The field interpolation
     */
    public org.ccsds.moims.mo.mps.structures.InterpolationTypeEnum getInterpolation() {
        return interpolation;
    }

    /**
     * Returns the field start.
     * 
     * @return The field start
     */
    public org.ccsds.moims.mo.mal.structures.Element getStart() {
        return start;
    }

    /**
     * Returns the field end.
     * 
     * @return The field end
     */
    public org.ccsds.moims.mo.mal.structures.Element getEnd() {
        return end;
    }

    /**
     * Returns the field startIncluded.
     * 
     * @return The field startIncluded
     */
    public Boolean getStartIncluded() {
        return startIncluded;
    }

    /**
     * Returns the field endIncluded.
     * 
     * @return The field endIncluded
     */
    public Boolean getEndIncluded() {
        return endIncluded;
    }

    /**
     * Returns the field profileEntries.
     * 
     * @return The field profileEntries
     */
    public org.ccsds.moims.mo.mps.structures.RelativeProfileEntryList getProfileEntries() {
        return profileEntries;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RelativeProfileSegment) {
            RelativeProfileSegment other = (RelativeProfileSegment) obj;
            if (interpolation == null) {
                if (other.interpolation != null) {
                    return false;
                }
            } else {
                if (! interpolation.equals(other.interpolation)) {
                    return false;
                }
            }
            if (start == null) {
                if (other.start != null) {
                    return false;
                }
            } else {
                if (! start.equals(other.start)) {
                    return false;
                }
            }
            if (end == null) {
                if (other.end != null) {
                    return false;
                }
            } else {
                if (! end.equals(other.end)) {
                    return false;
                }
            }
            if (startIncluded == null) {
                if (other.startIncluded != null) {
                    return false;
                }
            } else {
                if (! startIncluded.equals(other.startIncluded)) {
                    return false;
                }
            }
            if (endIncluded == null) {
                if (other.endIncluded != null) {
                    return false;
                }
            } else {
                if (! endIncluded.equals(other.endIncluded)) {
                    return false;
                }
            }
            if (profileEntries == null) {
                if (other.profileEntries != null) {
                    return false;
                }
            } else {
                if (! profileEntries.equals(other.profileEntries)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (interpolation != null ? interpolation.hashCode() : 0);
        hash = 83 * hash + (start != null ? start.hashCode() : 0);
        hash = 83 * hash + (end != null ? end.hashCode() : 0);
        hash = 83 * hash + (startIncluded != null ? startIncluded.hashCode() : 0);
        hash = 83 * hash + (endIncluded != null ? endIncluded.hashCode() : 0);
        hash = 83 * hash + (profileEntries != null ? profileEntries.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(RelativeProfileSegment: ");
        buf.append("interpolation=").append(interpolation);
        buf.append(", start=").append(start);
        buf.append(", end=").append(end);
        buf.append(", startIncluded=").append(startIncluded);
        buf.append(", endIncluded=").append(endIncluded);
        buf.append(", profileEntries=").append(profileEntries);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (start == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'start' cannot be null!");
        }
        if (end == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'end' cannot be null!");
        }
        if (profileEntries == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'profileEntries' cannot be null!");
        }
        encoder.encodeNullableElement(interpolation);
        encoder.encodeAbstractElement(start);
        encoder.encodeAbstractElement(end);
        encoder.encodeNullableBoolean(startIncluded);
        encoder.encodeNullableBoolean(endIncluded);
        encoder.encodeElement(profileEntries);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        interpolation = (org.ccsds.moims.mo.mps.structures.InterpolationTypeEnum) decoder.decodeNullableElement(org.ccsds.moims.mo.mps.structures.InterpolationTypeEnum.STEP);
        start = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        end = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        startIncluded = decoder.decodeNullableBoolean();
        endIncluded = decoder.decodeNullableBoolean();
        profileEntries = (org.ccsds.moims.mo.mps.structures.RelativeProfileEntryList) decoder.decodeElement(new org.ccsds.moims.mo.mps.structures.RelativeProfileEntryList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
