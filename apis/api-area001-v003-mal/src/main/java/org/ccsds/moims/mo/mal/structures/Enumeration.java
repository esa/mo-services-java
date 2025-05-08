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

/**
 * The abstract class represents the MAL Enumeration type. The class is extended
 * by the classes representing MAL enumeration types.
 *
 */
public abstract class Enumeration implements Element {

    /**
     * The value of the Enumeration.
     */
    protected Integer value;

    /**
     * The constructor of the Enumeration class.
     *
     * @param value The value of the enumeration.
     */
    protected Enumeration(final int value) {
        this.value = value;
    }

    /**
     * Returns the value of the enumeration.
     *
     * @return the value of the enumeration.
     */
    public final int getValue() {
        return value;
    }

    /**
     * Encodes the value of this object using the provided MALEncoder.
     *
     * @param encoder The encoder to use for encoding.
     * @throws org.ccsds.moims.mo.mal.MALException if any encoding errors are
     * detected.
     */
    @Override
    public void encode(MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        encoder.encodeEnumeration(this);
    }

    /**
     * Decodes the value of this object using the provided MALDecoder.
     *
     * @param decoder The decoder to use for decoding.
     * @return Returns this object.
     * @throws org.ccsds.moims.mo.mal.MALException if any decoding errors are
     * detected.
     */
    @Override
    public Element decode(MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        return decoder.decodeEnumeration(this);
    }

    /**
     * Returns the size of the enumeration.
     *
     * @return the size of the enumeration.
     */
    public abstract int getEnumSize();

    /**
     * Returns the respective Enumeration for a given value.
     *
     * @param value The value of this Enumeration.
     * @return The respective Enumeration.
     */
    public abstract Enumeration fromValue(Integer value);

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public boolean equals(final Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other instanceof Enumeration) {
            return (value.compareTo(((Enumeration) other).value) == 0);
        }
        return false;
    }
}
