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
package org.ccsds.moims.mo.mal.impl;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.provider.*;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;

/**
 * Dummy implemenation of the MAL interaction handler. Used to detect fault in the implementation.
 */
public class DummyHandler implements MALInteractionHandler
{
  public void handleInvoke(MALInvoke interaction, MALMessageBody requestBody) throws MALInteractionException, MALException
  {
    throw new MALInteractionException(new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER,
            new Union("Unexpected INVOKE message received")));
  }

  public void handleProgress(MALProgress interaction, MALMessageBody requestBody) throws MALInteractionException, MALException
  {
    throw new MALInteractionException(new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER,
            new Union("Unexpected PROGRESS message received")));
  }

  public void handleRequest(MALRequest interaction, MALMessageBody requestBody) throws MALInteractionException, MALException
  {
    throw new MALInteractionException(new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER,
            new Union("Unexpected REQUEST message received")));
  }

  public void handleSend(MALInteraction interaction, MALMessageBody body) throws MALInteractionException, MALException
  {
    throw new MALInteractionException(new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER,
            new Union("Unexpected SEND message received")));
  }

  public void handleSubmit(MALSubmit interaction, MALMessageBody submitBody) throws MALInteractionException, MALException
  {
    throw new MALInteractionException(new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER,
            new Union("Unexpected SUBMIT message received")));
  }

  @Override
  public void malFinalize(MALProvider provider)
  {
  }

  @Override
  public void malInitialize(MALProvider provider)
  {
  }
}
