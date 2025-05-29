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

import esa.mo.services.mpd.util.FileTransferManager;
import esa.mo.services.mpd.util.HelperProductFilters;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.helpertools.connections.ConfigurationProviderSingleton;
import org.ccsds.moims.mo.mal.helpertools.connections.ConnectionProvider;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.NullableAttribute;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mpd.NewProductAddedListener;
import org.ccsds.moims.mo.mpd.backends.ProductRetrievalBackend;
import org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryHelper;
import org.ccsds.moims.mo.mpd.productorderdelivery.provider.DeliverProductsPublisher;
import org.ccsds.moims.mo.mpd.productorderdelivery.provider.NotifyProductDeliveryPublisher;
import org.ccsds.moims.mo.mpd.productorderdelivery.provider.ProductOrderDeliveryInheritanceSkeleton;
import org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductMetadata;
import org.ccsds.moims.mo.mpd.structures.StandingOrder;
import org.ccsds.moims.mo.mpd.structures.TimeWindow;

/**
 * The Product Order Delivery service implementation, provider side.
 */
public class ProductOrderDeliveryProviderServiceImpl extends ProductOrderDeliveryInheritanceSkeleton {

    private static final Logger LOGGER = Logger.getLogger(ProductOrderDeliveryProviderServiceImpl.class.getName());

    private final ConnectionProvider connection = new ConnectionProvider();
    private boolean running = false;
    private MALProvider service;

    private final ConcurrentHashMap<Long, StandingOrder> standingOrders = new ConcurrentHashMap<>();
    private DeliverProductsPublisher deliverProductsPublisher;
    private NotifyProductDeliveryPublisher notifyProductDeliveryPublisher;
    private ProductRetrievalBackend backend;

    /**
     * Initializes the service.
     *
     * @param backend The backend of this service.
     * @throws MALException On initialisation error.
     */
    public synchronized void init(ProductRetrievalBackend backend) throws MALException {
        if (backend == null) {
            throw new IllegalArgumentException("The backend cannot be null!");
        }

        // shut down old service transport
        if (service != null) {
            connection.closeAll();
        }

        this.backend = backend;
        backend.setNewProductAddedListener(new NewProductAddedListener() {
            @Override
            public void onNewProductAdded(ObjectRef<Product> ref, ProductMetadata metadata) {
                onNewProductAddedToBackend(ref, metadata);
            }

        });
        service = connection.startService(ProductOrderDeliveryHelper.PRODUCTORDERDELIVERY_SERVICE, true, this);

        deliverProductsPublisher = super.createDeliverProductsPublisher(ConfigurationProviderSingleton.getDomain(),
                ConfigurationProviderSingleton.getNetwork(), SessionType.LIVE,
                ConfigurationProviderSingleton.getSourceSessionName(), QoSLevel.BESTEFFORT,
                null, new UInteger(0));

        notifyProductDeliveryPublisher = super.createNotifyProductDeliveryPublisher(ConfigurationProviderSingleton.getDomain(),
                ConfigurationProviderSingleton.getNetwork(), SessionType.LIVE,
                ConfigurationProviderSingleton.getSourceSessionName(), QoSLevel.BESTEFFORT,
                null, new UInteger(0));

        try {
            // Register the provider on the broker for both PUB-SUB operations:
            deliverProductsPublisher.registerWithDefaultKeys(new PublishInteractionListener());
            notifyProductDeliveryPublisher.registerWithDefaultKeys(new PublishInteractionListener());
        } catch (MALInteractionException ex) {
            Logger.getLogger(ProductOrderDeliveryProviderServiceImpl.class.getName()).log(
                    Level.SEVERE, "Something went wrong!", ex);
        }

        running = true;
        LOGGER.info("Product Order Delivery service READY");
    }

    private void onNewProductAddedToBackend(ObjectRef<Product> ref, ProductMetadata metadata) {
        // Push the Product to the matching Standing Orders
        pushProductToMatchingStandingOrders(ref, metadata);
    }

