/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl.patterns;

import org.ccsds.moims.mo.mal.MALFactory;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.MessageHeader;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.impl.MALImpl;
import org.ccsds.moims.mo.mal.impl.MALServiceComponentImpl;

/**
 *
 * @author cooper_sf
 */
public abstract class BaseInteractionImpl implements MALInteraction
{
  protected final MALImpl impl;
  protected final MALServiceComponentImpl handler;
  protected final Identifier internalTransId;
  protected final MALMessage msg;
  protected final MALOperation operation;

  public BaseInteractionImpl(MALImpl impl, MALServiceComponentImpl handler, Identifier internalTransId, MALMessage msg)
  {
    this.impl = impl;
    this.handler = handler;
    this.internalTransId = internalTransId;
    this.msg = msg;
    this.operation = MALFactory.lookupOperation(msg.getHeader().getArea(), msg.getHeader().getService(), msg.getHeader().getOperation());
  }

  @Override
  public MessageHeader getMessageHeader()
  {
    return msg.getHeader();
  }

  @Override
  public MALOperation getOperation()
  {
    return operation;
  }

  @Override
  public Element getQoSProperty(String name)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void setQoSProperty(String name, Element value)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
