/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl.patterns;

import org.ccsds.moims.mo.mal.impl.MessageSend;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 *
 * @author cooper_sf
 */
public class SendInteractionImpl extends BaseInteractionImpl implements MALInteraction
{
  public SendInteractionImpl(MessageSend sender, MALMessage msg)
  {
    super(sender, null, null, msg);
  }
}
