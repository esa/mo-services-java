package org.ccsds.moims.mo.mpd.productretrieval.consumer;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mpd.productretrieval.ProductRetrievalServiceInfo;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductMetadata;
import org.ccsds.moims.mo.mpd.structures.ProductMetadataList;

/**
 * Consumer adapter for ProductRetrieval service.
 */
public abstract class ProductRetrievalAdapter extends MALInteractionAdapter {

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation listProducts.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param metadatas The list of metadata entries that match the selected filters.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listProductsResponseReceived(MALMessageHeader msgHeader,
            ProductMetadataList metadatas,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation listProducts.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listProductsErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement is received from a provider
     * for the operation getProducts.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getProductsAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update is received from a provider for
     * the operation getProducts.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param product The selected mission data product(s).
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getProductsUpdateReceived(MALMessageHeader msgHeader,
            Product product,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response is received from a provider
     * for the operation getProducts.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getProductsResponseReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement error is received from
     * a provider for the operation getProducts.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getProductsAckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update error is received from a provider
     * for the operation getProducts.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getProductsUpdateErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response error is received from a provider
     * for the operation getProducts.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getProductsResponseErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement is received from a provider
     * for the operation deliverProductFiles.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deliverProductFilesAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update is received from a provider for
     * the operation deliverProductFiles.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param metadata The metadata of the transferred mission data product(s).
     * @param filename The filename of the transferred mission data product(s).
     * @param success The completion status of the remote file transfer.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deliverProductFilesUpdateReceived(MALMessageHeader msgHeader,
            ProductMetadata metadata,
            String filename,
            Boolean success,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response is received from a provider
     * for the operation deliverProductFiles.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deliverProductFilesResponseReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS acknowledgement error is received from
     * a provider for the operation deliverProductFiles.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deliverProductFilesAckErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS update error is received from a provider
     * for the operation deliverProductFiles.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deliverProductFilesUpdateErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a PROGRESS response error is received from a provider
     * for the operation deliverProductFiles.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deliverProductFilesResponseErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void requestResponseReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ProductRetrievalServiceInfo._LISTPRODUCTS_OP_NUMBER:
            listProductsResponseReceived(msgHeader,
                (ProductMetadataList) body.getBodyElement(0, new ProductMetadataList()), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void requestErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ProductRetrievalServiceInfo._LISTPRODUCTS_OP_NUMBER:
            listProductsErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressAckReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ProductRetrievalServiceInfo._GETPRODUCTS_OP_NUMBER:
            getProductsAckReceived(msgHeader, qosProperties);
            break;
          case ProductRetrievalServiceInfo._DELIVERPRODUCTFILES_OP_NUMBER:
            deliverProductFilesAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressAckErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ProductRetrievalServiceInfo._GETPRODUCTS_OP_NUMBER:
            getProductsAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ProductRetrievalServiceInfo._DELIVERPRODUCTFILES_OP_NUMBER:
            deliverProductFilesAckErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressUpdateReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ProductRetrievalServiceInfo._GETPRODUCTS_OP_NUMBER:
            getProductsUpdateReceived(msgHeader,
                (Product) body.getBodyElement(0, new Product()), qosProperties);
            break;
          case ProductRetrievalServiceInfo._DELIVERPRODUCTFILES_OP_NUMBER:
            deliverProductFilesUpdateReceived(msgHeader,
                (ProductMetadata) body.getBodyElement(0, new ProductMetadata()),
                (body.getBodyElement(1, new Union("")) == null) ? null : ((Union) body.getBodyElement(1, new Union(""))).getStringValue(),
                (body.getBodyElement(2, new Union(Boolean.FALSE)) == null) ? null : ((Union) body.getBodyElement(2, new Union(Boolean.FALSE))).getBooleanValue(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressUpdateErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ProductRetrievalServiceInfo._GETPRODUCTS_OP_NUMBER:
            getProductsUpdateErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ProductRetrievalServiceInfo._DELIVERPRODUCTFILES_OP_NUMBER:
            deliverProductFilesUpdateErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressResponseReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ProductRetrievalServiceInfo._GETPRODUCTS_OP_NUMBER:
            getProductsResponseReceived(msgHeader, qosProperties);
            break;
          case ProductRetrievalServiceInfo._DELIVERPRODUCTFILES_OP_NUMBER:
            deliverProductFilesResponseReceived(msgHeader, qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void progressResponseErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case ProductRetrievalServiceInfo._GETPRODUCTS_OP_NUMBER:
            getProductsResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case ProductRetrievalServiceInfo._DELIVERPRODUCTFILES_OP_NUMBER:
            deliverProductFilesResponseErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

}
