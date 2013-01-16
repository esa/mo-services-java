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
import org.ccsds.moims.mo.mal.transport.MALEncodedBody;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALTransmitErrorListener;

/**
 * Implementation of the MALConsumer interface. Delegates most of the work to the MessageSend class.
 */
class MALConsumerImpl extends MALClose implements MALConsumer
{
  private final MessageSend sender;
  private final MessageDetails details;
  private MALTransmitErrorListener transmissionListener;

  MALConsumerImpl(final MALContextImpl impl,
          final MALConsumerManagerImpl parent,
          final String localName,
          final URI uriTo,
          final URI brokerUri,
          final MALService service,
          final Blob authenticationId,
          final IdentifierList domain,
          final Identifier networkZone,
          final SessionType sessionType,
          final Identifier sessionName,
          final QoSLevel qosLevel,
          final Map qosProps,
          final UInteger priority) throws MALException
  {
    super(parent);
    this.sender = impl.getSendingInterface();

    final MALEndpoint ep = TransportSingleton.instance(uriTo, impl.getInitialProperties())
            .createEndpoint(localName, qosProps);
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

  MALConsumerImpl(final MALContextImpl impl,
          final MALConsumerManagerImpl parent,
          final MALEndpoint endPoint,
          final URI uriTo,
          final URI brokerUri,
          final MALService service,
          final Blob authenticationId,
          final IdentifierList domain,
          final Identifier networkZone,
          final SessionType sessionType,
          final Identifier sessionName,
          final QoSLevel qosLevel,
          final Map qosProps,
          final UInteger priority) throws MALException
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
  public MALMessage send(final MALSendOperation op, final Object... requestBody)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.onewayInteraction(details, null, op, new UOctet((short) 0), requestBody);
  }

