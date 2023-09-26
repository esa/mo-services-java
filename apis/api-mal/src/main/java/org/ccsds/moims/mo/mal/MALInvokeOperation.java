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
 * Class representing an Invoke operation.
 */
public class MALInvokeOperation extends MALOperation {

    /**
     * Literal representing the INVOKE stage.
     */
    public static final byte _INVOKE_STAGE = (byte) 0x1;
    /**
     * MAL UOctet representing the INVOKE stage.
     */
    public static final UOctet INVOKE_STAGE = new UOctet(_INVOKE_STAGE);
    /**
     * Literal representing the INVOKE_ACK stage.
     */
    public static final byte _INVOKE_ACK_STAGE = (byte) 0x2;
    /**
     * MAL UOctet representing the INVOKE_ACK stage.
     */
    public static final UOctet INVOKE_ACK_STAGE = new UOctet(_INVOKE_ACK_STAGE);
    /**
     * Literal representing the INVOKE_RESPONSE stage.
     */
    public static final byte _INVOKE_RESPONSE_STAGE = (byte) 0x3;
    /**
     * MAL UOctet representing the INVOKE_RESPONSE stage.
     */
    public static final UOctet INVOKE_RESPONSE_STAGE = new UOctet(_INVOKE_RESPONSE_STAGE);
    private final OperationField[] invokeStage;
    private final OperationField[] invokeAckStage;
    private final OperationField[] invokeResponseStage;

    /**
     * Initialises the internal variables with the supplied values.
     *
     * @param number Number of the operation.
     * @param name Name of the operation.
     * @param capabilitySet Capability set of the operation.
     * @param invokeStage The stage information for the INVOKE stage.
     * @param invokeAckStage The stage information for the INVOKE_ACK stage.
     * @param invokeResponseStage The stage information for the INVOKE_RESPONSE
     * stage.
     * @throws java.lang.IllegalArgumentException If any argument is null,
     * except the operation stage arguments.
     */
    public MALInvokeOperation(final UShort number,
            final Identifier name,
            final UShort capabilitySet,
            final OperationField[] invokeStage,
            final OperationField[] invokeAckStage,
            final OperationField[] invokeResponseStage)
            throws java.lang.IllegalArgumentException {
        super(number, name, InteractionType.INVOKE, capabilitySet);

        this.invokeStage = invokeStage;
        this.invokeAckStage = invokeAckStage;
        this.invokeResponseStage = invokeResponseStage;
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
            case _INVOKE_STAGE:
                return invokeStage;
            case _INVOKE_ACK_STAGE:
                return invokeAckStage;
            case _INVOKE_RESPONSE_STAGE:
                return invokeResponseStage;
            default:
                throw new IllegalArgumentException(
                        "Supplied stage number not supported by interaction pattern");
        }
    }
}
