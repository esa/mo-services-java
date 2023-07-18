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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.util.stream.Collectors;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.malprototype.MALPrototypeHelper;
import org.ccsds.moims.mo.malprototype.datatest.body.TestAbstractMultiReturnResponse;
import org.ccsds.moims.mo.malprototype.datatest.body.TestExplicitMultiReturnResponse;
import org.ccsds.moims.mo.malprototype.datatest.body.TestInnerAbstractMultiReturnResponse;
import org.ccsds.moims.mo.malprototype.structures.TestPublish;
import org.ccsds.moims.mo.malprototype.structures.TestPublishRegister;
import org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate;
import org.ccsds.moims.mo.malprototype.structures.Assertion;
import org.ccsds.moims.mo.malprototype.structures.AssertionList;
import org.ccsds.moims.mo.malprototype.structures.BasicAbstractComposite;
import org.ccsds.moims.mo.malprototype.structures.TestBody;
import org.ccsds.moims.mo.malprototype.structures.AbstractCompositeList;
import org.ccsds.moims.mo.malprototype.structures.Auto;
import org.ccsds.moims.mo.malprototype.structures.ComplexStructure;
import org.ccsds.moims.mo.malprototype.structures.Garage;
import org.ccsds.moims.mo.malprototype.structures.Lamborghini;
import org.ccsds.moims.mo.malprototype.structures.Porsche;
import org.ccsds.moims.mo.malprototype.structures.StructureWithAbstractField;
import org.ccsds.moims.mo.malprototype.structures.StructureWithAbstractFieldList;

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
    public static final Identifier txt = new Identifier("Text");

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
    public static final Vector testPolymorphicTypes = new Vector();

    public static final TestExplicitMultiReturnResponse testMultiReturnExplicit = new TestExplicitMultiReturnResponse(testUOctet, testUShort, testUInteger, testULong);
    public static final TestAbstractMultiReturnResponse testMultiReturnAbstract = new TestAbstractMultiReturnResponse(testUOctet, testUShort, testUInteger, testULong);
    public static final TestInnerAbstractMultiReturnResponse testMultiReturnInnerAbstract = new TestInnerAbstractMultiReturnResponse(testUOctet, testULong, testUShort, testUInteger);
    public static final TestExplicitMultiReturnResponse testMultiReturnNull = new TestExplicitMultiReturnResponse(testUOctet, testUShort, testUInteger, null);

    public static final TestBody testBody = new TestBody(testString, testInteger);
    public static final ComplexStructure testComplexStructure = new ComplexStructure(testString, testInteger, testBoolean, testInteger, testBody);
    public static final BasicAbstractComposite testBasicAbstractComposite = new BasicAbstractComposite(testString, testInteger);
    public static final StructureWithAbstractField testStructureWithAbstractField0 = new StructureWithAbstractField(testString, testInteger, testBasicAbstractComposite, testBoolean, testInteger);
    public static final StructureWithAbstractField testStructureWithAbstractField1 = new StructureWithAbstractField(testString, testInteger, testComplexStructure, testBoolean, testInteger);
    public static final StructureWithAbstractField testStructureWithAbstractField2 = new StructureWithAbstractField(testString, testInteger, testStructureWithAbstractField0, testBoolean, testInteger);
    public static final StructureWithAbstractField testStructureWithAbstractField3 = new StructureWithAbstractField(testString, testInteger, testStructureWithAbstractField2, testBoolean, testInteger);
    public static final StructureWithAbstractFieldList testStructureWithAbstractFieldSingleTypedList1 = new StructureWithAbstractFieldList();
    public static final StructureWithAbstractFieldList testStructureWithAbstractFieldSingleTypedList2 = new StructureWithAbstractFieldList();
    public static final AbstractCompositeList testAbstractCompositeMultipleTypedList = new AbstractCompositeList();

    public static final String testDomainAsString = "CCSDS.MAL.prototype";
    public static final IdentifierList testDomain = new IdentifierList(Arrays.stream(testDomainAsString.split("."))
            .map(s -> new Identifier(s))
            .collect(Collectors.toCollection(ArrayList<Identifier>::new)));
    public static final ObjectRef testObjectRef = new ObjectRef(testDomain, txt, txt, txt, new UInteger(1));
    public static final Garage testGarage;
    // TODO find the type Identifier from the Element
    public static final ObjectRef<Lamborghini> testCourtesyCar
            = new ObjectRef<>(testDomain, MALPrototypeHelper.MALPROTOTYPE_AREA_NAME,
                    new Identifier("Lamborghini"), new Identifier("car0"), new UInteger(1));
    public static final ObjectRef<Lamborghini> testCarL1
            = new ObjectRef<>(testDomain, MALPrototypeHelper.MALPROTOTYPE_AREA_NAME,
                    new Identifier("Lamborghini"), new Identifier("carL1"), new UInteger(1));
    public static final ObjectRef<Lamborghini> testCarL2
            = new ObjectRef<>(testDomain, MALPrototypeHelper.MALPROTOTYPE_AREA_NAME,
                    new Identifier("Lamborghini"), new Identifier("carL2"), new UInteger(1));
    public static final ObjectRef<Porsche> testCarP1
            = new ObjectRef<>(testDomain, MALPrototypeHelper.MALPROTOTYPE_AREA_NAME,
                    new Identifier("Porsche"), new Identifier("carP1"), new UInteger(1));
    public static final ObjectRef<Porsche> testCarP2
            = new ObjectRef<>(testDomain, MALPrototypeHelper.MALPROTOTYPE_AREA_NAME,
                    new Identifier("Porsche"), new Identifier("carP2"), new UInteger(1));
    public static final ObjectRefList testCars = new ObjectRefList();
    public static final ObjectRefList testPorscheCars = new ObjectRefList();
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

        testEnumerations.add(AreaNumber.MAL);
        testEnumerations.add(AreaNumber.COM);
        testEnumerations.add(AreaNumber.COMMON);
        testEnumerations.add(AreaNumber.MC);
        testEnumerations.add(AreaNumber.MPS);
        testEnumerations.add(AreaNumber.SM);
        testEnumerations.add(AreaNumber.MDPD);

        testEnumerations.add(AttributeType.BLOB);
        testEnumerations.add(AttributeType.BOOLEAN);
        testEnumerations.add(AttributeType.DURATION);
        testEnumerations.add(AttributeType.FLOAT);
        testEnumerations.add(AttributeType.DOUBLE);
        testEnumerations.add(AttributeType.IDENTIFIER);
        testEnumerations.add(AttributeType.OCTET);
        testEnumerations.add(AttributeType.UOCTET);
        testEnumerations.add(AttributeType.SHORT);
        testEnumerations.add(AttributeType.USHORT);
        testEnumerations.add(AttributeType.INTEGER);
        testEnumerations.add(AttributeType.UINTEGER);
        testEnumerations.add(AttributeType.LONG);
        testEnumerations.add(AttributeType.ULONG);
        testEnumerations.add(AttributeType.STRING);
        testEnumerations.add(AttributeType.TIME);
        testEnumerations.add(AttributeType.FINETIME);
        testEnumerations.add(AttributeType.URI);
        testEnumerations.add(AttributeType.OBJECTREF);

        // complete composites
        testComposites.add(testComposite);
        testComposites.add(new Pair(new Union(testInteger), testURI));

        // abstract composites
        IdentifierList domId = new IdentifierList();
        domId.add(testIdentifier);
        domId.add(testIdentifier);
        domId.add(testIdentifier);
        domId.add(testIdentifier);
        AttributeList testKeyValues = new AttributeList();
        testKeyValues.add(new Identifier("TestValue"));
        testKeyValues.add(new String("TestValue"));
        TestPublish a = new TestPublishRegister(QoSLevel.QUEUED, testUInteger,
                domId, testIdentifier, testEnumeration, testIdentifier,
                false, null, testUInteger);
        TestPublish b = new TestPublishUpdate(QoSLevel.QUEUED, testUInteger,
                domId, testIdentifier, testEnumeration, testIdentifier,
                false, null, null, testKeyValues, testUInteger, testBoolean, (AttributeList) null);
        TestPublish c = new TestPublishUpdate(QoSLevel.QUEUED, testUInteger,
                domId, testIdentifier, testEnumeration, testIdentifier,
                false, null, null, testKeyValues, testUInteger, testBoolean, (AttributeList) null);
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

        // Polymorphic composites and lists
        testStructureWithAbstractFieldSingleTypedList1.add(testStructureWithAbstractField0);
        testStructureWithAbstractFieldSingleTypedList1.add(testStructureWithAbstractField0);
        testStructureWithAbstractFieldSingleTypedList1.add(testStructureWithAbstractField0);
        testStructureWithAbstractFieldSingleTypedList2.add(testStructureWithAbstractField0);
        testStructureWithAbstractFieldSingleTypedList2.add(testStructureWithAbstractField1);
        testStructureWithAbstractFieldSingleTypedList2.add(testStructureWithAbstractField2);
        testStructureWithAbstractFieldSingleTypedList2.add(testStructureWithAbstractField3);
        testAbstractCompositeMultipleTypedList.add(testBasicAbstractComposite);
        testAbstractCompositeMultipleTypedList.add(testComplexStructure);
        testAbstractCompositeMultipleTypedList.add(testStructureWithAbstractField0);
        testAbstractCompositeMultipleTypedList.add(testStructureWithAbstractField1);
        testAbstractCompositeMultipleTypedList.add(testStructureWithAbstractField2);
        testAbstractCompositeMultipleTypedList.add(testStructureWithAbstractField3);

        testPolymorphicTypes.add(testBody);
        testPolymorphicTypes.add(testComplexStructure);
        testPolymorphicTypes.add(testBasicAbstractComposite);
        testPolymorphicTypes.add(testStructureWithAbstractField0);
        testPolymorphicTypes.add(testStructureWithAbstractField1);
        testPolymorphicTypes.add(testStructureWithAbstractField2);
        testPolymorphicTypes.add(testStructureWithAbstractField3);
        testPolymorphicTypes.add(testStructureWithAbstractFieldSingleTypedList1);
        testPolymorphicTypes.add(testStructureWithAbstractFieldSingleTypedList2);
        testPolymorphicTypes.add(testAbstractCompositeMultipleTypedList);

        // concatenate all together for simplicity in service provider
        testAll.addAll(testAttributes);
        testAll.addAll(testEnumerations);
        testAll.addAll(testComposites);
        testAll.addAll(testAbstracts);
        testAll.addAll(testNulls);
        testAll.addAll(testCompositeWithNulls);
        testAll.addAll(testPolymorphicTypes);

        // calculate the test indexes
        testIndexes = new int[7];
        testIndexes[0] = 0;
        testIndexes[1] = testIndexes[0] + testAttributes.size();
        testIndexes[2] = testIndexes[1] + testEnumerations.size();
        testIndexes[3] = testIndexes[2] + testComposites.size();
        testIndexes[4] = testIndexes[3] + testAbstracts.size();
        testIndexes[5] = testIndexes[4] + testNulls.size();
        testIndexes[6] = testIndexes[5] + testCompositeWithNulls.size();

        // finalize the definition of the test ObjectRef elements
        testCars.add(testCarL1);
        testCars.add(testCarL2);
        testCars.add(testCarP1);
        testCars.add(testCarP2);
        testPorscheCars.add(testCarP1);
        testPorscheCars.add(testCarP2);
        // TODO find the type Identifier more programmatically
        ObjectIdentity garageId = new ObjectIdentity(testDomain, MALPrototypeHelper.MALPROTOTYPE_AREA_NAME,
                new Identifier("Garage"), new Identifier("garage"), new UInteger(1));
