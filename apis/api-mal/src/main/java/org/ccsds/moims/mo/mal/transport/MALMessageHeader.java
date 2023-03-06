/* ----------------------------------------------------------------------------
 * Copyright (C) 2023      European Space Agency
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
package org.ccsds.moims.mo.mal.transport;

import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.NamedValue;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * The MALMessageHeader structure is used to hold the header fields that exist
 * in the header of MAL messages.
 */
public class MALMessageHeader {

    protected URI from;
    protected Blob authenticationId;
    protected URI to;
    protected Time timestamp;
    protected InteractionType interactionType;
    protected UOctet interactionStage;
    protected Long transactionId;
    protected UShort serviceArea;
    protected UShort service;
    protected UShort operation;
    protected UOctet serviceVersion;
    protected Boolean isErrorMessage;
    protected NamedValueList supplements;

    /**
     * Constructor.
     */
    public MALMessageHeader() {
    }

    /**
     * Constructor.
     *
     * @param from URI of the message source
     * @param authenticationId Authentication identifier of the message
     * @param to URI of the message destination
     * @param timestamp Timestamp of the message
     * @param interactionType Interaction type of the operation
     * @param interactionStage Interaction stage of the interaction
     * @param transactionId Transaction identifier of the interaction, may be
     * null.
     * @param serviceArea Area number of the service
     * @param service Service number
     * @param operation Operation number
     * @param serviceVersion Service version number
     * @param isErrorMessage Flag indicating if the message conveys an error
     * @param supplements The header supplements
     */
    public MALMessageHeader(final URI from,
            final Blob authenticationId,
            final URI to,
            final Time timestamp,
            final InteractionType interactionType,
            final UOctet interactionStage,
            final Long transactionId,
            final UShort serviceArea,
            final UShort service,
            final UShort operation,
            final UOctet serviceVersion,
            final Boolean isErrorMessage,
            final NamedValueList supplements) {
        this.from = from;
        this.authenticationId = authenticationId;
        this.to = to;
        this.timestamp = timestamp;
        this.interactionType = interactionType;
        this.interactionStage = interactionStage;
        this.transactionId = transactionId;
        this.serviceArea = serviceArea;
        this.service = service;
        this.operation = operation;
        this.serviceVersion = serviceVersion;
        this.isErrorMessage = isErrorMessage;
        this.supplements = supplements;
    }

    /**
     * Returns the field from.
     *
     * @return the field from.
     */
    public URI getFrom() {
        return from;
    }

    /**
     * Sets the field from.
     *
     * @param newValue The new value to set.
     */
    public void setFrom(final URI newValue) {
        this.from = newValue;
    }

    /**
     * Returns the field authenticationId.
     *
     * @return the field authenticationId.
     */
    public Blob getAuthenticationId() {
        return authenticationId;
    }

    /**
     * Sets the field authenticationId.
     *
     * @param newValue The new value to set.
     */
    public void setAuthenticationId(final Blob newValue) {
        this.authenticationId = newValue;
    }

    /**
     * Returns the field to.
     *
     * @return the field to.
     */
    public URI getTo() {
        return to;
    }

    /**
     * Returns the field timestamp.
     *
     * @return the field timestamp.
     */
    public Time getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the field interactionType.
     *
     * @return the field interactionType.
     */
    public InteractionType getInteractionType() {
        return interactionType;
    }

    /**
     * Returns the field interactionStage.
     *
     * @return the field interactionStage.
     */
    public UOctet getInteractionStage() {
        return interactionStage;
    }

    /**
     * Returns the field transactionId.
     *
     * @return the field transactionId.
     */
    public Long getTransactionId() {
        return transactionId;
    }

    /**
     * Returns the field area.
     *
     * @return the field area.
     */
    public UShort getServiceArea() {
        return serviceArea;
    }

    /**
     * Returns the field service.
     *
     * @return the field service.
     */
    public UShort getService() {
        return service;
    }

    /**
     * Returns the field operation.
     *
     * @return the field operation.
     */
    public UShort getOperation() {
        return operation;
    }

    /**
     * Returns the field version.
     *
     * @return the field version.
     */
    public UOctet getServiceVersion() {
        return serviceVersion;
    }

    /**
     * Returns the field isError.
     *
     * @return the field isError.
     */
    public Boolean getIsErrorMessage() {
        return isErrorMessage;
    }

    /**
     * Sets the field isError.
     *
     * @param isErrorMessage The new value to set.
     */
    public void setIsErrorMessage(final Boolean isErrorMessage) {
        this.isErrorMessage = isErrorMessage;
    }

    /**
     * Returns the field supplements.
     *
     * @return the field supplements.
     */
    public NamedValueList getSupplements() {
        return supplements;
    }

    /**
     * Returns the supplement value for a given key.
     *
     * @param key
     * @return the supplement value for the provided key.
     */
    public Attribute getSupplementValue(String key) {
        for (NamedValue pair : supplements) {
            if (pair.getValue().equals(key)) {
                return pair.getValue();
            }
        }
        return null;
    }
}
