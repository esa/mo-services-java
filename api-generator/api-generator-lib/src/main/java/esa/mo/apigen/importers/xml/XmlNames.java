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
package esa.mo.apigen.importers.xml;

/**
 * Namespaces and element names of the service schema.
 */
public final class XmlNames {

    public static final String MAL_V001 = "http://www.ccsds.org/schema/ServiceSchema";
    public static final String MAL_V003 = "http://www.ccsds.org/schema/ServiceSchema-v003";
    public static final String COM = "http://www.ccsds.org/schema/COMSchema";
    public static final String SVG = "http://www.w3.org/2000/svg";
    public static final String XSI = "http://www.w3.org/2001/XMLSchema-instance";

    private XmlNames() {
    }

    /**
     * Returns true if the namespace is one of the service schema versions.
     *
     * @param namespace The namespace URI.
     * @return true if it is a MAL service schema namespace.
     */
    public static boolean isMal(String namespace) {
        return MAL_V001.equals(namespace) || MAL_V003.equals(namespace);
    }

    /**
     * Returns true if the namespace is the COM schema.
     *
     * @param namespace The namespace URI.
     * @return true if it is the COM namespace.
     */
    public static boolean isCom(String namespace) {
        return COM.equals(namespace);
    }
}
