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

import java.util.List;
import org.ccsds.moims.mo.mal.structures.*;

/**
 * Encoding interface, implemented by specific encoding technology.
 */
public interface MALEncoder {

    /**
     * Encodes a non-null Boolean.
     *
     * @param att The Boolean to encode.
     * @throws IllegalArgumentException If the argument is null.
     * @throws MALException If an error detected during encoding.
     */
    void encodeBoolean(Boolean att) throws IllegalArgumentException, MALException;

    /**
     * Encodes a Boolean that may be null
     *
     * @param att The Boolean to encode.
     * @throws MALException If an error detected during encoding.
     */
    void encodeNullableBoolean(Boolean att) throws MALException;

    /**
     * Encodes a non-null Float.
     *
     * @param att The Float to encode.
     * @throws IllegalArgumentException If the argument is null.
     * @throws MALException If an error detected during encoding.
     */
    void encodeFloat(Float att) throws IllegalArgumentException, MALException;

    /**
     * Encodes a Float that may be null
     *
     * @param att The Float to encode.
     * @throws MALException If an error detected during encoding.
     */
    void encodeNullableFloat(Float att) throws MALException;

    /**
     * Encodes a non-null Double.
     *
     * @param att The Double to encode.
     * @throws IllegalArgumentException If the argument is null.
     * @throws MALException If an error detected during encoding.
     */
    void encodeDouble(Double att) throws IllegalArgumentException, MALException;

    /**
     * Encodes a Double that may be null
     *
     * @param att The Double to encode.
     * @throws MALException If an error detected during encoding.
     */
    void encodeNullableDouble(Double att) throws MALException;

    /**
     * Encodes a non-null Octet.
     *
     * @param att The Octet to encode.
     * @throws IllegalArgumentException If the argument is null.
     * @throws MALException If an error detected during encoding.
     */
    void encodeOctet(Byte att) throws IllegalArgumentException, MALException;

    /**
     * Encodes an Octet that may be null
     *
     * @param att The Octet to encode.
     * @throws MALException If an error detected during encoding.
     */
    void encodeNullableOctet(Byte att) throws MALException;

    /**
     * Encodes a non-null UOctet.
     *
     * @param att The UOctet to encode.
     * @throws IllegalArgumentException If the argument is null.
     * @throws MALException If an error detected during encoding.
     */
    void encodeUOctet(UOctet att) throws IllegalArgumentException, MALException;

    /**
     * Encodes a UOctet that may be null
     *
     * @param att The UOctet to encode.
     * @throws MALException If an error detected during encoding.
     */
    void encodeNullableUOctet(UOctet att) throws MALException;

    /**
     * Encodes a non-null Short.
     *
     * @param att The Short to encode.
     * @throws IllegalArgumentException If the argument is null.
     * @throws MALException If an error detected during encoding.
     */
    void encodeShort(Short att) throws IllegalArgumentException, MALException;

    /**
     * Encodes a Short that may be null
     *
     * @param att The Short to encode.
     * @throws MALException If an error detected during encoding.
     */
    void encodeNullableShort(Short att) throws MALException;

    /**
     * Encodes a non-null UShort.
     *
     * @param att The UShort to encode.
     * @throws IllegalArgumentException If the argument is null.
     * @throws MALException If an error detected during encoding.
     */
    void encodeUShort(UShort att) throws IllegalArgumentException, MALException;

    /**
     * Encodes a UShort that may be null
     *
     * @param att The UShort to encode.
     * @throws MALException If an error detected during encoding.
     */
    void encodeNullableUShort(UShort att) throws MALException;

    /**
     * Encodes a non-null Integer.
     *
     * @param att The Integer to encode.
     * @throws IllegalArgumentException If the argument is null.
     * @throws MALException If an error detected during encoding.
     */
    void encodeInteger(Integer att) throws IllegalArgumentException, MALException;

    /**
     * Encodes an Integer that may be null
     *
     * @param att The Integer to encode.
     * @throws MALException If an error detected during encoding.
     */
    void encodeNullableInteger(Integer att) throws MALException;

    /**
     * Encodes a non-null UInteger.
     *
     * @param att The UInteger to encode.
     * @throws IllegalArgumentException If the argument is null.
     * @throws MALException If an error detected during encoding.
     */
    void encodeUInteger(UInteger att) throws IllegalArgumentException, MALException;

    /**
     * Encodes a UInteger that may be null
     *
     * @param att The UInteger to encode.
     * @throws MALException If an error detected during encoding.
     */
    void encodeNullableUInteger(UInteger att) throws MALException;

    /**
     * Encodes a non-null Long.
     *
     * @param att The Long to encode.
     * @throws IllegalArgumentException If the argument is null.
     * @throws MALException If an error detected during encoding.
     */
    void encodeLong(Long att) throws IllegalArgumentException, MALException;

    /**
     * Encodes a Long that may be null
     *
     * @param att The Long to encode.
     * @throws MALException If an error detected during encoding.
     */
    void encodeNullableLong(Long att) throws MALException;

    /**
     * Encodes a non-null ULong.
     *
     * @param att The ULong to encode.
     * @throws IllegalArgumentException If the argument is null.
     * @throws MALException If an error detected during encoding.
     */
    void encodeULong(ULong att) throws IllegalArgumentException, MALException;

