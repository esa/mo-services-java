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

import static esa.mo.mal.encoder.http.HTTPXMLStreamReader.MAL_NS;
import static esa.mo.mal.encoder.http.HTTPXMLStreamReader.XSI_NS;
import static esa.mo.mal.transport.http.HTTPTransport.RLOGGER;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
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

    XMLEventReader eventReader;
    HTTPXMLStreamReader xmlStreamReader;

    public XMLReader(XMLEventReader eventReader, HTTPXMLStreamReader xmlStreamReader) throws XMLStreamException {
        this.eventReader = eventReader;
        this.xmlStreamReader = xmlStreamReader;

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

    public String readStringFromXMLElement(boolean mightBeNull) throws MALException {
        String value = "";

        try {
            XMLEvent event = null;

            Map<String, Integer> openedElements = new HashMap<>();

            while (eventReader.hasNext()) {
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
                        break;
                    }
                }
            }
        } catch (XMLStreamException e) {
            throw new MALException(e.getMessage());
        }

        return value;
    }

    public Element decodeHomogeneousList(final HomogeneousList emptyList) throws MALException {
        Element returnable = null;

        try {
            XMLEvent event = eventReader.nextTag();

            if (event.isStartElement()) {
                StartElement se = event.asStartElement();
                javax.xml.stream.events.Attribute nillable = se.getAttributeByName(new QName(XSI_NS, "nil", "xsi"));

                if (nillable != null && nillable.getValue().equals("true")) {
                    returnable = null;
                } else {
                    // returnable = element.decode(xmlStreamReader);
                    returnable = getListElements(emptyList);
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
                        "Expected a xml start element or nullable for element " + emptyList.getClass().getSimpleName());
            }

        } catch (XMLStreamException e) {
            throw new MALException(e.getMessage());
        }

        return returnable;
    }

    public Element getListElements(HomogeneousList list) throws IllegalArgumentException, MALException {
        HTTPXMLStreamListReader listDecoder = (HTTPXMLStreamListReader) xmlStreamReader.createListDecoder(list);

        while (listDecoder.hasNext()) {
            Element element = list.createTypedElement();

            if (element instanceof Composite) {
                eventReader.next();
            }

            Element decodedElement = element.decode(xmlStreamReader);
            //Element decodedElement = xmlReader.readNextElement(element);

            // If the decoded element is of Union type, then cast it to
            // its respective Java type before adding it to the list
            if (decodedElement instanceof Union) {
                list.add(Attribute.attribute2JavaType(decodedElement));
            } else {
                list.add(decodedElement);
            }
        }

        return list;
    }

    List<String> openstandingEndElements = new ArrayList<>();

    public Element readNextElement(Element element) throws MALException {
        Element returnable = null;
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

            if (element instanceof HomogeneousList) {
                return decodeHomogeneousList((HomogeneousList) element);
            }
            if (element instanceof HeterogeneousList) {
                eventReader.next();
                return element.decode(xmlStreamReader);
            }

            String superClassName = null;
            Map<String, Integer> openedElements = new HashMap<>();
            if (element != null) {
                superClassName = element.getClass().getSuperclass().getName();
            }

            while (eventReader.hasNext()) {
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
                            returnable = element.decode(xmlStreamReader);
                        } else {
                            returnable = xmlStreamReader.decodeElementByType(se.getName().getLocalPart(), se);
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
                        break;
                    }
                } else {
                    break;
                }
            }
        } catch (XMLStreamException e) {
            RLOGGER.log(Level.SEVERE, "The Element could not be fully decoded! State: " + element, e);
            throw new MALException(e.getMessage());
        }

        return returnable;
    }

    public Union decodeUnion() throws MALException {
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

                return union;
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
        String enumValue = this.readStringFromXMLElement(false);

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
