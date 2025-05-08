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
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.encoding.BufferHolder;
import org.ccsds.moims.mo.mal.encoding.Decoder;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Enumeration;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Time;

/**
 * Implements the MALDecoder interface for a binary encoding.
 */
public abstract class BaseBinaryDecoder extends Decoder {

    protected static final java.util.logging.Logger LOGGER = Logger.getLogger(
            BaseBinaryDecoder.class.getName());
    protected static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    protected static final int BLOCK_SIZE = 65536;

    protected final BinaryTimeHandler timeHandler;

    /**
     * Constructor allowing child classes to use own BufferHolder
     *
     * @param src Source buffer holder to use.
     * @param timeHandler Time handler to use.
     */
    protected BaseBinaryDecoder(final BufferHolder src, final BinaryTimeHandler timeHandler) {
        super(src);
        this.timeHandler = timeHandler;
    }

    @Override
    public byte[] getRemainingEncodedData() throws MALException {
        BaseBinaryBufferHolder dSourceBuffer = (BaseBinaryBufferHolder) sourceBuffer;
        return Arrays.copyOfRange(dSourceBuffer.buf.buf, dSourceBuffer.buf.offset,
                dSourceBuffer.buf.contentLength);
    }

    @Override
    public Duration decodeDuration() throws MALException {
        return timeHandler.decodeDuration((BaseBinaryBufferHolder) sourceBuffer);
    }

    @Override
    public Time decodeTime() throws MALException {
        return timeHandler.decodeTime((BaseBinaryBufferHolder) sourceBuffer);
    }

    @Override
    public FineTime decodeFineTime() throws MALException {
        return timeHandler.decodeFineTime((BaseBinaryBufferHolder) sourceBuffer);
    }

    @Override
    public Element decodeEnumeration(Enumeration enumeration) throws MALException {
        int enumSize = enumeration.getEnumSize();

        if (enumSize < 256) {
            return enumeration.fromValue((int) this.decodeUOctet().getValue());
        } else if (enumSize < 65536) {
            return enumeration.fromValue(this.decodeUShort().getValue());
        }

        throw new MALException("The Enumeration could not be decoded!");
    }

    public BaseBinaryBufferHolder getBufferHolder() {
        return (BaseBinaryBufferHolder) sourceBuffer;
    }

    public BinaryTimeHandler getTimeHandler() {
        return timeHandler;
    }

    /**
     * Internal class that is used to hold the byte buffer. Derived classes
     * should extend this (and replace it in the constructors).
     */
    public static abstract class BaseBinaryBufferHolder extends BufferHolder {

        protected final BaseBinaryInputReader buf;

        /**
         * Constructor.
         *
         * @param is Input stream to read from.
         * @param buf Source buffer to use.
         * @param offset Buffer offset to read from next.
         * @param length Length of readable data held in the array, which may be
         * larger.
         */
        public BaseBinaryBufferHolder(final java.io.InputStream is,
                final byte[] buf, final int offset, final int length) {
            super();
            this.buf = new BaseBinaryInputReader(is, buf, offset, length);
        }

        /**
         * Constructor allowing child classes to introduce its own input reader
         *
         * @param buf Source buffer to use.
         */
        protected BaseBinaryBufferHolder(final BaseBinaryInputReader buf) {
            super();
            this.buf = buf;
        }

        public BaseBinaryInputReader getBuf() {
            return buf;
        }

        @Override
        public String readString() throws MALException {
            final int len = readUnsignedInt();

            if (len >= 0) {
                buf.checkBuffer(len);

                final String s = new String(buf.buf, buf.offset, len, UTF8_CHARSET);
                buf.offset += len;
                return s;
            }
            return null;
        }

        @Override
        public byte read8() throws MALException {
            return buf.get8();
        }

        @Override
        public byte[] readBytes() throws MALException {
            return readBytes(readUnsignedInt());
        }

        @Override
        public boolean readBool() throws MALException {
            return !(read8() == 0);
        }

        @Override
        public boolean readIsNotNull() throws MALException {
            return readBool();
        }

        @Override
        public float readFloat() throws MALException {
            return Float.intBitsToFloat(readSignedInt());
        }

        @Override
        public double readDouble() throws MALException {
            return Double.longBitsToDouble(readSignedLong());
        }

        @Override
        public byte[] readBytes(final int size) throws MALException {
            return buf.directGetBytes(size);
        }
    }

    protected static class BaseBinaryInputReader {

