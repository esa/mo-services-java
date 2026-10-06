package org.ccsds.moims.mo.mc.statistic;

/**
 * Helper class for Statistic service.
 */
public class StatisticHelper {

    /**
     * Service singleton instance.
     */
    public static final StatisticServiceInfo STATISTIC_SERVICE = new StatisticServiceInfo();

    private StatisticHelper() {
        // Utility class; not meant to be instantiated.
    }

}