//        testGarage.setCourtesyCarAsPorsche(null);
        // TODO the next 2 lines do not compile
        ObjectRef<Porsche> porscheCar = new ObjectRef(testCourtesyCar.getDomain(), testCourtesyCar.getArea(),
                testCourtesyCar.getType(), testCourtesyCar.getKey(), testCourtesyCar.getObjectVersion());
        ObjectRef<Auto> autoCar = new ObjectRef(testCourtesyCar.getDomain(), testCourtesyCar.getArea(),
                testCourtesyCar.getType(), testCourtesyCar.getKey(), testCourtesyCar.getObjectVersion());
        ObjectRef<Element> elementCar = new ObjectRef(testCourtesyCar.getDomain(), testCourtesyCar.getArea(),
                testCourtesyCar.getType(), testCourtesyCar.getKey(), testCourtesyCar.getObjectVersion());

        testGarage = new Garage(garageId, porscheCar, autoCar, elementCar,
                testPorscheCars, testCars, testCars);
        //testGarage.setCourtesyCarAsPorsche(porscheCar);
        //testGarage.setCourtesyCarAsAuto(autoCar);
        //testGarage.setCourtesyCarAsObject(elementCar);

        // Lists:
        //testGarage.setCarsAsPorsches(testPorscheCars);
        //testGarage.setCarsAsAutos(testCars);
        //testGarage.setCarsAsObjects(testCars);
    }
}
