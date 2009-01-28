package org.ccsds.moims.smc.mal.impl;

import org.ccsds.moims.smc.mal.api.MALInvokeOperation;
import org.ccsds.moims.smc.mal.api.MALOperation;
import org.ccsds.moims.smc.mal.api.MALProgressOperation;
import org.ccsds.moims.smc.mal.api.MALPubSubOperation;
import org.ccsds.moims.smc.mal.api.MALRequestOperation;
import org.ccsds.moims.smc.mal.api.MALSendOperation;
import org.ccsds.moims.smc.mal.api.MALSubmitOperation;
import org.ccsds.moims.smc.mal.api.consumer.MALInteractionListener;
import org.ccsds.moims.smc.mal.api.structures.MALBoolean;
import org.ccsds.moims.smc.mal.api.structures.MALElement;
import org.ccsds.moims.smc.mal.api.structures.MALException;
import org.ccsds.moims.smc.mal.api.structures.MALIdentifier;
import org.ccsds.moims.smc.mal.api.structures.MALIdentifierList;
import org.ccsds.moims.smc.mal.api.structures.MALMessageHeader;
import org.ccsds.moims.smc.mal.api.structures.MALOctet;
import org.ccsds.moims.smc.mal.api.structures.MALPair;
import org.ccsds.moims.smc.mal.api.structures.MALString;
import org.ccsds.moims.smc.mal.api.structures.MALSubscription;
import org.ccsds.moims.smc.mal.api.structures.MALSubscriptionUpdateList;
import org.ccsds.moims.smc.mal.api.structures.MALTime;
import org.ccsds.moims.smc.mal.api.structures.MALURI;
import org.ccsds.moims.smc.mal.api.structures.MALUpdateList;
import org.ccsds.moims.smc.mal.api.transport.MALEndPoint;
import org.ccsds.moims.smc.mal.api.transport.MALMessage;
import org.ccsds.moims.smc.mal.impl.profile.MALProfiler;
import org.ccsds.moims.smc.mal.impl.transport.MALTransportSingleton;

/**
 * @version 1.0
 * @created 17-Aug-2006 10:24:12
 */
public class MALServiceSend
{
  private final MALServiceMaps maps;
  private final MALServiceReceive receiveHandler;
  private final MALBrokerHandler brokerHandler;

  public MALServiceSend(MALServiceMaps maps, MALServiceReceive receiveHandler, MALBrokerHandler brokerHandler)
  {
    this.maps = maps;
    this.receiveHandler = receiveHandler;
    this.brokerHandler = brokerHandler;
  }

  public void send(MALMessageDetails details, MALSendOperation op, MALElement requestBody)
  {
    try
    {
      MALMessage msg = details.endpoint.createMessage(createHeader(details, op, null, new MALOctet((byte) 0)), requestBody);

      details.endpoint.sendMessage(msg, details.qosProps);
    }
    catch (MALException ex)
    {
      System.out.println("Error with consumer : " + details.uriTo);
    }
  }

  public void submit(MALMessageDetails details, MALSubmitOperation op, MALElement requestBody) throws MALException
  {
    MALIdentifier transId = maps.getTransactionId();

    MALMessage msg = details.endpoint.createMessage(createHeader(details, op, transId, MALSubmitOperation.SUBMIT_STAGE), requestBody);

    try
    {
      details.endpoint.setMessageListener(receiveHandler);
      details.endpoint.sendMessage(msg, details.qosProps);

      MALMessage rtn = maps.waitForResponse(transId);
      MALProfiler.instance.rcvMarkServiceMessageReception(rtn);

      handlePossibleReturnError(rtn);
    }
    catch (MALException ex)
    {
      System.out.println("Error with consumer : " + msg.getHeader().getUriTo());
      throw ex;
    }
  }

  public MALElement request(MALMessageDetails details, MALRequestOperation op, MALElement requestBody) throws MALException
  {
    MALIdentifier transId = maps.getTransactionId();

    MALMessage msg = details.endpoint.createMessage(createHeader(details, op, transId, MALRequestOperation.REQUEST_STAGE), requestBody);

    try
    {
      details.endpoint.setMessageListener(receiveHandler);
      details.endpoint.sendMessage(msg, details.qosProps);

      MALMessage rtn = maps.waitForResponse(transId);
      MALProfiler.instance.rcvMarkServiceMessageReception(rtn);

      handlePossibleReturnError(rtn);

      return rtn.getBody();
    }
    catch (MALException ex)
    {
      System.out.println("Error with consumer : " + msg.getHeader().getUriTo());
      throw ex;
    }
  }

