package org.ccsds.moims.mo.mc.packet.provider;

/**
 * Interface that providers of the Packet service must implement to handle
 * the operations of that service.
 */
public interface PacketHandler {

    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.mc.packet.provider.PacketSkeleton skeleton);
}
