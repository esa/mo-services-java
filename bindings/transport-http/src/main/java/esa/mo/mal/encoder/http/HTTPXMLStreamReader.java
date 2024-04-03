package esa.mo.mal.encoder.http;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALElementsRegistry;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALListDecoder;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Enumeration;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.ULong;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.Union;

import esa.mo.mal.transport.http.util.UriHelper;

import static esa.mo.mal.transport.http.HTTPTransport.RLOGGER;

public class HTTPXMLStreamReader implements MALListDecoder {

  XMLInputFactory inputFactory;
  XMLEventReader eventReader;

  protected static final String XSI_NS = "http://www.w3.org/2001/XMLSchema-instance";
  protected static final String MAL_NS = "http://www.ccsds.org/schema/malxml/MAL";

  protected static final String MALFORMED_INPUT = "Malformed xml input";

  protected static final String LINE_END = "";
  protected static final String TAB = "";

  public HTTPXMLStreamReader() {
  }

  public HTTPXMLStreamReader(InputStream is) {

    try {

      if (is.available() > 0) {

        inputFactory = XMLInputFactory.newInstance();
        eventReader = inputFactory.createXMLEventReader(is);

        eventReader.nextEvent(); // xml header

        // message body
        XMLEvent event = eventReader.nextEvent();
        // skip \n, \t character events
        while (event.isCharacters()) {
          event = eventReader.nextEvent();
        }

        // validate allocation of body element in xml
        if (!(event.isStartElement() && event.asStartElement().getName().getLocalPart().equals("Body"))) {
          RLOGGER.severe("XML Malformed: Body element missing");
        }
      }

    } catch (Exception e) {
      RLOGGER.severe(e.getMessage());
      e.printStackTrace();
    }
  }

  @Override
  public Boolean decodeBoolean() throws MALException {

    return decodeXMLElement().equals("true");
  }

  @Override
  public Boolean decodeNullableBoolean() throws MALException {

    String decoded = decodeNullableXMLElement();
    if (decoded == null)
      return null;
    else if (decoded.equals(""))
      return false;
    return decoded.equals("true");
  }

  @Override
  public Float decodeFloat() throws MALException {

    return Float.valueOf(decodeXMLElement());
  }

  @Override
  public Float decodeNullableFloat() throws MALException {

    String decoded = decodeNullableXMLElement();
    if (decoded == null)
      return null;
    else if (decoded.equals(""))
      return 0f;
    return Float.valueOf(decoded);
  }

  @Override
  public Double decodeDouble() throws MALException {

    return Double.valueOf(decodeXMLElement());
  }

  @Override
  public Double decodeNullableDouble() throws MALException {

    String decoded = decodeNullableXMLElement();
    if (decoded == null)
      return null;
    else if (decoded.equals(""))
      return 0d;
    return Double.valueOf(decoded);
  }

  @Override
  public Byte decodeOctet() throws MALException {

    return Byte.valueOf(decodeXMLElement(), 10);
  }

  @Override
  public Byte decodeNullableOctet() throws MALException {

    String decoded = decodeNullableXMLElement();
    if (decoded == null)
      return null;
    else if (decoded.equals(""))
      return 0;
    return Byte.valueOf(decoded);
  }

  @Override
  public UOctet decodeUOctet() throws MALException {
    return new UOctet(Short.valueOf(decodeXMLElement()));
  }

  @Override
  public UOctet decodeNullableUOctet() throws MALException {

    String decoded = decodeNullableXMLElement();
    if (decoded == null)
      return null;
    else if (decoded.equals(""))
      return new UOctet((short) 0);
    return new UOctet(Short.valueOf(decodeXMLElement()));
  }

  @Override
  public Short decodeShort() throws MALException {
    return Short.valueOf(decodeXMLElement());
  }

  @Override
  public Short decodeNullableShort() throws MALException {

    String decoded = decodeNullableXMLElement();
    if (decoded == null)
      return null;
    else if (decoded.equals(""))
      return 0;
    return Short.valueOf(decoded);
  }

  @Override
  public UShort decodeUShort() throws MALException {
    return new UShort(Integer.valueOf(decodeXMLElement()));
  }

  @Override
  public UShort decodeNullableUShort() throws MALException {

    String decoded = decodeNullableXMLElement();
    if (decoded == null)
      return null;
    else
      return new UShort(Integer.valueOf(decoded));
  }

