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
package esa.mo.apigen.generators.xhtml;

import esa.mo.apigen.model.Area;
import esa.mo.apigen.model.MOModel;
import esa.mo.apigen.model.Service;
import esa.mo.apigen.model.types.TypeDefinition;
import esa.mo.apigen.model.types.TypeRef;

/**
 * Names the pages and the anchors within them, and links one to another.
 * <p>
 * Every anchor is {@code service_name}, or {@code _name} for something an area declares
 * itself, so an anchor is unique within its page whether or not two services name a type
 * the same way.
 * <p>
 * A link is only written when the target is known to exist. The generator this replaces
 * wrote one for every type it mentioned, so a list type linked to an anchor no page ever
 * defines - seven of them across the standards, {@code #_ElementList} among them, because
 * the name it linked to was the name of the list rather than of the type in it.
 */
public final class XhtmlLink {

    private XhtmlLink() {
    }

    /**
     * Names the page of an area, after the specification it is generated from.
     * <p>
     * The number and the version are part of the name because the name alone is not an
     * identity: MAL is published at version 1 and version 3, Monitor and Control at 1 and
     * 2, and the generator this replaces wrote both of each pair to {@code outputMAL.xhtml}
     * and {@code outputMC.xhtml}, so whichever specification was read second was the only
     * one that survived.
     *
     * @param area The area the page describes.
     * @return the file name of its page.
     */
    public static String pageOf(Area area) {
        return String.format("area%03d-v%03d-%s.xhtml",
                area.getNumber(), area.getVersion(), area.getName());
    }

    /**
     * The anchor of something a page describes.
     *
     * @param service The service that declares it, or null if the area does.
     * @param name The name of the thing, or null for the top of the page.
     * @return the anchor, without its '#'.
     */
    public static String anchorOf(Service service, String name) {
        return (service == null ? "" : service.getName()) + "_" + (name == null ? "" : name);
    }

    /**
     * Links to a type from the page of an area.
     * <p>
     * A list is drawn as a count and the type in it, so what is linked to is the type in
     * it: {@code List<UpdateHeader>} resolves to wherever UpdateHeader is described.
     *
     * @param model Every loaded specification.
     * @param from The area whose page is being written.
     * @param type The type to link to.
     * @return the href, or null if nothing loaded declares the type - in which case the
     * name is written without a link rather than linking nowhere.
     */
    public static String hrefTo(MOModel model, Area from, TypeRef type) {
        if (type == null) {
            return null;
        }
        TypeRef target = type.unwrapped();
        TypeDefinition declaration = model.resolve(target);
        if (declaration == null) {
            return null;
        }
        Area declaring = declaration.getArea();
        String anchor = anchorOf(declaration.getService(), declaration.getName());
        if (declaring == from) {
            return "#" + anchor;
        }
        return pageOf(declaring) + "#" + anchor;
    }

    /**
     * Links to something on the page being written.
     *
     * @param service The service that declares it, or null if the area does.
     * @param name The name of the thing.
     * @return the href.
     */
    public static String hrefWithin(Service service, String name) {
        return "#" + anchorOf(service, name);
    }
}
