package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Enumeration;

/**
 * Enumeration class for PlanStatusEnum.
 */
public final class PlanStatusEnum extends Enumeration {

    private static final long serialVersionUID = 1407374900330998L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330998L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Enumeration value for DRAFT.
     */
    public static final int DRAFT_VALUE = 1;

    /**
     * Enumeration singleton for value DRAFT.
     */
    public static final PlanStatusEnum DRAFT = new PlanStatusEnum(PlanStatusEnum.DRAFT_VALUE);

    /**
     * Enumeration value for RELEASED.
     */
    public static final int RELEASED_VALUE = 2;

    /**
     * Enumeration singleton for value RELEASED.
     */
    public static final PlanStatusEnum RELEASED = new PlanStatusEnum(PlanStatusEnum.RELEASED_VALUE);

    /**
     * Enumeration value for SUBMITTED.
     */
    public static final int SUBMITTED_VALUE = 3;

    /**
     * Enumeration singleton for value SUBMITTED.
     */
    public static final PlanStatusEnum SUBMITTED = new PlanStatusEnum(PlanStatusEnum.SUBMITTED_VALUE);

    /**
     * Enumeration value for ACTIVATED.
     */
    public static final int ACTIVATED_VALUE = 4;

    /**
     * Enumeration singleton for value ACTIVATED.
     */
    public static final PlanStatusEnum ACTIVATED = new PlanStatusEnum(PlanStatusEnum.ACTIVATED_VALUE);

    /**
     * Enumeration value for TERMINATED.
     */
    public static final int TERMINATED_VALUE = 5;

    /**
     * Enumeration singleton for value TERMINATED.
     */
    public static final PlanStatusEnum TERMINATED = new PlanStatusEnum(PlanStatusEnum.TERMINATED_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final PlanStatusEnum[] _ENUMERATIONS = {
        DRAFT, RELEASED, SUBMITTED, ACTIVATED, TERMINATED};

    /**
     * E1: PlanStatusEnum represents the status of a given Plan object. 
     */
    public PlanStatusEnum() {
        super(-1);
    }

    /**
     * E1: PlanStatusEnum represents the status of a given Plan object. .
     * 
     * @param value The value of the Enumeration.
     */
    public PlanStatusEnum(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case DRAFT_VALUE:
                return "DRAFT";
            case RELEASED_VALUE:
                return "RELEASED";
            case SUBMITTED_VALUE:
                return "SUBMITTED";
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
    public static PlanStatusEnum fromString(String s) {
        switch (s) {
            case "DRAFT":
                return PlanStatusEnum.DRAFT;
            case "RELEASED":
                return PlanStatusEnum.RELEASED;
            case "SUBMITTED":
                return PlanStatusEnum.SUBMITTED;
            case "ACTIVATED":
                return PlanStatusEnum.ACTIVATED;
            case "TERMINATED":
                return PlanStatusEnum.TERMINATED;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public Enumeration fromValue(Integer value) {
        switch (value) {
            case DRAFT_VALUE:
                return PlanStatusEnum.DRAFT;
            case RELEASED_VALUE:
                return PlanStatusEnum.RELEASED;
            case SUBMITTED_VALUE:
                return PlanStatusEnum.SUBMITTED;
            case ACTIVATED_VALUE:
                return PlanStatusEnum.ACTIVATED;
            case TERMINATED_VALUE:
                return PlanStatusEnum.TERMINATED;
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
