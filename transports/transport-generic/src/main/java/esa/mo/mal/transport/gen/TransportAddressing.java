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

import java.util.concurrent.ConcurrentHashMap;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 * Describes the shape of the URIs used by a transport, and parses them.
 *
 * A transport URI is built as:
 * {@code <protocol><protocolDelim><transport address><serviceDelim><routing part>}
 * where the routing part may itself be terminated by a routing delimiter when
 * the protocol supports routing.
 */
public class TransportAddressing {

    /**
     * The string used to represent this protocol.
     */
    private final String protocol;
    /**
     * The delimiter to use to separate the protocol part from the address part
     * of the URL.
     */
    private final String protocolDelim;
    /**
     * The delimiter to use to separate the external address part from the
     * internal object part of the URL.
     */
    private final char serviceDelim;
    /**
     * If the protocol delimiter is the same as the service delimiter then we
     * need a count to find the correct service delimiter.
     */
    private final int serviceDelimCounter;
    /**
     * Delimiter to use when holding routing information in a URL.
     */
    private final char routingDelim;
    /**
     * True if protocol supports the concept of routing.
     */
    private final boolean supportsRouting;
    /**
     * Map of cachedRoutingParts. This associates a URI to its Routing part.
     */
    private final ConcurrentHashMap<String, String> cachedRoutingParts = new ConcurrentHashMap<>();
    /**
     * The base string for URL for this protocol.
     */
    private String uriBase;

    /**
     * Constructor.
     *
     * @param protocol The protocol string.
     * @param protocolDelim The delimiter separating the protocol part of the
     * URL.
     * @param serviceDelim The delimiter separating the address part of the URL.
     * @param routingDelim The delimiter separating the routing part of the URL.
     * @param supportsRouting True if routing is supported by the naming
     * convention.
     */
    public TransportAddressing(final String protocol, final String protocolDelim,
            final char serviceDelim, final char routingDelim, final boolean supportsRouting) {
        this.protocol = protocol;
        this.protocolDelim = protocolDelim;
        this.serviceDelim = serviceDelim;
        this.routingDelim = routingDelim;
        this.supportsRouting = supportsRouting;

        if (protocolDelim.contains("" + serviceDelim)) {
            String replaced = protocolDelim.replace("" + serviceDelim, "");
            this.serviceDelimCounter = protocolDelim.length() - replaced.length();
        } else {
            this.serviceDelimCounter = 0;
        }
    }

    /**
     * Returns the protocol string.
     *
     * @return The protocol string.
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * Returns the protocol delimiter.
     *
     * @return The protocol delimiter.
     */
    public String getProtocolDelim() {
        return protocolDelim;
    }

    /**
     * Returns the service delimiter.
     *
     * @return The service delimiter.
     */
    public char getServiceDelim() {
        return serviceDelim;
    }

    /**
     * Returns the number of service delimiters contained in the protocol
     * delimiter.
     *
     * @return The service delimiter count.
     */
    public int getServiceDelimCounter() {
        return serviceDelimCounter;
    }

    /**
     * Returns the routing delimiter.
     *
     * @return The routing delimiter.
     */
    public char getRoutingDelim() {
        return routingDelim;
    }

    /**
     * Returns true if the protocol supports routing.
     *
     * @return True if the protocol supports routing.
     */
    public boolean supportsRouting() {
        return supportsRouting;
    }

    /**
     * Builds the base URI for this transport from the transport specific
     * address.
     *
     * @param transportAddress The address part specific to this transport
     * instance.
     */
    public void initUriBase(final String transportAddress) {
        String protocolString = protocol;

        if (protocol.contains(":")) {
            protocolString = protocol.substring(0, protocol.indexOf(':'));
        }

        uriBase = protocolString + protocolDelim + transportAddress + serviceDelim;
    }

    /**
     * Returns the base URI for this transport, or null if it has not been built
     * yet.
     *
     * @return The base URI.
     */
    public String getUriBase() {
        return uriBase;
    }

    /**
     * Returns the root part of a URI, i.e. the part identifying the transport
     * instance rather than the endpoint within it.
     *
     * @param uri The URI.
     * @return The root URI.
     */
    public String getRootURI(final URI uri) {
        return uri.getRootURI(serviceDelim, serviceDelimCounter);
    }

    /**
     * Returns true if a root URI refers to this transport instance, and so can
     * be delivered to without going through the underlying transport.
     *
     * @param rootURI The root URI to test.
     * @return True if the root URI refers to this transport instance.
     */
    public boolean matchesLocalBase(final String rootURI) {
        return uriBase.startsWith(rootURI) || rootURI.startsWith(uriBase);
    }

    /**
     * Returns the routing part of a URI, i.e. the endpoint identifier. Results
     * are cached, as the same URIs are parsed repeatedly.
     *
     * @param uriValue The URI value.
     * @return The routing part of the URI.
     */
    public String getRoutingPart(final String uriValue) {
        String routingPart = cachedRoutingParts.get(uriValue);

        if (routingPart == null) {
            final int iFirst = URI.nthIndexOf(uriValue, serviceDelim, serviceDelimCounter);
            int iSecond = supportsRouting ? uriValue.indexOf(routingDelim) : uriValue.length();
            if (iSecond < 0) {
                iSecond = uriValue.length();
            }

            routingPart = uriValue.substring(iFirst + 1, iSecond);
            cachedRoutingParts.put(uriValue, routingPart);
        }

        return routingPart;
    }
}
