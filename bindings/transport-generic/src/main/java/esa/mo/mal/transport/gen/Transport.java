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

import esa.mo.mal.transport.gen.receivers.GENIncomingMessageDecoder;
import esa.mo.mal.transport.gen.receivers.IncomingMessageHolder;
import esa.mo.mal.transport.gen.receivers.IncomingMessageReceiver;
import esa.mo.mal.transport.gen.sending.ConcurrentMessageSender;
import esa.mo.mal.transport.gen.sending.OutgoingMessageHolder;
import esa.mo.mal.transport.gen.util.TransportThreadFactory;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.*;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.transport.*;
import esa.mo.mal.transport.gen.sending.MessageSender;

/**
 * A generic implementation of the transport interface.
 *
 * @param <I> The type of incoming message
 * @param <O> The type of the outgoing encoded message
 */
public abstract class Transport<I, O> implements MALTransport {

    /**
     * System property to control whether message parts of wrapped in BLOBs.
     */
    public static final String WRAP_PROPERTY
            = "org.ccsds.moims.mo.mal.transport.gen.wrap";
    /**
     * System property to control whether in-process processing supported.
     */
    public static final String INPROC_PROPERTY
            = "org.ccsds.moims.mo.mal.transport.gen.fastInProcessMessages";
    /**
     * System property to control whether debug messages are generated.
     */
    public static final String DEBUG_PROPERTY
            = "org.ccsds.moims.mo.mal.transport.gen.debug";
    /**
     * System property to control the number of connections per client.
     */
    public static final String NUM_CLIENT_CONNS_PROPERTY
            = "org.ccsds.moims.mo.mal.transport.gen.numconnections";
    /**
     * The timeout in seconds to wait for confirmation of delivery.
     */
    public static final String DELIVERY_TIMEOUT_PROPERTY
            = "org.ccsds.moims.mo.mal.transport.gen.deliverytimeout";

    /**
     * System property to control whether a new outgoing data channel should be
     * created if one dosen't exist when sending a message
     */
    public static final String CONNECT_WHEN_CONSUMER_OFFLINE_PROPERTY
            = "org.ccsds.moims.mo.mal.transport.gen.connectwhenconsumeroffline";

    /**
     * Charset used for converting the encoded message into a string for
     * debugging.
     */
    public static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    /**
     * Logger
     */
    public static final java.util.logging.Logger LOGGER = Logger.getLogger(
            "org.ccsds.moims.mo.mal.transport.gen");
    /**
     * Used to create random local names for endpoints.
     */
    protected static final Random RANDOM_NAME = new Random();
    /**
     * Reference to our factory.
     */
    protected final MALTransportFactory factory;
    /**
     * The delimiter to use to separate the protocol part from the address part
     * of the URL.
     */
    protected final String protocolDelim;
    /**
     * The delimiter to use to separate the external address part from the
     * internal object part of the URL.
     */
    protected final char serviceDelim;
    /**
     * If the protocol delimiter is the same as the service delimiter then we
     * need a count to find the correct service delimiter.
     */
    protected final int serviceDelimCounter;
    /**
     * Delimiter to use when holding routing information in a URL
     */
    protected final char routingDelim;
    /**
     * True if protocol supports the concept of routing.
     */
    protected final boolean supportsRouting;
    /**
     * True if body parts should be wrapped in blobs for encoded element
     * support.
     */
    protected final boolean wrapBodyParts;
    /**
     * True if calls to ourselves should be handled in-process i.e. not via the
     * underlying transport.
     */
    protected final boolean inProcessSupport;
    /**
     * The timeout in seconds to wait for confirmation of delivery.
     */
    protected final int deliveryTimeout;
    /**
     * The string used to represent this protocol.
     */
    protected final String protocol;
    /**
     * Map of string MAL names to endpoints.
     */
    protected final Map<String, Endpoint> endpointMalMap = new HashMap<>();
    /**
     * Map of string transport routing names to endpoints.
     */
    protected final Map<String, Endpoint> endpointRoutingMap = new HashMap<>();
    /**
     * Map of QoS properties.
     */
    protected final Map qosProperties;
    /**
     * The number of connections per client or server. The Transport will
     * connect numConnections times to the predefined port and host per
     * different client/server.
     */
    private final int numConnections;
    /**
     * The thread that receives incoming message from the underlying transport.
     * All incoming raw data packets are processed by this thread.
     */
    private final ExecutorService decoderExecutor;
    /**
     * The thread pool of input message processors. All incoming messages are
     * processed by this thread pool after they have been decoded by the
     * decoderExecutor thread.
     */
    private final ExecutorService dispatcherExecutor;
    /**
     * The map of message queues, segregated by transaction id.
     */
    private final Map<Long, IncomingMessageDispatcher> transactionQueues = new HashMap<>();
    /**
     * Map of outgoing channels. This associates a URI to a transport resource
     * that is able to send messages to this URI.
     */
    private final Map<String, ConcurrentMessageSender> outgoingDataChannels = new HashMap<>();
    /**
     * The stream factory used for encoding and decoding messages.
     */
    private final MALElementStreamFactory streamFactory;
    /**
     * The base string for URL for this protocol.
     */
    protected String uriBase;
    /**
     * Map of cachedRoutingParts. This associates a URI to its Routing part.
     */
    private final ConcurrentHashMap<String, String> cachedRoutingParts = new ConcurrentHashMap<>();
    /**
     * Value of the
     * org.ccsds.moims.mo.mal.transport.gen.connectwhenconsumeroffline property
     */
    private boolean connectWhenConsumerOffline = true;

