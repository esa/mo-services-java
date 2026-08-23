package org.ccsds.moims.mo.mpd.structures;

/**
 * Enumeration class for DeliveryMethodEnum.
 */
public final class DeliveryMethodEnum extends org.ccsds.moims.mo.mal.structures.Enumeration {

    private static final long serialVersionUID = 2533274807173132L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 2533274807173132L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Enumeration value for SERVICE_COMPLETE.
     */
    public static final int SERVICE_COMPLETE_VALUE = 1;

    /**
     * Enumeration singleton for value SERVICE_COMPLETE.
     */
    public static final org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum SERVICE_COMPLETE = new org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum(org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum.SERVICE_COMPLETE_VALUE);

    /**
     * Enumeration value for SERVICE_JUST_METADATA.
     */
    public static final int SERVICE_JUST_METADATA_VALUE = 2;

    /**
     * Enumeration singleton for value SERVICE_JUST_METADATA.
     */
    public static final org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum SERVICE_JUST_METADATA = new org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum(org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum.SERVICE_JUST_METADATA_VALUE);

    /**
     * Enumeration value for FILETRANSFER.
     */
    public static final int FILETRANSFER_VALUE = 3;

    /**
     * Enumeration singleton for value FILETRANSFER.
     */
    public static final org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum FILETRANSFER = new org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum(org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum.FILETRANSFER_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum[] _ENUMERATIONS = {
        SERVICE_COMPLETE, SERVICE_JUST_METADATA, FILETRANSFER};

    /**
     * The DeliveryMethodEnum enumeration defines the delivery method to be used for delivery of mission data products.
     */
    public DeliveryMethodEnum() {
        super(-1);
    }

    /**
     * The DeliveryMethodEnum enumeration defines the delivery method to be used
     * for delivery of mission data products.
     * 
     * @param value The value of the Enumeration.
     */
    public DeliveryMethodEnum(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case SERVICE_COMPLETE_VALUE:
                return "SERVICE_COMPLETE";
            case SERVICE_JUST_METADATA_VALUE:
                return "SERVICE_JUST_METADATA";
            case FILETRANSFER_VALUE:
                return "FILETRANSFER";
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
    public static org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum fromString(String s) {
        switch (s) {
            case "SERVICE_COMPLETE":
                return DeliveryMethodEnum.SERVICE_COMPLETE;
            case "SERVICE_JUST_METADATA":
                return DeliveryMethodEnum.SERVICE_JUST_METADATA;
            case "FILETRANSFER":
                return DeliveryMethodEnum.FILETRANSFER;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Enumeration fromValue(Integer value) {
        switch (value) {
            case SERVICE_COMPLETE_VALUE:
                return DeliveryMethodEnum.SERVICE_COMPLETE;
            case SERVICE_JUST_METADATA_VALUE:
                return DeliveryMethodEnum.SERVICE_JUST_METADATA;
            case FILETRANSFER_VALUE:
                return DeliveryMethodEnum.FILETRANSFER;
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
        return 3;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
