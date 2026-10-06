package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * E4: A ProfileSegment defines the time range and interpolation method for
 * a set of ProfileEntries.
 */
public final class ProfileSegment implements Composite {

    private static final long serialVersionUID = 1407374900330800L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330800L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Interpolation method to be applied for values lying between points defined
     * in the profile segment. Default = Step.
     */
    private InterpolationTypeEnum interpolation;

    /**
     * Start of time range covered by the profile segment.
     */
    private Element start;

    /**
     * End of time range covered by the profile segment.
     */
    private Element end;

    /**
     * Indicates whether the start time is included in the profile segment. Default
     * = True.
     */
    private Boolean startIncluded;

    /**
     * Indicates whether the end time is included in the profile segment.  This
     * allows the same time to be used as the end of one segment and the start
     * of another. Default = False.
     */
    private Boolean endIncluded;

    /**
     * Set of profile entries (resource value points).
     */
    private ProfileEntryList profileEntries;

    /**
     * Default constructor for ProfileSegment.
     * 
     */
    public ProfileSegment() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param interpolation Interpolation method to be applied for values lying between points defined in the profile segment. Default = Step.
     * @param start Start of time range covered by the profile segment.
     * @param end End of time range covered by the profile segment.
     * @param startIncluded Indicates whether the start time is included in the profile segment. Default = True.
     * @param endIncluded Indicates whether the end time is included in the profile segment.  This allows the same time to be used as the end of one segment and the start of another. Default = False.
     * @param profileEntries Set of profile entries (resource value points).
     */
    public ProfileSegment(InterpolationTypeEnum interpolation,
            Element start,
            Element end,
            Boolean startIncluded,
            Boolean endIncluded,
            ProfileEntryList profileEntries) {
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
     * @param start Start of time range covered by the profile segment.
     * @param end End of time range covered by the profile segment.
     * @param profileEntries Set of profile entries (resource value points).
     */
    public ProfileSegment(Element start,
            Element end,
            ProfileEntryList profileEntries) {
        this.interpolation = null;
        this.start = start;
        this.end = end;
        this.startIncluded = null;
        this.endIncluded = null;
        this.profileEntries = profileEntries;
    }

    @Override
    public Element createElement() {
        return new ProfileSegment();
    }

    /**
     * Returns the field interpolation.
     * 
     * @return The field interpolation
     */
    public InterpolationTypeEnum getInterpolation() {
        return interpolation;
    }

    /**
     * Returns the field start.
     * 
     * @return The field start
     */
    public Element getStart() {
        return start;
    }

    /**
     * Returns the field end.
     * 
     * @return The field end
     */
    public Element getEnd() {
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
    public ProfileEntryList getProfileEntries() {
        return profileEntries;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ProfileSegment) {
            ProfileSegment other = (ProfileSegment) obj;
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
        buf.append("(ProfileSegment: ");
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
    public void encode(MALEncoder encoder) throws MALException {
        if (start == null) {
            throw new MALException("The field 'start' cannot be null!");
        }
        if (end == null) {
            throw new MALException("The field 'end' cannot be null!");
        }
        if (profileEntries == null) {
            throw new MALException("The field 'profileEntries' cannot be null!");
        }
        encoder.encodeNullableElement(interpolation);
        encoder.encodeAbstractElement(start);
        encoder.encodeAbstractElement(end);
        encoder.encodeNullableBoolean(startIncluded);
        encoder.encodeNullableBoolean(endIncluded);
        encoder.encodeElement(profileEntries);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        interpolation = (InterpolationTypeEnum) decoder.decodeNullableElement(InterpolationTypeEnum.STEP);
        start = (Element) decoder.decodeAbstractElement();
        end = (Element) decoder.decodeAbstractElement();
        startIncluded = decoder.decodeNullableBoolean();
        endIncluded = decoder.decodeNullableBoolean();
        profileEntries = (ProfileEntryList) decoder.decodeElement(new ProfileEntryList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
