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

import java.math.BigInteger;
import java.util.Vector;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.malprototype.datatest.body.TestAbstractMultiReturnResponse;
import org.ccsds.moims.mo.malprototype.datatest.body.TestExplicitMultiReturnResponse;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublish;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishRegister;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishUpdate;
import org.ccsds.moims.mo.malprototype.structures.Assertion;
import org.ccsds.moims.mo.malprototype.structures.AssertionList;

/**
 *
 */
public abstract class TestData {

    public static final Duration testDuration = new Duration(100);
    // 1 second = 10^12 picoseconds
    public static final FineTime testFineTime = new FineTime((long) Math.pow(10, 12));
    public static final Identifier testIdentifier = new Identifier("Identifier test");
    public static final Time testTime = new Time(102);
    public static final URI testURI = new URI("http://www.esa.int/");
    public static final Blob testBlob = new Blob(" BLOB test ".getBytes());
    public static final Boolean testBoolean = Boolean.TRUE;
    public static final Byte testOctet = new Byte(Byte.MAX_VALUE);
    public static final Double testDouble = new Double(Double.MAX_VALUE);
    public static final Float testFloat = new Float(Float.MAX_VALUE);
    public static final Integer testInteger = new Integer(Integer.MAX_VALUE);
    public static final UInteger testUInteger = new UInteger(4294967295L);
    public static final Long testLong = new Long(Long.MAX_VALUE);
    public static final Short testShort = new Short(Short.MAX_VALUE);
    public static final String testString = "String test|\\\\|";
    public static final UOctet testUOctet = new UOctet((short) 255);
    public static final ULong testULong = new ULong(new BigInteger("18446744073709551615"));
    public static final UShort testUShort = new UShort(65535);
    public static final ObjectRef testObjectRef = new ObjectRef();

    public static final Assertion testComposite = new Assertion("Test string", "Second test string", Boolean.FALSE);
    public static final SessionType testEnumeration = SessionType.SIMULATION;
    public static final AssertionList testList = new AssertionList();

    public static final Vector testAll = new Vector();
    public static final Vector testAttributes = new Vector();
    public static final Vector testEnumerations = new Vector();
    public static final Vector testComposites = new Vector();
    public static final Vector testAbstracts = new Vector();
    public static final Vector testNulls = new Vector();
    public static final Vector testCompositeWithNulls = new Vector();

    public static final TestExplicitMultiReturnResponse testMultiReturnExplicit = new TestExplicitMultiReturnResponse(testUOctet, testUShort, testUInteger, testULong);
    public static final TestAbstractMultiReturnResponse testMultiReturnAbstract = new TestAbstractMultiReturnResponse(testUOctet, testUShort, testUInteger, testULong);
    public static final TestExplicitMultiReturnResponse testMultiReturnNull = new TestExplicitMultiReturnResponse(testUOctet, testUShort, testUInteger, null);

    public static final int[] testIndexes;

    static {
        // attribute types
        testAttributes.add(testObjectRef);
        testAttributes.add(testDuration);
        testAttributes.add(testFineTime);
        testAttributes.add(testIdentifier);
        testAttributes.add(testTime);
        testAttributes.add(testURI);
        //testAttributes.add(testBlob);

        // union attribute types
        testAttributes.add(new Union(testBoolean));
        testAttributes.add(new Union(testOctet));
        testAttributes.add(new Union(testDouble));
        testAttributes.add(new Union(testFloat));
        testAttributes.add(new Union(testInteger));
        testAttributes.add(new Union(testLong));
        testAttributes.add(new Union(testShort));
        testAttributes.add(new Union(testString));

        // enumerations
        testEnumerations.add(InteractionType.SEND);
        testEnumerations.add(InteractionType.SUBMIT);
        testEnumerations.add(InteractionType.REQUEST);
        testEnumerations.add(InteractionType.INVOKE);
        testEnumerations.add(InteractionType.PROGRESS);
        testEnumerations.add(InteractionType.PUBSUB);
        testEnumerations.add(QoSLevel.BESTEFFORT);
        testEnumerations.add(QoSLevel.ASSURED);
        testEnumerations.add(QoSLevel.QUEUED);
        testEnumerations.add(QoSLevel.TIMELY);
        testEnumerations.add(SessionType.LIVE);
        testEnumerations.add(SessionType.REPLAY);
        testEnumerations.add(SessionType.SIMULATION);

        // complete composites
        testComposites.add(testComposite);
        testComposites.add(new Pair(new Union(testInteger), testURI));

        // abstract composites
        IdentifierList domId = new IdentifierList();
        domId.add(testIdentifier);
        domId.add(testIdentifier);
        domId.add(testIdentifier);
        domId.add(testIdentifier);
        TestPublish a = new TestPublishRegister(QoSLevel.QUEUED, testUInteger, domId, testIdentifier, testEnumeration, testIdentifier, false, null, testUInteger);
        TestPublish b = new TestPublishUpdate(QoSLevel.QUEUED, testUInteger, domId, testIdentifier, testEnumeration, testIdentifier, false, null, null, null, testUInteger, testBoolean, (AttributeList) null);
        TestPublish c = new TestPublishUpdate(QoSLevel.QUEUED, testUInteger, domId, testIdentifier, testEnumeration, testIdentifier, false, null, null, null, testUInteger, testBoolean, (AttributeList) null);
        testAbstracts.add(a);
        testAbstracts.add(b);
        testAbstracts.add(c);

        // DF: The abstract Update doesn't exist any more in the new API
        //UpdateList uLst = new UpdateList();
        //uLst.add(new TestUpdate(testTime, testURI, UpdateType.CREATION, new EntityKey(new Identifier("aFirst"), new Identifier("aSecond"), new Identifier("aThird"), new Identifier("aFourth")), testInteger));
        //uLst.add(new TestUpdate(testTime, testURI, UpdateType.CREATION, new EntityKey(new Identifier("aFirst"), new Identifier("aSecond"), new Identifier("aThird"), new Identifier("aFourth")), testInteger));
        //uLst.add(new TestUpdate(testTime, testURI, UpdateType.CREATION, new EntityKey(new Identifier("aFirst"), new Identifier("aSecond"), new Identifier("aThird"), new Identifier("aFourth")), testInteger));
        //testAbstracts.add(uLst);
        // null
        testNulls.add(null);

        // composites with null
        testCompositeWithNulls.add(new Pair(null, testURI));

        // concatenate all together for simplicity in service provider
        testAll.addAll(testAttributes);
        testAll.addAll(testEnumerations);
        testAll.addAll(testComposites);
        testAll.addAll(testAbstracts);
        testAll.addAll(testNulls);
        testAll.addAll(testCompositeWithNulls);

        // calculate the test indexes
        testIndexes = new int[6];
        testIndexes[0] = 0;
        testIndexes[1] = testIndexes[0] + testAttributes.size();
        testIndexes[2] = testIndexes[1] + testEnumerations.size();
        testIndexes[3] = testIndexes[2] + testComposites.size();
        testIndexes[4] = testIndexes[3] + testAbstracts.size();
        testIndexes[5] = testIndexes[4] + testNulls.size();
    }
}
