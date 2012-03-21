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
package org.ccsds.moims.mo.mal.impl;

import java.util.Hashtable;
import java.util.Map;
import org.ccsds.moims.mo.mal.*;
import org.ccsds.moims.mo.mal.accesscontrol.MALAccessControl;
import org.ccsds.moims.mo.mal.consumer.MALInteractionListener;
import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.*;

/**
 * This class is the central point for sending messages out.
 */
public class MessageSend
{
  private final MALAccessControl securityManager;
  private final InteractionMap imap;
  private final PubSubMap pmap;

  MessageSend(MALAccessControl securityManager, InteractionMap imap, PubSubMap pmap)
  {
    this.securityManager = securityManager;
    this.imap = imap;
    this.pmap = pmap;
  }

  /**
   * Synchronous register send.
   *
   * @param details Message details structure.
   * @param op The operation.
   * @param subscription Consumer subscription.
   * @param listener Update callback interface.
   * @throws MALException on error.
   */
  public void register(MessageDetails details,
          MALPubSubOperation op,
          Subscription subscription,
          MALInteractionListener listener) throws MALInteractionException, MALException
  {
    pmap.registerNotifyListener(details, op, subscription, listener);
    synchronousInteraction(details,
            op,
            MALPubSubOperation.REGISTER_STAGE,
            (LongHolder)null,
            (MALPublishInteractionListener) null,
            subscription);
  }

  /**
   * Synchronous publish register send.
   *
   * @param details Message details structure.
   * @param op The operation.
   * @param entityKeys List of keys that can be published.
   * @param listener Error callback interface.
   * @return Publish transaction identifier.
   * @throws MALException on error.
   */
  public Long publishRegister(MessageDetails details,
          MALPubSubOperation op,
          EntityKeyList entityKeys,
          MALPublishInteractionListener listener) throws MALInteractionException, MALException
  {
    pmap.registerPublishListener(details, listener);
    
    LongHolder transId = new LongHolder();
    synchronousInteraction(details,
            op,
            MALPubSubOperation.PUBLISH_REGISTER_STAGE,
            transId,
            (MALPublishInteractionListener) null,
            entityKeys);
    
    return transId.value;
  }

  /**
   * Synchronous publish deregister send.
   *
   * @param details Message details structure.
   * @param op The operation.
   * @throws MALException on error.
   */
  public void publishDeregister(MessageDetails details, MALPubSubOperation op) throws MALInteractionException, MALException
  {
    synchronousInteraction(details,
            op,
            MALPubSubOperation.PUBLISH_DEREGISTER_STAGE,
            (LongHolder)null,
            (MALPublishInteractionListener) null, (Object[]) null);
    pmap.getPublishListenerAndRemove(details.endpoint.getURI(), details.sessionName);
  }

  /**
   * Synchronous deregister send.
   *
   * @param details Message details structure.
   * @param op The operation.
   * @param unsubscription consumer unsubscription.
   * @throws MALException on error.
   */
  public void deregister(MessageDetails details,
          MALPubSubOperation op,
          IdentifierList unsubscription) throws MALInteractionException, MALException
  {
    synchronousInteraction(details,
            op,
            MALPubSubOperation.DEREGISTER_STAGE,
            (LongHolder)null,
            (MALPublishInteractionListener) null,
            unsubscription);
    pmap.deregisterNotifyListener(details, op, unsubscription);
  }

  /**
   * Asynchronous publish register send.
   *
   * @param details Message details structure.
   * @param op The operation.
   * @param entityKeys List of keys that can be published.
   * @param listener Response callback interface.
   * @return Publish transaction identifier.
   * @throws MALException on error.
   */
  public org.ccsds.moims.mo.mal.transport.MALMessage publishRegisterAsync(MessageDetails details,
          MALPubSubOperation op,
          EntityKeyList entityKeys,
          MALPublishInteractionListener listener) throws MALInteractionException, MALException
  {
    pmap.registerPublishListener(details, listener);
    return asynchronousInteraction(details,
            op,
            MALPubSubOperation.PUBLISH_REGISTER_STAGE,
            listener,
            entityKeys);
  }

