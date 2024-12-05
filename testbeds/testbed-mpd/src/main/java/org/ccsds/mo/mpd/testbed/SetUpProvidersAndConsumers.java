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
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.helpertools.connections.ConnectionProvider;
import org.ccsds.moims.mo.mal.helpertools.connections.SingleConnectionDetails;
import org.ccsds.moims.mo.mal.helpertools.helpers.HelperMisc;
import org.ccsds.moims.mo.mpd.MPDServicesFactory;
import org.ccsds.moims.mo.mpd.backends.ProductRetrievalBackend;
import org.ccsds.moims.mo.mpd.ordermanagement.consumer.OrderManagementStub;
import org.ccsds.moims.mo.mpd.ordermanagement.provider.OrderManagementInheritanceSkeleton;
import org.ccsds.moims.mo.mpd.productorderdelivery.consumer.ProductOrderDeliveryStub;
import org.ccsds.moims.mo.mpd.productorderdelivery.provider.ProductOrderDeliveryInheritanceSkeleton;
import org.ccsds.moims.mo.mpd.productretrieval.consumer.ProductRetrievalStub;
import org.ccsds.moims.mo.mpd.productretrieval.provider.ProductRetrievalInheritanceSkeleton;

/**
 *
 * @author Cesar.Coelho
 */
public class SetUpProvidersAndConsumers {

    private static ProductOrderDeliveryInheritanceSkeleton productOrderDeliveryProviderService = null;
    private static ProductOrderDeliveryStub productOrderDeliveryConsumerStub = null;

    private static OrderManagementInheritanceSkeleton orderManagementProviderService = null;
    private static OrderManagementStub orderManagementConsumerStub = null;

    private static ProductRetrievalInheritanceSkeleton productRetrievalProviderService = null;
    private static ProductRetrievalStub productRetrievalConsumerStub = null;

    public void setUp(ProductRetrievalBackend backend, boolean startProductOrderDelivery,
            boolean startOrderManagement, boolean startProductRetrieval) throws IOException {
        HelperMisc.loadPropertiesFile();
        ConnectionProvider.resetURILinksFile(); // Resets the providerURIs.properties file

        try {
            // Dynamic load here: It can be either for ESA's or NASA's implementation
            // And also the consumer and provider need to be selectable!
            // This can be achieved with the Factory pattern

            String factoryClassForProvider = System.getProperty("testbed.provider");
            String factoryClassForConsumer = System.getProperty("testbed.consumer");
            System.out.println("  >> factoryClassForProvider: " + factoryClassForProvider);
            System.out.println("  >> factoryClassForConsumer: " + factoryClassForConsumer);

            if ("null".equals(factoryClassForProvider) || "".equals(factoryClassForProvider)) {
                throw new IOException("The classname is empty or null for the provider side! "
                        + "Please select the correct Maven profile before running the test!");
            }

            if ("null".equals(factoryClassForConsumer) || "".equals(factoryClassForConsumer)) {
                throw new IOException("The classname is empty or null for the consumer side! "
                        + "Please select the correct Maven profile before running the test!");
            }

            // Provider Factory:
            Class factoryClassProvider = Class.forName(factoryClassForProvider);
            MPDServicesFactory factoryProvider = (MPDServicesFactory) factoryClassProvider.newInstance();
            // Consumer Factory:
            Class factoryClassConsumer = Class.forName(factoryClassForConsumer);
            MPDServicesFactory factoryConsumer = (MPDServicesFactory) factoryClassConsumer.newInstance();

            if (startProductOrderDelivery) {
                productOrderDeliveryProviderService = factoryProvider.createProviderProductOrderDelivery(backend);

                if (productOrderDeliveryProviderService == null) {
                    throw new MALException("The Product Order Delivery provider was not created!");
                }

                SingleConnectionDetails details = productOrderDeliveryProviderService.getConnection().getConnectionDetails();
                productOrderDeliveryConsumerStub = factoryConsumer.createConsumerStubProductOrderDelivery(details);
            }

            if (startOrderManagement) {
                orderManagementProviderService = factoryProvider.createProviderOrderManagement();

                if (orderManagementProviderService == null) {
                    throw new MALException("The Order Management provider was not created!");
                }

                SingleConnectionDetails details = orderManagementProviderService.getConnection().getConnectionDetails();
                orderManagementConsumerStub = factoryConsumer.createConsumerStubOrderManagement(details);
            }

            if (startProductRetrieval) {
                productRetrievalProviderService = factoryProvider.createProviderProductRetrieval(backend);

                if (productRetrievalProviderService == null) {
                    throw new MALException("The Product Retrieval provider was not created!");
                }

                SingleConnectionDetails details = productRetrievalProviderService.getConnection().getConnectionDetails();
                productRetrievalConsumerStub = factoryConsumer.createConsumerStubProductRetrieval(details);
            }
        } catch (InstantiationException ex) {
            Logger.getLogger(SetUpProvidersAndConsumers.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SetUpProvidersAndConsumers.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SetUpProvidersAndConsumers.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MALException ex) {
            Logger.getLogger(SetUpProvidersAndConsumers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ProductOrderDeliveryInheritanceSkeleton getProductOrderDeliveryProvider() {
        return productOrderDeliveryProviderService;
    }

    public ProductOrderDeliveryStub getProductOrderConsumer() {
        return productOrderDeliveryConsumerStub;
    }

    public OrderManagementInheritanceSkeleton getOrderManagementProvider() {
        return orderManagementProviderService;
    }

    public OrderManagementStub getOrderManagementConsumer() {
        return orderManagementConsumerStub;
    }

    public ProductRetrievalInheritanceSkeleton getProductRetrievalProvider() {
        return productRetrievalProviderService;
    }

    public ProductRetrievalStub getProductRetrievalConsumer() {
        return productRetrievalConsumerStub;
    }

    public void tearDown() throws IOException {
        if (orderManagementProviderService != null) {
            orderManagementProviderService.getConnection().closeAll();
        }
    }
}
