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
 * Class representing a Progress operation.
 */
public class MALProgressOperation extends MALOperation {

    /**
     * Literal representing the PROGRESS stage.
     */
    public static final byte _PROGRESS_STAGE = (byte) 0x1;
    /**
     * MAL UOctet representing the PROGRESS stage.
     */
    public static final UOctet PROGRESS_STAGE = new UOctet(_PROGRESS_STAGE);
    /**
     * Literal representing the PROGRESS_ACK stage.
     */
    public static final byte _PROGRESS_ACK_STAGE = (byte) 0x2;
    /**
     * MAL UOctet representing the PROGRESS_ACK stage.
     */
    public static final UOctet PROGRESS_ACK_STAGE = new UOctet(_PROGRESS_ACK_STAGE);
    /**
     * Literal representing the PROGRESS_UPDATE stage.
     */
    public static final byte _PROGRESS_UPDATE_STAGE = (byte) 0x3;
    /**
     * MAL UOctet representing the PROGRESS_UPDATE stage.
     */
    public static final UOctet PROGRESS_UPDATE_STAGE = new UOctet(_PROGRESS_UPDATE_STAGE);
    /**
     * Literal representing the PROGRESS_RESPONSE stage.
     */
    public static final byte _PROGRESS_RESPONSE_STAGE = (byte) 0x4;
    /**
     * MAL UOctet representing the PROGRESS_RESPONSE stage.
     */
    public static final UOctet PROGRESS_RESPONSE_STAGE = new UOctet(_PROGRESS_RESPONSE_STAGE);

    private final MALOperationStage progressStage;
    private final MALOperationStage progressAckStage;
    private final MALOperationStage progressUpdateStage;
    private final MALOperationStage progressResponseStage;

    /**
     * Initialises the internal variables with the supplied values.
     *
     * @param number Number of the operation.
     * @param name Name of the operation.
     * @param replayable Boolean that indicates whether the operation is
     * replayable or not
     * @param capabilitySet Capability set of the operation.
     * @param progressStage The stage information for the PROGRESS stage.
     * @param progressAckStage The stage information for the PROGRESS_ACK stage.
     * @param progressUpdateStage The stage information for the PROGRESS_UPDATE
     * stage.
     * @param progressResponseStage The stage information for the
     * PROGRESS_RESPONSE stage.
     * @throws java.lang.IllegalArgumentException If any argument is null,
     * except the operation stage arguments.
     */
    public MALProgressOperation(final UShort number,
            final Identifier name,
            final Boolean replayable,
            final UShort capabilitySet,
            final MALOperationStage progressStage,
            final MALOperationStage progressAckStage,
            final MALOperationStage progressUpdateStage,
            final MALOperationStage progressResponseStage) throws java.lang.IllegalArgumentException {
        super(number, name, replayable, InteractionType.PROGRESS, capabilitySet);

        this.progressStage = progressStage;
        this.progressAckStage = progressAckStage;
        this.progressUpdateStage = progressUpdateStage;
        this.progressResponseStage = progressResponseStage;
    }

    /**
     * Returns the operation stage for the supplied stage number.
     *
     * @param stageNumber The stage number to return.
     * @return The operation stage.
     * @throws java.lang.IllegalArgumentException if the supplied argument is
     * null or stage does not exist for this pattern.
     */
    @Override
    public MALOperationStage getOperationStage(final UOctet stageNumber) throws IllegalArgumentException {
        if (stageNumber == null) {
            throw new IllegalArgumentException("Supplied stage number must not be NULL");
        }

        switch (stageNumber.getValue()) {
            case _PROGRESS_STAGE:
                return progressStage;
            case _PROGRESS_ACK_STAGE:
                return progressAckStage;
            case _PROGRESS_UPDATE_STAGE:
                return progressUpdateStage;
            case _PROGRESS_RESPONSE_STAGE:
                return progressResponseStage;
            default:
                throw new IllegalArgumentException(
                        "Supplied stage number not supported by interaction pattern");
        }
    }
}
