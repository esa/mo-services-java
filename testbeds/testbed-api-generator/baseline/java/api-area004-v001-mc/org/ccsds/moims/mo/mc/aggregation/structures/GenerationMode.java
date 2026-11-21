package org.ccsds.moims.mo.mc.aggregation.structures;

import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Enumeration;

/**
 * Enumeration class for GenerationMode.
 */
public final class GenerationMode extends Enumeration {

    private static final long serialVersionUID = 1125925693423625L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125925693423625L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Enumeration value for ADHOC.
     */
    public static final int ADHOC_VALUE = 1;

    /**
     * Enumeration singleton for value ADHOC.
     */
    public static final GenerationMode ADHOC = new GenerationMode(GenerationMode.ADHOC_VALUE);

    /**
     * Enumeration value for PERIODIC.
     */
    public static final int PERIODIC_VALUE = 2;

    /**
     * Enumeration singleton for value PERIODIC.
     */
    public static final GenerationMode PERIODIC = new GenerationMode(GenerationMode.PERIODIC_VALUE);

    /**
     * Enumeration value for FILTERED_TIMEOUT.
     */
    public static final int FILTERED_TIMEOUT_VALUE = 3;

    /**
     * Enumeration singleton for value FILTERED_TIMEOUT.
     */
    public static final GenerationMode FILTERED_TIMEOUT = new GenerationMode(GenerationMode.FILTERED_TIMEOUT_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final GenerationMode[] _ENUMERATIONS = {
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
    public static GenerationMode fromString(String s) {
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
    public Enumeration fromValue(Integer value) {
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
    public Element createElement() {
        return _ENUMERATIONS[0];
    }

    @Override
    public int getEnumSize() {
        return 3;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
