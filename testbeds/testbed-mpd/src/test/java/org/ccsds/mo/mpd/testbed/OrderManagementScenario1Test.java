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
import org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum;
import org.ccsds.moims.mo.mpd.structures.StandingOrder;
import org.ccsds.moims.mo.mpd.structures.StandingOrderList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class OrderManagementScenario1Test extends MPSTest {

    public OrderManagementScenario1Test() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("Entered: setUpClass() - The Provider and Consumer will be started here!");

        setUp.setUp(new OneProductDataset(), true, true, false);
        providerOM = setUp.getOrderManagementProvider();
        consumerOM = setUp.getOrderManagementConsumer();
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
            StandingOrderList standingOrders = consumerOM.listStandingOrders(user, domain);
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
            StandingOrder orderDetails = new StandingOrder(user, dMethod);

            // Submit a Standing Order
            Long id = consumerOM.submitStandingOrder(orderDetails);
            assertNotEquals(null, id);

            Logger.getLogger(OrderManagementScenario1Test.class.getName()).log(Level.INFO,
                    "The returned Identifier is: {0}", id);

            // Request the list of standing orders
            IdentifierList domain = new IdentifierList();
            domain.add(new Identifier("*"));
            StandingOrderList standingOrders = consumerOM.listStandingOrders(new Identifier("*"), domain);
            Logger.getLogger(OrderManagementScenario1Test.class.getName()).log(Level.INFO,
                    "The returned list of standing orders is: {0}", standingOrders.toString());

            int size = standingOrders.size();
            assertEquals(1, size);

            StandingOrder standingOrder = standingOrders.get(0);
            assertEquals(user, standingOrder.getUser());
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
