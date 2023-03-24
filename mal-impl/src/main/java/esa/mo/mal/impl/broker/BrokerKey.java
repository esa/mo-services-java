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
package esa.mo.mal.impl.broker;

import java.util.logging.Level;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Simple class that represents a MAL level broker key. Brokers are separated on
 * the URI of the broker and the session. This allows a broker to host several
 * contexts separated by session.
 */
public class BrokerKey implements Comparable {

    private final String uri;

    /**
     * Constructor.
     *
     * @param hdr Source message.
     */
    public BrokerKey(final MALMessageHeader hdr) {
        this.uri = hdr.getToURI().getValue();
    }

    /**
     * Constructor.
     *
     * @param uri Broker URI
     */
    public BrokerKey(String uri) {
        this.uri = uri;
        MALBrokerImpl.LOGGER.log(Level.INFO, "The BrokerKey has url: ", uri);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof BrokerKey) {
            final BrokerKey other = (BrokerKey) obj;
            if (uri == null) {
                return (other.uri == null);
            } else {
                return uri.equals(other.uri);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return uri.hashCode();
    }

    @Override
    public int compareTo(final Object o) {
        final BrokerKey other = (BrokerKey) o;
        return uri.compareTo(other.uri);
    }
}
