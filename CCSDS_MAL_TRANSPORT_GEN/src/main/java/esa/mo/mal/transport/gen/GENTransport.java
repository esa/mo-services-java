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
import esa.mo.mal.transport.gen.sending.GENDataTransmitter;
import esa.mo.mal.transport.gen.sending.GENOutgoingDataHolder;
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
public abstract class GENTransport implements MALTransport, GENSender
{
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
   * True if want to log the packet data
   */
  protected final boolean logFullDebug;
  /**
   * The string used to represent this protocol.
   */
  protected final String protocol;
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
   * The thread pool of input data processors. All incoming raw data packets are processed by this thread pool.
   */
  private final ExecutorService asyncInputDataProcessors;
  /**
   * Map of outgoing channels. This associates a URI to a transport resource that is able to send messages to this URI.
   */
  private final Map<String, GENConcurrentMessageSender> outgoingDataChannels;
  /**
   * Map of string names to endpoints.
   */
  protected final Map<String, GENEndpoint> endpointMap = new HashMap<String, GENEndpoint>();
  /**
   * The base string for URL for this protocol.
   */
  protected String uriBase;
  static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
  private final MALElementStreamFactory streamFactory;
  protected final Map qosProperties;

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
    streamFactory = MALElementStreamFactory.newFactory(protocol, properties);

    LOGGER.log(Level.INFO, "GEN Creating element stream : {0}", streamFactory.getClass().getName());

    // very crude and faulty test but it will do for testing
    streamHasStrings = streamFactory.getClass().getName().contains("String");

    // decode configuration
    if (properties != null)
    {
      logFullDebug = properties.containsKey("org.ccsds.moims.mo.mal.transport.gen.debug");

      this.wrapBodyParts = Boolean.parseBoolean((String) properties.get("org.ccsds.moims.mo.mal.transport.gen.wrap"));

      // number of internal threads that process incoming MAL packets
      if (properties.containsKey("org.ccsds.moims.mo.mal.transport.gen.inputprocessors"))
      {
        this.inputProcessorThreads = Integer.parseInt((String) properties.get("org.ccsds.moims.mo.mal.transport.gen.inputprocessors"));
      }
      else
      {
        this.inputProcessorThreads = 20;
      }

      // number of connections per client/server
      if (properties.containsKey("org.ccsds.moims.mo.mal.transport.gen.numconnections"))
      {
        this.numConnections = Integer.parseInt((String) properties.get("org.ccsds.moims.mo.mal.transport.gen.numconnections"));
      }
      else
      {
        this.numConnections = 1;
      }
    }
    else
    {
      // default values
      this.logFullDebug = false;
      this.wrapBodyParts = wrapBodyParts;
      this.inputProcessorThreads = 20;
      this.numConnections = 1;
    }

    asyncInputDataProcessors = Executors.newFixedThreadPool(inputProcessorThreads);
    outgoingDataChannels = Collections.synchronizedMap(new HashMap<String, GENConcurrentMessageSender>());

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

    // decode configuration
    if (properties != null)
    {
      logFullDebug = properties.containsKey("org.ccsds.moims.mo.mal.transport.gen.debug");

      this.wrapBodyParts = Boolean.parseBoolean((String) properties.get("org.ccsds.moims.mo.mal.transport.gen.wrap"));

      // number of internal threads that process incoming MAL packets
      if (properties.containsKey("org.ccsds.moims.mo.mal.transport.gen.inputprocessors"))
      {
        this.inputProcessorThreads = Integer.parseInt((String) properties.get("org.ccsds.moims.mo.mal.transport.gen.inputprocessors"));
      }
      else
      {
        this.inputProcessorThreads = 20;
      }
      // number of connections per client/server
      if (properties.containsKey("org.ccsds.moims.mo.mal.transport.gen.numconnections"))
      {
        this.numConnections = Integer.parseInt((String) properties.get("org.ccsds.moims.mo.mal.transport.gen.numconnections"));
      }
      else
      {
        this.numConnections = 1;
      }
    }
    else
    {
      // default values
      this.logFullDebug = false;
      this.wrapBodyParts = wrapBodyParts;
      this.inputProcessorThreads = 20;
      this.numConnections = 1;
    }

