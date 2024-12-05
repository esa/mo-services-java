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

import esa.mo.services.mpd.util.MPDServicesConsumer;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.helpertools.connections.ConnectionConsumer;
import org.ccsds.moims.mo.mal.helpertools.helpers.HelperMisc;
import org.ccsds.moims.mo.mpd.MPDHelper;

/**
 * The MOSimpleConsumer class connects to MPDServices.
 */
public class MOSimpleConsumer {

    private final MPDServicesConsumer mpdConsumerServices = new MPDServicesConsumer();

    /**
     * Initializes the MO Simple Consumer.
     *
     * @throws org.ccsds.moims.mo.mal.MALException if the service could not be
     * started.
     * @throws java.net.MalformedURLException if the consumer properties file
     * could not be loaded.
     * @throws java.io.FileNotFoundException if the URIs file could not be
     * found.
     */
    public void init() throws MALException, MalformedURLException, java.io.FileNotFoundException {
        try {
            HelperMisc.loadConsumerProperties();
        } catch (MalformedURLException ex) {
            Logger.getLogger(MOSimpleConsumer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            // Ignore the exception if it does not exist - the file is becoming deprecated
            Logger.getLogger(MOSimpleConsumer.class.getName()).log(Level.FINE, null, ex);
        }
        ConnectionConsumer connection = new ConnectionConsumer();
        connection.loadURIs();

        MALContextFactory.getElementsRegistry().loadFullArea(MPDHelper.MPD_AREA);
        mpdConsumerServices.init(connection);
    }

    public MPDServicesConsumer getMPDServices() {
        return mpdConsumerServices;
    }
}
