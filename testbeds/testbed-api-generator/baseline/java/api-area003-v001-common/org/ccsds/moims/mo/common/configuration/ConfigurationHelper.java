package org.ccsds.moims.mo.common.configuration;

/**
 * Helper class for Configuration service.
 */
public class ConfigurationHelper {

    /**
     * Service singleton instance.
     */
    public static final org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo CONFIGURATION_SERVICE = new org.ccsds.moims.mo.common.configuration.ConfigurationServiceInfo();

    private ConfigurationHelper() {
        // Utility class; not meant to be instantiated.
    }

}
