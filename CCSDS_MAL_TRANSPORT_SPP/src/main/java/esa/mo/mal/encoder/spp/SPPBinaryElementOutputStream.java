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

import esa.mo.mal.encoder.gen.GENEncoder;

/**
 * Implements the MALElementOutputStream interface for a fixed length binary encoding.
 */
public class SPPBinaryElementOutputStream extends esa.mo.mal.encoder.binary.fixed.FixedBinaryElementOutputStream
{
  private final boolean smallLengthField;
  
  /**
   * Constructor.
   *
   * @param os Output stream to write to.
   * @param smallLengthField True if length field is 16bits, otherwise assumed to be 32bits.
   */
  public SPPBinaryElementOutputStream(final java.io.OutputStream os, final boolean smallLengthField)
  {
    super(os);
    
    this.smallLengthField = smallLengthField;
  }

  @Override
  protected GENEncoder createEncoder(java.io.OutputStream os)
  {
    return new SPPBinaryEncoder(os, smallLengthField);
  }
}
