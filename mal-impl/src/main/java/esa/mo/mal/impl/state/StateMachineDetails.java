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

import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * Wrapper class to hold message details when passing from a reception handler
 * to process handler.
 */
public final class StateMachineDetails {

    private final MALMessage message;
    private final boolean isIncorrectState;

    /**
     * Creates a Message Handler for a non-error message.
     *
     * @param msg The MAL message.
     * @param isIncorrectState
     */
    public StateMachineDetails(MALMessage msg, boolean isIncorrectState) {
        if (!isIncorrectState) {
            this.message = msg;
        } else {
            msg.getHeader().setIsErrorMessage(true);
            this.message = new DummyMessage(
                    msg.getHeader(),
                    new DummyErrorBody(new MOErrorException(MALHelper.INCORRECT_STATE_ERROR_NUMBER, null)),
                    msg.getQoSProperties());
        }
        this.isIncorrectState = isIncorrectState;
    }

    public MALMessage getMessage() {
        return message;
    }

    public boolean isIncorrectState() {
        return isIncorrectState;
    }
}
