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
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import org.ccsds.moims.mo.mal.encoding.StreamHolder;

/**
 * Extends the BaseBinaryEncoder for a fixed length binary encoding.
 */
public class FixedBinaryEncoder extends esa.mo.mal.encoder.binary.base.BaseBinaryEncoder {

    /**
     * Constructor.
     *
     * @param os Output stream to write to.
     * @param timeHandler Time handler to use.
     * @param shortLengthField True if length field is 16-bit wide, otherwise
     * assumed to be 32-bit.
     */
    public FixedBinaryEncoder(final OutputStream os,
            final BinaryTimeHandler timeHandler,
            final boolean shortLengthField) {
        super(new FixedBinaryStreamHolder(os, shortLengthField), timeHandler);
    }

    /**
     * Constructor for derived classes that have their own stream holder
     * implementation that should be used.
     *
     * @param os Output stream to write to.
     * @param timeHandler Time handler to use.
     */
    protected FixedBinaryEncoder(final StreamHolder os, final BinaryTimeHandler timeHandler) {
        super(os, timeHandler);
    }

    /**
     * Extends the StreamHolder class for handling fixed length, non-zig-zag
     * encoded fields.
     */
    public static class FixedBinaryStreamHolder extends BaseBinaryStreamHolder {

        /**
         * 16-bit length field encoding enabled
         */
        protected final boolean shortLengthField;

        private static final BigInteger B_255 = new BigInteger("255");

        /**
         * Constructor.
         *
         * @param outputStream The output stream to encode into.
         * @param shortLengthField True if length field is 16-bit wide,
         * otherwise assumed to be 32-bit.
         */
        public FixedBinaryStreamHolder(OutputStream outputStream,
                final boolean shortLengthField) {
            super(outputStream);
            this.shortLengthField = shortLengthField;
        }

        @Override
        public void writeSignedLong(final long value) throws IOException {
            writeUnsignedLong(value);
        }

        @Override
        public void writeSignedInt(final int value) throws IOException {
            writeUnsignedInt(value);
        }

        @Override
        public void writeSignedShort(final short value) throws IOException {
            writeUnsignedShort(value);
        }

        @Override
        public void writeUnsignedLong(long value) throws IOException {
            write(java.nio.ByteBuffer.allocate(8).putLong(value).array());
        }

        @Override
        public void writeUnsignedLong32(long value) throws IOException {
            write(java.nio.ByteBuffer.allocate(8).putLong(value).array(), 4, 4);
        }

        @Override
        public void writeUnsignedInt(int value) throws IOException {
            write(java.nio.ByteBuffer.allocate(4).putInt(value).array());
        }

        @Override
        public void writeUnsignedInt16(int value) throws IOException {
            write(java.nio.ByteBuffer.allocate(4).putInt(value).array(), 2, 2);
        }

        @Override
        public void writeUnsignedShort(int value) throws IOException {
            write(java.nio.ByteBuffer.allocate(2).putShort((short) value).array());
        }

        @Override
        public void writeUnsignedShort8(short value) throws IOException {
            write(java.nio.ByteBuffer.allocate(2).putShort(value).array()[1]);
        }

        @Override
        public void writeBigInteger(BigInteger value) throws IOException {
            byte[] valueBytes = value.toByteArray();
            int arrayLength = valueBytes.length;
            int arrayOffset = 0;
            // Strip sign bit if it is the only bit overflowing 8 bytes buffer
            if (valueBytes[0] == 0 && arrayLength == 9) {
                arrayOffset = 1;
                arrayLength--;
            }
            if (arrayLength > 8) {
                throw new IOException(
                        "Adding big integer larger than 8 bytes (size = "
                        + valueBytes.length + " bytes, value = " + value + ")");
            }
            java.nio.ByteBuffer buf = java.nio.ByteBuffer.allocate(8);
            ((java.nio.Buffer) buf).position(8 - arrayLength);
            write(buf.put(valueBytes, arrayOffset, arrayLength).array());
        }

        @Override
        public void writeBytes(final byte[] value) throws IOException {
            if (null == value) {
                if (shortLengthField) {
                    writeUnsignedShort(0);
                } else {
                    writeUnsignedInt(0);
                }
                throw new IOException("StreamHolder.addBytes: null value supplied!!");
            } else {
                if (shortLengthField) {
                    writeUnsignedShort(value.length);
                } else {
                    writeUnsignedInt(value.length);
                }
                write(value);
            }
        }
    }
}
