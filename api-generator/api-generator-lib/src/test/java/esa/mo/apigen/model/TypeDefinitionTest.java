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

import esa.mo.apigen.model.types.AttributeType;
import esa.mo.apigen.model.types.CompositeType;
import esa.mo.apigen.model.types.FundamentalType;
import static org.junit.Assert.*;
import org.junit.Test;

public class TypeDefinitionTest {

    /**
     * Absence of a short form part is the only thing that makes a composite abstract. The
     * schema says so outright, and 0 was never a legal value - the type is restricted to
     * 1 and above in every schema version - so nothing should treat 0 as a marker.
     */
    @Test
    public void aCompositeIsAbstractOnlyWhenItHasNoShortFormPart() {
        CompositeType abstractComposite = new CompositeType();
        assertNull(abstractComposite.getShortFormPart());
        assertTrue(abstractComposite.isAbstract());

        CompositeType concrete = new CompositeType();
        concrete.setShortFormPart(7);
        assertFalse(concrete.isAbstract());

        CompositeType zero = new CompositeType();
        zero.setShortFormPart(0);
        assertFalse("0 is not a marker for abstract", zero.isAbstract());
    }

    @Test
    public void fundamentalTypesAreAlwaysAbstract() {
        assertTrue(new FundamentalType().isAbstract());
    }

    @Test
    public void attributeTypesAreNeverAbstract() {
        assertFalse(new AttributeType().isAbstract());
    }
}
