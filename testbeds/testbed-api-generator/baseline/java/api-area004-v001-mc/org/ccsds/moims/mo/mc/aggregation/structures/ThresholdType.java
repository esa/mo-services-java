package org.ccsds.moims.mo.mc.aggregation.structures;

/**
 * Enumeration class for ThresholdType.
 */
public final class ThresholdType extends org.ccsds.moims.mo.mal.structures.Enumeration {

    private static final long serialVersionUID = 1125925693423624L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125925693423624L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Enumeration value for PERCENTAGE.
     */
    public static final int PERCENTAGE_VALUE = 1;

    /**
     * Enumeration singleton for value PERCENTAGE.
     */
    public static final org.ccsds.moims.mo.mc.aggregation.structures.ThresholdType PERCENTAGE = new org.ccsds.moims.mo.mc.aggregation.structures.ThresholdType(org.ccsds.moims.mo.mc.aggregation.structures.ThresholdType.PERCENTAGE_VALUE);

    /**
     * Enumeration value for DELTA.
     */
    public static final int DELTA_VALUE = 2;

    /**
     * Enumeration singleton for value DELTA.
     */
    public static final org.ccsds.moims.mo.mc.aggregation.structures.ThresholdType DELTA = new org.ccsds.moims.mo.mc.aggregation.structures.ThresholdType(org.ccsds.moims.mo.mc.aggregation.structures.ThresholdType.DELTA_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final org.ccsds.moims.mo.mc.aggregation.structures.ThresholdType[] _ENUMERATIONS = {
        PERCENTAGE, DELTA};

    /**
     * ThresholdType is an enumeration definition holding the types of filtering thresholds.
     */
    public ThresholdType() {
        super(-1);
    }

    /**
     * ThresholdType is an enumeration definition holding the types of filtering
     * thresholds.
     * 
     * @param value The value of the Enumeration.
     */
    public ThresholdType(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case PERCENTAGE_VALUE:
                return "PERCENTAGE";
            case DELTA_VALUE:
                return "DELTA";
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
    public static org.ccsds.moims.mo.mc.aggregation.structures.ThresholdType fromString(String s) {
        switch (s) {
            case "PERCENTAGE":
                return ThresholdType.PERCENTAGE;
            case "DELTA":
                return ThresholdType.DELTA;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Enumeration fromValue(Integer value) {
        switch (value) {
            case PERCENTAGE_VALUE:
                return ThresholdType.PERCENTAGE;
            case DELTA_VALUE:
                return ThresholdType.DELTA;
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
