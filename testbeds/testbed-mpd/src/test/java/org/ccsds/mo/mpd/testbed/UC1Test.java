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
import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mpd.ordermanagement.consumer.OrderManagementStub;
import org.ccsds.moims.mo.mpd.ordermanagement.provider.OrderManagementInheritanceSkeleton;
import org.ccsds.moims.mo.mpd.productorderdelivery.consumer.ProductOrderDeliveryStub;
import org.ccsds.moims.mo.mpd.productorderdelivery.provider.ProductOrderDeliveryInheritanceSkeleton;
import org.ccsds.moims.mo.mpd.productretrieval.consumer.ProductRetrievalStub;
import org.ccsds.moims.mo.mpd.productretrieval.provider.ProductRetrievalInheritanceSkeleton;
import org.ccsds.moims.mo.mpd.structures.ParameterFilterList;
import org.ccsds.moims.mo.mpd.structures.ProductFilter;
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

    private static SetUpProvidersAndConsumers setUp = new SetUpProvidersAndConsumers();
    private static OneProductDataset backend = null;
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
        System.out.println("Entered: setUpClass() - The Provider and Consumer will be started here!");
        backend = new OneProductDataset();
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
        System.out.println("Entered: setUp()");
    }

    @After
    public void tearDown() {
        System.out.println("Entered: tearDown()");
    }

    /**
     * Test Case 1 - Match APID.
     */
    @Test
    public void testCase_1() {
        System.out.println("Running: testCase_1()");

        ObjectRef<ProductType> productType = backend.productTypeRef1;  //  productType=typeTMPacket
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("myDomain"));

        ParameterFilterList parameterFilter = new ParameterFilterList();
        AttributeList values = new AttributeList();
        values.add(new Identifier("100"));
        parameterFilter.add(new ValueSet(new Identifier("APID"), true, values));

        ProductFilter productFilter = new ProductFilter(productType, domain, null, parameterFilter);

        TimeWindow creationDate = null;
        TimeWindow timeWindow = null;
        try {
            ProductSummaryList list = consumerPR.listProducts(productFilter, creationDate, timeWindow);
            int size = list.size();
            assertEquals(1, size);
        } catch (MALInteractionException ex) {
            Logger.getLogger(OrderManagementScenario1Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        } catch (MALException ex) {
            Logger.getLogger(OrderManagementScenario1Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.toString());
        }

        // The rest of the code is TBD!!!
    }
}
