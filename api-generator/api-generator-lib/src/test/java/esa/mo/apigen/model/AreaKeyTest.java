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
package esa.mo.apigen.model;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Area identity needs all three of name, number and version. The specifications contain
 * areas sharing a name under different numbers, and a number under different names.
 */
public class AreaKeyTest {

    @Test
    public void sameNameDifferentNumberIsADifferentArea() {
        // MC is area 4; Edge Monitor and Control is also called MC, but is area 104.
        assertNotEquals(new AreaKey("MC", 4, 1), new AreaKey("MC", 104, 1));
    }

    @Test
    public void sameNumberDifferentNameIsADifferentArea() {
        // MPD and 'distribution' both declare area number 9.
        assertNotEquals(new AreaKey("MPD", 9, 1), new AreaKey("distribution", 9, 1));
    }

    @Test
    public void sameNameAndNumberDifferentVersionIsADifferentArea() {
        assertNotEquals(new AreaKey("MAL", 1, 1), new AreaKey("MAL", 1, 3));
    }

    @Test
    public void identicalKeysAreEqualAndHashAlike() {
        assertEquals(new AreaKey("MAL", 1, 3), new AreaKey("MAL", 1, 3));
        assertEquals(new AreaKey("MAL", 1, 3).hashCode(), new AreaKey("MAL", 1, 3).hashCode());
    }
}
