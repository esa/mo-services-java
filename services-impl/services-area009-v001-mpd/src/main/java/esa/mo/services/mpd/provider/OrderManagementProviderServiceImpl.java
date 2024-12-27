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
package esa.mo.services.mpd.provider;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.helpertools.connections.ConnectionProvider;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mpd.ordermanagement.OrderManagementHelper;
import org.ccsds.moims.mo.mpd.ordermanagement.provider.OrderManagementInheritanceSkeleton;
import org.ccsds.moims.mo.mpd.structures.StandingOrder;
import org.ccsds.moims.mo.mpd.structures.StandingOrderList;

/**
 * The Order Management service implementation, provider side.
 */
public class OrderManagementProviderServiceImpl extends OrderManagementInheritanceSkeleton {

    private static final Logger LOGGER = Logger.getLogger(OrderManagementProviderServiceImpl.class.getName());

    private final ConnectionProvider connection = new ConnectionProvider();
    private MALProvider service;
    private boolean running = false;

    /**
     * Initializes the service.
     *
     * @throws MALException On initialisation error.
     */
    public synchronized void init() throws MALException {
        // shut down old service transport
        if (service != null) {
            connection.closeAll();
        }

        service = connection.startService(OrderManagementHelper.ORDERMANAGEMENT_SERVICE, false, this);
        running = true;
        LOGGER.info("Order Management service READY");
    }

    /**
     * Closes all running threads and releases the MAL resources.
     */
    public void close() {
        try {
            if (service != null) {
                service.close();
            }

            connection.closeAll();
            running = false;
        } catch (MALException ex) {
            LOGGER.log(Level.WARNING, "Exception during close down of the provider {0}", ex);
        }
    }

    public ConnectionProvider getConnection() {
        return this.connection;
    }

    @Override
    public StandingOrderList listStandingOrders(Identifier user, IdentifierList domain,
            MALInteraction interaction) throws MALInteractionException, MALException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Long submitStandingOrder(StandingOrder orderDetails,
            MALInteraction interaction) throws MALInteractionException, MALException {
        return 1L;
    }

    @Override
    public void cancelStandingOrder(Long orderID,
            MALInteraction interaction) throws MALInteractionException, MALException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
