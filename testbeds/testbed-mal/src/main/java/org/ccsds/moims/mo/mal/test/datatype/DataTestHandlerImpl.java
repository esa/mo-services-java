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
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.malprototype.datatest.DataTestHelper;
import org.ccsds.moims.mo.malprototype.datatest.body.TestAbstractMultiReturnResponse;
import org.ccsds.moims.mo.malprototype.datatest.body.TestExplicitMultiReturnResponse;
import org.ccsds.moims.mo.malprototype.datatest.body.TestInnerAbstractMultiReturnResponse;
import org.ccsds.moims.mo.malprototype.datatest.provider.DataTestInheritanceSkeleton;
import org.ccsds.moims.mo.malprototype.structures.TestPublish;
import org.ccsds.moims.mo.malprototype.structures.TestPublishList;
import org.ccsds.moims.mo.malprototype.structures.Assertion;
import org.ccsds.moims.mo.malprototype.structures.AssertionList;
import org.ccsds.moims.mo.malprototype.structures.AbstractCompositeList;
import org.ccsds.moims.mo.malprototype.structures.Auto;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

/**
 *
 */
public class DataTestHandlerImpl extends DataTestInheritanceSkeleton {

    private int testIndex = 0;

    public void setTestDataOffset(Integer _Integer, MALInteraction interaction) throws MALException {
        int newIndex = 0;

        if ((null != _Integer) && (_Integer.intValue() > 0)) {
            newIndex = _Integer.intValue();
        }

        testIndex = newIndex;
    }

    public Element testData(Element rcvdValue, MALInteraction interaction) throws MALInteractionException {
        int i = testIndex++;

        _testDataValue(TestData.testAll.get(i), rcvdValue, "data test at step: " + String.valueOf(i));

        return rcvdValue;
    }

    public Blob testDataBlob(Blob rcvdValue, MALInteraction interaction) throws MALInteractionException, MALException {
        LoggingBase.logMessage("testDataBlob: " + new String(rcvdValue.getValue()));

        if ((rcvdValue != null) && (rcvdValue.getValue().length != TestData.testBlob.getValue().length)) {
            _testDataValue(new Blob(new File(new String(rcvdValue.getValue())).toURI().toString()), rcvdValue, "Blob file test");
        } else {
            _testDataValue(TestData.testBlob, rcvdValue, "Blob buffer test");
        }

        return rcvdValue;
    }

