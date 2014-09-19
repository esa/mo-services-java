/* ----------------------------------------------------------------------------
 * Copyright (C) 2014      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO TCP/IP Transport Framework
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
package esa.mo.mal.transport.tcpip;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.rmi.server.UID;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInvokeOperation;
import org.ccsds.moims.mo.mal.MALProgressOperation;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALTransmitErrorException;
import org.ccsds.moims.mo.mal.transport.MALTransmitMultipleErrorException;
import org.ccsds.moims.mo.mal.transport.MALTransport;
import org.ccsds.moims.mo.mal.transport.MALTransportFactory;

import esa.mo.mal.transport.gen.GENMessage;
import esa.mo.mal.transport.gen.GENMessageHeader;
import esa.mo.mal.transport.tcpip.util.TCPIPClientConnectionDataReceiver;
import esa.mo.mal.transport.tcpip.util.TCPIPConcurrentSocketDataSender;
import esa.mo.mal.transport.tcpip.util.TCPIPConnectionDataReceiver;
import esa.mo.mal.transport.tcpip.util.TCPIPInputDataForwarder;
import esa.mo.mal.transport.tcpip.util.TCPIPOutgoingDataHolder;
import esa.mo.mal.transport.tcpip.util.TCPIPServerConncetionListener;

/**
 * The TCPIP MAL Transport implementation.
 *
 * The following properties configure the transport:
 *
 * org.ccsds.moims.mo.mal.transport.tcpip.wrap org.ccsds.moims.mo.mal.transport.tcpip.debug ==> debug mode , affects
 * logging org.ccsds.moims.mo.mal.transport.tcpip.numconnections ==> number of connections to a different MAL (either
 * server / client) org.ccsds.moims.mo.mal.transport.tcpip.inputprocessors ==> number of threads processing in parallel
 * raw MAL messages org.ccsds.moims.mo.mal.transport.tcpip.host ==> adapter (host / IP Address) that the transport will
 * use for incoming connections. In case of a pure client (i.e. not offering any services) this property should be
 * omitted. org.ccsds.moims.mo.mal.transport.tcpip.port ==> port that the transport listens to. In case this is a pure
 * client, this property should be omitted.
 *
 * The general logic is the following : The transport at first initialises the server listen port (if this is a server,
 * offering services).
 *
 * On receiving a request to send a MAL Message the transport tries to find if it has allocated some recourses
 * associated with the target URI (has already the means to exchange data with it) and if not, it creates
 * -numconnections- connections to the target server. If a client has already opened a connection to a server the server
 * will re-use that communication channel to send back data to the client.
 *
 * On the server, each incoming connection is handled separately by a different thread which on the first message
 * reception associates the remote URI with the connection (socket). This has the consequence that if the server wants
 * to either use a service, or reply to the remote URI, it will use on of these already allocated communication
 * resources.
 *
 * In the case of malformed MAL messages or communication errors, all resources related to the remote URI are released
 * and need to be reestablished.
 *
 * URIs:
 *
 * The TCPIP Transpost, generates URIs, in the for of : tcpip://<host>:<port or client ID>-<service id>
 * There are two categories of URIs Client URIs, which are in the form of tcpip://<host>:<clientId>-<serviceId> , where
 * the client id is a unique identifier for the client on its host, for example : 4783fbc147ab7aa56e7fff and ServerURIs,
 * which are in the form of tcpip://<host>:<port>-<serviceId> and clients can actively connect to.
 *
 * If a MAL instance does not offer any services then all of its endpoints get a Client URI. If a MAL instance offers at
 * least one service then all of its endpoints get a Server URI. A service provider communicates with a service consumer
 * with the communication channel that the service consumer initiated (uses bidirectional TCP/IP communication).
 *
 */
public class TCPIPTransport implements MALTransport
{
  /**
   * Logger
   */
  public static final java.util.logging.Logger LOGGER = Logger.getLogger("org.ccsds.moims.mo.mal.transport.tcpip");

