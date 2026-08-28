package org.ccsds.moims.mo.mc.group.provider;

/**
 * Interface that providers of the Group service must implement to handle
 * the operations of that service.
 */
public interface GroupHandler {

    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.mc.group.provider.GroupSkeleton skeleton);
}
