package org.ccsds.moims.mo.comprototype.eventtest.consumer;

import org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo;
import org.ccsds.moims.mo.comprototype.eventtest.structures.BasicEnum;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.ShortList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;

/**
 * Consumer stub for EventTest service.
 */
public class EventTestStub {

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
    public EventTestStub(MALConsumer consumer) {
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
     * Resets the EventTest service provider.
     * 
     * @param in1 The in1 field.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void resetTest(String in1) throws MALStandardError, MALException {
        try {
            consumer.submit(EventTestServiceInfo.RESETTEST_OP, (in1 == null) ? null : new Union(in1));
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method resetTest.
     * 
     * @param in1 The in1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncResetTest(String in1,
            EventTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(EventTestServiceInfo.RESETTEST_OP, adapter, (in1 == null) ? null : new Union(in1));
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
            EventTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(EventTestServiceInfo.RESETTEST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Creates an instance of one of the test objects: TestObject A or Test Object
     * B Arg 1 - ObjectNumber (identifies object to be created) Arg 2 Domain Arg
     * 3 Description Arg 4 parent instanceIdentifier returns object instance identifier.
     * The provider will publish a TestObjectCreation event reporting the deletion.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @return The return value of the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Long createinstance(Short in1,
            String in2,
            String in3,
            Long in4) throws MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(EventTestServiceInfo.CREATEINSTANCE_OP, (in1 == null) ? null : new Union(in1), (in2 == null) ? null : new Union(in2), (in3 == null) ? null : new Union(in3), (in4 == null) ? null : new Union(in4));
            Object body0 = (Object) body.getBodyElement(0, new Union(Long.MAX_VALUE));
            return (body0 == null) ? null : ((Union) body0).getLongValue();
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method createinstance.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncCreateinstance(Short in1,
            String in2,
            String in3,
            Long in4,
            EventTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(EventTestServiceInfo.CREATEINSTANCE_OP, adapter, (in1 == null) ? null : new Union(in1), (in2 == null) ? null : new Union(in2), (in3 == null) ? null : new Union(in3), (in4 == null) ? null : new Union(in4));
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
    public void continueCreateinstance(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            EventTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(EventTestServiceInfo.CREATEINSTANCE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * deletes a test object instance.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void deleteInstance(Short in1,
            String in2,
            Long in3) throws MALStandardError, MALException {
        try {
            consumer.submit(EventTestServiceInfo.DELETEINSTANCE_OP, (in1 == null) ? null : new Union(in1), (in2 == null) ? null : new Union(in2), (in3 == null) ? null : new Union(in3));
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method deleteInstance.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncDeleteInstance(Short in1,
            String in2,
            Long in3,
            EventTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(EventTestServiceInfo.DELETEINSTANCE_OP, adapter, (in1 == null) ? null : new Union(in1), (in2 == null) ? null : new Union(in2), (in3 == null) ? null : new Union(in3));
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
    public void continueDeleteInstance(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            EventTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(EventTestServiceInfo.DELETEINSTANCE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Updates a number of fields on an instance of a test object. The provider
     * will publish a TestObjectUpdate event reporting the updated attributes
     * .
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void updateInstance(Long in1,
            BasicEnum in2,
            Duration in3,
            ShortList in4) throws MALStandardError, MALException {
        try {
            consumer.submit(EventTestServiceInfo.UPDATEINSTANCE_OP, (in1 == null) ? null : new Union(in1), in2, in3, in4);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method updateInstance.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncUpdateInstance(Long in1,
            BasicEnum in2,
            Duration in3,
            ShortList in4,
            EventTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(EventTestServiceInfo.UPDATEINSTANCE_OP, adapter, (in1 == null) ? null : new Union(in1), in2, in3, in4);
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
    public void continueUpdateInstance(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            EventTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(EventTestServiceInfo.UPDATEINSTANCE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Updates the composite field on a instance of a test object. The provider
     * will publish a TestObjectUpdate event reporting the updated attributes
     * .
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void updateInstanceComposite(Long in1,
            UOctet in2,
            Byte in3,
            Double in4) throws MALStandardError, MALException {
        try {
            consumer.submit(EventTestServiceInfo.UPDATEINSTANCECOMPOSITE_OP, (in1 == null) ? null : new Union(in1), in2, (in3 == null) ? null : new Union(in3), (in4 == null) ? null : new Union(in4));
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method updateInstanceComposite.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncUpdateInstanceComposite(Long in1,
            UOctet in2,
            Byte in3,
            Double in4,
            EventTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(EventTestServiceInfo.UPDATEINSTANCECOMPOSITE_OP, adapter, (in1 == null) ? null : new Union(in1), in2, (in3 == null) ? null : new Union(in3), (in4 == null) ? null : new Union(in4));
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
    public void continueUpdateInstanceComposite(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            EventTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(EventTestServiceInfo.UPDATEINSTANCECOMPOSITE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

}
