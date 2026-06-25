/* ----------------------------------------------------------------------------
 * Copyright (C) 2021      European Space Agency
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
package org.ccsds.moims.mo.mal.helpertools.helpers;

import java.io.*;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.structures.*;

/**
 * A Helper class to simplify and solve many problems related with MAL
 * Attributes.
 */
public class HelperAttributes {

    /**
     * Selected value to represent a serialized object
     */
    public static Byte SERIAL_OBJECT_RAW_TYPE = (byte) 127;

    /**
     * Selected the String to represent a Serialized object
     */
    public static String SERIAL_OBJECT_STRING = "SerializedObject";

    /**
     * @deprecated Use {@link Attribute#attribute2double(Attribute)} directly.
     */
    @Deprecated
    public static Double attribute2double(Attribute in) {
        return Attribute.attribute2double(in);
    }

    /**
     * @deprecated Use {@link Attribute#attribute2string(Object)} directly.
     */
    @Deprecated
    public static String attribute2string(Object in) {
        return Attribute.attribute2string(in);
    }

    /**
     * Creates an instance of a MAL attribute from attribute name
     *
     * @param attributeName The Attribute name
     * @return The Attribute object
     * @throws java.lang.IllegalArgumentException If attributeName == null
     */
    public static Object attributeName2object(String attributeName) throws IllegalArgumentException {
        if (attributeName == null) {
            throw new IllegalArgumentException("AttributeName must not be null.");
        }
        if (attributeName.equals("Blob")) {
            return new Blob();
        }
        if (attributeName.equals("Boolean")) {
            return new Boolean(false);
        }
        if (attributeName.equals("Duration")) {
            return new Duration();
        }
        if (attributeName.equals("Float")) {
            return new Float(0);
        }
        if (attributeName.equals("Double")) {
            return new Double(0);
        }
        if (attributeName.equals("Identifier")) {
            return new Identifier();
        }
        if (attributeName.equals("Octet")) {
            return new Byte((byte) 0);
        }
        if (attributeName.equals("UOctet")) {
            return new UOctet();
        }
        if (attributeName.equals("Short")) {
            return new Short((short) 0);
        }
        if (attributeName.equals("UShort")) {
            return new UShort();
        }
        if (attributeName.equals("Integer")) {
            return new Integer((int) 0);
        }
        if (attributeName.equals("UInteger")) {
            return new UInteger();
        }
        if (attributeName.equals("Long")) {
            return new Long(0);
        }
        if (attributeName.equals("ULong")) {
            return new ULong();
        }
        if (attributeName.equals("String")) {
            return new String();
        }
        if (attributeName.equals("Time")) {
            return new Time();
        }
        if (attributeName.equals("FineTime")) {
            return new FineTime();
        }
        if (attributeName.equals("URI")) {
            return new URI();
        }
        if (attributeName.equals(SERIAL_OBJECT_STRING)) {
            return new Blob();
        }

        return null;
    }

    /**
     * Checks the java type and returns the equivalent MO type short form.
     *
     * @param type The java type.
     * @return The type short form in MO.
     */
    public static Integer getTypeShortForm(Class<?> type) {
        Integer helperValue = HelperAttributes.attributeName2typeShortForm(type.getSimpleName());
        if (helperValue != null) {
            return helperValue;
        }

        if (type.equals(boolean.class)) {
            return HelperAttributes.attributeName2typeShortForm("Boolean");
        } else if (type.equals(float.class)) {
            return HelperAttributes.attributeName2typeShortForm("Float");
        } else if (type.equals(double.class)) {
            return HelperAttributes.attributeName2typeShortForm("Double");
        } else if (type.equals(int.class)) {
            return HelperAttributes.attributeName2typeShortForm("Integer");
        } else if (type.equals(long.class)) {
            return HelperAttributes.attributeName2typeShortForm("Long");
        }
        return null;
    }

