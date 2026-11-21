package org.ccsds.moims.mo.com.activitytracking;

/**
 * Helper class for ActivityTracking service.
 */
public class ActivityTrackingHelper {

    /**
     * Service singleton instance.
     */
    public static final ActivityTrackingServiceInfo ACTIVITYTRACKING_SERVICE = new ActivityTrackingServiceInfo();

    private ActivityTrackingHelper() {
        // Utility class; not meant to be instantiated.
    }

}
