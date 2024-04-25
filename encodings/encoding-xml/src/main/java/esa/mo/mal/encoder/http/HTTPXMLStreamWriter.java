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
package esa.mo.mal.encoder.http;

import static esa.mo.mal.encoder.http.HTTPXMLStreamFactory.RLOGGER;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALListEncoder;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Enumeration;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.HomogeneousList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.ULong;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 *
 * @author rvangijlswijk
 *
 */
public class HTTPXMLStreamWriter implements MALListEncoder {

    protected OutputStream dos;
    protected XMLStreamWriter writer;
    protected XMLOutputFactory factory;

    protected static final String LINE_END = "";
    protected static final String TAB = "";

    protected static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    protected static final String ENCODING_EXCEPTION_STR = "Bad encoding";

    public HTTPXMLStreamWriter() {
    }

    public HTTPXMLStreamWriter(OutputStream os) {
        this.dos = os;
        factory = XMLOutputFactory.newInstance();

        try {
            writer = factory.createXMLStreamWriter(os, "UTF-8");
            writer.setDefaultNamespace("http://www.ccsds.org/schema/malxml/MAL");

            writer.writeStartDocument(UTF8_CHARSET.name(), "1.0");
            writer.writeDTD(LINE_END);

            // write body element
            writer.writeStartElement("malxml:Body");
            writer.writeNamespace("malxml", "http://www.ccsds.org/schema/malxml/MAL");
            writer.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
            writer.writeDTD(LINE_END);

        } catch (XMLStreamException e) {
            RLOGGER.log(Level.SEVERE, "HTTPXMLStreamWriter error: " + e.getMessage(), e);
        }
    }

    @Override
    public void encodeBlob(final Blob value) throws MALException {
        if (value.getValue() == null || value.getValue().length == 0) {
            addNode("Blob", "");
        } else {
            addNode("Blob", byteArrayToHexString(value.getValue()));
        }
    }

    @Override
    public void encodeBoolean(final Boolean value) throws MALException {
        addNode("Boolean", value.toString());
    }

    @Override
    public void encodeDuration(final Duration value) throws MALException {
        java.time.Duration d = java.time.Duration.ofMillis((long) (value.getValue() * 1000));
        addNode("Duration", d.toString());
    }

    @Override
    public void encodeFloat(final Float value) throws MALException {
        addNode("Float", value.toString());
    }

    @Override
    public void encodeDouble(final Double value) throws MALException {
        addNode("Double", value.toString());
    }

    @Override
    public void encodeIdentifier(final Identifier value) throws MALException {
        addNode("Identifier", value.toString());
    }

    @Override
    public void encodeNullableIdentifier(final Identifier value) throws MALException {
        addNullableNode("Identifier", value);
    }

    @Override
    public void encodeOctet(final Byte value) throws MALException {
        addNode("Octet", value.toString());
    }

    @Override
    public void encodeUOctet(final UOctet value) throws MALException {
        addNode("UOctet", value.toString());
    }

    @Override
    public void encodeShort(final Short value) throws MALException {
        addNode("Short", value.toString());
    }

    @Override
    public void encodeUShort(final UShort value) throws MALException {
        addNode("UShort", value.toString());
    }

    @Override
    public void encodeInteger(final Integer value) throws MALException {
        addNode("Integer", value.toString());
    }

    @Override
    public void encodeUInteger(final UInteger value) throws MALException {
        addNode("UInteger", value.toString());
    }

    @Override
    public void encodeLong(final Long value) throws MALException {
        addNode("Long", value.toString());
    }

    @Override
    public void encodeULong(final ULong value) throws MALException {
        addNode("ULong", value.toString());
    }

    @Override
    public void encodeString(final String value) throws MALException {
        addNode("String", value);
    }

    @Override
    public void encodeTime(final Time value) throws MALException {
        OffsetDateTime i = Instant.ofEpochMilli(value.getValue()).atOffset(ZoneOffset.UTC);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        addNode("Time", i.format(format));
    }

    @Override
    public void encodeFineTime(final FineTime value) throws MALException {
        long timestamp = value.getValue();
        long seconds = (long) (timestamp / 1e9); // whole seconds
        long nanoSeconds = timestamp - (long) (seconds * 1e9);
        OffsetDateTime i = Instant.ofEpochSecond(seconds, nanoSeconds).atOffset(ZoneOffset.UTC);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnnnnnnnn");
        addNode("FineTime", i.format(format));
    }

    @Override
    public void encodeURI(final URI value) throws MALException {
        addNullableNode("URI", value.toString());
    }

