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
 * The MALOperation class represents the specification of an operation provided
 * by a service. It is extended by interaction pattern specific classes.
 */
public abstract class MALOperation {

    private final Identifier name;
    private final UShort number;
    private final Boolean replayable;
    private final InteractionType interactionType;
    private final UShort capabilitySet;
    private MALService service;

    /**
     * Initialises the internal variables with the supplied values.
     *
     * @param number Number of the operation.
     * @param name Name of the operation.
     * @param replayable Boolean that indicates whether the operation is
     * replayable or not
     * @param interactionType Interaction type of the operation
     * @param capabilitySet Capability set of the operation.
     * @throws java.lang.IllegalArgumentException If any argument is null.
     */
    public MALOperation(final UShort number,
            final Identifier name,
            final Boolean replayable,
            final InteractionType interactionType,
            final UShort capabilitySet)
            throws java.lang.IllegalArgumentException {
        if ((number == null)
                || (name == null)
                || (replayable == null)
                || (interactionType == null)
                || (capabilitySet == null)) {
            throw new IllegalArgumentException("Supplied arguments must not be NULL");
        }
        this.name = name;
        this.number = number;
        this.replayable = replayable;
        this.interactionType = interactionType;
        this.capabilitySet = capabilitySet;
    }

    /**
     * Returns the operation name.
     *
     * @return The operation name.
     */
    public Identifier getName() {
        return name;
    }

    /**
     * Returns the operation number.
     *
     * @return The operation number.
     */
    public UShort getNumber() {
        return number;
    }

    /**
     * Returns the operation interaction type.
     *
     * @return The operation interaction type.
     */
    public InteractionType getInteractionType() {
        return interactionType;
    }

    /**
     * Returns whether the operation is replayable.
     *
     * @return Whether the operation is replayable.
     */
    public Boolean isReplayable() {
        return replayable;
    }

    /**
     * Returns the operation service.
     *
     * @return The operation service.
     */
    public MALService getService() {
        return service;
    }

    void setService(final MALService service) throws java.lang.IllegalArgumentException {
        this.service = service;
    }

    /**
     * Returns the operation capability set.
     *
     * @return The operation capability set.
     */
    public UShort getCapabilitySet() {
        return capabilitySet;
    }

    /**
     * Returns the operation stage for the supplied stage number.
     *
     * @param stageNumber The stage number to return, ignored for SEND pattern.
     * @return The operation stage.
     * @throws java.lang.IllegalArgumentException if the supplied argument is
     * null or stage does not exist for this pattern.
     */
    public abstract MALOperationStage getOperationStage(UOctet stageNumber) throws java.lang.IllegalArgumentException;
}
