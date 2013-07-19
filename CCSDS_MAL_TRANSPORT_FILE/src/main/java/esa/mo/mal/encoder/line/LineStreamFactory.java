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

import java.io.*;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.encoding.MALEncodingContext;
import org.ccsds.moims.mo.mal.structures.Blob;

/**
 * Implementation of the MALElementStreamFactory interface for the String encoding.
 */
public class LineStreamFactory extends MALElementStreamFactory
{
  @Override
  protected void init(final String protocol, final Map properties) throws IllegalArgumentException, MALException
  {
  }

  @Override
  public MALElementInputStream createInputStream(final byte[] bytes, final int offset)
  {
    return new LineElementInputStream(new ByteArrayInputStream(bytes, offset, bytes.length - offset));
  }

  @Override
  public MALElementInputStream createInputStream(final InputStream is) throws MALException
  {
    return new LineElementInputStream(is);
  }

  @Override
  public MALElementOutputStream createOutputStream(final OutputStream os) throws MALException
  {
    return new LineElementOutputStream(os);
  }

  @Override
  public Blob encode(final Object[] elements, final MALEncodingContext ctx) throws MALException
  {
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    
    final MALElementOutputStream os = createOutputStream(baos);
    
    for (int i = 0; i < elements.length; i++)
    {
      os.writeElement(elements[i], ctx);
    }
    
    os.flush();
    
    return new Blob(baos.toByteArray());
  }
}
