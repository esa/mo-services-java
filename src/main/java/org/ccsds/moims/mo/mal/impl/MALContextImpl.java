/* ----------------------------------------------------------------------------
 * (C) 2010      European Space Agency
 *               European Space Operations Centre
 *               Darmstadt Germany
 * ----------------------------------------------------------------------------
 * System       : CCSDS MO MALContext Implementation
 * Author       : Sam Cooper
 *
 * ----------------------------------------------------------------------------
 */
package org.ccsds.moims.mo.mal.impl;

import java.util.Map;
import java.util.TreeMap;
import org.ccsds.moims.mo.mal.MALContext;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.accesscontrol.MALAccessControl;
import org.ccsds.moims.mo.mal.accesscontrol.MALAccessControlFactory;
import org.ccsds.moims.mo.mal.accesscontrol.MALCheckErrorException;
import org.ccsds.moims.mo.mal.broker.MALBrokerManager;
import org.ccsds.moims.mo.mal.consumer.MALConsumerManager;
import org.ccsds.moims.mo.mal.impl.broker.MALBrokerBindingImpl;
import org.ccsds.moims.mo.mal.impl.broker.MALBrokerManagerImpl;
import org.ccsds.moims.mo.mal.impl.consumer.MALConsumerManagerImpl;
import org.ccsds.moims.mo.mal.impl.provider.MALProviderManagerImpl;
import org.ccsds.moims.mo.mal.impl.transport.TransportSingleton;
import org.ccsds.moims.mo.mal.impl.util.MALClose;
import org.ccsds.moims.mo.mal.provider.MALProviderManager;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALTransport;

/**
 * Implementation of the MALContext.
 */
public class MALContextImpl extends MALClose implements MALContext
{
  private final Map initialProperties;
  private final MALAccessControl securityManager;
  private final InteractionConsumerMap icmap = new InteractionConsumerMap();
  private final InteractionProviderMap ipmap = new InteractionProviderMap();
  private final InteractionPubSubMap ipsmap = new InteractionPubSubMap();
  private final Map<String, MALBrokerBindingImpl> brokerBindingMap = new TreeMap<String, MALBrokerBindingImpl>();
  private final MessageReceive receiver;
  private final MessageSend sender;

  /**
   * Constructor.
   * @param securityFactory The security factory.
   * @param properties initial qos properties.
   * @throws MALException on error.
   */
  public MALContextImpl(final MALAccessControlFactory securityFactory, final Map properties) throws MALException
  {
    super(null);

    initialProperties = properties;

    if (null != securityFactory)
    {
      securityManager = securityFactory.createAccessControl(initialProperties);
    }
    else
    {
      securityManager = new NullSecurityManager();
    }

    sender = new MessageSend(securityManager, icmap, ipmap, ipsmap);
    receiver = new MessageReceive(sender, securityManager, icmap, ipmap, ipsmap, brokerBindingMap);
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
  public MALTransport getTransport(final URI uri) throws MALException
  {
    return TransportSingleton.instance(uri, initialProperties);
  }

  @Override
  public MALTransport getTransport(final String protocol) throws MALException
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
  public Map getInitialProperties()
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
    public MALMessage check(final MALMessage msg) throws IllegalArgumentException, MALCheckErrorException
    {
      return msg;
    }
  }
}
