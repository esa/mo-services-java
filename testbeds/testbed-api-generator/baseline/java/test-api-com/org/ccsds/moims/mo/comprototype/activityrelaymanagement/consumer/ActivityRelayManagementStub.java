package org.ccsds.moims.mo.comprototype.activityrelaymanagement.consumer;

import org.ccsds.moims.mo.comprototype.activityrelaymanagement.ActivityRelayManagementServiceInfo;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * Consumer stub for ActivityRelayManagement service.
 */
public class ActivityRelayManagementStub {

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
    public ActivityRelayManagementStub(MALConsumer consumer) {
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
     * Resets all values back to their default value.
     * 
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void resetTest() throws MALStandardError, MALException {
        try {
            consumer.submit(ActivityRelayManagementServiceInfo.RESETTEST_OP, (Object[]) null);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method resetTest.
     * 
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncResetTest(ActivityRelayManagementAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(ActivityRelayManagementServiceInfo.RESETTEST_OP, adapter, (Object[]) null);
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
    public void continueResetTest(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ActivityRelayManagementAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ActivityRelayManagementServiceInfo.RESETTEST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Create a new relay node that supports the Activity and ActivityTest services.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void createRelay(String in1,
            String in2) throws MALStandardError, MALException {
        try {
            consumer.submit(ActivityRelayManagementServiceInfo.CREATERELAY_OP, (in1 == null) ? null : new Union(in1), (in2 == null) ? null : new Union(in2));
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method createRelay.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncCreateRelay(String in1,
            String in2,
            ActivityRelayManagementAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(ActivityRelayManagementServiceInfo.CREATERELAY_OP, adapter, (in1 == null) ? null : new Union(in1), (in2 == null) ? null : new Union(in2));
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
    public void continueCreateRelay(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ActivityRelayManagementAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ActivityRelayManagementServiceInfo.CREATERELAY_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

}
