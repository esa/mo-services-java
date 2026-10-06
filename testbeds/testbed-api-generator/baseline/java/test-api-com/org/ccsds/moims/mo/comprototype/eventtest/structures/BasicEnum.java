package org.ccsds.moims.mo.comprototype.eventtest.structures;

import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Enumeration;

/**
 * Enumeration class for BasicEnum.
 */
public final class BasicEnum extends Enumeration {

    private static final long serialVersionUID = 56295003948843021L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 56295003948843021L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Enumeration value for FIRST.
     */
    public static final int FIRST_VALUE = 1;

    /**
     * Enumeration singleton for value FIRST.
     */
    public static final BasicEnum FIRST = new BasicEnum(BasicEnum.FIRST_VALUE);

    /**
     * Enumeration value for SECOND.
     */
    public static final int SECOND_VALUE = 2;

    /**
     * Enumeration singleton for value SECOND.
     */
    public static final BasicEnum SECOND = new BasicEnum(BasicEnum.SECOND_VALUE);

    /**
     * Enumeration value for THIRD.
     */
    public static final int THIRD_VALUE = 3;

    /**
     * Enumeration singleton for value THIRD.
     */
    public static final BasicEnum THIRD = new BasicEnum(BasicEnum.THIRD_VALUE);

    /**
     * Enumeration value for FOURTH.
     */
    public static final int FOURTH_VALUE = 4;

    /**
     * Enumeration singleton for value FOURTH.
     */
    public static final BasicEnum FOURTH = new BasicEnum(BasicEnum.FOURTH_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final BasicEnum[] _ENUMERATIONS = {
        FIRST, SECOND, THIRD, FOURTH};

    /**
     * null
     */
    public BasicEnum() {
        super(-1);
    }

    /**
     * Creates an instance of the BasicEnum Enumeration.
     * 
     * @param value The value of the Enumeration.
     */
    public BasicEnum(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case FIRST_VALUE:
                return "FIRST";
            case SECOND_VALUE:
                return "SECOND";
            case THIRD_VALUE:
                return "THIRD";
            case FOURTH_VALUE:
                return "FOURTH";
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
    public static BasicEnum fromString(String s) {
        switch (s) {
            case "FIRST":
                return BasicEnum.FIRST;
            case "SECOND":
                return BasicEnum.SECOND;
            case "THIRD":
                return BasicEnum.THIRD;
            case "FOURTH":
                return BasicEnum.FOURTH;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public Enumeration fromValue(Integer value) {
        switch (value) {
            case FIRST_VALUE:
                return BasicEnum.FIRST;
            case SECOND_VALUE:
                return BasicEnum.SECOND;
            case THIRD_VALUE:
                return BasicEnum.THIRD;
            case FOURTH_VALUE:
                return BasicEnum.FOURTH;
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
        return 4;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
