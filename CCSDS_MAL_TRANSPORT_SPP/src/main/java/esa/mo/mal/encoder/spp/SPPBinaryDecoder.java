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

import static esa.mo.mal.encoder.spp.SPPBinaryStreamFactory.SECONDS_FROM_CCSDS_TO_UNIX_EPOCH;
import java.math.BigInteger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.ULong;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 * Implements the MALDecoder interface for a SPP binary encoding.
 */
public class SPPBinaryDecoder extends esa.mo.mal.encoder.binary.fixed.FixedBinaryDecoder
{
  private final boolean smallLengthField;
  private final boolean timeScaleIsUTC;
  private final boolean timeEpoch;
  private final int timeMajorUnitFieldLength;
  private final int timeMinorUnitFieldLength;
  private final boolean fineTimeScaleIsUTC;
  private final boolean fineTimeEpoch;
  private final int fineTimeMajorUnitFieldLength;
  private final int fineTimeMinorUnitFieldLength;

  /**
   * Constructor.
   *
   * @param src Byte array to read from.
   * @param smallLengthField True if length field is 16bits, otherwise assumed to be 32bits.
   */
  public SPPBinaryDecoder(final byte[] src, final boolean smallLengthField,
          final boolean timeScaleIsUTC,
          final boolean timeEpoch,
          final int timeMajorUnitFieldLength,
          final int timeMinorUnitFieldLength,
          final boolean fineTimeScaleIsUTC,
          final boolean fineTimeEpoch,
          final int fineTimeMajorUnitFieldLength,
          final int fineTimeMinorUnitFieldLength)
  {
    super(new SPPBufferHolder(null, src, 0, src.length, smallLengthField));

    this.smallLengthField = smallLengthField;
    this.timeScaleIsUTC = timeScaleIsUTC;
    this.timeEpoch = timeEpoch;
    this.timeMajorUnitFieldLength = timeMajorUnitFieldLength;
    this.timeMinorUnitFieldLength = timeMinorUnitFieldLength;
    this.fineTimeScaleIsUTC = fineTimeScaleIsUTC;
    this.fineTimeEpoch = fineTimeEpoch;
    this.fineTimeMajorUnitFieldLength = fineTimeMajorUnitFieldLength;
    this.fineTimeMinorUnitFieldLength = fineTimeMinorUnitFieldLength;
  }

  /**
   * Constructor.
   *
   * @param is Input stream to read from.
   * @param smallLengthField True if length field is 16bits, otherwise assumed to be 32bits.
   */
  public SPPBinaryDecoder(final java.io.InputStream is, final boolean smallLengthField,
          final boolean timeScaleIsUTC,
          final boolean timeEpoch,
          final int timeMajorUnitFieldLength,
          final int timeMinorUnitFieldLength,
          final boolean fineTimeScaleIsUTC,
          final boolean fineTimeEpoch,
          final int fineTimeMajorUnitFieldLength,
          final int fineTimeMinorUnitFieldLength)
  {
    super(new SPPBufferHolder(is, null, 0, 0, smallLengthField));

    this.smallLengthField = smallLengthField;
    this.timeScaleIsUTC = timeScaleIsUTC;
    this.timeEpoch = timeEpoch;
    this.timeMajorUnitFieldLength = timeMajorUnitFieldLength;
    this.timeMinorUnitFieldLength = timeMinorUnitFieldLength;
    this.fineTimeScaleIsUTC = fineTimeScaleIsUTC;
    this.fineTimeEpoch = fineTimeEpoch;
    this.fineTimeMajorUnitFieldLength = fineTimeMajorUnitFieldLength;
    this.fineTimeMinorUnitFieldLength = fineTimeMinorUnitFieldLength;
  }

