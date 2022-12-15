/* ----------------------------------------------------------------------------
 * Copyright (C) 2022      European Space Agency
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
package esa.mo.mal.encoder.gen;

import java.math.BigInteger;
import org.ccsds.moims.mo.mal.MALException;

/**
 * Internal class that is used to hold the byte buffer. Derived classes should
 * extend this (and replace it in the constructors) if they encode the fields
 * differently from this encoding.
 */
public abstract class BufferHolder {

    /**
     * Reads a string from the incoming stream.
     *
     * @return the extracted string.
     * @throws MALException If there is a problem with the decoding.
     */
    public abstract String readString() throws MALException;

    /**
     * Reads a float from the incoming stream.
     *
     * @return the extracted float.
     * @throws MALException If there is a problem with the decoding.
     */
    public abstract float readFloat() throws MALException;

    /**
     * Reads a double from the incoming stream.
     *
     * @return the extracted double.
     * @throws MALException If there is a problem with the decoding.
     */
    public abstract double readDouble() throws MALException;

    /**
     * Reads a BigInteger from the incoming stream.
     *
     * @return the extracted BigInteger.
     * @throws MALException If there is a problem with the decoding.
     */
    public abstract BigInteger readBigInteger() throws MALException;

    /**
     * Reads a single signed long from the incoming stream.
     *
     * @return the extracted value.
     * @throws MALException If there is a problem with the decoding.
     */
    public abstract long readSignedLong() throws MALException;

    /**
     * Reads a single signed integer from the incoming stream.
     *
     * @return the extracted value.
     * @throws MALException If there is a problem with the decoding.
     */
    public abstract int readSignedInt() throws MALException;

    /**
     * Reads a single signed short from the incoming stream.
     *
     * @return the extracted value.
     * @throws MALException If there is a problem with the decoding.
     */
    public abstract short readSignedShort() throws MALException;

    /**
     * Reads a single unsigned long from the incoming stream.
     *
     * @return the extracted value.
     * @throws MALException If there is a problem with the decoding.
     */
    public abstract long readUnsignedLong() throws MALException;

    /**
     * Reads a single 32 bit unsigned integer as a long from the incoming stream.
     *
     * @return the extracted value.
     * @throws MALException If there is a problem with the decoding.
     */
    public abstract long readUnsignedLong32() throws MALException;

    /**
     * Reads a single unsigned integer from the incoming stream.
     *
     * @return the extracted value.
     * @throws MALException If there is a problem with the decoding.
     */
    public abstract int readUnsignedInt() throws MALException;

    /**
     * Reads a single 16 bit unsigned integer as a int from the incoming stream.
     *
     * @return the extracted value.
     * @throws MALException If there is a problem with the decoding.
     */
    public abstract int readUnsignedInt16() throws MALException;

    /**
     * Reads a single unsigned short from the incoming stream.
     *
     * @return the extracted value.
     * @throws MALException If there is a problem with the decoding.
     */
    public abstract int readUnsignedShort() throws MALException;

    /**
     * Reads a single 8 bit unsigned integer as a short from the incoming stream.
     *
     * @return the extracted value.
     * @throws MALException If there is a problem with the decoding.
     */
    public abstract short readUnsignedShort8() throws MALException;

    /**
     * Reads a single byte from the incoming stream.
     *
     * @return the extracted byte.
     * @throws MALException If there is a problem with the decoding.
     */
    public abstract byte read8() throws MALException;

    /**
     * Reads a byte array from the incoming stream.
     *
     * @return the extracted byte.
     * @throws MALException If there is a problem with the decoding.
     */
    public abstract byte[] readBytes() throws MALException;

    /**
     * Reads a single Boolean value from the incoming stream.
     *
     * @return the extracted value.
     * @throws MALException If there is a problem with the decoding.
     */
    public abstract boolean readBool() throws MALException;

    /**
     * Reads a single Boolean and returns true is the next value is not NULL.
     *
     * @return the extracted value.
     * @throws MALException If there is a problem with the decoding.
     */
    public abstract boolean readIsNotNull() throws MALException;

    /**
     * Reads a byte array with the specified length from the incoming stream.
     *
     * @param length The number of bytes to retrieve
     * @return the extracted byte.
     * @throws MALException If there is a problem with the decoding.
     */
    public abstract byte[] readBytes(int length) throws MALException;
}
