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
 * Class representing a Submit operation.
 */
public class MALSubmitOperation extends MALOperation {

    /**
     * Literal representing the SUBMIT stage.
     */
    public static final byte _SUBMIT_STAGE = (byte) 0x1;
    /**
     * MAL UOctet representing the SUBMIT stage.
     */
    public static final UOctet SUBMIT_STAGE = new UOctet(_SUBMIT_STAGE);
    /**
     * Literal representing the SUBMIT_ACK stage.
     */
    public static final byte _SUBMIT_ACK_STAGE = (byte) 0x2;
    /**
     * MAL UOctet representing the SUBMIT_ACK stage.
     */
    public static final UOctet SUBMIT_ACK_STAGE = new UOctet(_SUBMIT_ACK_STAGE);

    private static final OperationField[] SUBMIT_ACK_OPERATION_STAGE = new OperationField[0];

    private final OperationField[] submitStage;

    /**
     * Initialises the internal variables with the supplied values.
     *
     * @param serviceKey Service Key for the service of this operation.
     * @param number Number of the operation.
     * @param name Name of the operation.
     * @param capabilitySet Capability set of the operation.
     * @param submitStage The stage information for the SUBMIT stage.
     * @throws java.lang.IllegalArgumentException If any argument is null,
     * except the operation stage arguments.
     */
    public MALSubmitOperation(final ServiceKey serviceKey,
            final UShort number,
            final Identifier name,
            final UShort capabilitySet,
            final OperationField[] submitStage)
            throws java.lang.IllegalArgumentException {
        super(serviceKey, number, name, InteractionType.SUBMIT, capabilitySet);

        this.submitStage = submitStage;
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
    public OperationField[] getFieldsOnStage(final UOctet stageNumber) throws IllegalArgumentException {
        if (stageNumber == null) {
            throw new IllegalArgumentException("Supplied stage number must not be NULL");
        }

        switch (stageNumber.getValue()) {
            case _SUBMIT_STAGE:
                return submitStage;
            case _SUBMIT_ACK_STAGE:
                return SUBMIT_ACK_OPERATION_STAGE;
            default:
                throw new IllegalArgumentException(
                        "Supplied stage number not supported by interaction pattern");
        }
    }
}
