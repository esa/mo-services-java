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
 * Class representing MAL UInteger type.
 */
public class UInteger implements Attribute {

    private static final long serialVersionUID = Attribute.UINTEGER_SHORT_FORM;

    /**
     * A constant holding the maximum value a {@code UInteger} can have,
     * 2<sup>32</sup>-1.
     */
    public static final long MAX_VALUE = 4294967295L;

    private long value;

    /**
     * Default constructor.
     */
    public UInteger() {
        value = 0;
    }

    /**
     * Initialiser constructor.
     *
     * @param value Value to initialise with.
     */
    public UInteger(final long value) {
        if (value < 0) {
            throw new IllegalArgumentException("UInteger argument must not be negative");
        }
        if (value > UInteger.MAX_VALUE) {
            throw new IllegalArgumentException(
                    "UInteger argument must not be greater than " + UInteger.MAX_VALUE);
        }
        this.value = value;
    }

    @Override
    public Element createElement() {
        return new UInteger();
    }

    /**
     * Returns the value of this type.
     *
     * @return the value.
     */
    public long getValue() {
        return value;
    }

    @Override
    public Long getShortForm() {
        return Attribute.UINTEGER_SHORT_FORM;
    }

    @Override
    public Integer getTypeShortForm() {
        return Attribute.UINTEGER_TYPE_SHORT_FORM;
    }

    @Override
    public UShort getAreaNumber() {
        return UShort.ATTRIBUTE_AREA_NUMBER;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.UOctet getAreaVersion() {
        return UOctet.AREA_VERSION;
    }

    @Override
    public UShort getServiceNumber() {
        return UShort.ATTRIBUTE_SERVICE_NUMBER;
    }

    @Override
    public TypeId getTypeId() {
        return new TypeId(Attribute.UINTEGER_SHORT_FORM);
    }

    @Override
    public void encode(final MALEncoder encoder) throws MALException {
        encoder.encodeUInteger(this);
    }

    @Override
    public Element decode(final MALDecoder decoder) throws MALException {
        return decoder.decodeUInteger();
    }

    @Override
    public boolean equals(final Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UInteger)) {
            return false;
        }
        return this.value == (((UInteger) obj).value);
    }

    @Override
    public int hashCode() {
        return (int) value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
