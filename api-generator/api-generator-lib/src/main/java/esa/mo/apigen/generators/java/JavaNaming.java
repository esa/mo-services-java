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

import esa.mo.apigen.model.Area;
import esa.mo.apigen.model.Service;

/**
 * Where generated Java lands: package names and their directories.
 */
public final class JavaNaming {

    /**
     * The root package of every generated API.
     */
    public static final String ROOT = "org.ccsds.moims.mo.";
    /**
     * The folder that holds an area's or a service's data types.
     */
    public static final String STRUCTURES = "structures";
    /**
     * The MAL area's package. Generated code refers to it constantly - every element,
     * encoder and type id lives there - so it is named once here.
     */
    public static final String MAL = ROOT + "mal.";
    /**
     * The package holding the MAL's own data types.
     */
    public static final String MAL_STRUCTURES = MAL + STRUCTURES + ".";
    /**
     * The folder that holds a service's consumer stubs.
     */
    public static final String CONSUMER = "consumer";
    /**
     * The folder that holds a service's provider skeletons.
     */
    public static final String PROVIDER = "provider";

    private JavaNaming() {
    }

    public static String packageOf(Area area) {
        return ROOT + area.getName().toLowerCase();
    }

    public static String packageOf(Area area, String folder) {
        return packageOf(area) + "." + folder;
    }

    public static String packageOf(Service service) {
        return packageOf(service.getArea()) + "." + service.getName().toLowerCase();
    }

    public static String packageOf(Service service, String folder) {
        return packageOf(service) + "." + folder;
    }

    /**
     * Returns the directory a package maps to, relative to the output root.
     *
     * @param packageName The package.
     * @return the relative path, using '/' as separator.
     */
    public static String directoryOf(String packageName) {
        return packageName.replace('.', '/');
    }
}
