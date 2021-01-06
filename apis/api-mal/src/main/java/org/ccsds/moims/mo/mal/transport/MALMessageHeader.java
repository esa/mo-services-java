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
package org.ccsds.moims.mo.mal.transport;

import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;

/**
 * The MALMessageHeader structure is used to hold all fields that are passed for
 * each message exchanged between a consumer and provider.
 */
public interface MALMessageHeader {

    /**
     * Returns the field URIfrom.
     *
     * @return the field URIfrom.
     */
    org.ccsds.moims.mo.mal.structures.URI getURIFrom();

    /**
     * Sets the field URIfrom.
     *
     * @param newValue The new value to set.
     */
    void setURIFrom(org.ccsds.moims.mo.mal.structures.URI newValue);

    /**
     * Returns the field authenticationId.
     *
     * @return the field authenticationId.
     */
    org.ccsds.moims.mo.mal.structures.Blob getAuthenticationId();

    /**
     * Sets the field authenticationId.
     *
     * @param newValue The new value to set.
     */
    void setAuthenticationId(org.ccsds.moims.mo.mal.structures.Blob newValue);

    /**
     * Returns the field URIto.
     *
     * @return the field URIto.
     */
    org.ccsds.moims.mo.mal.structures.URI getURITo();

    /**
     * Sets the field URIto.
     *
     * @param newValue The new value to set.
     */
    void setURITo(org.ccsds.moims.mo.mal.structures.URI newValue);

    /**
     * Returns the field timestamp.
     *
     * @return the field timestamp.
     */
    org.ccsds.moims.mo.mal.structures.Time getTimestamp();

    /**
     * Sets the field timestamp.
     *
     * @param newValue The new value to set.
     */
    void setTimestamp(org.ccsds.moims.mo.mal.structures.Time newValue);

    /**
     * Returns the field QoSlevel.
     *
     * @return the field QoSlevel.
     */
    org.ccsds.moims.mo.mal.structures.QoSLevel getQoSlevel();

    /**
     * Sets the field QoSlevel.
     *
     * @param newValue The new value to set.
     */
    void setQoSlevel(org.ccsds.moims.mo.mal.structures.QoSLevel newValue);

    /**
     * Returns the field priority.
     *
     * @return the field priority.
     */
    UInteger getPriority();

    /**
     * Sets the field priority.
     *
     * @param newValue The new value to set.
     */
    void setPriority(UInteger newValue);

    /**
     * Returns the field domain.
     *
     * @return the field domain.
     */
    org.ccsds.moims.mo.mal.structures.IdentifierList getDomain();

    /**
     * Sets the field domain.
     *
     * @param newValue The new value to set.
     */
    void setDomain(org.ccsds.moims.mo.mal.structures.IdentifierList newValue);

    /**
     * Returns the field networkZone.
     *
     * @return the field networkZone.
     */
    org.ccsds.moims.mo.mal.structures.Identifier getNetworkZone();

    /**
     * Sets the field networkZone.
     *
     * @param newValue The new value to set.
     */
    void setNetworkZone(org.ccsds.moims.mo.mal.structures.Identifier newValue);

    /**
     * Returns the field session.
     *
     * @return the field session.
     */
    org.ccsds.moims.mo.mal.structures.SessionType getSession();

    /**
     * Sets the field session.
     *
     * @param newValue The new value to set.
     */
    void setSession(org.ccsds.moims.mo.mal.structures.SessionType newValue);

    /**
     * Returns the field sessionName.
     *
     * @return the field sessionName.
     */
    org.ccsds.moims.mo.mal.structures.Identifier getSessionName();

    /**
     * Sets the field sessionName.
     *
     * @param newValue The new value to set.
     */
    void setSessionName(org.ccsds.moims.mo.mal.structures.Identifier newValue);

    /**
     * Returns the field interactionType.
     *
     * @return the field interactionType.
     */
    org.ccsds.moims.mo.mal.structures.InteractionType getInteractionType();

    /**
     * Sets the field interactionType.
     *
     * @param newValue The new value to set.
     */
    void setInteractionType(org.ccsds.moims.mo.mal.structures.InteractionType newValue);

    /**
     * Returns the field interactionStage.
     *
     * @return the field interactionStage.
     */
    UOctet getInteractionStage();

    /**
     * Sets the field interactionStage.
     *
     * @param newValue The new value to set.
     */
    void setInteractionStage(UOctet newValue);

    /**
     * Returns the field transactionId.
     *
     * @return the field transactionId.
     */
    Long getTransactionId();

    /**
     * Sets the field transactionId.
     *
     * @param newValue The new value to set.
     */
    void setTransactionId(Long newValue);

    /**
     * Returns the field area.
     *
     * @return the field area.
     */
    org.ccsds.moims.mo.mal.structures.UShort getServiceArea();

    /**
     * Sets the field area.
     *
     * @param newValue The new value to set.
     */
    void setServiceArea(org.ccsds.moims.mo.mal.structures.UShort newValue);

    /**
     * Returns the field service.
     *
     * @return the field service.
     */
    org.ccsds.moims.mo.mal.structures.UShort getService();

    /**
     * Sets the field service.
     *
     * @param newValue The new value to set.
     */
    void setService(org.ccsds.moims.mo.mal.structures.UShort newValue);

    /**
     * Returns the field operation.
     *
     * @return the field operation.
     */
    org.ccsds.moims.mo.mal.structures.UShort getOperation();

    /**
     * Sets the field operation.
     *
     * @param newValue The new value to set.
     */
    void setOperation(org.ccsds.moims.mo.mal.structures.UShort newValue);

    /**
     * Returns the field version.
     *
     * @return the field version.
     */
    UOctet getAreaVersion();

    /**
     * Sets the field version.
     *
     * @param newValue The new value to set.
     */
    void setAreaVersion(UOctet newValue);

    /**
     * Returns the field isError.
     *
     * @return the field isError.
     */
    Boolean getIsErrorMessage();

    /**
     * Sets the field isError.
     *
     * @param newValue The new value to set.
     */
    void setIsErrorMessage(Boolean newValue);
}
