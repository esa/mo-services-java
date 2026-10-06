package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Enumeration;

/**
 * Enumeration class for SeparationTypeEnum.
 */
public final class SeparationTypeEnum extends Enumeration {

    private static final long serialVersionUID = 1407374900330552L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330552L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Enumeration value for RELATIVE.
     */
    public static final int RELATIVE_VALUE = 1;

    /**
     * Enumeration singleton for value RELATIVE.
     */
    public static final SeparationTypeEnum RELATIVE = new SeparationTypeEnum(SeparationTypeEnum.RELATIVE_VALUE);

    /**
     * Enumeration value for ABSOLUTE.
     */
    public static final int ABSOLUTE_VALUE = 2;

    /**
     * Enumeration singleton for value ABSOLUTE.
     */
    public static final SeparationTypeEnum ABSOLUTE = new SeparationTypeEnum(SeparationTypeEnum.ABSOLUTE_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final SeparationTypeEnum[] _ENUMERATIONS = {
        RELATIVE, ABSOLUTE};

    /**
     * E1: A SeparationTypeEnum is used to define whether the separation between repetitions is relative or absolute.
     */
    public SeparationTypeEnum() {
        super(-1);
    }

    /**
     * E1: A SeparationTypeEnum is used to define whether the separation between
     * repetitions is relative or absolute.
     * 
     * @param value The value of the Enumeration.
     */
    public SeparationTypeEnum(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case RELATIVE_VALUE:
                return "RELATIVE";
            case ABSOLUTE_VALUE:
                return "ABSOLUTE";
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
    public static SeparationTypeEnum fromString(String s) {
        switch (s) {
            case "RELATIVE":
                return SeparationTypeEnum.RELATIVE;
            case "ABSOLUTE":
                return SeparationTypeEnum.ABSOLUTE;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public Enumeration fromValue(Integer value) {
        switch (value) {
            case RELATIVE_VALUE:
                return SeparationTypeEnum.RELATIVE;
            case ABSOLUTE_VALUE:
                return SeparationTypeEnum.ABSOLUTE;
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
