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
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Abstract class for handling a consumer state machine.
 *
 */
public abstract class IPConsumerHandler {

    /**
     * States, if the consumer is synchronous or not.
     */
    protected final boolean isSynchronous;
    /**
     * Holds the response.
     */
    protected final OperationResponseHolder responseHolder;

    /**
     * Constructor.
     *
     * @param isSynchronous Synchronous or not.
     * @param responseHolder The operation response holder.
     */
    protected IPConsumerHandler(final boolean isSynchronous, final OperationResponseHolder responseHolder) {
        this.isSynchronous = isSynchronous;
        this.responseHolder = responseHolder;
    }

    /**
     * Handles a MAL Message
     *
     * @param msg The MAL message
     * @throws MALInteractionException When something goes wrong
     */
    public abstract void handleStage(final MALMessage msg) throws MALInteractionException;

    /**
     * Handles an {@link MOErrorException MOErrorException} that might occur.
     *
     * @param hdr The MAL message header
     * @param err The MO error exception
     * @param qosMap The QoS Level
     */
    public abstract void handleError(final MALMessageHeader hdr, final MOErrorException err, final Map qosMap);

    /**
     * States, if the handle operation is finished or not.
     *
     * @return Boolean value if the operation is finished or not.
     */
    public abstract boolean finished();

    /**
     * Logs an unexpected transition error.
     *
     * @param interactionType The interaction Type
     * @param interactionStage The interaction Stage
     */
    protected static void logUnexpectedTransitionError(final int interactionType, final int interactionStage) {
        MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                "Unexpected transition IP({0}) Stage({1})",
                new Object[]{
                    new InteractionType(interactionType), interactionStage
                });
    }
}
