package org.ccsds.moims.mo.mps.structures;

/**
 * E1: Represents a specific period relative to two events that mark the start
 * and end of the EventWindow.
 */
public final class EventWindow implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1407374900330502L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330502L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The start of the event window is relative to the referenced startEvent.
     */
    private org.ccsds.moims.mo.mal.structures.Element startEvent;

    /**
     * The start of the event window is offset by the defined time period from
     * the startEvent.  A positive offset implies a shift later in time. Default
     * is no offset.
     */
    private org.ccsds.moims.mo.mal.structures.Element startOffset;

    /**
     * The end of the event window is relative to the referenced endEvent.
     */
    private org.ccsds.moims.mo.mal.structures.Element endEvent;

    /**
     * The end of the event window is offset by the defined time period from the
     * endEvent.  A positive offset implies a shift later in time. Default is
     * no offset.
     */
    private org.ccsds.moims.mo.mal.structures.Element endOffset;

    /**
     * Default constructor for EventWindow.
     * 
     */
    public EventWindow() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param startEvent The start of the event window is relative to the referenced startEvent.
     * @param startOffset The start of the event window is offset by the defined time period from the startEvent.  A positive offset implies a shift later in time. Default is no offset.
     * @param endEvent The end of the event window is relative to the referenced endEvent.
     * @param endOffset The end of the event window is offset by the defined time period from the endEvent.  A positive offset implies a shift later in time. Default is no offset.
     */
    public EventWindow(org.ccsds.moims.mo.mal.structures.Element startEvent,
            org.ccsds.moims.mo.mal.structures.Element startOffset,
            org.ccsds.moims.mo.mal.structures.Element endEvent,
            org.ccsds.moims.mo.mal.structures.Element endOffset) {
        this.startEvent = startEvent;
        this.startOffset = startOffset;
        this.endEvent = endEvent;
        this.endOffset = endOffset;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param startEvent The start of the event window is relative to the referenced startEvent.
     * @param endEvent The end of the event window is relative to the referenced endEvent.
     */
    public EventWindow(org.ccsds.moims.mo.mal.structures.Element startEvent,
            org.ccsds.moims.mo.mal.structures.Element endEvent) {
        this.startEvent = startEvent;
        this.startOffset = null;
        this.endEvent = endEvent;
        this.endOffset = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.EventWindow();
    }

    /**
     * Returns the field startEvent.
     * 
     * @return The field startEvent
     */
    public org.ccsds.moims.mo.mal.structures.Element getStartEvent() {
        return startEvent;
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
     * Returns the field endEvent.
     * 
     * @return The field endEvent
     */
    public org.ccsds.moims.mo.mal.structures.Element getEndEvent() {
        return endEvent;
    }

    /**
     * Returns the field endOffset.
     * 
     * @return The field endOffset
     */
    public org.ccsds.moims.mo.mal.structures.Element getEndOffset() {
        return endOffset;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EventWindow) {
            EventWindow other = (EventWindow) obj;
            if (startEvent == null) {
                if (other.startEvent != null) {
                    return false;
                }
            } else {
                if (! startEvent.equals(other.startEvent)) {
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
            if (endEvent == null) {
                if (other.endEvent != null) {
                    return false;
                }
            } else {
                if (! endEvent.equals(other.endEvent)) {
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
        int hash = 7;
        hash = 83 * hash + (startEvent != null ? startEvent.hashCode() : 0);
        hash = 83 * hash + (startOffset != null ? startOffset.hashCode() : 0);
        hash = 83 * hash + (endEvent != null ? endEvent.hashCode() : 0);
        hash = 83 * hash + (endOffset != null ? endOffset.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(EventWindow: ");
        buf.append("startEvent=").append(startEvent);
        buf.append(", startOffset=").append(startOffset);
        buf.append(", endEvent=").append(endEvent);
        buf.append(", endOffset=").append(endOffset);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (startEvent == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'startEvent' cannot be null!");
        }
        if (endEvent == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'endEvent' cannot be null!");
        }
        encoder.encodeAbstractElement(startEvent);
        encoder.encodeNullableAbstractElement(startOffset);
        encoder.encodeAbstractElement(endEvent);
        encoder.encodeNullableAbstractElement(endOffset);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        startEvent = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        startOffset = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeNullableAbstractElement();
        endEvent = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        endOffset = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeNullableAbstractElement();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
