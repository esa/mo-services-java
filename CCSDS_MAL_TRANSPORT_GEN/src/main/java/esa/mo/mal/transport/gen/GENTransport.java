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

import esa.mo.mal.transport.gen.sending.GENConcurrentMessageSender;
import esa.mo.mal.transport.gen.sending.GENMessageSender;
import esa.mo.mal.transport.gen.sending.GENOutgoingMessageHolder;
import esa.mo.mal.transport.gen.util.GENHelper;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.*;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.*;

/**
 * A generic implementation of the transport interface.
 */
public abstract class GENTransport implements MALTransport
{
  /**
   * System property to control whether message parts of wrapped in BLOBs.
   */
  public static final String WRAP_PROPERTY = "org.ccsds.moims.mo.mal.transport.gen.wrap";
  /**
   * System property to control whether in-process processing supported.
   */
  public static final String INPROC_PROPERTY = "org.ccsds.moims.mo.mal.transport.gen.fastInProcessMessages";
  /**
   * System property to control whether debug messages are generated.
   */
  public static final String DEBUG_PROPERTY = "org.ccsds.moims.mo.mal.transport.gen.debug";
  /**
   * System property to control the number of input processors.
   */
  public static final String INPUT_PROCESSORS_PROPERTY = "org.ccsds.moims.mo.mal.transport.gen.inputprocessors";
  /**
   * System property to control the number of connections per client.
   */
  public static final String NUM_CLIENT_CONNS_PROPERTY = "org.ccsds.moims.mo.mal.transport.gen.numconnections";
  /**
   * Charset used for converting the encoded message into a string for debugging.
   */
  public static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
  /**
   * Logger
   */
  public static final java.util.logging.Logger LOGGER = Logger.getLogger("org.ccsds.moims.mo.mal.transport.gen");
  /**
   * Used to create random local names for endpoints.
   */
  protected static final Random RANDOM_NAME = new Random();
  /**
   * Reference to our factory.
   */
  protected final MALTransportFactory factory;
  /**
   * The delimiter to use to separate the protocol part from the address part of the URL.
   */
  protected final String protocolDelim;
  /**
   * The delimiter to use to separate the external address part from the internal object part of the URL.
   */
  protected final char serviceDelim;
  /**
   * Delimiter to use when holding routing information in a URL
   */
  protected final char routingDelim;
  /**
   * True if protocol supports the concept of routing.
   */
  protected final boolean supportsRouting;
  /**
   * True if string based stream, can be logged as a string rather than hex.
   */
  protected final boolean streamHasStrings;
  /**
   * True if body parts should be wrapped in blobs for encoded element support.
   */
  protected final boolean wrapBodyParts;
  /**
   * True if calls to ourselves should be handled in-process i.e. not via the underlying transport.
   */
  protected final boolean inProcessSupport;
  /**
   * True if want to log the packet data
   */
  protected final boolean logFullDebug;
  /**
   * The string used to represent this protocol.
   */
  protected final String protocol;
  /**
   * Map of string names to endpoints.
   */
  protected final Map<String, GENEndpoint> endpointMap = new HashMap<String, GENEndpoint>();
  /**
   * Map of QoS properties.
   */
  protected final Map qosProperties;
  /**
   * The number of connections per client or server. The Transport will connect numConnections times to the predefined
   * port and host per different client/server.
   */
  private final int numConnections;
  /**
   * Number of processors that are capable of processing parallel input requests. This is the internal number of threads
   * that process incoming messages arriving from MAL clients. It is the maximum parallel requests this MAL instance can
   * concurrently serve.
   */
  private final int inputProcessorThreads;
  /**
   * The thread that receives incoming message from the underlying transport. All incoming raw data packets are
   * processed by this thread.
   */
  private final ExecutorService asyncInputReceptionProcessor;
  /**
   * The thread pool of input message processors. All incoming messages are processed by this thread pool after they
   * have been decoded by the asyncInputReceptionProcessor thread.
   */
  private final ExecutorService asyncInputDataProcessors;
  /**
   * The map of message queues, segregated by transaction id.
   */
  private final Map<Long, GENIncomingMessageProcessor> transactionQueues = new HashMap<Long, GENIncomingMessageProcessor>();
  /**
   * Map of outgoing channels. This associates a URI to a transport resource that is able to send messages to this URI.
   */
  private final Map<String, GENConcurrentMessageSender> outgoingDataChannels = Collections.synchronizedMap(new HashMap<String, GENConcurrentMessageSender>());
  /**
   * The stream factory used for encoding and decoding messages.
   */
  private final MALElementStreamFactory streamFactory;
  /**
   * The base string for URL for this protocol.
   */
  protected String uriBase;

