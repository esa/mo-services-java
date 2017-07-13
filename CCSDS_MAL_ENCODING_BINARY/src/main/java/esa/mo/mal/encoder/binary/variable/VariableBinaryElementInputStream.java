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
package esa.mo.mal.encoder.binary.variable;

/**
 * Implements the MALElementInputStream interface for a fixed length binary encoding.
 */
public class VariableBinaryElementInputStream extends esa.mo.mal.encoder.binary.BinaryElementInputStream
{
  /**
   * Constructor.
   *
   * @param is Input stream to read from.
   */
  public VariableBinaryElementInputStream(final java.io.InputStream is)
  {
    super(new VariableBinaryDecoder(is));
  }

  /**
   * Constructor.
   *
   * @param buf Byte buffer to read from.
   * @param offset Offset into buffer to start from.
   */
  public VariableBinaryElementInputStream(final byte[] buf, final int offset)
  {
    super(new VariableBinaryDecoder(buf, offset));
  }

  /**
   * Sub class constructor.
   *
   * @param pdec Decoder to use.
   */
  protected VariableBinaryElementInputStream(VariableBinaryDecoder pdec)
  {
    super(pdec);
  }
}
