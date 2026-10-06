package org.ccsds.moims.mo.mc.action.consumer;

import org.ccsds.moims.mo.com.DuplicateException;
import org.ccsds.moims.mo.com.InvalidException;
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
import org.ccsds.moims.mo.mc.action.ActionServiceInfo;
import org.ccsds.moims.mo.mc.action.structures.ActionCreationRequestList;
import org.ccsds.moims.mo.mc.action.structures.ActionDefinitionDetailsList;
import org.ccsds.moims.mo.mc.action.structures.ActionInstanceDetails;
import org.ccsds.moims.mo.mc.structures.ObjectInstancePairList;

/**
 * Consumer stub for Action service.
 */
public class ActionStub {

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
    public ActionStub(MALConsumer consumer) {
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
     * The submitAction operation allows a consumer to submit an action to a provider
     * for remote execution.
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
     * @throws InvalidException The list sizes held in the ActionInstanceDetails do not match the argument definitions or it contains one or more invalid argument values.
If the two lists are not the same length then the extra information field shall contain the first index of the element in the largest list which does not have corresponding element in the other list.
     * @throws UnknownException Submitted action definition is unknown.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void submitAction(Long actionInstId,
            ActionInstanceDetails actionDetails) throws InvalidException, UnknownException, MALStandardError, MALException {
        try {
            consumer.submit(ActionServiceInfo.SUBMITACTION_OP, (actionInstId == null) ? null : new Union(actionInstId), actionDetails);
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
     * Asynchronous version of method submitAction.
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
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncSubmitAction(Long actionInstId,
            ActionInstanceDetails actionDetails,
            ActionAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(ActionServiceInfo.SUBMITACTION_OP, adapter, (actionInstId == null) ? null : new Union(actionInstId), actionDetails);
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
    public void continueSubmitAction(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ActionAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ActionServiceInfo.SUBMITACTION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The preCheckAction operation allows a consumer to check that an action
     * would be successfully accepted for execution without actually submitting
     * the action. The operation is expected to be provided by local action proxies
     * rather than the remote system to allow for quick local checks before sending
     * the action over long and slow space links.
     * 
     * @param actionDetails The actionDetails part of the submission shall contain the argument values and related information of the action instance to be executed.
If the ActionInstanceDetails structure contains an argumentIds field value then this shall be compared to the same field in the matched ActionDefinition object and must be the same size and contain the same values, an INVALID error shall be returned if this is not the case.
If the supplied argument values do not match the attribute type specified in the action definition then an INVALID error shall be returned.
A service provider may apply some deployment specific checks to the action instance and can return an INVALID error if they fail.
     * @return The return value of the interaction
     * @throws InvalidException The argument list contains one or more invalid arguments.
     * @throws UnknownException Submitted action definition is unknown.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Boolean preCheckAction(ActionInstanceDetails actionDetails) throws InvalidException, UnknownException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ActionServiceInfo.PRECHECKACTION_OP, actionDetails);
            Object body0 = (Object) body.getBodyElement(0, new Union(Boolean.FALSE));
            return (body0 == null) ? null : ((Union) body0).getBooleanValue();
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
     * Asynchronous version of method preCheckAction.
     * 
     * @param actionDetails The actionDetails part of the submission shall contain the argument values and related information of the action instance to be executed.
If the ActionInstanceDetails structure contains an argumentIds field value then this shall be compared to the same field in the matched ActionDefinition object and must be the same size and contain the same values, an INVALID error shall be returned if this is not the case.
If the supplied argument values do not match the attribute type specified in the action definition then an INVALID error shall be returned.
A service provider may apply some deployment specific checks to the action instance and can return an INVALID error if they fail.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncPreCheckAction(ActionInstanceDetails actionDetails,
            ActionAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ActionServiceInfo.PRECHECKACTION_OP, adapter, actionDetails);
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
    public void continuePreCheckAction(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ActionAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ActionServiceInfo.PRECHECKACTION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The listDefinition operation allows a consumer to request the object instance
     * identifiers of the latest ActionIdentity and ActionDefinition objects for
     * the supported actions of the provider.
     * 
     * @param actionNames The actionNames field shall contain a list of action names to retrieve the ActionIdentity and ActionDefinition object instance identifiers for.
The request may contain the wildcard value of '*' to return all supported ActionIdentity and ActionDefinition objects.
The wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.
If a provided identifier does not include a wildcard and does not match an existing ActionIdentity object then this operation shall fail with an UNKNOWN error.
     * @return The return value of the interaction
     * @throws UnknownException One of the supplied identifiers is unknown.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public ObjectInstancePairList listDefinition(IdentifierList actionNames) throws UnknownException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ActionServiceInfo.LISTDEFINITION_OP, actionNames);
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
     * @param actionNames The actionNames field shall contain a list of action names to retrieve the ActionIdentity and ActionDefinition object instance identifiers for.
The request may contain the wildcard value of '*' to return all supported ActionIdentity and ActionDefinition objects.
The wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.
If a provided identifier does not include a wildcard and does not match an existing ActionIdentity object then this operation shall fail with an UNKNOWN error.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncListDefinition(IdentifierList actionNames,
            ActionAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ActionServiceInfo.LISTDEFINITION_OP, adapter, actionNames);
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
            ActionAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ActionServiceInfo.LISTDEFINITION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The addAction operation allows a consumer to define one or more actions
     * that do not currently exist. The new ActionIdentity and ActionDefinition
     * objects are expected to be stored in the COM archive by the provider of
     * the action service.
     * 
     * @param actionDefDetails The actionDefDetails field shall hold the name and definitions to be added.
The name field must not be the wildcard '*', or empty (an INVALID error shall be returned in this case).
The supplied name must be unique among all ActionIdentity objects for the domain of the provider otherwise a DUPLICATE error shall be raised.
If an error is raised then no new identities and definitions shall be added as a result of this operation call.
If the supplied name matches an existing, but removed, ActionIdentity then that ActionIdentity shall be reused otherwise a new ActionIdentity shall be created.
The provider shall create a new ActionDefinition object and store it, and any new ActionIdentity objects, in the COM archive.
     * @return The return value of the interaction
     * @throws InvalidException One of the supplied ActionIdentity objects contains an invalid action name.
     * @throws DuplicateException One or more of the ActionIdentity objects being added has supplied an action name that is already in use in the domain.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public ObjectInstancePairList addAction(ActionCreationRequestList actionDefDetails) throws InvalidException, DuplicateException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ActionServiceInfo.ADDACTION_OP, actionDefDetails);
            Object body0 = (Object) body.getBodyElement(0, new ObjectInstancePairList());
            return (ObjectInstancePairList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof DuplicateException) {
                throw (DuplicateException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method addAction.
     * 
     * @param actionDefDetails The actionDefDetails field shall hold the name and definitions to be added.
The name field must not be the wildcard '*', or empty (an INVALID error shall be returned in this case).
The supplied name must be unique among all ActionIdentity objects for the domain of the provider otherwise a DUPLICATE error shall be raised.
If an error is raised then no new identities and definitions shall be added as a result of this operation call.
If the supplied name matches an existing, but removed, ActionIdentity then that ActionIdentity shall be reused otherwise a new ActionIdentity shall be created.
The provider shall create a new ActionDefinition object and store it, and any new ActionIdentity objects, in the COM archive.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncAddAction(ActionCreationRequestList actionDefDetails,
            ActionAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ActionServiceInfo.ADDACTION_OP, adapter, actionDefDetails);
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
    public void continueAddAction(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ActionAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ActionServiceInfo.ADDACTION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The updateDefinition operation allows a consumer to update a definition
     * for one or more actions.
     * This differs from deleting an existing action and adding a new definition
     * with the same name in the fact that the ActionIdentity object is not changed
     * between the two definitions.
     * The replacement definition should be stored in the COM archive by the service
     * provider. The operation does not remove the previous ActionDefinition object
     * from the COM archive, merely removes the object from the provider. This
     * permits existing, and completed, ActionInstance objects to continue to
     * reference the correct ActionIdentity and ActionDefinition objects in the
     * COM archive.
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
     * @return The return value of the interaction
     * @throws InvalidException The supplied object instance identifiers list contains either a NULL or '0' or the two supplied lists are not the same length.
If the two lists are not the same length then the extra information field shall contain the first index of the element in the largest list which does not have corresponding element in the other list.
     * @throws UnknownException One of the supplied ActionIdentity object instance identifiers is unknown.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public LongList updateDefinition(LongList actionObjInstIds,
            ActionDefinitionDetailsList actionDefDetails) throws InvalidException, UnknownException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ActionServiceInfo.UPDATEDEFINITION_OP, actionObjInstIds, actionDefDetails);
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
     * @param actionObjInstIds The actionObjInstIds field shall contain the list of object instance identifiers of the ActionIdentity objects to be updated.
The supplied object instance identifiers shall match existing identity objects, an UNKNOWN error shall be raised if this is not the case.
If the actionObjInstIds list contains either NULL or '0' an INVALID error shall be raised.
     * @param actionDefDetails The actionDefDetails field shall contain the replacement ActionDefinitionDetails.
The two lists shall be ordered the same.
The number of entries in the two lists shall be the same size otherwise an INVALID error shall be returned.
If an error is raised then no definitions shall be modified as a result of this operation call.
The provider shall create a new ActionDefinition object and store it in the COM archive.
The new ActionDefinition object shall be the current ActionDefinition used for the specific ActionIdentity.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncUpdateDefinition(LongList actionObjInstIds,
            ActionDefinitionDetailsList actionDefDetails,
            ActionAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ActionServiceInfo.UPDATEDEFINITION_OP, adapter, actionObjInstIds, actionDefDetails);
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
            ActionAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ActionServiceInfo.UPDATEDEFINITION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The removeAction operation allows a consumer to remove one or more actions
     * from the list of actions supported by the action provider.
     * The operation does not remove the ActionIdentity or ActionDefinition object
     * from the COM archive, merely removes the objects from the provider. This
     * permits existing, and completed, ActionInstance objects to continue to
     * reference the correct ActionIdentity and ActionDefinition objects in the
     * COM archive.
     * 
     * @param actionInstIds The actionInstIds field shall hold the object instance identifiers of the ActionIdentity objects to be removed from the provider.
The wildcard value of '0' in the list of object instance identifiers shall be supported and matches all actions of the provider.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a provided ActionIdentity object instance identifier does not include a wildcard and does not match an existing ActionIdentity object then this operation shall fail with an UNKNOWN error.
If a matched definition is still being used by an executing action instance then this operation shall not fail because of this reason.
Matched ActionIdentity objects shall not be removed from the COM archive only the list of ActionIdentity objects in the provider.
Removed ActionIdentity object shall not be allowed to be referenced by new action instances.
If an error is raised then no actions shall be removed as a result of this operation call.
     * @throws UnknownException One of the supplied ActionIdentity object instance identifiers is unknown.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void removeAction(LongList actionInstIds) throws UnknownException, MALStandardError, MALException {
        try {
            consumer.submit(ActionServiceInfo.REMOVEACTION_OP, actionInstIds);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method removeAction.
     * 
     * @param actionInstIds The actionInstIds field shall hold the object instance identifiers of the ActionIdentity objects to be removed from the provider.
The wildcard value of '0' in the list of object instance identifiers shall be supported and matches all actions of the provider.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a provided ActionIdentity object instance identifier does not include a wildcard and does not match an existing ActionIdentity object then this operation shall fail with an UNKNOWN error.
If a matched definition is still being used by an executing action instance then this operation shall not fail because of this reason.
Matched ActionIdentity objects shall not be removed from the COM archive only the list of ActionIdentity objects in the provider.
Removed ActionIdentity object shall not be allowed to be referenced by new action instances.
If an error is raised then no actions shall be removed as a result of this operation call.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncRemoveAction(LongList actionInstIds,
            ActionAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(ActionServiceInfo.REMOVEACTION_OP, adapter, actionInstIds);
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
    public void continueRemoveAction(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ActionAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ActionServiceInfo.REMOVEACTION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

}
