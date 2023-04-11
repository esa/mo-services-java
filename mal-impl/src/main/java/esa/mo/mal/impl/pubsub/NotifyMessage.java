/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java Implementation
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
package esa.mo.mal.impl.pubsub;

/**
 * The NotifyMessageSet holds a set of notify message for a single subscription
 */
public final class NotifyMessage {

    /**
     * Message header.
     */
    private final NotifyMessageHeader header;

    /**
     * Message bodies.
     */
    private final NotifyMessageBody body;

    public NotifyMessage(NotifyMessageHeader header, NotifyMessageBody body) {
        this.header = header;
        this.body = body;
    }

    /**
     * Returns the header of the Notify message.
     *
     * @return The header of the Notify message.
     */
    public NotifyMessageHeader getHeader() {
        return header;
    }

    /**
     * Returns the body of the Notify message.
     *
     * @return The body of the Notify message.
     */
    public NotifyMessageBody getBody() {
        return body;
    }
}
