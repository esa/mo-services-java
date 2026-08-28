package org.ccsds.moims.mo.mps.structures;

/**
 * Enumeration class for InterpolationTypeEnum.
 */
public final class InterpolationTypeEnum extends org.ccsds.moims.mo.mal.structures.Enumeration {

    private static final long serialVersionUID = 1407374900330801L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330801L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Enumeration value for STEP.
     */
    public static final int STEP_VALUE = 1;

    /**
     * Enumeration singleton for value STEP.
     */
    public static final org.ccsds.moims.mo.mps.structures.InterpolationTypeEnum STEP = new org.ccsds.moims.mo.mps.structures.InterpolationTypeEnum(org.ccsds.moims.mo.mps.structures.InterpolationTypeEnum.STEP_VALUE);

    /**
     * Enumeration value for LINEAR.
     */
    public static final int LINEAR_VALUE = 2;

    /**
     * Enumeration singleton for value LINEAR.
     */
    public static final org.ccsds.moims.mo.mps.structures.InterpolationTypeEnum LINEAR = new org.ccsds.moims.mo.mps.structures.InterpolationTypeEnum(org.ccsds.moims.mo.mps.structures.InterpolationTypeEnum.LINEAR_VALUE);

    /**
     * Enumeration value for POLYNOMIAL.
     */
    public static final int POLYNOMIAL_VALUE = 3;

    /**
     * Enumeration singleton for value POLYNOMIAL.
     */
    public static final org.ccsds.moims.mo.mps.structures.InterpolationTypeEnum POLYNOMIAL = new org.ccsds.moims.mo.mps.structures.InterpolationTypeEnum(org.ccsds.moims.mo.mps.structures.InterpolationTypeEnum.POLYNOMIAL_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final org.ccsds.moims.mo.mps.structures.InterpolationTypeEnum[] _ENUMERATIONS = {
        STEP, LINEAR, POLYNOMIAL};

    /**
     * E4: The InterpolationTypeEnum describes the set of supported interpolation types for a given operation.
     */
    public InterpolationTypeEnum() {
        super(-1);
    }

    /**
     * E4: The InterpolationTypeEnum describes the set of supported interpolation
     * types for a given operation.
     * 
     * @param value The value of the Enumeration.
     */
    public InterpolationTypeEnum(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case STEP_VALUE:
                return "STEP";
            case LINEAR_VALUE:
                return "LINEAR";
            case POLYNOMIAL_VALUE:
                return "POLYNOMIAL";
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
    public static org.ccsds.moims.mo.mps.structures.InterpolationTypeEnum fromString(String s) {
        switch (s) {
            case "STEP":
                return InterpolationTypeEnum.STEP;
            case "LINEAR":
                return InterpolationTypeEnum.LINEAR;
            case "POLYNOMIAL":
                return InterpolationTypeEnum.POLYNOMIAL;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Enumeration fromValue(Integer value) {
        switch (value) {
            case STEP_VALUE:
                return InterpolationTypeEnum.STEP;
            case LINEAR_VALUE:
                return InterpolationTypeEnum.LINEAR;
            case POLYNOMIAL_VALUE:
                return InterpolationTypeEnum.POLYNOMIAL;
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
