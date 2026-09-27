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

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.mo.mpd.testbed.backends.FifteenProductsDataset;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.IntegerList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mpd.DeliveryFailedException;
import org.ccsds.moims.mo.mpd.InvalidException;
import org.ccsds.moims.mo.mpd.TooManyException;
import org.ccsds.moims.mo.mpd.UnknownException;
import org.ccsds.moims.mo.mpd.productretrieval.consumer.ProductRetrievalAdapter;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductFilter;
import org.ccsds.moims.mo.mpd.structures.ProductList;
import org.ccsds.moims.mo.mpd.structures.ProductMetadata;
import org.ccsds.moims.mo.mpd.structures.ProductMetadataList;
import org.ccsds.moims.mo.mpd.structures.TimeWindow;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import static org.junit.Assert.*;

/**
 *
 */
public class ProductRetrievalTest extends MPDTest {

    private static final FifteenProductsDataset dataset = new FifteenProductsDataset();
    private static final URI TMP_DIR = getHomeTmpDir();

    @BeforeClass
    public static void setUpClass() throws IOException {
        System.out.println(TEST_SET_UP_CLASS_1);
        System.out.println(TEST_SET_UP_CLASS_2);
        setUp.setUp(dataset, false, false, true);
    }

    /**
     * Test Case 1.
     */
    @Test
    public void testCase_01() {
        System.out.println("Running: testCase_01()");

        ProductFilter productFilter = new ProductFilter();
        Time now = Time.now();
        TimeWindow creationDate = new TimeWindow(now, new Time(now.getValue() - 100));
        TimeWindow contentDate = null;
        assertThrows(InvalidException.class, () -> testMOErrorListProducts(productFilter, creationDate, contentDate));
        Logger.getLogger(ProductRetrievalTest.class.getName()).log(Level.INFO, "Error returned successfully!");
    }

    /**
     * Test Case 2.
     */
    @Test
    public void testCase_02() {
        System.out.println("Running: testCase_02()");

        ProductFilter productFilter = new ProductFilter();
        Time now = Time.now();
        TimeWindow creationDate = null;
        TimeWindow contentDate = new TimeWindow(now, new Time(now.getValue() - 100));
        assertThrows(InvalidException.class, () -> testMOErrorListProducts(productFilter, creationDate, contentDate));
        Logger.getLogger(ProductRetrievalTest.class.getName()).log(Level.INFO, "Error returned successfully!");
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
        } catch (MOErrorException ex) {
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

        IdentifierList domain = new IdentifierList();
        Long typeId = Product.TYPE_ID.getTypeId();
        Identifier key = new Identifier("Non_Existing_Key");
        UInteger objectVersion = new UInteger(1);

        ObjectRefList productRefs = new ObjectRefList();
        productRefs.add(new ObjectRef(domain, typeId, key, objectVersion));
        assertUnknownAt(0, () -> this.testGetProducts(productRefs));
    }

    /**
     * Test Case 5.
     */
    @Test
    public void testCase_05() {
        System.out.println("Running: testCase_05()");

        IdentifierList domain = new IdentifierList();
        Long typeId = Product.TYPE_ID.getTypeId();
        Identifier key = new Identifier("Non_Existing_Key");
        UInteger objectVersion = new UInteger(1);

        ObjectRefList productRefs = new ObjectRefList();
        productRefs.add(dataset.ref);
        productRefs.add(new ObjectRef(domain, typeId, key, objectVersion));
        assertUnknownAt(1, () -> this.testGetProducts(productRefs));
    }

    /**
     * Test Case 6.
     */
    @Test
    public void testCase_06() {
        System.out.println("Running: testCase_06()");

        try {
            ObjectRefList productRefs = new ObjectRefList();
            productRefs.add(dataset.ref);
            URI deliverTo = TMP_DIR;
            this.testDeliverProductFiles(productRefs, deliverTo);
        } catch (MOErrorException ex) {
            Logger.getLogger(ProductRetrievalTest.class.getName()).log(Level.INFO, "Failed!", ex);
            fail("The operation was not expected to throw an exception!");
        }
    }

