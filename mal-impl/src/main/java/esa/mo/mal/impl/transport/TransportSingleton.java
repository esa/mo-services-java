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
package esa.mo.mal.impl.transport;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALTransport;
import org.ccsds.moims.mo.mal.transport.MALTransportFactory;

/**
 * The Transport singleton class stores Transport factories and Transport
 * objects to speed creation of Transport objects.
 */
public final class TransportSingleton {

    /**
     * Map of transport factories currently used by the application
     */
    private static final Map<String, MALTransportFactory> FACTORY_MAP =
            new HashMap<>();
    /**
     * Map of transport handlers currently used by the application
     */
    private static final Map<String, MALTransport> TRANSPORT_MAP =
            new HashMap<>();
    /**
     * The default protocol to be used by the provider
     */
    private static String defaultProtocol = null;

    private TransportSingleton() {
        // make default constructor private so cannot 
        // instatiate this class as it is a singleton.
    }

    /**
     * Initialises the transport singleton.
     */
    public static void init() {
        synchronized (TRANSPORT_MAP) {
            if (null == defaultProtocol) {
                String dp = System.getProperty("org.ccsds.moims.mo.mal.transport.default.protocol");
                if (null == dp) {
                    dp = "rmi://";
                }

                defaultProtocol = dp;
            }
        }
    }

    /**
     * Creates an instance of a Transport.
     *
     * @param dstUri The Uri.
     * @param properties QoS properties.
     * @return The transport handler.
     * @throws MALException on error.
     */
    public static MALTransport instance(final URI dstUri, final Map properties) throws MALException {
        init();

        if ((null != dstUri) && (null != dstUri.getValue())) {
            return internalInstance(dstUri.getValue(), properties);
        }

        return internalInstance(defaultProtocol, properties);
    }

    /**
     * Creates an instance of a Transport.
     *
     * @param dstUri The Uri.
     * @param properties QoS properties.
     * @return The transport handler.
     * @throws MALException on error.
     */
    public static MALTransport instance(final String dstUri, final Map properties) throws MALException {
        init();

        if (null != dstUri) {
            return internalInstance(dstUri, properties);
        }

        return internalInstance(defaultProtocol, properties);
    }

    /**
     * Check to see if a supplied URI would use the supplied Transport.
     *
     * @param dstUri The Uri.
     * @param transport The Transport to check.
     * @return Returns true if dstUri would create the same transport.
     */
    public static boolean isSameTransport(final URI dstUri, final MALTransport transport) {
        init();

        if ((null != dstUri) && (null != dstUri.getValue())) {
            return isSameTransport(dstUri.getValue(), transport);
        }

        return false;
    }

    /**
     * Check to see if a supplied URI would use the supplied Transport.
     *
     * @param dstUri The Uri.
     * @param transport The Transport to check.
     * @return Returns true if dstUri would create the same transport.
     */
    public static boolean isSameTransport(final String dstUri, final MALTransport transport) {
        init();

        if (null != dstUri) {
            // lookup for existing transport
            MALTransport existingTransport;

            synchronized (TRANSPORT_MAP) {
                existingTransport = TRANSPORT_MAP.get(getProtocol(dstUri));
            }

            return transport == existingTransport;
        }

        return false;
    }

    /**
     * Creates an instance of a Transport.
     *
     * @param dstUri The Uri.
     * @param properties QoS properties.
     * @return The transport handler.
     * @throws MALException on error.
     */
    private static MALTransport internalInstance(final String dstUri, 
            final Map properties) throws MALException {
        // get protocol from uri
        final String strProtocol = getProtocol(dstUri);

        // lookup for existing transport
        MALTransport transport;

        synchronized (TRANSPORT_MAP) {
            transport = TRANSPORT_MAP.get(strProtocol);
        }

        if (null == transport) {
            // lookup for existing handler else create new one and add to map
            MALTransportFactory ohandler = FACTORY_MAP.get(strProtocol);
            if (null == ohandler) {
                ohandler = MALTransportFactory.newFactory(strProtocol);

                if (null != ohandler) {
                    FACTORY_MAP.put(strProtocol, ohandler);
                } else {
                    throw new MALException("DESTINATION_UNKNOWN_ERROR_NUMBER");
                }
            }

            transport = ohandler.createTransport(null, properties);

            if (null != transport) {
                synchronized (TRANSPORT_MAP) {
                    TRANSPORT_MAP.put(strProtocol, transport);
                }

                // check QoS support
                transport.isSupportedQoSLevel(QoSLevel.BESTEFFORT);
                transport.isSupportedQoSLevel(QoSLevel.ASSURED);
                transport.isSupportedQoSLevel(QoSLevel.TIMELY);
                transport.isSupportedQoSLevel(QoSLevel.QUEUED);

                // check IP support
                transport.isSupportedInteractionType(InteractionType.SEND);
                transport.isSupportedInteractionType(InteractionType.SUBMIT);
                transport.isSupportedInteractionType(InteractionType.REQUEST);
                transport.isSupportedInteractionType(InteractionType.INVOKE);
                transport.isSupportedInteractionType(InteractionType.PROGRESS);
                transport.isSupportedInteractionType(InteractionType.PUBSUB);
            }
        }

        return transport;
    }

    /**
     * Closes the singleton and closes any open transports.
     *
     * @throws MALException If an error is detected closing the transports.
     */
    public static void close() throws MALException {
        StringBuilder exceptionList = new StringBuilder();
        synchronized (TRANSPORT_MAP) {
            for (Entry<String, MALTransport> obj : TRANSPORT_MAP.entrySet()) {
                try {
                    obj.getValue().close();
                } catch (MALException ex) {
                    // there was a problem, record it and carry on
                    exceptionList.append(" : ");
                    exceptionList.append(ex.getMessage());
                }
            }

            TRANSPORT_MAP.clear();
            FACTORY_MAP.clear();
        }

        if (0 != exceptionList.length()) {
            // now we can throw the exceptions we caught earlier.
            throw new MALException("Error during closing of transports" + exceptionList);
        }
    }

    private static String getProtocol(String dstUri) {
        // get protocol from uri
        final int iPro = dstUri.indexOf(':');
        if (-1 != iPro) {
            dstUri = dstUri.substring(0, iPro);
        }

        return dstUri;
    }
}
