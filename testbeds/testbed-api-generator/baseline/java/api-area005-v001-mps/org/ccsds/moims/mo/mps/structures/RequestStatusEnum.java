package org.ccsds.moims.mo.mps.structures;

/**
 * Enumeration class for RequestStatusEnum.
 */
public final class RequestStatusEnum extends org.ccsds.moims.mo.mal.structures.Enumeration {

    private static final long serialVersionUID = 1407374900330899L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330899L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Enumeration value for REQUESTED.
     */
    public static final int REQUESTED_VALUE = 1;

    /**
     * Enumeration singleton for value REQUESTED.
     */
    public static final org.ccsds.moims.mo.mps.structures.RequestStatusEnum REQUESTED = new org.ccsds.moims.mo.mps.structures.RequestStatusEnum(org.ccsds.moims.mo.mps.structures.RequestStatusEnum.REQUESTED_VALUE);

    /**
     * Enumeration value for ACCEPTED.
     */
    public static final int ACCEPTED_VALUE = 2;

    /**
     * Enumeration singleton for value ACCEPTED.
     */
    public static final org.ccsds.moims.mo.mps.structures.RequestStatusEnum ACCEPTED = new org.ccsds.moims.mo.mps.structures.RequestStatusEnum(org.ccsds.moims.mo.mps.structures.RequestStatusEnum.ACCEPTED_VALUE);

    /**
     * Enumeration value for REJECTED.
     */
    public static final int REJECTED_VALUE = 3;

    /**
     * Enumeration singleton for value REJECTED.
     */
    public static final org.ccsds.moims.mo.mps.structures.RequestStatusEnum REJECTED = new org.ccsds.moims.mo.mps.structures.RequestStatusEnum(org.ccsds.moims.mo.mps.structures.RequestStatusEnum.REJECTED_VALUE);

    /**
     * Enumeration value for CANCELLED.
     */
    public static final int CANCELLED_VALUE = 4;

    /**
     * Enumeration singleton for value CANCELLED.
     */
    public static final org.ccsds.moims.mo.mps.structures.RequestStatusEnum CANCELLED = new org.ccsds.moims.mo.mps.structures.RequestStatusEnum(org.ccsds.moims.mo.mps.structures.RequestStatusEnum.CANCELLED_VALUE);

    /**
     * Enumeration value for PLANNED.
     */
    public static final int PLANNED_VALUE = 5;

    /**
     * Enumeration singleton for value PLANNED.
     */
    public static final org.ccsds.moims.mo.mps.structures.RequestStatusEnum PLANNED = new org.ccsds.moims.mo.mps.structures.RequestStatusEnum(org.ccsds.moims.mo.mps.structures.RequestStatusEnum.PLANNED_VALUE);

    /**
     * Enumeration value for PROCESSING.
     */
    public static final int PROCESSING_VALUE = 6;

    /**
     * Enumeration singleton for value PROCESSING.
     */
    public static final org.ccsds.moims.mo.mps.structures.RequestStatusEnum PROCESSING = new org.ccsds.moims.mo.mps.structures.RequestStatusEnum(org.ccsds.moims.mo.mps.structures.RequestStatusEnum.PROCESSING_VALUE);

    /**
     * Enumeration value for PROCESSED.
     */
    public static final int PROCESSED_VALUE = 7;

    /**
     * Enumeration singleton for value PROCESSED.
     */
    public static final org.ccsds.moims.mo.mps.structures.RequestStatusEnum PROCESSED = new org.ccsds.moims.mo.mps.structures.RequestStatusEnum(org.ccsds.moims.mo.mps.structures.RequestStatusEnum.PROCESSED_VALUE);

    /**
     * Enumeration value for TERMINATED.
     */
    public static final int TERMINATED_VALUE = 8;

    /**
     * Enumeration singleton for value TERMINATED.
     */
    public static final org.ccsds.moims.mo.mps.structures.RequestStatusEnum TERMINATED = new org.ccsds.moims.mo.mps.structures.RequestStatusEnum(org.ccsds.moims.mo.mps.structures.RequestStatusEnum.TERMINATED_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final org.ccsds.moims.mo.mps.structures.RequestStatusEnum[] _ENUMERATIONS = {
        REQUESTED, ACCEPTED, REJECTED, CANCELLED, PLANNED, PROCESSING, PROCESSED,
        TERMINATED};

    /**
     * E1: The RequestStatusEnum enumeration represents the different statuses in which a planning request may be found.
     */
    public RequestStatusEnum() {
        super(-1);
    }

    /**
     * E1: The RequestStatusEnum enumeration represents the different statuses
     * in which a planning request may be found.
     * 
     * @param value The value of the Enumeration.
     */
    public RequestStatusEnum(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case REQUESTED_VALUE:
                return "REQUESTED";
            case ACCEPTED_VALUE:
                return "ACCEPTED";
            case REJECTED_VALUE:
                return "REJECTED";
            case CANCELLED_VALUE:
                return "CANCELLED";
            case PLANNED_VALUE:
                return "PLANNED";
            case PROCESSING_VALUE:
                return "PROCESSING";
            case PROCESSED_VALUE:
                return "PROCESSED";
            case TERMINATED_VALUE:
                return "TERMINATED";
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
    public static org.ccsds.moims.mo.mps.structures.RequestStatusEnum fromString(String s) {
        switch (s) {
            case "REQUESTED":
                return RequestStatusEnum.REQUESTED;
            case "ACCEPTED":
                return RequestStatusEnum.ACCEPTED;
            case "REJECTED":
                return RequestStatusEnum.REJECTED;
            case "CANCELLED":
                return RequestStatusEnum.CANCELLED;
            case "PLANNED":
                return RequestStatusEnum.PLANNED;
            case "PROCESSING":
                return RequestStatusEnum.PROCESSING;
            case "PROCESSED":
                return RequestStatusEnum.PROCESSED;
            case "TERMINATED":
                return RequestStatusEnum.TERMINATED;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Enumeration fromValue(Integer value) {
        switch (value) {
            case REQUESTED_VALUE:
                return RequestStatusEnum.REQUESTED;
            case ACCEPTED_VALUE:
                return RequestStatusEnum.ACCEPTED;
            case REJECTED_VALUE:
                return RequestStatusEnum.REJECTED;
            case CANCELLED_VALUE:
                return RequestStatusEnum.CANCELLED;
            case PLANNED_VALUE:
                return RequestStatusEnum.PLANNED;
            case PROCESSING_VALUE:
                return RequestStatusEnum.PROCESSING;
            case PROCESSED_VALUE:
                return RequestStatusEnum.PROCESSED;
            case TERMINATED_VALUE:
                return RequestStatusEnum.TERMINATED;
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
        return 8;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
