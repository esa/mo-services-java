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

import esa.mo.apigen.model.types.TypeRef;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * The area version is part of a type reference's identity. If it were not, the same type
 * name in two versions of an area would compare equal while resolving to different
 * definitions - which is why the version is carried in the reference rather than supplied
 * at each lookup.
 */
public class TypeRefTest {

    private static TypeRef malBlob(int version) {
        return new TypeRef("MAL", version, null, "Blob", false, false);
    }

    @Test
    public void sameNameDifferentAreaVersionIsNotTheSameType() {
        assertNotEquals(malBlob(1), malBlob(3));
        assertNotEquals(malBlob(1).hashCode(), malBlob(3).hashCode());
    }

    @Test
    public void identicalReferencesAreEqualAndHashAlike() {
        assertEquals(malBlob(3), malBlob(3));
        assertEquals(malBlob(3).hashCode(), malBlob(3).hashCode());
    }

    @Test
    public void listAndObjectRefChangeIdentity() {
        TypeRef plain = new TypeRef("MPS", 1, null, "RequestInstance", false, false);
        TypeRef list = new TypeRef("MPS", 1, null, "RequestInstance", true, false);
        TypeRef objRef = new TypeRef("MPS", 1, null, "RequestInstance", false, true);
        assertNotEquals(plain, list);
        assertNotEquals(plain, objRef);
        assertNotEquals(list, objRef);
    }

    @Test
    public void serviceQualifierIsPartOfIdentity() {
        TypeRef areaLevel = new TypeRef("MC", 1, null, "Foo", false, false);
        TypeRef serviceLevel = new TypeRef("MC", 1, "Action", "Foo", false, false);
        assertNotEquals(areaLevel, serviceLevel);
    }

    @Test
    public void linkingFillsInTheVersionAndChangesNothingElse() {
        TypeRef unlinked = new TypeRef("MAL", 0, null, "Blob", true, false);
        TypeRef linked = unlinked.withAreaVersion(3);
        assertEquals(0, unlinked.getAreaVersion());
        assertEquals(3, linked.getAreaVersion());
        assertEquals(unlinked.getName(), linked.getName());
        assertTrue(linked.isList());
    }
}
