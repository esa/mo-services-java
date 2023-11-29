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

import esa.mo.mal.encoder.zmtp.header.ZMTPHeaderStreamFactory;
import esa.mo.mal.transport.gen.Endpoint;
import esa.mo.mal.transport.gen.GENMessage;
import esa.mo.mal.transport.gen.PacketToString;
import esa.mo.mal.transport.gen.Transport;
import esa.mo.mal.transport.gen.receivers.IncomingMessageHolder;
import esa.mo.mal.transport.gen.sending.OutgoingMessageHolder;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALTransportFactory;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import esa.mo.mal.transport.gen.sending.MessageSender;
import org.ccsds.moims.mo.mal.structures.NamedValueList;

/**
 * The ZMTP MAL Transport implementation.
 *
 * The following properties configure the transport:
 *
 * org.ccsds.moims.mo.mal.transport.tcpip.wrap
 * org.ccsds.moims.mo.mal.transport.tcpip.debug == debug mode , affects logging
 * org.ccsds.moims.mo.mal.transport.tcpip.numconnections == number of
 * connections to a different MAL (either server / client)
 * org.ccsds.moims.mo.mal.transport.tcpip.inputprocessors == number of threads
 * processing in parallel raw MAL messages
 * org.ccsds.moims.mo.mal.transport.tcpip.host == adapter (host / IP Address)
 * that the transport will use for incoming connections. In case of a pure
 * client (i.e. not offering any services) this property should be omitted.
 * org.ccsds.moims.mo.mal.transport.tcpip.port == port that the transport
 * listens to. In case this is a pure client, this property should be omitted.
 *
 * The general logic is the following : The transport at first initialises the
 * server listen port (if this is a server, offering services).
 *
 * On receiving a request to send a MAL Message the transport tries to find if
 * it has allocated some recourses associated with the target URI (has already
 * the means to exchange data with it) and if not, it creates -numconnections-
 * connections to the target server. If a client has already opened a connection
 * to a server the server will re-use that communication channel to send back
 * data to the client.
 *
 * On the server, each incoming connection is handled separately by a different
 * thread which on the first message reception associates the remote URI with
 * the connection (socket). This has the consequence that if the server wants to
 * either use a service, or reply to the remote URI, it will use on of these
 * already allocated communication resources.
 *
 * In the case of malformed MAL messages or communication errors, all resources
 * related to the remote URI are released and need to be reestablished.
 *
 * URIs:
 *
 * The ZMTP Transport, generates URIs, in the for of :
 * {@code malzmtp://<host>:<port or client ID>/<service id>} There are two
 * categories of URIs Client URIs, which are in the form of
 * {@code malzmtp://<host>:<clientId>/<serviceId>} , where * the client id is a
 * unique identifier for the client on its host, for example :
 * 4783fbc147ab7aa56e7fff and ServerURIs, which are in the form of
 * {@code malzmtp://<host>:<port>-<serviceId>} and clients can actively connect
 * to.
 *
 * If a MAL instance does not offer any services then all of its endpoints get a
 * Client URI. If a MAL instance offers at least one service then all of its
 * endpoints get a Server URI. A service provider communicates with a service
 * consumer with the communication channel that the service consumer initiated
 * (uses bidirectional TCP/IP communication).
 *
 */
public class ZMTPTransport extends Transport<byte[], byte[]> {

    public static final int ZMTP_COMMUNICATION_PATTERN_P2P = 1;
    public static final int ZMTP_COMMUNICATION_PATTERN_MULTICAST = 2;

    protected static final String LOCAL_URI_PROPERTY_KEY
            = "org.ccsds.moims.mo.mal.transport.zmtp.localuri";
    protected static final String MAPPING_DIRECTORY_FILE_KEY
            = "org.ccsds.moims.mo.mal.transport.zmtp.mappingdirectoryfile";
    /**
     * Logger
     */
    public static final java.util.logging.Logger RLOGGER = Logger.getLogger(
            "org.ccsds.moims.mo.mal.transport.zmtp");

    /**
     * Port delimiter
     */
    protected static char PORT_DELIMITER = ':';

    /**
     * The server URI that the channel destination socket is set up on
     */
    protected String localURI;

    /**
     * The server port that the channel destination socket is set up on.
     *
     * Extracted from localURI
     */
    protected int localPort;

