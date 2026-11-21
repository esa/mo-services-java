package org.ccsds.moims.mo.common.directory.consumer;

import org.ccsds.moims.mo.com.InvalidException;
import org.ccsds.moims.mo.common.directory.DirectoryServiceInfo;
import org.ccsds.moims.mo.common.directory.body.PublishProviderResponse;
import org.ccsds.moims.mo.common.directory.structures.ProviderSummaryList;
import org.ccsds.moims.mo.common.directory.structures.PublishDetails;
import org.ccsds.moims.mo.common.directory.structures.ServiceFilter;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.UnknownException;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.structures.FileList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;

/**
 * Consumer stub for Directory service.
 */
public class DirectoryStub {

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
    public DirectoryStub(MALConsumer consumer) {
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
     * The lookup operation allows a service consumer to query the directory service
     * to return a list of service providers that match the requested criteria.
     * If no match is found, then an empty list is returned.
     * 
     * NOTE: The various filters that may be specified as part of this operation
     * are combined using AND logic.
     * 
     * @param filter The filter field shall define the lookup query and be used to match details previously published using the publishProvider operation, the specifics of the ServiceFilter fields are defined in the following requirements.
If the serviceProviderId field is NULL then all service provider identifiers shall be matched.
If the final identifier of the domain field of the filter is the wildcard '*', then all sub-domains shall be searched for matches. See R[2] section 3.5.6.5.g.
If the wildcard is used in any other part of the domain other than the final one then an INVALID error shall be returned.
If the domain field is NULL then all domains shall be matched.
If the network field is NULL then all networks shall be matched.
If the sessionType field is NULL then all session types shall be matched.
If the sessionName field is NULL then all session names shall be matched.
The serviceKey field shall be used to match against ServiceKey fields held in the PublishDetails used to publish a specific provider.
If the serviceKey field is NULL then all areas, services and versions shall be matched.
If the area field is the wildcard '0' then all areas names shall be matched.
If the service field is the wildcard '0' then all services shall be matched.
If the version field is the wildcard '0' then all area versions shall be matched.
If the requiredCapabilitySets field is NULL or an empty list then all service capability sets shall be matched.
     * @return The return value of the interaction
     * @throws InvalidException Invalid domain filter value.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public ProviderSummaryList lookupProvider(ServiceFilter filter) throws InvalidException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DirectoryServiceInfo.LOOKUPPROVIDER_OP, filter);
            Object body0 = (Object) body.getBodyElement(0, new ProviderSummaryList());
            return (ProviderSummaryList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method lookupProvider.
     * 
     * @param filter The filter field shall define the lookup query and be used to match details previously published using the publishProvider operation, the specifics of the ServiceFilter fields are defined in the following requirements.
If the serviceProviderId field is NULL then all service provider identifiers shall be matched.
If the final identifier of the domain field of the filter is the wildcard '*', then all sub-domains shall be searched for matches. See R[2] section 3.5.6.5.g.
If the wildcard is used in any other part of the domain other than the final one then an INVALID error shall be returned.
If the domain field is NULL then all domains shall be matched.
If the network field is NULL then all networks shall be matched.
If the sessionType field is NULL then all session types shall be matched.
If the sessionName field is NULL then all session names shall be matched.
The serviceKey field shall be used to match against ServiceKey fields held in the PublishDetails used to publish a specific provider.
If the serviceKey field is NULL then all areas, services and versions shall be matched.
If the area field is the wildcard '0' then all areas names shall be matched.
If the service field is the wildcard '0' then all services shall be matched.
If the version field is the wildcard '0' then all area versions shall be matched.
If the requiredCapabilitySets field is NULL or an empty list then all service capability sets shall be matched.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncLookupProvider(ServiceFilter filter,
            DirectoryAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DirectoryServiceInfo.LOOKUPPROVIDER_OP, adapter, filter);
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
    public void continueLookupProvider(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DirectoryAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DirectoryServiceInfo.LOOKUPPROVIDER_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The publishProvider operation adds a new or updates an existing entry in
     * the list of service providers held in the directory service.
     * 
     * @param newProviderDetails The newProviderDetails field shall hold the provider details of the service to be added or updated in the directory service.
If any of the fields of the newProviderDetails domain/sessionName/network fields are either empty or contain the wildcard '*' an INVALID error shall be returned.
If the providerId field of the PublishDetails structure is empty or contains the wildcard '*' an INVALID error shall be returned.
For each contained ServiceKey structure if the area/service/version fields contain '0' then an INVALID error shall be returned.
For each contained supportedCapabilitySets list if the list is empty or contains '0' then an INVALID error shall be returned.
If the supportedLevels list is empty or the priorityLevels field is '0' for each contained AddressDetails structure found either within the ProviderDetails or the inner ServiceCapability structures then an INVALID error shall be returned.
If an error is being returned then no changes shall be made.
     * @return The return value of the interaction
     * @throws InvalidException Submitted values are invalid.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public PublishProviderResponse publishProvider(PublishDetails newProviderDetails) throws InvalidException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DirectoryServiceInfo.PUBLISHPROVIDER_OP, newProviderDetails);
            Object body0 = (Object) body.getBodyElement(0, new Union(Long.MAX_VALUE));
            Object body1 = (Object) body.getBodyElement(1, new Union(Long.MAX_VALUE));
            return new PublishProviderResponse((body0 == null) ? null : ((Union) body0).getLongValue(), (body1 == null) ? null : ((Union) body1).getLongValue());
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method publishProvider.
     * 
     * @param newProviderDetails The newProviderDetails field shall hold the provider details of the service to be added or updated in the directory service.
If any of the fields of the newProviderDetails domain/sessionName/network fields are either empty or contain the wildcard '*' an INVALID error shall be returned.
If the providerId field of the PublishDetails structure is empty or contains the wildcard '*' an INVALID error shall be returned.
For each contained ServiceKey structure if the area/service/version fields contain '0' then an INVALID error shall be returned.
For each contained supportedCapabilitySets list if the list is empty or contains '0' then an INVALID error shall be returned.
If the supportedLevels list is empty or the priorityLevels field is '0' for each contained AddressDetails structure found either within the ProviderDetails or the inner ServiceCapability structures then an INVALID error shall be returned.
If an error is being returned then no changes shall be made.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncPublishProvider(PublishDetails newProviderDetails,
            DirectoryAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DirectoryServiceInfo.PUBLISHPROVIDER_OP, adapter, newProviderDetails);
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
    public void continuePublishProvider(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DirectoryAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DirectoryServiceInfo.PUBLISHPROVIDER_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The withdrawProvider operation removes an existing entry from the list
     * of service providers held in the directory service. If no match is found
     * for the withdraw request, then nothing is changed.
     * 
     * @param providerObjId The providerObjId field shall hold the object instance identifier for the ServiceProvider COM object to remove from the directory service.
If the supplied identifier is '0' an INVALID error shall be returned.
If the supplied identifier does not match an existing ServiceProvider COM object then an UNKNOWN error shall be returned.
If an error is being returned then no changes shall be made.
The matched provider shall be removed from the directory service.
     * @throws UnknownException Provider to withdraw was not found.
     * @throws InvalidException Submitted values are invalid.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void withdrawProvider(Long providerObjId) throws UnknownException, InvalidException, MALStandardError, MALException {
        try {
            consumer.submit(DirectoryServiceInfo.WITHDRAWPROVIDER_OP, (providerObjId == null) ? null : new Union(providerObjId));
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method withdrawProvider.
     * 
     * @param providerObjId The providerObjId field shall hold the object instance identifier for the ServiceProvider COM object to remove from the directory service.
If the supplied identifier is '0' an INVALID error shall be returned.
If the supplied identifier does not match an existing ServiceProvider COM object then an UNKNOWN error shall be returned.
If an error is being returned then no changes shall be made.
The matched provider shall be removed from the directory service.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncWithdrawProvider(Long providerObjId,
            DirectoryAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(DirectoryServiceInfo.WITHDRAWPROVIDER_OP, adapter, (providerObjId == null) ? null : new Union(providerObjId));
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
    public void continueWithdrawProvider(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DirectoryAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DirectoryServiceInfo.WITHDRAWPROVIDER_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The getServiceXML operation returns the list of XML files that were submitted
     * by the service provider by the publishProvider operation.
     * If no files were supplied then this operation returns an empty list.
     * 
     * @param providerObjId The providerObjId field shall hold the COM object instance identifier for the ServiceProvider to obtain the service XML for.
If the supplied instance identifier is '0' an INVALID error shall be returned.
If the supplied identifier does not match an existing ServiceProvider COM object then an UNKNOWN error shall be returned.
     * @return The return value of the interaction
     * @throws InvalidException Submitted values are invalid.
     * @throws UnknownException Provider was not found.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public FileList getServiceXML(Long providerObjId) throws InvalidException, UnknownException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DirectoryServiceInfo.GETSERVICEXML_OP, (providerObjId == null) ? null : new Union(providerObjId));
            Object body0 = (Object) body.getBodyElement(0, new FileList());
            return (FileList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method getServiceXML.
     * 
     * @param providerObjId The providerObjId field shall hold the COM object instance identifier for the ServiceProvider to obtain the service XML for.
If the supplied instance identifier is '0' an INVALID error shall be returned.
If the supplied identifier does not match an existing ServiceProvider COM object then an UNKNOWN error shall be returned.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncGetServiceXML(Long providerObjId,
            DirectoryAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DirectoryServiceInfo.GETSERVICEXML_OP, adapter, (providerObjId == null) ? null : new Union(providerObjId));
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
    public void continueGetServiceXML(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DirectoryAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DirectoryServiceInfo.GETSERVICEXML_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

}
