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

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import esa.mo.mal.impl.Address;
import esa.mo.mal.impl.MessageSend;
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
   * @throws MALInteractionException if the received message operation is unknown.
   */
  public SubmitInteractionImpl(final MessageSend sender,
          final Address address,
          final Long internalTransId,
          final MALMessage msg) throws MALInteractionException
  {
    super(sender, address, internalTransId, msg);
  }

  @Override
  public MALMessage sendAcknowledgement() throws MALException
  {
    return returnResponse(MALSubmitOperation.SUBMIT_ACK_STAGE, true, (Object[]) null);
  }

  @Override
  public MALMessage sendError(final MALStandardError error) throws MALException
  {
    return returnError(MALSubmitOperation.SUBMIT_ACK_STAGE, error);
  }
}
