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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.*;
import org.ccsds.moims.mo.mal.accesscontrol.MALAccessControl;
import org.ccsds.moims.mo.mal.accesscontrol.MALCheckErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionListener;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.*;

/**
 * This class is the central point for sending messages out.
 */
public class MALSender {

    private final MALAccessControl securityManager;
    private final InteractionConsumerMap icmap;
    private final InteractionPubSubMap ipsmap;

    /**
     * Constructor
     *
     * @param securityManager   Security Manager
     * @param imap              Interaction Consumers
     * @param psmap             Interaction PubSub Map
     */
    MALSender(final MALAccessControl securityManager,
            final InteractionConsumerMap imap, final InteractionPubSubMap psmap) {
        this.securityManager = securityManager;
        this.icmap = imap;
        this.ipsmap = psmap;
    }

    public MALAccessControl getSecurityManager() {
        return securityManager;
    }

    /**
     * Synchronous register send.
     *
     * @param details Message details structure.
     * @param op The operation.
     * @param subscription Consumer subscription.
     * @param listener Update callback interface.
     * @throws MALInteractionException if there is a problem during the
     * interaction.
     * @throws MALException on error.
     */
    public void register(final MessageTarget details, final MALPubSubOperation op,
            final Subscription subscription, final MALInteractionListener listener)
            throws MALInteractionException, MALException {
        String uri = details.getEndpoint().getURI().getValue();
        ipsmap.registerNotifyListener(uri, subscription, listener);
        initiateSynchronousInteraction(details,
                op,
                MALPubSubOperation.REGISTER_STAGE,
                (MALPublishInteractionListener) null,
                subscription);
    }

    /**
     * Synchronous publish register send.
     *
     * @param from The From field.
     * @param details Message details structure.
     * @param op The operation.
     * @param keyNames List of key names to be published.
     * @param keyTypes List of key types to be published.
     * @param listener Error callback interface.
     * @return Publish transaction identifier.
     * @throws MALInteractionException if there is a problem during the
     * interaction.
     * @throws MALException on error.
     */
    public Long publishRegister(final String from, final MessageTarget details, final MALPubSubOperation op,
            final IdentifierList keyNames, final AttributeTypeList keyTypes,
            final MALPublishInteractionListener listener) throws MALInteractionException, MALException {
        ipsmap.registerPublishListener(from, listener);
        IdentifierList publishedKeyNames = (keyNames != null) ? keyNames : new IdentifierList();
        AttributeTypeList publishedKeyValues = (keyTypes != null) ? keyTypes : new AttributeTypeList();

        return initiateSynchronousInteraction(details,
                op,
                MALPubSubOperation.PUBLISH_REGISTER_STAGE,
                (MALPublishInteractionListener) null,
                publishedKeyNames,
                publishedKeyValues);
    }

    /**
     * Synchronous publish deregister send.
     *
     * @param details Message details structure.
     * @param op The operation.
     * @throws MALInteractionException if there is a problem during the
     * interaction.
     * @throws MALException on error.
     */
    public void publishDeregister(final MessageTarget details,
            final MALPubSubOperation op) throws MALInteractionException, MALException {
        initiateSynchronousInteraction(details,
                op,
                MALPubSubOperation.PUBLISH_DEREGISTER_STAGE,
                (MALPublishInteractionListener) null,
                (Object[]) null);
        ipsmap.getPublishListenerAndRemove(details.getEndpoint().getURI());
    }

    /**
     * Synchronous deregister send.
     *
     * @param details Message details structure.
     * @param op The operation.
     * @param unsubscription consumer unsubscription.
     * @throws MALInteractionException if there is a problem during the
     * interaction.
     * @throws MALException on error.
     */
    public void deregister(final MessageTarget details,
            final MALPubSubOperation op,
            final IdentifierList unsubscription)
            throws MALInteractionException, MALException {
        String uri = details.getEndpoint().getURI().getValue();
        initiateSynchronousInteraction(details,
                op,
                MALPubSubOperation.DEREGISTER_STAGE,
                (MALPublishInteractionListener) null,
                unsubscription);
        ipsmap.deregisterNotifyListener(uri, unsubscription);
    }

