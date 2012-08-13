/* ----------------------------------------------------------------------------
 * (C) 2010      European Space Agency
 *               European Space Operations Centre
 *               Darmstadt Germany
 * ----------------------------------------------------------------------------
 * System       : CCSDS MO MAL Implementation
 * Author       : cooper_sf
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
  public MALBrokerManagerImpl(MALContextImpl impl, Map<String, MALBrokerBindingImpl> brokerBindingMap)
  {
    super(impl);

    this.impl = impl;
    this.brokerBindingMap = brokerBindingMap;
  }

  @Override
  public synchronized MALBroker createBroker() throws MALException
  {
    MALBrokerImpl retVal = new MALBrokerImpl(this, impl.getSendingInterface());
    addChild(retVal);

    return retVal;
  }

  @Override
  public MALBroker createBroker(MALBrokerHandler handler) throws IllegalArgumentException, MALException
  {
    MALBrokerBaseImpl retVal = new MALBrokerDelegateImpl(this, handler);
    addChild(retVal);

    return retVal;
  }

  @Override
  public synchronized MALBrokerBinding createBrokerBinding(MALBroker optionalMALBroker,
          String localName,
          String protocol,
          Blob authenticationId,
          QoSLevel[] expectedQos,
          UInteger priorityLevelNumber,
          Map qosProperties) throws MALException
  {
    MALBrokerBinding retVal = null;

    MALBrokerImpl tparent = (MALBrokerImpl) optionalMALBroker;
    if (null == optionalMALBroker)
    {
      tparent = (MALBrokerImpl) createBroker();

      MALTransport transport = TransportSingleton.instance(protocol, impl.getInitialProperties());
      retVal = transport.createBroker(localName,
              authenticationId,
              expectedQos,
              priorityLevelNumber,
              qosProperties);

      if (null != retVal)
      {
        retVal = new MALBrokerBindingTransportWrapper(tparent, retVal);
      }
    }

    if (null == retVal)
    {
      retVal = new MALBrokerBindingImpl(tparent,
              impl,
              localName,
              protocol,
              authenticationId,
              expectedQos,
              priorityLevelNumber,
              qosProperties);
      brokerBindingMap.put(retVal.getURI().getValue(), (MALBrokerBindingImpl) retVal);
    }

    return retVal;
  }

  /**
   *
   * @param optionalMALBroker
   * @param endPoint
   * @param authenticationId
   * @param expectedQos
   * @param priorityLevelNumber
   * @param qosProperties
   * @return
   * @throws MALException
   */
  public MALBrokerBinding createBrokerBinding(
          MALBroker optionalMALBroker,
          MALEndpoint endPoint,
          Blob authenticationId,
          QoSLevel[] expectedQos,
          UInteger priorityLevelNumber,
          Map qosProperties) throws IllegalArgumentException, MALException
  {
    MALBrokerBinding retVal = null;

    MALBrokerImpl tparent = (MALBrokerImpl) optionalMALBroker;
    if (null == optionalMALBroker)
    {
      tparent = (MALBrokerImpl) createBroker();

      MALTransport transport = TransportSingleton.instance(endPoint.getURI(), impl.getInitialProperties());
      retVal = transport.createBroker(endPoint,
              authenticationId,
              expectedQos,
              priorityLevelNumber,
              qosProperties);

      if (null != retVal)
      {
        retVal = new MALBrokerBindingTransportWrapper(tparent, retVal);
      }
    }

    if (null == retVal)
    {
      retVal = new MALBrokerBindingImpl(tparent,
              impl,
              endPoint,
              authenticationId,
              expectedQos,
              priorityLevelNumber,
              qosProperties);
      brokerBindingMap.put(retVal.getURI().getValue(), (MALBrokerBindingImpl) retVal);
    }

    return retVal;
  }
}
