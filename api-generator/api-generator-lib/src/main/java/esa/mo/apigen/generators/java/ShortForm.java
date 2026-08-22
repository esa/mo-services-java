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
 * The absolute short form of a type: the number that identifies it on the wire.
 * <p>
 * Area number, service number, area version and the type's own short form part, packed
 * into one long.
 */
public final class ShortForm {

    private ShortForm() {
    }

    /**
     * Computes the absolute short form.
     *
     * @param area The area defining the type.
     * @param service The service defining it, or null for an area-level type.
     * @param shortFormPart The type's short form part.
     * @return the absolute short form.
     */
    public static long of(Area area, Service service, long shortFormPart) {
        long serviceNumber = service == null ? 0 : service.getNumber();
        return ((long) area.getNumber() << 48)
                + (serviceNumber << 32)
                + ((long) area.getVersion() << 24)
                + shortFormPart;
    }

    /**
     * Writes the three fields that carry a type's identity: the version the class was
     * serialised under, the short form as a Long, and the TypeId built from it. Composites,
     * enumerations and lists all open with them, written as one block with no blank line
     * between them.
     *
     * @param out The source to write to.
     * @param shortForm The absolute short form of the type.
     */
    public static void writeIdentity(JavaSource out, long shortForm) {
        JavaFieldBuilder.named("serialVersionUID").scope("private").asStatic().asFinal()
                .ofType("long").value(shortForm + "L")
                .write(out);
        JavaFieldBuilder.named("SHORT_FORM").asStatic().asFinal()
                .ofType("Long").value(shortForm + "L")
                .comment("The TypeId of this Element as a long.")
                .joinedToPrevious().write(out);
        JavaFieldBuilder.named("TYPE_ID").asStatic().asFinal()
                .ofType(JavaNaming.MAL + "TypeId")
                .value("new " + JavaNaming.MAL + "TypeId(SHORT_FORM)")
                .comment("The TypeId of this Element.")
                .joinedToPrevious().write(out);
    }
}
