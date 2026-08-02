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
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.BlobList;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ElementList;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
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

    /**
     * Builds a Type Id without going through the TypeId constructor that takes
     * the parts, which narrows the type number to a short. A type number that
     * needs more than that can only be written out in full here.
     */
    private static long rawTypeIdOf(int areaNumber, int areaVersion,
            int serviceNumber, int typeNumber) {
        return ((long) areaNumber << 48) | ((long) serviceNumber << 32)
                | ((long) areaVersion << 24) | (typeNumber & 0xFFFFFFL);
    }

    /**
     * A Type Id of zero stands for a list that holds types of more than one
     * kind, and is answered before any factory is asked.
     */
    @Test
    public void typeIdOfZeroIsAHeterogeneousList() throws Exception {
        MALElementsRegistry registry = new MALElementsRegistry();
        registry.registerAreaFactory(factory);
        assertTrue(registry.createElement(0L) instanceof HeterogeneousList);
    }

    /**
     * A Type Id that no registered factory answers for has to be reported,
     * rather than answered with nothing for the caller to trip over later.
     */
    @Test
    public void aTypeIdThatNoFactoryClaimsIsReported() {
        MALElementsRegistry registry = new MALElementsRegistry();
        registry.registerAreaFactory(factory);

        long[] unclaimed = new long[]{
            rawTypeIdOf(999, 1, 0, 1), // An area that is not registered
            rawTypeIdOf(1, 9, 0, 1), // The MAL area, at a version that is not
            rawTypeIdOf(1, 3, 77, 1), // The MAL area, at a service it has not
            rawTypeIdOf(1, 3, 0, 0), // Zero is not a type number
            rawTypeIdOf(1, 3, 0, 8388607), // The widest a type number can be
            rawTypeIdOf(1, 3, 0, -8388607)
        };

        for (long typeId : unclaimed) {
            try {
                Element element = registry.createElement(typeId);
                fail("Type Id " + typeId + " was answered with " + element);
            } catch (NotFoundException expected) {
                // This is what a Type Id that no factory claims has to give
            } catch (Exception ex) {
                fail("Type Id " + typeId + " gave " + ex);
            }
        }
    }

    /**
     * The type number is read over its full width. A type number written by
     * hand can reach further than the XML schema lets a specification go, and
     * has to arrive at the factory as it was sent.
     */
    @Test
    public void theWidestTypeNumbersArriveWhole() {
        assertEquals(8388607, TypeId.typeNumberOf(rawTypeIdOf(1, 3, 0, 8388607)));
        assertEquals(-8388607, TypeId.typeNumberOf(rawTypeIdOf(1, 3, 0, -8388607)));
        assertEquals(1, TypeId.typeNumberOf(rawTypeIdOf(1, 3, 0, 1)));
        assertEquals(-1010, TypeId.typeNumberOf(rawTypeIdOf(1, 3, 0, -1010)));
    }

    /**
     * An Area can hold more than one factory, so that types written by hand are
     * reached next to the generated ones. A factory that does not know a type
     * has to be asked past rather than end the search.
     */
    @Test
    public void anAreaCanHoldMoreThanOneFactory() throws Exception {
        MALElementsRegistry registry = new MALElementsRegistry();
        registry.registerAreaFactory(factory);
        registry.registerAreaFactory(new HandWrittenTypeFactory());

        // Reached past the generated factory, which does not know this one
        Element handWritten = registry.createElement(rawTypeIdOf(
                MAL_AREA_NUMBER, MAL_AREA_VERSION, 0, HandWrittenTypeFactory.TYPE_NUMBER));
        assertEquals(Identifier.class, handWritten.getClass());

        // The generated types are still reached, and are not shadowed
        assertEquals(Blob.class, registry.createElement(typeIdOf(1)).getClass());
        assertEquals(BlobList.class, registry.createElement(typeIdOf(-1)).getClass());
    }

    /**
     * Registering the same factory again leaves the registry as it was. Every
     * route that loads an Area registers its factory, so the same one arrives
     * here more than once.
     */
    @Test
    public void registeringTheSameFactoryAgainChangesNothing() throws Exception {
        MALElementsRegistry registry = new MALElementsRegistry();
        registry.registerAreaFactory(factory);
        registry.registerAreaFactory(new MALElementFactory());
        registry.registerAreaFactory(factory);
        registry.registerAreaFactory(null);

        assertEquals(Blob.class, registry.createElement(typeIdOf(1)).getClass());
    }

    /**
     * A factory for a type whose number is wider than the XML schema allows,
     * standing for the ones the MAL/SPP testbed writes by hand.
     */
    private static final class HandWrittenTypeFactory implements AreaElementFactory {

        private static final int TYPE_NUMBER = 8388607;

        @Override
        public Element createElement(int serviceNumber, int typeNumber) {
            return (serviceNumber == 0 && typeNumber == TYPE_NUMBER)
                    ? new Identifier("handWritten") : null;
        }

        @Override
        public int getAreaNumber() {
            return MAL_AREA_NUMBER;
        }

        @Override
        public int getAreaVersion() {
            return MAL_AREA_VERSION;
        }
    }
}
