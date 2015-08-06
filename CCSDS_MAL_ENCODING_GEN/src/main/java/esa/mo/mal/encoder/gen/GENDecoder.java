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

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALException;

/**
 * Extends the MALDecoder interface for use in the generic encoding framework.
 */
public abstract class GENDecoder implements MALDecoder
{
  protected int internalDecodeAttributeType(byte value) throws MALException
  {
    return value;
  }

  /**
   * Returns the remaining data of the input stream that has not been used for decoding for wrapping in a MALEncodedBody
   * class.
   *
   * @return the unused body data.
   * @throws MALException if there is an error.
   */
  protected abstract byte[] getRemainingEncodedData() throws MALException;
}
