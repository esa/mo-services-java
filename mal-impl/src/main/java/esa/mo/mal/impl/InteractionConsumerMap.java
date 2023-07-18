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

import esa.mo.mal.impl.state.BaseOperationHandler;
import esa.mo.mal.impl.state.RequestOperationHandler;
import esa.mo.mal.impl.state.InvokeOperationHandler;
import esa.mo.mal.impl.state.MessageHandlerDetails;
import esa.mo.mal.impl.state.OperationResponseHolder;
import esa.mo.mal.impl.state.SubmitOperationHandler;
import esa.mo.mal.impl.state.PubSubOperationHandler;
import esa.mo.mal.impl.state.ProgressOperationHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.*;
import org.ccsds.moims.mo.mal.consumer.MALInteractionListener;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.*;

/**
 * The interaction map is responsible for maintaining the state of consumer
 * initiated interactions for a MAL instance. New transactions are added using
 * the creatTransaction methods. Synchronous consumer interactions are handled
 * by calling waitForResponse, and any message received is 'handled' using the
 * handleStage method.
 *
 * When a new interaction is created, an interaction handler class is created
 * which is responsible for ensuring the correct stages are received in the
 * correct order.
 */
public class InteractionConsumerMap {

    private final Map<Long, BaseOperationHandler> transactions = new HashMap<>();

    private final Map<Long, OperationResponseHolder> syncOpResponseMap = new HashMap<>();

    // This object will be shared across. It is thread-safe, so can be static!
    private final static InteractionTimeout INTERACTION_TIMEOUT = new InteractionTimeout();

    public Long createTransaction(final int interactionType,
            final boolean syncOperation,
            final MALInteractionListener listener) throws MALInteractionException {
        synchronized (transactions) {
            final Long oTransId = TransactionIdCounter.nextTransactionId();

            BaseOperationHandler handler = null;
            OperationResponseHolder responseHandler = new OperationResponseHolder(listener);

            switch (interactionType) {
                case InteractionType._SEND_INDEX:
                    // do nothing as no handler is required for SEND interaction
                    break;
                case InteractionType._SUBMIT_INDEX:
                    handler = new SubmitOperationHandler(syncOperation, responseHandler);
                    break;
                case InteractionType._REQUEST_INDEX:
                    handler = new RequestOperationHandler(syncOperation, responseHandler);
                    break;
                case InteractionType._INVOKE_INDEX:
                    handler = new InvokeOperationHandler(syncOperation, responseHandler);
                    break;
                case InteractionType._PROGRESS_INDEX:
                    handler = new ProgressOperationHandler(syncOperation, responseHandler);
                    break;
                case InteractionType._PUBSUB_INDEX:
                    handler = new PubSubOperationHandler(syncOperation, responseHandler);
                    break;
                default:
                    throw new MALInteractionException(
                            new MOErrorException(
                                    MALHelper.INTERNAL_ERROR_NUMBER,
                                    new Union("Pattern not supported")
                            )
                    );
            }

            if (handler != null) {
                transactions.put(oTransId, handler);
                INTERACTION_TIMEOUT.insertInQueue(handler);

                if (syncOperation) {
                    synchronized (syncOpResponseMap) {
                        syncOpResponseMap.put(oTransId, responseHandler);
                    }
                }
            }

            return oTransId;
        }
    }

    public Long createPubSubTransaction(final boolean syncOperation, final MALPublishInteractionListener listener) {
        synchronized (transactions) {
            final Long oTransId = TransactionIdCounter.nextTransactionId();

            OperationResponseHolder responseHolder = new OperationResponseHolder(listener);
            transactions.put(oTransId, new PubSubOperationHandler(syncOperation, responseHolder));

            if (syncOperation) {
                synchronized (syncOpResponseMap) {
                    syncOpResponseMap.put(oTransId, responseHolder);
                }
            }

            return oTransId;
        }
    }

