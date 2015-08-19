/* ----------------------------------------------------------------------------
 * Copyright (C) 2015      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO SPP Transport Framework
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
package esa.mo.mal.transport.spp;

import esa.mo.mal.transport.gen.GENMessage;
import esa.mo.mal.transport.gen.GENMessageHeader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;

/**
 * SPP message class.
 */
public class SPPMessage extends GENMessage
{
  /**
   * Constructor.
   *
   * @param wrapBodyParts True if the encoded body parts should be wrapped in BLOBs.
   * @param header The message header to use.
   * @param qosProperties The QoS properties for this message.
   * @param operation The details of the operation being encoding, can be null.
   * @param body the body of the message.
   * @throws org.ccsds.moims.mo.mal.MALInteractionException If the operation is unknown.
   */
  public SPPMessage(boolean wrapBodyParts, GENMessageHeader header, Map qosProperties, MALOperation operation, Object... body) throws MALInteractionException
  {
    super(wrapBodyParts, header, qosProperties, operation, body);
  }

  /**
   * Constructor.
   *
   * @param wrapBodyParts True if the encoded body parts should be wrapped in BLOBs.
   * @param readHeader True if the header should be read from the packet.
   * @param header An instance of the header class to use.
   * @param qosProperties The QoS properties for this message.
   * @param packet The message in encoded form.
   * @param encFactory The stream factory to use for decoding.
   * @throws MALException On decoding error.
   */
  public SPPMessage(boolean wrapBodyParts, boolean readHeader, GENMessageHeader header, Map qosProperties, byte[] packet, MALElementStreamFactory encFactory) throws MALException
  {
    super(wrapBodyParts, readHeader, header, qosProperties, packet, encFactory);
  }

  /**
   * Constructor.
   *
   * @param wrapBodyParts True if the encoded body parts should be wrapped in BLOBs.
   * @param readHeader True if the header should be read from the stream.
   * @param header An instance of the header class to use.
   * @param qosProperties The QoS properties for this message.
   * @param ios The message in encoded form.
   * @param encFactory The stream factory to use for decoding.
   * @throws MALException On decoding error.
   */
  public SPPMessage(boolean wrapBodyParts, boolean readHeader, GENMessageHeader header, Map qosProperties, InputStream ios, MALElementStreamFactory encFactory) throws MALException
  {
    super(wrapBodyParts, readHeader, header, qosProperties, ios, encFactory);
  }

  @Override
  public void encodeMessage(final MALElementStreamFactory streamFactory,
          final MALElementOutputStream enc,
          final OutputStream lowLevelOutputStream,
          final boolean writeHeader) throws MALException
  {
    try
    {
      final ByteArrayOutputStream baos = new ByteArrayOutputStream();
      final MALElementOutputStream lenc = streamFactory.createOutputStream(baos);

      super.encodeMessage(streamFactory, lenc, baos, writeHeader);

      // encode the SPP packet, then set the CCSDS packet length to the correct value.
      byte[] buf = baos.toByteArray();
      java.nio.ByteBuffer.wrap(buf).putShort(4, (short) (buf.length - 7));
      lowLevelOutputStream.write(buf);
    }
    catch (IOException ex)
    {
      throw new MALException("Internal error encoding message", ex);
    }
  }
}
