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

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * Handles the state machine for a consumer for a PUBSUB operation.
 */
public final class PubSubIPConsumerHandler extends SubmitIPConsumerHandler {

    /**
     * Constructor.
     *
     * @param syncOperation true if this is a isSynchronous call.
     * @param responseHolder The response holder.
     */
    public PubSubIPConsumerHandler(final boolean syncOperation,
            final OperationResponseHolder responseHolder) {
        super(InteractionType._PUBSUB_INDEX, 0, syncOperation, responseHolder);
    }

    /**
     * Constructor.
     *
     * @param responseHolder The response holder.
     */
    public PubSubIPConsumerHandler(final OperationResponseHolder responseHolder) {
        super(InteractionType._PUBSUB_INDEX, 0, false, responseHolder);
    }

    @Override
    protected boolean checkStage(final int stage) {
        switch (stage) {
            case MALPubSubOperation._REGISTER_ACK_STAGE:
            case MALPubSubOperation._PUBLISH_REGISTER_ACK_STAGE:
            case MALPubSubOperation._DEREGISTER_ACK_STAGE:
            case MALPubSubOperation._PUBLISH_DEREGISTER_ACK_STAGE:
                return true;
            default:
                return true;
        }
    }

    @Override
    protected void informListener(final MALMessage msg) throws MALException {
        if (msg.getHeader().getIsErrorMessage()) {
            responseHolder.getListener().registerErrorReceived(
                    msg.getHeader(),
                    (MALErrorBody) msg.getBody(),
                    msg.getQoSProperties());
        } else if ((MALPubSubOperation._PUBLISH_REGISTER_ACK_STAGE == msg.getHeader().getInteractionStage().getValue())
                || (MALPubSubOperation._REGISTER_ACK_STAGE == msg.getHeader().getInteractionStage().getValue())) {
            responseHolder.getListener().registerAckReceived(
                    msg.getHeader(), msg.getQoSProperties());
        } else {
            responseHolder.getListener().deregisterAckReceived(
                    msg.getHeader(), msg.getQoSProperties());
        }
    }
}