    public void continueTransaction(final int interactionType,
            final UOctet lastInteractionStage,
            final Long oTransId,
            final MALInteractionListener listener) throws MALException, MALInteractionException {
        synchronized (transactions) {
            if (transactions.containsKey(oTransId)) {
                throw new MALException("Transaction Id already in use and cannot be continued");
            }

            BaseOperationHandler handler = null;
            OperationResponseHolder responseHolder = new OperationResponseHolder(listener);

            switch (interactionType) {
                case InteractionType._SUBMIT_INDEX:
                    handler = new SubmitOperationHandler(responseHolder);
                    break;
                case InteractionType._REQUEST_INDEX:
                    handler = new RequestOperationHandler(responseHolder);
                    break;
                case InteractionType._INVOKE_INDEX:
                    handler = new InvokeOperationHandler(lastInteractionStage, responseHolder);
                    break;
                case InteractionType._PROGRESS_INDEX:
                    handler = new ProgressOperationHandler(lastInteractionStage, responseHolder);
                    break;
                case InteractionType._PUBSUB_INDEX:
                    handler = new PubSubOperationHandler(responseHolder);
                    break;
                default:
                    throw new MALInteractionException(
                            new MOErrorException(
                                    MALHelper.INTERNAL_ERROR_NUMBER,
                                    new Union("Pattern not supported")
                            )
                    );
            }

            transactions.put(oTransId, handler);
        }
    }

    public MALMessage waitForResponse(final Long id) throws MALInteractionException, MALException {
        OperationResponseHolder holder = null;

        synchronized (syncOpResponseMap) {
            if (syncOpResponseMap.containsKey(id)) {
                holder = syncOpResponseMap.get(id);
            } else {
                MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                        "No key found in service maps to wait for response! {0}", id);
            }
        }

        if (holder != null) { // Wait until ready...
            holder.waitForResponseSignal();

            // delete entry from trans map
            synchronized (syncOpResponseMap) {
                MALContextFactoryImpl.LOGGER.log(Level.FINE,
                        "Removing handler from sync service map: {0}", id);
                syncOpResponseMap.remove(id);
            }

            synchronized (holder) {
                return holder.getResult(); // must have value now
            }
        }

        return null;
    }

    public void handleStage(final MALMessage msg) throws MALInteractionException, MALException {
        final Long id = msg.getHeader().getTransactionId();
        BaseOperationHandler handler;
        MessageHandlerDetails dets = null;

        synchronized (transactions) {
            handler = transactions.get(id);

            if (handler == null) {
                String txt = "The transaction handler could not be found for transactionId: "
                        + id + "\nMessage header: " + msg.getHeader()
                        + "\n This error usually happens because the messages "
                        + "are being received out-of-order in the MAL layer. "
                        + "The problem is typically in the transport layer "
                        + "and usually is related with threading.";

                MALContextFactoryImpl.LOGGER.log(Level.WARNING, txt);
                throw new MALException(txt);
            }

            dets = handler.handleStage(msg);

            // delete entry from trans map
            if (handler.finished()) {
                MALContextFactoryImpl.LOGGER.log(Level.FINE,
                        "Removing handler from service maps: {0}", id);
                transactions.remove(id);
            }
        }

        synchronized (handler) {
            handler.processStage(dets);
        }
    }

    public void handleError(final MALMessageHeader hdr, final MOErrorException err, final Map qosMap) {
        final Long id = hdr.getTransactionId();
        BaseOperationHandler handler = null;

        synchronized (transactions) {
            if (transactions.containsKey(id)) {
                handler = transactions.get(id);
            } else {
                MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                        "No key found in service maps to get listener! {0} {1}",
                        new Object[]{id, hdr}
                );
            }

            if (handler != null) {
                // delete entry from trans map
                MALContextFactoryImpl.LOGGER.log(Level.FINE,
                        "Removing handler from service maps: {0}", id);
                transactions.remove(id);
            }
        }

        if (handler != null) {
            synchronized (handler) {
                handler.handleError(hdr, err, qosMap);
            }
        }
    }
}
