/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO String encoder
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
package esa.mo.mal.encoder.string;

import static esa.mo.mal.encoder.string.StringEncoder.STR_DELIM;
import static esa.mo.mal.encoder.string.StringEncoder.STR_DELIM_ESC;
import static esa.mo.mal.encoder.string.StringEncoder.STR_ESC;
import static esa.mo.mal.encoder.string.StringEncoder.STR_ESC_ESC;
import static esa.mo.mal.encoder.string.StringEncoder.STR_NULL;
import static esa.mo.mal.encoder.string.StringEncoder.STR_NULL_ESC;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.encoding.BufferHolder;
import org.ccsds.moims.mo.mal.encoding.Decoder;
import org.ccsds.moims.mo.mal.structures.*;

/**
 * The implementation of the MALDecoder interface for the String encoding.
 */
public class StringDecoder extends Decoder {

    static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    public static final int BLOCK_SIZE = 65536;

    /**
     * Constructor.
     *
     * @param src Source string to read from.
     */
    public StringDecoder(final String src) {
        super(new StringBufferHolder(src, 0));
    }

    /**
     * Constructor.
     *
     * @param is Source stream to read from.
     */
    public StringDecoder(final java.io.InputStream is) {
        super(new StringBufferHolder(is));
    }

    /**
     * Constructor.
     *
     * @param src Source buffer holder to use..
     */
    protected StringDecoder(final BufferHolder src) {
        super(src);
    }

    @Override
    public Element decodeNullableElement(final Element element) throws MALException {
        final String strVal = sourceBuffer.readString();

        // Check if object is not null...
        if (!strVal.equals(STR_NULL)) {
            return element.decode(this);
        }

        return null;
    }

    @Override
    public byte[] getRemainingEncodedData() throws MALException {
        StringBufferHolder dSourceBuffer = (StringBufferHolder) sourceBuffer;

        dSourceBuffer.preLoadBuffer();
        while (dSourceBuffer.loadExtraBuffer()) {
            // do nothing, just loading in the complete message
        }

        return dSourceBuffer.buf.substring(dSourceBuffer.offset).getBytes(UTF8_CHARSET);
    }

    @Override
    public Element decodeEnumeration(Enumeration enumeration) throws MALException {
        int enumSize = enumeration.getEnumSize();

        if (enumSize < 65536) {
            return enumeration.fromValue(this.decodeUShort().getValue());
        }

        throw new MALException("The Enumeration could not be decoded!");
    }

    /**
     * Simple class for holding the source string and the offset into that
     * string for the next read.
     */
    protected static class StringBufferHolder extends BufferHolder {

        private final java.io.InputStream inputStream;
        private String buf;
        private int offset;

        /**
         * Constructor.
         *
         * @param buf The source buffer string.
         * @param offset The current read offset.
         */
        public StringBufferHolder(final String buf, final int offset) {
            this.inputStream = null;
            this.buf = buf;
            this.offset = offset;
        }

        /**
         * Constructor.
         *
         * @param is Source stream to read from.
         */
        public StringBufferHolder(final java.io.InputStream is) {
            this.inputStream = is;
            this.buf = null;
            this.offset = 0;
        }

        @Override
        public String readString() throws MALException {
            return removeFirst();
        }

        @Override
        public float readFloat() throws MALException {
            try {
                return Float.valueOf(removeFirst());
            } catch (NumberFormatException ex) {
                throw new MALException(ex.getLocalizedMessage(), ex);
            }
        }

        @Override
        public double readDouble() throws MALException {
            try {
                return Double.valueOf(removeFirst());
            } catch (NumberFormatException ex) {
                throw new MALException(ex.getLocalizedMessage(), ex);
            }
        }

        @Override
        public BigInteger readBigInteger() throws MALException {
            try {
                return new BigInteger(removeFirst());
            } catch (NumberFormatException ex) {
                throw new MALException(ex.getLocalizedMessage(), ex);
            }
        }

        @Override
        public long readSignedLong() throws MALException {
            try {
                return Long.valueOf(removeFirst());
            } catch (NumberFormatException ex) {
                throw new MALException(ex.getLocalizedMessage(), ex);
            }
        }

        @Override
        public int readSignedInt() throws MALException {
            try {
                return Integer.valueOf(removeFirst());
            } catch (NumberFormatException ex) {
                throw new MALException(ex.getLocalizedMessage(), ex);
            }
        }

        @Override
        public short readSignedShort() throws MALException {
            try {
                return Short.valueOf(removeFirst());
            } catch (NumberFormatException ex) {
                throw new MALException(ex.getLocalizedMessage(), ex);
            }
        }

        @Override
        public long readUnsignedLong() throws MALException {
            return readSignedLong();
        }

        @Override
        public long readUnsignedLong32() throws MALException {
            return readSignedLong();
        }

