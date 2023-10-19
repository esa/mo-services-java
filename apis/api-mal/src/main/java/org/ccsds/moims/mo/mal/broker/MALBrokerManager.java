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
package org.ccsds.moims.mo.mal.broker;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;

/**
 * The MALBrokerManager interface encapsulates the resources used to enable MAL
 * brokers to handle PUBLISH-SUBSCRIBE interactions. It is a MALBroker factory.
 *
 */
public interface MALBrokerManager {

    /**
     * Creates a broker using an internally generated broker handler.
     *
     * @return The new MALBroker.
     * @throws MALException If an error occurs or if the MALBrokerManager is
     * closed
     */
    MALBroker createBroker() throws MALException;

    /**
     * The method binds a shared MAL level broker to a particular transport or
     * creates a transport level broker if no MAL level broker is supplied.
     *
     * @param optionalMALBroker MAL level broker to be bound, may be NULL
     * @param localName Name of the private MALEndpoint to be created and used
     * by the broker binding, may be NULL
     * @param protocol Name of the protocol used to bind the broker
     * @param authenticationId Authentication identifier that should be used by
     * the broker
     * @param expectedQos QoS levels the broker assumes it can rely on
     * @param priorityLevelNumber Number of priorities the broker uses
     * @param qosProperties Default QoS properties used by the broker to send
     * @param supplements Set of optional named values
     * messages, may be NULL
     * @return The new broker binding.
     * @throws java.lang.IllegalArgumentException If the parameters ‘protocol’,
     * ‘authenticationId’ or ‘expectedQos’ are NULL
     * @throws MALException If the MALBrokerManager is closed
     */
    MALBrokerBinding createBrokerBinding(
            MALBroker optionalMALBroker,
            String localName,
            String protocol,
            Blob authenticationId,
            QoSLevel[] expectedQos,
            UInteger priorityLevelNumber,
            Map qosProperties,
            NamedValueList supplements)
            throws java.lang.IllegalArgumentException, MALException;

    /**
     * The method binds a shared MAL level broker to a particular transport or
     * creates a transport level broker if no MAL level broker is supplied.
     *
     * @param optionalMALBroker MAL level broker to be bound, may be NULL
     * @param endpoint Shared MALEndpoint to be used by the broker
     * @param authenticationId Authentication identifier that should be used by
     * the broker
     * @param expectedQos QoS levels the broker assumes it can rely on
     * @param priorityLevelNumber Number of priorities the broker uses
     * @param qosProperties Default QoS properties used by the broker to send
     * @param supplements Set of optional named values
     * messages, may be NULL
     * @return The new broker binding.
     * @throws java.lang.IllegalArgumentException If the parameters ‘endpoint’,
     * ‘authenticationId’ or ‘expectedQos’ are NULL
     * @throws MALException If the MALBrokerManager is closed
     */
    MALBrokerBinding createBrokerBinding(
            MALBroker optionalMALBroker,
            MALEndpoint endpoint,
            Blob authenticationId,
            QoSLevel[] expectedQos,
            UInteger priorityLevelNumber,
            Map qosProperties,
            NamedValueList supplements)
            throws java.lang.IllegalArgumentException, MALException;

    /**
     * The method releases the resources owned by this MALBrokerManager.
     *
     * @throws MALException If an internal error occurs
     */
    void close() throws MALException;
}
