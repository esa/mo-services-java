/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Generic Transport Framework
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
package esa.mo.mal.transport.gen;

import java.util.Map;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInvokeOperation;
import org.ccsds.moims.mo.mal.MALProgressOperation;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALTransmitErrorException;

/**
 * Builds and sends the MO Error message that answers a received message which
 * could not be delivered or processed.
 *
 * Only certain interaction type and stage combinations have an error defined as
 * their answer; for all others no error is returned.
 */
public class ErrorReplyBuilder {

    private final Transport transport;
    private final EndpointRegistry endpoints;
    private final Map qosProperties;

    /**
     * Constructor.
     *
     * @param transport The transport used to send the error message.
     * @param endpoints The endpoints of that transport, used to find a fallback
     * sender when the intended endpoint is not known.
     * @param qosProperties The QoS properties to send the error message with.
     */
    public ErrorReplyBuilder(final Transport transport,
            final EndpointRegistry endpoints, final Map qosProperties) {
        this.transport = transport;
        this.endpoints = endpoints;
        this.qosProperties = qosProperties;
    }

    /**
     * Returns true if a message with this interaction type and stage has an MO
     * Error defined as a valid answer.
     *
     * @param interactionType The interaction type of the received message.
     * @param stage The interaction stage of the received message.
     * @return True if an error may be returned.
     */
    private static boolean isErrorExpected(final InteractionType interactionType, final short stage) {
        return ((interactionType.equals(InteractionType.SUBMIT)) && (stage == MALSubmitOperation._SUBMIT_STAGE))
                || ((interactionType.equals(InteractionType.REQUEST)) && (stage == MALRequestOperation._REQUEST_STAGE))
                || ((interactionType.equals(InteractionType.INVOKE)) && (stage == MALInvokeOperation._INVOKE_STAGE))
                || ((interactionType.equals(InteractionType.PROGRESS)) && (stage == MALProgressOperation._PROGRESS_STAGE))
                || ((interactionType.equals(InteractionType.PUBSUB)) && (stage == MALPubSubOperation._REGISTER_STAGE))
                || ((interactionType.equals(InteractionType.PUBSUB)) && (stage == MALPubSubOperation._DEREGISTER_STAGE))
                || ((interactionType.equals(InteractionType.PUBSUB)) && (stage == MALPubSubOperation._PUBLISH_REGISTER_STAGE))
                || ((interactionType.equals(InteractionType.PUBSUB)) && (stage == MALPubSubOperation._PUBLISH_DEREGISTER_STAGE));
    }

    /**
     * Creates and sends an error message answering a received message.
     *
     * The error is sent from the endpoint the received message was being
     * delivered to. When that endpoint is not known, for example because the
     * message could not be routed to one at all, any endpoint of the transport
     * is used instead so that the sender is at least informed.
     *
     * @param endpoint The endpoint the message was being delivered to, or null
     * if it is not known.
     * @param srcHdr The header of the received message.
     * @param errorNumber The error number.
     * @param errorMsg The error message.
     * @throws MALException if the response message could not be encoded.
     */
    public void returnError(final Endpoint endpoint, final MALMessageHeader srcHdr,
            final UInteger errorNumber, final String errorMsg) throws MALException {
        try {
            InteractionType interactionType = srcHdr.getInteractionType();
            final short stage = (null != srcHdr.getInteractionStage())
                    ? srcHdr.getInteractionStage().getValue() : 0;

            if (!isErrorExpected(interactionType, stage)) {
                Transport.LOGGER.log(Level.WARNING, "An MO Error will not be returned because this "
                        + "combination of type/stage does not have an MO Error to "
                        + "be returned! For interaction type: {0} - and stage: {1}",
                        new Object[]{interactionType.toString(), stage});
                return;
            }

            Endpoint sender = (endpoint != null) ? endpoint : endpoints.any();

            if (sender == null) {
                Transport.LOGGER.log(Level.WARNING, "(1) Unable to return error"
                        + " number ({0}) as no endpoint supplied: {1}",
                        new Object[]{errorNumber, srcHdr});
                return;
            }

            final GENMessage retMsg = (GENMessage) sender.createMessage(srcHdr.getAuthenticationId(),
                    srcHdr.getFromURI(),
                    Time.now(),
                    srcHdr.getInteractionType(),
                    new UOctet((short) (srcHdr.getInteractionStage().getValue() + 1)),
                    srcHdr.getTransactionId(),
                    srcHdr.getServiceArea(),
                    srcHdr.getService(),
                    srcHdr.getOperation(),
                    srcHdr.getAreaVersion(),
                    true,
                    srcHdr.getSupplements(),
                    qosProperties,
                    errorNumber, new Union(errorMsg));

            transport.sendMessage(null, true, retMsg);
        } catch (MALTransmitErrorException ex) {
            Transport.LOGGER.log(Level.WARNING,
                    "Error occurred when attempting to return previous error!",
                    ex);
        }
    }
}
