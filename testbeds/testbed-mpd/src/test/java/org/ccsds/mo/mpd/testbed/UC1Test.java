/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
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
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mpd.ordermanagement.consumer.OrderManagementStub;
import org.ccsds.moims.mo.mpd.ordermanagement.provider.OrderManagementInheritanceSkeleton;
import org.ccsds.moims.mo.mpd.productorderdelivery.consumer.ProductOrderDeliveryStub;
import org.ccsds.moims.mo.mpd.productorderdelivery.provider.ProductOrderDeliveryInheritanceSkeleton;
import org.ccsds.moims.mo.mpd.productretrieval.consumer.ProductRetrievalAdapter;
import org.ccsds.moims.mo.mpd.productretrieval.consumer.ProductRetrievalStub;
import org.ccsds.moims.mo.mpd.productretrieval.provider.ProductRetrievalInheritanceSkeleton;
import org.ccsds.moims.mo.mpd.structures.ParameterFilterList;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductFilter;
import org.ccsds.moims.mo.mpd.structures.ProductList;
import org.ccsds.moims.mo.mpd.structures.ProductSummary;
import org.ccsds.moims.mo.mpd.structures.ProductSummaryList;
import org.ccsds.moims.mo.mpd.structures.ProductType;
import org.ccsds.moims.mo.mpd.structures.TimeWindow;
import org.ccsds.moims.mo.mpd.structures.ValueSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class UC1Test {

    private static final int TIMEOUT = 1000; // In milliseconds
    private static final String BUMPER = "-------- Running New Test --------";
    private static final SetUpProvidersAndConsumers setUp = new SetUpProvidersAndConsumers();
    private static TMPacketsDataset backend = null;
    private static OrderManagementInheritanceSkeleton providerOM;
    private static OrderManagementStub consumerOM;
    private static ProductOrderDeliveryInheritanceSkeleton providerPOD = null;
    private static ProductOrderDeliveryStub consumerPOD = null;
    private static ProductRetrievalInheritanceSkeleton providerPR = null;
    private static ProductRetrievalStub consumerPR = null;

    public UC1Test() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("Entered: setUpClass() - The Provider and Consumer will be started here!");
        backend = new TMPacketsDataset();
        setUp.setUp(backend, true, true, true);
        providerOM = setUp.getOrderManagementProvider();
        consumerOM = setUp.getOrderManagementConsumer();
        providerPOD = setUp.getProductOrderDeliveryProvider();
        consumerPOD = setUp.getProductOrderConsumer();
        providerPR = setUp.getProductRetrievalProvider();
        consumerPR = setUp.getProductRetrievalConsumer();
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("Entered: tearDownClass()");
        System.out.println("The Provider and Consumer need to be closed here!");

        try {
            // Initialize the Order Management service
            setUp.tearDown();
        } catch (IOException ex) {
            Logger.getLogger(UC1Test.class.getName()).log(
                    Level.SEVERE, "The tearDown() operation failed!", ex);
        }
    }

    @Before
    public void setUp() {
        System.out.println(BUMPER);
        System.out.println("Entered: setUp()");
    }

    @After
    public void tearDown() {
        System.out.println("Entered: tearDown()");
    }

    /**
     * Test Case 1 - Match APID = 100.
     */
    @Test
    public void testCase_1() {
        System.out.println("Running: testCase_1()");
        UInteger apidValue = new UInteger(100);
        test(apidValue, 1);
    }

    /**
     * Test Case 2 - Match APID = 200.
     */
    @Test
    public void testCase_2() {
        System.out.println("Running: testCase_2()");
        UInteger apidValue = new UInteger(200);
        test(apidValue, 1);
    }

    /**
     * Test Case 3 - Match APID = 300.
     */
    @Test
    public void testCase_3() {
        System.out.println("Running: testCase_3()");
        UInteger apidValue = new UInteger(300);
        test(apidValue, 0);
    }

    /**
     * Test Case 4 - parameterFilter = NULL.
     */
    @Test
    public void testCase_4() {
        System.out.println("Running: testCase_4()");
        UInteger apidValue = null;
        test(apidValue, 2);
    }

    /**
     * Test Case 5 - Match APID = 100 and timeWindow = [APID100_TIME_START,
     * APID100_TIME_END].
     */
    @Test
    public void testCase_5() {
        System.out.println("Running: testCase_5()");
        // TBD
        /*
        UInteger apidValue = new UInteger(100);
        TimeWindow timeWindow = new TimeWindow(TMPacketsDataset.APID100_TIME_START, TMPacketsDataset.APID100_TIME_END);
        testWithTimeWindow(apidValue, 1, timeWindow);
         */
    }

    private void test(UInteger apidValue, int expectedNumberOfResults) {
        testWithTimeWindow(apidValue, expectedNumberOfResults, null);
    }

    private void testWithTimeWindow(UInteger apidValue, int expectedNumberOfResults, TimeWindow timeWindow) {
        ObjectRef<ProductType> productType = backend.getTMPacketsProductRef();  //  productType=typeTMPacket
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("myDomain"));

        ParameterFilterList parameterFilter = null;

        // When the apidValue is NULL, then the filtering is off!
        if (apidValue != null) {
            parameterFilter = new ParameterFilterList();
            AttributeList values = new AttributeList();
            values.add(apidValue);
            parameterFilter.add(new ValueSet(new Identifier("APID"), true, values));
        }

        ProductFilter productFilter = new ProductFilter(productType, domain, null, parameterFilter);
        ProductSummaryList list = null;

        try {
            TimeWindow creationDate = null;
            list = consumerPR.listProducts(productFilter, creationDate, timeWindow);
            int size = list.size();
            assertEquals(expectedNumberOfResults, size);
        } catch (MALInteractionException ex) {
            Logger.getLogger(UC1Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        } catch (MALException ex) {
            Logger.getLogger(UC1Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        }

        if (list == null) {
            fail("The list cannot be null");
            return;
        }

        // Prepare the ObjectRefList with the returned data from the previous step
        ObjectRefList productRefs = new ObjectRefList();

        for (ProductSummary metadata : list) {
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
                    Logger.getLogger(UC1Test.class.getName()).log(Level.SEVERE,
                            "Something went wrong...", error);
                    fail(error.toString());
                }

                @Override
                public void getProductsUpdateErrorReceived(MALMessageHeader msgHeader,
                        MOErrorException error, Map qosProperties) {
                    Logger.getLogger(UC1Test.class.getName()).log(Level.SEVERE,
                            "Something went wrong...", error);
                    fail(error.toString());
                }

                @Override
                public void getProductsResponseErrorReceived(MALMessageHeader msgHeader,
                        MOErrorException error, Map qosProperties) {
                    Logger.getLogger(UC1Test.class.getName()).log(Level.SEVERE,
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
                Logger.getLogger(UC1Test.class.getName()).log(Level.SEVERE, "The ACK was not received!");
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
                Logger.getLogger(UC1Test.class.getName()).log(Level.SEVERE, "The RESPONSE was not received!");
                fail("The RESPONSE was not received!");
            }

            assertNotNull(returnedProducts);
            int size = returnedProducts.size();
            System.out.println("Number of products returned: " + size);
            assertEquals(expectedNumberOfResults, size);

            // Finish the test if there's nothing else to check
            if (size == 0) {
                return;
            }

            // If there is only one entry, then check if the APID matches
            if (size == 1) {
                Product product = returnedProducts.get(0);
                NamedValueList attributes = product.getParameters();

                // Find the Attribute with the APID and check:
                for (NamedValue att : attributes) {
                    if ("APID".equals(att.getName().toString())) {
                        assertEquals(apidValue, att.getValue());
                    }
                }
            }
        } catch (MALInteractionException ex) {
            Logger.getLogger(UC1Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        } catch (MALException ex) {
            Logger.getLogger(UC1Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        }
    }
}
