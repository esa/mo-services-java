package org.ccsds.moims.mo.mpd.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Time;

/**
 * The TimeWindow represents a specific period, between the start and end
 * of a time window.
 */
public final class TimeWindow implements Composite {

    private static final long serialVersionUID = 2533274807173127L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 2533274807173127L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The start time of the time window.
     */
    private Time start;

    /**
     * The end time of the time window.
     */
    private Time end;

    /**
     * Default constructor for TimeWindow.
     * 
     */
    public TimeWindow() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param start The start time of the time window.
     * @param end The end time of the time window.
     */
    public TimeWindow(Time start,
            Time end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Element createElement() {
        return new TimeWindow();
    }

    /**
     * Returns the field start.
     * 
     * @return The field start
     */
    public Time getStart() {
        return start;
    }

    /**
     * Returns the field end.
     * 
     * @return The field end
     */
    public Time getEnd() {
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
    public void encode(MALEncoder encoder) throws MALException {
        if (start == null) {
            throw new MALException("The field 'start' cannot be null!");
        }
        if (end == null) {
            throw new MALException("The field 'end' cannot be null!");
        }
        encoder.encodeTime(start);
        encoder.encodeTime(end);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        start = decoder.decodeTime();
        end = decoder.decodeTime();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