  /**
   * Number of processors that are capable of processing parallel input requests. This is the internal number of threads
   * that process incoming messages arriving from MAL clients. It is the maximum parallel requests this MAL instance can
   * concurrently serve.
   */
  public final int inputProcessorThreads;

  /**
   * The server port that the TCP transport listens for incoming connections
   */
  private final int serverPort;

  /**
   * The number of connections (sockets) per client or server. The Transport will connect numConnections times to the
   * predefined port and host per different client/server.
   */
  private final int numConnections;

  /**
   * Server host, this can be one of the IP Addresses / hostnames of the host.
   */
  private final String serverHost;

  /**
   * The base string for URL for this protocol.
   */
  private final String uriBase;

  /**
   * Used to create random local names for endpoints.
   */
  private final Random RANDOM_NAME = new Random();

  /**
   * Reference to our factory.
   */
  private final MALTransportFactory factory;
  /**
   * The delimiter to use to separate the protocol part from the address part of the URL.
   */
  private final String protocolDelim;
  /**
   * The delimiter to use to separate the external address part from the internal object part of the URL.
   */
  private final char serviceDelim;
  /**
   * Delimiter to use when holding routing information in a URL
   */
  private final char routingDelim;
  /**
   * True if protocol supports the concept of routing.
   */
  private final boolean supportsRouting;
  /**
   * True if string based stream, can be logged as a string rather than hex.
   */
  private final boolean streamHasStrings;
  /**
   * True if body parts should be wrapped in blobs for encoded element support.
   */
  private final boolean wrapBodyParts;
  /**
   * True if want to log the packet data
   */
  private final boolean logFullDebug;
  /**
   * The string used to represent this protocol.
   */
  private final String protocol;

  /**
   * Map of string names to endpoints.
   */
  private final Map<String, TCPIPEndpoint> endpointMap = new TreeMap<String, TCPIPEndpoint>();

  // charset
  private final Charset UTF8_CHARSET = Charset.forName("UTF-8");

  // stream factory
  private final MALElementStreamFactory streamFactory;

  // port delimiter
  private final char portDelimiter = ':';

  /**
   * Map of outgoing channels. This associates a URI to a transport resource that is able to send messages to this URI.
   */
  private final Map<String, TCPIPConcurrentSocketDataSender> outgoingDataChannels;

  /**
   * The thread pool of input data processors. All incoming raw data packets are processed by this thread pool.
   */
  private final ExecutorService asyncInputDataProcessors;

