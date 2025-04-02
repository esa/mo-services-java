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
import org.ccsds.mo.mpd.testbed.backends.ImagesDataset;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NamedValue;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilter;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilterList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.URIList;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mpd.productorderdelivery.consumer.ProductOrderDeliveryAdapter;
import org.ccsds.moims.mo.mpd.structures.AttributeFilterList;
import org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductFilter;
import org.ccsds.moims.mo.mpd.structures.ProductMetadata;
import org.ccsds.moims.mo.mpd.structures.ProductMetadataList;
import org.ccsds.moims.mo.mpd.structures.StandingOrder;
import org.ccsds.moims.mo.mpd.structures.StandingOrderList;
import org.ccsds.moims.mo.mpd.structures.StringPattern;
import org.ccsds.moims.mo.mpd.structures.ValueRange;
import org.ccsds.moims.mo.mpd.structures.ValueSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
public class UC3_Ex2_Test extends MPDTest {

    private static final ImagesDataset backend = new ImagesDataset();
    private static final URI TMP_DIR = getHomeTmpDir();

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
        DeliveryMethodEnum delivery = DeliveryMethodEnum.FILETRANSFER;
        URI deliverTo = TMP_DIR;
        Identifier source = new Identifier("forest flyover");
        Identifier productType = null;
        test(user, domain, delivery, deliverTo, source, productType, 1);
    }

    /**
     * Test Case 2.
     */
    @Test
    public void testCase_02() {
        System.out.println("Running: testCase_2()");
        Identifier user = new Identifier("john.smith");
        IdentifierList domain = null;
        DeliveryMethodEnum delivery = DeliveryMethodEnum.FILETRANSFER;
        URI deliverTo = TMP_DIR;
        Identifier source = new Identifier("forest flyover");
        Identifier productType = null;
        test(user, domain, delivery, deliverTo, source, productType, 0);
    }

    /**
     * Test Case 3.
     */
    @Test
    public void testCase_03() {
        System.out.println("Running: testCase_3()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("esa"));
        domain.add(new Identifier("juice"));
        DeliveryMethodEnum delivery = DeliveryMethodEnum.FILETRANSFER;
        URI deliverTo = TMP_DIR;
        Identifier source = new Identifier("forest flyover");
        Identifier productType = null;
        test(user, domain, delivery, deliverTo, source, productType, 0);
    }

    /**
     * Test Case 4.
     */
    @Test
    public void testCase_04() {
        System.out.println("Running: testCase_4()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        DeliveryMethodEnum delivery = DeliveryMethodEnum.SERVICE_JUST_METADATA;
        URI deliverTo = TMP_DIR;
        Identifier source = new Identifier("forest flyover");
        Identifier productType = null;
        test(user, domain, delivery, deliverTo, source, productType, 0);
    }

    /**
     * Test Case 5.
     */
    @Test
    public void testCase_05() {
        System.out.println("Running: testCase_3()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        DeliveryMethodEnum delivery = DeliveryMethodEnum.SERVICE_COMPLETE;
        URI deliverTo = TMP_DIR;
        Identifier source = new Identifier("forest flyover");
        Identifier productType = null;
        test(user, domain, delivery, deliverTo, source, productType, 0);
    }

    /**
     * Test Case 6.
     */
    @Test
    public void testCase_06() {
        System.out.println("Running: testCase_6()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        DeliveryMethodEnum delivery = DeliveryMethodEnum.FILETRANSFER;
        String path = TMP_DIR.getValue().replace("file://", "");
        File targetDir = new File(path, "123");
        URI deliverTo = new URI("file://" + targetDir.getAbsolutePath());
        Identifier source = new Identifier("forest flyover");
        Identifier productType = null;
        test(user, domain, delivery, deliverTo, source, productType, 1);
    }

    /**
     * Test Case 7.
     */
    @Test
    public void testCase_07() {
        System.out.println("Running: testCase_7()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        DeliveryMethodEnum delivery = DeliveryMethodEnum.FILETRANSFER;
        URI deliverTo = TMP_DIR;
        Identifier source = null;
        Identifier productType = null;
        test(user, domain, delivery, deliverTo, source, productType, 1);
    }

    /**
     * Test Case 8.
     */
    @Test
    public void testCase_08() {
        System.out.println("Running: testCase_8()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        DeliveryMethodEnum delivery = DeliveryMethodEnum.FILETRANSFER;
        URI deliverTo = TMP_DIR;
        Identifier source = new Identifier("lake flyover");
        Identifier productType = null;
        test(user, domain, delivery, deliverTo, source, productType, 0);
    }

    /**
     * Test Case 9.
     */
    @Test
    public void testCase_09() {
        System.out.println("Running: testCase_9()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        DeliveryMethodEnum delivery = DeliveryMethodEnum.FILETRANSFER;
        URI deliverTo = TMP_DIR;
        Identifier source = new Identifier("forest flyover");
        Identifier productType = backend.typeImage.getName();
        test(user, domain, delivery, deliverTo, source, productType, 1);
    }

    /**
     * Test Case 10.
     */
    @Test
    public void testCase_10() {
        System.out.println("Running: testCase_10()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        DeliveryMethodEnum delivery = DeliveryMethodEnum.FILETRANSFER;
        URI deliverTo = TMP_DIR;
        Identifier source = new Identifier("forest flyover");
        Identifier productType = new Identifier("typeTMPacketDailyExtract");
        test(user, domain, delivery, deliverTo, source, productType, 0);
    }

    /**
     * Test Case 11.
     */
    @Test
    public void testCase_11() {
        System.out.println("Running: testCase_11()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        DeliveryMethodEnum delivery = DeliveryMethodEnum.FILETRANSFER;
        URI deliverTo = TMP_DIR;
        Identifier source = new Identifier("forest flyover");
        Identifier productType = null;
        // Attribute Filters:
        AttributeList values = new AttributeList();
        values.add(new Union("Earth"));
        AttributeFilterList attributeFilter = new AttributeFilterList();
        attributeFilter.add(new ValueSet(new Identifier("No_Name_Attribute"), true, values));
        testWithAttributeFilter(user, domain, delivery, deliverTo, source, productType, attributeFilter, 0);
    }

    /**
     * Test Case 12.
     */
    @Test
    public void testCase_12() {
        System.out.println("Running: testCase_12()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        DeliveryMethodEnum delivery = DeliveryMethodEnum.FILETRANSFER;
        URI deliverTo = TMP_DIR;
        Identifier source = new Identifier("forest flyover");
        Identifier productType = null;
        // Attribute Filters:
        AttributeList values = new AttributeList();
        values.add(new Union("Earth"));
        AttributeFilterList attributeFilter = new AttributeFilterList();
        attributeFilter.add(new ValueSet(new Identifier("imageSubject"), true, values));
        testWithAttributeFilter(user, domain, delivery, deliverTo, source, productType, attributeFilter, 1);
    }

    /**
     * Test Case 13.
     */
    @Test
    public void testCase_13() {
        System.out.println("Running: testCase_13()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        DeliveryMethodEnum delivery = DeliveryMethodEnum.FILETRANSFER;
        URI deliverTo = TMP_DIR;
        Identifier source = new Identifier("forest flyover");
        Identifier productType = null;
        // Attribute Filters:
        AttributeList values = new AttributeList();
        values.add(new Union("Mars"));
        AttributeFilterList attributeFilter = new AttributeFilterList();
        attributeFilter.add(new ValueSet(new Identifier("imageSubject"), true, values));
        testWithAttributeFilter(user, domain, delivery, deliverTo, source, productType, attributeFilter, 0);
    }

    /**
     * Test Case 14.
     */
    @Test
    public void testCase_14() {
        System.out.println("Running: testCase_14()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        DeliveryMethodEnum delivery = DeliveryMethodEnum.FILETRANSFER;
        URI deliverTo = TMP_DIR;
        Identifier source = new Identifier("forest flyover");
        Identifier productType = null;
        // Attribute Filters:
        AttributeList values = new AttributeList();
        values.add(new Union("Mars"));
        values.add(new Union("Earth"));
        AttributeFilterList attributeFilter = new AttributeFilterList();
        attributeFilter.add(new ValueSet(new Identifier("imageSubject"), true, values));
        testWithAttributeFilter(user, domain, delivery, deliverTo, source, productType, attributeFilter, 1);
    }

    /**
     * Test Case 15.
     */
    @Test
    public void testCase_15() {
        System.out.println("Running: testCase_15()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        DeliveryMethodEnum delivery = DeliveryMethodEnum.FILETRANSFER;
        URI deliverTo = TMP_DIR;
        Identifier source = new Identifier("forest flyover");
        Identifier productType = null;
        // Attribute Filters:
        AttributeList values_0 = new AttributeList(new Union("Earth"));
        AttributeList values_1 = new AttributeList(new Union("visible"));
        AttributeFilterList attributeFilter = new AttributeFilterList();
        attributeFilter.add(new ValueSet(new Identifier("imageSubject"), true, values_0));
        attributeFilter.add(new ValueSet(new Identifier("imageType"), true, values_1));
        testWithAttributeFilter(user, domain, delivery, deliverTo, source, productType, attributeFilter, 1);
    }

    /**
     * Test Case 16.
     */
    @Test
    public void testCase_16() {
        System.out.println("Running: testCase_16()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        DeliveryMethodEnum delivery = DeliveryMethodEnum.FILETRANSFER;
        URI deliverTo = TMP_DIR;
        Identifier source = new Identifier("forest flyover");
        Identifier productType = null;
        // Attribute Filters:
        AttributeList values_0 = new AttributeList(new Union("Earth"));
        AttributeList values_1 = new AttributeList(new Union("infrared"));
        AttributeFilterList attributeFilter = new AttributeFilterList();
        attributeFilter.add(new ValueSet(new Identifier("imageSubject"), true, values_0));
        attributeFilter.add(new ValueSet(new Identifier("imageType"), true, values_1));
        testWithAttributeFilter(user, domain, delivery, deliverTo, source, productType, attributeFilter, 0);
    }

    /**
     * Test Case 17.
     */
    @Test
    public void testCase_17() {
        System.out.println("Running: testCase_17()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        DeliveryMethodEnum delivery = DeliveryMethodEnum.FILETRANSFER;
        URI deliverTo = TMP_DIR;
        Identifier source = new Identifier("forest flyover");
        Identifier productType = null;
        // Attribute Filters:
        AttributeFilterList attributeFilter = new AttributeFilterList();
        attributeFilter.add(new ValueRange(new Identifier("coordinates.lat"), true, new Union(0.0), new Union(10.0)));
        testWithAttributeFilter(user, domain, delivery, deliverTo, source, productType, attributeFilter, 1);
    }

    /**
     * Test Case 18.
     */
    @Test
    public void testCase_18() {
        System.out.println("Running: testCase_18()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        DeliveryMethodEnum delivery = DeliveryMethodEnum.FILETRANSFER;
        URI deliverTo = TMP_DIR;
        Identifier source = new Identifier("forest flyover");
        Identifier productType = null;
        // Attribute Filters:
        AttributeFilterList attributeFilter = new AttributeFilterList();
        attributeFilter.add(new ValueRange(new Identifier("coordinates.lat"), false, new Union(0.0), new Union(10.0)));
        testWithAttributeFilter(user, domain, delivery, deliverTo, source, productType, attributeFilter, 0);
    }

    /**
     * Test Case 19.
     */
    @Test
    public void testCase_19() {
        System.out.println("Running: testCase_19()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        DeliveryMethodEnum delivery = DeliveryMethodEnum.FILETRANSFER;
        URI deliverTo = TMP_DIR;
        Identifier source = new Identifier("forest flyover");
        Identifier productType = null;
        // Attribute Filters:
        AttributeFilterList attributeFilter = new AttributeFilterList();
        attributeFilter.add(new ValueRange(new Identifier("coordinates.lat"), true, new Union(10.0), new Union(20.0)));
        testWithAttributeFilter(user, domain, delivery, deliverTo, source, productType, attributeFilter, 0);
    }

    /**
     * Test Case 20.
     */
    @Test
    public void testCase_20() {
        System.out.println("Running: testCase_20()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        DeliveryMethodEnum delivery = DeliveryMethodEnum.FILETRANSFER;
        URI deliverTo = TMP_DIR;
        Identifier source = new Identifier("forest flyover");
        Identifier productType = null;
        // Attribute Filters:
        AttributeFilterList attributeFilter = new AttributeFilterList();
        attributeFilter.add(new ValueRange(new Identifier("coordinates.lat"), false, new Union(10.0), new Union(20.0)));
        testWithAttributeFilter(user, domain, delivery, deliverTo, source, productType, attributeFilter, 1);
    }

    /**
     * Test Case 21.
     */
    @Test
    public void testCase_21() {
        System.out.println("Running: testCase_21()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        DeliveryMethodEnum delivery = DeliveryMethodEnum.FILETRANSFER;
        URI deliverTo = TMP_DIR;
        Identifier source = new Identifier("forest flyover");
        Identifier productType = null;
        // Attribute Filters:
        AttributeFilterList attributeFilter = new AttributeFilterList();
        attributeFilter.add(new StringPattern(new Identifier("imageSubject"), true, "Ea.*"));
        testWithAttributeFilter(user, domain, delivery, deliverTo, source, productType, attributeFilter, 1);
    }

    /**
     * Test Case 22.
     */
    @Test
    public void testCase_22() {
        System.out.println("Running: testCase_22()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        DeliveryMethodEnum delivery = DeliveryMethodEnum.FILETRANSFER;
        URI deliverTo = TMP_DIR;
        Identifier source = new Identifier("forest flyover");
        Identifier productType = null;
        // Attribute Filters:
        AttributeFilterList attributeFilter = new AttributeFilterList();
        attributeFilter.add(new StringPattern(new Identifier("imageSubject"), true, "Ma.*"));
        testWithAttributeFilter(user, domain, delivery, deliverTo, source, productType, attributeFilter, 0);
    }

    /**
     * Test Case 23.
     */
    @Test
    public void testCase_23() {
        System.out.println("Running: testCase_23()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        DeliveryMethodEnum delivery = DeliveryMethodEnum.FILETRANSFER;
        URI deliverTo = TMP_DIR;
        Identifier productType = null;
        // Attribute Filters:
        AttributeFilterList attributeFilter = new AttributeFilterList();
        attributeFilter.add(new StringPattern(new Identifier("imageSubject"), true, "Ea.*"));
        IdentifierList sources = new IdentifierList();
        sources.add(new Identifier("forest flyover"));
        sources.add(new Identifier("wrong source"));

        testWithAttributeFilterAndMultipleSources(user, domain, delivery, deliverTo,
                sources, productType, attributeFilter, 1);
    }

    /**
     * Test Case 24.
     */
    @Test
    public void testCase_24() {
        System.out.println("Running: testCase_24()");
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = null;
        DeliveryMethodEnum delivery = DeliveryMethodEnum.FILETRANSFER;
        URI deliverTo = TMP_DIR;
        Identifier productType = null;
        // Attribute Filters:
        AttributeFilterList attributeFilter = new AttributeFilterList();
        attributeFilter.add(new StringPattern(new Identifier("imageSubject"), true, "Ea.*"));
        IdentifierList sources = new IdentifierList();
        sources.add(new Identifier("wrong source"));
        sources.add(new Identifier("forest flyover"));

        testWithAttributeFilterAndMultipleSources(user, domain, delivery, deliverTo,
                sources, productType, attributeFilter, 1);
    }

    private void test(Identifier user, IdentifierList domain, DeliveryMethodEnum deliveryMethod,
            URI deliverTo, Identifier source, Identifier productType, int expectedNumberOfNotifications) {
        this.testWithAttributeFilter(user, domain, deliveryMethod, deliverTo,
                source, productType, null, expectedNumberOfNotifications);
    }

    private synchronized void testWithAttributeFilter(Identifier user, IdentifierList domain,
            DeliveryMethodEnum deliveryMethod, URI deliverTo, Identifier source,
            Identifier productType, AttributeFilterList attributeFilter, int expectedNumberOfNotifications) {
        IdentifierList sources = null;

        if (source != null) {
            sources = new IdentifierList();
            sources.add(source);
        }

        this.testWithAttributeFilterAndMultipleSources(user, domain, deliveryMethod,
                deliverTo, sources, productType, attributeFilter, expectedNumberOfNotifications);
    }

    private synchronized void testWithAttributeFilterAndMultipleSources(Identifier user, IdentifierList domain,
            DeliveryMethodEnum deliveryMethod, URI deliverTo, IdentifierList sources,
            Identifier productType, AttributeFilterList attributeFilter, int expectedNumberOfNotifications) {
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
            ProductFilter productFilter = new ProductFilter(productType, domain, sources, attributeFilter);
            StandingOrder orderDetails = new StandingOrder(null, orderUser,
                    productFilter, null, deliveryMethod, deliverTo, null);
            orderID = consumerOM.submitStandingOrder(orderDetails);
            System.out.println("The returned orderID is: " + orderID);
            assertNotNull(orderID);
        } catch (MALInteractionException ex) {
            if (DeliveryMethodEnum.FILETRANSFER.equals(deliveryMethod) && deliverTo == null) {
                Logger.getLogger(UC3_Ex1_Test.class.getName()).log(Level.INFO,
                        "The provider returned an MO Error correctly!");
                return;
            }
            if (!DeliveryMethodEnum.FILETRANSFER.equals(deliveryMethod) && deliverTo != null) {
                Logger.getLogger(UC3_Ex1_Test.class.getName()).log(Level.INFO,
                        "The provider returned an MO Error correctly!");
                return;
            }

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
            ProductMetadataList returnedProductMetadatas = new ProductMetadataList();
            StringList returnedFilenames = new StringList();
            URIList returnedDeliveredTos = new URIList();
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

            Identifier subId = new Identifier("myTestKey" + System.nanoTime());
            Subscription subscription = new Subscription(subId, null, null, filters);
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
                        Boolean success,
                        java.util.Map qosProperties) {
                    System.out.println("Reached: notifyProductDeliveryNotifyReceived()");
                    long duration = System.currentTimeMillis() - startTime;
                    System.out.println("NOTIFY received in: " + duration + " ms");
                    returnedProductMetadatas.add(metadata);
                    returnedFilenames.add(filename);
                    returnedDeliveredTos.add(deliveredTo);
                    notifyReceived.set(true);
                }

                @Override
                public void notifyProductDeliveryNotifyErrorReceived(MALMessageHeader msgHeader,
                        org.ccsds.moims.mo.mal.MOErrorException error,
                        java.util.Map qosProperties) {
                    System.out.println("Reached: notifyProductDeliveryNotifyErrorReceived()");
                    fail(error.toString());
                }
            });

            // Delete the product if it already exist in the specified location
            String productName = "tmData1";
            String specifiedLocation = deliverTo.getValue().replace("file://", "");
            File productDirectory = new File(specifiedLocation);
            productDirectory.mkdirs();

            File productFilepath = new File(specifiedLocation, productName);
            if (productFilepath.exists()) {
                productFilepath.delete();
            }

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

            // Provider becomes aware of a new Product with:
            IdentifierList productDomain = new IdentifierList();
            productDomain.add(new Identifier("nasa"));
            productDomain.add(new Identifier("hubble"));
            ObjectRef<Product> ref = new ObjectRef(productDomain, Product.TYPE_ID.getTypeId(),
                    new Identifier(productName), new UInteger(1));
            Blob productBody = new Blob(new byte[]{0x01, 0x02, 0x03});
            NamedValueList attributes = new NamedValueList();
            attributes.add(new NamedValue(new Identifier("imageSubject"), new Union("Earth")));
            attributes.add(new NamedValue(new Identifier("imageType"), new Union("visible")));
            attributes.add(new NamedValue(new Identifier("coordinates.lat"), new Union(5.0)));
            attributes.add(new NamedValue(new Identifier("coordinates.lon"), new Union(6.0)));

            ProductMetadata metadata = new ProductMetadata(backend.typeImage, ref, Time.now(),
                    new Identifier("forest flyover"), null, null, attributes, "description", null, null);
            backend.addNewProduct(ref, productBody, metadata);

            // ------------------------------------------------------------------------
            // Wait while NOTIFY has not been received and 1 second has not passed yet...
            timeSinceInteractionStarted = System.currentTimeMillis() - startTime;
            while (!notifyReceived.get() && timeSinceInteractionStarted < TIMEOUT) {
                // Recalculate it
                timeSinceInteractionStarted = System.currentTimeMillis() - startTime;
            }

            // Were we expecting to receive at least one product?
            if (!notifyReceived.get() && expectedNumberOfNotifications != 0) {
                Logger.getLogger(UC3_Ex2_Test.class.getName()).log(
                        Level.SEVERE, "The NOTIFY was not received!");
                fail("The NOTIFY was not received!");
            }

            // Did we receive the product(s) notifications?
            assertNotNull(returnedProductMetadatas);
            int size = returnedProductMetadatas.size();
            System.out.println("Number of metadata entries returned: " + size);
            assertEquals(expectedNumberOfNotifications, size);

            // Finish the test if nothing was returned.. (there's nothing else to check)
            if (size != 0) {
                // Check if it matches...
                ProductMetadata returnedProductMetadata = returnedProductMetadatas.get(0);
                String returnedFilename = returnedFilenames.get(0);
                URI returnedDeliveredTo = returnedDeliveredTos.get(0);

                assertNotNull(returnedProductMetadata);
                assertEquals(productName, returnedFilename);
                assertEquals(deliverTo.getValue(), returnedDeliveredTo.getValue());

                if (productType != null) {
                    assertEquals(productType.getValue(), returnedProductMetadata.getProductType().getName().getValue());
                }
            }

            // -----------------------------------------------------------------------------------------------
            // Check that the product was created in the specified location
            if (deliveryMethod.equals(DeliveryMethodEnum.FILETRANSFER) && expectedNumberOfNotifications != 0) {
                if (!productFilepath.exists()) {
                    fail("The product file does not exist in: " + productFilepath.getAbsolutePath());
                }
            }

            // Delete the file...
            if (productFilepath.exists()) {
                productFilepath.delete();
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

            IdentifierList subscriptions = new IdentifierList();
            subscriptions.add(subscription.getSubscriptionId());
            consumerPOD.notifyProductDeliveryDeregister(subscriptions);
            System.out.flush();
        } catch (MALInteractionException ex) {
            Logger.getLogger(UC3_Ex1_Test.class.getName()).log(Level.SEVERE,
                    "Something went wrong with the interaction!", ex);
        } catch (MALException ex) {
            Logger.getLogger(UC3_Ex1_Test.class.getName()).log(Level.SEVERE,
                    "Something went wrong...", ex);
        }
    }
}
