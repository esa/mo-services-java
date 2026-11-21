package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Enumeration;

/**
 * Enumeration class for ArgTypeEnum.
 */
public final class ArgTypeEnum extends Enumeration {

    private static final long serialVersionUID = 1407374900330497L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330497L;
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
    public static final ArgTypeEnum BLOB = new ArgTypeEnum(ArgTypeEnum.BLOB_VALUE);

    /**
     * Enumeration value for BOOLEAN.
     */
    public static final int BOOLEAN_VALUE = 2;

    /**
     * Enumeration singleton for value BOOLEAN.
     */
    public static final ArgTypeEnum BOOLEAN = new ArgTypeEnum(ArgTypeEnum.BOOLEAN_VALUE);

    /**
     * Enumeration value for DURATION.
     */
    public static final int DURATION_VALUE = 3;

    /**
     * Enumeration singleton for value DURATION.
     */
    public static final ArgTypeEnum DURATION = new ArgTypeEnum(ArgTypeEnum.DURATION_VALUE);

    /**
     * Enumeration value for FLOAT.
     */
    public static final int FLOAT_VALUE = 4;

    /**
     * Enumeration singleton for value FLOAT.
     */
    public static final ArgTypeEnum FLOAT = new ArgTypeEnum(ArgTypeEnum.FLOAT_VALUE);

    /**
     * Enumeration value for DOUBLE.
     */
    public static final int DOUBLE_VALUE = 5;

    /**
     * Enumeration singleton for value DOUBLE.
     */
    public static final ArgTypeEnum DOUBLE = new ArgTypeEnum(ArgTypeEnum.DOUBLE_VALUE);

    /**
     * Enumeration value for IDENTIFIER.
     */
    public static final int IDENTIFIER_VALUE = 6;

    /**
     * Enumeration singleton for value IDENTIFIER.
     */
    public static final ArgTypeEnum IDENTIFIER = new ArgTypeEnum(ArgTypeEnum.IDENTIFIER_VALUE);

    /**
     * Enumeration value for OCTET.
     */
    public static final int OCTET_VALUE = 7;

    /**
     * Enumeration singleton for value OCTET.
     */
    public static final ArgTypeEnum OCTET = new ArgTypeEnum(ArgTypeEnum.OCTET_VALUE);

    /**
     * Enumeration value for UOCTET.
     */
    public static final int UOCTET_VALUE = 8;

    /**
     * Enumeration singleton for value UOCTET.
     */
    public static final ArgTypeEnum UOCTET = new ArgTypeEnum(ArgTypeEnum.UOCTET_VALUE);

    /**
     * Enumeration value for SHORT.
     */
    public static final int SHORT_VALUE = 9;

    /**
     * Enumeration singleton for value SHORT.
     */
    public static final ArgTypeEnum SHORT = new ArgTypeEnum(ArgTypeEnum.SHORT_VALUE);

    /**
     * Enumeration value for USHORT.
     */
    public static final int USHORT_VALUE = 10;

    /**
     * Enumeration singleton for value USHORT.
     */
    public static final ArgTypeEnum USHORT = new ArgTypeEnum(ArgTypeEnum.USHORT_VALUE);

    /**
     * Enumeration value for INTEGER.
     */
    public static final int INTEGER_VALUE = 11;

    /**
     * Enumeration singleton for value INTEGER.
     */
    public static final ArgTypeEnum INTEGER = new ArgTypeEnum(ArgTypeEnum.INTEGER_VALUE);

    /**
     * Enumeration value for UINTEGER.
     */
    public static final int UINTEGER_VALUE = 12;

    /**
     * Enumeration singleton for value UINTEGER.
     */
    public static final ArgTypeEnum UINTEGER = new ArgTypeEnum(ArgTypeEnum.UINTEGER_VALUE);

    /**
     * Enumeration value for LONG.
     */
    public static final int LONG_VALUE = 13;

    /**
     * Enumeration singleton for value LONG.
     */
    public static final ArgTypeEnum LONG = new ArgTypeEnum(ArgTypeEnum.LONG_VALUE);

    /**
     * Enumeration value for ULONG.
     */
    public static final int ULONG_VALUE = 14;

    /**
     * Enumeration singleton for value ULONG.
     */
    public static final ArgTypeEnum ULONG = new ArgTypeEnum(ArgTypeEnum.ULONG_VALUE);

