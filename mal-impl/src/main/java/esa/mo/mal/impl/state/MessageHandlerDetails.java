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

import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * Wrapper class to hold message details when passing from a reception handler
 * to process handler.
 */
public final class MessageHandlerDetails {

    private final boolean ackStage;
    private final MALMessage message;
    private final boolean needToReturnAnException;

    /**
     * Creates a Message Handler for a non-error message.
     *
     * @param isAckStage If it is a ack stage.
     * @param msg The MAL message.
     */
    public MessageHandlerDetails(boolean isAckStage, MALMessage msg) {
        this.ackStage = isAckStage;
        this.message = msg;
        this.needToReturnAnException = false;
    }

    /**
     * Creates a Message Handler for an error message.
     *
     * @param isAckStage If it is a ack stage.
     * @param msg The MAL message.
     * @param errNum The error number.
     */
    public MessageHandlerDetails(boolean isAckStage, MALMessage msg, UInteger errNum) {
        this.ackStage = isAckStage;
        msg.getHeader().setIsErrorMessage(true);
        this.message = new DummyMessage(
                msg.getHeader(),
                new DummyErrorBody(new MALStandardError(errNum, null)),
                msg.getQoSProperties());
        this.needToReturnAnException = true;
    }

    public boolean isAckStage() {
        return ackStage;
    }

    public MALMessage getMessage() {
        return message;
    }

    public boolean isNeedToReturnAnException() {
        return needToReturnAnException;
    }
}
