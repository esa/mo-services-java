package org.ccsds.moims.mo.com.event.provider;

/**
 * Interface that providers of the Event service must implement to handle
 * the operations of that service.
 */
public interface EventHandler {

    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(EventSkeleton skeleton);
}
