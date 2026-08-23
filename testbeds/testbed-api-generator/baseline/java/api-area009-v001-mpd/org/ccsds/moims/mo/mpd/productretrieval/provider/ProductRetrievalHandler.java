package org.ccsds.moims.mo.mpd.productretrieval.provider;

/**
 * Interface that providers of the ProductRetrieval service must implement
 * to handle the operations of that service.
 */
public interface ProductRetrievalHandler {

    /**
     * Implements the operation listProducts.
     * 
     * @param productFilter The product filter used to refine the selection of products.
     * @param creationDate The time window used to filter products based on their creation date.
     * @param contentDate The time window used to filter products based on their content creation period.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mpd.InvalidException When a field in the message contains an invalid value.
     * @throws org.ccsds.moims.mo.mpd.TooManyException When the list cannot be returned due to too many entries.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mpd.structures.ProductMetadataList listProducts(org.ccsds.moims.mo.mpd.structures.ProductFilter productFilter,
            org.ccsds.moims.mo.mpd.structures.TimeWindow creationDate,
            org.ccsds.moims.mo.mpd.structures.TimeWindow contentDate,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mpd.InvalidException, org.ccsds.moims.mo.mpd.TooManyException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation getProducts.
     * 
     * @param productRefs The references to the products to be retrieved.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mpd.UnknownException When one or more of the productRefs was not found.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void getProducts(org.ccsds.moims.mo.mal.structures.ObjectRefList productRefs,
            org.ccsds.moims.mo.mpd.productretrieval.provider.GetProductsInteraction interaction) throws org.ccsds.moims.mo.mpd.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation deliverProductFiles.
     * 
     * @param productRefs The references to the products to be delivered.
     * @param deliverTo The location's URI where the mission data product must be delivered.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mpd.UnknownException When one or more of the productRefs was not found.
     * @throws org.ccsds.moims.mo.mpd.DeliveryFailedException When the provider is unable to reach the selected URI (e.g. unreachable target machine, wrong credentials, revoked access, etc).
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void deliverProductFiles(org.ccsds.moims.mo.mal.structures.ObjectRefList productRefs,
            org.ccsds.moims.mo.mal.structures.URI deliverTo,
            org.ccsds.moims.mo.mpd.productretrieval.provider.DeliverProductFilesInteraction interaction) throws org.ccsds.moims.mo.mpd.UnknownException, org.ccsds.moims.mo.mpd.DeliveryFailedException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.mpd.productretrieval.provider.ProductRetrievalSkeleton skeleton);
}
