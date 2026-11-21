package org.ccsds.moims.mo.mpd.productretrieval;

/**
 * Helper class for ProductRetrieval service.
 */
public class ProductRetrievalHelper {

    /**
     * Service singleton instance.
     */
    public static final ProductRetrievalServiceInfo PRODUCTRETRIEVAL_SERVICE = new ProductRetrievalServiceInfo();

    private ProductRetrievalHelper() {
        // Utility class; not meant to be instantiated.
    }

}
