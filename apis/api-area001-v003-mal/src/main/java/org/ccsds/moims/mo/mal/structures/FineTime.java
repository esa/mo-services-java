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
 * Class representing MAL FineTime type.
 */
public class FineTime implements Attribute {

    private static final long serialVersionUID = Attribute.FINETIME_SHORT_FORM;
    private final long value;

    /**
     * Default constructor.
     */
    public FineTime() {
        value = 0;
    }

    /**
     * Initialises the object with a certain time. The value shall be the
     * difference, measured in nanoseconds, between the current time and
     * midnight, January 1, 1970 UTC.
     *
     * The FineTime structure max date is: <b>2262-April-12 01:47:16.854</b>
     * Make sure your mission lifetime is not longer than that date or the
     * software might experience inconveniences with this FineTime structure.
     *
     * @param value The time to instantiate the object (Unix time).
     */
    public FineTime(final long value) {
        this.value = value;
    }

    @Override
    public Element createElement() {
        return new FineTime();
    }

    /**
     * Returns the value of this type.
     *
     * @return the value.
     */
    public long getValue() {
        return value;
    }

    /**
     * Returns the current time encapsulated in a FineTime type.
     *
     * @return The current time.
     */
    public static FineTime now() {
        // Convert from milliseconds (10^-3) to nanoseconds (10^-9)
        return new FineTime(System.currentTimeMillis() * ONE_MILLION);
    }

    public Time toTime() {
        return new Time(value / ONE_MILLION);
    }

    @Override
    public TypeId getTypeId() {
        return new TypeId(Attribute.FINETIME_SHORT_FORM);
    }

    @Override
    public void encode(final MALEncoder encoder) throws MALException {
        encoder.encodeFineTime(this);
    }

    @Override
    public Element decode(final MALDecoder decoder) throws MALException {
        return decoder.decodeFineTime();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FineTime)) {
            return false;
        }
        return this.value == (((FineTime) obj).value);
    }

    @Override
    public int hashCode() {
        return (int) Long.hashCode(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
