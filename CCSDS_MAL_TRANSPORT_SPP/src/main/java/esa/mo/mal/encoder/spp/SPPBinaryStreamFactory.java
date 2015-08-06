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

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;

/**
 * Implements the MALElementStreamFactory interface for a SPP binary encoding.
 */
public class SPPBinaryStreamFactory extends esa.mo.mal.encoder.binary.fixed.FixedBinaryStreamFactory
{
  public static final String SMALL_LENGTH_FIELD = "esa.mo.mal.encoding.spp.smallLengthField";
  private boolean smallLengthField = false;

  @Override
  protected void init(final String protocol, final Map properties) throws IllegalArgumentException, MALException
  {
    super.init(protocol, properties);

    if ((null != properties)
            && properties.containsKey(SMALL_LENGTH_FIELD)
            && Boolean.parseBoolean((String) properties.get(SMALL_LENGTH_FIELD)))
    {
      smallLengthField = true;
    }
  }

  @Override
  public org.ccsds.moims.mo.mal.encoding.MALElementInputStream createInputStream(final byte[] bytes, final int offset)
  {
    return new SPPBinaryElementInputStream(bytes, offset, smallLengthField);
  }

  @Override
  public org.ccsds.moims.mo.mal.encoding.MALElementInputStream createInputStream(final java.io.InputStream is)
          throws org.ccsds.moims.mo.mal.MALException
  {
    return new SPPBinaryElementInputStream(is, smallLengthField);
  }

  @Override
  public org.ccsds.moims.mo.mal.encoding.MALElementOutputStream createOutputStream(final java.io.OutputStream os)
          throws org.ccsds.moims.mo.mal.MALException
  {
    return new SPPBinaryElementOutputStream(os, smallLengthField);
  }
}
