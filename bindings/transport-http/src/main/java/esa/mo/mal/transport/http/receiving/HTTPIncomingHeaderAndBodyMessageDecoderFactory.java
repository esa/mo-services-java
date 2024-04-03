/* ----------------------------------------------------------------------------
 * Copyright (C) 2014      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO HTTP Transport Framework
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
package esa.mo.mal.transport.http.receiving;

import esa.mo.mal.transport.gen.GENMessage;
import esa.mo.mal.transport.gen.PacketToString;
import esa.mo.mal.transport.gen.ReceptionHandler;
import esa.mo.mal.transport.gen.Transport;
import esa.mo.mal.transport.gen.receivers.GENIncomingMessageDecoder;
import esa.mo.mal.transport.gen.receivers.IncomingMessageHolder;
import esa.mo.mal.transport.gen.receivers.MessageDecoderFactory;
import esa.mo.mal.transport.http.HTTPTransport;

import org.ccsds.moims.mo.mal.MALException;

/**
 * Factory class for HTTPHeaderAndBody decoders.
 */
public class HTTPIncomingHeaderAndBodyMessageDecoderFactory
    implements MessageDecoderFactory<HTTPHeaderAndBody, byte[]> {

  @Override
  public GENIncomingMessageDecoder createDecoder(Transport<HTTPHeaderAndBody, byte[]> transport,
      ReceptionHandler receptionHandler, HTTPHeaderAndBody messageSource) {
    return new HTTPIncomingHeaderAndBodyMessageDecoder(transport, messageSource);
  }

  /**
   * Implementation of the GENIncomingMessageDecoder class for newly arrived MAL Messages in HTTPHeaderAndBody format.
   */
  public static final class HTTPIncomingHeaderAndBodyMessageDecoder implements GENIncomingMessageDecoder {
    private final HTTPTransport transport;
    private final HTTPHeaderAndBody rawMessage;

    /**
     * Constructor
     *
     * @param transport
     *            Containing transport.
     * @param rawMessage
     *            The raw message
     */
    public HTTPIncomingHeaderAndBodyMessageDecoder(final Transport<HTTPHeaderAndBody, byte[]> transport,
        HTTPHeaderAndBody rawMessage) {
      this.transport = (HTTPTransport) transport;
      this.rawMessage = rawMessage;
    }

    @Override
    public IncomingMessageHolder decodeAndCreateMessage() throws MALException {
      PacketToString smsg = new PacketToString(rawMessage.getEncodedPacketData());
      GENMessage malMsg = transport.createMessage(rawMessage);

      return new IncomingMessageHolder(malMsg.getHeader().getTransactionId(), malMsg, smsg);
    }
  }
}
