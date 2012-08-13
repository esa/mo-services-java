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

import java.util.List;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.impl.Address;
import org.ccsds.moims.mo.mal.impl.DummyHandler;
import org.ccsds.moims.mo.mal.impl.MALContextImpl;
import org.ccsds.moims.mo.mal.impl.util.MALClose;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALTransport;

/**
 * Wrapper class for Transport level broker bindings.
 */
public class MALBrokerBindingTransportWrapper extends MALClose implements MALInternalBrokerBinding
{
  private final MALBrokerBinding transportDelegate;

  MALBrokerBindingTransportWrapper(MALClose parent,
          MALBrokerBinding transportDelegate) throws MALException
  {
    super(parent);

    this.transportDelegate = transportDelegate;
  }

  @Override
  public void close() throws MALException
  {
    transportDelegate.close();
  }

  @Override
  public Blob getAuthenticationId()
  {
    return transportDelegate.getAuthenticationId();
  }

  @Override
  public URI getURI()
  {
    return transportDelegate.getURI();
  }

  public MALMessage sendNotify(UShort area, UShort service, UShort operation, UOctet version, URI subscriber, Long transactionId, IdentifierList domainId, Identifier networkZone, SessionType sessionType, Identifier sessionName, QoSLevel notifyQos, Map notifyQosProps, UInteger notifyPriority, Identifier subscriptionId, UpdateHeaderList updateHeaderList, List... updateList) throws IllegalArgumentException, MALInteractionException, MALException
  {
    return transportDelegate.sendNotify(area, service, operation, version, subscriber, transactionId, domainId, networkZone, sessionType, sessionName, notifyQos, notifyQosProps, notifyPriority, subscriptionId, updateHeaderList, updateList);
  }

  public MALMessage sendNotify(MALOperation op, URI subscriber, Long transactionId, IdentifierList domainId, Identifier networkZone, SessionType sessionType, Identifier sessionName, QoSLevel notifyQos, Map notifyQosProps, UInteger notifyPriority, Identifier subscriptionId, UpdateHeaderList updateHeaderList, List... updateList) throws IllegalArgumentException, MALInteractionException, MALException
  {
    return transportDelegate.sendNotify(op, subscriber, transactionId, domainId, networkZone, sessionType, sessionName, notifyQos, notifyQosProps, notifyPriority, subscriptionId, updateHeaderList, updateList);
  }

  public MALMessage sendNotifyError(UShort area, UShort service, UShort operation, UOctet version, URI subscriber, Long transactionId, IdentifierList domainId, Identifier networkZone, SessionType sessionType, Identifier sessionName, QoSLevel notifyQos, Map notifyQosProps, UInteger notifyPriority, MALStandardError error) throws IllegalArgumentException, MALInteractionException, MALException
  {
    return transportDelegate.sendNotifyError(area, service, operation, version, subscriber, transactionId, domainId, networkZone, sessionType, sessionName, notifyQos, notifyQosProps, notifyPriority, error);
  }

  public MALMessage sendNotifyError(MALOperation op, URI subscriber, Long transactionId, IdentifierList domainId, Identifier networkZone, SessionType sessionType, Identifier sessionName, QoSLevel notifyQos, Map notifyQosProps, UInteger notifyPriority, MALStandardError error) throws IllegalArgumentException, MALInteractionException, MALException
  {
    return transportDelegate.sendNotifyError(op, subscriber, transactionId, domainId, networkZone, sessionType, sessionName, notifyQos, notifyQosProps, notifyPriority, error);
  }

  public MALMessage sendPublishError(UShort area, UShort service, UShort operation, UOctet version, URI publisher, Long transactionId, IdentifierList domainId, Identifier networkZone, SessionType sessionType, Identifier sessionName, QoSLevel qos, Map qosProps, UInteger priority, MALStandardError error) throws IllegalArgumentException, MALInteractionException, MALException
  {
    return transportDelegate.sendPublishError(area, service, operation, version, publisher, transactionId, domainId, networkZone, sessionType, sessionName, qos, qosProps, priority, error);
  }

  public MALMessage sendPublishError(MALOperation op, URI publisher, Long transactionId, IdentifierList domainId, Identifier networkZone, SessionType sessionType, Identifier sessionName, QoSLevel qos, Map qosProps, UInteger priority, MALStandardError error) throws IllegalArgumentException, MALInteractionException, MALException
  {
    return transportDelegate.sendPublishError(op, publisher, transactionId, domainId, networkZone, sessionType, sessionName, qos, qosProps, priority, error);
  }
}