  /**
   * Constructor.
   *
   * @param src Byte array to read from.
   * @param offset index in array to start reading from.
   * @param smallLengthField True if length field is 16bits, otherwise assumed to be 32bits.
   */
  public SPPBinaryDecoder(final byte[] src, final int offset, final boolean smallLengthField,
          final boolean timeScaleIsUTC,
          final boolean timeEpoch,
          final int timeMajorUnitFieldLength,
          final int timeMinorUnitFieldLength,
          final boolean fineTimeScaleIsUTC,
          final boolean fineTimeEpoch,
          final int fineTimeMajorUnitFieldLength,
          final int fineTimeMinorUnitFieldLength)
  {
    super(new SPPBufferHolder(null, src, offset, src.length, smallLengthField));

    this.smallLengthField = smallLengthField;
    this.timeScaleIsUTC = timeScaleIsUTC;
    this.timeEpoch = timeEpoch;
    this.timeMajorUnitFieldLength = timeMajorUnitFieldLength;
    this.timeMinorUnitFieldLength = timeMinorUnitFieldLength;
    this.fineTimeScaleIsUTC = fineTimeScaleIsUTC;
    this.fineTimeEpoch = fineTimeEpoch;
    this.fineTimeMajorUnitFieldLength = fineTimeMajorUnitFieldLength;
    this.fineTimeMinorUnitFieldLength = fineTimeMinorUnitFieldLength;
  }

  /**
   * Constructor.
   *
   * @param src Source buffer holder to use.
   * @param smallLengthField True if length field is 16bits, otherwise assumed to be 32bits.
   */
  protected SPPBinaryDecoder(final BufferHolder src, final boolean smallLengthField,
          final boolean timeScaleIsUTC,
          final boolean timeEpoch,
          final int timeMajorUnitFieldLength,
          final int timeMinorUnitFieldLength,
          final boolean fineTimeScaleIsUTC,
          final boolean fineTimeEpoch,
          final int fineTimeMajorUnitFieldLength,
          final int fineTimeMinorUnitFieldLength)
  {
    super(src);

    this.smallLengthField = smallLengthField;
    this.timeScaleIsUTC = timeScaleIsUTC;
    this.timeEpoch = timeEpoch;
    this.timeMajorUnitFieldLength = timeMajorUnitFieldLength;
    this.timeMinorUnitFieldLength = timeMinorUnitFieldLength;
    this.fineTimeScaleIsUTC = fineTimeScaleIsUTC;
    this.fineTimeEpoch = fineTimeEpoch;
    this.fineTimeMajorUnitFieldLength = fineTimeMajorUnitFieldLength;
    this.fineTimeMinorUnitFieldLength = fineTimeMinorUnitFieldLength;
  }

  @Override
  public org.ccsds.moims.mo.mal.MALListDecoder createListDecoder(final java.util.List list) throws MALException
  {
    return new SPPBinaryListDecoder(list, sourceBuffer,
            smallLengthField, timeScaleIsUTC, timeEpoch, timeMajorUnitFieldLength, timeMinorUnitFieldLength,
            fineTimeScaleIsUTC, fineTimeEpoch, fineTimeMajorUnitFieldLength, fineTimeMinorUnitFieldLength);
  }

  @Override
  public Boolean decodeNullableBoolean() throws MALException
  {
    if (sourceBuffer.getBool())
    {
      return decodeBoolean();
    }

    return null;
  }

  @Override
  public Blob decodeBlob() throws MALException
  {
    if (smallLengthField)
    {
      return new Blob(sourceBuffer.directGetBytes(sourceBuffer.getSignedShort()));
    }

    return super.decodeBlob();
  }

  @Override
  public Blob decodeNullableBlob() throws MALException
  {
    if (sourceBuffer.getBool())
    {
      return decodeBlob();
    }

    return null;
  }

  @Override
  public ULong decodeULong() throws MALException
  {
    byte[] buf =
    {
      0, 0, 0, 0, 0, 0, 0, 0, 0
    };

    System.arraycopy(sourceBuffer.directGetBytes(8), 0, buf, 1, 8);

    return new ULong(new BigInteger(buf));
  }

  @Override
  public ULong decodeNullableULong() throws MALException
  {
    if (sourceBuffer.getBool())
    {
      return decodeULong();
    }

    return null;
  }

  @Override
  public Time decodeTime() throws MALException
  {
    long s = getIntFromBytes(timeMajorUnitFieldLength);
    byte[] fs = sourceBuffer.directGetBytes(timeMinorUnitFieldLength);

    double subseconds = 0;
    for (int i = fs.length - 1; i >= 0; --i) {
      subseconds = (subseconds + (fs[i] & 0xFF)) / 256;
    }
    
    if (!timeScaleIsUTC)
    {
      // TAI scale is 10s out from UTC
      s -= 10;
    }
    
    if (timeEpoch)
    {
      // CCSDS time epoch is 1/1/1958
      s -= SECONDS_FROM_CCSDS_TO_UNIX_EPOCH;
    }
    
    s *= 1000;
    s +=  Math.round(subseconds * 1000.0);
    
    return new Time(s);
  }

