package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Enumeration;

/**
 * Enumeration class for ActivityStatusEnum.
 */
public final class ActivityStatusEnum extends Enumeration {

    private static final long serialVersionUID = 1407374900330599L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330599L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Enumeration value for PLANNED.
     */
    public static final int PLANNED_VALUE = 1;

    /**
     * Enumeration singleton for value PLANNED.
     */
    public static final ActivityStatusEnum PLANNED = new ActivityStatusEnum(ActivityStatusEnum.PLANNED_VALUE);

    /**
     * Enumeration value for ACTIVATED.
     */
    public static final int ACTIVATED_VALUE = 2;

    /**
     * Enumeration singleton for value ACTIVATED.
     */
    public static final ActivityStatusEnum ACTIVATED = new ActivityStatusEnum(ActivityStatusEnum.ACTIVATED_VALUE);

    /**
     * Enumeration value for EXECUTING.
     */
    public static final int EXECUTING_VALUE = 3;

    /**
     * Enumeration singleton for value EXECUTING.
     */
    public static final ActivityStatusEnum EXECUTING = new ActivityStatusEnum(ActivityStatusEnum.EXECUTING_VALUE);

    /**
     * Enumeration value for SUSPENDED.
     */
    public static final int SUSPENDED_VALUE = 4;

    /**
     * Enumeration singleton for value SUSPENDED.
     */
    public static final ActivityStatusEnum SUSPENDED = new ActivityStatusEnum(ActivityStatusEnum.SUSPENDED_VALUE);

    /**
     * Enumeration value for TERMINATED.
     */
    public static final int TERMINATED_VALUE = 5;

    /**
     * Enumeration singleton for value TERMINATED.
     */
    public static final ActivityStatusEnum TERMINATED = new ActivityStatusEnum(ActivityStatusEnum.TERMINATED_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final ActivityStatusEnum[] _ENUMERATIONS = {
        PLANNED, ACTIVATED, EXECUTING, SUSPENDED, TERMINATED};

    /**
     * E1: An ActivityStatusEnum represents the set of states possible for an ActivityInstance.
     */
    public ActivityStatusEnum() {
        super(-1);
    }

    /**
     * E1: An ActivityStatusEnum represents the set of states possible for an
     * ActivityInstance.
     * 
     * @param value The value of the Enumeration.
     */
    public ActivityStatusEnum(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case PLANNED_VALUE:
                return "PLANNED";
            case ACTIVATED_VALUE:
                return "ACTIVATED";
            case EXECUTING_VALUE:
                return "EXECUTING";
            case SUSPENDED_VALUE:
                return "SUSPENDED";
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
    public static ActivityStatusEnum fromString(String s) {
        switch (s) {
            case "PLANNED":
                return ActivityStatusEnum.PLANNED;
            case "ACTIVATED":
                return ActivityStatusEnum.ACTIVATED;
            case "EXECUTING":
                return ActivityStatusEnum.EXECUTING;
            case "SUSPENDED":
                return ActivityStatusEnum.SUSPENDED;
            case "TERMINATED":
                return ActivityStatusEnum.TERMINATED;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public Enumeration fromValue(Integer value) {
        switch (value) {
            case PLANNED_VALUE:
                return ActivityStatusEnum.PLANNED;
            case ACTIVATED_VALUE:
                return ActivityStatusEnum.ACTIVATED;
            case EXECUTING_VALUE:
                return ActivityStatusEnum.EXECUTING;
            case SUSPENDED_VALUE:
                return ActivityStatusEnum.SUSPENDED;
            case TERMINATED_VALUE:
                return ActivityStatusEnum.TERMINATED;
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
        return 5;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
