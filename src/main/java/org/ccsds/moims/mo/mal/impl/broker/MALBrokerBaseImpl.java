/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl.broker;

import java.util.Set;
import java.util.TreeSet;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.broker.MALBroker;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.broker.MALBrokerHandler;
import org.ccsds.moims.mo.mal.impl.util.MALClose;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALRegisterBody;

/**
 *
 * @author cooper_sf
 */
public abstract class MALBrokerBaseImpl extends MALClose implements MALBroker, MALBrokerHandler
{
  private final Set<MALBrokerBinding> bindings = new TreeSet<MALBrokerBinding> ();

  public MALBrokerBaseImpl(MALClose parent)
  {
    super(parent);
  }
  
  public void malInitialize(MALBrokerBinding brokerBinding)
  {
    bindings.add(brokerBinding);
  }

  public void malFinalize(MALBrokerBinding brokerBinding)
  {
    bindings.remove(brokerBinding);
  }

  @Override
  public MALBrokerBinding[] getBindings()
  {
    return bindings.toArray(new MALBrokerBinding[bindings.size()]);
  }

  public abstract QoSLevel getProviderQoSLevel(MALMessageHeader hdr);
  
  /**
   * Adds a consumer to this broker.
   * @param hdr Source message.
   * @param body Consumer subscription.
   * @param binding Broker binding.
   */
  public abstract void internalHandleRegister(MALInteraction interaction, MALRegisterBody body, MALBrokerBindingImpl binding) throws MALInteractionException, MALException;
}
