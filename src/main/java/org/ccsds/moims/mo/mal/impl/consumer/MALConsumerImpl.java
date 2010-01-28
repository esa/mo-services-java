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

import java.util.Hashtable;
import org.ccsds.moims.mo.mal.MALInvokeOperation;
import org.ccsds.moims.mo.mal.MALProgressOperation;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MALSendOperation;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.consumer.MALInteractionListener;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.DomainIdentifier;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.impl.Address;
import org.ccsds.moims.mo.mal.impl.DummyHandler;
import org.ccsds.moims.mo.mal.impl.EndPointAdapter;
import org.ccsds.moims.mo.mal.impl.MALImpl;
import org.ccsds.moims.mo.mal.impl.MessageDetails;
import org.ccsds.moims.mo.mal.impl.MessageSend;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.impl.transport.TransportSingleton;
import org.ccsds.moims.mo.mal.impl.util.MALClose;
import org.ccsds.moims.mo.mal.transport.MALEndPoint;
import org.ccsds.moims.mo.mal.transport.MALTransport;

/**
 *
 */
class MALConsumerImpl extends MALClose implements MALConsumer
{
  private final MessageSend sender;
  private final MessageDetails details;
  private final Address address;

  MALConsumerImpl(MALImpl impl,
          MALConsumerManagerImpl parent,
          URI uriTo,
          URI brokerUri,
          MALService service,
          Blob authenticationId,
          DomainIdentifier domain,
          Identifier networkZone,
          SessionType sessionType,
          Identifier sessionName,
          QoSLevel qosLevel,
          Hashtable qosProps,
          Integer priority) throws MALException
  {
    super(parent);
    this.sender = impl.getSendingInterface();
    MALTransport trans = TransportSingleton.instance(uriTo, impl.getInitialProperties());
    MALEndPoint ep = trans.createEndPoint(null, service, qosProps);
    address = new Address(ep, ep.getURI(), authenticationId, new DummyHandler());
    ep.setMessageListener(new EndPointAdapter(impl.getReceivingInterface(), address));
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
  }

  @Override
  public URI getURI()
  {
    return details.endpoint.getURI();
  }

  @Override
  public void send(MALSendOperation op, Element requestBody) throws MALException
  {
    sender.onewayInteraction(details, null, op, Byte.valueOf((byte) 0), requestBody);
  }

  @Override
  public void submit(MALSubmitOperation op, Element requestBody) throws MALException
  {
    sender.synchronousInteraction(details,
            op,
            MALSubmitOperation.SUBMIT_STAGE,
            (MALInteractionListener) null,
            requestBody);
  }

  @Override
  public Element request(MALRequestOperation op, Element requestBody) throws MALException
  {
    return sender.synchronousInteraction(details,
            op,
            MALRequestOperation.REQUEST_STAGE,
            (MALInteractionListener) null,
            requestBody);
  }

  @Override
  public Element invoke(MALInvokeOperation op,
          Element requestBody,
          MALInteractionListener listener) throws MALException
  {
    return sender.synchronousInteraction(details, op, MALInvokeOperation.INVOKE_STAGE, listener, requestBody);
  }

  @Override
  public Element progress(MALProgressOperation op,
          Element requestBody,
          MALInteractionListener listener) throws MALException
  {
    return sender.synchronousInteraction(details, op, MALProgressOperation._PROGRESS_STAGE, listener, requestBody);
  }

  @Override
  public void register(MALPubSubOperation op,
          Subscription subscription,
          MALInteractionListener listener) throws MALException
  {
    sender.register(details, op, subscription, listener);
  }

  @Override
  public void deregister(MALPubSubOperation op, IdentifierList unsubscription) throws MALException
  {
    sender.deregister(details, op, unsubscription);
  }

  @Override
  public void asyncSubmit(MALSubmitOperation op,
          Element requestBody,
          MALInteractionListener listener) throws MALException
  {
    sender.asynchronousInteraction(details, op, MALSubmitOperation.SUBMIT_STAGE, listener, requestBody);
  }

  @Override
  public void asyncRequest(MALRequestOperation op,
          Element requestBody,
          MALInteractionListener listener) throws MALException
  {
    sender.asynchronousInteraction(details, op, MALRequestOperation.REQUEST_STAGE, listener, requestBody);
  }

  @Override
  public void asyncInvoke(MALInvokeOperation op,
          Element requestBody,
          MALInteractionListener listener) throws MALException
  {
    sender.asynchronousInteraction(details, op, MALInvokeOperation.INVOKE_STAGE, listener, requestBody);
  }

  @Override
  public void asyncProgress(MALProgressOperation op,
          Element requestBody,
          MALInteractionListener listener) throws MALException
  {
    sender.asynchronousInteraction(details, op, MALProgressOperation.PROGRESS_STAGE, listener, requestBody);
  }

  @Override
  public void asyncRegister(MALPubSubOperation op,
          Subscription subscription,
          MALInteractionListener listener) throws MALException
  {
    sender.registerAsync(details, op, subscription, listener);
  }

  @Override
  public void asyncDeregister(MALPubSubOperation op,
          IdentifierList unsubscription,
          MALInteractionListener listener) throws MALException
  {
    sender.deregisterAsync(details, op, unsubscription, listener);
  }
}
