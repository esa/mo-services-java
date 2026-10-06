package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Enumeration;

/**
 * Enumeration class for SubPlanStatusEnum.
 */
public final class SubPlanStatusEnum extends Enumeration {

    private static final long serialVersionUID = 1407374900331008L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900331008L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Enumeration value for ACTIVATED.
     */
    public static final int ACTIVATED_VALUE = 1;

    /**
     * Enumeration singleton for value ACTIVATED.
     */
    public static final SubPlanStatusEnum ACTIVATED = new SubPlanStatusEnum(SubPlanStatusEnum.ACTIVATED_VALUE);

    /**
     * Enumeration value for DEACTIVATED.
     */
    public static final int DEACTIVATED_VALUE = 2;

    /**
     * Enumeration singleton for value DEACTIVATED.
     */
    public static final SubPlanStatusEnum DEACTIVATED = new SubPlanStatusEnum(SubPlanStatusEnum.DEACTIVATED_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final SubPlanStatusEnum[] _ENUMERATIONS = {
        ACTIVATED, DEACTIVATED};

    /**
     * E1: This enumeration may be used to indicate whether or not a given subplan is active.
     */
    public SubPlanStatusEnum() {
        super(-1);
    }

    /**
     * E1: This enumeration may be used to indicate whether or not a given subplan
     * is active.
     * 
     * @param value The value of the Enumeration.
     */
    public SubPlanStatusEnum(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case ACTIVATED_VALUE:
                return "ACTIVATED";
            case DEACTIVATED_VALUE:
                return "DEACTIVATED";
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
    public static SubPlanStatusEnum fromString(String s) {
        switch (s) {
            case "ACTIVATED":
                return SubPlanStatusEnum.ACTIVATED;
            case "DEACTIVATED":
                return SubPlanStatusEnum.DEACTIVATED;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public Enumeration fromValue(Integer value) {
        switch (value) {
            case ACTIVATED_VALUE:
                return SubPlanStatusEnum.ACTIVATED;
            case DEACTIVATED_VALUE:
                return SubPlanStatusEnum.DEACTIVATED;
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
        return 2;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
