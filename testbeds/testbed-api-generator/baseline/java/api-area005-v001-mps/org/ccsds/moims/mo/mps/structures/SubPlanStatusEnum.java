package org.ccsds.moims.mo.mps.structures;

/**
 * Enumeration class for SubPlanStatusEnum.
 */
public final class SubPlanStatusEnum extends org.ccsds.moims.mo.mal.structures.Enumeration {

    private static final long serialVersionUID = 1407374900331008L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900331008L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Enumeration value for ACTIVATED.
     */
    public static final int ACTIVATED_VALUE = 1;

    /**
     * Enumeration singleton for value ACTIVATED.
     */
    public static final org.ccsds.moims.mo.mps.structures.SubPlanStatusEnum ACTIVATED = new org.ccsds.moims.mo.mps.structures.SubPlanStatusEnum(org.ccsds.moims.mo.mps.structures.SubPlanStatusEnum.ACTIVATED_VALUE);

    /**
     * Enumeration value for DEACTIVATED.
     */
    public static final int DEACTIVATED_VALUE = 2;

    /**
     * Enumeration singleton for value DEACTIVATED.
     */
    public static final org.ccsds.moims.mo.mps.structures.SubPlanStatusEnum DEACTIVATED = new org.ccsds.moims.mo.mps.structures.SubPlanStatusEnum(org.ccsds.moims.mo.mps.structures.SubPlanStatusEnum.DEACTIVATED_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final org.ccsds.moims.mo.mps.structures.SubPlanStatusEnum[] _ENUMERATIONS = {
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
    public static org.ccsds.moims.mo.mps.structures.SubPlanStatusEnum fromString(String s) {
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
    public org.ccsds.moims.mo.mal.structures.Enumeration fromValue(Integer value) {
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
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return _ENUMERATIONS[0];
    }

    @Override
    public int getEnumSize() {
        return 2;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
