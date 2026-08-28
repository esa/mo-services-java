package org.ccsds.moims.mo.mps.planinformationmanagement.consumer;

/**
 * Consumer stub for PlanInformationManagement service.
 */
public class PlanInformationManagementStub {

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
    public PlanInformationManagementStub(org.ccsds.moims.mo.mal.consumer.MALConsumer consumer) {
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
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void listRequestDefs(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.ObjectRefList requestDefs,
            org.ccsds.moims.mo.mps.planinformationmanagement.consumer.PlanInformationManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.progress(org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo.LISTREQUESTDEFS_OP, adapter, domain, requestDefs);
    }

    /**
     * Asynchronous version of method listRequestDefs.
     * 
     * @param domain The domain field.
     * @param requestDefs The requestDefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncListRequestDefs(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.ObjectRefList requestDefs,
            org.ccsds.moims.mo.mps.planinformationmanagement.consumer.PlanInformationManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncProgress(org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo.LISTREQUESTDEFS_OP, adapter, domain, requestDefs);
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
    public void continueListRequestDefs(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planinformationmanagement.consumer.PlanInformationManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo.LISTREQUESTDEFS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The getRequestDefs operation is used to retrieve one or more available
     * RequestDefinitions, whose identity is known to the consumer.
     * 
     * @param requestDefs The requestDefs field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mps.structures.RequestDefinitionList getRequestDefs(org.ccsds.moims.mo.mal.structures.ObjectRefList requestDefs) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo.GETREQUESTDEFS_OP, requestDefs);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.RequestDefinitionList());
        return (org.ccsds.moims.mo.mps.structures.RequestDefinitionList) body0;
    }

    /**
     * Asynchronous version of method getRequestDefs.
     * 
     * @param requestDefs The requestDefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncGetRequestDefs(org.ccsds.moims.mo.mal.structures.ObjectRefList requestDefs,
            org.ccsds.moims.mo.mps.planinformationmanagement.consumer.PlanInformationManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo.GETREQUESTDEFS_OP, adapter, requestDefs);
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
    public void continueGetRequestDefs(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planinformationmanagement.consumer.PlanInformationManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo.GETREQUESTDEFS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
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
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void listEventDefs(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.ObjectRefList eventDefs,
            org.ccsds.moims.mo.mps.planinformationmanagement.consumer.PlanInformationManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.progress(org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo.LISTEVENTDEFS_OP, adapter, domain, eventDefs);
    }

    /**
     * Asynchronous version of method listEventDefs.
     * 
     * @param domain The domain field.
     * @param eventDefs The eventDefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncListEventDefs(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.ObjectRefList eventDefs,
            org.ccsds.moims.mo.mps.planinformationmanagement.consumer.PlanInformationManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncProgress(org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo.LISTEVENTDEFS_OP, adapter, domain, eventDefs);
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
    public void continueListEventDefs(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planinformationmanagement.consumer.PlanInformationManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo.LISTEVENTDEFS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The getEventDefs operation is used to retrieve one or more available EventDefinitions,
     * whose identity is known to the consumer.
     * 
     * @param eventDefs The eventDefs field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mps.structures.EventDefinitionList getEventDefs(org.ccsds.moims.mo.mal.structures.ObjectRefList eventDefs) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo.GETEVENTDEFS_OP, eventDefs);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.EventDefinitionList());
        return (org.ccsds.moims.mo.mps.structures.EventDefinitionList) body0;
    }

    /**
     * Asynchronous version of method getEventDefs.
     * 
     * @param eventDefs The eventDefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncGetEventDefs(org.ccsds.moims.mo.mal.structures.ObjectRefList eventDefs,
            org.ccsds.moims.mo.mps.planinformationmanagement.consumer.PlanInformationManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo.GETEVENTDEFS_OP, adapter, eventDefs);
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
    public void continueGetEventDefs(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planinformationmanagement.consumer.PlanInformationManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo.GETEVENTDEFS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
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
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void listActivityDefs(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.ObjectRefList activityDefs,
            org.ccsds.moims.mo.mal.structures.StringList defaultTags,
            org.ccsds.moims.mo.mps.planinformationmanagement.consumer.PlanInformationManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.progress(org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo.LISTACTIVITYDEFS_OP, adapter, domain, activityDefs, defaultTags);
    }

    /**
     * Asynchronous version of method listActivityDefs.
     * 
     * @param domain The domain field.
     * @param activityDefs The activityDefs field.
     * @param defaultTags The defaultTags field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncListActivityDefs(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.ObjectRefList activityDefs,
            org.ccsds.moims.mo.mal.structures.StringList defaultTags,
            org.ccsds.moims.mo.mps.planinformationmanagement.consumer.PlanInformationManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncProgress(org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo.LISTACTIVITYDEFS_OP, adapter, domain, activityDefs, defaultTags);
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
    public void continueListActivityDefs(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planinformationmanagement.consumer.PlanInformationManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo.LISTACTIVITYDEFS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The getActivityDefs operation is used to retrieve one or more available
     * ActivityDefinitions, whose identity is known to the consumer.
     * 
     * @param activityDefs The activityDefs field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mps.structures.ActivityDefinitionList getActivityDefs(org.ccsds.moims.mo.mal.structures.ObjectRefList activityDefs) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo.GETACTIVITYDEFS_OP, activityDefs);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.ActivityDefinitionList());
        return (org.ccsds.moims.mo.mps.structures.ActivityDefinitionList) body0;
    }

    /**
     * Asynchronous version of method getActivityDefs.
     * 
     * @param activityDefs The activityDefs field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncGetActivityDefs(org.ccsds.moims.mo.mal.structures.ObjectRefList activityDefs,
            org.ccsds.moims.mo.mps.planinformationmanagement.consumer.PlanInformationManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo.GETACTIVITYDEFS_OP, adapter, activityDefs);
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
    public void continueGetActivityDefs(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planinformationmanagement.consumer.PlanInformationManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo.GETACTIVITYDEFS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
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
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void listResourceDefs(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.AttributeTypeList dataType,
            org.ccsds.moims.mo.mps.planinformationmanagement.consumer.PlanInformationManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.progress(org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo.LISTRESOURCEDEFS_OP, adapter, domain, dataType);
    }

    /**
     * Asynchronous version of method listResourceDefs.
     * 
     * @param domain The domain field.
     * @param dataType The dataType field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncListResourceDefs(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.AttributeTypeList dataType,
            org.ccsds.moims.mo.mps.planinformationmanagement.consumer.PlanInformationManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncProgress(org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo.LISTRESOURCEDEFS_OP, adapter, domain, dataType);
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
    public void continueListResourceDefs(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planinformationmanagement.consumer.PlanInformationManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo.LISTRESOURCEDEFS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
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
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mps.structures.ResourceList getResourceDefs(org.ccsds.moims.mo.mal.structures.ObjectRefList resources) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo.GETRESOURCEDEFS_OP, resources);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mps.structures.ResourceList());
        return (org.ccsds.moims.mo.mps.structures.ResourceList) body0;
    }

    /**
     * Asynchronous version of method getResourceDefs.
     * 
     * @param resources The resources field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncGetResourceDefs(org.ccsds.moims.mo.mal.structures.ObjectRefList resources,
            org.ccsds.moims.mo.mps.planinformationmanagement.consumer.PlanInformationManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo.GETRESOURCEDEFS_OP, adapter, resources);
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
    public void continueGetResourceDefs(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mps.planinformationmanagement.consumer.PlanInformationManagementAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mps.planinformationmanagement.PlanInformationManagementServiceInfo.GETRESOURCEDEFS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

}
