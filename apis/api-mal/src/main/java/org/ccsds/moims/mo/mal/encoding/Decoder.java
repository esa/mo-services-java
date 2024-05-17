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

import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;
import org.ccsds.moims.mo.mal.structures.HomogeneousList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.ULong;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.Union;

/**
 * The Decoder class can be extended to create fully functional decoders.
 */
public abstract class Decoder implements MALDecoder {

    protected final BufferHolder sourceBuffer;

    protected Decoder(BufferHolder sourceBuffer) {
        this.sourceBuffer = sourceBuffer;
    }

    @Override
    public Blob decodeBlob() throws MALException {
        return new Blob(sourceBuffer.readBytes());
    }

    @Override
    public Boolean decodeBoolean() throws MALException {
        return sourceBuffer.readBool();
    }

    @Override
    public Identifier decodeIdentifier() throws MALException {
        return new Identifier(sourceBuffer.readString());
    }

    @Override
    public URI decodeURI() throws MALException {
        return new URI(sourceBuffer.readString());
    }

    @Override
    public String decodeString() throws MALException {
        return sourceBuffer.readString();
    }

    @Override
    public Integer decodeInteger() throws MALException {
        return sourceBuffer.readSignedInt();
    }

    @Override
    public Time decodeTime() throws MALException {
        return new Time(sourceBuffer.readUnsignedLong());
    }

    @Override
    public FineTime decodeFineTime() throws MALException {
        return new FineTime(sourceBuffer.readUnsignedLong());
    }

    @Override
    public Duration decodeDuration() throws MALException {
        return new Duration(sourceBuffer.readDouble());
    }

    @Override
    public Long decodeLong() throws MALException {
        return sourceBuffer.readSignedLong();
    }

    @Override
    public Byte decodeOctet() throws MALException {
        return sourceBuffer.read8();
    }

    @Override
    public Short decodeShort() throws MALException {
        return sourceBuffer.readSignedShort();
    }

    @Override
    public ULong decodeULong() throws MALException {
        return new ULong(sourceBuffer.readBigInteger());
    }

    @Override
    public UInteger decodeUInteger() throws MALException {
        return new UInteger(sourceBuffer.readUnsignedLong32());
    }

    @Override
    public UOctet decodeUOctet() throws MALException {
        return new UOctet(sourceBuffer.readUnsignedShort8());
    }

    @Override
    public UShort decodeUShort() throws MALException {
        return new UShort(sourceBuffer.readUnsignedInt16());
    }

    @Override
    public Float decodeFloat() throws MALException {
        return sourceBuffer.readFloat();
    }

    @Override
    public Double decodeDouble() throws MALException {
        return sourceBuffer.readDouble();
    }

    @Override
    public ObjectRef decodeObjectRef() throws MALException {
        IdentifierList decodedDomain = new IdentifierList();
        int length = sourceBuffer.readUnsignedInt();

        for (int i = 0; i < length; i++) {
            decodedDomain.add(new Identifier(sourceBuffer.readString()));
        }

        return new ObjectRef(decodedDomain,
                sourceBuffer.readSignedLong(),
                new Identifier(sourceBuffer.readString()),
                new UInteger(sourceBuffer.readSignedLong())
        );
    }

    @Override
    public String decodeNullableString() throws MALException {
        if (sourceBuffer.readIsNotNull()) {
            return sourceBuffer.readString();
        }

        return null;
    }

    @Override
    public Identifier decodeNullableIdentifier() throws MALException {
        if (sourceBuffer.readIsNotNull()) {
            return decodeIdentifier();
        }

        return null;
    }

    @Override
    public URI decodeNullableURI() throws MALException {
        if (sourceBuffer.readIsNotNull()) {
            return decodeURI();
        }

        return null;
    }

    @Override
    public Blob decodeNullableBlob() throws MALException {
        if (sourceBuffer.readIsNotNull()) {
            return decodeBlob();
        }

        return null;
    }

    @Override
    public Boolean decodeNullableBoolean() throws MALException {
        if (sourceBuffer.readIsNotNull()) {
            return decodeBoolean();
        }

        return null;
    }

