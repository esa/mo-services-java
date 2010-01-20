/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ccsds.moims.mo.mal.impl;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.provider.MALInvoke;
import org.ccsds.moims.mo.mal.provider.MALProgress;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALRequest;
import org.ccsds.moims.mo.mal.provider.MALSubmit;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 *
 * @author cooper_sf
 */
public class DummyHandler implements MALInteractionHandler
{
  @Override
  public void handleInvoke(MALInvoke interaction, Element requestBody) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void handleProgress(MALProgress interaction, Element requestBody) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void handleRequest(MALRequest interaction, Element requestBody) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void handleSend(MALInteraction interaction, Element body) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void handleSubmit(MALSubmit interaction, Element submitBody) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void malFinalize(MALProvider provider)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void malInitialize(MALProvider provider)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
