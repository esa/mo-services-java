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
package esa.mo.services.mpd.consumer;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.helpertools.connections.SingleConnectionDetails;
import org.ccsds.moims.mo.mal.helpertools.misc.ConsumerServiceImpl;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryHelper;
import org.ccsds.moims.mo.mpd.productorderdelivery.consumer.ProductOrderDeliveryStub;

/**
 * The Product Order Delivery service implementation, consumer side.
 */
public class ProductOrderDeliveryConsumerServiceImpl extends ConsumerServiceImpl {

    private ProductOrderDeliveryStub productOrderDeliveryService = null;

    public ProductOrderDeliveryConsumerServiceImpl(final SingleConnectionDetails connectionDetails)
            throws MALException, MALInteractionException {
        this(connectionDetails, null, null);
    }

    public ProductOrderDeliveryConsumerServiceImpl(final SingleConnectionDetails connectionDetails,
            final Blob authenticationId, final String localNamePrefix)
            throws MALException, MALInteractionException {
        this.connectionDetails = connectionDetails;

        // Close previous connection
        if (tmConsumer != null) {
            try {
                tmConsumer.close();
            } catch (MALException ex) {
                Logger.getLogger(ProductOrderDeliveryConsumerServiceImpl.class.getName()).log(
                        Level.SEVERE, "The previous connection could not be closed!", ex);
            }
        }

        tmConsumer = connection.startService(connectionDetails,
                ProductOrderDeliveryHelper.PRODUCTORDERDELIVERY_SERVICE, authenticationId, localNamePrefix);

        this.productOrderDeliveryService = new ProductOrderDeliveryStub(tmConsumer);
    }

    @Override
    public Object generateServiceStub(MALConsumer tmConsumer) {
        return new ProductOrderDeliveryStub(tmConsumer);
    }

    @Override
    public Object getStub() {
        return this.getProductOrderDeliveryStub();
    }

    public ProductOrderDeliveryStub getProductOrderDeliveryStub() {
        return this.productOrderDeliveryService;
    }
}
