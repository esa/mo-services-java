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
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 *
 */
public abstract class IPConsumerHandler {

    protected static final DummyErrorBody ERROR_BODY_INCORRECT_STATE
            = new DummyErrorBody(new MOErrorException(MALHelper.INCORRECT_STATE_ERROR_NUMBER, null));
    protected final boolean isSynchronous;
    protected final OperationResponseHolder responseHolder;

    protected IPConsumerHandler(final boolean isSynchronous, final OperationResponseHolder responseHolder) {
        this.isSynchronous = isSynchronous;
        this.responseHolder = responseHolder;
    }

    public abstract void handleStage(final MALMessage msg) throws MALInteractionException;

    public abstract void handleError(final MALMessageHeader hdr, final MOErrorException err, final Map qosMap);

    public abstract boolean finished();

    protected static void logUnexpectedTransitionError(final int interactionType, final int interactionStage) {
        MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                "Unexpected transition IP({0}) Stage({1})",
                new Object[]{
                    InteractionType.fromOrdinal(interactionType), interactionStage
                });
    }
}
