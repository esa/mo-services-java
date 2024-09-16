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

import esa.mo.services.mpd.consumer.OrderManagementConsumerServiceImpl;
import esa.mo.services.mpd.consumer.ProductOrderDeliveryConsumerServiceImpl;
import esa.mo.services.mpd.consumer.ProductRetrievalConsumerServiceImpl;
import esa.mo.services.mpd.provider.OrderManagementProviderServiceImpl;
import esa.mo.services.mpd.provider.ProductOrderDeliveryProviderServiceImpl;
import esa.mo.services.mpd.provider.ProductRetrievalProviderServiceImpl;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.helpertools.connections.SingleConnectionDetails;
import org.ccsds.moims.mo.mal.helpertools.misc.ConsumerServiceImpl;
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
 */
public class ESAMPDServicesFactory extends MPDServicesFactory {

    private OrderManagementProviderServiceImpl orderManagementService = null;
    private ProductOrderDeliveryProviderServiceImpl productOrderDeliveryService = null;
    private ProductRetrievalProviderServiceImpl productRetrievalService = null;

    public ESAMPDServicesFactory() {
    }

    @Override
    public OrderManagementInheritanceSkeleton createProviderOrderManagement() throws MALException {
        if (productOrderDeliveryService == null) {
            throw new MALException("The productOrderDeliveryService needs to be instantiated before!");
        }

        orderManagementService = new OrderManagementProviderServiceImpl();
        orderManagementService.init();
        return orderManagementService;
    }

    @Override
    public ProductOrderDeliveryInheritanceSkeleton createProviderProductOrderDelivery(ProductRetrievalBackend backend) throws MALException {
        productOrderDeliveryService = new ProductOrderDeliveryProviderServiceImpl();
        productOrderDeliveryService.init();
        return productOrderDeliveryService;
    }

    @Override
    public ProductRetrievalInheritanceSkeleton createProviderProductRetrieval(ProductRetrievalBackend backend) throws MALException {
        productRetrievalService = new ProductRetrievalProviderServiceImpl();
        productRetrievalService.init();
        return productRetrievalService;
    }

    @Override
    public OrderManagementStub createConsumerStubOrderManagement(SingleConnectionDetails details) throws MALException {
        if (details == null) {
            throw new MALException("The provider details are null!");
        }

        ConsumerServiceImpl consumerService = new OrderManagementConsumerServiceImpl(details);
        return (OrderManagementStub) consumerService.getStub();
    }

    @Override
    public ProductOrderDeliveryStub createConsumerStubProductOrderDelivery(SingleConnectionDetails details) throws MALException {
        if (details == null) {
            throw new MALException("The provider details are null!");
        }

        ConsumerServiceImpl consumerService = new ProductOrderDeliveryConsumerServiceImpl(details);
        return (ProductOrderDeliveryStub) consumerService.getStub();
    }

    @Override
    public ProductRetrievalStub createConsumerStubProductRetrieval(SingleConnectionDetails details) throws MALException {
        if (details == null) {
            throw new MALException("The provider details are null!");
        }

        ConsumerServiceImpl consumerService = new ProductRetrievalConsumerServiceImpl(details);
        return (ProductRetrievalStub) consumerService.getStub();
    }
}
