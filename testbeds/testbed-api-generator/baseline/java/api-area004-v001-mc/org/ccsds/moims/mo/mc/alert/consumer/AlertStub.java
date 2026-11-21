package org.ccsds.moims.mo.mc.alert.consumer;

import org.ccsds.moims.mo.com.DuplicateException;
import org.ccsds.moims.mo.com.InvalidException;
import org.ccsds.moims.mo.com.structures.InstanceBooleanPairList;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.UnknownException;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mc.alert.AlertServiceInfo;
import org.ccsds.moims.mo.mc.alert.structures.AlertCreationRequestList;
import org.ccsds.moims.mo.mc.alert.structures.AlertDefinitionDetailsList;
import org.ccsds.moims.mo.mc.structures.ObjectInstancePairList;

/**
 * Consumer stub for Alert service.
 */
public class AlertStub {

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
    public AlertStub(MALConsumer consumer) {
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
     * The enableGeneration operation allows a consumer to control whether updates
     * for specific alerts are generated or not. The operation allows the consumer
     * to select the alerts directly or indirectly using groups.
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
     * @return The return value of the interaction
     * @throws InvalidException One of the supplied groups is either not a group of groups or a group of AlertIdentity objects.
     * @throws UnknownException One or more of the requested alerts or group objects is unknown.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public LongList enableGeneration(Boolean isGroupIds,
            InstanceBooleanPairList enableInstances) throws InvalidException, UnknownException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(AlertServiceInfo.ENABLEGENERATION_OP, (isGroupIds == null) ? null : new Union(isGroupIds), enableInstances);
            Object body0 = (Object) body.getBodyElement(0, new LongList());
            return (LongList) body0;
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
     * Asynchronous version of method enableGeneration.
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
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncEnableGeneration(Boolean isGroupIds,
            InstanceBooleanPairList enableInstances,
            AlertAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(AlertServiceInfo.ENABLEGENERATION_OP, adapter, (isGroupIds == null) ? null : new Union(isGroupIds), enableInstances);
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
    public void continueEnableGeneration(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            AlertAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(AlertServiceInfo.ENABLEGENERATION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The listDefinition operation allows a consumer to request the latest object
     * instance identifiers of the AlertIdentity and AlertDefinition objects for
     * the supported alerts of the provider.
     * 
     * @param alertNames The alertNames field shall contain a list of alert names to retrieve the AlertIdentity and AlertDefinition object instance identifiers for.
The alertNames field may contain the wildcard value of '*' to return all supported AlertIdentity and AlertDefinition objects.
The wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.
If a provided identifier does not include a wildcard and does not match an existing AlertIdentity object then this operation shall fail with an UNKNOWN error.
     * @return The return value of the interaction
     * @throws UnknownException One of the supplied identifiers is unknown.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public ObjectInstancePairList listDefinition(IdentifierList alertNames) throws UnknownException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(AlertServiceInfo.LISTDEFINITION_OP, alertNames);
            Object body0 = (Object) body.getBodyElement(0, new ObjectInstancePairList());
            return (ObjectInstancePairList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method listDefinition.
     * 
     * @param alertNames The alertNames field shall contain a list of alert names to retrieve the AlertIdentity and AlertDefinition object instance identifiers for.
The alertNames field may contain the wildcard value of '*' to return all supported AlertIdentity and AlertDefinition objects.
The wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.
If a provided identifier does not include a wildcard and does not match an existing AlertIdentity object then this operation shall fail with an UNKNOWN error.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncListDefinition(IdentifierList alertNames,
            AlertAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(AlertServiceInfo.LISTDEFINITION_OP, adapter, alertNames);
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
    public void continueListDefinition(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            AlertAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(AlertServiceInfo.LISTDEFINITION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The addAlert operation allows a consumer to define one or more alerts that
     * do not currently exist.
     * The new AlertIdentity and AlertDefinition objects are expected to be stored
     * in the COM archive by the provider of the alert service.
     * 
     * @param alertDefDetails The alertDefDetails field shall hold the name and the AlertDefinitionDetails to be added.
The name field must not be the wildcard '*', or empty (an INVALID error shall be returned in this case).
The supplied name must be unique among all AlertIdentity objects for the domain of the provider otherwise a DUPLICATE error shall be raised.
If an error is raised then no new identities and definitions shall be added as a result of this operation call.
If the supplied name matches an existing, but removed, AlertIdentity then that AlertIdentity shall be reused otherwise a new AlertIdentity shall be created.
The provider shall create a new AlertDefinition object and store it, and any new AlertIdentity objects, in the COM archive.
     * @return The return value of the interaction
     * @throws DuplicateException One or more of the AlertIdentity objects being added has supplied an alert name that is already in use in the domain.
     * @throws InvalidException One of the supplied AlertIdentity objects contains an invalid name.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public ObjectInstancePairList addAlert(AlertCreationRequestList alertDefDetails) throws DuplicateException, InvalidException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(AlertServiceInfo.ADDALERT_OP, alertDefDetails);
            Object body0 = (Object) body.getBodyElement(0, new ObjectInstancePairList());
            return (ObjectInstancePairList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DuplicateException) {
                throw (DuplicateException) error;
            }
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method addAlert.
     * 
     * @param alertDefDetails The alertDefDetails field shall hold the name and the AlertDefinitionDetails to be added.
The name field must not be the wildcard '*', or empty (an INVALID error shall be returned in this case).
The supplied name must be unique among all AlertIdentity objects for the domain of the provider otherwise a DUPLICATE error shall be raised.
If an error is raised then no new identities and definitions shall be added as a result of this operation call.
If the supplied name matches an existing, but removed, AlertIdentity then that AlertIdentity shall be reused otherwise a new AlertIdentity shall be created.
The provider shall create a new AlertDefinition object and store it, and any new AlertIdentity objects, in the COM archive.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncAddAlert(AlertCreationRequestList alertDefDetails,
            AlertAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(AlertServiceInfo.ADDALERT_OP, adapter, alertDefDetails);
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
    public void continueAddAlert(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            AlertAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(AlertServiceInfo.ADDALERT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The updateDefinition operation allows a consumer to update a definition
     * for one or more alerts.
     * This differs from deleting an existing alert and adding a new definition
     * with the same alert name in the fact that the AlertIdentity object is not
     * changed between the two definitions.
     * The replacement definition should be stored in the COM archive by the service
     * provider. The operation does not remove the previous object from the COM
     * archive, merely removes the object from the provider.
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
     * @return The return value of the interaction
     * @throws InvalidException One of the supplied AlertDefinition objects contains an invalid value or the two supplied lists are not the same length.
If the two lists are not the same length then the extra information field shall contain the first index of the element in the largest list which does not have corresponding element in the other list.
     * @throws UnknownException One of the supplied AlertIdentity object instance identifiers is unknown.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public LongList updateDefinition(LongList alertObjInstIds,
            AlertDefinitionDetailsList alertDefDetails) throws InvalidException, UnknownException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(AlertServiceInfo.UPDATEDEFINITION_OP, alertObjInstIds, alertDefDetails);
            Object body0 = (Object) body.getBodyElement(0, new LongList());
            return (LongList) body0;
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
     * Asynchronous version of method updateDefinition.
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
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncUpdateDefinition(LongList alertObjInstIds,
            AlertDefinitionDetailsList alertDefDetails,
            AlertAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(AlertServiceInfo.UPDATEDEFINITION_OP, adapter, alertObjInstIds, alertDefDetails);
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
    public void continueUpdateDefinition(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            AlertAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(AlertServiceInfo.UPDATEDEFINITION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The removeAlert operation allows a consumer to remove one or more definitions
     * from the list of alerts supported by the alert provider.
     * The operation does not remove the AlertIdentity or AlertDefinition objects
     * from the COM archive, merely removes the objects from the provider. This
     * permits existing AlertEvent objects to continue to reference the correct
     * AlertDefinition object in the COM archive.
     * 
     * @param alertInstIds The alertInstIds field shall hold the object instance identifiers of the AlertIdentity objects to be removed from the provider.
The list may contain the wildcard value of '0'.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a provided AlertIdentity object instance identifier does not include a wildcard and does not match an existing AlertIdentity object then this operation shall fail with an UNKNOWN error.
Matched AlertIdentity objects shall not be removed from the COM archive only the list of AlertIdentity objects in the provider.
If an error is raised then no alerts shall be removed as a result of this operation call.
If the operation succeeds then the provider shall not publish AlertEvent events for the deleted AlertIdentity objects anymore.
     * @throws UnknownException One of the supplied AlertIdentity object instance identifiers is unknown.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void removeAlert(LongList alertInstIds) throws UnknownException, MALStandardError, MALException {
        try {
            consumer.submit(AlertServiceInfo.REMOVEALERT_OP, alertInstIds);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method removeAlert.
     * 
     * @param alertInstIds The alertInstIds field shall hold the object instance identifiers of the AlertIdentity objects to be removed from the provider.
The list may contain the wildcard value of '0'.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a provided AlertIdentity object instance identifier does not include a wildcard and does not match an existing AlertIdentity object then this operation shall fail with an UNKNOWN error.
Matched AlertIdentity objects shall not be removed from the COM archive only the list of AlertIdentity objects in the provider.
If an error is raised then no alerts shall be removed as a result of this operation call.
If the operation succeeds then the provider shall not publish AlertEvent events for the deleted AlertIdentity objects anymore.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncRemoveAlert(LongList alertInstIds,
            AlertAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(AlertServiceInfo.REMOVEALERT_OP, adapter, alertInstIds);
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
    public void continueRemoveAlert(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            AlertAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(AlertServiceInfo.REMOVEALERT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

}
