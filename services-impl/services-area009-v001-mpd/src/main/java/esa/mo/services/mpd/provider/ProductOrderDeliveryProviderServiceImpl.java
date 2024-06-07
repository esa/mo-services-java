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
import org.ccsds.moims.mo.mal.helpertools.connections.ConnectionProvider;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryHelper;
import org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryServiceInfo;
import org.ccsds.moims.mo.mpd.productorderdelivery.provider.ProductOrderDeliveryInheritanceSkeleton;

/**
 * The Product Order Delivery service implementation, provider side.
 */
public class ProductOrderDeliveryProviderServiceImpl extends ProductOrderDeliveryInheritanceSkeleton {

    private static final Logger LOGGER = Logger.getLogger(ProductOrderDeliveryProviderServiceImpl.class.getName());

    private final ConnectionProvider connection = new ConnectionProvider();

    private boolean running = false;
    private MALProvider productOrderDeliveryServiceProvider;

    /**
     * Initializes the service.
     *
     * @throws MALException On initialisation error.
     */
    public synchronized void init() throws MALException {
        // shut down old service transport
        if (null != productOrderDeliveryServiceProvider) {
            connection.closeAll();
        }

        productOrderDeliveryServiceProvider = connection.startService(
                ProductOrderDeliveryServiceInfo.PRODUCTORDERDELIVERY_SERVICE_NAME.toString(),
                ProductOrderDeliveryHelper.PRODUCTORDERDELIVERY_SERVICE, true, this);

        // PUB-SUB code needs to be added!!!
        running = true;
        LOGGER.info("Product Order Delivery service READY");
    }

    /**
     * Closes all running threads and releases the MAL resources.
     */
    public void close() {
        try {
            if (null != productOrderDeliveryServiceProvider) {
                productOrderDeliveryServiceProvider.close();
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

}
