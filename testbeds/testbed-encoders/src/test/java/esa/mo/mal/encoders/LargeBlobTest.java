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
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Element;
import static org.junit.Assume.assumeTrue;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Probes whether a Blob larger than 2 GB can be encoded and decoded:
 * generate a 3 GB file, wrap it in a Blob, encode, then decode.
 */
public class LargeBlobTest {

    private static final long SIZE = 3L * 1024 * 1024 * 1024; // 3 GB (on purpose > 2 GB)

    @Ignore("A Blob larger than 2 GB cannot yet be encoded (the length prefix is a "
            + "32-bit field); this probe is expected to fail until that is addressed. "
            + "Enable it manually to reproduce the limitation.")
    @Test
    public void encodeThenDecode3GBBlob() throws Exception {
        File dir = new File("target/large-blob-test");
        dir.mkdirs();
        File srcFile = new File(dir, "blob-3gb.bin");
        File encodedFile = new File(dir, "encoded.bin");

        // Pre-flight resource checks. This test deliberately uses a 3 GB Blob to
        // exercise the >2 GB limitation. Without enough heap or disk it would fail
        // for the wrong reason (a heap OutOfMemoryError, or "No space left on
        // device") which would mask the actual Blob limitation. In that case,
        // alert and skip the test rather than report a misleading failure.
        long maxHeap = Runtime.getRuntime().maxMemory();
        long freeDisk = dir.getUsableSpace();

        if (maxHeap < SIZE) {
            System.err.println("ALERT: skipping - not enough heap for a " + SIZE
                    + " byte Blob. Need -Xmx >= " + SIZE + " bytes, have " + maxHeap
                    + ". The test would fail with a heap OutOfMemoryError for lack of RAM,"
                    + " not because of the Blob 2 GB limitation.");
        }
        if (freeDisk < SIZE) {
            System.err.println("ALERT: skipping - not enough free disk to generate a " + SIZE
                    + " byte file in " + dir.getAbsolutePath() + ". Need " + SIZE
                    + " bytes, have " + freeDisk + ". The test would fail while writing the"
                    + " file, not because of the Blob limitation.");
        }
        assumeTrue("Not enough heap (-Xmx) for a 3 GB Blob", maxHeap >= SIZE);
        assumeTrue("Not enough free disk for a 3 GB file", freeDisk >= SIZE);

        try {
            // 1. Generate a 3 GB file with random content
            System.out.println("[1] Generating " + SIZE + " byte random file...");
            byte[] chunk = new byte[8 * 1024 * 1024];
            Random rnd = new Random(42);
            long written = 0;
            try (OutputStream os = new BufferedOutputStream(new FileOutputStream(srcFile))) {
                while (written < SIZE) {
                    rnd.nextBytes(chunk);
                    int toWrite = (int) Math.min(chunk.length, SIZE - written);
                    os.write(chunk, 0, toWrite);
                    written += toWrite;
                }
            }
            System.out.println("    File generated: " + srcFile.length() + " bytes");

            // 2. Create a Blob with it (file-based: a byte[] cannot hold 3 GB)
            Blob blob = new Blob(srcFile);
            System.out.println("[2] Blob created: " + blob);

            // 3. Encode
            System.out.println("[3] Encoding...");
            try (OutputStream fos = new BufferedOutputStream(new FileOutputStream(encodedFile))) {
                FixedBinaryEncoder encoder = new FixedBinaryEncoder(fos, new BinaryTimeHandler(), false);
                blob.encode(encoder);
                encoder.close();
            }
            System.out.println("    Encoded: " + encodedFile.length() + " bytes");

            // 4. Decode
            System.out.println("[4] Decoding...");
            try (InputStream fis = new BufferedInputStream(new FileInputStream(encodedFile))) {
                FixedBinaryDecoder decoder = new FixedBinaryDecoder(fis, new BinaryTimeHandler(), false);
                Element decoded = new Blob().decode(decoder);
                System.out.println("    Decoded: " + decoded);
            }

            System.out.println("RESULT: PASS");
        } finally {
            srcFile.delete();
            encodedFile.delete();
            dir.delete();
        }
    }
}
