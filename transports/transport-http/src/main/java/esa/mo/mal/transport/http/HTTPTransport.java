/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Transport - HTTP
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
package esa.mo.mal.transport.http;

import esa.mo.mal.transport.gen.GENMessage;
import esa.mo.mal.transport.gen.PacketToString;
import esa.mo.mal.transport.gen.Transport;
import esa.mo.mal.transport.gen.sending.MessageSender;
import esa.mo.mal.transport.gen.sending.OutgoingMessageHolder;
import esa.mo.mal.transport.http.api.IHttpResponse;
import esa.mo.mal.transport.http.api.IHttpServer;
import esa.mo.mal.transport.http.receiving.HTTPContextHandlerNoEncoding;
import esa.mo.mal.transport.http.receiving.HTTPContextHandlerNoResponse;
import esa.mo.mal.transport.http.receiving.HTTPContextHandlerRequestResponse;
import esa.mo.mal.transport.http.receiving.HTTPHeaderAndBody;
import esa.mo.mal.transport.http.sending.HTTPMessageSenderNoEncoding;
import esa.mo.mal.transport.http.sending.HTTPMessageSenderNoResponse;
import esa.mo.mal.transport.http.sending.HTTPMessageSenderRequestResponse;
import esa.mo.mal.transport.http.util.HttpApiImplException;
import esa.mo.mal.transport.http.util.StatusCodeHelper;
import java.io.ByteArrayOutputStream;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInvokeOperation;
import org.ccsds.moims.mo.mal.MALProgressOperation;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALTransmitErrorException;
import org.ccsds.moims.mo.mal.transport.MALTransportFactory;

/**
 * An implementation of the transport interface for the HTTP protocol.
 */
public class HTTPTransport extends Transport<HTTPHeaderAndBody, byte[]> {

    /**
     * Always use HTTP requests to deliver MAL messages (the HTTP response is
     * "204 No Content" on all occasions), but do not encode the MAL message
     * header separately via HTTP headers
     */
    private static final int HTTP_BINDING_MODE_NO_ENCODING = 0;

    /**
     * Always use HTTP requests to deliver MAL messages (the HTTP response is
     * "204 No Content" on all occasions), and additionally encode the MAL
     * message header separately via HTTP headers
     */
    private static final int HTTP_BINDING_MODE_NO_RESPONSE = 1;

    /**
     * Use HTTP request/response paradigm to map as many interaction type
     * operations as possible for the remaining operations HTTP requests go in
     * the other direction (see Table 3-3 in recommended standard),
     */
    private static final int HTTP_BINDING_MODE_REQUEST_RESPONSE = 2;

    /**
     * Define the default encoder which is used in combination with this
     * transport. The actually used encoder can be set in the properties file of
     * the transport binding. This constant is merely used as reference
     */
    public static final String HTTP_DEFAULT_XML_ENCODER = "esa.mo.mal.encoder.xml.XMLStreamFactory";

    /**
     * The default request/response timeout in seconds. HTTP requests that take
     * longer than the timeout are rejected.
     */
    public static final int HTTP_DEFAULT_TIMEOUT = 60;

    /**
     *
     */
    public static final String TIMESTAMP_STRING_FORMAT = "yyyy-DDD'T'HH:mm:ss.SSS";

    /**
     * The selected HTTP binding mode
     */
    private final int selectedHttpBindingMode;

    /**
     * Whether the communication should be secured via SSL/TLS
     */
    private final boolean useHttps;

    /**
     * The Java KeyStore filename
     */
    private final String keystoreFilename;

    /**
     * The Java KeyStore password
     */
    private final String keystorePassword;

    /**
     * Logger
     */
    public static final Logger RLOGGER = Logger.getLogger("org.ccsds.moims.mo.mal.transport.http");

    /**
     * Port delimiter
     */
    private static final char PORT_DELIMITER = ':';

    /**
     * Server host, this can be one of the IP Addresses / hostnames of the host
     */
    private final String host;

