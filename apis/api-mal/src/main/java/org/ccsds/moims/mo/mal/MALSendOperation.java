/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java API
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
package org.ccsds.moims.mo.mal;

import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * Class representing a Send operation.
 */
public class MALSendOperation extends MALOperation {

    /**
     * Literal representing the SUBMIT stage.
     */
    public static final byte _SEND_STAGE = (byte) 0x1;
    /**
     * MAL UOctet representing the SUBMIT stage.
     */
    public static final UOctet SEND_STAGE = new UOctet(_SEND_STAGE);

    private final MALOperationStage sendStage;

    /**
     * Initialises the internal variables with the supplied values.
     *
     * @param number Number of the operation.
     * @param name Name of the operation.
     * @param replayable Boolean that indicates whether the operation is
     * replayable or not
     * @param capabilitySet Capability set of the operation.
     * @param sendStage The stage information for the SEND stage.
     * @throws java.lang.IllegalArgumentException If any argument is null,
     * except the operation stage arguments.
     */
    public MALSendOperation(final UShort number,
            final Identifier name,
            final Boolean replayable,
            final UShort capabilitySet,
            final MALOperationStage sendStage)
            throws java.lang.IllegalArgumentException {
        super(number, name, replayable, InteractionType.SEND, capabilitySet);
        this.sendStage = sendStage;
    }

    /**
     * Returns the operation stage for the supplied stage number.
     *
     * @param stageNumber The stage number to return, ignored for SEND pattern.
     * @return The operation stage.
     */
    @Override
    public MALOperationStage getOperationStage(final UOctet stageNumber) {
        return sendStage;
    }
}
