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
package org.ccsds.moims.mo.mal.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.AttributeTypeList;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * The MALPublisher interface enables a provider to publish updates and errors
 * to registered consumers.
 */
public interface MALPublisher {

    /**
     * Publishes a list of updates.
     *
     * @param updateHeader Published UpdateHeader.
     * @param updateValues The published values of the Update message.
     * @return The MALMessage that has been sent.
     * @throws java.lang.IllegalArgumentException If the parameter
     * ‘updateHeaderList’ is NULL.
     * @throws MALException If a non-MAL error occurs during the initiation
     * message sending.
     * @throws MALInteractionException If a MAL standard error occurs during the
     * initiation message sending.
     */
    MALMessage publish(UpdateHeader updateHeader, Object... updateValues)
            throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

    /**
     * The method enables a provider to synchronously register to its broker.
     *
     * @param keyNames Key Names of the subscriptions that are to be published
     * @param keyTypes Key Types of the subscriptions that are to be published
     * @param listener Listener in charge of receiving the messages PUBLISH
     * ERROR
     * @throws java.lang.IllegalArgumentException If the parameters
     * ‘entityKeyList’ or ‘listener’ are NULL
     * @throws MALException if a non-MAL error occurs during the initiation
     * message sending or the MALPublisher is closed.
     * @throws MALInteractionException if a PUBLISH REGISTER ERROR or other MAL
     * error occurs.
     */
    void register(IdentifierList keyNames, AttributeTypeList keyTypes,
            MALPublishInteractionListener listener)
            throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

    /**
     * The method enables a provider to synchronously deregister from its
     * broker.
     *
     * @throws MALInteractionException if a MAL standard error occurs during the
     * initiation message sending.
     * @throws MALException if a non-MAL error occurs
     */
    void deregister() throws MALInteractionException, MALException;

    /**
     * The method enables a provider to asynchronously register to its broker.
     *
     * @param keyNames Key Names of the subscriptions that are to be published
     * @param keyTypes Key Types of the subscriptions that are to be published
     * @param listener Listener in charge of receiving the messages PUBLISH
     * REGISTER ACK, PUBLISH REGISTER ERROR and PUBLISH ERROR
     * @return the MALMessage that has been sent
     * @throws java.lang.IllegalArgumentException If the parameter
     * ‘entityKeyList’ or ‘listener’ are NULL
     * @throws MALException if a non-MAL error occurs during the initiation
     * message sending or the MALPublisher is closed.
     * @throws MALInteractionException if a MAL error occurs.
     */
    MALMessage asyncRegister(IdentifierList keyNames, AttributeTypeList keyTypes, MALPublishInteractionListener listener)
            throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

    /**
     * The method enables a provider to asynchronously deregister from its
     * broker.
     *
     * @param listener Listener in charge of receiving the messages PUBLISH
     * DEREGISTER ACK
     * @return the MALMessage that has been sent
     * @throws java.lang.IllegalArgumentException If the parameter ‘listener’ is
     * NULL
     * @throws MALException if a non-MAL error occurs during the initiation
     * message sending or the MALPublisher is closed.
     * @throws MALInteractionException if a MAL error occurs.
     */
    MALMessage asyncDeregister(MALPublishInteractionListener listener)
            throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

    /**
     * Return the MALProvider that created this MALPublisher.
     *
     * @return The Provider.
     */
    MALProvider getProvider();

    /**
     * The method releases the resources owned by this MALPublisher.
     *
     * @throws MALException if an error occurs.
     */
    void close() throws MALException;
}
