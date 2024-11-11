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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.mo.mpd.testbed.backends.OneProductDataset;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mpd.ordermanagement.consumer.OrderManagementStub;
import org.ccsds.moims.mo.mpd.ordermanagement.provider.OrderManagementInheritanceSkeleton;
import org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum;
import org.ccsds.moims.mo.mpd.structures.StandingOrder;
import org.ccsds.moims.mo.mpd.structures.StandingOrderList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class OrderManagementScenario1Test {

    private static SetUpProvidersAndConsumers setUp = new SetUpProvidersAndConsumers();
    private static OrderManagementInheritanceSkeleton providerService;
    private static OrderManagementStub consumerStub;

    public OrderManagementScenario1Test() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        System.out.println("Entered: setUpClass() - The Provider and Consumer will be started here!");

        setUp.setUp(new OneProductDataset());
        providerService = setUp.getProviderService();
        consumerStub = setUp.getOrderManagementService();
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("Entered: tearDownClass()");
        System.out.println("The Provider and Consumer need to be closed here!");

        try {
            // Initialize the Order Management service
            setUp.tearDown();
        } catch (IOException ex) {
            Logger.getLogger(OrderManagementScenario1Test.class.getName()).log(
                    Level.SEVERE, "The tearDown() operation failed!", ex);
        }
    }

    @Before
    public void setUp() {
        System.out.println("Entered: setUp()");
    }

    @After
    public void tearDown() {
        System.out.println("Entered: tearDown()");
    }

    /**
     * Test Case 1 - Lists the available orders. There should be zero standing
     * orders.
     */
    @Test
    public void testCase_1() {
        System.out.println("Running: testCase_1()");

        Identifier user = new Identifier("john.doe");
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("*"));

        try {
            StandingOrderList standingOrders = consumerStub.listStandingOrders(user, domain);
            int size = standingOrders.size();
            assertEquals(0, size);
        } catch (MALInteractionException ex) {
            Logger.getLogger(OrderManagementScenario1Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        } catch (MALException ex) {
            Logger.getLogger(OrderManagementScenario1Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        }
    }

    /**
     * Test Case 2 - Submits a standing order, then requests the list of
     * standing orders. Checks if there is one standing order, and if the data
     * matches what was submitted.
     */
    @Test
    public void testCase_2() {
        try {
            System.out.println("Running: testCase_2");

            // Input Data
            Identifier user = new Identifier("User");
            DeliveryMethodEnum dMethod = DeliveryMethodEnum.SERVICE;
            String comments = "A comment";
            StandingOrder orderDetails = new StandingOrder(user, dMethod, comments);

            // Submit a Standing Order
            Identifier id = consumerStub.submitStandingOrder(orderDetails);
            assertNotEquals(null, id);

            Logger.getLogger(OrderManagementScenario1Test.class.getName()).log(Level.INFO,
                    "The returned Identifier is: {0}", id.getValue());

            // Request the list of standing orders
            IdentifierList domain = new IdentifierList();
            domain.add(new Identifier("*"));
            StandingOrderList standingOrders = consumerStub.listStandingOrders(new Identifier("*"), domain);
            Logger.getLogger(OrderManagementScenario1Test.class.getName()).log(Level.INFO,
                    "The returned list of standing orders is: {0}", standingOrders.toString());

            int size = standingOrders.size();
            assertEquals(1, size);

            StandingOrder standingOrder = standingOrders.get(0);
            assertEquals(user, standingOrder.getUser());
            assertEquals(comments, standingOrder.getComments());
            assertEquals(dMethod, standingOrder.getDeliveryMethod());
        } catch (MALInteractionException ex) {
            Logger.getLogger(OrderManagementScenario1Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        } catch (MALException ex) {
            Logger.getLogger(OrderManagementScenario1Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        }
    }

}
