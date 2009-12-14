/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl.patterns;

import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.impl.MALImpl;

/**
 *
 * @author cooper_sf
 */
public class SendInteractionImpl extends BaseInteractionImpl implements MALInteraction
{
  public SendInteractionImpl(MALImpl impl, MALMessage msg)
  {
    super(impl, null, null, msg);
  }
}
