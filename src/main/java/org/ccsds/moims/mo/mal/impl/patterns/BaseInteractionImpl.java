/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl.patterns;

import java.util.HashMap;
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
  protected final HashMap qosProperties = new HashMap();

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
    return (Element)qosProperties.get(name);
  }

  @Override
  public void setQoSProperty(String name, Element value)
  {
    qosProperties.put(name, value);
  }
}