    @Override
    public Time decodeNullableTime() throws MALException {
        if (sourceBuffer.readIsNotNull()) {
            return decodeTime();
        }

        return null;
    }

    @Override
    public FineTime decodeNullableFineTime() throws MALException {
        if (sourceBuffer.readIsNotNull()) {
            return decodeFineTime();
        }

        return null;
    }

    @Override
    public Duration decodeNullableDuration() throws MALException {
        if (sourceBuffer.readIsNotNull()) {
            return decodeDuration();
        }

        return null;
    }

    @Override
    public Float decodeNullableFloat() throws MALException {
        if (sourceBuffer.readIsNotNull()) {
            return decodeFloat();
        }

        return null;
    }

    @Override
    public Double decodeNullableDouble() throws MALException {
        if (sourceBuffer.readIsNotNull()) {
            return decodeDouble();
        }

        return null;
    }

    @Override
    public Long decodeNullableLong() throws MALException {
        if (sourceBuffer.readIsNotNull()) {
            return decodeLong();
        }

        return null;
    }

    @Override
    public Integer decodeNullableInteger() throws MALException {
        if (sourceBuffer.readIsNotNull()) {
            return decodeInteger();
        }

        return null;
    }

    @Override
    public Short decodeNullableShort() throws MALException {
        if (sourceBuffer.readIsNotNull()) {
            return decodeShort();
        }

        return null;
    }

    @Override
    public Byte decodeNullableOctet() throws MALException {
        if (sourceBuffer.readIsNotNull()) {
            return decodeOctet();
        }

        return null;
    }

    @Override
    public ULong decodeNullableULong() throws MALException {
        if (sourceBuffer.readIsNotNull()) {
            return decodeULong();
        }

        return null;
    }

    @Override
    public UInteger decodeNullableUInteger() throws MALException {
        if (sourceBuffer.readIsNotNull()) {
            return decodeUInteger();
        }

        return null;
    }

    @Override
    public UShort decodeNullableUShort() throws MALException {
        if (sourceBuffer.readIsNotNull()) {
            return decodeUShort();
        }

        return null;
    }

    @Override
    public UOctet decodeNullableUOctet() throws MALException {
        if (sourceBuffer.readIsNotNull()) {
            return decodeUOctet();
        }

        return null;
    }

    @Override
    public Attribute decodeAttribute() throws MALException {
        byte myByte = sourceBuffer.read8();
        int attributeType = internalDecodeAttributeType(myByte);
        return internalDecodeAttribute(attributeType);
    }

    @Override
    public Attribute decodeNullableAttribute() throws MALException {
        if (sourceBuffer.readIsNotNull()) {
            return decodeAttribute();
        }

        return null;
    }

    @Override
    public ObjectRef decodeNullableObjectRef() throws MALException {
        if (sourceBuffer.readIsNotNull()) {
            return decodeObjectRef();
        }

        return null;
    }

    protected Attribute internalDecodeAttribute(final int typeval) throws MALException {
        switch (typeval) {
            case Attribute._BLOB_TYPE_SHORT_FORM:
                return decodeBlob();
            case Attribute._BOOLEAN_TYPE_SHORT_FORM:
                return new Union(decodeBoolean());
            case Attribute._DURATION_TYPE_SHORT_FORM:
                return decodeDuration();
            case Attribute._FLOAT_TYPE_SHORT_FORM:
                return new Union(decodeFloat());
            case Attribute._DOUBLE_TYPE_SHORT_FORM:
                return new Union(decodeDouble());
            case Attribute._IDENTIFIER_TYPE_SHORT_FORM:
                return decodeIdentifier();
            case Attribute._OCTET_TYPE_SHORT_FORM:
                return new Union(decodeOctet());
            case Attribute._UOCTET_TYPE_SHORT_FORM:
                return decodeUOctet();
            case Attribute._SHORT_TYPE_SHORT_FORM:
                return new Union(decodeShort());
            case Attribute._USHORT_TYPE_SHORT_FORM:
                return decodeUShort();
            case Attribute._INTEGER_TYPE_SHORT_FORM:
                return new Union(decodeInteger());
            case Attribute._UINTEGER_TYPE_SHORT_FORM:
                return decodeUInteger();
            case Attribute._LONG_TYPE_SHORT_FORM:
                return new Union(decodeLong());
            case Attribute._ULONG_TYPE_SHORT_FORM:
                return decodeULong();
            case Attribute._STRING_TYPE_SHORT_FORM:
                return new Union(decodeString());
            case Attribute._TIME_TYPE_SHORT_FORM:
                return decodeTime();
            case Attribute._FINETIME_TYPE_SHORT_FORM:
                return decodeFineTime();
            case Attribute._URI_TYPE_SHORT_FORM:
                return decodeURI();
            case Attribute._OBJECTREF_TYPE_SHORT_FORM:
                return decodeObjectRef();
            default:
                throw new MALException("Unknown attribute type received: " + typeval);
        }
    }

