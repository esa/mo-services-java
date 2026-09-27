package org.ccsds.moims.mo.comprototype.activitytest.consumer;

/**
 * Consumer stub for ActivityTest service.
 */
public class ActivityTestStub {

    /**
     * The consumer field.
     */
    private final org.ccsds.moims.mo.mal.consumer.MALConsumer consumer;

    /**
     * Wraps a MALconsumer connection with service specific methods that map from
     * the high level service API to the generic MAL API.
     * 
     * @param consumer consumer The MALConsumer to use in this stub.
     */
    public ActivityTestStub(org.ccsds.moims.mo.mal.consumer.MALConsumer consumer) {
        this.consumer = consumer;
    }

    /**
     * Returns the internal MAL consumer object used for sending of messages from
     * this interface.
     * 
     * @return The MAL consumer object.
     */
    public org.ccsds.moims.mo.mal.consumer.MALConsumer getConsumer() {
        return consumer;
    }

    /**
     * Resets all values back to their default value.
     * 
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void resetTest() throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.submit(org.ccsds.moims.mo.comprototype.activitytest.ActivityTestServiceInfo.RESETTEST_OP, (Object[]) null);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method resetTest.
     * 
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncResetTest(org.ccsds.moims.mo.comprototype.activitytest.consumer.ActivityTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncSubmit(org.ccsds.moims.mo.comprototype.activitytest.ActivityTestServiceInfo.RESETTEST_OP, adapter, (Object[]) null);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueResetTest(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.comprototype.activitytest.consumer.ActivityTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.comprototype.activitytest.ActivityTestServiceInfo.RESETTEST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Closes the service provider.
     * 
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void close() throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.submit(org.ccsds.moims.mo.comprototype.activitytest.ActivityTestServiceInfo.CLOSE_OP, (Object[]) null);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method close.
     * 
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncClose(org.ccsds.moims.mo.comprototype.activitytest.consumer.ActivityTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncSubmit(org.ccsds.moims.mo.comprototype.activitytest.ActivityTestServiceInfo.CLOSE_OP, adapter, (Object[]) null);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueClose(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.comprototype.activitytest.consumer.ActivityTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.comprototype.activitytest.ActivityTestServiceInfo.CLOSE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * 
     * @param in1 The in1 field.
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage send(org.ccsds.moims.mo.mal.structures.StringList in1) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.send(org.ccsds.moims.mo.comprototype.activitytest.ActivityTestServiceInfo.SEND_OP, in1);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * 
     * @param in1 The in1 field.
     * @throws org.ccsds.moims.mo.mal.UnknownException Fake error for testing.
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void testSubmit(org.ccsds.moims.mo.mal.structures.StringList in1) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.submit(org.ccsds.moims.mo.comprototype.activitytest.ActivityTestServiceInfo.TESTSUBMIT_OP, in1);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.mal.UnknownException) {
                throw (org.ccsds.moims.mo.mal.UnknownException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testSubmit.
     * 
     * @param in1 The in1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestSubmit(org.ccsds.moims.mo.mal.structures.StringList in1,
            org.ccsds.moims.mo.comprototype.activitytest.consumer.ActivityTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncSubmit(org.ccsds.moims.mo.comprototype.activitytest.ActivityTestServiceInfo.TESTSUBMIT_OP, adapter, in1);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueTestSubmit(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.comprototype.activitytest.consumer.ActivityTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.comprototype.activitytest.ActivityTestServiceInfo.TESTSUBMIT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * 
     * @param in1 The in1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.UnknownException Fake error for testing.
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.structures.StringList request(org.ccsds.moims.mo.mal.structures.StringList in1) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.comprototype.activitytest.ActivityTestServiceInfo.REQUEST_OP, in1);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.StringList());
            return (org.ccsds.moims.mo.mal.structures.StringList) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.mal.UnknownException) {
                throw (org.ccsds.moims.mo.mal.UnknownException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method request.
     * 
     * @param in1 The in1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncRequest(org.ccsds.moims.mo.mal.structures.StringList in1,
            org.ccsds.moims.mo.comprototype.activitytest.consumer.ActivityTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.comprototype.activitytest.ActivityTestServiceInfo.REQUEST_OP, adapter, in1);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueRequest(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.comprototype.activitytest.consumer.ActivityTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.comprototype.activitytest.ActivityTestServiceInfo.REQUEST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * 
     * @param in1 The in1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return The acknowledge value of the interaction
     * @throws org.ccsds.moims.mo.mal.UnknownException Fake error for testing.
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.structures.StringList invoke(org.ccsds.moims.mo.mal.structures.StringList in1,
            org.ccsds.moims.mo.comprototype.activitytest.consumer.ActivityTestAdapter adapter) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.invoke(org.ccsds.moims.mo.comprototype.activitytest.ActivityTestServiceInfo.INVOKE_OP, adapter, in1);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.StringList());
            return (org.ccsds.moims.mo.mal.structures.StringList) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.mal.UnknownException) {
                throw (org.ccsds.moims.mo.mal.UnknownException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method invoke.
     * 
     * @param in1 The in1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncInvoke(org.ccsds.moims.mo.mal.structures.StringList in1,
            org.ccsds.moims.mo.comprototype.activitytest.consumer.ActivityTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncInvoke(org.ccsds.moims.mo.comprototype.activitytest.ActivityTestServiceInfo.INVOKE_OP, adapter, in1);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueInvoke(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.comprototype.activitytest.consumer.ActivityTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.comprototype.activitytest.ActivityTestServiceInfo.INVOKE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * 
     * @param in1 The in1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return The acknowledge value of the interaction
     * @throws org.ccsds.moims.mo.mal.UnknownException Fake error for testing.
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.structures.StringList progress(org.ccsds.moims.mo.mal.structures.StringList in1,
            org.ccsds.moims.mo.comprototype.activitytest.consumer.ActivityTestAdapter adapter) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.progress(org.ccsds.moims.mo.comprototype.activitytest.ActivityTestServiceInfo.PROGRESS_OP, adapter, in1);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.StringList());
            return (org.ccsds.moims.mo.mal.structures.StringList) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.mal.UnknownException) {
                throw (org.ccsds.moims.mo.mal.UnknownException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method progress.
     * 
     * @param in1 The in1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncProgress(org.ccsds.moims.mo.mal.structures.StringList in1,
            org.ccsds.moims.mo.comprototype.activitytest.consumer.ActivityTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncProgress(org.ccsds.moims.mo.comprototype.activitytest.ActivityTestServiceInfo.PROGRESS_OP, adapter, in1);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueProgress(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.comprototype.activitytest.consumer.ActivityTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.comprototype.activitytest.ActivityTestServiceInfo.PROGRESS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

}
