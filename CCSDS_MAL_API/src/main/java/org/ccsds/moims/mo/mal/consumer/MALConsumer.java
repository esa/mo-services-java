/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java API
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
package org.ccsds.moims.mo.mal.consumer;

import org.ccsds.moims.mo.mal.*;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALEncodedBody;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALTransmitErrorListener;

/**
 * The MALConsumer interface provides a communication context that initiates interaction patterns either in a
 * synchronous or asynchronous way. The resources used by each MALConsumer should be shared as long as it is possible
 * for the implementation to do it. The allowed body element types are: a) MAL element types; b) MALEncodedElement; c)
 * Java types defined by a specific Java mapping extension
 *
 */
public interface MALConsumer
{
  /**
   * Returns the consumers local URI.
   *
   * @return The URI.
   */
  URI getURI();

  /**
   * Returns the consumers local authentication identifier used by the consumer during all the interactions with the service
   * provider
   *
   * @return The authentication identifier.
   */
  Blob getAuthenticationId();

  /**
   * Sets the consumers local authentication identifier used by the consumer during all the interactions with the service
   * provider
   *
   * @param newAuthenticationId The new authentication identifier.
   * @return The previous authentication identifier.
   */
  Blob setAuthenticationId(Blob newAuthenticationId);

  /**
   * The method initiates a synchronous SEND interaction.
   *
   * @param op The operation being initiated.
   * @param body The body elements to transmit in the initiation message
   * @return The MALMessage that was sent.
   * @throws java.lang.IllegalArgumentException If the ‘op’ argument is NULL
   * @throws MALException thrown if a non-MAL error occurs during the initiation message sending or if the MALConsumer
   * is closed.
   * @throws MALInteractionException if a MAL standard error occurs during the initiation message sending
   */
  MALMessage send(MALSendOperation op, Object... body)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

  /**
   * The method initiates a synchronous SEND interaction.
   *
   * @param op The operation being initiated.
   * @param body The already encoded body to transmit in the initiation message
   * @return The MALMessage that was sent.
   * @throws java.lang.IllegalArgumentException If the ‘op’ argument is NULL
   * @throws MALException thrown if a non-MAL error occurs during the initiation message sending or if the MALConsumer
   * is closed.
   * @throws MALInteractionException if a MAL standard error occurs during the initiation message sending
   */
  MALMessage send(MALSendOperation op, MALEncodedBody body)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

  /**
   * The method initiates a synchronous SUBMIT interaction.
   *
   * @param op The operation being initiated.
   * @param body The body elements to transmit in the initiation message
   * @throws java.lang.IllegalArgumentException If the ‘op’ argument is NULL
   * @throws MALException thrown if a non-MAL error occurs during the initiation message sending or if the MALConsumer
   * is closed.
   * @throws MALInteractionException if a MAL standard error occurs during the interaction
   */
  void submit(MALSubmitOperation op, Object... body)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

  /**
   * The method initiates a synchronous SUBMIT interaction.
   *
   * @param op The operation being initiated.
   * @param body The already encoded body to transmit in the initiation message
   * @throws java.lang.IllegalArgumentException If the ‘op’ argument is NULL
   * @throws MALException thrown if a non-MAL error occurs during the initiation message sending or if the MALConsumer
   * is closed.
   * @throws MALInteractionException if a MAL standard error occurs during the interaction
   */
  void submit(MALSubmitOperation op, MALEncodedBody body)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

  /**
   * The method initiates a synchronous REQUEST interaction.
   *
   * @param op The operation being initiated.
   * @param body The body elements to transmit in the initiation message
   * @return The interaction response.
   * @throws java.lang.IllegalArgumentException If the ‘op’ argument is NULL
   * @throws MALException thrown if a non-MAL error occurs during the initiation message sending or if the MALConsumer
   * is closed.
   * @throws MALInteractionException if a MAL standard error occurs during the interaction
   */
  MALMessageBody request(MALRequestOperation op, Object... body)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

