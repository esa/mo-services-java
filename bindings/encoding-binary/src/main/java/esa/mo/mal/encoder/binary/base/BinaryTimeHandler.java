/* ----------------------------------------------------------------------------
 * Copyright (C) 2017      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Binary encoder
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
package esa.mo.mal.encoder.binary.base;

import java.text.MessageFormat;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Time;
import java.io.IOException;
import org.ccsds.moims.mo.mal.structures.Duration;

/**
 * Class used for binary time coding/decoding
 *
 * It implements CCSDS Day Segmented Time Code (CDS) with P-field assumed to be
 * "01000000" for Time and "01000010" for FineTime
 */
public class BinaryTimeHandler {

    protected static final String IO_EXCEPTION_STR = "IO Exception during time encoding";
    public static final long ONE_MILLION = 1000000L;
    public static final long MILLISECONDS_FROM_CCSDS_TO_UNIX_EPOCH = 378691200000L;
    public static final long NANOSECONDS_FROM_CCSDS_TO_UNIX_EPOCH
            = MILLISECONDS_FROM_CCSDS_TO_UNIX_EPOCH * ONE_MILLION;
    public static final long MILLISECONDS_IN_DAY = 86400000;
    public static final long NANOSECONDS_IN_DAY = MILLISECONDS_IN_DAY * ONE_MILLION;

    /**
     * Converts MAL seconds timestamp to CDS with CCSDS Epoch, 16 bit day
     * segment, 32 bit ms of day segment, then writes it into the associated
     * binary encoder
     *
     * @param streamHolder Associated binary encoder stream holder
     * @param value Time to be encoded
     * @throws MALException if there is an error
     */
    public void encodeTime(final BaseBinaryEncoder.BaseBinaryStreamHolder streamHolder,
            Time value) throws MALException {
        long timestamp = value.getValue();
        timestamp += MILLISECONDS_FROM_CCSDS_TO_UNIX_EPOCH;
        long days = timestamp / MILLISECONDS_IN_DAY;
        long millisecondsInDay = (timestamp % MILLISECONDS_IN_DAY);

        if (days > 65535) {
            // This check allows values bigger than maximum signed short, 
            // because the encoded value is an unsigned short
            throw new MALException(MessageFormat.format(
                    "Overflow of unsigned 16-bit days ({0}) when encoding MAL Time", days));
        }

        try {
            streamHolder.directAdd(java.nio.ByteBuffer.allocate(2).putShort((short) days).array());
            streamHolder.directAdd(java.nio.ByteBuffer.allocate(4).putInt((int) millisecondsInDay).array());
        } catch (IOException ex) {
            throw new MALException(IO_EXCEPTION_STR, ex);
        }
    }

    /**
     * Converts MAL nanoseconds timestamp to CDS with CCSDS Epoch, 16 bit day
     * segment, 32 bit ms of day segment, 32 bit sub-ms segment, then writes it
     * into the associated binary encoder
     *
     * @param streamHolder Associated binary encoder stream holder
     * @param value FineTime to be encoded
     * @throws MALException if there is an error
     */
    public void encodeFineTime(final BaseBinaryEncoder.BaseBinaryStreamHolder streamHolder,
            FineTime value) throws MALException {
        long timestamp = value.getValue();
        timestamp += NANOSECONDS_FROM_CCSDS_TO_UNIX_EPOCH;
        long days = timestamp / NANOSECONDS_IN_DAY;
        long nanosecondsInDay = (timestamp % NANOSECONDS_IN_DAY);
        long millisecondsInDay = nanosecondsInDay / ONE_MILLION;
        long picosecondsInMillisecond = (nanosecondsInDay % ONE_MILLION) * 1000;

        if (days > 65535) {
            // This check allows values bigger than maximum signed short, because the encoded value is an unsigned short
            throw new MALException(MessageFormat.format(
                    "Overflow of unsigned 16-bit days ({0}) when encoding MAL FineTime", days));
        }
        try {
            streamHolder.directAdd(java.nio.ByteBuffer.allocate(2).putShort((short) days).array());
            streamHolder.directAdd(java.nio.ByteBuffer.allocate(4).putInt((int) millisecondsInDay).array());
            streamHolder.directAdd(
                    java.nio.ByteBuffer.allocate(4).putInt((int) picosecondsInMillisecond).array());
        } catch (IOException ex) {
            throw new MALException(IO_EXCEPTION_STR, ex);
        }
    }

