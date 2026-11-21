package org.ccsds.moims.mo.com.event;

/**
 * Helper class for Event service.
 */
public class EventHelper {

    /**
     * Service singleton instance.
     */
    public static final EventServiceInfo EVENT_SERVICE = new EventServiceInfo();

    private EventHelper() {
        // Utility class; not meant to be instantiated.
    }

}
