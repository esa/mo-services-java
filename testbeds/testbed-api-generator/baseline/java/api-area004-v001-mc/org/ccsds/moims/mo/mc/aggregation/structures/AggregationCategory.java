package org.ccsds.moims.mo.mc.aggregation.structures;

import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Enumeration;

/**
 * Enumeration class for AggregationCategory.
 */
public final class AggregationCategory extends Enumeration {

    private static final long serialVersionUID = 1125925693423623L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125925693423623L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Enumeration value for GENERAL.
     */
    public static final int GENERAL_VALUE = 1;

    /**
     * Enumeration singleton for value GENERAL.
     */
    public static final AggregationCategory GENERAL = new AggregationCategory(AggregationCategory.GENERAL_VALUE);

    /**
     * Enumeration value for DIAGNOSTIC.
     */
    public static final int DIAGNOSTIC_VALUE = 2;

    /**
     * Enumeration singleton for value DIAGNOSTIC.
     */
    public static final AggregationCategory DIAGNOSTIC = new AggregationCategory(AggregationCategory.DIAGNOSTIC_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final AggregationCategory[] _ENUMERATIONS = {
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
    public static AggregationCategory fromString(String s) {
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
    public Enumeration fromValue(Integer value) {
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