        @Override
        public int readUnsignedInt() throws MALException {
            return readSignedInt();
        }

        @Override
        public int readUnsignedInt16() throws MALException {
            return readSignedInt();
        }

        @Override
        public int readUnsignedShort() throws MALException {
            return readSignedInt();
        }

        @Override
        public short readUnsignedShort8() throws MALException {
            return readSignedShort();
        }

        @Override
        public byte[] readBytes() throws MALException {
            return hexStringToByteArray(removeFirst());
        }

        @Override
        public boolean readBool() throws MALException {
            try {
                return Boolean.valueOf(removeFirst());
            } catch (NumberFormatException ex) {
                throw new MALException(ex.getLocalizedMessage(), ex);
            }
        }

        @Override
        public byte read8() throws MALException {
            try {
                return Byte.valueOf(removeFirst());
            } catch (NumberFormatException ex) {
                throw new MALException(ex.getLocalizedMessage(), ex);
            }
        }

        @Override
        public boolean readIsNotNull() throws MALException {
            final String strVal = peekNext();

            // Check if object is null...
            if (strVal.equals(STR_NULL)) {
                // its null so need to pop the null flag
                popNext();
                return false;
            }

            // its not null so leave index where they are
            return true;
        }

        @Override
        public byte[] readBytes(int length) throws MALException {
            // not supported/required for this encoding
            return null;
        }

        private String removeFirst() throws MALException {
            String str;

            final int index = findNextOffset();

            // No more chars
            if (index == -1) {
                str = buf.substring(offset, buf.length());
                offset = buf.length();
            } else {
                str = buf.substring(offset, index);
                offset = index + 1;
            }

            return str.replace(STR_DELIM_ESC, STR_DELIM)
                    .replace(STR_NULL_ESC, STR_NULL)
                    .replace(STR_ESC_ESC, STR_ESC);
        }

        private String peekNext() throws MALException {
            String str;

            final int index = findNextOffset();

            // No more chars
            if (index == -1) {
                str = buf.substring(offset, buf.length());
            } else {
                str = buf.substring(offset, index);
            }

            return str;
        }

        private void popNext() throws MALException {
            final int index = findNextOffset();

            // No more chars
            if (index == -1) {
                offset = buf.length();
            } else {
                offset = index + 1;
            }
        }

        private int findNextOffset() throws MALException {
            preLoadBuffer();
            int index = findNextIndex();

            // ensure that we have loaded enough buffer from the 
            // input stream (if we are stream based) for the next read
            if (index == -1) {
                boolean needMore = true;
                while (needMore) {
                    final boolean haveMore = loadExtraBuffer();
                    index = findNextIndex();
                    needMore = haveMore && (index == -1);
                }
            }

            return index;
        }

        private int findNextIndex() throws MALException {
            int index = buf.indexOf(STR_DELIM, offset);

            while (-1 != index) {
                boolean isDelimiter = true;

                if (0 < index) {
                    // check for previous escape character
                    int lIndex = index - 1;
                    while ((0 <= lIndex) && ('\\' == buf.charAt(lIndex))) {
                        lIndex--;
                        isDelimiter = !isDelimiter;
                    }
                }

                if (isDelimiter) {
                    return index;
                }

                // did not find it in this segement
                if (index == (buf.length() - 1)) {
                    return -1;
                }

                // didn't find delimiter and need to scan on from the next character
                index = buf.indexOf(STR_DELIM, index + 1);
            }

            return -1;
        }

        private void preLoadBuffer() throws MALException {
            if ((inputStream != null) && (buf == null)) {
                // need to load in some
                final byte[] tbuf = new byte[BLOCK_SIZE];

                try {
                    final int length = inputStream.read(tbuf, 0, tbuf.length);
                    buf = new String(tbuf, 0, length, UTF8_CHARSET);
                    offset = 0;
                } catch (IOException ex) {
                    throw new MALException("Unable to read required amount from source stream", ex);
                }
            }
        }

        private boolean loadExtraBuffer() throws MALException {
            boolean moreAvailable = false;

            try {
                if (null != inputStream && (0 != inputStream.available())) {
                    // need to load in some
                    final byte[] tbuf = new byte[BLOCK_SIZE];

                    final int length = inputStream.read(tbuf, 0, tbuf.length);
                    buf += new String(tbuf, 0, length, UTF8_CHARSET);
                    moreAvailable = 0 != inputStream.available();
                }
            } catch (IOException ex) {
                throw new MALException("Unable to read required amount from source stream", ex);
            }

            return moreAvailable;
        }
    }

    private static byte[] hexStringToByteArray(final String s) {
        final int len = s.length();
        final byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(
                    s.charAt(i + 1), 16));
        }
        return data;
    }
}
