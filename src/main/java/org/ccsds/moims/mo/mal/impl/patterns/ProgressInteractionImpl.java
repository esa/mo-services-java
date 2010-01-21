/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl.patterns;

import org.ccsds.moims.mo.mal.provider.MALProgress;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALProgressOperation;
import org.ccsds.moims.mo.mal.impl.Address;
import org.ccsds.moims.mo.mal.impl.MessageSend;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.StandardError;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 *
 * @author cooper_sf
 */
public class ProgressInteractionImpl extends BaseInteractionImpl implements MALProgress
{
  private boolean ackSent = false;

  public ProgressInteractionImpl(MessageSend sender, Address address, Identifier internalTransId, MALMessage msg)
  {
    super(sender, address, internalTransId, msg);
  }

  @Override
  public void sendAcknowledgement(Element result) throws MALException
  {
    ackSent = true;
    sender.returnResponse(address, internalTransId, msg.getHeader(), MALProgressOperation.PROGRESS_ACK_STAGE, result);
  }

  @Override
  public void sendUpdate(Element update) throws MALException
  {
    sender.returnResponse(address, internalTransId, msg.getHeader(), MALProgressOperation.PROGRESS_UPDATE_STAGE, update);
  }

  @Override
  public void sendResponse(Element result) throws MALException
  {
    sender.returnResponse(address, internalTransId, msg.getHeader(), MALProgressOperation.PROGRESS_RESPONSE_STAGE, result);
  }

  @Override
  public void sendError(StandardError error) throws MALException
  {
    Byte stage = MALProgressOperation.PROGRESS_ACK_STAGE;

    if (ackSent)
    {
      stage = MALProgressOperation.PROGRESS_RESPONSE_STAGE;
    }

    sender.returnError(address, internalTransId, msg.getHeader(), stage, error);
  }

  @Override
  public void sendUpdateError(StandardError error) throws MALException
  {
    sender.returnError(address, internalTransId, msg.getHeader(), MALProgressOperation.PROGRESS_UPDATE_STAGE, error);
  }
}
