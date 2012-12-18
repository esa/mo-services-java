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

import java.util.List;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.impl.util.MALClose;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALTransmitErrorListener;

/**
 * Wrapper class for Transport level broker bindings.
 */
public class MALBrokerBindingTransportWrapper extends MALClose implements MALBrokerBinding
{
  private final MALBrokerBinding transportDelegate;

  MALBrokerBindingTransportWrapper(final MALClose parent,
          final MALBrokerBinding transportDelegate) throws MALException
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

  @Override
  public MALMessage sendNotify(final UShort area,
          final UShort service,
          final UShort operation,
          final UOctet version,
          final URI subscriber,
          final Long transactionId,
          final IdentifierList domainId,
          final Identifier networkZone,
          final SessionType sessionType,
          final Identifier sessionName,
          final QoSLevel notifyQos,
          final Map notifyQosProps,
          final UInteger notifyPriority,
          final Identifier subscriptionId,
          final UpdateHeaderList updateHeaderList,
          final List... updateList) throws IllegalArgumentException, MALInteractionException, MALException
  {
    return transportDelegate.sendNotify(area,
            service,
            operation,
            version,
            subscriber,
            transactionId,
            domainId,
            networkZone,
            sessionType,
            sessionName,
            notifyQos,
            notifyQosProps,
            notifyPriority,
            subscriptionId,
            updateHeaderList,
            updateList);
  }

  @Override
  public MALMessage sendNotify(final MALOperation op,
          final URI subscriber,
          final Long transactionId,
          final IdentifierList domainId,
          final Identifier networkZone,
          final SessionType sessionType,
          final Identifier sessionName,
          final QoSLevel notifyQos,
          final Map notifyQosProps,
          final UInteger notifyPriority,
          final Identifier subscriptionId,
          final UpdateHeaderList updateHeaderList,
          final List... updateList) throws IllegalArgumentException, MALInteractionException, MALException
  {
    return transportDelegate.sendNotify(op,
            subscriber,
            transactionId,
            domainId,
            networkZone,
            sessionType,
            sessionName,
            notifyQos,
            notifyQosProps,
            notifyPriority,
            subscriptionId,
            updateHeaderList,
            updateList);
  }

  @Override
  public MALMessage sendNotifyError(final UShort area,
          final UShort service,
          final UShort operation,
          final UOctet version,
          final URI subscriber,
          final Long transactionId,
          final IdentifierList domainId,
          final Identifier networkZone,
          final SessionType sessionType,
          final Identifier sessionName,
          final QoSLevel notifyQos,
          final Map notifyQosProps,
          final UInteger notifyPriority,
          final MALStandardError error) throws IllegalArgumentException, MALInteractionException, MALException
  {
    return transportDelegate.sendNotifyError(area,
            service,
            operation,
            version,
            subscriber,
            transactionId,
            domainId,
            networkZone,
            sessionType,
            sessionName,
            notifyQos,
            notifyQosProps,
            notifyPriority,
            error);
  }

  @Override
  public MALMessage sendNotifyError(final MALOperation op,
          final URI subscriber,
          final Long transactionId,
          final IdentifierList domainId,
          final Identifier networkZone,
          final SessionType sessionType,
          final Identifier sessionName,
          final QoSLevel notifyQos,
          final Map notifyQosProps,
          final UInteger notifyPriority,
          final MALStandardError error) throws IllegalArgumentException, MALInteractionException, MALException
  {
    return transportDelegate.sendNotifyError(op,
            subscriber,
            transactionId,
            domainId,
            networkZone,
            sessionType,
            sessionName,
            notifyQos,
            notifyQosProps,
            notifyPriority,
            error);
  }

  @Override
  public MALMessage sendPublishError(final UShort area,
          final UShort service,
          final UShort operation,
          final UOctet version,
          final URI publisher,
          final Long transactionId,
          final IdentifierList domainId,
          final Identifier networkZone,
          final SessionType sessionType,
          final Identifier sessionName,
          final QoSLevel qos,
          final Map qosProps,
          final UInteger priority,
          final MALStandardError error) throws IllegalArgumentException, MALInteractionException, MALException
  {
    return transportDelegate.sendPublishError(area,
            service,
            operation,
            version,
            publisher,
            transactionId,
            domainId,
            networkZone,
            sessionType,
            sessionName,
            qos,
            qosProps,
            priority,
            error);
  }

  @Override
  public MALMessage sendPublishError(final MALOperation op,
          final URI publisher,
          final Long transactionId,
          final IdentifierList domainId,
          final Identifier networkZone,
          final SessionType sessionType,
          final Identifier sessionName,
          final QoSLevel qos,
          final Map qosProps,
          final UInteger priority,
          final MALStandardError error) throws IllegalArgumentException, MALInteractionException, MALException
  {
    return transportDelegate.sendPublishError(op,
            publisher,
            transactionId,
            domainId,
            networkZone,
            sessionType,
            sessionName,
            qos,
            qosProps,
            priority,
            error);
  }

  @Override
  public void setTransmitErrorListener(final MALTransmitErrorListener listener) throws MALException
  {
    transportDelegate.setTransmitErrorListener(listener);
  }

  @Override
  public MALTransmitErrorListener getTransmitErrorListener() throws MALException
  {
    return transportDelegate.getTransmitErrorListener();
  }
}
