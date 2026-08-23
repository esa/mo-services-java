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
package esa.mo.apigen.exporters.mospec;

import esa.mo.apigen.model.Area;
import esa.mo.apigen.model.Field;
import esa.mo.apigen.model.Service;
import esa.mo.apigen.model.types.TypeDefinition;
import esa.mo.apigen.model.types.TypeRef;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * How a type is written in MOSpec.
 * <p>
 * Type-first, with the list and object-reference wrappers around the name and nullability
 * postfix on the whole thing: {@code List<ObjectRef<Product>>? refs}. The area and service
 * are left off only when both match where the type is being mentioned, so a name is never
 * ambiguous about where it comes from.
 */
public final class MOSpecTypes {

    /**
     * Words the grammar gives a meaning to. An identifier that collides with one is quoted,
     * so that a specification is never prevented from naming something what it likes.
     */
    private static final Set<String> KEYWORDS = new HashSet<String>(Arrays.asList(
            "specification", "area", "service", "capability", "com", "objects", "events",
            "object", "event", "related", "source", "archiveUsage", "activityUsage",
            "diagram", "doc", "composite", "enumeration", "attribute", "fundamental",
            "abstract", "extends", "extended", "error", "errors", "throws", "replayable",
            "send", "submit", "request", "invoke", "progress", "pubsub", "subscriptionKeys"));

    private MOSpecTypes() {
    }

    /**
     * Writes a type as it is referred to from somewhere.
     *
     * @param area The area doing the referring.
     * @param service The service doing the referring, or null at area level.
     * @param reference The type referred to.
     * @return the type, written.
     */
    public static String of(Area area, Service service, TypeRef reference) {
        TypeRef ref = reference.unwrapped();
        StringBuilder buf = new StringBuilder(qualified(area, service, ref));
        if (ref.isObjectRef()) {
            buf.insert(0, "ObjectRef<").append('>');
        }
        if (ref.isList()) {
            buf.insert(0, "List<").append('>');
        }
        return buf.toString();
    }

    /**
     * Writes a field: its type, then its name, with the nullable marker between them.
     *
     * @param area The area the field is declared in.
     * @param service The service it is declared in, or null at area level.
     * @param field The field.
     * @return the declaration.
     */
    public static String field(Area area, Service service, Field field) {
        return of(area, service, field.getType())
                + (field.isCanBeNull() ? "? " : " ") + identifier(field.getName());
    }

    /**
     * Writes a name.
     * <p>
     * Quoted where the grammar would otherwise read it as a keyword, and where it is not a
     * bare name at all: the MAL's own errors are called things like "Authentication Failed",
     * and a specification is not going to be told what it may call things.
     *
     * @param name An identifier out of a specification.
     * @return the name, quoted where it has to be.
     */
    public static String identifier(String name) {
        if (name == null) {
            return null;
        }
        return KEYWORDS.contains(name) || !isBare(name) ? "\"" + name + "\"" : name;
    }

    /**
     * @return true if the name is one the lexer would read as a single word.
     */
    private static boolean isBare(String name) {
        if (name.isEmpty() || !(Character.isLetter(name.charAt(0)) || name.charAt(0) == '_')) {
            return false;
        }
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (!Character.isLetterOrDigit(c) && c != '_') {
                return false;
            }
        }
        return true;
    }

    /**
     * Names a type from where it is mentioned.
     * <p>
     * The area is left off when it is the area doing the mentioning, and the service
     * likewise - a type of another service in the same area still says which service. MAL
     * is left off as well: its types are named in every specification and the language
     * imports them implicitly, exactly as the format it descends from did. The one case
     * that would be ambiguous - an area declaring a type of its own with the same name as a
     * MAL type - is written out in full.
     */
    /**
     * @return true if the area or the service being written declares a type of this name,
     * in which case an unqualified name would mean that one rather than the MAL's.
     */
    private static boolean declaresLocally(Area area, Service service, String name) {
        if (area != null) {
            for (TypeDefinition type : area.getDataTypes()) {
                if (name.equals(type.getName())) {
                    return true;
                }
            }
        }
        if (service != null) {
            for (TypeDefinition type : service.getDataTypes()) {
                if (name.equals(type.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String qualified(Area area, Service service, TypeRef ref) {
        StringBuilder buf = new StringBuilder();
        boolean sameArea = area != null && area.getName().equals(ref.getArea());
        boolean implicitMal = !sameArea && "MAL".equals(ref.getArea())
                && !declaresLocally(area, service, ref.getName());
        if (!sameArea && !implicitMal) {
            buf.append(ref.getArea()).append("::");
        }
        if (ref.getService() != null && !ref.getService().isEmpty()) {
            boolean sameService = sameArea && service != null
                    && ref.getService().equals(service.getName());
            if (!sameService) {
                buf.append(ref.getService()).append('.');
            }
        }
        return buf.append(identifier(ref.getName())).toString();
    }
}
