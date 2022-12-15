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
package esa.mo.mal.encoder.binary.variable;

import esa.mo.mal.encoder.binary.base.BinaryTimeHandler;
import esa.mo.mal.encoder.gen.StreamHolder;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;

/**
 * Implements the MALEncoder and MALListEncoder interfaces for a fixed length
 * binary encoding.
 */
public class VariableBinaryEncoder extends esa.mo.mal.encoder.binary.base.BaseBinaryEncoder {

    /**
     * Constructor.
     *
     * @param os Output stream to write to.
     * @param timeHandler Time handler to use.
     */
    public VariableBinaryEncoder(final OutputStream os, final BinaryTimeHandler timeHandler) {
        super(new VariableBinaryStreamHolder(os), timeHandler);
    }

    /**
     * Constructor for derived classes that have their own stream holder
     * implementation that should be used.
     *
     * @param os Output stream to write to.
     * @param timeHandler Time handler to use.
     */
    protected VariableBinaryEncoder(final StreamHolder os, final BinaryTimeHandler timeHandler) {
        super(os, timeHandler);
    }

    /**
     * Extends the StreamHolder class for handling fixed length, non-zig-zag
     * encoded, fields.
     */
    public static class VariableBinaryStreamHolder extends BaseBinaryStreamHolder {

        private static final BigInteger B_127 = new BigInteger("127");
        private static final BigInteger B_128 = new BigInteger("128");

        /**
         * Constructor.
         *
         * @param outputStream The output stream to encode into.
         */
        public VariableBinaryStreamHolder(OutputStream outputStream) {
            super(outputStream);
        }

        @Override
        public void writeUnsignedInt(int value) throws IOException {
            while ((value & -128) != 0L) {
                write((byte) ((value & 127) | 128));
                value >>>= 7;
            }
            write((byte) (value & 127));
        }

        @Override
        public void writeUnsignedLong(long value) throws IOException {
            while ((value & -128L) != 0L) {
                write((byte) (((int) value & 127) | 128));
                value >>>= 7;
            }
            write((byte) ((int) value & 127));
        }

        @Override
        public void writeSignedLong(final long value) throws IOException {
            writeUnsignedLong((value << 1) ^ (value >> 63));
        }

        @Override
        public void writeSignedInt(final int value) throws IOException {
            writeUnsignedInt((value << 1) ^ (value >> 31));
        }

        @Override
        public void writeSignedShort(final short value) throws IOException {
            writeUnsignedInt((value << 1) ^ (value >> 31));
        }

        @Override
        public void writeBigInteger(BigInteger value) throws IOException {
            while (value.and(B_127.not()).compareTo(BigInteger.ZERO) == 1) {
                byte byteToWrite = (value.and(B_127)).or(B_128).byteValue();
                write(byteToWrite);
                value = value.shiftRight(7);
            }
            BigInteger encoded = value.and(B_127);
            write(encoded.byteValue());
        }

        @Override
        public void writeUnsignedLong32(long value) throws IOException {
            writeUnsignedLong(value);
        }

        @Override
        public void writeUnsignedInt16(int value) throws IOException {
            writeUnsignedInt(value);
        }

        @Override
        public void writeUnsignedShort(int value) throws IOException {
            writeUnsignedInt(value);
        }

        @Override
        public void writeUnsignedShort8(short value) throws IOException {
            write(java.nio.ByteBuffer.allocate(2).putShort(value).array()[1]);
        }
    }
}
