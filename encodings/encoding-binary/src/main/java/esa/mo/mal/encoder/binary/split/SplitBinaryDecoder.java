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

import java.io.InputStream;
import java.util.List;
import org.ccsds.moims.mo.mal.MALException;
import esa.mo.mal.encoder.binary.base.BinaryTimeHandler;
import org.ccsds.moims.mo.mal.MALListDecoder;
import org.ccsds.moims.mo.mal.encoding.BufferHolder;

/**
 * Implements the MALDecoder interface for a split binary encoding.
 */
public class SplitBinaryDecoder extends esa.mo.mal.encoder.binary.variable.VariableBinaryDecoder {

    /**
     * Constructor.
     *
     * @param src Byte array to read from.
     * @param timeHandler Time handler to use.
     */
    public SplitBinaryDecoder(final byte[] src, final BinaryTimeHandler timeHandler) {
        super(new SplitBinaryBufferHolder(null, src, 0, src.length), timeHandler);
    }

    /**
     * Constructor.
     *
     * @param is Input stream to read from.
     * @param timeHandler Time handler to use.
     */
    public SplitBinaryDecoder(final java.io.InputStream is, final BinaryTimeHandler timeHandler) {
        super(new SplitBinaryBufferHolder(is, null, 0, 0), timeHandler);
    }

    /**
     * Constructor.
     *
     * @param src Byte array to read from.
     * @param offset index in array to start reading from.
     * @param timeHandler Time handler to use.
     */
    public SplitBinaryDecoder(final byte[] src, final int offset, final BinaryTimeHandler timeHandler) {
        super(new SplitBinaryBufferHolder(null, src, offset, src.length), timeHandler);
    }

    /**
     * Constructor.
     *
     * @param src Source buffer holder to use.
     * @param timeHandler Time handler to use.
     */
    public SplitBinaryDecoder(final BufferHolder src, final BinaryTimeHandler timeHandler) {
        super(src, timeHandler);
    }

    @Override
    public MALListDecoder createListDecoder(final List list) throws MALException {
        return new SplitBinaryListDecoder(list, sourceBuffer, timeHandler);
    }

    /**
     * Extends BufferHolder to handle split binary encoding.
     */
    public static class SplitBinaryBufferHolder extends VariableBinaryBufferHolder {

        /**
         * Constructor.
         *
         * @param is Input stream to read from.
         * @param buf Source buffer to use.
         * @param offset Buffer offset to read from next.
         * @param length Length of readable data held in the array, which may be
         * larger.
         */
        public SplitBinaryBufferHolder(final java.io.InputStream is,
                final byte[] buf, final int offset, final int length) {
            super(new SplitBinaryInputReader(is, buf, offset, length));

            this.buf.setForceRealloc(true);
        }

        public SplitBinaryInputReader getSplitInputReader() {
            return (SplitBinaryInputReader) getBuf();
        }

        @Override
        public boolean readBool() throws MALException {
            // ensure that the bit buffer has been loaded first
            if (!getSplitInputReader().bitStoreLoaded) {
                getSplitInputReader().loadBitStore();
            }

            return getSplitInputReader().bitStore.pop();
        }

        public long getFixedUnsignedLong() throws MALException {
            buf.checkBuffer(8);

            final int i = buf.shiftOffsetAndReturnPrevious(8);
            return java.nio.ByteBuffer.wrap(buf.getBuf(), i, 8).getLong();
        }
    }

    protected static class SplitBinaryInputReader extends VariableBinaryInputReader {

        protected boolean bitStoreLoaded = false;
        protected BitGet bitStore = null;

        public SplitBinaryInputReader(InputStream is, byte[] buf, int offset, int length) {
            super(is, buf, offset, length);

            forceRealloc = true;
        }

        @Override
        public void checkBuffer(final int requiredLength) throws MALException {
            // ensure that the bit buffer has been loaded first
            if (!bitStoreLoaded) {
                loadBitStore();
            }

            super.checkBuffer(requiredLength);
        }

        @Override
        public void bufferRealloced(int oldSize) {
            if (0 < oldSize) {
                setForceRealloc(false);
            }
        }

        /**
         * Ensures that the bit buffer has been loaded
         *
         * @throws MALException on error.
         */
        protected void loadBitStore() throws MALException {
            // ensure that the bit buffer has been loaded first
            bitStoreLoaded = true;
            int size = getUnsignedInt();

            if (size >= 0) {
                super.checkBuffer(size);

                bitStore = new BitGet(buf, offset, size);
                offset += size;
            } else {
                bitStore = new BitGet(null, 0, 0);
            }
        }

        protected int getUnsignedInt() throws MALException {
            int value = 0;
            int i = 0;
            int b;
            while (((b = get8()) & 0x80) != 0) {
                value |= (b & 0x7F) << i;
                i += 7;
            }
            return value | (b << i);
        }
    }

    /**
     * Simple helper class for dealing with bit array. Smaller and faster than
     * Java BitSet.
     */
    protected static class BitGet {

        private final byte[] bitBytes;
        private final int bitBytesOffset;
        private final int bitBytesInUse;
        private int byteIndex = 0;
        private int bitIndex = 0;

        /**
         * Constructor.
         *
         * @param bytes Encoded bit set bytes. Supplied array is accessed
         * directly, it is not copied.
         * @param offset Offset, in bytes, into supplied byte array for start of
         * bit set.
         * @param length Length, in bytes, of supplied bit set.
         */
        public BitGet(byte[] bytes, final int offset, final int length) {
            this.bitBytes = bytes;
            this.bitBytesOffset = offset;
            this.bitBytesInUse = length;
        }

        /**
         * Returns true if the next bit is set to '1', false is set to '0'.
         *
         * @return True is set to '1', false otherwise.
         */
        public boolean pop() {
            boolean rv = (byteIndex < bitBytesInUse)
                    && ((bitBytes[byteIndex + bitBytesOffset] & (1 << bitIndex)) != 0);

            if (7 == bitIndex) {
                bitIndex = 0;
                ++byteIndex;
            } else {
                ++bitIndex;
            }

            return rv;
        }
    }
}