    @Override
    public Element decodeElement(final Element element) throws IllegalArgumentException, MALException {
        return element.decode(this);
    }

    @Override
    public Element decodeNullableElement(final Element element) throws MALException {
        if (sourceBuffer.readIsNotNull()) {
            return element.decode(this);
        }

        return null;
    }

    @Override
    public Element decodeAbstractElement() throws MALException {
        Long sfp = decodeLong();
        try {
            Element type = MALContextFactory.getElementsRegistry().createElement(sfp);
            return type.decode(this);
        } catch (Exception ex) {
            throw new MALException("The Element could not be created!", ex);
        }
    }

    @Override
    public Element decodeNullableAbstractElement() throws MALException {
        if (sourceBuffer.readIsNotNull()) {
            Long sfp = decodeLong();
            try {
                Element type = MALContextFactory.getElementsRegistry().createElement(sfp);
                return type.decode(this);
            } catch (Exception ex) {
                throw new MALException("The Element could not be created!", ex);
            }
        }

        return null;
    }

    @Override
    public HomogeneousList decodeHomogeneousList(HomogeneousList list) throws MALException {
        UInteger size = decodeUInteger();
        long decodedSize = size.getValue();

        if (decodedSize > 1000000) {
            throw new org.ccsds.moims.mo.mal.MALException("The decoded list size is too big: " + decodedSize);
        }

        for (int i = 0; i < decodedSize; i++) {
            Element element = list.createTypedElement();

            if (element instanceof Union) {
                // Case for Attributes that are mapped to the Java API
                Union union = (Union) element;
                Attribute att = internalDecodeAttribute(union.getTypeShortForm());
                list.add(Attribute.attribute2JavaType(att));
            } else {
                // Normal Case
                list.add(element.decode(this));
            }
        }
        return list;
    }

    @Override
    public HeterogeneousList decodeHeterogeneousList(HeterogeneousList list) throws MALException {
        UInteger size = decodeUInteger();
        long decodedSize = size.getValue();

        for (int i = 0; i < decodedSize; i++) {
            if (HeterogeneousList.ENFORCE_NON_NULLABLE_ENTRIES) {
                list.add((Element) decodeAbstractElement());
            } else {
                list.add((Element) decodeNullableAbstractElement());
            }
        }
        return list;
    }

    /**
     * Allows the decoding for the type of an abstract element to be over-ridded
     *
     * @param isNullable If true encode a isNull field
     * @return The type to decode
     * @throws MALException if there is an error
     */
    @Override
    public Long decodeAbstractElementSFP(boolean isNullable) throws MALException {
        if (isNullable) {
            return decodeNullableLong();
        }

        return decodeLong();
    }

    public int internalDecodeAttributeType(byte value) throws MALException {
        return value;
    }

    /**
     * Returns the remaining data of the input stream that has not been used for
     * decoding for wrapping in a MALEncodedBody class.
     *
     * @return the unused body data.
     * @throws MALException if there is an error.
     */
    public abstract byte[] getRemainingEncodedData() throws MALException;

}
