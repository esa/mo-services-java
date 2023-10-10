/* ----------------------------------------------------------------------------
 * Copyright (C) 2016      European Space Agency
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
package esa.mo.mal.impl.ips;

import esa.mo.mal.impl.MALContextFactoryImpl;
import java.util.Map;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALProgressOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Handles the state machine for a consumer for a PROGRESS operation.
 */
public final class ProgressIPConsumerHandler extends IPConsumerHandler {

    private boolean receivedAck = false;
    private boolean receivedResponse = false;

    /**
     * Constructor.
     *
     * @param syncOperation true if this is a isSynchronous call.
     * @param responseHolder The response holder.
     */
    public ProgressIPConsumerHandler(final boolean syncOperation,
            final OperationResponseHolder responseHolder) {
        super(syncOperation, responseHolder);
    }

    @Override
    public void handleStage(final MALMessage msg) throws MALInteractionException {
        MALMessageHeader header = msg.getHeader();
        final int interactionType = header.getInteractionType().getOrdinal();
        final int interactionStage = header.getInteractionStage().getValue();
        boolean isError = header.getIsErrorMessage();
        StateMachineDetails state;

        synchronized (this) {
            if (!receivedAck) {
                if ((interactionType == InteractionType._PROGRESS_INDEX)
                        && (interactionStage == MALProgressOperation._PROGRESS_ACK_STAGE)) {
                    receivedAck = true;
                    if (isError) {
                        receivedResponse = true;
                    }
                    state = new StateMachineDetails(msg, false);
                } else {
                    receivedResponse = true;
                    logUnexpectedTransitionError(interactionType, interactionStage);
                    state = new StateMachineDetails(msg, true);
                }
            } else if ((!receivedResponse) && (interactionType == InteractionType._PROGRESS_INDEX)
                    && ((interactionStage == MALProgressOperation._PROGRESS_UPDATE_STAGE)
                    || (interactionStage == MALProgressOperation._PROGRESS_RESPONSE_STAGE))) {
                if (interactionStage == MALProgressOperation._PROGRESS_UPDATE_STAGE) {
                    if (isError) {
                        receivedResponse = true;
                    }
                } else {
                    receivedResponse = true;
                }
                state = new StateMachineDetails(msg, false);
            } else {
                receivedResponse = true;
                logUnexpectedTransitionError(interactionType, interactionStage);
                state = new StateMachineDetails(msg, true);
            }
        }

        try {
            if (interactionStage == MALProgressOperation._PROGRESS_ACK_STAGE) {
                if (isSynchronous) {
                    responseHolder.signalResponse(isError, state.getMessage());
                } else {
                    if (isError) {
                        responseHolder.getListener().progressAckErrorReceived(header,
                                (MALErrorBody) state.getMessage().getBody(),
                                state.getMessage().getQoSProperties());
                    } else {
                        responseHolder.getListener().progressAckReceived(header,
                                state.getMessage().getBody(),
                                state.getMessage().getQoSProperties());
                    }
                }
            }
            if (interactionStage == MALProgressOperation._PROGRESS_UPDATE_STAGE) {
                if (isError || !receivedAck) {
                    responseHolder.getListener().progressUpdateErrorReceived(header,
                            (MALErrorBody) state.getMessage().getBody(),
                            state.getMessage().getQoSProperties());
                } else {
                    responseHolder.getListener().progressUpdateReceived(header,
                            state.getMessage().getBody(),
                            state.getMessage().getQoSProperties());
                }
            }
            if (interactionStage == MALProgressOperation._PROGRESS_RESPONSE_STAGE) {
                if (isError || !receivedAck) {
                    responseHolder.getListener().progressResponseErrorReceived(header,
                            (MALErrorBody) state.getMessage().getBody(),
                            state.getMessage().getQoSProperties());
                } else {
                    responseHolder.getListener().progressResponseReceived(header,
                            state.getMessage().getBody(),
                            state.getMessage().getQoSProperties());
                }
            }
            if (state.isIncorrectState()) {
                throw new MALInteractionException(((MALErrorBody) state.getMessage().getBody()).getError());
            }
        } catch (MALException ex) {
            // nothing we can do with this
            MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                    "Exception thrown handling stage: " + interactionStage, ex);
        }
    }

    @Override
    public synchronized void handleError(final MALMessageHeader hdr,
            final MOErrorException err, final Map qosMap) {
        if (isSynchronous) {
            responseHolder.signalError(new DummyErrorBody(err));
        } else {
            try {
                if (!receivedAck) {
                    responseHolder.getListener().progressAckErrorReceived(
                            hdr, new DummyErrorBody(err), qosMap);
                } else {
                    responseHolder.getListener().progressResponseErrorReceived(
                            hdr, new DummyErrorBody(err), qosMap);
                }
            } catch (MALException ex) {
                // not a lot we can do with this at this stage apart from log it
                MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                        "Error received from consumer error handler in response to a provider error! {0}", ex);
            }
        }
    }

    @Override
    public synchronized boolean finished() {
        return receivedResponse;
    }
}