    /**
     * The port that the HTTP transport listens for incoming connections
     */
    private final int port;

    /**
     * Holds the HTTP server
     */
    private IHttpServer server = null;

    /**
     * the AbstractHttpServer interface implementation
     */
    private final String abstractServerImpl;

    /**
     * the AbstractPostClient interface implementation
     */
    private final String abstractPostClientImpl;

    private int timeout = 0;

    /**
     * Store for mappings from URI+transactionId to open HTTP response for
     * messages where a subsequent MAL message is expected after submitting the
     * HTTP request and needs to be encoded and passed within the HTTP response
     */
    private final Map<String, IHttpResponse> openHttpResponses = Collections
            .synchronizedMap(new HashMap<String, IHttpResponse>());

    /**
     * This Executor can be used to run submitted tasks asynchronously (e.g.
     * deferred processing the HTTP response after sending the HTTP request)
     */
    private final ExecutorService executor = Executors.newCachedThreadPool();

    private static boolean aliasesLoaded = false;

    /**
     * Maps host aliases to their ip
     */
    private static final Map<String, String> aliasToIp = new ConcurrentHashMap<>();

    /**
     * Constructor.
     *
     * @param protocol The protocol string.
     * @param serviceDelim The delimiter to use for separating the URL
     * @param supportsRouting True if routing is supported by the naming
     * convention
     * @param factory The factory that created us.
     * @param properties The QoS properties.
     * @throws MALException On error.
     */
    public HTTPTransport(final String protocol, final char serviceDelim, final boolean supportsRouting,
            final MALTransportFactory factory, final Map properties) throws MALException {

        super(protocol, serviceDelim, supportsRouting, false, factory, properties);

        // decode configuration
        if (properties != null) {
            loadHostAliases(properties);
            // host / ip adress
            if (properties.containsKey("org.ccsds.moims.mo.mal.transport.http.host")) {
                String hostName = (String) properties.get("org.ccsds.moims.mo.mal.transport.http.host");
                try {
                    this.host = InetAddress.getByName(hostName).getHostAddress();
                } catch (UnknownHostException ex) {
                    RLOGGER.log(Level.WARNING, "Cannot convert server hostname from properties file to IP address", ex);
                    throw new MALException("Cannot convert server hostname from properties file to IP address", ex);
                }
            } else {
                this.host = getDefaultHost();
            }

            // port
            if (properties.containsKey("org.ccsds.moims.mo.mal.transport.http.port")) {
                try {
                    this.port = Integer.parseInt((String) properties.get("org.ccsds.moims.mo.mal.transport.http.port"));
                } catch (NumberFormatException ex) {
                    RLOGGER.log(Level.WARNING, "Cannot parse server port number from properties file to Integer", ex);
                    throw new MALException("Cannot parse server port number from properties file to Integer", ex);
                }
            } else {
                // use default port
                this.port = getRandomClientPort();
            }

            if (properties.containsKey("org.ccsds.moims.mo.mal.transport.http.serverimpl")) {
                this.abstractServerImpl = (String) properties.get("org.ccsds.moims.mo.mal.transport.http.serverimpl");
            } else {
                this.abstractServerImpl = "esa.mo.mal.transport.http.connection.JdkServer";
            }

            if (properties.containsKey("org.ccsds.moims.mo.mal.transport.http.clientimpl")) {
                this.abstractPostClientImpl = (String) properties
                        .get("org.ccsds.moims.mo.mal.transport.http.clientimpl");
            } else {
                this.abstractPostClientImpl = "esa.mo.mal.transport.http.connection.JdkClient";
            }

            // binding mode
            if (properties.containsKey("org.ccsds.moims.mo.mal.transport.http.bindingmode")) {
                String bindingMode = (String) properties.get("org.ccsds.moims.mo.mal.transport.http.bindingmode");
                if (bindingMode.equals("NoEncoding")) {
                    this.selectedHttpBindingMode = HTTP_BINDING_MODE_NO_ENCODING;
                } else if (bindingMode.equals("NoResponse")) {
                    this.selectedHttpBindingMode = HTTP_BINDING_MODE_NO_RESPONSE;
                } else // bindingMode.equals("RequestResponse")
                {
                    this.selectedHttpBindingMode = HTTP_BINDING_MODE_REQUEST_RESPONSE;
                }
            } else {
                this.selectedHttpBindingMode = HTTP_BINDING_MODE_REQUEST_RESPONSE;
            }

            // use https?
            if (properties.containsKey("org.ccsds.moims.mo.mal.transport.http.usehttps")) {
                String secure = (String) properties.get("org.ccsds.moims.mo.mal.transport.http.usehttps");
                if (secure.equals("true")) {
                    this.useHttps = true;
                    this.keystoreFilename = (String) properties
                            .get("org.ccsds.moims.mo.mal.transport.http.keystore.filename");
                    this.keystorePassword = (String) properties
                            .get("org.ccsds.moims.mo.mal.transport.http.keystore.password");
                } else {
                    this.useHttps = false;
                    this.keystoreFilename = null;
                    this.keystorePassword = null;
                }
            } else {
                this.useHttps = false;
                this.keystoreFilename = null;
                this.keystorePassword = null;
            }

            // debug mode
            Level parsedLevel = null;
            if (properties.containsKey("org.ccsds.moims.mo.mal.transport.http.debug")) {
                Object level = properties.get("org.ccsds.moims.mo.mal.transport.http.debug");

                if (level != null) {
                    try {
                        ConsoleHandler cHandler = new ConsoleHandler();
                        parsedLevel = Level.parse(level.toString());
                        RLOGGER.setLevel(parsedLevel);
                        cHandler.setLevel(parsedLevel);
                        RLOGGER.info("Setting logger to level " + RLOGGER.getLevel());
                        RLOGGER.addHandler(cHandler);
                    } catch (IllegalArgumentException ex) {
                        RLOGGER.log(Level.WARNING,
                                "The debug level supplied by the parameter"
                                + "org.ccsds.moims.mo.mal.transport.tcpip.debug does not exist!"
                                + "Please provide a java-logging compatible debug level.");
                    }
                }
            }

            // timeout
            this.timeout = HTTP_DEFAULT_TIMEOUT;
            if (properties.containsKey("org.ccsds.moims.mo.mal.transport.http.timeout")) {
                String timeoutProperty = (String) properties.get("org.ccsds.moims.mo.mal.transport.http.timeout");

                if (timeoutProperty != null && !timeoutProperty.isEmpty()) {
                    this.timeout = Integer.parseInt(timeoutProperty);
                }
            }
        } else {
            this.host = getDefaultHost();
            this.port = getRandomClientPort();
            this.abstractServerImpl = "esa.mo.mal.transport.http.connection.JdkServer";
            this.abstractPostClientImpl = "esa.mo.mal.transport.http.connection.JdkClient";
            this.selectedHttpBindingMode = HTTP_BINDING_MODE_REQUEST_RESPONSE;
            this.useHttps = false;
            this.keystoreFilename = null;
            this.keystorePassword = null;
            this.timeout = HTTP_DEFAULT_TIMEOUT;
        }

        RLOGGER.log(Level.INFO, "HTTP Host/port: " + this.host + ":" + this.port);
        RLOGGER.log(Level.INFO, "HTTP Wrapping body parts set to : {0}", this.wrapBodyParts);
        String bindingMode = "Request-response";
        if (this.selectedHttpBindingMode != HTTP_BINDING_MODE_REQUEST_RESPONSE) {
            if (this.selectedHttpBindingMode == HTTP_BINDING_MODE_NO_RESPONSE) {
                bindingMode = "No-response";
            } else {
                bindingMode = "No-encoding";
            }
        }
        RLOGGER.info("HTTP Selected binding mode: " + bindingMode);
        RLOGGER.info("HTTP Timeout: " + this.timeout);
    }