  /**
   * The method initiates a synchronous REQUEST interaction.
   *
   * @param op The operation being initiated.
   * @param body The already encoded body to transmit in the initiation message
   * @return The interaction response.
   * @throws java.lang.IllegalArgumentException If the ‘op’ argument is NULL
   * @throws MALException thrown if a non-MAL error occurs during the initiation message sending or if the MALConsumer
   * is closed.
   * @throws MALInteractionException if a MAL standard error occurs during the interaction
   */
  MALMessageBody request(MALRequestOperation op, MALEncodedBody body)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

  /**
   * The method initiates a synchronous INVOKE interaction. The method returns as soon as the ACK message has been
   * received.
   *
   * @param op The operation being initiated.
   * @param listener Listener in charge of receiving the messages RESPONSE and RESPONSE ERROR
   * @param body The body elements to transmit in the initiation message
   * @return The MALMessage that was sent.
   * @throws java.lang.IllegalArgumentException If the ‘op’ argument is NULL
   * @throws MALException thrown if a non-MAL error occurs during the initiation message sending or if the MALConsumer
   * is closed.
   * @throws MALInteractionException if a MAL standard error occurs during the interaction
   */
  MALMessageBody invoke(MALInvokeOperation op, MALInteractionListener listener, Object... body)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

  /**
   * The method initiates a synchronous INVOKE interaction. The method returns as soon as the ACK message has been
   * received.
   *
   * @param op The operation being initiated.
   * @param listener Listener in charge of receiving the messages RESPONSE and RESPONSE ERROR
   * @param body The already encoded body to transmit in the initiation message
   * @return The MALMessage that was sent.
   * @throws java.lang.IllegalArgumentException If the ‘op’ argument is NULL
   * @throws MALException thrown if a non-MAL error occurs during the initiation message sending or if the MALConsumer
   * is closed.
   * @throws MALInteractionException if a MAL standard error occurs during the interaction
   */
  MALMessageBody invoke(MALInvokeOperation op, MALInteractionListener listener, MALEncodedBody body)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

  /**
   * The method initiates a synchronous PROGRESS interaction. The method returns as soon as the ACK message has been
   * received.
   *
   * @param op The operation being initiated.
   * @param listener Listener in charge of receiving the messages UPDATE, RESPONSE and RESPONSE ERROR
   * @param body The body elements to transmit in the initiation message
   * @return The MALMessage that was sent.
   * @throws java.lang.IllegalArgumentException If the ‘op’ argument is NULL
   * @throws MALException thrown if a non-MAL error occurs during the initiation message sending or if the MALConsumer
   * is closed.
   * @throws MALInteractionException if a MAL standard error occurs during the interaction
   */
  MALMessageBody progress(MALProgressOperation op, MALInteractionListener listener, Object... body)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

  /**
   * The method initiates a synchronous PROGRESS interaction. The method returns as soon as the ACK message has been
   * received.
   *
   * @param op The operation being initiated.
   * @param listener Listener in charge of receiving the messages UPDATE, RESPONSE and RESPONSE ERROR
   * @param body The already encoded body to transmit in the initiation message
   * @return The MALMessage that was sent.
   * @throws java.lang.IllegalArgumentException If the ‘op’ argument is NULL
   * @throws MALException thrown if a non-MAL error occurs during the initiation message sending or if the MALConsumer
   * is closed.
   * @throws MALInteractionException if a MAL standard error occurs during the interaction
   */
  MALMessageBody progress(MALProgressOperation op, MALInteractionListener listener, MALEncodedBody body)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

  /**
   * The method initiates a synchronous PubSub REGISTER interaction. The method returns as soon as the ACK message has
   * been received.
   *
   * @param op The operation being initiated.
   * @param subscription Subscription to be registered
   * @param listener Listener in charge of receiving the messages NOTIFY and NOTIFY ERROR.
   * @throws java.lang.IllegalArgumentException If the arguments are NULL
   * @throws MALException thrown if a non-MAL error occurs during the initiation message sending or if the MALConsumer
   * is closed.
   * @throws MALInteractionException if a MAL standard error occurs during the registration
   */
  void register(MALPubSubOperation op, Subscription subscription, MALInteractionListener listener)
          throws java.lang.IllegalArgumentException, MALException, MALInteractionException;

