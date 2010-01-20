package org.ccsds.moims.mo.mal.impl;

import java.util.Hashtable;
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
import org.ccsds.moims.mo.mal.MALFactory;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.structures.EntityKeyList;
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
import org.ccsds.moims.mo.mal.impl.transport.MALTransportSingleton;
import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.StandardError;
import org.ccsds.moims.mo.mal.structures.Union;

/**
 * @version 1.0
 * @created 17-Aug-2006 10:24:12
 */
public class MALServiceSend
{
  private final MALImpl impl;
  private final MALInteractionMap imap;
  private final MALPubSubMap pmap;
  private final MALServiceReceive receiveHandler;

  public MALServiceSend(MALImpl impl, MALInteractionMap imap, MALPubSubMap pmap, MALServiceReceive receiveHandler)
  {
    this.impl = impl;
    this.imap = imap;
    this.pmap = pmap;
    this.receiveHandler = receiveHandler;
  }

  public void send(MALMessageDetails details, MALSendOperation op, Element requestBody) throws MALException
  {
    onewayInteraction(details, null, op, Byte.valueOf((byte)0), requestBody);
  }

  public void submit(MALMessageDetails details, MALSubmitOperation op, Element requestBody) throws MALException
  {
    synchronousInteraction(details, op, MALSubmitOperation.SUBMIT_STAGE, (MALInteractionListener) null, requestBody);
  }

  public Element request(MALMessageDetails details, MALRequestOperation op, Element requestBody) throws MALException
  {
    return synchronousInteraction(details, op, MALRequestOperation.REQUEST_STAGE, (MALInteractionListener) null, requestBody);
  }

  public Element invoke(MALMessageDetails details, MALInvokeOperation op, Element requestBody, MALInteractionListener listener) throws MALException
  {
    return synchronousInteraction(details, op, MALInvokeOperation.INVOKE_STAGE, listener, requestBody);
  }

  public Element progress(MALMessageDetails details, MALProgressOperation op, Element requestBody, MALInteractionListener listener) throws MALException
  {
    return synchronousInteraction(details, op, MALProgressOperation._PROGRESS_STAGE, listener, requestBody);
  }

  public void register(MALMessageDetails details, MALPubSubOperation op, Subscription subscription, MALInteractionListener list) throws MALException
  {
    pmap.registerNotifyListener(details, op, subscription, list);
    synchronousInteraction(details, op, MALPubSubOperation.REGISTER_STAGE, (MALPublishInteractionListener) null, subscription);
  }

  public Identifier publishRegister(MALMessageDetails details, MALPubSubOperation op, EntityKeyList entityKeys, MALPublishInteractionListener listener) throws MALException
  {
    pmap.registerPublishListener(details, listener);
    return (Identifier) synchronousInteraction(details, op, MALPubSubOperation.PUBLISH_REGISTER_STAGE, (MALPublishInteractionListener) null, entityKeys);
  }

  public void publish(MALMessageDetails details, Identifier transId, MALPubSubOperation op, UpdateList updateList) throws MALException
  {
    onewayInteraction(details, transId, op, MALPubSubOperation.PUBLISH_STAGE, updateList);
  }

  public void publishDeregister(MALMessageDetails details, MALPubSubOperation op) throws MALException
  {
    synchronousInteraction(details, op, MALPubSubOperation.PUBLISH_DEREGISTER_STAGE, (MALPublishInteractionListener) null, null);
    pmap.getPublishListenerAndRemove(details.endpoint.getURI(), details.sessionName);
  }

  public void deregister(MALMessageDetails details, MALPubSubOperation op, IdentifierList unsubscription) throws MALException
  {
    synchronousInteraction(details, op, MALPubSubOperation.DEREGISTER_STAGE, (MALPublishInteractionListener) null, unsubscription);
    pmap.deregisterNotifyListener(details, op, unsubscription);
  }

  public void submitAsync(MALMessageDetails details, MALSubmitOperation op, Element requestBody, MALInteractionListener listener) throws MALException
  {
    asynchronousInteraction(details, op, MALSubmitOperation.SUBMIT_STAGE, listener, requestBody);
  }

  public void requestAsync(MALMessageDetails details, MALRequestOperation op, Element requestBody, MALInteractionListener listener) throws MALException
  {
    asynchronousInteraction(details, op, MALRequestOperation.REQUEST_STAGE, listener, requestBody);
  }

