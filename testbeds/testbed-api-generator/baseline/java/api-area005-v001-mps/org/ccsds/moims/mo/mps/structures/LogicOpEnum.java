package org.ccsds.moims.mo.mps.structures;

/**
 * Enumeration class for LogicOpEnum.
 */
public final class LogicOpEnum extends org.ccsds.moims.mo.mal.structures.Enumeration {

    private static final long serialVersionUID = 1407374900330526L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330526L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Enumeration value for AND.
     */
    public static final int AND_VALUE = 1;

    /**
     * Enumeration singleton for value AND.
     */
    public static final org.ccsds.moims.mo.mps.structures.LogicOpEnum AND = new org.ccsds.moims.mo.mps.structures.LogicOpEnum(org.ccsds.moims.mo.mps.structures.LogicOpEnum.AND_VALUE);

    /**
     * Enumeration value for OR.
     */
    public static final int OR_VALUE = 2;

    /**
     * Enumeration singleton for value OR.
     */
    public static final org.ccsds.moims.mo.mps.structures.LogicOpEnum OR = new org.ccsds.moims.mo.mps.structures.LogicOpEnum(org.ccsds.moims.mo.mps.structures.LogicOpEnum.OR_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final org.ccsds.moims.mo.mps.structures.LogicOpEnum[] _ENUMERATIONS = {
        AND, OR};

    /**
     * E1: A LogicOpEnum represents the type of logic used to combine two Boolean conditions.
     */
    public LogicOpEnum() {
        super(-1);
    }

    /**
     * E1: A LogicOpEnum represents the type of logic used to combine two Boolean
     * conditions.
     * 
     * @param value The value of the Enumeration.
     */
    public LogicOpEnum(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case AND_VALUE:
                return "AND";
            case OR_VALUE:
                return "OR";
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
    public static org.ccsds.moims.mo.mps.structures.LogicOpEnum fromString(String s) {
        switch (s) {
            case "AND":
                return LogicOpEnum.AND;
            case "OR":
                return LogicOpEnum.OR;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Enumeration fromValue(Integer value) {
        switch (value) {
            case AND_VALUE:
                return LogicOpEnum.AND;
            case OR_VALUE:
                return LogicOpEnum.OR;
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
