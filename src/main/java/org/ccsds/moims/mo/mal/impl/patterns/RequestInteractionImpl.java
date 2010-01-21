/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ccsds.moims.mo.mal.impl.patterns;

import org.ccsds.moims.mo.mal.provider.MALRequest;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.impl.Address;
import org.ccsds.moims.mo.mal.impl.MessageSend;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.structures.StandardError;

/**
 *
 * @author cooper_sf
 */
public class RequestInteractionImpl extends BaseInteractionImpl implements MALRequest
{
  public RequestInteractionImpl(MessageSend sender, Address address, Identifier internalTransId, MALMessage msg)
  {
    super(sender, address, internalTransId, msg);
  }

  @Override
  public void sendResponse(Element result) throws MALException
  {
    sender.returnResponse(address, internalTransId, msg.getHeader(), MALRequestOperation.REQUEST_RESPONSE_STAGE, result);
  }

  @Override
  public void sendError(StandardError error) throws MALException
  {
    sender.returnError(address, internalTransId, msg.getHeader(), MALRequestOperation.REQUEST_RESPONSE_STAGE, error);
  }
}