  public void invokeAsync(MALMessageDetails details, MALInvokeOperation op, Element requestBody, MALInteractionListener listener) throws MALException
  {
    asynchronousInteraction(details, op, MALInvokeOperation.INVOKE_STAGE, listener, requestBody);
  }

  public void progressAsync(MALMessageDetails details, MALProgressOperation op, Element requestBody, MALInteractionListener listener) throws MALException
  {
    asynchronousInteraction(details, op, MALProgressOperation.PROGRESS_STAGE, listener, requestBody);
  }

  public Identifier publishRegisterAsync(MALMessageDetails details, MALPubSubOperation op, EntityKeyList entityKeys, MALPublishInteractionListener listener) throws MALException
  {
    pmap.registerPublishListener(details, listener);
    return (Identifier) asynchronousInteraction(details, op, MALPubSubOperation.PUBLISH_REGISTER_STAGE, listener, entityKeys);
  }

  public void registerAsync(MALMessageDetails details, MALPubSubOperation op, Subscription subscription, MALInteractionListener listener) throws MALException
  {
    pmap.registerNotifyListener(details, op, subscription, listener);
    asynchronousInteraction(details, op, MALPubSubOperation.REGISTER_STAGE, listener, subscription);
  }

  public void publishDeregisterAsync(MALMessageDetails details, MALPubSubOperation op, MALPublishInteractionListener listener) throws MALException
  {
    pmap.getPublishListenerAndRemove(details.endpoint.getURI(), details.sessionName);
    asynchronousInteraction(details, op, MALPubSubOperation.PUBLISH_DEREGISTER_STAGE, listener, null);
  }

  public void deregisterAsync(MALMessageDetails details, MALPubSubOperation op, IdentifierList unsubscription, MALInteractionListener listener) throws MALException
  {
    asynchronousInteraction(details, op, MALPubSubOperation.DEREGISTER_STAGE, listener, unsubscription);
    pmap.deregisterNotifyListener(details, op, unsubscription);
  }

  public void returnResponse(MALServiceComponentImpl msgReceiver, Identifier internalTransId, MessageHeader srcHdr, Byte rspnInteractionStage, Element rspn)
  {
    try
    {
      MALEndPoint endpoint = msgReceiver.getEndpoint();
      MALMessage msg = endpoint.createMessage(createReturnHeader(msgReceiver, srcHdr, rspnInteractionStage, false), rspn, new Hashtable());

      endpoint.sendMessage(msg);
    }
    catch (MALException ex)
    {
      Logging.logMessage("ERROR: Error returning response to consumer : " + srcHdr.getURIfrom());
    }
  }

  public void returnErrorAndCalculateStage(MALServiceComponentImpl msgReceiver, Identifier internalTransId, MessageHeader srcHdr, StandardError error)
  {
    Byte rspnInteractionStage = -1;
    final int srcInteractionStage = srcHdr.getInteractionStage().intValue();

    switch (srcHdr.getInteractionType().getOrdinal())
    {
      case InteractionType._SUBMIT_INDEX:
      {
        if (MALSubmitOperation._SUBMIT_STAGE == srcInteractionStage)
        {
          rspnInteractionStage = MALSubmitOperation.SUBMIT_ACK_STAGE;
        }
        break;
      }
      case InteractionType._REQUEST_INDEX:
      {
        if (MALRequestOperation._REQUEST_STAGE == srcInteractionStage)
        {
          rspnInteractionStage = MALRequestOperation.REQUEST_RESPONSE_STAGE;
        }
        break;
      }
      case InteractionType._INVOKE_INDEX:
      {
        if (MALInvokeOperation._INVOKE_STAGE == srcInteractionStage)
        {
          rspnInteractionStage = MALInvokeOperation.INVOKE_ACK_STAGE;
        }
        break;
      }
      case InteractionType._PROGRESS_INDEX:
      {
        if (MALProgressOperation._PROGRESS_STAGE == srcInteractionStage)
        {
          rspnInteractionStage = MALProgressOperation.PROGRESS_ACK_STAGE;
        }
        break;
      }
      case InteractionType._PUBSUB_INDEX:
      {
        switch (srcInteractionStage)
        {
          case MALPubSubOperation._REGISTER_STAGE:
          {
            rspnInteractionStage = MALPubSubOperation.REGISTER_ACK_STAGE;
            break;
          }
          case MALPubSubOperation._PUBLISH_REGISTER_STAGE:
          {
            rspnInteractionStage = MALPubSubOperation.PUBLISH_REGISTER_ACK_STAGE;
            break;
          }
          case MALPubSubOperation._PUBLISH_STAGE:
          {
            rspnInteractionStage = MALPubSubOperation.PUBLISH_STAGE;
            throw new UnsupportedOperationException("Not supported yet.");
            //break;
            }
          case MALPubSubOperation._DEREGISTER_STAGE:
          {
            rspnInteractionStage = MALPubSubOperation.DEREGISTER_ACK_STAGE;
            break;
          }
          case MALPubSubOperation._PUBLISH_DEREGISTER_STAGE:
          {
            rspnInteractionStage = MALPubSubOperation.PUBLISH_DEREGISTER_ACK_STAGE;
            break;
          }
        }
        break;
      }
    }

    if (0 > rspnInteractionStage)
    {
      Logging.logMessage("ERROR: Unable to return error, already a return message (" + error + ")");
    }
    else
    {
      returnError(msgReceiver, internalTransId, srcHdr, rspnInteractionStage, error);
    }
  }

