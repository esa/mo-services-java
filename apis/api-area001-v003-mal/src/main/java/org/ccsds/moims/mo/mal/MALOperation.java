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

    private final ServiceKey serviceKey;
    private final Identifier name;
    private final UShort number;
    private final InteractionType interactionType;
    private final UShort capabilitySet;

    /**
     * Initialises the internal variables with the supplied values.
     *
     * @param serviceKey Service Key for the service of this operation.
     * @param number Number of the operation.
     * @param name Name of the operation.
     * @param interactionType Interaction type of the operation
     * @param capabilitySet Capability set of the operation.
     * @throws java.lang.IllegalArgumentException If any argument is null.
     */
    public MALOperation(final ServiceKey serviceKey,
            final UShort number,
            final Identifier name,
            final InteractionType interactionType,
            final UShort capabilitySet)
            throws java.lang.IllegalArgumentException {
        if ((number == null)
                || (name == null)
                || (interactionType == null)
                || (capabilitySet == null)) {
            throw new IllegalArgumentException("Supplied arguments must not be NULL");
        }
        this.serviceKey = serviceKey;
        this.name = name;
        this.number = number;
        this.interactionType = interactionType;
        this.capabilitySet = capabilitySet;
    }

    /**
     * Returns the Service Key.
     *
     * @return The Service Key.
     */
    public ServiceKey getServiceKey() {
        return serviceKey;
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
     * Returns if it is a PUB-SUB Interaction Pattern operation.
     *
     * @return True if it is a PUB-SUB Interaction Pattern operation. False
     * otherwise.
     */
    public boolean isPubSub() {
        return interactionType == InteractionType.PUBSUB;
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
    public abstract OperationField[] getFieldsOnStage(UOctet stageNumber) throws java.lang.IllegalArgumentException;
}
