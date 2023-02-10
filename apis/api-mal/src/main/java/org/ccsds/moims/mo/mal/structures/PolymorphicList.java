/* ----------------------------------------------------------------------------
 * Copyright (C) 2023      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java API
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
package org.ccsds.moims.mo.mal.structures;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;

/**
 * The polymorphic list allows elements of different types to be added on the
 * same list.
 */
public class PolymorphicList extends java.util.ArrayList<Element> implements ElementList<Element> {

    /**
     * Default constructor.
     *
     * @param element An element to be added to the list.
     */
    public PolymorphicList(Element element) {
        super();
        super.add(element);
    }

    /**
     * Default constructor.
     */
    public PolymorphicList() {
        super();
    }

    @Override
    public Element createElement() {
        return new PolymorphicList();
    }

    @Override
    public Long getShortForm() {
        return 0L;
    }

    @Override
    public UShort getAreaNumber() {
        return UShort.ATTRIBUTE_AREA_NUMBER;
    }

    @Override
    public UOctet getAreaVersion() {
        return UOctet.AREA_VERSION;
    }

    @Override
    public UShort getServiceNumber() {
        return UShort.ATTRIBUTE_SERVICE_NUMBER;
    }

    @Override
    public Integer getTypeShortForm() {
        return 0;
    }

    /**
     * Returns a list with all Elements in this object. It casts them to MO
     * Attribute type when necessary.
     *
     * @return The list of Elements wrapped in a Union type when it is needed.
     */
    public ArrayList<Element> getAsElements() {
        ArrayList<Element> elements = new ArrayList<>();
        for (Object obj : this) {
            elements.add((Element) Attribute.javaType2Attribute(obj));
        }
        return elements;
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        int size = this.size();
        encoder.encodeInteger(size);

        for (int i = 0; i < size; i++) {
            Element objToEncode = (Element) super.get(i);

            if (objToEncode != null && !(objToEncode instanceof Composite)) {
                throw new MALException("The object is not an Element type! "
                        + "It is: " + objToEncode.getClass().getCanonicalName()
                        + " - With value: " + objToEncode.toString());
            }

            if (objToEncode != null) {
                encoder.encodeLong(objToEncode.getShortForm());
                encoder.encodeNullableElement(objToEncode);
            } else {
                // Edge case when one of the entries is null
                encoder.encodeLong(0L);
                encoder.encodeNullableElement(objToEncode);
            }
        }
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        int size = decoder.decodeInteger();
        PolymorphicList newObj = new PolymorphicList();

        for (int i = 0; i < size; i++) {
            Long sfp = decoder.decodeLong();

            // Edge case when one of the entries is null
            if (sfp == 0) {
                decoder.decodeNullableElement(null);
                newObj.add(null);
                continue;
            }

            Element element = null;
            try {
                element = MALContextFactory.getElementsRegistry().createElement(sfp);
            } catch (Exception ex) {
                Logger.getLogger(PolymorphicList.class.getName()).log(Level.SEVERE,
                        "The element could not be decoded!", ex);
            }

            Element decodedElement = decoder.decodeNullableElement(element);
            newObj.add(decodedElement);
        }

        return newObj;
    }
}
