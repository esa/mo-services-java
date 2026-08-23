package org.ccsds.moims.mo.mps.structures;

/**
 * Enumeration class for SecondaryErrorCodeEnum.
 */
public final class SecondaryErrorCodeEnum extends org.ccsds.moims.mo.mal.structures.Enumeration {

    private static final long serialVersionUID = 1407374900330560L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330560L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Enumeration value for UNKNOWN.
     */
    public static final int UNKNOWN_VALUE = 1;

    /**
     * Enumeration singleton for value UNKNOWN.
     */
    public static final org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum UNKNOWN = new org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum(org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum.UNKNOWN_VALUE);

    /**
     * Enumeration value for UNDEFINED.
     */
    public static final int UNDEFINED_VALUE = 2;

    /**
     * Enumeration singleton for value UNDEFINED.
     */
    public static final org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum UNDEFINED = new org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum(org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum.UNDEFINED_VALUE);

    /**
     * Enumeration value for OUT_OF_RANGE.
     */
    public static final int OUT_OF_RANGE_VALUE = 3;

    /**
     * Enumeration singleton for value OUT_OF_RANGE.
     */
    public static final org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum OUT_OF_RANGE = new org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum(org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum.OUT_OF_RANGE_VALUE);

    /**
     * Enumeration value for UNRECOGNIZED.
     */
    public static final int UNRECOGNIZED_VALUE = 4;

    /**
     * Enumeration singleton for value UNRECOGNIZED.
     */
    public static final org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum UNRECOGNIZED = new org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum(org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum.UNRECOGNIZED_VALUE);

    /**
     * Enumeration value for BAD_TIME.
     */
    public static final int BAD_TIME_VALUE = 5;

    /**
     * Enumeration singleton for value BAD_TIME.
     */
    public static final org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum BAD_TIME = new org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum(org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum.BAD_TIME_VALUE);

    /**
     * Enumeration value for BAD_POSITION.
     */
    public static final int BAD_POSITION_VALUE = 6;

    /**
     * Enumeration singleton for value BAD_POSITION.
     */
    public static final org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum BAD_POSITION = new org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum(org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum.BAD_POSITION_VALUE);

    /**
     * Enumeration value for BAD_DIRECTION.
     */
    public static final int BAD_DIRECTION_VALUE = 7;

    /**
     * Enumeration singleton for value BAD_DIRECTION.
     */
    public static final org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum BAD_DIRECTION = new org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum(org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum.BAD_DIRECTION_VALUE);

    /**
     * Enumeration value for INCONSISTENT.
     */
    public static final int INCONSISTENT_VALUE = 8;

    /**
     * Enumeration singleton for value INCONSISTENT.
     */
    public static final org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum INCONSISTENT = new org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum(org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum.INCONSISTENT_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum[] _ENUMERATIONS = {
        UNKNOWN, UNDEFINED, OUT_OF_RANGE, UNRECOGNIZED, BAD_TIME, BAD_POSITION,
        BAD_DIRECTION, INCONSISTENT};

    /**
     * E1: For the INVALID error, the secondary error code is a MAL::UShort that allows for deployment specific extensibility.
     */
    public SecondaryErrorCodeEnum() {
        super(-1);
    }

    /**
     * E1: For the INVALID error, the secondary error code is a MAL::UShort that
     * allows for deployment specific extensibility.
     * 
     * @param value The value of the Enumeration.
     */
    public SecondaryErrorCodeEnum(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case UNKNOWN_VALUE:
                return "UNKNOWN";
            case UNDEFINED_VALUE:
                return "UNDEFINED";
            case OUT_OF_RANGE_VALUE:
                return "OUT_OF_RANGE";
            case UNRECOGNIZED_VALUE:
                return "UNRECOGNIZED";
            case BAD_TIME_VALUE:
                return "BAD_TIME";
            case BAD_POSITION_VALUE:
                return "BAD_POSITION";
            case BAD_DIRECTION_VALUE:
                return "BAD_DIRECTION";
            case INCONSISTENT_VALUE:
                return "INCONSISTENT";
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
    public static org.ccsds.moims.mo.mps.structures.SecondaryErrorCodeEnum fromString(String s) {
        switch (s) {
            case "UNKNOWN":
                return SecondaryErrorCodeEnum.UNKNOWN;
            case "UNDEFINED":
                return SecondaryErrorCodeEnum.UNDEFINED;
            case "OUT_OF_RANGE":
                return SecondaryErrorCodeEnum.OUT_OF_RANGE;
            case "UNRECOGNIZED":
                return SecondaryErrorCodeEnum.UNRECOGNIZED;
            case "BAD_TIME":
                return SecondaryErrorCodeEnum.BAD_TIME;
            case "BAD_POSITION":
                return SecondaryErrorCodeEnum.BAD_POSITION;
            case "BAD_DIRECTION":
                return SecondaryErrorCodeEnum.BAD_DIRECTION;
            case "INCONSISTENT":
                return SecondaryErrorCodeEnum.INCONSISTENT;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Enumeration fromValue(Integer value) {
        switch (value) {
            case UNKNOWN_VALUE:
                return SecondaryErrorCodeEnum.UNKNOWN;
            case UNDEFINED_VALUE:
                return SecondaryErrorCodeEnum.UNDEFINED;
            case OUT_OF_RANGE_VALUE:
                return SecondaryErrorCodeEnum.OUT_OF_RANGE;
            case UNRECOGNIZED_VALUE:
                return SecondaryErrorCodeEnum.UNRECOGNIZED;
            case BAD_TIME_VALUE:
                return SecondaryErrorCodeEnum.BAD_TIME;
            case BAD_POSITION_VALUE:
                return SecondaryErrorCodeEnum.BAD_POSITION;
            case BAD_DIRECTION_VALUE:
                return SecondaryErrorCodeEnum.BAD_DIRECTION;
            case INCONSISTENT_VALUE:
                return SecondaryErrorCodeEnum.INCONSISTENT;
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
        return 8;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
