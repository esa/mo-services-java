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

import java.math.BigInteger;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;

/**
 * Class representing MAL ULong type.
 */
public class ULong implements Attribute {

    private static final long serialVersionUID = Attribute.ULONG_SHORT_FORM;

    /**
     * A constant holding the maximum value a {@code ULong} can have,
     * 2<sup>64</sup>-1.
     */
    public static final BigInteger MAX_VALUE = new BigInteger("18446744073709551615");

    private java.math.BigInteger value;

    /**
     * Default constructor.
     */
    public ULong() {
        value = BigInteger.ZERO;
    }

    /**
     * Initialiser constructor.
     *
     * @param value Value to initialise with.
     */
    public ULong(final java.math.BigInteger value) {
        if (value == null) {
            throw new IllegalArgumentException("ULong argument must not be null");
        }
        if (value.signum() < 0) {
            throw new IllegalArgumentException("ULong argument must not be negative");
        }
        if (ULong.MAX_VALUE.compareTo(value) < 0) {
            throw new IllegalArgumentException(
                    "ULong argument must not be greater than " + ULong.MAX_VALUE);
        }
        this.value = value;
    }

    @Override
    public Element createElement() {
        return new ULong();
    }

    /**
     * Returns the value of this type.
     *
     * @return the value.
     */
    public java.math.BigInteger getValue() {
        return value;
    }

    @Override
    public TypeId getTypeId() {
        return new TypeId(Attribute.ULONG_SHORT_FORM);
    }

    @Override
    public void encode(final MALEncoder encoder) throws MALException {
        encoder.encodeULong(this);
    }

    @Override
    public Element decode(final MALDecoder decoder) throws MALException {
        return decoder.decodeULong();
    }

    @Override
    public boolean equals(final Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ULong)) {
            return false;
        }
        return this.value.equals(((ULong) obj).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