    /**
     * Encodes a ULong that may be null
     *
     * @param att The ULong to encode.
     * @throws MALException If an error detected during encoding.
     */
    void encodeNullableULong(ULong att) throws MALException;

    /**
     * Encodes a non-null String.
     *
     * @param att The String to encode.
     * @throws IllegalArgumentException If the argument is null.
     * @throws MALException If an error detected during encoding.
     */
    void encodeString(String att) throws IllegalArgumentException, MALException;

    /**
     * Encodes a String that may be null
     *
     * @param att The String to encode.
     * @throws MALException If an error detected during encoding.
     */
    void encodeNullableString(String att) throws MALException;

    /**
     * Encodes a non-null Blob.
     *
     * @param att The Blob to encode.
     * @throws IllegalArgumentException If the argument is null.
     * @throws MALException If an error detected during encoding.
     */
    void encodeBlob(Blob att) throws IllegalArgumentException, MALException;

    /**
     * Encodes a Blob that may be null
     *
     * @param att The Blob to encode.
     * @throws MALException If an error detected during encoding.
     */
    void encodeNullableBlob(Blob att) throws MALException;

    /**
     * Encodes a non-null Duration.
     *
     * @param att The Duration to encode.
     * @throws IllegalArgumentException If the argument is null.
     * @throws MALException If an error detected during encoding.
     */
    void encodeDuration(Duration att) throws IllegalArgumentException, MALException;

    /**
     * Encodes a Duration that may be null
     *
     * @param att The Duration to encode.
     * @throws MALException If an error detected during encoding.
     */
    void encodeNullableDuration(Duration att) throws MALException;

    /**
     * Encodes a non-null FineTime.
     *
     * @param att The FineTime to encode.
     * @throws IllegalArgumentException If the argument is null.
     * @throws MALException If an error detected during encoding.
     */
    void encodeFineTime(FineTime att) throws IllegalArgumentException, MALException;

    /**
     * Encodes a FineTime that may be null
     *
     * @param att The FineTime to encode.
     * @throws MALException If an error detected during encoding.
     */
    void encodeNullableFineTime(FineTime att) throws MALException;

    /**
     * Encodes a non-null Identifier.
     *
     * @param att The Identifier to encode.
     * @throws IllegalArgumentException If the argument is null.
     * @throws MALException If an error detected during encoding.
     */
    void encodeIdentifier(Identifier att) throws IllegalArgumentException, MALException;

    /**
     * Encodes an Identifier that may be null
     *
     * @param att The Identifier to encode.
     * @throws MALException If an error detected during encoding.
     */
    void encodeNullableIdentifier(Identifier att) throws MALException;

    /**
     * Encodes a non-null Time.
     *
     * @param att The Time to encode.
     * @throws IllegalArgumentException If the argument is null.
     * @throws MALException If an error detected during encoding.
     */
    void encodeTime(Time att) throws IllegalArgumentException, MALException;

    /**
     * Encodes a Time that may be null
     *
     * @param att The Time to encode.
     * @throws MALException If an error detected during encoding.
     */
    void encodeNullableTime(Time att) throws MALException;

    /**
     * Encodes a non-null URI.
     *
     * @param att The URI to encode.
     * @throws IllegalArgumentException If the argument is null.
     * @throws MALException If an error detected during encoding.
     */
    void encodeURI(URI att) throws IllegalArgumentException, MALException;

    /**
     * Encodes a URI that may be null
     *
     * @param att The URI to encode.
     * @throws MALException If an error detected during encoding.
     */
    void encodeNullableURI(URI att) throws MALException;

    /**
     * Encodes a non-null ObjectRef.
     *
     * @param att The ObjectRef to encode.
     * @throws IllegalArgumentException If the argument is null.
     * @throws MALException If an error detected during encoding.
     */
    void encodeObjectRef(ObjectRef att) throws IllegalArgumentException, MALException;

    /**
     * Encodes an ObjectRef that may be null
     *
     * @param att The ObjectRef to encode.
     * @throws MALException If an error detected during encoding.
     */
    void encodeNullableObjectRef(ObjectRef att) throws MALException;

    /**
     * Encodes a non-null Element.
     *
     * @param element The Element to encode.
     * @throws IllegalArgumentException If the argument is null.
     * @throws MALException If an error detected during encoding.
     */
    void encodeElement(Element element) throws IllegalArgumentException, MALException;

    /**
     * Encodes an Element that may be null
     *
     * @param element The Element to encode.
     * @throws MALException If an error detected during encoding.
     */
    void encodeNullableElement(Element element) throws MALException;

    /**
     * Encodes a non-null Attribute.
     *
     * @param att The Attribute to encode.
     * @throws IllegalArgumentException If the argument is null.
     * @throws MALException If an error detected during encoding.
     */
    void encodeAttribute(Attribute att) throws IllegalArgumentException, MALException;

    /**
     * Encodes an Attribute that may be null
     *
     * @param att The Attribute to encode.
     * @throws MALException If an error detected during encoding.
     */
    void encodeNullableAttribute(Attribute att) throws MALException;

    /**
     * Creates a list encoder for encoding a list element.
     *
     * @param list The list to encode, java.lang.IllegalArgumentException
     * exception thrown if null.
     * @return The new list encoder.
     * @throws java.lang.IllegalArgumentException If the list argument is null.
     * @throws MALException If an error detected during list encoder creation.
     */
    MALListEncoder createListEncoder(List list) throws IllegalArgumentException, MALException;
}
