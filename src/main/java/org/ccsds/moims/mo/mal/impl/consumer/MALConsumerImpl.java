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
package org.ccsds.moims.mo.mal.impl.consumer;

import java.util.Map;
import org.ccsds.moims.mo.mal.*;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.consumer.MALInteractionListener;
import org.ccsds.moims.mo.mal.impl.MALContextImpl;
import org.ccsds.moims.mo.mal.impl.MessageDetails;
import org.ccsds.moims.mo.mal.impl.MessageSend;
import org.ccsds.moims.mo.mal.impl.transport.TransportSingleton;
import org.ccsds.moims.mo.mal.impl.util.MALClose;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALTransport;

/**
 *
 */
class MALConsumerImpl extends MALClose implements MALConsumer
{
  private final MessageSend sender;
  private final MessageDetails details;

  MALConsumerImpl(MALContextImpl impl,
          MALConsumerManagerImpl parent,
          String localName,
          URI uriTo,
          URI brokerUri,
          MALService service,
          Blob authenticationId,
          IdentifierList domain,
          Identifier networkZone,
          SessionType sessionType,
          Identifier sessionName,
          QoSLevel qosLevel,
          Map qosProps,
          UInteger priority) throws MALException
  {
    super(parent);
    this.sender = impl.getSendingInterface();
    MALTransport trans = TransportSingleton.instance(uriTo, impl.getInitialProperties());
    MALEndpoint ep = trans.createEndpoint(localName, qosProps);
    ep.setMessageListener(impl.getReceivingInterface());
    this.details = new MessageDetails(ep,
            ep.getURI(),
            uriTo,
            brokerUri,
            service,
            authenticationId,
            domain,
            networkZone,
            sessionType,
            sessionName,
            qosLevel,
            qosProps,
            priority);

    ep.startMessageDelivery();
  }

  MALConsumerImpl(MALContextImpl impl,
          MALConsumerManagerImpl parent,
          MALEndpoint endPoint,
          URI uriTo,
          URI brokerUri,
          MALService service,
          Blob authenticationId,
          IdentifierList domain,
          Identifier networkZone,
          SessionType sessionType,
          Identifier sessionName,
          QoSLevel qosLevel,
          Map qosProps,
          UInteger priority) throws MALException
  {
    super(parent);
    this.sender = impl.getSendingInterface();
    endPoint.setMessageListener(impl.getReceivingInterface());
    this.details = new MessageDetails(endPoint,
            endPoint.getURI(),
            uriTo,
            brokerUri,
            service,
            authenticationId,
            domain,
            networkZone,
            sessionType,
            sessionName,
            qosLevel,
            qosProps,
            priority);
  }

  @Override
  public URI getURI()
  {
    return details.endpoint.getURI();
  }

  @Override
  public org.ccsds.moims.mo.mal.transport.MALMessage send(MALSendOperation op, Object... requestBody)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.onewayInteraction(details, null, op, new UOctet((short) 0), requestBody);
  }

  @Override
  public void submit(MALSubmitOperation op, Object... requestBody)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException
  {
    sender.synchronousInteraction(details,
            op,
            MALSubmitOperation.SUBMIT_STAGE,
            (MALInteractionListener) null,
            requestBody);
  }

  @Override
  public MALMessageBody request(MALRequestOperation op, Object... requestBody)
          throws IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.synchronousInteraction(details,
            op,
            MALRequestOperation.REQUEST_STAGE,
            (MALInteractionListener) null,
            requestBody);
  }

  @Override
  public MALMessageBody invoke(MALInvokeOperation op,
          MALInteractionListener listener,
          Object... requestBody) throws IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.synchronousInteraction(details, op, MALInvokeOperation.INVOKE_STAGE, listener, requestBody);
  }

  @Override
  public MALMessageBody progress(MALProgressOperation op,
          MALInteractionListener listener,
          Object... requestBody) throws IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.synchronousInteraction(details, op, MALProgressOperation.PROGRESS_STAGE, listener, requestBody);
  }

  @Override
  public void register(MALPubSubOperation op,
          Subscription subscription,
          MALInteractionListener listener) throws java.lang.IllegalArgumentException, MALInteractionException, MALException
  {
    sender.register(details, op, subscription, listener);
  }

  @Override
  public void deregister(MALPubSubOperation op, IdentifierList unsubscription)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException
  {
    sender.deregister(details, op, unsubscription);
  }

  @Override
  public MALMessage asyncSubmit(MALSubmitOperation op,
          MALInteractionListener listener,
          Object... requestBody) throws IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.asynchronousInteraction(details, op, MALSubmitOperation.SUBMIT_STAGE, listener, requestBody);
  }

  @Override
  public MALMessage asyncRequest(MALRequestOperation op,
          MALInteractionListener listener,
          Object... requestBody) throws IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.asynchronousInteraction(details, op, MALRequestOperation.REQUEST_STAGE, listener, requestBody);
  }

  @Override
  public MALMessage asyncInvoke(MALInvokeOperation op,
          MALInteractionListener listener,
          Object... requestBody) throws IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.asynchronousInteraction(details, op, MALInvokeOperation.INVOKE_STAGE, listener, requestBody);
  }

  @Override
  public MALMessage asyncProgress(MALProgressOperation op,
          MALInteractionListener listener,
          Object... requestBody) throws IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.asynchronousInteraction(details, op, MALProgressOperation.PROGRESS_STAGE, listener, requestBody);
  }

  @Override
  public org.ccsds.moims.mo.mal.transport.MALMessage asyncRegister(MALPubSubOperation op,
          Subscription subscription,
          MALInteractionListener listener) throws java.lang.IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.registerAsync(details, op, subscription, listener);
  }

  @Override
  public org.ccsds.moims.mo.mal.transport.MALMessage asyncDeregister(MALPubSubOperation op,
          IdentifierList unsubscription,
          MALInteractionListener listener) throws java.lang.IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.deregisterAsync(details, op, unsubscription, listener);
  }

  public void continueInteraction(MALOperation op,
          UOctet lastInteractionStage,
          Time initiationTimestamp,
          Long transactionId,
          MALInteractionListener listener) throws IllegalArgumentException, MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
