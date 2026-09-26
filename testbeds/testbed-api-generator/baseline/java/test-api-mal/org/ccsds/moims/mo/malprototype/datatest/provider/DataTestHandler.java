package org.ccsds.moims.mo.malprototype.datatest.provider;

/**
 * Interface that providers of the DataTest service must implement to handle
 * the operations of that service.
 */
public interface DataTestHandler {

    /**
     * Implements the operation setTestDataOffset.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void setTestDataOffset(Integer input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testData.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.Element testData(org.ccsds.moims.mo.mal.structures.Element input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDataBlob.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.Blob testDataBlob(org.ccsds.moims.mo.mal.structures.Blob input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDataBoolean.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    Boolean testDataBoolean(Boolean input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDataDouble.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    Double testDataDouble(Double input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDataDuration.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.Duration testDataDuration(org.ccsds.moims.mo.mal.structures.Duration input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDataFineTime.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.FineTime testDataFineTime(org.ccsds.moims.mo.mal.structures.FineTime input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDataFloat.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    Float testDataFloat(Float input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDataIdentifier.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.Identifier testDataIdentifier(org.ccsds.moims.mo.mal.structures.Identifier input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDataInteger.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    Integer testDataInteger(Integer input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDataLong.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    Long testDataLong(Long input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDataOctet.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    Byte testDataOctet(Byte input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDataShort.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    Short testDataShort(Short input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDataString.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    String testDataString(String input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDataTime.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.Time testDataTime(org.ccsds.moims.mo.mal.structures.Time input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDataURI.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.URI testDataURI(org.ccsds.moims.mo.mal.structures.URI input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDataComposite.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.malprototype.structures.Assertion testDataComposite(org.ccsds.moims.mo.malprototype.structures.Assertion input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDataEnumeration.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.SessionType testDataEnumeration(org.ccsds.moims.mo.mal.structures.SessionType input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDataList.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.malprototype.structures.AssertionList testDataList(org.ccsds.moims.mo.malprototype.structures.AssertionList input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDataUInteger.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.UInteger testDataUInteger(org.ccsds.moims.mo.mal.structures.UInteger input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDataULong.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.ULong testDataULong(org.ccsds.moims.mo.mal.structures.ULong input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDataUOctet.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.UOctet testDataUOctet(org.ccsds.moims.mo.mal.structures.UOctet input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDataUShort.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.UShort testDataUShort(org.ccsds.moims.mo.mal.structures.UShort input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testExplicitMultiReturn.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.malprototype.datatest.body.TestExplicitMultiReturnResponse testExplicitMultiReturn(org.ccsds.moims.mo.mal.structures.UOctet in1,
            org.ccsds.moims.mo.mal.structures.UShort in2,
            org.ccsds.moims.mo.mal.structures.UInteger in3,
            org.ccsds.moims.mo.mal.structures.ULong in4,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testAbstractMultiReturn.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.malprototype.datatest.body.TestAbstractMultiReturnResponse testAbstractMultiReturn(org.ccsds.moims.mo.mal.structures.UOctet in1,
            org.ccsds.moims.mo.mal.structures.UShort in2,
            org.ccsds.moims.mo.mal.structures.UInteger in3,
            org.ccsds.moims.mo.mal.structures.Element in4,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testEmptyBody.
     * 
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void testEmptyBody(org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testMalAttribute.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.Attribute testMalAttribute(org.ccsds.moims.mo.mal.structures.Attribute input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testMalComposite.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.Composite testMalComposite(org.ccsds.moims.mo.mal.structures.Composite input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testAbstractComposite.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.malprototype.structures.TestPublish testAbstractComposite(org.ccsds.moims.mo.malprototype.structures.TestPublish input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testMalAttributeList.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.AttributeList testMalAttributeList(org.ccsds.moims.mo.mal.structures.AttributeList input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testMalElementList.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.HeterogeneousList testMalElementList(org.ccsds.moims.mo.mal.structures.HeterogeneousList input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testMalCompositeList.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.CompositeList testMalCompositeList(org.ccsds.moims.mo.mal.structures.CompositeList input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testAbstractCompositeList.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.malprototype.structures.TestPublishList testAbstractCompositeList(org.ccsds.moims.mo.malprototype.structures.TestPublishList input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDataObjectRef.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto> testDataObjectRef(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto> input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testInnerAbstractMultiReturn.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.malprototype.datatest.body.TestInnerAbstractMultiReturnResponse testInnerAbstractMultiReturn(org.ccsds.moims.mo.mal.structures.UOctet in1,
            org.ccsds.moims.mo.mal.structures.Element in2,
            org.ccsds.moims.mo.mal.structures.Element in3,
            org.ccsds.moims.mo.mal.structures.UInteger in4,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testPolymorphicAbstractCompositeList.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.malprototype.structures.AbstractCompositeList testPolymorphicAbstractCompositeList(org.ccsds.moims.mo.malprototype.structures.AbstractCompositeList input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testPolymorphicMalCompositeList.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.CompositeList testPolymorphicMalCompositeList(org.ccsds.moims.mo.mal.structures.CompositeList input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testPolymorphicMalElementList.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.HeterogeneousList testPolymorphicMalElementList(org.ccsds.moims.mo.mal.structures.HeterogeneousList input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testPolymorphicObjectRefTypes.
     * 
     * @param garage The garage field.
     * @param porsches The porsches field.
     * @param autos The autos field.
     * @param elements The elements field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.malprototype.datatest.body.TestPolymorphicObjectRefTypesResponse testPolymorphicObjectRefTypes(org.ccsds.moims.mo.malprototype.structures.Garage garage,
            org.ccsds.moims.mo.mal.structures.ObjectRefList porsches,
            org.ccsds.moims.mo.mal.structures.ObjectRefList autos,
            org.ccsds.moims.mo.mal.structures.ObjectRefList elements,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation createObject.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.TestObjectExistsException Data interoperability error
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto> createObject(org.ccsds.moims.mo.malprototype.structures.Auto input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.TestObjectExistsException, org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation createObjectFromFields.
     * 
     * @param autoType The autoType field.
     * @param key The key field.
     * @param update The update field.
     * @param engine The engine field.
     * @param chassis The chassis field.
     * @param windows The windows field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.TestObjectExistsException Data interoperability error
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto> createObjectFromFields(Long autoType,
            org.ccsds.moims.mo.mal.structures.Identifier key,
            Boolean update,
            String engine,
            String chassis,
            org.ccsds.moims.mo.mal.structures.StringList windows,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.TestObjectExistsException, org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation deleteObject.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void deleteObject(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto> input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation getObject.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.DataErrorException Data interoperability error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.malprototype.structures.Auto getObject(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto> input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.DataErrorException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.malprototype.datatest.provider.DataTestSkeleton skeleton);
}
