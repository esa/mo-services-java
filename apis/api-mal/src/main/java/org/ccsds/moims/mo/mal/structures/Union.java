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
package org.ccsds.moims.mo.mal.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;

/**
 * The Union class assigns a MAL Attribute mapped to a non-Element Java type
 * (e.g. Boolean, String) to an Element variable.
 */
public class Union implements Attribute {

    private final Object value;
    private final Integer typeShortForm;
    private final Long shortForm;

    /**
     * Constructor for a Boolean type.
     *
     * @param value The value to initialise with.
     * @throws java.lang.IllegalArgumentException If the parameter ‘value’ is
     * NULL.
     */
    public Union(final Boolean value) throws java.lang.IllegalArgumentException {
        this.value = value;
        shortForm = Attribute.BOOLEAN_SHORT_FORM;
        typeShortForm = Attribute.BOOLEAN_TYPE_SHORT_FORM;
    }

    /**
     * Constructor for a Float type.
     *
     * @param value The value to initialise with.
     * @throws java.lang.IllegalArgumentException If the parameter ‘value’ is
     * NULL.
     */
    public Union(final Float value) throws java.lang.IllegalArgumentException {
        this.value = value;
        shortForm = FLOAT_SHORT_FORM;
        typeShortForm = Attribute.FLOAT_TYPE_SHORT_FORM;
    }

    /**
     * Constructor for a Double type.
     *
     * @param value The value to initialise with.
     * @throws java.lang.IllegalArgumentException If the parameter ‘value’ is
     * NULL.
     */
    public Union(final Double value) throws java.lang.IllegalArgumentException {
        this.value = value;
        shortForm = DOUBLE_SHORT_FORM;
        typeShortForm = Attribute.DOUBLE_TYPE_SHORT_FORM;
    }

    /**
     * Constructor for an Octet type.
     *
     * @param value The value to initialise with.
     * @throws java.lang.IllegalArgumentException If the parameter ‘value’ is
     * NULL.
     */
    public Union(final Byte value) throws java.lang.IllegalArgumentException {
        this.value = value;
        shortForm = OCTET_SHORT_FORM;
        typeShortForm = Attribute.OCTET_TYPE_SHORT_FORM;
    }

    /**
     * Constructor for a Short type.
     *
     * @param value The value to initialise with.
     * @throws java.lang.IllegalArgumentException If the parameter ‘value’ is
     * NULL.
     */
    public Union(final Short value) throws java.lang.IllegalArgumentException {
        this.value = value;
        shortForm = SHORT_SHORT_FORM;
        typeShortForm = Attribute.SHORT_TYPE_SHORT_FORM;
    }

    /**
     * Constructor for an Integer type.
     *
     * @param value The value to initialise with.
     * @throws java.lang.IllegalArgumentException If the parameter ‘value’ is
     * NULL.
     */
    public Union(final Integer value) throws java.lang.IllegalArgumentException {
        this.value = value;
        shortForm = INTEGER_SHORT_FORM;
        typeShortForm = Attribute.INTEGER_TYPE_SHORT_FORM;
    }

    /**
     * Constructor for a Long type.
     *
     * @param value The value to initialise with.
     * @throws java.lang.IllegalArgumentException If the parameter ‘value’ is
     * NULL.
     */
    public Union(final Long value) throws java.lang.IllegalArgumentException {
        this.value = value;
        shortForm = LONG_SHORT_FORM;
        typeShortForm = Attribute.LONG_TYPE_SHORT_FORM;
    }

    /**
     * Constructor for a String type.
     *
     * @param value The value to initialise with.
     * @throws java.lang.IllegalArgumentException If the parameter ‘value’ is
     * NULL.
     */
    public Union(final String value) throws java.lang.IllegalArgumentException {
        this.value = value;
        shortForm = STRING_SHORT_FORM;
        typeShortForm = Attribute.STRING_TYPE_SHORT_FORM;
    }

    /**
     * Constructor using a type short forms.
     *
     * @param typeShortForm The absolute type short form to initialise with.
     * @param shortForm The relative type short form to initialise with.
     */
    protected Union(final Integer typeShortForm, final Long shortForm) {
        this.value = null;
        this.shortForm = shortForm;
        this.typeShortForm = typeShortForm;
    }

    @Override
    public Element createElement() {
        return new Union(typeShortForm, shortForm);
    }

