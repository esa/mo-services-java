/* ----------------------------------------------------------------------------
 * Copyright (C) 2015      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO SPP Transport Framework
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
package esa.mo.mal.transport.spp;

import esa.mo.mal.transport.gen.GENEndpoint;
import esa.mo.mal.transport.gen.GENMessage;
import esa.mo.mal.transport.gen.GENTransport;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALTransportFactory;

public abstract class SPPBaseTransport<T> extends GENTransport
{
  /**
   * Logger
   */
  public static final java.util.logging.Logger LOGGER = Logger.getLogger("org.ccsds.moims.mo.mal.transport.spp");

  public static final String IS_TC_PACKET_PROPERTY = "org.ccsds.moims.mo.malspp.isTcPacket";
  public static final String APID_QUALIFIER_PROPERTY = "org.ccsds.moims.mo.malspp.apidQualifier";
  public static final String SEGMENT_MAX_SIZE_PROPERTY = "org.ccsds.moims.mo.malspp.segmentMaxSize";
  public static final String APID_PROPERTY = "org.ccsds.moims.mo.malspp.apid";
  public static final String APPEND_ID_TO_URI = "org.ccsds.moims.mo.malspp.appendIdToUri";
  public static final String AUTHENTICATION_ID_FLAG = "org.ccsds.moims.mo.malspp.authenticationIdFlag";
  public static final String DOMAIN_FLAG = "org.ccsds.moims.mo.malspp.domainFlag";
  public static final String NETWORK_ZONE_FLAG = "org.ccsds.moims.mo.malspp.networkZoneFlag";
  public static final String PRIORITY_FLAG = "org.ccsds.moims.mo.malspp.priorityFlag";
  public static final String SESSION_NAME_FLAG = "org.ccsds.moims.mo.malspp.sessionNameFlag";
  public static final String TIMESTAMP_FLAG = "org.ccsds.moims.mo.malspp.timestampFlag";

  protected final SPPConfiguration configuration;
  protected final SPPURIRepresentation uriRep;
  protected final SPPSourceSequenceCounterSimple ssc;
  protected final int apidQualifier;
  protected final int apid;
  protected final Map<Integer, SegmentHandler> segmentHandlers = new HashMap<Integer, SegmentHandler>();
  /**
   * The stream factory used for encoding and decoding message headers.
   */
  private final MALElementStreamFactory hdrStreamFactory;

  /*
   * Constructor.
   *
   * @param protocol The protocol string.
   * @param factory The factory that created us.
   * @param properties The QoS properties.
   * @throws MALException On error.
   */
  public SPPBaseTransport(SPPConfiguration configuration, SPPURIRepresentation uriRep, SPPSourceSequenceCounterSimple ssc, String protocol, String protocolDelim, char serviceDelim, char routingDelim, boolean supportsRouting, boolean wrapBodyParts, MALTransportFactory factory, Map properties) throws MALException
  {
    super(protocol, protocolDelim, serviceDelim, routingDelim, supportsRouting, wrapBodyParts, factory, properties);

    this.configuration = configuration;
    this.uriRep = uriRep;
    this.ssc = ssc;

    int aq = -1;
    int a = 1;

    // decode configuration
    if (properties != null)
    {
      if (properties.containsKey(APID_QUALIFIER_PROPERTY))
      {
        aq = Integer.parseInt((String) properties.get(APID_QUALIFIER_PROPERTY));
      }

      if (properties.containsKey(APID_PROPERTY))
      {
        a = Integer.parseInt((String) properties.get(APID_PROPERTY));
      }
    }

    this.apidQualifier = aq;
    this.apid = a;

    hdrStreamFactory = MALElementStreamFactory.newFactory("malspp_fixed", properties);

    LOGGER.log(Level.INFO, "SPP APID qualifier set to : {0}", apidQualifier);
    LOGGER.log(Level.INFO, "SPP APID           set to : {0}", apid);

    LOGGER.log(Level.INFO, "SPP Wrapping body parts set to  : {0}", this.wrapBodyParts);
  }

  @Override
  public MALBrokerBinding createBroker(final String localName, final Blob authenticationId, final QoSLevel[] expectedQos, final UInteger priorityLevelNumber, final Map defaultQoSProperties) throws MALException
  {
    // not support by SPP transport
    return null;
  }

  @Override
  public MALBrokerBinding createBroker(final MALEndpoint endpoint, final Blob authenticationId, final QoSLevel[] qosLevels, final UInteger priorities, final Map properties) throws MALException
  {
    // not support by SPP transport
    return null;
  }

  @Override
  public boolean isSupportedInteractionType(final InteractionType type)
  {
    // Supports all IPs except Pub Sub
    return InteractionType.PUBSUB.getOrdinal() != type.getOrdinal();
  }

  @Override
  public boolean isSupportedQoSLevel(final QoSLevel qos)
  {
    // The transport only supports BESTEFFORT in reality but this is only a
    // test transport so we say it supports all
    return true;
  }

  @Override
  protected String getLocalName(String localName, final java.util.Map properties)
  {
    StringBuilder buf = new StringBuilder();

    int a = apid;
    int aq = apidQualifier;

    // decode configuration
    if (properties != null)
    {
      if (properties.containsKey(APID_PROPERTY))
      {
        a = Integer.parseInt(properties.get(APID_PROPERTY).toString());
      }
      if (properties.containsKey(APID_QUALIFIER_PROPERTY))
      {
        aq = Integer.parseInt(properties.get(APID_QUALIFIER_PROPERTY).toString());
      }
    }

    buf.append(aq);
    buf.append('/');
    buf.append(a);

    if ((properties == null)
            || !properties.containsKey(APPEND_ID_TO_URI)
            || Boolean.parseBoolean(properties.get(APPEND_ID_TO_URI).toString()))
    {
      buf.append('/');
      buf.append(Math.abs((byte) RANDOM_NAME.nextInt()));
    }

    return buf.toString();
  }

  @Override
  public String getRoutingPart(String uriValue)
  {
    String endpointUriPart = uriValue;
    int iFirst = endpointUriPart.indexOf(protocolDelim) + 1;

    System.out.println("GRP: " + uriValue + "  :  " + endpointUriPart.substring(iFirst));
    return endpointUriPart.substring(iFirst);
  }

  @Override
  protected GENEndpoint internalCreateEndpoint(final String localName, final String routingName, final Map properties) throws MALException
  {
    return new SPPEndpoint(this, configuration, apidQualifier, uriRep, ssc, localName, routingName, uriBase + routingName, wrapBodyParts, properties);
  }

  @Override
  public GENMessage createMessage(byte[] packet) throws MALException
  {
    return new SPPMessage(65530, null, wrapBodyParts, true, new SPPMessageHeader(configuration, null, apidQualifier, uriRep, ssc), qosProperties, packet, getStreamFactory());
  }

  @Override
  public GENMessage createMessage(InputStream ios) throws MALException
  {
    return new SPPMessage(65530, null, wrapBodyParts, true, new SPPMessageHeader(configuration, null, apidQualifier, uriRep, ssc), qosProperties, ios, getStreamFactory());
  }

  public abstract GENMessage createMessage(T packet) throws MALException;

  protected GENMessage receiveSegement(int apidQualifier, int apid, int segmentFlags, byte[] packet) throws MALException
  {
    // find packet segment handler
    SegmentHandler hdr = segmentHandlers.get(apid);

    if (null == hdr)
    {
      hdr = new SegmentHandler();
      segmentHandlers.put(apid, hdr);
    }

    hdr.addSegment(segmentFlags, packet);

    if (hdr.messageComplete())
    {
      byte[] c = hdr.getCompleteMessage();
      segmentHandlers.remove(apid);

      GENMessage msg = createMessage(c);

      System.out.println("Decoded SPP segmented message: " + msg.getHeader());
      return msg;
    }

    return null;
  }

  protected class SegmentHandler
  {
    private int totalSize = 0;
    private boolean hadFirst = false;
    private long firstIndex = -1;
    private boolean hadLast = false;
    Map<Long, byte[]> segmentMap = new TreeMap<Long, byte[]>(); // use TreeMap so that key is kept sorted

    public void addSegment(int segmentFlags, byte[] packet)
    {
      int extra = (packet[26] & 0x80) != 0 ? 1 : 0;
      extra += (packet[26] & 0x40) != 0 ? 1 : 0;
      long segmentIndex = java.nio.ByteBuffer.wrap(packet).getInt(27 + extra);

      if (1 == segmentFlags)
      {
        hadFirst = true;
        firstIndex = segmentIndex;
      }
      else if (2 == segmentFlags)
      {
        hadLast = true;
      }

      System.out.println("Adding segment: " + apid + " : " + segmentIndex + " : " + segmentFlags + " : " + totalSize);

      segmentMap.put(segmentIndex, packet);
      totalSize += packet.length;
    }

    public boolean messageComplete()
    {
      boolean bv = hadFirst && hadLast && noGaps();

      System.out.println("Message complete: " + bv);

      return bv;
    }

    public byte[] getCompleteMessage() throws MALException
    {
      byte[] buf = new byte[totalSize];

      int index = 0;
      int initialOffset = index;

      boolean first = true;
      for (byte[] packet : segmentMap.values())
      {
        if (!first)
        {
          GENMessage msg = createMessage(packet);
          packet = msg.getBody().getEncodedBody().getEncodedBody().getValue();
          initialOffset = 0;
        }

        System.arraycopy(packet, initialOffset, buf, index, packet.length - initialOffset);

        index += (packet.length - initialOffset);
        first = false;
      }

      return buf;
    }

    protected boolean noGaps()
    {
      long index = firstIndex;

      for (long key : segmentMap.keySet())
      {
        if (key != index++)
        {
          return false;
        }
      }

      return true;
    }
  }
}
