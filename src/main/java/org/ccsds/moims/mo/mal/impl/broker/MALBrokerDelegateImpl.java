/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author cooper_sf
 */
public class MALBrokerDelegateImpl extends MALBrokerBaseImpl
{
  final MALBrokerHandler delegate;
  
  public MALBrokerDelegateImpl(MALClose parent, MALBrokerHandler delegate)
  {
    super(parent);
    
    this.delegate = delegate;
  }
  
  @Override
  public void malInitialize(MALBrokerBinding brokerBinding)
  {
    super.malInitialize(brokerBinding);
    delegate.malInitialize(brokerBinding);
  }

  @Override
  public void malFinalize(MALBrokerBinding brokerBinding)
  {
    super.malFinalize(brokerBinding);
    delegate.malFinalize(brokerBinding);
  }
  
  public void handlePublishRegister(MALInteraction interaction, MALPublishRegisterBody body) throws MALInteractionException, MALException
  {
    delegate.handlePublishRegister(interaction, body);
  }

  /**
   * Adds a consumer to this broker.
   * @param hdr Source message.
   * @param body Consumer subscription.
   * @param binding Broker binding.
   */
  public void internalHandleRegister(MALInteraction interaction, MALRegisterBody body, MALBrokerBindingImpl binding) throws MALInteractionException, MALException
  {
    delegate.handleRegister(interaction, body);
  }

  public void handleRegister(MALInteraction interaction, MALRegisterBody body) throws MALInteractionException, MALException
  {
    delegate.handleRegister(interaction, body);
  }
  
  public void handlePublish(MALInteraction interaction, MALPublishBody body) throws MALInteractionException, MALException
  {
    delegate.handlePublish(interaction, body);
  }

  public void handleDeregister(MALInteraction interaction, MALDeregisterBody body) throws MALInteractionException, MALException
  {
    delegate.handleDeregister(interaction, body);
  }

  public void handlePublishDeregister(MALInteraction interaction) throws MALInteractionException, MALException
  {
    delegate.handlePublishDeregister(interaction);
  }

  @Override
  public QoSLevel getProviderQoSLevel(MALMessageHeader hdr)
  {
    return QoSLevel.BESTEFFORT;
  }
}