    /**
     * Set of root uris to which the transport tried to connect. Used together
     * with the connectWhenConsumerOffline property to decide if the connection
     * is trying to be established for the first time.
     */
    private final static Set<String> connectionAttempts = new HashSet<>();

    /**
     * Constructor.
     *
     * @param protocol The protocol string.
     * @param serviceDelim The delimiter to use for separating the URL
     * @param supportsRouting True if routing is supported by the naming
     * convention
     * @param wrapBodyParts True is body parts should be wrapped in BLOBs
     * @param factory The factory that created us.
     * @param properties The QoS properties.
     * @throws MALException On error.
     */
    public Transport(final String protocol,
            final char serviceDelim,
            final boolean supportsRouting,
            final boolean wrapBodyParts,
            final MALTransportFactory factory,
            final java.util.Map properties) throws MALException {
        this(protocol, "://", serviceDelim, '@',
                supportsRouting, wrapBodyParts, factory, properties);
    }

    /**
     * Constructor.
     *
     * @param protocol The protocol string.
     * @param protocolDelim The delimiter to use for separating the protocol
     * part in the URL
     * @param serviceDelim The delimiter to use for separating the URL
     * @param routingDelim The delimiter to use for separating the URL for
     * routing
     * @param supportsRouting True if routing is supported by the naming
     * convention
     * @param wrapBodyParts True is body parts should be wrapped in BLOBs
     * @param factory The factory that created us.
     * @param properties The QoS properties.
     * @throws MALException On error.
     */
    public Transport(final String protocol,
            final String protocolDelim,
            final char serviceDelim,
            final char routingDelim,
            final boolean supportsRouting,
            final boolean wrapBodyParts,
            final MALTransportFactory factory,
            final java.util.Map properties) throws MALException {
        this.factory = factory;
        this.protocol = protocol;
        this.supportsRouting = supportsRouting;
        this.protocolDelim = protocolDelim;
        this.serviceDelim = serviceDelim;
        this.routingDelim = routingDelim;
        this.qosProperties = properties;

        streamFactory = MALElementStreamFactory.newFactory(protocol, properties);
        LOGGER.log(Level.FINE, "Created element stream: {0}",
                streamFactory.getClass().getName());

        if (protocolDelim.contains("" + serviceDelim)) {
            String replaced = protocolDelim.replace("" + serviceDelim, "");
            serviceDelimCounter = protocolDelim.length() - replaced.length();
        } else {
            serviceDelimCounter = 0;
        }

        // default values
        boolean lWrapBodyParts = wrapBodyParts;
        boolean lInProcessSupport = true;
        int lNumConnections = 1;
        int lDeliveryTime = 10;

        // decode configuration
        if (properties != null) {
            if (properties.containsKey(WRAP_PROPERTY)) {
                lWrapBodyParts = Boolean.parseBoolean((String) properties.get(WRAP_PROPERTY));
            }

            if (properties.containsKey(INPROC_PROPERTY)) {
                lInProcessSupport = Boolean.parseBoolean((String) properties.get(INPROC_PROPERTY));
            }

            // number of connections per client/server
            if (properties.containsKey(NUM_CLIENT_CONNS_PROPERTY)) {
                lNumConnections = Integer.parseInt((String) properties.get(NUM_CLIENT_CONNS_PROPERTY));
            }

            if (properties.containsKey(DELIVERY_TIMEOUT_PROPERTY)) {
                lDeliveryTime = Integer.parseInt((String) properties.get(DELIVERY_TIMEOUT_PROPERTY));
            }

            if (properties.containsKey(CONNECT_WHEN_CONSUMER_OFFLINE_PROPERTY)) {
                connectWhenConsumerOffline = Boolean.parseBoolean((String) properties.get(CONNECT_WHEN_CONSUMER_OFFLINE_PROPERTY));
            }
        }

        this.wrapBodyParts = lWrapBodyParts;
        this.inProcessSupport = lInProcessSupport;
        this.numConnections = lNumConnections;
        this.deliveryTimeout = lDeliveryTime;

        TransportThreadFactory decFactory = new TransportThreadFactory("Transport_Decoder");
        this.decoderExecutor = Executors.newSingleThreadExecutor(decFactory);
        this.dispatcherExecutor = TransportThreadFactory.createDispatcherExecutor(properties);

        LOGGER.log(Level.FINE, "Wrapping body parts set to: {0}", this.wrapBodyParts);
    }

