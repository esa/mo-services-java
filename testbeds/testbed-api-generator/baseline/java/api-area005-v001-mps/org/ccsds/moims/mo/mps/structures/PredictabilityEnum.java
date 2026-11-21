package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Enumeration;

/**
 * Enumeration class for PredictabilityEnum.
 */
public final class PredictabilityEnum extends Enumeration {

    private static final long serialVersionUID = 1407374900330700L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330700L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Enumeration value for PREDICTED.
     */
    public static final int PREDICTED_VALUE = 1;

    /**
     * Enumeration singleton for value PREDICTED.
     */
    public static final PredictabilityEnum PREDICTED = new PredictabilityEnum(PredictabilityEnum.PREDICTED_VALUE);

    /**
     * Enumeration value for POTENTIAL.
     */
    public static final int POTENTIAL_VALUE = 2;

    /**
     * Enumeration singleton for value POTENTIAL.
     */
    public static final PredictabilityEnum POTENTIAL = new PredictabilityEnum(PredictabilityEnum.POTENTIAL_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final PredictabilityEnum[] _ENUMERATIONS = {
        PREDICTED, POTENTIAL};

    /**
     * E1: The PredictabilityEnum enumeration is used to indicate whether a given Event is predictable or can occur at any time.
     */
    public PredictabilityEnum() {
        super(-1);
    }

    /**
     * E1: The PredictabilityEnum enumeration is used to indicate whether a given
     * Event is predictable or can occur at any time.
     * 
     * @param value The value of the Enumeration.
     */
    public PredictabilityEnum(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case PREDICTED_VALUE:
                return "PREDICTED";
            case POTENTIAL_VALUE:
                return "POTENTIAL";
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
    public static PredictabilityEnum fromString(String s) {
        switch (s) {
            case "PREDICTED":
                return PredictabilityEnum.PREDICTED;
            case "POTENTIAL":
                return PredictabilityEnum.POTENTIAL;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public Enumeration fromValue(Integer value) {
        switch (value) {
            case PREDICTED_VALUE:
                return PredictabilityEnum.PREDICTED;
            case POTENTIAL_VALUE:
                return PredictabilityEnum.POTENTIAL;
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
