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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.ccsds.mo.mpd.testbed.MPDTest.consumerOM;
import static org.ccsds.mo.mpd.testbed.MPDTest.consumerPOD;
import org.ccsds.mo.mpd.testbed.backends.TMPacketsDataset;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilter;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilterList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.URIList;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mpd.productorderdelivery.consumer.ProductOrderDeliveryAdapter;
import org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductFilter;
import org.ccsds.moims.mo.mpd.structures.ProductMetadata;
import org.ccsds.moims.mo.mpd.structures.ProductMetadataList;
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
public class UC3_Ex2_Test extends MPDTest {

    private static final TMPacketsDataset backend = new TMPacketsDataset();
    private static final URI TMP_DIR = getHomeTmpDir();

    @BeforeClass
    public static void setUpClass() throws IOException {
        System.out.println(TEST_SET_UP_CLASS_1);
        System.out.println(TEST_SET_UP_CLASS_2);
        setUp.setUp(backend, true, true, true);
    }

    private static URI getHomeTmpDir() {
        File homeDirectory = new File(System.getProperty("user.home"));
        File targetDir = new File(homeDirectory, "tmp");
        if (!targetDir.exists()) {
            // Create the directory if it does not exist:
            targetDir.mkdirs();
        }
        return new URI("file://" + targetDir.getAbsolutePath());
    }

    /**
     * Test Case 1.
     */
    @Test
    public void testCase_1() {
        System.out.println("Running: testCase_1()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        DeliveryMethodEnum delivery = DeliveryMethodEnum.FILETRANSFER;
        URI deliverTo = TMP_DIR;
        Identifier productType = null;
        test(user, domain, delivery, deliverTo, productType, 1);
    }

    /**
     * Test Case 2.
     */
    @Test
    public void testCase_2() {
        System.out.println("Running: testCase_2()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        DeliveryMethodEnum delivery = DeliveryMethodEnum.FILETRANSFER;
        URI deliverTo = TMP_DIR;
        Identifier productType = null;
        test(user, domain, delivery, deliverTo, productType, 1);
    }

    private void test(Identifier user, IdentifierList domain, DeliveryMethodEnum deliveryMethod,
            URI deliverTo, Identifier productType, int expectedNumberOfProducts) {
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
            StandingOrder orderDetails = new StandingOrder(null, orderUser,
                    productFilter, null, deliveryMethod, deliverTo, null);
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
            ProductMetadataList returnedProductMetadata = new ProductMetadataList();
            StringList returnedFilename = new StringList();
            URIList returnedDeliveredTo = new URIList();
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
            consumerPOD.asyncNotifyProductDeliveryRegister(subscription, new ProductOrderDeliveryAdapter() {
                @Override
                public void notifyProductDeliveryRegisterAckReceived(MALMessageHeader msgHeader,
                        java.util.Map qosProperties) {
                    System.out.println("Reached: notifyProductDeliveryRegisterAckReceived()");
                    ackReceived.set(true);
                }

                @Override
                public void notifyProductDeliveryRegisterErrorReceived(MALMessageHeader msgHeader,
                        org.ccsds.moims.mo.mal.MOErrorException error,
                        java.util.Map qosProperties) {
                    System.out.println("Reached: notifyProductDeliveryRegisterErrorReceived()");
                    fail(error.toString());
                }

                @Override
                public void notifyProductDeliveryDeregisterAckReceived(MALMessageHeader msgHeader,
                        java.util.Map qosProperties) {
                    System.out.println("Reached: notifyProductDeliveryDeregisterAckReceived()");
                }

                @Override
                public void notifyProductDeliveryNotifyReceived(MALMessageHeader msgHeader,
                        org.ccsds.moims.mo.mal.structures.Identifier subscriptionId,
                        org.ccsds.moims.mo.mal.structures.UpdateHeader updateHeader,
                        org.ccsds.moims.mo.mpd.structures.ProductMetadata metadata,
                        String filename,
                        org.ccsds.moims.mo.mal.structures.URI deliveredTo,
                        java.util.Map qosProperties) {
                    System.out.println("Reached: notifyProductDeliveryNotifyReceived()");
                    long duration = System.currentTimeMillis() - startTime;
                    System.out.println("NOTIFY received in: " + duration + " ms");
                    notifyReceived.set(true);
                    returnedProductMetadata.add(metadata);
                    returnedFilename.add(filename);
                    returnedDeliveredTo.add(deliveredTo);
                }

                @Override
                public void notifyProductDeliveryNotifyErrorReceived(MALMessageHeader msgHeader,
                        org.ccsds.moims.mo.mal.MOErrorException error,
                        java.util.Map qosProperties) {
                    System.out.println("Reached: notifyProductDeliveryNotifyErrorReceived()");
                    fail(error.toString());
                }
            });

            String productName = "tmData1";
            // Check that the directory to deliver products exists
            String specifiedLocation = deliverTo.getValue().replace("file://", "");
            File deliveryDirectory = new File(specifiedLocation);
            if (!deliveryDirectory.exists()) {
                fail("The directory already exists in: " + specifiedLocation);
            }

            // Check that the product does not exist in the specified location
            File productLocation = new File(specifiedLocation, productName);
            if (productLocation.exists()) {
                fail("The file already exists in: " + productLocation.getAbsolutePath());
            }

            // Provider pushes a new Product (on the backend)
            IdentifierList productDomain = new IdentifierList();
            productDomain.add(new Identifier("nasa"));
            productDomain.add(new Identifier("hubble"));
            ObjectRef<Product> ref = new ObjectRef(productDomain, Product.TYPE_ID.getTypeId(), new Identifier(productName), new UInteger(1));
            Blob productBody = new Blob(new byte[]{0x01, 0x02, 0x03});
            ProductMetadata metadata = new ProductMetadata(backend.typeTMPacketDailyExtract, ref, Time.now(),
                    null, null, TMPacketsDataset.timeWindowAPID100, null, "description");
            backend.addNewProduct(ref, productBody, metadata);

            // ------------------------------------------------------------------------
            // Wait while ACK has not been received and 1 second has not passed yet...
            long timeSinceInteractionStarted = System.currentTimeMillis() - startTime;
            while (!ackReceived.get() && timeSinceInteractionStarted < TIMEOUT) {
                // Recalculate it
                timeSinceInteractionStarted = System.currentTimeMillis() - startTime;
            }

            if (!ackReceived.get()) {
                Logger.getLogger(UC3_Ex2_Test.class.getName()).log(
                        Level.SEVERE, "The ACK was not received!");
                fail("The ACK was not received!");
            }

            // Check that the product was created in the specified location
            if (!productLocation.exists()) {
                fail("The product file does not exist in: " + productLocation.getAbsolutePath());
            }

        } catch (MALInteractionException ex) {
            Logger.getLogger(UC3_Ex1_Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MALException ex) {
            Logger.getLogger(UC3_Ex1_Test.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
