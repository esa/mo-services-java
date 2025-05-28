/* ----------------------------------------------------------------------------
 * Copyright (C) 2021      European Space Agency
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
package org.ccsds.moims.mo.mal.helpertools.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Time;

/**
 * A Helper class to simplify the conversions related with time.
 */
public class HelperTime {

    private final static String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    private final static long ONE_MILLION = 1000000;

    /**
     * Converts a FineTime MAL data type timestamp into a readable string
     *
     * @param timestamp Time
     * @return The String with the time
     * @throws java.lang.IllegalArgumentException If timestamp == null.
     */
    public static String time2readableString(FineTime timestamp) throws IllegalArgumentException {
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp must not be null.");
        }
        Date date = new Date(timestamp.getValue() / ONE_MILLION);
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.format(date);
    }

    /**
     * Converts a Time MAL data type timestamp into a readable string
     *
     * @param timestamp Time
     * @return The String with the time
     * @throws java.lang.IllegalArgumentException If timestamp == null
     */
    public static String time2readableString(Time timestamp) throws IllegalArgumentException {
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp must not be null.");
        }
        Date date = new Date(timestamp.getValue());
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.format(date);
    }

    /**
     * Converts a readable time string to a MAL FineTime data type.
     *
     * @param time The readable time string. Expected format is
     * {@value #DATE_PATTERN}.
     * @return The MAL FineTime object or null if ParseException occurred
     */
    public static FineTime readableString2FineTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
        Date date;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            Logger.getLogger(HelperTime.class.getName()).log(Level.SEVERE,
                    String.format("Error while parsing %s", time), e);
            return null;
        }
        return new FineTime(date.getTime() * ONE_MILLION);
    }

    /**
     * Converts a readable time string to a MAL Time data type.
     *
     * @param time The readable time string. Expected format is
     * {@value #DATE_PATTERN}.
     * @return The MAL Time object or null if a ParseException occurred
     */
    public static Time readableString2Time(String time) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
        Date date;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            Logger.getLogger(HelperTime.class.getName()).log(Level.SEVERE,
                    String.format("Error while parsing %s", time), e);
            return null;
        }
        return new Time(date.getTime());
    }

    /**
     * Returns the current time encapsulated in a FineTime type
     *
     * @return The current time
     */
    @Deprecated
    public static FineTime getTimestamp() {
        // Convert from milliseconds (10^-3) to nanoseconds (10^-9)
        return new FineTime(System.currentTimeMillis() * ONE_MILLION);
    }

    /**
     * Returns the current time encapsulated in a Time type
     *
     * @return The current time
     */
    @Deprecated
    public static Time getTimestampMillis() {
        return new Time(System.currentTimeMillis());
    }

    private static long fromMilliToNano(long milli) {
        return (milli * ONE_MILLION);
    }

    @Deprecated
    public static FineTime timeToFineTime(final Time time) {
        return (time == null) ? null : time.toFineTime();
    }

    @Deprecated
    public static Time fineTimeToTime(final FineTime fineTime) {
        return (fineTime == null) ? null : fineTime.toTime();
    }

    /**
     * Returns just the fractional part of a time value that is represented in
     * nanoseconds. So, for 12.34567890123 seconds, we would expect to get the
     * integer: 567890123 nanoseconds
     *
     * @param nano The time in nanoseconds
     * @return The fractional part of time in nanoseconds
     */
    public static int getFractionalPart(long nano) {
        return ((int) ((nano % 1000000000)));
    }

    /**
     * Remove the milliseconds component from the timestamp, then converts to
     * nano and adds the missing nano fractional part.
     *
     * @param timestamp The timestamp in SQL timestamp
     * @return The time in nanoseconds
     * @throws java.lang.IllegalArgumentException If timestamp == null
     */
    public static long getNanosecondsFromSQLTimestamp(java.sql.Timestamp timestamp) throws IllegalArgumentException {
        if (timestamp == null) {
            throw new IllegalArgumentException("The timestamp must not be null.");
        }
        return (HelperTime.fromMilliToNano(timestamp.getTime()
                - (timestamp.getTime() % 1000))
                + timestamp.getNanos());
    }
}