    public Boolean testDataBoolean(Boolean rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testBoolean, rcvdValue, "Boolean test");
        return rcvdValue;
    }

    public Double testDataDouble(Double rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testDouble, rcvdValue, "Double test");
        return rcvdValue;
    }

    public Duration testDataDuration(Duration rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testDuration, rcvdValue, "Duration test");
        return rcvdValue;
    }

    public FineTime testDataFineTime(FineTime rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testFineTime, rcvdValue, "FineTime test");
        return rcvdValue;
    }

    public Float testDataFloat(Float rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testFloat, rcvdValue, "Float test");
        return rcvdValue;
    }

    public Identifier testDataIdentifier(Identifier rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testIdentifier, rcvdValue, "Identifier test");
        return rcvdValue;
    }

    public Integer testDataInteger(Integer rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testInteger, rcvdValue, "Integer test");
        return rcvdValue;
    }

    public Long testDataLong(Long rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testLong, rcvdValue, "Long test");
        return rcvdValue;
    }

    public Byte testDataOctet(Byte rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testOctet, rcvdValue, "Byte test");
        return rcvdValue;
    }

    public Short testDataShort(Short rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testShort, rcvdValue, "Short test");
        return rcvdValue;
    }

    public String testDataString(String rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testString, rcvdValue, "String test");
        return rcvdValue;
    }

    public Time testDataTime(Time rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testTime, rcvdValue, "Time test");
        return rcvdValue;
    }

    public URI testDataURI(URI rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testURI, rcvdValue, "URI test");
        return rcvdValue;
    }

    public Assertion testDataComposite(Assertion rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testComposite, rcvdValue, "Composite test");
        return rcvdValue;
    }

    public SessionType testDataEnumeration(SessionType rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testEnumeration, rcvdValue, "Enumeration test");
        return rcvdValue;
    }

    public AssertionList testDataList(AssertionList rcvdValue, MALInteraction interaction) throws MALInteractionException {
        _testDataValue(TestData.testList, rcvdValue, "List test");
        return rcvdValue;
    }

    public UOctet testDataUOctet(UOctet rcvdValue, MALInteraction interaction) throws MALInteractionException, MALException {
        _testDataValue(TestData.testUOctet, rcvdValue, "UOctet test");
        return rcvdValue;
    }

    public UShort testDataUShort(UShort rcvdValue, MALInteraction interaction) throws MALInteractionException, MALException {
        _testDataValue(TestData.testUShort, rcvdValue, "UShort test");
        return rcvdValue;
    }

    public UInteger testDataUInteger(UInteger rcvdValue, MALInteraction interaction) throws MALInteractionException, MALException {
        _testDataValue(TestData.testUInteger, rcvdValue, "UInteger test");
        return rcvdValue;
    }

    public ULong testDataULong(ULong rcvdValue, MALInteraction interaction) throws MALInteractionException, MALException {
        _testDataValue(TestData.testULong, rcvdValue, "ULong test");
        return rcvdValue;
    }

    public ObjectRef<Auto> testDataObjectRef(ObjectRef<Auto> rcvdValue,
            MALInteraction interaction) throws MALInteractionException, MALException {
        _testDataValue(TestData.testObjectRef, rcvdValue, "ObjectRef test");
        return rcvdValue;
    }

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

    public TestAbstractMultiReturnResponse testAbstractMultiReturn(UOctet _UOctet0, UShort _UShort1, UInteger _UInteger2,
            Element _Element3, MALInteraction interaction) throws MALInteractionException, MALException {
        _testDataValue(TestData.testUOctet, _UOctet0, "Abstract multi test part 1");
        _testDataValue(TestData.testUShort, _UShort1, "Abstract multi test part 2");
        _testDataValue(TestData.testUInteger, _UInteger2, "Abstract multi test part 3");
        _testDataValue(TestData.testULong, _Element3, "Abstract multi test part 4");
        return new TestAbstractMultiReturnResponse(_UOctet0, _UShort1, _UInteger2, _Element3);
    }

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
                throw new MALInteractionException(new MALStandardError(DataTestHelper.DATA_ERROR_ERROR_NUMBER,
                        new Union("Failed comparison in provider of " + exString
                                + ", type " + testValue.getClass() + ", expected "
                                + String.valueOf(testValue) + " but received "
                                + String.valueOf(rcvdValue))));
            }
        } else {
            if (null != rcvdValue) {
                // decoding must have failed
                throw new MALInteractionException(new MALStandardError(DataTestHelper.DATA_ERROR_ERROR_NUMBER,
                        new Union("Failed comparison in provider of " + exString
                                + ", type should be null but is " + rcvdValue.getClass())));
            }
        }
    }

    public void testEmptyBody(MALInteraction mali) throws MALInteractionException, MALException {
        // Do nothing
    }

    public Attribute testMalAttribute(Attribute atrbt,
            MALInteraction mali) throws MALInteractionException, MALException {
        return atrbt;
    }

    public Composite testMalComposite(Composite cmpst,
            MALInteraction mali) throws MALInteractionException, MALException {
        return cmpst;
    }

    public TestPublish testAbstractComposite(TestPublish tp,
            MALInteraction mali) throws MALInteractionException, MALException {
        return tp;
    }

    public AttributeList testMalAttributeList(AttributeList al,
            MALInteraction mali) throws MALInteractionException, MALException {
        return al;
    }

    public ElementList testMalElementList(ElementList el,
            MALInteraction mali) throws MALInteractionException, MALException {
        return el;
    }

    public CompositeList testMalCompositeList(CompositeList cl,
            MALInteraction mali) throws MALInteractionException, MALException {
        return cl;
    }

    public TestPublishList testAbstractCompositeList(TestPublishList tpl,
            MALInteraction mali) throws MALInteractionException, MALException {
        return tpl;
    }

    @Override
    public ObjectRef<Auto> createObject(MOObject _MOObject0, MALInteraction interaction) throws MALInteractionException, MALException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ObjectRef<Auto> createObjectFromFields(String _String0, Integer _Integer1, String _String2, Boolean _Boolean3, MALInteraction interaction) throws MALInteractionException, MALException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteObject(ObjectRef<Auto> _Identifier_0, MALInteraction interaction) throws MALInteractionException, MALException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public MOObject getObject(ObjectRef<Auto> _Identifier_0, MALInteraction interaction) throws MALInteractionException, MALException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public AbstractCompositeList testPolymorphicAbstractCompositeList(AbstractCompositeList bacl,
            MALInteraction interaction) throws MALInteractionException, MALException {
        return bacl;
    }

    public CompositeList testPolymorphicMalCompositeList(CompositeList cl,
            MALInteraction interaction) throws MALInteractionException, MALException {
        return cl;
    }

    public ElementList testPolymorphicMalElementList(ElementList el,
            MALInteraction interaction) throws MALInteractionException, MALException {
        return el;
    }
}
