package org.ccsds.moims.mo.mal.structures;

import org.ccsds.moims.mo.mal.TypeId;

/**
 * Enumeration class for AttributeType.
 */
public final class AttributeType extends Enumeration {

    private static final long serialVersionUID = 281475027042408L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475027042408L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Enumeration value for BLOB.
     */
    public static final int BLOB_VALUE = 1;

    /**
     * Enumeration singleton for value BLOB.
     */
    public static final AttributeType BLOB = new AttributeType(AttributeType.BLOB_VALUE);

    /**
     * Enumeration value for BOOLEAN.
     */
    public static final int BOOLEAN_VALUE = 2;

    /**
     * Enumeration singleton for value BOOLEAN.
     */
    public static final AttributeType BOOLEAN = new AttributeType(AttributeType.BOOLEAN_VALUE);

    /**
     * Enumeration value for DURATION.
     */
    public static final int DURATION_VALUE = 3;

    /**
     * Enumeration singleton for value DURATION.
     */
    public static final AttributeType DURATION = new AttributeType(AttributeType.DURATION_VALUE);

    /**
     * Enumeration value for FLOAT.
     */
    public static final int FLOAT_VALUE = 4;

    /**
     * Enumeration singleton for value FLOAT.
     */
    public static final AttributeType FLOAT = new AttributeType(AttributeType.FLOAT_VALUE);

    /**
     * Enumeration value for DOUBLE.
     */
    public static final int DOUBLE_VALUE = 5;

    /**
     * Enumeration singleton for value DOUBLE.
     */
    public static final AttributeType DOUBLE = new AttributeType(AttributeType.DOUBLE_VALUE);

    /**
     * Enumeration value for IDENTIFIER.
     */
    public static final int IDENTIFIER_VALUE = 6;

    /**
     * Enumeration singleton for value IDENTIFIER.
     */
    public static final AttributeType IDENTIFIER = new AttributeType(AttributeType.IDENTIFIER_VALUE);

    /**
     * Enumeration value for OCTET.
     */
    public static final int OCTET_VALUE = 7;

    /**
     * Enumeration singleton for value OCTET.
     */
    public static final AttributeType OCTET = new AttributeType(AttributeType.OCTET_VALUE);

    /**
     * Enumeration value for UOCTET.
     */
    public static final int UOCTET_VALUE = 8;

    /**
     * Enumeration singleton for value UOCTET.
     */
    public static final AttributeType UOCTET = new AttributeType(AttributeType.UOCTET_VALUE);

    /**
     * Enumeration value for SHORT.
     */
    public static final int SHORT_VALUE = 9;

    /**
     * Enumeration singleton for value SHORT.
     */
    public static final AttributeType SHORT = new AttributeType(AttributeType.SHORT_VALUE);

    /**
     * Enumeration value for USHORT.
     */
    public static final int USHORT_VALUE = 10;

    /**
     * Enumeration singleton for value USHORT.
     */
    public static final AttributeType USHORT = new AttributeType(AttributeType.USHORT_VALUE);

    /**
     * Enumeration value for INTEGER.
     */
    public static final int INTEGER_VALUE = 11;

    /**
     * Enumeration singleton for value INTEGER.
     */
    public static final AttributeType INTEGER = new AttributeType(AttributeType.INTEGER_VALUE);

    /**
     * Enumeration value for UINTEGER.
     */
    public static final int UINTEGER_VALUE = 12;

    /**
     * Enumeration singleton for value UINTEGER.
     */
    public static final AttributeType UINTEGER = new AttributeType(AttributeType.UINTEGER_VALUE);

    /**
     * Enumeration value for LONG.
     */
    public static final int LONG_VALUE = 13;

    /**
     * Enumeration singleton for value LONG.
     */
    public static final AttributeType LONG = new AttributeType(AttributeType.LONG_VALUE);

    /**
     * Enumeration value for ULONG.
     */
    public static final int ULONG_VALUE = 14;

    /**
     * Enumeration singleton for value ULONG.
     */
    public static final AttributeType ULONG = new AttributeType(AttributeType.ULONG_VALUE);

    /**
     * Enumeration value for STRING.
     */
    public static final int STRING_VALUE = 15;

    /**
     * Enumeration singleton for value STRING.
     */
    public static final AttributeType STRING = new AttributeType(AttributeType.STRING_VALUE);

    /**
     * Enumeration value for TIME.
     */
    public static final int TIME_VALUE = 16;

    /**
     * Enumeration singleton for value TIME.
     */
    public static final AttributeType TIME = new AttributeType(AttributeType.TIME_VALUE);

    /**
     * Enumeration value for FINETIME.
     */
    public static final int FINETIME_VALUE = 17;

    /**
     * Enumeration singleton for value FINETIME.
     */
    public static final AttributeType FINETIME = new AttributeType(AttributeType.FINETIME_VALUE);

    /**
     * Enumeration value for URI.
     */
    public static final int URI_VALUE = 18;

    /**
     * Enumeration singleton for value URI.
     */
    public static final AttributeType URI = new AttributeType(AttributeType.URI_VALUE);

