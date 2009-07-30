/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.smc.mal.impl.patterns;

import org.ccsds.moims.smc.mal.api.provider.MALInteraction;
import org.ccsds.moims.smc.mal.api.transport.MALMessage;
import org.ccsds.moims.smc.mal.impl.MALImpl;

/**
 *
 * @author cooper_sf
 */
public class SendInteractionImpl extends BaseInteractionImpl implements MALInteraction
{
  public SendInteractionImpl(MALImpl impl, MALMessage msg)
  {
    super(impl, null, msg);
  }
}
