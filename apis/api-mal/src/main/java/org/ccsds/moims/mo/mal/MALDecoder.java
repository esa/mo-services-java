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
package org.ccsds.moims.mo.mal;

import java.util.ArrayList;
import java.util.List;
import org.ccsds.moims.mo.mal.structures.*;

/**
 * Decoding interface, implemented by specific decoding technology.
 */
public interface MALDecoder {

    /**
     * Decodes a Boolean.
     *
     * @return The decoded Boolean.
     * @throws MALException If an error detected during decoding.
     */
    Boolean decodeBoolean() throws MALException;

    /**
     * Decodes a Boolean that may be null.
     *
     * @return The decoded Boolean or null.
     * @throws MALException If an error detected during decoding.
     */
    Boolean decodeNullableBoolean() throws MALException;

    /**
     * Decodes a Float.
     *
     * @return The decoded Float.
     * @throws MALException If an error detected during decoding.
     */
    Float decodeFloat() throws MALException;

    /**
     * Decodes a Float that may be null.
     *
     * @return The decoded Float or null.
     * @throws MALException If an error detected during decoding.
     */
    Float decodeNullableFloat() throws MALException;

    /**
     * Decodes a Double.
     *
     * @return The decoded Double.
     * @throws MALException If an error detected during decoding.
     */
    Double decodeDouble() throws MALException;

    /**
     * Decodes a Double that may be null.
     *
     * @return The decoded Double or null.
     * @throws MALException If an error detected during decoding.
     */
    Double decodeNullableDouble() throws MALException;

    /**
     * Decodes an Octet.
     *
     * @return The decoded Octet.
     * @throws MALException If an error detected during decoding.
     */
    Byte decodeOctet() throws MALException;

    /**
     * Decodes an Octet that may be null.
     *
     * @return The decoded Octet or null.
     * @throws MALException If an error detected during decoding.
     */
    Byte decodeNullableOctet() throws MALException;

    /**
     * Decodes a UOctet.
     *
     * @return The decoded UOctet.
     * @throws MALException If an error detected during decoding.
     */
    UOctet decodeUOctet() throws MALException;

    /**
     * Decodes a UOctet that may be null.
     *
     * @return The decoded UOctet or null.
     * @throws MALException If an error detected during decoding.
     */
    UOctet decodeNullableUOctet() throws MALException;

    /**
     * Decodes a Short.
     *
     * @return The decoded Short.
     * @throws MALException If an error detected during decoding.
     */
    Short decodeShort() throws MALException;

    /**
     * Decodes a Short that may be null.
     *
     * @return The decoded Short or null.
     * @throws MALException If an error detected during decoding.
     */
    Short decodeNullableShort() throws MALException;

    /**
     * Decodes a UShort.
     *
     * @return The decoded UShort.
     * @throws MALException If an error detected during decoding.
     */
    UShort decodeUShort() throws MALException;

    /**
     * Decodes a UShort that may be null.
     *
     * @return The decoded UShort or null.
     * @throws MALException If an error detected during decoding.
     */
    UShort decodeNullableUShort() throws MALException;

    /**
     * Decodes an Integer.
     *
     * @return The decoded Integer.
     * @throws MALException If an error detected during decoding.
     */
    Integer decodeInteger() throws MALException;

    /**
     * Decodes an Integer that may be null.
     *
     * @return The decoded Integer or null.
     * @throws MALException If an error detected during decoding.
     */
    Integer decodeNullableInteger() throws MALException;

    /**
     * Decodes a UInteger.
     *
     * @return The decoded UInteger.
     * @throws MALException If an error detected during decoding.
     */
    UInteger decodeUInteger() throws MALException;

    /**
     * Decodes a UInteger that may be null.
     *
     * @return The decoded UInteger or null.
     * @throws MALException If an error detected during decoding.
     */
    UInteger decodeNullableUInteger() throws MALException;

    /**
     * Decodes a Long.
     *
     * @return The decoded Long.
     * @throws MALException If an error detected during decoding.
     */
    Long decodeLong() throws MALException;

    /**
     * Decodes a Long that may be null.
     *
     * @return The decoded Long or null.
     * @throws MALException If an error detected during decoding.
     */
    Long decodeNullableLong() throws MALException;

    /**
     * Decodes a ULong.
     *
     * @return The decoded ULong.
     * @throws MALException If an error detected during decoding.
     */
    ULong decodeULong() throws MALException;

    /**
     * Decodes a ULong that may be null.
     *
     * @return The decoded ULong or null.
     * @throws MALException If an error detected during decoding.
     */
    ULong decodeNullableULong() throws MALException;

    /**
     * Decodes a String.
     *
     * @return The decoded String.
     * @throws MALException If an error detected during decoding.
     */
    String decodeString() throws MALException;

    /**
     * Decodes a String that may be null.
     *
     * @return The decoded String or null.
     * @throws MALException If an error detected during decoding.
     */
    String decodeNullableString() throws MALException;

