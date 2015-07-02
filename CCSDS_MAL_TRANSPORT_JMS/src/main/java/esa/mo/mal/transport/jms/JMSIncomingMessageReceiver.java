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
import java.util.HashMap;
import org.ccsds.moims.mo.mal.MALException;

/**
 * This Runnable task is responsible for holding newly arrived MAL Messages (in raw format), decoding, and passing to
 * the transport executor.
 */
final class JMSIncomingMessageReceiver extends GENIncomingMessageReceiverBase
{
  final JMSUpdate jmsUpdate;

  /**
   * Constructor
   *
   * @param jmsUpdate The raw message
   * @param receptionHandler The reception handler to pass them to.
   */
  public JMSIncomingMessageReceiver(final JMSTransport transport, JMSUpdate jmsUpdate, GENReceptionHandler receptionHandler)
  {
    super(transport, receptionHandler);
    this.jmsUpdate = jmsUpdate;
  }

  @Override
  protected GENIncomingMessageHolder decodeAndCreateMessage() throws MALException
  {
    GENMessage malMsg = new GENMessage(false, true, new GENMessageHeader(), new HashMap(), jmsUpdate.getDat(), transport.getStreamFactory());
    return new GENIncomingMessageHolder(malMsg.getHeader().getTransactionId(), malMsg, transport.new PacketToString(null));
  }
  
}
