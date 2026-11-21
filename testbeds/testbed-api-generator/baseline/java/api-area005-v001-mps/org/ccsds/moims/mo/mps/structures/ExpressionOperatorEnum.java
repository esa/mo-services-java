package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Enumeration;

/**
 * Enumeration class for ExpressionOperatorEnum.
 */
public final class ExpressionOperatorEnum extends Enumeration {

    private static final long serialVersionUID = 1407374900330532L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330532L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Enumeration value for EQUAL.
     */
    public static final int EQUAL_VALUE = 1;

    /**
     * Enumeration singleton for value EQUAL.
     */
    public static final ExpressionOperatorEnum EQUAL = new ExpressionOperatorEnum(ExpressionOperatorEnum.EQUAL_VALUE);

    /**
     * Enumeration value for DIFFER.
     */
    public static final int DIFFER_VALUE = 2;

    /**
     * Enumeration singleton for value DIFFER.
     */
    public static final ExpressionOperatorEnum DIFFER = new ExpressionOperatorEnum(ExpressionOperatorEnum.DIFFER_VALUE);

    /**
     * Enumeration value for GREATER.
     */
    public static final int GREATER_VALUE = 3;

    /**
     * Enumeration singleton for value GREATER.
     */
    public static final ExpressionOperatorEnum GREATER = new ExpressionOperatorEnum(ExpressionOperatorEnum.GREATER_VALUE);

    /**
     * Enumeration value for GREATER_OR_EQUAL.
     */
    public static final int GREATER_OR_EQUAL_VALUE = 4;

    /**
     * Enumeration singleton for value GREATER_OR_EQUAL.
     */
    public static final ExpressionOperatorEnum GREATER_OR_EQUAL = new ExpressionOperatorEnum(ExpressionOperatorEnum.GREATER_OR_EQUAL_VALUE);

    /**
     * Enumeration value for LESS.
     */
    public static final int LESS_VALUE = 5;

    /**
     * Enumeration singleton for value LESS.
     */
    public static final ExpressionOperatorEnum LESS = new ExpressionOperatorEnum(ExpressionOperatorEnum.LESS_VALUE);

    /**
     * Enumeration value for LESS_OR_EQUAL.
     */
    public static final int LESS_OR_EQUAL_VALUE = 6;

    /**
     * Enumeration singleton for value LESS_OR_EQUAL.
     */
    public static final ExpressionOperatorEnum LESS_OR_EQUAL = new ExpressionOperatorEnum(ExpressionOperatorEnum.LESS_OR_EQUAL_VALUE);

    /**
     * Enumeration value for CONTAINS.
     */
    public static final int CONTAINS_VALUE = 7;

    /**
     * Enumeration singleton for value CONTAINS.
     */
    public static final ExpressionOperatorEnum CONTAINS = new ExpressionOperatorEnum(ExpressionOperatorEnum.CONTAINS_VALUE);

    /**
     * Enumeration value for ICONTAINS.
     */
    public static final int ICONTAINS_VALUE = 8;

    /**
     * Enumeration singleton for value ICONTAINS.
     */
    public static final ExpressionOperatorEnum ICONTAINS = new ExpressionOperatorEnum(ExpressionOperatorEnum.ICONTAINS_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final ExpressionOperatorEnum[] _ENUMERATIONS = {
        EQUAL, DIFFER, GREATER, GREATER_OR_EQUAL, LESS, LESS_OR_EQUAL, CONTAINS,
        ICONTAINS};

    /**
     * E5: Whenever a value comparison is needed in this standard, multiple Boolean operations may be chosen from.  These operations are described by each of the possible enumeration values of ExpressionOperatorEnum.
     */
    public ExpressionOperatorEnum() {
        super(-1);
    }

    /**
     * E5: Whenever a value comparison is needed in this standard, multiple Boolean
     * operations may be chosen from.  These operations are described by each
     * of the possible enumeration values of ExpressionOperatorEnum.
     * 
     * @param value The value of the Enumeration.
     */
    public ExpressionOperatorEnum(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case EQUAL_VALUE:
                return "EQUAL";
            case DIFFER_VALUE:
                return "DIFFER";
            case GREATER_VALUE:
                return "GREATER";
            case GREATER_OR_EQUAL_VALUE:
                return "GREATER_OR_EQUAL";
            case LESS_VALUE:
                return "LESS";
            case LESS_OR_EQUAL_VALUE:
                return "LESS_OR_EQUAL";
            case CONTAINS_VALUE:
                return "CONTAINS";
            case ICONTAINS_VALUE:
                return "ICONTAINS";
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
    public static ExpressionOperatorEnum fromString(String s) {
        switch (s) {
            case "EQUAL":
                return ExpressionOperatorEnum.EQUAL;
            case "DIFFER":
                return ExpressionOperatorEnum.DIFFER;
            case "GREATER":
                return ExpressionOperatorEnum.GREATER;
            case "GREATER_OR_EQUAL":
                return ExpressionOperatorEnum.GREATER_OR_EQUAL;
            case "LESS":
                return ExpressionOperatorEnum.LESS;
            case "LESS_OR_EQUAL":
                return ExpressionOperatorEnum.LESS_OR_EQUAL;
            case "CONTAINS":
                return ExpressionOperatorEnum.CONTAINS;
            case "ICONTAINS":
                return ExpressionOperatorEnum.ICONTAINS;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public Enumeration fromValue(Integer value) {
        switch (value) {
            case EQUAL_VALUE:
                return ExpressionOperatorEnum.EQUAL;
            case DIFFER_VALUE:
                return ExpressionOperatorEnum.DIFFER;
            case GREATER_VALUE:
                return ExpressionOperatorEnum.GREATER;
            case GREATER_OR_EQUAL_VALUE:
                return ExpressionOperatorEnum.GREATER_OR_EQUAL;
            case LESS_VALUE:
                return ExpressionOperatorEnum.LESS;
            case LESS_OR_EQUAL_VALUE:
                return ExpressionOperatorEnum.LESS_OR_EQUAL;
            case CONTAINS_VALUE:
                return ExpressionOperatorEnum.CONTAINS;
            case ICONTAINS_VALUE:
                return ExpressionOperatorEnum.ICONTAINS;
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
        return 8;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