    /**
     * Decodes a Blob.
     *
     * @return The decoded Blob.
     * @throws MALException If an error detected during decoding.
     */
    Blob decodeBlob() throws MALException;

    /**
     * Decodes a Blob that may be null.
     *
     * @return The decoded Blob or null.
     * @throws MALException If an error detected during decoding.
     */
    Blob decodeNullableBlob() throws MALException;

    /**
     * Decodes a Duration.
     *
     * @return The decoded Duration.
     * @throws MALException If an error detected during decoding.
     */
    Duration decodeDuration() throws MALException;

    /**
     * Decodes a Duration that may be null.
     *
     * @return The decoded Duration or null.
     * @throws MALException If an error detected during decoding.
     */
    Duration decodeNullableDuration() throws MALException;

    /**
     * Decodes a FineTime.
     *
     * @return The decoded FineTime.
     * @throws MALException If an error detected during decoding.
     */
    FineTime decodeFineTime() throws MALException;

    /**
     * Decodes a FineTime that may be null.
     *
     * @return The decoded FineTime or null.
     * @throws MALException If an error detected during decoding.
     */
    FineTime decodeNullableFineTime() throws MALException;

    /**
     * Decodes an Identifier.
     *
     * @return The decoded Identifier.
     * @throws MALException If an error detected during decoding.
     */
    Identifier decodeIdentifier() throws MALException;

    /**
     * Decodes an Identifier that may be null.
     *
     * @return The decoded Identifier or null.
     * @throws MALException If an error detected during decoding.
     */
    Identifier decodeNullableIdentifier() throws MALException;

    /**
     * Decodes a Time.
     *
     * @return The decoded Time.
     * @throws MALException If an error detected during decoding.
     */
    Time decodeTime() throws MALException;

    /**
     * Decodes a Time that may be null.
     *
     * @return The decoded Time or null.
     * @throws MALException If an error detected during decoding.
     */
    Time decodeNullableTime() throws MALException;

    /**
     * Decodes a URI.
     *
     * @return The decoded URI.
     * @throws MALException If an error detected during decoding.
     */
    URI decodeURI() throws MALException;

    /**
     * Decodes a URI that may be null.
     *
     * @return The decoded URI or null.
     * @throws MALException If an error detected during decoding.
     */
    URI decodeNullableURI() throws MALException;

    /**
     * Decodes an ObjectRef.
     *
     * @return The decoded ObjectRef.
     * @throws MALException If an error detected during decoding.
     */
    ObjectRef decodeObjectRef() throws MALException;

    /**
     * Decodes a ObjectRef that may be null.
     *
     * @return The decoded ObjectRef or null.
     * @throws MALException If an error detected during decoding.
     */
    ObjectRef decodeNullableObjectRef() throws MALException;

    /**
     * Decodes an Element.
     *
     * @param element An instance of the element to decode.
     * @return The decoded Element.
     * @throws IllegalArgumentException If the argument is null.
     * @throws MALException If an error detected during decoding.
     */
    Element decodeElement(Element element) throws IllegalArgumentException, MALException;

    /**
     * Decodes an Element that may be null.
     *
     * @param element An instance of the element to decode.
     * @return The decoded Element or null.
     * @throws IllegalArgumentException If the argument is null.
     * @throws MALException If an error detected during decoding.
     */
    Element decodeNullableElement(Element element) throws java.lang.IllegalArgumentException, MALException;

    /**
     * Decodes an Abstract Element.
     *
     * @return The decoded Element.
     * @throws MALException If an error detected during decoding.
     */
    Element decodeAbstractElement() throws MALException;

    /**
     * Decodes an Abstract Element that may be null.
     *
     * @return The decoded Element or null.
     * @throws MALException If an error detected during decoding.
     */
    Element decodeNullableAbstractElement() throws MALException;

    /**
     * Decodes an Attribute.
     *
     * @return The decoded Attribute.
     * @throws MALException If an error detected during decoding.
     */
    Attribute decodeAttribute() throws MALException;

    /**
     * Decodes an Attribute that may be null.
     *
     * @return The decoded Attribute or null.
     * @throws MALException If an error detected during decoding.
     */
    Attribute decodeNullableAttribute() throws MALException;

    /**
     * Creates a list decoder for decoding a list element.
     *
     * @param list The list to decode, java.lang.IllegalArgumentException
     * exception thrown if null.
     * @return The new list decoder.
     * @throws java.lang.IllegalArgumentException If the list argument is null.
     * @throws MALException If an error detected during list decoder creation.
     */
    MALListDecoder createListDecoder(List list) throws java.lang.IllegalArgumentException, MALException;

    /**
     * Decodes an Homogeneous list
     *
     * @param list The list to decode.
     * @return The decoded list.
     * @throws MALException If an error detected during decoding.
     */
    HomogeneousList decodeHomogeneousList(HomogeneousList list) throws MALException;
}
