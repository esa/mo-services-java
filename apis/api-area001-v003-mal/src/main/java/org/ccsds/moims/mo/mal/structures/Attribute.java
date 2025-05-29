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

import java.util.Arrays;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.TypeId;

/**
 * The Attribute interface represents the MAL Attribute type.
 */
public interface Attribute extends Element {

    /**
     * The TypeId of the MAL Area.
     */
    public final static TypeId TYPE_ID_MAL_AREA = new TypeId(
            MALHelper._MAL_AREA_NUMBER,
            MALHelper._MAL_AREA_VERSION,
            0,
            0
    );
    /**
     * Holds the MAL Area/Service/Version number in absolute type short form.
     */
    public final static long ABSOLUTE_AREA_SERVICE_NUMBER = TYPE_ID_MAL_AREA.getTypeId();
    /**
     * Relative type short form for BLOB, can be using in switch statements.
     */
    public final static int _BLOB_TYPE_SHORT_FORM = 1;
    /**
     * Relative type short form for BLOB.
     */
    public final static Integer BLOB_TYPE_SHORT_FORM = _BLOB_TYPE_SHORT_FORM;
    /**
     * Absolute type short form for BLOB.
     */
    public final static Long BLOB_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _BLOB_TYPE_SHORT_FORM;
    /**
     * Relative type short form for BOOLEAN, can be using in switch statements.
     */
    public final static int _BOOLEAN_TYPE_SHORT_FORM = 2;
    /**
     * Relative type short form for BOOLEAN.
     */
    public final static Integer BOOLEAN_TYPE_SHORT_FORM = _BOOLEAN_TYPE_SHORT_FORM;
    /**
     * Absolute type short form for BOOLEAN.
     */
    public final static Long BOOLEAN_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _BOOLEAN_TYPE_SHORT_FORM;
    /**
     * Relative type short form for DURATION, can be using in switch statements.
     */
    public final static int _DURATION_TYPE_SHORT_FORM = 3;
    /**
     * Relative type short form for DURATION.
     */
    public final static Integer DURATION_TYPE_SHORT_FORM = _DURATION_TYPE_SHORT_FORM;
    /**
     * Absolute type short form for DURATION.
     */
    public final static Long DURATION_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _DURATION_TYPE_SHORT_FORM;
    /**
     * Relative type short form for FLOAT, can be using in switch statements.
     */
    public final static int _FLOAT_TYPE_SHORT_FORM = 4;
    /**
     * Relative type short form for FLOAT.
     */
    public final static Integer FLOAT_TYPE_SHORT_FORM = _FLOAT_TYPE_SHORT_FORM;
    /**
     * Absolute type short form for FLOAT.
     */
    public final static Long FLOAT_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _FLOAT_TYPE_SHORT_FORM;
    /**
     * Relative type short form for DOUBLE, can be using in switch statements.
     */
    public final static int _DOUBLE_TYPE_SHORT_FORM = 5;
    /**
     * Relative type short form for DOUBLE.
     */
    public final static Integer DOUBLE_TYPE_SHORT_FORM = _DOUBLE_TYPE_SHORT_FORM;
    /**
     * Absolute type short form for DOUBLE.
     */
    public final static Long DOUBLE_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _DOUBLE_TYPE_SHORT_FORM;
    /**
     * Relative type short form for IDENTIFIER, can be using in switch
     * statements.
     */
    public final static int _IDENTIFIER_TYPE_SHORT_FORM = 6;
    /**
     * Relative type short form for IDENTIFIER.
     */
    public final static Integer IDENTIFIER_TYPE_SHORT_FORM = _IDENTIFIER_TYPE_SHORT_FORM;
    /**
     * Absolute type short form for IDENTIFIER.
     */
    public final static Long IDENTIFIER_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _IDENTIFIER_TYPE_SHORT_FORM;
    /**
     * Relative type short form for OCTET, can be using in switch statements.
     */
    public final static int _OCTET_TYPE_SHORT_FORM = 7;
    /**
     * Relative type short form for OCTET.
     */
    public final static Integer OCTET_TYPE_SHORT_FORM = _OCTET_TYPE_SHORT_FORM;
    /**
     * Absolute type short form for OCTET.
     */
    public final static Long OCTET_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _OCTET_TYPE_SHORT_FORM;
    /**
     * Relative type short form for UOCTET, can be using in switch statements.
     */
    public final static int _UOCTET_TYPE_SHORT_FORM = 8;
    /**
     * Relative type short form for UOCTET.
     */
    public final static Integer UOCTET_TYPE_SHORT_FORM = _UOCTET_TYPE_SHORT_FORM;
    /**
     * Absolute type short form for UOCTET.
     */
    public final static Long UOCTET_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _UOCTET_TYPE_SHORT_FORM;
    /**
     * Relative type short form for SHORT, can be using in switch statements.
     */
    public final static int _SHORT_TYPE_SHORT_FORM = 9;
    /**
     * Relative type short form for SHORT.
     */
    public final static Integer SHORT_TYPE_SHORT_FORM = _SHORT_TYPE_SHORT_FORM;
    /**
     * Absolute type short form for SHORT.
     */
    public final static Long SHORT_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _SHORT_TYPE_SHORT_FORM;
    /**
     * Relative type short form for USHORT, can be using in switch statements.
     */
    public final static int _USHORT_TYPE_SHORT_FORM = 10;
    /**
     * Relative type short form for USHORT.
     */
    public final static Integer USHORT_TYPE_SHORT_FORM = _USHORT_TYPE_SHORT_FORM;
    /**
     * Absolute type short form for USHORT.
     */
    public final static Long USHORT_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _USHORT_TYPE_SHORT_FORM;
    /**
     * Relative type short form for INTEGER, can be using in switch statements.
     */
    public final static int _INTEGER_TYPE_SHORT_FORM = 11;
    /**
     * Relative type short form for INTEGER.
     */
    public final static Integer INTEGER_TYPE_SHORT_FORM = _INTEGER_TYPE_SHORT_FORM;
    /**
     * Absolute type short form for INTEGER.
     */
    public final static Long INTEGER_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _INTEGER_TYPE_SHORT_FORM;
    /**
     * Relative type short form for UINTEGER, can be using in switch statements.
     */
    public final static int _UINTEGER_TYPE_SHORT_FORM = 12;
    /**
     * Relative type short form for UINTEGER.
     */
    public final static Integer UINTEGER_TYPE_SHORT_FORM = _UINTEGER_TYPE_SHORT_FORM;
    /**
     * Absolute type short form for UINTEGER.
     */
    public final static Long UINTEGER_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _UINTEGER_TYPE_SHORT_FORM;
    /**
     * Relative type short form for LONG, can be using in switch statements.
     */
    public final static int _LONG_TYPE_SHORT_FORM = 13;
    /**
     * Relative type short form for LONG.
     */
    public final static Integer LONG_TYPE_SHORT_FORM = _LONG_TYPE_SHORT_FORM;
    /**
     * Absolute type short form for LONG.
     */
    public final static Long LONG_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _LONG_TYPE_SHORT_FORM;
    /**
     * Relative type short form for ULONG, can be using in switch statements.
     */
    public final static int _ULONG_TYPE_SHORT_FORM = 14;
    /**
     * Relative type short form for ULONG.
     */
    public final static Integer ULONG_TYPE_SHORT_FORM = _ULONG_TYPE_SHORT_FORM;
    /**
     * Absolute type short form for ULONG.
     */
    public final static Long ULONG_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _ULONG_TYPE_SHORT_FORM;
    /**
     * Relative type short form for STRING, can be using in switch statements.
     */
    public final static int _STRING_TYPE_SHORT_FORM = 15;
    /**
     * Relative type short form for STRING.
     */
    public final static Integer STRING_TYPE_SHORT_FORM = _STRING_TYPE_SHORT_FORM;
    /**
     * Absolute type short form for STRING.
     */
    public final static Long STRING_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _STRING_TYPE_SHORT_FORM;
    /**
     * Relative type short form for TIME, can be using in switch statements.
     */
    public final static int _TIME_TYPE_SHORT_FORM = 16;
    /**
     * Relative type short form for TIME.
     */
    public final static Integer TIME_TYPE_SHORT_FORM = _TIME_TYPE_SHORT_FORM;
    /**
     * Absolute type short form for TIME.
     */
    public final static Long TIME_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _TIME_TYPE_SHORT_FORM;
    /**
     * Relative type short form for FINETIME, can be using in switch statements.
     */
    public final static int _FINETIME_TYPE_SHORT_FORM = 17;
    /**
     * Relative type short form for FINETIME.
     */
    public final static Integer FINETIME_TYPE_SHORT_FORM = _FINETIME_TYPE_SHORT_FORM;
    /**
     * Absolute type short form for FINETIME.
     */
    public final static Long FINETIME_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _FINETIME_TYPE_SHORT_FORM;
    /**
     * Relative type short form for URI, can be using in switch statements.
     */
    public final static int _URI_TYPE_SHORT_FORM = 18;
    /**
     * Relative type short form for URI.
     */
    public final static Integer URI_TYPE_SHORT_FORM = _URI_TYPE_SHORT_FORM;
    /**
     * Absolute type short form for URI.
     */
    public final static Long URI_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _URI_TYPE_SHORT_FORM;
    /**
     * Relative type short form for OBJECTREF, can be using in switch
     * statements.
     */
    public final static int _OBJECTREF_TYPE_SHORT_FORM = 19;
    /**
     * Relative type short form for URI.
     */
    public final static Integer OBJECTREF_TYPE_SHORT_FORM = _OBJECTREF_TYPE_SHORT_FORM;
    /**
     * Absolute type short form for URI.
     */
    public final static Long OBJECTREF_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _OBJECTREF_TYPE_SHORT_FORM;
    /**
     * ONE_MILLION is used by the Time and FineTime classes.
     */
    public final static long ONE_MILLION = 1000000;

