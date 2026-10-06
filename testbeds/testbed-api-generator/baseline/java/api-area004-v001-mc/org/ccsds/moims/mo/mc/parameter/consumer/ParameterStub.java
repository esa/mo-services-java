package org.ccsds.moims.mo.mc.parameter.consumer;

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
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mc.ReadonlyException;
import org.ccsds.moims.mo.mc.parameter.ParameterServiceInfo;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterCreationRequestList;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterDefinitionDetailsList;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterRawValueList;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterValueDetailsList;
import org.ccsds.moims.mo.mc.structures.ObjectInstancePairList;

/**
 * Consumer stub for Parameter service.
 */
public class ParameterStub {

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
    public ParameterStub(MALConsumer consumer) {
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
     * Register method for the monitorValue PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void monitorValueRegister(Subscription subscription,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.register(ParameterServiceInfo.MONITORVALUE_OP, subscription, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method monitorValueRegister.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncMonitorValueRegister(Subscription subscription,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRegister(ParameterServiceInfo.MONITORVALUE_OP, subscription, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Deregister method for the monitorValue PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void monitorValueDeregister(IdentifierList identifierList) throws MALStandardError, MALException {
        try {
            consumer.deregister(ParameterServiceInfo.MONITORVALUE_OP, identifierList);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method monitorValueDeregister.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncMonitorValueDeregister(IdentifierList identifierList,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncDeregister(ParameterServiceInfo.MONITORVALUE_OP, identifierList, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The getValue operation returns the latest received value for a requested
     * parameter.
     * 
     * @param paramInstIds The paramInstIds field shall provide the list of ParameterIdentity object instance identifiers.
The wildcard value of '0' shall be supported and matches all parameters of the provider.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a requested parameter is unknown then an UNKNOWN error shall be returned.
If a parameter is being reported periodically, using the operation shall not reset the reportInterval timer.
     * @return The return value of the interaction
     * @throws UnknownException One or more of the requested parameters is unknown.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public ParameterValueDetailsList getValue(LongList paramInstIds) throws UnknownException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ParameterServiceInfo.GETVALUE_OP, paramInstIds);
            Object body0 = (Object) body.getBodyElement(0, new ParameterValueDetailsList());
            return (ParameterValueDetailsList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method getValue.
     * 
     * @param paramInstIds The paramInstIds field shall provide the list of ParameterIdentity object instance identifiers.
The wildcard value of '0' shall be supported and matches all parameters of the provider.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a requested parameter is unknown then an UNKNOWN error shall be returned.
If a parameter is being reported periodically, using the operation shall not reset the reportInterval timer.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncGetValue(LongList paramInstIds,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ParameterServiceInfo.GETVALUE_OP, adapter, paramInstIds);
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
    public void continueGetValue(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ParameterServiceInfo.GETVALUE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The setValue operation allows a consumer to set the raw value for one or
     * more parameters.
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
     * @throws UnknownException One or more of the referenced parameters is unknown.
     * @throws ReadonlyException One or more of the parameters being set is read only.
     * @throws InvalidException One of the supplied parameter values contains an invalid value.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void setValue(ParameterRawValueList newRawValues) throws UnknownException, ReadonlyException, InvalidException, MALStandardError, MALException {
        try {
            consumer.submit(ParameterServiceInfo.SETVALUE_OP, newRawValues);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            if (error instanceof ReadonlyException) {
                throw (ReadonlyException) error;
            }
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method setValue.
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
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncSetValue(ParameterRawValueList newRawValues,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(ParameterServiceInfo.SETVALUE_OP, adapter, newRawValues);
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
    public void continueSetValue(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ParameterServiceInfo.SETVALUE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The enableGeneration operation allows a consumer to control whether reports
     * for specific parameters are generated or not. The operation allows the
     * consumer to select the parameters directly or indirectly using groups.
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
     * @return The return value of the interaction
     * @throws UnknownException One or more of the requested parameters or groups is unknown.
     * @throws InvalidException One of the supplied groups is either not a group of groups or a group of ParameterIdentity objects.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public LongList enableGeneration(Boolean isGroupIds,
            InstanceBooleanPairList enableInstances) throws UnknownException, InvalidException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ParameterServiceInfo.ENABLEGENERATION_OP, (isGroupIds == null) ? null : new Union(isGroupIds), enableInstances);
            Object body0 = (Object) body.getBodyElement(0, new LongList());
            return (LongList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method enableGeneration.
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
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncEnableGeneration(Boolean isGroupIds,
            InstanceBooleanPairList enableInstances,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ParameterServiceInfo.ENABLEGENERATION_OP, adapter, (isGroupIds == null) ? null : new Union(isGroupIds), enableInstances);
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
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ParameterServiceInfo.ENABLEGENERATION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The listDefinition operation allows a consumer to request the latest object
     * instance identifiers of the ParameterIdentity and ParameterDefinition objects
     * for the supported parameters of the provider.
     * 
     * @param paramNames The paramNames field shall contain a list of parameter names to retrieve the ParameterIdentity and ParameterDefinition object instance identifiers for.
The paramNames field may contain the wildcard value of '*' to return all supported ParameterIdentity and ParameterDefinition objects.
The wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.
If a provided identifier does not include a wildcard and does not match an existing ParameterIdentity object then this operation shall fail with an UNKNOWN error.
     * @return The return value of the interaction
     * @throws UnknownException One of the supplied identifiers is unknown.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public ObjectInstancePairList listDefinition(IdentifierList paramNames) throws UnknownException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ParameterServiceInfo.LISTDEFINITION_OP, paramNames);
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
     * @param paramNames The paramNames field shall contain a list of parameter names to retrieve the ParameterIdentity and ParameterDefinition object instance identifiers for.
The paramNames field may contain the wildcard value of '*' to return all supported ParameterIdentity and ParameterDefinition objects.
The wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.
If a provided identifier does not include a wildcard and does not match an existing ParameterIdentity object then this operation shall fail with an UNKNOWN error.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncListDefinition(IdentifierList paramNames,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ParameterServiceInfo.LISTDEFINITION_OP, adapter, paramNames);
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
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ParameterServiceInfo.LISTDEFINITION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The addParameter operation allows a consumer to define one or more parameters
     * that do not currently exist.
     * The new ParameterIdentity and ParameterDefinition objects are expected
     * to be stored in the COM archive by the provider of the parameter service.
     * 
     * @param paramDefDetails The paramDefDetails field shall hold the name and the ParameterDefinitionDetails to be added.
The name field must not be the wildcard '*', or empty (an INVALID error shall be returned in this case).
If the supplied reportInterval value is not supported by the provider then an INVALID error shall be returned.
The supplied name must be unique among all ParameterIdentity objects for the domain of the provider otherwise a DUPLICATE error shall be raised.
If an error is raised then no new identities and definitions shall be added as a result of this operation call.
If the supplied name matches an existing, but removed, ParameterIdentity then that ParameterIdentity shall be reused otherwise a new ParameterIdentity shall be created.
The provider shall create a new ParameterDefinition object and store it, and any new ParameterIdentity objects, in the COM archive.
     * @return The return value of the interaction
     * @throws InvalidException One of the supplied ParameterIdentity objects contains an invalid name or a supplied interval is not supported by the provider.
     * @throws DuplicateException One or more of the ParameterIdentity objects being added has supplied a parameter name that is already in use in the domain.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public ObjectInstancePairList addParameter(ParameterCreationRequestList paramDefDetails) throws InvalidException, DuplicateException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ParameterServiceInfo.ADDPARAMETER_OP, paramDefDetails);
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
     * Asynchronous version of method addParameter.
     * 
     * @param paramDefDetails The paramDefDetails field shall hold the name and the ParameterDefinitionDetails to be added.
The name field must not be the wildcard '*', or empty (an INVALID error shall be returned in this case).
If the supplied reportInterval value is not supported by the provider then an INVALID error shall be returned.
The supplied name must be unique among all ParameterIdentity objects for the domain of the provider otherwise a DUPLICATE error shall be raised.
If an error is raised then no new identities and definitions shall be added as a result of this operation call.
If the supplied name matches an existing, but removed, ParameterIdentity then that ParameterIdentity shall be reused otherwise a new ParameterIdentity shall be created.
The provider shall create a new ParameterDefinition object and store it, and any new ParameterIdentity objects, in the COM archive.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncAddParameter(ParameterCreationRequestList paramDefDetails,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ParameterServiceInfo.ADDPARAMETER_OP, adapter, paramDefDetails);
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
    public void continueAddParameter(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ParameterServiceInfo.ADDPARAMETER_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The updateDefinition operation allows a consumer to update a definition
     * for one or more parameters.
     * This differs from deleting an existing parameter and adding a new definition
     * with the same parameter name in the fact that the ParameterIdentity object
     * is not changed between the two definitions.
     * The replacement definition should be stored in the COM archive by the service
     * provider. The operation does not remove the previous object from the COM
     * archive, merely removes the object from the provider.
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
     * @return The return value of the interaction
     * @throws InvalidException The supplied object instance identifiers list contains either a NULL or '0' or the two supplied lists are not the same length or a supplied interval is not supported by the provider.
If the two lists are not the same length then the extra information field shall contain the first index of the element in the largest list which does not have corresponding element in the other list.
     * @throws UnknownException One of the supplied ParameterIdentity object instance identifiers is unknown.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public LongList updateDefinition(LongList paramInstIds,
            ParameterDefinitionDetailsList paramDefDetails) throws InvalidException, UnknownException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ParameterServiceInfo.UPDATEDEFINITION_OP, paramInstIds, paramDefDetails);
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
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncUpdateDefinition(LongList paramInstIds,
            ParameterDefinitionDetailsList paramDefDetails,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ParameterServiceInfo.UPDATEDEFINITION_OP, adapter, paramInstIds, paramDefDetails);
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
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ParameterServiceInfo.UPDATEDEFINITION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The removeParameter operation allows a consumer to remove one or more parameters
     * from the list of parameters supported by the parameter provider.
     * The operation does not remove the ParameterIdentity or ParameterDefinition
     * objects from the COM archive, merely removes the objects from the provider.
     * This permits existing parameter values to continue to reference the correct
     * ParameterIdentity and ParameterDefinition objects in the COM archive.
     * 
     * @param paramInstIds The paramInstIds field shall hold the object instance identifiers of the ParameterIdentity objects to be removed from the provider.
The list may contain the wildcard value of '0'.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a provided ParameterIdentity object instance identifier does not include a wildcard and does not match an existing parameter identity object then this operation shall fail with an UNKNOWN error.
Matched ParameterIdentity and ParameterDefinition objects shall not be removed from the COM archive only the list of ParameterIdentity and ParameterDefinition objects from the provider.
If an error is raised then no parameters shall be removed as a result of this operation call.
If the operation succeeds then the provider shall not publish parameter values for the deleted ParameterIdentity objects anymore.
     * @throws UnknownException One of the supplied ParameterIdentity object instance identifiers is unknown.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void removeParameter(LongList paramInstIds) throws UnknownException, MALStandardError, MALException {
        try {
            consumer.submit(ParameterServiceInfo.REMOVEPARAMETER_OP, paramInstIds);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method removeParameter.
     * 
     * @param paramInstIds The paramInstIds field shall hold the object instance identifiers of the ParameterIdentity objects to be removed from the provider.
The list may contain the wildcard value of '0'.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a provided ParameterIdentity object instance identifier does not include a wildcard and does not match an existing parameter identity object then this operation shall fail with an UNKNOWN error.
Matched ParameterIdentity and ParameterDefinition objects shall not be removed from the COM archive only the list of ParameterIdentity and ParameterDefinition objects from the provider.
If an error is raised then no parameters shall be removed as a result of this operation call.
If the operation succeeds then the provider shall not publish parameter values for the deleted ParameterIdentity objects anymore.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncRemoveParameter(LongList paramInstIds,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(ParameterServiceInfo.REMOVEPARAMETER_OP, adapter, paramInstIds);
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
    public void continueRemoveParameter(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ParameterAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ParameterServiceInfo.REMOVEPARAMETER_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

}
