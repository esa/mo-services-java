/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.smc.mal.impl.patterns;

import org.ccsds.moims.smc.mal.api.provider.MALProgress;
import org.ccsds.moims.smc.mal.api.structures.MALElement;
import org.ccsds.moims.smc.mal.api.structures.MALException;
import org.ccsds.moims.smc.mal.api.structures.MALIdentifier;
import org.ccsds.moims.smc.mal.api.transport.MALMessage;
import org.ccsds.moims.smc.mal.impl.MALImpl;

/**
 *
 * @author cooper_sf
 */
public class ProgressInteractionImpl extends InvokeInteractionImpl implements MALProgress
{
  public ProgressInteractionImpl(MALImpl impl, MALIdentifier internalTransId, MALMessage msg)
  {
    super(impl, internalTransId, msg);
  }

  public void sendUpdate(MALElement update) throws MALException
  {
    impl.getSendingInterface().returnResponse(internalTransId, msg, update);
  }
}
