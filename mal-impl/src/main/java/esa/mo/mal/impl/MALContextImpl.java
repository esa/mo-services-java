/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java Implementation
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * ----------------------------------------------------------------------------
 */
package esa.mo.mal.impl;

import esa.mo.mal.impl.broker.MALBrokerBindingImpl;
import esa.mo.mal.impl.broker.MALBrokerManagerImpl;
import esa.mo.mal.impl.consumer.MALConsumerManagerImpl;
import esa.mo.mal.impl.provider.MALProviderManagerImpl;
import esa.mo.mal.impl.transport.TransportSingleton;
import esa.mo.mal.impl.util.MALClose;
import java.util.HashMap;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALContext;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.accesscontrol.MALAccessControl;
import org.ccsds.moims.mo.mal.accesscontrol.MALAccessControlFactory;
import org.ccsds.moims.mo.mal.accesscontrol.MALCheckErrorException;
import org.ccsds.moims.mo.mal.broker.MALBrokerManager;
import org.ccsds.moims.mo.mal.consumer.MALConsumerManager;
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
  private final InteractionPubSubMap ipsmap = new InteractionPubSubMap();
  private final Map<String, MALBrokerBindingImpl> brokerBindingMap = new HashMap<String, MALBrokerBindingImpl>();
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

    sender = new MessageSend(securityManager, icmap, ipsmap);
    receiver = new MessageReceive(sender, securityManager, icmap, ipsmap, brokerBindingMap);
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
  public MALAccessControl getAccessControl() throws MALException
  {
    return securityManager;
  }

  @Override
  public void close() throws MALException
  {
    super.close();

    esa.mo.mal.impl.transport.TransportSingleton.close();
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
