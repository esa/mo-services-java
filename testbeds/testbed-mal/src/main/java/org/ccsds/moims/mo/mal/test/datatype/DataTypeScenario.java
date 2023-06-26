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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.util.stream.Collectors;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.CompositeList;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ElementList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.malprototype.MALPrototypeHelper;
import org.ccsds.moims.mo.malprototype.datatest.DataTestHelper;
import org.ccsds.moims.mo.malprototype.datatest.body.TestAbstractMultiReturnResponse;
import org.ccsds.moims.mo.malprototype.datatest.body.TestExplicitMultiReturnResponse;
import org.ccsds.moims.mo.malprototype.datatest.body.TestInnerAbstractMultiReturnResponse;
import org.ccsds.moims.mo.malprototype.datatest.body.TestPolymorphicObjectRefTypesResponse;
import org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestStub;
import org.ccsds.moims.mo.malprototype.structures.AbstractCompositeList;
import org.ccsds.moims.mo.malprototype.structures.Auto;
import org.ccsds.moims.mo.malprototype.structures.Lamborghini;
import org.ccsds.moims.mo.malprototype.structures.Porsche;
import org.ccsds.moims.mo.malprototype.structures.StructureWithAbstractFieldList;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

/**
 *
 */
public class DataTypeScenario extends LoggingBase {

    protected DataTestStub getDataTestStub() throws MALException {
        return LocalMALInstance.instance().dataTestStub();
    }

    public String explicitDurationTypeWorks() throws MALInteractionException, MALException {
        String rv;

        logMessage("Starting explicit Duration data test...");
        try {
            rv = subSingleTest(TestData.testDuration,
                    getDataTestStub().testDataDuration(TestData.testDuration), "explicit Duration");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "explicit Duration");
        }
        logMessage("Finished explicit Duration data test");

