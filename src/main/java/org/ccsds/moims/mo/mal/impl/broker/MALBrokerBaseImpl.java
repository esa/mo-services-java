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
 * Base class used by the internal brokers.
 */
public abstract class MALBrokerBaseImpl extends MALClose implements MALBroker, MALBrokerHandler
{
  private final Set<MALBrokerBinding> bindings = new TreeSet<MALBrokerBinding> ();

  /**
   * Constructor.
   * 
   * @param parent Parent component.
   */
  public MALBrokerBaseImpl(final MALClose parent)
  {
    super(parent);
  }
  
  @Override
  public void malInitialize(final MALBrokerBinding brokerBinding)
  {
    bindings.add(brokerBinding);
  }

  @Override
  public void malFinalize(final MALBrokerBinding brokerBinding)
  {
    bindings.remove(brokerBinding);
  }

  @Override
  public MALBrokerBinding[] getBindings()
  {
    return bindings.toArray(new MALBrokerBinding[bindings.size()]);
  }

  /**
   * Returns the QoS used when contacting the provider.
   * @param hdr The supplied header message.
   * @return The required QoS level.
   */
  public abstract QoSLevel getProviderQoSLevel(final MALMessageHeader hdr);
  
  /**
   * Adds a consumer to this broker.
   * @param interaction The interaction object.
   * @param body Consumer subscription.
   * @param binding Broker binding.
   * @throws MALInteractionException On error.
   * @throws MALException On internal error.
   */
  public abstract void internalHandleRegister(final MALInteraction interaction,
          final MALRegisterBody body,
          final MALBrokerBindingImpl binding) throws MALInteractionException, MALException;
}
