/* ----------------------------------------------------------------------------
 * Copyright (C) 2017      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO ZMTP Transport Framework
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
package esa.mo.mal.encoder.zmtp.header;

import esa.mo.mal.encoder.binary.base.BinaryTimeHandler;
import esa.mo.mal.encoder.binary.fixed.FixedBinaryEncoder;
import org.ccsds.moims.mo.mal.encoding.StreamHolder;
import esa.mo.mal.transport.zmtp.ZMTPTransport;
import java.io.IOException;
import java.io.OutputStream;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALListEncoder;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;

public class ZMTPHeaderEncoder extends FixedBinaryEncoder {

    /**
     * ZMTP transport that created the encoder - used for MDK encoding.
     */
    protected ZMTPTransport transport;

    /**
     * Constructor.
     *
     * @param os Output stream to write to
     * @param transport Parent transport
     * @param timeHandler Implementation of the time encoding to use
     */
    public ZMTPHeaderEncoder(OutputStream os, ZMTPTransport transport, BinaryTimeHandler timeHandler) {
        super(os, timeHandler, false);
        this.transport = transport;
    }

    /**
     * Constructor for derived classes that have their own stream holder
     * implementation that should be used.
     *
     * @param os Output stream to write to.
     * @param transport Parent transport
     * @param timeHandler Implementation of the time encoding to use
     */
    protected ZMTPHeaderEncoder(final StreamHolder os, ZMTPTransport transport,
            BinaryTimeHandler timeHandler) {
        super(os, timeHandler);
        this.transport = transport;
    }

    public void encodeVariableUInteger(UInteger value) throws MALException {
        try {
            addVariableUnsignedInt(value.getValue());
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    protected void addVariableSignedInt(final long value) throws IOException {
        addVariableUnsignedInt((value << 1) ^ (value >> 63));
    }

    protected void addVariableUnsignedInt(long value) throws IOException {
        while ((value & -128L) != 0L) {
            outputStream.write((byte) (((int) value & 127) | 128));
            value >>>= 7;
        }
        outputStream.write((byte) ((int) value & 127));
    }

    @Override
    public MALListEncoder createListEncoder(final java.util.List value) throws MALException {
        checkForNull(value);
        try {
            addVariableUnsignedInt(value.size());
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
        return this;
    }

    @Override
    public void encodeString(String value) throws MALException {
        checkForNull(value);
        try {
            int key = transport.stringMappingDirectory.getKey(value);
            if (key == -1) {
                byte[] stringBytes = value.getBytes(UTF8_CHARSET);
                addVariableSignedInt(stringBytes.length);
                outputStream.write(stringBytes);
            } else {
                key = -key;
                addVariableSignedInt(key);
            }
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeIdentifier(Identifier value) throws MALException {
        checkForNull(value);
        encodeString(value.getValue());
    }

    @Override
    public void encodeURI(URI value) throws MALException {
        checkForNull(value);
        encodeString(value.getValue());
    }

    @Override
    public void encodeBlob(Blob value) throws MALException {
        checkForNull(value);
        try {
            byte[] blobBytes = value.getValue();
            addVariableUnsignedInt(blobBytes.length);
            outputStream.write(blobBytes);
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

}
