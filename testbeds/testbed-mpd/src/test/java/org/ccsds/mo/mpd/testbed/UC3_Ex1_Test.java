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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.ccsds.mo.mpd.testbed.MPDTest.consumerOM;
import org.ccsds.mo.mpd.testbed.backends.TMPacketsDataset;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilter;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilterList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mpd.productorderdelivery.consumer.ProductOrderDeliveryAdapter;
import org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductFilter;
import org.ccsds.moims.mo.mpd.structures.ProductList;
import org.ccsds.moims.mo.mpd.structures.ProductMetadata;
import org.ccsds.moims.mo.mpd.structures.StandingOrder;
import org.ccsds.moims.mo.mpd.structures.StandingOrderList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
public class UC3_Ex1_Test extends MPDTest {

    private static final TMPacketsDataset backend = new TMPacketsDataset();

    @BeforeClass
    public static void setUpClass() throws IOException {
        System.out.println(TEST_SET_UP_CLASS_1);
        System.out.println(TEST_SET_UP_CLASS_2);
        setUp.setUp(backend, true, true, true);
    }

    /**
     * Test Case 1.
     */
    @Test
    public void testCase_01() {
        System.out.println("Running: testCase_1()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        Identifier productType = null;
        test(user, domain, DeliveryMethodEnum.SERVICE_COMPLETE, productType, 1);
    }

    /**
     * Test Case 2.
     */
    @Test
    public void testCase_02() {
        System.out.println("Running: testCase_2()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        Identifier productType = null;
        test(user, domain, DeliveryMethodEnum.FILETRANSFER, productType, 0);
    }

    /**
     * Test Case 3.
     */
    @Test
    public void testCase_03() {
        System.out.println("Running: testCase_3()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        Identifier productType = new Identifier("typeTMPacketDailyExtract");
        test(user, domain, DeliveryMethodEnum.SERVICE_COMPLETE, productType, 1);
    }

    /**
     * Test Case 4.
     */
    @Test
    public void testCase_04() {
        System.out.println("Running: testCase_4()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        Identifier productType = new Identifier("typeImage");
        test(user, domain, DeliveryMethodEnum.SERVICE_COMPLETE, productType, 0);
    }

    /**
     * Test Case 5.
     */
    @Test
    public void testCase_05() {
        System.out.println("Running: testCase_5()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        Identifier productType = new Identifier("*");
        test(user, domain, DeliveryMethodEnum.SERVICE_COMPLETE, productType, 0);
    }

    /**
     * Test Case 6.
     */
    @Test
    public void testCase_06() {
        System.out.println("Running: testCase_6()");
        Identifier user = null;
        IdentifierList domain = null;
        Identifier productType = null;
        test(user, domain, DeliveryMethodEnum.SERVICE_COMPLETE, productType, 1);
    }

    /**
     * Test Case 7.
     */
    @Test
    public void testCase_07() {
        System.out.println("Running: testCase_7()");
        Identifier user = new Identifier("john.smith");
        IdentifierList domain = null;
        Identifier productType = null;
        test(user, domain, DeliveryMethodEnum.SERVICE_COMPLETE, productType, 0);
    }

    /**
     * Test Case 8.
     */
    @Test
    public void testCase_08() {
        System.out.println("Running: testCase_8()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("*"));
        Identifier productType = null;
        test(user, domain, DeliveryMethodEnum.SERVICE_COMPLETE, productType, 1);
    }

    /**
     * Test Case 9.
     */
    @Test
    public void testCase_09() {
        System.out.println("Running: testCase_9()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("esa"));
        domain.add(new Identifier("juice"));
        Identifier productType = null;
        test(user, domain, DeliveryMethodEnum.SERVICE_COMPLETE, productType, 0);
    }

    /**
     * Test Case 10.
     */
    @Test
    public void testCase_10() {
        System.out.println("Running: testCase_10()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("nasa"));
        domain.add(new Identifier("dart"));
        Identifier productType = null;
        test(user, domain, DeliveryMethodEnum.SERVICE_COMPLETE, productType, 0);
    }

    /**
     * Test Case 11.
     */
    @Test
    public void testCase_11() {
        System.out.println("Running: testCase_11()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("nasa"));
        domain.add(new Identifier("*"));
        Identifier productType = null;
        test(user, domain, DeliveryMethodEnum.SERVICE_COMPLETE, productType, 1);
    }

    /**
     * Test Case 12.
     */
    @Test
    public void testCase_12() {
        System.out.println("Running: testCase_12()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("nasa"));
        domain.add(new Identifier("hubble"));
        Identifier productType = null;
        test(user, domain, DeliveryMethodEnum.SERVICE_COMPLETE, productType, 1);
    }

    private synchronized void test(Identifier user, IdentifierList domain,
            DeliveryMethodEnum deliveryMethod, Identifier productType, int expectedNumberOfProducts) {
        try {
            StandingOrderList standingOrders = consumerOM.listStandingOrders(user, domain);
            int size = standingOrders.size();
            System.out.println("Standing Orders returned size: " + size);
            assertEquals(0, size);
        } catch (MALInteractionException ex) {
            Logger.getLogger(UC3_Ex1_Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        } catch (MALException ex) {
            Logger.getLogger(UC3_Ex1_Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        }

        Identifier orderUser = new Identifier("john.doe");
        Long orderID = null;
        try {
            ProductFilter productFilter = new ProductFilter(productType, domain, null, null);
            StandingOrder orderDetails = new StandingOrder(null, orderUser, productFilter, null, deliveryMethod, null, null);
            orderID = consumerOM.submitStandingOrder(orderDetails);
            System.out.println("The returned orderID is: " + orderID);
            assertNotNull(orderID);
        } catch (MALInteractionException ex) {
            Logger.getLogger(UC3_Ex1_Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        } catch (MALException ex) {
            Logger.getLogger(UC3_Ex1_Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        }

        try {
            StandingOrderList standingOrders = consumerOM.listStandingOrders(new Identifier("*"), domain);
            int size = standingOrders.size();
            System.out.println("Standing Orders returned size: " + size);
            assertEquals(1, size);

            // If there is at least one:
            if (!standingOrders.isEmpty()) {
                StandingOrder receivedOrder = standingOrders.get(0);
                assertEquals(orderUser.getValue(), receivedOrder.getUser().getValue());
                assertEquals(deliveryMethod.getNumericValue(), receivedOrder.getDeliveryMethod().getNumericValue());
            }
        } catch (MALInteractionException ex) {
            Logger.getLogger(UC3_Ex1_Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        } catch (MALException ex) {
            Logger.getLogger(UC3_Ex1_Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        }

        try {
            ProductList returnedProducts = new ProductList();
            long startTime = System.currentTimeMillis();
            final AtomicBoolean ackReceived = new AtomicBoolean(false);
            final AtomicBoolean notifyReceived = new AtomicBoolean(false);
            SubscriptionFilterList filters = null;
            if (user != null) {
                filters = new SubscriptionFilterList();
                AttributeList values = new AttributeList();
                values.add(user);
                filters.add(new SubscriptionFilter(new Identifier("user"), values));
            }

            Subscription subscription = new Subscription(new Identifier("myTestKey"), null, null, filters);
            consumerPOD.asyncDeliverProductsRegister(subscription, new ProductOrderDeliveryAdapter() {
                @Override
                public void deliverProductsRegisterAckReceived(
                        org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
                        java.util.Map qosProperties) {
                    System.out.println("Reached: deliverProductsRegisterAckReceived()");
                    ackReceived.set(true);
                }

                @Override
                public void deliverProductsRegisterErrorReceived(
                        org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
                        org.ccsds.moims.mo.mal.MOErrorException error,
                        java.util.Map qosProperties) {
                    System.out.println("Reached: deliverProductsRegisterErrorReceived()");
                    fail(error.toString());
                }

                @Override
                public void deliverProductsDeregisterAckReceived(
                        org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
                        java.util.Map qosProperties) {
                    System.out.println("Reached: deliverProductsDeregisterAckReceived()");
                }

                @Override
                public void deliverProductsNotifyReceived(
                        org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
                        org.ccsds.moims.mo.mal.structures.Identifier subscriptionId,
                        org.ccsds.moims.mo.mal.structures.UpdateHeader updateHeader,
                        org.ccsds.moims.mo.mpd.structures.Product product,
                        java.util.Map qosProperties) {
                    System.out.println("Reached: deliverProductsNotifyReceived()");
                    long duration = System.currentTimeMillis() - startTime;
                    System.out.println("NOTIFY received in: " + duration + " ms");
                    returnedProducts.add(product);
                    notifyReceived.set(true);
                }

                @Override
                public void deliverProductsNotifyErrorReceived(
                        org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
                        org.ccsds.moims.mo.mal.MOErrorException error,
                        java.util.Map qosProperties) {
                    System.out.println("Reached: deliverProductsNotifyErrorReceived()");
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

            // Provider pushes a new Product (on the backend)
            IdentifierList productDomain = new IdentifierList();
            productDomain.add(new Identifier("nasa"));
            productDomain.add(new Identifier("hubble"));
            ObjectRef<Product> ref = new ObjectRef(productDomain, Product.TYPE_ID.getTypeId(), new Identifier("tmData1"), new UInteger(1));
            Blob productBody = new Blob(new byte[]{0x01, 0x02, 0x03});
            ProductMetadata metadata = new ProductMetadata(backend.typeTMPacketDailyExtract, ref, Time.now(),
                    null, null, TMPacketsDataset.contentTimeWindowAPID100, null, "description", null);
            backend.addNewProduct(ref, productBody, metadata);

            // ------------------------------------------------------------------------
            // Wait while NOTIFY has not been received and 1 second has not passed yet...
            timeSinceInteractionStarted = System.currentTimeMillis() - startTime;
            while (!notifyReceived.get() && timeSinceInteractionStarted < TIMEOUT) {
                // Recalculate it
                timeSinceInteractionStarted = System.currentTimeMillis() - startTime;
            }

            // Were we expecting to receive at least one product?
            if (!notifyReceived.get() && expectedNumberOfProducts != 0) {
                Logger.getLogger(UC1_Ex1_Test.class.getName()).log(
                        Level.SEVERE, "The NOTIFY was not received!");
                fail("The NOTIFY was not received!");
            }

            try {
                consumerOM.cancelStandingOrder(orderID);
            } catch (MALInteractionException ex) {
                Logger.getLogger(UC3_Ex1_Test.class.getName()).log(Level.SEVERE, null, ex);
                fail(ex.toString());
            } catch (MALException ex) {
                Logger.getLogger(UC3_Ex1_Test.class.getName()).log(Level.SEVERE, null, ex);
                fail(ex.toString());
            }

            // Did we receive the product(s)?
            assertNotNull(returnedProducts);
            int size = returnedProducts.size();
            System.out.println("Number of products returned: " + size);
            assertEquals(expectedNumberOfProducts, size);

            // Finish the test if nothing was returned.. (there's nothing else to check)
            if (size == 0) {
                return;
            }

            // Check that the ProductType matches!
            Product returnedProduct = returnedProducts.get(0);
            // Don't check if it is set to null
            if (productType != null) {
                assertEquals(productType, returnedProduct.getProductType().getName());
            }

            IdentifierList subscriptions = new IdentifierList();
            subscriptions.add(subscription.getSubscriptionId());
            consumerPOD.deliverProductsDeregister(subscriptions);
            System.out.flush();
        } catch (MALInteractionException ex) {
            Logger.getLogger(UC3_Ex1_Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MALException ex) {
            Logger.getLogger(UC3_Ex1_Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