    private static void loadHostAliases(Map properties) {
        if (aliasesLoaded) {
            return;
        }

        int index = 0;
        while (true) {
            // remoteUri@alias@routedUri
            String ALIAS_PROPERTY_NAME = String.format("org.ccsds.moims.mo.mal.transport.http.hostalias.%d", index);
            index += 1;

            String property = (String) properties.get(ALIAS_PROPERTY_NAME);
            if (property != null && !property.isEmpty()) {
                String[] split = property.split("@");
                String alias = split[0];
                String routedUri = split[1].equals("localhost") ? "127.0.0.1" : split[1];
                aliasToIp.put(alias, routedUri);
            } else {
                break;
            }
        }

        aliasesLoaded = true;
    }

    @Override
    public void init() throws MALException {
        super.init();

        // this is always a server (i.e. listens for incoming connections)
        RLOGGER.log(Level.INFO, "Starting HTTP Transport on port {0} with useHttps set to {1}",
                new Object[]{port, useHttps});

        // start HTTP server on predefined port / interface
        try {
            InetAddress serverHostAddr = InetAddress.getByName(host);
            InetSocketAddress serverSocket = new InetSocketAddress(serverHostAddr, port);

            server = createServer();
            server.initServer(serverSocket, useHttps, keystoreFilename, keystorePassword);
            if (selectedHttpBindingMode == HTTP_BINDING_MODE_NO_ENCODING) {
                server.addContextHandler(new HTTPContextHandlerNoEncoding(this));
            } else if (selectedHttpBindingMode == HTTP_BINDING_MODE_NO_RESPONSE) {
                server.addContextHandler(new HTTPContextHandlerNoResponse(this));
            } else // selectedHttpBindingMode ==
            // HTTP_BINDING_MODE_REQUEST_RESPONSE
            {
                server.addContextHandler(new HTTPContextHandlerRequestResponse(this));
            }

            // create thread that will listen for incoming connections
            synchronized (this) {
                server.startServer();
            }

            RLOGGER.log(Level.INFO, "Started HTTP Server Transport on port {0} with useHttps set to {1}",
                    new Object[]{port, useHttps});
        } catch (UnknownHostException ex) {
            throw new MALException("Error initialising HTTP Server (UnknownHostException)", ex);
        } catch (HttpApiImplException ex) {
            throw new MALException("Error initialising HTTP Server (HttpApiImplException)", ex);
        }
    }

