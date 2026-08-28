package org.ccsds.moims.mo.mc.aggregation.structures;

/**
 * Enumeration class for GenerationMode.
 */
public final class GenerationMode extends org.ccsds.moims.mo.mal.structures.Enumeration {

    private static final long serialVersionUID = 1125925693423625L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125925693423625L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Enumeration value for ADHOC.
     */
    public static final int ADHOC_VALUE = 1;

    /**
     * Enumeration singleton for value ADHOC.
     */
    public static final org.ccsds.moims.mo.mc.aggregation.structures.GenerationMode ADHOC = new org.ccsds.moims.mo.mc.aggregation.structures.GenerationMode(org.ccsds.moims.mo.mc.aggregation.structures.GenerationMode.ADHOC_VALUE);

    /**
     * Enumeration value for PERIODIC.
     */
    public static final int PERIODIC_VALUE = 2;

    /**
     * Enumeration singleton for value PERIODIC.
     */
    public static final org.ccsds.moims.mo.mc.aggregation.structures.GenerationMode PERIODIC = new org.ccsds.moims.mo.mc.aggregation.structures.GenerationMode(org.ccsds.moims.mo.mc.aggregation.structures.GenerationMode.PERIODIC_VALUE);

    /**
     * Enumeration value for FILTERED_TIMEOUT.
     */
    public static final int FILTERED_TIMEOUT_VALUE = 3;

    /**
     * Enumeration singleton for value FILTERED_TIMEOUT.
     */
    public static final org.ccsds.moims.mo.mc.aggregation.structures.GenerationMode FILTERED_TIMEOUT = new org.ccsds.moims.mo.mc.aggregation.structures.GenerationMode(org.ccsds.moims.mo.mc.aggregation.structures.GenerationMode.FILTERED_TIMEOUT_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final org.ccsds.moims.mo.mc.aggregation.structures.GenerationMode[] _ENUMERATIONS = {
        ADHOC, PERIODIC, FILTERED_TIMEOUT};

    /**
     * GenerationMode is an enumeration definition holding the reasons for the aggregation to be generated.
     */
    public GenerationMode() {
        super(-1);
    }

    /**
     * GenerationMode is an enumeration definition holding the reasons for the
     * aggregation to be generated.
     * 
     * @param value The value of the Enumeration.
     */
    public GenerationMode(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case ADHOC_VALUE:
                return "ADHOC";
            case PERIODIC_VALUE:
                return "PERIODIC";
            case FILTERED_TIMEOUT_VALUE:
                return "FILTERED_TIMEOUT";
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
    public static org.ccsds.moims.mo.mc.aggregation.structures.GenerationMode fromString(String s) {
        switch (s) {
            case "ADHOC":
                return GenerationMode.ADHOC;
            case "PERIODIC":
                return GenerationMode.PERIODIC;
            case "FILTERED_TIMEOUT":
                return GenerationMode.FILTERED_TIMEOUT;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Enumeration fromValue(Integer value) {
        switch (value) {
            case ADHOC_VALUE:
                return GenerationMode.ADHOC;
            case PERIODIC_VALUE:
                return GenerationMode.PERIODIC;
            case FILTERED_TIMEOUT_VALUE:
                return GenerationMode.FILTERED_TIMEOUT;
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
        return 3;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
