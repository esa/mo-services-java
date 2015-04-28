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
  protected final StreamHolder outputStream;

  /**
   * Constructor.
   *
   * @param os Output stream to write to.
   */
  public BinaryEncoder(final OutputStream os)
  {
    this.outputStream = new StreamHolder(os);
  }

  /**
   * Constructor for derived classes that have their own stream holder implementation that should be used.
   *
   * @param os Output stream to write to.
   */
  protected BinaryEncoder(final StreamHolder os)
  {
    this.outputStream = os;
  }

  @Override
  public MALListEncoder createListEncoder(final java.util.List value) throws MALException
  {
    try
    {
      outputStream.addUnsignedInt(value.size());

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
      outputStream.addSignedLong(Double.doubleToRawLongBits(value));
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
        outputStream.addBoolTrue();
        encodeDouble(value);
      }
      else
      {
        outputStream.addBoolFalse();
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
      outputStream.addSignedLong(value);
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
        outputStream.addBoolTrue();
        encodeLong(value);
      }
      else
      {
        outputStream.addBoolFalse();
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
      outputStream.directAdd(value);
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
        outputStream.addBoolTrue();
        encodeOctet(value);
      }
      else
      {
        outputStream.addBoolFalse();
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
      outputStream.addSignedShort(value);
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
        outputStream.addBoolTrue();
        encodeShort(value);
      }
      else
      {
        outputStream.addBoolFalse();
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
      outputStream.addUnsignedLong32(value.getValue());
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
        outputStream.addBoolTrue();
        encodeUInteger(value);
      }
      else
      {
        outputStream.addBoolFalse();
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
      outputStream.add(value.getValue().toByteArray());
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
        outputStream.add((byte[]) null);
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
      outputStream.addUnsignedShort8(value.getValue());
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
        outputStream.addBoolTrue();
        encodeUOctet(value);
      }
      else
      {
        outputStream.addBoolFalse();
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
      outputStream.addUnsignedInt16(value.getValue());
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
        outputStream.addBoolTrue();
        encodeUShort(value);
      }
      else
      {
        outputStream.addBoolFalse();
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
      outputStream.add(value.getValue().getBytes(BinaryDecoder.UTF8_CHARSET));
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
        outputStream.add((byte[]) null);
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
      outputStream.add(value.getValue().getBytes(BinaryDecoder.UTF8_CHARSET));
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
        outputStream.add((byte[]) null);
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
      outputStream.add(value.getBytes(BinaryDecoder.UTF8_CHARSET));
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
        outputStream.add((byte[]) null);
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
      outputStream.addSignedInt(value);
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
        outputStream.addBoolTrue();
        encodeInteger(value);
      }
      else
      {
        outputStream.addBoolFalse();
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
        outputStream.directAdd((byte) 1);
      }
      else
      {
        outputStream.directAdd((byte) 0);
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
        outputStream.directAdd((byte) 2);
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
      outputStream.addUnsignedLong(value.getValue());
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
        outputStream.addBoolTrue();
        encodeTime(value);
      }
      else
      {
        outputStream.addBoolFalse();
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
      outputStream.addUnsignedLong(value.getValue());
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
        outputStream.addBoolTrue();
        encodeFineTime(value);
      }
      else
      {
        outputStream.addBoolFalse();
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
      outputStream.add(value.getValue());
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
        outputStream.add((byte[]) null);
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
      outputStream.addSignedInt(value.getValue());
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
        outputStream.addBoolTrue();
        encodeDuration(value);
      }
      else
      {
        outputStream.addBoolFalse();
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
      outputStream.addSignedInt(Float.floatToRawIntBits(value));
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
        outputStream.addBoolTrue();
        encodeFloat(value);
      }
      else
      {
        outputStream.addBoolFalse();
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
      outputStream.directAdd(value.getTypeShortForm().byteValue());
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
        outputStream.addBoolTrue();
        encodeAttribute(value);
      }
      else
      {
        outputStream.addBoolFalse();
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
        outputStream.addBoolTrue();
        value.encode(this);
      }
      else
      {
        outputStream.addBoolFalse();
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

  /**
   * Internal class for accessing the binary stream. Overridden by sub-classes to alter the low level encoding.
   */
  protected static class StreamHolder
  {
    protected final OutputStream outputStream;

    /**
     * Constructor.
     *
     * @param outputStream the stream to encode in to.
     */
    public StreamHolder(OutputStream outputStream)
    {
      this.outputStream = outputStream;
    }

    /**
     * Adds a byte array to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public void add(final byte[] value) throws IOException
    {
      if (null == value)
      {
        addSignedInt(-1);
      }
      else
      {
        addSignedInt(value.length);
        directAdd(value);
      }
    }

    /**
     * Adds a signed long to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public void addSignedLong(final long value) throws IOException
    {
      addUnsignedLong((value << 1) ^ (value >> 63));
    }

    /**
     * Adds a signed int to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public void addSignedInt(final int value) throws IOException
    {
      addUnsignedInt((value << 1) ^ (value >> 31));
    }

    /**
     * Adds a signed short to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public void addSignedShort(final short value) throws IOException
    {
      addUnsignedInt((value << 1) ^ (value >> 31));
    }

    /**
     * Adds a zigzag encoded unsigned long to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public void addUnsignedLong(long value) throws IOException
    {
      while ((value & 0xFFFFFFFFFFFFFF80L) != 0L)
      {
        directAdd((byte) (((int) value & 0x7F) | 0x80));
        value >>>= 7;
      }
      directAdd((byte) ((int) value & 0x7F));
    }

    /**
     * Adds an unsigned 32bit integer held as a long to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public void addUnsignedLong32(long value) throws IOException
    {
      addUnsignedLong(value);
    }

    /**
     * Adds a zigzag encoded unsigned int to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public void addUnsignedInt(int value) throws IOException
    {
      while ((value & 0xFFFFFF80) != 0L)
      {
        directAdd((byte) ((value & 0x7F) | 0x80));
        value >>>= 7;
      }
      directAdd((byte) (value & 0x7F));
    }

    /**
     * Adds an unsigned 32bit integer held as a long to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public void addUnsignedInt16(int value) throws IOException
    {
      addUnsignedInt(value);
    }

    /**
     * Adds a zigzag encoded unsigned short to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public void addUnsignedShort(int value) throws IOException
    {
      addUnsignedInt(value);
    }

    /**
     * Adds an unsigned 32bit integer held as a long to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public void addUnsignedShort8(short value) throws IOException
    {
      addUnsignedShort(value);
    }

    /**
     * Adds a Boolean to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public void addBool(boolean value) throws IOException
    {
      if (value)
      {
        addBoolTrue();
      }
      else
      {
        addBoolFalse();
      }
    }

    /**
     * Adds a Boolean True value to the output stream.
     *
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public void addBoolTrue() throws IOException
    {
      directAdd((byte) 1);
    }

    /**
     * Adds a Boolean False value to the output stream.
     *
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public void addBoolFalse() throws IOException
    {
      directAdd((byte) 0);
    }

    /**
     * Low level byte array write to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public void directAdd(final byte[] value) throws IOException
    {
      outputStream.write(value);
    }

    /**
     * Low level byte array write to the output stream.
     *
     * @param value the value to encode.
     * @param os offset into array.
     * @param ln length to add.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public void directAdd(final byte[] value, int os, int ln) throws IOException
    {
      outputStream.write(value, os, ln);
    }

    /**
     * Low level byte write to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public void directAdd(final byte value) throws IOException
    {
      outputStream.write(value);
    }
  }
}
