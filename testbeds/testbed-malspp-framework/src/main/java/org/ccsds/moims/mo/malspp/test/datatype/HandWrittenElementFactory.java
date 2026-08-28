/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL/SPP Test bed
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
package org.ccsds.moims.mo.malspp.test.datatype;

import org.ccsds.moims.mo.mal.AreaElementFactory;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.malprototype.MALPrototypeHelper;

/**
 * Creates the Elements of the MAL Prototype area that are written by hand
 * rather than generated.
 *
 * The type numbers of these two reach the top of the range that a Type Id can
 * carry, which is further than the XML schema lets a specification go, so they
 * cannot be declared in XML and no generated factory knows about them. This one
 * is registered alongside the generated factory of the same area, which is
 * asked first and answers with nothing for these two.
 */
public class HandWrittenElementFactory implements AreaElementFactory {

    @Override
    public Element createElement(int serviceNumber, int typeNumber) {
        if (serviceNumber != 0) {
            return null; // Both are declared by the area itself
        }

        if (typeNumber == LargeEnumeration.TYPE_SHORT_FORM) {
            return new LargeEnumeration(0);
        }

        if (typeNumber == MediumEnumeration.TYPE_SHORT_FORM) {
            return new MediumEnumeration(0);
        }

        return null;
    }

    @Override
    public int getAreaNumber() {
        return MALPrototypeHelper._MALPROTOTYPE_AREA_NUMBER;
    }

    @Override
    public int getAreaVersion() {
        return MALPrototypeHelper._MALPROTOTYPE_AREA_VERSION;
    }
}
