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

import org.ccsds.moims.mo.mal.provider.MALRequest;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.impl.Address;
import org.ccsds.moims.mo.mal.impl.MessageSend;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.structures.StandardError;

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
   */
  public RequestInteractionImpl(MessageSend sender,
          Address address,
          Identifier internalTransId,
          MALMessage msg) throws MALException
  {
    super(sender, address, internalTransId, msg);
  }

  @Override
  /**
   *
   * @param result
   * @throws MALException
   */
  public void sendResponse(Element result) throws MALException
  {
    returnResponse(MALRequestOperation.REQUEST_RESPONSE_STAGE, result);
  }

  @Override
  /**
   *
   * @param error
   * @throws MALException
   */
  public void sendError(StandardError error) throws MALException
  {
    returnError(MALRequestOperation.REQUEST_RESPONSE_STAGE, error);
  }
}
