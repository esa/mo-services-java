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

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;

/**
 * Internal class for writing to the output stream. Overridden by sub-classes to
 * alter the low level encoding.
 */
public abstract class StreamHolder {

    protected final OutputStream outputStream;

    /**
     * Constructor.
     *
     * @param outputStream the stream to encode in to.
     */
    public StreamHolder(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * Adds a String to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public abstract void writeString(final String value) throws IOException;

    /**
     * Adds a float to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public abstract void writeFloat(final float value) throws IOException;

    /**
     * Adds a double to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public abstract void writeDouble(final double value) throws IOException;

    /**
     * Adds a BigInteger to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public abstract void writeBigInteger(final BigInteger value) throws IOException;

    /**
     * Adds a signed long to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public abstract void writeSignedLong(final long value) throws IOException;

    /**
     * Adds a signed int to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public abstract void writeSignedInt(final int value) throws IOException;

    /**
     * Adds a signed short to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public abstract void writeSignedShort(final short value) throws IOException;

    /**
     * Adds a zigzag encoded unsigned long to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public abstract void writeUnsignedLong(long value) throws IOException;

    /**
     * Adds an unsigned 32bit integer held as a long to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public abstract void writeUnsignedLong32(long value) throws IOException;

    /**
     * Adds a zigzag encoded unsigned int to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public abstract void writeUnsignedInt(int value) throws IOException;

    /**
     * Adds an unsigned 32bit integer held as a long to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public abstract void writeUnsignedInt16(int value) throws IOException;

    /**
     * Adds a zigzag encoded unsigned short to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public abstract void writeUnsignedShort(int value) throws IOException;

    /**
     * Adds an unsigned 32bit integer held as a long to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public abstract void writeUnsignedShort8(short value) throws IOException;

    /**
     * Adds a byte array to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public abstract void writeBytes(final byte[] value) throws IOException;

    /**
     * Adds a byte to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public abstract void writeByte(final byte value) throws IOException;

    /**
     * Adds a Boolean to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public abstract void writeBool(boolean value) throws IOException;

    /**
     * Adds a not Null flag value to the output stream.
     *
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public abstract void writeNotNull() throws IOException;

    /**
     * Adds an is Null flag value to the output stream.
     *
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public abstract void writeIsNull() throws IOException;

    /**
     * Low level byte array write to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public void write(final byte[] value) throws IOException {
        outputStream.write(value);
    }

    /**
     * Low level byte array write to the output stream.
     *
     * @param value the value to encode.
     * @param os offset into array.
     * @param ln length to add.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public void write(final byte[] value, int os, int ln) throws IOException {
        outputStream.write(value, os, ln);
    }

    /**
     * Low level byte write to the output stream.
     *
     * @param value the value to encode.
     * @throws IOException is there is a problem adding the value to the stream.
     */
    public void write(final byte value) throws IOException {
        outputStream.write(value);
    }

    /**
     * Closes and flushes the output stream.
     *
     * @throws IOException if there is an error.s
     */
    public void close() throws IOException {
        outputStream.flush();
    }
}
