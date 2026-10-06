package org.ccsds.moims.mo.malprototype.iptest2.consumer;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.UnknownException;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.malprototype.iptest2.IPTest2ServiceInfo;
import org.ccsds.moims.mo.malprototype.structures.TestPublishDeregister;
import org.ccsds.moims.mo.malprototype.structures.TestPublishRegister;
import org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate;

/**
 * Consumer stub for IPTest2 service.
 */
public class IPTest2Stub {

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
    public IPTest2Stub(MALConsumer consumer) {
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
     * Register method for the monitor PubSub interaction.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws UnknownException One or more of the entities identified in the registration do not exist.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void monitorRegister(Subscription subscription,
            IPTest2Adapter adapter) throws UnknownException, MALStandardError, MALException {
        try {
            consumer.register(IPTest2ServiceInfo.MONITOR_OP, subscription, adapter);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method monitorRegister.
     * 
     * @param subscription subscription the subscription to register for
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncMonitorRegister(Subscription subscription,
            IPTest2Adapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRegister(IPTest2ServiceInfo.MONITOR_OP, subscription, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Deregister method for the monitor PubSub interaction.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @throws UnknownException One or more of the entities identified in the registration do not exist.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void monitorDeregister(IdentifierList identifierList) throws UnknownException, MALStandardError, MALException {
        try {
            consumer.deregister(IPTest2ServiceInfo.MONITOR_OP, identifierList);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method monitorDeregister.
     * 
     * @param identifierList identifierList the subscription identifiers to deregister
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncMonitorDeregister(IdentifierList identifierList,
            IPTest2Adapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncDeregister(IPTest2ServiceInfo.MONITOR_OP, identifierList, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation cleans the assertions table, publishes an update as specified
     * by the parameter TestPublishUpdate and checks the header of the Publish
     * message (see 4.1.3). Moreover if an error is expected by the TestPublishUpdate
     * then the operation hangs until a Publish error is raised or a timer ends
     * (see 4.1.10). The header of the Publish error message is checked (see 4.1.4).
     * 
     * @param input The input field.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void publishUpdates(TestPublishUpdate input) throws MALStandardError, MALException {
        try {
            consumer.submit(IPTest2ServiceInfo.PUBLISHUPDATES_OP, input);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method publishUpdates.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncPublishUpdates(TestPublishUpdate input,
            IPTest2Adapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(IPTest2ServiceInfo.PUBLISHUPDATES_OP, adapter, input);
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
    public void continuePublishUpdates(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            IPTest2Adapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(IPTest2ServiceInfo.PUBLISHUPDATES_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation cleans the assertions table, registers a publisher as specified
     * by the parameter TestPublishRegister and checks the header of the Publish
     * Register message (see 4.1.5). Moreover if no error is expected by the TestPublishRegister,
     * it checks the header of the Publish Register acknowledgement message (see
     * 4.1.6). Otherwise it checks the header of the Publish Register error message
     * (see 4.1.7).
     * 
     * @param input The input field.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void publishRegister(TestPublishRegister input) throws MALStandardError, MALException {
        try {
            consumer.submit(IPTest2ServiceInfo.PUBLISHREGISTER_OP, input);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method publishRegister.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncPublishRegister(TestPublishRegister input,
            IPTest2Adapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(IPTest2ServiceInfo.PUBLISHREGISTER_OP, adapter, input);
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
    public void continuePublishRegister(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            IPTest2Adapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(IPTest2ServiceInfo.PUBLISHREGISTER_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation cleans the assertions table, registers a publisher as specified
     * by the parameter TestPublishDeregister and checks the header of the Publish
     * Deregister message (see 4.1.5). Moreover if no error is expected by the
     * TestPublishDeregister, it checks the header of the Publish Deregister acknowledgement
     * message (see 4.1.8). Otherwise it checks the header of the Publish Deregister
     * error message (see 4.1.9).
     * 
     * @param input The input field.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void publishDeregister(TestPublishDeregister input) throws MALStandardError, MALException {
        try {
            consumer.submit(IPTest2ServiceInfo.PUBLISHDEREGISTER_OP, input);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method publishDeregister.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncPublishDeregister(TestPublishDeregister input,
            IPTest2Adapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(IPTest2ServiceInfo.PUBLISHDEREGISTER_OP, adapter, input);
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
    public void continuePublishDeregister(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            IPTest2Adapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(IPTest2ServiceInfo.PUBLISHDEREGISTER_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * 
     * @param input The input field.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void testMultipleNotify(TestPublishUpdate input) throws MALStandardError, MALException {
        try {
            consumer.submit(IPTest2ServiceInfo.TESTMULTIPLENOTIFY_OP, input);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testMultipleNotify.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestMultipleNotify(TestPublishUpdate input,
            IPTest2Adapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(IPTest2ServiceInfo.TESTMULTIPLENOTIFY_OP, adapter, input);
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
    public void continueTestMultipleNotify(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            IPTest2Adapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(IPTest2ServiceInfo.TESTMULTIPLENOTIFY_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

}
