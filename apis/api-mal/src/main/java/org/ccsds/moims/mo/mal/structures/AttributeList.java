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

/**
 * The Attributes list. The added and removed objects to this class might or
 * might not be wrapped in a Union type. It is the responsibility of this class
 * to be able to handle each case when trying to encode and when passing the
 * values to the user.
 */
public class AttributeList extends org.ccsds.moims.mo.mal.structures.HeterogeneousList {

    /**
     * Default constructor.
     *
     * @param attribute An attribute to be added to the list. The type has to be
     * Object (instead of Attribute) because the Union type does not extend the
     * Attribute type.
     */
    public AttributeList(Object attribute) {
        super();
        super.add((Element) Attribute.javaType2Attribute(attribute));
    }

    /**
     * Default constructor.
     */
    public AttributeList() {
        super();
    }

    /**
     * Adds an element to the list and checks if the type is correct.
     *
     * @param element The element to be added.
     * @return The success status.
     */
    @Override
    public boolean add(org.ccsds.moims.mo.mal.structures.Element element) {
        if (element != null && !(element instanceof Attribute)) {
            throw new java.lang.ClassCastException("The added element does not extend the type: Attribute");
        }
        return super.add(element);
    }

    /**
     * Adds an element to the list. The element will be converted to Attribute
     * in case it is a native Java Type (E.g. Integer, Long, etc).
     *
     * @param element The element to be added.
     * @return The success status.
     */
    public boolean addAsJavaType(Object element) {
        Object att = Attribute.javaType2Attribute(element);
        if (att instanceof Attribute) {
            this.add((Attribute) att);
            return true;
        }
        throw new IllegalArgumentException("The added argument could not be converted to a MAL Attribute!");
    }

    @Override
    public Element createElement() {
        return new AttributeList();
    }

    public Object getAsJavaType(int index) {
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
}
