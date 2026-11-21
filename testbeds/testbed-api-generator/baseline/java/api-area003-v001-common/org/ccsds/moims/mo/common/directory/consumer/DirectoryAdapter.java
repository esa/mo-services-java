package org.ccsds.moims.mo.common.directory.consumer;

import java.util.Map;
import org.ccsds.moims.mo.common.directory.DirectoryServiceInfo;
import org.ccsds.moims.mo.common.directory.structures.ProviderSummaryList;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter;
import org.ccsds.moims.mo.mal.structures.FileList;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Consumer adapter for Directory service.
 */
public abstract class DirectoryAdapter extends MALInteractionAdapter {

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation lookupProvider.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param matchingProviders The operation shall return a list of service providers that match the filter.
If no service providers match the supplied filter then an empty list shall be returned.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void lookupProviderResponseReceived(MALMessageHeader msgHeader,
            ProviderSummaryList matchingProviders,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation lookupProvider.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void lookupProviderErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation publishProvider.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param providerObjId If the providerId field of the PublishDetails structure matches an existing ServiceProvider COM object, the operation shall update the existing details of that provider.
If the providerId field of the PublishDetails structure does not match an existing ServiceProvider COM object, then the operation shall create a new ServiceProvider COM object to represent the new service provider.
A new ProviderCapabilities COM object shall be created to store the capabilities of the provider.
The created objects should be stored in the COM archive by the directory service provider.
The operation shall return the COM object instance identifiers of the ServiceProvider and ProviderCapabilities COM objects representing the provider.
     * @param capabilitiesObjId capabilitiesObjId Argument number 1 as defined by the service operation
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void publishProviderResponseReceived(MALMessageHeader msgHeader,
            Long providerObjId,
            Long capabilitiesObjId,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation publishProvider.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void publishProviderErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation withdrawProvider.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void withdrawProviderAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation withdrawProvider.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void withdrawProviderErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getServiceXML.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param xmlFiles The list of XML files supplied during the publishProvider operation for the matched provider shall be returned.
If no XML files were supplied by the provider then an empty list shall be returned.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getServiceXMLResponseReceived(MALMessageHeader msgHeader,
            FileList xmlFiles,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getServiceXML.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getServiceXMLErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case DirectoryServiceInfo._WITHDRAWPROVIDER_OP_NUMBER:
            withdrawProviderAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void submitErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case DirectoryServiceInfo._WITHDRAWPROVIDER_OP_NUMBER:
            withdrawProviderErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void requestResponseReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case DirectoryServiceInfo._LOOKUPPROVIDER_OP_NUMBER:
            lookupProviderResponseReceived(msgHeader,
                (ProviderSummaryList) body.getBodyElement(0, new ProviderSummaryList()), qosProperties);
            break;
          case DirectoryServiceInfo._PUBLISHPROVIDER_OP_NUMBER:
            publishProviderResponseReceived(msgHeader,
                (body.getBodyElement(0, new Union(Long.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Long.MAX_VALUE))).getLongValue(),
                (body.getBodyElement(1, new Union(Long.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(1, new Union(Long.MAX_VALUE))).getLongValue(), qosProperties);
            break;
          case DirectoryServiceInfo._GETSERVICEXML_OP_NUMBER:
            getServiceXMLResponseReceived(msgHeader,
                (FileList) body.getBodyElement(0, new FileList()), qosProperties);
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
          case DirectoryServiceInfo._LOOKUPPROVIDER_OP_NUMBER:
            lookupProviderErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DirectoryServiceInfo._PUBLISHPROVIDER_OP_NUMBER:
            publishProviderErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DirectoryServiceInfo._GETSERVICEXML_OP_NUMBER:
            getServiceXMLErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

}
