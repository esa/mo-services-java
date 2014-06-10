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
package esa.mo.mal.encoder.binary;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALListDecoder;
import org.ccsds.moims.mo.mal.structures.*;

/**
 * Implements the MALDecoder interface for a binary encoding.
 */
public class BinaryDecoder implements MALDecoder
{
  protected static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
  protected static final int BLOCK_SIZE = 65536;
  protected final BufferHolder sourceBuffer;

  /**
   * Constructor.
   *
   * @param src Byte array to read from.
   */
  public BinaryDecoder(final byte[] src)
  {
    sourceBuffer = new BufferHolder(null, src, 0, src.length);
  }

  /**
   * Constructor.
   *
   * @param is Input stream to read from.
   */
  public BinaryDecoder(final java.io.InputStream is)
  {
    sourceBuffer = new BufferHolder(is, null, 0, 0);
  }

  /**
   * Constructor.
   *
   * @param src Byte array to read from.
   * @param offset index in array to start reading from.
   */
  public BinaryDecoder(final byte[] src, final int offset)
  {
    sourceBuffer = new BufferHolder(null, src, offset, src.length);
  }

  /**
   * Constructor.
   *
   * @param src Source buffer holder to use.
   */
  protected BinaryDecoder(final BufferHolder src)
  {
    sourceBuffer = src;
  }

  @Override
  public MALListDecoder createListDecoder(final List list) throws MALException
  {
    return new BinaryListDecoder(list, sourceBuffer);
  }

  @Override
  public Identifier decodeIdentifier() throws MALException
  {
    return new Identifier(sourceBuffer.getString());
  }

  @Override
  public Identifier decodeNullableIdentifier() throws MALException
  {
    final String s = sourceBuffer.getString();
    if (null != s)
    {
      return new Identifier(s);
    }

    return null;
  }

  @Override
  public URI decodeURI() throws MALException
  {
    return new URI(sourceBuffer.getString());
  }

  @Override
  public URI decodeNullableURI() throws MALException
  {
    final String s = sourceBuffer.getString();
    if (null != s)
    {
      return new URI(s);
    }

    return null;
  }

  @Override
  public String decodeString() throws MALException
  {
    return sourceBuffer.getString();
  }

  @Override
  public String decodeNullableString() throws MALException
  {
    return sourceBuffer.getString();
  }

  @Override
  public Integer decodeInteger() throws MALException
  {
    return sourceBuffer.getSignedInt();
  }

  @Override
  public Integer decodeNullableInteger() throws MALException
  {
    if (sourceBuffer.getBool())
    {
      return sourceBuffer.getSignedInt();
    }

    return null;
  }

  @Override
  public Boolean decodeBoolean() throws MALException
  {
    return (1 == sourceBuffer.get8() ? Boolean.TRUE : Boolean.FALSE);
  }

  @Override
  public Boolean decodeNullableBoolean() throws MALException
  {
    final byte b = sourceBuffer.get8();

    if (2 == b)
    {
      return null;
    }

    return (1 == b ? Boolean.TRUE : Boolean.FALSE);
  }

  @Override
  public Time decodeTime() throws MALException
  {
    return new Time(sourceBuffer.getUnsignedLong());
  }

  @Override
  public Time decodeNullableTime() throws MALException
  {
    if (sourceBuffer.getBool())
    {
      return new Time(sourceBuffer.getUnsignedLong());
    }

    return null;
  }

  @Override
  public FineTime decodeFineTime() throws MALException
  {
    return new FineTime(sourceBuffer.getUnsignedLong());
  }

  @Override
  public FineTime decodeNullableFineTime() throws MALException
  {
    if (sourceBuffer.getBool())
    {
      return new FineTime(sourceBuffer.getUnsignedLong());
    }

    return null;
  }

  @Override
  public Blob decodeBlob() throws MALException
  {
    return new Blob(sourceBuffer.get(sourceBuffer.getSignedInt()));
  }