    /**
     * Creates an instance of the AbstractHttpServer interface.
     *
     * @return the AbstractHttpServer implementation
     * @throws HttpApiImplException in case an error occurs when trying to
     * instantiate the AbstractHttpServer
     */
    protected IHttpServer createServer() throws HttpApiImplException {
        try {
            IHttpServer serverImpl = (IHttpServer) Class.forName(abstractServerImpl).newInstance();
            return serverImpl;
        } catch (ClassNotFoundException ex) {
            throw new HttpApiImplException("HTTPTransport: ClassNotFoundException at createServer()", ex);
        } catch (InstantiationException ex) {
            throw new HttpApiImplException("HTTPTransport: InstantiationException at createServer()", ex);
        } catch (IllegalAccessException ex) {
            throw new HttpApiImplException("HTTPTransport: IllegalAccessException at createServer()", ex);
        }
    }

    @Override
    public MALBrokerBinding createBroker(String localName, Blob authenticationId, QoSLevel[] expectedQos,
            UInteger priorityLevelNumber, Map defaultQosProperties) throws IllegalArgumentException, MALException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MALBrokerBinding createBroker(MALEndpoint endpoint, Blob authenticationId, QoSLevel[] expectedQos,
            UInteger priorityLevelNumber, Map defaultQosProperties) throws IllegalArgumentException, MALException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isSupportedInteractionType(InteractionType type) {
        // Supports all IPs except PubSub
        return InteractionType.PUBSUB.getOrdinal() != type.getOrdinal();
    }

