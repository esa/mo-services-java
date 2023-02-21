/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java API
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
package org.ccsds.moims.mo.mal.encoding;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALListEncoder;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.ULong;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * Extends the MALEncoder and MALListEncoder interfaces for use in the generic
 * encoding framework.
 */
public abstract class Encoder implements MALListEncoder {

    protected static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    protected static final String ENCODING_EXCEPTION_STR = "Bad encoding";
    protected final StreamHolder outputStream;

    /**
     * Constructor for derived classes that have their own stream holder
     * implementation that should be used.
     *
     * @param os Output stream to write to.
     */
    protected Encoder(final StreamHolder os) {
        this.outputStream = os;
    }

    @Override
    public MALListEncoder createListEncoder(final java.util.List value) throws MALException {
        try {
            outputStream.writeUnsignedInt(value.size());
            return this;
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeDouble(final Double value) throws MALException {
        try {
            checkForNull(value);
            outputStream.writeDouble(value);
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeNullableDouble(final Double value) throws MALException {
        try {
            if (value != null) {
                outputStream.writeNotNull();
                encodeDouble(value);
            } else {
                outputStream.writeIsNull();
            }
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeInteger(final Integer value) throws MALException {
        try {
            checkForNull(value);
            outputStream.writeSignedInt(value);
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeNullableInteger(final Integer value) throws MALException {
        try {
            if (value != null) {
                outputStream.writeNotNull();
                encodeInteger(value);
            } else {
                outputStream.writeIsNull();
            }
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeLong(final Long value) throws MALException {
        try {
            checkForNull(value);
            outputStream.writeSignedLong(value);
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeNullableLong(final Long value) throws MALException {
        try {
            if (value != null) {
                outputStream.writeNotNull();
                encodeLong(value);
            } else {
                outputStream.writeIsNull();
            }
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeOctet(final Byte value) throws MALException {
        try {
            checkForNull(value);
            outputStream.writeByte(value);
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeNullableOctet(final Byte value) throws MALException {
        try {
            if (value != null) {
                outputStream.writeNotNull();
                encodeOctet(value);
            } else {
                outputStream.writeIsNull();
            }
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeShort(final Short value) throws MALException {
        try {
            checkForNull(value);
            outputStream.writeSignedShort(value);
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeNullableShort(final Short value) throws MALException {
        try {
            if (value != null) {
                outputStream.writeNotNull();
                encodeShort(value);
            } else {
                outputStream.writeIsNull();
            }
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeUInteger(final UInteger value) throws MALException {
        try {
            checkForNull(value);
            outputStream.writeUnsignedLong32(value.getValue());
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeNullableUInteger(final UInteger value) throws MALException {
        try {
            if (value != null) {
                outputStream.writeNotNull();
                encodeUInteger(value);
            } else {
                outputStream.writeIsNull();
            }
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeULong(final ULong value) throws MALException {
        try {
            checkForNull(value);
            outputStream.writeBigInteger(value.getValue());
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeNullableULong(final ULong value) throws MALException {
        try {
            if (value != null) {
                outputStream.writeNotNull();
                encodeULong(value);
            } else {
                outputStream.writeIsNull();
            }
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeUOctet(final UOctet value) throws MALException {
        try {
            checkForNull(value);
            outputStream.writeUnsignedShort8(value.getValue());
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeNullableUOctet(final UOctet value) throws MALException {
        try {
            if (value != null) {
                outputStream.writeNotNull();
                encodeUOctet(value);
            } else {
                outputStream.writeIsNull();
            }
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeUShort(final UShort value) throws MALException {
        try {
            checkForNull(value);
            outputStream.writeUnsignedInt16(value.getValue());
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeNullableUShort(final UShort value) throws MALException {
        try {
            if (value != null) {
                outputStream.writeNotNull();
                encodeUShort(value);
            } else {
                outputStream.writeIsNull();
            }
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeURI(final URI value) throws MALException {
        try {
            checkForNull(value);
            checkForNull(value.getValue());
            outputStream.writeString(value.getValue());
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeNullableURI(final URI value) throws MALException {
        try {
            if ((value != null) && (null != value.getValue())) {
                outputStream.writeNotNull();
                encodeURI(value);
            } else {
                outputStream.writeIsNull();
            }
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeIdentifier(final Identifier value) throws MALException {
        try {
            checkForNull(value);
            checkForNull(value.getValue());
            outputStream.writeString(value.getValue());
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeNullableIdentifier(final Identifier value) throws MALException {
        try {
            if ((value != null) && (null != value.getValue())) {
                outputStream.writeNotNull();
                encodeIdentifier(value);
            } else {
                outputStream.writeIsNull();
            }
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeString(final String value) throws MALException {
        try {
            checkForNull(value);
            outputStream.writeString(value);
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeNullableString(final String value) throws MALException {
        try {
            if (value != null) {
                outputStream.writeNotNull();
                encodeString(value);
            } else {
                outputStream.writeIsNull();
            }
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeBoolean(final Boolean value) throws MALException {
        try {
            checkForNull(value);
            outputStream.writeBool(value);
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeNullableBoolean(final Boolean value) throws MALException {
        try {
            if (value != null) {
                outputStream.writeNotNull();
                encodeBoolean(value);
            } else {
                outputStream.writeIsNull();
            }
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeTime(final Time value) throws MALException {
        try {
            checkForNull(value);
            outputStream.writeUnsignedLong(value.getValue());
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeNullableTime(final Time value) throws MALException {
        try {
            if (value != null) {
                outputStream.writeNotNull();
                encodeTime(value);
            } else {
                outputStream.writeIsNull();
            }
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeFineTime(final FineTime value) throws MALException {
        try {
            checkForNull(value);
            outputStream.writeUnsignedLong(value.getValue());
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeNullableFineTime(final FineTime value) throws MALException {
        try {
            if (value != null) {
                outputStream.writeNotNull();
                encodeFineTime(value);
            } else {
                outputStream.writeIsNull();
            }
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeBlob(final Blob value) throws MALException {
        try {
            checkForNull(value);
            if (value.isURLBased()) {
                checkForNull(value.getURL());
            } else {
                checkForNull(value.getValue());
            }
            outputStream.writeBytes(value.getValue());
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeNullableBlob(final Blob value) throws MALException {
        try {
            if ((value != null)
                    && ((value.isURLBased() && (null != value.getURL()))
                    || (!value.isURLBased() && (null != value.getValue())))) {
                outputStream.writeNotNull();
                encodeBlob(value);
            } else {
                outputStream.writeIsNull();
            }
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeDuration(final Duration value) throws MALException {
        try {
            checkForNull(value);
            outputStream.writeDouble(value.getValue());
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeNullableDuration(final Duration value) throws MALException {
        try {
            if (value != null) {
                outputStream.writeNotNull();
                encodeDuration(value);
            } else {
                outputStream.writeIsNull();
            }
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeObjectRef(final ObjectRef value) throws IllegalArgumentException, MALException {
        try {
            checkForNull(value);
            outputStream.writeString(value.getDomain());
            outputStream.writeString(value.getArea().getValue());
            outputStream.writeString(value.getType().getValue());
            outputStream.writeString(value.getKey().getValue());
            outputStream.writeUnsignedLong32(value.getObjectVersion().getValue());
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeNullableObjectRef(final ObjectRef value) throws MALException {
        try {
            if (value != null) {
                outputStream.writeNotNull();
                encodeObjectRef(value);
            } else {
                outputStream.writeIsNull();
            }
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeFloat(final Float value) throws MALException {
        try {
            checkForNull(value);
            outputStream.writeFloat(value);
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeNullableFloat(final Float value) throws MALException {
        try {
            if (value != null) {
                outputStream.writeNotNull();
                encodeFloat(value);
            } else {
                outputStream.writeIsNull();
            }
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeAttribute(final Attribute value) throws MALException {
        try {
            checkForNull(value);
            outputStream.writeByte(internalEncodeAttributeType(value.getTypeShortForm().byteValue()));
            value.encode(this);
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeNullableAttribute(final Attribute value) throws MALException {
        try {
            if (value != null) {
                outputStream.writeNotNull();
                encodeAttribute(value);
            } else {
                outputStream.writeIsNull();
            }
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeElement(final Element value) throws MALException {
        checkForNull(value);
        value.encode(this);
    }

    @Override
    public void encodeNullableElement(final Element value) throws MALException {
        try {
            if (value != null) {
                outputStream.writeNotNull();
                value.encode(this);
            } else {
                outputStream.writeIsNull();
            }
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeAbstractElement(final Element value) throws MALException {
        encodeLong(value.getShortForm());
        value.encode(this);
    }

    @Override
    public void encodeNullableAbstractElement(final Element value) throws MALException {
        try {
            if (value != null) {
                outputStream.writeNotNull();
                encodeLong(value.getShortForm());
                value.encode(this);
            } else {
                outputStream.writeIsNull();
            }
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void close() {
        try {
            outputStream.close();
        } catch (IOException ex) {
            Logger.getLogger("org.ccsds.moims.mo.mal.encoding.gen").log(
                    Level.WARNING, "Exception thrown on Encoder.close", ex);
        }
    }

    /**
     * Allows the encoding of a byte array, usually for already encoded values
     *
     * @param value The type to encode
     * @throws MALException if there is an error
     */
    public void directEncodeBytes(final byte[] value) throws MALException {
        try {
            outputStream.write(value);
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    /**
     * Allows the encoding for the type of an abstract element to be over-ridded
     *
     * @param value The type to encode
     * @param withNull If true encode a isNull field
     * @throws MALException if there is an error
     */
    public void encodeAbstractElementType(final Long value, boolean withNull) throws MALException {
        if (withNull) {
            encodeNullableLong(value);
        } else {
            encodeLong(value);
        }
    }

    /**
     * Converts the MAL representation of an Attribute type short form to the
     * representation used by the encoding.
     *
     * @param value The Attribute type short form.
     * @return The byte value used by the encoding
     * @throws MALException On error.
     */
    public byte internalEncodeAttributeType(byte value) throws MALException {
        return value;
    }

    /**
     * Throws a MALException when supplied with a NULL value
     *
     * @param value The value to check
     * @throws MALException if value is NULL
     */
    protected void checkForNull(Object value) throws MALException {
        if (null == value) {
            throw new MALException("Null value supplied in a non-nullable field");
        }
    }
}