    /**
     * Asynchronous publish register send.
     *
     * @param from From field.
     * @param details Message details structure.
     * @param op The operation.
     * @param keyNames List of keys names that can be published.
     * @param keyTypes List of keys types that can be published.
     * @param listener Response callback interface.
     * @return The sent MAL message.
     * @throws MALInteractionException if there is a problem during the
     * interaction.
     * @throws MALException on error.
     */
    public MALMessage publishRegisterAsync(final String from,
            final MessageTarget details,
            final MALPubSubOperation op,
            final IdentifierList keyNames,
            final AttributeTypeList keyTypes,
            final MALPublishInteractionListener listener)
            throws MALInteractionException, MALException {
        ipsmap.registerPublishListener(from, listener);
        IdentifierList publishedKeyNames = (keyNames != null) ? keyNames : new IdentifierList();
        AttributeTypeList publishedKeyValues = (keyTypes != null) ? keyTypes : new AttributeTypeList();
        final Long transId = icmap.createPubSubTransaction(false, listener);
        return initiateAsynchronousInteraction(details.getEndpoint(),
                details.createMessage(op,
                        transId,
                        MALPubSubOperation.PUBLISH_REGISTER_STAGE,
                        publishedKeyNames,
                        publishedKeyValues)
        );
    }

    /**
     * Asynchronous register send.
     *
     * @param details Message details structure.
     * @param op The operation.
     * @param subscription Consumer subscription.
     * @param listener Response callback interface.
     * @return The sent MAL message.
     * @throws MALInteractionException if there is a problem during the
     * interaction.
     * @throws MALException on error.
     */
    public MALMessage registerAsync(final MessageTarget details,
            final MALPubSubOperation op,
            final Subscription subscription,
            final MALInteractionListener listener) throws MALInteractionException, MALException {
        String uri = details.getEndpoint().getURI().getValue();
        ipsmap.registerNotifyListener(uri, subscription, listener);
        return asynchronousInteraction(details, op, MALPubSubOperation.REGISTER_STAGE, listener, subscription);
    }

    /**
     * Asynchronous publish deregister send.
     *
     * @param details Message details structure.
     * @param op The operation.
     * @param listener Response callback interface.
     * @return The sent MAL message.
     * @throws MALInteractionException if there is a problem during the
     * interaction.
     * @throws MALException on error.
     */
    public MALMessage publishDeregisterAsync(final MessageTarget details,
            final MALPubSubOperation op, final MALPublishInteractionListener listener)
            throws MALInteractionException, MALException {
        ipsmap.getPublishListenerAndRemove(details.getEndpoint().getURI());
        final Long transId = icmap.createPubSubTransaction(false, listener);
        return initiateAsynchronousInteraction(details.getEndpoint(),
                details.createMessage(op, transId,
                        MALPubSubOperation.PUBLISH_DEREGISTER_STAGE, (Object[]) null));
    }

