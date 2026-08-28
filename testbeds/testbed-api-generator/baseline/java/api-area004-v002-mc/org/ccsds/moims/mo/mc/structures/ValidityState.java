package org.ccsds.moims.mo.mc.structures;

/**
 * Enumeration class for ValidityState.
 */
public final class ValidityState extends org.ccsds.moims.mo.mal.structures.Enumeration {

    private static final long serialVersionUID = 1125899940397076L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397076L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Enumeration value for VALID.
     */
    public static final int VALID_VALUE = 0;

    /**
     * Enumeration singleton for value VALID.
     */
    public static final org.ccsds.moims.mo.mc.structures.ValidityState VALID = new org.ccsds.moims.mo.mc.structures.ValidityState(org.ccsds.moims.mo.mc.structures.ValidityState.VALID_VALUE);

    /**
     * Enumeration value for EXPIRED.
     */
    public static final int EXPIRED_VALUE = 1;

    /**
     * Enumeration singleton for value EXPIRED.
     */
    public static final org.ccsds.moims.mo.mc.structures.ValidityState EXPIRED = new org.ccsds.moims.mo.mc.structures.ValidityState(org.ccsds.moims.mo.mc.structures.ValidityState.EXPIRED_VALUE);

    /**
     * Enumeration value for INVALID_RAW.
     */
    public static final int INVALID_RAW_VALUE = 2;

    /**
     * Enumeration singleton for value INVALID_RAW.
     */
    public static final org.ccsds.moims.mo.mc.structures.ValidityState INVALID_RAW = new org.ccsds.moims.mo.mc.structures.ValidityState(org.ccsds.moims.mo.mc.structures.ValidityState.INVALID_RAW_VALUE);

    /**
     * Enumeration value for INVALID_CONVERSION.
     */
    public static final int INVALID_CONVERSION_VALUE = 3;

    /**
     * Enumeration singleton for value INVALID_CONVERSION.
     */
    public static final org.ccsds.moims.mo.mc.structures.ValidityState INVALID_CONVERSION = new org.ccsds.moims.mo.mc.structures.ValidityState(org.ccsds.moims.mo.mc.structures.ValidityState.INVALID_CONVERSION_VALUE);

    /**
     * Enumeration value for UNVERIFIED.
     */
    public static final int UNVERIFIED_VALUE = 4;

    /**
     * Enumeration singleton for value UNVERIFIED.
     */
    public static final org.ccsds.moims.mo.mc.structures.ValidityState UNVERIFIED = new org.ccsds.moims.mo.mc.structures.ValidityState(org.ccsds.moims.mo.mc.structures.ValidityState.UNVERIFIED_VALUE);

    /**
     * Enumeration value for INVALID.
     */
    public static final int INVALID_VALUE = 5;

    /**
     * Enumeration singleton for value INVALID.
     */
    public static final org.ccsds.moims.mo.mc.structures.ValidityState INVALID = new org.ccsds.moims.mo.mc.structures.ValidityState(org.ccsds.moims.mo.mc.structures.ValidityState.INVALID_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final org.ccsds.moims.mo.mc.structures.ValidityState[] _ENUMERATIONS = {
        VALID, EXPIRED, INVALID_RAW, INVALID_CONVERSION, UNVERIFIED, INVALID};

    /**
     * The ValidityState enumeration shall be used to hold the validity states and their numeric values.
     */
    public ValidityState() {
        super(-1);
    }

    /**
     * The ValidityState enumeration shall be used to hold the validity states
     * and their numeric values.
     * 
     * @param value The value of the Enumeration.
     */
    public ValidityState(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case VALID_VALUE:
                return "VALID";
            case EXPIRED_VALUE:
                return "EXPIRED";
            case INVALID_RAW_VALUE:
                return "INVALID_RAW";
            case INVALID_CONVERSION_VALUE:
                return "INVALID_CONVERSION";
            case UNVERIFIED_VALUE:
                return "UNVERIFIED";
            case INVALID_VALUE:
                return "INVALID";
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
    public static org.ccsds.moims.mo.mc.structures.ValidityState fromString(String s) {
        switch (s) {
            case "VALID":
                return ValidityState.VALID;
            case "EXPIRED":
                return ValidityState.EXPIRED;
            case "INVALID_RAW":
                return ValidityState.INVALID_RAW;
            case "INVALID_CONVERSION":
                return ValidityState.INVALID_CONVERSION;
            case "UNVERIFIED":
                return ValidityState.UNVERIFIED;
            case "INVALID":
                return ValidityState.INVALID;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Enumeration fromValue(Integer value) {
        switch (value) {
            case VALID_VALUE:
                return ValidityState.VALID;
            case EXPIRED_VALUE:
                return ValidityState.EXPIRED;
            case INVALID_RAW_VALUE:
                return ValidityState.INVALID_RAW;
            case INVALID_CONVERSION_VALUE:
                return ValidityState.INVALID_CONVERSION;
            case UNVERIFIED_VALUE:
                return ValidityState.UNVERIFIED;
            case INVALID_VALUE:
                return ValidityState.INVALID;
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
        return 6;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
