/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl;

import org.ccsds.moims.mo.mal.impl.util.MALClose;
import java.util.Hashtable;
import javax.crypto.SecretKey;
import org.ccsds.moims.mo.mal.MAL;
import org.ccsds.moims.mo.mal.consumer.MALConsumerManager;
import org.ccsds.moims.mo.mal.provider.MALProviderManager;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.impl.broker.MALBroker;
import org.ccsds.moims.mo.mal.impl.broker.MALSimpleBrokerHandler;
import org.ccsds.moims.mo.mal.security.MALSecurityManager;
import org.ccsds.moims.mo.mal.security.MALSecurityManagerFactory;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 *
 * @author cooper_sf
 */
public class MALImpl extends MALClose implements MAL
{
  private final MALSecurityManager securityManager;
  private final MALServiceMaps maps;
  private final MALBroker brokerHandler;
  private final MALServiceReceive receiver;
  private final MALServiceSend sender;

  public MALImpl(MALSecurityManagerFactory securityFactory, Hashtable properties) throws MALException
  {
    super(null);

    if (null != securityFactory)
    {
      securityManager = securityFactory.createSecurityManager(properties);
    }
    else
    {
      securityManager = new NullSecurityManager();
    }

    maps = new MALServiceMaps();
    brokerHandler = createBroker();
    receiver = new MALServiceReceive(this, maps, brokerHandler);
    sender = new MALServiceSend(this, maps, receiver, brokerHandler);
  }

  @Override
  public MALConsumerManager createConsumerManager() throws MALException
  {
    return (MALConsumerManager) addChild(new MALConsumerManagerImpl(this));
  }

  @Override
  public MALProviderManager createProviderManager() throws MALException
  {
    return (MALProviderManager) addChild(new MALProviderManagerImpl(this));
  }

  @Override
  public void close() throws MALException
  {
    super.close();

    org.ccsds.moims.mo.mal.impl.transport.MALTransportSingleton.close();
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

  public MALSecurityManager getSecurityManager()
  {
    return securityManager;
  }

  private MALBroker createBroker()
  {
    String clsName = System.getProperty("org.ccsds.moims.mo.mal.broker.class", "org.ccsds.moims.mo.mal.impl.broker.MALSimpleBrokerHandler");

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

  private static final class NullSecurityManager implements MALSecurityManager
  {
    @Override
    public MALMessage check(MALMessage msg) throws MALException
    {
      return msg;
    }
  }
}
