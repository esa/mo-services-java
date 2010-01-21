/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl;

import org.ccsds.moims.mo.mal.impl.provider.MALProviderManagerImpl;
import org.ccsds.moims.mo.mal.impl.consumer.MALConsumerManagerImpl;
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
import org.ccsds.moims.mo.mal.impl.broker.BrokerHandler;
import org.ccsds.moims.mo.mal.impl.broker.simple.SimpleBrokerHandler;
import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.security.MALSecurityManager;
import org.ccsds.moims.mo.mal.security.MALSecurityManagerFactory;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 *
 * @author cooper_sf
 */
public class MALImpl extends MALClose implements MAL
{
  private final Hashtable initialProperties;
  private final MALSecurityManager securityManager;
  private final InteractionMap imap = new InteractionMap();
  private final PubSubMap pmap = new PubSubMap();
  private final Map<String, MALBrokerBindingImpl> brokerBindingMap = new TreeMap<String, MALBrokerBindingImpl>();
  private final MessageReceive receiver;
  private final MessageSend sender;

  public MALImpl(MALSecurityManagerFactory securityFactory, Hashtable properties) throws MALException
  {
    super(null);

    initialProperties = (null == properties) ? null : ((Hashtable)properties.clone());

    if (null != securityFactory)
    {
      securityManager = securityFactory.createSecurityManager(initialProperties);
    }
    else
    {
      securityManager = new NullSecurityManager();
    }

    sender = new MessageSend(securityManager, imap, pmap);
    receiver = new MessageReceive(sender, securityManager, imap, pmap, brokerBindingMap);
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

    org.ccsds.moims.mo.mal.impl.transport.TransportSingleton.close();
  }

  public Hashtable getInitialProperties()
  {
    return initialProperties;
  }

  public MessageSend getSendingInterface()
  {
    return sender;
  }

  public MessageReceive getReceivingInterface()
  {
    return receiver;
  }

  public InteractionMap getInteractionMap()
  {
    return imap;
  }

  public MALSecurityManager getSecurityManager()
  {
    return securityManager;
  }

  public BrokerHandler createBroker()
  {
    String clsName = System.getProperty("org.ccsds.moims.mo.mal.broker.class", SimpleBrokerHandler.class.getName());

    BrokerHandler broker = null;
    try
    {
      Class cls = ClassLoader.getSystemClassLoader().loadClass(clsName);

      broker = (BrokerHandler) cls.newInstance();
      Logging.logMessage("INFO: Creating internal MAL Broker handler: " + cls.getSimpleName());
    }
    catch (ClassNotFoundException ex)
    {
      Logging.logMessage("WARN: Unable to find MAL Broker handler class: " + clsName);
    }
    catch (InstantiationException ex)
    {
      Logging.logMessage("WARN: Unable to instantiate MAL Broker handler: " + clsName);
    }
    catch (IllegalAccessException ex)
    {
      Logging.logMessage("WARN: IllegalAccessException when instantiating MAL Broker handler class: " + clsName);
    }

    if (null == broker)
    {
      broker = new SimpleBrokerHandler();
      Logging.logMessage("INFO: Creating internal MAL Broker handler: SimpleBrokerHandler");
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
