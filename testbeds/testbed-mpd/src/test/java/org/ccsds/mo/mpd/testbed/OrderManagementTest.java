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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.mo.mpd.testbed.backends.OneProductDataset;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mpd.MPDHelper;
import org.ccsds.moims.mo.mpd.structures.AttributeFilter;
import org.ccsds.moims.mo.mpd.structures.AttributeFilterList;
import org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum;
import org.ccsds.moims.mo.mpd.structures.ProductFilter;
import org.ccsds.moims.mo.mpd.structures.StandingOrder;
import org.ccsds.moims.mo.mpd.structures.StandingOrderList;
import org.ccsds.moims.mo.mpd.structures.TimeWindow;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class OrderManagementTest extends MPDTest {

    @BeforeClass
    public static void setUpClass() throws IOException {
        System.out.println(TEST_SET_UP_CLASS_1);
        System.out.println(TEST_SET_UP_CLASS_2);
        setUp.setUp(new OneProductDataset(), true, true, false);
    }

    /**
     * Test Case 1 - Lists the available orders. There should be zero standing
     * orders.
     */
    @Test
    public void testCase_01() {
        System.out.println("Running: testCase_01()");

        Identifier user = new Identifier("john.doe");
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("*"));

        try {
            StandingOrderList standingOrders = consumerOM.listStandingOrders(user, domain);
            int size = standingOrders.size();
            assertEquals(0, size);
        } catch (MALInteractionException ex) {
            Logger.getLogger(OrderManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        } catch (MALException ex) {
            Logger.getLogger(OrderManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        }
    }

    /**
     * Test Case 2.
     */
    @Test
    public void testCase_02() {
        System.out.println("Running: testCase_02()");
        // Input Data
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("*"));
        DeliveryMethodEnum dMethod = DeliveryMethodEnum.SERVICE_COMPLETE;

        try {
            test(user, domain, dMethod, null, null, 1);
        } catch (MALInteractionException ex) {
            Logger.getLogger(OrderManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        }
    }

    /**
     * Test Case 3.
     */
    @Test
    public void testCase_03() {
        System.out.println("Running: testCase_03()");
        // Input Data
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("esa"));
        domain.add(new Identifier("juice"));
        DeliveryMethodEnum dMethod = DeliveryMethodEnum.SERVICE_COMPLETE;

        try {
            test(user, domain, dMethod, null, null, 0);
        } catch (MALInteractionException ex) {
            Logger.getLogger(OrderManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        }
    }

    /**
     * Test Case 4.
     */
    @Test
    public void testCase_04() {
        System.out.println("Running: testCase_04()");
        // Input Data
        Identifier user = new Identifier("john.doe");
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("*"));
        DeliveryMethodEnum dMethod = DeliveryMethodEnum.SERVICE_COMPLETE;
        AttributeFilter attributeFilter = null;
        Time now = Time.now();
        TimeWindow validityPeriod = new TimeWindow(now, new Time(now.getValue() - 100));
        try {
            test(user, domain, dMethod, attributeFilter, validityPeriod, 0);
            fail("The operation was expected to throw an Invalid exception!");
        } catch (MALInteractionException ex) {
            MOErrorException moError = ex.getStandardError();
            long errorNumber = moError.getErrorNumber().getValue();
            if (errorNumber == MPDHelper.INVALID_ERROR_NUMBER.getValue()) {
                Logger.getLogger(OrderManagementTest.class.getName()).log(Level.INFO, "Error returned successfully!");
            } else {
                Logger.getLogger(OrderManagementTest.class.getName()).log(Level.INFO, "Failed!", ex);
                fail("The operation was expected to throw an Invalid exception!");
            }
        }
    }

    /**
     * Test Case 5.
     */
    @Test
    public void testCase_05() {
        System.out.println("Running: testCase_05()");
        // Input Data
        DeliveryMethodEnum dMethod = DeliveryMethodEnum.FILETRANSFER;
        URI deliverTo = null;

        try {
            testInvalidErrorSubmitStandingOrder(dMethod, deliverTo);
            fail("The operation was expected to throw an Invalid exception!");
        } catch (MALInteractionException ex) {
            MOErrorException moError = ex.getStandardError();
            long errorNumber = moError.getErrorNumber().getValue();
            if (errorNumber == MPDHelper.INVALID_ERROR_NUMBER.getValue()) {
                Logger.getLogger(OrderManagementTest.class.getName()).log(Level.INFO, "Error returned successfully!");
            } else {
                Logger.getLogger(OrderManagementTest.class.getName()).log(Level.INFO, "Failed!", ex);
                fail("The operation was expected to throw an Invalid exception!");
            }
        }
    }

    /**
     * Test Case 6.
     */
    @Test
    public void testCase_06() {
        System.out.println("Running: testCase_06()");
        // Input Data
        DeliveryMethodEnum dMethod = DeliveryMethodEnum.SERVICE_COMPLETE;
        URI deliverTo = new URI("sftp://123.4.5.6:1234/folder/");

        try {
            testInvalidErrorSubmitStandingOrder(dMethod, deliverTo);
            fail("The operation was expected to throw an Invalid exception!");
        } catch (MALInteractionException ex) {
            MOErrorException moError = ex.getStandardError();
            long errorNumber = moError.getErrorNumber().getValue();
            if (errorNumber == MPDHelper.INVALID_ERROR_NUMBER.getValue()) {
                Logger.getLogger(OrderManagementTest.class.getName()).log(Level.INFO, "Error returned successfully!");
            } else {
                Logger.getLogger(OrderManagementTest.class.getName()).log(Level.INFO, "Failed!", ex);
                fail("The operation was expected to throw an Invalid exception!");
            }
        }
    }

    private void testInvalidErrorSubmitStandingOrder(DeliveryMethodEnum dMethod, URI deliverTo) throws MALInteractionException {
        try {
            Identifier user = new Identifier("john.doe");
            StandingOrder orderDetails = new StandingOrder(null, user,
                    null, null, dMethod, deliverTo, null);

            // Submit a Standing Order
            Long id = consumerOM.submitStandingOrder(orderDetails);
            assertNotEquals(null, id);

            Logger.getLogger(OrderManagementTest.class.getName()).log(Level.SEVERE,
                    "The submitStandingOrder must not return a value! id: {0}", id);

            consumerOM.cancelStandingOrder(id);
        } catch (MALException ex) {
            Logger.getLogger(OrderManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        }
    }

    private void test(Identifier user, IdentifierList domain, DeliveryMethodEnum dMethod,
            AttributeFilter attributeFilter, TimeWindow validityPeriod, int expectedNumberOfResults) throws MALInteractionException {
        try {
            IdentifierList domainFilter = new IdentifierList();
            domainFilter.add(new Identifier("nasa"));
            domainFilter.add(new Identifier("hubble"));
            AttributeFilterList attributeFilterList = new AttributeFilterList();
            if (attributeFilter != null) {
                attributeFilterList.add(attributeFilter);
            }
            ProductFilter productFilter = new ProductFilter(null, domainFilter, null, attributeFilterList);
            StandingOrder orderDetails = new StandingOrder(null, user,
                    productFilter, validityPeriod, dMethod, null, null);

            // Submit a Standing Order
            Long id = consumerOM.submitStandingOrder(orderDetails);
            assertNotEquals(null, id);

            Logger.getLogger(OrderManagementTest.class.getName()).log(
                    Level.INFO, "The returned Identifier is: {0}", id);

            // Request the list of standing orders
            StandingOrderList standingOrders = consumerOM.listStandingOrders(new Identifier("*"), domain);
            Logger.getLogger(OrderManagementTest.class.getName()).log(Level.INFO,
                    "The returned list of standing orders is: {0}", standingOrders.toString());

            int size = standingOrders.size();
            assertEquals(expectedNumberOfResults, size);

            if (size != 0) {
                StandingOrder standingOrder = standingOrders.get(0);
                assertEquals(user, standingOrder.getUser());
                assertEquals(dMethod, standingOrder.getDeliveryMethod());
            }

            consumerOM.cancelStandingOrder(id);
        } catch (MALException ex) {
            Logger.getLogger(OrderManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        }
    }
}
