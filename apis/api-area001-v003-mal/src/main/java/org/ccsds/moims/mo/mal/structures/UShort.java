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
import org.ccsds.moims.mo.mal.TypeId;

/**
 * Class representing MAL UShort type.
 */
public class UShort implements Attribute {

    private static final long serialVersionUID = Attribute.USHORT_SHORT_FORM;
    /**
     * The area number for all Attributes.
     */
    public static final UShort ATTRIBUTE_AREA_NUMBER = new UShort(1);
    /**
     * The service number for all Attributes.
     */
    public static final UShort ATTRIBUTE_SERVICE_NUMBER = new UShort(0);

    private int value;

    /**
     * A constant holding the maximum value a {@code UShort} can have,
     * 2<sup>16</sup>-1.
     */
    public static final int MAX_VALUE = 65535;

    /**
     * Default constructor.
     */
    public UShort() {
        value = 0;
    }

    /**
     * Initialiser constructor.
     *
     * @param value Value to initialise with.
     */
    public UShort(final int value) {
        if (value < 0) {
            throw new IllegalArgumentException("UShort argument must not be negative");
        }
        if (value > UShort.MAX_VALUE) {
            throw new IllegalArgumentException("UShort argument must not be greater than " + UShort.MAX_VALUE);
        }
        this.value = value;
    }

    @Override
    public Element createElement() {
        return new UShort();
    }

    /**
     * Returns the value of this type.
     *
     * @return the value.
     */
    public int getValue() {
        return value;
    }

    @Override
    public TypeId getTypeId() {
        return new TypeId(Attribute.USHORT_SHORT_FORM);
    }

    @Override
    public void encode(final MALEncoder encoder) throws MALException {
        encoder.encodeUShort(this);
    }

    @Override
    public Element decode(final MALDecoder decoder) throws MALException {
        return decoder.decodeUShort();
    }

    @Override
    public boolean equals(final Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UShort)) {
            return false;
        }
        return this.value == (((UShort) obj).value);
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