        return rv;
    }

    public String explicitFineTimeTypeWorks() throws MALInteractionException, MALException {
        String rv;

        logMessage("Starting explicit FineTime data test...");
        try {
            rv = subSingleTest(TestData.testFineTime,
                    getDataTestStub().testDataFineTime(TestData.testFineTime), "explicit FineTime");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "explicit FineTime");
        }
        logMessage("Finished explicit FineTime data test");

        return rv;
    }

    public String explicitIdentifierTypeWorks() throws MALInteractionException, MALException {
        String rv;

        logMessage("Starting explicit Identifier data test...");
        try {
            rv = subSingleTest(TestData.testIdentifier,
                    getDataTestStub().testDataIdentifier(TestData.testIdentifier), "explicit Identifier");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "explicit Identifier");
        }
        logMessage("Finished explicit Identifier data test");

        return rv;
    }

    public String explicitTimeTypeWorks() throws MALInteractionException, MALException {
        String rv;

        logMessage("Starting explicit Time data test...");
        try {
            rv = subSingleTest(TestData.testTime,
                    getDataTestStub().testDataTime(TestData.testTime), "explicit Time");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "explicit Time");
        }
        logMessage("Finished explicit Time data test");

        return rv;
    }

    public String explicitURITypeWorks() throws MALInteractionException, MALException {
        String rv;

        logMessage("Starting explicit URI data test...");
        try {
            rv = subSingleTest(TestData.testURI,
                    getDataTestStub().testDataURI(TestData.testURI), "explicit URI");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "explicit URI");
        }
        logMessage("Finished explicit URI data test");

        return rv;
    }

    public String explicitBlobTypeWorks() throws MALInteractionException, MALException {
        String rv;

        logMessage("Starting explicit Blob data test...");
        logMessage("   testing byte buffer...");
        try {
            // first try with byte array
            rv = subSingleTest(TestData.testBlob,
                    getDataTestStub().testDataBlob(TestData.testBlob), "explicit byte Blob");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "explicit Blob");
        }

        if (rv.equals("OK")) {
            logMessage("   testing file buffer...");
            try {
                // next try with an actual file
                File tempFile = createTempFile("TestBlobData");
                Blob fileBlob = new Blob(tempFile.toURI().toString());
                rv = subSingleTest(fileBlob,
                        getDataTestStub().testDataBlob(fileBlob), "explicit file Blob");
            } catch (MALInteractionException ex) {
                rv = subSingleTestExceptionHandler(ex, "explicit Blob");
            }
        }
        logMessage("Finished explicit Blob data test");

        return rv;
    }

    public String explicitBooleanTypeWorks() throws MALInteractionException, MALException {
        String rv;

        logMessage("Starting explicit Boolean data test...");
        try {
            rv = subSingleTest(TestData.testBoolean,
                    getDataTestStub().testDataBoolean(TestData.testBoolean), "explicit Boolean");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "explicit Boolean");
        }
        logMessage("Finished explicit Boolean data test");

        return rv;
    }

    public String explicitOctetTypeWorks() throws MALInteractionException, MALException {
        String rv;

        logMessage("Starting explicit Octet data test...");
        try {
            rv = subSingleTest(TestData.testOctet,
                    getDataTestStub().testDataOctet(TestData.testOctet), "explicit Octet");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "explicit Octet");
        }
        logMessage("Finished explicit Octet data test");

        return rv;
    }

    public String explicitDoubleTypeWorks() throws MALInteractionException, MALException {
        String rv;

        logMessage("Starting explicit Double data test...");
        try {
            rv = subSingleTest(TestData.testDouble,
                    getDataTestStub().testDataDouble(TestData.testDouble), "explicit Double");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "explicit Double");
        }
        logMessage("Finished explicit Double data test");

        return rv;
    }

    public String explicitFloatTypeWorks() throws MALInteractionException, MALException {
        String rv;

        logMessage("Starting explicit Float data test...");
        try {
            rv = subSingleTest(TestData.testFloat,
                    getDataTestStub().testDataFloat(TestData.testFloat), "explicit Float");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "explicit Float");
        }
        logMessage("Finished explicit Float data test");

        return rv;
    }

    public String explicitIntegerTypeWorks() throws MALInteractionException, MALException {
        String rv;

        logMessage("Starting explicit Integer data test...");
        try {
            rv = subSingleTest(TestData.testInteger,
                    getDataTestStub().testDataInteger(TestData.testInteger), "explicit Integer");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "explicit Integer");
        }
        logMessage("Finished explicit Integer data test");

        return rv;
    }

    public String explicitLongTypeWorks() throws MALInteractionException, MALException {
        String rv;

        logMessage("Starting explicit Long data test...");
        try {
            rv = subSingleTest(TestData.testLong,
                    getDataTestStub().testDataLong(TestData.testLong), "explicit Long");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "explicit Long");
        }
        logMessage("Finished explicit Long data test");

        return rv;
    }

    public String explicitShortTypeWorks() throws MALInteractionException, MALException {
        String rv;

        logMessage("Starting explicit Short data test...");
        try {
            rv = subSingleTest(TestData.testShort,
                    getDataTestStub().testDataShort(TestData.testShort), "explicit Short");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "explicit Short");
        }
        logMessage("Finished explicit Short data test");

        return rv;
    }

    public String explicitUOctetTypeWorks() throws MALInteractionException, MALException {
        String rv;

        logMessage("Starting explicit UOctet data test...");
        try {
            rv = subSingleTest(TestData.testUOctet,
                    getDataTestStub().testDataUOctet(TestData.testUOctet), "explicit UOctet");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "explicit UOctet");
        }
        logMessage("Finished explicit UOctet data test");

        return rv;
    }

    public String explicitUIntegerTypeWorks() throws MALInteractionException, MALException {
        String rv;

        logMessage("Starting explicit UInteger data test...");
        try {
            rv = subSingleTest(TestData.testUInteger,
                    getDataTestStub().testDataUInteger(TestData.testUInteger), "explicit UInteger");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "explicit UInteger");
        }
        logMessage("Finished explicit UInteger data test");

        return rv;
    }

    public String explicitULongTypeWorks() throws MALInteractionException, MALException {
        String rv;

        logMessage("Starting explicit ULong data test...");
        try {
            rv = subSingleTest(TestData.testULong,
                    getDataTestStub().testDataULong(TestData.testULong), "explicit ULong");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "explicit ULong");
        }
        logMessage("Finished explicit ULong data test");

        return rv;
    }

    public String explicitUShortTypeWorks() throws MALInteractionException, MALException {
        String rv;

        logMessage("Starting explicit UShort data test...");
        try {
            rv = subSingleTest(TestData.testUShort,
                    getDataTestStub().testDataUShort(TestData.testUShort), "explicit UShort");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "explicit UShort");
        }
        logMessage("Finished explicit UShort data test");

        return rv;
    }

    public String explicitObjectRefTypeWorks() throws MALInteractionException, MALException {
        String rv;

        logMessage("Starting explicit ObjectRef data test...");
        try {
            rv = subSingleTest(TestData.testObjectRef,
                    getDataTestStub().testDataObjectRef(TestData.testObjectRef), "explicit ObjectRef");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "explicit ObjectRef");
        }
        logMessage("Finished explicit ObjectRef data test");

        return rv;
    }

    public String explicitStringTypeWorks() throws MALInteractionException, MALException {
        String rv;

        logMessage("Starting explicit String data test...");
        try {
            rv = subSingleTest(TestData.testString,
                    getDataTestStub().testDataString(TestData.testString), "explicit String");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "explicit String");
        }
        logMessage("Finished explicit String data test");

        return rv;
    }

    public String attributeTypesWork() throws MALInteractionException, MALException {
        logMessage("Starting attribute data test...");
        String rv = subTest(TestData.testIndexes[0], TestData.testAttributes);
        logMessage("Finished attribute data test");

        return rv;
    }

    public String enumerationsWork() throws MALInteractionException, MALException {
        logMessage("Starting enumeration data test...");
        String rv = subTest(TestData.testIndexes[1], TestData.testEnumerations);
        logMessage("Finished enumeration data test");

        return rv;
    }

    public String completeCompositesWork() throws MALInteractionException, MALException {
        logMessage("Starting composite data test...");
        String rv = subTest(TestData.testIndexes[2], TestData.testComposites);
        logMessage("Finished composite data test");

        return rv;
    }

    public String abstractCompositesWork() throws MALInteractionException, MALException {
        logMessage("Starting abstract data test...");
        String rv = subTest(TestData.testIndexes[3], TestData.testAbstracts);
        logMessage("Finished abstract data test");

        return rv;
    }

    public String listsWork() throws MALInteractionException, MALException {
        String rv;

        logMessage("Starting list data test...");
        try {
            rv = subSingleTest(TestData.testList,
                    getDataTestStub().testDataList(TestData.testList), "explicit list");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "explicit list");
        }
        logMessage("Finished list data test");

        return rv;
    }

    public String nullsWork() throws MALInteractionException, MALException {
        logMessage("Starting null data test...");
        String rv = subTest(TestData.testIndexes[4], TestData.testNulls);
        logMessage("Finished null data test");

        return rv;
    }

    public String compositesWithNullWork() throws MALInteractionException, MALException {
        logMessage("Starting composite null data test...");
        String rv = subTest(TestData.testIndexes[5], TestData.testCompositeWithNulls);
        logMessage("Finished composite null data test");

        return rv;
    }

    public String explicitMultiReturnWorks() throws MALInteractionException, MALException {
        String rv;

        logMessage("Starting multi return data test...");
        try {
            getDataTestStub().setTestDataOffset(54);
            TestExplicitMultiReturnResponse tv = getDataTestStub().testExplicitMultiReturn(
                    TestData.testUOctet, TestData.testUShort, TestData.testUInteger, TestData.testULong);
            rv = subMultiTest(TestData.testMultiReturnExplicit.get_UOctet0(),
                    tv.get_UOctet0(), null, "explicit Multi return part 1");
            rv = subMultiTest(TestData.testMultiReturnExplicit.get_UShort1(),
                    tv.get_UShort1(), rv, "explicit Multi return part 2");
            rv = subMultiTest(TestData.testMultiReturnExplicit.get_UInteger2(),
                    tv.get_UInteger2(), rv, "explicit Multi return part 3");
            rv = subMultiTest(TestData.testMultiReturnExplicit.get_ULong3(),
                    tv.get_ULong3(), rv, "explicit Multi return part 4");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "explicit Multi return");
        }
        logMessage("Finished multi return data test");

        return rv;
    }

    public String abstractMultiReturnWorks() throws MALInteractionException, MALException {
        String rv;

        logMessage("Starting abstract multi return data test...");
        try {
            TestAbstractMultiReturnResponse tv = getDataTestStub().testAbstractMultiReturn(
                    TestData.testUOctet, TestData.testUShort, TestData.testUInteger, TestData.testULong);
            rv = subMultiTest(TestData.testMultiReturnAbstract.get_UOctet0(),
                    tv.get_UOctet0(), null, "abstract Multi return part 1");
            rv = subMultiTest(TestData.testMultiReturnAbstract.get_UShort1(),
                    tv.get_UShort1(), rv, "abstract Multi return part 2");
            rv = subMultiTest(TestData.testMultiReturnAbstract.get_UInteger2(),
                    tv.get_UInteger2(), rv, "abstract Multi return part 3");
            rv = subMultiTest(TestData.testMultiReturnAbstract.get_Element3(),
                    tv.get_Element3(), rv, "abstract Multi return part 4");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "abstract Multi return");
        }
        logMessage("Finished abstract multi return data test");

        return rv;
    }

    public String innerAbstractMultiReturnWorks() throws MALInteractionException, MALException {
        String rv;

        logMessage("Starting abstract multi return data test...");
        try {
            TestInnerAbstractMultiReturnResponse tv = getDataTestStub().testInnerAbstractMultiReturn(
                    TestData.testUOctet, TestData.testULong, TestData.testUShort, TestData.testUInteger);
            rv = subMultiTest(TestData.testMultiReturnInnerAbstract.get_UOctet0(),
                    tv.get_UOctet0(), null, "inner abstract Multi return part 1");
            rv = subMultiTest(TestData.testMultiReturnInnerAbstract.get_Element1(),
                    tv.get_Element1(), rv, "inner abstract Multi return part 2");
            rv = subMultiTest(TestData.testMultiReturnInnerAbstract.get_Element2(),
                    tv.get_Element2(), rv, "inner abstract Multi return part 3");
            rv = subMultiTest(TestData.testMultiReturnInnerAbstract.get_UInteger3(),
                    tv.get_UInteger3(), rv, "inner abstract Multi return part 4");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "inner abstract Multi return");
        }
        logMessage("Finished inner abstract multi return data test");

        return rv;
    }

    public String multiReturnWithNullsWork() throws MALInteractionException, MALException {
        String rv;

        logMessage("Starting multi return null data test...");
        try {
            getDataTestStub().setTestDataOffset(55);
            TestExplicitMultiReturnResponse tv = getDataTestStub().testExplicitMultiReturn(
                    TestData.testUOctet, TestData.testUShort, TestData.testUInteger, null);
            rv = subMultiTest(TestData.testMultiReturnNull.getBodyElement0(),
                    tv.getBodyElement0(), null, "null Multi return part 1");
            rv = subMultiTest(TestData.testMultiReturnNull.getBodyElement1(),
                    tv.getBodyElement1(), rv, "null Multi return part 2");
            rv = subMultiTest(TestData.testMultiReturnNull.getBodyElement2(),
                    tv.getBodyElement2(), rv, "null Multi return part 3");
            rv = subMultiTest(TestData.testMultiReturnNull.getBodyElement3(),
                    tv.getBodyElement3(), rv, "null Multi return part 4");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "null Multi return");
        }
        logMessage("Finished multi return null data test");

        return rv;
    }

    public String polymorphicTypesWork() throws MALInteractionException, MALException {
        logMessage("Starting polymorphic types data test...");
        String rv = subTest(TestData.testIndexes[6], TestData.testPolymorphicTypes);
        logMessage("Finished polymorphic types data test");

        return rv;
    }

    /**
     * Checks various parameter values for an operation parameter declared as a
     * List<AbstractComposite>.
     */
    public String polymorphicAbstractCompositeListsWork() throws MALInteractionException, MALException {
        logMessage("Starting polymorphic AbstractComposite list parameter test...");
        String rv;
        AbstractCompositeList res;
        try {
            /*
             * We must make sure that the parameter value is provided to the MAL level
             * with the expected type. The current proposed mapping actually provides
             * the parameter value with the List<Element> MAL type, which is not what
             * is expected.
             */
            AbstractCompositeList abstractList = new AbstractCompositeList();
            abstractList.addAll(TestData.testStructureWithAbstractFieldSingleTypedList1);
            res = getDataTestStub().testPolymorphicAbstractCompositeList(abstractList);
            rv = subSingleTest(abstractList,
                    res, "testStructureWithAbstractFieldSingleTypedList1");
            logMessage("The current Java mapping does not pass the proper concrete type.");
            // TODO SL forward changes to the next functions
            /*
             * ALTERNATE CODE
             * The following code uses a Java mapping that should enable the proper type
             * to be passed to the MAL, but remains to be implemented in the stub generator.
             *
            // homogeneous concrete list type List<StructureWithAbstractField>
            AbstractCompositeList abstractList = new AbstractCompositeList(
                    TestData.testStructureWithAbstractFieldSingleTypedList1);
            res = getDataTestStub().testPolymorphicAbstractCompositeList(abstractList);
            rv = subMultiTest(abstractList,
                    res, null, "testStructureWithAbstractFieldSingleTypedList1");
            // homogeneous concrete list type List<StructureWithAbstractField>
            // with inner field abstract_item holding heterogeneous values
            abstractList = new AbstractCompositeList(
                    TestData.testStructureWithAbstractFieldSingleTypedList2);
            res = getDataTestStub().testPolymorphicAbstractCompositeList(abstractList);
            rv = subMultiTest(abstractList,
                    res, rv, "testStructureWithAbstractFieldSingleTypedList2");
            // heterogeneous concrete list type List<Element>
            abstractList = new AbstractCompositeList(
                    TestData.testAbstractCompositeMultipleTypedList);
            res = getDataTestStub().testPolymorphicAbstractCompositeList(abstractList);
            rv = subMultiTest(abstractList,
                    res, rv, "testAbstractCompositeMultipleTypedList");
             *
             * END OF ALTERNATE CODE
             */
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "polymorphicAbstractCompositeListsWork");
        }
        logMessage("Finished polymorphic AbstractComposite list parameter test");
        return rv;
    }

    /**
     * Checks various parameter values for an operation parameter declared as a
     * List<Composite>.
     */
    public String polymorphicMalCompositeListsWork() throws MALInteractionException, MALException {
        logMessage("Starting polymorphic MAL Composite list parameter test...");
        String rv;
        CompositeList res;
        try {
            // homogeneous concrete list type List<StructureWithAbstractField>
            res = getDataTestStub().testPolymorphicMalCompositeList(TestData.testStructureWithAbstractFieldSingleTypedList1);
            rv = subMultiTest(TestData.testStructureWithAbstractFieldSingleTypedList1,
                    res, null, "testStructureWithAbstractFieldSingleTypedList1");
            // homogeneous concrete list type List<StructureWithAbstractField>
            // with inner field abstract_item holding heterogeneous values
            res = getDataTestStub().testPolymorphicMalCompositeList(TestData.testStructureWithAbstractFieldSingleTypedList2);
            rv = subMultiTest(TestData.testStructureWithAbstractFieldSingleTypedList2,
                    res, rv, "testStructureWithAbstractFieldSingleTypedList2");
            // heterogeneous concrete list type List<Element>
            /*
             * COMPILING ERROR
             * The method testPolymorphicMalCompositeList(CompositeList) in the type DataTestStub
             * is not applicable for the arguments (AbstractCompositeList)
             *
            res = getDataTestStub().testPolymorphicMalCompositeList(TestData.testAbstractCompositeMultipleTypedList);
             *
             * END OF COMPILING ERROR
             */
            res = null;
            rv = subMultiTest(TestData.testAbstractCompositeMultipleTypedList,
                    res, rv, "testAbstractCompositeMultipleTypedList");
            logMessage("The current Java mapping prevents this test from compiling.");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "polymorphicMalCompositeListsWork");
        }
        logMessage("Finished polymorphic MAL Composite list parameter test");
        return rv;
    }

    /**
     * Checks various parameter values for an operation parameter declared as a
     * List<Element>.
     */
    public String polymorphicMalElementListsWork() throws MALInteractionException, MALException {
        logMessage("Starting polymorphic MAL Element list parameter test...");
        String rv;
        ElementList res;
        try {
            // homogeneous concrete list type List<StructureWithAbstractField>
            res = getDataTestStub().testPolymorphicMalElementList(TestData.testStructureWithAbstractFieldSingleTypedList1);
            rv = subMultiTest(TestData.testStructureWithAbstractFieldSingleTypedList1,
                    res, null, "testStructureWithAbstractFieldSingleTypedList1");
            // homogeneous concrete list type List<StructureWithAbstractField>
            // with inner field abstract_item holding heterogeneous values
            res = getDataTestStub().testPolymorphicMalElementList(TestData.testStructureWithAbstractFieldSingleTypedList2);
            rv = subMultiTest(TestData.testStructureWithAbstractFieldSingleTypedList2,
                    res, rv, "testStructureWithAbstractFieldSingleTypedList2");
            // heterogeneous concrete list type List<Element>
            res = getDataTestStub().testPolymorphicMalElementList(TestData.testAbstractCompositeMultipleTypedList);
            rv = subMultiTest(TestData.testAbstractCompositeMultipleTypedList,
                    res, rv, "testAbstractCompositeMultipleTypedList");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "polymorphicMalElementListsWork");
        }
        logMessage("Finished polymorphic MAL Element list parameter test");
        return rv;
    }

    /**
     * Checks various polymorphic forms of an ObjectRef.
     */
    public String polymorphicObjectRefTypesWork() throws MALInteractionException, MALException {
        logMessage("Starting polymorphic ObjectRef types parameter test...");
        String rv;
        TestPolymorphicObjectRefTypesResponse res;
        try {
            res = getDataTestStub().testPolymorphicObjectRefTypes(
                    TestData.testGarage,
                    TestData.testGarage.getCarsAsPorsches(),
                    TestData.testGarage.getCarsAsAutos(),
                    TestData.testGarage.getCarsAsObjects());
            rv = subMultiTest(TestData.testGarage,
                    res.get_Garage0(), null, "polymorphicObjectRefTypesWork param 1");
            // temporarily removed tests
//            rv = subMultiTest(TestData.testGarage.getCourtesyCarAsPorsche(),
//                    res.get_TODO(), rv, "polymorphicObjectRefTypesWork param 2");
//            rv = subMultiTest(TestData.testGarage.getCourtesyCarAsAuto(),
//                    res.get_TODO(), rv, "polymorphicObjectRefTypesWork param 3");
//            rv = subMultiTest(TestData.testGarage.getCourtesyCarAsObject(),
//                    res.get_TODO(), rv, "polymorphicObjectRefTypesWork param 4");
//            rv = subMultiTest(TestData.testGarage.getCarsAsPorsches(),
//                    res.get_TODO(), rv, "polymorphicObjectRefTypesWork param 5");
//            rv = subMultiTest(TestData.testGarage.getCarsAsAutos(),
//                    res.get_TODO(), rv, "polymorphicObjectRefTypesWork param 6");
//            rv = subMultiTest(TestData.testGarage.getCarsAsObjects(),
//                    res.get_TODO(), rv, "polymorphicObjectRefTypesWork param 7");
            rv = subMultiTest(null,
                    "compilation errors", rv, "polymorphicObjectRefTypesWork compilation errors");
        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "polymorphicObjectRefTypesWork");
        }
        logMessage("Finished polymorphic ObjectRef types parameter test");
        return rv;
    }

    public static final ObjectIdentity getObjectIdentityFromObjectRef(ObjectRef objRef) {
        return new ObjectIdentity(objRef.getDomain(),
                objRef.getArea(),
                objRef.getType(),
                objRef.getKey(),
                objRef.getObjectVersion());
    }

    public static final ObjectRef getObjectRefFromObjectIdentity(ObjectIdentity objId) {
        return new ObjectRef(objId.getDomainId(),
                objId.getAreaId(),
                objId.getTypeId(),
                objId.getKeyId(),
                objId.getVersionId());
    }

    public String objectAssertionsAreChecked() throws MALInteractionException, MALException {
        logMessage("Starting Object assertions are checked test...");
        String rv = null;
        try {
            StringList windows1 = new StringList();
            windows1.add("front");
            windows1.add("left side");
            windows1.add("right side");
            windows1.add("rooftop");
            StringList windows2 = new StringList();
            windows2.add("front");
            windows2.add("left side");
            windows2.add("right side");
            windows2.add("rear");
            // create a first object
            ObjectRef<Auto> autoRef1 = getDataTestStub().createObjectFromFields(
                    new Identifier("Lamborghini"), new Identifier("my first car"), false,
                    "V12 L539 - 850 CV", "Monocoque CFRP", windows1);
            // create another object with exactly the same identity, must fail
            ObjectIdentity autoId2 = getObjectIdentityFromObjectRef(autoRef1);
            Lamborghini auto2 = new Lamborghini(autoId2,
                    "V12 LE3512 - 750 CV",
                    "Monocoque CFRP",
                    windows2);
            ObjectRef<Auto> autoRef2 = null;
            try {
                autoRef2 = getDataTestStub().createObject(auto2);
            } catch (MALInteractionException ex) {
                rv = subMultiTest(MALPrototypeHelper.TEST_OBJECT_EXISTS_ERROR_NUMBER,
                        ex.getStandardError().getErrorNumber(),
                        rv,
                        "Expected object already exists error for Object identity is unique test");
            }
            rv = subMultiTest(null, autoRef2, rv, "Expecting object already exists error for Object identity is unique test");

            // create a valid Object identity
            autoId2.setVersionId(new UInteger(autoId2.getVersionId().getValue() + 1));
            // use it for a wrong typed object
            // this could be detected by the Java mapping at object creation
            Porsche porsche2 = new Porsche(autoId2,
                    "V12 LE3512 - 750 CV",
                    "Monocoque CFRP",
                    windows2);
            ObjectRef<Auto> porscheRef2 = null;
            try {
                porscheRef2 = getDataTestStub().createObject(porsche2);
            } catch (MALInteractionException ex) {
                rv = subMultiTest(MALPrototypeHelper.DATA_ERROR_ERROR_NUMBER,
                        ex.getStandardError().getErrorNumber(),
                        rv,
                        "Wrong type error for Object identity type check");
            }
            rv = subMultiTest(null, porscheRef2, rv, "Expecting wrong type error for Object identity type check");

            // create a revision of the first object
            autoRef2 = getDataTestStub().createObject(auto2);
            rv = subMultiTest(autoId2.getVersionId(), autoRef2.getObjectVersion(), rv,
                    "New version of first object created");
            // get the latest version of an object
            ObjectRef<Auto> autoRef0 = new ObjectRef<>(autoRef1.getDomain(),
                    autoRef1.getArea(),
                    autoRef1.getType(),
                    autoRef1.getKey(),
                    new UInteger(0));
            Auto auto0 = getDataTestStub().getObject(autoRef0);
            rv = subMultiTest(auto2.getObjectIdentity(), auto0.getObjectIdentity(), rv,
                    "Latest object version, field objectIdentity");
            rv = subMultiTest(auto2.getEngine(), auto0.getEngine(), rv,
                    "Latest object version, field engine");
            rv = subMultiTest(auto2.getChassis(), auto0.getChassis(), rv,
                    "Latest object version, field chassis");
            rv = subMultiTest(auto2.getWindows(), auto0.getWindows(), rv,
                    "Latest object version, field windows");

        } catch (MALInteractionException ex) {
            rv = subSingleTestExceptionHandler(ex, "Object assertions are checked test");
        }
        logMessage("Finished Object assertions are checked test");
        return rv;
    }

    protected String subTest(int reportingOffset, Vector testdata) throws MALInteractionException, MALException {
        DataTestStub stub = getDataTestStub();

        stub.setTestDataOffset(reportingOffset);

        for (int i = 0; i < testdata.size(); i++, reportingOffset++) {
            Element testValue = (Element) testdata.get(i);
            try {
                Element rspnElement = stub.testData(testValue);

                if (null != testValue) {
                    if (!testValue.equals(rspnElement)) {
                        String msg = "Test step failed in consumer: " + String.valueOf(reportingOffset);
                        logMessage(msg);
                        return msg;
                    }
                } else {
                    if (null != rspnElement) {
                        String msg = "Test step failed in consumer: " + String.valueOf(reportingOffset);
                        logMessage(msg);
                        return msg;
                    }
                }
            } catch (MALInteractionException ex) {
                long errNum = ex.getStandardError().getErrorNumber().getValue();
                if ((MALHelper.BAD_ENCODING_ERROR_NUMBER.getValue() == errNum) || (MALPrototypeHelper.DATA_ERROR_ERROR_NUMBER.getValue() == errNum)) {
                    String msg = "Test step failed in consumer: " + ex.toString();
                    logMessage(msg);
                    return msg;
                }

                throw ex;
            }

            logMessage("Test step passed: " + String.valueOf(reportingOffset));
        }

        return "OK";
    }

    protected String subSingleTest(Object testValue, Object rspnValue, String testMsg) throws MALException {
        if (null != testValue) {
            if (!testValue.equals(rspnValue)) {
                String msg = "Test " + testMsg + " failed in consumer, received value does not match expected";
                logMessage(msg);
                return msg;
            }
        } else {
            if (null != rspnValue) {
                String msg = "Test " + testMsg + " failed in consumer, received value is not expected null value";
                logMessage(msg);
                return msg;
            }
        }

        logMessage("Test " + testMsg + " passed");

        return "OK";
    }

    protected String subMultiTest(Object testValue, Object rspnValue,
            String previousResult, String testMsg) throws MALException {
        if ((null == previousResult) || ("OK".equals(previousResult))) {
            previousResult = subSingleTest(testValue, rspnValue, testMsg);
        }

        return previousResult;
    }

    protected String subSingleTestExceptionHandler(MALInteractionException ex,
            String testMsg) throws MALInteractionException {
        long errNum = ex.getStandardError().getErrorNumber().getValue();
        if ((MALHelper.BAD_ENCODING_ERROR_NUMBER.getValue() == errNum) || (MALPrototypeHelper.DATA_ERROR_ERROR_NUMBER.getValue() == errNum)) {
            String msg = "Test " + testMsg + " failed in consumer, encoding error received: "
                    + ex.getStandardError().getExtraInformation().toString();
            logMessage(msg);
            return msg;
        }

        throw ex;
    }

    protected static File createTempFile(String prefix) {
        File fileData = null;

        try {
            fileData = createLoggingFile(prefix);
            OutputStream os = new FileOutputStream(fileData);
            os.write(fileData.getCanonicalPath().getBytes());
            os.flush();
            os.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return fileData;
    }
}
