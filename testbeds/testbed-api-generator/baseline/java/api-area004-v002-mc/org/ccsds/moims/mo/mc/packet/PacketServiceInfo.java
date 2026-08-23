package org.ccsds.moims.mo.mc.packet;

/**
 * Helper class for Packet service.
 */
public class PacketServiceInfo extends org.ccsds.moims.mo.mal.ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _PACKET_SERVICE_NUMBER = 9;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort PACKET_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_PACKET_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier PACKET_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("Packet");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            4, 2, PACKET_SERVICE_NUMBER);

    /**
     * Operation number literal for operation DELIVERPACKET.
     */
    public static final int _DELIVERPACKET_OP_NUMBER = 1;

    /**
     * Operation number instance for operation DELIVERPACKET.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort DELIVERPACKET_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_DELIVERPACKET_OP_NUMBER);

    /**
     * Operation instance for operation DELIVERPACKET.
     */
    public static final org.ccsds.moims.mo.mal.MALPubSubOperation DELIVERPACKET_OP = new org.ccsds.moims.mo.mal.MALPubSubOperation(SERVICE_KEY, 
            DELIVERPACKET_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("deliverPacket"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("timestamp", false, org.ccsds.moims.mo.mal.structures.Attribute.TIME_SHORT_FORM, ""),
                new org.ccsds.moims.mo.mal.OperationField("spacePacket", false, org.ccsds.moims.mo.mal.structures.Attribute.BLOB_SHORT_FORM, "")}, 
            "The deliverPacket operation allows a provider to publish space packets with associated metadata, and a consumer to receive a filtered set of those packets.");

    /**
     * Key names instance for DELIVERPACKET operation of pubsub interaction pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.Identifier [] _DELIVERPACKET_OP_KEY_NAMES = {new org.ccsds.moims.mo.mal.structures.Identifier("apid")};

    /**
     * Key names instance for DELIVERPACKET operation of pubsub interaction pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.IdentifierList DELIVERPACKET_OP_KEY_NAMES = new org.ccsds.moims.mo.mal.structures.IdentifierList(new java.util.ArrayList<>(java.util.Arrays.asList(_DELIVERPACKET_OP_KEY_NAMES)));

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] PACKET_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{DELIVERPACKET_OP};

    /**
     * Creates an instance of the Packet ServiceInfo.
     * 
     */
    public PacketServiceInfo() {
        super(SERVICE_KEY, PACKET_SERVICE_NAME, PACKET_SERVICE_ELEMENTS, OPERATIONS);
    }

    @Override
    public org.ccsds.moims.mo.mal.MALArea getArea() {
        return org.ccsds.moims.mo.mc.MCHelper.MC_AREA;
    }

    @Override
    public org.ccsds.moims.mo.mal.MOErrorException generateMOError(int errorNumber,
            Object extraInfo) {
        switch (errorNumber) {
            case 1:
                return new org.ccsds.moims.mo.mc.ReadOnlyException(extraInfo);
            case 2:
                return new org.ccsds.moims.mo.mc.DuplicateException(extraInfo);
            case 3:
                return new org.ccsds.moims.mo.mc.InvalidException(extraInfo);
            case 4:
                return new org.ccsds.moims.mo.mc.RejectedException(extraInfo);
            case 5:
                return new org.ccsds.moims.mo.mc.AmbiguousException(extraInfo);
        }
        return null;
    }

}
