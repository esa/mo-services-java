/* ----------------------------------------------------------------------------
 * Copyright (C) 2015      European Space Agency
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
import esa.mo.mal.transport.gen.Transport;
import esa.mo.mal.transport.gen.PacketToString;
import org.ccsds.moims.mo.mal.MALException;
import esa.mo.mal.transport.gen.ReceptionHandler;

/**
 * Factory class for byte decoders.
 */
public class ByteMessageDecoderFactory<O> implements MessageDecoderFactory<byte[], O> {

    @Override
    public GENIncomingMessageDecoder createDecoder(Transport<byte[], O> transport,
            ReceptionHandler receptionHandler, byte[] messageSource) {
        return new GENIncomingByteMessageDecoder(transport, messageSource);
    }

    /**
     * Implementation of the GENIncomingMessageDecoder class for newly arrived
     * MAL Messages in byte array format.
     */
    public static final class GENIncomingByteMessageDecoder<O> implements GENIncomingMessageDecoder {

        private final Transport<byte[], O> transport;
        private final byte[] rawMessage;

        /**
         * Constructor
         *
         * @param transport Containing transport.
         * @param rawMessage The raw message
         */
        public GENIncomingByteMessageDecoder(final Transport<byte[], O> transport, byte[] rawMessage) {
            this.transport = transport;
            this.rawMessage = rawMessage;
        }

        @Override
        public IncomingMessageHolder decodeAndCreateMessage() throws MALException {
            PacketToString smsg = new PacketToString(rawMessage);
            GENMessage malMsg = transport.createMessage(rawMessage);
            return new IncomingMessageHolder(malMsg.getHeader().getTransactionId(), malMsg, smsg);
        }
    }
}
