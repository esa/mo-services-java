package org.ccsds.moims.mo.malprototype.iptest;

/**
 * Helper class for IPTest service.
 */
public class IPTestHelper {

    /**
     * Service singleton instance.
     */
    public static final IPTestServiceInfo IPTEST_SERVICE = new IPTestServiceInfo();

    private IPTestHelper() {
        // Utility class; not meant to be instantiated.
    }

}