    /**
     * Enumeration value for OBJECTREF.
     */
    public static final int OBJECTREF_VALUE = 19;

    /**
     * Enumeration singleton for value OBJECTREF.
     */
    public static final AttributeType OBJECTREF = new AttributeType(AttributeType.OBJECTREF_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final AttributeType[] _ENUMERATIONS = {
        BLOB, BOOLEAN, DURATION, FLOAT, DOUBLE, IDENTIFIER, OCTET, UOCTET, SHORT,
        USHORT, INTEGER, UINTEGER, LONG, ULONG, STRING, TIME, FINETIME, URI, OBJECTREF};

    /**
     * AttributeType is an enumeration that shall be used to hold the defined MAL Attribute types.
     */
    public AttributeType() {
        super(-1);
    }

    /**
     * AttributeType is an enumeration that shall be used to hold the defined
     * MAL Attribute types.
     * 
     * @param value The value of the Enumeration.
     */
    public AttributeType(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case BLOB_VALUE:
                return "BLOB";
            case BOOLEAN_VALUE:
                return "BOOLEAN";
            case DURATION_VALUE:
                return "DURATION";
            case FLOAT_VALUE:
                return "FLOAT";
            case DOUBLE_VALUE:
                return "DOUBLE";
            case IDENTIFIER_VALUE:
                return "IDENTIFIER";
            case OCTET_VALUE:
                return "OCTET";
            case UOCTET_VALUE:
                return "UOCTET";
            case SHORT_VALUE:
                return "SHORT";
            case USHORT_VALUE:
                return "USHORT";
            case INTEGER_VALUE:
                return "INTEGER";
            case UINTEGER_VALUE:
                return "UINTEGER";
            case LONG_VALUE:
                return "LONG";
            case ULONG_VALUE:
                return "ULONG";
            case STRING_VALUE:
                return "STRING";
            case TIME_VALUE:
                return "TIME";
            case FINETIME_VALUE:
                return "FINETIME";
            case URI_VALUE:
                return "URI";
            case OBJECTREF_VALUE:
                return "OBJECTREF";
            default:
                throw new RuntimeException("Unknown ordinal!");
        }
    }

    /**
     * Returns the enumeration element represented by the supplied string, or
     * null if not matched.
     * 
     * @param s s The string to search for.
     * @return The matched enumeration element, or null if not matched.
     */
    public static AttributeType fromString(String s) {
        switch (s) {
            case "BLOB":
                return AttributeType.BLOB;
            case "BOOLEAN":
                return AttributeType.BOOLEAN;
            case "DURATION":
                return AttributeType.DURATION;
            case "FLOAT":
                return AttributeType.FLOAT;
            case "DOUBLE":
                return AttributeType.DOUBLE;
            case "IDENTIFIER":
                return AttributeType.IDENTIFIER;
            case "OCTET":
                return AttributeType.OCTET;
            case "UOCTET":
                return AttributeType.UOCTET;
            case "SHORT":
                return AttributeType.SHORT;
            case "USHORT":
                return AttributeType.USHORT;
            case "INTEGER":
                return AttributeType.INTEGER;
            case "UINTEGER":
                return AttributeType.UINTEGER;
            case "LONG":
                return AttributeType.LONG;
            case "ULONG":
                return AttributeType.ULONG;
            case "STRING":
                return AttributeType.STRING;
            case "TIME":
                return AttributeType.TIME;
            case "FINETIME":
                return AttributeType.FINETIME;
            case "URI":
                return AttributeType.URI;
            case "OBJECTREF":
                return AttributeType.OBJECTREF;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public Enumeration fromValue(Integer value) {
        switch (value) {
            case BLOB_VALUE:
                return AttributeType.BLOB;
            case BOOLEAN_VALUE:
                return AttributeType.BOOLEAN;
            case DURATION_VALUE:
                return AttributeType.DURATION;
            case FLOAT_VALUE:
                return AttributeType.FLOAT;
            case DOUBLE_VALUE:
                return AttributeType.DOUBLE;
            case IDENTIFIER_VALUE:
                return AttributeType.IDENTIFIER;
            case OCTET_VALUE:
                return AttributeType.OCTET;
            case UOCTET_VALUE:
                return AttributeType.UOCTET;
            case SHORT_VALUE:
                return AttributeType.SHORT;
            case USHORT_VALUE:
                return AttributeType.USHORT;
            case INTEGER_VALUE:
                return AttributeType.INTEGER;
            case UINTEGER_VALUE:
                return AttributeType.UINTEGER;
            case LONG_VALUE:
                return AttributeType.LONG;
            case ULONG_VALUE:
                return AttributeType.ULONG;
            case STRING_VALUE:
                return AttributeType.STRING;
            case TIME_VALUE:
                return AttributeType.TIME;
            case FINETIME_VALUE:
                return AttributeType.FINETIME;
            case URI_VALUE:
                return AttributeType.URI;
            case OBJECTREF_VALUE:
                return AttributeType.OBJECTREF;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided value: " + value);
        }
    }

    @Override
    public Element createElement() {
        return _ENUMERATIONS[0];
    }

    @Override
    public int getEnumSize() {
        return 19;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
