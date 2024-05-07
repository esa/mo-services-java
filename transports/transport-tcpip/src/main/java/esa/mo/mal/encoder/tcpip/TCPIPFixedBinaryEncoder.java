/* ----------------------------------------------------------------------------
 * Copyright (C) 2014      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO TCP/IP Transport Framework
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
package esa.mo.mal.encoder.tcpip;

import esa.mo.mal.encoder.binary.base.BinaryTimeHandler;
import esa.mo.mal.encoder.binary.fixed.FixedBinaryEncoder;
import java.io.IOException;
import java.io.OutputStream;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UInteger;

/**
 * TCPIP Message header encoder
 *
 * @author Rian van Gijlswijk
 *
 */
public class TCPIPFixedBinaryEncoder extends FixedBinaryEncoder {

    private static final long MAX_STRING_LENGTH = 2 * (long) Integer.MAX_VALUE + 1;
    private static final BinaryTimeHandler tHandler = new BinaryTimeHandler();

    public TCPIPFixedBinaryEncoder(final OutputStream os, final BinaryTimeHandler timeHandler) {
        super(new TCPIPStreamHolder(os), timeHandler);
    }

    public TCPIPFixedBinaryEncoder(final OutputStream os) {
        super(new TCPIPStreamHolder(os), tHandler);
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
        byte[] output = val.getBytes(UTF8_CHARSET);

        if (output.length > MAX_STRING_LENGTH) {
            throw new MALException("The string length is greater than "
                    + "2^32 -1 bytes! Please provide a shorter string.");
        }

        encodeUInteger(new UInteger(output.length));
        try {
            outputStream.write(output);
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    /**
     * Encodes a long.
     *
     * @param value The value to be encoded.
     * @throws MALException if it cannot be encoded.
     */
    public void encodeMALLong(Long value) throws MALException {
        try {
            outputStream.writeSignedLong(value);
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    /**
     * Encode a nullable identifier.
     *
     * @param value The value to be encoded.
     * @throws MALException if it cannot be encoded.
     */
    @Override
    public void encodeNullableIdentifier(final Identifier value) throws MALException {
        if (value != null) {
            // encode presence flag
            encodeBoolean(true);
            // encode element as String
            encodeIdentifier(value);
        } else {
            // encode presence flag
            encodeBoolean(false);
        }
    }

    /**
     * Encode an identifier.
     *
     * @param value The value to be encoded.
     * @throws MALException if it cannot be encoded.
     */
    @Override
    public void encodeIdentifier(final Identifier value) throws MALException {
        encodeString(value.getValue());
    }

    /**
     * Encode a blob.
     *
     * @param value The value to be encoded.
     * @throws MALException if it cannot be encoded.
     */
    @Override
    public void encodeBlob(final Blob value) throws MALException {
        byte[] byteValue = value.getValue();
        encodeUInteger(new UInteger(byteValue.length));

        if (value.getLength() > 0) {
            try {
                outputStream.write(byteValue);
            } catch (IOException ex) {
                throw new MALException(ENCODING_EXCEPTION_STR, ex);
            }
        }
    }

    public static class TCPIPStreamHolder extends FixedBinaryStreamHolder {

        public TCPIPStreamHolder(OutputStream outputStream) {
            super(outputStream, false);
        }

    }

}
