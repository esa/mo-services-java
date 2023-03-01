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

import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * The MALMessageHeader structure is used to hold the header fields that exist
 * in the header of MAL messages.
 */
public class MALMessageHeader {

    protected URI URIFrom;
    protected Blob authenticationId;
    protected URI URITo;
    protected Time timestamp;
    protected QoSLevel QoSlevel;
    protected UInteger priority;
    protected IdentifierList domain;
    protected Identifier networkZone;
    protected SessionType session;
    protected Identifier sessionName;
    protected InteractionType interactionType;
    protected UOctet interactionStage;
    protected Long transactionId;
    protected UShort serviceArea;
    protected UShort service;
    protected UShort operation;
    protected UOctet serviceVersion;
    protected Boolean isErrorMessage;

    /**
     * Constructor.
     */
    public MALMessageHeader() {
    }

    /**
     * Constructor.
     *
     * @param uriFrom URI of the message source
     * @param authenticationId Authentication identifier of the message
     * @param uriTo URI of the message destination
     * @param timestamp Timestamp of the message
     * @param qosLevel QoS level of the message
     * @param priority Priority of the message
     * @param domain Domain of the service provider
     * @param networkZone Network zone of the service provider
     * @param session Session of the service provider
     * @param sessionName Session name of the service provider
     * @param interactionType Interaction type of the operation
     * @param interactionStage Interaction stage of the interaction
     * @param transactionId Transaction identifier of the interaction, may be
     * null.
     * @param serviceArea Area number of the service
     * @param service Service number
     * @param operation Operation number
     * @param serviceVersion Service version number
     * @param isErrorMessage Flag indicating if the message conveys an error
     */
    public MALMessageHeader(final URI uriFrom,
            final Blob authenticationId,
            final URI uriTo,
            final Time timestamp,
            final QoSLevel qosLevel,
            final UInteger priority,
            final IdentifierList domain,
            final Identifier networkZone,
            final SessionType session,
            final Identifier sessionName,
            final InteractionType interactionType,
            final UOctet interactionStage,
            final Long transactionId,
            final UShort serviceArea,
            final UShort service,
            final UShort operation,
            final UOctet serviceVersion,
            final Boolean isErrorMessage) {
        this.URIFrom = uriFrom;
        this.authenticationId = authenticationId;
        this.URITo = uriTo;
        this.timestamp = timestamp;
        this.QoSlevel = qosLevel;
        this.priority = priority;
        this.domain = domain;
        this.networkZone = networkZone;
        this.session = session;
        this.sessionName = sessionName;
        this.interactionType = interactionType;
        this.interactionStage = interactionStage;
        this.transactionId = transactionId;
        this.serviceArea = serviceArea;
        this.service = service;
        this.operation = operation;
        this.serviceVersion = serviceVersion;
        this.isErrorMessage = isErrorMessage;
    }

    /**
     * Returns the field URIfrom.
     *
     * @return the field URIfrom.
     */
    public URI getURIFrom() {
        return URIFrom;
    }

    /**
     * Sets the field URIfrom.
     *
     * @param urIFrom The new value to set.
     */
    public void setURIFrom(final URI urIFrom) {
        this.URIFrom = urIFrom;
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
     * Returns the field URIto.
     *
     * @return the field URIto.
     */
    public URI getURITo() {
        return URITo;
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
     * Returns the field QoSlevel.
     *
     * @return the field QoSlevel.
     */
    public QoSLevel getQoSlevel() {
        return QoSlevel;
    }

    /**
     * Sets the field QoSlevel.
     *
     * @param newValue The new value to set.
     */
    public void setQoSlevel(final QoSLevel newValue) {
        this.QoSlevel = newValue;
    }

    /**
     * Returns the field priority.
     *
     * @return the field priority.
     */
    public UInteger getPriority() {
        return priority;
    }

    /**
     * Returns the field domain.
     *
     * @return the field domain.
     */
    public IdentifierList getDomain() {
        return domain;
    }

    /**
     * Returns the field networkZone.
     *
     * @return the field networkZone.
     */
    public Identifier getNetworkZone() {
        return networkZone;
    }

    /**
     * Returns the field session.
     *
     * @return the field session.
     */
    public SessionType getSession() {
        return session;
    }

    /**
     * Returns the field sessionName.
     *
     * @return the field sessionName.
     */
    public Identifier getSessionName() {
        return sessionName;
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
}
