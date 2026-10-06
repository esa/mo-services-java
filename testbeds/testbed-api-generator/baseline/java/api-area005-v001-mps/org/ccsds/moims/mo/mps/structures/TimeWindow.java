package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * E1: Represents a specific period of time, specified as two Expressions
 * of type Time defining the start and end of the TimeWindow.
 */
public final class TimeWindow implements Composite {

    private static final long serialVersionUID = 1407374900330501L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330501L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Start time of the time window.
     */
    private Element start;

    /**
     * End time of the time window.  Shall not be earlier in time than the start
     * of the time window.
     */
    private Element end;

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
    public TimeWindow(Element start,
            Element end) {
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
        encoder.encodeAbstractElement(start);
        encoder.encodeAbstractElement(end);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        start = (Element) decoder.decodeAbstractElement();
        end = (Element) decoder.decodeAbstractElement();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