    @Override
    public boolean isSupportedQoSLevel(QoSLevel qos) {
        // The transport only supports BESTEFFORT in reality
        // but this is only a test transport so we say it supports all
        return true;
        // TODO should be like this ??
        // return QoSLevel.BESTEFFORT.equals(qos);
        // return qos.getOrdinal() == QoSLevel._BESTEFFORT_INDEX;
    }

    @Override
    public void close() throws MALException {
        super.close();

        openHttpResponses.clear();
        executor.shutdown();

        try {
            synchronized (this) {
                if (null != server) {
                    server.stopServer();
                }
            }
        } catch (HttpApiImplException ex) {
            throw new MALException("Error stopping HTTP Server", ex);
        }
    }

    @Override
    protected String createTransportAddress() throws MALException {
        return host + PORT_DELIMITER + port;
    }

    /**
     * Provide a default IP address for this host
     *
     * @return The transport specific address part.
     * @throws MALException On error
     */
    private String getDefaultHost() throws MALException {
        try {
            final StringBuilder hostAddress = new StringBuilder();
            // Build MAL HTTP url string
            final InetAddress addr = InetAddress.getLocalHost();
            if (addr instanceof Inet6Address) {
                RLOGGER.fine("HTTP Address class is IPv6");
                hostAddress.append('[');
                hostAddress.append(addr.getHostAddress());
                hostAddress.append(']');
            } else if (addr instanceof Inet4Address) {
                hostAddress.append(addr.getHostAddress());
            }
            return hostAddress.toString();
        } catch (UnknownHostException ex) {
            throw new MALException("Could not determine local host address", ex);
        }
    }

    /**
     * This method returns a random port number to be used for differentiating
     * different MAL instances in the same host.
     *
     * @return the random, host unique port number
     */
    private int getRandomClientPort() {
        int min = 1024;
        int max = 65536;
        return new Random().nextInt(max - min) + min;
    }

    @Override
    protected String getLocalName(String localName, final Map properties) {
        if ((null == localName) || (0 == localName.length())) {
            localName = String.valueOf(RANDOM_NAME.nextInt(Integer.MAX_VALUE));
        }

        return localName;
    }

    @Override
    protected OutgoingMessageHolder<byte[]> encodeMessage(String destinationRootURI,
            String destinationURI, Object multiSendHandle, boolean lastForHandle,
            String targetURI, GENMessage msg) throws Exception {
        RLOGGER.log(Level.FINEST, "Header of msg to send: \n" + msg.getHeader().toString());

        if (selectedHttpBindingMode == HTTP_BINDING_MODE_NO_ENCODING) {
            byte[] data = internalEncodeByteMessage(msg);
            return new OutgoingMessageHolder<byte[]>(this.timeout, destinationRootURI, destinationURI,
                    multiSendHandle, lastForHandle, msg, data);
        } else {
            // encode the message
            StringBuilder sb = new StringBuilder();
            try {
                final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                final MALElementOutputStream enc = getStreamFactory().createOutputStream(baos);
                sb.append("Raw message to send (HTTPTrans):\n");
                sb.append(getMessageBody(msg));
                msg.encodeMessage(getStreamFactory(), enc, baos, false);
                byte[] data = baos.toByteArray();
                sb.append("\nEncoded message to send (HTTPTrans):\n");
                sb.append(new String(data));
                RLOGGER.log(Level.FINEST, sb.toString());

                // message is encoded!
                RLOGGER.log(Level.FINE, "HTTP Sending data to {0} : {1}",
                        new Object[]{targetURI, new PacketToString(data)});

                return new OutgoingMessageHolder<byte[]>(this.timeout, destinationRootURI, destinationURI,
                        multiSendHandle, lastForHandle, msg, data);
            } catch (MALException ex) {
                RLOGGER.log(Level.FINEST, sb.toString());
                RLOGGER.log(Level.SEVERE, "GEN could not encode message!", ex);
                throw new MALTransmitErrorException(msg.getHeader(),
                        new MOErrorException(MALHelper.BAD_ENCODING_ERROR_NUMBER, null), null);
            }
        }
    }

