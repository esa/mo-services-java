/* ----------------------------------------------------------------------------
 * Copyright (C) 2014      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO COM Support library
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
package org.ccsds.moims.mo.com.test.util;

import org.ccsds.moims.mo.com.structures.ObjectType;

/**
 * Helper class for COM services.
 */
public class ComStructureHelper {

    private ComStructureHelper() {
        // hides the default constructor.
    }

    /**
     * Generate a EntityKey sub key using fields as specified in COM STD
     * 3.2.4.2b
     *
     * @param area The area.
     * @param service The service.
     * @param version The version.
     * @param objectNumber The object number.
     * @return The short form part of the object.
     */
    public static Long generateSubKey(int area, int service, int version, int objectNumber) {
        long subkey = objectNumber;
        subkey = subkey | (((long) version) << 24);
        subkey = subkey | ((long) service << 32);
        subkey = subkey | ((long) area << 48);

        return subkey;
    }

    /**
     * Generate a EntityKey sub key using fields as specified in COM STD
     * 3.2.4.2b
     *
     * @param objectType The object type.
     * @return The short form part of the object.
     */
    public static Long generateSubKey(ObjectType objectType) {
        return generateSubKey(objectType.getArea().getValue(),
                objectType.getService().getValue(),
                objectType.getVersion().getValue(),
                objectType.getNumber().getValue());
    }
}
