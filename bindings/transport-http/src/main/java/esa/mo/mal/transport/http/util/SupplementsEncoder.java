/* ----------------------------------------------------------------------------
 * Copyright (C) 2023      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO HTTP Transport Framework
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
package esa.mo.mal.transport.http.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NamedValue;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.ULong;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.Union;

import static esa.mo.mal.transport.http.HTTPTransport.RLOGGER;

public class SupplementsEncoder {

  private static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;

  private SupplementsEncoder() {
  }

  public static String encode(NamedValueList supplementList) throws UnsupportedEncodingException {
    if (supplementList == null) {
      return null;
    }
    if (supplementList.isEmpty()) {
      return "";
    }
    StringBuilder supplements = new StringBuilder();
    for (NamedValue supplement : supplementList) {
      if (supplements.length() > 0) {
        supplements.append("&");
      }
      String key = null;
      Identifier name = supplement.getName();
      if (name != null) {
        key = urlEncode(supplement.getName().getValue());
      }
      Attribute attribute = supplement.getValue();
      String value = null;
      if (attribute != null) {
        Integer shortForm = attribute.getTypeShortForm();
        String encodedAttribute = encodeAttribute(attribute);
        value = String.format("%d_%s", shortForm, urlEncode(encodedAttribute));
      }
      String keyValue = String.format("%s=%s", key, value);
      supplements.append(keyValue);
    }
    return supplements.toString();
  }

  public static NamedValueList decode(String supplementsString) throws UnsupportedEncodingException {
    NamedValueList supplements = new NamedValueList();
    if (supplementsString == null || supplementsString.isEmpty()) {
      return supplements;
    }
    for (String supplementString : supplementsString.split("\\&")) {
      String[] supplement = supplementString.split("\\=", 2);
      Identifier name = new Identifier(urlDecode(supplement[0]));
      Attribute attribute = null;
      if (supplement.length > 1) {
        String[] typeValue = supplement[1].split("_", 2);
        if (typeValue[0].equals("null")) {
          attribute = null;
        } else {
          Integer shortForm = Integer.valueOf(typeValue[0]);
          String value = urlDecode(typeValue[1]);
          attribute = decodeAttribute(value, shortForm);
        }
      }
      supplements.add(new NamedValue(name, attribute));
    }
    return supplements;
  }

  private static String encodeAttribute(Attribute value) {
    if (value == null) {
      return "null";
    }

    if (value instanceof Union) {
      return encodeUnion((Union) value);
    }

    if (value instanceof Duration) {
      return String.valueOf(((Duration) value).toString());
    }

    if (value instanceof UOctet) {
      return String.valueOf(((UOctet) value).getValue());
    }

    if (value instanceof UShort) {
      return String.valueOf(((UShort) value).getValue());
    }

    if (value instanceof UInteger) {
      return String.valueOf(((UInteger) value).getValue());
    }

    if (value instanceof Blob) {
      try {
        return new String(((Blob) value).getValue(), UTF8_CHARSET);
      } catch (MALException e) {
        RLOGGER.log(Level.WARNING, e.getMessage(), e);
        return null;
      }
    }

    if (value instanceof ULong) {
      return String.valueOf(((ULong) value).getValue());
    }

    if (value instanceof Time) {
      return String.valueOf(((Time) value).getValue());
    }

    if (value instanceof Identifier) {
      return ((Identifier) value).getValue();
    }

    if (value instanceof FineTime) {
      return String.valueOf(((FineTime) value).getValue());
    }

    if (value instanceof URI) {
      return ((URI) value).toString();
    }

    if (value instanceof ObjectRef) {
      ObjectRef objectRef = (ObjectRef) value;
      List<String> domainList = objectRef.getDomain()
        .stream()
        .map(identifier -> identifier.getValue())
        .collect(Collectors.toList());
      String encodedDomain = String.join(",", domainList);
      return String.format("%s:%d:%s:%d",
          encodedDomain,
          objectRef.getabsoluteSFP().longValue(),
          objectRef.getKey().getValue(),
          objectRef.getObjectVersion().getValue());
    }

    return "";
  }

  private static String encodeUnion(Union union) {
    if (union.getTypeShortForm().equals(Attribute.DOUBLE_TYPE_SHORT_FORM)) {
      if (union.getDoubleValue() == null) {
        return "";
      }
      return union.getDoubleValue().toString();
    }

    if (union.getTypeShortForm().equals(Attribute.BOOLEAN_TYPE_SHORT_FORM)) {
      if (union.getBooleanValue() == null) {
        return "";
      }
      return union.getBooleanValue().booleanValue() ? "true" : "false";
    }

    if (union.getTypeShortForm().equals(Attribute.FLOAT_TYPE_SHORT_FORM)) {
      if (union.getFloatValue() == null) {
        return "";
      }
      return (union.getFloatValue()).toString();
    }

    if (union.getTypeShortForm().equals(Attribute.INTEGER_TYPE_SHORT_FORM)) {
      if (union.getIntegerValue() == null) {
        return "";
      }
      return (union.getIntegerValue()).toString();
    }

    if (union.getTypeShortForm().equals(Attribute.LONG_TYPE_SHORT_FORM)) {
      if (union.getLongValue() == null) {
        return "";
      }
      return (union.getLongValue()).toString();
    }

    if (union.getTypeShortForm().equals(Attribute.OCTET_TYPE_SHORT_FORM)) {
      if (union.getOctetValue() == null) {
        return "";
      }
      return (union.getOctetValue()).toString();
    }

    if (union.getTypeShortForm().equals(Attribute.SHORT_TYPE_SHORT_FORM)) {
      if (union.getShortValue() == null) {
        return "";
      }
      return (union.getShortValue()).toString();
    }

    if (union.getTypeShortForm().equals(Attribute.STRING_TYPE_SHORT_FORM)) {
      if (union.getStringValue() == null) {
        return "";
      }
      return union.getStringValue();
    }

    return "";
  }

  private static Attribute decodeAttribute(String value, Integer shortFormType) {
    if (value == null) {
      throw new IllegalArgumentException("The value must not be null.");
    }
    if (value.isEmpty()) {
      return null;
    }

    switch (shortFormType) {
      case Attribute._BLOB_TYPE_SHORT_FORM:
        return new Blob(value.getBytes(UTF8_CHARSET));
      case Attribute._BOOLEAN_TYPE_SHORT_FORM:
        return new Union(Boolean.parseBoolean(value));
      case Attribute._DURATION_TYPE_SHORT_FORM:
        return new Duration(Double.parseDouble(value));
      case Attribute._FLOAT_TYPE_SHORT_FORM:
        return new Union(Float.parseFloat(value));
      case Attribute._DOUBLE_TYPE_SHORT_FORM:
        return new Union(Double.parseDouble(value));
      case Attribute._IDENTIFIER_TYPE_SHORT_FORM:
        return new Identifier(value);
      case Attribute._OCTET_TYPE_SHORT_FORM:
        return new Union(Byte.parseByte(value));
      case Attribute._UOCTET_TYPE_SHORT_FORM:
        return new UOctet(Short.parseShort(value));
      case Attribute._SHORT_TYPE_SHORT_FORM:
        return new Union(Short.parseShort(value));
      case Attribute._USHORT_TYPE_SHORT_FORM:
        return new UShort(Integer.parseInt(value));
      case Attribute._INTEGER_TYPE_SHORT_FORM:
        return new Union(Integer.parseInt(value));
      case Attribute._UINTEGER_TYPE_SHORT_FORM:
        return new UInteger(Long.parseLong(value));
      case Attribute._LONG_TYPE_SHORT_FORM:
        return new Union(Long.parseLong(value));
      case Attribute._ULONG_TYPE_SHORT_FORM:
        return new ULong(new BigInteger(value));
      case Attribute._STRING_TYPE_SHORT_FORM:
        return new Union(value);
      case Attribute._TIME_TYPE_SHORT_FORM:
        return new Time(Long.parseLong(value));
      case Attribute._FINETIME_TYPE_SHORT_FORM:
        return new FineTime(Long.parseLong(value));
      case Attribute._URI_TYPE_SHORT_FORM:
        return new URI(value);
      case Attribute._OBJECTREF_TYPE_SHORT_FORM:
        String[] values = value.split(":");
        String[] domainValues = values[0].split(",");
        ArrayList<Identifier> domainList = Arrays.asList(domainValues).stream()
          .map(domainValue -> new Identifier(domainValue))
          .collect(Collectors.toCollection(ArrayList::new));
        return new ObjectRef<>(
            new IdentifierList(domainList),
            new Long(values[1]),
            new Identifier(values[2]),
            new UInteger(Long.parseLong(values[3])));
      default:
        return null;
    }
  }

  private static String urlEncode(String value) throws UnsupportedEncodingException {
    return URLEncoder.encode(value, "UTF-8");
  }

  private static String urlDecode(String value) throws UnsupportedEncodingException {
    return URLDecoder.decode(value, "UTF-8");
  }
}