    String getMessageBody(GENMessage msg) {

        if (msg.getBody() != null) {
            StringBuilder output = new StringBuilder();
            output.append(msg.getBody().getClass().getCanonicalName());
            output.append(" (");
            output.append(msg.getBody().getElementCount());
            output.append(")\n");
            for (int i = 0; i < msg.getBody().getElementCount(); i++) {
                try {
                    if (msg.getBody().getBodyElement(i, null) != null) {
                        output.append(" | ");
                        output.append(msg.getBody().getBodyElement(i, null).toString());
                    }
                } catch (Exception e) {
                    RLOGGER.log(Level.SEVERE, "HTTPXMLStreamWriter closing error: " + e.getMessage(), e);
                }
            }
            return output.toString();
        }
        return " --no body--";
    }

    /**
     * Internal method for the creation of receiving messages
     *
     * @param messageSource The input HTTPHeaderAndBody data structure to use.
     * @return The new message.
     * @throws MALException on Error.
     */
    @Override
    public GENMessage decodeMessage(HTTPHeaderAndBody messageSource) throws MALException {
        RLOGGER.fine("HTTPTransport.createMessage:\n" + messageSource.toString());

        MALMessageHeader header = messageSource.getHeader();
        byte[] packetData = messageSource.getEncodedPacketData();
        int statusCode = messageSource.getStatusCode();

        GENMessage returnable = null;

        // No content - response message not foreseen by the MAL interactions
        // CCSDS 524.3-R-0 Table 3-4
        if (statusCode == 204) {
            RLOGGER.warning("StatusCode 204 received");
            return null;
        }
        if (statusCode >= 400) {
            UInteger errorNumber = StatusCodeHelper.getMALErrorFromStatusCode(statusCode);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MALElementOutputStream os = getStreamFactory().createOutputStream(baos);
            os.writeElement(errorNumber, null);
            os.writeElement(new Union("remote"), null);
            os.close();

            packetData = baos.toByteArray();
        }

        String encodedMsg = new String(packetData);
        StringBuilder sb = new StringBuilder();
        sb.append("Encoded received message:\n");
        sb.append(encodedMsg);

        returnable = new GENMessage(wrapBodyParts, false, header, qosProperties, packetData,
                getStreamFactory());

        sb.append("\nDecoded message:");
        sb.append(returnable.getBody().toString());
        RLOGGER.log(Level.FINEST, sb.toString());

        return returnable;
    }

    @Override
    protected MessageSender createMessageSender(MALMessageHeader msgHeader,
            String remoteRootURI) throws MALException, MALTransmitErrorException {
        if (selectedHttpBindingMode == HTTP_BINDING_MODE_NO_ENCODING) {
            return new HTTPMessageSenderNoEncoding(this, abstractPostClientImpl);
        } else if (selectedHttpBindingMode == HTTP_BINDING_MODE_NO_RESPONSE) {
            return new HTTPMessageSenderNoResponse(this, abstractPostClientImpl);
        } else {
            return new HTTPMessageSenderRequestResponse(this, abstractPostClientImpl);
        }
    }

    @Override
    protected URI rerouteMessage(GENMessage message) {
        String uri = message.getHeader().getTo().getValue();

        if (aliasToIp.isEmpty()) {
            return new URI(uri);
        }

        int index = uri.indexOf("://") + 3;
        String protocol = uri.substring(0, index);
        String address = uri.substring(index);
        int portIndex = address.indexOf(":");
        String ip = address.substring(0, portIndex);
        String rest = address.substring(portIndex);

        if (aliasToIp.containsKey(ip)) {
            return new URI(protocol + aliasToIp.get(ip) + rest);
        } else {
            return new URI(uri);
        }
    }

