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

import java.util.ArrayList;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALListDecoder;
import org.ccsds.moims.mo.mal.MALListEncoder;

/**
 * The Attributes list. The added and removed objects to this class might or
 * might not be wrapped in a Union type. It is the responsibility of this class
 * to be able to handle each case when trying to encode and when passing the
 * values to the user.
 */
public class AttributeList extends java.util.ArrayList<Object> implements Element {

    /**
     * Default constructor.
     *
     * @param attribute An attribute to be added to the list. The type has to be
     * Object (instead of Attribute) because the Union type does not extend the
     * Attribute type.
     */
    public AttributeList(Object attribute) {
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

    public NullableAttributeList getAsNullableAttributeList() {
        NullableAttributeList attributes = new NullableAttributeList();
        for (Object obj : this) {
            attributes.add(new NullableAttribute((Attribute) Attribute.javaType2Attribute(obj)));
        }
        return attributes;
    }

    /**
     * Returns a list with all Attributes in this object. It casts them to MO
     * Attribute type when necessary.
     *
     * @return The list of Attributes wrapped in a Union type when it is needed.
     */
    public ArrayList<Attribute> getAsAttributes() {
        ArrayList<Attribute> attributes = new ArrayList<>();
        for (Object obj : this) {
            attributes.add((Attribute) Attribute.javaType2Attribute(obj));
        }
        return attributes;
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        MALListEncoder listEncoder = encoder.createListEncoder(this);
        for (int i = 0; i < size(); i++) {
            Object objToEncode = super.get(i);
            if (!(objToEncode instanceof Attribute)) {
                objToEncode = Attribute.javaType2Attribute(objToEncode);
            }
            listEncoder.encodeNullableAttribute((Attribute) objToEncode);
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
            add(Attribute.attribute2JavaType(listDecoder.decodeNullableAttribute()));
        }
        return this;
    }
}
