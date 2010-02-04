/* ----------------------------------------------------------------------------
 * (C) 2010      European Space Agency
 *               European Space Operations Centre
 *               Darmstadt Germany
 * ----------------------------------------------------------------------------
 * System       : CCSDS MO MAL Implementation
 * Author       : cooper_sf
 *
 * ----------------------------------------------------------------------------
 */
package org.ccsds.moims.mo.mal.impl.patterns;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.impl.MessageSend;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * Send interaction class.
 */
public class SendInteractionImpl extends BaseInteractionImpl implements MALInteraction
{
  /**
   * Constructor.
   * @param sender Used to return the messages.
   * @param msg The source message.
   */
  public SendInteractionImpl(MessageSend sender, MALMessage msg) throws MALException
  {
    super(sender, null, null, msg);
  }
}
