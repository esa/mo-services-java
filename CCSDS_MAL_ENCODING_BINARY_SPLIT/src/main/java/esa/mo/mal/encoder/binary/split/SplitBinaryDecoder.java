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

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.ULong;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 * Implements the MALDecoder interface for a split binary encoding.
 */
public class SplitBinaryDecoder extends esa.mo.mal.encoder.binary.BinaryDecoder
{

  /**
   * Constructor.
   *
   * @param src Byte array to read from.
   */
  public SplitBinaryDecoder(final byte[] src)
  {
    super(new SplitBufferHolder(null, src, 0, src.length));
  }

  /**
   * Constructor.
   *
   * @param is Input stream to read from.
   */
  public SplitBinaryDecoder(final java.io.InputStream is)
  {
    super(new SplitBufferHolder(is, null, 0, 0));
  }

  /**
   * Constructor.
   *
   * @param src Byte array to read from.
   * @param offset index in array to start reading from.
   */
  public SplitBinaryDecoder(final byte[] src, final int offset)
  {
    super(new SplitBufferHolder(null, src, offset, src.length));
  }

  /**
   * Constructor.
   *
   * @param src Source buffer holder to use.
   */
  protected SplitBinaryDecoder(final BufferHolder src)
  {
    super(src);
  }

  @Override
  public org.ccsds.moims.mo.mal.MALListDecoder createListDecoder(
          final java.util.List list) throws MALException
  {
    return new SplitBinaryListDecoder(list, sourceBuffer);
  }

  @Override
  public Boolean decodeBoolean() throws MALException
  {
    return sourceBuffer.getBool();
  }

  @Override
  public Boolean decodeNullableBoolean() throws MALException
  {

    boolean isNotNull = decodeBoolean();

    Boolean rv = null;

    // decode one element, or add null if presence flag indicates no element
    if (isNotNull)
    {
      rv = decodeBoolean();
    }

    return rv;
  }

  @Override
  public String decodeString() throws MALException
  {

    return sourceBuffer.getString();
  }

  @Override
  public String decodeNullableString() throws MALException
  {

    // decode presence flag
    boolean isNotNull = decodeBoolean();

    String rv = null;

    // decode one element, or add null if presence flag indicates no element
    if (isNotNull)
    {
      rv = decodeString();
    }

    return rv;
  }

  @Override
  public Blob decodeNullableBlob() throws MALException
  {

    // decode presence flag
    boolean isNotNull = decodeBoolean();

    // decode one element, or add null if presence flag indicates no element
    if (isNotNull)
    {
      return decodeBlob();
    }

    return null;
  }

  @Override
  public Identifier decodeNullableIdentifier() throws MALException
  {

    // decode presence flag
    boolean isNotNull = decodeBoolean();

    Identifier rv = null;

    // decode one element, or add null if presence flag indicates no element
    if (isNotNull)
    {
      rv = decodeIdentifier();
    }

    return rv;
  }

  @Override
  public URI decodeNullableURI() throws MALException
  {

    // decode presence flag
    boolean isNotNull = decodeBoolean();

    // decode one element, or add null if presence flag indicates no element
    if (isNotNull)
    {
      return decodeURI();
    }

    return null;
  }

  @Override
  public ULong decodeNullableULong() throws MALException
  {

    // decode presence flag
    boolean isNotNull = decodeBoolean();

    ULong rv = null;

    // decode one element, or add null if presence flag indicates no element
    if (isNotNull)
    {
      rv = decodeULong();
    }

    return rv;
  }

  @Override
  public Time decodeTime() throws MALException
  {
    return new Time(((SplitBufferHolder) sourceBuffer).getFixedUnsignedLong());
  }

  @Override
  public FineTime decodeFineTime() throws MALException
  {
    return new FineTime(((SplitBufferHolder) sourceBuffer).getFixedUnsignedLong());
  }

  /**
   * Extends BufferHolder to handle split binary encoding.
   */
  protected static class SplitBufferHolder extends BinaryBufferHolder
  {

    private boolean bitStoreLoaded = false;
    private BitGet bitStore = null;

    /**
     * Constructor.
     *
     * @param is Input stream to read from.
     * @param buf Source buffer to use.
     * @param offset Buffer offset to read from next.
     * @param length Length of readable data held in the array, which may be
     * larger.
     */
    public SplitBufferHolder(final java.io.InputStream is,
            final byte[] buf, final int offset, final int length)
    {
      super(is, buf, offset, length);

      super.buf.setForceRealloc(true);
    }

