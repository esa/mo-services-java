/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
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

import esa.mo.mal.transport.gen.GENMessage;
import esa.mo.mal.transport.gen.PacketToString;

/**
 * Simple structure class for holding related aspects of a decoded MAL message.
 */
public final class IncomingMessageHolder {

    /**
     * The decoded MAL message.
     */
    private final GENMessage malMsg;
    /**
     * A string representation for debug tracing.
     */
    private final PacketToString smsg;

    /**
     * Constructor.
     *
     * @param malMsg The decoded MAL message.
     * @param smsg A string representation for debug tracing.
     */
    public IncomingMessageHolder(final GENMessage malMsg, final PacketToString smsg) {
        this.malMsg = malMsg;
        this.smsg = smsg;
    }

    public GENMessage getMalMsg() {
        return malMsg;
    }

    public PacketToString getSmsg() {
        return smsg;
    }
}
