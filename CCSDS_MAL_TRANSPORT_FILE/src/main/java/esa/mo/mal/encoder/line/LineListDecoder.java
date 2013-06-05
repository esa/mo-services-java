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

import java.util.List;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALListDecoder;

/**
 * The implementation of the MALListDecoder interfaces for the String encoding.
 */
public class LineListDecoder extends LineDecoder implements MALListDecoder
{
  private final int size;
  private final List list;

  /**
   * Constructor.
   *
   * @param list List to decode into.
   * @param inputStream Input stream to read from.
   * @param srcBuffer Buffer to manage.
   * @throws MALException If cannot decode size of list.
   */
  public LineListDecoder(final List list, final java.io.InputStream inputStream, final BufferHolder srcBuffer)
          throws MALException
  {
    super(inputStream, srcBuffer);

    this.list = list;
    size = decodeInteger().intValue();
  }

  @Override
  public boolean hasNext()
  {
    return list.size() < size;
  }
}
