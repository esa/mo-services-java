/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl.patterns;

import org.ccsds.moims.mo.mal.provider.MALSubmit;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.impl.Address;
import org.ccsds.moims.mo.mal.impl.MALServiceSend;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.StandardError;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 *
 * @author cooper_sf
 */
public class SubmitInteractionImpl extends BaseInteractionImpl implements MALSubmit
{
  public SubmitInteractionImpl(MALServiceSend sender, Address address, Identifier internalTransId, MALMessage msg)
  {
    super(sender, address, internalTransId, msg);
  }

  @Override
  public void sendAcknowledgement() throws MALException
  {
    sender.returnResponse(address, internalTransId, msg.getHeader(), MALSubmitOperation.SUBMIT_ACK_STAGE, null);
  }

  @Override
  public void sendError(StandardError error) throws MALException
  {
    sender.returnError(address, internalTransId, msg.getHeader(), MALSubmitOperation.SUBMIT_ACK_STAGE, error);
  }
}
