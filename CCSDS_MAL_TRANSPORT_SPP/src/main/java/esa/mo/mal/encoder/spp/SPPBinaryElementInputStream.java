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
package esa.mo.mal.encoder.spp;

/**
 * Implements the MALElementInputStream interface for a fixed length binary encoding.
 */
public class SPPBinaryElementInputStream extends esa.mo.mal.encoder.binary.fixed.FixedBinaryElementInputStream
{
  /**
   * Constructor.
   *
   * @param is Input stream to read from.
   * @param smallLengthField True if length field is 16bits, otherwise assumed to be 32bits.
   */
  public SPPBinaryElementInputStream(final java.io.InputStream is, final boolean smallLengthField)
  {
    super(new SPPBinaryDecoder(is, smallLengthField));
  }

  /**
   * Constructor.
   *
   * @param buf Byte buffer to read from.
   * @param offset Offset into buffer to start from.
   * @param smallLengthField True if length field is 16bits, otherwise assumed to be 32bits.
   */
  public SPPBinaryElementInputStream(final byte[] buf, final int offset, final boolean smallLengthField)
  {
    super(new SPPBinaryDecoder(buf, offset, smallLengthField));
  }
}
