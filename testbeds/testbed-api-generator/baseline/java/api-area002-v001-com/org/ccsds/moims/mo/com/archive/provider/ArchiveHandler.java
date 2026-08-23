package org.ccsds.moims.mo.com.archive.provider;

/**
 * Interface that providers of the Archive service must implement to handle
 * the operations of that service.
 */
public interface ArchiveHandler {

    /**
     * Implements the operation retrieve.
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
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.com.InvalidException The request contains a wildcard value in either the object type field or the domain.
     * @throws org.ccsds.moims.mo.mal.UnknownException One or more of the requested objects specified in the operation do not exist and therefore cannot be found.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void retrieve(org.ccsds.moims.mo.com.structures.ObjectType objType,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.LongList objInstIds,
            org.ccsds.moims.mo.com.archive.provider.RetrieveInteraction interaction) throws org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation query.
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
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.com.InvalidException One or more of the query filters supplied contains an invalid value.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void query(Boolean returnBody,
            org.ccsds.moims.mo.com.structures.ObjectType objType,
            org.ccsds.moims.mo.com.archive.structures.ArchiveQueryList archiveQuery,
            org.ccsds.moims.mo.com.archive.structures.QueryFilterList queryFilter,
            org.ccsds.moims.mo.com.archive.provider.QueryInteraction interaction) throws org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation count.
     * 
     * @param objType The ObjectType, ArchiveQuery and QueryFilter parts of the request shall be populated exactly the same as for the query operation.
     * @param archiveQuery archiveQuery Argument number 1 as defined by the service operation
     * @param queryFilter queryFilter Argument number 2 as defined by the service operation
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.com.InvalidException One or more of the query filters supplied contains an invalid value.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void count(org.ccsds.moims.mo.com.structures.ObjectType objType,
            org.ccsds.moims.mo.com.archive.structures.ArchiveQueryList archiveQuery,
            org.ccsds.moims.mo.com.archive.structures.QueryFilterList queryFilter,
            org.ccsds.moims.mo.com.archive.provider.CountInteraction interaction) throws org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation store.
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
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.com.DuplicateException One or more of the objects being stored has supplied an object instance identifier that is already in use in the archive.
     * @throws org.ccsds.moims.mo.com.InvalidException One or more of the objects being stored contains an invalid value.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.LongList store(Boolean returnObjInstIds,
            org.ccsds.moims.mo.com.structures.ObjectType objType,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList objDetails,
            org.ccsds.moims.mo.mal.structures.HeterogeneousList objBodies,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.com.DuplicateException, org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation update.
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
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.UnknownException One or more of the requested objects specified in the operation do not exist and therefore cannot be found.
     * @throws org.ccsds.moims.mo.com.InvalidException One or more of the objects being updated contains a wildcard value in the object identifier fields.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void update(org.ccsds.moims.mo.com.structures.ObjectType objType,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList objDetails,
            org.ccsds.moims.mo.mal.structures.HeterogeneousList objBodies,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation delete.
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
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.UnknownException One or more of the requested objects specified in the operation do not exist and therefore cannot be found.
     * @throws org.ccsds.moims.mo.com.InvalidException The supplied object type or domain contains a wildcard value.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.LongList delete(org.ccsds.moims.mo.com.structures.ObjectType objType,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.LongList objInstIds,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.com.archive.provider.ArchiveSkeleton skeleton);
}
