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

import esa.mo.services.mpd.util.HelperMPD;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.helpertools.connections.ConnectionProvider;
import org.ccsds.moims.mo.mal.helpertools.helpers.HelperDomain;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mpd.InvalidException;
import org.ccsds.moims.mo.mpd.OrderFailedException;
import org.ccsds.moims.mo.mpd.UnknownException;
import org.ccsds.moims.mo.mpd.ordermanagement.OrderManagementHelper;
import org.ccsds.moims.mo.mpd.ordermanagement.provider.OrderManagementInheritanceSkeleton;
import org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum;
import org.ccsds.moims.mo.mpd.structures.ProductFilter;
import org.ccsds.moims.mo.mpd.structures.StandingOrder;
import org.ccsds.moims.mo.mpd.structures.StandingOrderList;
import org.ccsds.moims.mo.mpd.structures.TimeWindow;

/**
 * The Order Management service implementation, provider side.
 */
public class OrderManagementProviderServiceImpl extends OrderManagementInheritanceSkeleton {

    private static final Logger LOGGER = Logger.getLogger(OrderManagementProviderServiceImpl.class.getName());

    private final ConcurrentHashMap<Long, StandingOrder> standingOrders = new ConcurrentHashMap<>();
    private final ConnectionProvider connection = new ConnectionProvider();
    private final AtomicLong uniqueIds = new AtomicLong(System.currentTimeMillis());
    private ProductOrderDeliveryProviderServiceImpl deliveryService;
    private MALProvider service;
    private boolean running = false;

    /**
     * Initializes the service.
     *
     * @param deliveryService The backend of this service.
     * @throws MALException On initialisation error.
     */
    public synchronized void init(ProductOrderDeliveryProviderServiceImpl deliveryService) throws MALException {
        if (deliveryService == null) {
            throw new IllegalArgumentException("The deliveryService cannot be null!");
        }

        this.deliveryService = deliveryService;

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

    @Override
    public ConnectionProvider getConnection() {
        return this.connection;
    }

    @Override
    public StandingOrderList listStandingOrders(Identifier user, IdentifierList domain,
            MALInteraction interaction) throws MALInteractionException, MALException {
        StandingOrderList matchedStandingOrders = new StandingOrderList();

        for (StandingOrder order : standingOrders.values()) {
            if (user != null && !user.isWildcard()) {
                Identifier userFromOrder = order.getUser();
                // Skip if the user does not match
                if (!userFromOrder.equals(user)) {
                    continue;
                }
            }

            if (domain != null) {
                ProductFilter productFilter = order.getProductFilter();

                if (productFilter != null) {
                    IdentifierList standingOrderDomain = productFilter.getDomain();
                    if (!HelperDomain.domainMatchesWildcardDomain(standingOrderDomain, domain)) {
                        continue;
                    }
                }
            }

            matchedStandingOrders.add(order);
        }

        return matchedStandingOrders;
    }

    @Override
    public Long submitStandingOrder(StandingOrder orderDetails,
            MALInteraction interaction) throws MALInteractionException, MALException {
        // Validate the validity
        TimeWindow validity = orderDetails.getValidityPeriod();

        if (!HelperMPD.isTimeWindowValid(validity)) {
            String text = "The end date of the validity period is less than the start date!";
            throw new MALInteractionException(new InvalidException(text));
        }

        URI deliverTo = orderDetails.getDeliverTo();

        if (DeliveryMethodEnum.FILETRANSFER.equals(orderDetails.getDeliveryMethod()) && deliverTo == null) {
            String text = "The delivery method is selected as FILETRANFER and the delivery URI is set to NULL!";
            throw new MALInteractionException(new InvalidException(text));
        }

        if (!DeliveryMethodEnum.FILETRANSFER.equals(orderDetails.getDeliveryMethod()) && deliverTo != null) {
            String text = "The delivery method is not selected as FILETRANFER and the delivery URI is not set to NULL!";
            throw new MALInteractionException(new InvalidException(text));
        }

        if (deliverTo != null && !isSchemeSupported(deliverTo)) {
            String text = "The selected URI contains an unsupported scheme/protocol: " + deliverTo;
            throw new MALInteractionException(new OrderFailedException(text));
        }

        Long id = uniqueIds.getAndIncrement();
        deliveryService.addStandingOrder(id, orderDetails);
        standingOrders.put(id, orderDetails);
        return id;
    }

    @Override
    public void cancelStandingOrder(Long orderID,
            MALInteraction interaction) throws MALInteractionException, MALException {
        if (orderID == null) {
            throw new MALException("The orderRef cannot be null!");
        }

        StandingOrder standingOrder = standingOrders.get(orderID);

        // If not found...
        if (standingOrder == null) {
            throw new MALInteractionException(new UnknownException(orderID));
        }

        // Cancel the order and Remove from the list of Standing Orders
        deliveryService.removeStandingOrder(orderID);
        standingOrders.remove(orderID);
    }

    private boolean isSchemeSupported(URI deliverTo) {
        String uri = deliverTo.getValue();

        if (uri.startsWith("file://")) {
            return true;
        }

        return false;
    }

}
