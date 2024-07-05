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

import esa.mo.services.mpd.util.OrderManagementServicesFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.helpertools.connections.ConnectionProvider;
import org.ccsds.moims.mo.mal.helpertools.connections.SingleConnectionDetails;
import org.ccsds.moims.mo.mal.helpertools.helpers.HelperMisc;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mpd.ordermanagement.consumer.OrderManagementStub;
import org.ccsds.moims.mo.mpd.ordermanagement.provider.OrderManagementInheritanceSkeleton;
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

    private static OrderManagementInheritanceSkeleton providerService;
    private static OrderManagementStub consumerStub;

    public OrderManagementScenario1Test() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        System.out.println("Entered: setUpClass()");
        System.out.println("The Provider and Consumer will be started here!");

        HelperMisc.loadPropertiesFile();
        ConnectionProvider.resetURILinksFile(); // Resets the providerURIs.properties file

        try {
            // Dynamic load here: It can be either for ESA's or NASA's implementation
            // And also the consumer and provider need to be selectable!
            // This can be achieved with the Factory pattern

            String factoryClassForProvider = System.getProperty("testbed.provider");
            String factoryClassForConsumer = System.getProperty("testbed.consumer");
            System.out.println("factoryClassForProvider: " + factoryClassForProvider);
            System.out.println("factoryClassForConsumer: " + factoryClassForConsumer);

            if ("null".equals(factoryClassForProvider) || "".equals(factoryClassForProvider)) {
                throw new IOException("The classname is empty or null for the provider side!");
            }

            if ("null".equals(factoryClassForConsumer) || "".equals(factoryClassForConsumer)) {
                throw new IOException("The classname is empty or null for the consumer side!");
            }

            //factoryClassForProvider = "esa.mo.services.mpd.util.ESAOrderManagementServicesFactory";
            Class factoryClass = Class.forName(factoryClassForProvider);
            OrderManagementServicesFactory factoryProvider = (OrderManagementServicesFactory) factoryClass.newInstance();
            providerService = factoryProvider.createProvider();

            if (providerService == null) {
                throw new MALException("The provider was not created!");
            }

            SingleConnectionDetails details = providerService.getConnection().getConnectionDetails();

            Class factoryClassConsumer = Class.forName(factoryClassForConsumer);
            OrderManagementServicesFactory factoryConsumer = (OrderManagementServicesFactory) factoryClassConsumer.newInstance();
            consumerStub = factoryConsumer.createConsumerStub(details);

        } catch (InstantiationException ex) {
            Logger.getLogger(OrderManagementScenario1Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(OrderManagementScenario1Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OrderManagementScenario1Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MALException ex) {
            Logger.getLogger(OrderManagementScenario1Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MALInteractionException ex) {
            Logger.getLogger(OrderManagementScenario1Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("Entered: tearDownClass()");
        System.out.println("The Provider and Consumer need to be closed here!");

        // Initialize the Order Management service
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
     * Test Case 1
     */
    @Test
    public void testCase_1() {
        System.out.println("testCase_1");

        Identifier user = new Identifier("john.doe");
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("*"));

        StandingOrderList expectedStandingOrders = null;
        try {
            StandingOrderList standingOrders = consumerStub.listStandingOrders(user, domain);
            assertEquals(expectedStandingOrders, standingOrders);
        } catch (MALInteractionException ex) {
            Logger.getLogger(OrderManagementScenario1Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        } catch (MALException ex) {
            Logger.getLogger(OrderManagementScenario1Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        }
        /*
        IdentifierList consumerDomain = null;
        IdentifierList providerDomain = null;
        boolean expResult = true;
        boolean result = BrokerMatcher.domainMatchesWildcardDomain(consumerDomain, providerDomain);
        assertEquals(expResult, result);
         */
    }

    /**
     * Test Case 2
     */
    @Test
    public void testCase_2() {
        System.out.println("testCase_2");
        /*
        IdentifierList consumerDomain = null;
        IdentifierList providerDomain = new IdentifierList();
        providerDomain.add(new Identifier("spacecraftA"));
        boolean expResult = false;
        boolean result = BrokerMatcher.domainMatchesWildcardDomain(consumerDomain, providerDomain);
        assertEquals(expResult, result);
         */
    }

}
