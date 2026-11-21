package org.ccsds.moims.mo.mpd.productretrieval.consumer;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mpd.DeliveryFailedException;
import org.ccsds.moims.mo.mpd.InvalidException;
import org.ccsds.moims.mo.mpd.TooManyException;
import org.ccsds.moims.mo.mpd.UnknownException;
import org.ccsds.moims.mo.mpd.productretrieval.ProductRetrievalServiceInfo;
import org.ccsds.moims.mo.mpd.structures.ProductFilter;
import org.ccsds.moims.mo.mpd.structures.ProductMetadataList;
import org.ccsds.moims.mo.mpd.structures.TimeWindow;

/**
 * Consumer stub for ProductRetrieval service.
 */
public class ProductRetrievalStub {

    /**
     * The consumer field.
     */
    private final MALConsumer consumer;

    /**
     * Wraps a MALconsumer connection with service specific methods that map from
     * the high level service API to the generic MAL API.
     * 
     * @param consumer consumer The MALConsumer to use in this stub.
     */
    public ProductRetrievalStub(MALConsumer consumer) {
        this.consumer = consumer;
    }

    /**
     * Returns the internal MAL consumer object used for sending of messages from
     * this interface.
     * 
     * @return The MAL consumer object.
     */
    public MALConsumer getConsumer() {
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
     * @throws InvalidException When a field in the message contains an invalid value.
     * @throws TooManyException When the list cannot be returned due to too many entries.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public ProductMetadataList listProducts(ProductFilter productFilter,
            TimeWindow creationDate,
            TimeWindow contentDate) throws InvalidException, TooManyException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ProductRetrievalServiceInfo.LISTPRODUCTS_OP, productFilter, creationDate, contentDate);
            Object body0 = (Object) body.getBodyElement(0, new ProductMetadataList());
            return (ProductMetadataList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof TooManyException) {
                throw (TooManyException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method listProducts.
     * 
     * @param productFilter The product filter used to refine the selection of products.
     * @param creationDate The time window used to filter products based on their creation date.
     * @param contentDate The time window used to filter products based on their content creation period.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncListProducts(ProductFilter productFilter,
            TimeWindow creationDate,
            TimeWindow contentDate,
            ProductRetrievalAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ProductRetrievalServiceInfo.LISTPRODUCTS_OP, adapter, productFilter, creationDate, contentDate);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueListProducts(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ProductRetrievalAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ProductRetrievalServiceInfo.LISTPRODUCTS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The getProducts operation retrieves the selected mission data products
     * from the provider.
     * 
     * @param productRefs The references to the products to be retrieved.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws UnknownException When one or more of the productRefs was not found.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void getProducts(ObjectRefList productRefs,
            ProductRetrievalAdapter adapter) throws UnknownException, MALStandardError, MALException {
        try {
            consumer.progress(ProductRetrievalServiceInfo.GETPRODUCTS_OP, adapter, productRefs);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method getProducts.
     * 
     * @param productRefs The references to the products to be retrieved.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncGetProducts(ObjectRefList productRefs,
            ProductRetrievalAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncProgress(ProductRetrievalServiceInfo.GETPRODUCTS_OP, adapter, productRefs);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueGetProducts(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ProductRetrievalAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ProductRetrievalServiceInfo.GETPRODUCTS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The deliverProductFiles operation allows consumers to instruct the provider
     * to initiate a remote file transfer delivery of the selected mission data
     * products to a specified target.
     * 
     * @param productRefs The references to the products to be delivered.
     * @param deliverTo The location's URI where the mission data product must be delivered.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws UnknownException When one or more of the productRefs was not found.
     * @throws DeliveryFailedException When the provider is unable to reach the selected URI (e.g. unreachable target machine, wrong credentials, revoked access, etc).
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void deliverProductFiles(ObjectRefList productRefs,
            URI deliverTo,
            ProductRetrievalAdapter adapter) throws UnknownException, DeliveryFailedException, MALStandardError, MALException {
        try {
            consumer.progress(ProductRetrievalServiceInfo.DELIVERPRODUCTFILES_OP, adapter, productRefs, deliverTo);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            if (error instanceof DeliveryFailedException) {
                throw (DeliveryFailedException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method deliverProductFiles.
     * 
     * @param productRefs The references to the products to be delivered.
     * @param deliverTo The location's URI where the mission data product must be delivered.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncDeliverProductFiles(ObjectRefList productRefs,
            URI deliverTo,
            ProductRetrievalAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncProgress(ProductRetrievalServiceInfo.DELIVERPRODUCTFILES_OP, adapter, productRefs, deliverTo);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueDeliverProductFiles(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ProductRetrievalAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ProductRetrievalServiceInfo.DELIVERPRODUCTFILES_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

}
