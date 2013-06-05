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

import java.io.IOException;
import java.io.OutputStream;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.encoding.MALEncodingContext;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * Implements the MALElementOutputStream interface for String encodings.
 */
public class LineElementOutputStream implements MALElementOutputStream
{
  private final OutputStream dos;

  /**
   * Constructor.
   *
   * @param os Output stream to write to.
   */
  public LineElementOutputStream(final OutputStream os)
  {
    this.dos = os;
  }

  @Override
  public void writeElement(final Object element, final MALEncodingContext ctx) throws MALException
  {
    final LineEncoder enc = new LineEncoder();
    enc.encodeTopLevelElement("Body", (Element) element);

    try
    {
      dos.write(enc.toString().getBytes(LineDecoder.UTF8_CHARSET));
    }
    catch (Exception ex)
    {
      throw new MALException(ex.getLocalizedMessage(), ex);
    }
  }

  @Override
  public void flush() throws MALException
  {
    try
    {
      dos.flush();
    }
    catch (IOException ex)
    {
      throw new MALException("IO exception flushing Element stream", ex);
    }
  }

  @Override
  public void close() throws MALException
  {
    try
    {
      dos.close();
    }
    catch (IOException ex)
    {
      throw new MALException(ex.getLocalizedMessage(), ex);
    }
  }
}
