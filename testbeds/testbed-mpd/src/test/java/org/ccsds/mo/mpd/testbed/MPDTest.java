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
import org.ccsds.moims.mo.mpd.ordermanagement.consumer.OrderManagementStub;
import org.ccsds.moims.mo.mpd.ordermanagement.provider.OrderManagementInheritanceSkeleton;
import org.ccsds.moims.mo.mpd.productorderdelivery.consumer.ProductOrderDeliveryStub;
import org.ccsds.moims.mo.mpd.productorderdelivery.provider.ProductOrderDeliveryInheritanceSkeleton;
import org.ccsds.moims.mo.mpd.productretrieval.consumer.ProductRetrievalStub;
import org.ccsds.moims.mo.mpd.productretrieval.provider.ProductRetrievalInheritanceSkeleton;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;

/**
 * The abstract class for all MPS Tests.
 *
 * @author Cesar.Coelho
 */
public abstract class MPDTest {

    protected static final int TIMEOUT = 1000; // In milliseconds
    protected static final String TEST_START = "-------- Running New Test --------";
    protected static final String TEST_END = "Test is completed!";
    protected static final String TEST_SET_UP_CLASS_1 = "-----------------------------------------------------------------------";
    protected static final String TEST_SET_UP_CLASS_2 = "Entered: setUpClass() - The Provider and Consumer will be started here!";
    protected static final SetUpProvidersAndConsumers setUp = new SetUpProvidersAndConsumers();

    protected static OrderManagementInheritanceSkeleton providerOM = null;
    protected static OrderManagementStub consumerOM = null;
    protected static ProductOrderDeliveryInheritanceSkeleton providerPOD = null;
    protected static ProductOrderDeliveryStub consumerPOD = null;
    protected static ProductRetrievalInheritanceSkeleton providerPR = null;
    protected static ProductRetrievalStub consumerPR = null;

    @AfterClass
    public static void tearDownClass() {
        System.out.println("Entered: tearDownClass()");
        System.out.println("The Provider and Consumer are being closed!");

        try {
            setUp.tearDown(); // Close all the services
        } catch (IOException ex) {
            Logger.getLogger(MPDTest.class.getName()).log(Level.SEVERE,
                    "The tearDown() operation failed!", ex);
        }
    }

    @Before
    public void setUp() {
        System.out.println(TEST_START); // Right before running a test
        providerOM = setUp.getOrderManagementProvider();
        consumerOM = setUp.getOrderManagementConsumer();
        providerPOD = setUp.getProductOrderDeliveryProvider();
        consumerPOD = setUp.getProductOrderConsumer();
        providerPR = setUp.getProductRetrievalProvider();
        consumerPR = setUp.getProductRetrievalConsumer();
    }

    @After
    public void tearDown() {
        System.out.println(TEST_END);
    }

}