  @Override
  public Integer decodeInteger() throws MALException {
    return Integer.valueOf(decodeXMLElement());
  }

  @Override
  public Integer decodeNullableInteger() throws MALException {

    String decoded = decodeNullableXMLElement();
    if (decoded == null)
      return null;
    else if (decoded.equals(""))
      return 0;
    return Integer.valueOf(decoded);
  }

  @Override
  public UInteger decodeUInteger() throws MALException {
    Long longvalue = Long.valueOf(decodeXMLElement());
    return new UInteger(longvalue);
  }

  @Override
  public UInteger decodeNullableUInteger() throws MALException {

    String decoded = decodeNullableXMLElement();
    if (decoded == null)
      return null;
    else if (decoded.equals(""))
      return new UInteger();
    return new UInteger(Long.valueOf(decoded));
  }

  @Override
  public Long decodeLong() throws MALException {
    return Long.valueOf(decodeXMLElement());
  }

  @Override
  public Long decodeNullableLong() throws MALException {

    String decoded = decodeNullableXMLElement();
    if (decoded == null)
      return null;
    else if (decoded.equals(""))
      return 0L;
    return Long.valueOf(decoded);
  }

  @Override
  public ULong decodeULong() throws MALException {
    return new ULong(new BigInteger(decodeXMLElement()));
  }

  @Override
  public ULong decodeNullableULong() throws MALException {

    String decoded = decodeNullableXMLElement();
    if (decoded == null)
      return null;
    else if (decoded.equals(""))
      return new ULong();
    return new ULong(new BigInteger(decoded));
  }

  @Override
  public String decodeString() throws MALException {
    return decodeXMLElement();
  }

  @Override
  public String decodeNullableString() throws MALException {

    String decoded = decodeNullableXMLElement();
    if (decoded == null)
      return null;
    return decoded;
  }

  @Override
  public Blob decodeBlob() throws MALException {

    byte[] byteValue = hexStringToByteArray(decodeXMLElement());

    if (byteValue == null) {
      return new Blob();
    }
    return new Blob(byteValue);
  }

  @Override
  public Blob decodeNullableBlob() throws MALException {

    String decoded = decodeNullableXMLElement();
    if (decoded == null)
      return null;
    return new Blob(decoded);
  }

  @Override
  public Duration decodeDuration() throws MALException {

    String durationFormat = decodeXMLElement();
    java.time.Duration d = java.time.Duration.parse(durationFormat);
    Duration value = new Duration(d.getSeconds() + d.getNano() / 1e9d);
    return value;
  }

  @Override
  public Duration decodeNullableDuration() throws MALException {

    String durationFormat = decodeNullableXMLElement();
    if (durationFormat == null) {
      return null;
    }
    java.time.Duration d = java.time.Duration.parse(durationFormat);
    Duration value = new Duration(d.getSeconds() + d.getNano() / 1e9d);
    return value;
  }

  @Override
  public FineTime decodeFineTime() throws MALException {

    Instant i = Instant.parse(decodeXMLElement() + "Z");

    return new FineTime((long) (i.getEpochSecond() * 1e9) + i.getNano());
  }

  @Override
  public FineTime decodeNullableFineTime() throws MALException {

    Long value = decodeNullableLong();
    if (value == null)
      return null;
    return new FineTime(value);
  }

  @Override
  public Identifier decodeIdentifier() throws MALException {
    return new Identifier(decodeXMLElement());
  }

  @Override
  public Identifier decodeNullableIdentifier() throws MALException {

    String decoded = decodeNullableXMLElement();
    if (decoded == null)
      return null;
    return new Identifier(decoded);
  }

  @Override
  public Time decodeTime() throws MALException {

    Instant i = Instant.parse(decodeXMLElement() + "Z");

    return new Time(i.toEpochMilli());
  }

  @Override
  public Time decodeNullableTime() throws MALException {

    String decoded = decodeNullableXMLElement();
    if (decoded == null)
      return null;
    else if (decoded.equals(""))
      return new Time();

    Instant i = Instant.parse(decoded + "Z");
    return new Time(i.toEpochMilli());
  }

