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

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALTransmitErrorListener;

/**
 * The MALProvider interface represents the execution context of a service
 * provider for a given URI. If a service provider is to be bound to several
 * transport layers then several MALProviders should be created, each one
 * representing a binding between the service provider and a transport layer.
 * NOTE – Those MALProviders should own the same MALInteractionHandler instance.
 * However, it is not mandatory, as several MALInteractionHandlers can share the
 * same state, for example, a common database.
 */
public interface MALProvider {

    /**
     * Returns the URI of the provider.
     *
     * @return The URI.
     */
    URI getURI();

    /**
     * Returns the providers authentication identifier.
     *
     * @return The authentication identifier.
     */
    Blob getAuthenticationId();

    /**
     * Sets the providers authentication identifier.
     *
     * @param newAuthenticationId The new authentication identifier.
     * @return The previous authentication identifier.
     */
    Blob setAuthenticationId(Blob newAuthenticationId);

    /**
     * Indicates whether the provider can publish updates or not.
     *
     * @return True if a publisher.
     */
    boolean isPublisher();

    /**
     * Returns the URI of the private broker.
     *
     * @return The URI of the private broker, null if not a publisher.
     */
    URI getBrokerURI();

    /**
     * Returns the authentication identifier of the private broker.
     *
     * @return The authentication identifier, null if not a publisher.
     */
    Blob getBrokerAuthenticationId();

    /**
     * Sets the authentication identifier of the private broker.
     *
     * @param newAuthenticationId The new authentication identifier.
     * @return The previous authentication identifier.
     */
    Blob setBrokerAuthenticationId(Blob newAuthenticationId);

    /**
     * Returns the MALService provided by this MALProvider.
     *
     * @return The service.
     */
    MALService getService();

    /**
     * Creates a MALPublisher.
     *
     * @param op The operation
     * @param domain Domain of the PUBLISH messages
     * @param sessionType Session type of the PUBLISH messages
     * @param sessionName Session name of the PUBLISH messages
     * @param remotePublisherQos QoS level of the PUBLISH messages
     * @param remotePublisherQosProps QoS properties of the PUBLISH messages
     * @param supplements Set of optional named values
     * @return the new MALPublisher.
     * @throws java.lang.IllegalArgumentException If the parameters ‘op’ or
     * ‘domain’ or ‘networkZone’ or ‘sessionType’ or ‘sessionName’ or
     * ‘remotePublisherQos’ or ‘remotePublisherPriority’ are NULL.
     * @throws MALException If the MALProvider is not a publisher or if the
     * MALProvider is closed.
     */
    MALPublisher createPublisher(MALPubSubOperation op,
            IdentifierList domain,
            SessionType sessionType,
            Identifier sessionName,
            QoSLevel remotePublisherQos,
            Map remotePublisherQosProps,
            NamedValueList supplements)
            throws java.lang.IllegalArgumentException, MALException;

    /**
     * Sets the listener used for reporting transmission errors when no other
     * reporting mechanism is possible, for example on a SEND pattern.
     *
     * @param listener The listener to register.
     * @throws MALException If closed.
     */
    void setTransmitErrorListener(MALTransmitErrorListener listener) throws MALException;

    /**
     * Returns the current fall back transmission error listener.
     *
     * @return The current listener.
     * @throws MALException If closed.
     */
    MALTransmitErrorListener getTransmitErrorListener() throws MALException;

    /**
     * The method deactivates the MALInteractionHandler so that no interaction
     * pattern is handled any longer.
     *
     * @throws MALException If an internal error occurs.
     */
    void close() throws MALException;
}