  @Override
  public MALMessage send(final MALSendOperation op, final MALEncodedBody body)
          throws IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.onewayInteraction(details, null, op, new UOctet((short) 0), body);
  }

  @Override
  public void submit(final MALSubmitOperation op, final Object... requestBody)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException
  {
    sender.synchronousInteraction(details,
            op,
            MALSubmitOperation.SUBMIT_STAGE,
            (MALInteractionListener) null,
            requestBody);
  }

  @Override
  public void submit(final MALSubmitOperation op, final MALEncodedBody body)
          throws IllegalArgumentException, MALInteractionException, MALException
  {
    sender.synchronousInteraction(details,
            op,
            MALSubmitOperation.SUBMIT_STAGE,
            (MALInteractionListener) null,
            body);
  }

  @Override
  public MALMessageBody request(final MALRequestOperation op, final Object... requestBody)
          throws IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.synchronousInteraction(details,
            op,
            MALRequestOperation.REQUEST_STAGE,
            (MALInteractionListener) null,
            requestBody);
  }

  @Override
  public MALMessageBody request(final MALRequestOperation op, final MALEncodedBody body)
          throws IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.synchronousInteraction(details,
            op,
            MALRequestOperation.REQUEST_STAGE,
            (MALInteractionListener) null,
            body);
  }

  @Override
  public MALMessageBody invoke(final MALInvokeOperation op,
          final MALInteractionListener listener,
          final Object... requestBody)
          throws IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.synchronousInteraction(details, op, MALInvokeOperation.INVOKE_STAGE, listener, requestBody);
  }

  @Override
  public MALMessageBody invoke(final MALInvokeOperation op,
          final MALInteractionListener listener,
          final MALEncodedBody body)
          throws IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.synchronousInteraction(details, op, MALInvokeOperation.INVOKE_STAGE, listener, body);
  }

  @Override
  public MALMessageBody progress(final MALProgressOperation op,
          final MALInteractionListener listener,
          final Object... requestBody)
          throws IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.synchronousInteraction(details, op, MALProgressOperation.PROGRESS_STAGE, listener, requestBody);
  }

  @Override
  public MALMessageBody progress(final MALProgressOperation op,
          final MALInteractionListener listener,
          final MALEncodedBody body)
          throws IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.synchronousInteraction(details, op, MALProgressOperation.PROGRESS_STAGE, listener, body);
  }

  @Override
  public void register(final MALPubSubOperation op,
          final Subscription subscription,
          final MALInteractionListener listener)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException
  {
    sender.register(details, op, subscription, listener);
  }

  @Override
  public void deregister(final MALPubSubOperation op,
          final IdentifierList unsubscription)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException
  {
    sender.deregister(details, op, unsubscription);
  }

  @Override
  public MALMessage asyncSubmit(final MALSubmitOperation op,
          final MALInteractionListener listener,
          final Object... requestBody)
          throws IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.asynchronousInteraction(details, op, MALSubmitOperation.SUBMIT_STAGE, listener, requestBody);
  }

  @Override
  public MALMessage asyncSubmit(final MALSubmitOperation op,
          final MALInteractionListener listener,
          final MALEncodedBody body)
          throws IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.asynchronousInteraction(details, op, MALSubmitOperation.SUBMIT_STAGE, listener, body);
  }

  @Override
  public MALMessage asyncRequest(final MALRequestOperation op,
          final MALInteractionListener listener,
          final Object... requestBody)
          throws IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.asynchronousInteraction(details, op, MALRequestOperation.REQUEST_STAGE, listener, requestBody);
  }

  @Override
  public MALMessage asyncRequest(final MALRequestOperation op,
          final MALInteractionListener listener,
          final MALEncodedBody body)
          throws IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.asynchronousInteraction(details, op, MALRequestOperation.REQUEST_STAGE, listener, body);
  }

  @Override
  public MALMessage asyncInvoke(final MALInvokeOperation op,
          final MALInteractionListener listener,
          final Object... requestBody)
          throws IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.asynchronousInteraction(details, op, MALInvokeOperation.INVOKE_STAGE, listener, requestBody);
  }

  @Override
  public MALMessage asyncInvoke(final MALInvokeOperation op,
          final MALInteractionListener listener,
          final MALEncodedBody body)
          throws IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.asynchronousInteraction(details, op, MALInvokeOperation.INVOKE_STAGE, listener, body);
  }

  @Override
  public MALMessage asyncProgress(final MALProgressOperation op,
          final MALInteractionListener listener,
          final Object... requestBody)
          throws IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.asynchronousInteraction(details, op, MALProgressOperation.PROGRESS_STAGE, listener, requestBody);
  }

  @Override
  public MALMessage asyncProgress(final MALProgressOperation op,
          final MALInteractionListener listener,
          final MALEncodedBody body)
          throws IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.asynchronousInteraction(details, op, MALProgressOperation.PROGRESS_STAGE, listener, body);
  }

  @Override
  public MALMessage asyncRegister(final MALPubSubOperation op,
          final Subscription subscription,
          final MALInteractionListener listener)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.registerAsync(details, op, subscription, listener);
  }

  @Override
  public MALMessage asyncDeregister(final MALPubSubOperation op,
          final IdentifierList unsubscription,
          final MALInteractionListener listener)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException
  {
    return sender.deregisterAsync(details, op, unsubscription, listener);
  }

  @Override
  public void continueInteraction(final MALOperation op,
          final UOctet lastInteractionStage,
          final Time initiationTimestamp,
          final Long transactionId,
          final MALInteractionListener listener)
          throws IllegalArgumentException, MALInteractionException, MALException
  {
    sender.continueInteraction(op, lastInteractionStage, initiationTimestamp, transactionId, listener);
  }
  
  @Override
  public void setTransmitErrorListener(final MALTransmitErrorListener plistener) throws MALException
  {
    transmissionListener = plistener;
  }

  @Override
  public MALTransmitErrorListener getTransmitErrorListener() throws MALException
  {
    return transmissionListener;
  }
}