    /**
     * Initialises this transport.
     *
     * @throws MALException On error
     */
    public void init() throws MALException {
        String protocolString = protocol;
        if (protocol.contains(":")) {
            protocolString = protocol.substring(0, protocol.indexOf(':'));
        }

        uriBase = protocolString + protocolDelim + createTransportAddress() + serviceDelim;
    }

    @Override
    public MALEndpoint createEndpoint(final String localName, final Map qosProperties,
            final NamedValueList supplements) throws MALException {
        final Map localProperties = new HashMap();

        if (null != this.qosProperties) {
            localProperties.putAll(this.qosProperties);
        }
        if (null != qosProperties) {
            localProperties.putAll(qosProperties);
        }

        final String strRoutingName = getLocalName(localName, localProperties);
        Endpoint endpoint = endpointRoutingMap.get(strRoutingName);

        if (endpoint == null) {
            LOGGER.log(Level.FINE, "Creating endpoint {0} : {1}",
                    new Object[]{localName, strRoutingName});
            endpoint = internalCreateEndpoint(localName, strRoutingName, localProperties, supplements);
            endpointMalMap.put(localName, endpoint);
            endpointRoutingMap.put(strRoutingName, endpoint);
        }

        return endpoint;
    }

    @Override
    public MALEndpoint getEndpoint(final String localName) throws IllegalArgumentException {
        return endpointMalMap.get(localName);
    }

    @Override
    public MALEndpoint getEndpoint(final URI uri) throws IllegalArgumentException {
        String endpointUriPart = getRoutingPart(uri.getValue());
        return endpointRoutingMap.get(endpointUriPart);
    }

    /**
     * Returns the stream factory.
     *
     * @return the stream factory
     */
    public MALElementStreamFactory getStreamFactory() {
        return streamFactory;
    }

    public abstract GENMessage createMessage(I packet) throws MALException;