  @Override
  public Blob decodeNullableBlob() throws MALException
  {
    final int len = sourceBuffer.getSignedInt();
    if (len >= 0)
    {
      return new Blob(sourceBuffer.get(len));
    }

    return null;
  }

  @Override
  public Duration decodeDuration() throws MALException
  {
    return new Duration(sourceBuffer.getSignedInt());
  }

  @Override
  public Duration decodeNullableDuration() throws MALException
  {
    if (sourceBuffer.getBool())
    {
      return new Duration(sourceBuffer.getSignedInt());
    }

    return null;
  }

  @Override
  public Float decodeFloat() throws MALException
  {
    return Float.intBitsToFloat(sourceBuffer.getSignedInt());
  }

  @Override
  public Float decodeNullableFloat() throws MALException
  {
    if (sourceBuffer.getBool())
    {
      return Float.intBitsToFloat(sourceBuffer.getSignedInt());
    }

    return null;
  }

  @Override
  public Double decodeDouble() throws MALException
  {
    return Double.longBitsToDouble(sourceBuffer.getSignedLong());
  }

  @Override
  public Double decodeNullableDouble() throws MALException
  {
    if (sourceBuffer.getBool())
    {
      return Double.longBitsToDouble(sourceBuffer.getSignedLong());
    }

    return null;
  }

  @Override
  public Long decodeLong() throws MALException
  {
    return sourceBuffer.getSignedLong();
  }

  @Override
  public Long decodeNullableLong() throws MALException
  {
    if (sourceBuffer.getBool())
    {
      return sourceBuffer.getSignedLong();
    }

    return null;
  }

  @Override
  public Byte decodeOctet() throws MALException
  {
    return sourceBuffer.get8();
  }

  @Override
  public Byte decodeNullableOctet() throws MALException
  {
    if (sourceBuffer.getBool())
    {
      return sourceBuffer.get8();
    }

    return null;
  }

  @Override
  public Short decodeShort() throws MALException
  {
    return sourceBuffer.getSignedShort();
  }

  @Override
  public Short decodeNullableShort() throws MALException
  {
    if (sourceBuffer.getBool())
    {
      return sourceBuffer.getSignedShort();
    }

    return null;
  }

  @Override
  public UInteger decodeUInteger() throws MALException
  {
    return new UInteger(sourceBuffer.getUnsignedLong32());
  }

  @Override
  public UInteger decodeNullableUInteger() throws MALException
  {
    if (sourceBuffer.getBool())
    {
      return new UInteger(sourceBuffer.getUnsignedLong32());
    }

    return null;
  }

  @Override
  public ULong decodeULong() throws MALException
  {
    return new ULong(new BigInteger(sourceBuffer.get(sourceBuffer.getSignedInt())));
  }

  @Override
  public ULong decodeNullableULong() throws MALException
  {
    final int len = sourceBuffer.getSignedInt();
    if (len >= 0)
    {
      return new ULong(new BigInteger(sourceBuffer.get(len)));
    }

    return null;
  }

  @Override
  public UOctet decodeUOctet() throws MALException
  {
    return new UOctet(sourceBuffer.getUnsignedShort8());
  }

  @Override
  public UOctet decodeNullableUOctet() throws MALException
  {
    if (sourceBuffer.getBool())
    {
      return new UOctet(sourceBuffer.getUnsignedShort8());
    }

    return null;
  }

  @Override
  public UShort decodeUShort() throws MALException
  {
    return new UShort(sourceBuffer.getUnsignedInt16());
  }

  @Override
  public UShort decodeNullableUShort() throws MALException
  {
    if (sourceBuffer.getBool())
    {
      return new UShort(sourceBuffer.getUnsignedInt16());
    }

    return null;
  }

