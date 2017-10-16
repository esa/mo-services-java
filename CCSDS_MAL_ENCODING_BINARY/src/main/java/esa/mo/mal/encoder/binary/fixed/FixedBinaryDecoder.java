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
import java.math.BigInteger;
import java.util.List;
import org.ccsds.moims.mo.mal.MALException;

/**
 * Implements the MALDecoder interface for a fixed length binary encoding.
 */
public class FixedBinaryDecoder extends esa.mo.mal.encoder.binary.base.BaseBinaryDecoder
{
  /**
   * Constructor.
   *
   * @param src Byte array to read from.
   * @param timeHandler Time handler to use.
   */
  public FixedBinaryDecoder(final byte[] src, final BinaryTimeHandler timeHandler)
  {
    super(new FixedBinaryBufferHolder(null, src, 0, src.length), timeHandler);
  }

  /**
   * Constructor.
   *
   * @param is Input stream to read from.
   * @param timeHandler Time handler to use.
   */
  public FixedBinaryDecoder(final java.io.InputStream is, final BinaryTimeHandler timeHandler)
  {
    super(new FixedBinaryBufferHolder(is, null, 0, 0), timeHandler);
  }

  /**
   * Constructor.
   *
   * @param src Byte array to read from.
   * @param offset index in array to start reading from.
   * @param timeHandler Time handler to use.
   */
  public FixedBinaryDecoder(final byte[] src, final int offset, final BinaryTimeHandler timeHandler)
  {
    super(new FixedBinaryBufferHolder(null, src, offset, src.length), timeHandler);
  }

  /**
   * Constructor.
   *
   * @param src Source buffer holder to use.
   * @param timeHandler Time handler to use.
   */
  protected FixedBinaryDecoder(final BufferHolder src, final BinaryTimeHandler timeHandler)
  {
    super(src, timeHandler);
  }

  /**
   * Internal class that implements the fixed length field decoding.
   */
  public static class FixedBinaryBufferHolder extends BaseBinaryBufferHolder
  {
    /**
     * Constructor.
     *
     * @param is Input stream to read from.
     * @param buf Source buffer to use.
     * @param offset Buffer offset to read from next.
     * @param length Length of readable data held in the array, which may be larger.
     */
    public FixedBinaryBufferHolder(final java.io.InputStream is, final byte[] buf, final int offset, final int length)
    {
      super(is, buf, offset, length);
    }

    @Override
    public long getUnsignedLong() throws MALException
    {
      buf.checkBuffer(8);
      final int i = buf.shiftOffsetAndReturnPrevious(8);
      return java.nio.ByteBuffer.wrap(buf.getBuf(), i, 8).getLong();
    }

    @Override
    public long getUnsignedLong32() throws MALException
    {
      buf.checkBuffer(4);

      final int i = buf.shiftOffsetAndReturnPrevious(4);
      return java.nio.ByteBuffer.wrap(buf.getBuf(), i, 4).getInt() & 0xFFFFFFFFL;
    }

    @Override
    public int getUnsignedInt() throws MALException
    {
      buf.checkBuffer(4);
      final int i = buf.shiftOffsetAndReturnPrevious(4);
      return java.nio.ByteBuffer.wrap(buf.getBuf(), i, 4).getInt();
    }

    @Override
    public int getUnsignedInt16() throws MALException
    {
      buf.checkBuffer(2);
      final int i = buf.shiftOffsetAndReturnPrevious(2);
      return java.nio.ByteBuffer.wrap(buf.getBuf(), i, 2).getShort() & 0xFFFF;
    }

    @Override
    public int getUnsignedShort() throws MALException
    {
      buf.checkBuffer(2);
      final int i = buf.shiftOffsetAndReturnPrevious(2);
      return java.nio.ByteBuffer.wrap(buf.getBuf(), i, 2).getShort();
    }

    @Override
    public short getUnsignedShort8() throws MALException
    {
      return (short) (get8() & 0xFF);
    }

    @Override
    public short getSignedShort() throws MALException
    {
      return (short)getUnsignedShort();
    }

    @Override
    public int getSignedInt() throws MALException
    {
      return getUnsignedInt();
    }

    @Override
    public long getSignedLong() throws MALException
    {
      return getUnsignedLong();
    }

    @Override
    public BigInteger getBigInteger() throws MALException
    {
      // Make sure that sign bit is always 0
      byte[] readBuf = new byte[9];
      System.arraycopy(buf.directGetBytes(8), 0, readBuf, 1, 8);
      return new BigInteger(readBuf);
    }
  }
}