  @Override
  public URI decodeURI() throws MALException {
    String value = decodeXMLElement();
    return new URI(UriHelper.uriToUtf8(value));
  }

  @Override
  public URI decodeNullableURI() throws MALException {

    String decoded = decodeNullableXMLElement();
    if (decoded == null)
      return null;
    return new URI(decoded);
  }

  @Override
  public ObjectRef decodeObjectRef() throws MALException {
    return (ObjectRef) decodeElement(null);
  }

  @Override
  public ObjectRef decodeNullableObjectRef() throws MALException {
    Element element = decodeNullableElement(new ObjectRef());
    return (ObjectRef) element;
  }

  public Element decodeList(final Element element) throws MALException {

    Element returnable = null;

    try {
      XMLEvent event = eventReader.nextTag();

      if (event.isStartElement()) {

        StartElement se = event.asStartElement();

        javax.xml.stream.events.Attribute nillable = se.getAttributeByName(new QName(XSI_NS, "nil", "xsi"));

        if (nillable != null && nillable.getValue().equals("true")) {
          returnable = null;
        } else {
          returnable = element.decode(this);
        }

        if (eventReader.hasNext() && eventReader.peek().isEndElement()) {
          eventReader.next();
        }

      } else if (event.isEndElement()) {
        EndElement ee = event.asEndElement();
        if (openstandingEndElements.contains(ee.getName().getLocalPart())) {
          openstandingEndElements.remove(ee.getName().getLocalPart());
        }
      } else {
        throw new MALException(
            "Expected a xml start element or nullable for element " + element.getClass().getSimpleName());
      }

    } catch (XMLStreamException e) {
      throw new MALException(e.getMessage());
    }

    return returnable;
  }

  List<String> openstandingEndElements = new ArrayList<String>();

  @Override
  public Element decodeElement(Element element) throws IllegalArgumentException, MALException {

    Element returnable = null;
    String superClassName = null;

    try {

      if (eventReader == null || !eventReader.hasNext()) {
        return null;
      }

      while (eventReader.peek().isCharacters()
          && eventReader.peek().asCharacters().getData().matches("[\\n\\t]+")) {
        eventReader.next();
      }

      if (element == null && eventReader.peek().isStartElement()) {
        element = getElementByType(eventReader.peek().asStartElement());
      }

      if (eventReader.peek() != null && eventReader.peek().isEndElement()) {
        EndElement ee = eventReader.peek().asEndElement();
        String endElementName = ee.getName().getLocalPart();
        if (openstandingEndElements.contains(endElementName)) {
          openstandingEndElements.remove(endElementName);
          eventReader.nextTag();
        }
      }

      if (element instanceof List) {
        returnable = decodeList(element);

      } else {

        Map<String, Integer> openedElements = new HashMap<String, Integer>();
        if (element != null) {
          superClassName = element.getClass().getSuperclass().getName();
        }
        boolean done = false;

        while (eventReader.hasNext() && !done) {
          XMLEvent event = eventReader.nextTag();

          if (event.isStartElement()) {

            StartElement se = event.asStartElement();

            String possibleSuperClassName = null;
            if (eventReader.peek() != null && eventReader.peek().isStartElement()) {
              possibleSuperClassName = eventReader.peek().asStartElement().getName().getLocalPart();
            }

            int count = 0;
            if (openedElements.containsKey(se.getName().getLocalPart())) {
              count = openedElements.get(se.getName().getLocalPart());
            }
            openedElements.put(se.getName().getLocalPart(), ++count);

            if (element instanceof Composite && superClassName.equals(possibleSuperClassName)) {
              openstandingEndElements.add(possibleSuperClassName);
            } else if (element instanceof Enumeration) {
              returnable = decodeEnumeration((Enumeration) element);
            } else {

              javax.xml.stream.events.Attribute nillable = se.getAttributeByName(new QName(XSI_NS, "nil", "xsi"));
              if (nillable != null && nillable.getValue().equals("true")) {
                if (eventReader.peek().isEndElement()) {
                  eventReader.next();
                }
                return null;
              }

              if (element != null) {
                returnable = element.decode(this);
              } else {
                returnable = decodeElementByType(se.getName().getLocalPart(), se);
              }

            }

          } else if (event.isEndElement()) {

            EndElement ee = event.asEndElement();
            if (openedElements.containsKey(ee.getName().getLocalPart())) {
              int count = openedElements.get(ee.getName().getLocalPart()) - 1;
              if (count < 1) {
                openedElements.remove(ee.getName().getLocalPart());
                if (openedElements.containsKey(superClassName)) {
                  openedElements.remove(superClassName);
                }
              } else {
                openedElements.put(ee.getName().getLocalPart(), count);
              }
            }

            if (openedElements.size() < 1) {
              done = true;
            }
          }

          else {
            done = true;
          }
        }
      }

    } catch (XMLStreamException e) {
      RLOGGER.log(Level.SEVERE, e.getMessage(), e);
      throw new MALException(e.getMessage());
    }

    return returnable;
  }

