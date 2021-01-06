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

import org.ccsds.moims.mo.mal.MALException;

/**
 * The MALTransmitMultipleErrorException class represents a TRANSMITMULTIPLE
 * ERROR as an exception.
 */
public class MALTransmitMultipleErrorException extends MALException {

    private MALTransmitErrorException[] transmitExceptions;

    /**
     * Constructor.
     *
     * @param transmitExceptions Transmit errors preventing messages to be
     * transmitted
     */
    public MALTransmitMultipleErrorException(final MALTransmitErrorException[] transmitExceptions) {
        super();
        this.transmitExceptions = transmitExceptions;
    }

    /**
     * Returns the Transmit errors raised by the MALMessages which Transmit
     * failed.
     *
     * @return The transmit errors.
     */
    public MALTransmitErrorException[] getTransmitExceptions() {
        return transmitExceptions;
    }
}
