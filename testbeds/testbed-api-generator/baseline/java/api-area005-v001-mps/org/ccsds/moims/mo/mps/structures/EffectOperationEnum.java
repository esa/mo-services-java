package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Enumeration;

/**
 * Enumeration class for EffectOperationEnum.
 */
public final class EffectOperationEnum extends Enumeration {

    private static final long serialVersionUID = 1407374900330545L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330545L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Enumeration value for SET.
     */
    public static final int SET_VALUE = 1;

    /**
     * Enumeration singleton for value SET.
     */
    public static final EffectOperationEnum SET = new EffectOperationEnum(EffectOperationEnum.SET_VALUE);

    /**
     * Enumeration value for INCREASE.
     */
    public static final int INCREASE_VALUE = 2;

    /**
     * Enumeration singleton for value INCREASE.
     */
    public static final EffectOperationEnum INCREASE = new EffectOperationEnum(EffectOperationEnum.INCREASE_VALUE);

    /**
     * Enumeration value for DECREASE.
     */
    public static final int DECREASE_VALUE = 3;

    /**
     * Enumeration singleton for value DECREASE.
     */
    public static final EffectOperationEnum DECREASE = new EffectOperationEnum(EffectOperationEnum.DECREASE_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final EffectOperationEnum[] _ENUMERATIONS = {
        SET, INCREASE, DECREASE};

    /**
     * E5: An EffectOperationEnum is used to denote the specific type of change made to a planning resource for a given Effect.
     */
    public EffectOperationEnum() {
        super(-1);
    }

    /**
     * E5: An EffectOperationEnum is used to denote the specific type of change
     * made to a planning resource for a given Effect.
     * 
     * @param value The value of the Enumeration.
     */
    public EffectOperationEnum(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case SET_VALUE:
                return "SET";
            case INCREASE_VALUE:
                return "INCREASE";
            case DECREASE_VALUE:
                return "DECREASE";
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
    public static EffectOperationEnum fromString(String s) {
        switch (s) {
            case "SET":
                return EffectOperationEnum.SET;
            case "INCREASE":
                return EffectOperationEnum.INCREASE;
            case "DECREASE":
                return EffectOperationEnum.DECREASE;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public Enumeration fromValue(Integer value) {
        switch (value) {
            case SET_VALUE:
                return EffectOperationEnum.SET;
            case INCREASE_VALUE:
                return EffectOperationEnum.INCREASE;
            case DECREASE_VALUE:
                return EffectOperationEnum.DECREASE;
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
        return 3;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
