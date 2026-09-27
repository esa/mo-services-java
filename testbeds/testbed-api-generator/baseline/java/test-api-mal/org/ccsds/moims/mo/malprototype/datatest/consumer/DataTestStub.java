package org.ccsds.moims.mo.malprototype.datatest.consumer;

/**
 * Consumer stub for DataTest service.
 */
public class DataTestStub {

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
    public DataTestStub(org.ccsds.moims.mo.mal.consumer.MALConsumer consumer) {
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
     * This operation sets the index into the test list for the testData operation.
     * Passing non positive values resets it to the start of the list.
     * 
     * @param input1 The input1 field.
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void setTestDataOffset(Integer input1) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.submit(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.SETTESTDATAOFFSET_OP, (input1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(input1));
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method setTestDataOffset.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncSetTestDataOffset(Integer input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncSubmit(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.SETTESTDATAOFFSET_OP, adapter, (input1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(input1));
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
    public void continueSetTestDataOffset(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.SETTESTDATAOFFSET_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The &quot;testData&quot; operation allows a consumer to check that a data
     * is correctly decoded on the provider side, then that the same data sent
     * back by the provider is correctly decoded on the consumer side. The provider
     * needs to statically know the list of data that the consumer is going to
     * send. The consumer selects the data in the same order as the list and calls
     * the operation &quot;testData&quot;. The provider keeps the index of the
     * currently selected data from the static list. When the operation &quot;testData&quot;
     * is called, the provider checks that the received data is equal to the selected
     * data from the list. If the equality test fails, then the error DATA_ERROR
     * is raised, otherwise the provider returns the decoded data. When the consumer
     * receives the returned data, it checks that this data is equal to the original
     * data it sent.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.structures.Element testData(org.ccsds.moims.mo.mal.structures.Element input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATA_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (org.ccsds.moims.mo.mal.structures.Element) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testData.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestData(org.ccsds.moims.mo.mal.structures.Element input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATA_OP, adapter, input1);
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
    public void continueTestData(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATA_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic Blob type can be sent and received explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.structures.Blob testDataBlob(org.ccsds.moims.mo.mal.structures.Blob input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATABLOB_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Blob());
            return (org.ccsds.moims.mo.mal.structures.Blob) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataBlob.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDataBlob(org.ccsds.moims.mo.mal.structures.Blob input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATABLOB_OP, adapter, input1);
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
    public void continueTestDataBlob(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATABLOB_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic Boolean type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Boolean testDataBoolean(Boolean input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATABOOLEAN_OP, (input1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(input1));
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE));
            return (body0 == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body0).getBooleanValue();
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataBoolean.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDataBoolean(Boolean input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATABOOLEAN_OP, adapter, (input1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(input1));
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
    public void continueTestDataBoolean(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATABOOLEAN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic Double type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Double testDataDouble(Double input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATADOUBLE_OP, (input1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(input1));
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Double.MAX_VALUE));
            return (body0 == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body0).getDoubleValue();
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataDouble.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDataDouble(Double input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATADOUBLE_OP, adapter, (input1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(input1));
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
    public void continueTestDataDouble(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATADOUBLE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic Duration type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.structures.Duration testDataDuration(org.ccsds.moims.mo.mal.structures.Duration input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATADURATION_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Duration());
            return (org.ccsds.moims.mo.mal.structures.Duration) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataDuration.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDataDuration(org.ccsds.moims.mo.mal.structures.Duration input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATADURATION_OP, adapter, input1);
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
    public void continueTestDataDuration(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATADURATION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic FineTime type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.structures.FineTime testDataFineTime(org.ccsds.moims.mo.mal.structures.FineTime input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAFINETIME_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.FineTime());
            return (org.ccsds.moims.mo.mal.structures.FineTime) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataFineTime.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDataFineTime(org.ccsds.moims.mo.mal.structures.FineTime input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAFINETIME_OP, adapter, input1);
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
    public void continueTestDataFineTime(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAFINETIME_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic Float type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Float testDataFloat(Float input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAFLOAT_OP, (input1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(input1));
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Float.MAX_VALUE));
            return (body0 == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body0).getFloatValue();
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataFloat.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDataFloat(Float input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAFLOAT_OP, adapter, (input1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(input1));
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
    public void continueTestDataFloat(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAFLOAT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic Identifier type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.structures.Identifier testDataIdentifier(org.ccsds.moims.mo.mal.structures.Identifier input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAIDENTIFIER_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Identifier());
            return (org.ccsds.moims.mo.mal.structures.Identifier) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataIdentifier.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDataIdentifier(org.ccsds.moims.mo.mal.structures.Identifier input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAIDENTIFIER_OP, adapter, input1);
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
    public void continueTestDataIdentifier(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAIDENTIFIER_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic Integer type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Integer testDataInteger(Integer input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAINTEGER_OP, (input1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(input1));
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Integer.MAX_VALUE));
            return (body0 == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body0).getIntegerValue();
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataInteger.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDataInteger(Integer input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAINTEGER_OP, adapter, (input1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(input1));
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
    public void continueTestDataInteger(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAINTEGER_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic Long type can be sent and received explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Long testDataLong(Long input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATALONG_OP, (input1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(input1));
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE));
            return (body0 == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body0).getLongValue();
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataLong.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDataLong(Long input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATALONG_OP, adapter, (input1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(input1));
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
    public void continueTestDataLong(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATALONG_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic Octet type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Byte testDataOctet(Byte input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAOCTET_OP, (input1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(input1));
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Byte.MAX_VALUE));
            return (body0 == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body0).getOctetValue();
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataOctet.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDataOctet(Byte input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAOCTET_OP, adapter, (input1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(input1));
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
    public void continueTestDataOctet(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAOCTET_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic Short type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Short testDataShort(Short input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATASHORT_OP, (input1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(input1));
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Short.MAX_VALUE));
            return (body0 == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body0).getShortValue();
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataShort.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDataShort(Short input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATASHORT_OP, adapter, (input1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(input1));
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
    public void continueTestDataShort(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATASHORT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic String type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public String testDataString(String input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATASTRING_OP, (input1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(input1));
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(""));
            return (body0 == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body0).getStringValue();
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataString.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDataString(String input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATASTRING_OP, adapter, (input1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(input1));
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
    public void continueTestDataString(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATASTRING_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic Time type can be sent and received explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.structures.Time testDataTime(org.ccsds.moims.mo.mal.structures.Time input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATATIME_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Time());
            return (org.ccsds.moims.mo.mal.structures.Time) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataTime.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDataTime(org.ccsds.moims.mo.mal.structures.Time input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATATIME_OP, adapter, input1);
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
    public void continueTestDataTime(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATATIME_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic URI type can be sent and received explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.structures.URI testDataURI(org.ccsds.moims.mo.mal.structures.URI input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAURI_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.URI());
            return (org.ccsds.moims.mo.mal.structures.URI) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataURI.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDataURI(org.ccsds.moims.mo.mal.structures.URI input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAURI_OP, adapter, input1);
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
    public void continueTestDataURI(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAURI_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a composite type can be sent and received explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.malprototype.structures.Assertion testDataComposite(org.ccsds.moims.mo.malprototype.structures.Assertion input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATACOMPOSITE_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.Assertion());
            return (org.ccsds.moims.mo.malprototype.structures.Assertion) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataComposite.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDataComposite(org.ccsds.moims.mo.malprototype.structures.Assertion input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATACOMPOSITE_OP, adapter, input1);
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
    public void continueTestDataComposite(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATACOMPOSITE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a enumeration type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.structures.SessionType testDataEnumeration(org.ccsds.moims.mo.mal.structures.SessionType input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAENUMERATION_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, org.ccsds.moims.mo.mal.structures.SessionType.LIVE);
            return (org.ccsds.moims.mo.mal.structures.SessionType) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataEnumeration.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDataEnumeration(org.ccsds.moims.mo.mal.structures.SessionType input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAENUMERATION_OP, adapter, input1);
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
    public void continueTestDataEnumeration(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAENUMERATION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a list type can be sent and received explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.malprototype.structures.AssertionList testDataList(org.ccsds.moims.mo.malprototype.structures.AssertionList input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATALIST_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.AssertionList());
            return (org.ccsds.moims.mo.malprototype.structures.AssertionList) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataList.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDataList(org.ccsds.moims.mo.malprototype.structures.AssertionList input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATALIST_OP, adapter, input1);
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
    public void continueTestDataList(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATALIST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic UInteger type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.structures.UInteger testDataUInteger(org.ccsds.moims.mo.mal.structures.UInteger input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAUINTEGER_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.UInteger());
            return (org.ccsds.moims.mo.mal.structures.UInteger) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataUInteger.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDataUInteger(org.ccsds.moims.mo.mal.structures.UInteger input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAUINTEGER_OP, adapter, input1);
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
    public void continueTestDataUInteger(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAUINTEGER_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic ULong type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.structures.ULong testDataULong(org.ccsds.moims.mo.mal.structures.ULong input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAULONG_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ULong());
            return (org.ccsds.moims.mo.mal.structures.ULong) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataULong.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDataULong(org.ccsds.moims.mo.mal.structures.ULong input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAULONG_OP, adapter, input1);
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
    public void continueTestDataULong(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAULONG_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic UOctet type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.structures.UOctet testDataUOctet(org.ccsds.moims.mo.mal.structures.UOctet input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAUOCTET_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.UOctet());
            return (org.ccsds.moims.mo.mal.structures.UOctet) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataUOctet.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDataUOctet(org.ccsds.moims.mo.mal.structures.UOctet input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAUOCTET_OP, adapter, input1);
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
    public void continueTestDataUOctet(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAUOCTET_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic UShort type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.structures.UShort testDataUShort(org.ccsds.moims.mo.mal.structures.UShort input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAUSHORT_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.UShort());
            return (org.ccsds.moims.mo.mal.structures.UShort) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataUShort.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDataUShort(org.ccsds.moims.mo.mal.structures.UShort input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAUSHORT_OP, adapter, input1);
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
    public void continueTestDataUShort(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAUSHORT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that multiple types can be sent and received explicitly.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.malprototype.datatest.body.TestExplicitMultiReturnResponse testExplicitMultiReturn(org.ccsds.moims.mo.mal.structures.UOctet in1,
            org.ccsds.moims.mo.mal.structures.UShort in2,
            org.ccsds.moims.mo.mal.structures.UInteger in3,
            org.ccsds.moims.mo.mal.structures.ULong in4) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTEXPLICITMULTIRETURN_OP, in1, in2, in3, in4);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.UOctet());
            Object body1 = (Object) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UShort());
            Object body2 = (Object) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.UInteger());
            Object body3 = (Object) body.getBodyElement(3, new org.ccsds.moims.mo.mal.structures.ULong());
            return new org.ccsds.moims.mo.malprototype.datatest.body.TestExplicitMultiReturnResponse((org.ccsds.moims.mo.mal.structures.UOctet) body0, (org.ccsds.moims.mo.mal.structures.UShort) body1, (org.ccsds.moims.mo.mal.structures.UInteger) body2, (org.ccsds.moims.mo.mal.structures.ULong) body3);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testExplicitMultiReturn.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestExplicitMultiReturn(org.ccsds.moims.mo.mal.structures.UOctet in1,
            org.ccsds.moims.mo.mal.structures.UShort in2,
            org.ccsds.moims.mo.mal.structures.UInteger in3,
            org.ccsds.moims.mo.mal.structures.ULong in4,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTEXPLICITMULTIRETURN_OP, adapter, in1, in2, in3, in4);
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
    public void continueTestExplicitMultiReturn(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTEXPLICITMULTIRETURN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that multiple types with a final abstract type can
     * be sent and received explicitly.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.malprototype.datatest.body.TestAbstractMultiReturnResponse testAbstractMultiReturn(org.ccsds.moims.mo.mal.structures.UOctet in1,
            org.ccsds.moims.mo.mal.structures.UShort in2,
            org.ccsds.moims.mo.mal.structures.UInteger in3,
            org.ccsds.moims.mo.mal.structures.Element in4) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTABSTRACTMULTIRETURN_OP, in1, in2, in3, in4);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.UOctet());
            Object body1 = (Object) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UShort());
            Object body2 = (Object) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.UInteger());
            Object body3 = (Object) body.getBodyElement(3, null);
            return new org.ccsds.moims.mo.malprototype.datatest.body.TestAbstractMultiReturnResponse((org.ccsds.moims.mo.mal.structures.UOctet) body0, (org.ccsds.moims.mo.mal.structures.UShort) body1, (org.ccsds.moims.mo.mal.structures.UInteger) body2, (org.ccsds.moims.mo.mal.structures.Element) body3);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testAbstractMultiReturn.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestAbstractMultiReturn(org.ccsds.moims.mo.mal.structures.UOctet in1,
            org.ccsds.moims.mo.mal.structures.UShort in2,
            org.ccsds.moims.mo.mal.structures.UInteger in3,
            org.ccsds.moims.mo.mal.structures.Element in4,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTABSTRACTMULTIRETURN_OP, adapter, in1, in2, in3, in4);
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
    public void continueTestAbstractMultiReturn(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTABSTRACTMULTIRETURN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that an empty body can be sent and received explicitly.
     * 
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void testEmptyBody() throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTEMPTYBODY_OP, (Object[]) null);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testEmptyBody.
     * 
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestEmptyBody(org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTEMPTYBODY_OP, adapter, (Object[]) null);
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
    public void continueTestEmptyBody(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTEMPTYBODY_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a MAL::Attribute can be sent and received as
     * an abstract Attribute.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.structures.Attribute testMalAttribute(org.ccsds.moims.mo.mal.structures.Attribute input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTMALATTRIBUTE_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (org.ccsds.moims.mo.mal.structures.Attribute) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testMalAttribute.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestMalAttribute(org.ccsds.moims.mo.mal.structures.Attribute input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTMALATTRIBUTE_OP, adapter, input1);
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
    public void continueTestMalAttribute(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTMALATTRIBUTE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a Composite can be sent and received as a MAL
     * Composite. It is no longer used in the testbed.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.structures.Composite testMalComposite(org.ccsds.moims.mo.mal.structures.Composite input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTMALCOMPOSITE_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (org.ccsds.moims.mo.mal.structures.Composite) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testMalComposite.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestMalComposite(org.ccsds.moims.mo.mal.structures.Composite input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTMALCOMPOSITE_OP, adapter, input1);
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
    public void continueTestMalComposite(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTMALCOMPOSITE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a Composite can be sent and received as an abstract
     * Composite. It is no longer used in the testbed.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.malprototype.structures.TestPublish testAbstractComposite(org.ccsds.moims.mo.malprototype.structures.TestPublish input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTABSTRACTCOMPOSITE_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (org.ccsds.moims.mo.malprototype.structures.TestPublish) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testAbstractComposite.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestAbstractComposite(org.ccsds.moims.mo.malprototype.structures.TestPublish input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTABSTRACTCOMPOSITE_OP, adapter, input1);
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
    public void continueTestAbstractComposite(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTABSTRACTCOMPOSITE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a list of MAL::Attribute can be sent and received
     * explicitly. It is no longer used in the testbed.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.structures.AttributeList testMalAttributeList(org.ccsds.moims.mo.mal.structures.AttributeList input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTMALATTRIBUTELIST_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.AttributeList());
            return (org.ccsds.moims.mo.mal.structures.AttributeList) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testMalAttributeList.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestMalAttributeList(org.ccsds.moims.mo.mal.structures.AttributeList input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTMALATTRIBUTELIST_OP, adapter, input1);
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
    public void continueTestMalAttributeList(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTMALATTRIBUTELIST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a list of MAL::Element can be sent and received
     * explicitly. It is no longer used in the testbed.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.structures.HeterogeneousList testMalElementList(org.ccsds.moims.mo.mal.structures.HeterogeneousList input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTMALELEMENTLIST_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (org.ccsds.moims.mo.mal.structures.HeterogeneousList) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testMalElementList.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestMalElementList(org.ccsds.moims.mo.mal.structures.HeterogeneousList input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTMALELEMENTLIST_OP, adapter, input1);
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
    public void continueTestMalElementList(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTMALELEMENTLIST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a list of MAL::Composite can be sent and received
     * explicitly. It is no longer used in the testbed.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.structures.CompositeList testMalCompositeList(org.ccsds.moims.mo.mal.structures.CompositeList input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTMALCOMPOSITELIST_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.CompositeList());
            return (org.ccsds.moims.mo.mal.structures.CompositeList) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testMalCompositeList.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestMalCompositeList(org.ccsds.moims.mo.mal.structures.CompositeList input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTMALCOMPOSITELIST_OP, adapter, input1);
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
    public void continueTestMalCompositeList(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTMALCOMPOSITELIST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a list of abstract composite can be sent and
     * received explicitly. It is no longer used in the testbed.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.malprototype.structures.TestPublishList testAbstractCompositeList(org.ccsds.moims.mo.malprototype.structures.TestPublishList input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTABSTRACTCOMPOSITELIST_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.TestPublishList());
            return (org.ccsds.moims.mo.malprototype.structures.TestPublishList) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testAbstractCompositeList.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestAbstractCompositeList(org.ccsds.moims.mo.malprototype.structures.TestPublishList input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTABSTRACTCOMPOSITELIST_OP, adapter, input1);
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
    public void continueTestAbstractCompositeList(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTABSTRACTCOMPOSITELIST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic ObjectRef type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto> testDataObjectRef(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto> input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAOBJECTREF_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto>());
            return (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto>) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataObjectRef.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestDataObjectRef(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto> input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAOBJECTREF_OP, adapter, input1);
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
    public void continueTestDataObjectRef(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTDATAOBJECTREF_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that multiple types with a not final abstract type
     * can be sent and received explicitly.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.malprototype.datatest.body.TestInnerAbstractMultiReturnResponse testInnerAbstractMultiReturn(org.ccsds.moims.mo.mal.structures.UOctet in1,
            org.ccsds.moims.mo.mal.structures.Element in2,
            org.ccsds.moims.mo.mal.structures.Element in3,
            org.ccsds.moims.mo.mal.structures.UInteger in4) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTINNERABSTRACTMULTIRETURN_OP, in1, in2, in3, in4);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.UOctet());
            Object body1 = (Object) body.getBodyElement(1, null);
            Object body2 = (Object) body.getBodyElement(2, null);
            Object body3 = (Object) body.getBodyElement(3, new org.ccsds.moims.mo.mal.structures.UInteger());
            return new org.ccsds.moims.mo.malprototype.datatest.body.TestInnerAbstractMultiReturnResponse((org.ccsds.moims.mo.mal.structures.UOctet) body0, (org.ccsds.moims.mo.mal.structures.Element) body1, (org.ccsds.moims.mo.mal.structures.Element) body2, (org.ccsds.moims.mo.mal.structures.UInteger) body3);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testInnerAbstractMultiReturn.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestInnerAbstractMultiReturn(org.ccsds.moims.mo.mal.structures.UOctet in1,
            org.ccsds.moims.mo.mal.structures.Element in2,
            org.ccsds.moims.mo.mal.structures.Element in3,
            org.ccsds.moims.mo.mal.structures.UInteger in4,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTINNERABSTRACTMULTIRETURN_OP, adapter, in1, in2, in3, in4);
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
    public void continueTestInnerAbstractMultiReturn(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTINNERABSTRACTMULTIRETURN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that various concrete values can be sent and received
     * explicitly as a list of abstract composite.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.malprototype.structures.AbstractCompositeList testPolymorphicAbstractCompositeList(org.ccsds.moims.mo.malprototype.structures.AbstractCompositeList input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTPOLYMORPHICABSTRACTCOMPOSITELIST_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.AbstractCompositeList());
            return (org.ccsds.moims.mo.malprototype.structures.AbstractCompositeList) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testPolymorphicAbstractCompositeList.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestPolymorphicAbstractCompositeList(org.ccsds.moims.mo.malprototype.structures.AbstractCompositeList input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTPOLYMORPHICABSTRACTCOMPOSITELIST_OP, adapter, input1);
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
    public void continueTestPolymorphicAbstractCompositeList(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTPOLYMORPHICABSTRACTCOMPOSITELIST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that various concrete values can be sent and received
     * explicitly as a list of MAL Composite.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.structures.CompositeList testPolymorphicMalCompositeList(org.ccsds.moims.mo.mal.structures.CompositeList input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTPOLYMORPHICMALCOMPOSITELIST_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.CompositeList());
            return (org.ccsds.moims.mo.mal.structures.CompositeList) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testPolymorphicMalCompositeList.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestPolymorphicMalCompositeList(org.ccsds.moims.mo.mal.structures.CompositeList input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTPOLYMORPHICMALCOMPOSITELIST_OP, adapter, input1);
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
    public void continueTestPolymorphicMalCompositeList(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTPOLYMORPHICMALCOMPOSITELIST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that various concrete values can be sent and received
     * explicitly as a list of MAL Element.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.structures.HeterogeneousList testPolymorphicMalElementList(org.ccsds.moims.mo.mal.structures.HeterogeneousList input1) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTPOLYMORPHICMALELEMENTLIST_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (org.ccsds.moims.mo.mal.structures.HeterogeneousList) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testPolymorphicMalElementList.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestPolymorphicMalElementList(org.ccsds.moims.mo.mal.structures.HeterogeneousList input1,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTPOLYMORPHICMALELEMENTLIST_OP, adapter, input1);
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
    public void continueTestPolymorphicMalElementList(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTPOLYMORPHICMALELEMENTLIST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks the ObjectRef(T) polymorphism in operation type signature
     * and in composite fields.
     * 
     * @param garage The garage field.
     * @param porsches The porsches field.
     * @param autos The autos field.
     * @param elements The elements field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.malprototype.datatest.body.TestPolymorphicObjectRefTypesResponse testPolymorphicObjectRefTypes(org.ccsds.moims.mo.malprototype.structures.Garage garage,
            org.ccsds.moims.mo.mal.structures.ObjectRefList porsches,
            org.ccsds.moims.mo.mal.structures.ObjectRefList autos,
            org.ccsds.moims.mo.mal.structures.ObjectRefList elements) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTPOLYMORPHICOBJECTREFTYPES_OP, garage, porsches, autos, elements);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.Garage());
            Object body1 = (Object) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.ObjectRefList());
            Object body2 = (Object) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.ObjectRefList());
            Object body3 = (Object) body.getBodyElement(3, new org.ccsds.moims.mo.mal.structures.ObjectRefList());
            return new org.ccsds.moims.mo.malprototype.datatest.body.TestPolymorphicObjectRefTypesResponse((org.ccsds.moims.mo.malprototype.structures.Garage) body0, (org.ccsds.moims.mo.mal.structures.ObjectRefList) body1, (org.ccsds.moims.mo.mal.structures.ObjectRefList) body2, (org.ccsds.moims.mo.mal.structures.ObjectRefList) body3);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testPolymorphicObjectRefTypes.
     * 
     * @param garage The garage field.
     * @param porsches The porsches field.
     * @param autos The autos field.
     * @param elements The elements field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncTestPolymorphicObjectRefTypes(org.ccsds.moims.mo.malprototype.structures.Garage garage,
            org.ccsds.moims.mo.mal.structures.ObjectRefList porsches,
            org.ccsds.moims.mo.mal.structures.ObjectRefList autos,
            org.ccsds.moims.mo.mal.structures.ObjectRefList elements,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTPOLYMORPHICOBJECTREFTYPES_OP, adapter, garage, porsches, autos, elements);
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
    public void continueTestPolymorphicObjectRefTypes(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.TESTPOLYMORPHICOBJECTREFTYPES_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation creates a new MO Object from a full value.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.TestObjectExistsException Data interoperability error
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto> createObject(org.ccsds.moims.mo.malprototype.structures.Auto input) throws org.ccsds.moims.mo.malprototype.TestObjectExistsException, org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.CREATEOBJECT_OP, input);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto>());
            return (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto>) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.TestObjectExistsException) {
                throw (org.ccsds.moims.mo.malprototype.TestObjectExistsException) error;
            }
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method createObject.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncCreateObject(org.ccsds.moims.mo.malprototype.structures.Auto input,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.CREATEOBJECT_OP, adapter, input);
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
    public void continueCreateObject(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.CREATEOBJECT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation creates a new MO Object from fields values.
     * 
     * @param autoType The autoType field.
     * @param key The key field.
     * @param update The update field.
     * @param engine The engine field.
     * @param chassis The chassis field.
     * @param windows The windows field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.TestObjectExistsException Data interoperability error
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto> createObjectFromFields(Long autoType,
            org.ccsds.moims.mo.mal.structures.Identifier key,
            Boolean update,
            String engine,
            String chassis,
            org.ccsds.moims.mo.mal.structures.StringList windows) throws org.ccsds.moims.mo.malprototype.TestObjectExistsException, org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.CREATEOBJECTFROMFIELDS_OP, (autoType == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(autoType), key, (update == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(update), (engine == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(engine), (chassis == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(chassis), windows);
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto>());
            return (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto>) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.TestObjectExistsException) {
                throw (org.ccsds.moims.mo.malprototype.TestObjectExistsException) error;
            }
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method createObjectFromFields.
     * 
     * @param autoType The autoType field.
     * @param key The key field.
     * @param update The update field.
     * @param engine The engine field.
     * @param chassis The chassis field.
     * @param windows The windows field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncCreateObjectFromFields(Long autoType,
            org.ccsds.moims.mo.mal.structures.Identifier key,
            Boolean update,
            String engine,
            String chassis,
            org.ccsds.moims.mo.mal.structures.StringList windows,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.CREATEOBJECTFROMFIELDS_OP, adapter, (autoType == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(autoType), key, (update == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(update), (engine == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(engine), (chassis == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(chassis), windows);
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
    public void continueCreateObjectFromFields(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.CREATEOBJECTFROMFIELDS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation deletes an MO Object.
     * 
     * @param input The input field.
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void deleteObject(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto> input) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.submit(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.DELETEOBJECT_OP, input);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method deleteObject.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncDeleteObject(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto> input,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncSubmit(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.DELETEOBJECT_OP, adapter, input);
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
    public void continueDeleteObject(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.DELETEOBJECT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation gets an MO Object value from its reference.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.malprototype.structures.Auto getObject(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto> input) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.GETOBJECT_OP, input);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (org.ccsds.moims.mo.malprototype.structures.Auto) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.malprototype.DataErrorException) {
                throw (org.ccsds.moims.mo.malprototype.DataErrorException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method getObject.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncGetObject(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto> input,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.GETOBJECT_OP, adapter, input);
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
    public void continueGetObject(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo.GETOBJECT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

}
