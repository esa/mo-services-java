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
package esa.mo.mal.encoder.binary.base;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.encoding.MALEncodingContext;
import org.ccsds.moims.mo.mal.structures.Blob;

/**
 * Implements the MALElementStreamFactory interface for a binary encoding.
 */
public abstract class BaseBinaryStreamFactory extends MALElementStreamFactory
{
  Class inputStreamImpl;
  Class outputStreamImpl;

  protected BaseBinaryStreamFactory(final Class inputStreamImpl, final Class outputStreamImpl)
  {
    this.inputStreamImpl = inputStreamImpl;
    this.outputStreamImpl = outputStreamImpl;
  }

  @Override
  protected void init(final String protocol, final Map properties) throws MALException
  {
    // nothing required here
  }

  @Override
  public MALElementInputStream createInputStream(final byte[] bytes, final int offset) throws java.lang.IllegalArgumentException, MALException
  {
    try
    {
      return (MALElementInputStream) inputStreamImpl.getDeclaredConstructor(byte[].class, int.class).newInstance(bytes, offset);
    }
    catch (Exception ex)
    {
      throw new MALException("Error when creating input stream.", ex);
    }
  }

  @Override
  public MALElementInputStream createInputStream(final InputStream is) throws MALException
  {
    try
    {
      return (MALElementInputStream) inputStreamImpl.getDeclaredConstructor(InputStream.class).newInstance(is);
    }
    catch (Exception ex)
    {
      throw new MALException("Error when creating input stream.", ex);
    }
  }

  @Override
  public MALElementOutputStream createOutputStream(final OutputStream os) throws MALException
  {
    try
    {
      return (MALElementOutputStream) outputStreamImpl.getDeclaredConstructor(OutputStream.class).newInstance(os);
    }
    catch (Exception ex)
    {
      throw new MALException("Error when creating output stream.", ex);
    }
  }

  @Override
  public Blob encode(final Object[] elements, final MALEncodingContext ctx) throws MALException
  {
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();

    final MALElementOutputStream os = createOutputStream(baos);

    for (Object element : elements)
    {
      os.writeElement(element, ctx);
    }

    os.flush();

    return new Blob(baos.toByteArray());
  }
}
