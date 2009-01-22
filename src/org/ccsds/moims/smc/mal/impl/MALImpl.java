/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ccsds.moims.smc.mal.impl;

import org.ccsds.moims.smc.mal.impl.util.MALClose;
import java.util.Hashtable;
import org.ccsds.moims.smc.mal.api.MAL;
import org.ccsds.moims.smc.mal.api.consumer.MALConsumerManager;
import org.ccsds.moims.smc.mal.api.provider.MALProviderManager;
import org.ccsds.moims.smc.mal.api.structures.MALException;

/**
 *
 * @author cooper_sf
 */
public class MALImpl extends MALClose implements MAL
{
  private final MALServiceMaps maps;
  private final MALBrokerHandler brokerHandler;
  private final MALServiceReceive receiver;
  private final MALServiceSend sender;

  public MALImpl(Hashtable properties)
  {
    super(null);

    maps = new MALServiceMaps();
    brokerHandler = new MALBrokerHandler();
    receiver = new MALServiceReceive(this, maps, brokerHandler);
    sender = new MALServiceSend(maps, receiver, brokerHandler);
  }

  public MALConsumerManager createConsumerManager() throws MALException
  {
    return (MALConsumerManager)addChild(new MALConsumerManagerImpl(this));
  }

  public MALProviderManager createProviderManager() throws MALException
  {
    return (MALProviderManager)addChild(new MALProviderManagerImpl(this));
  }

  @Override
  public void close() throws MALException
  {
    super.close();

    org.ccsds.moims.smc.mal.impl.transport.MALTransportSingleton.close();
  }

  public MALServiceSend getSendingInterface()
  {
    return sender;
  }

  public MALServiceReceive getReceivingInterface()
  {
    return receiver;
  }

  public MALServiceMaps getMaps()
  {
    return maps;
  }
}
