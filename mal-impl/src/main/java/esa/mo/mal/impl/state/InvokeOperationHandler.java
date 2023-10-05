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
package esa.mo.mal.impl.state;

import esa.mo.mal.impl.MALContextFactoryImpl;
import java.util.Map;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALInvokeOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Handles the state machine for a consumer for an INVOKE operation.
 */
public final class InvokeOperationHandler extends OperationHandler {

    private boolean receivedAck = false;
    private boolean receivedResponse = false;

    /**
     * Constructor.
     *
     * @param syncOperation true if this is a isSynchronous call.
     * @param responseHolder The response holder.
     */
    public InvokeOperationHandler(final boolean syncOperation,
            final OperationResponseHolder responseHolder) {
        super(syncOperation, responseHolder);
    }

    /**
     * Constructor.
     *
     * @param lastInteractionStage Last seen interaction stage.
     * @param responseHolder The response holder.
     */
    public InvokeOperationHandler(final UOctet lastInteractionStage,
            final OperationResponseHolder responseHolder) {
        super(false, responseHolder);
        final int interactionStage = lastInteractionStage.getValue();
        if (interactionStage == MALInvokeOperation._INVOKE_ACK_STAGE) {
            receivedAck = true;
        }
    }

    @Override
    public StateMachineDetails handleStage(final MALMessage msg) throws MALInteractionException {
        final int interactionType = msg.getHeader().getInteractionType().getOrdinal();
        final int interactionStage = msg.getHeader().getInteractionStage().getValue();
        if (!receivedAck) {
            if ((interactionType == InteractionType._INVOKE_INDEX) && (interactionStage == MALInvokeOperation._INVOKE_ACK_STAGE)) {
                receivedAck = true;
                if (!isSynchronous && msg.getHeader().getIsErrorMessage()) {
                    receivedResponse = true;
                }
                return new StateMachineDetails(true, msg, false);
            } else {
                receivedResponse = true;
                logUnexpectedTransitionError(interactionType, interactionStage);
                return new StateMachineDetails(true, msg, true);
            }
        } else if ((!receivedResponse) && (interactionType == InteractionType._INVOKE_INDEX) && (interactionStage == MALInvokeOperation._INVOKE_RESPONSE_STAGE)) {
            receivedResponse = true;
            return new StateMachineDetails(false, msg, false);
        } else {
            logUnexpectedTransitionError(interactionType, interactionStage);
            receivedResponse = true;
            return new StateMachineDetails(false, msg, true);
        }
    }

    @Override
    public void processStage(final StateMachineDetails state) throws MALInteractionException {
        boolean isError = state.getMessage().getHeader().getIsErrorMessage();

        try {
            if (state.isAckStage()) {
                if (isSynchronous) {
                    responseHolder.signalResponse(isError, state.getMessage());
                } else if (isError) {
                    responseHolder.getListener().invokeAckErrorReceived(state.getMessage().getHeader(),
                            (MALErrorBody) state.getMessage().getBody(),
                            state.getMessage().getQoSProperties());
                } else {
                    responseHolder.getListener().invokeAckReceived(state.getMessage().getHeader(),
                            state.getMessage().getBody(),
                            state.getMessage().getQoSProperties());
                }
            } else if (isError) {
                responseHolder.getListener().invokeResponseErrorReceived(state.getMessage().getHeader(),
                        (MALErrorBody) state.getMessage().getBody(),
                        state.getMessage().getQoSProperties());
            } else {
                responseHolder.getListener().invokeResponseReceived(state.getMessage().getHeader(),
                        state.getMessage().getBody(),
                        state.getMessage().getQoSProperties());
            }

            if (state.isIncorrectState()) {
                MALErrorBody errorBody = (MALErrorBody) state.getMessage().getBody();
                throw new MALInteractionException(errorBody.getError());
            }
        } catch (MALException ex) {
            // nothing we can do with this
            MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                    "Exception thrown handling stage {0}", ex);
        }
    }

    @Override
    public synchronized void handleError(final MALMessageHeader hdr,
            final MOErrorException err, final Map qosMap) {
        if (isSynchronous) {
            responseHolder.signalResponse(true, new DummyMessage(hdr, new DummyErrorBody(err), qosMap));
        } else {
            try {
                if (!receivedAck) {
                    responseHolder.getListener().invokeAckErrorReceived(hdr, new DummyErrorBody(err), qosMap);
                } else {
                    responseHolder.getListener().invokeResponseErrorReceived(hdr, new DummyErrorBody(err), qosMap);
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
