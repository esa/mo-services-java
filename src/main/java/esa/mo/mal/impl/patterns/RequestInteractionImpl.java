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
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MALStandardError;
import esa.mo.mal.impl.Address;
import esa.mo.mal.impl.MessageSend;
import org.ccsds.moims.mo.mal.provider.MALRequest;
import org.ccsds.moims.mo.mal.transport.MALEncodedBody;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * Request interaction class.
 */
public class RequestInteractionImpl extends BaseInteractionImpl implements MALRequest
{
  /**
   * Constructor.
   * @param sender Used to return the messages.
   * @param address Details of this endpoint.
   * @param internalTransId Internal transaction identifier.
   * @param msg The source message.
   * @throws MALInteractionException if the received message operation is unknown.
   */
  public RequestInteractionImpl(final MessageSend sender,
          final Address address,
          final Long internalTransId,
          final MALMessage msg) throws MALInteractionException
  {
    super(sender, address, internalTransId, msg);
  }

  @Override
  public MALMessage sendResponse(final Object... result) throws MALInteractionException, MALException
  {
    return returnResponse(MALRequestOperation.REQUEST_RESPONSE_STAGE, true, result);
  }

  @Override
  public MALMessage sendResponse(final MALEncodedBody body) throws MALInteractionException, MALException
  {
    return returnResponse(MALRequestOperation.REQUEST_RESPONSE_STAGE, true, body);
  }

  @Override
  public MALMessage sendError(final MALStandardError error) throws MALException
  {
    return returnError(MALRequestOperation.REQUEST_RESPONSE_STAGE, error);
  }
}