    /**
     * Holds ZMQ Context of the binding used to create sockets
     */
    protected ZContext zmqContext;

    /**
     * Holds ZMQ URI mapping implementation class instance
     */
    protected ZMTPURIMapping uriMapping;

    /**
     * Holds ZMTP String Mapping Directory used for MDK encoding/decoding
     */
    public ZMTPStringMappingDirectory stringMappingDirectory = new ZMTPStringMappingDirectory();

    /**
     * Holds default ZMTP Configuration loaded from properties.
     *
     * Can be overloaded per-message basis
     */
    public ZMTPConfiguration defaultConfiguration;

    /**
     * P2P server
     */
    protected ZMTPChannelDestination ptpDest;

    /**
     * MCAST server
     */
    protected ZMTPChannelDestination multicastDest;

    /**
     * Encoder stream factory used to decode/encode the message header.
     */
    protected MALElementStreamFactory hdrStreamFactory;

    /**
     * Selector of encoding for MAL message body transmitted over ZMTP.
     */
    protected ZMTPEncodingSelector bodyEncodingSelector;

    /*
   * Constructor.
   *
   * @param protocol The protocol string.
   * @param supportsRouting True if routing is supported by the naming convention
   * @param wrapBodyParts True is body parts should be wrapped in BLOBs
   * @param factory The factory that created us.
   * @param properties The transport binding properties.
   * @throws MALException On error.
     */
    public ZMTPTransport(final String protocol, final boolean supportsRouting,
            final MALTransportFactory factory, final java.util.Map properties,
            final ZMTPURIMapping uriMapping) throws MALException {
        super(protocol, '/', supportsRouting, false, factory, properties);
        // First assume minimal default config
        defaultConfiguration = new ZMTPConfiguration();

        hdrStreamFactory = new ZMTPHeaderStreamFactory(this);
        bodyEncodingSelector = new ZMTPEncodingSelector();
        bodyEncodingSelector.init(properties);

        this.uriMapping = uriMapping;

        // decode configuration
        if (properties != null) {
            // Load actual default ZMTP mapping & QoS configuration from properties
            defaultConfiguration = new ZMTPConfiguration(defaultConfiguration, properties);
            if (properties.containsKey(MAPPING_DIRECTORY_FILE_KEY)) {
                String mappingDirPath = (String) properties.get(MAPPING_DIRECTORY_FILE_KEY);
                if (!mappingDirPath.isEmpty()) {
                    stringMappingDirectory.loadDirectory(mappingDirPath);
                }
            }
            if (properties.containsKey(LOCAL_URI_PROPERTY_KEY)) {
                this.localURI = (String) properties.get(LOCAL_URI_PROPERTY_KEY);
                try {
                    URI uri = new URI(this.localURI);
                    if (uri.getPort() == -1) {
                        RLOGGER.log(Level.SEVERE,
                                "Malformed URI (missing port): {0}", localURI);
                    } else {
                        this.localPort = uri.getPort();
                    }
                } catch (URISyntaxException e) {
                    RLOGGER.log(Level.SEVERE, "Invalid URI: {0}", localURI);
                }
            } else {
                // default values - select random port
                this.localPort = getRandomSocketPort();
                this.localURI = "malzmtp://localhost:" + this.localPort;
                RLOGGER.log(Level.INFO,
                        "No local URI set. Generated a random URI: {0}", this.localURI);
            }
        } else {
            // default values - select random port
            this.localPort = getRandomSocketPort();
            this.localURI = "malzmtp://localhost:" + this.localPort;
            RLOGGER.log(Level.INFO,
                    "No local URI set. Generated a random URI: {0}", this.localURI);
        }

        RLOGGER.log(Level.INFO,
                "ZMTP Wrapping body parts set to : {0}", this.wrapBodyParts);
    }

