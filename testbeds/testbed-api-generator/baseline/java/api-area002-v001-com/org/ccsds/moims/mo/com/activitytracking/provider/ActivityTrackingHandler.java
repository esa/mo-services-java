package org.ccsds.moims.mo.com.activitytracking.provider;

/**
 * Interface that providers of the ActivityTracking service must implement
 * to handle the operations of that service.
 */
public interface ActivityTrackingHandler {

    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.com.activitytracking.provider.ActivityTrackingSkeleton skeleton);
}
