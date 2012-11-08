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
package org.ccsds.moims.mo.mal.impl.patterns;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALInvokeOperation;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.impl.Address;
import org.ccsds.moims.mo.mal.impl.MessageSend;
import org.ccsds.moims.mo.mal.provider.MALInvoke;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * Invoke interaction class.
 */
public class InvokeInteractionImpl extends BaseInteractionImpl implements MALInvoke
{
  private boolean ackSent = false;
  
  /**
   * Constructor.
   * @param sender Used to return the messages.
   * @param address Details of this endpoint.
   * @param internalTransId Internal transaction identifier.
   * @param msg The source message.
   * @throws MALInteractionException if the received message operation is unknown.
   */
  public InvokeInteractionImpl(final MessageSend sender,
          final Address address,
          final Long internalTransId,
          final MALMessage msg) throws MALInteractionException
  {
    super(sender, address, internalTransId, msg);
  }

  @Override
  public MALMessage sendAcknowledgement(final Object... result) throws MALInteractionException, MALException
  {
    ackSent = true;
    return returnResponse(MALInvokeOperation.INVOKE_ACK_STAGE, false, result);
  }

  @Override
  public MALMessage sendResponse(final Object... result) throws MALInteractionException, MALException
  {
    return returnResponse(MALInvokeOperation.INVOKE_RESPONSE_STAGE, true, result);
  }

  @Override
  public MALMessage sendError(final MALStandardError error) throws MALException
  {
    UOctet stage = MALInvokeOperation.INVOKE_ACK_STAGE;

    if (ackSent)
    {
      stage = MALInvokeOperation.INVOKE_RESPONSE_STAGE;
    }

    return returnError(stage, error);
  }
}
