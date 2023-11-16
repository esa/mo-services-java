/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Split Binary encoder
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
package esa.mo.mal.transport.tcpip;

import org.ccsds.moims.mo.mal.MALException;
import esa.mo.mal.transport.gen.GENMessage;
import esa.mo.mal.transport.gen.Transport;
import esa.mo.mal.transport.gen.PacketToString;
import esa.mo.mal.transport.gen.receivers.GENIncomingMessageDecoder;
import esa.mo.mal.transport.gen.receivers.IncomingMessageHolder;
import esa.mo.mal.transport.gen.receivers.MessageDecoderFactory;
import esa.mo.mal.transport.gen.ReceptionHandler;

/**
 *
 * @author Rian van Gijlswijk
 * @param <O> The type of the outgoing messages.
 *
 */
public class TCPIPMessageDecoderFactory<O> implements MessageDecoderFactory<TCPIPPacketInfoHolder, O> {

    @Override
    public GENIncomingMessageDecoder createDecoder(Transport transport,
            ReceptionHandler receptionHandler, TCPIPPacketInfoHolder packetInfo) {
        return new TCPIPMessageDecoder((TCPIPTransport) transport, packetInfo);
    }

    /**
     * The TCPIPMessageDecoder to decode the message.
     */
    public static final class TCPIPMessageDecoder implements GENIncomingMessageDecoder {

        private final TCPIPTransport transport;
        private final TCPIPPacketInfoHolder packetInfo;

        /**
         * The constructor for this class.
         *
         * @param transport The transport.
         * @param packetInfo The packet information.
         */
        public TCPIPMessageDecoder(TCPIPTransport transport, TCPIPPacketInfoHolder packetInfo) {
            this.transport = transport;
            this.packetInfo = packetInfo;
        }

        @Override
        public IncomingMessageHolder decodeAndCreateMessage() throws MALException {
            GENMessage msg = transport.createMessage(packetInfo);
            packetInfo.setPacketData(null);

            if (msg != null) {
                PacketToString smsg = new PacketToString(null);
                return new IncomingMessageHolder(msg.getHeader().getTransactionId(), msg, smsg);
            }

            return null;
        }
    }

}