    /**
     * Sets a value into any MAL Attribute object. The value provided must be a
     * string. The method takes care of doing the appropriate conversion to fit
     * the correct set type.
     *
     * @param in The object to be set
     * @param value The string value to be used for the set
     * @return The final object with the selected value or null if in == null
     * @throws java.lang.IllegalArgumentException If value == null
     */
    public static Object string2attribute(Object in, String value)
            throws NumberFormatException, IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException("The value must not be null.");
        }
        if (value.isEmpty()) {
            return null;
        }
        if (in instanceof Union) {
            Integer sfp = ((Union) in).getTypeId().getSFP();

            if (sfp.equals(Union.DOUBLE_TYPE_SHORT_FORM)) {
                return new Union(Double.parseDouble(value));
            }

            if (sfp.equals(Union.BOOLEAN_TYPE_SHORT_FORM)) {
                return new Union(Boolean.parseBoolean(value));
            }

            if (sfp.equals(Union.FLOAT_TYPE_SHORT_FORM)) {
                return new Union(Float.parseFloat(value));
            }

            if (sfp.equals(Union.INTEGER_TYPE_SHORT_FORM)) {
                return new Union(Integer.parseInt(value));
            }

            if (sfp.equals(Union.LONG_TYPE_SHORT_FORM)) {
                return new Union(Long.parseLong(value));
            }

            if (sfp.equals(Union.OCTET_TYPE_SHORT_FORM)) {
                return new Union(Byte.parseByte(value));
            }

            if (sfp.equals(Union.SHORT_TYPE_SHORT_FORM)) {
                return new Union(Short.parseShort(value));
            }

            if (sfp.equals(Union.STRING_TYPE_SHORT_FORM)) {
                return new Union(value);
            }

        }

        if (in instanceof Duration) {
            return new Duration(Double.parseDouble(value));
        }

        if (in instanceof UOctet) {
            return new UOctet(Short.parseShort(value));
        }

        if (in instanceof UShort) {
            return new UShort(Integer.parseInt(value));
        }

        if (in instanceof UInteger) {
            return new UInteger(Long.parseLong(value));
        }

        if (in instanceof ULong) {
            return new ULong(new BigInteger(value));
        }

        if (in instanceof Time) {
            return new Time(Long.parseLong(value));
        }

        if (in instanceof FineTime) {
            return new FineTime(Long.parseLong(value));
        }

        if (in instanceof Identifier) {
            return new Identifier(value);
        }

        if (in instanceof URI) {
            return new URI(value);
        }

        if (in instanceof Long) {
            return Long.parseLong(value);
        }

        if (in instanceof Boolean) {
            return Boolean.valueOf(value);
        }

        return null;
    }

    /**
     * @deprecated Use {@link Attribute#javaType2Attribute(Object)} directly.
     */
    @Deprecated
    public static Object javaType2Attribute(Object obj) {
        return Attribute.javaType2Attribute(obj);
    }

    /**
     * Converts a MAL data type into a Java data type
     *
     * @param obj The object in the MAL data type
     * @return The object in the Java data type
     * @deprecated Use {@link Attribute#attribute2JavaType(Object)} directly.
     */
    @Deprecated
    public static Object attribute2JavaType(Object obj) {
        return Attribute.attribute2JavaType(obj);
    }

    /**
     * Generates the correct Element List based on the Java type
     *
     * @param obj The object in the Java data type
     * @return A MAL data type Elements List
     */
    public static ElementList generateElementListFromJavaType(Object obj) {

        if (obj instanceof java.lang.Boolean) {
            return new BooleanList();
        }

        if (obj instanceof java.lang.Integer) {
            return new IntegerList();
        }

        if (obj instanceof java.lang.Long) {
            return new LongList();
        }

        if (obj instanceof java.lang.String) {
            return new StringList();
        }

        if (obj instanceof java.lang.Double) {
            return new DoubleList();
        }

        if (obj instanceof java.lang.Float) {
            return new FloatList();
        }

        if (obj instanceof java.lang.Byte) {
            return new OctetList();
        }

        if (obj instanceof java.lang.Short) {
            return new ShortList();
        }

        return null;
    }

    /**
     * Translates the type short form number into the name of the element
     *
     * @param typeShortForm The type short form number
     * @return The name of the MAL Attribute
     */
    public static String typeShortForm2attributeName(Integer typeShortForm) {

        if (typeShortForm == 1) {
            return "Blob";
        }
        if (typeShortForm == 2) {
            return "Boolean";
        }
        if (typeShortForm == 3) {
            return "Duration";
        }
        if (typeShortForm == 4) {
            return "Float";
        }
        if (typeShortForm == 5) {
            return "Double";
        }
        if (typeShortForm == 6) {
            return "Identifier";
        }
        if (typeShortForm == 7) {
            return "Octet";
        }
        if (typeShortForm == 8) {
            return "UOctet";
        }
        if (typeShortForm == 9) {
            return "Short";
        }
        if (typeShortForm == 10) {
            return "UShort";
        }
        if (typeShortForm == 11) {
            return "Integer";
        }
        if (typeShortForm == 12) {
            return "UInteger";
        }
        if (typeShortForm == 13) {
            return "Long";
        }
        if (typeShortForm == 14) {
            return "ULong";
        }
        if (typeShortForm == 15) {
            return "String";
        }
        if (typeShortForm == 16) {
            return "Time";
        }
        if (typeShortForm == 17) {
            return "FineTime";
        }
        if (typeShortForm == 18) {
            return "URI";
        }
        if (typeShortForm == SERIAL_OBJECT_RAW_TYPE.intValue()) {
            return SERIAL_OBJECT_STRING;
        }

        return "";
    }

    /**
     * Translates the name of the element into the type short form number
     *
     * @param attributeName The name of the MAL Attribute
     * @return The type short form number
     * @throws java.lang.IllegalArgumentException If attributeName == null
     */
    public static Integer attributeName2typeShortForm(String attributeName)
            throws IllegalArgumentException {
        if (attributeName == null) {
            throw new IllegalArgumentException("ArgumentName must not be null.");
        }
        if (attributeName.equals("Blob")) {
            return 1;
        }
        if (attributeName.equals("Boolean")) {
            return 2;
        }
        if (attributeName.equals("Duration")) {
            return 3;
        }
        if (attributeName.equals("Float")) {
            return 4;
        }
        if (attributeName.equals("Double")) {
            return 5;
        }
        if (attributeName.equals("Identifier")) {
            return 6;
        }
        if (attributeName.equals("Octet")) {
            return 7;
        }
        if (attributeName.equals("UOctet")) {
            return 8;
        }
        if (attributeName.equals("Short")) {
            return 9;
        }
        if (attributeName.equals("UShort")) {
            return 10;
        }
        if (attributeName.equals("Integer")) {
            return 11;
        }
        if (attributeName.equals("UInteger")) {
            return 12;
        }
        if (attributeName.equals("Long")) {
            return 13;
        }
        if (attributeName.equals("ULong")) {
            return 14;
        }
        if (attributeName.equals("String")) {
            return 15;
        }
        if (attributeName.equals("Time")) {
            return 16;
        }
        if (attributeName.equals("FineTime")) {
            return 17;
        }
        if (attributeName.equals("URI")) {
            return 18;
        }
        if (attributeName.equals(SERIAL_OBJECT_STRING)) {
            return SERIAL_OBJECT_RAW_TYPE.intValue();
        }

        return null;
    }

    /**
     * Serializes an object and fits it into a Blob attribute
     *
     * @param obj The object to be serialized
     * @return The Blob with the serialized object inside
     * @throws java.io.IOException When the serialization of the object fails
     */
    public static Blob serialObject2blobAttribute(Serializable obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] serialBytesOut = null;

        try {
            out = new ObjectOutputStream(baos);
            out.writeObject(obj);
            serialBytesOut = baos.toByteArray();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
            try {
                baos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }

        return new Blob(serialBytesOut);
    }

    /**
     * Tries to deserialize an object inside a Blob
     *
     * @param obj The object to be serialized
     * @return The deserialized object
     * @throws java.io.IOException When the deserialization of the object fails
     * @throws java.lang.IllegalArgumentException If obj == null
     */
    public static Serializable blobAttribute2serialObject(Blob obj)
            throws IOException, IllegalArgumentException {

        if (obj == null) {
            throw new IllegalArgumentException("The Blob must not be null.");
        }
        ByteArrayInputStream bis = null;
        Object o = null;

        try {
            bis = new ByteArrayInputStream(obj.getValue());
            ObjectInput in = null;
            try {
                in = new ObjectInputStream(bis);
                o = in.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HelperAttributes.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    bis.close();
                } catch (IOException ex) {
                    // ignore close exception
                }
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException ex) {
                    // ignore close exception
                }
            }
        } finally {
            try {
                bis.close();
            } catch (IOException ex) {
                Logger.getLogger(HelperAttributes.class.getName()).log(Level.SEVERE, null, ex);
                // ignore close exception
            }
        }

        return (Serializable) o;
    }
}
