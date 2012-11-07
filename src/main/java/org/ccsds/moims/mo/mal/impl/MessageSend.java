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
package org.ccsds.moims.mo.mal.impl;

import java.util.Date;
import java.util.Hashtable;
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
  private final InteractionConsumerMap icmap;
  private final InteractionProviderMap ipmap;
  private final InteractionPubSubMap ipsmap;

  MessageSend(final MALAccessControl securityManager,
          final InteractionConsumerMap imap,
          final InteractionProviderMap pmap,
          final InteractionPubSubMap psmap)
  {
    this.securityManager = securityManager;
    this.icmap = imap;
    this.ipmap = pmap;
    this.ipsmap = psmap;
  }

  /**
   * Synchronous register send.
   *
   * @param details Message details structure.
   * @param op The operation.
   * @param subscription Consumer subscription.
   * @param listener Update callback interface.
   * @throws MALInteractionException if there is a problem during the interaction.
   * @throws MALException on error.
   */
  public void register(final MessageDetails details,
          final MALPubSubOperation op,
          final Subscription subscription,
          final MALInteractionListener listener) throws MALInteractionException, MALException
  {
    ipsmap.registerNotifyListener(details, op, subscription, listener);
    initiateSynchronousInteraction(details,
            op,
            MALPubSubOperation.REGISTER_STAGE,
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
   * @throws MALInteractionException if there is a problem during the interaction.
   * @throws MALException on error.
   */
  public Long publishRegister(final MessageDetails details,
          final MALPubSubOperation op,
          final EntityKeyList entityKeys,
          final MALPublishInteractionListener listener) throws MALInteractionException, MALException
  {
    ipsmap.registerPublishListener(details, listener);

    return initiateSynchronousInteraction(details,
            op,
            MALPubSubOperation.PUBLISH_REGISTER_STAGE,
            (MALPublishInteractionListener) null,
            entityKeys);
  }

  /**
   * Synchronous publish deregister send.
   *
   * @param details Message details structure.
   * @param op The operation.
   * @throws MALInteractionException if there is a problem during the interaction.
   * @throws MALException on error.
   */
  public void publishDeregister(final MessageDetails details, final MALPubSubOperation op)
          throws MALInteractionException, MALException
  {
    initiateSynchronousInteraction(details,
            op,
            MALPubSubOperation.PUBLISH_DEREGISTER_STAGE,
            (MALPublishInteractionListener) null,
            (Object[]) null);
    ipsmap.getPublishListenerAndRemove(details.endpoint.getURI(), details);
  }

  /**
   * Synchronous deregister send.
   *
   * @param details Message details structure.
   * @param op The operation.
   * @param unsubscription consumer unsubscription.
   * @throws MALInteractionException if there is a problem during the interaction.
   * @throws MALException on error.
   */
  public void deregister(final MessageDetails details,
          final MALPubSubOperation op,
          final IdentifierList unsubscription) throws MALInteractionException, MALException
  {
    initiateSynchronousInteraction(details,
            op,
            MALPubSubOperation.DEREGISTER_STAGE,
            (MALPublishInteractionListener) null,
            unsubscription);
    ipsmap.deregisterNotifyListener(details, op, unsubscription);
  }

  /**
   * Asynchronous publish register send.
   *
   * @param details Message details structure.
   * @param op The operation.
   * @param entityKeys List of keys that can be published.
   * @param listener Response callback interface.
   * @return The sent MAL message.
   * @throws MALInteractionException if there is a problem during the interaction.
   * @throws MALException on error.
   */
  public MALMessage publishRegisterAsync(final MessageDetails details,
          final MALPubSubOperation op,
          final EntityKeyList entityKeys,
          final MALPublishInteractionListener listener) throws MALInteractionException, MALException
  {
    ipsmap.registerPublishListener(details, listener);
    return initiateAsynchronousInteraction(icmap.createTransaction(false, listener),
            details,
            op,
            MALPubSubOperation.PUBLISH_REGISTER_STAGE,
            entityKeys);
  }

  /**
   * Asynchronous register send.
   *
   * @param details Message details structure.
   * @param op The operation.
   * @param subscription Consumer subscription.
   * @param listener Response callback interface.
   * @return The sent MAL message.
   * @throws MALInteractionException if there is a problem during the interaction.
   * @throws MALException on error.
   */
  public MALMessage registerAsync(final MessageDetails details,
          final MALPubSubOperation op,
          final Subscription subscription,
          final MALInteractionListener listener) throws MALInteractionException, MALException
  {
    ipsmap.registerNotifyListener(details, op, subscription, listener);
    return asynchronousInteraction(details, op, MALPubSubOperation.REGISTER_STAGE, listener, subscription);
  }

  /**
   * Asynchronous publish deregister send.
   *
   * @param details Message details structure.
   * @param op The operation.
   * @param listener Response callback interface.
   * @return The sent MAL message.
   * @throws MALInteractionException if there is a problem during the interaction.
   * @throws MALException on error.
   */
  public MALMessage publishDeregisterAsync(final MessageDetails details,
          final MALPubSubOperation op,
          final MALPublishInteractionListener listener) throws MALInteractionException, MALException
  {
    ipsmap.getPublishListenerAndRemove(details.endpoint.getURI(), details);
    return initiateAsynchronousInteraction(icmap.createTransaction(false, listener),
            details,
            op,
            MALPubSubOperation.PUBLISH_DEREGISTER_STAGE,
            (Object[]) null);
  }

  /**
   * Asynchronous deregister send.
   *
   * @param details Message details structure.
   * @param op The operation.
   * @param unsubscription consumer unsubscription.
   * @param listener Response callback interface.
   * @return The sent MAL message.
   * @throws MALInteractionException if there is a problem during the interaction.
   * @throws MALException on error.
   */
  public MALMessage deregisterAsync(final MessageDetails details,
          final MALPubSubOperation op,
          final IdentifierList unsubscription,
          final MALInteractionListener listener) throws MALInteractionException, MALException
  {
    final MALMessage msg = asynchronousInteraction(details,
            op,
            MALPubSubOperation.DEREGISTER_STAGE,
            listener,
            unsubscription);
    ipsmap.deregisterNotifyListener(details, op, unsubscription);

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
   * @return The sent MAL message.
   * @throws MALInteractionException if there is a problem during the interaction.
   * @throws MALException on Error.
   */
  public MALMessage onewayInteraction(final MessageDetails details,
          final Long transId,
          final MALOperation op,
          final UOctet stage,
          final Object... msgBody) throws MALInteractionException, MALException
  {
    return initiateOnewayInteraction(details, createMessage(details, op, transId, stage, msgBody));
  }

  /**
   * Sends a oneway PUBLISH message.
   *
   * @param details Message details structure.
   * @param transId The transaction identifier to use.
   * @param domain The domain of the message.
   * @param networkZone The network zone of the message
   * @param area The area.
   * @param service the service.
   * @param operation The operation.
   * @param version The version.
   * @param updates The publish updates.
   * @return The sent MAL message.
   * @throws MALInteractionException if there is a problem during the interaction.
   * @throws MALException on internal error.
   */
  public MALMessage onewayPublish(final MessageDetails details,
          final Long transId,
          final IdentifierList domain,
          final Identifier networkZone,
          final UShort area,
          final UShort service,
          final UShort operation,
          final UOctet version,
          final Object[] updates)
          throws MALInteractionException, MALException
  {
    final MALMessage msg = details.endpoint.createMessage(details.authenticationId,
            details.brokerUri,
            new Time(new Date().getTime()),
            details.qosLevel,
            details.priority,
            domain,
            networkZone,
            details.sessionType,
            details.sessionName,
            InteractionType.PUBSUB,
            MALPubSubOperation.NOTIFY_STAGE,
            transId,
            area,
            service,
            operation,
            version,
            Boolean.FALSE,
            details.qosProps,
            (Object[]) updates);

    return initiateOnewayInteraction(details, msg);
  }

  /**
   * Performs a two way interaction, sends the message and then waits for the specified stage before returning.
   *
   * @param details Message details structure.
   * @param op The operation.
   * @param syncStage The interaction stage to wait for before returning.
   * @param listener Interaction listener to use for the reception of other stages.
   * @param msgBody The message body.
   * @return The returned message body.
   * @throws MALInteractionException if there is a problem during the interaction.
   * @throws MALException on Error.
   */
  public MALMessageBody synchronousInteraction(final MessageDetails details,
          final MALOperation op,
          final UOctet syncStage,
          final MALInteractionListener listener,
          final Object... msgBody) throws MALInteractionException, MALException
  {
    return initiateSynchronousInteraction(icmap.createTransaction(op.getInteractionType().getOrdinal(), true, listener),
            details,
            op,
            syncStage,
            msgBody);
  }

  /**
   * Performs a two way interaction, sends the message.
   *
   * @param details Message details structure.
   * @param op The operation.
   * @param initialStage The initial interaction stage.
   * @param listener Interaction listener to use for the reception of other stages.
   * @param msgBody The message body.
   * @return The sent MAL message.
   * @throws MALInteractionException if there is a problem during the interaction.
   * @throws MALException on Error.
   */
  public MALMessage asynchronousInteraction(final MessageDetails details,
          final MALOperation op,
          final UOctet initialStage,
          final MALInteractionListener listener,
          final Object... msgBody) throws MALInteractionException, MALException
  {
    return initiateAsynchronousInteraction(icmap.createTransaction(op.getInteractionType().getOrdinal(),
            false,
            listener),
            details,
            op,
            initialStage,
            msgBody);
  }

  /**
   * Send return response method.
   *
   * @param msgAddress Address structure to use for return message.
   * @param internalTransId Internal transaction identifier.
   * @param srcHdr Message header to use as reference for return messages header.
   * @param lvl The QoS level to use.
   * @param rspnInteractionStage Interaction stage to use on the response.
   * @param rspn Response message body.
   * @param isFinalStage True if this the final stage of the interaction.
   * @param operation The operation.
   * @return The sent MAL message.
   */
  public MALMessage returnResponse(final Address msgAddress,
          final Long internalTransId,
          final MALMessageHeader srcHdr,
          final QoSLevel lvl,
          final UOctet rspnInteractionStage,
          final boolean isFinalStage,
          final MALOperation operation,
          final Object... rspn)
  {
    MALMessage msg = null;

    try
    {
      msg = msgAddress.endpoint.createMessage(msgAddress.authenticationId,
              srcHdr.getURIFrom(),
              new Time(new Date().getTime()),
              lvl,
              srcHdr.getPriority(),
              srcHdr.getDomain(),
              srcHdr.getNetworkZone(),
              srcHdr.getSession(),
              srcHdr.getSessionName(),
              srcHdr.getTransactionId(),
              false,
              operation,
              rspnInteractionStage,
              new Hashtable(),
              rspn);

      if (isFinalStage)
      {
        ipmap.removeTransactionSource(internalTransId);
      }

      msgAddress.endpoint.sendMessage(msg);
    }
    catch (MALException ex)
    {
      Logging.logMessage("ERROR: Error returning response to consumer : "
              + srcHdr.getURIFrom() + " : " + ex.toString());
      ex.printStackTrace();
    }
    catch (MALTransmitErrorException ex)
    {
      Logging.logMessage("ERROR: Error returning response to consumer : "
              + srcHdr.getURIFrom() + " : " + ex.toString());
      ex.printStackTrace();
    }
    catch (RuntimeException ex)
    {
      Logging.logMessage("ERROR: Error returning response to consumer : "
              + srcHdr.getURIFrom() + " : " + ex.toString());
      ex.printStackTrace();
    }

    return msg;
  }

  /**
   * Send return error method.
   *
   * @param msgAddress Address structure to use for return message.
   * @param internalTransId Internal transaction identifier.
   * @param srcHdr Message header to use as reference for return messages header.
   * @param rspnInteractionStage Interaction stage to use on the response.
   * @param error Response message error.
   * @return The sent MAL message.
   */
  public MALMessage returnError(final Address msgAddress,
          final Long internalTransId,
          final MALMessageHeader srcHdr,
          final UOctet rspnInteractionStage,
          final MALException error)
  {
    return initiateReturnError(msgAddress,
            internalTransId,
            srcHdr,
            srcHdr.getQoSlevel(),
            rspnInteractionStage,
            new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER, new Union(error.getLocalizedMessage())));
  }

  /**
   * Send return error method.
   *
   * @param msgAddress Address structure to use for return message.
   * @param internalTransId Internal transaction identifier.
   * @param srcHdr Message header to use as reference for return messages header.
   * @param rspnInteractionStage Interaction stage to use on the response.
   * @param error Response message error.
   * @return The sent MAL message.
   */
  public MALMessage returnError(final Address msgAddress,
          final Long internalTransId,
          final MALMessageHeader srcHdr,
          final UOctet rspnInteractionStage,
          final MALStandardError error)
  {
    return initiateReturnError(msgAddress,
            internalTransId,
            srcHdr,
            srcHdr.getQoSlevel(),
            rspnInteractionStage,
            error);
  }

  private MALMessage initiateOnewayInteraction(final MessageDetails details,
          MALMessage msg) throws MALInteractionException, MALException
  {
    try
    {
      msg = securityManager.check(msg);

      details.endpoint.sendMessage(msg);
    }
    catch (IllegalArgumentException ex)
    {
      throw new MALException("ERROR: Error with one way send : IllegalArgumentException : ", ex);
    }
    catch (MALException ex)
    {
      Logging.logMessage("ERROR: Error with one way send : " + msg.getHeader().getURITo());
      throw ex;
    }

    return msg;
  }

  private Long initiateSynchronousInteraction(final MessageDetails details,
          final MALOperation op,
          final UOctet syncStage,
          final MALPublishInteractionListener listener,
          final Object... msgBody) throws MALInteractionException, MALException
  {
    final Long transId = icmap.createTransaction(true, listener);

    initiateSynchronousInteraction(transId, details, op, syncStage, msgBody);

    return transId;
  }

  private MALMessageBody initiateSynchronousInteraction(final Long transId,
          final MessageDetails details,
          final MALOperation op,
          final UOctet syncStage,
          final Object... msgBody) throws MALInteractionException, MALException
  {
    MALMessage msg = createMessage(details, op, transId, syncStage, msgBody);

    try
    {
      msg = securityManager.check(msg);

      details.endpoint.sendMessage(msg);

      final MALMessage rtn = icmap.waitForResponse(transId);

      if (null != rtn)
      {
        // handle possible return error
        if (rtn.getHeader().getIsErrorMessage())
        {
          if (rtn.getBody() instanceof MALErrorBody)
          {
            throw new MALInteractionException(((MALErrorBody) rtn.getBody()).getError());
          }

          throw new MALInteractionException(new MALStandardError(MALHelper.BAD_ENCODING_ERROR_NUMBER,
                  new Union("Return message marked as error but did not contain a MALException")));
        }

        return rtn.getBody();
      }

      throw new MALException("Return message was null");
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

  private MALMessage initiateAsynchronousInteraction(final Long transId,
          final MessageDetails details,
          final MALOperation op,
          final UOctet initialStage,
          final Object... msgBody) throws MALInteractionException, MALException
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

  private MALMessage initiateReturnError(final Address msgAddress,
          final Long internalTransId,
          final MALMessageHeader srcHdr,
          QoSLevel level,
          final UOctet rspnInteractionStage,
          final MALStandardError error)
  {
    MALMessage msg = null;

    try
    {
      if (null == level)
      {
        level = srcHdr.getQoSlevel();
      }

      msg = msgAddress.endpoint.createMessage(msgAddress.authenticationId,
              srcHdr.getURIFrom(),
              new Time(new Date().getTime()),
              level,
              srcHdr.getPriority(),
              srcHdr.getDomain(),
              srcHdr.getNetworkZone(),
              srcHdr.getSession(),
              srcHdr.getSessionName(),
              srcHdr.getInteractionType(),
              rspnInteractionStage,
              srcHdr.getTransactionId(),
              srcHdr.getServiceArea(),
              srcHdr.getService(),
              srcHdr.getOperation(),
              srcHdr.getServiceVersion(),
              true,
              new Hashtable(),
              error.getErrorNumber(), error.getExtraInformation());


      ipmap.removeTransactionSource(internalTransId);

      msgAddress.endpoint.sendMessage(msg);
    }
    catch (MALException ex)
    {
      Logging.logMessage("ERROR: Error returning error to consumer : " + srcHdr.getURIFrom() + " : " + ex.toString());
      ex.printStackTrace();
    }
    catch (MALTransmitErrorException ex)
    {
      Logging.logMessage("ERROR: Error returning error to consumer : " + srcHdr.getURIFrom() + " : " + ex.toString());
      ex.printStackTrace();
    }
    catch (RuntimeException ex)
    {
      Logging.logMessage("ERROR: Error returning error to consumer : " + srcHdr.getURIFrom() + " : " + ex.toString());
      ex.printStackTrace();
    }

    return msg;
  }

  private static MALMessage createMessage(final MessageDetails details,
          final MALOperation op,
          final Long transactionId,
          final UOctet interactionStage,
          final Object... body) throws MALException
  {
    URI to = details.brokerUri;

    if (op.getInteractionType() != InteractionType.PUBSUB)
    {
      to = details.uriTo;
    }

    return details.endpoint.createMessage(details.authenticationId,
            to,
            new Time(new Date().getTime()),
            details.qosLevel,
            details.priority,
            details.domain,
            details.networkZone,
            details.sessionType,
            details.sessionName,
            transactionId,
            Boolean.FALSE,
            op,
            interactionStage,
            details.qosProps,
            body);
  }
}
