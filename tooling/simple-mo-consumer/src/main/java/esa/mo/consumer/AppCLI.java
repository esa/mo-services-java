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
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mpd.productorderdelivery.consumer.ProductOrderDeliveryAdapter;
import org.ccsds.moims.mo.mpd.structures.DeliveryMethodEnum;
import org.ccsds.moims.mo.mpd.structures.StandingOrder;

/**
 *
 * @author Cesar Coelho
 */
public class AppCLI {

    /**
     * The main method.
     *
     * @param args The arguments
     */
    public static void main(String[] args) {
        String providerURI = "maltcp://xxx.xxx.xxx.xxx:1024/Directory";
        MOSimpleConsumer consumer = new MOSimpleConsumer();

        try {
            consumer.init(new URI(providerURI));

            OrderManagementConsumerServiceImpl orderManagement = consumer.getMPDServices().getOrderManagementService();

            StandingOrder orderDetails = new StandingOrder(new Identifier("User"),
                    DeliveryMethodEnum.SERVICE, "A comment");
            Identifier id = orderManagement.getOrderManagementStub().submitStandingOrder(orderDetails);

            Logger.getLogger(AppCLI.class.getName()).log(Level.INFO,
                    "The returned Identifier is: {0}", id.getValue());

            Logger.getLogger(AppCLI.class.getName()).log(Level.INFO,
                    "Registering in the Broker for service: Product Order Delivery...");

            ProductOrderDeliveryConsumerServiceImpl pod = consumer.getMPDServices().getProductOrderDeliveryService();
            Subscription subscription = ConnectionConsumer.subscriptionWildcard();
            pod.getProductOrderDeliveryStub().deliverProductsRegister(subscription, new PODAdapter());

            Logger.getLogger(AppCLI.class.getName()).log(Level.INFO, "Registered!");

        } catch (MALException ex) {
            Logger.getLogger(AppCLI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(AppCLI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MALInteractionException ex) {
            Logger.getLogger(AppCLI.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AppCLI.class.getName()).log(Level.INFO,
                    "Deliver Products received: {0}", number);
        }
    }
}