  private Enumeration decodeEnumeration(Enumeration element) throws MALException {
    String enumValue = decodeXMLElement();

    Class<?> cls = element.getClass();
    Method m;
    try {
      m = cls.getMethod("fromString", String.class);

      if (m != null) {
        return (Enumeration) m.invoke(null, enumValue);
      }
    } catch (NoSuchMethodException e) {
      return null;
    } catch (Exception e) {
      RLOGGER.severe(e.getMessage());
      e.printStackTrace();
    }

    return null;
  }

  private Element getElementByType(StartElement event) throws MALException {

    if (event.getAttributeByName(new QName(MAL_NS, "type", "malxml")) != null) {

      javax.xml.stream.events.Attribute att = event.getAttributeByName(new QName(MAL_NS, "type", "malxml"));
      Long shortForm = Long.valueOf(att.getValue());

      MALElementsRegistry elementsRegistry = MALContextFactory.getElementsRegistry();

      try {
        return (Element) elementsRegistry.createElement(shortForm);
      } catch (Exception e) {
        throw new MALException("Can't find element with shortForm " + shortForm.toString());
      }
    } else {
      return null;
    }
  }

  private Element decodeElementByType(String typeName, StartElement event) throws MALException {

    if (typeName.equals("Blob")) {
      return decodeBlob();
    } else if (typeName.equals("Duration")) {
      return decodeDuration();
    } else if (typeName.equals("FineTime")) {
      return decodeFineTime();
    } else if (typeName.equals("Identifier")) {
      return decodeIdentifier();
    } else if (typeName.equals("Time")) {
      return decodeTime();
    } else if (typeName.equals("Union")) {
      return decodeUnion();
    } else if (typeName.equals("UInteger")) {
      return decodeUInteger();
    } else if (typeName.equals("ULong")) {
      return decodeULong();
    } else if (typeName.equals("URI")) {
      return decodeURI();
    } else if (typeName.equals("UShort")) {
      return decodeUShort();
    } else if (typeName.equals("String")) {
      return new Union(decodeString());
    } else if (typeName.equals("Float")) {
      return new Union(decodeFloat());
    } else if (typeName.equals("Boolean")) {
      return new Union(decodeBoolean());
    } else if (typeName.equals("URI")) {
      return decodeURI();
    } else if (typeName.equals("Integer")) {
      return new Union(decodeInteger());
    } else if (typeName.equals("ObjectRef")) {
      return new ObjectRef(
          (IdentifierList) decodeNullableElement(new IdentifierList()),
          decodeNullableLong(),
          decodeNullableIdentifier(),
          decodeNullableUInteger());
    } else if (typeName.equals("AttributeList")) {
      return new AttributeList().decode(this);
    } else if (event.getAttributeByName(new QName(MAL_NS, "type", "malxml")) != null) {
      // can't determine type by name, using xml type attribute

      javax.xml.stream.events.Attribute att = event.getAttributeByName(new QName(MAL_NS, "type", "malxml"));
      Long shortForm = Long.valueOf(att.getValue());

      MALElementsRegistry elementsRegistry = MALContextFactory.getElementsRegistry();

      try {
        Element el = (Element) elementsRegistry.createElement(shortForm);
        return el.decode(this);
      } catch (Exception e) {
        throw new MALException("Can't find element of type " + typeName + " with shortForm " + shortForm.toString());
      }
    } else {
      throw new MALException("Can't determine type of element " + typeName + "!");
    }
  }

