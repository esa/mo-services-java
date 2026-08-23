package org.ccsds.moims.mo.mpd.productretrieval.consumer;

/**
 * Consumer stub for ProductRetrieval service.
 */
public class ProductRetrievalStub {

    /**
     * The consumer field.
     */
    private final org.ccsds.moims.mo.mal.consumer.MALConsumer consumer;

    /**
     * Wraps a MALconsumer connection with service specific methods that map from
     * the high level service API to the generic MAL API.
     * 
     * @param consumer consumer The MALConsumer to use in this stub.
     */
    public ProductRetrievalStub(org.ccsds.moims.mo.mal.consumer.MALConsumer consumer) {
        this.consumer = consumer;
    }

    /**
     * Returns the internal MAL consumer object used for sending of messages from
     * this interface.
     * 
     * @return The MAL consumer object.
     */
    public org.ccsds.moims.mo.mal.consumer.MALConsumer getConsumer() {
        return consumer;
    }

    /**
     * The listProducts operation lists the available products for a selected
     * product filter and optionally also for a selected creation date and for
     * a selected content date time window.
     * 
     * @param productFilter The product filter used to refine the selection of products.
     * @param creationDate The time window used to filter products based on their creation date.
     * @param contentDate The time window used to filter products based on their content creation period.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mpd.structures.ProductMetadataList listProducts(org.ccsds.moims.mo.mpd.structures.ProductFilter productFilter,
            org.ccsds.moims.mo.mpd.structures.TimeWindow creationDate,
            org.ccsds.moims.mo.mpd.structures.TimeWindow contentDate) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mpd.productretrieval.ProductRetrievalServiceInfo.LISTPRODUCTS_OP, productFilter, creationDate, contentDate);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mpd.structures.ProductMetadataList());
        return (org.ccsds.moims.mo.mpd.structures.ProductMetadataList) body0;
    }

    /**
     * Asynchronous version of method listProducts.
     * 
     * @param productFilter The product filter used to refine the selection of products.
     * @param creationDate The time window used to filter products based on their creation date.
     * @param contentDate The time window used to filter products based on their content creation period.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncListProducts(org.ccsds.moims.mo.mpd.structures.ProductFilter productFilter,
            org.ccsds.moims.mo.mpd.structures.TimeWindow creationDate,
            org.ccsds.moims.mo.mpd.structures.TimeWindow contentDate,
            org.ccsds.moims.mo.mpd.productretrieval.consumer.ProductRetrievalAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mpd.productretrieval.ProductRetrievalServiceInfo.LISTPRODUCTS_OP, adapter, productFilter, creationDate, contentDate);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueListProducts(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mpd.productretrieval.consumer.ProductRetrievalAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mpd.productretrieval.ProductRetrievalServiceInfo.LISTPRODUCTS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The getProducts operation retrieves the selected mission data products
     * from the provider.
     * 
     * @param productRefs The references to the products to be retrieved.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void getProducts(org.ccsds.moims.mo.mal.structures.ObjectRefList productRefs,
            org.ccsds.moims.mo.mpd.productretrieval.consumer.ProductRetrievalAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.progress(org.ccsds.moims.mo.mpd.productretrieval.ProductRetrievalServiceInfo.GETPRODUCTS_OP, adapter, productRefs);
    }

    /**
     * Asynchronous version of method getProducts.
     * 
     * @param productRefs The references to the products to be retrieved.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncGetProducts(org.ccsds.moims.mo.mal.structures.ObjectRefList productRefs,
            org.ccsds.moims.mo.mpd.productretrieval.consumer.ProductRetrievalAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncProgress(org.ccsds.moims.mo.mpd.productretrieval.ProductRetrievalServiceInfo.GETPRODUCTS_OP, adapter, productRefs);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueGetProducts(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mpd.productretrieval.consumer.ProductRetrievalAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mpd.productretrieval.ProductRetrievalServiceInfo.GETPRODUCTS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The deliverProductFiles operation allows consumers to instruct the provider
     * to initiate a remote file transfer delivery of the selected mission data
     * products to a specified target.
     * 
     * @param productRefs The references to the products to be delivered.
     * @param deliverTo The location's URI where the mission data product must be delivered.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void deliverProductFiles(org.ccsds.moims.mo.mal.structures.ObjectRefList productRefs,
            org.ccsds.moims.mo.mal.structures.URI deliverTo,
            org.ccsds.moims.mo.mpd.productretrieval.consumer.ProductRetrievalAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.progress(org.ccsds.moims.mo.mpd.productretrieval.ProductRetrievalServiceInfo.DELIVERPRODUCTFILES_OP, adapter, productRefs, deliverTo);
    }

    /**
     * Asynchronous version of method deliverProductFiles.
     * 
     * @param productRefs The references to the products to be delivered.
     * @param deliverTo The location's URI where the mission data product must be delivered.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncDeliverProductFiles(org.ccsds.moims.mo.mal.structures.ObjectRefList productRefs,
            org.ccsds.moims.mo.mal.structures.URI deliverTo,
            org.ccsds.moims.mo.mpd.productretrieval.consumer.ProductRetrievalAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncProgress(org.ccsds.moims.mo.mpd.productretrieval.ProductRetrievalServiceInfo.DELIVERPRODUCTFILES_OP, adapter, productRefs, deliverTo);
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void continueDeliverProductFiles(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mpd.productretrieval.consumer.ProductRetrievalAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mpd.productretrieval.ProductRetrievalServiceInfo.DELIVERPRODUCTFILES_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

}