    asyncInputDataProcessors = Executors.newFixedThreadPool(inputProcessorThreads);
    outgoingDataChannels = Collections.synchronizedMap(new HashMap<String, GENConcurrentMessageSender>());

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
      protocolString = protocol.substring(0, protocol.indexOf(":"));
    }

    uriBase = protocolString + protocolDelim + createTransportAddress() + serviceDelim;
  }

  @Override
  public MALEndpoint createEndpoint(final String localName, final Map qosProperties) throws MALException
  {
    final String strLocalName = getLocalName(localName);
    GENEndpoint endpoint = (GENEndpoint) endpointMap.get(strLocalName);

    if (null == endpoint)
    {
      LOGGER.log(Level.INFO, "GEN Creating endpoint ", strLocalName);
      endpoint = internalCreateEndpoint(strLocalName, qosProperties);
      endpointMap.put(strLocalName, endpoint);
    }

    return endpoint;
  }

  @Override
  public MALEndpoint getEndpoint(final String localName) throws IllegalArgumentException
  {
    return (GENEndpoint) endpointMap.get(localName);
  }

  @Override
  public MALEndpoint getEndpoint(final URI uri) throws IllegalArgumentException
  {
    String endpointUriPart = uri.getValue();
    final int iFirst = endpointUriPart.indexOf(serviceDelim);
    endpointUriPart = endpointUriPart.substring(iFirst + 1, endpointUriPart.length());

    return (GENEndpoint) endpointMap.get(endpointUriPart);
  }

  @Override
  public void deleteEndpoint(final String localName) throws MALException
  {
    final GENEndpoint endpoint = (GENEndpoint) endpointMap.get(localName);

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
      final GENEndpoint ep = entry.getValue();
      ep.close();
    }

    endpointMap.clear();

    asyncInputDataProcessors.shutdown();

    for (Map.Entry<String, GENConcurrentMessageSender> entry : outgoingDataChannels.entrySet())
    {
      final GENConcurrentMessageSender sender = entry.getValue();

      sender.terminate();
    }

    outgoingDataChannels.clear();
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
   * On reception of an IO stream this method should be called. This is the main reception entry point into the generic
   * transport for stream based transports.
   *
   * @param ios The stream being received.
   * @param receptionHandler
   */
  public void receive(final java.io.InputStream ios, GENReceptionHandler receptionHandler)
  {
    asyncInputDataProcessors.submit(new GENIncomingDataProcessor(this, ios, receptionHandler));
  }

  /**
   * On reception of a packet this method should be called. This is the main reception entry point into the generic
   * transport.
   *
   * @param rawMessage The raw message being received.
   * @param receptionHandler
   */
  public void receive(final byte[] rawMessage, GENReceptionHandler receptionHandler)
  {
    asyncInputDataProcessors.submit(new GENIncomingDataProcessor(this, rawMessage, receptionHandler));
  }

  @Override
  public void sendMessage(final GENEndpoint ep,
          final Object handle,
          final boolean lastForHandle,
          final GENMessage msg) throws MALTransmitErrorException
  {
    // first check if its actually a message to ourselves
    String endpointUriPart = getRoutingPart(msg.getHeader().getURITo().getValue(), serviceDelim, routingDelim, supportsRouting);

    if (endpointMap.containsKey(endpointUriPart))
    {
      LOGGER.log(Level.INFO, "GEN routing msg internally to {0}", new Object[]
      {
        endpointUriPart
      });

      // if local then just send internally
      receiveMessageThreadMain(msg, "");
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
        GENConcurrentMessageSender dataSender = checkConnections(msg, remoteRootURI, null);

        GENOutgoingDataHolder outgoingPacket = internalEncodeMessage(dataSender.getTargetURI(), msg);

        dataSender.sendMessage(outgoingPacket);

        Boolean dataSendResult = Boolean.FALSE;
        try
        {
          dataSendResult = outgoingPacket.getResult();
        }
        catch (InterruptedException e)
        {
          LOGGER.log(Level.SEVERE, "Interrupted while waiting for data reply", e);
          throw new MALTransmitErrorException(msg.getHeader(), new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER, null), null);
        }

        if (!dataSendResult)
        {
        // data was not sent succesfully, throw an exception for the
          // higher MAL layers
          throw new MALTransmitErrorException(msg.getHeader(), new MALStandardError(MALHelper.DELIVERY_FAILED_ERROR_NUMBER, null), null);
        }

        LOGGER.log(Level.INFO, "GEN finished Sending data to {0}", remoteRootURI);
      }
      catch (Exception t)
      {
        LOGGER.log(Level.SEVERE, "GEN could not send message!", t);
        throw new MALTransmitErrorException(msg.getHeader(), new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER, null), null);
      }
    }
  }

  protected void internalMessageReceive(final java.io.InputStream ios, GENReceptionHandler receptionHandler)
  {
    LOGGER.log(Level.INFO, "GEN Receiving data (creating thread) : ");

    if (null != ios)
    {
      try
      {
        GENMessage malMsg = createMessage(ios);

        checkConnections(malMsg, null, receptionHandler);

        receiveMessageThreadMain(malMsg, "");
      }
      catch (Exception e)
      {
        LOGGER.log(Level.WARNING, "GEN Error occurred when decoding data : {0}", e);

        final StringWriter wrt = new StringWriter();
        e.printStackTrace(new PrintWriter(wrt));

        communicationError(null, receptionHandler);
      }
    }
  }

  protected void internalMessageReceive(final byte[] rawMessage, GENReceptionHandler receptionHandler)
  {
    final String smsg = packetToString(rawMessage);
    LOGGER.log(Level.INFO, "GEN Receiving data (creating thread) : {0}", smsg);

    if (null != rawMessage)
    {
      try
      {
        GENMessage malMsg = createMessage(rawMessage);

        checkConnections(malMsg, null, receptionHandler);

        receiveMessageThreadMain(malMsg, smsg);
      }
      catch (Exception e)
      {
        LOGGER.log(Level.WARNING, "GEN Error occurred when decoding data : {0}", e);

        final StringWriter wrt = new StringWriter();
        e.printStackTrace(new PrintWriter(wrt));

        communicationError(null, receptionHandler);
      }
    }
  }

  protected void receiveMessageThreadMain(final GENMessage msg, String smsg)
  {
    try
    {
      LOGGER.log(Level.INFO, "GEN Receiving and processing data : {0}", smsg);

      String endpointUriPart = getRoutingPart(msg.getHeader().getURITo().getValue(), serviceDelim, routingDelim, supportsRouting);

      final GENEndpoint oSkel = (GENEndpoint) endpointMap.get(endpointUriPart);

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
        LOGGER.log(Level.SEVERE, "GEN Error occurred when return error data : {0}", e);
      }
    }
  }

  /**
   * Used to request the transport close a connection with a client. In this case the transport will terminate
   * all communication channels with the destination in order for them to be re-established.
   *
   * @param uriTo the connection handler that received this message
   * @param receptionHandler
   */
  public void closeConnection(String uriTo, GENReceptionHandler receptionHandler)
  {
    // remove all associations with this target URI
    if ((null == uriTo) && (null != receptionHandler))
    {
      uriTo = receptionHandler.getRemoteURI();
    }

    if (uriTo != null)
    {
      GENConcurrentMessageSender commsChannel = outgoingDataChannels.get(uriTo);
      if (commsChannel != null)
      {
        commsChannel.terminate();
        outgoingDataChannels.remove(uriTo);
      }
      else
      {
        LOGGER.log(Level.FINE, "Could not locate associated data to close communications for URI : {0} ", uriTo);
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

  /**
   * Creates a return error message based on a received message.
   *
   * @param ep The endpoint to use for sending the error.
   * @param oriMsg The original message
   * @param errorNumber The error number
   * @param errorMsg The error message.
   * @throws org.ccsds.moims.mo.mal.MALException if cannot encode a response message
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

        if ((null == ep) && (0 < endpointMap.size()))
        {
          ep = endpointMap.entrySet().iterator().next().getValue();

          final GENMessage retMsg = (GENMessage) ep.createMessage(srcHdr.getAuthenticationId(),
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

          sendMessage(ep, null, true, retMsg);
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
    }
    String rootURI = fullURI.substring(0, serviceDelimPosition);
    return rootURI;
  }

  protected static String getRoutingPart(String uriValue, char serviceDelim, char routingDelim, boolean supportsRouting)
  {
    String endpointUriPart = uriValue;
    final int iFirst = endpointUriPart.indexOf(serviceDelim);
    int iSecond = (supportsRouting ? endpointUriPart.indexOf(routingDelim) : endpointUriPart.length());
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
        return byteArrayToHexString(data);
      }
    }
    else
    {
      return "";
    }
  }

  /**
   * Creates a string version of byte buffer in hex.
   *
   * @param data the packet.
   * @return the string representation.
   */
  public static String byteArrayToHexString(final byte[] data)
  {
    final StringBuilder hexString = new StringBuilder();

    if (null != data)
    {
      for (int i = 0; i < data.length; i++)
      {
        final String hex = Integer.toHexString(0xFF & data[i]);
        if (hex.length() == 1)
        {
          // could use a for loop, but we're only dealing with a single byte
          hexString.append('0');
        }
        hexString.append(hex);
      }
    }

    return hexString.toString();
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
   * Creates the part of the URL specific to this transport instance.
   *
   * @return The transport specific address part.
   * @throws MALException On error
   */
  protected abstract String createTransportAddress() throws MALException;

  protected synchronized GENConcurrentMessageSender checkConnections(GENMessage msg, String remoteRootURI, GENReceptionHandler receptionHandler) throws MALTransmitErrorException
  {
    if (null == remoteRootURI)
    {
      if (null == receptionHandler)
      {
        String destinationURI = msg.getHeader().getURITo().getValue();
        remoteRootURI = getRootURI(destinationURI);
      }
      else
      {
        remoteRootURI = receptionHandler.getRemoteURI();

        // need to do a bit of slight of hand here
        if (null == remoteRootURI)
        {
          String destinationURI = msg.getHeader().getURIFrom().getValue();
          remoteRootURI = getRootURI(destinationURI);

          receptionHandler.setRemoteURI(remoteRootURI);

          registerDataSender(receptionHandler.getTransportTransmitter(), remoteRootURI);
        }
      }
    }

    GENConcurrentMessageSender dataSender = outgoingDataChannels.get(remoteRootURI);

    if (dataSender == null)
    {
      // we do not have any connections to this client
      // try to create a set of connections to this URI 
      LOGGER.log(Level.INFO, "GEN received request to create connections to URI:{0}", remoteRootURI);

      try
      {
        // create new sender for this URI
        dataSender = registerDataSender(createDataReceiver(msg, remoteRootURI), remoteRootURI);

        LOGGER.log(Level.INFO, "GEN opening {0}", numConnections);

        for (int i = 1; i < numConnections; i++)
        {
          // insert new processor (data sender) to root data sender for the URI        	
          dataSender.addProcessor(createDataReceiver(msg, remoteRootURI), remoteRootURI);
        }
      }
      catch (MALException e)
      {
        LOGGER.log(Level.WARNING, "GEN could not connect to :" + remoteRootURI, e);
        throw new MALTransmitErrorException(msg.getHeader(), new MALStandardError(MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER, null), null);

      }
    }

    return dataSender;
  }

  public synchronized GENConcurrentMessageSender registerDataSender(GENDataTransmitter dataTransmitter, String remoteRootURI)
  {
    // create new sender for this URI
    GENConcurrentMessageSender dataSender = new GENConcurrentMessageSender(this, remoteRootURI);

    LOGGER.log(Level.INFO, "GEN registering data sender for URI:{0}", remoteRootURI);
    outgoingDataChannels.put(remoteRootURI, dataSender);

    // insert new processor (data sender) to root data sender for the URI        	
    dataSender.addProcessor(dataTransmitter, remoteRootURI);

    return dataSender;
  }

  protected abstract GENDataTransmitter createDataReceiver(GENMessage msg, String remoteRootURI) throws MALException, MALTransmitErrorException;

  protected GENOutgoingDataHolder internalEncodeMessage(final String targetURI,
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

      return new GENOutgoingDataHolder(data);
    }
    catch (MALException ex)
    {
      LOGGER.log(Level.SEVERE, "GEN could not encode message!", ex);
      throw new MALTransmitErrorException(msg.getHeader(), new MALStandardError(MALHelper.BAD_ENCODING_ERROR_NUMBER, null), null);
    }
  }

  /**
   * This Runnable task is responsible for holding newly arrived MAL Messages (in raw format) and passing to the
   * transport executor.
   *
   */
  protected final class GENIncomingDataProcessor implements Runnable
  {
    private final GENTransport transport;
    private final byte[] rawMessage;
    private final java.io.InputStream ioMessage;
    private final GENReceptionHandler receptionHandler;

    public GENIncomingDataProcessor(GENTransport transport, byte[] rawMessage, GENReceptionHandler receptionHandler)
    {
      this.transport = transport;
      this.rawMessage = rawMessage;
      this.ioMessage = null;
      this.receptionHandler = receptionHandler;
    }

    public GENIncomingDataProcessor(GENTransport transport, java.io.InputStream ioMessage, GENReceptionHandler receptionHandler)
    {
      this.transport = transport;
      this.rawMessage = null;
      this.ioMessage = ioMessage;
      this.receptionHandler = receptionHandler;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run()
    {
      if (null == rawMessage)
      {
        transport.internalMessageReceive(ioMessage, receptionHandler);
      }
      else
      {
        transport.internalMessageReceive(rawMessage, receptionHandler);
      }
    }
  }
}
