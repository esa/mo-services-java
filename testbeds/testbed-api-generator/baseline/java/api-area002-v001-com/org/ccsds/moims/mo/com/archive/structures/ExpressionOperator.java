package org.ccsds.moims.mo.com.archive.structures;

import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Enumeration;

/**
 * Enumeration class for ExpressionOperator.
 */
public final class ExpressionOperator extends Enumeration {

    private static final long serialVersionUID = 562958560133125L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 562958560133125L;
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
    public static final ExpressionOperator EQUAL = new ExpressionOperator(ExpressionOperator.EQUAL_VALUE);

    /**
     * Enumeration value for DIFFER.
     */
    public static final int DIFFER_VALUE = 2;

    /**
     * Enumeration singleton for value DIFFER.
     */
    public static final ExpressionOperator DIFFER = new ExpressionOperator(ExpressionOperator.DIFFER_VALUE);

    /**
     * Enumeration value for GREATER.
     */
    public static final int GREATER_VALUE = 3;

    /**
     * Enumeration singleton for value GREATER.
     */
    public static final ExpressionOperator GREATER = new ExpressionOperator(ExpressionOperator.GREATER_VALUE);

    /**
     * Enumeration value for GREATER_OR_EQUAL.
     */
    public static final int GREATER_OR_EQUAL_VALUE = 4;

    /**
     * Enumeration singleton for value GREATER_OR_EQUAL.
     */
    public static final ExpressionOperator GREATER_OR_EQUAL = new ExpressionOperator(ExpressionOperator.GREATER_OR_EQUAL_VALUE);

    /**
     * Enumeration value for LESS.
     */
    public static final int LESS_VALUE = 5;

    /**
     * Enumeration singleton for value LESS.
     */
    public static final ExpressionOperator LESS = new ExpressionOperator(ExpressionOperator.LESS_VALUE);

    /**
     * Enumeration value for LESS_OR_EQUAL.
     */
    public static final int LESS_OR_EQUAL_VALUE = 6;

    /**
     * Enumeration singleton for value LESS_OR_EQUAL.
     */
    public static final ExpressionOperator LESS_OR_EQUAL = new ExpressionOperator(ExpressionOperator.LESS_OR_EQUAL_VALUE);

    /**
     * Enumeration value for CONTAINS.
     */
    public static final int CONTAINS_VALUE = 7;

    /**
     * Enumeration singleton for value CONTAINS.
     */
    public static final ExpressionOperator CONTAINS = new ExpressionOperator(ExpressionOperator.CONTAINS_VALUE);

    /**
     * Enumeration value for ICONTAINS.
     */
    public static final int ICONTAINS_VALUE = 8;

    /**
     * Enumeration singleton for value ICONTAINS.
     */
    public static final ExpressionOperator ICONTAINS = new ExpressionOperator(ExpressionOperator.ICONTAINS_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final ExpressionOperator[] _ENUMERATIONS = {
        EQUAL, DIFFER, GREATER, GREATER_OR_EQUAL, LESS, LESS_OR_EQUAL, CONTAINS,
        ICONTAINS};

    /**
     * The ExpressionOperator enumeration holds a set of possible expression operators.
     */
    public ExpressionOperator() {
        super(-1);
    }

    /**
     * The ExpressionOperator enumeration holds a set of possible expression operators.
     * 
     * @param value The value of the Enumeration.
     */
    public ExpressionOperator(int value) {
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
    public static ExpressionOperator fromString(String s) {
        switch (s) {
            case "EQUAL":
                return ExpressionOperator.EQUAL;
            case "DIFFER":
                return ExpressionOperator.DIFFER;
            case "GREATER":
                return ExpressionOperator.GREATER;
            case "GREATER_OR_EQUAL":
                return ExpressionOperator.GREATER_OR_EQUAL;
            case "LESS":
                return ExpressionOperator.LESS;
            case "LESS_OR_EQUAL":
                return ExpressionOperator.LESS_OR_EQUAL;
            case "CONTAINS":
                return ExpressionOperator.CONTAINS;
            case "ICONTAINS":
                return ExpressionOperator.ICONTAINS;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public Enumeration fromValue(Integer value) {
        switch (value) {
            case EQUAL_VALUE:
                return ExpressionOperator.EQUAL;
            case DIFFER_VALUE:
                return ExpressionOperator.DIFFER;
            case GREATER_VALUE:
                return ExpressionOperator.GREATER;
            case GREATER_OR_EQUAL_VALUE:
                return ExpressionOperator.GREATER_OR_EQUAL;
            case LESS_VALUE:
                return ExpressionOperator.LESS;
            case LESS_OR_EQUAL_VALUE:
                return ExpressionOperator.LESS_OR_EQUAL;
            case CONTAINS_VALUE:
                return ExpressionOperator.CONTAINS;
            case ICONTAINS_VALUE:
                return ExpressionOperator.ICONTAINS;
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