    /**
     * Checks whether the communication should be secured via SSL/TLS.
     *
     * @return true if HTTPS should be used
     */
    public boolean useHttps() {
        return useHttps;
    }

    /**
     * Gets the filename of the Java KeyStore assigned to this transport.
     *
     * @return the KeyStore filename
     */
    public String getKeystoreFilename() {
        return keystoreFilename;
    }

    /**
     * Gets the password of the Java KeyStore assigned to this transport.
     *
     * @return the KeyStore password
     */
    public String getKeystorePassword() {
        return keystorePassword;
    }

    /**
     * Stores an open HTTP response for the message's unique URI+transactionId.
     *
     * @param uri the to header field
     * @param transactionId the transactionId
     * @param abstractHttpResponse the open AbstractHttpResponse
     */
    public synchronized void storeOpenHttpResponse(String uri, Long transactionId,
            IHttpResponse abstractHttpResponse) {
        String key = uri + transactionId.toString();
        openHttpResponses.put(key, abstractHttpResponse);
    }

    /**
     * Retrieves an open HTTP response for the message's unique
     * URI+transactionId.
     *
     * @param uri the URIFrom header field
     * @param transactionId the transactionId
     * @return the open AbstractHttpResponse
     */
    public synchronized IHttpResponse retrieveOpenHttpResponse(String uri, Long transactionId) {
        String key = uri + transactionId.toString();
        IHttpResponse abstractHttpResponse = openHttpResponses.remove(key);
        return abstractHttpResponse;
    }

    /**
     * Use the Executor to run the submitted task asynchronously.
     *
     * @param task the Runnable to submit for execution
     */
    public void runAsynchronousTask(Runnable task) {
        executor.submit(task);
    }

    /**
     * According to Table 3-3 in recommended standard, checks whether the MAL
     * interaction type and stage require the message to be a simple HTTP
     * request without the need for further processing.
     *
     * @param header the MALMessageHeader
     * @return true if the message is a simple HTTP request without having to
     * process the response
     */
    public static boolean messageHasEmtpyHttpResponse(MALMessageHeader header) {
        return (header.getInteractionType().getOrdinal() == InteractionType._SEND_INDEX)
                || (header.getInteractionType().getOrdinal() == InteractionType._INVOKE_INDEX
                && header.getInteractionStage().getValue() == MALInvokeOperation._INVOKE_RESPONSE_STAGE)
                || (header.getInteractionType().getOrdinal() == InteractionType._PROGRESS_INDEX
                && header.getInteractionStage().getValue() == MALProgressOperation._PROGRESS_UPDATE_STAGE)
                || (header.getInteractionType().getOrdinal() == InteractionType._PROGRESS_INDEX
                && header.getInteractionStage().getValue() == MALProgressOperation._PROGRESS_RESPONSE_STAGE)
                || (header.getInteractionType().getOrdinal() == InteractionType._PUBSUB_INDEX
                && header.getInteractionStage().getValue() == MALPubSubOperation._PUBLISH_STAGE)
                || (header.getInteractionType().getOrdinal() == InteractionType._PUBSUB_INDEX
                && header.getInteractionStage().getValue() == MALPubSubOperation._NOTIFY_STAGE);
    }

