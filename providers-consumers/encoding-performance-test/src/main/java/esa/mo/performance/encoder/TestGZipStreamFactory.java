/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Encoder performance test
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
package esa.mo.performance.encoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.encoding.MALEncodingContext;
import org.ccsds.moims.mo.mal.structures.Blob;


public class TestGZipStreamFactory extends MALElementStreamFactory
{
  private final MALElementStreamFactory delegate;

  public TestGZipStreamFactory(MALElementStreamFactory delegate)
  {
    this.delegate = delegate;
  }

  @Override
  protected void init(String protocol, Map properties) throws IllegalArgumentException, MALException
  {
  }

  @Override
  public MALElementInputStream createInputStream(InputStream is) throws IllegalArgumentException, MALException
  {
    try
    {
      return delegate.createInputStream(new GZIPInputStream(is));
    }
    catch (IOException ex)
    {
      throw new MALException("XML Encoding error", ex);
    }
  }

  @Override
  public MALElementOutputStream createOutputStream(OutputStream os) throws IllegalArgumentException, MALException
  {
    try
    {
      return delegate.createOutputStream(new GZIPOutputStream(os));
    }
    catch (IOException ex)
    {
      throw new MALException("XML Encoding error", ex);
    }
  }

  @Override
  public MALElementInputStream createInputStream(byte[] bytes, int offset) throws IllegalArgumentException, MALException
  {
    return delegate.createInputStream(bytes, offset);
  }

  @Override
  public Blob encode(Object[] elements, MALEncodingContext ctx) throws IllegalArgumentException, MALException
  {
    return delegate.encode(elements, ctx);
  }
}
