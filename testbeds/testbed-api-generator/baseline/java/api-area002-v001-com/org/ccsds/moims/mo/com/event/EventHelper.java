package org.ccsds.moims.mo.com.event;

/**
 * Helper class for Event service.
 */
public class EventHelper {

    /**
     * Service singleton instance.
     */
    public static final org.ccsds.moims.mo.com.event.EventServiceInfo EVENT_SERVICE = new org.ccsds.moims.mo.com.event.EventServiceInfo();

    private EventHelper() {
        // Utility class; not meant to be instantiated.
    }

}
