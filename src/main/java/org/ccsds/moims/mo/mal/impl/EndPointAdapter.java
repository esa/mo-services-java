package org.ccsds.moims.mo.mal.impl;

import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.structures.StandardError;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageListener;

public final class EndPointAdapter implements MALMessageListener
{
  private final MessageReceive rcv;
  private final Address address;

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
    rcv.internalHandleMessage(msg, address);
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
