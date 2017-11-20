/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Fixed Length Binary encoder
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
package esa.mo.mal.encoder.binary.fixed;

import esa.mo.mal.encoder.binary.base.BinaryTimeHandler;

/**
 * Implements the MALElementInputStream interface for a fixed length binary encoding.
 */
public class FixedBinaryElementInputStream extends esa.mo.mal.encoder.binary.base.BaseBinaryElementInputStream
{
  /**
   * Constructor.
   *
   * @param is Input stream to read from.
   * @param timeHandler Time handler to use.
   * @param shortLengthField True if length field is 16-bit wide, otherwise
   * assumed to be 32-bit.
   */
  public FixedBinaryElementInputStream(final java.io.InputStream is,
          final BinaryTimeHandler timeHandler,
          final boolean shortLengthField)
  {
    super(new FixedBinaryDecoder(is, timeHandler, shortLengthField));
  }

  /**
   * Constructor.
   *
   * @param buf Byte buffer to read from.
   * @param offset Offset into buffer to start from.
   * @param timeHandler Time handler to use.
   * @param shortLengthField True if length field is 16-bit wide, otherwise
   * assumed to be 32-bit.
   */
  public FixedBinaryElementInputStream(final byte[] buf,
          final int offset,
          final BinaryTimeHandler timeHandler,
          final boolean shortLengthField)
  {
    super(new FixedBinaryDecoder(buf, offset, timeHandler, shortLengthField));
  }
  /**
   * Sub class constructor.
   *
   * @param pdec Decoder to use.
   */
  protected FixedBinaryElementInputStream(FixedBinaryDecoder pdec)
  {
    super(pdec);
  }
}
