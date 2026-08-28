package org.ccsds.moims.mo.mc.check.structures;

/**
 * Enumeration class for CheckState.
 */
public final class CheckState extends org.ccsds.moims.mo.mal.structures.Enumeration {

    private static final long serialVersionUID = 1125917103489030L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125917103489030L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Enumeration value for DISABLED.
     */
    public static final int DISABLED_VALUE = 1;

    /**
     * Enumeration singleton for value DISABLED.
     */
    public static final org.ccsds.moims.mo.mc.check.structures.CheckState DISABLED = new org.ccsds.moims.mo.mc.check.structures.CheckState(org.ccsds.moims.mo.mc.check.structures.CheckState.DISABLED_VALUE);

    /**
     * Enumeration value for UNCHECKED.
     */
    public static final int UNCHECKED_VALUE = 2;

    /**
     * Enumeration singleton for value UNCHECKED.
     */
    public static final org.ccsds.moims.mo.mc.check.structures.CheckState UNCHECKED = new org.ccsds.moims.mo.mc.check.structures.CheckState(org.ccsds.moims.mo.mc.check.structures.CheckState.UNCHECKED_VALUE);

    /**
     * Enumeration value for INVALID.
     */
    public static final int INVALID_VALUE = 3;

    /**
     * Enumeration singleton for value INVALID.
     */
    public static final org.ccsds.moims.mo.mc.check.structures.CheckState INVALID = new org.ccsds.moims.mo.mc.check.structures.CheckState(org.ccsds.moims.mo.mc.check.structures.CheckState.INVALID_VALUE);

    /**
     * Enumeration value for OK.
     */
    public static final int OK_VALUE = 4;

    /**
     * Enumeration singleton for value OK.
     */
    public static final org.ccsds.moims.mo.mc.check.structures.CheckState OK = new org.ccsds.moims.mo.mc.check.structures.CheckState(org.ccsds.moims.mo.mc.check.structures.CheckState.OK_VALUE);

    /**
     * Enumeration value for NOT_OK.
     */
    public static final int NOT_OK_VALUE = 5;

    /**
     * Enumeration singleton for value NOT_OK.
     */
    public static final org.ccsds.moims.mo.mc.check.structures.CheckState NOT_OK = new org.ccsds.moims.mo.mc.check.structures.CheckState(org.ccsds.moims.mo.mc.check.structures.CheckState.NOT_OK_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final org.ccsds.moims.mo.mc.check.structures.CheckState[] _ENUMERATIONS = {
        DISABLED, UNCHECKED, INVALID, OK, NOT_OK};

    /**
     * The CheckState enumeration holds the possible basic states of a check. The meaning of the NOT_OK value is check specific and detailed in the relevant check type definition.
     */
    public CheckState() {
        super(-1);
    }

    /**
     * The CheckState enumeration holds the possible basic states of a check.
     * The meaning of the NOT_OK value is check specific and detailed in the relevant
     * check type definition.
     * 
     * @param value The value of the Enumeration.
     */
    public CheckState(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case DISABLED_VALUE:
                return "DISABLED";
            case UNCHECKED_VALUE:
                return "UNCHECKED";
            case INVALID_VALUE:
                return "INVALID";
            case OK_VALUE:
                return "OK";
            case NOT_OK_VALUE:
                return "NOT_OK";
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
    public static org.ccsds.moims.mo.mc.check.structures.CheckState fromString(String s) {
        switch (s) {
            case "DISABLED":
                return CheckState.DISABLED;
            case "UNCHECKED":
                return CheckState.UNCHECKED;
            case "INVALID":
                return CheckState.INVALID;
            case "OK":
                return CheckState.OK;
            case "NOT_OK":
                return CheckState.NOT_OK;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Enumeration fromValue(Integer value) {
        switch (value) {
            case DISABLED_VALUE:
                return CheckState.DISABLED;
            case UNCHECKED_VALUE:
                return CheckState.UNCHECKED;
            case INVALID_VALUE:
                return CheckState.INVALID;
            case OK_VALUE:
                return CheckState.OK;
            case NOT_OK_VALUE:
                return CheckState.NOT_OK;
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
        return 5;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
