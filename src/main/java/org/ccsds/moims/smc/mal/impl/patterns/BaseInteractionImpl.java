/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.smc.mal.impl.patterns;

import org.ccsds.moims.smc.mal.api.MALFactory;
import org.ccsds.moims.smc.mal.api.MALOperation;
import org.ccsds.moims.smc.mal.api.provider.MALInteraction;
import org.ccsds.moims.smc.mal.api.structures.MALElement;
import org.ccsds.moims.smc.mal.api.structures.MALIdentifier;
import org.ccsds.moims.smc.mal.api.structures.MALMessageHeader;
import org.ccsds.moims.smc.mal.api.transport.MALMessage;
import org.ccsds.moims.smc.mal.impl.MALImpl;

/**
 *
 * @author cooper_sf
 */
public abstract class BaseInteractionImpl implements MALInteraction
{
  protected final MALImpl impl;
  protected final MALIdentifier internalTransId;
  protected final MALMessage msg;
  protected final MALOperation operation;

  public BaseInteractionImpl(MALImpl impl, MALIdentifier internalTransId, MALMessage msg)
  {
    this.impl = impl;
    this.internalTransId = internalTransId;
    this.msg = msg;
    this.operation = MALFactory.lookupOperation(msg.getHeader().getArea(), msg.getHeader().getService(), msg.getHeader().getOperation());
  }

  public MALMessageHeader getMessageHeader()
  {
    return msg.getHeader();
  }

  public MALOperation getOperation()
  {
    return operation;
  }

  public MALElement getQoSProperty(String name)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void setQoSProperty(String name, MALElement value)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
