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
import static esa.mo.mal.encoder.xml.XMLStreamReader.MAL_NS;
import static esa.mo.mal.encoder.xml.XMLStreamReader.XSI_NS;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALElementsRegistry;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Enumeration;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;
import org.ccsds.moims.mo.mal.structures.HomogeneousList;
import org.ccsds.moims.mo.mal.structures.Union;

/**
 *
 * @author Cesar.Coelho
 */
public class XMLReader {

    private final XMLEventReader eventReader;
    private final XMLStreamReader xmlStreamReader;

    public XMLReader(XMLEventReader eventReader, XMLStreamReader xmlStreamReader) throws XMLStreamException {
        this.eventReader = eventReader;
        this.xmlStreamReader = xmlStreamReader;
        eventReader.nextEvent(); // xml header
        XMLEvent event = eventReader.nextEvent(); // message body

        while (event.isCharacters()) { // skip \n, \t character events
            event = eventReader.nextEvent();
        }

        // validate allocation of body element in xml
        if (!(event.isStartElement()
                && event.asStartElement().getName().getLocalPart().equals("Body"))) {
            RLOGGER.severe("XML Malformed: Body element missing");
        }
    }

    public XMLEventReader getEventReader() {
        return eventReader;
    }

    private javax.xml.stream.events.Attribute getAttributeNil(StartElement se) {
        return se.getAttributeByName(new QName(XSI_NS, "nil", "xsi"));
    }

