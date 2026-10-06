package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Enumeration;

/**
 * Enumeration class for EventStatusEnum.
 */
public final class EventStatusEnum extends Enumeration {

    private static final long serialVersionUID = 1407374900330699L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330699L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Enumeration value for GROUP.
     */
    public static final int GROUP_VALUE = 1;

    /**
     * Enumeration singleton for value GROUP.
     */
    public static final EventStatusEnum GROUP = new EventStatusEnum(EventStatusEnum.GROUP_VALUE);

    /**
     * Enumeration value for PLANNED.
     */
    public static final int PLANNED_VALUE = 2;

    /**
     * Enumeration singleton for value PLANNED.
     */
    public static final EventStatusEnum PLANNED = new EventStatusEnum(EventStatusEnum.PLANNED_VALUE);

    /**
     * Enumeration value for ACTIVATED.
     */
    public static final int ACTIVATED_VALUE = 3;

    /**
     * Enumeration singleton for value ACTIVATED.
     */
    public static final EventStatusEnum ACTIVATED = new EventStatusEnum(EventStatusEnum.ACTIVATED_VALUE);

    /**
     * Enumeration value for TERMINATED.
     */
    public static final int TERMINATED_VALUE = 4;

    /**
     * Enumeration singleton for value TERMINATED.
     */
    public static final EventStatusEnum TERMINATED = new EventStatusEnum(EventStatusEnum.TERMINATED_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final EventStatusEnum[] _ENUMERATIONS = {
        GROUP, PLANNED, ACTIVATED, TERMINATED};

    /**
     * E1: The EventStatusEnum represents the status of a given EventInstance.
     */
    public EventStatusEnum() {
        super(-1);
    }

    /**
     * E1: The EventStatusEnum represents the status of a given EventInstance.
     * 
     * @param value The value of the Enumeration.
     */
    public EventStatusEnum(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case GROUP_VALUE:
                return "GROUP";
            case PLANNED_VALUE:
                return "PLANNED";
            case ACTIVATED_VALUE:
                return "ACTIVATED";
            case TERMINATED_VALUE:
                return "TERMINATED";
            default:
                throw new RuntimeException("Unknown ordinal!");
        }
    }

    /**
     * Returns the enumeration element represented by the supplied string, or
     * null if not matched.
     * 
     * @param s s The string to search for.
     * @return The matched enumeration element, or null if not matched.
     */
    public static EventStatusEnum fromString(String s) {
        switch (s) {
            case "GROUP":
                return EventStatusEnum.GROUP;
            case "PLANNED":
                return EventStatusEnum.PLANNED;
            case "ACTIVATED":
                return EventStatusEnum.ACTIVATED;
            case "TERMINATED":
                return EventStatusEnum.TERMINATED;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public Enumeration fromValue(Integer value) {
        switch (value) {
            case GROUP_VALUE:
                return EventStatusEnum.GROUP;
            case PLANNED_VALUE:
                return EventStatusEnum.PLANNED;
            case ACTIVATED_VALUE:
                return EventStatusEnum.ACTIVATED;
            case TERMINATED_VALUE:
                return EventStatusEnum.TERMINATED;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided value: " + value);
        }
    }

    @Override
    public Element createElement() {
        return _ENUMERATIONS[0];
    }

    @Override
    public int getEnumSize() {
        return 4;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
