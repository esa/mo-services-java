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
     * Returns the field timestamp.
     *
     * @return the field timestamp.
     */
    org.ccsds.moims.mo.mal.structures.Time getTimestamp();

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
     * Returns the field domain.
     *
     * @return the field domain.
     */
    org.ccsds.moims.mo.mal.structures.IdentifierList getDomain();

    /**
     * Returns the field networkZone.
     *
     * @return the field networkZone.
     */
    org.ccsds.moims.mo.mal.structures.Identifier getNetworkZone();

    /**
     * Returns the field session.
     *
     * @return the field session.
     */
    org.ccsds.moims.mo.mal.structures.SessionType getSession();

    /**
     * Returns the field sessionName.
     *
     * @return the field sessionName.
     */
    org.ccsds.moims.mo.mal.structures.Identifier getSessionName();

    /**
     * Returns the field interactionType.
     *
     * @return the field interactionType.
     */
    org.ccsds.moims.mo.mal.structures.InteractionType getInteractionType();

    /**
     * Returns the field interactionStage.
     *
     * @return the field interactionStage.
     */
    UOctet getInteractionStage();

    /**
     * Returns the field transactionId.
     *
     * @return the field transactionId.
     */
    Long getTransactionId();

    /**
     * Returns the field area.
     *
     * @return the field area.
     */
    org.ccsds.moims.mo.mal.structures.UShort getServiceArea();

    /**
     * Returns the field service.
     *
     * @return the field service.
     */
    org.ccsds.moims.mo.mal.structures.UShort getService();

    /**
     * Returns the field operation.
     *
     * @return the field operation.
     */
    org.ccsds.moims.mo.mal.structures.UShort getOperation();

    /**
     * Returns the field version.
     *
     * @return the field version.
     */
    UOctet getAreaVersion();

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
