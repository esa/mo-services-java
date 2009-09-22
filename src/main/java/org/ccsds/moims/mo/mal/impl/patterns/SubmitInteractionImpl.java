/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl.patterns;

import org.ccsds.moims.mo.mal.provider.MALSubmit;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.StandardError;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.impl.MALImpl;

/**
 *
 * @author cooper_sf
 */
public class SubmitInteractionImpl extends BaseInteractionImpl implements MALSubmit
{
  public SubmitInteractionImpl(MALImpl impl, Identifier internalTransId, MALMessage msg)
  {
    super(impl, internalTransId, msg);
  }

  @Override
  public void sendAcknowledgement() throws MALException
  {
    impl.getSendingInterface().returnResponse(internalTransId, msg, null);
  }

  @Override
  public void sendError(StandardError error) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
