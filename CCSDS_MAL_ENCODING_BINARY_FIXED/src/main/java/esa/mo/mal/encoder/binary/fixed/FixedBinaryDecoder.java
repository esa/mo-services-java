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

import org.ccsds.moims.mo.mal.MALException;

/**
 * Implements the MALDecoder interface for a fixed length binary encoding.
 */
public class FixedBinaryDecoder extends esa.mo.mal.encoder.binary.BinaryDecoder
{
  /**
   * Constructor.
   *
   * @param src Byte array to read from.
   */
  public FixedBinaryDecoder(final byte[] src)
  {
    super(src);
  }

  /**
   * Constructor.
   *
   * @param is Input stream to read from.
   */
  public FixedBinaryDecoder(final java.io.InputStream is)
  {
    super(is);
  }

  /**
   * Constructor.
   *
   * @param src Byte array to read from.
   * @param offset index in array to start reading from.
   */
  public FixedBinaryDecoder(final byte[] src, final int offset)
  {
    super(src, offset);
  }

  /**
   * Constructor.
   *
   * @param src Source buffer holder to use.
   */
  protected FixedBinaryDecoder(final BufferHolder src)
  {
    super(src);
  }

  @Override
  public org.ccsds.moims.mo.mal.MALListDecoder createListDecoder(final java.util.List list) throws MALException
  {
    return new FixedBinaryListDecoder(list, sourceBuffer);
  }

  @Override
  protected BufferHolder createBufferHolder(final java.io.InputStream is, final byte[] buf, final int offset, final int length)
  {
    return new FixedBufferHolder(is, buf, offset, length);
  }

  protected static class FixedBufferHolder extends BufferHolder
  {
    /**
     * Constructor.
     *
     * @param is Input stream to read from.
     * @param buf Source buffer to use.
     * @param offset Buffer offset to read from next.
     * @param length Length of readable data held in the array, which may be larger.
     */
    public FixedBufferHolder(final java.io.InputStream is, final byte[] buf, final int offset, final int length)
    {
      super(is, buf, offset, length);
    }

    @Override
    public long getUnsignedLong() throws MALException
    {
      checkBuffer(8);
      final int i = shiftOffsetAndReturnPrevious(8);
      return java.nio.ByteBuffer.wrap(getBuf(), i, 8).getLong();
    }

    @Override
    public long getUnsignedLong32() throws MALException
    {
      checkBuffer(4);

      final int i = shiftOffsetAndReturnPrevious(4);
      return java.nio.ByteBuffer.wrap(getBuf(), i, 4).getInt() & 0xFFFFFFFFL;
    }

    @Override
    public int getUnsignedInt() throws MALException
    {
      checkBuffer(4);
      final int i = shiftOffsetAndReturnPrevious(4);
      return java.nio.ByteBuffer.wrap(getBuf(), i, 4).getInt();
    }

    @Override
    public int getUnsignedInt16() throws MALException
    {
      checkBuffer(2);
      final int i = shiftOffsetAndReturnPrevious(2);
      return java.nio.ByteBuffer.wrap(getBuf(), i, 2).getInt() & 0xFFFF;
    }

    @Override
    public short getUnsignedShort() throws MALException
    {
      checkBuffer(2);
      final int i = shiftOffsetAndReturnPrevious(2);
      return java.nio.ByteBuffer.wrap(getBuf(), i, 2).getShort();
    }

    @Override
    public short getUnsignedShort8() throws MALException
    {
      return (short) (get8() & 0xFF);
    }
  }
}