  /**
   * The method initiates a synchronous PubSub DEREGISTER interaction. The method returns as soon as the ACK message has
   * been received.
   *
   * @param op The operation being initiated.
   * @param subscriptionIdList Subscription to be deregistered
   * @throws java.lang.IllegalArgumentException If the arguments are NULL
   * @throws MALException thrown if a non-MAL error occurs during the initiation message sending or if the MALConsumer
   * is closed.
   * @throws MALInteractionException if a MAL standard error occurs during the deregistration
   */
  void deregister(MALPubSubOperation op, IdentifierList subscriptionIdList)
          throws java.lang.IllegalArgumentException, MALException, MALInteractionException;

  /**
   * The method initiates an asynchronous SUBMIT interaction.
   *
   * @param op The operation being initiated.
   * @param listener Listener in charge of receiving the returned messages
   * @param body The body elements to transmit in the initiation message
   * @return the initial MALMessage that was sent.
   * @throws java.lang.IllegalArgumentException If the ‘op’ or 'listener' arguments are NULL
   * @throws MALException thrown if a non-MAL error occurs during the initiation message sending or if the MALConsumer
   * is closed.
   * @throws MALInteractionException if a MAL standard error occurs during the interaction
   */
  MALMessage asyncSubmit(MALSubmitOperation op, MALInteractionListener listener, Object... body)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

  /**
   * The method initiates an asynchronous SUBMIT interaction.
   *
   * @param op The operation being initiated.
   * @param listener Listener in charge of receiving the returned messages
   * @param body The already encoded body to transmit in the initiation message
   * @return the initial MALMessage that was sent.
   * @throws java.lang.IllegalArgumentException If the ‘op’ or 'listener' arguments are NULL
   * @throws MALException thrown if a non-MAL error occurs during the initiation message sending or if the MALConsumer
   * is closed.
   * @throws MALInteractionException if a MAL standard error occurs during the interaction
   */
  MALMessage asyncSubmit(MALSubmitOperation op, MALInteractionListener listener, MALEncodedBody body)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

  /**
   * The method initiates an asynchronous REQUEST interaction.
   *
   * @param op The operation being initiated.
   * @param listener Listener in charge of receiving the returned messages
   * @param body The body elements to transmit in the initiation message
   * @return the initial MALMessage that was sent.
   * @throws java.lang.IllegalArgumentException If the ‘op’ or 'listener' arguments are NULL
   * @throws MALException thrown if a non-MAL error occurs during the initiation message sending or if the MALConsumer
   * is closed.
   * @throws MALInteractionException if a MAL standard error occurs during the interaction
   */
  MALMessage asyncRequest(MALRequestOperation op, MALInteractionListener listener, Object... body)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

  /**
   * The method initiates an asynchronous REQUEST interaction.
   *
   * @param op The operation being initiated.
   * @param listener Listener in charge of receiving the returned messages
   * @param body The already encoded body to transmit in the initiation message
   * @return the initial MALMessage that was sent.
   * @throws java.lang.IllegalArgumentException If the ‘op’ or 'listener' arguments are NULL
   * @throws MALException thrown if a non-MAL error occurs during the initiation message sending or if the MALConsumer
   * is closed.
   * @throws MALInteractionException if a MAL standard error occurs during the interaction
   */
  MALMessage asyncRequest(MALRequestOperation op, MALInteractionListener listener, MALEncodedBody body)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

  /**
   * The method initiates an asynchronous INVOKE interaction.
   *
   * @param op The operation being initiated.
   * @param listener Listener in charge of receiving the returned messages
   * @param body The body elements to transmit in the initiation message
   * @return the initial MALMessage that was sent.
   * @throws java.lang.IllegalArgumentException If the ‘op’ or 'listener' arguments are NULL
   * @throws MALException thrown if a non-MAL error occurs during the initiation message sending or if the MALConsumer
   * is closed.
   * @throws MALInteractionException if a MAL standard error occurs during the interaction
   */
  MALMessage asyncInvoke(MALInvokeOperation op, MALInteractionListener listener, Object... body)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

