/* ----------------------------------------------------------------------------
 * Copyright (C) 2023      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Generic Transport Framework
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
package esa.mo.mal.transport.gen.receivers;

import java.io.IOException;

/**
 * Simple interface for reading encoded messages from a low level transport.
 * Used by the message poller class.
 *
 * @param <T> The type of the encoded messages.
 */
public interface MessageReceiver<T> {

    /**
     * Reads an encoded MALMessage.
     *
     * @return the object containing the encoded MAL Message, may be null if
     * nothing to read at this time
     * @throws IOException in case the encoded message cannot be read
     * @throws InterruptedException in case IO read is interrupted
     */
    T readEncodedMessage() throws IOException, InterruptedException;

    /**
     * Closes any used resources.
     */
    void close();
}
