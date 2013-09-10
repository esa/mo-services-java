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
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;

/**
 * Extends the MALElementInputStream interface to enable aware transport access to the encoded data stream.
 */
public interface GENElementInputStream extends MALElementInputStream
{
  /**
   * Returns a new byte array containing the remaining encoded data for this stream. Expected to be used for creating an
   * MAL encoded body object.
   *
   * @return a byte array containing the remaining encoded data for this stream.
   * @throws MALException On error.
   */
  public byte[] getRemainingEncodedData() throws MALException;
}
