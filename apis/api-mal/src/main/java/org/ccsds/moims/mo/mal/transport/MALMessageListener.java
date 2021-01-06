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

/**
 * The MALMessageListener interface enables the MAL layer to be notified by the
 * transport module when a MALMessage has been received by a MALEndpoint or an
 * asynchronous internal error has been raised by the transport layer as a
 * consequence of a severe failure making the transport unable to work.
 *
 */
public interface MALMessageListener extends MALTransmitErrorListener {

    /**
     * The method receives a message.
     *
     * @param callingEndpoint MALEndpoint calling the MALMessageListener
     * @param msg Message received by the listener
     */
    void onMessage(MALEndpoint callingEndpoint, MALMessage msg);

    /**
     * The method receives multiple messages.
     *
     * @param callingEndpoint MALEndpoint calling the MALMessageListener
     * @param msgList List of the messages received by the listener
     */
    void onMessages(MALEndpoint callingEndpoint, MALMessage[] msgList);

    /**
     * The method receives an internal error.
     *
     * @param callingEndpoint MALEndpoint calling the MALMessageListener
     * @param err Error to be received by the listener
     */
    void onInternalError(MALEndpoint callingEndpoint, Throwable err);
}
