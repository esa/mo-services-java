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

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALNotifyBody;

/**
 * The MALInteractionListener interface enables a consumer to asynchronously
 * receive a message.
 */
public interface MALInteractionListener {

    /**
     * Receives a SUBMIT ACK message.
     *
     * @param header The header of the message as exchanged between the provider
     * and the consumer.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    void submitAckReceived(MALMessageHeader header, Map qosProperties)
            throws MALException;

    /**
     * Receives a SUBMIT ERROR message.
     *
     * @param header The header of the message as exchanged between the provider
     * and the consumer.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    void submitErrorReceived(MALMessageHeader header, MALErrorBody body,
            Map qosProperties) throws MALException;

    /**
     * Receives a REQUEST RESPONSE message.
     *
     * @param header The header of the message as exchanged between the provider
     * and the consumer.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    void requestResponseReceived(MALMessageHeader header, MALMessageBody body,
            Map qosProperties) throws MALException;

    /**
     * Receives a REQUEST ERROR message.
     *
     * @param header The header of the message as exchanged between the provider
     * and the consumer.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    void requestErrorReceived(MALMessageHeader header, MALErrorBody body,
            Map qosProperties) throws MALException;

    /**
     * Receives a INVOKE ACK message.
     *
     * @param header The header of the message as exchanged between the provider
     * and the consumer.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    void invokeAckReceived(MALMessageHeader header, MALMessageBody body,
            Map qosProperties) throws MALException;

    /**
     * Receives a INVOKE ACK ERROR message.
     *
     * @param header The header of the message as exchanged between the provider
     * and the consumer.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    void invokeAckErrorReceived(MALMessageHeader header, MALErrorBody body,
            Map qosProperties) throws MALException;

    /**
     * Receives a INVOKE RESPONSE message.
     *
     * @param header The header of the message as exchanged between the provider
     * and the consumer.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    void invokeResponseReceived(MALMessageHeader header, MALMessageBody body,
            Map qosProperties) throws MALException;

    /**
     * Receives a INVOKE RESPONSE ERROR message.
     *
     * @param header The header of the message as exchanged between the provider
     * and the consumer.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    void invokeResponseErrorReceived(MALMessageHeader header, MALErrorBody body,
            Map qosProperties) throws MALException;

    /**
     * Receives a PROGRESS ACK message.
     *
     * @param header The header of the message as exchanged between the provider
     * and the consumer.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    void progressAckReceived(MALMessageHeader header, MALMessageBody body,
            Map qosProperties) throws MALException;

    /**
     * Receives a PROGRESS ACK ERROR message.
     *
     * @param header The header of the message as exchanged between the provider
     * and the consumer.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    void progressAckErrorReceived(MALMessageHeader header, MALErrorBody body,
            Map qosProperties) throws MALException;

    /**
     * Receives a PROGRESS UPDATE message.
     *
     * @param header The header of the message as exchanged between the provider
     * and the consumer.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    void progressUpdateReceived(MALMessageHeader header, MALMessageBody body,
            Map qosProperties) throws MALException;

    /**
     * Receives a PROGRESS UPDATE ERROR message.
     *
     * @param header The header of the message as exchanged between the provider
     * and the consumer.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    void progressUpdateErrorReceived(MALMessageHeader header, MALErrorBody body,
            Map qosProperties) throws MALException;

    /**
     * Receives a PROGRESS RESPONSE message.
     *
     * @param header The header of the message as exchanged between the provider
     * and the consumer.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    void progressResponseReceived(MALMessageHeader header, MALMessageBody body,
            Map qosProperties) throws MALException;

    /**
     * Receives a PROGRESS RESPONSE ERROR message.
     *
     * @param header The header of the message as exchanged between the provider
     * and the consumer.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    void progressResponseErrorReceived(MALMessageHeader header,
            MALErrorBody body, Map qosProperties) throws MALException;

    /**
     * Receives a REGISTER ACK message.
     *
     * @param header The header of the message as exchanged between the provider
     * and the consumer.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    void registerAckReceived(MALMessageHeader header, Map qosProperties)
            throws MALException;

    /**
     * Receives a REGISTER ERROR message.
     *
     * @param header The header of the message as exchanged between the provider
     * and the consumer.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    void registerErrorReceived(MALMessageHeader header, MALErrorBody body,
            Map qosProperties) throws MALException;

    /**
     * Receives a NOTIFY message. The selectedKeys define the order and the
     * subset of the Subscription Key values carried in the UpdateHeader when the
     * consumer enabled trimming (see CCSDS 521.0-B-3, section 3.6.6.5). When
     * trimming was not enabled, selectedKeys is null and the key values follow
     * the order defined by the operation.
     *
     * @param header The header of the message as exchanged between the provider
     * and the consumer.
     * @param body The body of the message.
     * @param selectedKeys The selected Subscription Key names of the
     * subscription, or null if trimming was not enabled.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    void notifyReceived(MALMessageHeader header, MALNotifyBody body,
            IdentifierList selectedKeys, Map qosProperties) throws MALException;

    /**
     * Receives a NOTIFY ERROR message.
     *
     * @param header The header of the message as exchanged between the provider
     * and the consumer.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    void notifyErrorReceived(MALMessageHeader header, MALErrorBody body,
            Map qosProperties) throws MALException;

    /**
     * Receives a DEREGISTER ACK message.
     *
     * @param header The header of the message as exchanged between the provider
     * and the consumer.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    void deregisterAckReceived(MALMessageHeader header, Map qosProperties)
            throws MALException;
}
