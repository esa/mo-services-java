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

import org.ccsds.moims.mo.mal.provider.MALSubmit;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.impl.Address;
import org.ccsds.moims.mo.mal.impl.MessageSend;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.StandardError;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * Submit interaction class.
 */
public class SubmitInteractionImpl extends BaseInteractionImpl implements MALSubmit
{
  /**
   * Constructor.
   * @param sender Used to return the messages.
   * @param address Details of this endpoint.
   * @param internalTransId Internal transaction identifier.
   * @param msg The source message.
   */
  public SubmitInteractionImpl(MessageSend sender, Address address, Identifier internalTransId, MALMessage msg)
  {
    super(sender, address, internalTransId, msg);
  }

  @Override
  /**
   *
   * @throws MALException
   */
  public void sendAcknowledgement() throws MALException
  {
    returnResponse(MALSubmitOperation.SUBMIT_ACK_STAGE, null);
  }

  @Override
  /**
   *
   * @param error
   * @throws MALException
   */
  public void sendError(StandardError error) throws MALException
  {
    returnError(MALSubmitOperation.SUBMIT_ACK_STAGE, error);
  }
}
