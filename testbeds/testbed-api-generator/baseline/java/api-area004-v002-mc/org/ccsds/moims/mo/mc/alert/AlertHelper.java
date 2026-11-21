package org.ccsds.moims.mo.mc.alert;

/**
 * Helper class for Alert service.
 */
public class AlertHelper {

    /**
     * Service singleton instance.
     */
    public static final AlertServiceInfo ALERT_SERVICE = new AlertServiceInfo();

    private AlertHelper() {
        // Utility class; not meant to be instantiated.
    }

}
