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
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.structures.*;

/**
 * The MALEndpoint interface sends and receive MALMessages. A new MALEndpoint is
 * created when the MAL layer needs to allocate a new URI. This happens when
 * creating a MALConsumer, MALProvider, or a MALBrokerBinding owned by a
 * MALBroker. In those cases, the URI of the MALConsumer, the MALProvider, or
 * the MALBrokerBinding is the one owned by its MALEndpoint. A MALEndpoint is a
 * MALMessage factory.
 *
 */
public interface MALEndpoint {

    /**
     * The method explicitly starts the message delivery.
     *
     * @throws MALException If the MALEndpoint is closed
     */
    void startMessageDelivery() throws MALException;

    /**
     * The method stops the message delivery.
     *
     * @throws MALException If the MALEndpoint is closed
     */
    void stopMessageDelivery() throws MALException;

    /**
     * The method instantiates a new MALMessage object.
     *
     * @param authenticationId Authentication identifier of the message
     * @param uriTo URI of the message destination
     * @param timestamp Timestamp of the message
     * @param interactionType Interaction type of the operation
     * @param interactionStage Interaction stage of the interaction
     * @param transactionId Transaction identifier of the interaction, may be
     * null.
     * @param serviceAreaNumber Area number of the service
     * @param serviceNumber Service number
     * @param operationNumber Operation number
     * @param areaVersion Area version number
     * @param isErrorMessage Flag indicating if the message conveys an error
     * @param supplements The header supplements
     * @param qosProperties QoS properties of the message, may be null.
     * @param body Message body elements
     * @return The created message.
     * @throws java.lang.IllegalArgumentException If any of the parameters
     * except ‘transactionId’ or ‘qosProperties’ are NULL
     * @throws MALException If the MALEndpoint is closed
     */
    MALMessage createMessage(
            Blob authenticationId,
            URI uriTo,
            Time timestamp,
            InteractionType interactionType,
            UOctet interactionStage,
            Long transactionId,
            UShort serviceAreaNumber,
            UShort serviceNumber,
            UShort operationNumber,
            UOctet areaVersion,
            Boolean isErrorMessage,
            NamedValueList supplements,
            Map qosProperties,
            Object... body)
            throws java.lang.IllegalArgumentException, MALException;

    /**
     * The method instantiates a new MALMessage object.
     *
     * @param authenticationId Authentication identifier of the message
     * @param uriTo URI of the message destination
     * @param timestamp Timestamp of the message
     * @param interactionType Interaction type of the operation
     * @param interactionStage Interaction stage of the interaction
     * @param transactionId Transaction identifier of the interaction, may be
     * null.
     * @param serviceAreaNumber Area number of the service
     * @param serviceNumber Service number
     * @param operationNumber Operation number
     * @param areaVersion Area version number
     * @param isErrorMessage Flag indicating if the message conveys an error
     * @param supplements The header supplements
     * @param qosProperties QoS properties of the message, may be null.
     * @param body The already encoded message body
     * @return The created message.
     * @throws java.lang.IllegalArgumentException If any of the parameters
     * except ‘transactionId’ or ‘qosProperties’ are NULL
     * @throws MALException If the MALEndpoint is closed
     */
    MALMessage createMessage(
            Blob authenticationId,
            URI uriTo,
            Time timestamp,
            InteractionType interactionType,
            UOctet interactionStage,
            Long transactionId,
            UShort serviceAreaNumber,
            UShort serviceNumber,
            UShort operationNumber,
            UOctet areaVersion,
            Boolean isErrorMessage,
            NamedValueList supplements,
            Map qosProperties,
            MALEncodedBody body)
            throws java.lang.IllegalArgumentException, MALException;

    /**
     * The method sends a MALMessage.
     *
     * @param msg The message to be sent.
     * @throws java.lang.IllegalArgumentException If the parameter is NULL
     * @throws MALTransmitErrorException If a TRANSMIT ERROR occurs
     * @throws MALException If the MALEndpoint is closed
     */
    void sendMessage(MALMessage msg) throws java.lang.IllegalArgumentException, 
            MALTransmitErrorException, MALException;

    /**
     * The method sends a list of MALMessages. Throws
     * MALTransmitMultipleErrorException If a MULTIPLETRANSMIT ERROR occurs
     *
     * @param msgList List of messages to send.
     * @throws java.lang.IllegalArgumentException If the parameter is NULL
     * @throws MALException If the MALEndpoint is closed
     */
    void sendMessages(MALMessage[] msgList)
            throws java.lang.IllegalArgumentException, MALException;

    /**
     * The method sets the listener for delivered messages.
     *
     * @param listener MALMessageListener in charge of receiving the MALMessages
     * @throws MALException If the MALEndpoint is closed
     */
    void setMessageListener(MALMessageListener listener) throws MALException;

    /**
     * Return the URI of the MALEndpoint.
     *
     * @return the URI.
     */
    URI getURI();

    /**
     * Return the local name of the MALEndpoint.
     *
     * @return The local name.
     */
    String getLocalName();

    /**
     * The method releases the resources owned by a MALEndpoint.
     *
     * @throws MALException If an internal error occurs
     */
    void close() throws MALException;
}
