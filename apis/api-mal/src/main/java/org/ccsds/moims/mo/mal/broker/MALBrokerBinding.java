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
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALTransmitErrorListener;

/**
 * The MALBrokerBinding interface represents: a binding of a shared MAL level
 * broker to a transport layer; a transport level broker.
 *
 */
public interface MALBrokerBinding {

    /**
     * Returns the URI of the broker binding.
     *
     * @return the URI.
     */
    URI getURI();

    /**
     * Returns the authentication identifier of the broker.
     *
     * @return The authentication identifier.
     */
    Blob getAuthenticationId();

    /**
     * Sets the authentication identifier of the broker.
     *
     * @param newAuthenticationId The new authentication identifier.
     * @return The previous authentication identifier.
     */
    Blob setAuthenticationId(Blob newAuthenticationId);

    /**
     * The method enables a MALBrokerHandler to send a NOTIFY message to a
     * subscriber. The allowed update list types shall be: a MAL element list; a
     * {@code List<MALEncodedElement>} containing the encoded updates; a List
     * defined by a specific Java mapping extension.
     *
     * @param area Area of the NOTIFY message
     * @param service Service of the NOTIFY message
     * @param operation Operation number of the NOTIFY message
     * @param version Service version of the NOTIFY message
     * @param subscriber Subscriber’s URI
     * @param transactionId Transaction identifier of the NOTIFY message
     * @param domainId Domain of the NOTIFY message
     * @param notifyQosProps QoS properties of the NOTIFY message
     * @param subscriptionId Subscription identifier
     * @param updateHeader Update header
     * @param updateObjects Update objects
     * @return The sent MALMessage.
     * @throws java.lang.IllegalArgumentException If at least one of the
     * arguments, except ‘notifyQoSProps’, is NULL
     * @throws MALInteractionException if a MAL standard error occurs during the
     * message sending
     * @throws MALException thrown if a non-MAL error occurs during the message
     * sending, MALBrokerBinding represents a transport level broker or is
     * linked to a MAL level broker without MALBrokerHandler, or
     * MALBrokerBinding is closed.
     */
    MALMessage sendNotify(
            UShort area,
            UShort service,
            UShort operation,
            UOctet version,
            URI subscriber,
            Long transactionId,
            IdentifierList domainId,
            Map notifyQosProps,
            Identifier subscriptionId,
            UpdateHeader updateHeader,
            Object... updateObjects)
            throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

    /**
     * The method enables a MALBrokerHandler to send a NOTIFY message to a
     * subscriber. The allowed update list types shall be: a MAL element list; a
     * {@code List<MALEncodedElement>} containing the encoded updates; a List
     * defined by a specific Java mapping extension.
     *
     * @param op Operation of the NOTIFY message
     * @param subscriber Subscriber’s URI
     * @param transactionId Transaction identifier of the NOTIFY message
     * @param domainId Domain of the NOTIFY message
     * @param networkZone Network zone of the NOTIFY message
     * @param sessionType Session type of the NOTIFY message
     * @param sessionName Session name of the NOTIFY message
     * @param notifyQos QoS level of the NOTIFY message
     * @param notifyQosProps QoS properties of the NOTIFY message
     * @param notifyPriority Priority of the NOTIFY message
     * @param subscriptionId Subscription identifier
     * @param updateHeader Update header
     * @param updateObjects Update objects
     * @return The sent MALMessage.
     * @throws java.lang.IllegalArgumentException If at least one of the
     * arguments, except ‘notifyQoSProps’, is NULL
     * @throws MALInteractionException if a MAL standard error occurs during the
     * message sending
     * @throws MALException thrown if a non-MAL error occurs during the message
     * sending, MALBrokerBinding represents a transport level broker or is
     * linked to a MAL level broker without MALBrokerHandler, or
     * MALBrokerBinding is closed.
     */
    MALMessage sendNotify(
            MALOperation op,
            URI subscriber,
            Long transactionId,
            IdentifierList domainId,
            Identifier networkZone,
            SessionType sessionType,
            Identifier sessionName,
            QoSLevel notifyQos,
            Map notifyQosProps,
            UInteger notifyPriority,
            Identifier subscriptionId,
            UpdateHeader updateHeader,
            Object... updateObjects)
            throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

    /**
     * The method enables a MALBrokerHandler to send a NOTIFY ERROR message to a
     * subscriber
     *
     * @param area Area of the NOTIFY ERROR message
     * @param service Service of the NOTIFY ERROR message
     * @param operation Operation number of the NOTIFY ERROR message
     * @param version Service version of the NOTIFY ERROR message
     * @param subscriber Subscriber’s URI
     * @param transactionId Transaction identifier of the NOTIFY ERROR message
     * @param domainId Domain of the NOTIFY ERROR message
     * @param networkZone Network zone of the NOTIFY ERROR message
     * @param sessionType Session type of the NOTIFY ERROR message
     * @param sessionName Session name of the NOTIFY ERROR message
     * @param notifyQos QoS level of the NOTIFY ERROR message
     * @param notifyQosProps QoS properties of the NOTIFY ERROR message
     * @param notifyPriority Priority of the NOTIFY ERROR message
     * @param error Body of the NOTIFY ERROR Error message
     * @return The sent MALMessage.
     * @throws java.lang.IllegalArgumentException If at least one of the
     * arguments, except ‘notifyQoSProps’, is NULL
     * @throws MALInteractionException if a MAL standard error occurs during the
     * message sending
     * @throws MALException thrown if a non-MAL error occurs during the message
     * sending, MALBrokerBinding represents a transport level broker or is
     * linked to a MAL level broker without MALBrokerHandler, or
     * MALBrokerBinding is closed.
     */
    MALMessage sendNotifyError(
            UShort area,
            UShort service,
            UShort operation,
            UOctet version,
            URI subscriber,
            Long transactionId,
            IdentifierList domainId,
            Identifier networkZone,
            SessionType sessionType,
            Identifier sessionName,
            QoSLevel notifyQos,
            Map notifyQosProps,
            UInteger notifyPriority,
            MALStandardError error)
            throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

