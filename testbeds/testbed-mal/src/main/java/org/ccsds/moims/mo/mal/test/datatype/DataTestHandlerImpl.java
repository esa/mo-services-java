/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Test bed
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * ----------------------------------------------------------------------------
 */
package org.ccsds.moims.mo.mal.test.datatype;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.malprototype.MALPrototypeHelper;
import org.ccsds.moims.mo.malprototype.datatest.body.TestAbstractMultiReturnResponse;
import org.ccsds.moims.mo.malprototype.datatest.body.TestExplicitMultiReturnResponse;
import org.ccsds.moims.mo.malprototype.datatest.body.TestInnerAbstractMultiReturnResponse;
import org.ccsds.moims.mo.malprototype.datatest.body.TestPolymorphicObjectRefTypesResponse;
import org.ccsds.moims.mo.malprototype.datatest.provider.DataTestInheritanceSkeleton;
import org.ccsds.moims.mo.malprototype.structures.TestPublish;
import org.ccsds.moims.mo.malprototype.structures.TestPublishList;
import org.ccsds.moims.mo.malprototype.structures.Assertion;
import org.ccsds.moims.mo.malprototype.structures.AssertionList;
import org.ccsds.moims.mo.malprototype.structures.AbstractCompositeList;
import org.ccsds.moims.mo.malprototype.structures.Auto;
import org.ccsds.moims.mo.malprototype.structures.Garage;
import org.ccsds.moims.mo.malprototype.structures.Lamborghini;
import org.ccsds.moims.mo.malprototype.structures.Porsche;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

/**
 *
 */
public class DataTestHandlerImpl extends DataTestInheritanceSkeleton {

    private int testIndex = 0;

    @Override
    public void setTestDataOffset(Integer _Integer, MALInteraction interaction) throws MALException {
        int newIndex = 0;

        if (_Integer != null && _Integer > 0) {
            newIndex = _Integer.intValue();
        }

        testIndex = newIndex;
    }

    @Override
    public Element testData(Element rcvdValue, MALInteraction interaction) throws MALInteractionException {
        int i = testIndex++;

        _testDataValue(TestData.testAll.get(i), rcvdValue, "data test at step: " + String.valueOf(i));

        return rcvdValue;
    }

    @Override
    public Blob testDataBlob(Blob rcvdValue, MALInteraction interaction) throws MALInteractionException, MALException {
        LoggingBase.logMessage("testDataBlob: " + new String(rcvdValue.getValue()));

        if ((rcvdValue != null) && (rcvdValue.getValue().length != TestData.testBlob.getValue().length)) {
            _testDataValue(new Blob(new File(new String(rcvdValue.getValue())).toURI().toString()), rcvdValue, "Blob file test");
        } else {
            _testDataValue(TestData.testBlob, rcvdValue, "Blob buffer test");
        }

        return rcvdValue;
    }

