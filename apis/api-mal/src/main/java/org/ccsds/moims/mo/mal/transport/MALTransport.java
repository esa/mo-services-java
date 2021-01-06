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

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.structures.*;

/**
 * The MALTransport interface enables the MAL layer to send and receive MAL
 * messages through a single protocol. If several protocols are used by the MAL
 * layer then one MALTransport instance is created for each of them. Messages
 * are sent and received through communication ports represented by the
 * interface MALEndpoint. A MALTransport is a factory of MALEndpoint. One
 * MALEndpoint is created for each MALConsumer and MALProvider. NOTE – If a
 * MALProvider owns a private broker, the MAL layer can create either two
 * different MALEndpoints, one for the service provider and the other for the
 * broker, or a single one for both. In the first case, the service provider and
 * its broker have two different URIs whereas in the second case they share the
 * same one.
 *
 */
public interface MALTransport {

    /**
     * The method instantiates a new MALEndpoint object.
     *
     * @param localName Name of the endpoint
     * @param qosProperties QoS properties to be used when creating the
     * MALEndpoint
     * @return The new endpoint.
     * @throws MALException If the MALTransport is closed
     */
    MALEndpoint createEndpoint(String localName, Map qosProperties) throws MALException;

    /**
     * Returns the reference of a MALEndpoint from its local name
     *
     * @param localName Name of the MALEndpoint to get
     * @return The endpoint.
     * @throws IllegalArgumentException If the argument is null.
     * @throws MALException If the MALTransport is closed
     */
    MALEndpoint getEndpoint(String localName) throws IllegalArgumentException, MALException;

    /**
     * Returns the reference of a MALEndpoint from its URI
     *
     * @param uri URI of the MALEndpoint to get
     * @return The endpoint.
     * @throws IllegalArgumentException If the argument is null.
     * @throws MALException If the MALTransport is closed
     */
    MALEndpoint getEndpoint(URI uri) throws IllegalArgumentException, MALException;

    /**
     * The method deletes an endpoint designated by its local name.
     *
     * @param localName Name of the MALEndpoint to delete
     * @throws IllegalArgumentException If the parameter ‘localName’ is NULL
     * @throws MALException If the MALTransport is closed
     */
    void deleteEndpoint(String localName) throws IllegalArgumentException, MALException;

    /**
     * The method allows the creation of a transport level broker. The method
     * returns NULL if no broker can be created by this MALTransport.
     *
     * @param localName Name of the private MALEndpoint to be created and used
     * by the broker, may be null.
     * @param authenticationId Authentication identifier that should be used by
     * the broker, may be null.
     * @param expectedQos QoS levels the broker assumes it can rely on
     * @param priorityLevelNumber Number of priorities the broker uses
     * @param defaultQosProperties Default QoS properties used by the broker to
     * send messages, may be null.
     * @return The new broker binding.
     * @throws java.lang.IllegalArgumentException If the parameters
     * ‘expectedQoS’ or ‘priorityLevelNumber’ are NULL
     * @throws MALException If the MALTransport is closed
     */
    MALBrokerBinding createBroker(
            String localName,
            Blob authenticationId,
            QoSLevel[] expectedQos,
            UInteger priorityLevelNumber,
            Map defaultQosProperties)
            throws java.lang.IllegalArgumentException, MALException;

    /**
     * The method allows the creation of a transport level broker. The method
     * returns NULL if no broker can be created by this MALTransport.
     *
     * @param endpoint Shared MALEndpoint to be used by the broker
     * @param authenticationId Authentication identifier that should be used by
     * the broker, may be null.
     * @param expectedQos QoS levels the broker assumes it can rely on
     * @param priorityLevelNumber Number of priorities the broker uses
     * @param defaultQosProperties Default QoS properties used by the broker to
     * send messages, may be null.
     * @return The new broker binding.
     * @throws java.lang.IllegalArgumentException If the parameters ‘endpoint’
     * or ‘expectedQoS’ or ‘priorityLevelNumber’ are NULL
     * @throws MALException If the MALTransport is closed
     */
    MALBrokerBinding createBroker(
            MALEndpoint endpoint,
            Blob authenticationId,
            QoSLevel[] expectedQos,
            UInteger priorityLevelNumber,
            Map defaultQosProperties)
            throws java.lang.IllegalArgumentException, MALException;

    /**
     * The method indicates whether a QoS level is supported or not.
     *
     * @param qos QoSLevel which support is to be tested
     * @return TRUE if the specified QoSLevel is supported by the MALTransport
     * otherwise FALSE
     */
    boolean isSupportedQoSLevel(QoSLevel qos);

    /**
     * The method indicates whether an IP is supported or not.
     *
     * @param type The InteractionType which support is to be tested.
     * @return TRUE if the specified InteractionType is supported by the
     * MALTransport otherwise FALSE
     */
    boolean isSupportedInteractionType(InteractionType type);

    /**
     * The method releases all the resources allocated by the MALTransport.
     *
     * @throws MALException If an internal error occurs
     */
    void close() throws MALException;
}