    /**
     * The method enables a MALBrokerHandler to send a NOTIFY ERROR message to a
     * subscriber
     *
     * @param op Operation of the NOTIFY ERROR message
     * @param subscriber Subscriber’s URI
     * @param transactionId Transaction identifier of the NOTIFY ERROR message
     * @param domainId Domain of the NOTIFY ERROR message
     * @param networkZone Network zone of the NOTIFY ERROR message
     * @param sessionType Session type of the NOTIFY ERROR message
     * @param sessionName Session name of the NOTIFY ERROR message
     * @param notifyQos QoS level of the NOTIFY ERROR message
     * @param notifyQosProps QoS properties of the NOTIFY ERROR message
     * @param notifyPriority Priority of the NOTIFY ERROR message
     * @param error Body of the NOTIFY ERROR Error message
     * @return The sent MALMessage.
     * @throws java.lang.IllegalArgumentException If at least one of the
     * arguments, except ‘notifyQoSProps’, is NULL
     * @throws MALInteractionException if a MAL standard error occurs during the
     * message sending
     * @throws MALException thrown if a non-MAL error occurs during the message
     * sending, MALBrokerBinding represents a transport level broker or is
     * linked to a MAL level broker without MALBrokerHandler, or
     * MALBrokerBinding is closed.
     */
    MALMessage sendNotifyError(
            MALOperation op,
            URI subscriber,
            Long transactionId,
            IdentifierList domainId,
            Identifier networkZone,
            SessionType sessionType,
            Identifier sessionName,
            QoSLevel notifyQos,
            Map notifyQosProps,
            UInteger notifyPriority,
            MALStandardError error)
            throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

    /**
     * The method enables a MALBrokerHandler to send a PUBLISH ERROR message to
     * a publisher
     *
     * @param area Area of the PUBLISH ERROR message
     * @param service Service of the PUBLISH ERROR message
     * @param operation Operation number of the PUBLISH ERROR message
     * @param version Service version of the PUBLISH ERROR message
     * @param publisher Publisher’s URI
     * @param transactionId Transaction identifier of the PUBLISH ERROR message
     * @param domainId Domain of the PUBLISH ERROR message
     * @param networkZone Network zone of the PUBLISH ERROR message
     * @param sessionType Session type of the PUBLISH ERROR message
     * @param sessionName Session name of the PUBLISH ERROR message
     * @param qos QoS level of the PUBLISH ERROR message
     * @param qosProps QoS properties of the PUBLISH ERROR message
     * @param priority Priority of the PUBLISH ERROR message
     * @param error Body of the PUBLISH ERROR Error message
     * @return The sent MALMessage.
     * @throws java.lang.IllegalArgumentException If at least one of the
     * arguments, except ‘notifyQoSProps’, is NULL
     * @throws MALInteractionException if a MAL standard error occurs during the
     * message sending
     * @throws MALException thrown if a non-MAL error occurs during the message
     * sending, MALBrokerBinding represents a transport level broker or is
     * linked to a MAL level broker without MALBrokerHandler, or
     * MALBrokerBinding is closed.
     */
    MALMessage sendPublishError(
            UShort area,
            UShort service,
            UShort operation,
            UOctet version,
            URI publisher,
            Long transactionId,
            IdentifierList domainId,
            Identifier networkZone,
            SessionType sessionType,
            Identifier sessionName,
            QoSLevel qos,
            Map qosProps,
            UInteger priority,
            MALStandardError error)
            throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

    /**
     * The method enables a MALBrokerHandler to send a PUBLISH ERROR message to
     * a publisher
     *
     * @param op Operation of the PUBLISH ERROR message
     * @param publisher Publisher’s URI
     * @param transactionId Transaction identifier of the PUBLISH ERROR message
     * @param domainId Domain of the PUBLISH ERROR message
     * @param networkZone Network zone of the PUBLISH ERROR message
     * @param sessionType Session type of the PUBLISH ERROR message
     * @param sessionName Session name of the PUBLISH ERROR message
     * @param qos QoS level of the PUBLISH ERROR message
     * @param qosProps QoS properties of the PUBLISH ERROR message
     * @param priority Priority of the PUBLISH ERROR message
     * @param error Body of the PUBLISH ERROR Error message
     * @return The sent MALMessage.
     * @throws java.lang.IllegalArgumentException If at least one of the
     * arguments, except ‘notifyQoSProps’, is NULL
     * @throws MALInteractionException if a MAL standard error occurs during the
     * message sending
     * @throws MALException thrown if a non-MAL error occurs during the message
     * sending, MALBrokerBinding represents a transport level broker or is
     * linked to a MAL level broker without MALBrokerHandler, or
     * MALBrokerBinding is closed.
     */
    MALMessage sendPublishError(
            MALOperation op,
            URI publisher,
            Long transactionId,
            IdentifierList domainId,
            Identifier networkZone,
            SessionType sessionType,
            Identifier sessionName,
            QoSLevel qos,
            Map qosProps,
            UInteger priority,
            MALStandardError error)
            throws java.lang.IllegalArgumentException, MALInteractionException, MALException;

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
     * The method terminates all pending interactions and deactivates the broker
     * binding.
     *
     * @throws MALException If an internal error occurs
     */
    void close() throws MALException;
}
