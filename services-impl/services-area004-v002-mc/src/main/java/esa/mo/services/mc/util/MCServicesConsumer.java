/* ----------------------------------------------------------------------------
 * Copyright (C) 2025      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO services
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
package esa.mo.services.mc.util;

import esa.mo.services.mc.consumer.ParameterConsumerServiceImpl;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.helpertools.connections.ConnectionConsumer;
import org.ccsds.moims.mo.mal.helpertools.connections.SingleConnectionDetails;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mc.parameter.ParameterServiceInfo;

/**
 * The MC services consumer class that contains all the consumer services.
 */
public class MCServicesConsumer {

    private ParameterConsumerServiceImpl parameterService;

    /**
     * Initializes the consumers.
     *
     * @param connectionConsumer The connection details to connect to.
     */
    public void init(ConnectionConsumer connectionConsumer) {
        init(connectionConsumer, null, null);
    }

    /**
     * Initializes the consumers.
     *
     * @param connectionConsumer The connection details to connect to.
     * @param authenticationId The authenticationId token.
     * @param localNamePrefix The local name prefix.
     */
    public void init(ConnectionConsumer connectionConsumer, Blob authenticationId, String localNamePrefix) {
        SingleConnectionDetails details;

        try {
            // Initialize the Order Management service
            details = connectionConsumer.getServicesDetails().get(ParameterServiceInfo.PARAMETER_SERVICE_NAME);
            if (details != null) {
                parameterService = new ParameterConsumerServiceImpl(details, authenticationId, localNamePrefix);
            }

            /*
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
             */
        } catch (MALException ex) {
            Logger.getLogger(MCServicesConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Returns the Parameter service consumer.
     *
     * @return The Parameter service consumer.
     */
    public ParameterConsumerServiceImpl getParameterService() {
        return this.parameterService;
    }

    /**
     * Closes the service consumer connections
     */
    public void closeConnections() {
        if (this.parameterService != null) {
            this.parameterService.closeConnection();
        }
        /*
        if (this.productOrderDeliveryService != null) {
            this.productOrderDeliveryService.closeConnection();
        }
        if (this.productRetrievalService != null) {
            this.productRetrievalService.closeConnection();
        }
         */
    }

    /**
     * Sets the authenticationId.
     *
     * @param authenticationId The authenticationId.
     */
    public void setAuthenticationId(Blob authenticationId) {
        if (this.parameterService != null) {
            this.parameterService.setAuthenticationId(authenticationId);
        }
        /*
        if (this.productOrderDeliveryService != null) {
            this.productOrderDeliveryService.setAuthenticationId(authenticationId);
        }
        if (this.productRetrievalService != null) {
            this.productRetrievalService.setAuthenticationId(authenticationId);
        }
         */
    }
}
