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
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;

/**
 * The MALTransmitErrorException class represents a TRANSMIT ERROR as an
 * exception.
 */
public class MALTransmitErrorException extends MALInteractionException {

    private MALMessageHeader header;
    private Map qosProperties;

    /**
     * Constructor.
     *
     * @param header Header of the MALMessage that cannot be transmitted
     * @param standardError Error preventing the message to be transmitted
     * @param qosProperties QoS properties of the MALMessage which cannot be
     * transmitted
     */
    public MALTransmitErrorException(final MALMessageHeader header,
            final MALStandardError standardError,
            final Map qosProperties) {
        super(standardError);
        this.header = header;
        this.qosProperties = qosProperties;
    }

    /**
     * Returns the header of the MALMessage which cannot be transmitted.
     *
     * @return The header.
     */
    public MALMessageHeader getHeader() {
        return header;
    }

    /**
     * Returns the QoS properties of the MALMessage which cannot be transmitted.
     *
     * @return The properties.
     */
    public Map getQosProperties() {
        return qosProperties;
    }
}
