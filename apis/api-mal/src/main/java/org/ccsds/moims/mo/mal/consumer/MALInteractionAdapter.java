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
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALNotifyBody;

/**
 * The MALInteractionAdapter abstract class enables a MAL client not to
 * implement all the methods provided by the interface MALInteractionListener.
 */
public abstract class MALInteractionAdapter implements MALInteractionListener {

    /**
     * Receives a SUBMIT ACK message.
     *
     * @param header The header of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    public void submitAckReceived(final MALMessageHeader header,
            final Map qosProperties) throws MALException {
    }

    /**
     * Receives a SUBMIT ERROR message.
     *
     * @param header The header of the message.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    public void submitErrorReceived(final MALMessageHeader header,
            final MALErrorBody body, final Map qosProperties) throws MALException {
    }

    /**
     * Receives a REQUEST RESPONSE message.
     *
     * @param header The header of the message.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    public void requestResponseReceived(final MALMessageHeader header,
            final MALMessageBody body, final Map qosProperties) throws MALException {
    }

    /**
     * Receives a REQUEST ERROR message.
     *
     * @param header The header of the message.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    public void requestErrorReceived(final MALMessageHeader header,
            final MALErrorBody body, final Map qosProperties) throws MALException {
    }

    /**
     * Receives a INVOKE ACK message.
     *
     * @param header The header of the message.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    public void invokeAckReceived(final MALMessageHeader header,
            final MALMessageBody body, final Map qosProperties) throws MALException {
    }

    /**
     * Receives a INVOKE ACK ERROR message.
     *
     * @param header The header of the message.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    public void invokeAckErrorReceived(final MALMessageHeader header,
            final MALErrorBody body, final Map qosProperties) throws MALException {
    }

    /**
     * Receives a INVOKE RESPONSE message.
     *
     * @param header The header of the message.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    public void invokeResponseReceived(final MALMessageHeader header,
            final MALMessageBody body, final Map qosProperties) throws MALException {
    }

    /**
     * Receives a INVOKE RESPONSE ERROR message.
     *
     * @param header The header of the message.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    public void invokeResponseErrorReceived(final MALMessageHeader header,
            final MALErrorBody body, final Map qosProperties) throws MALException {
    }

    /**
     * Receives a PROGRESS ACK message.
     *
     * @param header The header of the message.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    public void progressAckReceived(final MALMessageHeader header,
            final MALMessageBody body, final Map qosProperties) throws MALException {
    }

    /**
     * Receives a PROGRESS ACK ERROR message.
     *
     * @param header The header of the message.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    public void progressAckErrorReceived(final MALMessageHeader header,
            final MALErrorBody body, final Map qosProperties) throws MALException {
    }

    /**
     * Receives a PROGRESS UPDATE message.
     *
     * @param header The header of the message.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    public void progressUpdateReceived(final MALMessageHeader header,
            final MALMessageBody body, final Map qosProperties) throws MALException {
    }

    /**
     * Receives a PROGRESS UPDATE ERROR message.
     *
     * @param header The header of the message.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    public void progressUpdateErrorReceived(final MALMessageHeader header,
            final MALErrorBody body, final Map qosProperties) throws MALException {
    }

    /**
     * Receives a PROGRESS RESPONSE message.
     *
     * @param header The header of the message.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    public void progressResponseReceived(final MALMessageHeader header,
            final MALMessageBody body, final Map qosProperties) throws MALException {
    }

    /**
     * Receives a PROGRESS RESPONSE ERROR message.
     *
     * @param header The header of the message.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    public void progressResponseErrorReceived(final MALMessageHeader header,
            final MALErrorBody body, final Map qosProperties) throws MALException {
    }

    /**
     * Receives a REGISTER ACK message.
     *
     * @param header The header of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    public void registerAckReceived(final MALMessageHeader header,
            final Map qosProperties) throws MALException {
    }

    /**
     * Receives a REGISTER ERROR message.
     *
     * @param header The header of the message.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    public void registerErrorReceived(final MALMessageHeader header,
            final MALErrorBody body, final Map qosProperties) throws MALException {
    }

    /**
     * Receives a NOTIFY message.
     *
     * @param header The header of the message.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    public void notifyReceived(final MALMessageHeader header,
            final MALNotifyBody body, final Map qosProperties) throws MALException {
    }

    /**
     * Receives a NOTIFY ERROR message.
     *
     * @param header The header of the message.
     * @param body The body of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    public void notifyErrorReceived(final MALMessageHeader header,
            final MALErrorBody body, final Map qosProperties) throws MALException {
    }

    /**
     * Receives a DEREGISTER ACK message.
     *
     * @param header The header of the message.
     * @param qosProperties The QoS properties of the message, may be null.
     * @throws MALException If an error occurs
     */
    public void deregisterAckReceived(final MALMessageHeader header,
            final Map qosProperties) throws MALException {
    }
}