  public void returnError(MALServiceComponentImpl msgReceiver, Identifier internalTransId, MessageHeader srcHdr, Byte rspnInteractionStage, StandardError error)
  {
    returnError(msgReceiver, internalTransId, srcHdr, srcHdr.getQoSlevel(), rspnInteractionStage, error);
  }

  public void returnError(MALServiceComponentImpl msgReceiver, Identifier internalTransId, MessageHeader srcHdr, QoSLevel level, Byte rspnInteractionStage, StandardError error)
  {
    try
    {
      MALEndPoint endpoint = null;
      URI uriFrom = null;
      Blob authId = null;

      if (null != msgReceiver)
      {
        endpoint = msgReceiver.getEndpoint();
        uriFrom = msgReceiver.getURI();
        authId = msgReceiver.authenticationId;
      }
      else
      {
        URI uriTo = null;

        Pair details = imap.resolveTransactionSource(internalTransId);
        if (null != details)
        {
          uriTo = (URI) details.getFirst();
        }
        else
        {
          uriTo = srcHdr.getURIfrom();
        }

        MALService service = MALFactory.lookupOperation(srcHdr.getArea(), srcHdr.getService(), srcHdr.getOperation()).getService();
        endpoint = MALTransportSingleton.instance(uriTo, impl.getInitialProperties()).createEndPoint(null, service, null);
        uriFrom = endpoint.getURI();
        endpoint.setMessageListener(receiveHandler);
      }

      if (null == level)
      {
        level = srcHdr.getQoSlevel();
      }

      MALMessage msg = endpoint.createMessage(createReturnHeader(uriFrom, authId, srcHdr, level, rspnInteractionStage, true), error, new Hashtable());

      if (MALPubSubOperation.PUBLISH_STAGE.byteValue() == rspnInteractionStage.byteValue())
      {
        Logging.logMessage("RTNERROR: " + msg.getHeader().toString());
      }

      endpoint.sendMessage(msg);
    }
    catch (MALException ex)
    {
      Logging.logMessage("ERROR: Error returning exception to consumer : " + srcHdr.getURIfrom());
    }
  }

  private void onewayInteraction(MALMessageDetails details, Identifier transId, MALOperation op, Byte stage, Element msgBody) throws MALException
  {
    MALMessage msg = details.endpoint.createMessage(createHeader(details, op, transId, stage), msgBody, details.qosProps);

    try
    {
      msg = impl.getSecurityManager().check(msg);

      details.endpoint.sendMessage(msg);
    }
    catch (MALException ex)
    {
      Logging.logMessage("ERROR: Error with one way send : " + msg.getHeader().getURIto());
      throw ex;
    }
  }

  private Element synchronousInteraction(MALMessageDetails details, MALOperation op, Byte syncStage, MALInteractionListener listener, Element msgBody) throws MALException
  {
    Identifier transId = imap.createTransaction(op, true, syncStage, listener);

    return synchronousInteraction(transId, details, op, syncStage, msgBody);
  }

  private Element synchronousInteraction(MALMessageDetails details, MALOperation op, Byte syncStage, MALPublishInteractionListener listener, Element msgBody) throws MALException
  {
    Identifier transId = imap.createTransaction(op, true, syncStage, listener);

    Element rv = synchronousInteraction(transId, details, op, syncStage, msgBody);

    if (MALPubSubOperation._PUBLISH_REGISTER_STAGE == syncStage)
    {
      return transId;
    }
    else
    {
      return rv;
    }
  }

