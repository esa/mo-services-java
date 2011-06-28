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

import org.ccsds.moims.mo.mal.provider.MALProgress;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALProgressOperation;
import org.ccsds.moims.mo.mal.impl.Address;
import org.ccsds.moims.mo.mal.impl.MessageSend;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.StandardError;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * Progress interaction class.
 */
public class ProgressInteractionImpl extends BaseInteractionImpl implements MALProgress
{
  private boolean ackSent = false;

  /**
   * Constructor.
   * @param sender Used to return the messages.
   * @param address Details of this endpoint.
   * @param internalTransId Internal transaction identifier.
   * @param msg The source message.
   */
  public ProgressInteractionImpl(MessageSend sender,
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
  public org.ccsds.moims.mo.mal.transport.MALMessage sendAcknowledgement(Element result) throws MALException
  {
    ackSent = true;
    return returnResponse(MALProgressOperation.PROGRESS_ACK_STAGE, result);
  }

  @Override
  /**
   *
   * @param update
   * @throws MALException
   */
  public org.ccsds.moims.mo.mal.transport.MALMessage sendUpdate(Element update) throws MALException
  {
    return returnResponse(MALProgressOperation.PROGRESS_UPDATE_STAGE, update);
  }

  @Override
  /**
   *
   * @param result
   * @throws MALException
   */
  public org.ccsds.moims.mo.mal.transport.MALMessage sendResponse(Element result) throws MALException
  {
    return returnResponse(MALProgressOperation.PROGRESS_RESPONSE_STAGE, result);
  }

  @Override
  /**
   *
   * @param error
   * @throws MALException
   */
  public org.ccsds.moims.mo.mal.transport.MALMessage sendError(StandardError error) throws MALException
  {
    Byte stage = MALProgressOperation.PROGRESS_ACK_STAGE;

    if (ackSent)
    {
      stage = MALProgressOperation.PROGRESS_RESPONSE_STAGE;
    }

    return returnError(stage, error);
  }

  @Override
  /**
   *
   * @param error
   * @throws MALException
   */
  public org.ccsds.moims.mo.mal.transport.MALMessage sendUpdateError(StandardError error) throws MALException
  {
    return returnError(MALProgressOperation.PROGRESS_UPDATE_STAGE, error);
  }
}
