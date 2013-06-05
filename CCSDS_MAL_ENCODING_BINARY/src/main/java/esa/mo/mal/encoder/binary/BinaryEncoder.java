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
import java.io.OutputStream;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALListEncoder;
import org.ccsds.moims.mo.mal.structures.*;

/**
 * Implements the MALEncoder and MALListEncoder interfaces for a binary encoding.
 */
public class BinaryEncoder implements MALListEncoder
{
  protected static final String ENCODING_EXCEPTION_STR = "Bad encoding";
  protected final OutputStream outputStream;

  /**
   * Constructor.
   *
   * @param os Output stream to write to.
   */
  public BinaryEncoder(final OutputStream os)
  {
    this.outputStream = os;
  }

  @Override
  public MALListEncoder createListEncoder(final java.util.List value) throws MALException
  {
    try
    {
      addUnsignedInt(value.size());

      return this;
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeDouble(final Double value) throws MALException
  {
    try
    {
      addSignedLong(Double.doubleToRawLongBits(value.doubleValue()));
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeNullableDouble(final Double value) throws MALException
  {
    try
    {
      if (null != value)
      {
        addNotNull();
        encodeDouble(value);
      }
      else
      {
        addNull();
      }
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeLong(final Long value) throws MALException
  {
    try
    {
      addSignedLong(value);
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeNullableLong(final Long value) throws MALException
  {
    try
    {
      if (null != value)
      {
        addNotNull();
        encodeLong(value);
      }
      else
      {
        addNull();
      }
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeOctet(final Byte value) throws MALException
  {
    try
    {
      directAdd(value.byteValue());
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeNullableOctet(final Byte value) throws MALException
  {
    try
    {
      if (null != value)
      {
        addNotNull();
        encodeOctet(value);
      }
      else
      {
        addNull();
      }
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeShort(final Short value) throws MALException
  {
    try
    {
      addSignedInt(value.shortValue());
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeNullableShort(final Short value) throws MALException
  {
    try
    {
      if (null != value)
      {
        addNotNull();
        encodeShort(value);
      }
      else
      {
        addNull();
      }
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeUInteger(final UInteger value) throws IllegalArgumentException, MALException
  {
    try
    {
      addUnsignedLong(value.getValue());
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeNullableUInteger(final UInteger value) throws MALException
  {
    try
    {
      if (null != value)
      {
        addNotNull();
        encodeUInteger(value);
      }
      else
      {
        addNull();
      }
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeULong(final ULong value) throws IllegalArgumentException, MALException
  {
    try
    {
      add(value.getValue().toByteArray());
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeNullableULong(final ULong value) throws MALException
  {
    try
    {
      if (null != value)
      {
        encodeULong(value);
      }
      else
      {
        add((byte[]) null);
      }
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeUOctet(final UOctet value) throws IllegalArgumentException, MALException
  {
    try
    {
      addUnsignedShort(value.getValue());
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeNullableUOctet(final UOctet value) throws MALException
  {
    try
    {
      if (null != value)
      {
        addNotNull();
        encodeUOctet(value);
      }
      else
      {
        addNull();
      }
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeUShort(final UShort value) throws IllegalArgumentException, MALException
  {
    try
    {
      addUnsignedInt(value.getValue());
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeNullableUShort(final UShort value) throws MALException
  {
    try
    {
      if (null != value)
      {
        addNotNull();
        encodeUShort(value);
      }
      else
      {
        addNull();
      }
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeURI(final URI value) throws MALException
  {
    try
    {
      add(value.getValue().getBytes(BinaryDecoder.UTF8_CHARSET));
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeNullableURI(final URI value) throws MALException
  {
    try
    {
      if ((null != value) && (null != value.getValue()))
      {
        encodeURI(value);
      }
      else
      {
        add((byte[]) null);
      }
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeIdentifier(final Identifier value) throws MALException
  {
    try
    {
      add(value.getValue().getBytes(BinaryDecoder.UTF8_CHARSET));
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeNullableIdentifier(final Identifier value) throws MALException
  {
    try
    {
      if ((null != value) && (null != value.getValue()))
      {
        encodeIdentifier(value);
      }
      else
      {
        add((byte[]) null);
      }
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeString(final String value) throws MALException
  {
    try
    {
      add(value.getBytes(BinaryDecoder.UTF8_CHARSET));
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeNullableString(final String value) throws MALException
  {
    try
    {
      if (null != value)
      {
        encodeString(value);
      }
      else
      {
        add((byte[]) null);
      }
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeInteger(final Integer value) throws MALException
  {
    try
    {
      addSignedInt(value);
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeNullableInteger(final Integer value) throws MALException
  {
    try
    {
      if (null != value)
      {
        addNotNull();
        encodeInteger(value);
      }
      else
      {
        addNull();
      }
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeBoolean(final Boolean value) throws MALException
  {
    try
    {
      if (value)
      {
        directAdd((byte) 1);
      }
      else
      {
        directAdd((byte) 0);
      }
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
        encodeBoolean(value);
      }
      else
      {
        directAdd((byte) 2);
      }
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeTime(final Time value) throws MALException
  {
    try
    {
      addUnsignedLong(value.getValue());
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeNullableTime(final Time value) throws MALException
  {
    try
    {
      if (null != value)
      {
        addNotNull();
        encodeTime(value);
      }
      else
      {
        addNull();
      }
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeFineTime(final FineTime value) throws MALException
  {
    try
    {
      addUnsignedLong(value.getValue());
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeNullableFineTime(final FineTime value) throws MALException
  {
    try
    {
      if (null != value)
      {
        addNotNull();
        encodeFineTime(value);
      }
      else
      {
        addNull();
      }
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeBlob(final Blob value) throws MALException
  {
    try
    {
      add(value.getValue());
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeNullableBlob(final Blob value) throws MALException
  {
    try
    {
      if ((null == value)
              || (value.isURLBased() && (null == value.getURL()))
              || (!value.isURLBased() && (null == value.getValue())))
      {
        add((byte[]) null);
      }
      else
      {
        encodeBlob(value);
      }
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeDuration(final Duration value) throws MALException
  {
    try
    {
      addUnsignedInt(value.getValue());
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeNullableDuration(final Duration value) throws MALException
  {
    try
    {
      if (null != value)
      {
        addNotNull();
        encodeDuration(value);
      }
      else
      {
        addNull();
      }
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeFloat(final Float value) throws MALException
  {
    try
    {
      addSignedInt(Float.floatToRawIntBits(value.floatValue()));
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeNullableFloat(final Float value) throws MALException
  {
    try
    {
      if (null != value)
      {
        addNotNull();
        encodeFloat(value);
      }
      else
      {
        addNull();
      }
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeAttribute(final Attribute value) throws IllegalArgumentException, MALException
  {
    try
    {
      directAdd(value.getTypeShortForm().byteValue());
      value.encode(this);
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeNullableAttribute(final Attribute value) throws MALException
  {
    try
    {
      if (null != value)
      {
        addNotNull();
        encodeAttribute(value);
      }
      else
      {
        addNull();
      }
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeElement(final Element value) throws MALException
  {
    value.encode(this);
  }

  @Override
  public void encodeNullableElement(final Element value) throws MALException
  {
    try
    {
      if (null != value)
      {
        // Initial delim to represent not-null
        addNotNull();
        value.encode(this);
      }
      else
      {
        addNull();
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
    // Do nothing
  }

  protected void addNull() throws IOException
  {
    directAdd((byte) 0);
  }

  protected void addNotNull() throws IOException
  {
    directAdd((byte) 1);
  }

  protected void add(final byte[] val) throws IOException
  {
    if (null == val)
    {
      addSignedInt(-1);
    }
    else
    {
      addSignedInt(val.length);
      directAdd(val);
    }
  }

  protected void addSignedLong(final long value) throws IOException
  {
    addUnsignedLong((value << 1) ^ (value >> 63));
  }

  protected void addSignedInt(final int value) throws IOException
  {
    addUnsignedInt((value << 1) ^ (value >> 31));
  }

  protected void addUnsignedLong(long value) throws IOException
  {
    while ((value & 0xFFFFFFFFFFFFFF80L) != 0L)
    {
      directAdd((byte)(((int) value & 0x7F) | 0x80));
      value >>>= 7;
    }
    directAdd((byte)((int) value & 0x7F));
  }

  protected void addUnsignedInt(int value) throws IOException
  {
    while ((value & 0xFFFFFF80) != 0L)
    {
      directAdd((byte)((value & 0x7F) | 0x80));
      value >>>= 7;
    }
    directAdd((byte)(value & 0x7F));
  }

  protected void addUnsignedShort(short value) throws IOException
  {
    if ((value & 0xFF80) != 0L)
    {
      directAdd((byte)((value & 0x7F) | 0x80));
      value >>>= 7;
    }
    directAdd((byte)(value & 0x7F));
  }

  protected void directAdd(final byte[] val) throws IOException
  {
    outputStream.write(val);
  }

  protected void directAdd(final byte val) throws IOException
  {
    outputStream.write(val);
  }
}
