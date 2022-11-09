/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
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

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;

/**
 * The Attributes list.
 *
 */
public class AttributeList extends java.util.ArrayList<Object> implements Element {

    /**
     * Default constructor.
     *
     * @param attribute An attribute to be added to the list.
     */
    public AttributeList(Attribute attribute) {
        super();
        super.add(attribute);
    }

    /**
     * Default constructor.
     */
    public AttributeList() {
        super();
    }

    @Override
    public Element createElement() {
        return new AttributeList();
    }

    @Override
    public Long getShortForm() {
        throw new UnsupportedOperationException("This method should never be called!");
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
        throw new UnsupportedOperationException("This method should never be called!");
    }

    @Override
    public Object get(int index) {
        return Attribute.attribute2JavaType(super.get(index));
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        int size = this.size();
        encoder.encodeInteger(size);

        for (int i = 0; i < size; i++) {
            Object objToEncode = super.get(i);

            if (!(objToEncode instanceof Attribute)) {
                objToEncode = Attribute.javaType2Attribute(objToEncode);
            }

            if (objToEncode != null && !(objToEncode instanceof Attribute)) {
                throw new MALException("The object is not an Attribute type! "
                        + "It is: " + objToEncode.getClass().getCanonicalName()
                        + " - With value: " + objToEncode.toString());
            }

            encoder.encodeNullableAttribute((Attribute) objToEncode);
        }
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        int size = decoder.decodeInteger();
        AttributeList newObj = new AttributeList();

        for (int i = 0; i < size; i++) {
            newObj.add(decoder.decodeNullableAttribute());
        }

        return newObj;
    }
}
