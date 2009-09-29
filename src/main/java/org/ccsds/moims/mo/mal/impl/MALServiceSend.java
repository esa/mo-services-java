package org.ccsds.moims.mo.mal.impl;

import java.util.Vector;
import org.ccsds.moims.mo.mal.impl.broker.MALBroker;
import org.ccsds.moims.mo.mal.MALInvokeOperation;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALProgressOperation;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MALSendOperation;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.consumer.MALInteractionListener;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.MessageHeader;
import org.ccsds.moims.mo.mal.structures.Pair;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UpdateList;
import org.ccsds.moims.mo.mal.transport.MALEndPoint;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.impl.broker.MALBrokerMessage;
import org.ccsds.moims.mo.mal.impl.profile.MALProfiler;
import org.ccsds.moims.mo.mal.impl.transport.MALTransportSingleton;
import org.ccsds.moims.mo.mal.structures.StandardError;
import org.ccsds.moims.mo.mal.structures.Union;

/**
 * @version 1.0
 * @created 17-Aug-2006 10:24:12
 */
public class MALServiceSend
{
  private final MALServiceMaps maps;
  private final MALServiceReceive receiveHandler;
  private final MALBroker brokerHandler;

  public MALServiceSend(MALServiceMaps maps, MALServiceReceive receiveHandler, MALBroker brokerHandler)
  {
    this.maps = maps;
    this.receiveHandler = receiveHandler;
    this.brokerHandler = brokerHandler;
  }

  public void send(MALMessageDetails details, MALSendOperation op, Element requestBody)
  {
    try
    {
      MALMessage msg = details.endpoint.createMessage(createHeader(details, op, null, (byte) 0), requestBody, details.qosProps);

      details.endpoint.sendMessage(msg);
    }
    catch (MALException ex)
    {
      System.out.println("Error with consumer : " + details.uriTo);
    }
  }

  public void submit(MALMessageDetails details, MALSubmitOperation op, Element requestBody) throws MALException
  {
    Identifier transId = maps.getTransactionId();

    MALMessage msg = details.endpoint.createMessage(createHeader(details, op, transId, MALSubmitOperation.SUBMIT_STAGE), requestBody, details.qosProps);

    try
    {
      details.endpoint.setMessageListener(receiveHandler);
      details.endpoint.sendMessage(msg);

      MALMessage rtn = maps.waitForResponse(transId);
      MALProfiler.instance.rcvMarkServiceMessageReception(rtn);

      handlePossibleReturnError(rtn);
    }
    catch (MALException ex)
    {
      System.out.println("Error with consumer : " + msg.getHeader().getURIto());
      throw ex;
    }
  }

  public Element request(MALMessageDetails details, MALRequestOperation op, Element requestBody) throws MALException
  {
    Identifier transId = maps.getTransactionId();

    MALMessage msg = details.endpoint.createMessage(createHeader(details, op, transId, MALRequestOperation.REQUEST_STAGE), requestBody, details.qosProps);

    try
    {
      details.endpoint.setMessageListener(receiveHandler);
      details.endpoint.sendMessage(msg);

      MALMessage rtn = maps.waitForResponse(transId);
      MALProfiler.instance.rcvMarkServiceMessageReception(rtn);

      handlePossibleReturnError(rtn);

      return rtn.getBody();
    }
    catch (MALException ex)
    {
      System.out.println("Error with consumer : " + msg.getHeader().getURIto());
      throw ex;
    }
  }

