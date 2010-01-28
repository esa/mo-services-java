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

import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.broker.MALBroker;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.broker.MALBrokerManager;
import org.ccsds.moims.mo.mal.impl.MALImpl;
import org.ccsds.moims.mo.mal.impl.transport.TransportSingleton;
import org.ccsds.moims.mo.mal.impl.util.MALClose;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.transport.MALTransport;

/**
 * Implements the MALBrokerManager interface.
 */
public class MALBrokerManagerImpl extends MALClose implements MALBrokerManager
{
  private final MALImpl impl;
  private final Map<String, MALBrokerImpl> brokers = new TreeMap();
  private final Map<String, MALBrokerBindingImpl> brokerBindingMap;

  /**
   * Constructor.
   * @param impl MAL implementation.
   * @param brokerBindingMap Broker binding map.
   */
  public MALBrokerManagerImpl(MALImpl impl, Map<String, MALBrokerBindingImpl> brokerBindingMap)
  {
    super(impl);

    this.impl = impl;
    this.brokerBindingMap = brokerBindingMap;
  }

  @Override
  public synchronized MALBroker createBroker(MALService service) throws MALException
  {
    String key = service.getName().getValue();
    MALBrokerImpl retVal = brokers.get(key);

    if (null == retVal)
    {
      retVal = new MALBrokerImpl(this);
      brokers.put(key, retVal);
      addChild(retVal);
    }

    return retVal;
  }

  @Override
  public synchronized MALBrokerBinding createBrokerBinding(MALBroker optionalMALBroker,
          String localName,
          String protocol,
          MALService service,
          Blob authenticationId,
          QoSLevel[] expectedQos,
          int priorityLevelNumber,
          Hashtable qosProperties) throws MALException
  {
    MALBrokerBinding retVal = null;

    MALBrokerImpl tparent = (MALBrokerImpl) optionalMALBroker;
    if (null == optionalMALBroker)
    {
      tparent = (MALBrokerImpl) createBroker(service);

      MALTransport transport = TransportSingleton.instance(protocol, impl.getInitialProperties());
      retVal = transport.createBroker(localName,
              service,
              authenticationId,
              expectedQos,
              priorityLevelNumber,
              qosProperties);

      if (null != retVal)
      {
        retVal = new MALBrokerBindingTransportWrapper(tparent, impl, transport, service, retVal);
      }
    }

    if (null == retVal)
    {
      retVal = new MALBrokerBindingImpl(tparent,
              impl,
              localName,
              protocol,
              service,
              authenticationId,
              expectedQos,
              priorityLevelNumber,
              qosProperties);
      brokerBindingMap.put(retVal.getURI().getValue(), (MALBrokerBindingImpl) retVal);
    }

    return retVal;
  }

  @Override
  public synchronized void deleteBrokerBinding(String localName, String protocol) throws MALException
  {
  }
}
