package org.ccsds.moims.mo.mc.action.provider;

/**
 * Interface that providers of the Action service must implement to handle
 * the operations of that service.
 */
public interface ActionHandler {

    /**
     * Implements the operation submitAction.
     * 
     * @param actionInstId The actionInstId field of the submission shall contain the object instance identifier of the ActionInstance to be used for activity tracking events.
     * @param actionDetails The actionDetails part of the submission shall contain the argument values and related information of the action instance to be executed.
If the defInstId of the supplied actionDetails field does not match a known ActionDefinition object then an UNKNOWN error shall be returned.
The size of the argumentValues list of the ActionInstanceDetails structure shall be compared to the size of the argument list in the matched ActionDefinition object and an INVALID error shall be returned if they are not the same.
If the ActionInstanceDetails structure contains an argumentIds field value then this shall be compared to the same field in the matched ActionDefinition object and must be the same size and contain the same values, an INVALID error shall be returned if this is not the case.
If the ActionInstanceDetails structure contains an isRawValue field value then the size of this list shall be compared to the size of the argument list in the matched ActionDefinition object and an INVALID error shall be returned if they are not the same.
If the supplied argument values do not match the attribute type specified in the action definition then an INVALID error shall be returned.
A service provider may apply some deployment specific checks to the action instance and can return an INVALID error if they fail.
If an error is raised then no action shall be executed.
The SUBMIT acknowledgement shall be returned once the action has been accepted for execution but before execution starts.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.com.InvalidException The list sizes held in the ActionInstanceDetails do not match the argument definitions or it contains one or more invalid argument values.
If the two lists are not the same length then the extra information field shall contain the first index of the element in the largest list which does not have corresponding element in the other list.
     * @throws org.ccsds.moims.mo.mal.UnknownException Submitted action definition is unknown.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void submitAction(Long actionInstId,
            org.ccsds.moims.mo.mc.action.structures.ActionInstanceDetails actionDetails,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation preCheckAction.
     * 
     * @param actionDetails The actionDetails part of the submission shall contain the argument values and related information of the action instance to be executed.
If the ActionInstanceDetails structure contains an argumentIds field value then this shall be compared to the same field in the matched ActionDefinition object and must be the same size and contain the same values, an INVALID error shall be returned if this is not the case.
If the supplied argument values do not match the attribute type specified in the action definition then an INVALID error shall be returned.
A service provider may apply some deployment specific checks to the action instance and can return an INVALID error if they fail.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.com.InvalidException The argument list contains one or more invalid arguments.
     * @throws org.ccsds.moims.mo.mal.UnknownException Submitted action definition is unknown.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    Boolean preCheckAction(org.ccsds.moims.mo.mc.action.structures.ActionInstanceDetails actionDetails,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation listDefinition.
     * 
     * @param actionNames The actionNames field shall contain a list of action names to retrieve the ActionIdentity and ActionDefinition object instance identifiers for.
The request may contain the wildcard value of '*' to return all supported ActionIdentity and ActionDefinition objects.
The wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.
If a provided identifier does not include a wildcard and does not match an existing ActionIdentity object then this operation shall fail with an UNKNOWN error.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.UnknownException One of the supplied identifiers is unknown.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mc.structures.ObjectInstancePairList listDefinition(org.ccsds.moims.mo.mal.structures.IdentifierList actionNames,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation addAction.
     * 
     * @param actionDefDetails The actionDefDetails field shall hold the name and definitions to be added.
The name field must not be the wildcard '*', or empty (an INVALID error shall be returned in this case).
The supplied name must be unique among all ActionIdentity objects for the domain of the provider otherwise a DUPLICATE error shall be raised.
If an error is raised then no new identities and definitions shall be added as a result of this operation call.
If the supplied name matches an existing, but removed, ActionIdentity then that ActionIdentity shall be reused otherwise a new ActionIdentity shall be created.
The provider shall create a new ActionDefinition object and store it, and any new ActionIdentity objects, in the COM archive.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.com.InvalidException One of the supplied ActionIdentity objects contains an invalid action name.
     * @throws org.ccsds.moims.mo.com.DuplicateException One or more of the ActionIdentity objects being added has supplied an action name that is already in use in the domain.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mc.structures.ObjectInstancePairList addAction(org.ccsds.moims.mo.mc.action.structures.ActionCreationRequestList actionDefDetails,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.com.DuplicateException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation updateDefinition.
     * 
     * @param actionObjInstIds The actionObjInstIds field shall contain the list of object instance identifiers of the ActionIdentity objects to be updated.
The supplied object instance identifiers shall match existing identity objects, an UNKNOWN error shall be raised if this is not the case.
If the actionObjInstIds list contains either NULL or '0' an INVALID error shall be raised.
     * @param actionDefDetails The actionDefDetails field shall contain the replacement ActionDefinitionDetails.
The two lists shall be ordered the same.
The number of entries in the two lists shall be the same size otherwise an INVALID error shall be returned.
If an error is raised then no definitions shall be modified as a result of this operation call.
The provider shall create a new ActionDefinition object and store it in the COM archive.
The new ActionDefinition object shall be the current ActionDefinition used for the specific ActionIdentity.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.com.InvalidException The supplied object instance identifiers list contains either a NULL or '0' or the two supplied lists are not the same length.
If the two lists are not the same length then the extra information field shall contain the first index of the element in the largest list which does not have corresponding element in the other list.
     * @throws org.ccsds.moims.mo.mal.UnknownException One of the supplied ActionIdentity object instance identifiers is unknown.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.LongList updateDefinition(org.ccsds.moims.mo.mal.structures.LongList actionObjInstIds,
            org.ccsds.moims.mo.mc.action.structures.ActionDefinitionDetailsList actionDefDetails,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation removeAction.
     * 
     * @param actionInstIds The actionInstIds field shall hold the object instance identifiers of the ActionIdentity objects to be removed from the provider.
The wildcard value of '0' in the list of object instance identifiers shall be supported and matches all actions of the provider.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a provided ActionIdentity object instance identifier does not include a wildcard and does not match an existing ActionIdentity object then this operation shall fail with an UNKNOWN error.
If a matched definition is still being used by an executing action instance then this operation shall not fail because of this reason.
Matched ActionIdentity objects shall not be removed from the COM archive only the list of ActionIdentity objects in the provider.
Removed ActionIdentity object shall not be allowed to be referenced by new action instances.
If an error is raised then no actions shall be removed as a result of this operation call.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.UnknownException One of the supplied ActionIdentity object instance identifiers is unknown.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void removeAction(org.ccsds.moims.mo.mal.structures.LongList actionInstIds,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.mc.action.provider.ActionSkeleton skeleton);
}