    @Override
    public Boolean testDataBoolean(Boolean rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testBoolean, rcvdValue, "Boolean test");
        return rcvdValue;
    }

    @Override
    public Double testDataDouble(Double rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testDouble, rcvdValue, "Double test");
        return rcvdValue;
    }

    @Override
    public Duration testDataDuration(Duration rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testDuration, rcvdValue, "Duration test");
        return rcvdValue;
    }

    @Override
    public FineTime testDataFineTime(FineTime rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testFineTime, rcvdValue, "FineTime test");
        return rcvdValue;
    }

    @Override
    public Float testDataFloat(Float rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testFloat, rcvdValue, "Float test");
        return rcvdValue;
    }

    @Override
    public Identifier testDataIdentifier(Identifier rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testIdentifier, rcvdValue, "Identifier test");
        return rcvdValue;
    }

    @Override
    public Integer testDataInteger(Integer rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testInteger, rcvdValue, "Integer test");
        return rcvdValue;
    }

    @Override
    public Long testDataLong(Long rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testLong, rcvdValue, "Long test");
        return rcvdValue;
    }

    @Override
    public Byte testDataOctet(Byte rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testOctet, rcvdValue, "Byte test");
        return rcvdValue;
    }

    @Override
    public Short testDataShort(Short rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testShort, rcvdValue, "Short test");
        return rcvdValue;
    }

    @Override
    public String testDataString(String rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testString, rcvdValue, "String test");
        return rcvdValue;
    }

    @Override
    public Time testDataTime(Time rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testTime, rcvdValue, "Time test");
        return rcvdValue;
    }

    @Override
    public URI testDataURI(URI rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testURI, rcvdValue, "URI test");
        return rcvdValue;
    }

    @Override
    public Assertion testDataComposite(Assertion rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testComposite, rcvdValue, "Composite test");
        return rcvdValue;
    }

    @Override
    public SessionType testDataEnumeration(SessionType rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testEnumeration, rcvdValue, "Enumeration test");
        return rcvdValue;
    }

    @Override
    public AssertionList testDataList(AssertionList rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testList, rcvdValue, "List test");
        return rcvdValue;
    }

    @Override
    public UOctet testDataUOctet(UOctet rcvdValue, MALInteraction interaction) throws MALInteractionException, MALException {
        _testDataValue(TestData.testUOctet, rcvdValue, "UOctet test");
        return rcvdValue;
    }

    @Override
    public UShort testDataUShort(UShort rcvdValue, MALInteraction interaction) throws MALInteractionException, MALException {
        _testDataValue(TestData.testUShort, rcvdValue, "UShort test");
        return rcvdValue;
    }

    @Override
    public UInteger testDataUInteger(UInteger rcvdValue, MALInteraction interaction) throws MALInteractionException, MALException {
        _testDataValue(TestData.testUInteger, rcvdValue, "UInteger test");
        return rcvdValue;
    }

    @Override
    public ULong testDataULong(ULong rcvdValue, MALInteraction interaction) throws MALInteractionException, MALException {
        _testDataValue(TestData.testULong, rcvdValue, "ULong test");
        return rcvdValue;
    }

    @Override
    public ObjectRef<Auto> testDataObjectRef(ObjectRef<Auto> rcvdValue,
            MALInteraction interaction) throws MALInteractionException, MALException {
        _testDataValue(TestData.testObjectRef, rcvdValue, "ObjectRef test");
        return rcvdValue;
    }

    @Override
    public TestExplicitMultiReturnResponse testExplicitMultiReturn(UOctet _UOctet0, UShort _UShort1,
            UInteger _UInteger2, ULong _ULong3, MALInteraction interaction) throws MALInteractionException, MALException {
        if (54 == testIndex) {
            _testDataValue(TestData.testUOctet, _UOctet0, "Explicit multi test part 1");
            _testDataValue(TestData.testUShort, _UShort1, "Explicit multi test part 2");
            _testDataValue(TestData.testUInteger, _UInteger2, "Explicit multi test part 3");
            _testDataValue(TestData.testULong, _ULong3, "Explicit multi test part 4");
        } else {
            _testDataValue(TestData.testUOctet, _UOctet0, "Null multi test part 1");
            _testDataValue(TestData.testUShort, _UShort1, "Null multi test part 2");
            _testDataValue(TestData.testUInteger, _UInteger2, "Null multi test part 3");
            _testDataValue(null, _ULong3, "Null multi test part 4");
        }
        return new TestExplicitMultiReturnResponse(_UOctet0, _UShort1, _UInteger2, _ULong3);
    }

    @Override
    public TestAbstractMultiReturnResponse testAbstractMultiReturn(UOctet _UOctet0, UShort _UShort1, UInteger _UInteger2,
            Element _Element3, MALInteraction interaction) throws MALInteractionException, MALException {
        _testDataValue(TestData.testUOctet, _UOctet0, "Abstract multi test part 1");
        _testDataValue(TestData.testUShort, _UShort1, "Abstract multi test part 2");
        _testDataValue(TestData.testUInteger, _UInteger2, "Abstract multi test part 3");
        _testDataValue(TestData.testULong, _Element3, "Abstract multi test part 4");
        return new TestAbstractMultiReturnResponse(_UOctet0, _UShort1, _UInteger2, _Element3);
    }

    @Override
    public TestInnerAbstractMultiReturnResponse testInnerAbstractMultiReturn(UOctet _UOctet0, Element _Element1,
            Element _Element2, UInteger _UInteger3, MALInteraction interaction) throws MALInteractionException, MALException {
        _testDataValue(TestData.testUOctet, _UOctet0, "Abstract multi test part 1");
        _testDataValue(TestData.testULong, _Element1, "Abstract multi test part 2");
        _testDataValue(TestData.testUShort, _Element2, "Abstract multi test part 3");
        _testDataValue(TestData.testUInteger, _UInteger3, "Abstract multi test part 4");
        return new TestInnerAbstractMultiReturnResponse(_UOctet0, _Element1, _Element2, _UInteger3);
    }

    protected static void _testDataValue(Object testValue, Object rcvdValue, String exString) throws MALInteractionException {
        LoggingBase.logMessage("DataTestHandlerImpl:" + exString + " : " + testValue + " : " + rcvdValue);

        if (null != testValue) {
            if (!testValue.equals(rcvdValue)) {
                // decoding must have failed
                throw new MALInteractionException(new MALStandardError(MALPrototypeHelper.DATA_ERROR_ERROR_NUMBER,
                        new Union("Failed comparison in provider of " + exString
                                + ", type " + testValue.getClass() + ",\nexpected "
                                + String.valueOf(testValue) + "\n received "
                                + String.valueOf(rcvdValue))));
            }
        } else {
            if (rcvdValue != null) {
                // decoding must have failed
                throw new MALInteractionException(new MALStandardError(MALPrototypeHelper.DATA_ERROR_ERROR_NUMBER,
                        new Union("Failed comparison in provider of " + exString
                                + ", type should be null but is " + rcvdValue.getClass())));
            }
        }
    }

    @Override
    public void testEmptyBody(MALInteraction mali) throws MALInteractionException, MALException {
        // Do nothing
    }

    @Override
    public Attribute testMalAttribute(Attribute atrbt,
            MALInteraction mali) throws MALInteractionException, MALException {
        return atrbt;
    }

    @Override
    public Composite testMalComposite(Composite cmpst,
            MALInteraction mali) throws MALInteractionException, MALException {
        return cmpst;
    }

    @Override
    public TestPublish testAbstractComposite(TestPublish tp,
            MALInteraction mali) throws MALInteractionException, MALException {
        return tp;
    }

    @Override
    public AttributeList testMalAttributeList(AttributeList al,
            MALInteraction mali) throws MALInteractionException, MALException {
        return al;
    }

    @Override
    public ElementList testMalElementList(ElementList el,
            MALInteraction mali) throws MALInteractionException, MALException {
        return el;
    }

    @Override
    public CompositeList testMalCompositeList(CompositeList cl,
            MALInteraction mali) throws MALInteractionException, MALException {
        return cl;
    }

    @Override
    public TestPublishList testAbstractCompositeList(TestPublishList tpl,
            MALInteraction mali) throws MALInteractionException, MALException {
        return tpl;
    }

    // Operations related to the MOObject assertions
    public static final String testDomainAsString = "CCSDS.MAL.prototype";
    public static final IdentifierList testDomain = new IdentifierList(Arrays.stream(testDomainAsString.split("\\."))
            .map(s -> new Identifier(s))
            .collect(Collectors.toCollection(ArrayList<Identifier>::new)));
    HashMap<ObjectIdentity, Auto> autoList = new HashMap<>();

    @Override
    public ObjectRef<Auto> createObject(Auto auto, MALInteraction interaction) throws MALInteractionException, MALException {
        if (auto == null) {
            throw new MALInteractionException(new MALStandardError(MALPrototypeHelper.TEST_ERROR_ERROR_NUMBER,
                    new Union("Unexpected exception - null object value.")));
        }
        // MO Objects have a unique and an immutable identity
        ObjectIdentity autoId0 = new ObjectIdentity(auto.getObjectIdentity().getDomainId(),
                auto.getObjectIdentity().getAreaId(),
                auto.getObjectIdentity().getTypeId(),
                auto.getObjectIdentity().getKeyId(),
                new UInteger(0));
        Auto lastAuto = autoList.get(autoId0);
        long versionUpdate = auto.getObjectIdentity().getVersionId().getValue();
        if (lastAuto == null) {
            if (versionUpdate != 1) {
                throw new MALInteractionException(new MALStandardError(MALPrototypeHelper.DATA_ERROR_ERROR_NUMBER,
                        new Union("Wrong version for new object.")));
            }
        } else {
            versionUpdate -= lastAuto.getObjectIdentity().getVersionId().getValue();
            if (versionUpdate == 0) {
                throw new MALInteractionException(new MALStandardError(MALPrototypeHelper.TEST_OBJECT_EXISTS_ERROR_NUMBER,
                        new Union("Object already exists.")));
            } else if (versionUpdate != 1) {
                throw new MALInteractionException(new MALStandardError(MALPrototypeHelper.DATA_ERROR_ERROR_NUMBER,
                        new Union("Wrong version for updated object.")));
            }
        }
        // check the identity type
        // TODO improve Java mapping to allow simpler checking code
        String expectedAutoType = null;
        if (auto instanceof Lamborghini) {
            expectedAutoType = "Lamborghini";
        } else if (auto instanceof Porsche) {
            expectedAutoType = "Porsche";
        } else {
            throw new MALInteractionException(new MALStandardError(MALPrototypeHelper.DATA_ERROR_ERROR_NUMBER,
                    new Union("Unexpected Auto value.")));
        }
        if (!expectedAutoType.equals(auto.getObjectIdentity().getTypeId().getValue())) {
            throw new MALInteractionException(new MALStandardError(MALPrototypeHelper.DATA_ERROR_ERROR_NUMBER,
                    new Union("Illegal type field value.")));
        }

        autoList.put(autoId0, auto);
        autoList.put(auto.getObjectIdentity(), auto);

        return auto.getObjectRef();
    }

    public ObjectRef<Auto> createObjectFromFields(Identifier autoType, Identifier key,
            Boolean update, String engine, String chassis, StringList windows,
            MALInteraction interaction) throws MALInteractionException, MALException {
        ObjectIdentity autoId0 = new ObjectIdentity(testDomain,
                MALPrototypeHelper.MALPROTOTYPE_AREA_NAME,
                autoType,
                key,
                new UInteger(0));
        Auto auto = autoList.get(autoId0);
        if (auto != null && !update.booleanValue()) {
            throw new MALInteractionException(new MALStandardError(MALPrototypeHelper.DATA_ERROR_ERROR_NUMBER,
                    new Union("Object already exists.")));
        }
        ObjectIdentity autoId = new ObjectIdentity(autoId0.getDomainId(),
                autoId0.getAreaId(),
                autoId0.getTypeId(),
                autoId0.getKeyId(),
                auto == null ? new UInteger(1) : new UInteger(auto.getObjectIdentity().getVersionId().getValue() + 1));

        if (new Identifier("Lamborghini").equals(autoType)) {
            auto = new Lamborghini(autoId, engine, chassis, windows);
        } else if (new Identifier("Porsche").equals(autoType)) {
            auto = new Porsche(autoId, engine, chassis, windows);
        } else {
            throw new MALInteractionException(new MALStandardError(MALPrototypeHelper.DATA_ERROR_ERROR_NUMBER,
                    new Union("Unexpected Auto value.")));
        }

        return createObject(auto, interaction);
    }

    public void deleteObject(ObjectRef<Auto> autoRef, MALInteraction interaction) throws MALInteractionException, MALException {
        ObjectIdentity autoId = new ObjectIdentity(
                autoRef.getDomain(),
                autoRef.getArea(),
                autoRef.getType(),
                autoRef.getKey(),
                autoRef.getObjectVersion());
        Auto auto = autoList.remove(autoId);

        if (new UInteger(0).equals(autoRef.getObjectVersion())) {
            // remove all versions of the object
            for (long version = auto.getObjectIdentity().getVersionId().getValue(); --version > 0;) {
                ObjectIdentity autoId2 = new ObjectIdentity(
                        autoRef.getDomain(),
                        autoRef.getArea(),
                        autoRef.getType(),
                        autoRef.getKey(),
                        new UInteger(version));

                autoList.remove(autoId2);
            }
        }
    }

    /**
     * Gets an object from its reference.
     *
     * @param autoRef
     */
    @Override
    public Auto getObject(ObjectRef<Auto> autoRef, MALInteraction interaction) throws MALInteractionException, MALException {
        ObjectIdentity autoId = new ObjectIdentity(autoRef.getDomain(),
                autoRef.getArea(),
                autoRef.getType(),
                autoRef.getKey(),
                autoRef.getObjectVersion());

        return autoList.get(autoId);
    }

    @Override
    public AbstractCompositeList testPolymorphicAbstractCompositeList(AbstractCompositeList bacl,
            MALInteraction interaction) throws MALInteractionException, MALException {
        return bacl;
    }

    @Override
    public CompositeList testPolymorphicMalCompositeList(CompositeList cl,
            MALInteraction interaction) throws MALInteractionException, MALException {
        return cl;
    }

    @Override
    public ElementList testPolymorphicMalElementList(ElementList el,
            MALInteraction interaction) throws MALInteractionException, MALException {
        return el;
    }

    @Override
    public TestPolymorphicObjectRefTypesResponse testPolymorphicObjectRefTypes(
            Garage _Garage0, ObjectRefList _Porsche_1, ObjectRefList _Auto_2,
            ObjectRefList _Element_3, MALInteraction interaction) throws MALInteractionException, MALException {
        return new TestPolymorphicObjectRefTypesResponse(_Garage0, _Porsche_1, _Auto_2, _Element_3);
    }
}