    @Override
    public void init() throws MALException {
        super.init();

        zmqContext = new ZContext(4);

        String mappedPtpURI = uriMapping.getLocalPtpZmtpUri(localURI);
        RLOGGER.log(Level.INFO,
                "Starting ZMTP PTP Channel Destination at {0} ({1})",
                new Object[]{localURI, mappedPtpURI});
        try {
            ptpDest = new ZMTPChannelDestination(this, ZMTP_COMMUNICATION_PATTERN_P2P, mappedPtpURI);
            ptpDest.runRxThread();
            RLOGGER.log(Level.INFO,
                    "Started ZMTP PTP Channel Destination at {0} ({1})",
                    new Object[]{localURI, mappedPtpURI});
        } catch (Exception ex) {
            throw new MALException("Error initialising ZMTP PTP Channel Destination", ex);
        }
        String mappedMcastURI = uriMapping.getLocalMcastZmtpUri(localURI);
        if (mappedMcastURI != null) {
            RLOGGER.log(Level.INFO, "Starting ZMTP MCAST Channel Destination at {0} ({1})",
                    new Object[]{localURI, mappedMcastURI});
            try {
                multicastDest = new ZMTPChannelDestination(this,
                        ZMTP_COMMUNICATION_PATTERN_MULTICAST, mappedMcastURI);
                multicastDest.runRxThread();
                RLOGGER.log(Level.INFO,
                        "Started ZMTP MCAST Channel Destination at {0} ({1})",
                        new Object[]{localURI, mappedMcastURI});
            } catch (Exception ex) {
                throw new MALException("Error initialising ZMTP MCAST Channel Destination", ex);
            }
        } else {
            RLOGGER.log(Level.INFO,
                    "No ZMTP MCAST Channel Destination mapped from {0}", localURI);
        }

    }

    @Override
    public MALBrokerBinding createBroker(final String localName, final Blob authenticationId,
            final QoSLevel[] expectedQos, final UInteger priorityLevelNumber,
            final Map defaultQoSProperties) throws MALException {
        // not supported by ZMTP transport
        return null;
    }

    @Override
    public MALBrokerBinding createBroker(final MALEndpoint endpoint, final Blob authenticationId,
            final QoSLevel[] qosLevels, final UInteger priorities, final Map properties) throws
            MALException {
        // not supported by ZMTP transport
        return null;
    }

    @Override
    public boolean isSupportedInteractionType(final InteractionType type) {
        // Supports all IPs
        return true;
    }

    @Override
    public boolean isSupportedQoSLevel(final QoSLevel qos) {
        // The underlying TCP transport only supports BESTEFFORT
        // It has to be kept in mind that ZMQ can use other transports and it is
        // configurable by URI mapping, so this method can be improved

        return qos == QoSLevel.BESTEFFORT;
    }

    @Override
    public void close() throws MALException {
        super.close();

        if (null != ptpDest) {
            ptpDest.interrupt();
        }
        if (null != multicastDest) {
            multicastDest.interrupt();
        }
        if (null != this.getZmqContext()) {
            // Don't destroy the context properly as it locks the application
            //this.getZmqContext().destroy();
        }
    }

    @Override
    protected String createTransportAddress() throws MALException {
        // Return channel destination URI
        return getDefaultHost() + PORT_DELIMITER + localPort;
    }

    @Override
    protected MessageSender createMessageSender(GENMessage msg,
            String remoteRootURI) throws MALException {
        return createMessageSender(remoteRootURI);
    }

    public synchronized ZMTPChannelSource createMessageSender(
            String remoteRootURI) throws MALException {
        try {
            String mappedRemoteURI = uriMapping.getRemotePtpZmtpUri(remoteRootURI);
            ZMQ.Socket socket = openSocket(getZmqContext(),
                    ZMTP_COMMUNICATION_PATTERN_P2P, mappedRemoteURI, false);
            return new ZMTPChannelSource(socket);
        } catch (IllegalArgumentException e) {
            RLOGGER.log(Level.WARNING,
                    "Malformed parameters when creating sender to : {0}", remoteRootURI);
            throw new MALException("Malformed parameters when creating sender to : " + remoteRootURI);
        } catch (Exception e) {
            RLOGGER.log(Level.WARNING, "Exception" + e.toString()
                    + " when creating sender to : {0}", remoteRootURI);
            throw new MALException("Exception " + e.toString()
                    + " when creating sender to : " + remoteRootURI);
        }
    }

