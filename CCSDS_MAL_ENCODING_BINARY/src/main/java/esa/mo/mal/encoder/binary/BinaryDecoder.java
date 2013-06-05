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
  protected final java.io.InputStream inputStream;
  protected final BufferHolder sourceBuffer;

  /**
   * Constructor.
   *
   * @param src Byte array to read from.
   */
  public BinaryDecoder(final byte[] src)
  {
    inputStream = null;
    sourceBuffer = new BufferHolder(src, 0, src.length);
  }

  /**
   * Constructor.
   *
   * @param is Input stream to read from.
   */
  public BinaryDecoder(final java.io.InputStream is)
  {
    inputStream = is;
    sourceBuffer = new BufferHolder(null, 0, 0);
  }

  /**
   * Constructor.
   *
   * @param src Byte array to read from.
   * @param offset index in array to start reading from.
   */
  public BinaryDecoder(final byte[] src, final int offset)
  {
    inputStream = null;
    sourceBuffer = new BufferHolder(src, offset, src.length);
  }

  /**
   * Constructor.
   *
   * @param is Input stream to read from.
   * @param src Source buffer holder to use.
   */
  protected BinaryDecoder(final java.io.InputStream is, final BufferHolder src)
  {
    inputStream = is;
    sourceBuffer = src;
  }

  @Override
  public MALListDecoder createListDecoder(final List list) throws MALException
  {
    return new BinaryListDecoder(list, inputStream, sourceBuffer);
  }

  @Override
  public Identifier decodeIdentifier() throws MALException
  {
    return new Identifier(getString());
  }

  @Override
  public Identifier decodeNullableIdentifier() throws MALException
  {
    final String s = getString();
    if (null != s)
    {
      return new Identifier(s);
    }

    return null;
  }

  @Override
  public URI decodeURI() throws MALException
  {
    return new URI(getString());
  }

  @Override
  public URI decodeNullableURI() throws MALException
  {
    final String s = getString();
    if (null != s)
    {
      return new URI(s);
    }

    return null;
  }

  @Override
  public String decodeString() throws MALException
  {
    return getString();
  }

  @Override
  public String decodeNullableString() throws MALException
  {
    if (!isNull())
    {
      return getString();
    }

    return null;
  }

  @Override
  public Integer decodeInteger() throws MALException
  {
    return getSignedInt();
  }

  @Override
  public Integer decodeNullableInteger() throws MALException
  {
    if (!isNull())
    {
      return getSignedInt();
    }

    return null;
  }

  @Override
  public Boolean decodeBoolean() throws MALException
  {
    return (1 == get8() ? Boolean.TRUE : Boolean.FALSE);
  }

  @Override
  public Boolean decodeNullableBoolean() throws MALException
  {
    final byte b = get8();

    if (2 == b)
    {
      return null;
    }

    return (1 == b ? Boolean.TRUE : Boolean.FALSE);
  }

  @Override
  public Time decodeTime() throws MALException
  {
    return new Time(getUnsignedLong());
  }

  @Override
  public Time decodeNullableTime() throws MALException
  {
    if (!isNull())
    {
      return new Time(getUnsignedLong());
    }

    return null;
  }

  @Override
  public FineTime decodeFineTime() throws MALException
  {
    return new FineTime(getUnsignedLong());
  }

  @Override
  public FineTime decodeNullableFineTime() throws MALException
  {
    if (!isNull())
    {
      return new FineTime(getUnsignedLong());
    }

    return null;
  }

  @Override
  public Blob decodeBlob() throws MALException
  {
    return new Blob(get(getSignedInt()));
  }

  @Override
  public Blob decodeNullableBlob() throws MALException
  {
    final int len = getSignedInt();
    if (len >= 0)
    {
      return new Blob(get(len));
    }

    return null;
  }

  @Override
  public Duration decodeDuration() throws MALException
  {
    return new Duration(getSignedInt());
  }

  @Override
  public Duration decodeNullableDuration() throws MALException
  {
    if (!isNull())
    {
      return new Duration(getSignedInt());
    }

    return null;
  }

  @Override
  public Float decodeFloat() throws MALException
  {
    return Float.intBitsToFloat(getSignedInt());
  }

  @Override
  public Float decodeNullableFloat() throws MALException
  {
    if (!isNull())
    {
      return Float.intBitsToFloat(getSignedInt());
    }

    return null;
  }

  @Override
  public Double decodeDouble() throws MALException
  {
    return Double.longBitsToDouble(getSignedLong());
  }

  @Override
  public Double decodeNullableDouble() throws MALException
  {
    if (!isNull())
    {
      return Double.longBitsToDouble(getSignedLong());
    }

    return null;
  }

  @Override
  public Long decodeLong() throws MALException
  {
    return getSignedLong();
  }

  @Override
  public Long decodeNullableLong() throws MALException
  {
    if (!isNull())
    {
      return getSignedLong();
    }

    return null;
  }

  @Override
  public Byte decodeOctet() throws MALException
  {
    return get8();
  }

  @Override
  public Byte decodeNullableOctet() throws MALException
  {
    if (!isNull())
    {
      return get8();
    }

    return null;
  }

  @Override
  public Short decodeShort() throws MALException
  {
    return (short) getSignedInt();
  }

  @Override
  public Short decodeNullableShort() throws MALException
  {
    if (!isNull())
    {
      return (short) getSignedInt();
    }

    return null;
  }

  @Override
  public UInteger decodeUInteger() throws MALException
  {
    return new UInteger(getUnsignedLong());
  }

  @Override
  public UInteger decodeNullableUInteger() throws MALException
  {
    if (!isNull())
    {
      return new UInteger(getUnsignedLong());
    }

    return null;
  }

  @Override
  public ULong decodeULong() throws MALException
  {
    return new ULong(new BigInteger(get(getSignedInt())));
  }

  @Override
  public ULong decodeNullableULong() throws MALException
  {
    final int len = getSignedInt();
    if (len >= 0)
    {
      return new ULong(new BigInteger(get(len)));
    }

    return null;
  }

  @Override
  public UOctet decodeUOctet() throws MALException
  {
    return new UOctet(getUnsignedShort());
  }

  @Override
  public UOctet decodeNullableUOctet() throws MALException
  {
    if (!isNull())
    {
      return new UOctet(getUnsignedShort());
    }

    return null;
  }

  @Override
  public UShort decodeUShort() throws MALException
  {
    return new UShort(getUnsignedInt());
  }

  @Override
  public UShort decodeNullableUShort() throws MALException
  {
    if (!isNull())
    {
      return new UShort(getUnsignedInt());
    }

    return null;
  }

  @Override
  public Attribute decodeAttribute() throws MALException
  {
    return internalDecodeAttribute(get8());
  }

  @Override
  public Attribute decodeNullableAttribute() throws MALException
  {
    if (!isNull())
    {
      return internalDecodeAttribute(get8());
    }

    return null;
  }

  protected Attribute internalDecodeAttribute(final int typeval) throws MALException
  {
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
  public Element decodeElement(final Element element) throws IllegalArgumentException, MALException
  {
    return element.decode(this);
  }

  @Override
  public Element decodeNullableElement(final Element element) throws MALException
  {
    if (!isNull())
    {
      return element.decode(this);
    }

    return null;
  }

  protected boolean isNull() throws MALException
  {
    return 0 == get8();
  }

  protected byte get8() throws MALException
  {
    checkBuffer(1);

    final int i = sourceBuffer.offset;
    sourceBuffer.offset++;
    return sourceBuffer.buf[i];
  }

  protected String getString() throws MALException
  {
    final int len = getSignedInt();
    if (len >= 0)
    {
      checkBuffer(len);

      final String s = new String(sourceBuffer.buf, sourceBuffer.offset, len, UTF8_CHARSET);
      sourceBuffer.offset += len;
      return s;
    }
    return null;
  }

  protected byte[] get(final int size) throws MALException
  {
    if (size >= 0)
    {
      checkBuffer(size);

      final byte[] v = Arrays.copyOfRange(sourceBuffer.buf, sourceBuffer.offset, sourceBuffer.offset + size);
      sourceBuffer.offset += size;
      return v;
    }

    return null;
  }

  protected long getSignedLong() throws MALException
  {
    final long raw = getUnsignedLong();
    final long temp = (((raw << 63) >> 63) ^ raw) >> 1;
    return temp ^ (raw & (1L << 63));
  }

  protected int getSignedInt() throws MALException
  {
    final int raw = getUnsignedInt();
    final int temp = (((raw << 31) >> 31) ^ raw) >> 1;
    return temp ^ (raw & (1 << 31));
  }

  protected long getUnsignedLong() throws MALException
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

  protected int getUnsignedInt() throws MALException
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

  protected short getUnsignedShort() throws MALException
  {
    short value = 0;
    int i = 0;
    short b;
    if (((b = get8()) & 0x80) != 0)
    {
      value |= (b & 0x7F) << i;
      i += 7;
    }

    return (short) (value | (b << i));
  }

  protected void checkBuffer(final int requiredLength) throws MALException
  {
    // ensure that we have loaded enough buffer from the input stream (if we are stream based) for the next read
    if (null != inputStream)
    {
      int existingContentRemaining = 0;
      int existingBufferLength = 0;

      // have we got any loaded data currently
      if (null != sourceBuffer.buf)
      {
        existingContentRemaining = sourceBuffer.contentLength - sourceBuffer.offset;
        existingBufferLength = sourceBuffer.buf.length;
      }

      // check to see if currently loaded data covers the required data size
      if (existingContentRemaining < requiredLength)
      {
        // ok, check to see if we have enough space left in the current buffer for what we need to load
        if ((existingBufferLength - sourceBuffer.offset) < requiredLength)
        {
          byte[] destBuf = sourceBuffer.buf;

          // its not big enough, we need to check if we need a bigger buffer
          if (existingBufferLength < requiredLength)
          {
            // we do, so allocate one
            existingBufferLength = (requiredLength > BLOCK_SIZE) ? requiredLength : BLOCK_SIZE;
            destBuf = new byte[existingBufferLength];
          }

          // this either shifts the existing contents to the start of the old buffer, or copies it into the new buffer
          for (int i = 0; i < existingContentRemaining; i++)
          {
            destBuf[i] = sourceBuffer.buf[sourceBuffer.offset + i];
          }

          // the start of the data in the buffer has moved to zero now
          sourceBuffer.buf = destBuf;
          sourceBuffer.offset = 0;
          sourceBuffer.contentLength = existingContentRemaining;
        }

        try
        {
          // read into the empty space of the buffer
          sourceBuffer.contentLength += inputStream.read(sourceBuffer.buf,
                  sourceBuffer.contentLength, existingBufferLength - sourceBuffer.contentLength);
        }
        catch (IOException ex)
        {
          throw new MALException("Unable to read required amount from source stream", ex);
        }
      }
    }
  }

  protected static class BufferHolder
  {
    protected byte[] buf;
    protected int offset;
    protected int contentLength;

    /**
     * Constructor.
     *
     * @param buf Source buffer to use.
     * @param offset Buffer offset to read from next.
     * @param length Length of readable data held in the array, which may be larger.
     */
    public BufferHolder(final byte[] buf, final int offset, final int length)
    {
      this.buf = buf;
      this.offset = offset;
      this.contentLength = length;
    }
  }
}
