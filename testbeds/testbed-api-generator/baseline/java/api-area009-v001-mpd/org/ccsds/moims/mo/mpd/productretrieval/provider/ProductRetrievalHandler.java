package org.ccsds.moims.mo.mpd.productretrieval.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mpd.DeliveryFailedException;
import org.ccsds.moims.mo.mpd.InvalidException;
import org.ccsds.moims.mo.mpd.TooManyException;
import org.ccsds.moims.mo.mpd.UnknownException;
import org.ccsds.moims.mo.mpd.structures.ProductFilter;
import org.ccsds.moims.mo.mpd.structures.ProductMetadataList;
import org.ccsds.moims.mo.mpd.structures.TimeWindow;

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
     * @throws InvalidException When a field in the message contains an invalid value.
     * @throws TooManyException When the list cannot be returned due to too many entries.
     * @throws MALException if there is an implementation exception
     */
    ProductMetadataList listProducts(ProductFilter productFilter,
            TimeWindow creationDate,
            TimeWindow contentDate,
            MALInteraction interaction) throws InvalidException, TooManyException, MALException;
    /**
     * Implements the operation getProducts.
     * 
     * @param productRefs The references to the products to be retrieved.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws UnknownException When one or more of the productRefs was not found.
     * @throws MALException if there is an implementation exception
     */
    void getProducts(ObjectRefList productRefs,
            GetProductsInteraction interaction) throws UnknownException, MALException;
    /**
     * Implements the operation deliverProductFiles.
     * 
     * @param productRefs The references to the products to be delivered.
     * @param deliverTo The location's URI where the mission data product must be delivered.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws UnknownException When one or more of the productRefs was not found.
     * @throws DeliveryFailedException When the provider is unable to reach the selected URI (e.g. unreachable target machine, wrong credentials, revoked access, etc).
     * @throws MALException if there is an implementation exception
     */
    void deliverProductFiles(ObjectRefList productRefs,
            URI deliverTo,
            DeliverProductFilesInteraction interaction) throws UnknownException, DeliveryFailedException, MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(ProductRetrievalSkeleton skeleton);
}
