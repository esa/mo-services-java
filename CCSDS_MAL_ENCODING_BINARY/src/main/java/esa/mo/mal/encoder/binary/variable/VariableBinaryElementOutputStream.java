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

import esa.mo.mal.encoder.binary.base.BinaryTimeHandler;

/**
 * Implements the MALElementOutputStream interface for a fixed length binary encoding.
 */
public class VariableBinaryElementOutputStream extends esa.mo.mal.encoder.binary.base.BaseBinaryElementOutputStream
{

  /**
   * Constructor.
   *
   * @param os          Output stream to write to.
   * @param timeHandler Time handler to use.
   */
  public VariableBinaryElementOutputStream(final java.io.OutputStream os,
      final BinaryTimeHandler timeHandler)
  {
    super(os, timeHandler);
  }

  @Override
  protected esa.mo.mal.encoder.gen.GENEncoder createEncoder(java.io.OutputStream os)
  {
    return new VariableBinaryEncoder(os, timeHandler);
  }
}
