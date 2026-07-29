/* ----------------------------------------------------------------------------
 * Copyright (C) 2026      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Transport Framework
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
package esa.mo.mal.encoders;

import esa.mo.mal.encoder.binary.base.BinaryTimeHandler;
import esa.mo.mal.encoder.binary.fixed.FixedBinaryDecoder;
import esa.mo.mal.encoder.binary.fixed.FixedBinaryEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Random;
import org.ccsds.moims.mo.mal.structures.Blob;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Encodes and decodes Blobs of a range of sizes, including sizes larger than
 * 2 GB, to check that the whole round-trip is streamed (never fully held in
 * memory) and preserves the content length.
 */
public class LargeBlobTest {

    private static final long KB = 1024;
    private static final long MB = 1024 * KB;
    private static final long GB = 1024 * MB;

    @Test
    public void blob512KB() throws Exception {
        encodeThenDecodeBlob("512 KB", 512 * KB);
    }

    @Test
    public void blob32MB() throws Exception {
        encodeThenDecodeBlob("32 MB", 32 * MB);
    }

    @Test
    public void blob1GB() throws Exception {
        encodeThenDecodeBlob("1 GB", 1 * GB);
    }

    @Test
    public void blob3GB() throws Exception {
        encodeThenDecodeBlob("3 GB", 3 * GB);
    }

    @Test
    public void blob5GB() throws Exception {
        encodeThenDecodeBlob("5 GB", 5 * GB);
    }

    /**
     * Generates a random file of the given size, wraps it in a Blob, encodes it
     * with the fixed binary encoder, decodes it back and checks the decoded Blob
     * has the same length. The round-trip is streamed, so it works for files
     * larger than the heap. Source, encoded and any spooled temporary files are
     * cleaned up afterwards.
     *
     * @param label a human-readable size label, for messages.
     * @param size the Blob size in bytes.
     * @throws Exception if the round-trip fails.
     */
    private void encodeThenDecodeBlob(String label, long size) throws Exception {
        System.out.println("=== " + label + " (" + size + " bytes) ===");

        File dir = new File("target/large-blob-test");
        dir.mkdirs();
        File srcFile = new File(dir, "blob-src.bin");
        File encodedFile = new File(dir, "encoded.bin");

        try {
            // 1. Generate a file of the requested size with random content
            long start = System.nanoTime();
            generateRandomFile(srcFile, size);
            System.out.println("[1] Generated " + srcFile.length() + " byte file in "
                    + elapsedMs(start) + " ms");

            // 2. Wrap it in a Blob (file-based, so a byte[] is never needed)
            Blob blob = new Blob(srcFile);
            System.out.println("[2] Blob created: " + blob);

            // 3. Encode
            start = System.nanoTime();
            try (OutputStream fos = new BufferedOutputStream(new FileOutputStream(encodedFile))) {
                FixedBinaryEncoder encoder = new FixedBinaryEncoder(fos, new BinaryTimeHandler(), false);
                blob.encode(encoder);
                encoder.close();
            }
            System.out.println("[3] Encoded " + encodedFile.length() + " bytes in "
                    + elapsedMs(start) + " ms");

            // 4. Decode and check the content length survived the round-trip
            start = System.nanoTime();
            try (InputStream fis = new BufferedInputStream(new FileInputStream(encodedFile))) {
                FixedBinaryDecoder decoder = new FixedBinaryDecoder(fis, new BinaryTimeHandler(), false);
                Blob decoded = (Blob) new Blob().decode(decoder);
                System.out.println("[4] Decoded " + decoded + " in " + elapsedMs(start) + " ms");
                assertEquals(label + ": decoded length", size, decoded.getLengthLong());

                // Remove the temporary file the decoder spools large Blobs into
                if (decoded.isURLBased()) {
                    new File(URI.create(decoded.getURL())).delete();
                }
            }
        } finally {
            srcFile.delete();
            encodedFile.delete();
            dir.delete();
        }
    }

    /**
     * Returns the number of milliseconds elapsed since the given nanosecond
     * timestamp.
     *
     * @param startNanos the start time, from {@link System#nanoTime()}.
     * @return the elapsed time in milliseconds.
     */
    private static long elapsedMs(long startNanos) {
        return (System.nanoTime() - startNanos) / 1_000_000;
    }

    /**
     * Generates a file of the given size filled with deterministic pseudo-random
     * bytes. The content is written in fixed-size chunks so the generation itself
     * never holds the whole file in memory, allowing sizes larger than the heap.
     * Reusable by any test that needs a large file.
     *
     * @param file the file to (over)write.
     * @param size the number of bytes to write.
     * @throws IOException if the file could not be written.
     */
    public static void generateRandomFile(File file, long size) throws IOException {
        byte[] chunk = new byte[8 * 1024 * 1024];
        Random rnd = new Random(42);
        long written = 0;
        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(file))) {
            while (written < size) {
                rnd.nextBytes(chunk);
                int toWrite = (int) Math.min(chunk.length, size - written);
                os.write(chunk, 0, toWrite);
                written += toWrite;
            }
        }
    }
}
