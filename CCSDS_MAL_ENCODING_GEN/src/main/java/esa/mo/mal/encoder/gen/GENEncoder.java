/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Generic Encoder Framework
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
package esa.mo.mal.encoder.gen;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALListEncoder;

/**
 * Extends the MALEncoder and MALListEncoder interfaces for use in the generic encoding framework.
 */
public abstract class GENEncoder implements MALListEncoder
{
  /**
   * Converts the MAL representation of an Attribute type short form to the representation used by the encoding.
   *
   * @param value The Attribute type short form.
   * @return The byte value used by the encoding
   * @throws MALException On error.
   */
  protected byte internalEncodeAttributeType(byte value) throws MALException
  {
    return value;
  }
}
