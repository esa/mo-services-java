/* ----------------------------------------------------------------------------
 * (C) 2010      European Space Agency
 *               European Space Operations Centre
 *               Darmstadt Germany
 * ----------------------------------------------------------------------------
 * System       : CCSDS MO MALContext Implementation
 * Author       : cooper_sf
 *
 * ----------------------------------------------------------------------------
 */
package org.ccsds.moims.mo.mal.impl;

import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;
import org.ccsds.moims.mo.mal.MALContext;
import org.ccsds.moims.mo.mal.consumer.MALConsumerManager;
import org.ccsds.moims.mo.mal.provider.MALProviderManager;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.broker.MALBrokerManager;
import org.ccsds.moims.mo.mal.impl.broker.MALBrokerBindingImpl;
import org.ccsds.moims.mo.mal.impl.broker.MALBrokerManagerImpl;
import org.ccsds.moims.mo.mal.impl.consumer.MALConsumerManagerImpl;
import org.ccsds.moims.mo.mal.impl.provider.MALProviderManagerImpl;
import org.ccsds.moims.mo.mal.impl.transport.TransportSingleton;
import org.ccsds.moims.mo.mal.impl.util.MALClose;
import org.ccsds.moims.mo.mal.accesscontrol.MALAccessControl;
import org.ccsds.moims.mo.mal.accesscontrol.MALAccessControlFactory;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALTransport;

/**
 * Implementation of the MALContext.
 */
public class MALContextImpl extends MALClose implements MALContext
{
  private final Hashtable initialProperties;
  private final MALAccessControl securityManager;
  private final InteractionMap imap = new InteractionMap();
  private final PubSubMap pmap = new PubSubMap();
  private final Map<String, MALBrokerBindingImpl> brokerBindingMap = new TreeMap<String, MALBrokerBindingImpl>();
  private final MessageReceive receiver;
  private final MessageSend sender;

  /**
   * Constructor.
   * @param securityFactory The security factory.
   * @param properties initial qos properties.
   * @throws MALException on error.
   */
  public MALContextImpl(MALAccessControlFactory securityFactory, Hashtable properties) throws MALException
  {
    super(null);

    initialProperties = (null == properties) ? null : ((Hashtable) properties.clone());

    if (null != securityFactory)
    {
      securityManager = securityFactory.createAccessControl(initialProperties);
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
  public MALTransport getTransport(URI uri) throws MALException
  {
    return TransportSingleton.instance(uri, initialProperties);
  }

  @Override
  public MALTransport getTransport(String protocol) throws MALException
  {
    return TransportSingleton.instance(protocol, initialProperties);
  }

  @Override
  public void close() throws MALException
  {
    super.close();

    org.ccsds.moims.mo.mal.impl.transport.TransportSingleton.close();
  }

  /**
   * Returns the qos properties used in the creation of this MALContext.
   * @return the QOS properties.
   */
  public Hashtable getInitialProperties()
  {
    return initialProperties;
  }

  /**
   * Returns the sending class.
   * @return The sender.
   */
  public MessageSend getSendingInterface()
  {
    return sender;
  }

  /**
   * Returns the receiving class.
   * @return The receiver.
   */
  public MessageReceive getReceivingInterface()
  {
    return receiver;
  }

  /**
   * Returns the active security manager.
   * @return the security manager.
   */
  public MALAccessControl getSecurityManager()
  {
    return securityManager;
  }

  private static final class NullSecurityManager implements MALAccessControl
  {
    @Override
    public MALMessage check(MALMessage msg) throws MALException
    {
      return msg;
    }
  }
}
