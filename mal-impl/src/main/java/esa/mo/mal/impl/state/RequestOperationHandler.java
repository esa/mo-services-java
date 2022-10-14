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

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * Handles the state machine for a consumer for an REQUEST operation.
 */
public final class RequestOperationHandler extends SubmitOperationHandler {

    /**
     * Constructor.
     *
     * @param syncOperation true if this is a isSynchronous call.
     * @param responseHolder The response holder.
     */
    public RequestOperationHandler(final boolean syncOperation,
            final OperationResponseHolder responseHolder) {
        super(InteractionType._REQUEST_INDEX,
                MALRequestOperation._REQUEST_RESPONSE_STAGE,
                syncOperation,
                responseHolder);
    }

    /**
     * Constructor.
     *
     * @param responseHolder The response holder.
     */
    public RequestOperationHandler(final OperationResponseHolder responseHolder) {
        super(InteractionType._REQUEST_INDEX,
                MALRequestOperation._REQUEST_RESPONSE_STAGE,
                false,
                responseHolder);
    }

    @Override
    protected void informListener(final MALMessage msg) throws MALException {
        if (msg.getHeader().getIsErrorMessage()) {
            responseHolder.getListener().requestErrorReceived(
                    msg.getHeader(), (MALErrorBody) msg.getBody(), msg.getQoSProperties());
        } else {
            responseHolder.getListener().requestResponseReceived(
                    msg.getHeader(), msg.getBody(), msg.getQoSProperties());
        }
    }
}