  /**
   * Asynchronous register send.
   *
   * @param details Message details structure.
   * @param op The operation.
   * @param subscription Consumer subscription.
   * @param listener Response callback interface.
   * @throws MALException on error.
   */
  public org.ccsds.moims.mo.mal.transport.MALMessage registerAsync(MessageDetails details,
          MALPubSubOperation op,
          Subscription subscription,
          MALInteractionListener listener) throws MALInteractionException, MALException
  {
    pmap.registerNotifyListener(details, op, subscription, listener);
    return asynchronousInteraction(details, op, MALPubSubOperation.REGISTER_STAGE, listener, subscription);
  }

  /**
   * Asynchronous publish deregister send.
   *
   * @param details Message details structure.
   * @param op The operation.
   * @param listener Response callback interface.
   * @throws MALException on error.
   */
  public org.ccsds.moims.mo.mal.transport.MALMessage publishDeregisterAsync(MessageDetails details,
          MALPubSubOperation op,
          MALPublishInteractionListener listener) throws MALInteractionException, MALException
  {
    pmap.getPublishListenerAndRemove(details.endpoint.getURI(), details.sessionName);
    return asynchronousInteraction(details, op, MALPubSubOperation.PUBLISH_DEREGISTER_STAGE, listener, (Object[]) null);
  }

  /**
   * Asynchronous deregister send.
   *
   * @param details Message details structure.
   * @param op The operation.
   * @param unsubscription consumer unsubscription.
   * @param listener Response callback interface.
   * @throws MALException on error.
   */
  public org.ccsds.moims.mo.mal.transport.MALMessage deregisterAsync(MessageDetails details,
          MALPubSubOperation op,
          IdentifierList unsubscription,
          MALInteractionListener listener) throws MALInteractionException, MALException
  {
    org.ccsds.moims.mo.mal.transport.MALMessage msg = asynchronousInteraction(details,
            op, MALPubSubOperation.DEREGISTER_STAGE, listener, unsubscription);
    pmap.deregisterNotifyListener(details, op, unsubscription);

    return msg;
  }

  /**
   * Send return response method.
   *
   * @param msgAddress Address structure to use for return message.
   * @param internalTransId Internal transaction identifier.
   * @param srcHdr Message header to use as reference for return messages header.
   * @param rspnInteractionStage Interaction stage to use on the response.
   * @param rspn Response message body.
   */
  public org.ccsds.moims.mo.mal.transport.MALMessage returnResponse(Address msgAddress,
          Long internalTransId,
          MALMessageHeader srcHdr,
          UOctet rspnInteractionStage,
          Object... rspn)
  {
    return returnResponse(msgAddress, internalTransId, srcHdr, srcHdr.getQoSlevel(), rspnInteractionStage, rspn);
  }

  /**
   * Send return response method.
   *
   * @param msgAddress Address structure to use for return message.
   * @param internalTransId Internal transaction identifier.
   * @param srcHdr Message header to use as reference for return messages header.
   * @param rspnInteractionStage Interaction stage to use on the response.
   * @param rspn Response message body.
   */
  public org.ccsds.moims.mo.mal.transport.MALMessage returnResponse(Address msgAddress,
          Long internalTransId,
          MALMessageHeader srcHdr,
          QoSLevel lvl,
          UOctet rspnInteractionStage,
          Object... rspn)
  {
    MALMessage msg = null;

    try
    {
      MALEndPoint endpoint = msgAddress.endpoint;
      msg = createReturnMessage(endpoint, msgAddress.uri,
              msgAddress.authenticationId,
              srcHdr,
              lvl,
              rspnInteractionStage,
              false, new Hashtable(), rspn);

      endpoint.sendMessage(msg);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      Logging.logMessage("ERROR: Error returning response to consumer : " + srcHdr.getURIFrom());
    }

    return msg;
  }

  void returnErrorAndCalculateStage(Address msgAddress,
          Long internalTransId,
          MALMessageHeader srcHdr,
          MALException error)
  {
    UOctet rspnInteractionStage = _returnErrorAndCalculateStage(msgAddress, internalTransId, srcHdr);
    
    if (0 == rspnInteractionStage.getValue())
    {
      Logging.logMessage("ERROR: Unable to return error, already a return message (" + error + ")");
    }
    else
    {
      returnError(msgAddress, internalTransId, srcHdr, rspnInteractionStage, error);
    }
  }