    /**
     * Encodes MAL Duration - defaults to a double encoding
     *
     * @param streamHolder Associated binary encoder stream holder
     * @param value Duration to be encoded
     * @throws MALException if there is an error
     */
    public void encodeDuration(final BaseBinaryEncoder.BaseBinaryStreamHolder streamHolder,
            Duration value) throws MALException {
        try {
            streamHolder.addDouble(value.getValue());
        } catch (IOException ex) {
            throw new MALException(IO_EXCEPTION_STR, ex);
        }
    }

    /**
     * Reads CDS with CCSDS Epoch, 16 bit day segment, 32 bit ms of day segment,
     * then converts it into a MAL seconds timestamp
     *
     * @param inputBufferHolder Associated binary decoder buffer holder
     * @return Time object
     * @throws MALException if there is an error
     */
    public Time decodeTime(final BaseBinaryDecoder.BaseBinaryBufferHolder inputBufferHolder) throws
            MALException {
        // Suppress sign extensions to use a full unsigned range
        long days = java.nio.ByteBuffer.wrap(inputBufferHolder.directGetBytes(2)).getShort() & 0xFFFFL;
        long millisecondsInDay
                = java.nio.ByteBuffer.wrap(inputBufferHolder.directGetBytes(4)).getInt() & 0xFFFFFFFFL;
        long timestamp = days * MILLISECONDS_IN_DAY;
        timestamp += millisecondsInDay;
        timestamp -= MILLISECONDS_FROM_CCSDS_TO_UNIX_EPOCH;
        return new Time(timestamp);
    }

    /**
     * Reads CDS with CCSDS Epoch, 16 bit day segment, 32 bit ms of day segment,
     * 32 bit sub-ms segment, then converts it into a MAL nanoseconds timestamp
     *
     * @param inputBufferHolder Associated binary decoder buffer holder
     * @return FineTime object
     * @throws MALException if there is an error
     */
    public FineTime decodeFineTime(final BaseBinaryDecoder.BaseBinaryBufferHolder inputBufferHolder)
            throws MALException {
        // Suppress sign extensions to use a full unsigned range
        long days = java.nio.ByteBuffer.wrap(inputBufferHolder.directGetBytes(2)).getShort() & 0xFFFFL;
        long millisecondsInDay
                = java.nio.ByteBuffer.wrap(inputBufferHolder.directGetBytes(4)).getInt() & 0xFFFFFFFFL;
        long picosecondsInMillisecond
                = java.nio.ByteBuffer.wrap(inputBufferHolder.directGetBytes(4)).getInt() & 0xFFFFFFFFL;
        long timestamp = days * NANOSECONDS_IN_DAY;
        timestamp += millisecondsInDay * ONE_MILLION;
        timestamp += picosecondsInMillisecond / 1000;
        timestamp -= NANOSECONDS_FROM_CCSDS_TO_UNIX_EPOCH;
        return new FineTime(timestamp);
    }

    /**
     * Reads MAL Duration - defaults to a double encoding
     *
     * @param inputBufferHolder Associated binary decoder buffer holder
     * @return Duration object
     * @throws MALException if there is an error
     */
    public Duration decodeDuration(final BaseBinaryDecoder.BaseBinaryBufferHolder inputBufferHolder)
            throws MALException {
        return new Duration(inputBufferHolder.getDouble());
    }
}
