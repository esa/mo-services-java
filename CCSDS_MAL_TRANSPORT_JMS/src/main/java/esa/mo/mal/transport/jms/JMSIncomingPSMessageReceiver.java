/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.mo.mal.transport.jms;

import esa.mo.mal.transport.gen.GENMessage;
import esa.mo.mal.transport.gen.GENMessageHeader;
import esa.mo.mal.transport.gen.GENReceptionHandler;
import esa.mo.mal.transport.gen.GENTransport.GENIncomingMessageReceiverBase;
import esa.mo.mal.transport.gen.GENTransport.PacketToString;
import esa.mo.mal.transport.gen.receivers.GENIncomingMessageHolder;
import java.io.ByteArrayInputStream;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALElementFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.Union;

/**
 * This Runnable task is responsible for holding newly arrived MAL Messages (in raw format), decoding, and passing to
 * the transport executor.
 */
final class JMSIncomingPSMessageReceiver extends GENIncomingMessageReceiverBase
{
  private final JMSUpdate jmsUpdate;
  private final URI uri;
  private final UOctet version;
  private final Identifier subId;
  private final URI URIFrom;
  private final QoSLevel level;
  private final UInteger priority;
  private final Identifier networkZone;
  private final SessionType session;
  private final Identifier sessionName;
  private final Long transactionId;

  public JMSIncomingPSMessageReceiver(final JMSTransport transport, JMSUpdate jmsUpdate, URI uri, UOctet version, Identifier subId, URI URIFrom, QoSLevel level, UInteger priority, Identifier networkZone, SessionType session, Identifier sessionName, Long transactionId, GENReceptionHandler receptionHandler)
  {
    super(transport, receptionHandler);
    this.jmsUpdate = jmsUpdate;
    this.uri = uri;
    this.version = version;
    this.subId = subId;
    this.URIFrom = URIFrom;
    this.level = level;
    this.priority = priority;
    this.networkZone = networkZone;
    this.session = session;
    this.sessionName = sessionName;
    this.transactionId = transactionId;
  }

  @Override
  protected GENIncomingMessageHolder decodeAndCreateMessage() throws MALException
  {
    // build header
    GENMessageHeader hdr = new GENMessageHeader();
    hdr.setURITo(uri);
    hdr.setTimestamp(new Time(new java.util.Date().getTime()));
    hdr.setInteractionType(InteractionType.PUBSUB);
    hdr.setInteractionStage(MALPubSubOperation.NOTIFY_STAGE);
    hdr.setAreaVersion(version);
    hdr.setIsErrorMessage(false);
    hdr.setURIFrom(URIFrom);
    hdr.setAuthenticationId(new Blob(JMSTransport.authId));
    hdr.setQoSlevel(level);
    hdr.setPriority(priority);
    hdr.setNetworkZone(networkZone);
    hdr.setSession(session);
    hdr.setSessionName(sessionName);
    hdr.setTransactionId(transactionId);
    try
    {
      ByteArrayInputStream baos = new ByteArrayInputStream(jmsUpdate.getDat());
      MALElementInputStream enc = transport.getStreamFactory().createInputStream(baos);
      UShort lstCount = (UShort) enc.readElement(new UShort(0), null);
      Object[] new_objs = new Object[lstCount.getValue() + 1];
      new_objs[0] = subId;
      for (int i = 1; i < new_objs.length; i++)
      {
        MALElementFactory factory = MALContextFactory.getElementFactoryRegistry().lookupElementFactory(((Union) enc.readElement(new Union(0L), null)).getLongValue());
        new_objs[i] = enc.readElement(factory.createElement(), null);
      }
      GENMessage malMsg = new GENMessage(false, new JMSMessageHeader(hdr, jmsUpdate), null, null, new_objs);
      return new GENIncomingMessageHolder(malMsg.getHeader().getTransactionId(), malMsg, transport.new PacketToString(null));
    }
    catch (Throwable ex)
    {
      throw new MALException("Internal error decoding message", ex);
    }
  }

}