    @Override
    public GENMessage createMessage(byte[] packet) throws MALException {
        // Default configuration (loaded from transport properties) is used for decoding
        ZMTPMessageHeader header = new ZMTPMessageHeader(
                new ZMTPConfiguration(defaultConfiguration, qosProperties), null);
        ZMTPMessage dummyMessage = new ZMTPMessage(hdrStreamFactory, wrapBodyParts,
                true, header, qosProperties, packet, hdrStreamFactory);

        // now full message including body
        try {
            return new ZMTPMessage(hdrStreamFactory, wrapBodyParts, false,
                    (MALMessageHeader) dummyMessage.getHeader(), qosProperties,
                    dummyMessage.getBody().getEncodedBody().getEncodedBody().getValue(),
                    getBodyEncodingSelector().getDecoderStreamFactory(header));
        } catch (MALException ex) {
            returnErrorMessage(dummyMessage, MALHelper.INTERNAL_ERROR_NUMBER,
                    "The message body could not be decoded. The message will be discarded!");

            return null;
        }
    }

    @Override
    protected OutgoingMessageHolder<byte[]> internalEncodeMessage(
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
                internalEncodeByteMessage(msg));
    }

    protected MALElementStreamFactory getHeaderStreamFactory() {
        return hdrStreamFactory;
    }

    @Override
    protected Endpoint internalCreateEndpoint(final String localName,
            final String routingName, final Map properties, NamedValueList supplements) throws MALException {
        return new ZMTPEndpoint(this, defaultConfiguration, localName,
                routingName, uriBase + routingName, wrapBodyParts, properties);
    }

    /**
     * Provide a default IP address for this host
     *
     * @return The transport specific address part.
     * @throws MALException On error
     */
    private String getDefaultHost() throws MALException {
        try {
            final InetAddress addr = Inet4Address.getLocalHost();
            final StringBuilder hostAddress = new StringBuilder();
            if (addr instanceof Inet6Address) {
                RLOGGER.fine("ZMTP Address class is IPv6");
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

    public void channelDataReceived(byte[] remoteIdentity, byte[] data) {
        try {
            PacketToString smsg = new PacketToString(data);
            GENMessage malMsg = this.createMessage(data);
            this.receive(null, new IncomingMessageHolder(malMsg, smsg));
        } catch (MALException ex) {
            Logger.getLogger(ZMTPTransport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the bodyEncodingSelector
     */
    public ZMTPEncodingSelector getBodyEncodingSelector() {
        return bodyEncodingSelector;
    }

    /**
     * This method returns a random port number to be used for differentiating
     * different MAL instances in the same host.
     *
     * @return the random, host unique port number
     */
    private int getRandomSocketPort() {
        // By default the server ports will start on the 1024 range, So we can
        // exclude the first 1000 range from being hit by nasty randomness
        int min = 2024;
        int max = 65536;
        return new Random().nextInt(max - min) + min;
    }

    /**
     * Implementation of ZMTP binding OPEN primitive
     *
     * @param ctxt context to create the socket from
     * @param communicationPattern if P2P, ROUTER-DEALER sockets are created, if
     * multicast, PUB-SUB sockets are created
     * @param zmtpURI valid ZMTP URI
     * @param asServer if true, the socket binds to the given URI, listening for
     * connections, if false the socket connects to the given URI
     * @return ZMQ Socket instance configured according to the parameters
     */
    public static ZMQ.Socket openSocket(ZContext ctxt, int communicationPattern,
            String zmtpURI, boolean asServer) throws IllegalArgumentException {
        ZMQ.Socket ret = null;
        if (zmtpURI == null) {
            throw new IllegalArgumentException();
        }
        if (asServer) {
            switch (communicationPattern) {
                case ZMTPTransport.ZMTP_COMMUNICATION_PATTERN_P2P:
                    ret = ctxt.createSocket(ZMQ.ROUTER);
                    break;
                case ZMTPTransport.ZMTP_COMMUNICATION_PATTERN_MULTICAST:
                    ret = ctxt.createSocket(ZMQ.SUB);
                    // Subscribe to everything coming to that socket
                    ret.subscribe("".getBytes());
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            ret.bind(zmtpURI);
        } else {
            switch (communicationPattern) {
                case ZMTP_COMMUNICATION_PATTERN_P2P:
                    ret = ctxt.createSocket(ZMQ.DEALER);
                    break;
                case ZMTP_COMMUNICATION_PATTERN_MULTICAST:
                    ret = ctxt.createSocket(ZMQ.PUB);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            ret.connect(zmtpURI);
        }
        return ret;
    }

    /**
     * @return the zmqContext
     */
    public ZContext getZmqContext() {
        return zmqContext;
    }

}
