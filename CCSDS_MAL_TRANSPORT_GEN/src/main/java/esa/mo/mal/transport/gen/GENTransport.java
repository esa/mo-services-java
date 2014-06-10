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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.*;
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
   * Set of bad URLs
   */
  protected final Set badUrls = new TreeSet();
  /**
   * Map of string names to endpoints.
   */
  protected final Map<String, GENEndpoint> endpointMap = new TreeMap<String, GENEndpoint>();
  /**
   * List of outgoing messages for the message pump.
   */
  protected final List outgoingMessageList = new LinkedList();
  /**
   * The base string for URL for this protocol.
   */
  protected String uriBase;
  static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
  private final MALElementStreamFactory streamFactory;
  private Thread asyncSendThread = null;

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
    streamFactory = MALElementStreamFactory.newFactory(protocol, properties);

    LOGGER.log(Level.INFO, "GEN Creating element stream : {0}", streamFactory.getClass().getName());

    // very crude and faulty test but it will do for testing
    streamHasStrings = streamFactory.getClass().getName().contains("String");

    logFullDebug = (null != properties) && (properties.containsKey("org.ccsds.moims.mo.mal.transport.gen.debug"));

    if ((null != properties) && (properties.containsKey("org.ccsds.moims.mo.mal.transport.gen.wrap")))
    {
      this.wrapBodyParts = Boolean.parseBoolean((String) properties.get("org.ccsds.moims.mo.mal.transport.gen.wrap"));
    }
    else
    {
      this.wrapBodyParts = wrapBodyParts;
    }

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
    streamFactory = MALElementStreamFactory.newFactory(protocol, properties);

    LOGGER.log(Level.INFO, "GEN Creating element stream : {0}", streamFactory.getClass().getName());

    // very crude and faulty test but it will do for testing
    streamHasStrings = streamFactory.getClass().getName().contains("String");

    logFullDebug = (null != properties) && (properties.containsKey("org.ccsds.moims.mo.mal.transport.gen.debug"));

    if ((null != properties) && (properties.containsKey("org.ccsds.moims.mo.mal.transport.gen.wrap")))
    {
      this.wrapBodyParts = Boolean.parseBoolean((String) properties.get("org.ccsds.moims.mo.mal.transport.gen.wrap"));
    }
    else
    {
      this.wrapBodyParts = wrapBodyParts;
    }

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
  }

  /**
   * On reception of an IO stream this method should be called. This is the main reception entry point into the generic
   * transport for stream based transports.
   *
   * @param ios The stream being received.
   */
  public void receive(final java.io.InputStream ios)
  {
    LOGGER.log(Level.INFO, "GEN Receiving data (creating thread) : ");

    if (null != ios)
    {
      // create a thread to Receive, so we can do it asynchronously
      final Thread oAsyncReceiveThread = new Thread()
      {
        @Override
        public void run()
        {
          try
          {
            receiveMessageThreadMain(createMessage(ios), "");
          }
          catch (Exception e)
          {
            LOGGER.log(Level.WARNING, "GEN Error occurred when decoding data : {0}", e);

            final StringWriter wrt = new StringWriter();
            e.printStackTrace(new PrintWriter(wrt));
          }
        }
      };

      oAsyncReceiveThread.start();
    }
  }

  /**
   * On reception of a packet this method should be called. This is the main reception entry point into the generic
   * transport.
   *
   * @param packet The packet being received.
   */
  public void receive(final byte[] packet)
  {
    final String smsg = packetToString(packet);
    LOGGER.log(Level.INFO, "GEN Receiving data (creating thread) : {0}", smsg);

    if (null != packet)
    {
      // create a thread to Receive, so we can do it asynchronously
      final Thread oAsyncReceiveThread = new Thread()
      {
        @Override
        public void run()
        {
          try
          {
            receiveMessageThreadMain(createMessage(packet), smsg);
          }
          catch (Exception e)
          {
            LOGGER.log(Level.WARNING, "GEN Error occurred when decoding data : {0}", e);

            final StringWriter wrt = new StringWriter();
            e.printStackTrace(new PrintWriter(wrt));
          }
        }
      };

      oAsyncReceiveThread.start();
    }
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

  @Override
  public void sendMessage(final GENEndpoint ep,
          final Object handle,
          final boolean lastForHandle,
          final GENMessage msg) throws MALTransmitErrorException
  {
    final String strURL = msg.getHeader().getURITo().getValue();
    final int iSecond = strURL.indexOf(serviceDelim);
    final String oRemoteObjectKey = strURL.substring(0, iSecond);

    // check to see if we've already had a problem with this remote object
    synchronized (badUrls)
    {
      if (badUrls.contains(oRemoteObjectKey))
      {
        badUrls.remove(oRemoteObjectKey);

        throw new MALTransmitErrorException(msg.getHeader(),
                new MALStandardError(MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER, null),
                msg.getQoSProperties());
      }
    }

    // add it to the outgoing message list and start a mnessage pump if one already not running
    synchronized (outgoingMessageList)
    {
      outgoingMessageList.add(new MsgPair(ep, oRemoteObjectKey, strURL, handle, lastForHandle, msg));

      if (null == asyncSendThread)
      {
        createSendMessagePump();
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

  private void createSendMessagePump()
  {
    // create a thread to Send, so we can do it asynchronously
    asyncSendThread = new Thread()
    {
      @Override
      public void run()
      {
        // we loop whilst there are still messages to process
        boolean bContinue = true;

        while (bContinue)
        {
          MsgPair tmsg = null;

          // pop a message from the list
          synchronized (outgoingMessageList)
          {
            if (0 < outgoingMessageList.size())
            {
              tmsg = (MsgPair) outgoingMessageList.remove(0);
            }
          }

          // although we never put null in the list, because of the sync logic above there is a small chance tmsg
          // could be null
          if (null != tmsg)
          {
            try
            {
              internalSendMessage(tmsg);
            }
            catch (Exception e)
            {
              synchronized (badUrls)
              {
                badUrls.add(tmsg.addr);
              }
              LOGGER.log(Level.WARNING, "GEN Error occurred when sending data : {0}", e);
            }
          }

          // we leave the test to exit until here to ensure that all has been sent before we exit
          synchronized (outgoingMessageList)
          {
            if (0 == outgoingMessageList.size())
            {
              bContinue = false;
              asyncSendThread = null;
            }
          }
        }
      }
    };

    asyncSendThread.start();
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
  protected GENMessage createMessage(final java.io.InputStream ios) throws MALException
  {
    return new GENMessage(wrapBodyParts, true, new GENMessageHeader(), ios, getStreamFactory());
  }

  /**
   * Overridable internal method for the creation of receiving messages.
   *
   * @param packet The input packet to use.
   * @return The new message.
   * @throws MALException on Error.
   */
  protected GENMessage createMessage(final byte[] packet) throws MALException
  {
    return new GENMessage(wrapBodyParts, true, new GENMessageHeader(), packet, getStreamFactory());
  }

  /**
   * Creates the part of the URL specific to this transport instance.
   *
   * @return The transport specific address part.
   * @throws MALException On error
   */
  protected abstract String createTransportAddress() throws MALException;

  /**
   * Performs the actual transport specific message send.
   *
   * @param tmsg The message to send.
   * @throws Exception On error.
   */
  protected abstract void internalSendMessage(final MsgPair tmsg) throws Exception;

  /**
   * Small struct style class for holding message details for sending.
   */
  public static final class MsgPair
  {
    /**
     * The source endpoint.
     */
    public final GENEndpoint srcEp;
    /**
     * The destination address for the transport.
     */
    public final String addr;
    /**
     * The full destination URL
     */
    public final String url;
    /**
     * A multi send context handle, may be null.
     */
    public final Object handle;
    /**
     * True if last send for the above handle.
     */
    public final boolean lastForHandle;
    /**
     * The source message
     */
    public final GENMessage msg;

    /**
     * Constructor.
     *
     * @param srcEp The source endpoint.
     * @param addr The destination address for the transport.
     * @param url The full destination URL
     * @param handle A multi send context handle, may be null.
     * @param lastForHandle True if last send for the above handle.
     * @param msg The source message
     */
    public MsgPair(final GENEndpoint srcEp,
            final String addr,
            final String url,
            final Object handle,
            final boolean lastForHandle,
            final GENMessage msg)
    {
      this.srcEp = srcEp;
      this.addr = addr;
      this.url = url;
      this.handle = handle;
      this.lastForHandle = lastForHandle;
      this.msg = msg;
    }
  }
}
