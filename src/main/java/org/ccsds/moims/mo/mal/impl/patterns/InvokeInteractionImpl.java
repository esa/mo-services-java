/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl.patterns;

import org.ccsds.moims.mo.mal.provider.MALInvoke;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInvokeOperation;
import org.ccsds.moims.mo.mal.impl.Address;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.impl.MALImpl;
import org.ccsds.moims.mo.mal.structures.StandardError;

/**
 *
 * @author cooper_sf
 */
public class InvokeInteractionImpl extends BaseInteractionImpl implements MALInvoke
{
  private boolean ackSent = false;
  
  public InvokeInteractionImpl(MALImpl impl, Address address, Identifier internalTransId, MALMessage msg)
  {
    super(impl, address, internalTransId, msg);
  }

  @Override
  public void sendAcknowledgement(Element result) throws MALException
  {
    ackSent = true;
    sender.returnResponse(address, internalTransId, msg.getHeader(), MALInvokeOperation.INVOKE_ACK_STAGE, result);
  }

  @Override
  public void sendResponse(Element result) throws MALException
  {
    sender.returnResponse(address, internalTransId, msg.getHeader(), MALInvokeOperation.INVOKE_RESPONSE_STAGE, result);
  }

  @Override
  public void sendError(StandardError error) throws MALException
  {
    Byte stage = MALInvokeOperation.INVOKE_ACK_STAGE;

    if (ackSent)
    {
      stage = MALInvokeOperation.INVOKE_RESPONSE_STAGE;
    }

    sender.returnError(address, internalTransId, msg.getHeader(), stage, error);
  }
}
