package org.ccsds.moims.mo.mc.conversion;

/**
 * Helper class for Conversion service.
 */
public class ConversionHelper {

    /**
     * Service singleton instance.
     */
    public static final ConversionServiceInfo CONVERSION_SERVICE = new ConversionServiceInfo();

    private ConversionHelper() {
        // Utility class; not meant to be instantiated.
    }

}
