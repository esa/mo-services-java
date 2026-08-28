package org.ccsds.moims.mo.mc.parameter.provider;

/**
 * Interface that providers of the Parameter service must implement to handle
 * the operations of that service.
 */
public interface ParameterHandler {

    /**
     * Implements the operation getValue.
     * 
     * @param paramInstIds The paramInstIds field shall provide the list of ParameterIdentity object instance identifiers.
The wildcard value of '0' shall be supported and matches all parameters of the provider.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a requested parameter is unknown then an UNKNOWN error shall be returned.
If a parameter is being reported periodically, using the operation shall not reset the reportInterval timer.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.UnknownException One or more of the requested parameters is unknown.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mc.parameter.structures.ParameterValueDetailsList getValue(org.ccsds.moims.mo.mal.structures.LongList paramInstIds,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation setValue.
     * 
     * @param newRawValues The submitted newRawValues shall hold a list of ParameterRawValues that contain the ParameterIdentity object instance identifier and the respective raw value to be set.
If the paramInstId field contains the wildcard value of '0' then an INVALID error shall be returned.
If a requested ParameterIdentity is unknown then an UNKNOWN error shall be returned.
If a request ParameterIdentity is not settable due to it being read only then a READONLY error shall be returned.
The rawValue shall contain the new parameter raw value to be set.
If the supplied new parameter raw value does not match the defined type for the ParameterIdentity then an INVALID error shall be returned.
If an error is raised then no modifications shall be made as a result of this operation call.
The parameter values shall be set concurrently, by this it is meant that all values are set at the same time without interleaving of other values being (ATOMIC behaviour). How this is implemented is an implementation detail.
The service provider shall create new ParameterValueInstance objects for the updated parameter values, store these in the COM Archive, and publish these new values.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.UnknownException One or more of the referenced parameters is unknown.
     * @throws org.ccsds.moims.mo.mc.ReadonlyException One or more of the parameters being set is read only.
     * @throws org.ccsds.moims.mo.com.InvalidException One of the supplied parameter values contains an invalid value.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void setValue(org.ccsds.moims.mo.mc.parameter.structures.ParameterRawValueList newRawValues,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mc.ReadonlyException, org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation enableGeneration.
     * 
     * @param isGroupIds If the isGroupIds field is TRUE then the enableInstances field shall contain GroupIdentity object instance identifiers, otherwise the field contains ParameterIdentity object instance identifiers.
The ParameterIdentity objects referenced, either directly or indirectly via groups, by the enableInstances field shall be the ParameterIdentity objects to match.
The id of the enableInstances field shall support the wildcard value of '0' and matches all ParameterIdentity objects of the provider.
The service provider shall check for the wildcard value in the list of object instance identifiers in the enableInstances field first and if found no other checks of supplied object instance identifiers shall be made.
If the enableInstances field contains a value of TRUE then reports for matching ParameterIdentity objects shall be generated, a value of FALSE requests that reports will not be generated.
No error shall be raised if the enableInstances Boolean value supplied is the same as the current generationEnabled field of the definition for a matched ParameterIdentity object i.e. enabling an already enabled parameter will not result in an error.
If a requested ParameterIdentity or GroupIdentity object is unknown then an UNKNOWN error shall be returned.
If a requested Group, or the Group objects referenced by that Group, does not contain ParameterIdentity objects then an INVALID error shall be returned.
If an error is raised then no modifications shall be made as a result of this operation call.
The provider shall create and store a new ParameterDefinition object in the COM archive if the generationEnabled field is changed.
If a new ParameterDefinition object is created then that new object shall be the current ParameterDefinition used for the specific ParameterIdentity.
     * @param enableInstances If the generation of reports is being enabled, and the parameter is defined as being periodic, then the provider shall generate a report immediately and start the report interval from that report.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.UnknownException One or more of the requested parameters or groups is unknown.
     * @throws org.ccsds.moims.mo.com.InvalidException One of the supplied groups is either not a group of groups or a group of ParameterIdentity objects.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.LongList enableGeneration(Boolean isGroupIds,
            org.ccsds.moims.mo.com.structures.InstanceBooleanPairList enableInstances,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation listDefinition.
     * 
     * @param paramNames The paramNames field shall contain a list of parameter names to retrieve the ParameterIdentity and ParameterDefinition object instance identifiers for.
The paramNames field may contain the wildcard value of '*' to return all supported ParameterIdentity and ParameterDefinition objects.
The wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.
If a provided identifier does not include a wildcard and does not match an existing ParameterIdentity object then this operation shall fail with an UNKNOWN error.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.UnknownException One of the supplied identifiers is unknown.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mc.structures.ObjectInstancePairList listDefinition(org.ccsds.moims.mo.mal.structures.IdentifierList paramNames,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation addParameter.
     * 
     * @param paramDefDetails The paramDefDetails field shall hold the name and the ParameterDefinitionDetails to be added.
The name field must not be the wildcard '*', or empty (an INVALID error shall be returned in this case).
If the supplied reportInterval value is not supported by the provider then an INVALID error shall be returned.
The supplied name must be unique among all ParameterIdentity objects for the domain of the provider otherwise a DUPLICATE error shall be raised.
If an error is raised then no new identities and definitions shall be added as a result of this operation call.
If the supplied name matches an existing, but removed, ParameterIdentity then that ParameterIdentity shall be reused otherwise a new ParameterIdentity shall be created.
The provider shall create a new ParameterDefinition object and store it, and any new ParameterIdentity objects, in the COM archive.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.com.InvalidException One of the supplied ParameterIdentity objects contains an invalid name or a supplied interval is not supported by the provider.
     * @throws org.ccsds.moims.mo.com.DuplicateException One or more of the ParameterIdentity objects being added has supplied a parameter name that is already in use in the domain.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mc.structures.ObjectInstancePairList addParameter(org.ccsds.moims.mo.mc.parameter.structures.ParameterCreationRequestList paramDefDetails,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.com.DuplicateException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation updateDefinition.
     * 
     * @param paramInstIds The paramInstIds field shall contain the object instance identifiers of the ParameterIdentity objects to be updated.
The supplied object instance identifiers shall match existing identity objects, an UNKNOWN error shall be raised if this is not the case.
If the paramInstIds list contains either NULL or '0' an INVALID error shall be raised.
     * @param paramDefDetails The paramDefDetails field shall contain the replacement ParameterDefinitionDetails.
The two lists shall be ordered the same.
The number of entries in the two lists shall be the same size otherwise an INVALID error shall be returned.
If the supplied reportInterval value is not supported by the provider then an INVALID error shall be returned.
If an error is raised then no definitions shall be updated as a result of this operation call.
The provider shall create a new ParameterDefinition object and store it in the COM archive.
The new ParameterDefinition object shall be the current ParameterDefinition used for the specific ParameterIdentity.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.com.InvalidException The supplied object instance identifiers list contains either a NULL or '0' or the two supplied lists are not the same length or a supplied interval is not supported by the provider.
If the two lists are not the same length then the extra information field shall contain the first index of the element in the largest list which does not have corresponding element in the other list.
     * @throws org.ccsds.moims.mo.mal.UnknownException One of the supplied ParameterIdentity object instance identifiers is unknown.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.LongList updateDefinition(org.ccsds.moims.mo.mal.structures.LongList paramInstIds,
            org.ccsds.moims.mo.mc.parameter.structures.ParameterDefinitionDetailsList paramDefDetails,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation removeParameter.
     * 
     * @param paramInstIds The paramInstIds field shall hold the object instance identifiers of the ParameterIdentity objects to be removed from the provider.
The list may contain the wildcard value of '0'.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a provided ParameterIdentity object instance identifier does not include a wildcard and does not match an existing parameter identity object then this operation shall fail with an UNKNOWN error.
Matched ParameterIdentity and ParameterDefinition objects shall not be removed from the COM archive only the list of ParameterIdentity and ParameterDefinition objects from the provider.
If an error is raised then no parameters shall be removed as a result of this operation call.
If the operation succeeds then the provider shall not publish parameter values for the deleted ParameterIdentity objects anymore.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.UnknownException One of the supplied ParameterIdentity object instance identifiers is unknown.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void removeParameter(org.ccsds.moims.mo.mal.structures.LongList paramInstIds,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.mc.parameter.provider.ParameterSkeleton skeleton);
}