    @Override
    public void encodeObjectRef(final ObjectRef att) throws MALException {
        try {
            writer.writeDTD(LINE_END);
            writer.writeDTD(TAB);
            writer.writeStartElement("ObjectRef");
            encodeNullableElement(att.getDomain());
            encodeNullableLong(att.getabsoluteSFP());
            encodeNullableIdentifier(att.getKey());
            encodeNullableUInteger(att.getObjectVersion());
            writer.writeEndElement();
        } catch (Exception ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeNullableBoolean(Boolean att) throws MALException {
        addNullableNode("Boolean", att);
    }

    @Override
    public void encodeNullableFloat(Float att) throws MALException {
        addNullableNode("Float", att);

    }

    @Override
    public void encodeNullableDouble(Double att) throws MALException {
        addNullableNode("Double", att);
    }

    @Override
    public void encodeNullableOctet(Byte att) throws MALException {
        addNullableNode("Octet", att);

    }

    @Override
    public void encodeNullableUOctet(UOctet att) throws MALException {
        addNullableNode("UOctet", att);

    }

    @Override
    public void encodeNullableShort(Short att) throws MALException {
        addNullableNode("Short", att);

    }

    @Override
    public void encodeNullableUShort(UShort att) throws MALException {
        addNullableNode("UShort", att);

    }

    @Override
    public void encodeNullableInteger(Integer att) throws MALException {
        addNullableNode("Integer", att);

    }

    @Override
    public void encodeNullableUInteger(UInteger att) throws MALException {
        addNullableNode("UInteger", att);

    }

    @Override
    public void encodeNullableLong(Long att) throws MALException {
        addNullableNode("Long", att);

    }

    @Override
    public void encodeNullableULong(ULong att) throws MALException {
        addNullableNode("ULong", att);

    }

    @Override
    public void encodeNullableString(String att) throws MALException {
        addNullableNode("String", att);

    }

    @Override
    public void encodeNullableBlob(Blob att) throws MALException {
        addNullableNode("Blob", att);

    }

    @Override
    public void encodeNullableDuration(Duration att) throws MALException {
        addNullableNode("Duration", att);

    }

    @Override
    public void encodeNullableFineTime(FineTime att) throws MALException {
        addNullableNode("FineTime", att);

    }

    @Override
    public void encodeNullableTime(Time att) throws MALException {
        addNullableNode("Time", att);

    }

    @Override
    public void encodeNullableURI(URI att) throws MALException {
        addNullableNode("URI", att);

    }

    @Override
    public void encodeNullableObjectRef(ObjectRef att) throws MALException {
        if (att == null) {
            addNull("ObjectRef");
        } else {
            encodeObjectRef(att);
        }
    }

    @Override
    public void encodeElement(Element element) throws IllegalArgumentException, MALException {
        String typeName = element.getClass().getSimpleName();
        encodeElement(element, typeName);
    }

    public void encodeElement(final Element element, String typeName) throws MALException {
        if (element instanceof List) {
            element.encode(this);
        } else if (element instanceof Composite) {
            encodeComposite(typeName, element.getClass(), (Composite) element, true);
        } else if (element instanceof Enumeration) {
            encodeEnumeration(typeName, (Enumeration) element);
        } else {
            try {
                writer.writeDTD(TAB);
                writer.writeStartElement(typeName);
                writer.writeAttribute("malxml:type", element.getShortForm().toString());
                writer.writeDTD(LINE_END);
                writer.writeDTD(TAB);
                element.encode(this);
                writer.writeEndElement();
            } catch (XMLStreamException e) {
                throw new MALException(e.getMessage());
            }
        }
    }

    @Override
    public void encodeNullableElement(final Element value) throws MALException {
        if (value == null) {
            addNull("Element");
        } else {
            encodeElement(value);
        }
    }

    public void encodeNullableElement(final Element value, String name) throws MALException {
        if (value == null) {
            addNull(name);
        } else {
            encodeElement(value, name);
        }
    }

    @Override
    public void encodeAttribute(Attribute att) throws IllegalArgumentException, MALException {
        if (att instanceof ObjectRef) {
            encodeObjectRef((ObjectRef) att);
        } else {
            encodeElement(att, "Attribute");
        }
    }

    @Override
    public void encodeNullableAttribute(Attribute att) throws MALException {
        if (att == null) {
            addNull("Attribute");
        } else {
            encodeAttribute(att);
        }
    }

    @Override
    public void encodeAbstractElement(Element element) throws MALException {
        encodeElement(element);
    }

    @Override
    public void encodeNullableAbstractElement(Element element) throws MALException {
        encodeNullableElement(element);
    }

    private void encodeComposite(final String name, final Class cls,
            final Composite composite, boolean writeElement) throws MALException {
        try {
            if (writeElement) {
                writer.writeStartElement(name);
                writer.writeAttribute("malxml:type", composite.getShortForm().toString());
            }

            Class superCls = cls.getSuperclass();
            if (!"Object".equals(superCls.getSimpleName())) {

                try {
                    encodeComposite(superCls.getName(), superCls, composite, false);
                } catch (Exception ex) {
                    throw new MALException(
                            "Error when encoding parent Composite of super class "
                            + superCls.getName() + " from Composite "
                            + name + " of class " + cls.getName(),
                            ex);
                }
            }

            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {

                final int mods = field.getModifiers();
                if (!Modifier.isStatic(mods)) {
                    try {
                        field.setAccessible(true);
                        Object fieldValue = field.get(composite);
                        if (fieldValue == null) {
                            addNull(field.getName());
                        } else if (fieldValue instanceof Element) {
                            encodeNullableElement((Element) fieldValue, field.getType().getSimpleName());
                        } else {
                            encode(fieldValue, field.getName());
                        }
                    } catch (Exception ex) {
                        throw new MALException("Error when encoding field "
                                + field.getName() + " of class "
                                + field.getType().getName() + " from Composite "
                                + name + " of class " + cls.getName(), ex);
                    }
                }
            }

            if (writeElement) {
                writer.writeEndElement();
            }
        } catch (XMLStreamException e) {
            throw new MALException(e.getMessage());
        }
    }

    private void encodeEnumeration(String typeName, Enumeration element) throws MALException {
        try {
            writer.writeStartElement(typeName);
            writer.writeAttribute("malxml:type", element.getShortForm().toString());
            addNode(typeName, element.toString());
            writer.writeEndElement();
        } catch (Exception ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    public <T extends Object> void encode(final T value, String typeName) throws MALException {
        try {
            writer.writeDTD(LINE_END);
            writer.writeDTD(TAB);
            writer.writeStartElement(typeName);
            addNode(value.getClass().getSimpleName(), value.toString());
            writer.writeEndElement();
        } catch (Exception ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    private void addNode(String name, String value) throws MALException {
        try {
            writer.writeDTD(LINE_END);
            writer.writeDTD(TAB);
            writer.writeStartElement(name);
            writer.writeCharacters(value);
            writer.writeEndElement();
        } catch (Exception ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    private void addNull(String name) throws MALException {
        try {
            writer.writeDTD(LINE_END);
            writer.writeDTD(TAB);
            writer.writeEmptyElement(name);
            writer.writeAttribute("xsi:nil", "true");
        } catch (Exception ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    private void addNullableNode(String name, Object value) throws MALException {
        try {
            if (value == null) {
                addNull(name);
            } else {
                addNode(name, value.toString());
            }
        } catch (Exception ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public MALListEncoder createListEncoder(List list) throws IllegalArgumentException, MALException {
        return new HTTPXMLStreamListWriter(this.writer, list);
    }

    public MALListEncoder createListEncoder(List list, String typeName) throws IllegalArgumentException, MALException {
        return new HTTPXMLStreamListWriter(this.writer, list, typeName);
    }

    @Override
    public void close() {
        try {
            writer.writeDTD(LINE_END);
            writer.writeEndElement(); // close body element
            writer.writeDTD(LINE_END);

            writer.writeEndDocument();
            writer.close();
        } catch (XMLStreamException e) {
            RLOGGER.log(Level.SEVERE, "HTTPXMLStreamWriter closing error: " + e.getMessage(), e);
        }
    }

    private static String byteArrayToHexString(final byte[] data) {
        final StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < data.length; i++) {
            final String hex = Integer.toHexString(0xFF & data[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    @Override
    public void encodeHomogeneousList(HomogeneousList list) throws MALException {
        HTTPXMLStreamListWriter listEncoder = (HTTPXMLStreamListWriter) this.createListEncoder(list);
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            Element element = (obj instanceof Element) ? (Element) obj : (Element) Attribute.javaType2Attribute(obj);

            if (element instanceof Composite) {
                String name = element.getClass().getSimpleName();
                encodeComposite(name, element.getClass(), (Composite) element, true);
            } else {
                element.encode(listEncoder);
            }
        }
        listEncoder.close();
    }
}