  /**
   * The method initiates an asynchronous INVOKE interaction.
   *
   * @param op The operation being initiated.
   * @param listener Listener in charge of receiving the returned messages
   * @param body The already encoded body to transmit in the initiation message
   * @return the initial MALMessage that was sent.
   * @throws java.lang.IllegalArgumentException If the ‘op’ or 'listener' arguments are NULL
   * @throws MALException thrown if a non-MAL error occurs during the initiation message sending or if the MALConsumer
   * is closed.
   * @throws MALInteractionException if a MAL standard error occurs during the interaction
   */
  MALMessage asyncInvoke(MALInvokeOperation op, MALInteractionListener listener, MALEncodedBody body)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

  /**
   * The method initiates an asynchronous PROGRESS interaction.
   *
   * @param op The operation being initiated.
   * @param listener Listener in charge of receiving the returned messages
   * @param body The body elements to transmit in the initiation message
   * @return the initial MALMessage that was sent.
   * @throws java.lang.IllegalArgumentException If the ‘op’ or 'listener' arguments are NULL
   * @throws MALException thrown if a non-MAL error occurs during the initiation message sending or if the MALConsumer
   * is closed.
   * @throws MALInteractionException if a MAL standard error occurs during the interaction
   */
  MALMessage asyncProgress(MALProgressOperation op, MALInteractionListener listener, Object... body)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

  /**
   * The method initiates an asynchronous PROGRESS interaction.
   *
   * @param op The operation being initiated.
   * @param listener Listener in charge of receiving the returned messages
   * @param body The already encoded body to transmit in the initiation message
   * @return the initial MALMessage that was sent.
   * @throws java.lang.IllegalArgumentException If the ‘op’ or 'listener' arguments are NULL
   * @throws MALException thrown if a non-MAL error occurs during the initiation message sending or if the MALConsumer
   * is closed.
   * @throws MALInteractionException if a MAL standard error occurs during the interaction
   */
  MALMessage asyncProgress(MALProgressOperation op, MALInteractionListener listener, MALEncodedBody body)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

  /**
   * The method initiates an asynchronous PubSub REGISTER interaction.
   *
   * @param op The operation being initiated.
   * @param subscription The subscription
   * @param listener Listener in charge of receiving the returned messages
   * @return the initial MALMessage that was sent.
   * @throws java.lang.IllegalArgumentException If the ‘op’ or 'listener' arguments are NULL
   * @throws MALException thrown if a non-MAL error occurs during the initiation message sending or if the MALConsumer
   * is closed.
   * @throws MALInteractionException if a MAL standard error occurs during the interaction
   */
  MALMessage asyncRegister(MALPubSubOperation op, Subscription subscription, MALInteractionListener listener)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

  /**
   * The method initiates an asynchronous PubSub DEREGISTER interaction.
   *
   * @param op The operation being initiated.
   * @param subscriptionIdList The list of subscriptions to deregister
   * @param listener Listener in charge of receiving the returned messages
   * @return the initial MALMessage that was sent.
   * @throws java.lang.IllegalArgumentException If the ‘op’ or 'listener' arguments are NULL
   * @throws MALException thrown if a non-MAL error occurs during the initiation message sending or if the MALConsumer
   * is closed.
   * @throws MALInteractionException if a MAL standard error occurs during the interaction
   */
  MALMessage asyncDeregister(MALPubSubOperation op, IdentifierList subscriptionIdList, MALInteractionListener listener)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

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
  void continueInteraction(
          MALOperation op,
          UOctet lastInteractionStage,
          Time initiationTimestamp,
          Long transactionId,
          MALInteractionListener listener)
          throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

  /**
   * Sets the listener used for reporting transmission errors when no other reporting mechanism is possible, for example
   * on a SEND pattern.
   *
   * @param listener The listener to register.
   * @throws MALException If closed.
   */
  void setTransmitErrorListener(MALTransmitErrorListener listener) throws MALException;

  /**
   * Returns the current fall back transmission error listener.
   * @return The current listener.
   * @throws MALException If closed.
   */
  MALTransmitErrorListener getTransmitErrorListener() throws MALException;

  /**
   * The method terminates all pending interaction patterns initiated by the MALConsumer.
   *
   * @throws MALException If an internal error occurs
   */
  void close() throws MALException;
}
