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

import java.util.List;

/**
 * The NotifyMessageSet holds a set of notify message for a single subscription
 */
public final class NotifyMessageSet {

    /**
     * Message header.
     */
    private final NotifyMessageHeader header;

    /**
     * Message bodies.
     */
    private final List<NotifyMessageBody> bodies;

    public NotifyMessageSet(NotifyMessageHeader header, List<NotifyMessageBody> bodies) {
        this.header = header;
        this.bodies = bodies;
    }

    public NotifyMessageHeader getDetails() {
        return header;
    }

    public List<NotifyMessageBody> getBodies() {
        return bodies;
    }

}