  @Override
  public Attribute decodeAttribute() throws MALException
  {
    final int typeval = sourceBuffer.get8();

    switch (typeval)
    {
      case Attribute._BLOB_TYPE_SHORT_FORM:
        return decodeBlob();
      case Attribute._BOOLEAN_TYPE_SHORT_FORM:
        return new Union(decodeBoolean());
      case Attribute._DURATION_TYPE_SHORT_FORM:
        return decodeDuration();
      case Attribute._FLOAT_TYPE_SHORT_FORM:
        return new Union(decodeFloat());
      case Attribute._DOUBLE_TYPE_SHORT_FORM:
        return new Union(decodeDouble());
      case Attribute._IDENTIFIER_TYPE_SHORT_FORM:
        return decodeIdentifier();
      case Attribute._OCTET_TYPE_SHORT_FORM:
        return new Union(decodeOctet());
      case Attribute._UOCTET_TYPE_SHORT_FORM:
        return decodeUOctet();
      case Attribute._SHORT_TYPE_SHORT_FORM:
        return new Union(decodeShort());
      case Attribute._USHORT_TYPE_SHORT_FORM:
        return decodeUShort();
      case Attribute._INTEGER_TYPE_SHORT_FORM:
        return new Union(decodeInteger());
      case Attribute._UINTEGER_TYPE_SHORT_FORM:
        return decodeUInteger();
      case Attribute._LONG_TYPE_SHORT_FORM:
        return new Union(decodeLong());
      case Attribute._ULONG_TYPE_SHORT_FORM:
        return decodeULong();
      case Attribute._STRING_TYPE_SHORT_FORM:
        return new Union(decodeString());
      case Attribute._TIME_TYPE_SHORT_FORM:
        return decodeTime();
      case Attribute._FINETIME_TYPE_SHORT_FORM:
        return decodeFineTime();
      case Attribute._URI_TYPE_SHORT_FORM:
        return decodeURI();
      default:
        throw new MALException("Unknown attribute type received: " + typeval);
    }
  }

  @Override
  public Attribute decodeNullableAttribute() throws MALException
  {
    if (sourceBuffer.getBool())
    {
      return decodeAttribute();
    }

    return null;
  }

  @Override
  public Element decodeElement(final Element element) throws IllegalArgumentException, MALException
  {
    return element.decode(this);
  }

  @Override
  public Element decodeNullableElement(final Element element) throws MALException
  {
    if (sourceBuffer.getBool())
    {
      return element.decode(this);
    }

    return null;
  }

  /**
   * Internal class that is used to hold the byte buffer. Derived classes should extend this (and replace it in the
   * constructors) if they encode the fields differently from this encoding.
   */
  protected static class BufferHolder
  {
    protected final java.io.InputStream inputStream;
    protected byte[] buf;
    protected int offset;
    protected int contentLength;
    protected boolean forceRealloc = false;

    /**
     * Constructor.
     *
     * @param is Input stream to read from.
     * @param buf Source buffer to use.
     * @param offset Buffer offset to read from next.
     * @param length Length of readable data held in the array, which may be larger.
     */
    public BufferHolder(final java.io.InputStream is, final byte[] buf, final int offset, final int length)
    {
      this.inputStream = is;
      this.buf = buf;
      this.offset = offset;
      this.contentLength = length;
    }

