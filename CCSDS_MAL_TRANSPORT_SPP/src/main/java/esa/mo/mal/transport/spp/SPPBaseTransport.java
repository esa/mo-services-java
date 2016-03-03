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

public abstract class SPPBaseTransport<T> extends GENTransport
{
  /**
   * Logger
   */
  public static final java.util.logging.Logger RLOGGER = Logger.getLogger("org.ccsds.moims.mo.mal.transport.spp");

  protected final SPPConfiguration configuration;
  protected final SPPURIRepresentation uriRep;
  protected final SPPSourceSequenceCounterSimple ssc;
  protected final int apidQualifier;
  protected final int apid;

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
      if (properties.containsKey("org.ccsds.moims.mo.malspp.apidQualifier"))
      {
        aq = Integer.parseInt((String) properties.get("org.ccsds.moims.mo.malspp.apidQualifier"));
      }

      if (properties.containsKey("org.ccsds.moims.mo.malspp.apid"))
      {
        a = Integer.parseInt((String) properties.get("org.ccsds.moims.mo.malspp.apid"));
      }
    }

    this.apidQualifier = aq;
    this.apid = a;

    RLOGGER.log(Level.INFO, "SPP APID qualifier set to : {0}", apidQualifier);
    RLOGGER.log(Level.INFO, "SPP APID           set to : {0}", apid);

    RLOGGER.log(Level.INFO, "SPP Wrapping body parts set to  : {0}", this.wrapBodyParts);
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
      if (properties.containsKey("org.ccsds.moims.mo.malspp.apid"))
      {
        a = Integer.parseInt(properties.get("org.ccsds.moims.mo.malspp.apid").toString());
      }
      if (properties.containsKey("org.ccsds.moims.mo.malspp.apidQualifier"))
      {
        aq = Integer.parseInt(properties.get("org.ccsds.moims.mo.malspp.apidQualifier").toString());
      }
    }
    
    buf.append(aq);
    buf.append('/');
    buf.append(a);
    buf.append('/');
    buf.append(Math.abs((byte) RANDOM_NAME.nextInt()));

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
    int a = apid;
    int aq = apidQualifier;
    
    // decode configuration
    if (properties != null)
    {
      if (properties.containsKey("org.ccsds.moims.mo.malspp.apid"))
      {
        a = Integer.parseInt(properties.get("org.ccsds.moims.mo.malspp.apid").toString());
      }
      if (properties.containsKey("org.ccsds.moims.mo.malspp.apidQualifier"))
      {
        aq = Integer.parseInt(properties.get("org.ccsds.moims.mo.malspp.apidQualifier").toString());
      }
    }
    
    return new SPPEndpoint(this, configuration, aq, uriRep, ssc, localName, routingName, uriBase + routingName, wrapBodyParts, qosProperties);
  }

  @Override
  public GENMessage createMessage(byte[] packet) throws MALException
  {
    return new SPPMessage(wrapBodyParts, true, new SPPMessageHeader(configuration, apidQualifier, uriRep, ssc), qosProperties, packet, getStreamFactory());
  }

  @Override
  public GENMessage createMessage(InputStream ios) throws MALException
  {
    return new SPPMessage(wrapBodyParts, true, new SPPMessageHeader(configuration, apidQualifier, uriRep, ssc), qosProperties, ios, getStreamFactory());
  }

  public abstract GENMessage createMessage(T packet) throws MALException;
}