  public MALElement invoke(MALMessageDetails details, MALInvokeOperation op, MALElement requestBody, MALInteractionListener listener) throws MALException
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

  public MALElement progress(MALMessageDetails details, MALProgressOperation op, MALElement requestBody, MALInteractionListener listener) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void register(MALMessageDetails details, MALPubSubOperation op, MALSubscription subscription, MALInteractionListener list) throws MALException
  {
    MALIdentifier transId = maps.getTransactionId();

    MALMessage msg = details.endpoint.createMessage(createHeader(details, op, transId, MALPubSubOperation.REGISTER_STAGE), subscription);

    try
    {
      maps.registerNotifyListener(details, op, subscription, list);

      details.endpoint.setMessageListener(receiveHandler);
      details.endpoint.sendMessage(msg, details.qosProps);

      MALMessage rtn = maps.waitForResponse(transId);
      MALProfiler.instance.rcvMarkServiceMessageReception(rtn);

      handlePossibleReturnError(rtn);
    }
    catch (MALException ex)
    {
      System.out.println("Error with consumer : " + msg.getHeader().getUriTo());
      throw ex;
    }
  }

  public void deregister(MALMessageDetails details, MALPubSubOperation op, MALIdentifierList unsubscription) throws MALException
  {
    MALIdentifier transId = maps.getTransactionId();

    MALMessage msg = details.endpoint.createMessage(createHeader(details, op, transId, MALPubSubOperation.DEREGISTER_STAGE), unsubscription);

    try
    {
      details.endpoint.setMessageListener(receiveHandler);
      details.endpoint.sendMessage(msg, details.qosProps);

      MALMessage rtn = maps.waitForResponse(transId);
      MALProfiler.instance.rcvMarkServiceMessageReception(rtn);

      handlePossibleReturnError(rtn);

      maps.deregisterNotifyListener(details, op, unsubscription);
    }
    catch (MALException ex)
    {
      System.out.println("Error with consumer : " + msg.getHeader().getUriTo());
      throw ex;
    }
  }

  public void submitAsync(MALMessageDetails details, MALSubmitOperation op, MALElement requestBody, MALInteractionListener listener)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void requestAsync(MALMessageDetails details, MALSubmitOperation op, MALElement requestBody, MALInteractionListener listener)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void invokeAsync(MALMessageDetails details, MALSubmitOperation op, MALElement requestBody, MALInteractionListener listener)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void registerAsync(MALMessageDetails details, MALSubmitOperation op, MALElement requestBody, MALInteractionListener listener)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void deregisterAsync(MALMessageDetails details, MALSubmitOperation op, MALElement requestBody, MALInteractionListener listener)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void returnResponse(MALIdentifier internalTransId, MALMessage sourceMessage, MALElement rspn)
  {
    MALPair details = maps.resolveTransactionSource(internalTransId);
    MALURI uriTo = (MALURI) details.getFirst();
    MALIdentifier transId = (MALIdentifier) details.getSecond();

    try
    {
      MALEndPoint endpoint = MALTransportSingleton.instance(uriTo, null).createEndPoint(null, null);
      MALMessage msg = endpoint.createMessage(createReturnHeader(sourceMessage, false), rspn);

      endpoint.sendMessage(msg, null);
    }
    catch (MALException ex)
    {
      System.out.println("Error returning response to consumer : " + sourceMessage.getHeader().getUriFrom());
    }
  }

  public void returnError(MALIdentifier internalTransId, MALMessage sourceMessage, MALException error)
  {
    MALPair details = maps.resolveTransactionSource(internalTransId);
    MALURI uriTo = (MALURI) details.getFirst();
    MALIdentifier transId = (MALIdentifier) details.getSecond();

    try
    {
      MALEndPoint endpoint = MALTransportSingleton.instance(uriTo, null).createEndPoint(null, null);
      MALMessage msg = endpoint.createMessage(createReturnHeader(sourceMessage, true), error);

      endpoint.sendMessage(msg, null);
    }
    catch (MALException ex)
    {
      System.out.println("Error returning exception to consumer : " + sourceMessage.getHeader().getUriFrom());
    }
  }

  public void returnNotify(MALMessageDetails details, MALPubSubOperation operation, MALUpdateList updateList) throws MALException
  {
    MALMessageHeader hdr = createHeader(details, operation, null, MALPubSubOperation.PUBLISH_STAGE);
    MALProfiler.instance.sendMALTransferObject(details, hdr);

    returnNotify(hdr, updateList);
  }