  public Element decodeUnion() throws MALException {

    try {

      Union union = null;

      while (eventReader.peek().isCharacters()
          && eventReader.peek().asCharacters().getData().matches("[\\n\\t]+")) {
        eventReader.next();
      }

      XMLEvent nextEvent = eventReader.peek();
      if (nextEvent.isStartElement()) {
        StartElement se = nextEvent.asStartElement();
        String subElementType = se.getName().getLocalPart();

        if (subElementType.equals("Boolean")) {
          union = new Union(false);
        } else if (subElementType.equals("Float")) {
          union = new Union(0f);
        } else if (subElementType.equals("Double")) {
          union = new Union(0d);
        } else if (subElementType.equals("Integer")) {
          union = new Union(0);
        } else if (subElementType.equals("Long")) {
          union = new Union(0L);
        } else if (subElementType.equals("Octet")) {
          union = new Union((byte) 0);
        } else if (subElementType.equals("Short")) {
          union = new Union((short) 0);
        } else {
          union = new Union("");
        }

        return union.decode(this);
      }

    } catch (XMLStreamException e) {
      throw new MALException(e.getMessage());
    }

    return null;
  }

  @Override
  public Element decodeNullableElement(final Element element) throws IllegalArgumentException, MALException {

    return decodeElement(element);
  }

  @Override
  public Attribute decodeAttribute() throws MALException {

    return (Attribute) decodeElement(null);
  }

  @Override
  public Attribute decodeNullableAttribute() throws MALException {

    return (Attribute) decodeElement(null);
  }

  @Override
  public Element decodeAbstractElement() throws MALException {
    return decodeElement(null);
  }

  @Override
  public Element decodeNullableAbstractElement() throws MALException {
    return decodeNullableElement(null);
  }

  public String decodeXMLElement() throws MALException {
    return decodeXMLElement(false);
  }

  public String decodeNullableXMLElement() throws MALException {
    return decodeXMLElement(true);
  }

  public String decodeXMLElement(boolean mightBeNull) throws MALException {

    String value = "";

    try {

      XMLEvent event = null;
      boolean done = false;

      Map<String, Integer> openedElements = new HashMap<String, Integer>();

      while (eventReader.hasNext() && !done) {
        event = eventReader.nextEvent();

        if (event.isStartElement()) {
          StartElement se = event.asStartElement();
          int count = 0;
          if (openedElements.containsKey(se.getName().getLocalPart())) {
            count = openedElements.get(se.getName().getLocalPart());
          }
          openedElements.put(se.getName().getLocalPart(), ++count);

          if (mightBeNull) {
            javax.xml.stream.events.Attribute att = se.getAttributeByName(new QName(XSI_NS, "nil", "xsi"));
            if (att != null && att.getValue().equals("true")) {
              value = null;
            }
          }
        }

        if (event.isCharacters() && !event.asCharacters().getData().matches("[\\n\\t ]+")) {
          value = event.asCharacters().getData();
        }

        if (event.isEndElement()) {
          EndElement ee = event.asEndElement();
          if (openedElements.containsKey(ee.getName().getLocalPart())) {
            int count = openedElements.get(ee.getName().getLocalPart()) - 1;
            if (count < 1) {
              openedElements.remove(ee.getName().getLocalPart());
            } else {
              openedElements.put(ee.getName().getLocalPart(), count);
            }
          }
          if (openedElements.size() < 1) {
            done = true;
          }
        }
      }

    } catch (XMLStreamException e) {
      throw new MALException(e.getMessage());
    }

    return value;
  }

  @Override
  public MALListDecoder createListDecoder(List list)
      throws IllegalArgumentException, MALException {

    return new HTTPXMLStreamListReader(list, eventReader);
  }

  @Override
  public boolean hasNext() {
    return false;
  }

  @Override
  public int size() {
    return 0;
  }

  private static byte[] hexStringToByteArray(final String s) {

    final int len = s.length();
    final byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
          .digit(s.charAt(i + 1), 16));
    }
    return data;
  }

  /**
   * Check if a string is a valid URL without using external libraries
   * 
   * @param url
   * @return whether the string is a URL
   */
  private static boolean isURL(String url) {

    // check for custom CCSDS transport protocols
    if (url.startsWith("maltcp://") || url.startsWith("malhttp://"))
      return true;

    try {
      new URL(url);
      return true;

    } catch (Exception e) {
      return false;
    }
  }
}
