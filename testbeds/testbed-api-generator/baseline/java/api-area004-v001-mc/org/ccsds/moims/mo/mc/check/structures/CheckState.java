package org.ccsds.moims.mo.mc.check.structures;

import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Enumeration;

/**
 * Enumeration class for CheckState.
 */
public final class CheckState extends Enumeration {

    private static final long serialVersionUID = 1125917103489030L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125917103489030L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Enumeration value for DISABLED.
     */
    public static final int DISABLED_VALUE = 1;

    /**
     * Enumeration singleton for value DISABLED.
     */
    public static final CheckState DISABLED = new CheckState(CheckState.DISABLED_VALUE);

    /**
     * Enumeration value for UNCHECKED.
     */
    public static final int UNCHECKED_VALUE = 2;

    /**
     * Enumeration singleton for value UNCHECKED.
     */
    public static final CheckState UNCHECKED = new CheckState(CheckState.UNCHECKED_VALUE);

    /**
     * Enumeration value for INVALID.
     */
    public static final int INVALID_VALUE = 3;

    /**
     * Enumeration singleton for value INVALID.
     */
    public static final CheckState INVALID = new CheckState(CheckState.INVALID_VALUE);

    /**
     * Enumeration value for OK.
     */
    public static final int OK_VALUE = 4;

    /**
     * Enumeration singleton for value OK.
     */
    public static final CheckState OK = new CheckState(CheckState.OK_VALUE);

    /**
     * Enumeration value for NOT_OK.
     */
    public static final int NOT_OK_VALUE = 5;

    /**
     * Enumeration singleton for value NOT_OK.
     */
    public static final CheckState NOT_OK = new CheckState(CheckState.NOT_OK_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final CheckState[] _ENUMERATIONS = {
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
    public static CheckState fromString(String s) {
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
    public Enumeration fromValue(Integer value) {
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
    public Element createElement() {
        return _ENUMERATIONS[0];
    }

    @Override
    public int getEnumSize() {
        return 5;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
