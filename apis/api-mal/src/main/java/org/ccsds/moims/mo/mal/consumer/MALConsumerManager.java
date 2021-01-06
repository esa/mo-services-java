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
package org.ccsds.moims.mo.mal.consumer;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;

/**
 * The MALConsumerManager interface encapsulates the resources used to enable a
 * MAL consumer to initiate interactions. The MALConsumerManager is a
 * MALConsumer factory.
 */
public interface MALConsumerManager {

    /**
     * The method creates a MALConsumer.
     *
     * @param localName Name of the private MALEndpoint to be created and used
     * by the consumer, may be NULL
     * @param uriTo URI of the service provider the consumer will interact with,
     * may be NULL
     * @param brokerUri URI of the broker used by the service provider to
     * publish updates, may be NULL
     * @param service Definition of the consumed service
     * @param authenticationId Authentication identifier used by the consumer
     * during all the interactions with the service provider
     * @param domain Domain the service provider belongs to
     * @param networkZone Network zone the provider belongs to
     * @param sessionType Session type of the service
     * @param sessionName Name of the session
     * @param qosLevel QoS level required by the consumer for all the
     * interactions with the provider
     * @param qosProps QoS properties that are needed to configure the QoS
     * level, may be NULL
     * @param priority Message priority required by the consumer for all the
     * interactions with the provider
     * @return The created MALConsumer.
     * @throws java.lang.IllegalArgumentException If the parameters ‘service’,
     * ‘authenticationId’, ‘domain’, ‘networkZone’, ‘sessionType’,
     * ‘sessionName’, ‘qosLevel’ or ‘priority’ are NULL
     * @throws MALException If the MALConsumerManager is closed
     */
    MALConsumer createConsumer(
            String localName,
            URI uriTo,
            URI brokerUri,
            MALService service,
            Blob authenticationId,
            IdentifierList domain,
            Identifier networkZone,
            SessionType sessionType,
            Identifier sessionName,
            QoSLevel qosLevel,
            java.util.Map qosProps,
            UInteger priority)
            throws java.lang.IllegalArgumentException, MALException;

    /**
     * The method creates a MALConsumer.
     *
     * @param endpoint Shared MALEndpoint to be used by the consumer
     * @param uriTo URI of the service provider the consumer will interact with,
     * may be NULL
     * @param brokerUri URI of the broker used by the service provider to
     * publish updates, may be NULL
     * @param service Definition of the consumed service
     * @param authenticationId Authentication identifier used by the consumer
     * during all the interactions with the service provider
     * @param domain Domain the service provider belongs to
     * @param networkZone Network zone the provider belongs to
     * @param sessionType Session type of the service
     * @param sessionName Name of the session
     * @param qosLevel QoS level required by the consumer for all the
     * interactions with the provider
     * @param qosProps QoS properties that are needed to configure the QoS
     * level, may be NULL
     * @param priority Message priority required by the consumer for all the
     * interactions with the provider
     * @return The created MALConsumer.
     * @throws java.lang.IllegalArgumentException If the parameters 'endpoint',
     * ‘service’, ‘authenticationId’, ‘domain’, ‘networkZone’, ‘sessionType’,
     * ‘sessionName’, ‘qosLevel’ or ‘priority’ are NULL
     * @throws MALException If the MALConsumerManager is closed
     */
    MALConsumer createConsumer(
            MALEndpoint endpoint,
            URI uriTo,
            URI brokerUri,
            MALService service,
            Blob authenticationId,
            IdentifierList domain,
            Identifier networkZone,
            SessionType sessionType,
            Identifier sessionName,
            QoSLevel qosLevel,
            java.util.Map qosProps,
            UInteger priority)
            throws java.lang.IllegalArgumentException, MALException;

    /**
     * The method releases the resources owned by a MALConsumerManager
     *
     * @throws MALException If an internal error occurs
     */
    void close() throws MALException;
}
