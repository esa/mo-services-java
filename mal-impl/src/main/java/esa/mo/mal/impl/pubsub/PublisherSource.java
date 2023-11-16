/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java Implementation
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
package esa.mo.mal.impl.pubsub;

import esa.mo.mal.impl.broker.MALBrokerImpl;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;

/**
 * Represents a publisher (provider) in a broker, so contains the list of
 * entities it is allowed to publish.
 */
public final class PublisherSource {

    private final String uri;
    private IdentifierList subscriptionKeyNames;

    /**
     * Constructor.
     *
     * @param uri The publisher source uri.
     */
    public PublisherSource(final String uri) {
        this.uri = uri;
    }

    public IdentifierList getSubscriptionKeyNames() {
        return subscriptionKeyNames;
    }

    public void setSubscriptionKeyNames(IdentifierList subscriptionKeyNames) {
        this.subscriptionKeyNames = subscriptionKeyNames;
    }

    /**
     * Logs the START and END providers and the subscription keys.
     */
    public void report() {
        MALBrokerImpl.LOGGER.log(Level.FINE, "  START Provider ( {0} )", uri);

        for (Identifier key : subscriptionKeyNames) {
            MALBrokerImpl.LOGGER.log(Level.FINE, "    Allowed key: {0}", key);
        }
        MALBrokerImpl.LOGGER.log(Level.FINE, "  END Provider ( {0} )", uri);
    }
}
