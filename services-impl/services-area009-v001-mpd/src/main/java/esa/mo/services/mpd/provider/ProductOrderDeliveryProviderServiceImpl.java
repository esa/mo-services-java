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
import org.ccsds.moims.mo.mal.helpertools.connections.ConfigurationProviderSingleton;
import org.ccsds.moims.mo.mal.helpertools.connections.ConnectionProvider;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryHelper;
import org.ccsds.moims.mo.mpd.productorderdelivery.provider.DeliverProductsPublisher;
import org.ccsds.moims.mo.mpd.productorderdelivery.provider.ProductOrderDeliveryInheritanceSkeleton;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductType;
import org.ccsds.moims.mo.mpd.structures.TimeWindow;

/**
 * The Product Order Delivery service implementation, provider side.
 */
public class ProductOrderDeliveryProviderServiceImpl extends ProductOrderDeliveryInheritanceSkeleton {

    private static final Logger LOGGER = Logger.getLogger(ProductOrderDeliveryProviderServiceImpl.class.getName());

    private final ConnectionProvider connection = new ConnectionProvider();

    private boolean running = false;
    private MALProvider service;

    private DeliverProductsPublisher publisher;

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

        service = connection.startService(ProductOrderDeliveryHelper.PRODUCTORDERDELIVERY_SERVICE, true, this);
        // PUB-SUB code needs to be added!!!

        publisher = super.createDeliverProductsPublisher(ConfigurationProviderSingleton.getDomain(),
                ConfigurationProviderSingleton.getNetwork(), SessionType.LIVE,
                ConfigurationProviderSingleton.getSourceSessionName(), QoSLevel.BESTEFFORT,
                null, new UInteger(0));

        try {
            // Register the provider on the broker:
            publisher.registerWithDefaultKeys(new PublishInteractionListener());
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ProductOrderDeliveryProviderServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MALInteractionException ex) {
            Logger.getLogger(ProductOrderDeliveryProviderServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        running = true;
        PublishThread t1 = new PublishThread();
        t1.start();
        LOGGER.info("Product Order Delivery service READY");
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

    public class PublishThread extends Thread {

        @Override
        public void run() {
            int counter = 0;
            while (running) {
                try {
                    Thread.sleep(10 * 1000); // 10 seconds
                    NullableAttributeList keyValues = new NullableAttributeList();
                    UpdateHeader updateHeader = new UpdateHeader(new Identifier("source"),
                            connection.getConnectionDetails().getDomain(), keyValues);

                    ObjectIdentity id = new ObjectIdentity(new IdentifierList(), new Identifier("key"), new UInteger(1L));
                    ObjectRef<ProductType> productType = new ObjectRef<>();
                    byte[] byteArray = new byte[]{(byte) counter};
                    counter++;
                    Product product = new Product(id, productType, Time.now(),
                            new TimeWindow(Time.now(), Time.now()), new Blob(byteArray));
                    publisher.publish(updateHeader, product);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ProductOrderDeliveryProviderServiceImpl.class.getName()).log(
                            Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(ProductOrderDeliveryProviderServiceImpl.class.getName()).log(
                            Level.SEVERE, null, ex);
                } catch (MALInteractionException ex) {
                    Logger.getLogger(ProductOrderDeliveryProviderServiceImpl.class.getName()).log(
                            Level.SEVERE, null, ex);
                } catch (MALException ex) {
                    Logger.getLogger(ProductOrderDeliveryProviderServiceImpl.class.getName()).log(
                            Level.SEVERE, null, ex);
                }
            }
        }
    }
}
