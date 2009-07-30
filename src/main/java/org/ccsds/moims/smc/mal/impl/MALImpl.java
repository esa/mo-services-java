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
import org.ccsds.moims.smc.mal.impl.broker.MALBroker;
import org.ccsds.moims.smc.mal.impl.broker.MALSimpleBrokerHandler;

/**
 *
 * @author cooper_sf
 */
public class MALImpl extends MALClose implements MAL
{
  private final MALServiceMaps maps;
  private final MALBroker brokerHandler;
  private final MALServiceReceive receiver;
  private final MALServiceSend sender;

  public MALImpl(Hashtable properties)
  {
    super(null);

    maps = new MALServiceMaps();
    brokerHandler = createBroker();
    receiver = new MALServiceReceive(this, maps, brokerHandler);
    sender = new MALServiceSend(maps, receiver, brokerHandler);
  }

  public MALConsumerManager createConsumerManager() throws MALException
  {
    return (MALConsumerManager) addChild(new MALConsumerManagerImpl(this));
  }

  public MALProviderManager createProviderManager() throws MALException
  {
    return (MALProviderManager) addChild(new MALProviderManagerImpl(this));
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

  private MALBroker createBroker()
  {
    String clsName = System.getProperty("org.ccsds.moims.smc.mal.broker.class", "org.ccsds.moims.smc.mal.impl.broker.MALSimpleBrokerHandler");

    MALBroker broker = null;
    try
    {
      Class cls = ClassLoader.getSystemClassLoader().loadClass(clsName);

      broker = (MALBroker) cls.newInstance();
      System.out.println("MAL Broker implementation: " + cls.getSimpleName());
    }
    catch (Exception ex)
    {
      broker = new MALSimpleBrokerHandler();
      System.out.println("MAL Broker implementation: MALSimpleBrokerHandler");
    }

    return broker;
  }
}
