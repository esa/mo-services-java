package org.ccsds.moims.mo.malprototype.datatest.consumer;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.CompositeList;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.ULong;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.malprototype.DataErrorException;
import org.ccsds.moims.mo.malprototype.TestObjectExistsException;
import org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo;
import org.ccsds.moims.mo.malprototype.datatest.body.TestAbstractMultiReturnResponse;
import org.ccsds.moims.mo.malprototype.datatest.body.TestExplicitMultiReturnResponse;
import org.ccsds.moims.mo.malprototype.datatest.body.TestInnerAbstractMultiReturnResponse;
import org.ccsds.moims.mo.malprototype.datatest.body.TestPolymorphicObjectRefTypesResponse;
import org.ccsds.moims.mo.malprototype.structures.AbstractCompositeList;
import org.ccsds.moims.mo.malprototype.structures.Assertion;
import org.ccsds.moims.mo.malprototype.structures.AssertionList;
import org.ccsds.moims.mo.malprototype.structures.Auto;
import org.ccsds.moims.mo.malprototype.structures.Garage;
import org.ccsds.moims.mo.malprototype.structures.TestPublish;
import org.ccsds.moims.mo.malprototype.structures.TestPublishList;

/**
 * Consumer stub for DataTest service.
 */
public class DataTestStub {

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
    public DataTestStub(MALConsumer consumer) {
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
     * This operation sets the index into the test list for the testData operation.
     * Passing non positive values resets it to the start of the list.
     * 
     * @param input1 The input1 field.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void setTestDataOffset(Integer input1) throws MALStandardError, MALException {
        try {
            consumer.submit(DataTestServiceInfo.SETTESTDATAOFFSET_OP, (input1 == null) ? null : new Union(input1));
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method setTestDataOffset.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncSetTestDataOffset(Integer input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(DataTestServiceInfo.SETTESTDATAOFFSET_OP, adapter, (input1 == null) ? null : new Union(input1));
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
    public void continueSetTestDataOffset(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.SETTESTDATAOFFSET_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
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
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Element testData(Element input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTDATA_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (Element) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testData.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestData(Element input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTDATA_OP, adapter, input1);
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
    public void continueTestData(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTDATA_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic Blob type can be sent and received explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Blob testDataBlob(Blob input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTDATABLOB_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new Blob());
            return (Blob) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataBlob.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDataBlob(Blob input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTDATABLOB_OP, adapter, input1);
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
    public void continueTestDataBlob(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTDATABLOB_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic Boolean type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Boolean testDataBoolean(Boolean input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTDATABOOLEAN_OP, (input1 == null) ? null : new Union(input1));
            Object body0 = (Object) body.getBodyElement(0, new Union(Boolean.FALSE));
            return (body0 == null) ? null : ((Union) body0).getBooleanValue();
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataBoolean.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDataBoolean(Boolean input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTDATABOOLEAN_OP, adapter, (input1 == null) ? null : new Union(input1));
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
    public void continueTestDataBoolean(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTDATABOOLEAN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic Double type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Double testDataDouble(Double input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTDATADOUBLE_OP, (input1 == null) ? null : new Union(input1));
            Object body0 = (Object) body.getBodyElement(0, new Union(Double.MAX_VALUE));
            return (body0 == null) ? null : ((Union) body0).getDoubleValue();
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataDouble.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDataDouble(Double input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTDATADOUBLE_OP, adapter, (input1 == null) ? null : new Union(input1));
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
    public void continueTestDataDouble(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTDATADOUBLE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic Duration type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Duration testDataDuration(Duration input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTDATADURATION_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new Duration());
            return (Duration) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataDuration.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDataDuration(Duration input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTDATADURATION_OP, adapter, input1);
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
    public void continueTestDataDuration(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTDATADURATION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic FineTime type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public FineTime testDataFineTime(FineTime input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTDATAFINETIME_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new FineTime());
            return (FineTime) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataFineTime.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDataFineTime(FineTime input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTDATAFINETIME_OP, adapter, input1);
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
    public void continueTestDataFineTime(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTDATAFINETIME_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic Float type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Float testDataFloat(Float input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTDATAFLOAT_OP, (input1 == null) ? null : new Union(input1));
            Object body0 = (Object) body.getBodyElement(0, new Union(Float.MAX_VALUE));
            return (body0 == null) ? null : ((Union) body0).getFloatValue();
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataFloat.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDataFloat(Float input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTDATAFLOAT_OP, adapter, (input1 == null) ? null : new Union(input1));
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
    public void continueTestDataFloat(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTDATAFLOAT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic Identifier type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Identifier testDataIdentifier(Identifier input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTDATAIDENTIFIER_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new Identifier());
            return (Identifier) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataIdentifier.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDataIdentifier(Identifier input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTDATAIDENTIFIER_OP, adapter, input1);
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
    public void continueTestDataIdentifier(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTDATAIDENTIFIER_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic Integer type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Integer testDataInteger(Integer input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTDATAINTEGER_OP, (input1 == null) ? null : new Union(input1));
            Object body0 = (Object) body.getBodyElement(0, new Union(Integer.MAX_VALUE));
            return (body0 == null) ? null : ((Union) body0).getIntegerValue();
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataInteger.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDataInteger(Integer input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTDATAINTEGER_OP, adapter, (input1 == null) ? null : new Union(input1));
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
    public void continueTestDataInteger(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTDATAINTEGER_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic Long type can be sent and received explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Long testDataLong(Long input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTDATALONG_OP, (input1 == null) ? null : new Union(input1));
            Object body0 = (Object) body.getBodyElement(0, new Union(Long.MAX_VALUE));
            return (body0 == null) ? null : ((Union) body0).getLongValue();
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataLong.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDataLong(Long input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTDATALONG_OP, adapter, (input1 == null) ? null : new Union(input1));
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
    public void continueTestDataLong(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTDATALONG_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic Octet type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Byte testDataOctet(Byte input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTDATAOCTET_OP, (input1 == null) ? null : new Union(input1));
            Object body0 = (Object) body.getBodyElement(0, new Union(Byte.MAX_VALUE));
            return (body0 == null) ? null : ((Union) body0).getOctetValue();
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataOctet.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDataOctet(Byte input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTDATAOCTET_OP, adapter, (input1 == null) ? null : new Union(input1));
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
    public void continueTestDataOctet(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTDATAOCTET_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic Short type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Short testDataShort(Short input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTDATASHORT_OP, (input1 == null) ? null : new Union(input1));
            Object body0 = (Object) body.getBodyElement(0, new Union(Short.MAX_VALUE));
            return (body0 == null) ? null : ((Union) body0).getShortValue();
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataShort.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDataShort(Short input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTDATASHORT_OP, adapter, (input1 == null) ? null : new Union(input1));
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
    public void continueTestDataShort(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTDATASHORT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic String type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public String testDataString(String input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTDATASTRING_OP, (input1 == null) ? null : new Union(input1));
            Object body0 = (Object) body.getBodyElement(0, new Union(""));
            return (body0 == null) ? null : ((Union) body0).getStringValue();
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataString.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDataString(String input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTDATASTRING_OP, adapter, (input1 == null) ? null : new Union(input1));
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
    public void continueTestDataString(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTDATASTRING_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic Time type can be sent and received explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Time testDataTime(Time input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTDATATIME_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new Time());
            return (Time) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataTime.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDataTime(Time input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTDATATIME_OP, adapter, input1);
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
    public void continueTestDataTime(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTDATATIME_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic URI type can be sent and received explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public URI testDataURI(URI input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTDATAURI_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new URI());
            return (URI) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataURI.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDataURI(URI input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTDATAURI_OP, adapter, input1);
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
    public void continueTestDataURI(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTDATAURI_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a composite type can be sent and received explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Assertion testDataComposite(Assertion input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTDATACOMPOSITE_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new Assertion());
            return (Assertion) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataComposite.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDataComposite(Assertion input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTDATACOMPOSITE_OP, adapter, input1);
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
    public void continueTestDataComposite(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTDATACOMPOSITE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a enumeration type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public SessionType testDataEnumeration(SessionType input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTDATAENUMERATION_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, SessionType.LIVE);
            return (SessionType) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataEnumeration.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDataEnumeration(SessionType input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTDATAENUMERATION_OP, adapter, input1);
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
    public void continueTestDataEnumeration(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTDATAENUMERATION_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a list type can be sent and received explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public AssertionList testDataList(AssertionList input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTDATALIST_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new AssertionList());
            return (AssertionList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataList.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDataList(AssertionList input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTDATALIST_OP, adapter, input1);
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
    public void continueTestDataList(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTDATALIST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic UInteger type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public UInteger testDataUInteger(UInteger input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTDATAUINTEGER_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new UInteger());
            return (UInteger) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataUInteger.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDataUInteger(UInteger input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTDATAUINTEGER_OP, adapter, input1);
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
    public void continueTestDataUInteger(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTDATAUINTEGER_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic ULong type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public ULong testDataULong(ULong input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTDATAULONG_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new ULong());
            return (ULong) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataULong.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDataULong(ULong input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTDATAULONG_OP, adapter, input1);
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
    public void continueTestDataULong(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTDATAULONG_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic UOctet type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public UOctet testDataUOctet(UOctet input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTDATAUOCTET_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new UOctet());
            return (UOctet) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataUOctet.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDataUOctet(UOctet input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTDATAUOCTET_OP, adapter, input1);
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
    public void continueTestDataUOctet(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTDATAUOCTET_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic UShort type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public UShort testDataUShort(UShort input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTDATAUSHORT_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new UShort());
            return (UShort) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataUShort.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDataUShort(UShort input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTDATAUSHORT_OP, adapter, input1);
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
    public void continueTestDataUShort(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTDATAUSHORT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
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
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public TestExplicitMultiReturnResponse testExplicitMultiReturn(UOctet in1,
            UShort in2,
            UInteger in3,
            ULong in4) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTEXPLICITMULTIRETURN_OP, in1, in2, in3, in4);
            Object body0 = (Object) body.getBodyElement(0, new UOctet());
            Object body1 = (Object) body.getBodyElement(1, new UShort());
            Object body2 = (Object) body.getBodyElement(2, new UInteger());
            Object body3 = (Object) body.getBodyElement(3, new ULong());
            return new TestExplicitMultiReturnResponse((UOctet) body0, (UShort) body1, (UInteger) body2, (ULong) body3);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
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
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestExplicitMultiReturn(UOctet in1,
            UShort in2,
            UInteger in3,
            ULong in4,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTEXPLICITMULTIRETURN_OP, adapter, in1, in2, in3, in4);
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
    public void continueTestExplicitMultiReturn(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTEXPLICITMULTIRETURN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
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
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public TestAbstractMultiReturnResponse testAbstractMultiReturn(UOctet in1,
            UShort in2,
            UInteger in3,
            Element in4) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTABSTRACTMULTIRETURN_OP, in1, in2, in3, in4);
            Object body0 = (Object) body.getBodyElement(0, new UOctet());
            Object body1 = (Object) body.getBodyElement(1, new UShort());
            Object body2 = (Object) body.getBodyElement(2, new UInteger());
            Object body3 = (Object) body.getBodyElement(3, null);
            return new TestAbstractMultiReturnResponse((UOctet) body0, (UShort) body1, (UInteger) body2, (Element) body3);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
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
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestAbstractMultiReturn(UOctet in1,
            UShort in2,
            UInteger in3,
            Element in4,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTABSTRACTMULTIRETURN_OP, adapter, in1, in2, in3, in4);
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
    public void continueTestAbstractMultiReturn(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTABSTRACTMULTIRETURN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that an empty body can be sent and received explicitly.
     * 
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void testEmptyBody() throws DataErrorException, MALStandardError, MALException {
        try {
            consumer.request(DataTestServiceInfo.TESTEMPTYBODY_OP, (Object[]) null);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testEmptyBody.
     * 
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestEmptyBody(DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTEMPTYBODY_OP, adapter, (Object[]) null);
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
    public void continueTestEmptyBody(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTEMPTYBODY_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a MAL::Attribute can be sent and received as
     * an abstract Attribute.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Attribute testMalAttribute(Attribute input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTMALATTRIBUTE_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (Attribute) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testMalAttribute.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestMalAttribute(Attribute input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTMALATTRIBUTE_OP, adapter, input1);
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
    public void continueTestMalAttribute(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTMALATTRIBUTE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a Composite can be sent and received as a MAL
     * Composite. It is no longer used in the testbed.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Composite testMalComposite(Composite input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTMALCOMPOSITE_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (Composite) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testMalComposite.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestMalComposite(Composite input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTMALCOMPOSITE_OP, adapter, input1);
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
    public void continueTestMalComposite(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTMALCOMPOSITE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a Composite can be sent and received as an abstract
     * Composite. It is no longer used in the testbed.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public TestPublish testAbstractComposite(TestPublish input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTABSTRACTCOMPOSITE_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (TestPublish) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testAbstractComposite.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestAbstractComposite(TestPublish input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTABSTRACTCOMPOSITE_OP, adapter, input1);
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
    public void continueTestAbstractComposite(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTABSTRACTCOMPOSITE_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a list of MAL::Attribute can be sent and received
     * explicitly. It is no longer used in the testbed.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public AttributeList testMalAttributeList(AttributeList input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTMALATTRIBUTELIST_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new AttributeList());
            return (AttributeList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testMalAttributeList.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestMalAttributeList(AttributeList input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTMALATTRIBUTELIST_OP, adapter, input1);
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
    public void continueTestMalAttributeList(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTMALATTRIBUTELIST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a list of MAL::Element can be sent and received
     * explicitly. It is no longer used in the testbed.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public HeterogeneousList testMalElementList(HeterogeneousList input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTMALELEMENTLIST_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (HeterogeneousList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testMalElementList.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestMalElementList(HeterogeneousList input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTMALELEMENTLIST_OP, adapter, input1);
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
    public void continueTestMalElementList(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTMALELEMENTLIST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a list of MAL::Composite can be sent and received
     * explicitly. It is no longer used in the testbed.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public CompositeList testMalCompositeList(CompositeList input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTMALCOMPOSITELIST_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new CompositeList());
            return (CompositeList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testMalCompositeList.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestMalCompositeList(CompositeList input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTMALCOMPOSITELIST_OP, adapter, input1);
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
    public void continueTestMalCompositeList(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTMALCOMPOSITELIST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a list of abstract composite can be sent and
     * received explicitly. It is no longer used in the testbed.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public TestPublishList testAbstractCompositeList(TestPublishList input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTABSTRACTCOMPOSITELIST_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new TestPublishList());
            return (TestPublishList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testAbstractCompositeList.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestAbstractCompositeList(TestPublishList input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTABSTRACTCOMPOSITELIST_OP, adapter, input1);
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
    public void continueTestAbstractCompositeList(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTABSTRACTCOMPOSITELIST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that a basic ObjectRef type can be sent and received
     * explicitly.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public ObjectRef<Auto> testDataObjectRef(ObjectRef<Auto> input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTDATAOBJECTREF_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new ObjectRef<Auto>());
            return (ObjectRef<Auto>) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testDataObjectRef.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestDataObjectRef(ObjectRef<Auto> input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTDATAOBJECTREF_OP, adapter, input1);
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
    public void continueTestDataObjectRef(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTDATAOBJECTREF_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
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
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public TestInnerAbstractMultiReturnResponse testInnerAbstractMultiReturn(UOctet in1,
            Element in2,
            Element in3,
            UInteger in4) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTINNERABSTRACTMULTIRETURN_OP, in1, in2, in3, in4);
            Object body0 = (Object) body.getBodyElement(0, new UOctet());
            Object body1 = (Object) body.getBodyElement(1, null);
            Object body2 = (Object) body.getBodyElement(2, null);
            Object body3 = (Object) body.getBodyElement(3, new UInteger());
            return new TestInnerAbstractMultiReturnResponse((UOctet) body0, (Element) body1, (Element) body2, (UInteger) body3);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
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
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestInnerAbstractMultiReturn(UOctet in1,
            Element in2,
            Element in3,
            UInteger in4,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTINNERABSTRACTMULTIRETURN_OP, adapter, in1, in2, in3, in4);
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
    public void continueTestInnerAbstractMultiReturn(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTINNERABSTRACTMULTIRETURN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that various concrete values can be sent and received
     * explicitly as a list of abstract composite.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public AbstractCompositeList testPolymorphicAbstractCompositeList(AbstractCompositeList input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTPOLYMORPHICABSTRACTCOMPOSITELIST_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new AbstractCompositeList());
            return (AbstractCompositeList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testPolymorphicAbstractCompositeList.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestPolymorphicAbstractCompositeList(AbstractCompositeList input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTPOLYMORPHICABSTRACTCOMPOSITELIST_OP, adapter, input1);
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
    public void continueTestPolymorphicAbstractCompositeList(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTPOLYMORPHICABSTRACTCOMPOSITELIST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that various concrete values can be sent and received
     * explicitly as a list of MAL Composite.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public CompositeList testPolymorphicMalCompositeList(CompositeList input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTPOLYMORPHICMALCOMPOSITELIST_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, new CompositeList());
            return (CompositeList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testPolymorphicMalCompositeList.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestPolymorphicMalCompositeList(CompositeList input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTPOLYMORPHICMALCOMPOSITELIST_OP, adapter, input1);
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
    public void continueTestPolymorphicMalCompositeList(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTPOLYMORPHICMALCOMPOSITELIST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation checks that various concrete values can be sent and received
     * explicitly as a list of MAL Element.
     * 
     * @param input1 The input1 field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public HeterogeneousList testPolymorphicMalElementList(HeterogeneousList input1) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTPOLYMORPHICMALELEMENTLIST_OP, input1);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (HeterogeneousList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method testPolymorphicMalElementList.
     * 
     * @param input1 The input1 field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestPolymorphicMalElementList(HeterogeneousList input1,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTPOLYMORPHICMALELEMENTLIST_OP, adapter, input1);
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
    public void continueTestPolymorphicMalElementList(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTPOLYMORPHICMALELEMENTLIST_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
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
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public TestPolymorphicObjectRefTypesResponse testPolymorphicObjectRefTypes(Garage garage,
            ObjectRefList porsches,
            ObjectRefList autos,
            ObjectRefList elements) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.TESTPOLYMORPHICOBJECTREFTYPES_OP, garage, porsches, autos, elements);
            Object body0 = (Object) body.getBodyElement(0, new Garage());
            Object body1 = (Object) body.getBodyElement(1, new ObjectRefList());
            Object body2 = (Object) body.getBodyElement(2, new ObjectRefList());
            Object body3 = (Object) body.getBodyElement(3, new ObjectRefList());
            return new TestPolymorphicObjectRefTypesResponse((Garage) body0, (ObjectRefList) body1, (ObjectRefList) body2, (ObjectRefList) body3);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
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
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncTestPolymorphicObjectRefTypes(Garage garage,
            ObjectRefList porsches,
            ObjectRefList autos,
            ObjectRefList elements,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.TESTPOLYMORPHICOBJECTREFTYPES_OP, adapter, garage, porsches, autos, elements);
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
    public void continueTestPolymorphicObjectRefTypes(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.TESTPOLYMORPHICOBJECTREFTYPES_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation creates a new MO Object from a full value.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws TestObjectExistsException Data interoperability error
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public ObjectRef<Auto> createObject(Auto input) throws TestObjectExistsException, DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.CREATEOBJECT_OP, input);
            Object body0 = (Object) body.getBodyElement(0, new ObjectRef<Auto>());
            return (ObjectRef<Auto>) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof TestObjectExistsException) {
                throw (TestObjectExistsException) error;
            }
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method createObject.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncCreateObject(Auto input,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.CREATEOBJECT_OP, adapter, input);
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
    public void continueCreateObject(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.CREATEOBJECT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
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
     * @throws TestObjectExistsException Data interoperability error
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public ObjectRef<Auto> createObjectFromFields(Long autoType,
            Identifier key,
            Boolean update,
            String engine,
            String chassis,
            StringList windows) throws TestObjectExistsException, DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.CREATEOBJECTFROMFIELDS_OP, (autoType == null) ? null : new Union(autoType), key, (update == null) ? null : new Union(update), (engine == null) ? null : new Union(engine), (chassis == null) ? null : new Union(chassis), windows);
            Object body0 = (Object) body.getBodyElement(0, new ObjectRef<Auto>());
            return (ObjectRef<Auto>) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof TestObjectExistsException) {
                throw (TestObjectExistsException) error;
            }
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
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
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncCreateObjectFromFields(Long autoType,
            Identifier key,
            Boolean update,
            String engine,
            String chassis,
            StringList windows,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.CREATEOBJECTFROMFIELDS_OP, adapter, (autoType == null) ? null : new Union(autoType), key, (update == null) ? null : new Union(update), (engine == null) ? null : new Union(engine), (chassis == null) ? null : new Union(chassis), windows);
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
    public void continueCreateObjectFromFields(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.CREATEOBJECTFROMFIELDS_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation deletes an MO Object.
     * 
     * @param input The input field.
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void deleteObject(ObjectRef<Auto> input) throws DataErrorException, MALStandardError, MALException {
        try {
            consumer.submit(DataTestServiceInfo.DELETEOBJECT_OP, input);
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method deleteObject.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncDeleteObject(ObjectRef<Auto> input,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(DataTestServiceInfo.DELETEOBJECT_OP, adapter, input);
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
    public void continueDeleteObject(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.DELETEOBJECT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * This operation gets an MO Object value from its reference.
     * 
     * @param input The input field.
     * @return The return value of the interaction
     * @throws DataErrorException Data interoperability error
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public Auto getObject(ObjectRef<Auto> input) throws DataErrorException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(DataTestServiceInfo.GETOBJECT_OP, input);
            Object body0 = (Object) body.getBodyElement(0, null);
            return (Auto) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DataErrorException) {
                throw (DataErrorException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method getObject.
     * 
     * @param input The input field.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncGetObject(ObjectRef<Auto> input,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(DataTestServiceInfo.GETOBJECT_OP, adapter, input);
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
    public void continueGetObject(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            DataTestAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(DataTestServiceInfo.GETOBJECT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

}
