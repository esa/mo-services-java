/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Generic Transport Framework
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
package esa.mo.mal.transport.gen;

import java.util.HashMap;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;

/**
 * Holds the endpoints that belong to a transport. Each endpoint is indexed
 * twice: once under the MAL local name it was created with, and once under the
 * transport routing name derived from it.
 */
public class EndpointRegistry {

    /**
     * Map of string MAL names to endpoints.
     */
    private final Map<String, Endpoint> endpointMalMap = new HashMap<>();
    /**
     * Map of string transport routing names to endpoints.
     */
    private final Map<String, Endpoint> endpointRoutingMap = new HashMap<>();

    /**
     * Returns the endpoint registered under a MAL local name.
     *
     * @param localName The MAL local name.
     * @return The endpoint, or null if none is registered.
     */
    public Endpoint getByLocalName(final String localName) {
        return endpointMalMap.get(localName);
    }

    /**
     * Returns the endpoint registered under a transport routing name.
     *
     * @param routingName The transport routing name.
     * @return The endpoint, or null if none is registered.
     */
    public Endpoint getByRoutingName(final String routingName) {
        return endpointRoutingMap.get(routingName);
    }

    /**
     * Returns true if an endpoint is registered under a routing name.
     *
     * @param routingName The transport routing name.
     * @return True if an endpoint is registered under that routing name.
     */
    public boolean containsRoutingName(final String routingName) {
        return endpointRoutingMap.containsKey(routingName);
    }

    /**
     * Registers an endpoint under both its MAL local name and its routing name.
     *
     * @param localName The MAL local name.
     * @param routingName The transport routing name.
     * @param endpoint The endpoint to register.
     */
    public void add(final String localName, final String routingName, final Endpoint endpoint) {
        endpointMalMap.put(localName, endpoint);
        endpointRoutingMap.put(routingName, endpoint);
    }

    /**
     * Removes the endpoint registered under a MAL local name, together with its
     * routing name entry. The endpoint itself is not closed.
     *
     * @param localName The MAL local name.
     * @return The removed endpoint, or null if none was registered.
     */
    public Endpoint remove(final String localName) {
        final Endpoint endpoint = endpointMalMap.get(localName);

        if (endpoint != null) {
            endpointMalMap.remove(localName);
            endpointRoutingMap.remove(endpoint.getRoutingName());
        }

        return endpoint;
    }

    /**
     * Returns an arbitrary registered endpoint. Used when a message has to be
     * sent that is not tied to any particular endpoint, such as an error reply
     * for a message that could not be routed.
     *
     * @return Any registered endpoint, or null if none are registered.
     */
    public Endpoint any() {
        for (Endpoint endpoint : endpointMalMap.values()) {
            return endpoint;
        }

        return null;
    }

    /**
     * Closes every registered endpoint and empties the registry.
     *
     * @throws MALException if an endpoint could not be closed.
     */
    public void closeAll() throws MALException {
        for (Endpoint endpoint : endpointMalMap.values()) {
            endpoint.close();
        }

        endpointMalMap.clear();
        endpointRoutingMap.clear();
    }
}
