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

import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.BlobList;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ElementList;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests the factory that the generator writes for the MAL area.
 *
 * The factory is a switch over the type numbers, and the generator is free to
 * split that switch over more than one method to keep it compiling to a jump
 * table. These tests hold the switch to what it must answer whatever shape the
 * generator gives it.
 */
public class TestAreaElementFactory {

    private static final int MAL_AREA_NUMBER = 1;

    private static final int MAL_AREA_VERSION = 3;

    /**
     * The type numbers of the MAL reach past 1000, so the sweep has to go
     * further out than that to cover the whole area.
     */
    private static final int SWEEP = 2000;

    private final AreaElementFactory factory = new MALElementFactory();

    private static long typeIdOf(int typeNumber) {
        return new TypeId(MAL_AREA_NUMBER, MAL_AREA_VERSION, 0, typeNumber).getTypeId();
    }

    /**
     * Every Element the factory hands out has to be the type that was asked
     * for. A switch label that names the wrong class, or a type number that
     * reaches the branch of another one, is caught here.
     */
    @Test
    public void createdElementIsOfTheTypeThatWasAskedFor() {
        for (int typeNumber = -SWEEP; typeNumber <= SWEEP; typeNumber++) {
            if (typeNumber == 0) {
                continue; // Not a type number: it stands for a heterogeneous list
            }

            Element element = factory.createElement(0, typeNumber);

            if (element == null) {
                continue; // The MAL does not declare this one
            }

            assertEquals("The factory answered type number " + typeNumber + " with "
                    + element.getClass().getSimpleName(),
                    typeIdOf(typeNumber), element.getTypeId().getTypeId());
        }
    }

    /**
     * A type number that the MAL does not declare has to be answered with
     * nothing, rather than with the Element of a number that lies near it.
     */
    @Test
    public void undeclaredTypeNumberIsAnsweredWithNothing() {
        for (int typeNumber : new int[]{20, 21, 50, 99, 100, 106, 500, 1000, 1011, 1500}) {
            assertNull("Type number " + typeNumber + " is not declared by the MAL",
                    factory.createElement(0, typeNumber));
            assertNull("Type number " + (-typeNumber) + " is not declared by the MAL",
                    factory.createElement(0, -typeNumber));
        }
    }

    /**
     * A negative type number stands for the list of the type that holds the
     * same number, so the two have to answer as a pair.
     */
    @Test
    public void everyTypeIsPairedWithItsList() {
        int pairs = 0;

        for (int typeNumber = 1; typeNumber <= SWEEP; typeNumber++) {
            Element element = factory.createElement(0, typeNumber);
            Element list = factory.createElement(0, -typeNumber);

            if (element == null && list == null) {
                continue;
            }

            assertNotNull("Type number " + typeNumber + " has a list but no type", element);
            assertNotNull("Type number " + typeNumber + " has a type but no list", list);
            assertTrue("Type number " + (-typeNumber) + " is not a list",
                    list instanceof ElementList);
            pairs++;
        }

        // The MAL declares the attributes, the enumerations and the composites
        // that every other area builds on, so this is a floor and not a count.
        assertTrue("Only " + pairs + " types were found in the MAL area", pairs >= 30);
    }

    /**
     * The factory only answers for the types of the area itself, which the MAL
     * addresses with service number zero.
     */
    @Test
    public void onlyTheAreaItselfIsAnsweredFor() {
        assertNull(factory.createElement(1, 1));
        assertNull(factory.createElement(99, 1));
    }

    /**
     * The factory says which area it belongs to, so that it cannot be
     * registered against the wrong number or version.
     */
    @Test
    public void factoryNamesItsOwnArea() {
        assertEquals(MAL_AREA_NUMBER, factory.getAreaNumber());
        assertEquals(MAL_AREA_VERSION, factory.getAreaVersion());
    }

    /**
     * Spot checks at both ends of the area, so that a change which moves the
     * split between the methods of the factory cannot go unnoticed.
     */
    @Test
    public void bothEndsOfTheAreaAreReachable() {
        assertEquals(Blob.class, factory.createElement(0, 1).getClass());
        assertEquals(BlobList.class, factory.createElement(0, -1).getClass());

        // These lie far above the rest, so the generator holds them apart
        assertNotNull(factory.createElement(0, 1001));
        assertNotNull(factory.createElement(0, -1001));
        assertNotNull(factory.createElement(0, 1010));
        assertNotNull(factory.createElement(0, -1010));
    }

    /**
     * The registry has to reach the same Elements through the factory of the
     * area, which is the path every decoded message takes.
     */
    @Test
    public void registryReachesTheFactory() throws Exception {
        MALElementsRegistry registry = new MALElementsRegistry();
        registry.registerAreaFactory(factory);

        for (int typeNumber : new int[]{1, -1, 19, -19, 105, -105, 1001, -1001, 1010, -1010}) {
            Element element = registry.createElement(typeIdOf(typeNumber));
            assertNotNull("The registry did not reach type number " + typeNumber, element);
            assertEquals(typeIdOf(typeNumber), element.getTypeId().getTypeId());
        }
    }
}
