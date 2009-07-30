/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.smc.mal.impl.patterns;

import org.ccsds.moims.smc.mal.api.provider.MALSubmit;
import org.ccsds.moims.smc.mal.api.structures.MALException;
import org.ccsds.moims.smc.mal.api.structures.MALIdentifier;
import org.ccsds.moims.smc.mal.api.transport.MALMessage;
import org.ccsds.moims.smc.mal.impl.MALImpl;

/**
 *
 * @author cooper_sf
 */
public class SubmitInteractionImpl extends BaseInteractionImpl implements MALSubmit
{
  public SubmitInteractionImpl(MALImpl impl, MALIdentifier internalTransId, MALMessage msg)
  {
    super(impl, internalTransId, msg);
  }

  public void sendAcknowledgement() throws MALException
  {
    impl.getSendingInterface().returnResponse(internalTransId, msg, null);
  }

  public void sendException(MALException exception) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
