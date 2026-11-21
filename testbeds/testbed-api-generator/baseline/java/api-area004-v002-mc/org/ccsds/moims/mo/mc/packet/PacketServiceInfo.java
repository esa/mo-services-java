package org.ccsds.moims.mo.mc.packet;

import java.util.ArrayList;
import java.util.Arrays;
import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.ServiceKey;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mc.MCHelper;

/**
 * Helper class for Packet service.
 */
public class PacketServiceInfo extends ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _PACKET_SERVICE_NUMBER = 9;

    /**
     * Service number instance.
     */
    public static final UShort PACKET_SERVICE_NUMBER = new UShort(_PACKET_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier PACKET_SERVICE_NAME = new Identifier("Packet");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            4, 2, PACKET_SERVICE_NUMBER);

    /**
     * Operation number literal for operation DELIVERPACKET.
     */
    public static final int _DELIVERPACKET_OP_NUMBER = 1;

    /**
     * Operation number instance for operation DELIVERPACKET.
     */
    private static final UShort DELIVERPACKET_OP_NUMBER = new UShort(_DELIVERPACKET_OP_NUMBER);

    /**
     * Operation instance for operation DELIVERPACKET.
     */
    public static final MALPubSubOperation DELIVERPACKET_OP = new MALPubSubOperation(SERVICE_KEY, 
            DELIVERPACKET_OP_NUMBER, 
            new Identifier("deliverPacket"), 
            new UShort(1), 
            new OperationField[] {
                new OperationField("timestamp", false, Attribute.TIME_SHORT_FORM, ""),
                new OperationField("spacePacket", false, Attribute.BLOB_SHORT_FORM, "")}, 
            "The deliverPacket operation allows a provider to publish space packets with associated metadata, and a consumer to receive a filtered set of those packets.");

    /**
     * Key names instance for DELIVERPACKET operation of pubsub interaction pattern.
     */
    private static final Identifier [] _DELIVERPACKET_OP_KEY_NAMES = {new Identifier("apid")};

    /**
     * Key names instance for DELIVERPACKET operation of pubsub interaction pattern.
     */
    private static final IdentifierList DELIVERPACKET_OP_KEY_NAMES = new IdentifierList(new ArrayList<>(Arrays.asList(_DELIVERPACKET_OP_KEY_NAMES)));

    /**
     * Area elements.
     */
    public static final Element[] PACKET_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{DELIVERPACKET_OP};

    /**
     * Creates an instance of the Packet ServiceInfo.
     * 
     */
    public PacketServiceInfo() {
        super(SERVICE_KEY, PACKET_SERVICE_NAME, PACKET_SERVICE_ELEMENTS, OPERATIONS);
    }

    @Override
    public MALArea getArea() {
        return MCHelper.MC_AREA;
    }

    @Override
    public MOErrorException generateMOError(int operationNumber,
            int errorNumber,
            Object extraInfo) {
        MOErrorException areaError = MCHelper.generateMOError(errorNumber, extraInfo);
        return (areaError != null) ? areaError : MALHelper.generateMOError(errorNumber, extraInfo);
    }

}
