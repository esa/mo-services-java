package org.ccsds.moims.mo.mc.packet;

/**
 * Helper class for Packet service.
 */
public class PacketHelper {

    /**
     * Service singleton instance.
     */
    public static final PacketServiceInfo PACKET_SERVICE = new PacketServiceInfo();

    private PacketHelper() {
        // Utility class; not meant to be instantiated.
    }

}
