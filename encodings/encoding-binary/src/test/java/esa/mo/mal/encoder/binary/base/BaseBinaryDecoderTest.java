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

import esa.mo.mal.encoder.binary.fixed.FixedBinaryDecoder;
import esa.mo.mal.encoder.binary.variable.VariableBinaryDecoder;
import esa.mo.mal.encoder.binary.split.SplitBinaryDecoder;
import esa.mo.mal.encoder.binary.split.SplitBinaryEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.ccsds.moims.mo.mal.MALException;
import static org.junit.Assert.*;
import org.junit.Test;

public class BaseBinaryDecoderTest {

    private static ByteArrayInputStream fragmented(byte[] data, final int chunkSize) {
        return new ByteArrayInputStream(data) {
            @Override
            public synchronized int read(byte[] buffer, int offset, int length) {
                return super.read(buffer, offset, Math.min(length, chunkSize));
            }
        };
    }

    @Test
    public void fixedIntegerSurvivesShortReads() throws MALException {
        FixedBinaryDecoder decoder = new FixedBinaryDecoder(
                fragmented(new byte[]{1, 35, 69, 103, 42}, 1), null, false);
        assertEquals(Integer.valueOf(0x01234567), decoder.decodeInteger());
        assertEquals(Byte.valueOf((byte) 42), decoder.decodeOctet());
    }

    @Test
    public void variableStringSurvivesShortReads() throws MALException {
        VariableBinaryDecoder decoder = new VariableBinaryDecoder(
                fragmented(new byte[]{5, 'h', 'e', 'l', 'l', 'o', 42}, 2), null);
        assertEquals("hello", decoder.decodeString());
        assertEquals(Byte.valueOf((byte) 42), decoder.decodeOctet());
    }

    @Test
    public void largeReadSurvivesShortReads() throws MALException {
        byte[] data = new byte[BaseBinaryDecoder.BLOCK_SIZE + 17];
        for (int i = 0; i < data.length; ++i) {
            data[i] = (byte) i;
        }
        BaseBinaryDecoder.BaseBinaryInputReader reader =
                new BaseBinaryDecoder.BaseBinaryInputReader(fragmented(data, 37), null, 0, 0);
        assertArrayEquals(data, reader.directGetBytes(data.length));
    }

    @Test
    public void compactionPreservesUnreadBytes() throws MALException {
        byte[] buffered = new byte[]{99, 99, 1, 2};
        BaseBinaryDecoder.BaseBinaryInputReader reader =
                new BaseBinaryDecoder.BaseBinaryInputReader(
                        fragmented(new byte[]{3, 4, 5}, 1), buffered, 2, 4);
        assertArrayEquals(new byte[]{1, 2, 3, 4}, reader.directGetBytes(4));
        assertEquals(5, reader.get8());
    }

    @Test(expected = MALException.class)
    public void truncatedStreamIsRejected() throws MALException {
        FixedBinaryDecoder decoder = new FixedBinaryDecoder(
                fragmented(new byte[]{1, 2, 3}, 1), null, false);
        decoder.decodeInteger();
    }

    @Test(expected = MALException.class)
    public void truncatedArrayIsRejected() throws MALException {
        BaseBinaryDecoder.BaseBinaryInputReader reader =
                new BaseBinaryDecoder.BaseBinaryInputReader(null, new byte[]{1, 2}, 0, 2);
        reader.directGetBytes(3);
    }

    @Test(expected = MALException.class)
    public void unusedArrayCapacityIsNotDecoded() throws MALException {
        BaseBinaryDecoder.BaseBinaryInputReader reader =
                new BaseBinaryDecoder.BaseBinaryInputReader(null, new byte[]{1, 2, 99, 99}, 0, 2);
        reader.directGetBytes(3);
    }

    @Test
    public void bufferedReadPreservesOffset() throws MALException {
        BaseBinaryDecoder.BaseBinaryInputReader reader =
                new BaseBinaryDecoder.BaseBinaryInputReader(null, new byte[]{99, 1, 2, 3}, 1, 4);
        assertArrayEquals(new byte[]{1, 2}, reader.directGetBytes(2));
        assertEquals(3, reader.get8());
    }

    @Test
    public void splitBitStoreSurvivesBufferGrowth() throws MALException {
        String value = new String(new char[BaseBinaryDecoder.BLOCK_SIZE + 9]).replace('\0', 'x');
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        SplitBinaryEncoder encoder = new SplitBinaryEncoder(output, null);
        encoder.encodeBoolean(true);
        encoder.encodeString(value);
        encoder.encodeBoolean(false);
        encoder.encodeNullableString(null);
        encoder.close();

        SplitBinaryDecoder decoder = new SplitBinaryDecoder(fragmented(output.toByteArray(), 31), null);
        assertTrue(decoder.decodeBoolean());
        assertEquals(value, decoder.decodeString());
        assertFalse(decoder.decodeBoolean());
        assertNull(decoder.decodeNullableString());
    }

    @Test(expected = MALException.class, timeout = 1000)
    public void streamWithoutProgressIsRejected() throws MALException {
        ByteArrayInputStream input = new ByteArrayInputStream(new byte[]{1}) {
            @Override
            public synchronized int read(byte[] buffer, int offset, int length) {
                return 0;
            }
        };
        new FixedBinaryDecoder(input, null, false).decodeInteger();
    }

    @Test
    public void streamFailurePreservesCause() {
        final IOException failure = new IOException("read failed");
        InputStream input = new InputStream() {
            @Override
            public int read() throws IOException {
                throw failure;
            }
        };
        try {
            new FixedBinaryDecoder(input, null, false).decodeInteger();
            fail("Expected the stream failure to be reported");
        } catch (MALException ex) {
            assertSame(failure, ex.getCause());
        }
    }
}
