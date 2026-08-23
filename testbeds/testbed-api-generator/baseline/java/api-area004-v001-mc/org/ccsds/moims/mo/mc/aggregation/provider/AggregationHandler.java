package org.ccsds.moims.mo.mc.aggregation.provider;

/**
 * Interface that providers of the Aggregation service must implement to handle
 * the operations of that service.
 */
public interface AggregationHandler {

    /**
     * Implements the operation getValue.
     * 
     * @param aggInstIds The aggInstIds field shall provide the list of AggregationIdentity object instance identifiers.
The wildcard value of '0' shall be supported and matches all aggregations of the provider.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a requested aggregation is unknown then an UNKNOWN error shall be returned.
The filter shall not be applied for the getValue operation.
If an aggregation is being reported periodically, using the operation shall not reset the reportInterval or filteredTimeout timer.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.UnknownException One or more of the requested aggregations is unknown.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mc.aggregation.structures.AggregationValueDetailsList getValue(org.ccsds.moims.mo.mal.structures.LongList aggInstIds,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation enableGeneration.
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
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.UnknownException One or more of the requested aggregations or groups is unknown.
     * @throws org.ccsds.moims.mo.com.InvalidException One of the supplied groups is either not a group of groups or a group of AggregationIdentity objects.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.LongList enableGeneration(Boolean isGroupIds,
            org.ccsds.moims.mo.com.structures.InstanceBooleanPairList enableInstances,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation enableFilter.
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
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.com.InvalidException One of the supplied groups is either not a group of groups or a group of AggregationIdentity objects.
     * @throws org.ccsds.moims.mo.mal.UnknownException One or more of the requested aggregations or groups is unknown.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void enableFilter(Boolean isGroupIds,
            org.ccsds.moims.mo.com.structures.InstanceBooleanPairList enableInstances,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation listDefinition.
     * 
     * @param aggNames The aggNames field shall contain a list of aggregation names to retrieve the AggregationIdentity and AggregationDefinition object instance identifiers for.
The aggNames field may contain the wildcard value of '*' to return all supported AggregationIdentity and AggregationDefinition objects.
The wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.
If a provided identifier does not include a wildcard and does not match an existing AggregationIdentity object then this operation shall fail with an UNKNOWN error.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.UnknownException One of the supplied identifiers is unknown.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mc.structures.ObjectInstancePairList listDefinition(org.ccsds.moims.mo.mal.structures.IdentifierList aggNames,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation addAggregation.
     * 
     * @param aggDefDetails The aggDefDetails field shall hold the name and the AggregationDefinitionDetails to be added.
The name field must not be the wildcard '*', or empty (an INVALID error shall be returned in this case).
If the supplied reportInterval or sampleInterval values are not supported by the provider then an INVALID error shall be returned.
The supplied name must be unique among all AggregationIdentity objects for the domain of the provider otherwise a DUPLICATE error shall be raised.
If an error is raised then no new identities and definitions shall be added as a result of this operation call.
If the supplied name matches an existing, but removed, AggregationIdentity then that AggregationIdentity shall be reused otherwise a new AggregationIdentity shall be created.
The provider shall create a new AggregationDefinition object and store it, and any new AggregationIdentity objects, in the COM archive.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.com.DuplicateException One or more of the aggregation objects being added has supplied an aggregation name that is already in use in the domain.
     * @throws org.ccsds.moims.mo.com.InvalidException One of the supplied aggregation objects contains an invalid name or a supplied interval is not supported by the provider.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mc.structures.ObjectInstancePairList addAggregation(org.ccsds.moims.mo.mc.aggregation.structures.AggregationCreationRequestList aggDefDetails,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.com.DuplicateException, org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation updateDefinition.
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
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.UnknownException One of the supplied AggregationIdentity object instance identifiers is unknown.
     * @throws org.ccsds.moims.mo.com.InvalidException The supplied object instance identifiers list contains either a NULL or '0' or the two supplied lists are not the same length or a supplied interval is not supported by the provider.
If the two lists are not the same length then the extra information field shall contain the first index of the element in the largest list which does not have corresponding element in the other list.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.LongList updateDefinition(org.ccsds.moims.mo.mal.structures.LongList aggInstIds,
            org.ccsds.moims.mo.mc.aggregation.structures.AggregationDefinitionDetailsList aggDefDetails,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation removeAggregation.
     * 
     * @param aggInstIds The aggInstIds field shall hold the object instance identifiers of the AggregationIdentity objects to be removed from the provider.
The list may contain the wildcard value of '0'.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a provided AggregationIdentity object instance identifier does not include a wildcard and does not match an existing aggregation then this operation shall fail with an UNKNOWN error.
Matched AggregationIdentity and AggregationDefinition objects shall not be removed from the COM archive only the list of AggregationIdentity and AggregationDefinition objects in the provider.
If an error is raised then no aggregations shall be removed as a result of this operation call.
If the operation succeeds then the provider shall not publish aggregation values for the deleted AggregationIdentity objects anymore.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.UnknownException One of the supplied AggregationIdentity object instance identifiers is unknown.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void removeAggregation(org.ccsds.moims.mo.mal.structures.LongList aggInstIds,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.mc.aggregation.provider.AggregationSkeleton skeleton);
}