    /**
     * Converts a Java data type into a MAL data type if possible
     *
     * @param obj The object in the Java data type
     * @return The object in the MAL data type or the original object
     */
    public static Object javaType2Attribute(Object obj) {
        if (obj instanceof java.lang.Boolean) {
            return new Union((Boolean) obj);
        }

        if (obj instanceof java.lang.Integer) {
            return new Union((Integer) obj);
        }

        if (obj instanceof java.lang.Long) {
            return new Union((Long) obj);
        }

        if (obj instanceof java.lang.String) {
            return new Union((String) obj);
        }

        if (obj instanceof java.lang.Double) {
            return new Union((Double) obj);
        }

        if (obj instanceof java.lang.Float) {
            return new Union((Float) obj);
        }

        if (obj instanceof java.lang.Byte) {
            return new Union((Byte) obj);
        }

        if (obj instanceof java.lang.Short) {
            return new Union((Short) obj);
        }

        return obj;
    }

    /**
     * Converts a MAL data type into a Java data type
     *
     * @param obj The object in the MAL data type
     * @return The object in the Java data type
     */
    public static Object attribute2JavaType(Object obj) {
        if (obj instanceof Union) {
            Integer typeShortForm = ((Union) obj).getTypeId().getSFP();

            if (typeShortForm == Attribute._BOOLEAN_TYPE_SHORT_FORM) {
                return (boolean) ((Union) obj).getBooleanValue();
            }

            if (typeShortForm == Attribute._INTEGER_TYPE_SHORT_FORM) {
                return (int) ((Union) obj).getIntegerValue();
            }

            if (typeShortForm == Attribute._LONG_TYPE_SHORT_FORM) {
                return (long) ((Union) obj).getLongValue();
            }

            if (typeShortForm == Attribute._STRING_TYPE_SHORT_FORM) {
                return ((Union) obj).getStringValue();
            }

            if (typeShortForm == Attribute._DOUBLE_TYPE_SHORT_FORM) {
                return (double) ((Union) obj).getDoubleValue();
            }

            if (typeShortForm == Attribute._FLOAT_TYPE_SHORT_FORM) {
                return (float) ((Union) obj).getFloatValue();
            }

            if (typeShortForm == Attribute._OCTET_TYPE_SHORT_FORM) {
                return (byte) ((Union) obj).getOctetValue();
            }

            if (typeShortForm == Attribute._SHORT_TYPE_SHORT_FORM) {
                return (short) ((Union) obj).getShortValue();
            }
        }

        return obj;
    }

