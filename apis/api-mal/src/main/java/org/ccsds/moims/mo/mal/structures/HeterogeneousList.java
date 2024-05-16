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
import org.ccsds.moims.mo.mal.TypeId;

/**
 * The HeterogeneousList allows elements of different types to be added on the
 * same list.
 */
public class HeterogeneousList extends java.util.ArrayList<Element> implements ElementList<Element> {

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

    @Override
    public TypeId getTypeId() {
        return new TypeId(0L);
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
        encoder.encodeHeterogeneousList(this);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        decoder.decodeHeterogeneousList(this);
        return this;
    }

    /**
     * Adds an element to the list and checks if the type is correct.
     *
     * @param element The element to be added.
     * @return The success status.
     */
    @Override
    public boolean add(org.ccsds.moims.mo.mal.structures.Element element) {
        if (ENFORCE_NON_NULLABLE_ENTRIES) {
            if (element == null) {
                throw new java.lang.IllegalArgumentException("The added element cannot be NULL!");
            }
        }
        return super.add(element);
    }
}
