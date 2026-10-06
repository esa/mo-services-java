package org.ccsds.moims.mo.mps.planinformationmanagement.consumer;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.structures.AttributeTypeList;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mps.InvalidException;
import org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo;
import org.ccsds.moims.mo.mps.structures.ActivityDefinitionList;
import org.ccsds.moims.mo.mps.structures.EventDefinitionList;
import org.ccsds.moims.mo.mps.structures.RequestDefinitionList;
import org.ccsds.moims.mo.mps.structures.ResourceList;

/**
 * Consumer stub for PlanInformationManagement service.
 */
public class PlanInformationManagementStub {

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
    public PlanInformationManagementStub(MALConsumer consumer) {
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
     * The listRequestDefs operation is used to obtain a list of available RequestDefinitions
     * together with their descriptions.  The list can be filtered by domain or
     * restricted to specified definition IDs.  All available versions are listed.
     * The domain field is an ordered list of identifiers representing a domain
     * hierarchy, any node of which can use ‘*’ as a wildcard (meaning any domain
     * identifier at that level of the hierarchy).  If a set of domains is required
     * that cannot be represented through the use of wildcards, then the operation
     * will need to be repeated using different domain filters.
     * 
     * @param domain The domain field.
     * @param requestDefs The requestDefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void listRequestDefs(IdentifierList domain,
            ObjectRefList requestDefs,
            PlanInformationManagementAdapter adapter) throws InvalidException, MALStandardError, MALException {
        try {
            consumer.progress(PlanInformationManagementServiceInfo.LISTREQUESTDEFS_OP, adapter, domain, requestDefs);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method listRequestDefs.
     * 
     * @param domain The domain field.
     * @param requestDefs The requestDefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncListRequestDefs(IdentifierList domain,
            ObjectRefList requestDefs,
            PlanInformationManagementAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncProgress(PlanInformationManagementServiceInfo.LISTREQUESTDEFS_OP, adapter, domain, requestDefs);
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
    public void continueListRequestDefs(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanInformationManagementAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanInformationManagementServiceInfo.LISTREQUESTDEFS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The getRequestDefs operation is used to retrieve one or more available
     * RequestDefinitions, whose identity is known to the consumer.
     * 
     * @param requestDefs The requestDefs field.
     * @return The return value of the interaction
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public RequestDefinitionList getRequestDefs(ObjectRefList requestDefs) throws InvalidException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(PlanInformationManagementServiceInfo.GETREQUESTDEFS_OP, requestDefs);
            Object body0 = (Object) body.getBodyElement(0, new RequestDefinitionList());
            return (RequestDefinitionList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method getRequestDefs.
     * 
     * @param requestDefs The requestDefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncGetRequestDefs(ObjectRefList requestDefs,
            PlanInformationManagementAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(PlanInformationManagementServiceInfo.GETREQUESTDEFS_OP, adapter, requestDefs);
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
    public void continueGetRequestDefs(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanInformationManagementAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanInformationManagementServiceInfo.GETREQUESTDEFS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The listEventDefs operation is used to obtain a list of available EventDefinitions
     * together with their descriptions.  The list can be filtered by domain or
     * restricted to specified definition IDs.  All available versions are listed.
     * The domain field is an ordered list of identifiers representing a domain
     * hierarchy, any node of which can use ‘*’ as a wildcard (meaning any domain
     * identifier at that level of the hierarchy).  If a set of domains is required
     * that cannot be represented through the use of wildcards, then the operation
     * will need to be repeated using different domain filters.
     * 
     * @param domain The domain field.
     * @param eventDefs The eventDefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void listEventDefs(IdentifierList domain,
            ObjectRefList eventDefs,
            PlanInformationManagementAdapter adapter) throws InvalidException, MALStandardError, MALException {
        try {
            consumer.progress(PlanInformationManagementServiceInfo.LISTEVENTDEFS_OP, adapter, domain, eventDefs);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method listEventDefs.
     * 
     * @param domain The domain field.
     * @param eventDefs The eventDefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncListEventDefs(IdentifierList domain,
            ObjectRefList eventDefs,
            PlanInformationManagementAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncProgress(PlanInformationManagementServiceInfo.LISTEVENTDEFS_OP, adapter, domain, eventDefs);
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
    public void continueListEventDefs(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanInformationManagementAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanInformationManagementServiceInfo.LISTEVENTDEFS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The getEventDefs operation is used to retrieve one or more available EventDefinitions,
     * whose identity is known to the consumer.
     * 
     * @param eventDefs The eventDefs field.
     * @return The return value of the interaction
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public EventDefinitionList getEventDefs(ObjectRefList eventDefs) throws InvalidException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(PlanInformationManagementServiceInfo.GETEVENTDEFS_OP, eventDefs);
            Object body0 = (Object) body.getBodyElement(0, new EventDefinitionList());
            return (EventDefinitionList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method getEventDefs.
     * 
     * @param eventDefs The eventDefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncGetEventDefs(ObjectRefList eventDefs,
            PlanInformationManagementAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(PlanInformationManagementServiceInfo.GETEVENTDEFS_OP, adapter, eventDefs);
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
    public void continueGetEventDefs(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanInformationManagementAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanInformationManagementServiceInfo.GETEVENTDEFS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The listActivityDefs operation is used to obtain a list of available ActivityDefinitions
     * together with their descriptions.  The list can be filtered by domain or
     * restricted to specified definition IDs.  All available versions are listed.
     * The domain field is an ordered list of identifiers representing a domain
     * hierarchy, any node of which can use ‘*’ as a wildcard (meaning any domain
     * identifier at that level of the hierarchy).  If a set of domains is required
     * that cannot be represented through the use of wildcards, then the operation
     * will need to be repeated using different domain filters.
     * 
     * @param domain The domain field.
     * @param activityDefs The activityDefs field.
     * @param defaultTags The defaultTags field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void listActivityDefs(IdentifierList domain,
            ObjectRefList activityDefs,
            StringList defaultTags,
            PlanInformationManagementAdapter adapter) throws InvalidException, MALStandardError, MALException {
        try {
            consumer.progress(PlanInformationManagementServiceInfo.LISTACTIVITYDEFS_OP, adapter, domain, activityDefs, defaultTags);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method listActivityDefs.
     * 
     * @param domain The domain field.
     * @param activityDefs The activityDefs field.
     * @param defaultTags The defaultTags field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncListActivityDefs(IdentifierList domain,
            ObjectRefList activityDefs,
            StringList defaultTags,
            PlanInformationManagementAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncProgress(PlanInformationManagementServiceInfo.LISTACTIVITYDEFS_OP, adapter, domain, activityDefs, defaultTags);
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
    public void continueListActivityDefs(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanInformationManagementAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanInformationManagementServiceInfo.LISTACTIVITYDEFS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The getActivityDefs operation is used to retrieve one or more available
     * ActivityDefinitions, whose identity is known to the consumer.
     * 
     * @param activityDefs The activityDefs field.
     * @return The return value of the interaction
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public ActivityDefinitionList getActivityDefs(ObjectRefList activityDefs) throws InvalidException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(PlanInformationManagementServiceInfo.GETACTIVITYDEFS_OP, activityDefs);
            Object body0 = (Object) body.getBodyElement(0, new ActivityDefinitionList());
            return (ActivityDefinitionList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method getActivityDefs.
     * 
     * @param activityDefs The activityDefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncGetActivityDefs(ObjectRefList activityDefs,
            PlanInformationManagementAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(PlanInformationManagementServiceInfo.GETACTIVITYDEFS_OP, adapter, activityDefs);
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
    public void continueGetActivityDefs(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanInformationManagementAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanInformationManagementServiceInfo.GETACTIVITYDEFS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The listResourceDefs operation is used to obtain a list of available Resources
     * together with their descriptions.  The list can be filtered by domain or
     * restricted to data types.  All available versions are listed. The domain
     * field is an ordered list of identifiers representing a domain hierarchy,
     * any node of which can use ‘*’ as a wildcard (meaning any domain identifier
     * at that level of the hierarchy).  If a set of domains is required that
     * cannot be represented through the use of wildcards, then the operation
     * will need to be repeated using different domain filters.
     * 
     * @param domain The domain field.
     * @param dataType The dataType field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void listResourceDefs(IdentifierList domain,
            AttributeTypeList dataType,
            PlanInformationManagementAdapter adapter) throws InvalidException, MALStandardError, MALException {
        try {
            consumer.progress(PlanInformationManagementServiceInfo.LISTRESOURCEDEFS_OP, adapter, domain, dataType);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method listResourceDefs.
     * 
     * @param domain The domain field.
     * @param dataType The dataType field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncListResourceDefs(IdentifierList domain,
            AttributeTypeList dataType,
            PlanInformationManagementAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncProgress(PlanInformationManagementServiceInfo.LISTRESOURCEDEFS_OP, adapter, domain, dataType);
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
    public void continueListResourceDefs(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanInformationManagementAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanInformationManagementServiceInfo.LISTRESOURCEDEFS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The getResourceDefs operation is used to retrieve the definition of one
     * or more available Resources, whose identity is known to the consumer. It
     * should be noted that this operation is designed to retrieve the resource
     * definition and not the current value of the resource (the value field may
     * contain a default value for the resource).
     * 
     * @param resources The resources field.
     * @return The return value of the interaction
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public ResourceList getResourceDefs(ObjectRefList resources) throws InvalidException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(PlanInformationManagementServiceInfo.GETRESOURCEDEFS_OP, resources);
            Object body0 = (Object) body.getBodyElement(0, new ResourceList());
            return (ResourceList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method getResourceDefs.
     * 
     * @param resources The resources field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncGetResourceDefs(ObjectRefList resources,
            PlanInformationManagementAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(PlanInformationManagementServiceInfo.GETRESOURCEDEFS_OP, adapter, resources);
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
    public void continueGetResourceDefs(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            PlanInformationManagementAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(PlanInformationManagementServiceInfo.GETRESOURCEDEFS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

}