    /**
     * Test Case 7.
     */
    @Test
    public void testCase_07() {
        System.out.println("Running: testCase_07()");

        ObjectRefList productRefs = new ObjectRefList();
        productRefs.add(dataset.ref);
        String path = TMP_DIR.getValue().replace("file://", "");
        File targetDir = new File(path, "wrong_directory");
        URI deliverTo = new URI("file://" + targetDir.getAbsolutePath());
        DeliveryFailedException ex = assertThrows(DeliveryFailedException.class,
                () -> this.testDeliverProductFiles(productRefs, deliverTo));
        String extraInformation = ((Union) ex.getExtraInformation()).getStringValue();
        Logger.getLogger(ProductRetrievalTest.class.getName()).log(Level.INFO,
                "Error returned successfully! With extraInformation message: {0}", extraInformation);
    }

    /**
     * Test Case 8.
     */
    @Test
    public void testCase_08() {
        System.out.println("Running: testCase_08()");

        IdentifierList domain = new IdentifierList();
        Long typeId = Product.TYPE_ID.getTypeId();
        Identifier key = new Identifier("Non_Existing_Key");
        UInteger objectVersion = new UInteger(1);

        ObjectRefList productRefs = new ObjectRefList();
        productRefs.add(new ObjectRef(domain, typeId, key, objectVersion));
        URI deliverTo = TMP_DIR;
        assertUnknownAt(0, () -> this.testDeliverProductFiles(productRefs, deliverTo));
    }

    /**
     * Test Case 9.
     */
    @Test
    public void testCase_09() {
        System.out.println("Running: testCase_09()");

        ProductFilter productFilter = new ProductFilter();
        TimeWindow creationDate = null;
        TimeWindow contentDate = null;
        assertThrows(TooManyException.class, () -> testMOErrorListProducts(productFilter, creationDate, contentDate));
        Logger.getLogger(ProductRetrievalTest.class.getName()).log(Level.INFO, "Error returned successfully!");
    }

    /**
     * Asserts that the call raises the MPD Unknown error for exactly one product, the one at
     * the given index of the list it was given.
     */
    private static void assertUnknownAt(int expectedIndex, ThrowingRunnable call) {
        UnknownException ex = assertThrows(UnknownException.class, call);
        Logger.getLogger(ProductRetrievalTest.class.getName()).log(Level.INFO, "Error returned successfully!");
        // The error lists the index of each product that was not found
        IntegerList indexes = (IntegerList) ex.getExtraInformation();
        assertEquals("The 'Unknown' exception does not have 1 entry!", 1, indexes.size());
        assertEquals(expectedIndex, indexes.get(0).intValue());
    }

