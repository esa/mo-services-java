package org.ccsds.moims.mo.malprototype.errortest.consumer;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo;

/**
 * Consumer stub for ErrorTest service.
 */
public class ErrorTestStub {

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
    public ErrorTestStub(MALConsumer consumer) {
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
     * This operation does nothing. Actually the error is raised by the transport
     * layer before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Element testDeliveryFailed(Element input) throws MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ErrorTestServiceInfo.TESTDELIVERYFAILED_OP, input);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (Element) body0;
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDeliveryFailed.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDeliveryFailed(Element input,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ErrorTestServiceInfo.TESTDELIVERYFAILED_OP, adapter, input);
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
    public void continueTestDeliveryFailed(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ErrorTestServiceInfo.TESTDELIVERYFAILED_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation does nothing. Actually the error is raised by the transport
     * layer before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Element testDeliveryTimedout(Element input) throws MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ErrorTestServiceInfo.TESTDELIVERYTIMEDOUT_OP, input);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (Element) body0;
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDeliveryTimedout.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDeliveryTimedout(Element input,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ErrorTestServiceInfo.TESTDELIVERYTIMEDOUT_OP, adapter, input);
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
    public void continueTestDeliveryTimedout(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ErrorTestServiceInfo.TESTDELIVERYTIMEDOUT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation does nothing. Actually the error is raised by the transport
     * layer before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Element testDeliveryDelayed(Element input) throws MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ErrorTestServiceInfo.TESTDELIVERYDELAYED_OP, input);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (Element) body0;
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDeliveryDelayed.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDeliveryDelayed(Element input,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ErrorTestServiceInfo.TESTDELIVERYDELAYED_OP, adapter, input);
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
    public void continueTestDeliveryDelayed(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ErrorTestServiceInfo.TESTDELIVERYDELAYED_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation does nothing. Actually the error is raised by the transport
     * layer before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Element testDestinationUnknown(Element input) throws MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ErrorTestServiceInfo.TESTDESTINATIONUNKNOWN_OP, input);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (Element) body0;
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDestinationUnknown.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDestinationUnknown(Element input,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ErrorTestServiceInfo.TESTDESTINATIONUNKNOWN_OP, adapter, input);
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
    public void continueTestDestinationUnknown(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ErrorTestServiceInfo.TESTDESTINATIONUNKNOWN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation does nothing. Actually the error is raised by the transport
     * layer before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Element testDestinationTransient(Element input) throws MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ErrorTestServiceInfo.TESTDESTINATIONTRANSIENT_OP, input);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (Element) body0;
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDestinationTransient.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDestinationTransient(Element input,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ErrorTestServiceInfo.TESTDESTINATIONTRANSIENT_OP, adapter, input);
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
    public void continueTestDestinationTransient(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ErrorTestServiceInfo.TESTDESTINATIONTRANSIENT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation does nothing. Actually the error is raised by the transport
     * layer before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Element testDestinationLost(Element input) throws MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ErrorTestServiceInfo.TESTDESTINATIONLOST_OP, input);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (Element) body0;
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDestinationLost.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDestinationLost(Element input,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ErrorTestServiceInfo.TESTDESTINATIONLOST_OP, adapter, input);
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
    public void continueTestDestinationLost(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ErrorTestServiceInfo.TESTDESTINATIONLOST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation does nothing. Actually the error is raised by the transport
     * layer before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Element testEncryptionFail(Element input) throws MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ErrorTestServiceInfo.TESTENCRYPTIONFAIL_OP, input);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (Element) body0;
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testEncryptionFail.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestEncryptionFail(Element input,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ErrorTestServiceInfo.TESTENCRYPTIONFAIL_OP, adapter, input);
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
    public void continueTestEncryptionFail(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ErrorTestServiceInfo.TESTENCRYPTIONFAIL_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation does nothing. Actually the error is raised by the transport
     * layer before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Element testUnsupportedArea(Element input) throws MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ErrorTestServiceInfo.TESTUNSUPPORTEDAREA_OP, input);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (Element) body0;
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testUnsupportedArea.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestUnsupportedArea(Element input,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ErrorTestServiceInfo.TESTUNSUPPORTEDAREA_OP, adapter, input);
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
    public void continueTestUnsupportedArea(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ErrorTestServiceInfo.TESTUNSUPPORTEDAREA_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation does nothing. Actually the error is raised by the transport
     * layer before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Element testUnsupportedOperation(Element input) throws MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ErrorTestServiceInfo.TESTUNSUPPORTEDOPERATION_OP, input);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (Element) body0;
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testUnsupportedOperation.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestUnsupportedOperation(Element input,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ErrorTestServiceInfo.TESTUNSUPPORTEDOPERATION_OP, adapter, input);
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
    public void continueTestUnsupportedOperation(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ErrorTestServiceInfo.TESTUNSUPPORTEDOPERATION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation does nothing. Actually the error is raised by the transport
     * layer before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Element testUnsupportedAreaVersion(Element input) throws MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ErrorTestServiceInfo.TESTUNSUPPORTEDAREAVERSION_OP, input);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (Element) body0;
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testUnsupportedAreaVersion.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestUnsupportedAreaVersion(Element input,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ErrorTestServiceInfo.TESTUNSUPPORTEDAREAVERSION_OP, adapter, input);
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
    public void continueTestUnsupportedAreaVersion(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ErrorTestServiceInfo.TESTUNSUPPORTEDAREAVERSION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation does nothing. Actually the error is raised by the transport
     * layer before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Element testBadEncoding(Element input) throws MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ErrorTestServiceInfo.TESTBADENCODING_OP, input);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (Element) body0;
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testBadEncoding.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestBadEncoding(Element input,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ErrorTestServiceInfo.TESTBADENCODING_OP, adapter, input);
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
    public void continueTestBadEncoding(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ErrorTestServiceInfo.TESTBADENCODING_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation does nothing. Actually the error is raised by the transport
     * layer before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Element testUnknown(Element input) throws MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ErrorTestServiceInfo.TESTUNKNOWN_OP, input);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (Element) body0;
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testUnknown.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestUnknown(Element input,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ErrorTestServiceInfo.TESTUNKNOWN_OP, adapter, input);
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
    public void continueTestUnknown(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ErrorTestServiceInfo.TESTUNKNOWN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation does nothing. Actually the error is raised by the MAL layer
     * before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Element testAuthenticationFailure(Element input) throws MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ErrorTestServiceInfo.TESTAUTHENTICATIONFAILURE_OP, input);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (Element) body0;
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testAuthenticationFailure.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestAuthenticationFailure(Element input,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ErrorTestServiceInfo.TESTAUTHENTICATIONFAILURE_OP, adapter, input);
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
    public void continueTestAuthenticationFailure(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ErrorTestServiceInfo.TESTAUTHENTICATIONFAILURE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation does nothing. Actually the error is raised by the MAL layer
     * before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Element testAuthorizationFailure(Element input) throws MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ErrorTestServiceInfo.TESTAUTHORIZATIONFAILURE_OP, input);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (Element) body0;
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testAuthorizationFailure.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestAuthorizationFailure(Element input,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ErrorTestServiceInfo.TESTAUTHORIZATIONFAILURE_OP, adapter, input);
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
    public void continueTestAuthorizationFailure(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ErrorTestServiceInfo.TESTAUTHORIZATIONFAILURE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation does nothing. Actually the error is raised by the transport
     * layer before the provider is invoked.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Element testUnsupportedService(Element input) throws MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(ErrorTestServiceInfo.TESTUNSUPPORTEDSERVICE_OP, input);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (Element) body0;
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testUnsupportedService.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestUnsupportedService(Element input,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(ErrorTestServiceInfo.TESTUNSUPPORTEDSERVICE_OP, adapter, input);
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
    public void continueTestUnsupportedService(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            ErrorTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(ErrorTestServiceInfo.TESTUNSUPPORTEDSERVICE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

}
