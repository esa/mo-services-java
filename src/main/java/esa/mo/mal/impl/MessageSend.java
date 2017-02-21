/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java Implementation
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * ----------------------------------------------------------------------------
 */
package esa.mo.mal.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.*;
import org.ccsds.moims.mo.mal.accesscontrol.MALAccessControl;
import org.ccsds.moims.mo.mal.consumer.MALInteractionListener;
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
  private final InteractionPubSubMap ipsmap;

  MessageSend(final MALAccessControl securityManager,
          final InteractionConsumerMap imap,
          final InteractionPubSubMap psmap)
  {
    this.securityManager = securityManager;
    this.icmap = imap;
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
    ipsmap.registerNotifyListener(details, subscription, listener);
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
    ipsmap.deregisterNotifyListener(details, unsubscription);
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
    final Long transId = icmap.createTransaction(false, listener);
    return initiateAsynchronousInteraction(details,
            createMessage(details, op, transId, MALPubSubOperation.PUBLISH_REGISTER_STAGE, entityKeys));
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
    ipsmap.registerNotifyListener(details, subscription, listener);
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
    final Long transId = icmap.createTransaction(false, listener);
    return initiateAsynchronousInteraction(details,
            createMessage(details, op, transId, MALPubSubOperation.PUBLISH_DEREGISTER_STAGE, (Object[]) null));
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
    ipsmap.deregisterNotifyListener(details, unsubscription);

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
          Long transId,
          final MALOperation op,
          final UOctet stage,
          final Object... msgBody) throws MALInteractionException, MALException
  {
    if (null == transId)
    {
      transId = icmap.createTransaction(op.getInteractionType().getOrdinal(), true, null);
    }

    return initiateOnewayInteraction(details, createMessage(details, op, transId, stage, msgBody));
  }

  /**
   * Performs a oneway interaction, sends the message and then returns.
   *
   * @param details Message details structure.
   * @param transId The transaction identifier to use.
   * @param op The operation.
   * @param stage The interaction stage to use.
   * @param msgBody The already encoded message body.
   * @return The sent MAL message.
   * @throws MALInteractionException if there is a problem during the interaction.
   * @throws MALException on Error.
   */
  public MALMessage onewayInteraction(final MessageDetails details,
          Long transId,
          final MALOperation op,
          final UOctet stage,
          final MALEncodedBody msgBody) throws MALInteractionException, MALException
  {
    if (null == transId)
    {
      transId = icmap.createTransaction(op.getInteractionType().getOrdinal(), true, null);
    }

    return initiateOnewayInteraction(details, createMessage(details, op, transId, stage, msgBody));
  }

  /**
   * Sends a set of oneway PUBLISH messages.
   *
   * @param ep The endpoint to use to send the messages.
   * @param msgs Set of messages to send.
   * @throws MALInteractionException if there is a problem during the interaction.
   * @throws MALException on internal error.
   */
  public void onewayMultiPublish(final MALEndpoint ep, final List<MALMessage> msgs)
          throws MALInteractionException, MALException
  {
    initiateMultiOnewayInteraction(ep, msgs);
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
    final Long transId = icmap.createTransaction(op.getInteractionType().getOrdinal(), true, listener);
    return initiateSynchronousInteraction(transId,
            details,
            createMessage(details, op, transId, syncStage, msgBody));
  }

  /**
   * Performs a two way interaction, sends the message and then waits for the specified stage before returning.
   *
   * @param details Message details structure.
   * @param op The operation.
   * @param syncStage The interaction stage to wait for before returning.
   * @param listener Interaction listener to use for the reception of other stages.
   * @param msgBody The already encoded message body.
   * @return The returned message body.
   * @throws MALInteractionException if there is a problem during the interaction.
   * @throws MALException on Error.
   */
  public MALMessageBody synchronousInteraction(final MessageDetails details,
          final MALOperation op,
          final UOctet syncStage,
          final MALInteractionListener listener,
          final MALEncodedBody msgBody) throws MALInteractionException, MALException
  {
    final Long transId = icmap.createTransaction(op.getInteractionType().getOrdinal(), true, listener);
    return initiateSynchronousInteraction(transId,
            details,
            createMessage(details, op, transId, syncStage, msgBody));
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
    final Long transId = icmap.createTransaction(op.getInteractionType().getOrdinal(), false, listener);

    return initiateAsynchronousInteraction(details, createMessage(details, op, transId, initialStage, msgBody));
  }

  /**
   * Performs a two way interaction, sends the message.
   *
   * @param details Message details structure.
   * @param op The operation.
   * @param initialStage The initial interaction stage.
   * @param listener Interaction listener to use for the reception of other stages.
   * @param msgBody The already encoded message body.
   * @return The sent MAL message.
   * @throws MALInteractionException if there is a problem during the interaction.
   * @throws MALException on Error.
   */
  public MALMessage asynchronousInteraction(final MessageDetails details,
          final MALOperation op,
          final UOctet initialStage,
          final MALInteractionListener listener,
          final MALEncodedBody msgBody) throws MALInteractionException, MALException
  {
    final Long transId = icmap.createTransaction(op.getInteractionType().getOrdinal(), false, listener);

    return initiateAsynchronousInteraction(details, createMessage(details, op, transId, initialStage, msgBody));
  }

  /**
   * The method continues an interaction that has been interrupted.
   *
   * @param op The operation to continue
   * @param lastInteractionStage The last stage of the interaction to continue
   * @param initiationTimestamp Timestamp of the interaction initiation message
   * @param transactionId Transaction identifier of the interaction to continue
   * @param listener Listener in charge of receiving the messages from the service provider
   * @throws java.lang.IllegalArgumentException If the parameters ‘op’ or ‘lastInteractionStage’ or
   * ‘initiationTimestamp’ or ‘transactionId ‘ or ‘listener’ are NULL
   * @throws MALException thrown if a non-MAL error occurs during the initiation message sending or if the MALConsumer
   * is closed.
   * @throws MALInteractionException if a MAL standard error occurs during the interaction
   */
  public void continueInteraction(final MALOperation op,
          final UOctet lastInteractionStage,
          final Time initiationTimestamp,
          final Long transactionId,
          final MALInteractionListener listener)
          throws IllegalArgumentException, MALException, MALInteractionException
  {
    if (null == op)
    {
      throw new IllegalArgumentException("MALOperation argument of continueInteraction mustnot be null");
    }
    if (null == lastInteractionStage)
    {
      throw new IllegalArgumentException("lastInteractionStage argument of continueInteraction mustnot be null");
    }
    if (null == initiationTimestamp)
    {
      throw new IllegalArgumentException("initiationTimestamp argument of continueInteraction mustnot be null");
    }
    if (null == transactionId)
    {
      throw new IllegalArgumentException("transactionId argument of continueInteraction mustnot be null");
    }
    if (null == listener)
    {
      throw new IllegalArgumentException("listener argument of continueInteraction mustnot be null");
    }

    icmap.continueTransaction(op.getInteractionType().getOrdinal(), lastInteractionStage, transactionId, listener);
  }

  /**
   * Send return response method.
   *
   * @param msgAddress Address structure to use for return message.
   * @param srcHdr Message header to use as reference for return messages header.
   * @param lvl The QoS level to use.
   * @param rspnInteractionStage Interaction stage to use on the response.
   * @param rspn Response message body.
   * @param qosProperties The QoS properties.
   * @param operation The operation.
   * @return The sent MAL message.
   */
  public MALMessage returnResponse(final Address msgAddress,
          final MALMessageHeader srcHdr,
          final QoSLevel lvl,
          final UOctet rspnInteractionStage,
          final MALOperation operation,
          final Map qosProperties,
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
              qosProperties,
              rspn);

      msgAddress.endpoint.sendMessage(msg);
    }
    catch (MALException ex)
    {
      MALContextFactoryImpl.LOGGER.log(Level.WARNING,
              "Error returning response to consumer : " + srcHdr.getURIFrom() + " : ", ex);
    }
    catch (MALTransmitErrorException ex)
    {
      MALContextFactoryImpl.LOGGER.log(Level.WARNING,
              "Error returning response to consumer : " + srcHdr.getURIFrom() + " : ", ex);
    }
    catch (RuntimeException ex)
    {
      MALContextFactoryImpl.LOGGER.log(Level.WARNING,
              "Error returning response to consumer : " + srcHdr.getURIFrom() + " : ", ex);
    }

    return msg;
  }

  /**
   * Send return response method.
   *
   * @param msgAddress Address structure to use for return message.
   * @param srcHdr Message header to use as reference for return messages header.
   * @param lvl The QoS level to use.
   * @param rspnInteractionStage Interaction stage to use on the response.
   * @param rspn Response encoded message body.
   * @param qosProperties The QoS properties.
   * @param operation The operation.
   * @return The sent MAL message.
   */
  public MALMessage returnResponse(final Address msgAddress,
          final MALMessageHeader srcHdr,
          final QoSLevel lvl,
          final UOctet rspnInteractionStage,
          final MALOperation operation,
          final Map qosProperties,
          final MALEncodedBody rspn)
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
              qosProperties,
              rspn);

      msgAddress.endpoint.sendMessage(msg);
    }
    catch (MALException ex)
    {
      MALContextFactoryImpl.LOGGER.log(Level.WARNING,
              "Error returning response to consumer : " + srcHdr.getURIFrom() + " : ", ex);
    }
    catch (MALTransmitErrorException ex)
    {
      MALContextFactoryImpl.LOGGER.log(Level.WARNING,
              "Error returning response to consumer : " + srcHdr.getURIFrom() + " : ", ex);
    }
    catch (RuntimeException ex)
    {
      MALContextFactoryImpl.LOGGER.log(Level.WARNING,
              "Error returning response to consumer : " + srcHdr.getURIFrom() + " : ", ex);
    }

    return msg;
  }

  /**
   * Send return error method.
   *
   * @param msgAddress Address structure to use for return message.
   * @param srcHdr Message header to use as reference for return messages header.
   * @param rspnInteractionStage Interaction stage to use on the response.
   * @param error Response message error.
   * @return The sent MAL message.
   */
  public MALMessage returnError(final Address msgAddress,
          final MALMessageHeader srcHdr,
          final UOctet rspnInteractionStage,
          final MALException error)
  {
    return initiateReturnError(msgAddress,
            srcHdr,
            srcHdr.getQoSlevel(),
            rspnInteractionStage,
            new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER, new Union(error.getLocalizedMessage())));
  }

  /**
   * Send return error method.
   *
   * @param msgAddress Address structure to use for return message.
   * @param srcHdr Message header to use as reference for return messages header.
   * @param rspnInteractionStage Interaction stage to use on the response.
   * @param error Response message error.
   * @return The sent MAL message.
   */
  public MALMessage returnError(final Address msgAddress,
          final MALMessageHeader srcHdr,
          final UOctet rspnInteractionStage,
          final MALStandardError error)
  {
    return initiateReturnError(msgAddress,
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
      MALContextFactoryImpl.LOGGER.log(Level.WARNING, "Error with one way send : {0}", msg.getHeader().getURITo());
      throw ex;
    }

    return msg;
  }

  private void initiateMultiOnewayInteraction(final MALEndpoint ep, final List<MALMessage> msgs)
          throws MALInteractionException, MALException
  {
    try
    {
      for (int i = 0; i < msgs.size(); i++)
      {
        MALMessage msg = msgs.get(i);
        msg = securityManager.check(msg);
        msgs.set(i, msg);
      }

      ep.sendMessages(msgs.toArray(new MALMessage[msgs.size()]));
    }
    catch (IllegalArgumentException ex)
    {
      throw new MALException("ERROR: Error with one way send : IllegalArgumentException : ", ex);
    }
    catch (MALException ex)
    {
      MALContextFactoryImpl.LOGGER.log(Level.WARNING, "Error with multi one way send : {0}", ep.getURI());
      throw ex;
    }
  }

  private Long initiateSynchronousInteraction(final MessageDetails details,
          final MALOperation op,
          final UOctet syncStage,
          final MALPublishInteractionListener listener,
          final Object... msgBody) throws MALInteractionException, MALException
  {
    final Long transId = icmap.createTransaction(true, listener);

    initiateSynchronousInteraction(transId, details, createMessage(details, op, transId, syncStage, msgBody));

    return transId;
  }

  private MALMessageBody initiateSynchronousInteraction(final Long transId,
          final MessageDetails details,
          MALMessage msg) throws MALInteractionException, MALException
  {
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
      MALContextFactoryImpl.LOGGER.log(Level.WARNING, "Error with consumer : {0}", msg.getHeader().getURITo());
      throw ex;
    }
  }

  private MALMessage initiateAsynchronousInteraction(final MessageDetails details, MALMessage msg)
          throws MALInteractionException, MALException
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
      MALContextFactoryImpl.LOGGER.log(Level.WARNING, "Error with consumer : {0}", msg.getHeader().getURITo());
      throw ex;
    }

    return msg;
  }

  private MALMessage initiateReturnError(final Address msgAddress,
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
              srcHdr.getAreaVersion(),
              true,
              new HashMap(),
              error.getErrorNumber(), error.getExtraInformation());

      msgAddress.endpoint.sendMessage(msg);
    }
    catch (MALException ex)
    {
      MALContextFactoryImpl.LOGGER.log(Level.WARNING,
              "Error returning error to consumer : {0} : {1}", new Object[]
      {
        srcHdr.getURIFrom(), ex
      });
    }
    catch (MALTransmitErrorException ex)
    {
      MALContextFactoryImpl.LOGGER.log(Level.WARNING,
              "Error returning error to consumer : {0} : {1}", new Object[]
      {
        srcHdr.getURIFrom(), ex
      });
    }
    catch (RuntimeException ex)
    {
      MALContextFactoryImpl.LOGGER.log(Level.WARNING,
              "Error returning error to consumer : {0} : {1}", new Object[]
      {
        srcHdr.getURIFrom(), ex
      });
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

  private static MALMessage createMessage(final MessageDetails details,
          final MALOperation op,
          final Long transactionId,
          final UOctet interactionStage,
          final MALEncodedBody body) throws MALException
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
