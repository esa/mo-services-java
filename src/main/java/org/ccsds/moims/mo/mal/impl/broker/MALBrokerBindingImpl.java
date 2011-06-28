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
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.impl.MALContextImpl;
import org.ccsds.moims.mo.mal.impl.ServiceComponentImpl;
import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.transport.MALEndPoint;

/**
 * Implementation ofo MALBrokerBinding for MAL level brokers.
 */
public class MALBrokerBindingImpl extends ServiceComponentImpl implements MALInternalBrokerBinding
{
  private final MALBrokerImpl brokerImpl;

  MALBrokerBindingImpl(MALBrokerImpl parent,
          MALContextImpl impl,
          String localName,
          String protocol,
          Blob authenticationId,
          QoSLevel[] expectedQos,
          int priorityLevelNumber,
          Hashtable qosProperties) throws MALException
  {
    super(parent,
            impl,
            localName,
            protocol,
            null,
            authenticationId,
            expectedQos,
            priorityLevelNumber,
            qosProperties,
            null);

    this.brokerImpl = parent;
    this.endpoint.startMessageDelivery();

    Logging.logMessage("INFO: Creating internal MAL Broker for localName: "
            + localName + " on protocol: " + protocol + " with URI: " + this.localUri);
  }


  MALBrokerBindingImpl(MALBrokerImpl parent,
          MALContextImpl impl,
          MALEndPoint endPoint,
          Blob authenticationId,
          QoSLevel[] expectedQos,
          int priorityLevelNumber,
          Hashtable qosProperties) throws MALException
  {
    super(parent,
            impl,
            endPoint,
            null,
            authenticationId,
            expectedQos,
            priorityLevelNumber,
            qosProperties,
            null);

    this.brokerImpl = parent;

    Logging.logMessage("INFO: Creating internal MAL Broker for localName: "
            + localName + " with URI: " + this.localUri);
  }

  @Override
  public Blob getAuthenticationId()
  {
    return authenticationId;
  }

  /**
   * Returns the reference to the top level broker object.
   * @return The parent broker.
   */
  public MALBrokerImpl getBrokerImpl()
  {
    return brokerImpl;
  }
}
