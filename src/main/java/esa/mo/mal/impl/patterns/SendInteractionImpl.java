/* ----------------------------------------------------------------------------
 * (C) 2010      European Space Agency
 *               European Space Operations Centre
 *               Darmstadt Germany
 * ----------------------------------------------------------------------------
 * System       : CCSDS MO MAL Implementation
 * Author       : Sam Cooper
 *
 * ----------------------------------------------------------------------------
 */
package esa.mo.mal.impl.patterns;

import org.ccsds.moims.mo.mal.MALInteractionException;
import esa.mo.mal.impl.MessageSend;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * Send interaction class.
 */
public class SendInteractionImpl extends BaseInteractionImpl
{
  /**
   * Constructor.
   * @param sender Used to return the messages.
   * @param msg The source message.
   * @throws MALInteractionException if the received message operation is unknown.
   */
  public SendInteractionImpl(final MessageSend sender, final MALMessage msg) throws MALInteractionException
  {
    super(sender, null, null, msg);
  }
}
