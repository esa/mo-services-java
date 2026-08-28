package org.ccsds.moims.mo.mal.structures;

/**
 * Enumeration class for InteractionType.
 */
public final class InteractionType extends org.ccsds.moims.mo.mal.structures.Enumeration {

    private static final long serialVersionUID = 281475027042405L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475027042405L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Enumeration value for SEND.
     */
    public static final int SEND_VALUE = 1;

    /**
     * Enumeration singleton for value SEND.
     */
    public static final org.ccsds.moims.mo.mal.structures.InteractionType SEND = new org.ccsds.moims.mo.mal.structures.InteractionType(org.ccsds.moims.mo.mal.structures.InteractionType.SEND_VALUE);

    /**
     * Enumeration value for SUBMIT.
     */
    public static final int SUBMIT_VALUE = 2;

    /**
     * Enumeration singleton for value SUBMIT.
     */
    public static final org.ccsds.moims.mo.mal.structures.InteractionType SUBMIT = new org.ccsds.moims.mo.mal.structures.InteractionType(org.ccsds.moims.mo.mal.structures.InteractionType.SUBMIT_VALUE);

    /**
     * Enumeration value for REQUEST.
     */
    public static final int REQUEST_VALUE = 3;

    /**
     * Enumeration singleton for value REQUEST.
     */
    public static final org.ccsds.moims.mo.mal.structures.InteractionType REQUEST = new org.ccsds.moims.mo.mal.structures.InteractionType(org.ccsds.moims.mo.mal.structures.InteractionType.REQUEST_VALUE);

    /**
     * Enumeration value for INVOKE.
     */
    public static final int INVOKE_VALUE = 4;

    /**
     * Enumeration singleton for value INVOKE.
     */
    public static final org.ccsds.moims.mo.mal.structures.InteractionType INVOKE = new org.ccsds.moims.mo.mal.structures.InteractionType(org.ccsds.moims.mo.mal.structures.InteractionType.INVOKE_VALUE);

    /**
     * Enumeration value for PROGRESS.
     */
    public static final int PROGRESS_VALUE = 5;

    /**
     * Enumeration singleton for value PROGRESS.
     */
    public static final org.ccsds.moims.mo.mal.structures.InteractionType PROGRESS = new org.ccsds.moims.mo.mal.structures.InteractionType(org.ccsds.moims.mo.mal.structures.InteractionType.PROGRESS_VALUE);

    /**
     * Enumeration value for PUBSUB.
     */
    public static final int PUBSUB_VALUE = 6;

    /**
     * Enumeration singleton for value PUBSUB.
     */
    public static final org.ccsds.moims.mo.mal.structures.InteractionType PUBSUB = new org.ccsds.moims.mo.mal.structures.InteractionType(org.ccsds.moims.mo.mal.structures.InteractionType.PUBSUB_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final org.ccsds.moims.mo.mal.structures.InteractionType[] _ENUMERATIONS = {
        SEND, SUBMIT, REQUEST, INVOKE, PROGRESS, PUBSUB};

    /**
     * InteractionType is an enumeration that shall be used to hold the possible Interaction Pattern types.
     */
    public InteractionType() {
        super(-1);
    }

    /**
     * InteractionType is an enumeration that shall be used to hold the possible
     * Interaction Pattern types.
     * 
     * @param value The value of the Enumeration.
     */
    public InteractionType(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case SEND_VALUE:
                return "SEND";
            case SUBMIT_VALUE:
                return "SUBMIT";
            case REQUEST_VALUE:
                return "REQUEST";
            case INVOKE_VALUE:
                return "INVOKE";
            case PROGRESS_VALUE:
                return "PROGRESS";
            case PUBSUB_VALUE:
                return "PUBSUB";
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
    public static org.ccsds.moims.mo.mal.structures.InteractionType fromString(String s) {
        switch (s) {
            case "SEND":
                return InteractionType.SEND;
            case "SUBMIT":
                return InteractionType.SUBMIT;
            case "REQUEST":
                return InteractionType.REQUEST;
            case "INVOKE":
                return InteractionType.INVOKE;
            case "PROGRESS":
                return InteractionType.PROGRESS;
            case "PUBSUB":
                return InteractionType.PUBSUB;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Enumeration fromValue(Integer value) {
        switch (value) {
            case SEND_VALUE:
                return InteractionType.SEND;
            case SUBMIT_VALUE:
                return InteractionType.SUBMIT;
            case REQUEST_VALUE:
                return InteractionType.REQUEST;
            case INVOKE_VALUE:
                return InteractionType.INVOKE;
            case PROGRESS_VALUE:
                return InteractionType.PROGRESS;
            case PUBSUB_VALUE:
                return InteractionType.PUBSUB;
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
        return 6;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
