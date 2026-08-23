package org.ccsds.moims.mo.mps.structures;

/**
 * E1: A repetition is used to specify the repeated instantiation of a [set
 * of] planning activities.  Multiple subtypes of Repetition are defined to
 * support the specification of repeat cycles by different criteria.  It can
 * be used in the context of a planning request to specify a standing order
 * for repeated execution of the [set of] planning activities. In the context
 * of an ActivityNode embedded within a planning request (see 4.5.2.3.2),
 * it is possible to nest one Repetition inside another, enabling the specification
 * of complex repetitive sequences of activities.
 */
public abstract class Repetition implements org.ccsds.moims.mo.mal.structures.Composite {

    /**
     * Maximum number of repeat cycles/instances. If not specified there is no
     * limit to the number of repetitions.
     */
    private Integer count;

    /**
     * Time period over which the repetition is applicable. If not specified repetition
     * continues indefinitely.
     */
    private org.ccsds.moims.mo.mps.structures.TimeWindow timeWindow;

    /**
     * Specifies whether the repetition interval is Relative to the previous occurrence,
     * or Absolute for all occurrences.
     */
    private org.ccsds.moims.mo.mps.structures.SeparationTypeEnum separationType;

    /**
     * Default constructor for Repetition.
     * 
     */
    public Repetition() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param count Maximum number of repeat cycles/instances. If not specified there is no limit to the number of repetitions.
     * @param timeWindow Time period over which the repetition is applicable. If not specified repetition continues indefinitely.
     * @param separationType Specifies whether the repetition interval is Relative to the previous occurrence, or Absolute for all occurrences.
     */
    public Repetition(Integer count,
            org.ccsds.moims.mo.mps.structures.TimeWindow timeWindow,
            org.ccsds.moims.mo.mps.structures.SeparationTypeEnum separationType) {
        this.count = count;
        this.timeWindow = timeWindow;
        this.separationType = separationType;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param separationType Specifies whether the repetition interval is Relative to the previous occurrence, or Absolute for all occurrences.
     */
    public Repetition(org.ccsds.moims.mo.mps.structures.SeparationTypeEnum separationType) {
        this.count = null;
        this.timeWindow = null;
        this.separationType = separationType;
    }

    /**
     * Returns the field count.
     * 
     * @return The field count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * Returns the field timeWindow.
     * 
     * @return The field timeWindow
     */
    public org.ccsds.moims.mo.mps.structures.TimeWindow getTimeWindow() {
        return timeWindow;
    }

    /**
     * Returns the field separationType.
     * 
     * @return The field separationType
     */
    public org.ccsds.moims.mo.mps.structures.SeparationTypeEnum getSeparationType() {
        return separationType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Repetition) {
            Repetition other = (Repetition) obj;
            if (count == null) {
                if (other.count != null) {
                    return false;
                }
            } else {
                if (! count.equals(other.count)) {
                    return false;
                }
            }
            if (timeWindow == null) {
                if (other.timeWindow != null) {
                    return false;
                }
            } else {
                if (! timeWindow.equals(other.timeWindow)) {
                    return false;
                }
            }
            if (separationType == null) {
                if (other.separationType != null) {
                    return false;
                }
            } else {
                if (! separationType.equals(other.separationType)) {
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
        hash = 83 * hash + (count != null ? count.hashCode() : 0);
        hash = 83 * hash + (timeWindow != null ? timeWindow.hashCode() : 0);
        hash = 83 * hash + (separationType != null ? separationType.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(Repetition: ");
        buf.append("count=").append(count);
        buf.append(", timeWindow=").append(timeWindow);
        buf.append(", separationType=").append(separationType);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (separationType == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'separationType' cannot be null!");
        }
        encoder.encodeNullableInteger(count);
        encoder.encodeNullableElement(timeWindow);
        encoder.encodeElement(separationType);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        count = decoder.decodeNullableInteger();
        timeWindow = (org.ccsds.moims.mo.mps.structures.TimeWindow) decoder.decodeNullableElement(new org.ccsds.moims.mo.mps.structures.TimeWindow());
        separationType = (org.ccsds.moims.mo.mps.structures.SeparationTypeEnum) decoder.decodeElement(org.ccsds.moims.mo.mps.structures.SeparationTypeEnum.RELATIVE);
        return this;
    }

}
