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

/**
 * The MALMessage interface gives a generic access to the transport specific
 * messages.
 */
public interface MALMessage {

    /**
     * Returns the message header.
     *
     * @return The message header.
     */
    MALMessageHeader getHeader();

    /**
     * Returns the message body.
     *
     * @return The message body.
     */
    MALMessageBody getBody();

    /**
     * Returns the message QoS properties.
     *
     * @return The message QoS properties.
     */
    Map getQoSProperties();

    /**
     * The method enables the transport to free resources owned by the
     * MALMessage.
     *
     * @throws MALException If an internal error occurs
     */
    void free() throws MALException;
}
