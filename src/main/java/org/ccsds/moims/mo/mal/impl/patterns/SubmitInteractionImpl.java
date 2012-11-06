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
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.impl.Address;
import org.ccsds.moims.mo.mal.impl.MessageSend;
import org.ccsds.moims.mo.mal.provider.MALSubmit;
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
  public SubmitInteractionImpl(MessageSend sender,
          Address address,
          Long internalTransId,
          MALMessage msg) throws MALInteractionException
  {
    super(sender, address, internalTransId, msg);
  }

  @Override
  /**
   *
   * @throws MALException
   */
  public org.ccsds.moims.mo.mal.transport.MALMessage sendAcknowledgement() throws MALException
  {
    return returnResponse(MALSubmitOperation.SUBMIT_ACK_STAGE, true, (Object[]) null);
  }

  @Override
  /**
   *
   * @param error
   * @throws MALException
   */
  public org.ccsds.moims.mo.mal.transport.MALMessage sendError(MALStandardError error) throws MALException
  {
    return returnError(MALSubmitOperation.SUBMIT_ACK_STAGE, error);
  }
}
