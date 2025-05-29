/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MPD services
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
package esa.mo.services.mpd.util;

import esa.mo.services.mpd.provider.OrderManagementProviderServiceImpl;
import esa.mo.services.mpd.provider.ProductOrderDeliveryProviderServiceImpl;
import esa.mo.services.mpd.provider.ProductRetrievalProviderServiceImpl;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mpd.backends.ProductRetrievalBackend;

/**
 * The MPD services consumer class that contains all the provider services.
 */
public class MPDServicesProvider {

    private final ProductOrderDeliveryProviderServiceImpl productOrderDeliveryService = new ProductOrderDeliveryProviderServiceImpl();
    private final OrderManagementProviderServiceImpl orderManagementService = new OrderManagementProviderServiceImpl();
    private final ProductRetrievalProviderServiceImpl productRetrievalService = new ProductRetrievalProviderServiceImpl();

    /**
     * Initializes the service providers.
     *
     * @param backendProductRetrievalProvider The backend to retrieve products.
     * @throws MALException if the services could not be initialized.
     */
    public void init(ProductRetrievalBackend backendProductRetrievalProvider) throws MALException {
        productOrderDeliveryService.init(backendProductRetrievalProvider);
        orderManagementService.init(productOrderDeliveryService);
        productRetrievalService.init(backendProductRetrievalProvider);
    }

    /**
     * Returns the Product Order Delivery service provider.
     *
     * @return The Product Order Delivery service provider.
     */
    public ProductOrderDeliveryProviderServiceImpl getProductOrderDeliveryService() {
        return this.productOrderDeliveryService;
    }

    /**
     * Returns the Order Management service provider.
     *
     * @return The Order Management service provider.
     */
    public OrderManagementProviderServiceImpl getOrderManagementService() {
        return this.orderManagementService;
    }

    /**
     * Returns the Product Retrieval service provider.
     *
     * @return The Product Retrieval service provider.
     */
    public ProductRetrievalProviderServiceImpl getProductRetrievalService() {
        return this.productRetrievalService;
    }
}
