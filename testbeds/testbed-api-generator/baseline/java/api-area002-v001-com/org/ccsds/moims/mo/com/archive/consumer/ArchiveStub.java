package org.ccsds.moims.mo.com.archive.consumer;

/**
 * Consumer stub for Archive service.
 */
public class ArchiveStub {

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
    public ArchiveStub(org.ccsds.moims.mo.mal.consumer.MALConsumer consumer) {
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
     * Retrieves a set of objects identified by their object instance identifier.
     * 
     * @param objType The first part of the request shall contain the type of the object required.
If any of the fields of the object type contains the wildcard value of '0' then an INVALID error shall be returned.
The second part of the request shall contain the domain to match.
If the domain contains the wildcard value of '*' then an INVALID error shall be returned.
The third part of the request shall contain the list of object instance identifiers to match.
If the object instance identifier list contains the wildcard value '0' then all object instances shall be matched.
If any explicitly requested object cannot be matched then an UNKNOWN error shall be returned.
     * @param domain domain Argument number 1 as defined by the service operation
     * @param objInstIds objInstIds Argument number 2 as defined by the service operation
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void retrieve(org.ccsds.moims.mo.com.structures.ObjectType objType,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.LongList objInstIds,
            org.ccsds.moims.mo.com.archive.consumer.ArchiveAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.invoke(org.ccsds.moims.mo.com.archive.ArchiveServiceInfo.RETRIEVE_OP, adapter, objType, domain, objInstIds);
    }

    /**
     * Asynchronous version of method retrieve.
     * 
     * @param objType The first part of the request shall contain the type of the object required.
If any of the fields of the object type contains the wildcard value of '0' then an INVALID error shall be returned.
The second part of the request shall contain the domain to match.
If the domain contains the wildcard value of '*' then an INVALID error shall be returned.
The third part of the request shall contain the list of object instance identifiers to match.
If the object instance identifier list contains the wildcard value '0' then all object instances shall be matched.
If any explicitly requested object cannot be matched then an UNKNOWN error shall be returned.
     * @param domain domain Argument number 1 as defined by the service operation
     * @param objInstIds objInstIds Argument number 2 as defined by the service operation
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncRetrieve(org.ccsds.moims.mo.com.structures.ObjectType objType,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.LongList objInstIds,
            org.ccsds.moims.mo.com.archive.consumer.ArchiveAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncInvoke(org.ccsds.moims.mo.com.archive.ArchiveServiceInfo.RETRIEVE_OP, adapter, objType, domain, objInstIds);
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
    public void continueRetrieve(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.com.archive.consumer.ArchiveAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.com.archive.ArchiveServiceInfo.RETRIEVE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * Retrieves a set of object instance identifiers, and optionally the object
     * bodies, from a list of supplied queries. The PROGRESS interaction pattern
     * is used as the returned set of data may be quite large and this allows
     * it to be split over several MAL messages.
     * 
     * @param returnBody The first part of the request shall contain a Boolean that if set to TRUE requests that the body of the objects is returned otherwise only the ObjectType and ArchiveDetails shall be returned and the returned list of the bodies of the objects shall be replaced with a NULL.
The second part of the request shall contain the type of the object required.
Each part of the object type may contain the wildcard value of '0'.
The third and fourth parts of the request shall contain the queries to evaluate.
A single query shall be formed by the combination of an ArchiveQuery from the first list and a QueryFilter from the second list.
The two lists shall be ordered identically so that the query and the filter parts can be matched together.
If a query does not contain a QueryFilter part then that entry in the QueryFilter list shall be replaced with a NULL value.
If the request does not contain any QueryFilters then the complete list may be replaced with a NULL.
The size of the two lists must be the same unless the complete second list is replaced with a NULL otherwise an INVALID error shall be raised.
For each query, the ArchiveQuery and the QueryFilter shall contain the COM object fields to filter on.
The ArchiveQuery may contain the wildcard value of NULL on each of the fields.
If an ArchiveQuery contains an end time but no start time then it shall match the single object that has a timestamp closest to, but not greater than, the end time field.
The end time field may specify a time in the future.
If the sortFieldName of the ArchiveQuery does not reference a defined field then an INVALID error shall be returned.
Each query shall be evaluated separately from each other, the filter of one query will not affect the filter of another. This forms a logical OR operation.
If the QueryFilter contains an error then an INVALID error shall be returned. The definition of erroneous values are filter specific and defined in the relevant filter structure specification.
     * @param objType objType Argument number 1 as defined by the service operation
     * @param archiveQuery archiveQuery Argument number 2 as defined by the service operation
     * @param queryFilter queryFilter Argument number 3 as defined by the service operation
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void query(Boolean returnBody,
            org.ccsds.moims.mo.com.structures.ObjectType objType,
            org.ccsds.moims.mo.com.archive.structures.ArchiveQueryList archiveQuery,
            org.ccsds.moims.mo.com.archive.structures.QueryFilterList queryFilter,
            org.ccsds.moims.mo.com.archive.consumer.ArchiveAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.progress(org.ccsds.moims.mo.com.archive.ArchiveServiceInfo.QUERY_OP, adapter, (returnBody == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(returnBody), objType, archiveQuery, queryFilter);
    }

    /**
     * Asynchronous version of method query.
     * 
     * @param returnBody The first part of the request shall contain a Boolean that if set to TRUE requests that the body of the objects is returned otherwise only the ObjectType and ArchiveDetails shall be returned and the returned list of the bodies of the objects shall be replaced with a NULL.
The second part of the request shall contain the type of the object required.
Each part of the object type may contain the wildcard value of '0'.
The third and fourth parts of the request shall contain the queries to evaluate.
A single query shall be formed by the combination of an ArchiveQuery from the first list and a QueryFilter from the second list.
The two lists shall be ordered identically so that the query and the filter parts can be matched together.
If a query does not contain a QueryFilter part then that entry in the QueryFilter list shall be replaced with a NULL value.
If the request does not contain any QueryFilters then the complete list may be replaced with a NULL.
The size of the two lists must be the same unless the complete second list is replaced with a NULL otherwise an INVALID error shall be raised.
For each query, the ArchiveQuery and the QueryFilter shall contain the COM object fields to filter on.
The ArchiveQuery may contain the wildcard value of NULL on each of the fields.
If an ArchiveQuery contains an end time but no start time then it shall match the single object that has a timestamp closest to, but not greater than, the end time field.
The end time field may specify a time in the future.
If the sortFieldName of the ArchiveQuery does not reference a defined field then an INVALID error shall be returned.
Each query shall be evaluated separately from each other, the filter of one query will not affect the filter of another. This forms a logical OR operation.
If the QueryFilter contains an error then an INVALID error shall be returned. The definition of erroneous values are filter specific and defined in the relevant filter structure specification.
     * @param objType objType Argument number 1 as defined by the service operation
     * @param archiveQuery archiveQuery Argument number 2 as defined by the service operation
     * @param queryFilter queryFilter Argument number 3 as defined by the service operation
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncQuery(Boolean returnBody,
            org.ccsds.moims.mo.com.structures.ObjectType objType,
            org.ccsds.moims.mo.com.archive.structures.ArchiveQueryList archiveQuery,
            org.ccsds.moims.mo.com.archive.structures.QueryFilterList queryFilter,
            org.ccsds.moims.mo.com.archive.consumer.ArchiveAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncProgress(org.ccsds.moims.mo.com.archive.ArchiveServiceInfo.QUERY_OP, adapter, (returnBody == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(returnBody), objType, archiveQuery, queryFilter);
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
    public void continueQuery(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.com.archive.consumer.ArchiveAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.com.archive.ArchiveServiceInfo.QUERY_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * Counts the set of objects based on a supplied query.
     * 
     * @param objType The ObjectType, ArchiveQuery and QueryFilter parts of the request shall be populated exactly the same as for the query operation.
     * @param archiveQuery archiveQuery Argument number 1 as defined by the service operation
     * @param queryFilter queryFilter Argument number 2 as defined by the service operation
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void count(org.ccsds.moims.mo.com.structures.ObjectType objType,
            org.ccsds.moims.mo.com.archive.structures.ArchiveQueryList archiveQuery,
            org.ccsds.moims.mo.com.archive.structures.QueryFilterList queryFilter,
            org.ccsds.moims.mo.com.archive.consumer.ArchiveAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.invoke(org.ccsds.moims.mo.com.archive.ArchiveServiceInfo.COUNT_OP, adapter, objType, archiveQuery, queryFilter);
    }

    /**
     * Asynchronous version of method count.
     * 
     * @param objType The ObjectType, ArchiveQuery and QueryFilter parts of the request shall be populated exactly the same as for the query operation.
     * @param archiveQuery archiveQuery Argument number 1 as defined by the service operation
     * @param queryFilter queryFilter Argument number 2 as defined by the service operation
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncCount(org.ccsds.moims.mo.com.structures.ObjectType objType,
            org.ccsds.moims.mo.com.archive.structures.ArchiveQueryList archiveQuery,
            org.ccsds.moims.mo.com.archive.structures.QueryFilterList queryFilter,
            org.ccsds.moims.mo.com.archive.consumer.ArchiveAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncInvoke(org.ccsds.moims.mo.com.archive.ArchiveServiceInfo.COUNT_OP, adapter, objType, archiveQuery, queryFilter);
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
    public void continueCount(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.com.archive.consumer.ArchiveAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.com.archive.ArchiveServiceInfo.COUNT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * Stores new objects in the archive and causes an ObjectStored event to be
     * published by the archive.
     * When new objects are being stored in an archive by a service provider the
     * archive service provider is capable of allocating an unused object instance
     * identifier for the objects being stored. The returned object instance identifier
     * should be used by the service provider for identifying the object instances
     * to its consumer to ensure that only a single object instance identifier
     * is used for each object instance.
     * 
     * @param returnObjInstIds The first part of the request indicates whether the operation should return the object instance identifiers used, if TRUE it shall return them, otherwise it returns NULL.
The second part of the request shall contain the type of object being stored.
The third part of the request shall contain the domain of the objects being stored.
The fourth part of the request shall contain the list of archive details to use, one for each object being stored.
If the object instance identifier supplied in the archive details is set to 0 then the store operation shall allocate a new and unused object instance identifier.
If the object instance identifier supplied in the archive details is not set to 0 and is currently used in the archive then a DUPLICATE error is returned and no objects from the request shall be stored.
The fifth part of the request shall contain the list of objects to store.
The fourth and fifth list must be the same size as there is only one entry in each for each object to be stored. If they differ in size an INVALID error is returned with the extra error information integer giving the index of the list entry without a matching entry in the other list.
An INVALID error shall be returned if a wildcard value of '0' is used in the object type.
An INVALID error shall be returned if a wildcard value of '*' is used in the domain identifier list.
An INVALID error shall be returned if the values of '0', '*', or NULL are used in the network, timestamp or provider fields of the archive details except for the object instance identifier.
The type of the body of the object should be checked against the declared type in the relevant service specification, if different an INVALID error is raised.
If any error is returned then the store operation shall be rolled back and nothing is stored as a result of the operation.
     * @param objType objType Argument number 1 as defined by the service operation
     * @param domain domain Argument number 2 as defined by the service operation
     * @param objDetails objDetails Argument number 3 as defined by the service operation
     * @param objBodies objBodies Argument number 4 as defined by the service operation
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.structures.LongList store(Boolean returnObjInstIds,
            org.ccsds.moims.mo.com.structures.ObjectType objType,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList objDetails,
            org.ccsds.moims.mo.mal.structures.HeterogeneousList objBodies) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.com.archive.ArchiveServiceInfo.STORE_OP, (returnObjInstIds == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(returnObjInstIds), objType, domain, objDetails, objBodies);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.LongList());
        return (org.ccsds.moims.mo.mal.structures.LongList) body0;
    }

    /**
     * Asynchronous version of method store.
     * 
     * @param returnObjInstIds The first part of the request indicates whether the operation should return the object instance identifiers used, if TRUE it shall return them, otherwise it returns NULL.
The second part of the request shall contain the type of object being stored.
The third part of the request shall contain the domain of the objects being stored.
The fourth part of the request shall contain the list of archive details to use, one for each object being stored.
If the object instance identifier supplied in the archive details is set to 0 then the store operation shall allocate a new and unused object instance identifier.
If the object instance identifier supplied in the archive details is not set to 0 and is currently used in the archive then a DUPLICATE error is returned and no objects from the request shall be stored.
The fifth part of the request shall contain the list of objects to store.
The fourth and fifth list must be the same size as there is only one entry in each for each object to be stored. If they differ in size an INVALID error is returned with the extra error information integer giving the index of the list entry without a matching entry in the other list.
An INVALID error shall be returned if a wildcard value of '0' is used in the object type.
An INVALID error shall be returned if a wildcard value of '*' is used in the domain identifier list.
An INVALID error shall be returned if the values of '0', '*', or NULL are used in the network, timestamp or provider fields of the archive details except for the object instance identifier.
The type of the body of the object should be checked against the declared type in the relevant service specification, if different an INVALID error is raised.
If any error is returned then the store operation shall be rolled back and nothing is stored as a result of the operation.
     * @param objType objType Argument number 1 as defined by the service operation
     * @param domain domain Argument number 2 as defined by the service operation
     * @param objDetails objDetails Argument number 3 as defined by the service operation
     * @param objBodies objBodies Argument number 4 as defined by the service operation
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncStore(Boolean returnObjInstIds,
            org.ccsds.moims.mo.com.structures.ObjectType objType,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList objDetails,
            org.ccsds.moims.mo.mal.structures.HeterogeneousList objBodies,
            org.ccsds.moims.mo.com.archive.consumer.ArchiveAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.com.archive.ArchiveServiceInfo.STORE_OP, adapter, (returnObjInstIds == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(returnObjInstIds), objType, domain, objDetails, objBodies);
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
    public void continueStore(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.com.archive.consumer.ArchiveAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.com.archive.ArchiveServiceInfo.STORE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * Updates an object (or set of objects), causes an ObjectUpdated event to
     * be published by the archive.
     * 
     * @param objType The first part of the request shall contain the type of object being updated.
The second part of the request shall contain the domain of the objects being updated.
The third part of the request shall contain the list of ArchiveDetails.
The object instance identifier contained in the ArchiveDetails, combined with the object type and domain from the request, shall be used to match objects.
If requested object cannot be matched then an UNKNOWN error shall be returned and nothing will be updated.
The remainder of the ArchiveDetails shall be used to update the matched objects.
The fourth part of the request shall contain the list of objects to replace the matched objects with.
No wildcard values shall be accepted in the object type, the domain, and the object instance identifier, an INVALID error is returned in this case and no objects are updated.
     * @param domain domain Argument number 1 as defined by the service operation
     * @param objDetails objDetails Argument number 2 as defined by the service operation
     * @param objBodies objBodies Argument number 3 as defined by the service operation
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public void update(org.ccsds.moims.mo.com.structures.ObjectType objType,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList objDetails,
            org.ccsds.moims.mo.mal.structures.HeterogeneousList objBodies) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.submit(org.ccsds.moims.mo.com.archive.ArchiveServiceInfo.UPDATE_OP, objType, domain, objDetails, objBodies);
    }

    /**
     * Asynchronous version of method update.
     * 
     * @param objType The first part of the request shall contain the type of object being updated.
The second part of the request shall contain the domain of the objects being updated.
The third part of the request shall contain the list of ArchiveDetails.
The object instance identifier contained in the ArchiveDetails, combined with the object type and domain from the request, shall be used to match objects.
If requested object cannot be matched then an UNKNOWN error shall be returned and nothing will be updated.
The remainder of the ArchiveDetails shall be used to update the matched objects.
The fourth part of the request shall contain the list of objects to replace the matched objects with.
No wildcard values shall be accepted in the object type, the domain, and the object instance identifier, an INVALID error is returned in this case and no objects are updated.
     * @param domain domain Argument number 1 as defined by the service operation
     * @param objDetails objDetails Argument number 2 as defined by the service operation
     * @param objBodies objBodies Argument number 3 as defined by the service operation
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncUpdate(org.ccsds.moims.mo.com.structures.ObjectType objType,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList objDetails,
            org.ccsds.moims.mo.mal.structures.HeterogeneousList objBodies,
            org.ccsds.moims.mo.com.archive.consumer.ArchiveAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncSubmit(org.ccsds.moims.mo.com.archive.ArchiveServiceInfo.UPDATE_OP, adapter, objType, domain, objDetails, objBodies);
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
    public void continueUpdate(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.com.archive.consumer.ArchiveAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.com.archive.ArchiveServiceInfo.UPDATE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

    /**
     * Deletes an object (or set of objects) and causes an ObjectDeleted event
     * to be published by the archive.
     * 
     * @param objType The first part of the request shall contain the type of object to match and is not permitted to contain the wildcard value.
The second part of the request shall contain the domain of the objects to match and is not permitted to contain the wildcard value.
If either the first or second part contain a wildcard value then an INVALID error shall be returned and no object deleted.
The third part of the request shall contain the list of object instance identifiers to match.
If the object instance identifier list contains the wildcard value '0' then all object instances shall be matched.
If any explicitly requested object cannot be matched then an UNKNOWN error shall be returned and nothing will be deleted.
The matched objects shall be deleted from the archive.
     * @param domain domain Argument number 1 as defined by the service operation
     * @param objInstIds objInstIds Argument number 2 as defined by the service operation
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.structures.LongList delete(org.ccsds.moims.mo.com.structures.ObjectType objType,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.LongList objInstIds) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.com.archive.ArchiveServiceInfo.DELETE_OP, objType, domain, objInstIds);
        Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.LongList());
        return (org.ccsds.moims.mo.mal.structures.LongList) body0;
    }

    /**
     * Asynchronous version of method delete.
     * 
     * @param objType The first part of the request shall contain the type of object to match and is not permitted to contain the wildcard value.
The second part of the request shall contain the domain of the objects to match and is not permitted to contain the wildcard value.
If either the first or second part contain a wildcard value then an INVALID error shall be returned and no object deleted.
The third part of the request shall contain the list of object instance identifiers to match.
If the object instance identifier list contains the wildcard value '0' then all object instances shall be matched.
If any explicitly requested object cannot be matched then an UNKNOWN error shall be returned and nothing will be deleted.
The matched objects shall be deleted from the archive.
     * @param domain domain Argument number 1 as defined by the service operation
     * @param objInstIds objInstIds Argument number 2 as defined by the service operation
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncDelete(org.ccsds.moims.mo.com.structures.ObjectType objType,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.LongList objInstIds,
            org.ccsds.moims.mo.com.archive.consumer.ArchiveAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return consumer.asyncRequest(org.ccsds.moims.mo.com.archive.ArchiveServiceInfo.DELETE_OP, adapter, objType, domain, objInstIds);
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
    public void continueDelete(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.com.archive.consumer.ArchiveAdapter adapter) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        consumer.continueInteraction(org.ccsds.moims.mo.com.archive.ArchiveServiceInfo.DELETE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
    }

}
