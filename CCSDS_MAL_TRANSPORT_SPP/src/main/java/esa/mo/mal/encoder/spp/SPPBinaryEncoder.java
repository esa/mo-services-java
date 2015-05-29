/* ----------------------------------------------------------------------------
 * Copyright (C) 2015      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO SPP Transport Framework
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
package esa.mo.mal.encoder.spp;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.List;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALListEncoder;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.ULong;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 * Implements the MALEncoder and MALListEncoder interfaces for a SPP binary encoding.
 */
public class SPPBinaryEncoder extends esa.mo.mal.encoder.binary.fixed.FixedBinaryEncoder
{
  protected static final byte[] PADDING =
  {
    0, 0, 0, 0, 0, 0, 0, 0
  };
  protected static final BigInteger ZERO = new BigInteger("0");
  protected static final BigInteger MAX_ULONG = new BigInteger("18446744073709551615");

  /**
   * Constructor.
   *
   * @param os Output stream to write to.
   */
  public SPPBinaryEncoder(final OutputStream os)
  {
    super(new SPPStreamHolder(os));
  }

  @Override
  public MALListEncoder createListEncoder(List value) throws MALException
  {
    try
    {
      outputStream.addUnsignedShort((short) value.size());

      return this;
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
      BigInteger v = value.getValue();
      if (-1 == v.signum())
      {
        v = ZERO;
      }
      else if (0 > MAX_ULONG.compareTo(v))
      {
        v = MAX_ULONG;
      }

      byte[] buf = v.toByteArray();
      int pad = 8 - (buf.length - 1);
      if (0 < pad)
      {
        outputStream.directAdd(PADDING, 0, pad);
      }
      outputStream.directAdd(buf, 1, buf.length - 1);
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
        outputStream.addBoolTrue();
        encodeULong(value);
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
  public void encodeDuration(Duration value) throws MALException
  {
    long tm = value.getValue();

    int ms = (int) (tm % 1000);
    int s = (int) (tm / 1000);

    try
    {
      outputStream.directAdd(java.nio.ByteBuffer.allocate(4).putInt(s).array(), 0, 4);
      outputStream.directAdd(java.nio.ByteBuffer.allocate(4).putInt(ms).array(), 1, 3);
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeFineTime(FineTime value) throws MALException
  {
    long tm = value.getValue();

    int ms = (int) (tm % 1000);
    int s = (int) (tm / 1000);

    try
    {
      outputStream.directAdd(java.nio.ByteBuffer.allocate(4).putInt(s).array(), 0, 4);
      outputStream.directAdd(java.nio.ByteBuffer.allocate(4).putInt(ms).array(), 1, 3);
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeTime(Time value) throws MALException
  {
    long tm = value.getValue();

    int ms = (int) (tm % 1000);
    int s = (int) (tm / 1000);

    try
    {
      outputStream.directAdd(java.nio.ByteBuffer.allocate(4).putInt(s).array(), 0, 4);
      outputStream.directAdd(java.nio.ByteBuffer.allocate(4).putInt(ms).array(), 1, 3);
    }
    catch (IOException ex)
    {
      throw new MALException(ENCODING_EXCEPTION_STR, ex);
    }
  }

  @Override
  public void encodeNullableBlob(Blob value) throws MALException
  {
    try
    {
      if (null != value)
      {
        outputStream.addBoolTrue();
        encodeBlob(value);
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
  public void encodeNullableString(String value) throws MALException
  {
    try
    {
      if (null != value)
      {
        outputStream.addBoolTrue();
        encodeString(value);
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
  public void encodeNullableIdentifier(Identifier value) throws MALException
  {
    try
    {
      if (null != value)
      {
        outputStream.addBoolTrue();
        encodeIdentifier(value);
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
  public void encodeNullableURI(URI value) throws MALException
  {
    try
    {
      if (null != value)
      {
        outputStream.addBoolTrue();
        encodeURI(value);
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

  /**
   * Extends the FixedStreamHolder class for handling SPP fields.
   */
  protected static class SPPStreamHolder extends FixedStreamHolder
  {
    /**
     * Constructor.
     *
     * @param outputStream The output stream to encode into.
     */
    public SPPStreamHolder(OutputStream outputStream)
    {
      super(outputStream);
    }

    @Override
    public void add(byte[] val) throws IOException
    {
      if (null == val)
      {
        addSignedShort((short) -1);
      }
      else
      {
        addSignedShort((short) val.length);
        directAdd(val);
      }
    }
  }
}
