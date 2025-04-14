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
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.*;

/**
 * Extends the MALEncoder and MALListEncoder interfaces for use in the generic
 * encoding framework.
 */
public abstract class Encoder implements MALEncoder {

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
    public void close() {
        try {
            outputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(Encoder.class.getName()).log(Level.WARNING,
                    "Exception thrown on Encoder.close", ex);
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
                outputStream.writeIsNotNull();
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
                outputStream.writeIsNotNull();
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
                outputStream.writeIsNotNull();
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
                outputStream.writeIsNotNull();
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
                outputStream.writeIsNotNull();
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
                outputStream.writeIsNotNull();
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
                outputStream.writeIsNotNull();
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
                outputStream.writeIsNotNull();
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
                outputStream.writeIsNotNull();
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
            if ((value != null) && (value.getValue() != null)) {
                outputStream.writeIsNotNull();
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
                outputStream.writeIsNotNull();
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
                outputStream.writeIsNotNull();
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
                outputStream.writeIsNotNull();
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
                outputStream.writeIsNotNull();
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
                outputStream.writeIsNotNull();
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
                outputStream.writeIsNotNull();
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
            outputStream.writeDouble(value.getInSeconds());
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeNullableDuration(final Duration value) throws MALException {
        try {
            if (value != null) {
                outputStream.writeIsNotNull();
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
            int length = value.getDomain().size();
            outputStream.writeUnsignedInt(length);

            for (int i = 0; i < length; i++) {
                outputStream.writeString(value.getDomain().get(i).getValue());
            }

            outputStream.writeSignedLong(value.getabsoluteSFP());
            outputStream.writeString(value.getKey().getValue());
            outputStream.writeSignedLong(value.getObjectVersion().getValue());
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeNullableObjectRef(final ObjectRef value) throws MALException {
        try {
            if (value != null) {
                outputStream.writeIsNotNull();
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
                outputStream.writeIsNotNull();
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

            if (value.getTypeId().getSFP() > 20) {
                throw new IOException("The value.getTypeShortForm() is greater than 20");
            }

            byte shortForm = ((Integer) value.getTypeId().getSFP()).byteValue();
            outputStream.writeByte(internalEncodeAttributeType(shortForm));
            value.encode(this);
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeNullableAttribute(final Attribute value) throws MALException {
        try {
            if (value == null) {
                outputStream.writeIsNull();
                return;
            }

            // Union is a special case because it may wrap a null inside it!
            if (value instanceof Union) {
                if (((Union) value).isNull()) {
                    outputStream.writeIsNull();
                } else {
                    outputStream.writeIsNotNull();
                    encodeAttribute(value);
                }
            } else {
                outputStream.writeIsNotNull();
                encodeAttribute(value);
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
                outputStream.writeIsNotNull();
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
        encodeLong(value.getTypeId().getTypeId());
        value.encode(this);
    }

    @Override
    public void encodeNullableAbstractElement(final Element value) throws MALException {
        try {
            if (value != null) {
                outputStream.writeIsNotNull();
                encodeLong(value.getTypeId().getTypeId());
                value.encode(this);
            } else {
                outputStream.writeIsNull();
            }
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeHomogeneousList(final HomogeneousList list) throws MALException {
        try {
            outputStream.writeUnsignedInt(list.size());
            for (int i = 0; i < list.size(); i++) {
                Object obj = list.get(i);
                Element element = (obj instanceof Element) ? (Element) obj : (Element) Attribute.javaType2Attribute(obj);
                element.encode(this);
            }
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
        }
    }

    @Override
    public void encodeHeterogeneousList(HeterogeneousList list) throws MALException {
        try {
            outputStream.writeUnsignedInt(list.size());

            for (int i = 0; i < list.size(); i++) {
                Object entry = list.get(i);
                if (!(entry instanceof Element)) {
                    entry = Attribute.javaType2Attribute(entry);
                }
                if (HeterogeneousList.ENFORCE_NON_NULLABLE_ENTRIES) {
                    encodeAbstractElement((Element) entry);
                } else {
                    encodeNullableAbstractElement((Element) entry);
                }
            }
        } catch (IOException ex) {
            throw new MALException(ENCODING_EXCEPTION_STR, ex);
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
     * @param isNullable If true encode a isNull field
     * @throws MALException if there is an error
     */
    public void encodeAbstractElementSFP(final Long value, boolean isNullable) throws MALException {
        if (isNullable) {
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
        if (value == null) {
            throw new MALException("Null value supplied in a non-nullable field!");
        }
    }
}
