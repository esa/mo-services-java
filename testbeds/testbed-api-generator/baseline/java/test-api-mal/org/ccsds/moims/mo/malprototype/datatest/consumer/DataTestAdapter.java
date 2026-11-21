package org.ccsds.moims.mo.malprototype.datatest.consumer;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter;
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
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.ULong;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo;
import org.ccsds.moims.mo.malprototype.structures.AbstractCompositeList;
import org.ccsds.moims.mo.malprototype.structures.Assertion;
import org.ccsds.moims.mo.malprototype.structures.AssertionList;
import org.ccsds.moims.mo.malprototype.structures.Auto;
import org.ccsds.moims.mo.malprototype.structures.Garage;
import org.ccsds.moims.mo.malprototype.structures.TestPublish;
import org.ccsds.moims.mo.malprototype.structures.TestPublishList;

/**
 * Consumer adapter for DataTest service.
 */
public abstract class DataTestAdapter extends MALInteractionAdapter {

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation setTestDataOffset.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void setTestDataOffsetAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation setTestDataOffset.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void setTestDataOffsetErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testData.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param input1 The input1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataResponseReceived(MALMessageHeader msgHeader,
            Element input1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testData.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataBlob.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataBlobResponseReceived(MALMessageHeader msgHeader,
            Blob output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataBlob.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataBlobErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataBoolean.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataBooleanResponseReceived(MALMessageHeader msgHeader,
            Boolean output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataBoolean.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataBooleanErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataDouble.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataDoubleResponseReceived(MALMessageHeader msgHeader,
            Double output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataDouble.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataDoubleErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataDuration.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataDurationResponseReceived(MALMessageHeader msgHeader,
            Duration output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataDuration.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataDurationErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataFineTime.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataFineTimeResponseReceived(MALMessageHeader msgHeader,
            FineTime output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataFineTime.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataFineTimeErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataFloat.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataFloatResponseReceived(MALMessageHeader msgHeader,
            Float output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataFloat.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataFloatErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataIdentifier.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataIdentifierResponseReceived(MALMessageHeader msgHeader,
            Identifier output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataIdentifier.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataIdentifierErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataInteger.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataIntegerResponseReceived(MALMessageHeader msgHeader,
            Integer output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataInteger.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataIntegerErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataLong.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataLongResponseReceived(MALMessageHeader msgHeader,
            Long output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataLong.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataLongErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataOctet.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataOctetResponseReceived(MALMessageHeader msgHeader,
            Byte output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataOctet.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataOctetErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataShort.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataShortResponseReceived(MALMessageHeader msgHeader,
            Short output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataShort.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataShortErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataString.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataStringResponseReceived(MALMessageHeader msgHeader,
            String output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataString.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataStringErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataTime.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataTimeResponseReceived(MALMessageHeader msgHeader,
            Time output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataTime.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataTimeErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataURI.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataURIResponseReceived(MALMessageHeader msgHeader,
            URI output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataURI.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataURIErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataComposite.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataCompositeResponseReceived(MALMessageHeader msgHeader,
            Assertion output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataComposite.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataCompositeErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataEnumeration.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataEnumerationResponseReceived(MALMessageHeader msgHeader,
            SessionType output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataEnumeration.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataEnumerationErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataListResponseReceived(MALMessageHeader msgHeader,
            AssertionList output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataListErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataUInteger.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataUIntegerResponseReceived(MALMessageHeader msgHeader,
            UInteger output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataUInteger.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataUIntegerErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataULong.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataULongResponseReceived(MALMessageHeader msgHeader,
            ULong output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataULong.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataULongErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataUOctet.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataUOctetResponseReceived(MALMessageHeader msgHeader,
            UOctet output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataUOctet.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataUOctetErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataUShort.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataUShortResponseReceived(MALMessageHeader msgHeader,
            UShort output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataUShort.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataUShortErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
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
    public void testExplicitMultiReturnResponseReceived(MALMessageHeader msgHeader,
            UOctet out1,
            UShort out2,
            UInteger out3,
            ULong out4,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testExplicitMultiReturn.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testExplicitMultiReturnErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
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
    public void testAbstractMultiReturnResponseReceived(MALMessageHeader msgHeader,
            UOctet out1,
            UShort out2,
            UInteger out3,
            Element out4,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testAbstractMultiReturn.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testAbstractMultiReturnErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testEmptyBody.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testEmptyBodyResponseReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testEmptyBody.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testEmptyBodyErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testMalAttribute.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testMalAttributeResponseReceived(MALMessageHeader msgHeader,
            Attribute output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testMalAttribute.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testMalAttributeErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testMalComposite.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testMalCompositeResponseReceived(MALMessageHeader msgHeader,
            Composite output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testMalComposite.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testMalCompositeErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testAbstractComposite.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testAbstractCompositeResponseReceived(MALMessageHeader msgHeader,
            TestPublish output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testAbstractComposite.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testAbstractCompositeErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testMalAttributeList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testMalAttributeListResponseReceived(MALMessageHeader msgHeader,
            AttributeList output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testMalAttributeList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testMalAttributeListErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testMalElementList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testMalElementListResponseReceived(MALMessageHeader msgHeader,
            HeterogeneousList output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testMalElementList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testMalElementListErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testMalCompositeList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testMalCompositeListResponseReceived(MALMessageHeader msgHeader,
            CompositeList output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testMalCompositeList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testMalCompositeListErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testAbstractCompositeList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testAbstractCompositeListResponseReceived(MALMessageHeader msgHeader,
            TestPublishList output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testAbstractCompositeList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testAbstractCompositeListErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testDataObjectRef.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataObjectRefResponseReceived(MALMessageHeader msgHeader,
            ObjectRef<Auto> output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testDataObjectRef.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testDataObjectRefErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
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
    public void testInnerAbstractMultiReturnResponseReceived(MALMessageHeader msgHeader,
            UOctet out1,
            Element out2,
            Element out3,
            UInteger out4,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testInnerAbstractMultiReturn.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testInnerAbstractMultiReturnErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testPolymorphicAbstractCompositeList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testPolymorphicAbstractCompositeListResponseReceived(MALMessageHeader msgHeader,
            AbstractCompositeList output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testPolymorphicAbstractCompositeList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testPolymorphicAbstractCompositeListErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testPolymorphicMalCompositeList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testPolymorphicMalCompositeListResponseReceived(MALMessageHeader msgHeader,
            CompositeList output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testPolymorphicMalCompositeList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testPolymorphicMalCompositeListErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation testPolymorphicMalElementList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output1 The output1 field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testPolymorphicMalElementListResponseReceived(MALMessageHeader msgHeader,
            HeterogeneousList output1,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testPolymorphicMalElementList.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testPolymorphicMalElementListErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
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
    public void testPolymorphicObjectRefTypesResponseReceived(MALMessageHeader msgHeader,
            Garage output1,
            ObjectRefList output2,
            ObjectRefList output3,
            ObjectRefList output4,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation testPolymorphicObjectRefTypes.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void testPolymorphicObjectRefTypesErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation createObject.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void createObjectResponseReceived(MALMessageHeader msgHeader,
            ObjectRef<Auto> output,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation createObject.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void createObjectErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation createObjectFromFields.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void createObjectFromFieldsResponseReceived(MALMessageHeader msgHeader,
            ObjectRef<Auto> output,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation createObjectFromFields.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void createObjectFromFieldsErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation deleteObject.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deleteObjectAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation deleteObject.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void deleteObjectErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation getObject.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param output The output field.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getObjectResponseReceived(MALMessageHeader msgHeader,
            Auto output,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation getObject.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void getObjectErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case DataTestServiceInfo._SETTESTDATAOFFSET_OP_NUMBER:
            setTestDataOffsetAckReceived(msgHeader, qosProperties);
            break;
          case DataTestServiceInfo._DELETEOBJECT_OP_NUMBER:
            deleteObjectAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void submitErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case DataTestServiceInfo._SETTESTDATAOFFSET_OP_NUMBER:
            setTestDataOffsetErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._DELETEOBJECT_OP_NUMBER:
            deleteObjectErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void requestResponseReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case DataTestServiceInfo._TESTDATA_OP_NUMBER:
            testDataResponseReceived(msgHeader,
                (Element) body.getBodyElement(0, null), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATABLOB_OP_NUMBER:
            testDataBlobResponseReceived(msgHeader,
                (Blob) body.getBodyElement(0, new Blob()), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATABOOLEAN_OP_NUMBER:
            testDataBooleanResponseReceived(msgHeader,
                (body.getBodyElement(0, new Union(Boolean.FALSE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Boolean.FALSE))).getBooleanValue(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATADOUBLE_OP_NUMBER:
            testDataDoubleResponseReceived(msgHeader,
                (body.getBodyElement(0, new Union(Double.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Double.MAX_VALUE))).getDoubleValue(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATADURATION_OP_NUMBER:
            testDataDurationResponseReceived(msgHeader,
                (Duration) body.getBodyElement(0, new Duration()), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATAFINETIME_OP_NUMBER:
            testDataFineTimeResponseReceived(msgHeader,
                (FineTime) body.getBodyElement(0, new FineTime()), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATAFLOAT_OP_NUMBER:
            testDataFloatResponseReceived(msgHeader,
                (body.getBodyElement(0, new Union(Float.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Float.MAX_VALUE))).getFloatValue(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATAIDENTIFIER_OP_NUMBER:
            testDataIdentifierResponseReceived(msgHeader,
                (Identifier) body.getBodyElement(0, new Identifier()), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATAINTEGER_OP_NUMBER:
            testDataIntegerResponseReceived(msgHeader,
                (body.getBodyElement(0, new Union(Integer.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Integer.MAX_VALUE))).getIntegerValue(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATALONG_OP_NUMBER:
            testDataLongResponseReceived(msgHeader,
                (body.getBodyElement(0, new Union(Long.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Long.MAX_VALUE))).getLongValue(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATAOCTET_OP_NUMBER:
            testDataOctetResponseReceived(msgHeader,
                (body.getBodyElement(0, new Union(Byte.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Byte.MAX_VALUE))).getOctetValue(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATASHORT_OP_NUMBER:
            testDataShortResponseReceived(msgHeader,
                (body.getBodyElement(0, new Union(Short.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Short.MAX_VALUE))).getShortValue(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATASTRING_OP_NUMBER:
            testDataStringResponseReceived(msgHeader,
                (body.getBodyElement(0, new Union("")) == null) ? null : ((Union) body.getBodyElement(0, new Union(""))).getStringValue(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATATIME_OP_NUMBER:
            testDataTimeResponseReceived(msgHeader,
                (Time) body.getBodyElement(0, new Time()), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATAURI_OP_NUMBER:
            testDataURIResponseReceived(msgHeader,
                (URI) body.getBodyElement(0, new URI()), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATACOMPOSITE_OP_NUMBER:
            testDataCompositeResponseReceived(msgHeader,
                (Assertion) body.getBodyElement(0, new Assertion()), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATAENUMERATION_OP_NUMBER:
            testDataEnumerationResponseReceived(msgHeader,
                (SessionType) body.getBodyElement(0, SessionType.LIVE), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATALIST_OP_NUMBER:
            testDataListResponseReceived(msgHeader,
                (AssertionList) body.getBodyElement(0, new AssertionList()), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATAUINTEGER_OP_NUMBER:
            testDataUIntegerResponseReceived(msgHeader,
                (UInteger) body.getBodyElement(0, new UInteger()), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATAULONG_OP_NUMBER:
            testDataULongResponseReceived(msgHeader,
                (ULong) body.getBodyElement(0, new ULong()), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATAUOCTET_OP_NUMBER:
            testDataUOctetResponseReceived(msgHeader,
                (UOctet) body.getBodyElement(0, new UOctet()), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATAUSHORT_OP_NUMBER:
            testDataUShortResponseReceived(msgHeader,
                (UShort) body.getBodyElement(0, new UShort()), qosProperties);
            break;
          case DataTestServiceInfo._TESTEXPLICITMULTIRETURN_OP_NUMBER:
            testExplicitMultiReturnResponseReceived(msgHeader,
                (UOctet) body.getBodyElement(0, new UOctet()),
                (UShort) body.getBodyElement(1, new UShort()),
                (UInteger) body.getBodyElement(2, new UInteger()),
                (ULong) body.getBodyElement(3, new ULong()), qosProperties);
            break;
          case DataTestServiceInfo._TESTABSTRACTMULTIRETURN_OP_NUMBER:
            testAbstractMultiReturnResponseReceived(msgHeader,
                (UOctet) body.getBodyElement(0, new UOctet()),
                (UShort) body.getBodyElement(1, new UShort()),
                (UInteger) body.getBodyElement(2, new UInteger()),
                (Element) body.getBodyElement(3, null), qosProperties);
            break;
          case DataTestServiceInfo._TESTEMPTYBODY_OP_NUMBER:
            testEmptyBodyResponseReceived(msgHeader, qosProperties);
            break;
          case DataTestServiceInfo._TESTMALATTRIBUTE_OP_NUMBER:
            testMalAttributeResponseReceived(msgHeader,
                (Attribute) body.getBodyElement(0, null), qosProperties);
            break;
          case DataTestServiceInfo._TESTMALCOMPOSITE_OP_NUMBER:
            testMalCompositeResponseReceived(msgHeader,
                (Composite) body.getBodyElement(0, null), qosProperties);
            break;
          case DataTestServiceInfo._TESTABSTRACTCOMPOSITE_OP_NUMBER:
            testAbstractCompositeResponseReceived(msgHeader,
                (TestPublish) body.getBodyElement(0, null), qosProperties);
            break;
          case DataTestServiceInfo._TESTMALATTRIBUTELIST_OP_NUMBER:
            testMalAttributeListResponseReceived(msgHeader,
                (AttributeList) body.getBodyElement(0, new AttributeList()), qosProperties);
            break;
          case DataTestServiceInfo._TESTMALELEMENTLIST_OP_NUMBER:
            testMalElementListResponseReceived(msgHeader,
                (HeterogeneousList) body.getBodyElement(0, null), qosProperties);
            break;
          case DataTestServiceInfo._TESTMALCOMPOSITELIST_OP_NUMBER:
            testMalCompositeListResponseReceived(msgHeader,
                (CompositeList) body.getBodyElement(0, new CompositeList()), qosProperties);
            break;
          case DataTestServiceInfo._TESTABSTRACTCOMPOSITELIST_OP_NUMBER:
            testAbstractCompositeListResponseReceived(msgHeader,
                (TestPublishList) body.getBodyElement(0, new TestPublishList()), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATAOBJECTREF_OP_NUMBER:
            testDataObjectRefResponseReceived(msgHeader,
                (ObjectRef<Auto>) body.getBodyElement(0, new ObjectRef<Auto>()), qosProperties);
            break;
          case DataTestServiceInfo._TESTINNERABSTRACTMULTIRETURN_OP_NUMBER:
            testInnerAbstractMultiReturnResponseReceived(msgHeader,
                (UOctet) body.getBodyElement(0, new UOctet()),
                (Element) body.getBodyElement(1, null),
                (Element) body.getBodyElement(2, null),
                (UInteger) body.getBodyElement(3, new UInteger()), qosProperties);
            break;
          case DataTestServiceInfo._TESTPOLYMORPHICABSTRACTCOMPOSITELIST_OP_NUMBER:
            testPolymorphicAbstractCompositeListResponseReceived(msgHeader,
                (AbstractCompositeList) body.getBodyElement(0, new AbstractCompositeList()), qosProperties);
            break;
          case DataTestServiceInfo._TESTPOLYMORPHICMALCOMPOSITELIST_OP_NUMBER:
            testPolymorphicMalCompositeListResponseReceived(msgHeader,
                (CompositeList) body.getBodyElement(0, new CompositeList()), qosProperties);
            break;
          case DataTestServiceInfo._TESTPOLYMORPHICMALELEMENTLIST_OP_NUMBER:
            testPolymorphicMalElementListResponseReceived(msgHeader,
                (HeterogeneousList) body.getBodyElement(0, null), qosProperties);
            break;
          case DataTestServiceInfo._TESTPOLYMORPHICOBJECTREFTYPES_OP_NUMBER:
            testPolymorphicObjectRefTypesResponseReceived(msgHeader,
                (Garage) body.getBodyElement(0, new Garage()),
                (ObjectRefList) body.getBodyElement(1, new ObjectRefList()),
                (ObjectRefList) body.getBodyElement(2, new ObjectRefList()),
                (ObjectRefList) body.getBodyElement(3, new ObjectRefList()), qosProperties);
            break;
          case DataTestServiceInfo._CREATEOBJECT_OP_NUMBER:
            createObjectResponseReceived(msgHeader,
                (ObjectRef<Auto>) body.getBodyElement(0, new ObjectRef<Auto>()), qosProperties);
            break;
          case DataTestServiceInfo._CREATEOBJECTFROMFIELDS_OP_NUMBER:
            createObjectFromFieldsResponseReceived(msgHeader,
                (ObjectRef<Auto>) body.getBodyElement(0, new ObjectRef<Auto>()), qosProperties);
            break;
          case DataTestServiceInfo._GETOBJECT_OP_NUMBER:
            getObjectResponseReceived(msgHeader,
                (Auto) body.getBodyElement(0, null), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void requestErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case DataTestServiceInfo._TESTDATA_OP_NUMBER:
            testDataErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATABLOB_OP_NUMBER:
            testDataBlobErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATABOOLEAN_OP_NUMBER:
            testDataBooleanErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATADOUBLE_OP_NUMBER:
            testDataDoubleErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATADURATION_OP_NUMBER:
            testDataDurationErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATAFINETIME_OP_NUMBER:
            testDataFineTimeErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATAFLOAT_OP_NUMBER:
            testDataFloatErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATAIDENTIFIER_OP_NUMBER:
            testDataIdentifierErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATAINTEGER_OP_NUMBER:
            testDataIntegerErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATALONG_OP_NUMBER:
            testDataLongErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATAOCTET_OP_NUMBER:
            testDataOctetErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATASHORT_OP_NUMBER:
            testDataShortErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATASTRING_OP_NUMBER:
            testDataStringErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATATIME_OP_NUMBER:
            testDataTimeErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATAURI_OP_NUMBER:
            testDataURIErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATACOMPOSITE_OP_NUMBER:
            testDataCompositeErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATAENUMERATION_OP_NUMBER:
            testDataEnumerationErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATALIST_OP_NUMBER:
            testDataListErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATAUINTEGER_OP_NUMBER:
            testDataUIntegerErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATAULONG_OP_NUMBER:
            testDataULongErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATAUOCTET_OP_NUMBER:
            testDataUOctetErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATAUSHORT_OP_NUMBER:
            testDataUShortErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTEXPLICITMULTIRETURN_OP_NUMBER:
            testExplicitMultiReturnErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTABSTRACTMULTIRETURN_OP_NUMBER:
            testAbstractMultiReturnErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTEMPTYBODY_OP_NUMBER:
            testEmptyBodyErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTMALATTRIBUTE_OP_NUMBER:
            testMalAttributeErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTMALCOMPOSITE_OP_NUMBER:
            testMalCompositeErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTABSTRACTCOMPOSITE_OP_NUMBER:
            testAbstractCompositeErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTMALATTRIBUTELIST_OP_NUMBER:
            testMalAttributeListErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTMALELEMENTLIST_OP_NUMBER:
            testMalElementListErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTMALCOMPOSITELIST_OP_NUMBER:
            testMalCompositeListErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTABSTRACTCOMPOSITELIST_OP_NUMBER:
            testAbstractCompositeListErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTDATAOBJECTREF_OP_NUMBER:
            testDataObjectRefErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTINNERABSTRACTMULTIRETURN_OP_NUMBER:
            testInnerAbstractMultiReturnErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTPOLYMORPHICABSTRACTCOMPOSITELIST_OP_NUMBER:
            testPolymorphicAbstractCompositeListErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTPOLYMORPHICMALCOMPOSITELIST_OP_NUMBER:
            testPolymorphicMalCompositeListErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTPOLYMORPHICMALELEMENTLIST_OP_NUMBER:
            testPolymorphicMalElementListErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._TESTPOLYMORPHICOBJECTREFTYPES_OP_NUMBER:
            testPolymorphicObjectRefTypesErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._CREATEOBJECT_OP_NUMBER:
            createObjectErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._CREATEOBJECTFROMFIELDS_OP_NUMBER:
            createObjectFromFieldsErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case DataTestServiceInfo._GETOBJECT_OP_NUMBER:
            getObjectErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

}
