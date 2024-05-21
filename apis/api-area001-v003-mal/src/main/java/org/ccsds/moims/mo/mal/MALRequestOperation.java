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
 * Class representing a Request operation.
 */
public class MALRequestOperation extends MALOperation {

    /**
     * Literal representing the REQUEST stage.
     */
    public static final byte _REQUEST_STAGE = (byte) 0x1;
    /**
     * MAL UOctet representing the REQUEST stage.
     */
    public static final UOctet REQUEST_STAGE = new UOctet(_REQUEST_STAGE);
    /**
     * Literal representing the REQUEST_RESPONSE stage.
     */
    public static final byte _REQUEST_RESPONSE_STAGE = (byte) 0x2;
    /**
     * MAL UOctet representing the REQUEST_RESPONSE stage.
     */
    public static final UOctet REQUEST_RESPONSE_STAGE = new UOctet(_REQUEST_RESPONSE_STAGE);

    private final OperationField[] requestStage;
    private final OperationField[] responseStage;

    /**
     * Initialises the internal variables with the supplied values.
     *
     * @param serviceKey Service Key for the service of this operation.
     * @param number Number of the operation.
     * @param name Name of the operation.
     * @param capabilitySet Capability set of the operation.
     * @param requestStage The stage information for the REQUEST stage.
     * @param responseStage The stage information for the REQUEST_RESPONSE
     * stage.
     * @throws java.lang.IllegalArgumentException If any argument is null,
     * except the operation stage arguments.
     */
    public MALRequestOperation(final ServiceKey serviceKey,
            final UShort number,
            final Identifier name,
            final UShort capabilitySet,
            final OperationField[] requestStage,
            final OperationField[] responseStage)
            throws java.lang.IllegalArgumentException {
        super(serviceKey, number, name, InteractionType.REQUEST, capabilitySet);

        this.requestStage = requestStage;
        this.responseStage = responseStage;
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
            case _REQUEST_STAGE:
                return requestStage;
            case _REQUEST_RESPONSE_STAGE:
                return responseStage;
            default:
                throw new IllegalArgumentException(
                        "Supplied stage number not supported by interaction pattern");
        }
    }
}
