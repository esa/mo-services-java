/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl;

import org.ccsds.moims.mo.mal.impl.broker.MALBrokerManagerImpl;
import org.ccsds.moims.mo.mal.broker.MALBrokerManager;
import org.ccsds.moims.mo.mal.impl.util.MALClose;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;
import org.ccsds.moims.mo.mal.MAL;
import org.ccsds.moims.mo.mal.consumer.MALConsumerManager;
import org.ccsds.moims.mo.mal.provider.MALProviderManager;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.impl.broker.MALBrokerBindingImpl;
import org.ccsds.moims.mo.mal.impl.broker.MALBrokerHandler;
import org.ccsds.moims.mo.mal.impl.broker.simple.SimpleBrokerHandler;
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
  private final MALInteractionMap imap = new MALInteractionMap();
  private final MALPubSubMap pmap = new MALPubSubMap();
  private final Map<String, MALBrokerBindingImpl> brokerBindingMap = new TreeMap<String, MALBrokerBindingImpl>();
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

    receiver = new MALServiceReceive(this, imap, pmap, brokerBindingMap);
    sender = new MALServiceSend(this, imap, pmap, receiver);
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
  public MALBrokerManager createBrokerManager() throws MALException
  {
    return (MALBrokerManager) addChild(new MALBrokerManagerImpl(this, brokerBindingMap));
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

  public MALInteractionMap getMaps()
  {
    return imap;
  }

  public MALSecurityManager getSecurityManager()
  {
    return securityManager;
  }

  public MALBrokerHandler createBroker()
  {
    String clsName = System.getProperty("org.ccsds.moims.mo.mal.broker.class", SimpleBrokerHandler.class.getName());

    MALBrokerHandler broker = null;
    try
    {
      Class cls = ClassLoader.getSystemClassLoader().loadClass(clsName);

      broker = (MALBrokerHandler) cls.newInstance();
      System.out.println("INFO: Creating internal MAL Broker handler: " + cls.getSimpleName());
    }
    catch (ClassNotFoundException ex)
    {
      System.out.println("WARN: Unable to find MAL Broker handler class: " + clsName);
    }
    catch (InstantiationException ex)
    {
      System.out.println("WARN: Unable to instantiate MAL Broker handler: " + clsName);
    }
    catch (IllegalAccessException ex)
    {
      System.out.println("WARN: IllegalAccessException when instantiating MAL Broker handler class: " + clsName);
    }

    if (null == broker)
    {
      broker = new SimpleBrokerHandler();
      System.out.println("INFO: Creating internal MAL Broker handler: SimpleBrokerHandler");
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
