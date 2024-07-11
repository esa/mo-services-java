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
 * Class representing MAL UOctet type.
 */
public class UOctet implements Attribute {

    private static final long serialVersionUID = Attribute.UOCTET_SHORT_FORM;

    /**
     * Version for area.
     */
    public static final UOctet AREA_VERSION = new UOctet((short) 1);

    /**
     * A constant holding the maximum value a {@code UOctet} can have,
     * 2<sup>8</sup>-1.
     */
    public static final short MAX_VALUE = 255;

    private short value;

    /**
     * Default constructor.
     */
    public UOctet() {
        value = 0;
    }

    /**
     * Constructor.
     *
     * @param value Value to initialise with.
     */
    public UOctet(final short value) {
        if (value < 0) {
            throw new IllegalArgumentException("UOctet argument must not be negative");
        }
        if (value > UOctet.MAX_VALUE) {
            throw new IllegalArgumentException(
                    "UOctet argument must not be greater than " + UOctet.MAX_VALUE);
        }
        this.value = value;
    }

    /**
     * Constructor.
     *
     * @param value Value to initialise with.
     */
    public UOctet(final int value) {
        this((short) value);
    }

    @Override
    public Element createElement() {
        return new UOctet();
    }

    /**
     * Returns the value of this type.
     *
     * @return the value.
     */
    public short getValue() {
        return value;
    }

    @Override
    public TypeId getTypeId() {
        return new TypeId(Attribute.UOCTET_SHORT_FORM);
    }

    @Override
    public void encode(final MALEncoder encoder) throws MALException {
        encoder.encodeUOctet(this);
    }

    @Override
    public Element decode(final MALDecoder decoder) throws MALException {
        return decoder.decodeUOctet();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UOctet)) {
            return false;
        }
        return this.value == (((UOctet) obj).value);
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
