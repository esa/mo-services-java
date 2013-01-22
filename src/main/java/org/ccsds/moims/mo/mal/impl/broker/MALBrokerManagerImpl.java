/* ----------------------------------------------------------------------------
 * (C) 2010      European Space Agency
 *               European Space Operations Centre
 *               Darmstadt Germany
 * ----------------------------------------------------------------------------
 * System       : CCSDS MO MAL Implementation
 * Author       : Sam Cooper
 *
 * ----------------------------------------------------------------------------
 */
package org.ccsds.moims.mo.mal.impl.broker;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.broker.MALBroker;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.broker.MALBrokerHandler;
import org.ccsds.moims.mo.mal.broker.MALBrokerManager;
import org.ccsds.moims.mo.mal.impl.MALContextImpl;
import org.ccsds.moims.mo.mal.impl.transport.TransportSingleton;
import org.ccsds.moims.mo.mal.impl.util.MALClose;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALTransport;

/**
 * Implements the MALBrokerManager interface.
 */
public class MALBrokerManagerImpl extends MALClose implements MALBrokerManager
{
  private final MALContextImpl impl;
  private final Map<String, MALBrokerBindingImpl> brokerBindingMap;

  /**
   * Constructor.
   *
   * @param impl MAL implementation.
   * @param brokerBindingMap Broker binding map.
   */
  public MALBrokerManagerImpl(final MALContextImpl impl, final Map<String, MALBrokerBindingImpl> brokerBindingMap)
  {
    super(impl);

    this.impl = impl;
    this.brokerBindingMap = brokerBindingMap;
  }

  @Override
  public synchronized MALBroker createBroker() throws MALException
  {
    return (MALBroker) addChild(new MALBrokerImpl(this));
  }

  @Override
  public MALBroker createBroker(final MALBrokerHandler handler) throws IllegalArgumentException, MALException
  {
    return (MALBroker) addChild(new MALBrokerImpl(this, handler));
  }

  @Override
  public synchronized MALBrokerBinding createBrokerBinding(final MALBroker optionalMALBroker,
          final String localName,
          final String protocol,
          final Blob authenticationId,
          final QoSLevel[] expectedQos,
          final UInteger priorityLevelNumber,
          final Map qosProperties) throws MALException
  {
    MALBrokerBinding retVal = null;

    MALBrokerImpl tparent = (MALBrokerImpl) optionalMALBroker;
    if (null == optionalMALBroker)
    {
      tparent = (MALBrokerImpl) createBroker();

      final MALTransport transport = TransportSingleton.instance(protocol, impl.getInitialProperties());
      retVal = transport.createBroker(localName,
              authenticationId,
              expectedQos,
              priorityLevelNumber,
              qosProperties);

      if (null != retVal)
      {
        retVal = (MALBrokerBinding) addChild(new MALBrokerBindingTransportWrapper(tparent, retVal));
      }
    }

    if (null == retVal)
    {
      retVal = (MALBrokerBinding) addChild(new MALBrokerBindingImpl(tparent,
              impl,
              localName,
              protocol,
              authenticationId,
              expectedQos,
              priorityLevelNumber,
              qosProperties));
      ((MALBrokerBindingImpl) retVal).init();
      brokerBindingMap.put(retVal.getURI().getValue(), (MALBrokerBindingImpl) retVal);
    }

    return retVal;
  }

  @Override
  public MALBrokerBinding createBrokerBinding(
          final MALBroker optionalMALBroker,
          final MALEndpoint endPoint,
          final Blob authenticationId,
          final QoSLevel[] expectedQos,
          final UInteger priorityLevelNumber,
          final Map qosProperties) throws IllegalArgumentException, MALException
  {
    MALBrokerBinding retVal = null;

    MALBrokerImpl tparent = (MALBrokerImpl) optionalMALBroker;
    if (null == optionalMALBroker)
    {
      tparent = (MALBrokerImpl) createBroker();

      final MALTransport transport = TransportSingleton.instance(endPoint.getURI(), impl.getInitialProperties());
      retVal = transport.createBroker(endPoint,
              authenticationId,
              expectedQos,
              priorityLevelNumber,
              qosProperties);

      if (null != retVal)
      {
        retVal = (MALBrokerBinding) addChild(new MALBrokerBindingTransportWrapper(tparent, retVal));
      }
    }

    if (null == retVal)
    {
      retVal = (MALBrokerBinding) addChild(new MALBrokerBindingImpl(tparent,
              impl,
              endPoint,
              authenticationId,
              expectedQos,
              priorityLevelNumber,
              qosProperties));
      ((MALBrokerBindingImpl) retVal).init();
      brokerBindingMap.put(retVal.getURI().getValue(), (MALBrokerBindingImpl) retVal);
    }

    return retVal;
  }

  @Override
  protected void thisObjectClose() throws MALException
  {
    super.thisObjectClose();
    
    // we are closing this so make sure the broker binding map shared with out MAL context is empty too.
    brokerBindingMap.clear();
  }
}
