/* ----------------------------------------------------------------------------
 * Copyright (C) 2017      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO ZMTP Transport Framework
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
package esa.mo.mal.encoder.zmtp.header;

import esa.mo.mal.encoder.binary.base.BinaryTimeHandler;
import esa.mo.mal.encoder.binary.fixed.FixedBinaryDecoder;
import esa.mo.mal.transport.zmtp.ZMTPTransport;
import java.util.List;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALListDecoder;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;

public class ZMTPHeaderDecoder extends FixedBinaryDecoder
{

  /**
   * ZMTP transport that created the decoder - used for MDK decoding.
   */
  protected ZMTPTransport transport;

  /**
   * Constructor.
   *
   * @param src       Byte array to read from.
   * @param transport Parent transport
   */
  public ZMTPHeaderDecoder(final byte[] src, final ZMTPTransport transport,
      final BinaryTimeHandler timeHandler)
  {
    super(src, timeHandler, false);
    this.transport = transport;
  }

  /**
   * Constructor.
   *
   * @param is        Input stream to read from.
   * @param transport Parent transport
   */
  public ZMTPHeaderDecoder(final java.io.InputStream is, final ZMTPTransport transport,
      final BinaryTimeHandler timeHandler)
  {
    super(is, timeHandler, false);
    this.transport = transport;
  }

  /**
   * Constructor.
   *
   * @param src       Byte array to read from.
   * @param offset    index in array to start reading from.
   * @param transport Parent transport
   */
  public ZMTPHeaderDecoder(final byte[] src, final int offset, final ZMTPTransport transport,
      final BinaryTimeHandler timeHandler)
  {
    super(src, offset, timeHandler, false);
    this.transport = transport;
  }

  /**
   * Constructor.
   *
   * @param src       Source buffer holder to use.
   * @param transport Parent transport
   */
  protected ZMTPHeaderDecoder(final FixedBinaryBufferHolder src, final ZMTPTransport transport,
      final BinaryTimeHandler timeHandler)
  {
    super(src, timeHandler);
    this.transport = transport;
  }

  @Override
  public MALListDecoder createListDecoder(final List list) throws MALException
  {
    return new ZMTPHeaderListDecoder(list, (FixedBinaryBufferHolder) sourceBuffer, transport,
        timeHandler);
  }

  /**
   * Reads Varint-encoded UInteger
   *
   * @return Decoded UInteger
   * @throws MALException
   */
  public UInteger decodeVariableUInteger() throws MALException
  {
    return new UInteger(getVariableUnsigned());
  }

  /**
   * Reads Varint-encoded Integer
   *
   * @return Decoded Integer
   * @throws MALException
   */
  public Integer decodeVariableInteger() throws MALException
  {
    return (int) getVariableSigned();
  }

  /**
   * Reads unsigned Varint
   *
   * @return Decoded unsigned Varint stored as long
   * @throws MALException
   */
  public long getVariableUnsigned() throws MALException
  {
    long value = 0L;
    int i = 0;
    long b;
    while (((b = sourceBuffer.get8()) & 0x80L) != 0) {
      value |= (b & 0x7F) << i;
      i += 7;
    }
    return value | (b << i);
  }

  /**
   * Reads signed Varint
   *
   * @return Decoded signed Varint stored as long
   * @throws MALException
   */
  public int getVariableSigned() throws MALException
  {
    final long raw = getVariableUnsigned();
    final long temp = (((raw << 63) >> 63) ^ raw) >> 1;
    return (int) (temp ^ (raw & (1L << 63)));
  }

  /**
   * Implements string decoding using Mapping Directory
   *
   * @return Decoded string.
   * @throws MALException In case of decoding error (e.g. missing MDK entry)
   */
  @Override
  public String decodeString() throws MALException
  {
    int lengthOrMDK = getVariableSigned();
    String ret;
    if (lengthOrMDK < 0) {
      int mdk = -lengthOrMDK;
      ret = transport.stringMappingDirectory.getValue(mdk);
      if (ret == null) {
        throw new MALException("Cannot resolve String MDK " + mdk + ". Missing directory entry.");
      }
    } else {
      ret = new String(sourceBuffer.directGetBytes(lengthOrMDK));
    }
    return ret;
  }

  @Override
  public Identifier decodeIdentifier() throws MALException
  {
    return new Identifier(decodeString());
  }

  @Override
  public URI decodeURI() throws MALException
  {
    return new URI(decodeString());
  }

  @Override
  public String decodeNullableString() throws MALException
  {
    if (sourceBuffer.isNotNull()) {
      return decodeString();
    }
    return null;
  }

  @Override
  public Identifier decodeNullableIdentifier() throws MALException
  {
    final String s = decodeNullableString();
    if (null != s) {
      return new Identifier(s);
    }
    return null;
  }

  @Override
  public URI decodeNullableURI() throws MALException
  {
    final String s = decodeNullableString();
    if (null != s) {
      return new URI(s);
    }
    return null;
  }

  @Override
  public Blob decodeBlob() throws MALException
  {
    final int len = (int) getVariableUnsigned();
    if (len < 0) {
      throw new MALException("Negative blob length: " + len);
    }
    return new Blob(sourceBuffer.directGetBytes(len));
  }
}