        protected final java.io.InputStream inputStream;
        protected byte[] buf;
        protected int offset;
        protected int contentLength;
        protected boolean forceRealloc = false;

        /**
         * Constructor.
         *
         * @param is Input stream to read from.
         * @param buf Source buffer to use.
         * @param offset Buffer offset to read from next.
         * @param length Length of readable data held in the array, which may be
         * larger.
         */
        public BaseBinaryInputReader(final java.io.InputStream is,
                final byte[] buf, final int offset, final int length) {
            super();
            this.inputStream = is;
            this.buf = buf;
            this.offset = offset;
            this.contentLength = length;
        }

        public void setForceRealloc(boolean forceRealloc) {
            this.forceRealloc = forceRealloc;
        }

        public byte get8() throws MALException {
            checkBuffer(1);

            return buf[offset++];
        }

        public byte[] directGetBytes(final int size) throws MALException {
            if (size >= 0) {
                checkBuffer(size);

                final byte[] v = Arrays.copyOfRange(buf, offset, offset + size);
                offset += size;
                return v;
            }

            throw new IllegalArgumentException("Size must not be negative");
        }

        /**
         * Ensures that we have loaded enough buffer from the input stream (if
         * we are stream based) for the next read.
         *
         * @param requiredLength number of bytes required.
         * @throws MALException if there is an error reading from the stream
         */
        public void checkBuffer(final int requiredLength) throws MALException {
            if (null != inputStream) {
                int existingContentRemaining = 0;
                int existingBufferLength = 0;

                // have we got any loaded data currently
                if (null != this.buf) {
                    existingContentRemaining = this.contentLength - this.offset;
                    existingBufferLength = this.buf.length;
                }

                // check to see if currently loaded data covers the required data size
                if (existingContentRemaining < requiredLength) {
                    LOGGER.log(Level.FINER,
                            "Not enought bytes available. Expecting {0}",
                            requiredLength);

                    // ok, check to see if we have enough space left in the 
                    // current buffer for what we need to load
                    if ((existingBufferLength - this.offset) < requiredLength) {
                        byte[] destBuf = this.buf;

                        // its not big enough, we need to check if we need a 
                        // bigger buffer or in case we know the existing
                        // buffer is still required.
                        if (forceRealloc || (existingBufferLength < requiredLength)) {
                            // we do, so allocate one
                            bufferRealloced(existingBufferLength);
                            existingBufferLength = (requiredLength > BLOCK_SIZE) ? requiredLength : BLOCK_SIZE;
                            destBuf = new byte[existingBufferLength];
                        }

                        // this either shifts the existing contents to the start 
                        // of the old buffer, or copies it into the new buffer
                        // NOTE: this is faster than System.arraycopy, as that 
                        // performs argument type checks
                        for (int i = 0; i < existingContentRemaining; ++i) {
                            destBuf[i] = this.buf[this.offset + i];
                        }

                        // the start of the data in the buffer has moved to zero now
                        this.buf = destBuf;
                        this.offset = 0;
                        this.contentLength = existingContentRemaining;
                    }

                    try {
                        // read into the empty space of the buffer
                        LOGGER.log(Level.FINER, "Reading from input stream: {0}",
                                (existingBufferLength - this.contentLength));
                        final int read = inputStream.read(this.buf, this.contentLength,
                                existingBufferLength - this.contentLength);
                        LOGGER.log(Level.FINER, "Read from input stream: {0}", read);
                        if (read < 0) {
                            throw new MALException(
                                    "(1) Unable to read required amount from source stream: end of file.");
                        }
                        this.contentLength += read;
                    } catch (IOException ex) {
                        throw new MALException(
                                "(2) Unable to read required amount from source stream", ex);
                    }
                }
            }
        }

        /**
         * Returns the internal byte buffer.
         *
         * @return the byte buffer
         */
        public byte[] getBuf() {
            return buf;
        }

        public int getOffset() {
            return offset;
        }

        /**
         * Adds a delta to the internal offset and returns the previous offset
         *
         * @param delta the delta to apply
         * @return the previous offset
         */
        public int shiftOffsetAndReturnPrevious(int delta) {
            int i = offset;
            offset += delta;
            return i;
        }

        /**
         * Notification method that can be used by derived classes to notify
         * them that the internal buffer has been reallocated.
         *
         * @param oldSize the old buffer size
         */
        public void bufferRealloced(int oldSize) {
            // no implementation for standard decoder
        }
    }
}
