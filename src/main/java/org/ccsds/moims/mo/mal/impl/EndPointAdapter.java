/* ----------------------------------------------------------------------------
 * (C) 2010      European Space Agency
 *               European Space Operations Centre
 *               Darmstadt Germany
 * ----------------------------------------------------------------------------
 * System       : CCSDS MO MAL Implementation
 * Author       : cooper_sf
 *
 * ----------------------------------------------------------------------------
 */
package org.ccsds.moims.mo.mal.impl;

import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.structures.StandardError;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageListener;

/**
 * Small adapter class that maps from the EndPoint to the Receiver class.
 */
public final class EndPointAdapter implements MALMessageListener
{
  private final MessageReceive rcv;
  private final Address address;

  /**
   * Constructor.
   * @param rcv The message receiving class.
   * @param address Address to use.
   */
  public EndPointAdapter(MessageReceive rcv, Address address)
  {
    super();
    this.rcv = rcv;
    this.address = address;
  }

  @Override
  public void onInternalError(StandardError err)
  {
    Logging.logMessage("INFO: MAL Receiving ERROR!");
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void onMessage(MALMessage msg)
  {
    rcv.handleMessage(msg, address);
  }

  @Override
  public void onMessages(MALMessage[] msgList)
  {
    for (int i = 0; i < msgList.length; i++)
    {
      onMessage(msgList[i]);
    }
  }
}