    /**
     * Returns the value of this type if it represents a Boolean.
     *
     * @return The value.
     */
    public Boolean getBooleanValue() {
        return (Boolean) value;
    }

    /**
     * Returns the value of this type if it represents a Float.
     *
     * @return The value.
     */
    public Float getFloatValue() {
        return (Float) value;
    }

    /**
     * Returns the value of this type if it represents a Double.
     *
     * @return The value.
     */
    public Double getDoubleValue() {
        return (Double) value;
    }

    /**
     * Returns the value of this type if it represents an Octet.
     *
     * @return The value.
     */
    public Byte getOctetValue() {
        return (Byte) value;
    }

    /**
     * Returns the value of this type if it represents a Short.
     *
     * @return The value.
     */
    public Short getShortValue() {
        return (Short) value;
    }

    /**
     * Returns the value of this type if it represents an Integer.
     *
     * @return The value.
     */
    public Integer getIntegerValue() {
        return (Integer) value;
    }

    /**
     * Returns the value of this type if it represents a Long.
     *
     * @return The value.
     */
    public Long getLongValue() {
        return (Long) value;
    }

    /**
     * Returns the value of this type if it represents a String.
     *
     * @return The value.
     */
    public String getStringValue() {
        return (String) value;
    }

    /**
     * Returns true if the value is null.
     *
     * @return True if null, false otherwise.
     */
    public boolean isNull() {
        return (value == null);
    }
    
    @Override
    public Long getShortForm() {
        return shortForm;
    }

    @Override
    public Integer getTypeShortForm() {
        return typeShortForm;
    }

    @Override
    public UShort getAreaNumber() {
        return UShort.ATTRIBUTE_AREA_NUMBER;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.UOctet getAreaVersion() {
        return UOctet.AREA_VERSION;
    }

    @Override
    public UShort getServiceNumber() {
        return UShort.ATTRIBUTE_SERVICE_NUMBER;
    }

    @Override
    public void encode(final MALEncoder encoder) throws MALException {
        switch (this.getTypeShortForm()) {
            case Attribute._BOOLEAN_TYPE_SHORT_FORM:
                encoder.encodeBoolean(getBooleanValue());
                break;
            case Attribute._DOUBLE_TYPE_SHORT_FORM:
                encoder.encodeDouble(getDoubleValue());
                break;
            case Attribute._FLOAT_TYPE_SHORT_FORM:
                encoder.encodeFloat(getFloatValue());
                break;
            case Attribute._INTEGER_TYPE_SHORT_FORM:
                encoder.encodeInteger(getIntegerValue());
                break;
            case Attribute._LONG_TYPE_SHORT_FORM:
                encoder.encodeLong(getLongValue());
                break;
            case Attribute._OCTET_TYPE_SHORT_FORM:
                encoder.encodeOctet(getOctetValue());
                break;
            case Attribute._SHORT_TYPE_SHORT_FORM:
                encoder.encodeShort(getShortValue());
                break;
            default:
                //case Attribute._STRING_TYPE_SHORT_FORM:
                encoder.encodeString(getStringValue());
                break;
        }
    }

    @Override
    public Element decode(final MALDecoder decoder) throws MALException {
        switch (this.getTypeShortForm()) {
            case Attribute._BOOLEAN_TYPE_SHORT_FORM:
                return new Union(decoder.decodeBoolean());
            case Attribute._DOUBLE_TYPE_SHORT_FORM:
                return new Union(decoder.decodeDouble());
            case Attribute._FLOAT_TYPE_SHORT_FORM:
                return new Union(decoder.decodeFloat());
            case Attribute._INTEGER_TYPE_SHORT_FORM:
                return new Union(decoder.decodeInteger());
            case Attribute._LONG_TYPE_SHORT_FORM:
                return new Union(decoder.decodeLong());
            case Attribute._OCTET_TYPE_SHORT_FORM:
                return new Union(decoder.decodeOctet());
            case Attribute._SHORT_TYPE_SHORT_FORM:
                return new Union(decoder.decodeShort());
            default:
                //case Attribute._STRING_TYPE_SHORT_FORM:
                return new Union(decoder.decodeString());
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Union)) {
            return false;
        }
        return this.value.equals(((Union) obj).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        if (null != value) {
            return value.toString();
        }

        return String.valueOf(value);
    }

    private static final long serialVersionUID = Attribute.ABSOLUTE_AREA_SERVICE_NUMBER;
}