  /*
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
  public TCPIPTransport(final String protocol, final char serviceDelim, final boolean supportsRouting, final MALTransportFactory factory, final java.util.Map properties) throws MALException
  {
    this.factory = factory;
    this.protocol = protocol;
    this.supportsRouting = supportsRouting;
    this.protocolDelim = "://";
    this.serviceDelim = serviceDelim; // typically '-'
    this.routingDelim = '@';

    // create stream factory
    streamFactory = MALElementStreamFactory.newFactory(protocol, properties);
    // very crude and faulty test but it will do for testing
    streamHasStrings = streamFactory.getClass().getName().contains("String");

    LOGGER.log(Level.INFO, "TCPIP Creating element stream : {0}", streamFactory.getClass().getName());

    // decode configuration
    if (properties != null)
    {
      // wrap body parts
      if (properties.containsKey("org.ccsds.moims.mo.mal.transport.tcpip.wrap"))
      {
        this.wrapBodyParts = Boolean.parseBoolean((String) properties.get("org.ccsds.moims.mo.mal.transport.tcpip.wrap"));
      }
      else
      {
        this.wrapBodyParts = false;
      }

      // debug
      logFullDebug = properties.containsKey("org.ccsds.moims.mo.mal.transport.tcpip.debug");

      // number of connections per client/server
      if (properties.containsKey("org.ccsds.moims.mo.mal.transport.tcpip.numconnections"))
      {
        this.numConnections = Integer.parseInt((String) properties.get("org.ccsds.moims.mo.mal.transport.tcpip.numconnections"));
      }
      else
      {
        this.numConnections = 10;
      }

      // number of internal threads that process incoming MAL packets
      if (properties.containsKey("org.ccsds.moims.mo.mal.transport.tcpip.inputprocessors"))
      {
        this.inputProcessorThreads = Integer.parseInt((String) properties.get("org.ccsds.moims.mo.mal.transport.tcpip.inputprocessors"));
      }
      else
      {
        this.inputProcessorThreads = 20;
      }

      // host / ip adress
      if (properties.containsKey("org.ccsds.moims.mo.mal.transport.tcpip.host"))
      {
        this.serverHost = (String) properties.get("org.ccsds.moims.mo.mal.transport.tcpip.host");
      }
      else
      {
        this.serverHost = null; // this is only a client
      }

      // port
      if (properties.containsKey("org.ccsds.moims.mo.mal.transport.tcpip.port"))
      {
        this.serverPort = Integer.parseInt((String) properties.get("org.ccsds.moims.mo.mal.transport.tcpip.port"));
      }
      else
      {
        if (serverHost != null)
        {
          //this is a server, use default port
          this.serverPort = 61616;
        }
        else
        {
          //this is a client
          this.serverPort = 0; //0 means this is a client
        }

      }
    }
    else
    {
      // default values
      this.wrapBodyParts = false;
      this.logFullDebug = false;
      this.numConnections = 10;
      this.inputProcessorThreads = 20;
      this.serverPort = 0; //0 means this is a client
      this.serverHost = null; //null means this is a client
    }

    // uri base
    String protocolString = protocol;
    if (protocol.contains(":"))
    {
      protocolString = protocol.substring(0, protocol.indexOf(":"));
    }
    if (serverHost == null)
    {
	    //this is a pure client
      //in this case we get the IP Address of the host and provide a unique id as the port.
      //the actual IP and port information does not matter as the server will not try
      //to connect to it, it is used as an identifier for the MAL un the URI.
      uriBase = protocolString + protocolDelim + getDefaultHost() + portDelimiter + getRandomClientId() + serviceDelim;
    }
    else
    {
      //this a server (and potentially a client)
      uriBase = protocolString + protocolDelim + serverHost + portDelimiter + serverPort + serviceDelim;
    }

    outgoingDataChannels = Collections.synchronizedMap(new HashMap<String, TCPIPConcurrentSocketDataSender>());

    asyncInputDataProcessors = Executors.newFixedThreadPool(inputProcessorThreads);

    LOGGER.log(Level.INFO, "TCPIP Wrapping body parts set to  : {0}", this.wrapBodyParts);

  }

  /**
   * Initialises this transport.
   *
   * @throws MALException On error
   */
  public void init() throws MALException
  {

    if (serverHost != null)
    {
      // this is also a server (i.e. provides some services)
      LOGGER.log(Level.INFO, "Starting TCP Server Transport on port {0}", serverPort);

      // start server socket on predefined port / interface
      try
      {
        InetAddress serverHostAddr = InetAddress.getByName(serverHost);
        ServerSocket serverSocket = new ServerSocket(serverPort, 0, serverHostAddr);

        // create thread that will listen for connections
        TCPIPServerConncetionListener serverConnectionListener = new TCPIPServerConncetionListener(this, serverSocket);
        serverConnectionListener.start();

        LOGGER.log(Level.INFO, "Started TCP Server Transport on port {0}", serverPort);

      }
      catch (Exception ex)
      {
        throw new MALException("Error initialising TCP Server", ex);
      }
    }

  }

  @Override
  public MALEndpoint createEndpoint(final String localName, final Map qosProperties) throws MALException
  {

	// endpoints are created by clients and servers in order to send
    // messages
    final String strLocalName = getLocalName(localName);
    TCPIPEndpoint endpoint = (TCPIPEndpoint) endpointMap.get(strLocalName);

    if (null == endpoint)
    {
      LOGGER.log(Level.INFO, "TCPIP Creating endpoint " + strLocalName);
      endpoint = internalCreateEndpoint(strLocalName, qosProperties);
      LOGGER.log(Level.INFO, "TCPIP Created endpoint " + endpoint.getURI());
      endpointMap.put(strLocalName, endpoint);
    }

    return endpoint;
  }