  private int getIntFromBytes(int countToRead) throws MALException
  {
    byte[] fs = sourceBuffer.directGetBytes(countToRead);

    byte[] b = new byte[4];
    b[0] = 0;
    b[1] = 0;
    b[2] = 0;
    b[3] = 0;
    
    System.arraycopy(fs, 0, b, 4 - countToRead, countToRead);
    return java.nio.ByteBuffer.wrap(b).getInt();
  }
  
  @Override
  public Time decodeNullableTime() throws MALException
  {
    if (sourceBuffer.getBool())
    {
      return decodeTime();
    }

    return null;
  }

  @Override
  public FineTime decodeFineTime() throws MALException
  {
    long s = getIntFromBytes(fineTimeMajorUnitFieldLength);
    byte[] fs = sourceBuffer.directGetBytes(fineTimeMinorUnitFieldLength);

    double subseconds = 0;
    for (int i = fs.length - 1; i >= 0; --i) {
      subseconds = (subseconds + (fs[i] & 0xFF)) / 256;
    }
    
    if (!fineTimeScaleIsUTC)
    {
      // TAI scale is 10s out from UTC
      s -= 10;
    }
    
    if (fineTimeEpoch)
    {
      // CCSDS time epoch is 1/1/1958
      //s -= SECONDS_FROM_CCSDS_TO_UNIX_EPOCH;
    }
    
    s *= 1000000000000L;
    s +=  Math.round(subseconds * 1000000000000.0);
    
    return new FineTime(s);
  }

  @Override
  public FineTime decodeNullableFineTime() throws MALException
  {
    if (sourceBuffer.getBool())
    {
      return decodeFineTime();
    }

    return null;
  }

  @Override
  public Duration decodeDuration() throws MALException
  {
    long s = sourceBuffer.getUnsignedLong32() * 1000;
    byte[] ss = sourceBuffer.directGetBytes(3);

    byte[] b = new byte[4];
    b[0] = 0;
    b[1] = ss[0];
    b[2] = ss[1];
    b[3] = ss[2];
    int ms = java.nio.ByteBuffer.wrap(b).getInt();

    s += ms;
    return new Duration(((double) s)/1000.0);
  }

  @Override
  public Duration decodeNullableDuration() throws MALException
  {
    if (sourceBuffer.getBool())
    {
      return decodeDuration();
    }

    return null;
  }

  @Override
  public String decodeNullableString() throws MALException
  {
    if (sourceBuffer.getBool())
    {
      return decodeString();
    }

    return null;
  }

  @Override
  public URI decodeNullableURI() throws MALException
  {
    if (sourceBuffer.getBool())
    {
      return decodeURI();
    }

    return null;
  }

  @Override
  public Identifier decodeNullableIdentifier() throws MALException
  {
    if (sourceBuffer.getBool())
    {
      return decodeIdentifier();
    }

    return null;
  }

  @Override
  protected int internalDecodeAttributeType(byte value) throws MALException
  {
    return value + 1;
  }

  /**
   * Extends the fixed length internal buffer holder to cope with the smaller size of the size field for Strings in SPP
   * packets.
   */
  protected static class SPPBufferHolder extends FixedBufferHolder
  {
    private final boolean smallLengthField;

    /**
     * Constructor.
     *
     * @param is Input stream to read from.
     * @param buf Source buffer to use.
     * @param offset Buffer offset to read from next.
     * @param length Length of readable data held in the array, which may be larger.
     * @param smallLengthField True if length field is 16bits, otherwise assumed to be 32bits.
     */
    public SPPBufferHolder(final java.io.InputStream is,
            final byte[] buf,
            final int offset,
            final int length,
            final boolean smallLengthField)
    {
      super(is, buf, offset, length);

      this.smallLengthField = smallLengthField;
    }

    @Override
    public String getString() throws MALException
    {
      if (smallLengthField)
      {
        final int len = getSignedShort();

        if (len >= 0)
        {
          checkBuffer(len);

          final String s = new String(buf, offset, len, UTF8_CHARSET);
          offset += len;
          return s;
        }

        return null;
      }
      else
      {
        return super.getString();
      }
    }
  }
}
