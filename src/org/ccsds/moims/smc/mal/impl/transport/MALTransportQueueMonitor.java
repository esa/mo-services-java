/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.smc.mal.impl.transport;

import org.ccsds.moims.smc.mal.api.structures.MALException;
import org.ccsds.moims.smc.mal.api.structures.MALIdentifier;

/**
 *
 * @author cooper_sf
 */
public class MALTransportQueueMonitor extends Thread
{
  private final String controlServiceURI;
  private final ControlCallback controlService;
  private final MALIdentifier clientId;

  public MALTransportQueueMonitor(MALIdentifier clientId, String controlServiceURI, ControlCallback controlService)
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
    public void checkStatus(MALIdentifier clientId) throws MALException;
  }
}
