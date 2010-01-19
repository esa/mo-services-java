/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl.broker;

import org.ccsds.moims.mo.mal.impl.*;
import java.util.Hashtable;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 *
 * @author cooper_sf
 */
public class MALBrokerBindingImpl extends MALServiceComponentImpl implements MALInternalBrokerBinding
{
  private final MALBrokerImpl brokerImpl;

  public MALBrokerBindingImpl(MALBrokerImpl parent, MALImpl impl, Map<String, MALBrokerBindingImpl> brokerMap, String localName, boolean supportMultipleProtocols, String protocol, MALService service, Blob authenticationId, QoSLevel[] expectedQos, int priorityLevelNumber, Hashtable qosProperties) throws MALException
  {
    super(parent, impl, localName, protocol, service, authenticationId, expectedQos, priorityLevelNumber, qosProperties, null);

    this.brokerImpl = parent;

    brokerMap.put(getURI().getValue(), this);

    System.out.println("INFO: Creating internal MAL Broker for service: " + service.getName() + " on protocol: " + protocol + " with URI: " + this.localUri);
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

  public MALBrokerImpl getParent()
  {
    return brokerImpl;
  }
}
