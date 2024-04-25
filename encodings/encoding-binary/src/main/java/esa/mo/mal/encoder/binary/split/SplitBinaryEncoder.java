/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Split Binary encoder
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
package esa.mo.mal.encoder.binary.split;

import java.io.IOException;
import java.io.OutputStream;
import esa.mo.mal.encoder.binary.base.BinaryTimeHandler;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.encoding.StreamHolder;

/**
 * Implements the MALEncoder and MALListEncoder interfaces for a split binary
 * encoding.
 */
public class SplitBinaryEncoder extends esa.mo.mal.encoder.binary.variable.VariableBinaryEncoder {

    private int openCount = 1;

    /**
     * Constructor.
     *
     * @param os Output stream to write to.
     * @param timeHandler Time handler to use.
     */
    public SplitBinaryEncoder(final OutputStream os, final BinaryTimeHandler timeHandler) {
        super(new SplitBinaryStreamHolder(os), timeHandler);
    }

    /**
     * Constructor for derived classes that have their own stream holder
     * implementation that should be used.
     *
     * @param os Output stream to write to.
     * @param timeHandler Time handler to use.
     */
    protected SplitBinaryEncoder(final StreamHolder os, final BinaryTimeHandler timeHandler) {
        super(os, timeHandler);
    }

    @Override
    public org.ccsds.moims.mo.mal.MALListEncoder createListEncoder(
            final java.util.List value) throws MALException {
        ++openCount;

        return super.createListEncoder(value);
    }

    /**
     * A MAL string is encoded as follows: - String Length: UInteger -
     * Character: UTF-8, variable size, multiple of octet The field 'string
     * length' shall be assigned with the number of octets required to encode
     * the character of the string
     *
     * @param val The string to encode
     * @throws MALException if the string to encode is too large
     */
    @Override
    public void encodeString(String val) throws MALException {

        try {
            outputStream.writeString(val);
        } catch (IOException e) {
            throw new MALException(ENCODING_EXCEPTION_STR, e);
        }
    }

    @Override
    public void encodeNullableString(String value) throws MALException {

        try {
            if (value != null) {
                // encode presence flag
                outputStream.writeIsNotNull();
                // encode element as String
                encodeString(value);
            } else {
                // encode presence flag
                outputStream.writeIsNull();

            }
        } catch (IOException e) {
            throw new MALException(ENCODING_EXCEPTION_STR, e);
        }
    }

    @Override
    public void close() {
        --openCount;

        if (1 > openCount) {
            try {
                ((SplitBinaryStreamHolder) outputStream).close();
            } catch (IOException ex) {
                // do nothing
            }
        }
    }

    /**
     * Extends the StreamHolder class for handling splitting out the Boolean
     * values.
     */
    public static class SplitBinaryStreamHolder extends VariableBinaryStreamHolder {

        private static final int BIT_BYTES_BLOCK_SIZE = 1024;
        private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        private byte[] bitBytes = new byte[BIT_BYTES_BLOCK_SIZE];
        private int bitBytesInUse = 0;
        private int bitIndex = 0;

        /**
         * Constructor.
         *
         * @param outputStream The output stream to encode into.
         */
        public SplitBinaryStreamHolder(OutputStream outputStream) {
            super(outputStream);
        }

        @Override
        public void close() throws IOException {
            streamAddUnsignedInt(outputStream, bitBytesInUse);
            outputStream.write(bitBytes, 0, bitBytesInUse);
            baos.writeTo(outputStream);
        }

        @Override
        public void writeBool(boolean value) throws IOException {
            if (value) {
                setBit(bitIndex);
            }
            ++bitIndex;
        }

        @Override
        public void writeIsNull() throws IOException {
            ++bitIndex;
        }

        @Override
        public void writeIsNotNull() throws IOException {
            setBit(bitIndex);
            ++bitIndex;
        }

        @Override
        public void write(final byte[] value, int os, int ln) throws IOException {
            baos.write(value, os, ln);
        }

        @Override
        public void write(final byte[] val) throws IOException {
            baos.write(val);
        }

        @Override
        public void write(final byte val) throws IOException {
            baos.write(val);
        }

        private static void streamAddUnsignedInt(java.io.OutputStream os, int value) throws IOException {
            while ((value & 0xFFFFFF80) != 0L) {
                os.write((value & 0x7F) | 0x80);
                value >>>= 7;
            }
            os.write(value & 0x7F);
        }

        public void addFixedUnsignedLong(long value) throws IOException {
            write(java.nio.ByteBuffer.allocate(8).putLong(value).array());
        }

        private void setBit(int bitIndex) {
            int byteIndex = bitIndex / 8;

            int bytesRequired = byteIndex + 1;
            if (bitBytesInUse < bytesRequired) {
                if (bitBytes.length < bytesRequired) {
                    bitBytes = java.util.Arrays.copyOf(bitBytes,
                            ((bytesRequired / BIT_BYTES_BLOCK_SIZE) + 1)
                            * BIT_BYTES_BLOCK_SIZE);
                }

                bitBytesInUse = bytesRequired;
            }

            bitIndex %= 8;
            bitBytes[byteIndex] |= (1 << bitIndex);

        }
    }
}
