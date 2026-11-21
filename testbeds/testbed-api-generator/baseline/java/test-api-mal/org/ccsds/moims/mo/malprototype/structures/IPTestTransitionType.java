package org.ccsds.moims.mo.malprototype.structures;

import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Enumeration;

/**
 * Enumeration class for IPTestTransitionType.
 */
public final class IPTestTransitionType extends Enumeration {

    private static final long serialVersionUID = 28147497687842819L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687842819L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Enumeration value for ACK.
     */
    public static final int ACK_VALUE = 1;

    /**
     * Enumeration singleton for value ACK.
     */
    public static final IPTestTransitionType ACK = new IPTestTransitionType(IPTestTransitionType.ACK_VALUE);

    /**
     * Enumeration value for RESPONSE.
     */
    public static final int RESPONSE_VALUE = 2;

    /**
     * Enumeration singleton for value RESPONSE.
     */
    public static final IPTestTransitionType RESPONSE = new IPTestTransitionType(IPTestTransitionType.RESPONSE_VALUE);

    /**
     * Enumeration value for ACK_ERROR.
     */
    public static final int ACK_ERROR_VALUE = 3;

    /**
     * Enumeration singleton for value ACK_ERROR.
     */
    public static final IPTestTransitionType ACK_ERROR = new IPTestTransitionType(IPTestTransitionType.ACK_ERROR_VALUE);

    /**
     * Enumeration value for RESPONSE_ERROR.
     */
    public static final int RESPONSE_ERROR_VALUE = 4;

    /**
     * Enumeration singleton for value RESPONSE_ERROR.
     */
    public static final IPTestTransitionType RESPONSE_ERROR = new IPTestTransitionType(IPTestTransitionType.RESPONSE_ERROR_VALUE);

    /**
     * Enumeration value for UPDATE.
     */
    public static final int UPDATE_VALUE = 5;

    /**
     * Enumeration singleton for value UPDATE.
     */
    public static final IPTestTransitionType UPDATE = new IPTestTransitionType(IPTestTransitionType.UPDATE_VALUE);

    /**
     * Enumeration value for UPDATE_ERROR.
     */
    public static final int UPDATE_ERROR_VALUE = 6;

    /**
     * Enumeration singleton for value UPDATE_ERROR.
     */
    public static final IPTestTransitionType UPDATE_ERROR = new IPTestTransitionType(IPTestTransitionType.UPDATE_ERROR_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final IPTestTransitionType[] _ENUMERATIONS = {
        ACK, RESPONSE, ACK_ERROR, RESPONSE_ERROR, UPDATE, UPDATE_ERROR};

    /**
     * 
     */
    public IPTestTransitionType() {
        super(-1);
    }

    /**
     * Creates an instance of the IPTestTransitionType Enumeration.
     * 
     * @param value The value of the Enumeration.
     */
    public IPTestTransitionType(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case ACK_VALUE:
                return "ACK";
            case RESPONSE_VALUE:
                return "RESPONSE";
            case ACK_ERROR_VALUE:
                return "ACK_ERROR";
            case RESPONSE_ERROR_VALUE:
                return "RESPONSE_ERROR";
            case UPDATE_VALUE:
                return "UPDATE";
            case UPDATE_ERROR_VALUE:
                return "UPDATE_ERROR";
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
    public static IPTestTransitionType fromString(String s) {
        switch (s) {
            case "ACK":
                return IPTestTransitionType.ACK;
            case "RESPONSE":
                return IPTestTransitionType.RESPONSE;
            case "ACK_ERROR":
                return IPTestTransitionType.ACK_ERROR;
            case "RESPONSE_ERROR":
                return IPTestTransitionType.RESPONSE_ERROR;
            case "UPDATE":
                return IPTestTransitionType.UPDATE;
            case "UPDATE_ERROR":
                return IPTestTransitionType.UPDATE_ERROR;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public Enumeration fromValue(Integer value) {
        switch (value) {
            case ACK_VALUE:
                return IPTestTransitionType.ACK;
            case RESPONSE_VALUE:
                return IPTestTransitionType.RESPONSE;
            case ACK_ERROR_VALUE:
                return IPTestTransitionType.ACK_ERROR;
            case RESPONSE_ERROR_VALUE:
                return IPTestTransitionType.RESPONSE_ERROR;
            case UPDATE_VALUE:
                return IPTestTransitionType.UPDATE;
            case UPDATE_ERROR_VALUE:
                return IPTestTransitionType.UPDATE_ERROR;
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
        return 6;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
