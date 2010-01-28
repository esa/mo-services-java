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
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.impl.MALImpl;
import org.ccsds.moims.mo.mal.impl.ServiceComponentImpl;
import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.QoSLevel;

/**
 * Implementation ofo MALBrokerBinding for MAL level brokers.
 */
public class MALBrokerBindingImpl extends ServiceComponentImpl implements MALInternalBrokerBinding
{
  private final MALBrokerImpl brokerImpl;

  MALBrokerBindingImpl(MALBrokerImpl parent,
          MALImpl impl,
          String localName,
          String protocol,
          MALService service,
          Blob authenticationId,
          QoSLevel[] expectedQos,
          int priorityLevelNumber,
          Hashtable qosProperties) throws MALException
  {
    super(parent,
            impl,
            localName,
            protocol,
            service,
            authenticationId,
            expectedQos,
            priorityLevelNumber,
            qosProperties,
            null);

    this.brokerImpl = parent;

    Logging.logMessage("INFO: Creating internal MAL Broker for service: "
            + service.getName() + " on protocol: " + protocol + " with URI: " + this.localUri);
  }

  @Override
  public boolean isMALLevelBroker()
  {
    return true;
  }

  @Override
  public void activate() throws MALException
  {
    // no op
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
