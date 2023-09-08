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
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALListDecoder;
import org.ccsds.moims.mo.mal.MALListEncoder;

/**
 * The polymorphic list allows elements of different types to be added on the
 * same list.
 */
public class HeterogeneousList extends java.util.ArrayList<Element> implements ElementList<Element> {

    /**
     * Default constructor.
     *
     * @param element An element to be added to the list.
     */
    public HeterogeneousList(Element element) {
        super();
        super.add(element);
    }

    /**
     * Default constructor.
     */
    public HeterogeneousList() {
        super();
    }

    @Override
    public Element createElement() {
        return new HeterogeneousList();
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
        MALListEncoder listEncoder = encoder.createListEncoder(this);
        for (int i = 0; i < size(); i++) {
            Object entry = get(i);
            if (!(entry instanceof Element)) {
                entry = Attribute.javaType2Attribute(entry);
            }
            listEncoder.encodeNullableAbstractElement((Element) entry);
        }
        listEncoder.close();
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        MALListDecoder listDecoder = decoder.createListDecoder(this);
        int decodedSize = listDecoder.size();
        if (decodedSize > 0) {
            ensureCapacity(decodedSize);
        }
        while (listDecoder.hasNext()) {
            add((Element) listDecoder.decodeNullableAbstractElement());
        }
        return this;
    }
}
