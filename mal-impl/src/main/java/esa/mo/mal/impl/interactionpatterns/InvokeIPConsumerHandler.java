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
package esa.mo.mal.impl.interactionpatterns;

import esa.mo.mal.impl.MALContextFactoryImpl;
import java.util.Map;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.IncorrectStateException;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALInvokeOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionListener;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Handles the state machine for a consumer for an INVOKE operation.
 */
public final class InvokeIPConsumerHandler extends IPConsumerHandler {

    private boolean receivedAck = false;
    private boolean finished = false;

    /**
     * Constructor.
     *
     * @param syncOperation true if this is a isSynchronous call.
     * @param responseHolder The response holder.
     */
    public InvokeIPConsumerHandler(final boolean syncOperation,
            final OperationResponseHolder responseHolder) {
        super(syncOperation, responseHolder);
    }

    @Override
    public void handleStage(final MALMessage msg) throws MALInteractionException {
        MALMessageHeader header = msg.getHeader();
        boolean isError = header.getIsErrorMessage();
        int interactionStage = header.getInteractionStage().getValue();
        MALInteractionListener listener = responseHolder.getListener();
        Map qos = msg.getQoSProperties();

        try {
            if (!receivedAck) {
                if (interactionStage == MALInvokeOperation._INVOKE_ACK_STAGE) {
                    receivedAck = true;

                    if (isSynchronous) {
                        responseHolder.signalResponse(isError, msg);
                    }

                    if (isError) {
                        finished = true;
                        listener.invokeAckErrorReceived(header, (MALErrorBody) msg.getBody(), qos);
                    } else {
                        listener.invokeAckReceived(header, msg.getBody(), qos);
                    }
                } else {
                    finished = true;
                    MOErrorException incorrectStateError = new IncorrectStateException(
                            "The received message is not an INVOKE_ACK_STAGE!");
                    listener.invokeAckErrorReceived(header, incorrectStateError, qos);
                }
                return;
            }

            // If it is not the first ACK then we are done!
            finished = true;

            if (interactionStage == MALInvokeOperation._INVOKE_RESPONSE_STAGE) {
                if (isError) {
                    listener.invokeResponseErrorReceived(header, (MALErrorBody) msg.getBody(), qos);
                } else {
                    listener.invokeResponseReceived(header, msg.getBody(), qos);
                }
                return;
            }

            // If it is not ACK, nor RESPONSE, then something went wrong!
            MOErrorException incorrectStateError = new IncorrectStateException(
                    "The received message is not an INVOKE_ACK_STAGE, nor INVOKE_RESPONSE_STAGE!");
            listener.invokeResponseErrorReceived(header, incorrectStateError, qos);
        } catch (MALException ex) {
            // nothing we can do with this
            MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                    "Exception thrown handling stage: " + interactionStage, ex);
        }
    }

    @Override
    public synchronized void handleError(final MALMessageHeader hdr,
            final MOErrorException error, final Map qosMap) {
        if (isSynchronous) {
            responseHolder.signalError(error);
        } else {
            try {
                if (!receivedAck) {
                    responseHolder.getListener().invokeAckErrorReceived(hdr, error, qosMap);
                } else {
                    responseHolder.getListener().invokeResponseErrorReceived(hdr, error, qosMap);
                }
            } catch (MALException ex) {
                // not a lot we can do with this at this stage apart from log it
                MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                        "Error received from consumer error handler in response to a provider error!", ex);
            }
        }
    }

    @Override
    public synchronized boolean finished() {
        return finished;
    }
}
