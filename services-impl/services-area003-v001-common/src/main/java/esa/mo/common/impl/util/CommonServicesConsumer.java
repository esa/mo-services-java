/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Common services
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
package esa.mo.common.impl.util;

import esa.mo.common.impl.consumer.DirectoryConsumerServiceImpl;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.common.directory.DirectoryServiceInfo;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.helpertools.connections.ConnectionConsumer;
import org.ccsds.moims.mo.mal.helpertools.connections.SingleConnectionDetails;
import org.ccsds.moims.mo.mal.structures.Blob;

/**
 * The Common services consumer class that contains all the consumer services.
 */
public class CommonServicesConsumer {

    private DirectoryConsumerServiceImpl directoryService;

    /**
     * Initializes the consumers.
     *
     * @param connectionConsumer The connectionConsumer.
     */
    public void init(ConnectionConsumer connectionConsumer) {
        init(connectionConsumer, null, null);
    }

    /**
     * Initializes the consumers.
     *
     * @param connectionConsumer The connectionConsumer.
     * @param authenticationId The authenticationId token.
     * @param localNamePrefix The local name prefix.
     */
    public void init(ConnectionConsumer connectionConsumer, Blob authenticationId, String localNamePrefix) {
        SingleConnectionDetails details;

        try {
            // Initialize the Directory service
            details = connectionConsumer.getServicesDetails().get(DirectoryServiceInfo.DIRECTORY_SERVICE_NAME);
            if (details != null) {
                directoryService = new DirectoryConsumerServiceImpl(details.getProviderURI(),
                        authenticationId, localNamePrefix);
            }
        } catch (MALException ex) {
            Logger.getLogger(CommonServicesConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns the Directory service consumer.
     *
     * @return The Directory service consumer.
     */
    public DirectoryConsumerServiceImpl getDirectoryService() {
        return this.directoryService;
    }

    /**
     * Sets the services.
     *
     * @param directoryService The Directory service consumer.
     */
    public void setServices(DirectoryConsumerServiceImpl directoryService) {
        this.directoryService = directoryService;
    }

    /**
     * Sets the Directory service.
     *
     * @param directoryService The Directory service consumer.
     */
    public void setDirectoryService(DirectoryConsumerServiceImpl directoryService) {
        this.directoryService = directoryService;
    }

    /**
     * Closes the service consumer connections
     */
    public void closeConnections() {
        if (this.directoryService != null) {
            this.directoryService.closeConnection();
        }
    }

    /**
     * Sets the authenticationId.
     *
     * @param authenticationId The authenticationId.
     */
    public void setAuthenticationId(Blob authenticationId) {
        if (this.directoryService != null) {
            this.directoryService.setAuthenticationId(authenticationId);
        }
    }
}
