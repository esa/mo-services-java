/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ccsds.moims.mo.mal.impl.patterns;

import org.ccsds.moims.mo.mal.provider.MALRequest;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.impl.MALImpl;
import org.ccsds.moims.mo.mal.structures.StandardError;

/**
 *
 * @author cooper_sf
 */
public class RequestInteractionImpl extends BaseInteractionImpl implements MALRequest
{
  public RequestInteractionImpl(MALImpl impl, Identifier internalTransId, MALMessage msg)
  {
    super(impl, internalTransId, msg);
  }

  @Override
  public void sendResponse(Element result) throws MALException
  {
    impl.getSendingInterface().returnResponse(internalTransId, msg, result);
  }

  @Override
  public void sendError(StandardError error) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