  /**
   * Constructor.
   *
   * @param protocol The protocol string.
   * @param serviceDelim The delimiter to use for separating the URL
   * @param supportsRouting True if routing is supported by the naming convention
   * @param wrapBodyParts True is body parts should be wrapped in BLOBs
   * @param factory The factory that created us.
   * @param properties The QoS properties.
   * @throws MALException On error.
   */
  public GENTransport(final String protocol,
          final char serviceDelim,
          final boolean supportsRouting,
          final boolean wrapBodyParts,
          final MALTransportFactory factory,
          final java.util.Map properties) throws MALException
  {
    this.factory = factory;
    this.protocol = protocol;
    this.supportsRouting = supportsRouting;
    this.protocolDelim = "://";
    this.serviceDelim = serviceDelim;
    this.routingDelim = '@';
    this.qosProperties = properties;
    this.streamFactory = MALElementStreamFactory.newFactory(protocol, properties);

    LOGGER.log(Level.INFO, "GEN Creating element stream : {0}", streamFactory.getClass().getName());

    // very crude and faulty test but it will do for testing
    this.streamHasStrings = streamFactory.getClass().getName().contains("String");

    // default values
    boolean lLogFullDebug = false;
    boolean lWrapBodyParts = wrapBodyParts;
    boolean lInProcessSupport = true;
    int lInputProcessorThreads = 100;
    int lNumConnections = 1;

    // decode configuration
    if (properties != null)
    {
      lLogFullDebug = properties.containsKey(DEBUG_PROPERTY);

      if (properties.containsKey(WRAP_PROPERTY))
      {
        lWrapBodyParts = Boolean.parseBoolean((String) properties.get(WRAP_PROPERTY));
      }

      if (properties.containsKey(INPROC_PROPERTY))
      {
        lInProcessSupport = Boolean.parseBoolean((String) properties.get(INPROC_PROPERTY));
      }

      // number of internal threads that process incoming MAL packets
      if (properties.containsKey(INPUT_PROCESSORS_PROPERTY))
      {
        lInputProcessorThreads = Integer.parseInt((String) properties.get(INPUT_PROCESSORS_PROPERTY));
      }

      // number of connections per client/server
      if (properties.containsKey(NUM_CLIENT_CONNS_PROPERTY))
      {
        lNumConnections = Integer.parseInt((String) properties.get(NUM_CLIENT_CONNS_PROPERTY));
      }
    }

    this.logFullDebug = lLogFullDebug;
    this.wrapBodyParts = lWrapBodyParts;
    this.inProcessSupport = lInProcessSupport;
    this.inputProcessorThreads = lInputProcessorThreads;
    this.numConnections = lNumConnections;

    this.asyncInputReceptionProcessor = Executors.newSingleThreadExecutor();
    this.asyncInputDataProcessors = Executors.newFixedThreadPool(inputProcessorThreads);

    LOGGER.log(Level.INFO, "GEN Wrapping body parts set to  : {0}", this.wrapBodyParts);
  }

  /**
   * Constructor.
   *
   * @param protocol The protocol string.
   * @param protocolDelim The delimiter to use for separating the protocol part in the URL
   * @param serviceDelim The delimiter to use for separating the URL
   * @param routingDelim The delimiter to use for separating the URL for routing
   * @param supportsRouting True if routing is supported by the naming convention
   * @param wrapBodyParts True is body parts should be wrapped in BLOBs
   * @param factory The factory that created us.
   * @param properties The QoS properties.
   * @throws MALException On error.
   */
  public GENTransport(final String protocol,
          final String protocolDelim,
          final char serviceDelim,
          final char routingDelim,
          final boolean supportsRouting,
          final boolean wrapBodyParts,
          final MALTransportFactory factory,
          final java.util.Map properties) throws MALException
  {
    this.factory = factory;
    this.protocol = protocol;
    this.supportsRouting = supportsRouting;
    this.protocolDelim = protocolDelim;
    this.serviceDelim = serviceDelim;
    this.routingDelim = routingDelim;
    this.qosProperties = properties;
    streamFactory = MALElementStreamFactory.newFactory(protocol, properties);

    LOGGER.log(Level.INFO, "GEN Creating element stream : {0}", streamFactory.getClass().getName());

    // very crude and faulty test but it will do for testing
    streamHasStrings = streamFactory.getClass().getName().contains("String");

    // default values
    boolean lLogFullDebug = false;
    boolean lWrapBodyParts = wrapBodyParts;
    boolean lInProcessSupport = true;
    int lInputProcessorThreads = 100;
    int lNumConnections = 1;

    // decode configuration
    if (properties != null)
    {
      lLogFullDebug = properties.containsKey(DEBUG_PROPERTY);

      if (properties.containsKey(WRAP_PROPERTY))
      {
        lWrapBodyParts = Boolean.parseBoolean((String) properties.get(WRAP_PROPERTY));
      }

      if (properties.containsKey(INPROC_PROPERTY))
      {
        lInProcessSupport = Boolean.parseBoolean((String) properties.get(INPROC_PROPERTY));
      }

      // number of internal threads that process incoming MAL packets
      if (properties.containsKey(INPUT_PROCESSORS_PROPERTY))
      {
        lInputProcessorThreads = Integer.parseInt((String) properties.get(INPUT_PROCESSORS_PROPERTY));
      }

      // number of connections per client/server
      if (properties.containsKey(NUM_CLIENT_CONNS_PROPERTY))
      {
        lNumConnections = Integer.parseInt((String) properties.get(NUM_CLIENT_CONNS_PROPERTY));
      }
    }

    this.logFullDebug = lLogFullDebug;
    this.wrapBodyParts = lWrapBodyParts;
    this.inProcessSupport = lInProcessSupport;
    this.inputProcessorThreads = lInputProcessorThreads;
    this.numConnections = lNumConnections;

    asyncInputReceptionProcessor = Executors.newSingleThreadExecutor();
    asyncInputDataProcessors = Executors.newFixedThreadPool(inputProcessorThreads);

    LOGGER.log(Level.INFO, "GEN Wrapping body parts set to  : {0}", this.wrapBodyParts);
  }

