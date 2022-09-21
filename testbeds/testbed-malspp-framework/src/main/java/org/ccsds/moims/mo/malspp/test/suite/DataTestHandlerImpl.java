/* 
 * MAL/SPP Binding for CCSDS Mission Operations Framework
 * Copyright (C) 2015 Deutsches Zentrum für Luft- und Raumfahrt e.V. (DLR).
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package org.ccsds.moims.mo.malspp.test.suite;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.CompositeList;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ElementList;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.ULong;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.malprototype.datatest.DataTestHelper;
import org.ccsds.moims.mo.malprototype.datatest.body.TestAbstractMultiReturnResponse;
import org.ccsds.moims.mo.malprototype.datatest.body.TestExplicitMultiReturnResponse;
import org.ccsds.moims.mo.malprototype.datatest.provider.DataTestInheritanceSkeleton;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublish;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishList;
import org.ccsds.moims.mo.malprototype.structures.Assertion;
import org.ccsds.moims.mo.malprototype.structures.AssertionList;
import org.ccsds.moims.mo.malspp.test.segmentation.MalSppSegmentationTest;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

public class DataTestHandlerImpl extends DataTestInheritanceSkeleton {

  public Element testData(Element rcvdValue, MALInteraction interaction) throws MALInteractionException, MALException {
    LoggingBase.logMessage("testData: small blob");
    _testDataValue(MalSppSegmentationTest.testSmallBlob, rcvdValue, "small blob data test");
    return rcvdValue;
  }

  public Blob testDataBlob(Blob rcvdValue, MALInteraction interaction) throws MALInteractionException, MALException {
    LoggingBase.logMessage("testDataBlob: large blob");
    _testDataValue(MalSppSegmentationTest.testLargeBlob, rcvdValue, "large blob data test");
    return rcvdValue;
  }

  protected static void _testDataValue(Object testValue, Object rcvdValue, String exString) throws MALInteractionException {
    //LoggingBase.logMessage("DataTestHandlerImpl:" + exString + " : " + testValue + " : " + rcvdValue);

    if (null != testValue) {
      if (!testValue.equals(rcvdValue)) {
        // decoding must have failed
        throw new MALInteractionException(new MALStandardError(DataTestHelper.DATA_ERROR_ERROR_NUMBER,
                new Union("Failed comparison in provider of " + exString + ", type " + testValue.getClass() + ", expected " + String.valueOf(testValue) + " but received " + String.valueOf(rcvdValue))));
      }
    } else {
      if (null != rcvdValue) {
        // decoding must have failed
        throw new MALInteractionException(new MALStandardError(DataTestHelper.DATA_ERROR_ERROR_NUMBER,
                new Union("Failed comparison in provider of " + exString + ", type should be null but is " + rcvdValue.getClass())));
      }
    }
  }

  public void setTestDataOffset(Integer _Integer0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public Boolean testDataBoolean(Boolean _Boolean0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public Double testDataDouble(Double _Double0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public Duration testDataDuration(Duration _Duration0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public FineTime testDataFineTime(FineTime _FineTime0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public Float testDataFloat(Float _Float0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public Identifier testDataIdentifier(Identifier _Identifier0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public Integer testDataInteger(Integer _Integer0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public Long testDataLong(Long _Long0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public Byte testDataOctet(Byte _Byte0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public Short testDataShort(Short _Short0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public String testDataString(String rcvdValue, MALInteraction interaction) throws MALInteractionException, MALException {
    // Only handle empty string. Non-empty string should cause error already on consumer side.
    LoggingBase.logMessage("testDataString: empty string");
    _testDataValue(MalSppSegmentationTest.testEmptyString, rcvdValue, "empty string data test");
    return rcvdValue;
  }

  public Time testDataTime(Time _Time0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public URI testDataURI(URI _URI0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public Assertion testDataComposite(Assertion _Assertion0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public SessionType testDataEnumeration(SessionType _SessionType0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public AssertionList testDataList(AssertionList _AssertionList0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public UInteger testDataUInteger(UInteger _UInteger0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public ULong testDataULong(ULong _ULong0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public UOctet testDataUOctet(UOctet _UOctet0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public UShort testDataUShort(UShort _UShort0, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public TestExplicitMultiReturnResponse testExplicitMultiReturn(UOctet _UOctet0, UShort _UShort1, UInteger _UInteger2, ULong _ULong3, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public TestAbstractMultiReturnResponse testAbstractMultiReturn(UOctet _UOctet0, UShort _UShort1, UInteger _UInteger2, Element _Element3, MALInteraction interaction) throws MALInteractionException, MALException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void testEmptyBody(MALInteraction mali) throws MALInteractionException, MALException {
    // Do nothing
  }

  public Attribute testMalAttribute(Attribute atrbt, MALInteraction mali) throws MALInteractionException, MALException {
    return atrbt;
  }

  public Composite testMalComposite(Composite cmpst, MALInteraction mali) throws MALInteractionException, MALException {
    return cmpst;
  }

  public TestPublish testAbstractComposite(TestPublish tp, MALInteraction mali) throws MALInteractionException, MALException {
    return tp;
  }
  
  public AttributeList testMalAttributeList(AttributeList al, MALInteraction mali) throws MALInteractionException, MALException {
    return al;
  }

  public ElementList testMalElementList(ElementList el, MALInteraction mali) throws MALInteractionException, MALException {
    return el;
  }

  public CompositeList testMalCompositeList(CompositeList cl, MALInteraction mali) throws MALInteractionException, MALException {
    return cl;
  }

  public TestPublishList testAbstractCompositeList(TestPublishList tpl, MALInteraction mali) throws MALInteractionException, MALException {
    return tpl;
  }
  
}
