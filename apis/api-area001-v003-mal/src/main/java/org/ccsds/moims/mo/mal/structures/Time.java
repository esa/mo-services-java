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

import java.time.Instant;
import java.util.Calendar;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;

/**
 * Class representing MAL Time type.
 */
public class Time implements Attribute {

    private static final long serialVersionUID = Attribute.TIME_SHORT_FORM;
    private final long value;

    /**
     * Default constructor.
     */
    public Time() {
        value = 0;
    }

    /**
     * Initialises the object with a certain time. The value shall be the
     * difference, measured in milliseconds, between the current time and
     * midnight, January 1, 1970 UTC. This is based on the native Java operation
     * from the System.currentTimeMillis().
     *
     * <p>
     * See the description of the class <code>Date</code> for a discussion of
     * slight discrepancies that may arise between "computer time" and
     * coordinated universal time (UTC).
     *
     * @param value The time to instantiate the object (Unix time).
     */
    public Time(final long value) {
        this.value = value;
    }

    /**
     * Initialises the object with a certain time. The value must represent a
     * valid instant in UTC and is parsed using DateTimeFormatter#ISO_INSTANT.
     * The time must be in a text string such as
     * {@code 2007-12-03T10:15:30.00Z}.
     *
     * <p>
     * See the description of the class <code>Date</code> for a discussion of
     * slight discrepancies that may arise between "computer time" and
     * coordinated universal time (UTC).
     *
     * @param value The time in UTC and is parsed using
     * DateTimeFormatter#ISO_INSTANT.
     */
    public Time(final String value) {
        this(Instant.parse(value).toEpochMilli());
    }

    @Override
    public Element createElement() {
        return new Time();
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
     * Returns the current time encapsulated in a Time type.
     *
     * @return The current time.
     */
    public static Time now() {
        return new Time(System.currentTimeMillis());
    }

    /**
     * Converts this Time object into a FineTime object.
     *
     * @return The FineTime object for the same time.
     */
    public FineTime toFineTime() {
        return new FineTime(value * ONE_MILLION);
    }

    public static Time generateTime(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 12, 0, 0);
        return new Time(calendar.getTime().getTime());
    }

    @Override
    public TypeId getTypeId() {
        return new TypeId(Attribute.TIME_SHORT_FORM);
    }

    @Override
    public void encode(final MALEncoder encoder) throws MALException {
        encoder.encodeTime(this);
    }

    @Override
    public Element decode(final MALDecoder decoder) throws MALException {
        return decoder.decodeTime();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Time)) {
            return false;
        }
        return this.value == (((Time) obj).value);
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