  @Override
  public MALEndpoint getEndpoint(final String localName) throws IllegalArgumentException
  {
    return (TCPIPEndpoint) endpointMap.get(localName);
  }

  @Override
  public MALEndpoint getEndpoint(final URI uri) throws IllegalArgumentException
  {
    String endpointUriPart = uri.getValue();
    final int iFirst = endpointUriPart.indexOf(serviceDelim);
    endpointUriPart = endpointUriPart.substring(iFirst + 1, endpointUriPart.length());

    return (TCPIPEndpoint) endpointMap.get(endpointUriPart);
  }

  @Override
  public void deleteEndpoint(final String localName) throws MALException
  {
    final TCPIPEndpoint endpoint = (TCPIPEndpoint) endpointMap.get(localName);

    if (null != endpoint)
    {
      LOGGER.log(Level.INFO, "TCPIP Deleting endpoint " + localName);
      endpointMap.remove(localName);
      endpoint.close();
    }
  }

  @Override
  public void close() throws MALException
  {
    for (Map.Entry<String, TCPIPEndpoint> entry : endpointMap.entrySet())
    {
      final TCPIPEndpoint ep = entry.getValue();
      ep.close();
    }

    endpointMap.clear();
  }

  /**
   * Entry point for receiving a MAL Message.
   *
   * This method receives a raw MAL message, tries to decode it and then passes it to the higher layers of the MAL. If
   * the message cannot be decoded, it will close the transport resource that delivered this message and if this is a
   * resource already associated with a URI, it will close all associations (sockets, resources) with the particular
   * URI.
   *
   * On receiving the first message from a transport resource, it will associate the remote URI with the resource in
   * order to be able to send messages to this URI.
   *
   * This is typically called by the low level transport threads that receive the raw data.
   *
   * @param rawMessage the raw message received in bytes
   * @param receptionHandler the connection handler that received this message
   */
  public void receive(byte[] rawMessage, TCPIPConnectionDataReceiver receptionHandler)
  {

    GENMessage malMsg = null;
    try
    {
      String rawDataToHex = packetToString(rawMessage);
      LOGGER.log(Level.INFO, "TCPIP Receiving and processing data : {0}", rawDataToHex);

      // parse / convert to MAL Message
      try
      {
        malMsg = createMessage(rawMessage);
      }
      catch (MALException mex)
      {
        try
        {
          // try to close this socket
          receptionHandler.getSocket().close();
        }
        catch (IOException e)
        {/* ignore */

        }

		// remove all associations with this target URI
        // if the error came from a transport resource that has an
        // associated URI (i.e. has received at least one good message, or is a client socket reader in which
        // case the socket is already associated at creation time)
        if (receptionHandler.getRemoteURI() != null)
        {
          this.communicationError(receptionHandler.getRemoteURI());
        }
        // do not further process the incoming data since it cannot be decoded
        return;
      }

	    // here we have decoded a MAL Message
      // see if there is an associated URI for this resource and if not create one
      if (receptionHandler.getRemoteURI() == null)
      {
		// this is the first message we receive from this
        // receptionHandler so we need to associate it with the remote URI

        // get root URI and set it to the reception handler
        String remoteURI = malMsg.getHeader().getURIFrom().getValue();
        String remoteRootURI = getRootURI(remoteURI);
        receptionHandler.setRemoteURI(remoteRootURI);

        // register this resource with the URI
        registerChannel(receptionHandler.getSocket(), receptionHandler.getRemoteURI());
      }

	    // route the message to the higher MAL layer
	    // get the endpoint that we must send this message
      // String endpointUriPart = getRootURI(malMsg.getHeader().getURITo().getValue());
      String endpointUriPart = getEndpointId(malMsg.getHeader().getURITo().getValue(), serviceDelim, routingDelim, supportsRouting);

      TCPIPEndpoint targetEndpoint = (TCPIPEndpoint) endpointMap.get(endpointUriPart);

      if (null != targetEndpoint)
      {
        LOGGER.log(Level.INFO, "TCPIP Passing to message handler " + targetEndpoint.getLocalName());
        targetEndpoint.receiveMessage(malMsg);
      }
      else
      {
        LOGGER.log(Level.WARNING, "TCPIP Message handler NOT FOUND " + endpointUriPart);
        returnErrorMessage(null, malMsg, MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER, "Cannot find endpoint: " + endpointUriPart);
      }
    }
    catch (Exception e)
    {
      LOGGER.log(Level.WARNING, "Error occurred when receiving data : {0}", e);

      final StringWriter wrt = new StringWriter();
      e.printStackTrace(new PrintWriter(wrt));

      try
      {
        returnErrorMessage(null, malMsg, MALHelper.INTERNAL_ERROR_NUMBER, "Error occurred: " + e.toString() + " : " + wrt.toString());
      }
      catch (MALException ex)
      {
        LOGGER.log(Level.SEVERE, "Error occurred when return error data : {0}", e);
      }
    }

  }

