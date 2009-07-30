/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.smc.mal.impl.patterns;

import org.ccsds.moims.smc.mal.api.provider.MALInvoke;
import org.ccsds.moims.smc.mal.api.structures.MALElement;
import org.ccsds.moims.smc.mal.api.structures.MALException;
import org.ccsds.moims.smc.mal.api.structures.MALIdentifier;
import org.ccsds.moims.smc.mal.api.transport.MALMessage;
import org.ccsds.moims.smc.mal.impl.MALImpl;

/**
 *
 * @author cooper_sf
 */
public class InvokeInteractionImpl extends RequestInteractionImpl implements MALInvoke
{
  public InvokeInteractionImpl(MALImpl impl, MALIdentifier internalTransId, MALMessage msg)
  {
    super(impl, internalTransId, msg);
  }

  public void sendAcknowledgement(MALElement result) throws MALException
  {
    impl.getSendingInterface().returnResponse(internalTransId, msg, result);
  }
}
