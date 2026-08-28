package org.ccsds.moims.mo.mc.aggregation.structures;

/**
 * Enumeration class for AggregationCategory.
 */
public final class AggregationCategory extends org.ccsds.moims.mo.mal.structures.Enumeration {

    private static final long serialVersionUID = 1125925693423623L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125925693423623L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Enumeration value for GENERAL.
     */
    public static final int GENERAL_VALUE = 1;

    /**
     * Enumeration singleton for value GENERAL.
     */
    public static final org.ccsds.moims.mo.mc.aggregation.structures.AggregationCategory GENERAL = new org.ccsds.moims.mo.mc.aggregation.structures.AggregationCategory(org.ccsds.moims.mo.mc.aggregation.structures.AggregationCategory.GENERAL_VALUE);

    /**
     * Enumeration value for DIAGNOSTIC.
     */
    public static final int DIAGNOSTIC_VALUE = 2;

    /**
     * Enumeration singleton for value DIAGNOSTIC.
     */
    public static final org.ccsds.moims.mo.mc.aggregation.structures.AggregationCategory DIAGNOSTIC = new org.ccsds.moims.mo.mc.aggregation.structures.AggregationCategory(org.ccsds.moims.mo.mc.aggregation.structures.AggregationCategory.DIAGNOSTIC_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final org.ccsds.moims.mo.mc.aggregation.structures.AggregationCategory[] _ENUMERATIONS = {
        GENERAL, DIAGNOSTIC};

    /**
     * AggregationCategory is an enumeration definition holding the categories of aggregations.
     */
    public AggregationCategory() {
        super(-1);
    }

    /**
     * AggregationCategory is an enumeration definition holding the categories
     * of aggregations.
     * 
     * @param value The value of the Enumeration.
     */
    public AggregationCategory(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case GENERAL_VALUE:
                return "GENERAL";
            case DIAGNOSTIC_VALUE:
                return "DIAGNOSTIC";
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
    public static org.ccsds.moims.mo.mc.aggregation.structures.AggregationCategory fromString(String s) {
        switch (s) {
            case "GENERAL":
                return AggregationCategory.GENERAL;
            case "DIAGNOSTIC":
                return AggregationCategory.DIAGNOSTIC;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Enumeration fromValue(Integer value) {
        switch (value) {
            case GENERAL_VALUE:
                return AggregationCategory.GENERAL;
            case DIAGNOSTIC_VALUE:
                return AggregationCategory.DIAGNOSTIC;
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