  public Element invoke(MALMessageDetails details, MALInvokeOperation op, Element requestBody, MALInteractionListener listener) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  /*
  MALInteger transId = maps.getTransactionId();
  MALString urlFrom = ProtocolFactory.register(urlTo, new MALString("CS"), CommonService.getReceiveInterface());

  ProtocolMessage msg = ProtocolFactory.instance(urlTo).createProtocolMessage(urlTo, urlFrom, transId, MessageType.INVOKE, serviceId, methodId, urlNotify, arg);

  try
  {
  ProtocolFactory.instance(urlTo).sendMessage(msg);

  ProtocolMessage rtn = maps.waitForResponse(transId);

  handlePossibleReturnError(rtn);
  }
  catch (ProtocolException ex)
  {
  System.out.println("Error with consumer : "  + msg.getToURL());
  throw new org.ccsds.moims.common.api.lang.MALSystemException();
  }
   */
  }

  public Element progress(MALMessageDetails details, MALProgressOperation op, Element requestBody, MALInteractionListener listener) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void register(MALMessageDetails details, MALPubSubOperation op, Subscription subscription, MALInteractionListener list) throws MALException
  {
    Identifier transId = maps.getTransactionId();

    MALMessage msg = details.endpoint.createMessage(createHeader(details, op, transId, MALPubSubOperation.REGISTER_STAGE), subscription, details.qosProps);

    try
    {
      maps.registerNotifyListener(details, op, subscription, list);

      details.endpoint.setMessageListener(receiveHandler);
      details.endpoint.sendMessage(msg);

      MALMessage rtn = maps.waitForResponse(transId);
      MALProfiler.instance.rcvMarkServiceMessageReception(rtn);

      handlePossibleReturnError(rtn);
    }
    catch (MALException ex)
    {
      System.out.println("Error with consumer : " + msg.getHeader().getURIto());
      throw ex;
    }
  }

  public void publish(MALMessageDetails details, MALPubSubOperation op, UpdateList updateList) throws MALException
  {
    MALMessage msg = details.endpoint.createMessage(createHeader(details, op, null, MALPubSubOperation.PUBLISH_STAGE), updateList, details.qosProps);

    try
    {
      details.endpoint.sendMessage(msg);
    }
    catch (MALException ex)
    {
      System.out.println("Error with publish : " + msg.getHeader().getURIto());
      throw ex;
    }
  }

  public void deregister(MALMessageDetails details, MALPubSubOperation op, IdentifierList unsubscription) throws MALException
  {
    Identifier transId = maps.getTransactionId();

    MALMessage msg = details.endpoint.createMessage(createHeader(details, op, transId, MALPubSubOperation.DEREGISTER_STAGE), unsubscription, details.qosProps);

    try
    {
      details.endpoint.setMessageListener(receiveHandler);
      details.endpoint.sendMessage(msg);

      MALMessage rtn = maps.waitForResponse(transId);
      MALProfiler.instance.rcvMarkServiceMessageReception(rtn);

      handlePossibleReturnError(rtn);

      maps.deregisterNotifyListener(details, op, unsubscription);
    }
    catch (MALException ex)
    {
      System.out.println("Error with consumer : " + msg.getHeader().getURIto());
      throw ex;
    }
  }

  public void submitAsync(MALMessageDetails details, MALSubmitOperation op, Element requestBody, MALInteractionListener listener)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void requestAsync(MALMessageDetails details, MALSubmitOperation op, Element requestBody, MALInteractionListener listener)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void invokeAsync(MALMessageDetails details, MALSubmitOperation op, Element requestBody, MALInteractionListener listener)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void registerAsync(MALMessageDetails details, MALSubmitOperation op, Element requestBody, MALInteractionListener listener)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void deregisterAsync(MALMessageDetails details, MALSubmitOperation op, Element requestBody, MALInteractionListener listener)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void returnResponse(Identifier internalTransId, MALMessage sourceMessage, Element rspn)
  {
    Pair details = maps.resolveTransactionSource(internalTransId);
    URI uriTo = (URI) details.getFirst();
    Identifier transId = (Identifier) details.getSecond();

    try
    {
      MALEndPoint endpoint = MALTransportSingleton.instance(uriTo, null).createEndPoint(null, null, null);
      MALMessage msg = endpoint.createMessage(createReturnHeader(sourceMessage, false), rspn, null);

      endpoint.sendMessage(msg);
    }
    catch (MALException ex)
    {
      System.out.println("Error returning response to consumer : " + sourceMessage.getHeader().getURIfrom());
    }
  }

  public void returnError(Identifier internalTransId, MALMessage sourceMessage, StandardError error)
  {
    Pair details = maps.resolveTransactionSource(internalTransId);
    URI uriTo = (URI) details.getFirst();
    Identifier transId = (Identifier) details.getSecond();

    try
    {
      MALEndPoint endpoint = MALTransportSingleton.instance(uriTo, null).createEndPoint(null, null, null);
      MALMessage msg = endpoint.createMessage(createReturnHeader(sourceMessage, true), error, null);

      endpoint.sendMessage(msg);
    }
    catch (MALException ex)
    {
      System.out.println("Error returning exception to consumer : " + sourceMessage.getHeader().getURIfrom());
    }
  }

  public void returnNotify(MALMessageDetails details, MALPubSubOperation operation, UpdateList updateList) throws MALException
  {
    MessageHeader hdr = createHeader(details, operation, null, MALPubSubOperation.PUBLISH_STAGE);

    if (MALTransportSingleton.instance(details.endpoint.getURI(), null).isSupportedInteractionType(InteractionType.PUBSUB))
    {
      MALMessage msg = details.endpoint.createMessage(hdr, updateList, details.qosProps);

      MALProfiler.instance.sendMALTransferObject(details, msg);
      details.endpoint.sendMessage(msg);
    }
    else
    {
      MALProfiler.instance.sendMALTransferObject(details, hdr);
      returnNotify(details.endpoint, hdr, updateList);
    }
  }

  public void returnNotify(MALEndPoint endpoint, MessageHeader hdr, UpdateList updateList)
  {
    MALProfiler.instance.sendMarkMALBrokerStarting(hdr);
    java.util.List<MALBrokerMessage> msgList = brokerHandler.createNotify(hdr, updateList);
    MALProfiler.instance.sendMarkMALBrokerFinished(hdr);

    if (!msgList.isEmpty())
    {
      java.util.List<MALMessage> transMsgs = new Vector<MALMessage>(msgList.size());
      for (MALBrokerMessage brokerMessage : msgList)
      {
        try
        {
          if (null == endpoint)
          {
            endpoint = MALTransportSingleton.instance(brokerMessage.header.getURIto(), null).createEndPoint(null, null, null);
          }

          MALMessage msg = endpoint.createMessage(brokerMessage.header, brokerMessage.updates, null);

          transMsgs.add(msg);

          MALProfiler.instance.sendMALAddObject(hdr, msg);
        }
        catch (MALException ex)
        {
          // with the exception being thrown we assume that there is a problem with this consumer so remove
          //  them from the observe manager
          System.out.println("Error with notify consumer, removing from list : " + brokerMessage.header.getURIto());
          brokerHandler.report();
          brokerHandler.removeLostConsumer(brokerMessage.header);
          brokerHandler.report();

        // TODO: notify local provider
        }
      }

      // send it out
      try
      {
        endpoint.sendMessages((MALMessage[])transMsgs.toArray());
      }
      catch (MALException ex)
      {
        // TODO: notify local provider
      }
    }

    MALProfiler.instance.sendMALRemoveObject(hdr);
  }

  void handlePossibleReturnError(MALMessage rtn) throws MALException
  {
    if ((null != rtn) && (rtn.getHeader().getIsError()))
    {
      if (rtn.getBody() instanceof StandardError)
      {
        throw new MALException((StandardError)rtn.getBody());
      }

      throw new MALException(new StandardError(MALHelper.BAD_ENCODING_ERROR_NUMBER, new Union("Return message marked as error but did not contain a MALException")));
    }
  }

  MessageHeader createHeader(MALMessageDetails details, MALOperation op, Identifier transactionId, Byte interactionStage)
  {
    MessageHeader hdr = new MessageHeader();

    hdr.setURIfrom(details.endpoint.getURI());

    if (op.getInteractionType() == InteractionType.PUBSUB)
    {
      hdr.setURIto(details.brokerUri);
    }
    else
    {
      hdr.setURIto(details.uriTo);
    }
    hdr.setAuthenticationId(details.authenticationId);
    hdr.setTimestamp(new Time(new java.util.Date().getTime()));
    hdr.setQoSlevel(details.qosLevel);
    hdr.setPriority(details.priority);
    hdr.setDomain(details.domain);
    hdr.setNetworkZone(details.networkZone);
    hdr.setSession(details.sessionType);
    hdr.setSessionName(details.sessionName);
    hdr.setInteractionType(op.getInteractionType());
    hdr.setInteractionStage(interactionStage);
    hdr.setTransactionId(transactionId);
    hdr.setArea(op.getService().getArea().getName());
    hdr.setService(op.getService().getName());
    hdr.setOperation(op.getName());
    hdr.setVersion(op.getService().getVersion());
    hdr.setIsError(new Boolean(false));

    return hdr;
  }

  MessageHeader createReturnHeader(MALMessage sourceMessage, boolean isError)
  {
    MessageHeader hdr = new MessageHeader();
    MessageHeader srcHdr = sourceMessage.getHeader();

    hdr.setURIfrom(srcHdr.getURIto());
    hdr.setURIto(srcHdr.getURIfrom());
    hdr.setAuthenticationId(srcHdr.getAuthenticationId());
    hdr.setTimestamp(new Time(new java.util.Date().getTime()));
    hdr.setQoSlevel(srcHdr.getQoSlevel());
    hdr.setPriority(srcHdr.getPriority());
    hdr.setDomain(srcHdr.getDomain());
    hdr.setNetworkZone(srcHdr.getNetworkZone());
    hdr.setSession(srcHdr.getSession());
    hdr.setSessionName(srcHdr.getSessionName());
    hdr.setInteractionType(srcHdr.getInteractionType());
    hdr.setInteractionStage(new Byte((byte)(srcHdr.getInteractionStage().byteValue() + 1)));
    hdr.setTransactionId(srcHdr.getTransactionId());
    hdr.setArea(srcHdr.getArea());
    hdr.setService(srcHdr.getService());
    hdr.setOperation(srcHdr.getOperation());
    hdr.setVersion(srcHdr.getVersion());
    hdr.setIsError(new Boolean(isError));

    return hdr;
  }
}