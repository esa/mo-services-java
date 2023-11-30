/* ----------------------------------------------------------------------------
 * Copyright (C) 2023      European Space Agency
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

import static esa.mo.mal.transport.gen.Transport.LOGGER;
import esa.mo.mal.transport.gen.sending.ConcurrentMessageSender;
import esa.mo.mal.transport.gen.sending.MessageSender;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.DestinationUnknownException;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.transport.MALTransmitErrorException;

/**
 * Holds the outgoing data channels for communications.
 */
public class OutgoingDataChannels {

    /**
     * System property to control the number of connections per client.
     */
    public static final String NUM_CLIENT_CONNS_PROPERTY
            = "org.ccsds.moims.mo.mal.transport.gen.numconnections";

    /**
     * System property to control whether a new outgoing data channel should be
     * created if one dosen't exist when sending a message
     */
    public static final String CONNECT_WHEN_CONSUMER_OFFLINE_PROPERTY
            = "org.ccsds.moims.mo.mal.transport.gen.connectwhenconsumeroffline";

    /**
     * Value of the
     * org.ccsds.moims.mo.mal.transport.gen.connectwhenconsumeroffline property
     */
    private boolean connectWhenConsumerOffline = true;

    /**
     * The number of connections per client or server. The Transport will
     * connect numConnections times to the predefined port and host per
     * different client/server.
     */
    private final int numConnections;
    /**
     * Set of root uris to which the transport tried to connect. Used together
     * with the connectWhenConsumerOffline property to decide if the connection
     * is trying to be established for the first time.
     */
    private final static Set<String> connectionAttempts = new HashSet<>();

    /**
     * Map of outgoing channels. This associates a URI to a transport resource
     * that is able to send messages to this URI.
     */
    private final Map<String, ConcurrentMessageSender> outgoingDataChannels = new HashMap<>();
    private final Transport transport;

    public OutgoingDataChannels(Transport transport, final java.util.Map properties) {
        this.transport = transport;
        int lNumConnections = 1;

        // decode configuration
        if (properties != null) {
            // number of connections per client/server
            if (properties.containsKey(NUM_CLIENT_CONNS_PROPERTY)) {
                lNumConnections = Integer.parseInt((String) properties.get(NUM_CLIENT_CONNS_PROPERTY));
            }

            if (properties.containsKey(CONNECT_WHEN_CONSUMER_OFFLINE_PROPERTY)) {
                connectWhenConsumerOffline = Boolean.parseBoolean((String) properties.get(CONNECT_WHEN_CONSUMER_OFFLINE_PROPERTY));
            }
        }
        this.numConnections = lNumConnections;
    }

    public synchronized void closeConnection(final String localUriTo) {
        ConcurrentMessageSender commsChannel = outgoingDataChannels.get(localUriTo);
        if (commsChannel != null) {
            outgoingDataChannels.remove(localUriTo);
        } else {
            LOGGER.log(Level.WARNING,
                    "Could not locate associated data channel to close "
                    + "communications, perhaps it was closed before. URI : {0} ",
                    localUriTo);
        }
        if (commsChannel != null) {
            // need to do this outside the sync block so 
            // that we do not affect other threads
            commsChannel.terminate();
        }
    }

    public synchronized void closeAll() {
        LOGGER.fine("Closing outgoing channels");
        for (ConcurrentMessageSender sender : outgoingDataChannels.values()) {
            sender.terminate();
        }

        outgoingDataChannels.clear();
        LOGGER.fine("Closed outgoing channels");
    }

    /**
     * Registers a message sender for a given root URI. If this is the first
     * data sender for the URI, it also creates a GENConcurrentMessageSender to
     * manage all the senders. If there are already enough connections
     * (numConnections) to the given URI the method does not register the
     * sender. This ensures that we will have at maximum numConnections to the
     * target root URI.
     *
     * @param dataTransmitter The data sender that is able to send messages to
     * the URI
     * @param remoteRootURI the remote root URI
     * @return returns the GENConcurrentMessageSender for this URI.
     */
    protected synchronized ConcurrentMessageSender registerMessageSender(
            MessageSender dataTransmitter, String remoteRootURI) {
        //check if we already have a communication channel for this URI
        ConcurrentMessageSender dataSender = outgoingDataChannels.get(remoteRootURI);

        if (dataSender != null) {
            //we already have a communication channel for this URI
            //check if we have enough connections for the URI, if not then add the data sender 
            if (dataSender.getNumberOfProcessors() < numConnections) {
                LOGGER.log(Level.FINE, "Registering data sender for URI: {0}", remoteRootURI);
                // insert new processor (message sender) to root data sender for the URI
                dataSender.addProcessor(dataTransmitter, remoteRootURI);
            }
        } else {
            //we do not have a communication channel, create a data sender manager and add the first data sender
            // create new sender manager for this URI
            LOGGER.log(Level.FINE, "Creating data sender manager for URI: {0}", remoteRootURI);
            dataSender = new ConcurrentMessageSender(transport, remoteRootURI);

            LOGGER.log(Level.FINE, "Registering data sender for URI: {0}", remoteRootURI);
            outgoingDataChannels.put(remoteRootURI, dataSender);

            // insert new processor (message sender) to root data sender for the URI
            dataSender.addProcessor(dataTransmitter, remoteRootURI);
        }

        return dataSender;
    }

    public synchronized ConcurrentMessageSender manageCommunicationChannelOutgoing(GENMessage msg,
            String remoteRootURI) throws MALTransmitErrorException {
        // get sender if it exists
        ConcurrentMessageSender sender = outgoingDataChannels.get(remoteRootURI);

        boolean firstTime = !connectionAttempts.contains(remoteRootURI);
        if (firstTime) {
            connectionAttempts.add(remoteRootURI);
        }

        if (sender == null && (connectWhenConsumerOffline || firstTime)) {
            // we do not have any channel for this URI
            // try to create a set of connections to this URI 
            LOGGER.log(Level.INFO, "Establishing connection to: {0}", remoteRootURI);

            try {
                // create new sender for this URI
                MessageSender transmitter = transport.createMessageSender(msg.getHeader(), remoteRootURI);
                sender = this.registerMessageSender(transmitter, remoteRootURI);

                LOGGER.log(Level.FINE, "Opening {0}", numConnections);

                for (int i = 1; i < numConnections; i++) {
                    // insert new processor (message sender) to root data sender for the URI
                    MessageSender anotherTransmitter = transport.createMessageSender(msg.getHeader(), remoteRootURI);
                    sender.addProcessor(anotherTransmitter, remoteRootURI);
                }
            } catch (MALException e) {
                LOGGER.log(Level.WARNING,
                        "Could not connect to: " + remoteRootURI, e);

                throw new MALTransmitErrorException(msg.getHeader(),
                        new DestinationUnknownException(null),
                        null);
            }
        } else if (sender == null && !connectWhenConsumerOffline) {
            LOGGER.log(Level.FINE, "Could not locate an outgoing data channel and "
                    + "the connectWhenConsumerOffline property prevents establishing a new one");
            throw new MALTransmitErrorException(msg.getHeader(),
                    new DestinationUnknownException(null),
                    null);
        }
        return sender;
    }
}
