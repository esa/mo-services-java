package org.ccsds.moims.mo.mc.alert.provider;

import org.ccsds.moims.mo.com.DuplicateException;
import org.ccsds.moims.mo.com.InvalidException;
import org.ccsds.moims.mo.com.structures.InstanceBooleanPairList;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.UnknownException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mc.alert.structures.AlertCreationRequestList;
import org.ccsds.moims.mo.mc.alert.structures.AlertDefinitionDetailsList;
import org.ccsds.moims.mo.mc.structures.ObjectInstancePairList;

/**
 * Interface that providers of the Alert service must implement to handle
 * the operations of that service.
 */
public interface AlertHandler {

    /**
     * Implements the operation enableGeneration.
     * 
     * @param isGroupIds If the isGroupIds field is TRUE then the enableInstances field shall contain GroupIdentity object instance identifiers, otherwise the field contains AlertIdentity object instance identifiers.
The AlertIdentity objects referenced, either directly or indirectly via groups, by the enableInstances field shall be the AlertIdentity objects to match.
The id of the enableInstances field shall support the wildcard value of '0' and matches all AlertIdentity objects of the provider.
The service provider shall check for the wildcard value in the list of object instance identifiers in the enableInstances field first and if found no other checks of supplied object instance identifiers shall be made.
If the enableInstances field contains a value of TRUE then instances of matching AlertIdentity objects shall be generated, a value of FALSE requests that instances will not be generated.
No error shall be raised if the enableInstances Boolean value supplied is the same as the current generationEnabled field for an alert object i.e. enabling an already enabled alert will not result in an error.
If a requested AlertIdentity or GroupIdentity object is unknown then an UNKNOWN error shall be returned.
If a requested Group, or the Group objects referenced by that Group, does not contain AlertIdentity objects then an INVALID error shall be returned.
If an error is raised then no modifications shall be made as a result of this operation call.
The provider shall create and store a new AlertDefinition object in the COM archive if the generationEnabled field is changed.
If a new AlertDefinition object is created then that new object shall be the current AlertDefinition used for the specific AlertIdentity.
     * @param enableInstances enableInstances Argument number 1 as defined by the service operation
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws InvalidException One of the supplied groups is either not a group of groups or a group of AlertIdentity objects.
     * @throws UnknownException One or more of the requested alerts or group objects is unknown.
     * @throws MALException if there is an implementation exception
     */
    LongList enableGeneration(Boolean isGroupIds,
            InstanceBooleanPairList enableInstances,
            MALInteraction interaction) throws InvalidException, UnknownException, MALException;
    /**
     * Implements the operation listDefinition.
     * 
     * @param alertNames The alertNames field shall contain a list of alert names to retrieve the AlertIdentity and AlertDefinition object instance identifiers for.
The alertNames field may contain the wildcard value of '*' to return all supported AlertIdentity and AlertDefinition objects.
The wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.
If a provided identifier does not include a wildcard and does not match an existing AlertIdentity object then this operation shall fail with an UNKNOWN error.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws UnknownException One of the supplied identifiers is unknown.
     * @throws MALException if there is an implementation exception
     */
    ObjectInstancePairList listDefinition(IdentifierList alertNames,
            MALInteraction interaction) throws UnknownException, MALException;
    /**
     * Implements the operation addAlert.
     * 
     * @param alertDefDetails The alertDefDetails field shall hold the name and the AlertDefinitionDetails to be added.
The name field must not be the wildcard '*', or empty (an INVALID error shall be returned in this case).
The supplied name must be unique among all AlertIdentity objects for the domain of the provider otherwise a DUPLICATE error shall be raised.
If an error is raised then no new identities and definitions shall be added as a result of this operation call.
If the supplied name matches an existing, but removed, AlertIdentity then that AlertIdentity shall be reused otherwise a new AlertIdentity shall be created.
The provider shall create a new AlertDefinition object and store it, and any new AlertIdentity objects, in the COM archive.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DuplicateException One or more of the AlertIdentity objects being added has supplied an alert name that is already in use in the domain.
     * @throws InvalidException One of the supplied AlertIdentity objects contains an invalid name.
     * @throws MALException if there is an implementation exception
     */
    ObjectInstancePairList addAlert(AlertCreationRequestList alertDefDetails,
            MALInteraction interaction) throws DuplicateException, InvalidException, MALException;
    /**
     * Implements the operation updateDefinition.
     * 
     * @param alertObjInstIds The alertObjInstIds field shall contain the object instance identifiers of the AlertIdentity objects to be updated.
The supplied object instance identifiers shall match existing identity objects, an UNKNOWN error shall be raised if this is not the case.
If the alertObjInstIds list contains either NULL or '0' an INVALID error shall be raised.
     * @param alertDefDetails The alertDefDetails field shall contain the replacement AlertDefinitionDetails.
The two lists shall be ordered the same.
The number of entries in the two lists shall be the same size otherwise an INVALID error shall be returned.
If an error is raised then no definitions shall be updated as a result of this operation call.
The provider shall create a new AlertDefinition object and store it in the COM archive.
The new AlertDefinition object shall be the current AlertDefinition used for the specific AlertIdentity.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws InvalidException One of the supplied AlertDefinition objects contains an invalid value or the two supplied lists are not the same length.
If the two lists are not the same length then the extra information field shall contain the first index of the element in the largest list which does not have corresponding element in the other list.
     * @throws UnknownException One of the supplied AlertIdentity object instance identifiers is unknown.
     * @throws MALException if there is an implementation exception
     */
    LongList updateDefinition(LongList alertObjInstIds,
            AlertDefinitionDetailsList alertDefDetails,
            MALInteraction interaction) throws InvalidException, UnknownException, MALException;
    /**
     * Implements the operation removeAlert.
     * 
     * @param alertInstIds The alertInstIds field shall hold the object instance identifiers of the AlertIdentity objects to be removed from the provider.
The list may contain the wildcard value of '0'.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a provided AlertIdentity object instance identifier does not include a wildcard and does not match an existing AlertIdentity object then this operation shall fail with an UNKNOWN error.
Matched AlertIdentity objects shall not be removed from the COM archive only the list of AlertIdentity objects in the provider.
If an error is raised then no alerts shall be removed as a result of this operation call.
If the operation succeeds then the provider shall not publish AlertEvent events for the deleted AlertIdentity objects anymore.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws UnknownException One of the supplied AlertIdentity object instance identifiers is unknown.
     * @throws MALException if there is an implementation exception
     */
    void removeAlert(LongList alertInstIds,
            MALInteraction interaction) throws UnknownException, MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(AlertSkeleton skeleton);
}
