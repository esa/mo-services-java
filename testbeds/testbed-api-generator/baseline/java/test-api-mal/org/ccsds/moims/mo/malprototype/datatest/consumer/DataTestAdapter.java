package org.ccsds.moims.mo.malprototype.datatest.consumer;

/**
 * Consumer adapter for DataTest service.
 */
public abstract class DataTestAdapter extends org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter {

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation setTestDataOffset.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void setTestDataOffsetAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation setTestDataOffset.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void setTestDataOffsetErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testData.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param input1 The input1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Element input1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testData.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataBlob.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataBlobResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Blob output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataBlob.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataBlobErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataBoolean.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataBooleanResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            Boolean output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataBoolean.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataBooleanErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataDouble.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataDoubleResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            Double output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataDouble.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataDoubleErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataDuration.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataDurationResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Duration output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataDuration.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataDurationErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataFineTime.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataFineTimeResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.FineTime output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataFineTime.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataFineTimeErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataFloat.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataFloatResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            Float output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataFloat.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataFloatErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataIdentifier.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataIdentifierResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Identifier output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataIdentifier.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataIdentifierErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataInteger.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataIntegerResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            Integer output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataInteger.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataIntegerErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataLong.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataLongResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            Long output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataLong.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataLongErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataOctet.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataOctetResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            Byte output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataOctet.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataOctetErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataShort.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataShortResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            Short output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataShort.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataShortErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataString.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataStringResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            String output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataString.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataStringErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataTime.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataTimeResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Time output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataTime.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataTimeErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataURI.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataURIResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.URI output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataURI.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataURIErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataComposite.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataCompositeResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.malprototype.structures.Assertion output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataComposite.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataCompositeErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataEnumeration.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataEnumerationResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.SessionType output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataEnumeration.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataEnumerationErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataListResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.malprototype.structures.AssertionList output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataListErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataUInteger.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataUIntegerResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.UInteger output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataUInteger.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataUIntegerErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataULong.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataULongResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.ULong output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataULong.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataULongErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataUOctet.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataUOctetResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.UOctet output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataUOctet.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataUOctetErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataUShort.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataUShortResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.UShort output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataUShort.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataUShortErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testExplicitMultiReturn.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param out1 The out1 field.
     * @param out2 The out2 field.
     * @param out3 The out3 field.
     * @param out4 The out4 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testExplicitMultiReturnResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.UOctet out1,
            org.ccsds.moims.mo.mal.structures.UShort out2,
            org.ccsds.moims.mo.mal.structures.UInteger out3,
            org.ccsds.moims.mo.mal.structures.ULong out4,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testExplicitMultiReturn.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testExplicitMultiReturnErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testAbstractMultiReturn.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param out1 The out1 field.
     * @param out2 The out2 field.
     * @param out3 The out3 field.
     * @param out4 The out4 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testAbstractMultiReturnResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.UOctet out1,
            org.ccsds.moims.mo.mal.structures.UShort out2,
            org.ccsds.moims.mo.mal.structures.UInteger out3,
            org.ccsds.moims.mo.mal.structures.Element out4,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testAbstractMultiReturn.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testAbstractMultiReturnErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testEmptyBody.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testEmptyBodyResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testEmptyBody.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testEmptyBodyErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testMalAttribute.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testMalAttributeResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Attribute output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testMalAttribute.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testMalAttributeErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testMalComposite.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testMalCompositeResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Composite output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testMalComposite.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testMalCompositeErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testAbstractComposite.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testAbstractCompositeResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.malprototype.structures.TestPublish output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testAbstractComposite.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testAbstractCompositeErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testMalAttributeList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testMalAttributeListResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.AttributeList output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testMalAttributeList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testMalAttributeListErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testMalElementList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testMalElementListResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.HeterogeneousList output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testMalElementList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testMalElementListErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testMalCompositeList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testMalCompositeListResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.CompositeList output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testMalCompositeList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testMalCompositeListErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testAbstractCompositeList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testAbstractCompositeListResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.malprototype.structures.TestPublishList output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testAbstractCompositeList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testAbstractCompositeListErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataObjectRef.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataObjectRefResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto> output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataObjectRef.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataObjectRefErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testInnerAbstractMultiReturn.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param out1 The out1 field.
     * @param out2 The out2 field.
     * @param out3 The out3 field.
     * @param out4 The out4 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testInnerAbstractMultiReturnResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.UOctet out1,
            org.ccsds.moims.mo.mal.structures.Element out2,
            org.ccsds.moims.mo.mal.structures.Element out3,
            org.ccsds.moims.mo.mal.structures.UInteger out4,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testInnerAbstractMultiReturn.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testInnerAbstractMultiReturnErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testPolymorphicAbstractCompositeList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testPolymorphicAbstractCompositeListResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.malprototype.structures.AbstractCompositeList output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testPolymorphicAbstractCompositeList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testPolymorphicAbstractCompositeListErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testPolymorphicMalCompositeList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testPolymorphicMalCompositeListResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.CompositeList output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testPolymorphicMalCompositeList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testPolymorphicMalCompositeListErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testPolymorphicMalElementList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testPolymorphicMalElementListResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.HeterogeneousList output1,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testPolymorphicMalElementList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testPolymorphicMalElementListErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testPolymorphicObjectRefTypes.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param output2 The output2 field.
     * @param output3 The output3 field.
     * @param output4 The output4 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testPolymorphicObjectRefTypesResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.malprototype.structures.Garage output1,
            org.ccsds.moims.mo.mal.structures.ObjectRefList output2,
            org.ccsds.moims.mo.mal.structures.ObjectRefList output3,
            org.ccsds.moims.mo.mal.structures.ObjectRefList output4,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testPolymorphicObjectRefTypes.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testPolymorphicObjectRefTypesErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation createObject.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void createObjectResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto> output,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation createObject.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void createObjectErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation createObjectFromFields.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void createObjectFromFieldsResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto> output,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation createObjectFromFields.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void createObjectFromFieldsErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation deleteObject.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deleteObjectAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation deleteObject.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deleteObjectErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getObject.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getObjectResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.malprototype.structures.Auto output,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getObject.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getObjectErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._SETTESTDATAOFFSET_OP_NUMBER:
            setTestDataOffsetAckReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._DELETEOBJECT_OP_NUMBER:
            deleteObjectAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void submitErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._SETTESTDATAOFFSET_OP_NUMBER:
            setTestDataOffsetErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._DELETEOBJECT_OP_NUMBER:
            deleteObjectErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void requestResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATA_OP_NUMBER:
            testDataResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATABLOB_OP_NUMBER:
            testDataBlobResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Blob) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Blob()), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATABOOLEAN_OP_NUMBER:
            testDataBooleanResponseReceived(msgHeader,
                (body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE))).getBooleanValue(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATADOUBLE_OP_NUMBER:
            testDataDoubleResponseReceived(msgHeader,
                (body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Double.MAX_VALUE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Double.MAX_VALUE))).getDoubleValue(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATADURATION_OP_NUMBER:
            testDataDurationResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Duration) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Duration()), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAFINETIME_OP_NUMBER:
            testDataFineTimeResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.FineTime) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.FineTime()), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAFLOAT_OP_NUMBER:
            testDataFloatResponseReceived(msgHeader,
                (body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Float.MAX_VALUE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Float.MAX_VALUE))).getFloatValue(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAIDENTIFIER_OP_NUMBER:
            testDataIdentifierResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Identifier) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Identifier()), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAINTEGER_OP_NUMBER:
            testDataIntegerResponseReceived(msgHeader,
                (body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Integer.MAX_VALUE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Integer.MAX_VALUE))).getIntegerValue(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATALONG_OP_NUMBER:
            testDataLongResponseReceived(msgHeader,
                (body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE))).getLongValue(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAOCTET_OP_NUMBER:
            testDataOctetResponseReceived(msgHeader,
                (body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Byte.MAX_VALUE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Byte.MAX_VALUE))).getOctetValue(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATASHORT_OP_NUMBER:
            testDataShortResponseReceived(msgHeader,
                (body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Short.MAX_VALUE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Short.MAX_VALUE))).getShortValue(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATASTRING_OP_NUMBER:
            testDataStringResponseReceived(msgHeader,
                (body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union("")) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(""))).getStringValue(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATATIME_OP_NUMBER:
            testDataTimeResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Time) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Time()), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAURI_OP_NUMBER:
            testDataURIResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.URI) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.URI()), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATACOMPOSITE_OP_NUMBER:
            testDataCompositeResponseReceived(msgHeader,
                (org.ccsds.moims.mo.malprototype.structures.Assertion) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.Assertion()), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAENUMERATION_OP_NUMBER:
            testDataEnumerationResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.SessionType) body.getBodyElement(0, org.ccsds.moims.mo.mal.structures.SessionType.LIVE), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATALIST_OP_NUMBER:
            testDataListResponseReceived(msgHeader,
                (org.ccsds.moims.mo.malprototype.structures.AssertionList) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.AssertionList()), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAUINTEGER_OP_NUMBER:
            testDataUIntegerResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.UInteger) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.UInteger()), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAULONG_OP_NUMBER:
            testDataULongResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.ULong) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ULong()), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAUOCTET_OP_NUMBER:
            testDataUOctetResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.UOctet) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.UOctet()), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAUSHORT_OP_NUMBER:
            testDataUShortResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.UShort) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.UShort()), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTEXPLICITMULTIRETURN_OP_NUMBER:
            testExplicitMultiReturnResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.UOctet) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.UOctet()),
                (org.ccsds.moims.mo.mal.structures.UShort) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UShort()),
                (org.ccsds.moims.mo.mal.structures.UInteger) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.UInteger()),
                (org.ccsds.moims.mo.mal.structures.ULong) body.getBodyElement(3, new org.ccsds.moims.mo.mal.structures.ULong()), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTABSTRACTMULTIRETURN_OP_NUMBER:
            testAbstractMultiReturnResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.UOctet) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.UOctet()),
                (org.ccsds.moims.mo.mal.structures.UShort) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UShort()),
                (org.ccsds.moims.mo.mal.structures.UInteger) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.UInteger()),
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(3, null), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTEMPTYBODY_OP_NUMBER:
            testEmptyBodyResponseReceived(msgHeader, qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTMALATTRIBUTE_OP_NUMBER:
            testMalAttributeResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Attribute) body.getBodyElement(0, null), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTMALCOMPOSITE_OP_NUMBER:
            testMalCompositeResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Composite) body.getBodyElement(0, null), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTABSTRACTCOMPOSITE_OP_NUMBER:
            testAbstractCompositeResponseReceived(msgHeader,
                (org.ccsds.moims.mo.malprototype.structures.TestPublish) body.getBodyElement(0, null), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTMALATTRIBUTELIST_OP_NUMBER:
            testMalAttributeListResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.AttributeList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.AttributeList()), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTMALELEMENTLIST_OP_NUMBER:
            testMalElementListResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.HeterogeneousList) body.getBodyElement(0, null), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTMALCOMPOSITELIST_OP_NUMBER:
            testMalCompositeListResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.CompositeList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.CompositeList()), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTABSTRACTCOMPOSITELIST_OP_NUMBER:
            testAbstractCompositeListResponseReceived(msgHeader,
                (org.ccsds.moims.mo.malprototype.structures.TestPublishList) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.TestPublishList()), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAOBJECTREF_OP_NUMBER:
            testDataObjectRefResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto>) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto>()), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTINNERABSTRACTMULTIRETURN_OP_NUMBER:
            testInnerAbstractMultiReturnResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.UOctet) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.UOctet()),
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(1, null),
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(2, null),
                (org.ccsds.moims.mo.mal.structures.UInteger) body.getBodyElement(3, new org.ccsds.moims.mo.mal.structures.UInteger()), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTPOLYMORPHICABSTRACTCOMPOSITELIST_OP_NUMBER:
            testPolymorphicAbstractCompositeListResponseReceived(msgHeader,
                (org.ccsds.moims.mo.malprototype.structures.AbstractCompositeList) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.AbstractCompositeList()), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTPOLYMORPHICMALCOMPOSITELIST_OP_NUMBER:
            testPolymorphicMalCompositeListResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.CompositeList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.CompositeList()), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTPOLYMORPHICMALELEMENTLIST_OP_NUMBER:
            testPolymorphicMalElementListResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.HeterogeneousList) body.getBodyElement(0, null), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTPOLYMORPHICOBJECTREFTYPES_OP_NUMBER:
            testPolymorphicObjectRefTypesResponseReceived(msgHeader,
                (org.ccsds.moims.mo.malprototype.structures.Garage) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.Garage()),
                (org.ccsds.moims.mo.mal.structures.ObjectRefList) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.ObjectRefList()),
                (org.ccsds.moims.mo.mal.structures.ObjectRefList) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.ObjectRefList()),
                (org.ccsds.moims.mo.mal.structures.ObjectRefList) body.getBodyElement(3, new org.ccsds.moims.mo.mal.structures.ObjectRefList()), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._CREATEOBJECT_OP_NUMBER:
            createObjectResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto>) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto>()), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._CREATEOBJECTFROMFIELDS_OP_NUMBER:
            createObjectFromFieldsResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto>) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto>()), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._GETOBJECT_OP_NUMBER:
            getObjectResponseReceived(msgHeader,
                (org.ccsds.moims.mo.malprototype.structures.Auto) body.getBodyElement(0, null), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void requestErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATA_OP_NUMBER:
            testDataErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATABLOB_OP_NUMBER:
            testDataBlobErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATABOOLEAN_OP_NUMBER:
            testDataBooleanErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATADOUBLE_OP_NUMBER:
            testDataDoubleErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATADURATION_OP_NUMBER:
            testDataDurationErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAFINETIME_OP_NUMBER:
            testDataFineTimeErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAFLOAT_OP_NUMBER:
            testDataFloatErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAIDENTIFIER_OP_NUMBER:
            testDataIdentifierErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAINTEGER_OP_NUMBER:
            testDataIntegerErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATALONG_OP_NUMBER:
            testDataLongErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAOCTET_OP_NUMBER:
            testDataOctetErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATASHORT_OP_NUMBER:
            testDataShortErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATASTRING_OP_NUMBER:
            testDataStringErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATATIME_OP_NUMBER:
            testDataTimeErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAURI_OP_NUMBER:
            testDataURIErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATACOMPOSITE_OP_NUMBER:
            testDataCompositeErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAENUMERATION_OP_NUMBER:
            testDataEnumerationErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATALIST_OP_NUMBER:
            testDataListErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAUINTEGER_OP_NUMBER:
            testDataUIntegerErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAULONG_OP_NUMBER:
            testDataULongErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAUOCTET_OP_NUMBER:
            testDataUOctetErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAUSHORT_OP_NUMBER:
            testDataUShortErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTEXPLICITMULTIRETURN_OP_NUMBER:
            testExplicitMultiReturnErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTABSTRACTMULTIRETURN_OP_NUMBER:
            testAbstractMultiReturnErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTEMPTYBODY_OP_NUMBER:
            testEmptyBodyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTMALATTRIBUTE_OP_NUMBER:
            testMalAttributeErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTMALCOMPOSITE_OP_NUMBER:
            testMalCompositeErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTABSTRACTCOMPOSITE_OP_NUMBER:
            testAbstractCompositeErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTMALATTRIBUTELIST_OP_NUMBER:
            testMalAttributeListErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTMALELEMENTLIST_OP_NUMBER:
            testMalElementListErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTMALCOMPOSITELIST_OP_NUMBER:
            testMalCompositeListErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTABSTRACTCOMPOSITELIST_OP_NUMBER:
            testAbstractCompositeListErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAOBJECTREF_OP_NUMBER:
            testDataObjectRefErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTINNERABSTRACTMULTIRETURN_OP_NUMBER:
            testInnerAbstractMultiReturnErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTPOLYMORPHICABSTRACTCOMPOSITELIST_OP_NUMBER:
            testPolymorphicAbstractCompositeListErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTPOLYMORPHICMALCOMPOSITELIST_OP_NUMBER:
            testPolymorphicMalCompositeListErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTPOLYMORPHICMALELEMENTLIST_OP_NUMBER:
            testPolymorphicMalElementListErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTPOLYMORPHICOBJECTREFTYPES_OP_NUMBER:
            testPolymorphicObjectRefTypesErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._CREATEOBJECT_OP_NUMBER:
            createObjectErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._CREATEOBJECTFROMFIELDS_OP_NUMBER:
            createObjectFromFieldsErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._GETOBJECT_OP_NUMBER:
            getObjectErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

}