    /**
     * Asynchronous deregister send.
     *
     * @param details Message details structure.
     * @param op The operation.
     * @param unsubscription consumer unsubscription.
     * @param listener Response callback interface.
     * @return The sent MAL message.
     * @throws MALInteractionException if there is a problem during the
     * interaction.
     * @throws MALException on error.
     */
    public MALMessage deregisterAsync(final MessageTarget details,
            final MALPubSubOperation op, final IdentifierList unsubscription,
            final MALInteractionListener listener) throws MALInteractionException, MALException {
        final MALMessage msg = asynchronousInteraction(details,
                op,
                MALPubSubOperation.DEREGISTER_STAGE,
                listener,
                unsubscription);
        String uri = details.getEndpoint().getURI().getValue();
        ipsmap.deregisterNotifyListener(uri, unsubscription);

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
     * @throws MALInteractionException if there is a problem during the
     * interaction.
     * @throws MALException on Error.
     */
    public MALMessage onewayInteraction(final MessageTarget details,
            Long transId, final MALOperation op, final UOctet stage,
            final Object... msgBody) throws MALInteractionException, MALException {
        if (transId == null) {
            transId = icmap.createTransaction(op.getInteractionType().getOrdinal(), true, null);
        }

        MALMessage msg = details.createMessage(op, transId, stage, msgBody);
        return initiateOnewayInteraction(details.getEndpoint(), msg);
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
     * @throws MALInteractionException if there is a problem during the
     * interaction.
     * @throws MALException on Error.
     */
    public MALMessage onewayInteraction(final MessageTarget details,
            Long transId, final MALOperation op, final UOctet stage,
            final MALEncodedBody msgBody) throws MALInteractionException, MALException {
        if (transId == null) {
            transId = icmap.createTransaction(op.getInteractionType().getOrdinal(), true, null);
        }

        MALMessage msg = details.createMessage(op, transId, stage, msgBody);
        return initiateOnewayInteraction(details.getEndpoint(), msg);
    }

    /**
     * Sends a set of oneway PUBLISH messages.
     *
     * @param ep The endpoint to use to send the messages.
     * @param msgs Set of messages to send.
     * @throws MALInteractionException if there is a problem during the
     * interaction.
     * @throws MALException on internal error.
     */
    public void onewayMultiPublish(final MALEndpoint ep, final List<MALMessage> msgs)
            throws MALInteractionException, MALException {
        initiateMultiOnewayInteraction(ep, msgs);
    }

    /**
     * Performs a two way interaction, sends the message and then waits for the
     * specified stage before returning.
     *
     * @param details Message details structure.
     * @param op The operation.
     * @param syncStage The interaction stage to wait for before returning.
     * @param listener Interaction listener to use for the reception of other
     * stages.
     * @param msgBody The message body.
     * @return The returned message body.
     * @throws MALInteractionException if there is a problem during the
     * interaction.
     * @throws MALException on Error.
     */
    public MALMessageBody synchronousInteraction(final MessageTarget details,
            final MALOperation op, final UOctet syncStage,
            final MALInteractionListener listener, final Object... msgBody)
            throws MALInteractionException, MALException {
        final Long transId = icmap.createTransaction(op.getInteractionType().getOrdinal(), true, listener);
        MALMessage msg = details.createMessage(op, transId, syncStage, msgBody);
        return initiateSynchronousInteraction(transId, details.getEndpoint(), msg);
    }

    /**
     * Performs a two way interaction, sends the message and then waits for the
     * specified stage before returning.
     *
     * @param details Message details structure.
     * @param op The operation.
     * @param syncStage The interaction stage to wait for before returning.
     * @param listener Interaction listener to use for the reception of other
     * stages.
     * @param msgBody The already encoded message body.
     * @return The returned message body.
     * @throws MALInteractionException if there is a problem during the
     * interaction.
     * @throws MALException on Error.
     */
    public MALMessageBody synchronousInteraction(final MessageTarget details,
            final MALOperation op, final UOctet syncStage,
            final MALInteractionListener listener, final MALEncodedBody msgBody)
            throws MALInteractionException, MALException {
        final Long transId = icmap.createTransaction(op.getInteractionType().getOrdinal(), true, listener);
        MALMessage msg = details.createMessage(op, transId, syncStage, msgBody);
        return initiateSynchronousInteraction(transId, details.getEndpoint(), msg);
    }

    /**
     * Performs a two way interaction, sends the message.
     *
     * @param details Message details structure.
     * @param op The operation.
     * @param initialStage The initial interaction stage.
     * @param listener Interaction listener to use for the reception of other
     * stages.
     * @param msgBody The message body.
     * @return The sent MAL message.
     * @throws MALInteractionException if there is a problem during the
     * interaction.
     * @throws MALException on Error.
     */
    public MALMessage asynchronousInteraction(final MessageTarget details,
            final MALOperation op, final UOctet initialStage,
            final MALInteractionListener listener, final Object... msgBody)
            throws MALInteractionException, MALException {
        final Long transId = icmap.createTransaction(op.getInteractionType().getOrdinal(), false, listener);
        MALMessage msg = details.createMessage(op, transId, initialStage, msgBody);
        return initiateAsynchronousInteraction(details.getEndpoint(), msg);
    }

    /**
     * Performs a two way interaction, sends the message.
     *
     * @param details Message details structure.
     * @param op The operation.
     * @param initialStage The initial interaction stage.
     * @param listener Interaction listener to use for the reception of other
     * stages.
     * @param msgBody The already encoded message body.
     * @return The sent MAL message.
     * @throws MALInteractionException if there is a problem during the
     * interaction.
     * @throws MALException on Error.
     */
    public MALMessage asynchronousInteraction(final MessageTarget details,
            final MALOperation op, final UOctet initialStage,
            final MALInteractionListener listener, final MALEncodedBody msgBody)
            throws MALInteractionException, MALException {
        final Long transId = icmap.createTransaction(op.getInteractionType().getOrdinal(), false, listener);
        MALMessage msg = details.createMessage(op, transId, initialStage, msgBody);
        return initiateAsynchronousInteraction(details.getEndpoint(), msg);
    }

    /**
     * The method continues an interaction that has been interrupted.
     *
     * @param op The operation to continue
     * @param lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp Timestamp of the interaction initiation
     * message
     * @param transactionId Transaction identifier of the interaction to
     * continue
     * @param listener Listener in charge of receiving the messages from the
     * service provider
     * @throws java.lang.IllegalArgumentException If the parameters ‘op’ or
     * ‘lastInteractionStage’ or ‘initiationTimestamp’ or ‘transactionId ‘ or
     * ‘listener’ are NULL
     * @throws MALException thrown if a non-MAL error occurs during the
     * initiation message sending or if the MALConsumer is closed.
     * @throws MALInteractionException if a MAL standard error occurs during the
     * interaction
     */
    public void continueInteraction(final MALOperation op,
            final UOctet lastInteractionStage,
            final Time initiationTimestamp,
            final Long transactionId,
            final MALInteractionListener listener)
            throws IllegalArgumentException, MALException, MALInteractionException {
        if (op == null) {
            throw new IllegalArgumentException("op argument of continueInteraction must not be null");
        }
        if (lastInteractionStage == null) {
            throw new IllegalArgumentException("lastInteractionStage argument of continueInteraction must not be null");
        }
        if (initiationTimestamp == null) {
            throw new IllegalArgumentException("initiationTimestamp argument of continueInteraction must not be null");
        }
        if (transactionId == null) {
            throw new IllegalArgumentException("transactionId argument of continueInteraction must not be null");
        }
        if (listener == null) {
            throw new IllegalArgumentException("listener argument of continueInteraction must not be null");
        }

        icmap.continueTransaction(op.getInteractionType().getOrdinal(), lastInteractionStage, transactionId, listener);
    }

    /**
     * Send return response method.
     *
     * @param msgAddress Address structure to use for return message.
     * @param srcHdr Message header to use as reference for return messages
     * header.
     * @param rspnInteractionStage Interaction stage to use on the response.
     * @param rspn Response message body.
     * @param qosProperties The QoS properties.
     * @param operation The operation.
     * @return The sent MAL message.
     */
    public MALMessage returnResponse(final Address msgAddress,
            final MALMessageHeader srcHdr,
            final UOctet rspnInteractionStage,
            final MALOperation operation,
            final Map qosProperties,
            final Object... rspn) {
        MALMessage msg = null;

        try {
            MALEndpoint endpoint = msgAddress.getEndpoint();
            msg = endpoint.createMessage(
                    msgAddress.getAuthenticationId(),
                    srcHdr.getFromURI(),
                    Time.now(),
                    operation.getInteractionType(),
                    rspnInteractionStage,
                    srcHdr.getTransactionId(),
                    srcHdr.getServiceArea(),
                    srcHdr.getService(),
                    srcHdr.getOperation(),
                    srcHdr.getServiceVersion(),
                    false,
                    srcHdr.getSupplements(),
                    qosProperties,
                    rspn);

            msg = securityManager.check(msg);
            endpoint.sendMessage(msg);
        } catch (MALException ex) {
            MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                    "(1) Error returning response to consumer : " + srcHdr.getFrom() + " : ", ex);
        } catch (MALTransmitErrorException ex) {
            MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                    "(2) Error returning response to consumer : " + srcHdr.getFrom() + " : ", ex);
        } catch (RuntimeException ex) {
            MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                    "(3) Error returning response to consumer : " + srcHdr.getFrom() + " : ", ex);
        } catch (MALCheckErrorException ex) {
            MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                    "(4) Error returning response to consumer : " + srcHdr.getFrom() + " : ", ex);
        }

        return msg;
    }

    /**
     * Send return error method.
     *
     * @param msgAddress Address structure to use for return message.
     * @param srcHdr Message header to use as reference for return messages
     * header.
     * @param rspnInteractionStage Interaction stage to use on the response.
     * @param error Response message error.
     * @return The sent MAL message.
     */
    public MALMessage returnError(final Address msgAddress,
            final MALMessageHeader srcHdr,
            final UOctet rspnInteractionStage,
            final MALException error) {
        Union wrap = new Union(error.getLocalizedMessage());
        return initiateReturnError(msgAddress,
                srcHdr,
                rspnInteractionStage,
                new MOErrorException(MALHelper.INTERNAL_ERROR_NUMBER, wrap));
    }

    /**
     * Send return error method.
     *
     * @param msgAddress Address structure to use for return message.
     * @param srcHdr Message header to use as reference for return messages
     * header.
     * @param rspnInteractionStage Interaction stage to use on the response.
     * @param error Response message error.
     * @return The sent MAL message.
     */
    public MALMessage returnError(final Address msgAddress, final MALMessageHeader srcHdr,
            final UOctet rspnInteractionStage, final MOErrorException error) {
        return initiateReturnError(msgAddress, srcHdr, rspnInteractionStage, error);
    }

    private MALMessage initiateOnewayInteraction(final MALEndpoint endpoint,
            MALMessage msg) throws MALInteractionException, MALException {
        try {
            msg = securityManager.check(msg);
            endpoint.sendMessage(msg);
        } catch (IllegalArgumentException ex) {
            throw new MALException("ERROR: Error with one way send : IllegalArgumentException : ", ex);
        } catch (MALException ex) {
            MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                    "Error with one way send : {0}", msg.getHeader().getTo());
            throw ex;
        }

        return msg;
    }

    private void initiateMultiOnewayInteraction(final MALEndpoint ep,
            final List<MALMessage> msgs) throws MALInteractionException, MALException {
        try {
            for (int i = 0; i < msgs.size(); i++) {
                MALMessage msg = msgs.get(i);
                msg = securityManager.check(msg);
                msgs.set(i, msg);
            }

            ep.sendMessages(msgs.toArray(new MALMessage[msgs.size()]));
        } catch (IllegalArgumentException ex) {
            throw new MALException("ERROR: Error with one way send : IllegalArgumentException : ", ex);
        } catch (MALException ex) {
            MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                    "Error with multi one way send : {0}", ep.getURI());
            throw ex;
        }
    }

    private Long initiateSynchronousInteraction(final MessageTarget details,
            final MALOperation op, final UOctet syncStage,
            final MALPublishInteractionListener listener,
            final Object... msgBody) throws MALInteractionException, MALException {
        final Long transId = icmap.createPubSubTransaction(true, listener);
        MALMessage msg = details.createMessage(op, transId, syncStage, msgBody);
        initiateSynchronousInteraction(transId, details.getEndpoint(), msg);
        return transId;
    }

    private MALMessageBody initiateSynchronousInteraction(final Long transId,
            final MALEndpoint endpoint, MALMessage msg) throws MALInteractionException, MALException {
        try {
            msg = securityManager.check(msg);
            endpoint.sendMessage(msg);
            final MALMessage rtn = icmap.waitForResponse(transId);

            if (rtn == null) {
                throw new MALException("Return message was null!");
            }

            // handle possible return error
            if (rtn.getHeader().getIsErrorMessage()) {
                if (rtn.getBody() instanceof MALErrorBody) {
                    MOErrorException error = ((MALErrorBody) rtn.getBody()).getError();
                    MALContextFactoryImpl.LOGGER.log(Level.SEVERE,
                            "Something went wrong!", error);
                    throw new MALInteractionException(error);
                }

                throw new MALInteractionException(new MOErrorException(
                        MALHelper.BAD_ENCODING_ERROR_NUMBER,
                        new Union("Return message marked as error but did not contain a MALException")));
            }

            return rtn.getBody(); // All Good!
        } catch (IllegalArgumentException ex) {
            throw new MALException("IllegalArgumentException", ex);
        } catch (MALException ex) {
            MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                    "Error with consumer : {0}", msg.getHeader().getTo());
            throw ex;
        }
    }

    private MALMessage initiateAsynchronousInteraction(final MALEndpoint endpoint,
            MALMessage msg) throws MALInteractionException, MALException {
        try {
            msg = securityManager.check(msg);
            endpoint.sendMessage(msg);
        } catch (IllegalArgumentException ex) {
            throw new MALException("IllegalArgumentException", ex);
        } catch (MALException ex) {
            MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                    "Error with consumer : {0}", msg.getHeader().getTo());
            throw ex;
        }

        return msg;
    }

    private MALMessage initiateReturnError(final Address msgAddress,
            final MALMessageHeader srcHdr, final UOctet rspnInteractionStage,
            final MOErrorException error) {
        MALMessage msg = null;
        URI destination = srcHdr.getFromURI();

        try {
            MALEndpoint endpoint = msgAddress.getEndpoint();
            msg = endpoint.createMessage(
                    msgAddress.getAuthenticationId(),
                    destination,
                    Time.now(),
                    srcHdr.getInteractionType(),
                    rspnInteractionStage,
                    srcHdr.getTransactionId(),
                    srcHdr.getServiceArea(),
                    srcHdr.getService(),
                    srcHdr.getOperation(),
                    srcHdr.getServiceVersion(),
                    true,
                    srcHdr.getSupplements(),
                    new HashMap(),
                    error.getErrorNumber(),
                    error.getExtraInformation());

            msg = securityManager.check(msg);
            endpoint.sendMessage(msg);
        } catch (MALException ex) {
            MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                    "(1) Error occurred while trying to return error to consumer: "
                    + destination, ex);
        } catch (MALTransmitErrorException ex) {
            MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                    "(2) Error occurred while trying to return error to consumer: "
                    + destination, ex);
        } catch (RuntimeException ex) {
            MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                    "(3) Error occurred while trying to return error to consumer: "
                    + destination, ex);
        } catch (MALCheckErrorException ex) {
            MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                    "(4) Error occurred while trying to return error to consumer: "
                    + destination, ex);
        }

        return msg;
    }
}