    /**
     * Ensures that we have loaded enough buffer from the input stream (if we are stream based) for the next read.
     *
     * @param requiredLength number of bytes required.
     * @throws MALException if there is an error reading from the stream
     */
    public void checkBuffer(final int requiredLength) throws MALException
    {
      if (null != inputStream)
      {
        int existingContentRemaining = 0;
        int existingBufferLength = 0;

        // have we got any loaded data currently
        if (null != this.buf)
        {
          existingContentRemaining = this.contentLength - this.offset;
          existingBufferLength = this.buf.length;
        }

        // check to see if currently loaded data covers the required data size
        if (existingContentRemaining < requiredLength)
        {
          // ok, check to see if we have enough space left in the current buffer for what we need to load
          if ((existingBufferLength - this.offset) < requiredLength)
          {
            byte[] destBuf = this.buf;

            // its not big enough, we need to check if we need a bigger buffer or in case we know the existing 
            // buffer is still required.
            if (forceRealloc || (existingBufferLength < requiredLength))
            {
              // we do, so allocate one
              bufferRealloced(existingBufferLength);
              existingBufferLength = (requiredLength > BLOCK_SIZE) ? requiredLength : BLOCK_SIZE;
              destBuf = new byte[existingBufferLength];
            }

            // this either shifts the existing contents to the start of the old buffer, or copies it into the new buffer
            for (int i = 0; i < existingContentRemaining; i++)
            {
              destBuf[i] = this.buf[this.offset + i];
            }

            // the start of the data in the buffer has moved to zero now
            this.buf = destBuf;
            this.offset = 0;
            this.contentLength = existingContentRemaining;
          }

          try
          {
            // read into the empty space of the buffer
            this.contentLength += inputStream.read(this.buf,
                    this.contentLength, existingBufferLength - this.contentLength);
          }
          catch (IOException ex)
          {
            throw new MALException("Unable to read required amount from source stream", ex);
          }
        }
      }
    }

    /**
     * Notification method that can be used by derived classes to notify them that the internal buffer has been
     * reallocated.
     *
     * @param oldSize the old buffer size
     */
    protected void bufferRealloced(int oldSize)
    {
    }

    /**
     * Returns the internal byte buffer.
     *
     * @return the byte buffer
     */
    public byte[] getBuf()
    {
      return buf;
    }

    /**
     * Adds a delta to the internal offset and returns the previous offset
     *
     * @param delta the delta to apply
     * @return the previous offset
     */
    public int shiftOffsetAndReturnPrevious(int delta)
    {
      int i = offset;
      offset += delta;
      return i;
    }

    public byte get8() throws MALException
    {
      checkBuffer(1);

      final int i = offset;
      offset++;
      return buf[i];
    }

    public String getString() throws MALException
    {
      final int len = getSignedInt();
      if (len >= 0)
      {
        checkBuffer(len);

        final String s = new String(buf, offset, len, UTF8_CHARSET);
        offset += len;
        return s;
      }
      return null;
    }

    public byte[] get(final int size) throws MALException
    {
      if (size >= 0)
      {
        checkBuffer(size);

        final byte[] v = Arrays.copyOfRange(buf, offset, offset + size);
        offset += size;
        return v;
      }

      return null;
    }

    public long getSignedLong() throws MALException
    {
      final long raw = getUnsignedLong();
      final long temp = (((raw << 63) >> 63) ^ raw) >> 1;
      return temp ^ (raw & (1L << 63));
    }

    public int getSignedInt() throws MALException
    {
      final int raw = getUnsignedInt();
      final int temp = (((raw << 31) >> 31) ^ raw) >> 1;
      return temp ^ (raw & (1 << 31));
    }

    public short getSignedShort() throws MALException
    {
      return (short) getSignedInt();
    }

    public long getUnsignedLong() throws MALException
    {
      long value = 0L;
      int i = 0;
      long b;
      while (((b = get8()) & 0x80L) != 0)
      {
        value |= (b & 0x7F) << i;
        i += 7;
      }
      return value | (b << i);
    }

    public long getUnsignedLong32() throws MALException
    {
      return getUnsignedLong();
    }

    public int getUnsignedInt() throws MALException
    {
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

    public int getUnsignedInt16() throws MALException
    {
      return getUnsignedInt();
    }

    public int getUnsignedShort() throws MALException
    {
      return getUnsignedInt();
    }

    public short getUnsignedShort8() throws MALException
    {
      return (short) getUnsignedShort();
    }

    public boolean getBool() throws MALException
    {
      return !(0 == get8());
    }
  }
}
