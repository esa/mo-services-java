/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO String encoder
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
package esa.mo.mal.encoder.string;

import esa.mo.mal.encoder.gen.GENEncoder;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALListEncoder;
import org.ccsds.moims.mo.mal.structures.*;

/**
 * The implementation of the MALEncoder and MALListEncoder interfaces for the String encoding.
 */
public class StringEncoder extends GENEncoder
{
  private static final String STR_DELIM = "|";
  private static final String STR_NULL = "_";
  private static final int HEX_MASK = 0xFF;
  private final Writer buffer;

  /**
   * Constructor.
   *
   * @param buffer The output stream to write to.
   */
  public StringEncoder(OutputStream buffer)
  {
    this.buffer = new PrintWriter(buffer, false);
  }

  @Override
  public MALListEncoder createListEncoder(final List value) throws MALException
  {
    encodeInteger(value.size());

    return this;
  }

  @Override
  public void encodeDouble(final Double value) throws MALException
  {
    add(value.toString());
  }

  @Override
  public void encodeNullableDouble(final Double value) throws MALException
  {
    String strVal = STR_NULL;
    if (null != value)
    {
      strVal = value.toString();
    }
    add(strVal);
  }

  @Override
  public void encodeLong(final Long value) throws MALException
  {
    add(value.toString());
  }

  @Override
  public void encodeNullableLong(final Long value) throws MALException
  {
    String strVal = STR_NULL;
    if (null != value)
    {
      strVal = value.toString();
    }
    add(strVal);
  }

  @Override
  public void encodeOctet(final Byte value) throws MALException
  {
    add(value.toString());
  }

  @Override
  public void encodeNullableOctet(final Byte value) throws MALException
  {
    String strVal = STR_NULL;
    if (null != value)
    {
      strVal = value.toString();
    }
    add(strVal);
  }

  @Override
  public void encodeShort(final Short value) throws MALException
  {
    add(value.toString());
  }

  @Override
  public void encodeNullableShort(final Short value) throws MALException
  {
    String strVal = STR_NULL;
    if (null != value)
    {
      strVal = value.toString();
    }
    add(strVal);
  }

  @Override
  public void encodeUInteger(final UInteger value) throws IllegalArgumentException, MALException
  {
    add(Long.toString(value.getValue()));
  }

  @Override
  public void encodeNullableUInteger(final UInteger value) throws MALException
  {
    String strVal = STR_NULL;
    if (null != value)
    {
      strVal = Long.toString(value.getValue());
    }
    add(strVal);
  }

  @Override
  public void encodeULong(final ULong value) throws IllegalArgumentException, MALException
  {
    add(value.getValue().toString());
  }

  @Override
  public void encodeNullableULong(final ULong value) throws MALException
  {
    String strVal = STR_NULL;
    if (null != value)
    {
      strVal = value.getValue().toString();
    }
    add(strVal);
  }

  @Override
  public void encodeUOctet(final UOctet value) throws IllegalArgumentException, MALException
  {
    add(Short.toString(value.getValue()));
  }

  @Override
  public void encodeNullableUOctet(final UOctet value) throws MALException
  {
    String strVal = STR_NULL;
    if (null != value)
    {
      strVal = Short.toString(value.getValue());
    }
    add(strVal);
  }

  @Override
  public void encodeUShort(final UShort value) throws IllegalArgumentException, MALException
  {
    add(Integer.toString(value.getValue()));
  }

  @Override
  public void encodeNullableUShort(final UShort value) throws MALException
  {
    String strVal = STR_NULL;
    if (null != value)
    {
      strVal = Integer.toString(value.getValue());
    }
    add(strVal);
  }

  @Override
  public void encodeURI(final URI value) throws MALException
  {
    add(value.getValue());
  }

  @Override
  public void encodeNullableURI(final URI value) throws MALException
  {
    String strVal = STR_NULL;
    if ((null != value) && (null != value.getValue()))
    {
      strVal = value.getValue();
    }
    add(strVal);
  }

  @Override
  public void encodeIdentifier(final Identifier value) throws MALException
  {
    add(value.getValue());
  }

  @Override
  public void encodeNullableIdentifier(final Identifier value) throws MALException
  {
    String strVal = STR_NULL;
    if ((null != value) && (null != value.getValue()))
    {
      strVal = value.getValue();
    }
    add(strVal);
  }

