/* ----------------------------------------------------------------------------
 * Copyright (C) 2021      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java API
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
package org.ccsds.moims.mo.mal.helpertools.connections;

import java.io.IOException;
import org.ccsds.moims.mo.mal.helpertools.helpers.HelperMisc;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.SessionType;

/**
 * Holds the configurations for a Consumer.
 */
public class ConfigurationConsumer {

    private final Identifier network;
    private final SessionType session;
    private final Identifier sessionName;

    /**
     * Initializes the class with the values made available in the PROPERTIES.
     * This includes the generation of the domain from the properties:
     * ORGANIZATION_NAME, MISSION_NAME, MO_APP_NAME
     *
     * It also generates the network zone field from the properties:
     * ORGANIZATION_NAME, MISSION_NAME, NETWORK_ZONE, DEVICE_NAME
     *
     * Additionally, it sets the session to SessionType.LIVE and sets the
     * session name as LIVE.
     */
    public ConfigurationConsumer() {
        if (System.getProperty(HelperMisc.PROP_ORGANIZATION_NAME) == null) {
            try {
                // The property does not exist?
                HelperMisc.loadConsumerProperties(); // try to load the properties from the file...
            } catch (IOException ex) {
                // Ignore the exception if it does not exist
                // This file is becoming deprecated and eventually will be removed...
            }
        }

        // ------------------------Network----------------------------
        String networkString = "";
        if (System.getProperty(HelperMisc.PROP_NETWORK) != null) {
            networkString = System.getProperty(HelperMisc.PROP_NETWORK);
        } else {
            if (System.getProperty(HelperMisc.PROP_ORGANIZATION_NAME) != null) {
                networkString = networkString.concat(System.getProperty(HelperMisc.PROP_ORGANIZATION_NAME));
            } else {
                networkString += "OrganizationName";
            }

            networkString += ".";

            if (System.getProperty(HelperMisc.PROP_MISSION_NAME) != null) {
                networkString = networkString.concat(System.getProperty(HelperMisc.PROP_MISSION_NAME));
            } else {
                networkString += "MissionName";
            }

            networkString += ".";

            if (System.getProperty(HelperMisc.PROP_NETWORK_ZONE) != null) {
                networkString = networkString.concat(System.getProperty(HelperMisc.PROP_NETWORK_ZONE));
            } else {
                networkString += "NetworkZone";
            }

            networkString += ".";

            if (System.getProperty(HelperMisc.PROP_DEVICE_NAME) != null) {
                networkString = networkString.concat(System.getProperty(HelperMisc.PROP_DEVICE_NAME));
            } else {
                networkString += "DeviceName";
            }
        }

        this.network = new Identifier(networkString);
        this.session = SessionType.LIVE;
        this.sessionName = HelperMisc.SESSION_NAME; // Default it to "LIVE"
    }

    /**
     * Returns the network zone.
     *
     * @return The network zone.
     */
    public Identifier getNetwork() {
        return this.network;
    }

    /**
     * Returns the session type.
     *
     * @return The session type.
     */
    public SessionType getSession() {
        return this.session;
    }

    /**
     * Returns the session name.
     *
     * @return The session name.
     */
    public Identifier getSessionName() {
        return this.sessionName;
    }

}
