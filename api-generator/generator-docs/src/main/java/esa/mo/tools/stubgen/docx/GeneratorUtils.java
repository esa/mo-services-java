/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Service Stub Generator
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
package esa.mo.tools.stubgen.docx;

import esa.mo.xsd.AreaType;
import esa.mo.xsd.ObjectReference;
import esa.mo.xsd.ServiceType;
import esa.mo.xsd.TypeReference;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Cesar.Coelho
 */
public class GeneratorUtils {

    public static String yesNoType(boolean bool) throws IOException {
        return (bool) ? "Yes" : "No";
    }

    public static String createFQTypeName(AreaType area, ServiceType service, TypeReference type) {
        return createFQTypeName(area, service, type, type.isList());
    }

    public static String createFQTypeName(AreaType area, ServiceType service, TypeReference type, boolean isList) {
        String servicename = (null == service) ? "" : service.getName();
        return createFQTypeName(area.getName(), servicename, type.getArea(),
                type.getService(), type.getName(), isList);
    }

    public static String createFQTypeName(AreaType area, ServiceType service, ObjectReference type) {
        String servicename = (null == service) ? "" : service.getName();
        return createFQTypeName(area.getName(), servicename, type.getArea(),
                type.getService(), String.valueOf(type.getNumber()), false);
    }

    public static String createFQTypeName(String myArea, String myService,
            String typeArea, String typeService, String typeName, boolean isList) {
        StringBuilder buf = new StringBuilder();

        if (!myArea.equalsIgnoreCase(typeArea)) {
            buf.append(typeArea);
            buf.append("::");
        }

        if ((typeService != null) && (typeService.length() > 0 && !typeService.equalsIgnoreCase(myService))) {
            buf.append(typeService);
            buf.append("::");
        }

        buf.append(typeName);

        if (isList) {
            buf.insert(0, "List<");
            buf.append(">");
        }

        return buf.toString();
    }

    /**
     * Splits a supplied string on any CRs or double spaces.
     *
     * @param srcArr Source string array to append to.
     * @param str String to split.
     * @return The updated source array.
     */
    public static List<String> splitString(List<String> srcArr, String str) {
        if (srcArr == null) {
            srcArr = new LinkedList<>();
        }
        if (str != null) {
            srcArr.addAll(Arrays.asList(str.split("(  |\n)")));
        }
        return srcArr;
    }
}
