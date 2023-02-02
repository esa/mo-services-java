/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Fixed Length Binary encoder
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
package esa.mo.mal.encoder.binary.fixed;

import esa.mo.mal.encoder.binary.base.BinaryTimeHandler;
import java.math.BigInteger;
import java.util.List;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALListDecoder;
import org.ccsds.moims.mo.mal.encoding.BufferHolder;

/**
 * Implements the MALDecoder interface for a fixed length binary encoding.
 */
public class FixedBinaryDecoder extends esa.mo.mal.encoder.binary.base.BaseBinaryDecoder {

    /**
     * Constructor.
     *
     * @param src Byte array to read from.
     * @param timeHandler Time handler to use.
     * @param shortLengthField True if length field is 16-bit wide, otherwise
     * assumed to be 32-bit.
     */
    public FixedBinaryDecoder(final byte[] src,
            final BinaryTimeHandler timeHandler, final boolean shortLengthField) {
        super(new FixedBinaryBufferHolder(null, src, 0, src.length, shortLengthField),
                timeHandler);
    }

    /**
     * Constructor.
     *
     * @param is Input stream to read from.
     * @param timeHandler Time handler to use.
     * @param shortLengthField True if length field is 16-bit wide, otherwise
     * assumed to be 32-bit.
     */
    public FixedBinaryDecoder(final java.io.InputStream is,
            final BinaryTimeHandler timeHandler, final boolean shortLengthField) {
        super(new FixedBinaryBufferHolder(is, null, 0, 0, shortLengthField), timeHandler);
    }

    /**
     * Constructor.
     *
     * @param src Byte array to read from.
     * @param offset index in array to start reading from.
     * @param timeHandler Time handler to use.
     * @param shortLengthField True if length field is 16-bit wide, otherwise
     * assumed to be 32-bit.
     */
    public FixedBinaryDecoder(final byte[] src, final int offset,
            final BinaryTimeHandler timeHandler, final boolean shortLengthField) {
        super(new FixedBinaryBufferHolder(null, src, offset, src.length, shortLengthField), timeHandler);
    }

    /**
     * Constructor.
     *
     * @param src Source buffer holder to use.
     * @param timeHandler Time handler to use.
     */
    public FixedBinaryDecoder(final BufferHolder src, final BinaryTimeHandler timeHandler) {
        super(src, timeHandler);
    }

    @Override
    public MALListDecoder createListDecoder(final List list) throws MALException {
        return new FixedBinaryListDecoder(list, sourceBuffer, timeHandler);
    }

    /**
     * Internal class that implements the fixed length field decoding.
     */
    public static class FixedBinaryBufferHolder extends BaseBinaryBufferHolder {

        /**
         * 16-bit length field encoding enabled
         */
        protected final boolean shortLengthField;

        /**
         * Constructor.
         *
         * @param is Input stream to read from.
         * @param buf Source buffer to use.
         * @param offset Buffer offset to read from next.
         * @param length Length of readable data held in the array, which may be
         * larger.
         * @param shortLengthField True if length field is 16-bit wide,
         * otherwise assumed to be 32-bit.
         */
        public FixedBinaryBufferHolder(final java.io.InputStream is,
                final byte[] buf,
                final int offset,
                final int length,
                final boolean shortLengthField) {
            super(is, buf, offset, length);
            this.shortLengthField = shortLengthField;
        }

        @Override
        public long readUnsignedLong() throws MALException {
            buf.checkBuffer(8);
            final int i = buf.shiftOffsetAndReturnPrevious(8);
            return java.nio.ByteBuffer.wrap(buf.getBuf(), i, 8).getLong();
        }

        @Override
        public long readUnsignedLong32() throws MALException {
            buf.checkBuffer(4);
            final int i = buf.shiftOffsetAndReturnPrevious(4);
            return java.nio.ByteBuffer.wrap(buf.getBuf(), i, 4).getInt() & 0xFFFFFFFFL;
        }

        @Override
        public int readUnsignedInt() throws MALException {
            buf.checkBuffer(4);
            final int i = buf.shiftOffsetAndReturnPrevious(4);
            return java.nio.ByteBuffer.wrap(buf.getBuf(), i, 4).getInt();
        }

        @Override
        public int readUnsignedInt16() throws MALException {
            buf.checkBuffer(2);
            final int i = buf.shiftOffsetAndReturnPrevious(2);
            return java.nio.ByteBuffer.wrap(buf.getBuf(), i, 2).getShort() & 0xFFFF;
        }

        @Override
        public int readUnsignedShort() throws MALException {
            buf.checkBuffer(2);
            final int i = buf.shiftOffsetAndReturnPrevious(2);
            return java.nio.ByteBuffer.wrap(buf.getBuf(), i, 2).getShort();
        }

        @Override
        public short readUnsignedShort8() throws MALException {
            return (short) (read8() & 0xFF);
        }

        @Override
        public short readSignedShort() throws MALException {
            return (short) readUnsignedShort();
        }

        @Override
        public int readSignedInt() throws MALException {
            return readUnsignedInt();
        }

        @Override
        public long readSignedLong() throws MALException {
            return readUnsignedLong();
        }

        @Override
        public BigInteger readBigInteger() throws MALException {
            // Make sure that sign bit is always 0
            byte[] readBuf = new byte[9];
            System.arraycopy(buf.directGetBytes(8), 0, readBuf, 1, 8);
            return new BigInteger(readBuf);
        }

        @Override
        public String readString() throws MALException {
            int len = (shortLengthField) ? readUnsignedShort() : readUnsignedInt();

            if (len >= 0) {
                buf.checkBuffer(len);
                return new String(buf.getBuf(),
                        buf.shiftOffsetAndReturnPrevious(len), len, UTF8_CHARSET);
            }
            return null;
        }

        @Override
        public byte[] readBytes() throws MALException {
            int len = (shortLengthField) ? readUnsignedShort() : readUnsignedInt();
            return readBytes(len);
        }
    }
}
