/* ----------------------------------------------------------------------------
 * Copyright (C) 2026      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO API Generator
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
package esa.mo.apigen.generators.java;

import java.util.HashMap;
import java.util.Map;

/**
 * How the MAL's attribute types are represented in Java.
 * <p>
 * The model takes the attribute types themselves from the MAL specification; this holds
 * only the Java side of the mapping, which belongs to this generator and nowhere else.
 * <p>
 * Seven of them are plain Java types rather than generated classes, and the name is not
 * always the MAL one - an Octet is a {@code Byte}.
 */
public final class JavaTypes {

    private static final Map<String, String> NATIVE = new HashMap<String, String>();

    static {
        NATIVE.put("Boolean", "Boolean");
        NATIVE.put("Double", "Double");
        NATIVE.put("Float", "Float");
        NATIVE.put("Integer", "Integer");
        NATIVE.put("Long", "Long");
        NATIVE.put("Octet", "Byte");
        NATIVE.put("Short", "Short");
        NATIVE.put("String", "String");
    }

    /**
     * The value a native attribute is created with where one has to be created without
     * knowing anything about it - the Union that carries it has no empty constructor.
     */
    private static final Map<String, String> NATIVE_DEFAULT = new HashMap<String, String>();

    static {
        NATIVE_DEFAULT.put("Boolean", "Boolean.FALSE");
        NATIVE_DEFAULT.put("Double", "Double.MAX_VALUE");
        NATIVE_DEFAULT.put("Float", "Float.MAX_VALUE");
        NATIVE_DEFAULT.put("Integer", "Integer.MAX_VALUE");
        NATIVE_DEFAULT.put("Long", "Long.MAX_VALUE");
        NATIVE_DEFAULT.put("Octet", "Byte.MAX_VALUE");
        NATIVE_DEFAULT.put("Short", "Short.MAX_VALUE");
        NATIVE_DEFAULT.put("String", "\"\"");
    }

    /**
     * MAL types whose Java class is named differently, to avoid colliding with a Java
     * built-in: MAL's Object would otherwise shadow java.lang.Object.
     */
    private static final Map<String, String> RENAMED = new HashMap<String, String>();

    static {
        RENAMED.put("Object", "MOObject");
        RENAMED.put("ElementList", "HeterogeneousList");
    }

    private JavaTypes() {
    }

    /**
     * Returns the Java class name for a MAL type, which is usually its own name.
     *
     * @param malArea The area the type belongs to.
     * @param typeName The MAL type name.
     * @return the Java class name.
     */
    public static String className(String malArea, String typeName) {
        if ("MAL".equals(malArea) && RENAMED.containsKey(typeName)) {
            return RENAMED.get(typeName);
        }
        return typeName;
    }

    /**
     * Returns true if the MAL attribute type is a plain Java type rather than a class this
     * generator produces.
     *
     * @param malArea The area the type belongs to.
     * @param typeName The MAL type name.
     * @return true if it maps to a Java built-in.
     */
    public static boolean isNative(String malArea, String typeName) {
        return "MAL".equals(malArea) && NATIVE.containsKey(typeName);
    }

    /**
     * Returns the Java name of a native MAL attribute type.
     *
     * @param typeName The MAL type name.
     * @return the Java type name.
     */
    public static String nativeName(String typeName) {
        return NATIVE.get(typeName);
    }

    /**
     * Returns the value a native MAL attribute type is created with when nothing is known
     * about the value it is going to carry.
     *
     * @param typeName The MAL type name.
     * @return the Java expression of the default value.
     */
    public static String nativeDefault(String typeName) {
        return NATIVE_DEFAULT.get(typeName);
    }
}