  @Override
  public void encodeString(final String value) throws MALException
  {
    add(value);
  }

  @Override
  public void encodeNullableString(final String value) throws MALException
  {
    String strVal = STR_NULL;
    if (null != value)
    {
      strVal = value;
    }
    add(strVal);
  }

  @Override
  public void encodeInteger(final Integer value) throws MALException
  {
    add(value.toString());
  }

  @Override
  public void encodeNullableInteger(final Integer value) throws MALException
  {
    String strVal = STR_NULL;
    if (null != value)
    {
      strVal = value.toString();
    }
    add(strVal);
  }

  @Override
  public void encodeBoolean(final Boolean value) throws MALException
  {
    add(value.toString());
  }

  @Override
  public void encodeNullableBoolean(final Boolean value) throws MALException
  {
    String strVal = STR_NULL;
    if (null != value)
    {
      strVal = value.toString();
    }
    add(strVal);
  }

  @Override
  public void encodeTime(final Time value) throws MALException
  {
    add(Long.toString(value.getValue()));
  }

  @Override
  public void encodeNullableTime(final Time value) throws MALException
  {
    String strVal = STR_NULL;
    if (null != value)
    {
      strVal = Long.toString(value.getValue());
    }
    add(strVal);
  }

  @Override
  public void encodeFineTime(final FineTime value) throws MALException
  {
    add(Long.toString(value.getValue()));
  }

  @Override
  public void encodeNullableFineTime(final FineTime value) throws MALException
  {
    String strVal = STR_NULL;
    if (null != value)
    {
      strVal = Long.toString(value.getValue());
    }
    add(strVal);
  }

  @Override
  public void encodeBlob(final Blob value) throws MALException
  {
    add(byteArrayToHexString(value.getValue()));
  }

  @Override
  public void encodeNullableBlob(final Blob value) throws MALException
  {
    //should encode to 64 bit char string
    if ((null == value)
            || (value.isURLBased() && (null == value.getURL()))
            || (!value.isURLBased() && (null == value.getValue())))
    {
      add(STR_NULL);
    }
    else
    {
      add(byteArrayToHexString(value.getValue()));
    }
  }

  @Override
  public void encodeDuration(final Duration value) throws MALException
  {
    add(Integer.toString(value.getValue()));
  }

  @Override
  public void encodeNullableDuration(final Duration value) throws MALException
  {
    String strVal = STR_NULL;
    if (null != value)
    {
      strVal = Integer.toString(value.getValue());
    }
    add(strVal);
  }

  @Override
  public void encodeFloat(final Float value) throws MALException
  {
    add(value.toString());
  }

  @Override
  public void encodeNullableFloat(final Float value) throws MALException
  {
    String strVal = STR_NULL;
    if (null != value)
    {
      strVal = value.toString();
    }
    add(strVal);
  }

  @Override
  public void encodeAttribute(final Attribute value) throws IllegalArgumentException, MALException
  {
    add(Byte.toString(value.getShortForm().byteValue()));
    value.encode(this);
  }

  @Override
  public void encodeNullableAttribute(final Attribute value) throws MALException
  {
    if (null != value)
    {
      add(Byte.toString(value.getShortForm().byteValue()));
      value.encode(this);
    }
    else
    {
      add(STR_NULL);
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
    if (null != value)
    {
      // Initial delim to represent not-null
      add("");
      value.encode(this);
    }
    else
    {
      add(STR_NULL);
    }
  }

  @Override
  public void close()
  {
    // Do nothing
  }

  private void add(final String val) throws MALException
  {
    try
    {
      buffer.append(val);
      buffer.append(STR_DELIM);
    }
    catch (Exception ex)
    {
      throw new MALException(ex.getLocalizedMessage(), ex);
    }
  }

  private static String byteArrayToHexString(final byte[] data)
  {
    final StringBuilder hexString = new StringBuilder();
    for (int i = 0; i < data.length; i++)
    {
      final String hex = Integer.toHexString(HEX_MASK & data[i]);
      if (hex.length() == 1)
      {
        hexString.append('0');
      }
      hexString.append(hex);
    }

    return hexString.toString();
  }
}
