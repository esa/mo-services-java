/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl.patterns;

import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.impl.Address;
import org.ccsds.moims.mo.mal.impl.MessageSend;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 *
 * @author cooper_sf
 */
public class PubSubInteractionImpl extends BaseInteractionImpl
{
  public PubSubInteractionImpl(MessageSend sender, Address address, Long internalTransId, MALMessage msg) throws MALInteractionException
  {
    super(sender, address, internalTransId, msg);
  }
}
