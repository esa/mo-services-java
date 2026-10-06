package org.ccsds.moims.mo.common.configuration;

/**
 * Helper class for Configuration service.
 */
public class ConfigurationHelper {

    /**
     * Service singleton instance.
     */
    public static final ConfigurationServiceInfo CONFIGURATION_SERVICE = new ConfigurationServiceInfo();

    private ConfigurationHelper() {
        // Utility class; not meant to be instantiated.
    }

}
