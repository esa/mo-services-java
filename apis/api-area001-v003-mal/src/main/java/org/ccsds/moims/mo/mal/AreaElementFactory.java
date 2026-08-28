/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
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
package org.ccsds.moims.mo.mal;

import org.ccsds.moims.mo.mal.structures.Element;

/**
 * Creates the Elements of one Area.
 *
 * One of these is generated for each Area, and answers with a new Element for a
 * service number and a type number of that Area. The generated implementation
 * is a switch on the service number, and then a switch on the type number, so
 * that the class of a type is only ever loaded when a message actually carries
 * that type. Holding an instance of every type instead, only to be able to ask
 * it for a copy of itself, made every one of them load at start up.
 */
public interface AreaElementFactory {

    /**
     * Returns a new Element of the given type, or null if this Area does not
     * define it.
     *
     * @param serviceNumber The service the type belongs to, or 0 for the types
     * defined by the Area itself.
     * @param typeNumber The type number, negative for the list of a type.
     * @return A new Element, or null if this Area does not define that type.
     */
    Element createElement(int serviceNumber, int typeNumber);

    /**
     * Returns the number of the Area this factory creates the Elements of.
     *
     * @return The Area number.
     */
    int getAreaNumber();

    /**
     * Returns the version of the Area this factory creates the Elements of.
     *
     * @return The Area version.
     */
    int getAreaVersion();
}