    /**
     * According to Table 3-3 in recommended standard, checks whether the MAL
     * interaction type and stage require the message to further process the
     * HTTP response for a subsequent MAL message after submitting the HTTP
     * request.
     *
     * @param header the MALMessageHeader
     * @return true if the message expects a subsequent MAL message encoded
     * within the HTTP response
     */
    public static boolean messageExpectsHttpResponse(MALMessageHeader header) {
        return (header.getInteractionType().getOrdinal() == InteractionType._SUBMIT_INDEX
                && header.getInteractionStage().getValue() == MALSubmitOperation._SUBMIT_STAGE)
                || (header.getInteractionType().getOrdinal() == InteractionType._REQUEST_INDEX
                && header.getInteractionStage().getValue() == MALRequestOperation._REQUEST_STAGE)
                || (header.getInteractionType().getOrdinal() == InteractionType._INVOKE_INDEX
                && header.getInteractionStage().getValue() == MALInvokeOperation._INVOKE_STAGE)
                || (header.getInteractionType().getOrdinal() == InteractionType._PROGRESS_INDEX
                && header.getInteractionStage().getValue() == MALProgressOperation._PROGRESS_STAGE)
                || (header.getInteractionType().getOrdinal() == InteractionType._PUBSUB_INDEX
                && header.getInteractionStage().getValue() == MALPubSubOperation._REGISTER_STAGE)
                || (header.getInteractionType().getOrdinal() == InteractionType._PUBSUB_INDEX
                && header.getInteractionStage().getValue() == MALPubSubOperation._PUBLISH_REGISTER_STAGE)
                || (header.getInteractionType().getOrdinal() == InteractionType._PUBSUB_INDEX
                && header.getInteractionStage().getValue() == MALPubSubOperation._DEREGISTER_STAGE)
                || (header.getInteractionType().getOrdinal() == InteractionType._PUBSUB_INDEX
                && header.getInteractionStage().getValue() == MALPubSubOperation._PUBLISH_DEREGISTER_STAGE);
    }

    /**
     * According to Table 3-3 in recommended standard, checks whether the MAL
     * interaction type and stage require the message to be encoded and passed
     * directly within a HTTP response.
     *
     * @param header the MALMessageHeader
     * @return true if the message shall be passed within a HTTP response
     */
    public static boolean messageIsEncodedHttpResponse(MALMessageHeader header) {
        return (header.getInteractionType().getOrdinal() == InteractionType._SUBMIT_INDEX
                && header.getInteractionStage().getValue() == MALSubmitOperation._SUBMIT_ACK_STAGE)
                || (header.getInteractionType().getOrdinal() == InteractionType._REQUEST_INDEX
                && header.getInteractionStage().getValue() == MALRequestOperation._REQUEST_RESPONSE_STAGE)
                || (header.getInteractionType().getOrdinal() == InteractionType._INVOKE_INDEX
                && header.getInteractionStage().getValue() == MALInvokeOperation._INVOKE_ACK_STAGE)
                || (header.getInteractionType().getOrdinal() == InteractionType._PROGRESS_INDEX
                && header.getInteractionStage().getValue() == MALProgressOperation._PROGRESS_ACK_STAGE)
                || (header.getInteractionType().getOrdinal() == InteractionType._PUBSUB_INDEX
                && header.getInteractionStage().getValue() == MALPubSubOperation._REGISTER_ACK_STAGE)
                || (header.getInteractionType().getOrdinal() == InteractionType._PUBSUB_INDEX
                && header.getInteractionStage().getValue() == MALPubSubOperation._PUBLISH_REGISTER_ACK_STAGE)
                || (header.getInteractionType().getOrdinal() == InteractionType._PUBSUB_INDEX
                && header.getInteractionStage().getValue() == MALPubSubOperation._DEREGISTER_ACK_STAGE)
                || (header.getInteractionType().getOrdinal() == InteractionType._PUBSUB_INDEX
                && header.getInteractionStage().getValue() == MALPubSubOperation._PUBLISH_DEREGISTER_ACK_STAGE);
    }

    /**
     * Converts a byte array to its corresponding hexadecimal string.
     *
     * @param data the byte array to convert
     * @return the corresponding hexadecimal string
     */
    public static String byteArrayToHexString(final byte[] data) {
        final int HEX_MASK = 0xFF;
        final StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            final String hex = Integer.toHexString(HEX_MASK & data[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * Converts a hexadecimal string to its corresponding byte array.
     *
     * @param s the hexadecimal string to convert
     * @return the corresponding byte array
     */
    public static byte[] hexStringToByteArray(final String s) {
        final int len = s.length();
        final byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