    /**
     * Enumeration value for STRING.
     */
    public static final int STRING_VALUE = 15;

    /**
     * Enumeration singleton for value STRING.
     */
    public static final ArgTypeEnum STRING = new ArgTypeEnum(ArgTypeEnum.STRING_VALUE);

    /**
     * Enumeration value for TIME.
     */
    public static final int TIME_VALUE = 16;

    /**
     * Enumeration singleton for value TIME.
     */
    public static final ArgTypeEnum TIME = new ArgTypeEnum(ArgTypeEnum.TIME_VALUE);

    /**
     * Enumeration value for FINETIME.
     */
    public static final int FINETIME_VALUE = 17;

    /**
     * Enumeration singleton for value FINETIME.
     */
    public static final ArgTypeEnum FINETIME = new ArgTypeEnum(ArgTypeEnum.FINETIME_VALUE);

    /**
     * Enumeration value for URI.
     */
    public static final int URI_VALUE = 18;

    /**
     * Enumeration singleton for value URI.
     */
    public static final ArgTypeEnum URI = new ArgTypeEnum(ArgTypeEnum.URI_VALUE);

    /**
     * Enumeration value for OBJECTREF.
     */
    public static final int OBJECTREF_VALUE = 19;

    /**
     * Enumeration singleton for value OBJECTREF.
     */
    public static final ArgTypeEnum OBJECTREF = new ArgTypeEnum(ArgTypeEnum.OBJECTREF_VALUE);

    /**
     * Enumeration value for POSITION.
     */
    public static final int POSITION_VALUE = 129;

    /**
     * Enumeration singleton for value POSITION.
     */
    public static final ArgTypeEnum POSITION = new ArgTypeEnum(ArgTypeEnum.POSITION_VALUE);

    /**
     * Enumeration value for DIRECTION.
     */
    public static final int DIRECTION_VALUE = 130;

    /**
     * Enumeration singleton for value DIRECTION.
     */
    public static final ArgTypeEnum DIRECTION = new ArgTypeEnum(ArgTypeEnum.DIRECTION_VALUE);

    /**
     * Enumeration value for ANGLE.
     */
    public static final int ANGLE_VALUE = 131;

    /**
     * Enumeration singleton for value ANGLE.
     */
    public static final ArgTypeEnum ANGLE = new ArgTypeEnum(ArgTypeEnum.ANGLE_VALUE);

    /**
     * Enumeration value for ANGULAR_RATE.
     */
    public static final int ANGULAR_RATE_VALUE = 132;

    /**
     * Enumeration singleton for value ANGULAR_RATE.
     */
    public static final ArgTypeEnum ANGULAR_RATE = new ArgTypeEnum(ArgTypeEnum.ANGULAR_RATE_VALUE);

    /**
     * Enumeration value for DISTANCE.
     */
    public static final int DISTANCE_VALUE = 133;

    /**
     * Enumeration singleton for value DISTANCE.
     */
    public static final ArgTypeEnum DISTANCE = new ArgTypeEnum(ArgTypeEnum.DISTANCE_VALUE);

    /**
     * Enumeration value for ANY.
     */
    public static final int ANY_VALUE = 134;

