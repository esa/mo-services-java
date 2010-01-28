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
  public ProgressInteractionImpl(MessageSend sender, Address address, Identifier internalTransId, MALMessage msg)
  {
    super(sender, address, internalTransId, msg);
  }

  @Override
  /**
   *
   * @param result
   * @throws MALException
   */
  public void sendAcknowledgement(Element result) throws MALException
  {
    ackSent = true;
    returnResponse(MALProgressOperation.PROGRESS_ACK_STAGE, result);
  }

  @Override
  /**
   *
   * @param update
   * @throws MALException
   */
  public void sendUpdate(Element update) throws MALException
  {
    returnResponse(MALProgressOperation.PROGRESS_UPDATE_STAGE, update);
  }

  @Override
  /**
   *
   * @param result
   * @throws MALException
   */
  public void sendResponse(Element result) throws MALException
  {
    returnResponse(MALProgressOperation.PROGRESS_RESPONSE_STAGE, result);
  }

  @Override
  /**
   *
   * @param error
   * @throws MALException
   */
  public void sendError(StandardError error) throws MALException
  {
    Byte stage = MALProgressOperation.PROGRESS_ACK_STAGE;

    if (ackSent)
    {
      stage = MALProgressOperation.PROGRESS_RESPONSE_STAGE;
    }

    returnError(stage, error);
  }

  @Override
  /**
   *
   * @param error
   * @throws MALException
   */
  public void sendUpdateError(StandardError error) throws MALException
  {
    returnError(MALProgressOperation.PROGRESS_UPDATE_STAGE, error);
  }
}