  /**
   * Initialises this transport.
   *
   * @throws MALException On error
   */
  public void init() throws MALException
  {
    String protocolString = protocol;
    if (protocol.contains(":"))
    {
      protocolString = protocol.substring(0, protocol.indexOf(':'));
    }

    uriBase = protocolString + protocolDelim + createTransportAddress() + serviceDelim;
  }

  @Override
  public MALEndpoint createEndpoint(final String localName, final Map qosProperties) throws MALException
  {
    final String strLocalName = getLocalName(localName);
    GENEndpoint endpoint = endpointMap.get(strLocalName);

    if (null == endpoint)
    {
      LOGGER.log(Level.INFO, "GEN Creating endpoint {0}", strLocalName);
      endpoint = internalCreateEndpoint(strLocalName, qosProperties);
      endpointMap.put(strLocalName, endpoint);
    }

    return endpoint;
  }

  @Override
  public MALEndpoint getEndpoint(final String localName) throws IllegalArgumentException
  {
    return endpointMap.get(localName);
  }

  @Override
  public MALEndpoint getEndpoint(final URI uri) throws IllegalArgumentException
  {
    String endpointUriPart = uri.getValue();
    final int iFirst = endpointUriPart.indexOf(serviceDelim);
    endpointUriPart = endpointUriPart.substring(iFirst + 1, endpointUriPart.length());

    return endpointMap.get(endpointUriPart);
  }

  /**
   * Returns the stream factory.
   *
   * @return the stream factory
   */
  public MALElementStreamFactory getStreamFactory()
  {
    return streamFactory;
  }

  /**
   * Overridable internal method for the creation of receiving messages.
   *
   * @param ios The input stream to use.
   * @return The new message.
   * @throws MALException on Error.
   */
  public GENMessage createMessage(final java.io.InputStream ios) throws MALException
  {
    return new GENMessage(wrapBodyParts, true, new GENMessageHeader(), qosProperties, ios, getStreamFactory());
  }

  /**
   * Overridable internal method for the creation of receiving messages.
   *
   * @param packet The input packet to use.
   * @return The new message.
   * @throws MALException on Error.
   */
  public GENMessage createMessage(final byte[] packet) throws MALException
  {
    return new GENMessage(wrapBodyParts, true, new GENMessageHeader(), qosProperties, packet, getStreamFactory());
  }

  /**
   * On reception of an IO stream this method should be called. This is the main reception entry point into the generic
   * transport for stream based transports.
   *
   * @param ios The stream being received.
   * @param receptionHandler
   */
  public void receive(final java.io.InputStream ios, GENReceptionHandler receptionHandler)
  {
    asyncInputReceptionProcessor.submit(new GENIncomingMessageReceiver(ios, receptionHandler));
  }

  /**
   * On reception of a packet this method should be called. This is the main reception entry point into the generic
   * transport.
   *
   * @param rawMessage The raw message being received.
   * @param receptionHandler NULL if the transport does not support bi-directional communications
   */
  public void receive(final byte[] rawMessage, GENReceptionHandler receptionHandler)
  {
    asyncInputReceptionProcessor.submit(new GENIncomingMessageReceiver(rawMessage, receptionHandler));
  }

