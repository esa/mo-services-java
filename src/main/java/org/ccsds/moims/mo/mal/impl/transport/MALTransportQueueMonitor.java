/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl.transport;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 *
 * @author cooper_sf
 */
public class MALTransportQueueMonitor extends Thread
{
  private final String controlServiceURI;
  private final ControlCallback controlService;
  private final Identifier clientId;

  public MALTransportQueueMonitor(Identifier clientId, String controlServiceURI, ControlCallback controlService)
  {
    String strProtocol = controlServiceURI;

    int iPro = controlServiceURI.indexOf("://");
    if (-1 != iPro)
    {
      strProtocol = controlServiceURI.substring(0, iPro);
    }

    this.controlServiceURI = strProtocol;
    this.controlService = controlService;
    this.clientId = clientId;
  }

  @Override
  public void run()
  {
    try
    {
      while (true)
      {
        try
        {
          Thread.sleep(1000);

          controlService.checkStatus(clientId);
        }
        catch (Exception ex)
        {
          ex.printStackTrace();
        }
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  public interface ControlCallback
  {
    public void checkStatus(Identifier clientId) throws MALException;
  }
}
