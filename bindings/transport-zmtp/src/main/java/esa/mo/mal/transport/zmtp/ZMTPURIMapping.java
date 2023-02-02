/* ----------------------------------------------------------------------------
 * Copyright (C) 2017      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO ZMTP Transport Framework
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
package esa.mo.mal.transport.zmtp;

import org.ccsds.moims.mo.mal.MALException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * This class implements default MAL-ZMTP URI mapping.
 *
 * An alternative mapping can be used by overriding this class and
 * ZMTPTransportFactoryImpl
 */
public class ZMTPURIMapping {

    public static final String USE_MULTICAST_KEY = "esa.mo.mal.transport.zmtp.usemulticast";

    /**
     * True if multicast transport channel is used in addition to P2P channel
     * Otherwise mapping functions return null for multicast URIs
     */
    private final boolean useMulticast;

    /**
     * Default ZMTP URI Mapping constructor
     *
     * @param properties Mapping properties
     */
    public ZMTPURIMapping(final java.util.Map properties) {
        if (properties.containsKey(USE_MULTICAST_KEY)) {
            this.useMulticast = Boolean.parseBoolean((String) properties.get(USE_MULTICAST_KEY));
        } else {
            this.useMulticast = false; // default to P2P only
        }
    }

    /**
     * Maps a local MAL URI to a P2P ZMTP URI
     *
     * @param MalUri Local MAL URI
     * @return ZMTP URI the ZMQ P2P channel destination socket should bind to
     * @throws MALException if passed MAL URI is invalid
     */
    public String getLocalPtpZmtpUri(String MalUri) throws MALException {
        try {
            URI uri = new URI(MalUri);
            if (uri.getPort() == -1) {
                throw new MALException("Malformed URI (missing port): " + MalUri);
            }
            // The URI that ZMTP binds to
            return "tcp://*:" + Integer.toString(uri.getPort());
        } catch (URISyntaxException e) {
            throw new MALException("Malformed URI: " + MalUri);
        }
    }

    /**
     * Maps a local MAL URI to a multicast ZMTP URI (if enabled)
     *
     * @param MalUri Local MAL URI
     * @return ZMTP URI the ZMQ multicast channel destination socket should bind
     * to, null if multicast is not supported
     * @throws MALException if passed MAL URI is invalid
     */
    public String getLocalMcastZmtpUri(String MalUri) throws MALException {
        if (!this.useMulticast) {
            return null;
        } else {
            try {
                URI uri = new URI(MalUri);
                if (uri.getPort() == -1) {
                    throw new MALException("Malformed URI (missing port): " + MalUri);
                }
                // The URI that ZMTP binds to
                return "tcp://*:" + Integer.toString(uri.getPort() + 1);
            } catch (URISyntaxException e) {
                throw new MALException("Malformed URI: " + MalUri);
            }
        }
    }

    /**
     * Maps a remote MAL URI to a P2P ZMTP URI
     *
     * @param MalUri MAL URI corresponding to MAL header field 'URI To'
     * @return ZMTP URI the ZMQ P2P channel source socket should connect to
     * @throws MALException if passed MAL URI is invalid
     */
    public String getRemotePtpZmtpUri(String MalUri) throws MALException {
        try {
            URI uri = new URI(MalUri);
            if (uri.getPort() == -1) {
                throw new MALException("Malformed URI (missing port): " + MalUri);
            }
            // The URI that ZMTP connects to
            return "tcp://" + uri.getHost() + ":" + Integer.toString(uri.getPort());
        } catch (URISyntaxException e) {
            throw new MALException("Malformed URI: " + MalUri);
        }
    }

    /**
     * Maps a remote MAL URI to a multicast ZMTP URI (if enabled)
     *
     * @param MalUri MAL URI corresponding to MAL header field 'URI To'
     * @return ZMTP URI the ZMQ multicast channel source socket should connect
     * to, null if multicast is not supported
     * @throws MALException if passed MAL URI is invalid
     */
    public String getRemoteMcastZmtpUri(String MalUri) throws MALException {
        if (!this.useMulticast) {
            return null;
        } else {
            try {
                URI uri = new URI(MalUri);
                if (uri.getPort() == -1) {
                    throw new MALException("Malformed URI (missing port): " + MalUri);
                }
                // The URI that ZMTP connects to
                return "tcp://" + uri.getHost() + ":"
                        + Integer.toString(uri.getPort() + 1);
            } catch (URISyntaxException e) {
                throw new MALException("Malformed URI: " + MalUri);
            }
        }
    }
}
