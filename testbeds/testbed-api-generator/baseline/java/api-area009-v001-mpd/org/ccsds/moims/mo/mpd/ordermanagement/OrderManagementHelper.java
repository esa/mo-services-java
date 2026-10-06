package org.ccsds.moims.mo.mpd.ordermanagement;

/**
 * Helper class for OrderManagement service.
 */
public class OrderManagementHelper {

    /**
     * Service singleton instance.
     */
    public static final OrderManagementServiceInfo ORDERMANAGEMENT_SERVICE = new OrderManagementServiceInfo();

    private OrderManagementHelper() {
        // Utility class; not meant to be instantiated.
    }

}
