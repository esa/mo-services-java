/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Encoder - XML
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
package esa.mo.mal.encoder.xml;

import static esa.mo.mal.encoder.xml.XMLStreamFactory.RLOGGER;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALElementsRegistry;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALListDecoder;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.HomogeneousList;
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

public class XMLStreamReader implements MALDecoder {

    protected static final String XSI_NS = "http://www.w3.org/2001/XMLSchema-instance";
    protected static final String MAL_NS = "http://www.ccsds.org/schema/malxml/MAL";
    protected static final String MALFORMED_INPUT = "Malformed xml input";
    protected static final String LINE_END = "";
    protected static final String TAB = "";

    private XMLReader xmlReader;

    public XMLStreamReader() {
    }

    public XMLStreamReader(InputStream is) {
        try {
            if (is.available() > 0) {
                XMLInputFactory inputFactory = XMLInputFactory.newInstance();
                XMLEventReader eventReader = inputFactory.createXMLEventReader(is);
                xmlReader = new XMLReader(eventReader, this);
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
        if (decoded == null) {
            return null;
        } else if (decoded.equals("")) {
            return false;
        }
        return decoded.equals("true");
    }

    @Override
    public Float decodeFloat() throws MALException {
        return Float.valueOf(decodeXMLElement());
    }

    @Override
    public Float decodeNullableFloat() throws MALException {
        String decoded = decodeNullableXMLElement();
        if (decoded == null) {
            return null;
        } else if (decoded.equals("")) {
            return 0f;
        }
        return Float.valueOf(decoded);
    }

    @Override
    public Double decodeDouble() throws MALException {
        return Double.valueOf(decodeXMLElement());
    }

    @Override
    public Double decodeNullableDouble() throws MALException {
        String decoded = decodeNullableXMLElement();
        if (decoded == null) {
            return null;
        } else if (decoded.equals("")) {
            return 0d;
        }
        return Double.valueOf(decoded);
    }

    @Override
    public Byte decodeOctet() throws MALException {
        return Byte.valueOf(decodeXMLElement(), 10);
    }

    @Override
    public Byte decodeNullableOctet() throws MALException {
        String decoded = decodeNullableXMLElement();
        if (decoded == null) {
            return null;
        } else if (decoded.equals("")) {
            return 0;
        }
        return Byte.valueOf(decoded);
    }

    @Override
    public UOctet decodeUOctet() throws MALException {
        return new UOctet(Short.parseShort(decodeXMLElement()));
    }

    @Override
    public UOctet decodeNullableUOctet() throws MALException {
        String decoded = decodeNullableXMLElement();
        if (decoded == null) {
            return null;
        } else if (decoded.equals("")) {
            return new UOctet((short) 0);
        }
        return new UOctet(Short.parseShort(decodeXMLElement()));
    }

    @Override
    public Short decodeShort() throws MALException {
        return Short.valueOf(decodeXMLElement());
    }

    @Override
    public Short decodeNullableShort() throws MALException {
        String decoded = decodeNullableXMLElement();
        if (decoded == null) {
            return null;
        } else if (decoded.equals("")) {
            return 0;
        }
        return Short.valueOf(decoded);
    }

    @Override
    public UShort decodeUShort() throws MALException {
        return new UShort(Integer.parseInt(decodeXMLElement()));
    }

    @Override
    public UShort decodeNullableUShort() throws MALException {
        String decoded = decodeNullableXMLElement();
        return (decoded == null) ? null : new UShort(Integer.parseInt(decoded));
    }

    @Override
    public Integer decodeInteger() throws MALException {
        return Integer.valueOf(decodeXMLElement());
    }

    @Override
    public Integer decodeNullableInteger() throws MALException {
        String decoded = decodeNullableXMLElement();
        if (decoded == null) {
            return null;
        } else if (decoded.equals("")) {
            return 0;
        }
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
        if (decoded == null) {
            return null;
        } else if (decoded.equals("")) {
            return new UInteger();
        }
        return new UInteger(Long.parseLong(decoded));
    }

    @Override
    public Long decodeLong() throws MALException {
        return Long.valueOf(decodeXMLElement());
    }

    @Override
    public Long decodeNullableLong() throws MALException {
        String decoded = decodeNullableXMLElement();
        if (decoded == null) {
            return null;
        } else if (decoded.equals("")) {
            return 0L;
        }
        return Long.valueOf(decoded);
    }

    @Override
    public ULong decodeULong() throws MALException {
        return new ULong(new BigInteger(decodeXMLElement()));
    }

    @Override
    public ULong decodeNullableULong() throws MALException {
        String decoded = decodeNullableXMLElement();
        if (decoded == null) {
            return null;
        } else if (decoded.equals("")) {
            return new ULong();
        }
        return new ULong(new BigInteger(decoded));
    }

    @Override
    public String decodeString() throws MALException {
        return decodeXMLElement();
    }

    @Override
    public String decodeNullableString() throws MALException {
        String decoded = decodeNullableXMLElement();
        return (decoded == null) ? null : decoded;
    }

    @Override
    public Blob decodeBlob() throws MALException {
        byte[] byteValue = hexStringToByteArray(decodeXMLElement());
        return (byteValue == null) ? new Blob() : new Blob(byteValue);
    }

    @Override
    public Blob decodeNullableBlob() throws MALException {
        String decoded = decodeNullableXMLElement();
        return (decoded == null) ? null : new Blob(decoded);
    }

    @Override
    public Duration decodeDuration() throws MALException {
        String durationFormat = decodeXMLElement();
        java.time.Duration d = java.time.Duration.parse(durationFormat);
        return new Duration(d.getSeconds() + d.getNano() / 1e9d);
    }

    @Override
    public Duration decodeNullableDuration() throws MALException {
        String durationFormat = decodeNullableXMLElement();
        if (durationFormat == null) {
            return null;
        }
        java.time.Duration d = java.time.Duration.parse(durationFormat);
        return new Duration(d.getSeconds() + d.getNano() / 1e9d);
    }

    @Override
    public FineTime decodeFineTime() throws MALException {
        Instant i = Instant.parse(decodeXMLElement() + "Z");
        return new FineTime((long) (i.getEpochSecond() * 1e9) + i.getNano());
    }

    @Override
    public FineTime decodeNullableFineTime() throws MALException {
        Long value = decodeNullableLong();
        return (value == null) ? null : new FineTime(value);
    }

    @Override
    public Identifier decodeIdentifier() throws MALException {
        return new Identifier(decodeXMLElement());
    }

    @Override
    public Identifier decodeNullableIdentifier() throws MALException {
        String decoded = decodeNullableXMLElement();
        return (decoded == null) ? null : new Identifier(decoded);
    }

    @Override
    public Time decodeTime() throws MALException {
        Instant i = Instant.parse(decodeXMLElement() + "Z");
        return new Time(i.toEpochMilli());
    }

    @Override
    public Time decodeNullableTime() throws MALException {
        String decoded = decodeNullableXMLElement();

        if (decoded == null) {
            return null;
        } else if (decoded.equals("")) {
            return new Time();
        }

        Instant i = Instant.parse(decoded + "Z");
        return new Time(i.toEpochMilli());
    }

    @Override
    public URI decodeURI() throws MALException {
        String value = decodeXMLElement();
        return new URI(uriToUtf8(value));
    }

    @Override
    public URI decodeNullableURI() throws MALException {
        String decoded = decodeNullableXMLElement();
        return (decoded == null) ? null : new URI(decoded);
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

    @Override
    public Element decodeElement(Element element) throws IllegalArgumentException, MALException {
        return xmlReader.readNextElement(element);
    }

    public Element decodeElementByType(String typeName, StartElement event) throws MALException {
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
        Element union = xmlReader.getDummyUnionForDecoding();
        if (union != null) {
            return union.decode(this);
        }

        return union;
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
        return (Attribute) decodeNullableElement(null);
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
        return xmlReader.extractNextString(false);
    }

    public String decodeNullableXMLElement() throws MALException {
        return xmlReader.extractNextString(true);
    }

    @Override
    public MALListDecoder createListDecoder(List list) throws MALException {
        return new XMLStreamListReader(list, xmlReader.getEventReader());
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

    @Override
    public HomogeneousList decodeHomogeneousList(HomogeneousList list) throws MALException {
        return (HomogeneousList) xmlReader.readNextElement(list);
    }

    private static String uriToUtf8(String uri) {
        String decodedUri = "";
        try {
            decodedUri = java.net.URLDecoder.decode(uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodedUri;
    }

    @Override
    public Long decodeAbstractElementSFP(boolean isNullable) throws MALException {
        if (isNullable) {
            return decodeNullableLong();
        }

        return decodeLong();
    }
}