    /**
     * On reception of an IO stream this method should be called. This is the
     * main reception entry point into the generic transport for stream based
     * transports.
     *
     * @param receptionHandler The reception handler to pass them to.
     * @param decoder The class responsible for decoding the message from the
     * incoming connection
     */
    public void receive(ReceptionHandler receptionHandler, GENIncomingMessageDecoder decoder) {
        decoderExecutor.submit(new IncomingMessageReceiver(this, receptionHandler, decoder));
    }

    /**
     * The main exit point for messages from this transport.
     *
     * @param multiSendHandle A context handle for multi send
     * @param lastForHandle True if that is the last message in a multi send for
     * the handle
     * @param msg The message to send.
     * @throws MALTransmitErrorException On transmit error.
     */
    public void sendMessage(final Object multiSendHandle, final boolean lastForHandle,
            final GENMessage msg) throws MALTransmitErrorException {
        MALMessageHeader header = msg.getHeader();

        if (header.getTo() == null || header.getTo().getValue() == null) {
            throw new MALTransmitErrorException(header,
                    new DestinationUnknownException("URI To field must not be null"), qosProperties);
        }

        // get the root URI, (e.g. maltcp://10.0.0.1:61616 )
        String destinationURI = header.getTo().getValue();
        String remoteRootURI = header.getToURI().getRootURI(serviceDelim, serviceDelimCounter);

        // first check if its actually a message to ourselves
        String endpointUriPart = getRoutingPart(destinationURI);

        if (inProcessSupport
                && (uriBase.startsWith(remoteRootURI) || remoteRootURI.startsWith(uriBase))
                && endpointRoutingMap.containsKey(endpointUriPart)) {
            LOGGER.log(Level.FINE, "Routing msg internally to: {0}",
                    new Object[]{endpointUriPart});

            // if local then just send internally
            receiveIncomingMessage(new IncomingMessageHolder(
                    header.getTransactionId(), msg, new PacketToString(null)));
        } else {
            try {
                LOGGER.log(Level.FINE,
                        "Sending msg. Target root URI: {0} full URI: {1}",
                        new Object[]{remoteRootURI, destinationURI});

                // get outgoing channel
                ConcurrentMessageSender dataSender = manageCommunicationChannel(msg, false, null);

                OutgoingMessageHolder outgoingPacket = internalEncodeMessage(
                        remoteRootURI, destinationURI, multiSendHandle,
                        lastForHandle, dataSender.getTargetURI(), msg);

                dataSender.sendMessage(outgoingPacket);

                if (!Boolean.TRUE.equals(outgoingPacket.getResult())) {
                    // data was not sent succesfully, throw an exception for the
                    // higher MAL layers
                    throw new MALTransmitErrorException(header,
                            new DeliveryFailedException(null), null);
                }

                LOGGER.log(Level.FINE,
                        "Finished sending data to: {0}", remoteRootURI);
            } catch (MALTransmitErrorException e) {
                // this stops any true MAL exceptoins getting caught by the generic catch all below
                throw e;
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, "Interrupted while waiting for data reply", e);
                throw new MALTransmitErrorException(header,
                        new InternalException(null), null);
            } catch (Exception t) {
                LOGGER.log(Level.SEVERE, "Could not send message!", t);
                throw new MALTransmitErrorException(header,
                        new InternalException(null), null);
            }
        }
    }

    /**
     * Used to request the transport close a connection with a client. In this
     * case the transport will terminate all communication channels with the
     * destination in order for them to be re-established.
     *
     * @param uriTo the connection handler that received this message
     * @param receptionHandler The reception handler to pass them to.
     */
    public void closeConnection(final String uriTo, final ReceptionHandler receptionHandler) {
        String localUriTo = uriTo;
        // remove all associations with this target URI
        if ((localUriTo == null) && (receptionHandler != null)) {
            localUriTo = receptionHandler.getRemoteURI();
        }

        if (localUriTo != null) {
            ConcurrentMessageSender commsChannel;

            synchronized (this) {
                commsChannel = outgoingDataChannels.get(localUriTo);
                if (commsChannel != null) {
                    outgoingDataChannels.remove(localUriTo);
                } else {
                    LOGGER.log(Level.WARNING,
                            "Could not locate associated data channel to close "
                            + "communications, perhaps it was closed before. URI : {0} ",
                            localUriTo);
                }
            }
            if (commsChannel != null) {
                // need to do this outside the sync block so 
                // that we do not affect other threads
                commsChannel.terminate();
            }
        }

        if (receptionHandler != null) {
            receptionHandler.close();
        }
    }

    /**
     * Used to inform the transport about communication problems with clients.
     * In this case the transport will terminate all communication channels with
     * the destination in order for them to be re-established.
     *
     * @param uriTo the connection handler that received this message
     * @param receptionHandler The reception handler to pass them to.
     */
    public void communicationError(String uriTo, ReceptionHandler receptionHandler) {
        LOGGER.log(Level.WARNING, "Communication Error with uri: {0}", uriTo);
        closeConnection(uriTo, receptionHandler);
    }

    @Override
    public void deleteEndpoint(final String localName) throws MALException {
        final Endpoint endpoint = endpointMalMap.get(localName);

        if (null != endpoint) {
            LOGGER.log(Level.INFO, "Deleting endpoint", localName);
            endpointMalMap.remove(localName);
            endpointRoutingMap.remove(endpoint.getRoutingName());
            endpoint.close();
        }
    }

    @Override
    public void close() throws MALException {
        for (Endpoint entry : endpointMalMap.values()) {
            entry.close();
        }

        endpointMalMap.clear();
        endpointRoutingMap.clear();

        decoderExecutor.shutdown();
        dispatcherExecutor.shutdown();

        LOGGER.fine("Closing outgoing channels");
        synchronized (this) {
            for (ConcurrentMessageSender sender : outgoingDataChannels.values()) {
                sender.terminate();
            }

            outgoingDataChannels.clear();
        }
        LOGGER.fine("Closed outgoing channels");
    }

    /**
     * This method receives an incoming message and adds to to the correct queue
     * based on its transaction id.
     *
     * @param malMsg the message
     */
    public void receiveIncomingMessage(final IncomingMessageHolder malMsg) {
        LOGGER.log(Level.FINE, "Queuing message : {0} : {1}",
                new Object[]{malMsg.malMsg.getHeader().getTransactionId(), malMsg.smsg});

        synchronized (transactionQueues) {
            IncomingMessageDispatcher dispatcher = transactionQueues.get(malMsg.transactionId);

            if (dispatcher == null) {
                dispatcher = new IncomingMessageDispatcher(this, malMsg);
                transactionQueues.put(malMsg.transactionId, dispatcher);
                dispatcherExecutor.submit(dispatcher);
            } else if (dispatcher.addMessage(malMsg)) {
                // need to resubmit this to the processing threads
                dispatcherExecutor.submit(dispatcher);
            }

            Set<Long> transactionsToRemove = new HashSet<>();
            for (Map.Entry<Long, IncomingMessageDispatcher> entrySet : transactionQueues.entrySet()) {
                Long transId = entrySet.getKey();
                IncomingMessageDispatcher lproc = entrySet.getValue();

                if (lproc.isFinished()) {
                    transactionsToRemove.add(transId);
                }
            }

            for (Long transId : transactionsToRemove) {
                transactionQueues.remove(transId);
            }
        }
    }

    /**
     * This method dispatches an incoming message by routing it to the
     * appropriate endpoint, returning an error if the message cannot be
     * dispatched.
     *
     * @param msg The source message.
     * @param smsg The message in a string representation for logging.
     */
    public void dispatchMessage(final GENMessage msg, PacketToString smsg) {
        try {
            LOGGER.log(Level.FINE, "Processing message : {0} : {1}",
                    new Object[]{msg.getHeader().getTransactionId(), smsg});

            String endpointUriPart = getRoutingPart(msg.getHeader().getTo().getValue());
            final Endpoint endpoint = endpointRoutingMap.get(endpointUriPart);

            if (endpoint != null) {
                LOGGER.log(Level.FINE, "Passing message to endpoint {0} : {1}",
                        new Object[]{endpoint.getLocalName(), smsg});
                endpoint.receiveMessage(msg);
            } else {
                LOGGER.log(Level.WARNING, "Endpoint not found: {0}! "
                        + "Double check the uri, in particular, the ending part!",
                        new Object[]{endpointUriPart});
                returnErrorMessage(null,
                        msg,
                        MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER,
                        "Endpoint not found: " + endpointUriPart);
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error occurred when receiving data!", e);

            final StringWriter wrt = new StringWriter();
            e.printStackTrace(new PrintWriter(wrt));

            try {
                returnErrorMessage(null,
                        msg,
                        MALHelper.INTERNAL_ERROR_NUMBER,
                        "Error occurred: " + e.toString() + " : " + wrt.toString());
            } catch (MALException ex) {
                LOGGER.log(Level.SEVERE,
                        "Error occurred while trying to return error msg!", ex);
            }
        } catch (Error e) {
            // This is bad, Java errors are serious, 
            // so inform the other side if we can
            LOGGER.log(Level.SEVERE,
                    "Error occurred when processing message!", e);

            final StringWriter wrt = new StringWriter();
            e.printStackTrace(new PrintWriter(wrt));

            try {
                returnErrorMessage(null,
                        msg,
                        MALHelper.INTERNAL_ERROR_NUMBER,
                        "Error occurred: " + e.toString() + " : " + wrt.toString());
            } catch (MALException ex) {
                LOGGER.log(Level.SEVERE, "Error occurred when return error data : {0}", ex);
            }
        }
    }

    /**
     * Creates a return error message based on a received message.
     *
     * @param ep The endpoint to use for sending the error.
     * @param srcMsg The original message
     * @param errorNumber The error number
     * @param errorMsg The error message.
     * @throws MALException if cannot encode a response message
     */
    protected void returnErrorMessage(Endpoint ep, final GENMessage srcMsg,
            final UInteger errorNumber, final String errorMsg) throws MALException {
        try {
            final MALMessageHeader srcHdr = srcMsg.getHeader();
            final int type = srcHdr.getInteractionType().getOrdinal();
            final short stage = (null != srcHdr.getInteractionStage())
                    ? srcHdr.getInteractionStage().getValue() : 0;

            // first check that message should be responded to
            if (((type == InteractionType._SUBMIT_INDEX) && (stage == MALSubmitOperation._SUBMIT_STAGE))
                    || ((type == InteractionType._REQUEST_INDEX) && (stage == MALRequestOperation._REQUEST_STAGE))
                    || ((type == InteractionType._INVOKE_INDEX) && (stage == MALInvokeOperation._INVOKE_STAGE))
                    || ((type == InteractionType._PROGRESS_INDEX) && (stage == MALProgressOperation._PROGRESS_STAGE))
                    || ((type == InteractionType._PUBSUB_INDEX) && (stage == MALPubSubOperation._REGISTER_STAGE))
                    || ((type == InteractionType._PUBSUB_INDEX) && (stage == MALPubSubOperation._DEREGISTER_STAGE))
                    || ((type == InteractionType._PUBSUB_INDEX) && (stage == MALPubSubOperation._PUBLISH_REGISTER_STAGE))
                    || ((type == InteractionType._PUBSUB_INDEX) && (stage == MALPubSubOperation._PUBLISH_DEREGISTER_STAGE))) {

                if ((null == ep) && (!endpointMalMap.isEmpty())) {
                    Endpoint endpoint = endpointMalMap.entrySet().iterator().next().getValue();

                    final GENMessage retMsg = (GENMessage) endpoint.createMessage(srcHdr.getAuthenticationId(),
                            srcHdr.getFromURI(),
                            Time.now(),
                            srcHdr.getInteractionType(),
                            new UOctet((short) (srcHdr.getInteractionStage().getValue() + 1)),
                            srcHdr.getTransactionId(),
                            srcHdr.getServiceArea(),
                            srcHdr.getService(),
                            srcHdr.getOperation(),
                            srcHdr.getServiceVersion(),
                            true,
                            srcHdr.getSupplements(),
                            srcMsg.getQoSProperties(),
                            errorNumber, new Union(errorMsg));

                    sendMessage(null, true, retMsg);
                } else {
                    LOGGER.log(Level.WARNING, "(1) Unable to return error"
                            + " number ({0}) as no endpoint supplied: {1}",
                            new Object[]{errorNumber, srcHdr});
                }
            } else {
                throw new MALException("Unknown type/stage! Type: " + type + " - Stage: " + stage);
            }
        } catch (MALTransmitErrorException ex) {
            LOGGER.log(Level.WARNING,
                    "Error occurred when attempting to return previous error!",
                    ex);
        }
    }

    /**
     * Returns the local name or creates a random one if null.
     *
     * @param localName The existing local name string to check.
     * @param properties The QoS properties.
     * @return The local name to use.
     */
    protected String getLocalName(String localName, final java.util.Map properties) {
        if (localName == null || localName.isEmpty()) {
            // This sets the "sign bit" as zero, to avoid having negative numbers:
            return String.valueOf(RANDOM_NAME.nextInt() & Integer.MAX_VALUE);
        }

        return localName;
    }

    /**
     * Returns the routing part of the URI.
     *
     * @param uriValue The URI value
     * @return the routing part of the URI
     */
    public String getRoutingPart(String uriValue) {
        String routingPart = cachedRoutingParts.get(uriValue);

        if (routingPart == null) {
            final int iFirst = URI.nthIndexOf(uriValue, serviceDelim, serviceDelimCounter);
            int iSecond = supportsRouting ? uriValue.indexOf(routingDelim)
                    : uriValue.length();
            if (0 > iSecond) {
                iSecond = uriValue.length();
            }

            routingPart = uriValue.substring(iFirst + 1, iSecond);
            cachedRoutingParts.put(uriValue, routingPart);
        }

        return routingPart;
    }

    /**
     * Overridable internal method for the creation of endpoints.
     *
     * @param localName The local mal name to use.
     * @param routingName The local routing name to use.
     * @param qosProperties the QoS properties.
     * @param supplements The supplements for this endpoint.
     * @return The new endpoint
     * @throws MALException on Error.
     */
    protected Endpoint internalCreateEndpoint(final String localName,
            final String routingName, final Map qosProperties,
            final NamedValueList supplements) throws MALException {
        return new Endpoint(this, localName, routingName, uriBase + routingName, wrapBodyParts, supplements);
    }

    /**
     * This method checks if there is a communication channel for sending a
     * particular message and in addition stores the communication channel on
     * incoming messages in case of bi-directional transports for re-use. If
     * there is no communication channel for sending a message the transport
     * creates and registers it.
     *
     * @param msg The message received or to be sent
     * @param isIncomingMsg the message direction
     * @param receptionHandler the message reception handler, null if the
     * message is an outgoing message
     * @return returns an existing or newly created message sender
     * @throws MALTransmitErrorException in case of communication problems
     */
    public synchronized ConcurrentMessageSender manageCommunicationChannel(GENMessage msg,
            boolean isIncomingMsg, ReceptionHandler receptionHandler) throws MALTransmitErrorException {
        ConcurrentMessageSender sender = null;

        if (isIncomingMsg) {
            // incoming msg
            if ((receptionHandler != null) && (receptionHandler.getRemoteURI() == null)) {
                // transport supports bi-directional communication
                // this is the first message received form this reception handler
                // add the remote base URI it is receiving messages from
                URI sourceURI = msg.getHeader().getFromURI();
                String sourceRootURI = sourceURI.getRootURI(serviceDelim, serviceDelimCounter);
                receptionHandler.setRemoteURI(sourceRootURI);

                //register the communication channel with this URI if needed
                sender = registerMessageSender(receptionHandler.getMessageSender(), sourceRootURI);
            }
        } else {
            // outgoing message
            // get target URI
            URI reroutedMsg = rerouteMessage(msg);
            String remoteRootURI = reroutedMsg.getRootURI(serviceDelim, serviceDelimCounter);

            // get sender if it exists
            sender = outgoingDataChannels.get(remoteRootURI);

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
                    MessageSender transmitter = createMessageSender(msg, remoteRootURI);
                    sender = registerMessageSender(transmitter, remoteRootURI);

                    LOGGER.log(Level.FINE, "Opening {0}", numConnections);

                    for (int i = 1; i < numConnections; i++) {
                        // insert new processor (message sender) to root data sender for the URI
                        MessageSender anotherTransmitter = createMessageSender(msg, remoteRootURI);
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
        }

        return sender;
    }

    protected URI rerouteMessage(GENMessage message) {
        return message.getHeader().getToURI();
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
            dataSender = new ConcurrentMessageSender(this, remoteRootURI);

            LOGGER.log(Level.FINE, "Registering data sender for URI: {0}", remoteRootURI);
            outgoingDataChannels.put(remoteRootURI, dataSender);

            // insert new processor (message sender) to root data sender for the URI
            dataSender.addProcessor(dataTransmitter, remoteRootURI);
        }

        return dataSender;
    }

    /**
     * Internal method for encoding the message.
     *
     * @param destinationRootURI The destination root URI.
     * @param destinationURI The complete destination URI.
     * @param multiSendHandle Handle for multi send messages.
     * @param lastForHandle true if last message in a multi send.
     * @param targetURI The target URI.
     * @param msg The message to send.
     * @return The message holder for the outgoing message.
     * @throws Exception if an error.
     */
    protected abstract OutgoingMessageHolder<O> internalEncodeMessage(
            final String destinationRootURI,
            final String destinationURI,
            final Object multiSendHandle,
            final boolean lastForHandle,
            final String targetURI,
            final GENMessage msg) throws Exception;

    /**
     * Internal method for encoding the message.
     *
     * @param destinationRootURI The destination root URI.
     * @param destinationURI The complete destination URI.
     * @param multiSendHandle Handle for multi send messages.
     * @param lastForHandle true if last message in a multi send.
     * @param targetURI The target URI.
     * @param msg The message to send.
     * @return The message holder for the outgoing message.
     * @throws MALTransmitErrorException if an error.
     */
    protected byte[] internalEncodeByteMessage(final String destinationRootURI,
            final String destinationURI,
            final Object multiSendHandle,
            final boolean lastForHandle,
            final String targetURI,
            final GENMessage msg) throws MALTransmitErrorException {
        // encode the message
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final MALElementOutputStream enc = getStreamFactory().createOutputStream(baos);
            msg.encodeMessage(getStreamFactory(), enc, baos, true);
            byte[] data = baos.toByteArray();

            // message is encoded!
            LOGGER.log(Level.FINE, "Sending data to {0} : {1}",
                    new Object[]{targetURI, new PacketToString(data)});

            return data;
        } catch (MALException ex) {
            LOGGER.log(Level.SEVERE, "Could not encode message!", ex);
            throw new MALTransmitErrorException(msg.getHeader(),
                    new BadEncodingException(null), null);
        }
    }

    /**
     * Creates the part of the URL specific to this transport instance.
     *
     * @return The transport specific address part.
     * @throws MALException On error
     */
    protected abstract String createTransportAddress() throws MALException;

    /**
     * Method to be implemented by the transport in order to return a message
     * sender capable if sending messages to a target root URI.
     *
     * @param msg the message to be send
     * @param remoteRootURI the remote root URI.
     * @return returns a message sender capable of sending messages to the
     * target URI
     * @throws MALException in case of error trying to create the communication
     * channel
     * @throws MALTransmitErrorException in case of error connecting to the
     * target URI
     */
    protected abstract MessageSender createMessageSender(GENMessage msg,
            String remoteRootURI) throws MALException, MALTransmitErrorException;

}
