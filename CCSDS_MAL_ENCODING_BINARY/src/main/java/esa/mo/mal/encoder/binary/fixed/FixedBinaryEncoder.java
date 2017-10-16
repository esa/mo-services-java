/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Fixed Length Binary encoder
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
package esa.mo.mal.encoder.binary.fixed;

import esa.mo.mal.encoder.binary.base.BinaryTimeHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;

/**
 * Implements the MALEncoder and MALListEncoder interfaces for a fixed length binary encoding.
 */
public class FixedBinaryEncoder extends esa.mo.mal.encoder.binary.base.BaseBinaryEncoder
{
  /**
   * Constructor.
   *
   * @param os Output stream to write to.
   * @param timeHandler Time handler to use.
   */
  public FixedBinaryEncoder(final OutputStream os, final BinaryTimeHandler timeHandler)
  {
    super(new FixedBinaryStreamHolder(os), timeHandler);
  }

  /**
   * Constructor for derived classes that have their own stream holder implementation that should be used.
   *
   * @param os Output stream to write to.
   * @param timeHandler Time handler to use.
   */
  protected FixedBinaryEncoder(final StreamHolder os, final BinaryTimeHandler timeHandler)
  {
    super(os, timeHandler);
  }

  /**
   * Extends the StreamHolder class for handling fixed length, non-zig-zag encoded, fields.
   */
  public static class FixedBinaryStreamHolder extends BaseBinaryStreamHolder
  {

    private static final BigInteger B_255 = new BigInteger("255");
    /**
     * Constructor.
     * 
     * @param outputStream The output stream to encode into.
     */
    public FixedBinaryStreamHolder(OutputStream outputStream)
    {
      super(outputStream);
    }

    @Override
    public void addSignedLong(final long value) throws IOException
    {
      addUnsignedLong(value);
    }

    @Override
    public void addSignedInt(final int value) throws IOException
    {
      addUnsignedInt(value);
    }

    @Override
    public void addSignedShort(final short value) throws IOException
    {
      addUnsignedShort(value);
    }

    @Override
    public void addUnsignedLong(long value) throws IOException
    {
      directAdd(java.nio.ByteBuffer.allocate(8).putLong(value).array());
    }

    @Override
    public void addUnsignedLong32(long value) throws IOException
    {
      directAdd(java.nio.ByteBuffer.allocate(8).putLong(value).array(), 4, 4);
    }

    @Override
    public void addUnsignedInt(int value) throws IOException
    {
      directAdd(java.nio.ByteBuffer.allocate(4).putInt(value).array());
    }

    @Override
    public void addUnsignedInt16(int value) throws IOException
    {
      directAdd(java.nio.ByteBuffer.allocate(4).putInt(value).array(), 2, 2);
    }

    @Override
    public void addUnsignedShort(int value) throws IOException
    {
      directAdd(java.nio.ByteBuffer.allocate(2).putShort((short)value).array());
    }

    @Override
    public void addUnsignedShort8(short value) throws IOException
    {
      directAdd(java.nio.ByteBuffer.allocate(2).putShort(value).array()[1]);
    }

    @Override
    public void addBigInteger(BigInteger value) throws IOException
    {
      byte[] valueBytes = value.toByteArray();
      int arrayLength = valueBytes.length;
      int arrayOffset = 0;
      // Strip sign bit if it is the only bit overflowing 8 bytes buffer
      if (valueBytes[0] == 0 && arrayLength == 9)
      {
        arrayOffset = 1;
        arrayLength--;
      }
      if (arrayLength > 8)
      {
        throw new IOException("Adding big integer larger than 8 bytes (size = " + valueBytes.length + " bytes, value = " + value + ")");
      }
      java.nio.ByteBuffer buf = java.nio.ByteBuffer.allocate(8);
      buf.position(8 - arrayLength);
      directAdd(buf.put(valueBytes, arrayOffset, arrayLength).array());
    }
  }
}
