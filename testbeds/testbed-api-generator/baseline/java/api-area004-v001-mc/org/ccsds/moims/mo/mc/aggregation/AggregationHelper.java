package org.ccsds.moims.mo.mc.aggregation;

/**
 * Helper class for Aggregation service.
 */
public class AggregationHelper {

    /**
     * Service singleton instance.
     */
    public static final AggregationServiceInfo AGGREGATION_SERVICE = new AggregationServiceInfo();

    private AggregationHelper() {
        // Utility class; not meant to be instantiated.
    }

}
