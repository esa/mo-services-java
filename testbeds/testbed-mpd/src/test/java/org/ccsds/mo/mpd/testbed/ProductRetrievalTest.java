/* ----------------------------------------------------------------------------
 * Copyright (C) 2025      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Testbed - MPD
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
package org.ccsds.mo.mpd.testbed;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.mo.mpd.testbed.backends.OneProductDataset;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.IntegerList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mpd.MPDHelper;
import org.ccsds.moims.mo.mpd.productretrieval.consumer.ProductRetrievalAdapter;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductFilter;
import org.ccsds.moims.mo.mpd.structures.ProductList;
import org.ccsds.moims.mo.mpd.structures.TimeWindow;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class ProductRetrievalTest extends MPDTest {

    private static OneProductDataset oneProductDataset = new OneProductDataset();

    @BeforeClass
    public static void setUpClass() throws IOException {
        System.out.println(TEST_SET_UP_CLASS_1);
        System.out.println(TEST_SET_UP_CLASS_2);
        setUp.setUp(oneProductDataset, false, false, true);
    }

    /**
     * Test Case 1.
     */
    @Test
    public void testCase_01() {
        System.out.println("Running: testCase_01()");

        try {
            ProductFilter productFilter = new ProductFilter();
            Time now = Time.now();
            TimeWindow creationDate = new TimeWindow(now, new Time(now.getValue() - 100));
            TimeWindow contentDate = null;
            testMOErrorListProducts(productFilter, creationDate, contentDate);
            fail("The operation was expected to throw an 'Invalid' exception!");
        } catch (MALInteractionException ex) {
            MOErrorException moError = ex.getStandardError();
            long errorNumber = moError.getErrorNumber().getValue();
            if (errorNumber == MPDHelper.INVALID_ERROR_NUMBER.getValue()) {
                Logger.getLogger(ProductRetrievalTest.class.getName()).log(Level.INFO, "Error returned successfully!");
            } else {
                Logger.getLogger(ProductRetrievalTest.class.getName()).log(Level.INFO, "Failed!", ex);
                fail("The operation was expected to throw an 'Invalid' exception!");
            }
        }
    }

    /**
     * Test Case 2.
     */
    @Test
    public void testCase_02() {
        System.out.println("Running: testCase_02()");

        try {
            ProductFilter productFilter = new ProductFilter();
            Time now = Time.now();
            TimeWindow creationDate = null;
            TimeWindow contentDate = new TimeWindow(now, new Time(now.getValue() - 100));
            testMOErrorListProducts(productFilter, creationDate, contentDate);
            fail("The operation was expected to throw an 'Invalid' exception!");
        } catch (MALInteractionException ex) {
            MOErrorException moError = ex.getStandardError();
            long errorNumber = moError.getErrorNumber().getValue();
            if (errorNumber == MPDHelper.INVALID_ERROR_NUMBER.getValue()) {
                Logger.getLogger(ProductRetrievalTest.class.getName()).log(Level.INFO, "Error returned successfully!");
            } else {
                Logger.getLogger(ProductRetrievalTest.class.getName()).log(Level.INFO, "Failed!", ex);
                fail("The operation was expected to throw an 'Invalid' exception!");
            }
        }
    }

    /**
     * Test Case 3.
     */
    @Test
    public void testCase_03() {
        System.out.println("Running: testCase_03()");

        try {
            ObjectRefList productRefs = new ObjectRefList();
            this.testGetProducts(productRefs);
        } catch (MALInteractionException ex) {
            Logger.getLogger(ProductRetrievalTest.class.getName()).log(Level.INFO, "Failed!", ex);
            fail("The operation is not expected to throw an exception!");
        }
    }

    /**
     * Test Case 4.
     */
    @Test
    public void testCase_04() {
        System.out.println("Running: testCase_04()");

        try {
            IdentifierList domain = new IdentifierList();
            Long typeId = Product.TYPE_ID.getTypeId();
            Identifier key = new Identifier("Non_Existing_Key");
            UInteger objectVersion = new UInteger(1);

            ObjectRefList productRefs = new ObjectRefList();
            productRefs.add(new ObjectRef(domain, typeId, key, objectVersion));
            this.testGetProducts(productRefs);
            fail("The operation was expected to throw an 'Unknown' exception!");
        } catch (MALInteractionException ex) {
            MOErrorException moError = ex.getStandardError();
            long errorNumber = moError.getErrorNumber().getValue();
            if (errorNumber == MPDHelper.UNKNOWN_ERROR_NUMBER.getValue()) {
                Logger.getLogger(ProductRetrievalTest.class.getName()).log(Level.INFO, "Error returned successfully!");
                // Check if the error includes in the index list a zero!
                IntegerList indexes = (IntegerList) moError.getExtraInformation();
                int size = indexes.size();

                if (size != 1) {
                    fail("The 'Unknown' exception does not have 1 entry!");
                } else {
                    Integer indexError = indexes.get(0);
                    assertEquals(0, indexError.intValue()); // The wrong index
                }
            } else {
                Logger.getLogger(ProductRetrievalTest.class.getName()).log(Level.INFO, "Failed!", ex);
                fail("The operation was expected to throw an 'Unknown' exception!");
            }
        }
    }

    /**
     * Test Case 5.
     */
    @Test
    public void testCase_05() {
        System.out.println("Running: testCase_05()");

        try {
            IdentifierList domain = new IdentifierList();
            Long typeId = Product.TYPE_ID.getTypeId();
            Identifier key = new Identifier("Non_Existing_Key");
            UInteger objectVersion = new UInteger(1);

            ObjectRefList productRefs = new ObjectRefList();
            productRefs.add(oneProductDataset.ref);
            productRefs.add(new ObjectRef(domain, typeId, key, objectVersion));
            this.testGetProducts(productRefs);
            fail("The operation was expected to throw an 'Unknown' exception!");
        } catch (MALInteractionException ex) {
            MOErrorException moError = ex.getStandardError();
            long errorNumber = moError.getErrorNumber().getValue();
            if (errorNumber == MPDHelper.UNKNOWN_ERROR_NUMBER.getValue()) {
                Logger.getLogger(ProductRetrievalTest.class.getName()).log(Level.INFO, "Error returned successfully!");
                // Check if the error includes in the index list a zero!
                IntegerList indexes = (IntegerList) moError.getExtraInformation();
                int size = indexes.size();

                if (size != 1) {
                    fail("The 'Unknown' exception does not have 1 entry!");
                } else {
                    Integer indexError = indexes.get(0);
                    assertEquals(1, indexError.intValue()); // The wrong index
                }
            } else {
                Logger.getLogger(ProductRetrievalTest.class.getName()).log(Level.INFO, "Failed!", ex);
                fail("The operation was expected to throw an 'Unknown' exception!");
            }
        }
    }

    private void testGetProducts(ObjectRefList productRefs) throws MALInteractionException {
        try {
            ProductList returnedProducts = new ProductList();
            long startTime = System.currentTimeMillis();
            final AtomicBoolean ackReceived = new AtomicBoolean(false);
            final AtomicBoolean rspReceived = new AtomicBoolean(false);

            consumerPR.getProducts(productRefs, new ProductRetrievalAdapter() {

                @Override
                public void getProductsAckReceived(MALMessageHeader msgHeader, Map qosProperties) {
                    long duration = System.currentTimeMillis() - startTime;
                    System.out.println("ACK received in: " + duration + " ms");
                    ackReceived.set(true);
                }

                @Override
                public void getProductsUpdateReceived(MALMessageHeader msgHeader, Product product, Map qosProperties) {
                    returnedProducts.add(product);
                }

                @Override
                public void getProductsResponseReceived(MALMessageHeader msgHeader, Map qosProperties) {
                    long duration = System.currentTimeMillis() - startTime;
                    System.out.println("RESPONSE received in: " + duration + " ms");
                    rspReceived.set(true);
                }

                @Override
                public void getProductsAckErrorReceived(MALMessageHeader msgHeader,
                        MOErrorException error, Map qosProperties) {
                    Logger.getLogger(UC1_Ex1_Test.class.getName()).log(Level.SEVERE,
                            "Something went wrong...", error);
                    fail(error.toString());
                }

                @Override
                public void getProductsUpdateErrorReceived(MALMessageHeader msgHeader,
                        MOErrorException error, Map qosProperties) {
                    Logger.getLogger(UC1_Ex1_Test.class.getName()).log(Level.SEVERE,
                            "Something went wrong...", error);
                    fail(error.toString());
                }

                @Override
                public void getProductsResponseErrorReceived(MALMessageHeader msgHeader,
                        MOErrorException error, Map qosProperties) {
                    Logger.getLogger(UC1_Ex1_Test.class.getName()).log(Level.SEVERE,
                            "Something went wrong...", error);
                    fail(error.toString());
                }

            });
        } catch (MALException ex) {
            Logger.getLogger(ProductRetrievalTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void testMOErrorListProducts(ProductFilter productFilter,
            TimeWindow creationDate, TimeWindow contentDate) throws MALInteractionException {
        try {
            consumerPR.listProducts(productFilter, creationDate, contentDate);
        } catch (MALException ex) {
            Logger.getLogger(ProductRetrievalTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        }
    }

}
