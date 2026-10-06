package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * E1: The PlannedItems section of the Plan specifies the set of planning
 * activities and planning events contained within the Plan.  It comprises
 * two lists of contained MO objects:  one of EventInstances and one of ActivityInstances.
 * Both lists can be empty.
 */
public final class PlannedItems implements Composite {

    private static final long serialVersionUID = 1407374900331000L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900331000L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * List of planned events contained within the Plan.
     */
    private EventInstanceList plannedEvents;

    /**
     * List of planned activities contained within the Plan.
     */
    private ActivityInstanceList plannedActivities;

    /**
     * Default constructor for PlannedItems.
     * 
     */
    public PlannedItems() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param plannedEvents List of planned events contained within the Plan.
     * @param plannedActivities List of planned activities contained within the Plan.
     */
    public PlannedItems(EventInstanceList plannedEvents,
            ActivityInstanceList plannedActivities) {
        this.plannedEvents = plannedEvents;
        this.plannedActivities = plannedActivities;
    }

    @Override
    public Element createElement() {
        return new PlannedItems();
    }

    /**
     * Returns the field plannedEvents.
     * 
     * @return The field plannedEvents
     */
    public EventInstanceList getPlannedEvents() {
        return plannedEvents;
    }

    /**
     * Returns the field plannedActivities.
     * 
     * @return The field plannedActivities
     */
    public ActivityInstanceList getPlannedActivities() {
        return plannedActivities;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlannedItems) {
            PlannedItems other = (PlannedItems) obj;
            if (plannedEvents == null) {
                if (other.plannedEvents != null) {
                    return false;
                }
            } else {
                if (! plannedEvents.equals(other.plannedEvents)) {
                    return false;
                }
            }
            if (plannedActivities == null) {
                if (other.plannedActivities != null) {
                    return false;
                }
            } else {
                if (! plannedActivities.equals(other.plannedActivities)) {
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
        hash = 83 * hash + (plannedEvents != null ? plannedEvents.hashCode() : 0);
        hash = 83 * hash + (plannedActivities != null ? plannedActivities.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(PlannedItems: ");
        buf.append("plannedEvents=").append(plannedEvents);
        buf.append(", plannedActivities=").append(plannedActivities);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        encoder.encodeNullableElement(plannedEvents);
        encoder.encodeNullableElement(plannedActivities);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        plannedEvents = (EventInstanceList) decoder.decodeNullableElement(new EventInstanceList());
        plannedActivities = (ActivityInstanceList) decoder.decodeNullableElement(new ActivityInstanceList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
