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
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.URI;
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
        String providerURI = "maltcp://10.36.181.167:1024/Directory";
        MOSimpleConsumer consumer = new MOSimpleConsumer();

        try {
            consumer.init(new URI(providerURI));

            OrderManagementConsumerServiceImpl orderManagement = consumer.getMPDServices().getOrderManagementService();

            StandingOrder orderDetails = new StandingOrder(new Identifier("User"),
                    DeliveryMethodEnum.SERVICE, "A comment");
            Identifier id = orderManagement.getOrderManagementStub().submitStandingOrder(orderDetails);

            Logger.getLogger(AppCLI.class.getName()).log(Level.SEVERE,
                    "The returned Identifier is: {0}", id.getValue());
        } catch (MALException ex) {
            Logger.getLogger(AppCLI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(AppCLI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MALInteractionException ex) {
            Logger.getLogger(AppCLI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