  /**
   * This method is the main entry point for sending a message to another MAL through the transport. It finds the
   * appropriate transport resources associated to the URI to route this message to and if they do not exist it creates
   * them.
   *
   * It then encodes and forwards the message to the appropriate transport resource and waits for a reply indicating if
   * the transport was Successful in sending this message.
   *
   * It is necessary for the caller thread to verify that the message was correctly sent otherwise there are no means to
   * inform the higher leyers of the MAL that something went wrong.
   *
   * @param ep The endpoint sending the message.
   * @param msg The message to send.
   * @throws MALTransmitErrorException On transmit error.
   */
  public void sendMessage(final TCPIPEndpoint ep, final GENMessage msg) throws MALTransmitErrorException
  {
    try
    {
      // get the root URI, (e.g. tcpip://10.0.0.1:61616 )
      String destinationURI = msg.getHeader().getURITo().getValue();
      String remoteRootURI = getRootURI(destinationURI);

      LOGGER.log(Level.INFO, "TCPIP sending msg. Target root URI: " + remoteRootURI + " full URI:" + destinationURI);

      // get outgoing channel
      TCPIPConcurrentSocketDataSender dataSender = outgoingDataChannels.get(remoteRootURI);
      if (dataSender == null)
      {
        // we do not have any connections to this client
        try
        {
          // try to create a set of connections to this URI 
          createInitialConnections(remoteRootURI);

        }
        catch (UnknownHostException e)
        {
          LOGGER.log(Level.WARNING, "TCPIP cound not connect to :" + remoteRootURI, e);
          throw new MALTransmitErrorException(msg.getHeader(), new MALStandardError(MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER, null), null);

        }
        catch (IOException e)
        {
          LOGGER.log(Level.WARNING, "TCPIP cound not connect to :" + remoteRootURI, e);
          throw new MALTransmitErrorException(msg.getHeader(), new MALStandardError(MALHelper.DELIVERY_FAILED_ERROR_NUMBER, null), null);
        }
        catch (MALException e)
        {
          LOGGER.log(Level.WARNING, "TCPIP cound not connect to :" + remoteRootURI, e);
          throw new MALTransmitErrorException(msg.getHeader(), new MALStandardError(MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER, null), null);

        }

      }

      // get the data sender
      dataSender = outgoingDataChannels.get(remoteRootURI);

      // encode message
      byte[] data = null;

      // encode the message
      try
      {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final MALElementOutputStream enc = getStreamFactory().createOutputStream(baos);
        msg.encodeMessage(getStreamFactory(), enc, baos);
        data = baos.toByteArray();
      }
      catch (MALException ex)
      {
        LOGGER.log(Level.SEVERE, "TCPIP cound not encode message!", ex);
        throw new MALTransmitErrorException(msg.getHeader(), new MALStandardError(MALHelper.BAD_ENCODING_ERROR_NUMBER, null), null);
      }

      // message is encoded!
      LOGGER.log(Level.INFO, "TCPIP Sending data to {0} : {1}", new Object[]
      {
        remoteRootURI, packetToString(data)
      });
      TCPIPOutgoingDataHolder outgoingPacket = new TCPIPOutgoingDataHolder(data);
      dataSender.sendData(outgoingPacket);

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
      LOGGER.log(Level.INFO, "TCPIP finished Sending data to " + remoteRootURI);

    }
    catch (RuntimeException t)
    {
      LOGGER.log(Level.SEVERE, "TCPIP cound not send message!", t);
      throw new MALTransmitErrorException(msg.getHeader(), new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER, null), null);
    }
  }

  /**
   * This method sends a number of messages sequentially using the sendMessage method
   *
   * @param tcpipEndpoint the endpoint that the message is sent from
   * @param msg the messages
   * @throws MALTransmitMultipleErrorException in case there were errors in transmitting the messages
   */
  public void sendMessages(TCPIPEndpoint tcpipEndpoint, GENMessage[] msg) throws MALTransmitMultipleErrorException
  {
    final List<MALTransmitErrorException> v = new LinkedList<MALTransmitErrorException>();

    for (int i = 0; i < msg.length; i++)
    {
      try
      {
        sendMessage(tcpipEndpoint, msg[i]);
      }
      catch (MALTransmitErrorException mex)
      {
        v.add(mex);
      }
    }
    if (!v.isEmpty())
    {
      throw new MALTransmitMultipleErrorException(v.toArray(new MALTransmitErrorException[v.size()]));
    }
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
  private void returnErrorMessage(TCPIPEndpoint ep, final GENMessage oriMsg, final UInteger errorNumber, final String errorMsg) throws MALException
  {
    try
    {
      final int type = oriMsg.getHeader().getInteractionType().getOrdinal();
      final short stage = oriMsg.getHeader().getInteractionStage().getValue();

      // first check that message should be responded to
      if (((type == InteractionType._SUBMIT_INDEX) && (stage == MALSubmitOperation._SUBMIT_STAGE)) || ((type == InteractionType._REQUEST_INDEX) && (stage == MALRequestOperation._REQUEST_STAGE)) || ((type == InteractionType._INVOKE_INDEX) && (stage == MALInvokeOperation._INVOKE_STAGE)) || ((type == InteractionType._PROGRESS_INDEX) && (stage == MALProgressOperation._PROGRESS_STAGE)) || ((type == InteractionType._PUBSUB_INDEX) && (stage == MALPubSubOperation._REGISTER_STAGE)) || ((type == InteractionType._PUBSUB_INDEX) && (stage == MALPubSubOperation._DEREGISTER_STAGE)) || ((type == InteractionType._PUBSUB_INDEX) && (stage == MALPubSubOperation._PUBLISH_REGISTER_STAGE)) || ((type == InteractionType._PUBSUB_INDEX) && (stage == MALPubSubOperation._PUBLISH_DEREGISTER_STAGE)))
      {
        final MALMessageHeader srcHdr = oriMsg.getHeader();

        if ((null == ep) && (0 < endpointMap.size()))
        {
          ep = endpointMap.entrySet().iterator().next().getValue();

          final GENMessage retMsg = (GENMessage) ep.createMessage(srcHdr.getAuthenticationId(), srcHdr.getURIFrom(), new Time(new Date().getTime()), srcHdr.getQoSlevel(), srcHdr.getPriority(), srcHdr.getDomain(), srcHdr.getNetworkZone(), srcHdr.getSession(), srcHdr.getSessionName(), srcHdr.getInteractionType(), new UOctet((short) (srcHdr.getInteractionStage().getValue() + 1)), srcHdr.getTransactionId(), srcHdr.getServiceArea(), srcHdr.getService(), srcHdr.getOperation(), srcHdr.getAreaVersion(), true, oriMsg.getQoSProperties(), errorNumber, new Union(errorMsg));

          sendMessage(ep, retMsg);
        }
      }
    }
    catch (MALTransmitErrorException ex)
    {
      LOGGER.log(Level.WARNING, "TCPIP Error occurred when attempting to return previous error : {0}", ex);
    }
  }

  /**
   * Creates a number of connections to a URI. If the provided URI is a client URI (has a hex string in the place of the
   * port , for example: 4783fbc147ab7aa56e7fff ) then there is no attempt to make a connection (it means that there are
   * no active connections to the client)
   *
   * @param rootURI the target root URI, e.g. tcpip://10.0.0.1:61616
   * @throws IOException
   * @throws UnknownHostException
   * @throws MALException
   */
  private synchronized void createInitialConnections(String rootURI) throws UnknownHostException, IOException, MALException
  {
    LOGGER.log(Level.INFO, "TCPIP received request to create connections to URI:" + rootURI);

    if (outgoingDataChannels.get(rootURI) != null)
    {
      // exists, could happen on concurrent sends to a destination for the first time
      return;
    }

    // decode target address
    String targetAddress = rootURI.replaceAll(protocol + protocolDelim, "");
    targetAddress = targetAddress.replaceAll(protocol, ""); // in case the protocol is in the format tcpip://

    if (!targetAddress.contains(":"))
    {
      // malformed URI
      throw new MALException("Malformed URI:" + rootURI);
    }

    String host = targetAddress.split(":")[0];
    int port;
    try
    {
      //URIs are split into 2 categories, Server URIs 
      port = Integer.parseInt(targetAddress.split(":")[1]);
    }
    catch (NumberFormatException nfe)
    {
      LOGGER.log(Level.WARNING, "Have no means to communicate with client URI : " + rootURI);
      throw new MALException("Have no means to communicate with client URI : " + rootURI);
    }

    // create new sender for this URI
    TCPIPConcurrentSocketDataSender dataSender = new TCPIPConcurrentSocketDataSender(this, rootURI);
    outgoingDataChannels.put(rootURI, dataSender);

    LOGGER.log(Level.INFO, "TCPIP opening " + numConnections + " connections to host :" + host + " on port:" + port);
    try
    {
      for (int i = 0; i < numConnections; i++)
      {
        Socket socket = new Socket(host, port);
        // insert new processor (data sender) to root data sender for the URI        	
        dataSender.addProcessor(socket, rootURI);
        // create also a data reader thread for this socket in order to read messages from it        	
        new TCPIPClientConnectionDataReceiver(this, socket, rootURI).start();
      }
    }
    catch (IOException e)
    {
      //there was a communication problem, we need to clean up the objects we created in the meanwhile
      this.communicationError(rootURI);
      //rethrow for higher MAL leyers
      throw (e);
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

  /**
   * Returns the local name or creates a random one if null.
   *
   * @param localName The existing local name string to check.
   * @return The local name to use.
   */
  private String getLocalName(String localName)
  {
    if ((null == localName) || (0 == localName.length()))
    {
      localName = String.valueOf(RANDOM_NAME.nextInt(Integer.MAX_VALUE));
    }

    return localName;
  }

  /**
   * Returns the endpoint-relevant information from this URI
   *
   * @param uriValue
   * @param serviceDelim
   * @param routingDelim
   * @param supportsRouting
   * @return
   */
  private String getEndpointId(String uriValue, char serviceDelim, char routingDelim, boolean supportsRouting)
  {
    String endpointUriPart = uriValue;
    final int iFirst = endpointUriPart.indexOf(serviceDelim);
    int iSecond = (supportsRouting ? endpointUriPart.indexOf(routingDelim) : endpointUriPart.length());
    if (iSecond < 0)
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
  private String packetToString(final byte[] data)
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
		    // could use a for loop, but we're only dealing with a
          // single byte
          hexString.append('0');
        }
        hexString.append(hex);
      }
    }

    return hexString.toString();
  }

  /**
   * Internal method for the creation of endpoints.
   *
   * @param localName The local name to use.
   * @param qosProperties the QoS properties.
   * @return The new endpoint
   * @throws MALException on Error.
   */
  private TCPIPEndpoint internalCreateEndpoint(final String localName, final Map qosProperties) throws MALException
  {
    return new TCPIPEndpoint(this, localName, uriBase + localName, wrapBodyParts);
  }

  /**
   * Internal method for the creation of receiving messages.
   *
   * @param packet The input packet to use.
   * @return The new message.
   * @throws MALException on Error.
   */
  private GENMessage createMessage(final byte[] packet) throws MALException
  {
    return new GENMessage(wrapBodyParts, true, new GENMessageHeader(), packet, getStreamFactory());
  }

  /**
   * Provide a default IP address for this host
   *
   * @return The transport specific address part.
   * @throws MALException On error
   */
  private String getDefaultHost() throws MALException
  {
    try
    {
      // Build RMI url string
      final InetAddress addr = Inet4Address.getLocalHost();
      final StringBuilder hostAddress = new StringBuilder();
      if (addr instanceof Inet6Address)
      {
        LOGGER.fine("TCPIP Address class is IPv6");
        hostAddress.append('[');
        hostAddress.append(addr.getHostAddress());
        hostAddress.append(']');
      }
      else
      {
        hostAddress.append(addr.getHostAddress());
      }

      return hostAddress.toString();
    }
    catch (UnknownHostException ex)
    {
      throw new MALException("Could not determine local host address", ex);
    }
  }

  /**
   * This method returns a random Id to be used for differentiating different MAL instances in the same host.
   *
   * @return the random, host unique, id
   */
  private String getRandomClientId()
  {
    UID hostUniqueUID = new UID();
    String hostUniqueUIDHex = hostUniqueUID.toString().replaceAll("[^abcdef0-9]", "");
    return hostUniqueUIDHex;
  }

  @Override
  public MALBrokerBinding createBroker(final String localName, final Blob authenticationId, final QoSLevel[] expectedQos, final UInteger priorityLevelNumber, final Map defaultQoSProperties) throws MALException
  {
    // not support by TCPIP transport
    return null;
  }

  @Override
  public MALBrokerBinding createBroker(final MALEndpoint endpoint, final Blob authenticationId, final QoSLevel[] qosLevels, final UInteger priorities, final Map properties) throws MALException
  {
    // not support by TCPIP transport
    return null;
  }

  @Override
  public boolean isSupportedInteractionType(final InteractionType type)
  {
    // Supports all IPs except Pub Sub
    return (InteractionType.PUBSUB.getOrdinal() != type.getOrdinal());
  }

  @Override
  public boolean isSupportedQoSLevel(final QoSLevel qos)
  {
	// The transport only supports BESTEFFORT in reality but this is only a
    // test transport so we say it supports all
    return true;
  }

  /**
   * Registers a connection (channel) with a remote URI
   *
   * @param socket the TCP socket
   * @param uriTo the target URI that this socket will send messages to
   */
  private void registerChannel(Socket socket, String uriTo)
  {

    TCPIPConcurrentSocketDataSender channelSender = outgoingDataChannels.get(uriTo);
    if (channelSender == null)
    {
      // create a new one
      channelSender = new TCPIPConcurrentSocketDataSender(this, uriTo);
      outgoingDataChannels.put(uriTo, channelSender);
    }

    // register the socket for this URI
    try
    {
      channelSender.addProcessor(socket, uriTo);
    }
    catch (IOException e)
    {
      // communication problem. terminate all connections to this client
      this.communicationError(uriTo);
    }
  }

  /**
   * Used to inform the transport about communication problems with clients. In this case the transport will terminate
   * all communication channels with the destination in order for them to be re-established.
   *
   * @param uriTo the URI where the communication problem occured
   */
  public void communicationError(String uriTo)
  {
    LOGGER.log(Level.WARNING, "TCPIP Communication Error with {0} ", uriTo);

    TCPIPConcurrentSocketDataSender commsChannel = outgoingDataChannels.get(uriTo);
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

  /**
   * Returns the "root" URI from the full URI. The root URI only contains the protocol and the main destination and is
   * something unique for all URIs of the same MAL.
   *
   * @param fullURI the full URI, for example tcpip://10.0.0.1:61616-serviceXYZ
   * @return the root URI, for example tcpip://10.0.0.1:61616
   */
  private String getRootURI(String fullURI)
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

  /**
   * Getter
   *
   * @return the input packet processing queue
   */
  public void submitIncomingDataTask(TCPIPInputDataForwarder dataProcessTask)
  {
    asyncInputDataProcessors.submit(dataProcessTask);
  }

}
