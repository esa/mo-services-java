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
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.helpertools.connections.ConnectionConsumer;
import org.ccsds.moims.mo.mal.helpertools.connections.SingleConnectionDetails;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mpd.ordermanagement.OrderManagementServiceInfo;

/**
 * The MPD services consumer class that contains all the consumer services.
 */
public class MPDServicesConsumer {

    private OrderManagementConsumerServiceImpl orderManagementService;

    public void init(ConnectionConsumer connectionConsumer) {
        init(connectionConsumer, null, null);
    }

    public void init(ConnectionConsumer connectionConsumer, Blob authenticationId, String localNamePrefix) {
        SingleConnectionDetails details;

        try {
            // Initialize the Order Management service
            details = connectionConsumer.getServicesDetails().get(OrderManagementServiceInfo.ORDERMANAGEMENT_SERVICE_NAME);
            if (details != null) {
                orderManagementService = new OrderManagementConsumerServiceImpl(details, authenticationId, localNamePrefix);
            }
        } catch (MALException | MALInteractionException | MalformedURLException ex) {
            Logger.getLogger(MPDServicesConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public OrderManagementConsumerServiceImpl getOrderManagementService() {
        return this.orderManagementService;
    }

    /**
     * Closes the service consumer connections
     *
     */
    public void closeConnections() {
        if (this.orderManagementService != null) {
            this.orderManagementService.closeConnection();
        }
    }

    public void setAuthenticationId(Blob authenticationId) {
        if (this.orderManagementService != null) {
            this.orderManagementService.setAuthenticationId(authenticationId);
        }
    }
}
