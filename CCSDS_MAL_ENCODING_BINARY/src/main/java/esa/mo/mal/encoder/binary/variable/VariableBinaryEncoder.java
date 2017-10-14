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
package esa.mo.mal.encoder.binary.variable;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;

/**
 * Implements the MALEncoder and MALListEncoder interfaces for a fixed length binary encoding.
 */
public class VariableBinaryEncoder extends esa.mo.mal.encoder.binary.base.BaseBinaryEncoder
{
  /**
   * Constructor.
   *
   * @param os Output stream to write to.
   */
  public VariableBinaryEncoder(final OutputStream os)
  {
    super(new VariableBinaryStreamHolder(os));
  }

  /**
   * Constructor for derived classes that have their own stream holder implementation that should be used.
   *
   * @param os Output stream to write to.
   */
  protected VariableBinaryEncoder(final StreamHolder os)
  {
    super(os);
  }

  /**
   * Extends the StreamHolder class for handling fixed length, non-zig-zag encoded, fields.
   */
  public static class VariableBinaryStreamHolder extends BaseBinaryStreamHolder
  {

    private static final BigInteger B_127 = new BigInteger("127");
    private static final BigInteger B_128 = new BigInteger("128");

    /**
     * Constructor.
     * 
     * @param outputStream The output stream to encode into.
     */
    public VariableBinaryStreamHolder(OutputStream outputStream)
    {
      super(outputStream);
    }

    @Override
    public void addUnsignedInt(int value) throws IOException
    {
      while ((value & -128) != 0L)
      {
        directAdd((byte) ((value & 127) | 128));
        value >>>= 7;
      }
      directAdd((byte) (value & 127));
    }

    @Override
    public void addUnsignedLong(long value) throws IOException
    {
      while ((value & -128L) != 0L)
      {
        directAdd((byte) (((int) value & 127) | 128));
        value >>>= 7;
      }
      directAdd((byte) ((int) value & 127));
    }

    @Override
    public void addSignedLong(final long value) throws IOException
    {
      addUnsignedLong((value << 1) ^ (value >> 63));
    }

    @Override
    public void addSignedInt(final int value) throws IOException
    {
      addUnsignedInt((value << 1) ^ (value >> 31));
    }

    @Override
    public void addSignedShort(final short value) throws IOException
    {
      addUnsignedInt((value << 1) ^ (value >> 31));
    }

    @Override
    public void addBigInteger(BigInteger value) throws IOException
    {
      while (value.and(B_127.not()).compareTo(BigInteger.ZERO) == 1)
      {
        byte byteToWrite = (value.and(B_127)).or(B_128).byteValue();
        directAdd(byteToWrite);
        value = value.shiftRight(7);
      }
      BigInteger encoded = value.and(B_127);
      directAdd(encoded.byteValue());
    }

    @Override
    public void addUnsignedLong32(long value) throws IOException
    {
      addUnsignedLong(value);
    }

    @Override
    public void addUnsignedInt16(int value) throws IOException
    {
      addUnsignedInt(value);
    }

    @Override
    public void addUnsignedShort(int value) throws IOException
    {
      addUnsignedInt(value);
    }

    @Override
    public void addUnsignedShort8(short value) throws IOException
    {
      directAdd(java.nio.ByteBuffer.allocate(2).putShort(value).array()[1]);
    }
  }
}