    private void pushProductToMatchingStandingOrders(ObjectRef<Product> ref, ProductMetadata metadata) {
        Time now = Time.now();
        // Iterate through the Standing Orders and push the products if it makes sense!
        for (StandingOrder order : standingOrders.values()) {
            TimeWindow validityPeriod = order.getValidityPeriod();

            if (validityPeriod != null) {
                // If it is outside the validity boundaries, then skip it
                if (now.getValue() < validityPeriod.getStart().getValue()) {
                    continue;
                }
                if (now.getValue() > validityPeriod.getEnd().getValue()) {
                    continue;
                }
            }

            try {
                // If the filters do not match, then skip it!
                if (!HelperProductFilters.productMetadataMatchesFilter(metadata, order.getProductFilter())) {
                    continue;
                }
            } catch (IOException ex) {
                // Jump over if the something goes wrong while attempting to match
                Logger.getLogger(ProductRetrievalProviderServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                continue;
            }

            Identifier user = order.getUser();
            Long orderID = order.getOrderID();

            // Passed all checks! Send product...
            if (DeliveryMethodEnum.SERVICE_COMPLETE.equals(order.getDeliveryMethod())) {
                Product product = backend.getProduct(ref, true);
                pushProduct(product, user, orderID);
            }
            if (DeliveryMethodEnum.SERVICE_JUST_METADATA.equals(order.getDeliveryMethod())) {
                Product product = backend.getProduct(ref, false);
                pushProduct(product, user, orderID);
            }
            if (DeliveryMethodEnum.FILETRANSFER.equals(order.getDeliveryMethod())) {
                Product product = backend.getProduct(ref, true);
                String filename = ref.getKey().getValue();
                URI deliverTo = order.getDeliverTo();
                if (deliverTo == null) {
                    Logger.getLogger(ProductOrderDeliveryProviderServiceImpl.class.getName()).log(
                            Level.SEVERE, "The deliverTo cannot be NULL!");
                    return;
                }
                FileTransferManager fileTransfer = new FileTransferManager(deliverTo);
                boolean success = true;
                try {
                    fileTransfer.connect();
                    fileTransfer.executeTransfer(product, filename); // Do the File Transfer
                } catch (IOException ex) {
                    success = false;
                }

                // Push notifications after the transfer...
                pushNotifications(metadata, filename, order.getDeliverTo(), success, user, orderID);
            }
        }
    }

    /**
     * Closes all running threads and releases the MAL resources.
     */
    public synchronized void close() {
        // Remove all standing orders!
        standingOrders.clear();

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

    @Override
    public ConnectionProvider getConnection() {
        return this.connection;
    }

    /**
     * Adds a standing order to this provider.
     *
     * @param orderID The orderId.
     * @param orderDetails The details of the order.
     * @return True if there was already a standing order with the same orderId,
     * false otherwise.
     */
    public synchronized boolean addStandingOrder(Long orderID, StandingOrder orderDetails) {
        StandingOrder previous = standingOrders.put(orderID, orderDetails);
        return (previous != null);
    }

    /**
     * Removes a standing order.
     *
     * @param orderID The orderId of the standing order to be removed.
     */
    public synchronized void removeStandingOrder(Long orderID) {
        standingOrders.remove(orderID);
    }

    private void pushProduct(Product product, Identifier userID, Long orderID) {
        try {
            NullableAttributeList keyValues = new NullableAttributeList();
            keyValues.add(new NullableAttribute(userID)); // keyNames userID
            keyValues.add(new NullableAttribute(new Union(orderID))); // keyNames: orderID
            UpdateHeader updateHeader = new UpdateHeader(new Identifier("source"),
                    connection.getConnectionDetails().getDomain(), keyValues);

            deliverProductsPublisher.publish(updateHeader, product);
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

    private void pushNotifications(ProductMetadata metadata, String filename,
            URI deliveredTo, Boolean success, Identifier userID, Long orderID) {
        try {
            NullableAttributeList keyValues = new NullableAttributeList();
            keyValues.add(new NullableAttribute(userID)); // keyNames userID
            keyValues.add(new NullableAttribute(new Union(orderID))); // keyNames: orderID
            UpdateHeader updateHeader = new UpdateHeader(new Identifier("source"),
                    connection.getConnectionDetails().getDomain(), keyValues);

            notifyProductDeliveryPublisher.publish(updateHeader, metadata, filename, deliveredTo, success);
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
