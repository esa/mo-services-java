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
package esa.mo.provider;

import esa.mo.common.impl.provider.DirectoryProviderServiceImpl;
import esa.mo.services.mpd.util.MPDServicesProvider;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.helpertools.connections.ConnectionProvider;
import org.ccsds.moims.mo.mal.helpertools.helpers.HelperMisc;

/**
 * The MOSimpleProvider class contains a provider with a Directory service and a
 * few other services.
 */
public class MOSimpleProvider {

    // Create provider name to be registerd on the Directory service...
    private final static String PROVIDER_NAME = "Simple MO Provider";

    private final DirectoryProviderServiceImpl directoryService = new DirectoryProviderServiceImpl();
    private final MPDServicesProvider mpdServices = new MPDServicesProvider();

    private long startTime;

    /**
     * Initializes the MO Simple Provider.
     */
    public void init() {
        this.startTime = System.currentTimeMillis();
        // Loads: provider.properties; settings.properties; transport.properties
        HelperMisc.loadPropertiesFile();
        // Let's reset the providerURIs.properties file:
        ConnectionProvider.resetURILinksFile();

        try {
            Logger.getLogger(MOSimpleProvider.class.getName()).log(Level.INFO,
                    "Initializing services...");

            directoryService.init();

            DummyProductsBackend backend = new DummyProductsBackend();
            mpdServices.init(backend);
        } catch (MALException ex) {
            Logger.getLogger(MOSimpleProvider.class.getName()).log(Level.SEVERE,
                    "The services could not be initialized. Perhaps there's "
                    + "something wrong with the selected Transport Layer.", ex);
            return;
        }

        // Populate the Directory service with the entries from the URIs File
        Logger.getLogger(MOSimpleProvider.class.getName()).log(
                Level.INFO, "Populating Directory service...");
        directoryService.loadURIs(PROVIDER_NAME);

        Logger.getLogger(MOSimpleProvider.class.getName()).log(Level.INFO,
                "Simple MO Provider initialized in {0} seconds!",
                ((float) (System.currentTimeMillis() - this.startTime)) / 1000);

        final String uri = directoryService.getConnection().getConnectionDetails().getProviderURI().toString();
        Logger.getLogger(MOSimpleProvider.class.getName()).log(
                Level.INFO, "URI: {0}\n", uri);
    }

}