    public void checkBuffer(final int requiredLength) throws MALException
    {
      // ensure that the bit buffer has been loaded first
      if (!bitStoreLoaded)
      {
        loadBitStore();
      }

      super.buf.checkBuffer(requiredLength);
    }

    protected void bufferRealloced(int oldSize)
    {
      if (0 < oldSize)
      {
        super.buf.setForceRealloc(false);
      }
    }

    @Override
    public String getString() throws MALException
    {

      // ensure that the bit buffer has been loaded first
      if (!bitStoreLoaded)
      {
        loadBitStore();
      }

      final int len = getUnsignedInt();

      if (len >= 0)
      {
        checkBuffer(len);

        final String s = new String(buf.getBuf(), buf.getOffset(), len, UTF8_CHARSET);
        buf.shiftOffsetAndReturnPrevious((int) len);
        return s;
      }
      return null;
    }

    @Override
    public byte[] getBytes() throws MALException
    {

      // ensure that the bit buffer has been loaded first
      if (!bitStoreLoaded)
      {
        loadBitStore();
      }

      return directGetBytes(getUnsignedInt());
    }

    @Override
    public boolean getBool() throws MALException
    {
      // ensure that the bit buffer has been loaded first
      if (!bitStoreLoaded)
      {
        loadBitStore();
      }

      return bitStore.pop();
    }

    /**
     * Ensures that the bit buffer has been loaded
     *
     * @throws MALException on error.
     */
    protected void loadBitStore() throws MALException
    {
      // ensure that the bit buffer has been loaded first
      bitStoreLoaded = true;
      int size = super.getUnsignedInt();

      if (size >= 0)
      {
        super.buf.checkBuffer(size);

        bitStore = new BitGet(buf.getBuf(), buf.getOffset(), size);
        buf.shiftOffsetAndReturnPrevious((int) size);
      }
      else
      {
        bitStore = new BitGet(null, 0, 0);
      }
    }

    /**
     * Decode an unsigned int using a split-binary approach
     */
    @Override
    public int getUnsignedInt() throws MALException
    {

      if (!bitStoreLoaded)
      {
        loadBitStore();
      }

      int value = 0;
      int i = 0;
      int b;
      while (((b = get8()) & 0x80) != 0)
      {
        value |= (b & 0x7F) << i;
        i += 7;
      }
      return value | (b << i);
    }

    /**
     * Decode an unsigned long using a split-binary approach
     */
    @Override
    public long getUnsignedLong() throws MALException
    {

      if (!bitStoreLoaded)
      {
        loadBitStore();
      }

      long value = 0;
      int i = 0;
      long b;
      while (((b = get8()) & 0x80) != 0)
      {
        value |= (b & 0x7F) << i;
        i += 7;
      }
      return value | (b << i);
    }

    public long getFixedUnsignedLong() throws MALException
    {

      checkBuffer(8);

      final int i = super.buf.shiftOffsetAndReturnPrevious(8);
      return java.nio.ByteBuffer.wrap(super.buf.getBuf(), i, 8).getLong();
    }
  }

  /**
   * Simple helper class for dealing with bit array. Smaller and faster than
   * Java BitSet.
   */
  protected static class BitGet
  {

    private final byte[] bitBytes;
    private final int bitBytesOffset;
    private final int bitBytesInUse;
    private int byteIndex = 0;
    private int bitIndex = 0;

    /**
     * Constructor.
     *
     * @param bytes Encoded bit set bytes. Supplied array is accessed directly,
     * it is not copied.
     * @param offset Offset, in bytes, into supplied byte array for start of bit
     * set.
     * @param length Length, in bytes, of supplied bit set.
     */
    public BitGet(byte[] bytes, final int offset, final int length)
    {
      this.bitBytes = bytes;
      this.bitBytesOffset = offset;
      this.bitBytesInUse = length;
    }

    /**
     * Returns true if the next bit is set to '1', false is set to '0'.
     *
     * @return True is set to '1', false otherwise.
     */
    public boolean pop()
    {
      boolean rv = (byteIndex < bitBytesInUse)
              && ((bitBytes[byteIndex + bitBytesOffset] & (1 << bitIndex)) != 0);

      if (7 == bitIndex)
      {
        bitIndex = 0;
        ++byteIndex;
      }
      else
      {
        ++bitIndex;
      }

      return rv;
    }
  }
}
