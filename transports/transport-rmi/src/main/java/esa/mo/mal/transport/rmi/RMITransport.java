/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO RMI Transport
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
package esa.mo.mal.transport.rmi;

import esa.mo.mal.transport.gen.GENMessage;
import esa.mo.mal.transport.gen.Transport;
import esa.mo.mal.transport.gen.body.LazyMessageBody;
import esa.mo.mal.transport.gen.sending.MessageSender;
import esa.mo.mal.transport.gen.sending.OutgoingMessageHolder;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.DeliveryFailedException;
import org.ccsds.moims.mo.mal.DestinationUnknownException;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALTransmitErrorException;

/**
 * An implementation of the transport interface for the RMI protocol.
 */
public class RMITransport extends Transport<byte[], byte[]> {

    /**
     * Logger
     */
    public static final java.util.logging.Logger RLOGGER = Logger.getLogger(
            "org.ccsds.moims.mo.mal.transport.rmi");
    /**
     * System property to set the host name used locally.
     */
    public static final String RMI_HOSTNAME_PROPERTY = "org.ccsds.moims.mo.mal.transport.rmi.host";
    private static final char RMI_PORT_DELIM = ':';
    private final String serverHost;
    private Registry registry;
    private int portNumber;
    private UnicastRemoteObject ourRMIinterface;

    /**
     * Constructor.
     *
     * @param protocol The protocol string.
     * @param properties The QoS properties.
     * @throws MALException On error.
     */
    public RMITransport(final String protocol, final java.util.Map properties) throws MALException {
        super(protocol, '-', true, properties);

        String lhost = null;

        if ((properties != null) && (properties.containsKey(RMI_HOSTNAME_PROPERTY))) {
            lhost = (String) properties.get(RMI_HOSTNAME_PROPERTY);
        }

        this.serverHost = lhost;
    }

    @Override
    public void init() throws MALException {
        // Port numbers above 1023 are up for grabs on any machine....
        int iRmiPort = 1024;
        while (true) {
            try {
                registry = java.rmi.registry.LocateRegistry.createRegistry(iRmiPort);
                // Got a valid port number, lets get out of here...
                break;
            } catch (RemoteException e) {
                // Port already in use, lets try the next one...
                ++iRmiPort;
            }
        }

        portNumber = iRmiPort;
        RLOGGER.log(Level.FINE, "RMI Creating registry on port {0}", portNumber);

        super.init();

        try {
            ourRMIinterface = new RMIReceiveImpl(this);
            registry.rebind(String.valueOf(portNumber), ourRMIinterface);
            RLOGGER.log(Level.INFO, "RMI Bound to registry on port {0}", portNumber);
        } catch (RemoteException ex) {
            throw new MALException("Error initialising RMI connection", ex);
        }
    }

    @Override
    protected String createTransportAddress() throws MALException {
        final StringBuilder transportAddress = new StringBuilder();

        transportAddress.append(getHostName(serverHost));
        transportAddress.append(RMI_PORT_DELIM);
        transportAddress.append(portNumber);
        transportAddress.append('/');
        transportAddress.append(portNumber);

        return transportAddress.toString();
    }

    @Override
    public MALBrokerBinding createBroker(final String localName,
            final Blob authenticationId,
            final QoSLevel[] expectedQos,
            final UInteger priorityLevelNumber,
            final Map defaultQoSProperties) throws MALException {
        // not support by RMI transport
        return null;
    }

    @Override
    public MALBrokerBinding createBroker(final MALEndpoint endpoint,
            final Blob authenticationId,
            final QoSLevel[] qosLevels,
            final UInteger priorities,
            final Map properties) throws MALException {
        // not support by RMI transport
        return null;
    }

    @Override
    public boolean isSupportedInteractionType(final InteractionType type) {
        // Supports all IPs except Pub Sub
        return InteractionType.PUBSUB.getValue() != type.getValue();
    }

    @Override
    public boolean isSupportedQoSLevel(final QoSLevel qos) {
        // The transport only supports BESTEFFORT in reality but this is only 
        // a test transport so we say it supports all
        return true;
    }

    @Override
    public void close() throws MALException {
        super.close();

        try {
            registry.unbind(String.valueOf(portNumber));
            UnicastRemoteObject.unexportObject(ourRMIinterface, true);
            UnicastRemoteObject.unexportObject(registry, true);
        } catch (java.rmi.NotBoundException ex) {
            // NoOp
        } catch (RemoteException ex) {
            // NoOp
        }

        registry = null;
        ourRMIinterface = null;
    }

    @Override
    protected MessageSender<byte[]> createMessageSender(MALMessageHeader msgHeader,
            String remoteRootURI) throws MALException, MALTransmitErrorException {
        RLOGGER.log(Level.FINE,
                "RMI received request to create connections to URI: {0}", remoteRootURI);

        try {
            return new RMIMessageSender(remoteRootURI); // create new sender for this URI
        } catch (NotBoundException e) {
            RLOGGER.log(Level.WARNING, "(1) RMI could not connect to: " + remoteRootURI, e);
            throw new MALTransmitErrorException(msgHeader,
                    new DestinationUnknownException(null),
                    null);
        } catch (IOException e) {
            RLOGGER.log(Level.WARNING, "(2) RMI could not connect to: " + remoteRootURI, e);
            throw new MALTransmitErrorException(msgHeader,
                    new DeliveryFailedException(null),
                    null);
        }
    }

    @Override
    public GENMessage decodeMessage(byte[] packet) throws MALException {
        MALElementStreamFactory encFactory = getStreamFactory();
        final ByteArrayInputStream bais = new ByteArrayInputStream(packet);
        final MALElementInputStream enc = encFactory.createInputStream(bais);

        MALMessageHeader header = enc.readHeader(new MALMessageHeader());
        LazyMessageBody lazyBody = LazyMessageBody.createMessageBody(header, encFactory, enc);
        return new GENMessage(header, lazyBody, encFactory, qosProperties);
    }

    @Override
    protected OutgoingMessageHolder<byte[]> encodeMessage(
            String destinationRootURI,
            String destinationURI,
            Object multiSendHandle,
            boolean lastForHandle,
            String targetURI,
            GENMessage msg) throws Exception {
        return new OutgoingMessageHolder<byte[]>(10,
                destinationRootURI,
                destinationURI,
                multiSendHandle,
                lastForHandle,
                msg,
                msg.internalEncodeByteMessage()
        );
    }

    /**
     * Provide an IP address for this host
     *
     * @param preferredHostname The preferred host name, may be NULL in which
     * case the name will be calculated.
     * @return The transport specific address part.
     * @throws MALException On error
     */
    private static String getHostName(String preferredHostname) throws MALException {
        if (preferredHostname == null) {
            try {
                // Build RMI url string
                final InetAddress addr = Inet4Address.getLocalHost();
                final StringBuilder hostAddress = new StringBuilder();
                if (addr instanceof Inet6Address) {
                    RLOGGER.fine("RMI Address class is IPv6");
                    hostAddress.append('[');
                    hostAddress.append(addr.getHostAddress());
                    hostAddress.append(']');
                } else {
                    hostAddress.append(addr.getHostAddress());
                }

                return hostAddress.toString();
            } catch (UnknownHostException ex) {
                throw new MALException("Could not determine local host address", ex);
            }
        }

        return preferredHostname;
    }
}