    /**
     * Enumeration singleton for value ANY.
     */
    public static final ArgTypeEnum ANY = new ArgTypeEnum(ArgTypeEnum.ANY_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final ArgTypeEnum[] _ENUMERATIONS = {
        BLOB, BOOLEAN, DURATION, FLOAT, DOUBLE, IDENTIFIER, OCTET, UOCTET, SHORT,
        USHORT, INTEGER, UINTEGER, LONG, ULONG, STRING, TIME, FINETIME, URI, OBJECTREF,
        POSITION, DIRECTION, ANGLE, ANGULAR_RATE, DISTANCE, ANY};

    /**
     * E1: ArgTypeEnum is an MPS extension of the MAL::AttributeType enumeration (see reference [2] section 4.6.4) that also allows specification of the Geometric data types.
     */
    public ArgTypeEnum() {
        super(-1);
    }

    /**
     * E1: ArgTypeEnum is an MPS extension of the MAL::AttributeType enumeration
     * (see reference [2] section 4.6.4) that also allows specification of the
     * Geometric data types.
     * 
     * @param value The value of the Enumeration.
     */
    public ArgTypeEnum(int value) {
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
            case POSITION_VALUE:
                return "POSITION";
            case DIRECTION_VALUE:
                return "DIRECTION";
            case ANGLE_VALUE:
                return "ANGLE";
            case ANGULAR_RATE_VALUE:
                return "ANGULAR_RATE";
            case DISTANCE_VALUE:
                return "DISTANCE";
            case ANY_VALUE:
                return "ANY";
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
    public static ArgTypeEnum fromString(String s) {
        switch (s) {
            case "BLOB":
                return ArgTypeEnum.BLOB;
            case "BOOLEAN":
                return ArgTypeEnum.BOOLEAN;
            case "DURATION":
                return ArgTypeEnum.DURATION;
            case "FLOAT":
                return ArgTypeEnum.FLOAT;
            case "DOUBLE":
                return ArgTypeEnum.DOUBLE;
            case "IDENTIFIER":
                return ArgTypeEnum.IDENTIFIER;
            case "OCTET":
                return ArgTypeEnum.OCTET;
            case "UOCTET":
                return ArgTypeEnum.UOCTET;
            case "SHORT":
                return ArgTypeEnum.SHORT;
            case "USHORT":
                return ArgTypeEnum.USHORT;
            case "INTEGER":
                return ArgTypeEnum.INTEGER;
            case "UINTEGER":
                return ArgTypeEnum.UINTEGER;
            case "LONG":
                return ArgTypeEnum.LONG;
            case "ULONG":
                return ArgTypeEnum.ULONG;
            case "STRING":
                return ArgTypeEnum.STRING;
            case "TIME":
                return ArgTypeEnum.TIME;
            case "FINETIME":
                return ArgTypeEnum.FINETIME;
            case "URI":
                return ArgTypeEnum.URI;
            case "OBJECTREF":
                return ArgTypeEnum.OBJECTREF;
            case "POSITION":
                return ArgTypeEnum.POSITION;
            case "DIRECTION":
                return ArgTypeEnum.DIRECTION;
            case "ANGLE":
                return ArgTypeEnum.ANGLE;
            case "ANGULAR_RATE":
                return ArgTypeEnum.ANGULAR_RATE;
            case "DISTANCE":
                return ArgTypeEnum.DISTANCE;
            case "ANY":
                return ArgTypeEnum.ANY;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public Enumeration fromValue(Integer value) {
        switch (value) {
            case BLOB_VALUE:
                return ArgTypeEnum.BLOB;
            case BOOLEAN_VALUE:
                return ArgTypeEnum.BOOLEAN;
            case DURATION_VALUE:
                return ArgTypeEnum.DURATION;
            case FLOAT_VALUE:
                return ArgTypeEnum.FLOAT;
            case DOUBLE_VALUE:
                return ArgTypeEnum.DOUBLE;
            case IDENTIFIER_VALUE:
                return ArgTypeEnum.IDENTIFIER;
            case OCTET_VALUE:
                return ArgTypeEnum.OCTET;
            case UOCTET_VALUE:
                return ArgTypeEnum.UOCTET;
            case SHORT_VALUE:
                return ArgTypeEnum.SHORT;
            case USHORT_VALUE:
                return ArgTypeEnum.USHORT;
            case INTEGER_VALUE:
                return ArgTypeEnum.INTEGER;
            case UINTEGER_VALUE:
                return ArgTypeEnum.UINTEGER;
            case LONG_VALUE:
                return ArgTypeEnum.LONG;
            case ULONG_VALUE:
                return ArgTypeEnum.ULONG;
            case STRING_VALUE:
                return ArgTypeEnum.STRING;
            case TIME_VALUE:
                return ArgTypeEnum.TIME;
            case FINETIME_VALUE:
                return ArgTypeEnum.FINETIME;
            case URI_VALUE:
                return ArgTypeEnum.URI;
            case OBJECTREF_VALUE:
                return ArgTypeEnum.OBJECTREF;
            case POSITION_VALUE:
                return ArgTypeEnum.POSITION;
            case DIRECTION_VALUE:
                return ArgTypeEnum.DIRECTION;
            case ANGLE_VALUE:
                return ArgTypeEnum.ANGLE;
            case ANGULAR_RATE_VALUE:
                return ArgTypeEnum.ANGULAR_RATE;
            case DISTANCE_VALUE:
                return ArgTypeEnum.DISTANCE;
            case ANY_VALUE:
                return ArgTypeEnum.ANY;
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
        return 25;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
