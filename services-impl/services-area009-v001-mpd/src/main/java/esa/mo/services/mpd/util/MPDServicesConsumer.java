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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.helpertools.connections.ConnectionConsumer;
import org.ccsds.moims.mo.mal.helpertools.connections.SingleConnectionDetails;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mpd.ordermanagement.OrderManagementServiceInfo;
import org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryServiceInfo;
import org.ccsds.moims.mo.mpd.productretrieval.ProductRetrievalServiceInfo;

/**
 * The MPD services consumer class that contains all the consumer services.
 */
public class MPDServicesConsumer {

    private OrderManagementConsumerServiceImpl orderManagementService;
    private ProductOrderDeliveryConsumerServiceImpl productOrderDeliveryService;
    private ProductRetrievalConsumerServiceImpl productRetrievalService;

    /**
     * Initializes the MPD consumers.
     *
     * @param connectionConsumer The connection details to connect to.
     */
    public void init(ConnectionConsumer connectionConsumer) {
        init(connectionConsumer, null, null);
    }

    /**
     * Initializes the MPD consumers.
     *
     * @param connectionConsumer The connection details to connect to.
     * @param authenticationId The authenticationId token.
     * @param localNamePrefix The local name prefix.
     */
    public void init(ConnectionConsumer connectionConsumer, Blob authenticationId, String localNamePrefix) {
        SingleConnectionDetails details;

        try {
            // Initialize the Order Management service
            details = connectionConsumer.getServicesDetails().get(OrderManagementServiceInfo.ORDERMANAGEMENT_SERVICE_NAME);
            if (details != null) {
                orderManagementService = new OrderManagementConsumerServiceImpl(details, authenticationId, localNamePrefix);
            }

            // Initialize the Product Order Delivery service
            details = connectionConsumer.getServicesDetails().get(ProductOrderDeliveryServiceInfo.PRODUCTORDERDELIVERY_SERVICE_NAME);
            if (details != null) {
                productOrderDeliveryService = new ProductOrderDeliveryConsumerServiceImpl(details, authenticationId, localNamePrefix);
            }

            // Initialize the Product Retrieval service
            details = connectionConsumer.getServicesDetails().get(ProductRetrievalServiceInfo.PRODUCTRETRIEVAL_SERVICE_NAME);
            if (details != null) {
                productRetrievalService = new ProductRetrievalConsumerServiceImpl(details, authenticationId, localNamePrefix);
            }
        } catch (MALException ex) {
            Logger.getLogger(MPDServicesConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Returns the Order Management service consumer.
     *
     * @return The Order Management service consumer.
     */
    public OrderManagementConsumerServiceImpl getOrderManagementService() {
        return this.orderManagementService;
    }

    /**
     * Returns the Product Order Delivery service consumer.
     *
     * @return The Product Order Delivery service consumer.
     */
    public ProductOrderDeliveryConsumerServiceImpl getProductOrderDeliveryService() {
        return this.productOrderDeliveryService;
    }

    /**
     * Returns the Product Retrieval service consumer.
     *
     * @return The Product Retrieval service consumer.
     */
    public ProductRetrievalConsumerServiceImpl getProductRetrievalService() {
        return this.productRetrievalService;
    }

    /**
     * Closes the service consumer connections
     */
    public void closeConnections() {
        if (this.orderManagementService != null) {
            this.orderManagementService.closeConnection();
        }
        if (this.productOrderDeliveryService != null) {
            this.productOrderDeliveryService.closeConnection();
        }
        if (this.productRetrievalService != null) {
            this.productRetrievalService.closeConnection();
        }
    }

    /**
     * Sets the authenticationId.
     *
     * @param authenticationId The authenticationId.
     */
    public void setAuthenticationId(Blob authenticationId) {
        if (this.orderManagementService != null) {
            this.orderManagementService.setAuthenticationId(authenticationId);
        }
        if (this.productOrderDeliveryService != null) {
            this.productOrderDeliveryService.setAuthenticationId(authenticationId);
        }
        if (this.productRetrievalService != null) {
            this.productRetrievalService.setAuthenticationId(authenticationId);
        }
    }
}