    public String extractNextString(boolean mightBeNull) throws MALException {
        String value = "";

        try {
            int pendingTags = 0;

            // Check if the next Element is a EndElement! If so, there are problems!
            if (eventReader.peek().isEndElement()) {
                XMLEvent next = eventReader.nextEvent();
                String name = next.asEndElement().getName().getLocalPart();
                throw new MALException("(1) The element is EndElement! The sync is wrong! Close tag: " + name);
            }

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    pendingTags++;
                    StartElement se = event.asStartElement();

                    if (mightBeNull) {
                        javax.xml.stream.events.Attribute att = this.getAttributeNil(se);
                        if (att != null && att.getValue().equals("true")) {
                            value = null;
                        }
                    }
                }

                if (event.isCharacters() && !event.asCharacters().getData().matches("[\\n\\t ]+")) {
                    value = event.asCharacters().getData();
                }

                if (event.isEndElement()) {
                    pendingTags--;

                    if (pendingTags == 0) {
                        break;
                    }
                }
            }
        } catch (XMLStreamException e) {
            throw new MALException("Something went wrong!", e);
        }

        return value;
    }

    private Element decodeHomogeneousList(final HomogeneousList emptyList) throws MALException {
        HomogeneousList returnable = null;

        try {
            if (eventReader.peek().isEndElement()) {
                XMLEvent next = eventReader.nextEvent();
                EndElement ee = next.asEndElement();
                String name = ee.getName().getLocalPart();
                throw new MALException("(2) The element is EndElement! The sync is wrong! Close tag: " + name);
            }

            XMLEvent event = eventReader.nextTag();

            if (event.isStartElement()) {
                StartElement se = event.asStartElement();
                javax.xml.stream.events.Attribute nillable = this.getAttributeNil(se);

                if (nillable != null && nillable.getValue().equals("true")) {
                    returnable = null;
                } else {
                    returnable = extractNextListElements(emptyList);
                }

                if (eventReader.hasNext() && eventReader.peek().isEndElement()) {
                    eventReader.next();
                }
            } else {
                throw new MALException(
                        "Expected a xml start element or nullable for element " + emptyList.getClass().getSimpleName());
            }
        } catch (XMLStreamException e) {
            throw new MALException(e.getMessage());
        }

        return returnable;
    }

    private HomogeneousList extractNextListElements(HomogeneousList list) throws IllegalArgumentException {
        try {
            XMLStreamListReader listDecoder = (XMLStreamListReader) xmlStreamReader.createListDecoder(list);

            while (listDecoder.hasNext()) {
                Element element = list.createTypedElement();

                if (element instanceof Composite) {
                    eventReader.next();
                }

                Element decodedElement = element.decode(xmlStreamReader);
                //Element decodedElement = this.readNextElement(element);

                if (element instanceof Composite) {
                    eventReader.next();
                }

                // If the decoded element is of Union type, then cast it to
                // its respective Java type before adding it to the list
                if (decodedElement instanceof Union) {
                    list.add(Attribute.attribute2JavaType(decodedElement));
                } else {
                    list.add(decodedElement);
                }
            }
        } catch (MALException ex) {
            Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE,
                    "The list could not be read: " + list.toString(), ex);
        }
        return list;
    }

    public Element readNextElement(Element element) throws MALException {
        Element returnable = null;
        try {
            if (eventReader == null || !eventReader.hasNext()) {
                return null;
            }

            // Skip all the garbage elements (example spaces and tabs)
            while (eventReader.peek().isCharacters()
                    && eventReader.peek().asCharacters().getData().matches("[\\n\\t]+")) {
                eventReader.next();
            }

            // Did we receive an abstract element?
            if (element == null && eventReader.peek().isStartElement()) {
                element = getElementByType(eventReader.peek().asStartElement());
            }

            if (element instanceof HomogeneousList) {
                return decodeHomogeneousList((HomogeneousList) element);
            }

            if (element instanceof HeterogeneousList) {
                eventReader.next();
                returnable = element.decode(xmlStreamReader);
                eventReader.next();
                return returnable;
            }

            int pendingTags = 0;

            while (eventReader.hasNext() && !eventReader.peek().isEndDocument()) {
                XMLEvent event = eventReader.nextTag();

                if (event.isStartElement()) {
                    StartElement se = event.asStartElement();
                    String localPart = se.getName().getLocalPart();
                    String possibleSuperClassName = null;

                    if (eventReader.peek() != null && eventReader.peek().isStartElement()) {
                        possibleSuperClassName = eventReader.peek().asStartElement().getName().getLocalPart();
                    }

                    pendingTags++;

                    if (element instanceof Enumeration) {
                        returnable = decodeEnumeration((Enumeration) element);
                    } else {
                        javax.xml.stream.events.Attribute nillable = this.getAttributeNil(se);

                        // Check if it is null, and if so, then return a null!
                        if (nillable != null && nillable.getValue().equals("true")) {
                            if (eventReader.peek().isEndElement()) {
                                eventReader.next();
                            }
                            return null;
                        }

                        if (element != null) {
                            try {
                                returnable = element.decode(xmlStreamReader);
                            } catch (MALException ex) {
                                RLOGGER.log(Level.SEVERE,
                                        "The element could not be decoded: " + element.toString(), ex);
                            }
                        } else {
                            try {
                                returnable = xmlStreamReader.decodeElementByType(localPart, se);
                            } catch (MALException ex) {
                                returnable = xmlStreamReader.decodeElementByType(possibleSuperClassName, se);
                                eventReader.next();
                            }
                        }
                    }
                } else if (event.isEndElement()) {
                    pendingTags--;

                    if (pendingTags == 0) {
                        break;
                    }
                } else {
                    break;
                }
            }
        } catch (XMLStreamException e) {
            RLOGGER.log(Level.SEVERE, "The Element could not be fully decoded! State: "
                    + element + " and with returnable: " + returnable, e);
            throw new MALException(e.getMessage());
        }

        return returnable;
    }

    public Union getDummyUnionForDecoding() throws MALException {
        try {
            while (eventReader.peek().isCharacters()
                    && eventReader.peek().asCharacters().getData().matches("[\\n\\t]+")) {
                eventReader.next();
            }

            XMLEvent nextEvent = eventReader.peek();
            if (nextEvent.isStartElement()) {
                StartElement se = nextEvent.asStartElement();
                String subElementType = se.getName().getLocalPart();

                if (subElementType.equals("Boolean")) {
                    return new Union(false);
                } else if (subElementType.equals("Float")) {
                    return new Union(0f);
                } else if (subElementType.equals("Double")) {
                    return new Union(0d);
                } else if (subElementType.equals("Integer")) {
                    return new Union(0);
                } else if (subElementType.equals("Long")) {
                    return new Union(0L);
                } else if (subElementType.equals("Octet")) {
                    return new Union((byte) 0);
                } else if (subElementType.equals("Short")) {
                    return new Union((short) 0);
                } else {
                    return new Union("");
                }
            }
        } catch (XMLStreamException e) {
            throw new MALException("The Union type could not be decoded!", e);
        }

        return null;
    }

    private Element getElementByType(StartElement event) throws MALException {
        if (event.getAttributeByName(new QName(MAL_NS, "type", "malxml")) == null) {
            return null;
        }

        javax.xml.stream.events.Attribute att = event.getAttributeByName(new QName(MAL_NS, "type", "malxml"));
        Long shortForm = Long.valueOf(att.getValue());
        MALElementsRegistry elementsRegistry = MALContextFactory.getElementsRegistry();

        try {
            return (Element) elementsRegistry.createElement(shortForm);
        } catch (Exception e) {
            throw new MALException("Can't find element with shortForm: " + shortForm.toString());
        }
    }

    private Enumeration decodeEnumeration(Enumeration element) throws MALException {
        String enumValue = this.extractNextString(false);

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
}