    /**
     * Converts any MAL Attribute data type to a String java type
     *
     * @param in The MAL Attribute data type
     * @return The convert String value
     */
    public static String attribute2string(Object in) {
        if (in == null) {
            return "null";
        }

        if (in instanceof Union) {
            Integer sfp = ((Union) in).getTypeId().getSFP();

            if (sfp.equals(Union.DOUBLE_TYPE_SHORT_FORM)) {
                if (((Union) in).getDoubleValue() == null) {
                    return "";
                }
                return ((Union) in).getDoubleValue().toString();
            }

            if (sfp.equals(Union.BOOLEAN_TYPE_SHORT_FORM)) {
                if (((Union) in).getBooleanValue() == null) {
                    return "";
                }
                String dou = ((Union) in).getBooleanValue() ? "true" : "false";
                return dou;
            }

            if (sfp.equals(Union.FLOAT_TYPE_SHORT_FORM)) {
                if (((Union) in).getFloatValue() == null) {
                    return "";
                }
                return (((Union) in).getFloatValue()).toString();
            }

            if (sfp.equals(Union.INTEGER_TYPE_SHORT_FORM)) {
                if (((Union) in).getIntegerValue() == null) {
                    return "";
                }
                return (((Union) in).getIntegerValue()).toString();
            }

            if (sfp.equals(Union.LONG_TYPE_SHORT_FORM)) {
                if (((Union) in).getLongValue() == null) {
                    return "";
                }
                return (((Union) in).getLongValue()).toString();
            }

            if (sfp.equals(Union.OCTET_TYPE_SHORT_FORM)) {
                if (((Union) in).getOctetValue() == null) {
                    return "";
                }
                return (((Union) in).getOctetValue()).toString();
            }

            if (sfp.equals(Union.SHORT_TYPE_SHORT_FORM)) {
                if (((Union) in).getShortValue() == null) {
                    return "";
                }
                return (((Union) in).getShortValue()).toString();
            }

            if (sfp.equals(Union.STRING_TYPE_SHORT_FORM)) {
                if (((Union) in).getStringValue() == null) {
                    return "";
                }
                return ((Union) in).getStringValue();
            }

        }

        if (in instanceof Duration) {
            return String.valueOf(((Duration) in).toString());
        }

        if (in instanceof UOctet) {
            return String.valueOf(((UOctet) in).getValue());
        }

        if (in instanceof UShort) {
            return String.valueOf(((UShort) in).getValue());
        }

        if (in instanceof UInteger) {
            return String.valueOf(((UInteger) in).getValue());
        }

        if (in instanceof Blob) {
            return Arrays.toString(((Blob) in).getValue());
        }

        if (in instanceof ULong) {
            return String.valueOf(((ULong) in).getValue());
        }

        if (in instanceof Time) {
            return String.valueOf(((Time) in).getValue());
        }

        if (in instanceof Identifier) {
            return ((Identifier) in).getValue();
        }

        if (in instanceof FineTime) {
            return String.valueOf(((FineTime) in).getValue());
        }

        if (in instanceof URI) {
            return ((URI) in).toString();
        }

        if (in instanceof Long) {
            return ((Long) in).toString();
        }

        return "";
    }

    /**
     * Converts this MAL Attribute data type to a String java type.
     *
     * @return The convert String value.
     */
    default String attribute2string() throws IllegalArgumentException {
        return attribute2string(this);
    }

    /**
     * Checks if the attribute is an Identifier, String or URI MAL data type.
     *
     * @return True if the object can be read as a string
     * @throws java.lang.IllegalArgumentException If obj == null
     */
    default boolean isStringAttribute() throws IllegalArgumentException {
        return isStringAttribute(this);
    }

    /**
     * Checks if an attribute is an Identifier, String or URI MAL data type
     *
     * @param obj The attribute
     * @return True if the object can be read as a string
     * @throws java.lang.IllegalArgumentException If obj == null
     */
    public static boolean isStringAttribute(Attribute obj) throws IllegalArgumentException {
        if (obj == null) {
            throw new IllegalArgumentException("Obj must not be null");
        }

        Integer sfp = obj.getTypeId().getSFP(); // Short Form Part

        // Check if: Identifier, String, URI
        return (sfp == Attribute._IDENTIFIER_TYPE_SHORT_FORM)
                || (sfp == Attribute._STRING_TYPE_SHORT_FORM)
                || (sfp == Attribute._URI_TYPE_SHORT_FORM);
    }
}
