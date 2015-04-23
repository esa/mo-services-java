/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Binary encoder
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
package esa.mo.mal.encoder.binary;

import esa.mo.mal.encoder.gen.GENElementInputStream;
import java.io.InputStream;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.encoding.MALEncodingContext;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * Implements the MALElementInputStream interface for a binary encoding.
 */
public class BinaryElementInputStream implements GENElementInputStream
{
  private final BinaryDecoder dec;

  /**
   * Constructor.
   *
   * @param is Input stream to read from.
   */
  public BinaryElementInputStream(final InputStream is)
  {
    dec = new BinaryDecoder(is);
  }

  /**
   * Constructor.
   *
   * @param buf Byte buffer to read from.
   * @param offset Offset into buffer to start from.
   */
  public BinaryElementInputStream(final byte[] buf, final int offset)
  {
    dec = new BinaryDecoder(buf, offset);
  }

  /**
   * Sub class constructor.
   *
   * @param pdec Decoder to use.
   */
  protected BinaryElementInputStream(BinaryDecoder pdec)
  {
    dec = pdec;
  }

  @Override
  public Object readElement(final Object element, final MALEncodingContext ctx)
          throws IllegalArgumentException, MALException
  {
    if ((null != ctx) && (element == ctx.getHeader()))
    {
      return dec.decodeElement((Element) element);
    }
    else
    {
      return dec.decodeNullableElement((Element) element);
    }
  }

  @Override
  public byte[] getRemainingEncodedData() throws MALException
  {
    return dec.getRemainingEncodedData();
  }

  @Override
  public void close() throws MALException
  {
  }
}
