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

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.broker.MALBrokerHandler;
import org.ccsds.moims.mo.mal.impl.util.MALClose;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.transport.*;

/**
 * Represents a broker that is handled externally.
 */
public class MALBrokerDelegateImpl extends MALBrokerBaseImpl
{
  private final MALBrokerHandler delegate;

  /**
   * Constructor.
   *
   * @param parent Parent component.
   * @param delegate Broker handler to delegate too.
   */
  public MALBrokerDelegateImpl(final MALClose parent, final MALBrokerHandler delegate)
  {
    super(parent);

    this.delegate = delegate;
  }

  @Override
  public void malInitialize(final MALBrokerBinding brokerBinding)
  {
    super.malInitialize(brokerBinding);
    delegate.malInitialize(brokerBinding);
  }

  @Override
  public void malFinalize(final MALBrokerBinding brokerBinding)
  {
    super.malFinalize(brokerBinding);
    delegate.malFinalize(brokerBinding);
  }

  @Override
  public void handlePublishRegister(final MALInteraction interaction, final MALPublishRegisterBody body)
          throws MALInteractionException, MALException
  {
    delegate.handlePublishRegister(interaction, body);
  }

  @Override
  public void internalHandleRegister(final MALInteraction interaction,
          final MALRegisterBody body,
          final MALBrokerBindingImpl binding)
          throws MALInteractionException, MALException
  {
    delegate.handleRegister(interaction, body);
  }

  @Override
  public void handleRegister(final MALInteraction interaction, final MALRegisterBody body)
          throws MALInteractionException, MALException
  {
    delegate.handleRegister(interaction, body);
  }

  @Override
  public void handlePublish(final MALInteraction interaction, final MALPublishBody body)
          throws MALInteractionException, MALException
  {
    delegate.handlePublish(interaction, body);
  }

  @Override
  public void handleDeregister(final MALInteraction interaction, final MALDeregisterBody body)
          throws MALInteractionException, MALException
  {
    delegate.handleDeregister(interaction, body);
  }

  @Override
  public void handlePublishDeregister(final MALInteraction interaction)
          throws MALInteractionException, MALException
  {
    delegate.handlePublishDeregister(interaction);
  }

  @Override
  public QoSLevel getProviderQoSLevel(final MALMessageHeader hdr)
  {
    return QoSLevel.BESTEFFORT;
  }
}
