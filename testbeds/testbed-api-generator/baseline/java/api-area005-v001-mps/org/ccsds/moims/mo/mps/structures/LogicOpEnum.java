package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Enumeration;

/**
 * Enumeration class for LogicOpEnum.
 */
public final class LogicOpEnum extends Enumeration {

    private static final long serialVersionUID = 1407374900330526L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330526L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Enumeration value for AND.
     */
    public static final int AND_VALUE = 1;

    /**
     * Enumeration singleton for value AND.
     */
    public static final LogicOpEnum AND = new LogicOpEnum(LogicOpEnum.AND_VALUE);

    /**
     * Enumeration value for OR.
     */
    public static final int OR_VALUE = 2;

    /**
     * Enumeration singleton for value OR.
     */
    public static final LogicOpEnum OR = new LogicOpEnum(LogicOpEnum.OR_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final LogicOpEnum[] _ENUMERATIONS = {
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
    public static LogicOpEnum fromString(String s) {
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
    public Enumeration fromValue(Integer value) {
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
