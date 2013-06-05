/* ----------------------------------------------------------------------------
 * (C) 2011      European Space Agency
 *               European Space Operations Centre
 *               Darmstadt Germany
 * ----------------------------------------------------------------------------
 * System       : CCSDS MO Line encoder
 * Author       : Sam Cooper
 *
 * ----------------------------------------------------------------------------
 */
package esa.mo.mal.encoder.line;

import java.io.InputStream;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALEncodingContext;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * Implements the MALElementInputStream interface for String encodings.
 */
public class LineElementInputStream implements MALElementInputStream
{
  private final LineDecoder dec;

  /**
   * Constructor.
   * @param is Input stream to read from.
   */
  public LineElementInputStream(final InputStream is)
  {
    dec = new LineDecoder(is);
  }

  @Override
  public Object readElement(final Object element, final MALEncodingContext ctx)
          throws IllegalArgumentException, MALException
  {
    return dec.decodeNullableElement((Element) element);
  }

  @Override
  public void close() throws MALException
  {
  }
}
