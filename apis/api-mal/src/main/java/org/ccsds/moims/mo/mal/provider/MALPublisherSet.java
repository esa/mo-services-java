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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * The MALPublisherSet class manages a set of MALPublishers that publish updates
 * through the same PUBLISH-SUBSCRIBE operation in the same domain, network zone
 * and session with the same QoS level and priority.
 *
 */
public class MALPublisherSet {

    private final MALProviderSet providerSet;
    private final Set<MALPublisher> subpublishers = new HashSet<>();
    private final MALPubSubOperation op;
    private final IdentifierList domain;
    private final Identifier networkZone;
    private final SessionType sessionType;
    private final Identifier sessionName;
    private final QoSLevel remotePublisherQos;
    private final Map remotePublisherQosProps;
    private final UInteger remotePublisherPriority;

    /**
     * Constructor.
     *
     * @param providerSet MALProviderSet that owns this MALPublisherSet
     * @param op PUBLISH-SUBSCRIBE operation
     * @param domain Domain of the PUBLISH messages
     * @param networkZone Network zone of the PUBLISH messages
     * @param sessionType Session type of the PUBLISH messages
     * @param sessionName Session name of the PUBLISH messages
     * @param remotePublisherQos QoS level of the PUBLISH messages, may be null.
     * @param remotePublisherQosProps QoS properties of the PUBLISH messages,
     * may be null.
     * @param remotePublisherPriority Priority of the PUBLISH messages, may be
     * null.
     * @throws java.lang.IllegalArgumentException If the parameters
     * ‘providerSet’ or ‘domain’ or ‘networkZone’ or ‘sessionType’ or
     * ‘sessionName’ are NULL
     * @throws MALException If an error occurs.
     */
    public MALPublisherSet(final MALProviderSet providerSet,
            final MALPubSubOperation op,
            final IdentifierList domain,
            final Identifier networkZone,
            final SessionType sessionType,
            final Identifier sessionName,
            final QoSLevel remotePublisherQos,
            final Map remotePublisherQosProps,
            final UInteger remotePublisherPriority) 
            throws java.lang.IllegalArgumentException, MALException {
        this.providerSet = providerSet;
        this.op = op;
        this.domain = domain;
        this.networkZone = networkZone;
        this.sessionType = sessionType;
        this.sessionName = sessionName;
        this.remotePublisherQos = remotePublisherQos;
        this.remotePublisherQosProps = remotePublisherQosProps;
        this.remotePublisherPriority = remotePublisherPriority;
    }

    void createPublisher(final MALProvider provider)
            throws java.lang.IllegalArgumentException, MALException {
        final MALPublisher pub = provider.createPublisher(op,
                domain,
                networkZone,
                sessionType,
                sessionName,
                remotePublisherQos,
                remotePublisherQosProps,
                remotePublisherPriority);
        subpublishers.add(pub);
    }

    void deletePublisher(final MALProvider provider)
            throws java.lang.IllegalArgumentException, MALException {
        //ToDo
    }

    /**
     * The method synchronously registers through all the MALPublishers of this
     * MALPublisherSet.
     *
     * @param keys Keys of the subscriptions that are to be published
     * @param listener Listener in charge of receiving the messages PUBLISH
     * ERROR
     * @throws java.lang.IllegalArgumentException If entityKeyList is null.
     * @throws MALInteractionException If thrown by the contained MALPublishers.
     * @throws MALException If thrown by the contained MALPublishers.
     */
    public void register(final IdentifierList keys, final MALPublishInteractionListener listener)
            throws java.lang.IllegalArgumentException, MALInteractionException, MALException {
        for (MALPublisher publisher : subpublishers) {
            publisher.register(keys, listener);
        }
    }

    /**
     * The method ‘publish’ publishes updates through all the MALPublishers of
     * this MALPublisherSet.
     *
     * @param updateHeader Published UpdateHeader.
     * @param updateValues The published values of the Update message.
     * @return The message sent.
     * @throws java.lang.IllegalArgumentException If updateHeader is null.
     * @throws MALInteractionException If thrown by the contained MALPublishers.
     * @throws MALException If thrown by the contained MALPublishers.
     */
    public MALMessage publish(final UpdateHeader updateHeader, final Object... updateValues)
            throws java.lang.IllegalArgumentException, MALInteractionException, MALException {
        MALMessage msg = null;
        for (MALPublisher p : subpublishers) {
            msg = p.publish(updateHeader, updateValues);
        }

        return msg;
    }

    /**
     * The method synchronously deregisters through all the MALPublishers of
     * this MALPublisherSet.
     *
     * @throws MALInteractionException If thrown by the contained MALPublishers.
     * @throws MALException If thrown by the contained MALPublishers.
     */
    public void deregister() throws MALInteractionException, MALException {
        for (MALPublisher p : subpublishers) {
            p.deregister();
        }
    }

    /**
     * The method asynchronously registers through all the MALPublishers of this
     * MALPublisherSet.
     *
     * @param keys Keys of the subscriptions that are to be published
     * @param listener Listener in charge of receiving the messages PUBLISH
     * REGISTER ACK, PUBLISH REGISTER ERROR and PUBLISH ERROR
     * @return The message sent.
     * @throws java.lang.IllegalArgumentException If entityKeyList or listener
     * are null.
     * @throws MALInteractionException If thrown by the contained MALPublishers.
     * @throws MALException If thrown by the contained MALPublishers.
     */
    public MALMessage asyncRegister(final IdentifierList keys, final MALPublishInteractionListener listener)
            throws java.lang.IllegalArgumentException, MALInteractionException, MALException {
        MALMessage msg = null;
        for (MALPublisher p : subpublishers) {
            msg = p.asyncRegister(keys, listener);
        }

        return msg;
    }

    /**
     * The method asynchronously deregisters through all the MALPublishers of
     * this MALPublisherSet.
     *
     * @param listener Listener in charge of receiving the messages PUBLISH
     * DEREGISTER ACK
     * @return The message sent.
     * @throws java.lang.IllegalArgumentException If listener is null.
     * @throws MALInteractionException If thrown by the contained MALPublishers.
     * @throws MALException If thrown by the contained MALPublishers.
     */
    public MALMessage asyncDeregister(final MALPublishInteractionListener listener)
            throws java.lang.IllegalArgumentException, MALInteractionException, MALException {
        MALMessage msg = null;
        for (MALPublisher p : subpublishers) {
            msg = p.asyncDeregister(listener);
        }

        return msg;
    }

    /**
     * The method closes all the MALPublishers of this MALPublisherSet.
     *
     * @throws MALException If an error occurs.
     */
    public void close() throws MALException {
        //ToDo
    }
}
