package org.ccsds.moims.mo.malprototype.datatest.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
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
import org.ccsds.moims.mo.malprototype.DataErrorException;
import org.ccsds.moims.mo.malprototype.TestObjectExistsException;
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
 * Interface that providers of the DataTest service must implement to handle
 * the operations of that service.
 */
public interface DataTestHandler {

    /**
     * Implements the operation setTestDataOffset.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws MALException if there is an implementation exception
     */
    void setTestDataOffset(Integer input1,
            MALInteraction interaction) throws MALException;
    /**
     * Implements the operation testData.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    Element testData(Element input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testDataBlob.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    Blob testDataBlob(Blob input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testDataBoolean.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    Boolean testDataBoolean(Boolean input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testDataDouble.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    Double testDataDouble(Double input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testDataDuration.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    Duration testDataDuration(Duration input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testDataFineTime.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    FineTime testDataFineTime(FineTime input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testDataFloat.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    Float testDataFloat(Float input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testDataIdentifier.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    Identifier testDataIdentifier(Identifier input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testDataInteger.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    Integer testDataInteger(Integer input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testDataLong.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    Long testDataLong(Long input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testDataOctet.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    Byte testDataOctet(Byte input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testDataShort.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    Short testDataShort(Short input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testDataString.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    String testDataString(String input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testDataTime.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    Time testDataTime(Time input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testDataURI.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    URI testDataURI(URI input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testDataComposite.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    Assertion testDataComposite(Assertion input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testDataEnumeration.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    SessionType testDataEnumeration(SessionType input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testDataList.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    AssertionList testDataList(AssertionList input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testDataUInteger.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    UInteger testDataUInteger(UInteger input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testDataULong.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    ULong testDataULong(ULong input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testDataUOctet.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    UOctet testDataUOctet(UOctet input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testDataUShort.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    UShort testDataUShort(UShort input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testExplicitMultiReturn.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    TestExplicitMultiReturnResponse testExplicitMultiReturn(UOctet in1,
            UShort in2,
            UInteger in3,
            ULong in4,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testAbstractMultiReturn.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    TestAbstractMultiReturnResponse testAbstractMultiReturn(UOctet in1,
            UShort in2,
            UInteger in3,
            Element in4,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testEmptyBody.
     * 
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    void testEmptyBody(MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testMalAttribute.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    Attribute testMalAttribute(Attribute input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testMalComposite.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    Composite testMalComposite(Composite input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testAbstractComposite.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    TestPublish testAbstractComposite(TestPublish input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testMalAttributeList.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    AttributeList testMalAttributeList(AttributeList input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testMalElementList.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    HeterogeneousList testMalElementList(HeterogeneousList input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testMalCompositeList.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    CompositeList testMalCompositeList(CompositeList input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testAbstractCompositeList.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    TestPublishList testAbstractCompositeList(TestPublishList input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testDataObjectRef.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    ObjectRef<Auto> testDataObjectRef(ObjectRef<Auto> input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testInnerAbstractMultiReturn.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    TestInnerAbstractMultiReturnResponse testInnerAbstractMultiReturn(UOctet in1,
            Element in2,
            Element in3,
            UInteger in4,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testPolymorphicAbstractCompositeList.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    AbstractCompositeList testPolymorphicAbstractCompositeList(AbstractCompositeList input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testPolymorphicMalCompositeList.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    CompositeList testPolymorphicMalCompositeList(CompositeList input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testPolymorphicMalElementList.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    HeterogeneousList testPolymorphicMalElementList(HeterogeneousList input1,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation testPolymorphicObjectRefTypes.
     * 
     * @param garage The garage field.
     * @param porsches The porsches field.
     * @param autos The autos field.
     * @param elements The elements field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    TestPolymorphicObjectRefTypesResponse testPolymorphicObjectRefTypes(Garage garage,
            ObjectRefList porsches,
            ObjectRefList autos,
            ObjectRefList elements,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation createObject.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws TestObjectExistsException Data interoperability error
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    ObjectRef<Auto> createObject(Auto input,
            MALInteraction interaction) throws TestObjectExistsException, DataErrorException, MALException;
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
     * @throws TestObjectExistsException Data interoperability error
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    ObjectRef<Auto> createObjectFromFields(Long autoType,
            Identifier key,
            Boolean update,
            String engine,
            String chassis,
            StringList windows,
            MALInteraction interaction) throws TestObjectExistsException, DataErrorException, MALException;
    /**
     * Implements the operation deleteObject.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    void deleteObject(ObjectRef<Auto> input,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Implements the operation getObject.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws DataErrorException Data interoperability error
     * @throws MALException if there is an implementation exception
     */
    Auto getObject(ObjectRef<Auto> input,
            MALInteraction interaction) throws DataErrorException, MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(DataTestSkeleton skeleton);
}
