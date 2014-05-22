/* ----------------------------------------------------------------------------
 * Copyright (C) 2014      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO In Process Transport Framework
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
package esa.mo.mal.transport.inproc;

import esa.mo.mal.transport.gen.GENEndpoint;
import esa.mo.mal.transport.gen.GENTransport;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALTransportFactory;

/**
 * An implementation of the transport interface for an in process transport. Incoming messages can be handled by calling
 * one of the receive methods.
 */
public class InProcTransport extends GENTransport
{
  /**
   * Logger
   */
  public static final java.util.logging.Logger RLOGGER = Logger.getLogger("org.ccsds.moims.mo.mal.transport.inproc");
  private ExternalMessageSink externalSink = null;

  /**
   * Constructor.
   *
   * @param protocol The protocol string.
   * @param factory The factory that created us.
   * @param properties The QoS properties.
   * @throws MALException On error.
   */
  public InProcTransport(final String protocol,
          final MALTransportFactory factory,
          final java.util.Map properties) throws MALException
  {
    super(protocol, '-', false, false, factory, properties);
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
  public InProcTransport(String protocol, String protocolDelim, char serviceDelim, char routingDelim, boolean supportsRouting, boolean wrapBodyParts, MALTransportFactory factory, Map properties) throws MALException
  {
    super(protocol, protocolDelim, serviceDelim, routingDelim, supportsRouting, wrapBodyParts, factory, properties);
  }

  public void setExternalSink(ExternalMessageSink sink)
  {
    externalSink = sink;
  }

  @Override
  protected String createTransportAddress() throws MALException
  {
    return String.valueOf(Math.random());
  }

  @Override
  protected GENEndpoint internalCreateEndpoint(final String localName, final Map qosProperties) throws MALException
  {
    return new GENEndpoint(this, localName, uriBase + localName, false);
  }

  @Override
  public MALBrokerBinding createBroker(final String localName,
          final Blob authenticationId,
          final QoSLevel[] expectedQos,
          final UInteger priorityLevelNumber,
          final Map defaultQoSProperties) throws MALException
  {
    // not support by InProc transport
    return null;
  }

  @Override
  public MALBrokerBinding createBroker(final MALEndpoint endpoint,
          final Blob authenticationId,
          final QoSLevel[] qosLevels,
          final UInteger priorities,
          final Map properties) throws MALException
  {
    // not support by InProc transport
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
    // The transport only supports BESTEFFORT in reality but this is only a test transport so we say it supports all
    return QoSLevel.BESTEFFORT.equals(qos);
  }

  @Override
  protected void internalSendMessage(final GENTransport.MsgPair tmsg) throws Exception
  {
    // this need to do the local look up
    String endpointUriPart = getRoutingPart(tmsg.url, serviceDelim, routingDelim, supportsRouting);
    
    if (endpointMap.containsKey(endpointUriPart))
    {
      RLOGGER.log(Level.INFO, "InProc Sending data internally to {0}", new Object[]
      {
        tmsg.url
      });

      // if local then just make up call
      receiveMessageThreadMain(tmsg.msg, "");
    }
    else
    {
      ExternalMessageSink es = externalSink;

      if (null != es)
      {
        RLOGGER.log(Level.INFO, "InProc Sending data remotely to {0}", new Object[]
        {
          tmsg.url
        });

        // if not then encode and add to outgoing queue
        byte[] buf = null;

        // encode the message
        try
        {
          final ByteArrayOutputStream baos = new ByteArrayOutputStream();
          final MALElementOutputStream enc = getStreamFactory().createOutputStream(baos);
          tmsg.msg.encodeMessage(getStreamFactory(), enc, baos);
          buf = baos.toByteArray();
        }
        catch (MALException ex)
        {
          ex.printStackTrace();
        }

        RLOGGER.log(Level.INFO, "InProc Sending data to {0} : {1}", new Object[]
        {
          tmsg.url, packetToString(buf)
        });

        es.receiveMessage(buf);
      }
      else
      {
        RLOGGER.log(Level.WARNING, "No external sink set, message dropped and error returned for {0}", new Object[]
        {
          tmsg.url
        });
        returnErrorMessage(tmsg.srcEp,
                tmsg.msg,
                MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER,
                "GEN Cannot find endpoint: " + endpointUriPart);
      }
    }
  }

  public static interface ExternalMessageSink
  {
    void receiveMessage(byte[] buf);
  }
}
