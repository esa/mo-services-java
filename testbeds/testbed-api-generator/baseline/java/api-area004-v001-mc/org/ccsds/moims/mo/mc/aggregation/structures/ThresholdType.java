package org.ccsds.moims.mo.mc.aggregation.structures;

import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Enumeration;

/**
 * Enumeration class for ThresholdType.
 */
public final class ThresholdType extends Enumeration {

    private static final long serialVersionUID = 1125925693423624L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125925693423624L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Enumeration value for PERCENTAGE.
     */
    public static final int PERCENTAGE_VALUE = 1;

    /**
     * Enumeration singleton for value PERCENTAGE.
     */
    public static final ThresholdType PERCENTAGE = new ThresholdType(ThresholdType.PERCENTAGE_VALUE);

    /**
     * Enumeration value for DELTA.
     */
    public static final int DELTA_VALUE = 2;

    /**
     * Enumeration singleton for value DELTA.
     */
    public static final ThresholdType DELTA = new ThresholdType(ThresholdType.DELTA_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final ThresholdType[] _ENUMERATIONS = {
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
    public static ThresholdType fromString(String s) {
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
    public Enumeration fromValue(Integer value) {
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