  void returnErrorAndCalculateStage(Address msgAddress,
          Long internalTransId,
          MALMessageHeader srcHdr,
          MALStandardError error)
  {
    UOctet rspnInteractionStage = _returnErrorAndCalculateStage(msgAddress, internalTransId, srcHdr);
    
    if (0 == rspnInteractionStage.getValue())
    {
      Logging.logMessage("ERROR: Unable to return error, already a return message (" + error + ")");
    }
    else
    {
      returnError(msgAddress, internalTransId, srcHdr, rspnInteractionStage, error);
    }
  }

  UOctet _returnErrorAndCalculateStage(Address msgAddress,
          Long internalTransId,
          MALMessageHeader srcHdr)
  {
    UOctet rspnInteractionStage = new UOctet((short) 100);
    final short srcInteractionStage = srcHdr.getInteractionStage().getValue();

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
            break;
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
          default:
          {
            // no op
          }
        }
        break;
      }
      default:
      {
        // no op
      }
    }

    return rspnInteractionStage;
  }

  /**
   * Send return error method.
   *
   * @param msgAddress Address structure to use for return message.
   * @param internalTransId Internal transaction identifier.
   * @param srcHdr Message header to use as reference for return messages header.
   * @param rspnInteractionStage Interaction stage to use on the response.
   * @param error Response message error.
   */
  public org.ccsds.moims.mo.mal.transport.MALMessage returnError(Address msgAddress,
          Long internalTransId,
          MALMessageHeader srcHdr,
          UOctet rspnInteractionStage,
          MALException error)
  {
    return returnError(msgAddress, internalTransId, srcHdr, srcHdr.getQoSlevel(), rspnInteractionStage, new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER, new Union(error.getLocalizedMessage())));
  }

  /**
   * Send return error method.
   *
   * @param msgAddress Address structure to use for return message.
   * @param internalTransId Internal transaction identifier.
   * @param srcHdr Message header to use as reference for return messages header.
   * @param rspnInteractionStage Interaction stage to use on the response.
   * @param error Response message error.
   */
  public org.ccsds.moims.mo.mal.transport.MALMessage returnError(Address msgAddress,
          Long internalTransId,
          MALMessageHeader srcHdr,
          UOctet rspnInteractionStage,
          MALStandardError error)
  {
    return returnError(msgAddress, internalTransId, srcHdr, srcHdr.getQoSlevel(), rspnInteractionStage, error);
  }

  org.ccsds.moims.mo.mal.transport.MALMessage returnError(Address msgAddress,
          Long internalTransId,
          MALMessageHeader srcHdr,
          QoSLevel level,
          UOctet rspnInteractionStage,
          MALStandardError error)
  {
    MALMessage msg = null;

    try
    {
      if (null == level)
      {
        level = srcHdr.getQoSlevel();
      }

      msg = createReturnMessage(msgAddress.endpoint, msgAddress.uri,
              msgAddress.authenticationId, srcHdr, level, rspnInteractionStage, true, new Hashtable(), error.getErrorNumber(), error.getExtraInformation());

      msgAddress.endpoint.sendMessage(msg);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      Logging.logMessage("ERROR: Error returning exception to consumer : " + srcHdr.getURIFrom());
    }

    return msg;
  }

  /**
   * Performs a oneway interaction, sends the message and then returns.
   *
   * @param details Message details structure.
   * @param transId The transaction identifier to use.
   * @param op The operation.
   * @param stage The interaction stage to use.
   * @param msgBody The message body.
   * @throws MALException on Error.
   */
  public org.ccsds.moims.mo.mal.transport.MALMessage onewayInteraction(MessageDetails details,
          Long transId,
          MALOperation op,
          UOctet stage,
          Object... msgBody) throws MALInteractionException, MALException
  {
    MALMessage msg = createMessage(details, op, transId, stage, msgBody);
    
    return onewayInteraction(details, msg);
  }
  
  public org.ccsds.moims.mo.mal.transport.MALMessage onewayPublish(NotifyMessage notifyMessage)
          throws MALInteractionException, MALException
  {
    MALMessage msg = createMessage(notifyMessage.details, notifyMessage.transId, InteractionType.PUBSUB, MALPubSubOperation.NOTIFY_STAGE, notifyMessage.area, notifyMessage.service, notifyMessage.operation, notifyMessage.version, (Object[]) notifyMessage.updates);
    msg.getHeader().setDomain(notifyMessage.domain);
    msg.getHeader().setNetworkZone(notifyMessage.networkZone);
    return onewayInteraction(notifyMessage.details, msg);
  }
  
  public org.ccsds.moims.mo.mal.transport.MALMessage onewayInteraction(MessageDetails details,
          MALMessage msg) throws MALInteractionException, MALException
  {
    try
    {
      msg = securityManager.check(msg);

      details.endpoint.sendMessage(msg);
    }
    catch (IllegalArgumentException ex)
    {
      throw new MALException("IllegalArgumentException", ex);
    }
    catch (MALException ex)
    {
      Logging.logMessage("ERROR: Error with one way send : " + msg.getHeader().getURITo());
      throw ex;
    }

    return msg;
  }

  /**
   * Performs a two way interaction, sends the message and then waits for the specified stage before returning.
   *
   * @param details Message details structure.
   * @param op The operation.
   * @param syncStage The interaction stage to wait for before returning.
   * @param listener Interaction listener to use for the reception of other stages.
   * @param msgBody The message body.
   * @return The return value.
   * @throws MALException on Error.
   */
  public MALMessageBody synchronousInteraction(MessageDetails details,
          MALOperation op,
          UOctet syncStage,
          MALInteractionListener listener,
          Object... msgBody) throws MALInteractionException, MALException
  {
    Long transId = imap.createTransaction(op, true, listener);

    return synchronousInteraction(transId, details, op, syncStage, msgBody);
  }

  /**
   * Performs a two way publisher interaction, sends the message and then waits for the specified stage before
   * returning.
   *
   * @param details Message details structure.
   * @param op The operation.
   * @param syncStage The interaction stage to wait for before returning.
   * @param listener Interaction listener to use for the reception of other stages.
   * @param msgBody The message body.
   * @return The return value.
   * @throws MALException on Error.
   */
  public MALMessageBody synchronousInteraction(MessageDetails details,
          MALOperation op,
          UOctet syncStage,
          LongHolder transactionHolder,
          MALPublishInteractionListener listener,
          Object... msgBody) throws MALInteractionException, MALException
  {
    Long transId = imap.createTransaction(op, true, listener);

    MALMessageBody rv = synchronousInteraction(transId, details, op, syncStage, msgBody);

    if (MALPubSubOperation.PUBLISH_REGISTER_STAGE == syncStage)
    {
      transactionHolder.value = transId;
    }

    return rv;
  }

  private MALMessageBody synchronousInteraction(Long transId,
          MessageDetails details,
          MALOperation op,
          UOctet syncStage,
          Object... msgBody) throws MALInteractionException, MALException
  {
    MALMessage msg = createMessage(details, op, transId, syncStage, msgBody);

    try
    {
      msg = securityManager.check(msg);

      details.endpoint.sendMessage(msg);

      MALMessage rtn = imap.waitForResponse(transId);

      handlePossibleReturnError(rtn);

      return rtn.getBody();
    }
    catch (IllegalArgumentException ex)
    {
      throw new MALException("IllegalArgumentException", ex);
    }
    catch (MALException ex)
    {
      Logging.logMessage("ERROR: Error with consumer : " + msg.getHeader().getURITo());
      throw ex;
    }
  }

  /**
   * Performs a two way interaction, sends the message.
   *
   * @param details Message details structure.
   * @param op The operation.
   * @param initialStage The initial interaction stage.
   * @param listener Interaction listener to use for the reception of other stages.
   * @param msgBody The message body.
   * @throws MALException on Error.
   */
  public org.ccsds.moims.mo.mal.transport.MALMessage asynchronousInteraction(MessageDetails details,
          MALOperation op,
          UOctet initialStage,
          MALInteractionListener listener,
          Object... msgBody) throws MALInteractionException, MALException
  {
    Long transId = imap.createTransaction(op, false, listener);

    return asynchronousInteraction(transId, details, op, initialStage, msgBody);
  }

  /**
   * Performs a two way interaction, sends the message.
   *
   * @param details Message details structure.
   * @param op The operation.
   * @param initialStage The initial interaction stage.
   * @param listener Interaction listener to use for the reception of other stages.
   * @param msgBody The message body.
   * @return The transaction identifier is this is a PUBLISH REGISTER message, else null.
   * @throws MALException on Error.
   */
  public org.ccsds.moims.mo.mal.transport.MALMessage asynchronousInteraction(MessageDetails details,
          MALOperation op,
          UOctet initialStage,
          MALPublishInteractionListener listener,
          Object... msgBody) throws MALInteractionException, MALException
  {
    Long transId = imap.createTransaction(op, false, listener);

    return asynchronousInteraction(transId, details, op, initialStage, msgBody);
  }

  private org.ccsds.moims.mo.mal.transport.MALMessage asynchronousInteraction(Long transId,
          MessageDetails details,
          MALOperation op,
          UOctet initialStage,
          Object... msgBody) throws MALInteractionException, MALException
  {
    MALMessage msg = createMessage(details, op, transId, initialStage, msgBody);

    try
    {
      msg = securityManager.check(msg);

      details.endpoint.sendMessage(msg);
    }
    catch (IllegalArgumentException ex)
    {
      throw new MALException("IllegalArgumentException", ex);
    }
    catch (MALException ex)
    {
      Logging.logMessage("ERROR: Error with consumer : " + msg.getHeader().getURITo());
      throw ex;
    }

    return msg;
  }

  private void handlePossibleReturnError(MALMessage rtn) throws MALInteractionException, MALException
  {
    if ((null != rtn) && (rtn.getHeader().getIsErrorMessage()))
    {
      if (rtn.getBody() instanceof MALErrorBody)
      {
        throw new MALInteractionException(((MALErrorBody) rtn.getBody()).getError());
      }

      throw new MALInteractionException(new MALStandardError(MALHelper.BAD_ENCODING_ERROR_NUMBER,
              new Union("Return message marked as error but did not contain a MALException")));
    }
  }

  /**
   * Creates a message header.
   *
   * @param details Message details structure.
   * @param op The operation.
   * @param transactionId The transaction identifier to use.
   * @param interactionStage The interaction stage.
   * @return the new message header.
   */
  public static MALMessage createMessage(MessageDetails details,
          MALOperation op,
          Long transactionId,
          UOctet interactionStage,
          Object... body) throws MALException
  {
    return createMessage(details,
            transactionId,
            op.getInteractionType(),
            interactionStage,
            op.getService().getArea().getNumber(),
            op.getService().getNumber(),
            op.getNumber(),
            op.getService().getVersion(),
            body);
  }
  
  public static MALMessage createMessage(MessageDetails details,
          Long transactionId,
          InteractionType interactionType,
          UOctet interactionStage,
          UShort area,
          UShort service,
          UShort operation,
          UOctet version,
          Object... body) throws MALException
  {
    URI to = details.brokerUri;

    if (interactionType != InteractionType.PUBSUB)
    {
      to = details.uriTo;
    }

    return details.endpoint.createMessage(details.authenticationId,
            to,
            new Time(new java.util.Date().getTime()),
            details.qosLevel,
            details.priority,
            details.domain,
            details.networkZone,
            details.sessionType,
            details.sessionName,
            interactionType,
            interactionStage,
            transactionId,
            area,
            service,
            operation,
            version,
            Boolean.FALSE,
            details.qosProps,
            body);
  }

  MALMessage createReturnMessage(MALEndPoint endPoint, URI uriFrom,
          Blob authId,
          MALMessageHeader srcHdr,
          QoSLevel level,
          UOctet interactionStage,
          boolean isError,
          Map qos, Object... rspn) throws IllegalArgumentException, MALException
  {
    return endPoint.createMessage(authId,
            srcHdr.getURIFrom(),
            new Time(new java.util.Date().getTime()),
            level,
            srcHdr.getPriority(),
            srcHdr.getDomain(),
            srcHdr.getNetworkZone(),
            srcHdr.getSession(),
            srcHdr.getSessionName(),
            srcHdr.getInteractionType(),
            interactionStage,
            srcHdr.getTransactionId(),
            srcHdr.getServiceArea(),
            srcHdr.getService(),
            srcHdr.getOperation(),
            srcHdr.getServiceVersion(),
            isError,
            qos,
            rspn);
  }
}