  public void returnNotify(MALMessageHeader hdr, MALUpdateList updateList) throws MALException
  {
    MALProfiler.instance.sendMarkMALBrokerStarting(hdr);
    java.util.List<MALBrokerHandler.BrokerMessage> msgList = brokerHandler.createNotify(hdr, updateList);
    MALProfiler.instance.sendMarkMALBrokerFinished(hdr);

    for (MALBrokerHandler.BrokerMessage brokerMessage : msgList)
    {
      // send it out
      MALProfiler.instance.sendMALTransferObject(hdr, brokerMessage.header);
      returnSingleNotify(brokerMessage.header, brokerMessage.updates);
      MALProfiler.instance.sendMALTransferObject(brokerMessage.header, hdr);
    }

    MALProfiler.instance.sendMALRemoveObject(hdr);
  }

  void returnSingleNotify(MALMessageHeader header, MALSubscriptionUpdateList updateList)
  {
    try
    {
      MALEndPoint endpoint = MALTransportSingleton.instance(header.getUriTo(), null).createEndPoint(null, null);
      MALMessage msg = endpoint.createMessage(header, updateList);

      MALProfiler.instance.sendMALAddObject(header, msg);
      endpoint.sendMessage(msg, null);
    }
    catch (MALException ex)
    {
      // with the exception being thrown we assume that there is a problem with this consumer so remove
      //  them from the observe manager
      System.out.println("Error with notify consumer, removing from list : " + header.getUriTo());
      brokerHandler.report();
      brokerHandler.removeLostConsumer(header);
      brokerHandler.report();

    // TODO: notify local provider
    }
  }

  void handlePossibleReturnError(MALMessage rtn) throws MALException
  {
    if ((null != rtn) && (rtn.getHeader().isError().getBooleanValue()))
    {
      if (rtn.getBody() instanceof MALException)
      {
        throw (MALException) rtn.getBody();
      }

      throw new MALException(MALException.BAD_ENCODING, new MALString("Return message marked as error but did not contain a MALException"));
    }
  }

  MALMessageHeader createHeader(MALMessageDetails details, MALOperation op, MALIdentifier transactionId, MALOctet interactionStage)
  {
    MALMessageHeader hdr = new MALMessageHeader();

    hdr.setUriFrom(details.endpoint.getURI());
    hdr.setUriTo(details.uriTo);
    hdr.setAuthenticationId(details.authenticationId);
    hdr.setTimeStamp(new MALTime(new java.util.Date().getTime()));
    hdr.setQoSLevel(details.qosLevel);
    hdr.setPriority(details.priority);
    hdr.setDomain(details.domain);
    hdr.setNetworkZone(details.networkZone);
    hdr.setSession(details.sessionType);
    hdr.setSessionName(details.sessionName);
    hdr.setInteractionType(op.getInteractionType());
    hdr.setInteractionStage(interactionStage);
    hdr.setTransactionId(transactionId);
    hdr.setArea(op.getService().getArea().getName());
    hdr.setService(op.getService().getId());
    hdr.setOperation(op.getName());
    hdr.setVersion(op.getService().getVersion());
    hdr.setIsError(new MALBoolean(false));

    return hdr;
  }

  MALMessageHeader createReturnHeader(MALMessage sourceMessage, boolean isError)
  {
    MALMessageHeader hdr = new MALMessageHeader();
    MALMessageHeader srcHdr = sourceMessage.getHeader();

    hdr.setUriFrom(srcHdr.getUriTo());
    hdr.setUriTo(srcHdr.getUriFrom());
    hdr.setAuthenticationId(srcHdr.getAuthenticationId());
    hdr.setTimeStamp(new MALTime(new java.util.Date().getTime()));
    hdr.setQoSLevel(srcHdr.getQoSLevel());
    hdr.setPriority(srcHdr.getPriority());
    hdr.setDomain(srcHdr.getDomain());
    hdr.setNetworkZone(srcHdr.getNetworkZone());
    hdr.setSession(srcHdr.getSession());
    hdr.setSessionName(srcHdr.getSessionName());
    hdr.setInteractionType(srcHdr.getInteractionType());
    hdr.setInteractionStage(new MALOctet((byte) (srcHdr.getInteractionStage().getOctetValue() + 1)));
    hdr.setTransactionId(srcHdr.getTransactionId());
    hdr.setArea(srcHdr.getArea());
    hdr.setService(srcHdr.getService());
    hdr.setOperation(srcHdr.getOperation());
    hdr.setVersion(srcHdr.getVersion());
    hdr.setIsError(new MALBoolean(isError));

    return hdr;
  }
}