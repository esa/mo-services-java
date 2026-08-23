package org.ccsds.moims.mo.mc.structures;

/**
 * Enumeration class for Severity.
 */
public final class Severity extends org.ccsds.moims.mo.mal.structures.Enumeration {

    private static final long serialVersionUID = 1125899940397062L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397062L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Enumeration value for INFORMATIONAL.
     */
    public static final int INFORMATIONAL_VALUE = 1;

    /**
     * Enumeration singleton for value INFORMATIONAL.
     */
    public static final org.ccsds.moims.mo.mc.structures.Severity INFORMATIONAL = new org.ccsds.moims.mo.mc.structures.Severity(org.ccsds.moims.mo.mc.structures.Severity.INFORMATIONAL_VALUE);

    /**
     * Enumeration value for WARNING.
     */
    public static final int WARNING_VALUE = 2;

    /**
     * Enumeration singleton for value WARNING.
     */
    public static final org.ccsds.moims.mo.mc.structures.Severity WARNING = new org.ccsds.moims.mo.mc.structures.Severity(org.ccsds.moims.mo.mc.structures.Severity.WARNING_VALUE);

    /**
     * Enumeration value for ALARM.
     */
    public static final int ALARM_VALUE = 3;

    /**
     * Enumeration singleton for value ALARM.
     */
    public static final org.ccsds.moims.mo.mc.structures.Severity ALARM = new org.ccsds.moims.mo.mc.structures.Severity(org.ccsds.moims.mo.mc.structures.Severity.ALARM_VALUE);

    /**
     * Enumeration value for SEVERE.
     */
    public static final int SEVERE_VALUE = 4;

    /**
     * Enumeration singleton for value SEVERE.
     */
    public static final org.ccsds.moims.mo.mc.structures.Severity SEVERE = new org.ccsds.moims.mo.mc.structures.Severity(org.ccsds.moims.mo.mc.structures.Severity.SEVERE_VALUE);

    /**
     * Enumeration value for CRITICAL.
     */
    public static final int CRITICAL_VALUE = 5;

    /**
     * Enumeration singleton for value CRITICAL.
     */
    public static final org.ccsds.moims.mo.mc.structures.Severity CRITICAL = new org.ccsds.moims.mo.mc.structures.Severity(org.ccsds.moims.mo.mc.structures.Severity.CRITICAL_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final org.ccsds.moims.mo.mc.structures.Severity[] _ENUMERATIONS = {
        INFORMATIONAL, WARNING, ALARM, SEVERE, CRITICAL};

    /**
     * The severity enumeration shall be used to hold the possible values for a severity.
     */
    public Severity() {
        super(-1);
    }

    /**
     * The severity enumeration shall be used to hold the possible values for
     * a severity.
     * 
     * @param value The value of the Enumeration.
     */
    public Severity(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case INFORMATIONAL_VALUE:
                return "INFORMATIONAL";
            case WARNING_VALUE:
                return "WARNING";
            case ALARM_VALUE:
                return "ALARM";
            case SEVERE_VALUE:
                return "SEVERE";
            case CRITICAL_VALUE:
                return "CRITICAL";
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
    public static org.ccsds.moims.mo.mc.structures.Severity fromString(String s) {
        switch (s) {
            case "INFORMATIONAL":
                return Severity.INFORMATIONAL;
            case "WARNING":
                return Severity.WARNING;
            case "ALARM":
                return Severity.ALARM;
            case "SEVERE":
                return Severity.SEVERE;
            case "CRITICAL":
                return Severity.CRITICAL;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Enumeration fromValue(Integer value) {
        switch (value) {
            case INFORMATIONAL_VALUE:
                return Severity.INFORMATIONAL;
            case WARNING_VALUE:
                return Severity.WARNING;
            case ALARM_VALUE:
                return Severity.ALARM;
            case SEVERE_VALUE:
                return Severity.SEVERE;
            case CRITICAL_VALUE:
                return Severity.CRITICAL;
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
