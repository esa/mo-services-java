package org.ccsds.moims.mo.mps.structures;

/**
 * E1: Represents a specific period of time, specified as two Expressions
 * of type Time defining the start and end of the TimeWindow.
 */
public final class TimeWindow implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1407374900330501L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330501L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Start time of the time window.
     */
    private org.ccsds.moims.mo.mal.structures.Element start;

    /**
     * End time of the time window.  Shall not be earlier in time than the start
     * of the time window.
     */
    private org.ccsds.moims.mo.mal.structures.Element end;

    /**
     * Default constructor for TimeWindow.
     * 
     */
    public TimeWindow() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param start Start time of the time window.
     * @param end End time of the time window.  Shall not be earlier in time than the start of the time window.
     */
    public TimeWindow(org.ccsds.moims.mo.mal.structures.Element start,
            org.ccsds.moims.mo.mal.structures.Element end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.TimeWindow();
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TimeWindow) {
            TimeWindow other = (TimeWindow) obj;
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
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (start != null ? start.hashCode() : 0);
        hash = 83 * hash + (end != null ? end.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(TimeWindow: ");
        buf.append("start=").append(start);
        buf.append(", end=").append(end);
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
        encoder.encodeAbstractElement(start);
        encoder.encodeAbstractElement(end);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        start = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        end = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
