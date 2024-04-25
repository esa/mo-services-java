/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
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

import java.io.IOException;
import java.io.OutputStream;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.encoding.Encoder;
import org.ccsds.moims.mo.mal.encoding.StreamHolder;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Time;

/**
 * Implements the MALEncoder and MALListEncoder interfaces for a binary
 * encoding.
 *
 */
public abstract class BaseBinaryEncoder extends Encoder {

    protected BinaryTimeHandler timeHandler;

    /**
     * Constructor for derived classes that have their own stream holder
     * implementation that should be used.
     *
     * @param os Output stream to write to.
     * @param timeHandler Time handler to use.
     */
    protected BaseBinaryEncoder(final StreamHolder os, final BinaryTimeHandler timeHandler) {
        super(os);
        this.timeHandler = timeHandler;
    }

    @Override
    public void encodeDuration(final Duration value) throws MALException {
        checkForNull(value);
        timeHandler.encodeDuration((BaseBinaryStreamHolder) outputStream, value);
    }

    @Override
    public void encodeTime(final Time value) throws MALException {
        checkForNull(value);
        timeHandler.encodeTime((BaseBinaryStreamHolder) outputStream, value);
    }

    @Override
    public void encodeFineTime(final FineTime value) throws MALException {
        checkForNull(value);
        timeHandler.encodeFineTime((BaseBinaryStreamHolder) outputStream, value);
    }

    public BaseBinaryStreamHolder getStreamHolder() {
        return (BaseBinaryStreamHolder) outputStream;
    }

    public BinaryTimeHandler getTimeHandler() {
        return timeHandler;
    }

    /**
     * Internal class for accessing the output stream. Overridden by sub-classes
     * to alter the low level encoding.
     */
    public static abstract class BaseBinaryStreamHolder extends StreamHolder {

        /**
         * Constructor.
         *
         * @param outputStream the stream to encode in to.
         */
        public BaseBinaryStreamHolder(OutputStream outputStream) {
            super(outputStream);
        }

        @Override
        public void writeBytes(final byte[] value) throws IOException {
            if (null == value) {
                writeUnsignedInt(0);
                throw new IOException("StreamHolder.writeBytes: null value supplied!");
            } else {
                writeUnsignedInt(value.length);
                write(value);
            }
        }

        @Override
        public void writeString(String value) throws IOException {
            writeBytes(value.getBytes(UTF8_CHARSET));
        }

        @Override
        public void writeFloat(float value) throws IOException {
            writeSignedInt(Float.floatToRawIntBits(value));
        }

        @Override
        public void writeDouble(double value) throws IOException {
            writeSignedLong(Double.doubleToRawLongBits(value));
        }

        @Override
        public void writeByte(byte value) throws IOException {
            write(value);
        }

        @Override
        public void writeBool(boolean value) throws IOException {
            if (value) {
                write((byte) 1);
            } else {
                write((byte) 0);
            }
        }

        @Override
        public void writeIsNotNull() throws IOException {
            write((byte) 1);
        }

        @Override
        public void writeIsNull() throws IOException {
            write((byte) 0);
        }

        public OutputStream getOutputStream() {
            return outputStream;
        }
    }
}