    private void testDeliverProductFiles(ObjectRefList productRefs, URI deliverTo)
            throws UnknownException, DeliveryFailedException, MALStandardError {
        try {
            ProductMetadataList returnedMetadatas = new ProductMetadataList();
            long startTime = System.currentTimeMillis();
            final AtomicBoolean ackReceived = new AtomicBoolean(false);
            final AtomicBoolean updateReceived = new AtomicBoolean(false);
            final AtomicBoolean rspReceived = new AtomicBoolean(false);

            consumerPR.deliverProductFiles(productRefs, deliverTo, new ProductRetrievalAdapter() {

                @Override
                public void deliverProductFilesAckReceived(MALMessageHeader msgHeader, Map qosProperties) {
                    long duration = System.currentTimeMillis() - startTime;
                    System.out.println("ACK received in: " + duration + " ms");
                    ackReceived.set(true);
                }

                @Override
                public void deliverProductFilesUpdateReceived(MALMessageHeader msgHeader,
                        ProductMetadata metadata, String filename, Boolean success, Map qosProperties) {
                    long duration = System.currentTimeMillis() - startTime;
                    System.out.println("UPDATE received in: " + duration + " ms");
                    returnedMetadatas.add(metadata);
                    updateReceived.set(true);
                }

                @Override
                public void deliverProductFilesResponseReceived(MALMessageHeader msgHeader, Map qosProperties) {
                    long duration = System.currentTimeMillis() - startTime;
                    System.out.println("RESPONSE received in: " + duration + " ms");
                    rspReceived.set(true);
                }

                @Override
                public void deliverProductFilesAckErrorReceived(MALMessageHeader msgHeader,
                        MOErrorException error, Map qosProperties) {
                    Logger.getLogger(ProductRetrievalTest.class.getName()).log(
                            Level.SEVERE, "Something went wrong...", error);
                    fail(error.toString());
                }

                @Override
                public void deliverProductFilesUpdateErrorReceived(MALMessageHeader msgHeader,
                        MOErrorException error, Map qosProperties) {
                    Logger.getLogger(ProductRetrievalTest.class.getName()).log(
                            Level.SEVERE, "Something went wrong...", error);
                    fail(error.toString());
                }

                @Override
                public void deliverProductFilesResponseErrorReceived(MALMessageHeader msgHeader,
                        MOErrorException error, Map qosProperties) {
                    Logger.getLogger(ProductRetrievalTest.class.getName()).log(
                            Level.SEVERE, "Something went wrong...", error);
                    fail(error.toString());
                }

            });

            // ------------------------------------------------------------------------
            // Wait while ACK has not been received and 1 second has not passed yet...
            long timeSinceInteractionStarted = System.currentTimeMillis() - startTime;
            while (!ackReceived.get() && timeSinceInteractionStarted < TIMEOUT) {
                // Recalculate it
                timeSinceInteractionStarted = System.currentTimeMillis() - startTime;
            }

            if (!ackReceived.get()) {
                Logger.getLogger(ProductRetrievalTest.class.getName()).log(
                        Level.SEVERE, "The ACK was not received!");
                fail("The ACK was not received!");
            }

            // Wait while UPDATE has not been received and 1 second has not passed yet...
            while (!updateReceived.get() && timeSinceInteractionStarted < TIMEOUT) {
                // Recalculate it
                timeSinceInteractionStarted = System.currentTimeMillis() - startTime;
            }

            // Were we expecting to receive at least one product?
            if (!updateReceived.get()) {
                Logger.getLogger(ProductRetrievalTest.class.getName()).log(
                        Level.SEVERE, "The UPDATE was not received!");
                fail("The UPDATE was not received!");
            }

            // Did we receive the product(s) notifications?
            assertNotNull(returnedMetadatas);
            int size = returnedMetadatas.size();
            System.out.println("Number of metadata entries returned: " + size);
            assertEquals(1, size);

            // ------------------------------------------------------------------------
            // Wait while RESPONSE has not been received and 1 second has not passed yet...
            timeSinceInteractionStarted = System.currentTimeMillis() - startTime;
            while (!rspReceived.get() && timeSinceInteractionStarted < TIMEOUT) {
                // Recalculate it
                timeSinceInteractionStarted = System.currentTimeMillis() - startTime;
            }

            // Were we expecting to receive at least one product?
            if (!rspReceived.get()) {
                Logger.getLogger(ProductRetrievalTest.class.getName()).log(
                        Level.SEVERE, "The RESPONSE was not received!");
                fail("The RESPONSE was not received!");
            }
        } catch (MALException ex) {
            Logger.getLogger(ProductRetrievalTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void testGetProducts(ObjectRefList productRefs) throws UnknownException, MALStandardError {
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
                    long duration = System.currentTimeMillis() - startTime;
                    System.out.println("UPDATE received in: " + duration + " ms");
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
                    Logger.getLogger(ProductRetrievalTest.class.getName()).log(
                            Level.SEVERE, "Something went wrong...", error);
                    fail(error.toString());
                }

                @Override
                public void getProductsUpdateErrorReceived(MALMessageHeader msgHeader,
                        MOErrorException error, Map qosProperties) {
                    Logger.getLogger(ProductRetrievalTest.class.getName()).log(
                            Level.SEVERE, "Something went wrong...", error);
                    fail(error.toString());
                }

                @Override
                public void getProductsResponseErrorReceived(MALMessageHeader msgHeader,
                        MOErrorException error, Map qosProperties) {
                    Logger.getLogger(ProductRetrievalTest.class.getName()).log(
                            Level.SEVERE, "Something went wrong...", error);
                    fail(error.toString());
                }

            });
        } catch (MALException ex) {
            Logger.getLogger(ProductRetrievalTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void testMOErrorListProducts(ProductFilter productFilter,
            TimeWindow creationDate, TimeWindow contentDate)
            throws InvalidException, TooManyException, MALStandardError {
        try {
            consumerPR.listProducts(productFilter, creationDate, contentDate);
        } catch (MALException ex) {
            Logger.getLogger(ProductRetrievalTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        }
    }

}
