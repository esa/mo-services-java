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
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.provider.MALInvoke;
import org.ccsds.moims.mo.mal.provider.MALProgress;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALRequest;
import org.ccsds.moims.mo.mal.provider.MALSubmit;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.StandardError;
import org.ccsds.moims.mo.mal.structures.Union;

/**
 * Dummy implemenation of the MAL interaction handler. Used to detect fault in the implementation.
 */
public class DummyHandler implements MALInteractionHandler
{
  @Override
  public void handleInvoke(MALInvoke interaction, Element requestBody) throws MALException
  {
    throw new MALException(new StandardError(MALHelper.INTERNAL_ERROR_NUMBER,
            new Union("Unexpected INVOKE message received")));
  }

  @Override
  public void handleProgress(MALProgress interaction, Element requestBody) throws MALException
  {
    throw new MALException(new StandardError(MALHelper.INTERNAL_ERROR_NUMBER,
            new Union("Unexpected PROGRESS message received")));
  }

  @Override
  public void handleRequest(MALRequest interaction, Element requestBody) throws MALException
  {
    throw new MALException(new StandardError(MALHelper.INTERNAL_ERROR_NUMBER,
            new Union("Unexpected REQUEST message received")));
  }

  @Override
  public void handleSend(MALInteraction interaction, Element body) throws MALException
  {
    throw new MALException(new StandardError(MALHelper.INTERNAL_ERROR_NUMBER,
            new Union("Unexpected SEND message received")));
  }

  @Override
  public void handleSubmit(MALSubmit interaction, Element submitBody) throws MALException
  {
    throw new MALException(new StandardError(MALHelper.INTERNAL_ERROR_NUMBER,
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
