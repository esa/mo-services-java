package org.ccsds.moims.mo.mc.aggregation.consumer;

/**
 * Consumer stub for Aggregation service.
 */
public class AggregationStub {

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
    public AggregationStub(org.ccsds.moims.mo.mal.consumer.MALConsumer consumer) {
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
     * Register method for the monitorValue PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void monitorValueRegister(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.mc.aggregation.consumer.AggregationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.register(org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.MONITORVALUE_OP, subscription, adapter);
    }

    /**
     * Asynchronous version of method monitorValueRegister.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncMonitorValueRegister(org.ccsds.moims.mo.mal.structures.Subscription subscription,
            org.ccsds.moims.mo.mc.aggregation.consumer.AggregationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRegister(org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.MONITORVALUE_OP, subscription, adapter);
    }

    /**
     * Deregister method for the monitorValue PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void monitorValueDeregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.deregister(org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.MONITORVALUE_OP, identifierList);
    }

    /**
     * Asynchronous version of method monitorValueDeregister.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncMonitorValueDeregister(org.ccsds.moims.mo.mal.structures.IdentifierList identifierList,
            org.ccsds.moims.mo.mc.aggregation.consumer.AggregationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncDeregister(org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.MONITORVALUE_OP, identifierList, adapter);
    }

    /**
     * The getValue operation returns the latest received value for a requested
     * aggregation.
     * 
     * @param aggInstIds The aggInstIds field shall provide the list of AggregationIdentity object instance identifiers.
The wildcard value of '0' shall be supported and matches all aggregations of the provider.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a requested aggregation is unknown then an UNKNOWN error shall be returned.
The filter shall not be applied for the getValue operation.
If an aggregation is being reported periodically, using the operation shall not reset the reportInterval or filteredTimeout timer.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mc.aggregation.structures.AggregationValueDetailsList getValue(org.ccsds.moims.mo.mal.structures.LongList aggInstIds) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.GETVALUE_OP, aggInstIds);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mc.aggregation.structures.AggregationValueDetailsList());
        return (org.ccsds.moims.mo.mc.aggregation.structures.AggregationValueDetailsList) body0;
    }

    /**
     * Asynchronous version of method getValue.
     * 
     * @param aggInstIds The aggInstIds field shall provide the list of AggregationIdentity object instance identifiers.
The wildcard value of '0' shall be supported and matches all aggregations of the provider.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a requested aggregation is unknown then an UNKNOWN error shall be returned.
The filter shall not be applied for the getValue operation.
If an aggregation is being reported periodically, using the operation shall not reset the reportInterval or filteredTimeout timer.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncGetValue(org.ccsds.moims.mo.mal.structures.LongList aggInstIds,
            org.ccsds.moims.mo.mc.aggregation.consumer.AggregationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.GETVALUE_OP, adapter, aggInstIds);
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
    public void continueGetValue(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mc.aggregation.consumer.AggregationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.GETVALUE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The enableGeneration operation allows a consumer to control whether reports
     * for specific aggregations are generated or not. The operation allows the
     * consumer to select the aggregations directly or indirectly using groups.
     * This affects all types of aggregations, periodic, filtered and ad-hoc.
     * 
     * @param isGroupIds If the isGroupIds field is TRUE then the enableInstances field shall contain GroupIdentity object instance identifiers, otherwise the field contains AggregationIdentity object instance identifiers.
The AggregationIdentity objects referenced, either directly or indirectly via groups, by the enableInstances field shall be the AggregationIdentity objects to match.
The id of the enableInstances field shall support the wildcard value of '0' and matches all AggregationIdentity objects of the provider.
The service provider shall check for the wildcard value in the list of object instance identifiers in the enableInstances field first and if found no other checks of supplied object instance identifiers shall be made.
If the enableInstances field contains a value of TRUE then reports of matching AggregationIdentity objects shall be generated, a value of FALSE requests that reports will not be generated.
No error shall be raised if the enableInstances Boolean value supplied is the same as the current generationEnabled field of the definition for a matched AggregationIdentity object i.e. enabling an already enabled aggregation will not result in an error.
If a requested AggregationIdentity or GroupIdentity object is unknown then an UNKNOWN error shall be returned.
If a requested Group, or the Group objects referenced by that Group, does not contain AggregationIdentity objects then an INVALID error shall be returned.
If an error is raised then no modifications shall be made as a result of this operation call.
The provider shall create and store a new AggregationDefinition object in the COM archive if the generationEnabled field is changed.
If a new AggregationDefinition object is created then that new object shall be the current AggregationDefinition used for the specific AggregationIdentity.
     * @param enableInstances If the generation of reports is being enabled, and the aggregation is defined as being periodic, then the provider shall generate a report immediately and start the report interval from that report.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.structures.LongList enableGeneration(Boolean isGroupIds,
            org.ccsds.moims.mo.com.structures.InstanceBooleanPairList enableInstances) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.ENABLEGENERATION_OP, (isGroupIds == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(isGroupIds), enableInstances);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.LongList());
        return (org.ccsds.moims.mo.mal.structures.LongList) body0;
    }

    /**
     * Asynchronous version of method enableGeneration.
     * 
     * @param isGroupIds If the isGroupIds field is TRUE then the enableInstances field shall contain GroupIdentity object instance identifiers, otherwise the field contains AggregationIdentity object instance identifiers.
The AggregationIdentity objects referenced, either directly or indirectly via groups, by the enableInstances field shall be the AggregationIdentity objects to match.
The id of the enableInstances field shall support the wildcard value of '0' and matches all AggregationIdentity objects of the provider.
The service provider shall check for the wildcard value in the list of object instance identifiers in the enableInstances field first and if found no other checks of supplied object instance identifiers shall be made.
If the enableInstances field contains a value of TRUE then reports of matching AggregationIdentity objects shall be generated, a value of FALSE requests that reports will not be generated.
No error shall be raised if the enableInstances Boolean value supplied is the same as the current generationEnabled field of the definition for a matched AggregationIdentity object i.e. enabling an already enabled aggregation will not result in an error.
If a requested AggregationIdentity or GroupIdentity object is unknown then an UNKNOWN error shall be returned.
If a requested Group, or the Group objects referenced by that Group, does not contain AggregationIdentity objects then an INVALID error shall be returned.
If an error is raised then no modifications shall be made as a result of this operation call.
The provider shall create and store a new AggregationDefinition object in the COM archive if the generationEnabled field is changed.
If a new AggregationDefinition object is created then that new object shall be the current AggregationDefinition used for the specific AggregationIdentity.
     * @param enableInstances If the generation of reports is being enabled, and the aggregation is defined as being periodic, then the provider shall generate a report immediately and start the report interval from that report.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncEnableGeneration(Boolean isGroupIds,
            org.ccsds.moims.mo.com.structures.InstanceBooleanPairList enableInstances,
            org.ccsds.moims.mo.mc.aggregation.consumer.AggregationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.ENABLEGENERATION_OP, adapter, (isGroupIds == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(isGroupIds), enableInstances);
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
    public void continueEnableGeneration(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mc.aggregation.consumer.AggregationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.ENABLEGENERATION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The enableFilter operation allows a consumer to control whether reports
     * for specific aggregations are filtered or not. The operation allows the
     * consumer to select the aggregations directly or indirectly using groups.
     * This affects both periodic and ad-hoc aggregations.
     * 
     * @param isGroupIds If the isGroupIds field is TRUE then the enableInstances field shall contain GroupIdentity object instance identifiers, otherwise the field contains AggregationIdentity object instance identifiers.
The AggregationIdentity objects referenced, either directly or indirectly via groups, by the enableInstances field shall be the AggregationIdentity objects to match.
The id of the enableInstances field shall support the wildcard value of '0' and matches all AggregationIdentity objects of the provider.
The service provider shall check for the wildcard value in the list of object instance identifiers in the enableInstances field first and if found no other checks of supplied object instance identifiers shall be made.
If the enableInstances field contains a value of TRUE then reports of matching AggregationIdentity objects shall be filtered, a value of FALSE requests that reports will not be filtered.
No error shall be raised if the enableInstances Boolean value supplied is the same as the current filterEnabled field of the definition for a matched AggregationIdentity object i.e. filtering an already filtered aggregation will not result in an error.
If a requested AggregationIdentity or GroupIdentity object is unknown then an UNKNOWN error shall be returned.
If a requested Group, or the Group objects referenced by that Group, does not contain AggregationIdentity objects then an INVALID error shall be returned.
If an error is raised then no modifications shall be made as a result of this operation call.
The provider shall create and store a new AggregationDefinition object in the COM archive if the filterEnabled field is changed.
If a new AggregationDefinition object is created then that new object shall be the current AggregationDefinition used for the specific AggregationIdentity.
     * @param enableInstances enableInstances Argument number 1 as defined by the service operation
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void enableFilter(Boolean isGroupIds,
            org.ccsds.moims.mo.com.structures.InstanceBooleanPairList enableInstances) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.ENABLEFILTER_OP, (isGroupIds == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(isGroupIds), enableInstances);
    }

    /**
     * Asynchronous version of method enableFilter.
     * 
     * @param isGroupIds If the isGroupIds field is TRUE then the enableInstances field shall contain GroupIdentity object instance identifiers, otherwise the field contains AggregationIdentity object instance identifiers.
The AggregationIdentity objects referenced, either directly or indirectly via groups, by the enableInstances field shall be the AggregationIdentity objects to match.
The id of the enableInstances field shall support the wildcard value of '0' and matches all AggregationIdentity objects of the provider.
The service provider shall check for the wildcard value in the list of object instance identifiers in the enableInstances field first and if found no other checks of supplied object instance identifiers shall be made.
If the enableInstances field contains a value of TRUE then reports of matching AggregationIdentity objects shall be filtered, a value of FALSE requests that reports will not be filtered.
No error shall be raised if the enableInstances Boolean value supplied is the same as the current filterEnabled field of the definition for a matched AggregationIdentity object i.e. filtering an already filtered aggregation will not result in an error.
If a requested AggregationIdentity or GroupIdentity object is unknown then an UNKNOWN error shall be returned.
If a requested Group, or the Group objects referenced by that Group, does not contain AggregationIdentity objects then an INVALID error shall be returned.
If an error is raised then no modifications shall be made as a result of this operation call.
The provider shall create and store a new AggregationDefinition object in the COM archive if the filterEnabled field is changed.
If a new AggregationDefinition object is created then that new object shall be the current AggregationDefinition used for the specific AggregationIdentity.
     * @param enableInstances enableInstances Argument number 1 as defined by the service operation
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncEnableFilter(Boolean isGroupIds,
            org.ccsds.moims.mo.com.structures.InstanceBooleanPairList enableInstances,
            org.ccsds.moims.mo.mc.aggregation.consumer.AggregationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.ENABLEFILTER_OP, adapter, (isGroupIds == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(isGroupIds), enableInstances);
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
    public void continueEnableFilter(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mc.aggregation.consumer.AggregationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.ENABLEFILTER_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The listDefinition operation allows a consumer to request the latest object
     * instance identifiers of the AggregationIdentity and AggregationDefinition
     * objects for the supported aggregations of the provider.
     * 
     * @param aggNames The aggNames field shall contain a list of aggregation names to retrieve the AggregationIdentity and AggregationDefinition object instance identifiers for.
The aggNames field may contain the wildcard value of '*' to return all supported AggregationIdentity and AggregationDefinition objects.
The wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.
If a provided identifier does not include a wildcard and does not match an existing AggregationIdentity object then this operation shall fail with an UNKNOWN error.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mc.structures.ObjectInstancePairList listDefinition(org.ccsds.moims.mo.mal.structures.IdentifierList aggNames) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.LISTDEFINITION_OP, aggNames);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mc.structures.ObjectInstancePairList());
        return (org.ccsds.moims.mo.mc.structures.ObjectInstancePairList) body0;
    }

    /**
     * Asynchronous version of method listDefinition.
     * 
     * @param aggNames The aggNames field shall contain a list of aggregation names to retrieve the AggregationIdentity and AggregationDefinition object instance identifiers for.
The aggNames field may contain the wildcard value of '*' to return all supported AggregationIdentity and AggregationDefinition objects.
The wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.
If a provided identifier does not include a wildcard and does not match an existing AggregationIdentity object then this operation shall fail with an UNKNOWN error.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncListDefinition(org.ccsds.moims.mo.mal.structures.IdentifierList aggNames,
            org.ccsds.moims.mo.mc.aggregation.consumer.AggregationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.LISTDEFINITION_OP, adapter, aggNames);
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
    public void continueListDefinition(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mc.aggregation.consumer.AggregationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.LISTDEFINITION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The addAggregation operation allows a consumer to define one or more aggregations
     * that do not currently exist.
     * The new AggregationIdentity and AggregationDefinition objects are expected
     * to be stored in the COM archive by the provider of the aggregation service.
     * 
     * @param aggDefDetails The aggDefDetails field shall hold the name and the AggregationDefinitionDetails to be added.
The name field must not be the wildcard '*', or empty (an INVALID error shall be returned in this case).
If the supplied reportInterval or sampleInterval values are not supported by the provider then an INVALID error shall be returned.
The supplied name must be unique among all AggregationIdentity objects for the domain of the provider otherwise a DUPLICATE error shall be raised.
If an error is raised then no new identities and definitions shall be added as a result of this operation call.
If the supplied name matches an existing, but removed, AggregationIdentity then that AggregationIdentity shall be reused otherwise a new AggregationIdentity shall be created.
The provider shall create a new AggregationDefinition object and store it, and any new AggregationIdentity objects, in the COM archive.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mc.structures.ObjectInstancePairList addAggregation(org.ccsds.moims.mo.mc.aggregation.structures.AggregationCreationRequestList aggDefDetails) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.ADDAGGREGATION_OP, aggDefDetails);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mc.structures.ObjectInstancePairList());
        return (org.ccsds.moims.mo.mc.structures.ObjectInstancePairList) body0;
    }

    /**
     * Asynchronous version of method addAggregation.
     * 
     * @param aggDefDetails The aggDefDetails field shall hold the name and the AggregationDefinitionDetails to be added.
The name field must not be the wildcard '*', or empty (an INVALID error shall be returned in this case).
If the supplied reportInterval or sampleInterval values are not supported by the provider then an INVALID error shall be returned.
The supplied name must be unique among all AggregationIdentity objects for the domain of the provider otherwise a DUPLICATE error shall be raised.
If an error is raised then no new identities and definitions shall be added as a result of this operation call.
If the supplied name matches an existing, but removed, AggregationIdentity then that AggregationIdentity shall be reused otherwise a new AggregationIdentity shall be created.
The provider shall create a new AggregationDefinition object and store it, and any new AggregationIdentity objects, in the COM archive.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncAddAggregation(org.ccsds.moims.mo.mc.aggregation.structures.AggregationCreationRequestList aggDefDetails,
            org.ccsds.moims.mo.mc.aggregation.consumer.AggregationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.ADDAGGREGATION_OP, adapter, aggDefDetails);
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
    public void continueAddAggregation(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mc.aggregation.consumer.AggregationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.ADDAGGREGATION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The updateDefinition operation allows a consumer to update a definition
     * for one or more aggregations.
     * This differs from deleting an existing aggregation and adding a new definition
     * with the same aggregation name in the fact that the AggregationIdentity
     * object is not changed between the two definitions.
     * The replacement definition should be stored in the COM archive by the service
     * provider. The operation does not remove the previous object from the COM
     * archive, merely removes the object from the provider.
     * 
     * @param aggInstIds The aggInstIds field shall contain the object instance identifiers of the AggregationIdentity objects to be updated.
The supplied object instance identifiers shall match existing identity objects, an UNKNOWN error shall be raised if this is not the case.
If the aggInstIds list contains either NULL or '0' an INVALID error shall be raised.
     * @param aggDefDetails The aggDefDetails field shall contain the replacement AggregationDefinitionDetails.
The two lists shall be ordered the same.
The number of entries in the two lists shall be the same size otherwise an INVALID error shall be returned.
If the supplied reportInterval or sampleInterval values are not supported by the provider then an INVALID error shall be returned.
If an error is raised then no definitions shall be updated as a result of this operation call.
The provider shall create a new AggregationDefinition object and store it in the COM archive.
The new AggregationDefinition object shall be the current AggregationDefinition used for the specific AggregationIdentity.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.structures.LongList updateDefinition(org.ccsds.moims.mo.mal.structures.LongList aggInstIds,
            org.ccsds.moims.mo.mc.aggregation.structures.AggregationDefinitionDetailsList aggDefDetails) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.UPDATEDEFINITION_OP, aggInstIds, aggDefDetails);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.LongList());
        return (org.ccsds.moims.mo.mal.structures.LongList) body0;
    }

    /**
     * Asynchronous version of method updateDefinition.
     * 
     * @param aggInstIds The aggInstIds field shall contain the object instance identifiers of the AggregationIdentity objects to be updated.
The supplied object instance identifiers shall match existing identity objects, an UNKNOWN error shall be raised if this is not the case.
If the aggInstIds list contains either NULL or '0' an INVALID error shall be raised.
     * @param aggDefDetails The aggDefDetails field shall contain the replacement AggregationDefinitionDetails.
The two lists shall be ordered the same.
The number of entries in the two lists shall be the same size otherwise an INVALID error shall be returned.
If the supplied reportInterval or sampleInterval values are not supported by the provider then an INVALID error shall be returned.
If an error is raised then no definitions shall be updated as a result of this operation call.
The provider shall create a new AggregationDefinition object and store it in the COM archive.
The new AggregationDefinition object shall be the current AggregationDefinition used for the specific AggregationIdentity.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncUpdateDefinition(org.ccsds.moims.mo.mal.structures.LongList aggInstIds,
            org.ccsds.moims.mo.mc.aggregation.structures.AggregationDefinitionDetailsList aggDefDetails,
            org.ccsds.moims.mo.mc.aggregation.consumer.AggregationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.UPDATEDEFINITION_OP, adapter, aggInstIds, aggDefDetails);
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
    public void continueUpdateDefinition(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mc.aggregation.consumer.AggregationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.UPDATEDEFINITION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * The removeAggregation operation allows a consumer to remove one or more
     * aggregations from the list of aggregations supported by the aggregation
     * provider.
     * The operation does not remove the AggregationIdentity or AggregationDefinition
     * objects from the COM archive, merely removes the objects from the provider.
     * This permits existing AggregationValueInstance objects to continue to reference
     * the correct AggregationDefinition object in the COM archive.
     * 
     * @param aggInstIds The aggInstIds field shall hold the object instance identifiers of the AggregationIdentity objects to be removed from the provider.
The list may contain the wildcard value of '0'.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a provided AggregationIdentity object instance identifier does not include a wildcard and does not match an existing aggregation then this operation shall fail with an UNKNOWN error.
Matched AggregationIdentity and AggregationDefinition objects shall not be removed from the COM archive only the list of AggregationIdentity and AggregationDefinition objects in the provider.
If an error is raised then no aggregations shall be removed as a result of this operation call.
If the operation succeeds then the provider shall not publish aggregation values for the deleted AggregationIdentity objects anymore.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void removeAggregation(org.ccsds.moims.mo.mal.structures.LongList aggInstIds) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.REMOVEAGGREGATION_OP, aggInstIds);
    }

    /**
     * Asynchronous version of method removeAggregation.
     * 
     * @param aggInstIds The aggInstIds field shall hold the object instance identifiers of the AggregationIdentity objects to be removed from the provider.
The list may contain the wildcard value of '0'.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a provided AggregationIdentity object instance identifier does not include a wildcard and does not match an existing aggregation then this operation shall fail with an UNKNOWN error.
Matched AggregationIdentity and AggregationDefinition objects shall not be removed from the COM archive only the list of AggregationIdentity and AggregationDefinition objects in the provider.
If an error is raised then no aggregations shall be removed as a result of this operation call.
If the operation succeeds then the provider shall not publish aggregation values for the deleted AggregationIdentity objects anymore.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncRemoveAggregation(org.ccsds.moims.mo.mal.structures.LongList aggInstIds,
            org.ccsds.moims.mo.mc.aggregation.consumer.AggregationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.REMOVEAGGREGATION_OP, adapter, aggInstIds);
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
    public void continueRemoveAggregation(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.mc.aggregation.consumer.AggregationAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.mc.aggregation.AggregationServiceInfo.REMOVEAGGREGATION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

}