  /**
   * The main exit point for messages from this transport.
   *
   * @param multiSendHandle A context handle for multi send
   * @param lastForHandle True if that is the last message in a multi send for the handle
   * @param msg The message to send.
   * @throws MALTransmitErrorException On transmit error.
   */
  public void sendMessage(final Object multiSendHandle,
          final boolean lastForHandle,
          final GENMessage msg) throws MALTransmitErrorException
  {
    // first check if its actually a message to ourselves
    String endpointUriPart = getRoutingPart(msg.getHeader().getURITo().getValue(), serviceDelim, routingDelim, supportsRouting);

    if (inProcessSupport && endpointMap.containsKey(endpointUriPart))
    {
      LOGGER.log(Level.INFO, "GEN routing msg internally to {0}", new Object[]
      {
        endpointUriPart
      });

      // if local then just send internally
      processIncomingMessage(msg, "");
    }
    else
    {
      try
      {
        // get the root URI, (e.g. tcpip://10.0.0.1:61616 )
        String destinationURI = msg.getHeader().getURITo().getValue();
        String remoteRootURI = getRootURI(destinationURI);

        LOGGER.log(Level.INFO, "GEN sending msg. Target root URI: {0} full URI:{1}", new Object[]
        {
          remoteRootURI, destinationURI
        });

        // get outgoing channel
        GENConcurrentMessageSender dataSender = manageCommunicationChannel(msg, false, null);

        GENOutgoingMessageHolder outgoingPacket = internalEncodeMessage(remoteRootURI, destinationURI, multiSendHandle, lastForHandle, dataSender.getTargetURI(), msg);

        dataSender.sendMessage(outgoingPacket);

        if (!outgoingPacket.getResult())
        {
          // data was not sent succesfully, throw an exception for the
          // higher MAL layers
          throw new MALTransmitErrorException(msg.getHeader(), new MALStandardError(MALHelper.DELIVERY_FAILED_ERROR_NUMBER, null), null);
        }

        LOGGER.log(Level.INFO, "GEN finished Sending data to {0}", remoteRootURI);
      }
      catch (MALTransmitErrorException e)
      {
        // this stops any true MAL exceptoins getting caught by the generic catch all below
        throw e;
      }
      catch (InterruptedException e)
      {
        LOGGER.log(Level.SEVERE, "Interrupted while waiting for data reply", e);
        throw new MALTransmitErrorException(msg.getHeader(), new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER, null), null);
      }
      catch (Exception t)
      {
        LOGGER.log(Level.SEVERE, "GEN could not send message!", t);
        throw new MALTransmitErrorException(msg.getHeader(), new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER, null), null);
      }
    }
  }

  /**
   * Used to request the transport close a connection with a client. In this case the transport will terminate all
   * communication channels with the destination in order for them to be re-established.
   *
   * @param uriTo the connection handler that received this message
   * @param receptionHandler
   */
  public void closeConnection(final String uriTo, final GENReceptionHandler receptionHandler)
  {
    String localUriTo = uriTo;
    // remove all associations with this target URI
    if ((null == localUriTo) && (null != receptionHandler))
    {
      localUriTo = receptionHandler.getRemoteURI();
    }

    if (localUriTo != null)
    {
      GENConcurrentMessageSender commsChannel = outgoingDataChannels.get(localUriTo);
      if (commsChannel != null)
      {
        commsChannel.terminate();
        outgoingDataChannels.remove(localUriTo);
      }
      else
      {
        LOGGER.log(Level.FINE, "Could not locate associated data to close communications for URI : {0} ", localUriTo);
      }
    }

    if (null != receptionHandler)
    {
      receptionHandler.close();
    }
  }

  /**
   * Used to inform the transport about communication problems with clients. In this case the transport will terminate
   * all communication channels with the destination in order for them to be re-established.
   *
   * @param uriTo the connection handler that received this message
   * @param receptionHandler
   */
  public void communicationError(String uriTo, GENReceptionHandler receptionHandler)
  {
    LOGGER.log(Level.WARNING, "GEN Communication Error with {0} ", uriTo);

    closeConnection(uriTo, receptionHandler);
  }

  @Override
  public void deleteEndpoint(final String localName) throws MALException
  {
    final GENEndpoint endpoint = endpointMap.get(localName);

    if (null != endpoint)
    {
      LOGGER.log(Level.INFO, "GEN Deleting endpoint", localName);
      endpointMap.remove(localName);
      endpoint.close();
    }
  }

  @Override
  public void close() throws MALException
  {
    for (Map.Entry<String, GENEndpoint> entry : endpointMap.entrySet())
    {
      entry.getValue().close();
    }

    endpointMap.clear();

    asyncInputReceptionProcessor.shutdown();
    asyncInputDataProcessors.shutdown();

    for (Map.Entry<String, GENConcurrentMessageSender> entry : outgoingDataChannels.entrySet())
    {
      final GENConcurrentMessageSender sender = entry.getValue();

      sender.terminate();
    }

    outgoingDataChannels.clear();
  }

  /**
   * This method receives an incoming message and adds to to the correct queue based on its transaction id.
   *
   * @param malMsg the message
   */
  protected void receiveIncomingMessage(final MessageDetails malMsg)
  {
    synchronized (transactionQueues)
    {
      GENIncomingMessageProcessor proc = transactionQueues.get(malMsg.transactionId);

      if (null == proc)
      {
        proc = new GENIncomingMessageProcessor(malMsg);
        transactionQueues.put(malMsg.transactionId, proc);
        asyncInputDataProcessors.submit(proc);
      }
      else
      {
        if (proc.addMessage(malMsg))
        {
          // need to resubmit this to the processing threads
          asyncInputDataProcessors.submit(proc);
        }
      }

      Set<Long> transactionsToRemove = new HashSet<Long>();
      for (Map.Entry<Long, GENIncomingMessageProcessor> entrySet : transactionQueues.entrySet())
      {
        Long key = entrySet.getKey();
        GENIncomingMessageProcessor lproc = entrySet.getValue();

        if (lproc.isFinished())
        {
          transactionsToRemove.add(key);
        }
      }

      for (Long transId : transactionsToRemove)
      {
        transactionQueues.remove(transId);
      }
    }
  }

  /**
   * This method processes an incoming message by routing it to the appropriate endpoint, returning an error if the
   * message cannot be processed.
   *
   * @param msg The source message.
   * @param smsg The message in a string representation for logging.
   */
  protected void processIncomingMessage(final GENMessage msg, String smsg)
  {
    try
    {
      LOGGER.log(Level.INFO, "GEN Processing message : {0} : {1}", new Object[]
      {
        msg.getHeader().getTransactionId(), smsg
      });

      String endpointUriPart = getRoutingPart(msg.getHeader().getURITo().getValue(), serviceDelim, routingDelim, supportsRouting);

      final GENEndpoint oSkel = endpointMap.get(endpointUriPart);

      if (null != oSkel)
      {
        LOGGER.log(Level.INFO, "GEN Passing to message handler " + oSkel.getLocalName() + " : {0}", smsg);
        oSkel.receiveMessage(msg);
      }
      else
      {
        LOGGER.log(Level.WARNING, "GEN Message handler NOT FOUND " + endpointUriPart + " : {0}", smsg);
        returnErrorMessage(null,
                msg,
                MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER,
                "GEN Cannot find endpoint: " + endpointUriPart);
      }
    }
    catch (Exception e)
    {
      LOGGER.log(Level.WARNING, "GEN Error occurred when receiving data : {0}", e);

      final StringWriter wrt = new StringWriter();
      e.printStackTrace(new PrintWriter(wrt));

      try
      {
        returnErrorMessage(null,
                msg,
                MALHelper.INTERNAL_ERROR_NUMBER,
                "GEN Error occurred: " + e.toString() + " : " + wrt.toString());
      }
      catch (MALException ex)
      {
        LOGGER.log(Level.SEVERE, "GEN Error occurred when return error data : {0}", ex);
      }
    }
  }

  /**
   * Creates a return error message based on a received message.
   *
   * @param ep The endpoint to use for sending the error.
   * @param oriMsg The original message
   * @param errorNumber The error number
   * @param errorMsg The error message.
   * @throws MALException if cannot encode a response message
   */
  protected void returnErrorMessage(GENEndpoint ep,
          final GENMessage oriMsg,
          final UInteger errorNumber,
          final String errorMsg) throws MALException
  {
    try
    {
      final int type = oriMsg.getHeader().getInteractionType().getOrdinal();
      final short stage = oriMsg.getHeader().getInteractionStage().getValue();

      // first check that message should be responded to
      if (((type == InteractionType._SUBMIT_INDEX) && (stage == MALSubmitOperation._SUBMIT_STAGE))
              || ((type == InteractionType._REQUEST_INDEX) && (stage == MALRequestOperation._REQUEST_STAGE))
              || ((type == InteractionType._INVOKE_INDEX) && (stage == MALInvokeOperation._INVOKE_STAGE))
              || ((type == InteractionType._PROGRESS_INDEX) && (stage == MALProgressOperation._PROGRESS_STAGE))
              || ((type == InteractionType._PUBSUB_INDEX) && (stage == MALPubSubOperation._REGISTER_STAGE))
              || ((type == InteractionType._PUBSUB_INDEX) && (stage == MALPubSubOperation._DEREGISTER_STAGE))
              || ((type == InteractionType._PUBSUB_INDEX) && (stage == MALPubSubOperation._PUBLISH_REGISTER_STAGE))
              || ((type == InteractionType._PUBSUB_INDEX) && (stage == MALPubSubOperation._PUBLISH_DEREGISTER_STAGE)))
      {
        final MALMessageHeader srcHdr = oriMsg.getHeader();

        if ((null == ep) && (!endpointMap.isEmpty()))
        {
          GENEndpoint endpoint = endpointMap.entrySet().iterator().next().getValue();

          final GENMessage retMsg = (GENMessage) endpoint.createMessage(srcHdr.getAuthenticationId(),
                  srcHdr.getURIFrom(),
                  new Time(new Date().getTime()),
                  srcHdr.getQoSlevel(),
                  srcHdr.getPriority(),
                  srcHdr.getDomain(),
                  srcHdr.getNetworkZone(),
                  srcHdr.getSession(),
                  srcHdr.getSessionName(),
                  srcHdr.getInteractionType(),
                  new UOctet((short) (srcHdr.getInteractionStage().getValue() + 1)),
                  srcHdr.getTransactionId(),
                  srcHdr.getServiceArea(),
                  srcHdr.getService(),
                  srcHdr.getOperation(),
                  srcHdr.getAreaVersion(),
                  true,
                  oriMsg.getQoSProperties(),
                  errorNumber, new Union(errorMsg));

          sendMessage(null, true, retMsg);
        }
      }
    }
    catch (MALTransmitErrorException ex)
    {
      LOGGER.log(Level.WARNING, "GEN Error occurred when attempting to return previous error : {0}", ex);
    }
  }

  /**
   * Returns the local name or creates a random one if null.
   *
   * @param localName The existing local name string to check.
   * @return The local name to use.
   */
  protected String getLocalName(String localName)
  {
    if ((null == localName) || (0 == localName.length()))
    {
      localName = String.valueOf(RANDOM_NAME.nextInt());
    }

    return localName;
  }

  /**
   * Returns the "root" URI from the full URI. The root URI only contains the protocol and the main destination and is
   * something unique for all URIs of the same MAL.
   *
   * @param fullURI the full URI, for example tcpip://10.0.0.1:61616-serviceXYZ
   * @return the root URI, for example tcpip://10.0.0.1:61616
   */
  protected String getRootURI(String fullURI)
  {
    // get the root URI, (e.g. tcpip://10.0.0.1:61616 )
    int serviceDelimPosition = fullURI.indexOf(serviceDelim);

    if (serviceDelimPosition < 0)
    {
      // does not exist, return as is      
      return fullURI;
    }

    return fullURI.substring(0, serviceDelimPosition);
  }

  /**
   * Returns the routing part of the URI.
   *
   * @param uriValue The URI value
   * @param serviceDelim The service delimiter
   * @param routingDelim The routing delimiter
   * @param supportsRouting True if this URI scheme supporting routing
   * @return the routing part of the URI
   */
  protected static String getRoutingPart(String uriValue, char serviceDelim, char routingDelim, boolean supportsRouting)
  {
    String endpointUriPart = uriValue;
    final int iFirst = endpointUriPart.indexOf(serviceDelim);
    int iSecond = supportsRouting ? endpointUriPart.indexOf(routingDelim) : endpointUriPart.length();
    if (0 > iSecond)
    {
      iSecond = endpointUriPart.length();
    }

    return endpointUriPart.substring(iFirst + 1, iSecond);
  }

  /**
   * Converts the packet to a string form for logging.
   *
   * @param data the packet.
   * @return the string representation.
   */
  protected String packetToString(final byte[] data)
  {
    if (logFullDebug)
    {
      if (streamHasStrings)
      {
        return new String(data, UTF8_CHARSET);
      }
      else
      {
        return GENHelper.byteArrayToHexString(data);
      }
    }
    else
    {
      return "";
    }
  }

  /**
   * Overridable internal method for the creation of endpoints.
   *
   * @param localName The local name to use.
   * @param qosProperties the QoS properties.
   * @return The new endpoint
   * @throws MALException on Error.
   */
  protected GENEndpoint internalCreateEndpoint(final String localName, final Map qosProperties) throws MALException
  {
    return new GENEndpoint(this, localName, uriBase + localName, wrapBodyParts);
  }

  /**
   * This method checks if there is a communication channel for sending a particular message and in addition stores the
   * communication channel on incoming messages in case of bi-directional transports for re-use. If there is no
   * communication channel for sending a message the transport creates and registers it.
   *
   * @param msg The message received or to be sent
   * @param isIncomingMsgDirection the message direction
   * @param receptionHandler the message reception handler, null if the message is an outgoing message
   * @return returns an existing or newly created message sender
   * @throws MALTransmitErrorException in case of communication problems
   */
  protected synchronized GENConcurrentMessageSender manageCommunicationChannel(GENMessage msg, boolean isIncomingMsgDirection, GENReceptionHandler receptionHandler) throws MALTransmitErrorException
  {
    GENConcurrentMessageSender sender = null;

    if (isIncomingMsgDirection)
    {
      // incoming msg
      if ((null != receptionHandler) && (null == receptionHandler.getRemoteURI()))
      {
        // transport supports bi-directional communication
        // this is the first message received form this reception handler
        // add the remote base URI it is receiving messages from
        String sourceURI = msg.getHeader().getURIFrom().getValue();
        String sourceRootURI = getRootURI(sourceURI);

        receptionHandler.setRemoteURI(sourceRootURI);

        //register the communication channel with this URI if needed
        sender = registerMessageSender(receptionHandler.getMessageSender(), sourceRootURI);
      }
    }
    else
    {
      // outgoing message
      // get target URI
      String remoteRootURI = getRootURI(msg.getHeader().getURITo().getValue());

      // get sender if it exists
      sender = outgoingDataChannels.get(remoteRootURI);

      if (null == sender)
      {
        // we do not have any channel for this URI
        // try to create a set of connections to this URI 
        LOGGER.log(Level.INFO, "GEN received request to create connections to URI:{0}", remoteRootURI);

        try
        {
          // create new sender for this URI
          sender = registerMessageSender(createMessageSender(msg, remoteRootURI), remoteRootURI);

          LOGGER.log(Level.INFO, "GEN opening {0}", numConnections);

          for (int i = 1; i < numConnections; i++)
          {
            // insert new processor (message sender) to root data sender for the URI
            sender.addProcessor(createMessageSender(msg, remoteRootURI), remoteRootURI);
          }
        }
        catch (MALException e)
        {
          LOGGER.log(Level.WARNING, "GEN could not connect to :" + remoteRootURI, e);
          throw new MALTransmitErrorException(msg.getHeader(),
                  new MALStandardError(MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER, null), null);
        }
      }
    }

    return sender;
  }

  /**
   * Registers a message sender for a given root URI. If this is the first data sender for the URI, it also creates a
   * GENConcurrentMessageSender to manage all the senders. If there are already enough connections (numConnections) to
   * the given URI the method does not register the sender. This ensures that we will have at maximum numConnections to
   * the target root URI.
   *
   * @param dataTransmitter The data sender that is able to send messages to the URI
   * @param remoteRootURI the remote root URI
   * @return returns the GENConcurrentMessageSender for this URI.
   */
  protected synchronized GENConcurrentMessageSender registerMessageSender(GENMessageSender dataTransmitter, String remoteRootURI)
  {
    //check if we already have a communication channel for this URI
    GENConcurrentMessageSender dataSender = outgoingDataChannels.get(remoteRootURI);
    if (dataSender != null)
    {
      //we already have a communication channel for this URI
      //check if we have enough connections for the URI, if not then add the data sender 
      if (dataSender.getNumberOfProcessors() < numConnections)
      {
        LOGGER.log(Level.INFO, "GEN registering data sender for URI:{0}", remoteRootURI);
        // insert new processor (message sender) to root data sender for the URI
        dataSender.addProcessor(dataTransmitter, remoteRootURI);
      }
    }
    else
    {
      //we do not have a communication channel, create a data sender manager and add the first data sender
      // create new sender manager for this URI
      LOGGER.log(Level.INFO, "GEN creating data sender manager for URI:{0}", remoteRootURI);
      dataSender = new GENConcurrentMessageSender(this, remoteRootURI);

      LOGGER.log(Level.INFO, "GEN registering data sender for URI:{0}", remoteRootURI);
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
  protected GENOutgoingMessageHolder internalEncodeMessage(final String destinationRootURI,
          final String destinationURI,
          final Object multiSendHandle,
          final boolean lastForHandle,
          final String targetURI,
          final GENMessage msg) throws Exception
  {
    // encode the message
    try
    {
      final ByteArrayOutputStream baos = new ByteArrayOutputStream();
      final MALElementOutputStream enc = getStreamFactory().createOutputStream(baos);
      msg.encodeMessage(getStreamFactory(), enc, baos);
      byte[] data = baos.toByteArray();

      // message is encoded!
      LOGGER.log(Level.INFO, "GEN Sending data to {0} : {1}", new Object[]
      {
        targetURI, packetToString(data)
      });

      return new GENOutgoingMessageHolder(destinationRootURI, destinationURI, multiSendHandle, lastForHandle, data);
    }
    catch (MALException ex)
    {
      LOGGER.log(Level.SEVERE, "GEN could not encode message!", ex);
      throw new MALTransmitErrorException(msg.getHeader(), new MALStandardError(MALHelper.BAD_ENCODING_ERROR_NUMBER, null), null);
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
   * Method to be implemented by the transport in order to return a message sender capable if sending messages to a
   * target root URI.
   *
   * @param msg the message to be send
   * @param remoteRootURI the remote root URI.
   * @return returns a message sender capable of sending messages to the target URI
   * @throws MALException in case of error trying to create the communication channel
   * @throws MALTransmitErrorException in case of error connecting to the target URI
   */
  protected abstract GENMessageSender createMessageSender(GENMessage msg, String remoteRootURI) throws MALException, MALTransmitErrorException;

  /**
   * Simple structure class for holding related aspects of a decoded MAL message.
   */
  protected static final class MessageDetails
  {
    /**
     * The transaction id of this message.
     */
    public final Long transactionId;
    /**
     * The decoded MAL message.
     */
    public final GENMessage malMsg;
    /**
     * A string representation for debug tracing.
     */
    public final String smsg;

    /**
     * Constructor.
     *
     * @param transactionId the message transaction id.
     * @param malMsg The decoded MAL message.
     * @param smsg A string representation for debug tracing.
     */
    public MessageDetails(final Long transactionId, final GENMessage malMsg, final String smsg)
    {
      this.transactionId = transactionId;
      this.malMsg = malMsg;
      this.smsg = smsg;
    }
  }

  /**
   * This Runnable task is responsible for holding newly arrived MAL Messages (in raw format), decoding, and passing to
   * the transport executor.
   *
   */
  private final class GENIncomingMessageReceiver implements Runnable
  {
    private final byte[] rawMessage;
    private final java.io.InputStream ioMessage;
    private final GENReceptionHandler receptionHandler;

    /**
     * Constructor
     *
     * @param rawMessage The raw message
     * @param receptionHandler The reception handler to pass them to.
     */
    public GENIncomingMessageReceiver(byte[] rawMessage, GENReceptionHandler receptionHandler)
    {
      this.rawMessage = rawMessage;
      this.ioMessage = null;
      this.receptionHandler = receptionHandler;
    }

    /**
     * Constructor
     *
     * @param ioMessage The raw message
     * @param receptionHandler The reception handler to pass them to.
     */
    public GENIncomingMessageReceiver(java.io.InputStream ioMessage, GENReceptionHandler receptionHandler)
    {
      this.rawMessage = null;
      this.ioMessage = ioMessage;
      this.receptionHandler = receptionHandler;
    }

    /**
     * This method processes an incoming message and then forwards it for routing to the appropriate message queue. The
     * processing consists of transforming the raw message to the appropriate format and then registering if necessary
     * the communication channel.
     */
    @Override
    public void run()
    {
      try
      {
        String smsg;
        GENMessage malMsg;

        if (null == rawMessage)
        {
          // create message
          smsg = "";
          malMsg = createMessage(ioMessage);
        }
        else
        {
          // create message
          smsg = packetToString(rawMessage);
          malMsg = createMessage(rawMessage);
        }
        LOGGER.log(Level.INFO, "GEN Receving message : {0}", smsg);

        //register communication channel if needed
        manageCommunicationChannel(malMsg, true, receptionHandler);

        receiveIncomingMessage(new MessageDetails(malMsg.getHeader().getTransactionId(), malMsg, smsg));
      }
      catch (MALException e)
      {
        LOGGER.log(Level.WARNING, "GEN Error occurred when decoding data : {0}", e);
        communicationError(null, receptionHandler);
      }
      catch (MALTransmitErrorException e)
      {
        LOGGER.log(Level.WARNING, "GEN Error occurred when decoding data : {0}", e);
        communicationError(null, receptionHandler);
      }
    }
  }

  /**
   * This Runnable task is responsible for processing the already decoded message. It holds a queue of messages split on
   * transaction id so that messages with the same transaction id get processed in reception order.
   *
   */
  private final class GENIncomingMessageProcessor implements Runnable
  {
    private final Queue<MessageDetails> malMsgs = new ArrayDeque<MessageDetails>();
    private boolean finished = false;

    /**
     * Constructor
     *
     * @param malMsg The MAL message.
     */
    public GENIncomingMessageProcessor(final MessageDetails malMsg)
    {
      malMsgs.add(malMsg);
    }

    /**
     * Adds a message to the internal queue. If the thread associated with this executor has finished it resets the flag
     * and returns true to indicate that it should be resubmitted for more processing to the Executor pool.
     *
     * @param malMsg The decoded message.
     * @return True if this needs to be resubmitted to the processing executor pool.
     */
    public synchronized boolean addMessage(final MessageDetails malMsg)
    {
      malMsgs.add(malMsg);

      if (finished)
      {
        finished = false;

        // need to resubmit this to the processing threads
        return true;
      }

      return false;
    }

    /**
     * Returns true if this thread has finished processing its queue.
     *
     * @return True if finished processing queue.
     */
    public boolean isFinished()
    {
      return finished;
    }

    @Override
    public void run()
    {
      MessageDetails msg;

      synchronized (this)
      {
        msg = malMsgs.poll();
      }

      while (null != msg)
      {
        // send message for further processing and routing
        processIncomingMessage(msg.malMsg, msg.smsg);

        synchronized (this)
        {
          msg = malMsgs.poll();

          if (null == msg)
          {
            finished = true;
          }
        }
      }
    }
  }
}
