/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : ESA CCSDS MO Services
 * ----------------------------------------------------------------------------
 * Licensed under European Space Agency Public License (ESA-PL) Weak Copyleft – v2.4
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
package esa.mo.consumer;

import esa.mo.services.mpd.consumer.OrderManagementConsumerServiceImpl;
import esa.mo.services.mpd.consumer.ProductOrderDeliveryConsumerServiceImpl;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.helpertools.connections.ConnectionConsumer;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mpd.ordermanagement.consumer.OrderManagementStub;
import org.ccsds.moims.mo.mpd.productorderdelivery.consumer.ProductOrderDeliveryAdapter;
import org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum;
import org.ccsds.moims.mo.mpd.structures.StandingOrder;
import org.ccsds.moims.mo.mpd.structures.StandingOrderList;

/**
 *
 * @author Cesar Coelho
 */
public class AppWithDirectory {

    /**
     * The main method.
     *
     * @param args The arguments
     */
    public static void main(String[] args) {
        String providerURI = "maltcp://xxx.xxx.xxx.xxx:1024/Directory";
        MOConsumerWithDirectory consumer = new MOConsumerWithDirectory();

        try {
            consumer.init(new URI(providerURI));

            // Register on the Broker for service delivery of products
            Logger.getLogger(AppWithDirectory.class.getName()).log(Level.INFO,
                    "Registering in the Broker for service: Product Order Delivery...");

            ProductOrderDeliveryConsumerServiceImpl pod = consumer.getMPDServices().getProductOrderDeliveryService();
            Subscription subscription = ConnectionConsumer.subscriptionWildcard();
            pod.getProductOrderDeliveryStub().deliverProductsRegister(subscription, new PODAdapter());
            Logger.getLogger(AppWithDirectory.class.getName()).log(Level.INFO, "Registered!");

            // Submit a Standing Order
            StandingOrder orderDetails = new StandingOrder(new Identifier("User"), DeliveryMethodEnum.SERVICE);

            OrderManagementConsumerServiceImpl orderManagement = consumer.getMPDServices().getOrderManagementService();
            OrderManagementStub stub = orderManagement.getOrderManagementStub();
            Long id = stub.submitStandingOrder(orderDetails);

            Logger.getLogger(AppWithDirectory.class.getName()).log(Level.INFO,
                    "The returned Identifier is: {0}", id);

            // Request the list of standing orders
            IdentifierList domain = new IdentifierList();
            domain.add(new Identifier("*"));
            StandingOrderList list = stub.listStandingOrders(new Identifier("*"), domain);
            Logger.getLogger(AppWithDirectory.class.getName()).log(Level.INFO,
                    "The returned list of standing orders is: {0}", list.toString());

            try {
                // Wait 15 seconds...
                Logger.getLogger(AppWithDirectory.class.getName()).log(Level.INFO, "Waiting 15 seconds...");
                Thread.sleep(15 * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(AppWithDirectory.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Cancel all standing orders
            stub.cancelStandingOrder(id);

            Logger.getLogger(AppWithDirectory.class.getName()).log(Level.INFO,
                    "The following order was cancelled: {0}", id);
        } catch (MALException ex) {
            Logger.getLogger(AppWithDirectory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(AppWithDirectory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MALInteractionException ex) {
            Logger.getLogger(AppWithDirectory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static class PODAdapter extends ProductOrderDeliveryAdapter {

        @Override
        public void deliverProductsNotifyReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
                org.ccsds.moims.mo.mal.structures.Identifier subscriptionId,
                org.ccsds.moims.mo.mal.structures.UpdateHeader updateHeader,
                org.ccsds.moims.mo.mpd.structures.Product product,
                java.util.Map qosProperties) {
            Blob productBody = product.getProductBody();
            byte[] value = productBody.getValue();
            int number = (int) value[0];
            Logger.getLogger(AppWithDirectory.class.getName()).log(Level.INFO,
                    "Deliver Products received: {0}", number);
        }
    }
}