  private Element synchronousInteraction(Identifier transId, MALMessageDetails details, MALOperation op, Byte syncStage, Element msgBody) throws MALException
  {
    MALMessage msg = details.endpoint.createMessage(createHeader(details, op, transId, syncStage), msgBody, details.qosProps);

    try
    {
      msg = impl.getSecurityManager().check(msg);

      details.endpoint.sendMessage(msg);

      MALMessage rtn = imap.waitForResponse(transId);

      handlePossibleReturnError(rtn);

      return rtn.getBody();
    }
    catch (MALException ex)
    {
      Logging.logMessage("ERROR: Error with consumer : " + msg.getHeader().getURIto());
      throw ex;
    }
  }

  private void asynchronousInteraction(MALMessageDetails details, MALOperation op, Byte syncStage, MALInteractionListener listener, Element msgBody) throws MALException
  {
    Identifier transId = imap.createTransaction(op, false, syncStage, listener);

    asynchronousInteraction(transId, details, op, syncStage, msgBody);
  }

  private Element asynchronousInteraction(MALMessageDetails details, MALOperation op, Byte syncStage, MALPublishInteractionListener listener, Element msgBody) throws MALException
  {
    Identifier transId = imap.createTransaction(op, false, syncStage, listener);

    asynchronousInteraction(transId, details, op, syncStage, msgBody);

    if (MALPubSubOperation._PUBLISH_REGISTER_STAGE == syncStage)
    {
      return transId;
    }
    else
    {
      return null;
    }
  }

  private void asynchronousInteraction(Identifier transId, MALMessageDetails details, MALOperation op, Byte syncStage, Element msgBody) throws MALException
  {
    MALMessage msg = details.endpoint.createMessage(createHeader(details, op, transId, syncStage), msgBody, details.qosProps);

    try
    {
      msg = impl.getSecurityManager().check(msg);

      details.endpoint.sendMessage(msg);
    }
    catch (MALException ex)
    {
      Logging.logMessage("ERROR: Error with consumer : " + msg.getHeader().getURIto());
      throw ex;
    }
  }

  private void handlePossibleReturnError(MALMessage rtn) throws MALException
  {
    if ((null != rtn) && (rtn.getHeader().isError()))
    {
      if (rtn.getBody() instanceof StandardError)
      {
        throw new MALException((StandardError) rtn.getBody());
      }

      throw new MALException(new StandardError(MALHelper.BAD_ENCODING_ERROR_NUMBER, new Union("Return message marked as error but did not contain a MALException")));
    }
  }

  public static MessageHeader createHeader(MALMessageDetails details, MALOperation op, Identifier transactionId, Byte interactionStage)
  {
    MessageHeader hdr = new MessageHeader();

    if (null != details.uriFrom)
    {
      hdr.setURIfrom(details.uriFrom);
    }
    else
    {
      hdr.setURIfrom(details.endpoint.getURI());
    }

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
    hdr.setError(Boolean.FALSE);

    return hdr;
  }

  MessageHeader createReturnHeader(MALServiceComponentImpl msgSource, MessageHeader srcHdr, Byte interactionStage, boolean isError)
  {
    return createReturnHeader(msgSource.getURI(), msgSource.authenticationId, srcHdr, srcHdr.getQoSlevel(), interactionStage, isError);
  }

  MessageHeader createReturnHeader(URI uriFrom, Blob authId, MessageHeader srcHdr, QoSLevel level, Byte interactionStage, boolean isError)
  {
    MessageHeader hdr = new MessageHeader();

    hdr.setURIfrom(uriFrom);
    hdr.setURIto(srcHdr.getURIfrom());
    hdr.setAuthenticationId(authId);
    hdr.setTimestamp(new Time(new java.util.Date().getTime()));
    hdr.setQoSlevel(level);
    hdr.setPriority(srcHdr.getPriority());
    hdr.setDomain(srcHdr.getDomain());
    hdr.setNetworkZone(srcHdr.getNetworkZone());
    hdr.setSession(srcHdr.getSession());
    hdr.setSessionName(srcHdr.getSessionName());
    hdr.setInteractionType(srcHdr.getInteractionType());
    hdr.setInteractionStage(interactionStage);
    hdr.setTransactionId(srcHdr.getTransactionId());
    hdr.setArea(srcHdr.getArea());
    hdr.setService(srcHdr.getService());
    hdr.setOperation(srcHdr.getOperation());
    hdr.setVersion(srcHdr.getVersion());
    hdr.setError(Boolean.valueOf(isError));

    return hdr;
  }
}
