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
import org.ccsds.mo.mpd.testbed.backends.TMPacketsDataset;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NamedValue;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mpd.productretrieval.consumer.ProductRetrievalAdapter;
import org.ccsds.moims.mo.mpd.structures.AttributeFilterList;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductFilter;
import org.ccsds.moims.mo.mpd.structures.ProductList;
import org.ccsds.moims.mo.mpd.structures.ProductMetadata;
import org.ccsds.moims.mo.mpd.structures.ProductMetadataList;
import org.ccsds.moims.mo.mpd.structures.ProductType;
import org.ccsds.moims.mo.mpd.structures.TimeWindow;
import org.ccsds.moims.mo.mpd.structures.ValueSet;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class UC1_Ex1_Test extends MPDTest {

    private static final TMPacketsDataset backend = new TMPacketsDataset();

    @BeforeClass
    public static void setUpClass() throws IOException {
        System.out.println(TEST_SET_UP_CLASS_1);
        System.out.println(TEST_SET_UP_CLASS_2);
        setUp.setUp(backend, true, true, true);
    }

    /**
     * Test Case 1 - Match APID = 100.
     */
    @Test
    public void testCase_01() {
        System.out.println("Running: testCase_1()");
        UInteger apidValue = new UInteger(100);
        test(apidValue, 1);
    }

    /**
     * Test Case 2 - Match APID = 200.
     */
    @Test
    public void testCase_02() {
        System.out.println("Running: testCase_2()");
        UInteger apidValue = new UInteger(200);
        test(apidValue, 1);
    }

    /**
     * Test Case 3 - Match APID = 300.
     */
    @Test
    public void testCase_03() {
        System.out.println("Running: testCase_3()");
        UInteger apidValue = new UInteger(300);
        test(apidValue, 0);
    }

    /**
     * Test Case 4 - attributeFilter = NULL.
     */
    @Test
    public void testCase_04() {
        System.out.println("Running: testCase_4()");
        UInteger apidValue = null;
        test(apidValue, 2);
    }

    /**
     * Test Case 5 - Match APID = 100 and Date. With: timeWindow =
     * [APID100_TIME_START, APID100_TIME_END].
     */
    @Test
    public void testCase_05() {
        System.out.println("Running: testCase_5()");
        UInteger apidValue = new UInteger(100);
        TimeWindow contentDate = new TimeWindow(TMPacketsDataset.APID100_TIME_START, TMPacketsDataset.APID100_TIME_END);
        testWithTimeWindow(apidValue, 1, contentDate);
    }

    /**
     * Test Case 6 - Match APID = 100 and NOT Date. With: timeWindow =
     * [1970-01-01, 1970-12-31].
     */
    @Test
    public void testCase_06() {
        System.out.println("Running: testCase_6()");
        UInteger apidValue = new UInteger(100);
        Time start = Time.generateTime(1970, 1, 1);
        Time end = Time.generateTime(1970, 12, 31);
        TimeWindow contentDate = new TimeWindow(start, end);
        testWithTimeWindow(apidValue, 0, contentDate);
    }

    /**
     * Test Case 7 - Match NOT APID = 200 and Date. With: timeWindow =
     * [APID100_TIME_START, APID100_TIME_END].
     */
    @Test
    public void testCase_07() {
        System.out.println("Running: testCase_7()");
        UInteger apidValue = new UInteger(200);
        TimeWindow contentDate = new TimeWindow(TMPacketsDataset.APID100_TIME_START, TMPacketsDataset.APID100_TIME_END);
        testWithTimeWindow(apidValue, 0, contentDate);
    }

    /**
     * Test Case 8 - With timeWindow = [1970-01-01, 1970-12-31].
     */
    @Test
    public void testCase_08() {
        System.out.println("Running: testCase_8()");
        Time start = Time.generateTime(1970, 1, 1);
        Time end = Time.generateTime(1970, 12, 31);
        TimeWindow contentDate = new TimeWindow(start, end);
        testWithTimeWindow(null, 0, contentDate);
    }

    /**
     * Test Case 9 - With timeWindow = [APID100_TIME_START, APID200_TIME_END].
     */
    @Test
    public void testCase_09() {
        System.out.println("Running: testCase_9()");
        TimeWindow contentDate = new TimeWindow(TMPacketsDataset.APID100_TIME_START, TMPacketsDataset.APID200_TIME_END);
        testWithTimeWindow(null, 2, contentDate);
    }

    private void test(UInteger apidValue, int expectedNumberOfResults) {
        testWithTimeWindow(apidValue, expectedNumberOfResults, null);
    }

    private synchronized void testWithTimeWindow(UInteger apidValue, int expectedNumberOfResults, TimeWindow contentDate) {
        ProductType productType = backend.typeTMPacketDailyExtract;  //  productType=typeTMPacket
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("myDomain"));

        AttributeFilterList attributeFilter = null;

        // When the apidValue is NULL, then the filtering is off!
        if (apidValue != null) {
            attributeFilter = new AttributeFilterList();
            AttributeList values = new AttributeList();
            values.add(apidValue);
            attributeFilter.add(new ValueSet(new Identifier("APID"), true, values));
        }

        ProductFilter productFilter = new ProductFilter(productType.getName(), domain, null, attributeFilter);
        ProductMetadataList list = null;

        try {
            TimeWindow creationDate = null;
            list = consumerPR.listProducts(productFilter, creationDate, contentDate);
            int size = list.size();
            System.out.println("Number of listed products returned: " + size);
            assertEquals(expectedNumberOfResults, size);
        } catch (MALInteractionException ex) {
            Logger.getLogger(UC1_Ex1_Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        } catch (MALException ex) {
            Logger.getLogger(UC1_Ex1_Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        }

        if (list == null) {
            fail("The list cannot be null");
            return;
        }

        // Prepare the ObjectRefList with the returned data from the previous step
        ObjectRefList productRefs = new ObjectRefList();

        for (ProductMetadata metadata : list) {
            productRefs.add(metadata.getProduct());
        }

        try {
            ProductList returnedProducts = new ProductList();
            long startTime = System.currentTimeMillis();
            final AtomicBoolean ackReceived = new AtomicBoolean(false);
            final AtomicBoolean rspReceived = new AtomicBoolean(false);
            consumerPR.asyncGetProducts(productRefs, new ProductRetrievalAdapter() {

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

            // ------------------------------------------------------------------------
            // Wait while ACK has not been received and 1 second has not passed yet...
            long timeSinceInteractionStarted = System.currentTimeMillis() - startTime;
            while (!ackReceived.get() && timeSinceInteractionStarted < TIMEOUT) {
                // Recalculate it
                timeSinceInteractionStarted = System.currentTimeMillis() - startTime;
            }

            if (!ackReceived.get()) {
                Logger.getLogger(UC1_Ex1_Test.class.getName()).log(
                        Level.SEVERE, "The ACK was not received!");
                fail("The ACK was not received!");
            }

            // ------------------------------------------------------------------------
            // Wait while RESPONSE has not been received and 1 second has not passed yet...
            timeSinceInteractionStarted = System.currentTimeMillis() - startTime;
            while (!rspReceived.get() && timeSinceInteractionStarted < TIMEOUT) {
                // Recalculate it
                timeSinceInteractionStarted = System.currentTimeMillis() - startTime;
            }

            if (!rspReceived.get()) {
                Logger.getLogger(UC1_Ex1_Test.class.getName()).log(Level.SEVERE, "The RESPONSE was not received!");
                fail("The RESPONSE was not received!");
            }

            assertNotNull(returnedProducts);
            int size = returnedProducts.size();
            System.out.println("Number of products returned: " + size);
            assertEquals(expectedNumberOfResults, size);

            // Finish the test if nothing was returned.. (there's nothing else to check)
            if (size == 0) {
                return;
            }

            // Check the timeWindows for all the received products, if one was selected
            if (contentDate != null) {
                for (Product p : returnedProducts) {
                    TimeWindow receivedTW = p.getProductMetadata().getContentDate();

                    if (receivedTW.getStart().getValue() > contentDate.getEnd().getValue()) {
                        fail("The received TimeWindow start time is after the requested TimeWindow end time!");
                    }
                    if (receivedTW.getEnd().getValue() < contentDate.getStart().getValue()) {
                        fail("The received TimeWindow end time is before the requested TimeWindow start time!");
                    }
                }
            }

            // Check that the productType matches
            for (Product p : returnedProducts) {
                if (!p.getProductMetadata().getProductType().equals(productType)) {
                    fail("The productType is not the same! For product: " + p.toString());
                }
            }

            // If there is only one entry, then check if the APID matches
            if (size == 1) {
                Product product = returnedProducts.get(0);
                NamedValueList attributes = product.getProductMetadata().getAttributes();

                // Find the Attribute with the APID and check:
                for (NamedValue att : attributes) {
                    if ("APID".equals(att.getName().toString())) {
                        assertEquals(apidValue, att.getValue());
                    }
                }
            }
            System.out.flush();
        } catch (MALInteractionException ex) {
            Logger.getLogger(UC1_Ex1_Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        } catch (MALException ex) {
            Logger.getLogger(UC1_Ex1_Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        }
    }
}
