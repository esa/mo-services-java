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

import java.io.OutputStream;

/**
 * Implements the MALEncoder and MALListEncoder interfaces for a fixed length binary encoding.
 */
public class VariableBinaryEncoder extends esa.mo.mal.encoder.binary.BinaryEncoder
{
  /**
   * Constructor.
   *
   * @param os Output stream to write to.
   */
  public VariableBinaryEncoder(final OutputStream os)
  {
    super(new VariableStreamHolder(os));
  }

  /**
   * Constructor for derived classes that have their own stream holder implementation that should be used.
   *
   * @param os Output stream to write to.
   */
  protected VariableBinaryEncoder(final StreamHolder os)
  {
    super(os);
  }

  /**
   * Extends the StreamHolder class for handling fixed length, non-zig-zag encoded, fields.
   */
  public static class VariableStreamHolder extends BinaryStreamHolder
  {
    /**
     * Constructor.
     * 
     * @param outputStream The output stream to encode into.
     */
    public VariableStreamHolder(OutputStream outputStream)
    {
      super(outputStream);
    }
  }
}
