/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Split Binary encoder
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
package esa.mo.mal.encoder.binary.split;

import java.io.IOException;
import java.io.OutputStream;
import org.ccsds.moims.mo.mal.MALException;

/**
 * Implements the MALEncoder and MALListEncoder interfaces for a split binary encoding.
 */
public class SplitBinaryEncoder extends esa.mo.mal.encoder.binary.BinaryEncoder
{
  private int openCount = 1;

  /**
   * Constructor.
   *
   * @param os Output stream to write to.
   */
  public SplitBinaryEncoder(final OutputStream os)
  {
    super(os);
  }

  @Override
  public org.ccsds.moims.mo.mal.MALListEncoder createListEncoder(final java.util.List value) throws MALException
  {
    ++openCount;

    return super.createListEncoder(value);
  }

  @Override
  protected StreamHolder createStreamHolder(final OutputStream os)
  {
    return new SplitStreamHolder(os);
  }

  @Override
  public void encodeBoolean(final Boolean value) throws MALException
  {
    try
    {
      outputStream.addBool(value);
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeNullableBoolean(final Boolean value) throws MALException
  {
    try
    {
      if (null != value)
      {
        outputStream.addBool(true);
        outputStream.addBool(value);
      }
      else
      {
        outputStream.addBool(false);
      }
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void close()
  {
    --openCount;

    if (1 > openCount)
    {
      try
      {
        ((SplitStreamHolder) outputStream).close();
      }
      catch (IOException ex)
      {
        // do nothing
      }
    }
  }

  protected static class SplitStreamHolder extends StreamHolder
  {
    private final java.util.BitSet bitStore = new java.util.BitSet();
    private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
    private int bitIndex = 0;

    public SplitStreamHolder(OutputStream outputStream)
    {
      super(outputStream);
    }

    public void close() throws IOException
    {
      byte[] bb = bitStore.toByteArray();
      _addUnsignedInt(outputStream, bb.length);
      outputStream.write(bb);
      outputStream.write(baos.toByteArray());
    }

    @Override
    public void addBool(boolean value) throws IOException
    {
      if (value)
      {
        bitStore.set(bitIndex);
      }

      ++bitIndex;
    }

    @Override
    public void directAdd(final byte[] val) throws IOException
    {
      baos.write(val);
    }

    @Override
    public void directAdd(final byte val) throws IOException
    {
      baos.write(val);
    }

    private static void _addUnsignedInt(java.io.OutputStream os, int value) throws IOException
    {
      while ((value & 0xFFFFFF80) != 0L)
      {
        os.write((value & 0x7F) | 0x80);
        value >>>= 7;
      }
      os.write(value & 0x7F);
    }
  }
}
