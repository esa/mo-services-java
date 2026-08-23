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

import esa.mo.apigen.model.com.COMObject;
import esa.mo.apigen.model.com.ObjectReference;
import esa.mo.apigen.model.types.CompositeType;
import esa.mo.apigen.model.types.TypeDefinition;
import esa.mo.apigen.model.types.TypeRef;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Every loaded specification, with an index over their areas.
 * <p>
 * The model captures what the specifications say and nothing about what anyone intends to
 * do with them: a build loads more specifications than it generates, but the difference is
 * the caller's business, not the model's.
 */
public final class MOModel {

    private final List<Specification> specifications = new ArrayList<Specification>();
    private final Map<AreaKey, Area> byKey = new LinkedHashMap<AreaKey, Area>();
    private final Map<String, List<Area>> byName = new LinkedHashMap<String, List<Area>>();
    private final List<Area> conflicting = new ArrayList<Area>();

    /**
     * Adds a specification and indexes its areas.
     * <p>
     * An area whose identity is already present is still added to the model, but is
     * recorded as conflicting rather than replacing the one already there - silently
     * overwriting is exactly the failure this index exists to prevent.
     * <p>
     * The same file arriving twice is not a conflict. A module commonly names its own
     * specification among its references as well as its target, and the two copies say the
     * same thing; only the first is indexed, and the second is left out of the index
     * entirely so that a lookup by name and version stays unambiguous.
     *
     * @param specification The specification to add.
     */
    public void add(Specification specification) {
        specifications.add(specification);
        for (Area area : specification.getAreas()) {
            AreaKey key = area.getKey();
            Area alreadyThere = byKey.get(key);

            if (alreadyThere != null) {
                if (!isSameFile(alreadyThere, area)) {
                    conflicting.add(area);
                }
                continue;
            }

            byKey.put(key, area);
            List<Area> sameName = byName.get(area.getName());
            if (sameName == null) {
                sameName = new ArrayList<Area>();
                byName.put(area.getName(), sameName);
            }
            sameName.add(area);
        }
    }

    /**
     * @return true if both areas were read from a file of the same name, which is what a
     * specification named twice looks like.
     */
    private static boolean isSameFile(Area one, Area other) {
        String first = sourceNameOf(one);
        String second = sourceNameOf(other);
        return first != null && first.equals(second);
    }

    private static String sourceNameOf(Area area) {
        if (area.getSpecification() == null || area.getSpecification().getSource() == null) {
            return null;
        }
        return area.getSpecification().getSource().getName();
    }

    public List<Specification> getSpecifications() {
        return Collections.unmodifiableList(specifications);
    }

    /**
     * @return every indexed area, in load order.
     */
    public List<Area> getAreas() {
        return Collections.unmodifiableList(new ArrayList<Area>(byKey.values()));
    }

    /**
     * Returns the areas that could not be indexed because their identity was already
     * taken. Empty in a healthy model; the validator reports whatever is here.
     *
     * @return the conflicting areas.
     */
    public List<Area> getConflictingAreas() {
        return Collections.unmodifiableList(conflicting);
    }

    /**
     * Returns the area with exactly this identity.
     *
     * @param key The identity to look for.
     * @return the area, or null if no such area is loaded.
     */
    public Area findArea(AreaKey key) {
        return byKey.get(key);
    }

    /**
     * Returns every loaded area with the given name. More than one is possible: the
     * specifications use the same name under different numbers.
     *
     * @param name The area name.
     * @return the matching areas, never null.
     */
    public List<Area> findAreas(String name) {
        List<Area> found = byName.get(name);
        return found == null ? Collections.<Area>emptyList() : Collections.unmodifiableList(found);
    }

    /**
     * Returns the one area with this name and version.
     *
     * @param name The area name.
     * @param version The area version.
     * @return the area, or null if there is no such area or more than one - an ambiguous
     * reference cannot be resolved, and saying so is better than guessing.
     */
    public Area findArea(String name, int version) {
        Area match = null;
        for (Area area : findAreas(name)) {
            if (area.getVersion() == version) {
                if (match != null) {
                    return null;
                }
                match = area;
            }
        }
        return match;
    }

    /**
     * Returns the one area with this name, whatever its version. Used while linking, to
     * discover the version an unversioned reference must mean.
     *
     * @param name The area name.
     * @return the area, or null if there is no such area or more than one.
     */
    public Area findUniqueArea(String name) {
        List<Area> found = findAreas(name);
        return found.size() == 1 ? found.get(0) : null;
    }

    /**
     * Resolves a type reference to the type it names.
     *
     * @param reference The reference, already linked.
     * @return the type definition, or null if it cannot be resolved.
     */
    public TypeDefinition resolve(TypeRef reference) {
        if (reference == null) {
            return null;
        }
        TypeRef ref = reference.unwrapped();
        Area area = findArea(ref.getArea(), ref.getAreaVersion());
        if (area == null) {
            return null;
        }
        if (ref.getService() == null) {
            return findType(area.getDataTypes(), ref.getName());
        }
        Service service = area.getService(ref.getService());
        return service == null ? null : findType(service.getDataTypes(), ref.getName());
    }

    /**
     * Returns the fields a composite inherits, outermost ancestor first, followed by the
     * ones its immediate parent declares.
     * <p>
     * A composite extending the MAL's Object contributes one field that is not written
     * anywhere: the schema declares Object as a fundamental, so it has no fields to read,
     * but an MO Object is by definition something with an identity. That identity is
     * named here rather than in each generator that has to show it.
     *
     * @param composite The composite whose ancestry is wanted.
     * @return the inherited fields, empty if it extends nothing but Composite.
     */
    public List<Field> inheritedFields(CompositeType composite) {
        List<Field> inherited = new ArrayList<Field>();
        TypeRef parent = composite.getSuperType();

        if (parent == null || "Composite".equals(parent.getName())) {
            return inherited;
        }
        if ("MAL".equals(parent.getArea()) && "Object".equals(parent.getName())) {
            Field identity = new Field();
            identity.setName("objectIdentity");
            identity.setComment("The identity of the MO Object.");
            identity.setCanBeNull(false);
            identity.setType(new TypeRef("MAL", parent.getAreaVersion(), null,
                    "ObjectIdentity", false, false));
            inherited.add(identity);
            return inherited;
        }

        TypeDefinition resolved = resolve(parent);
        if (resolved instanceof CompositeType) {
            CompositeType above = (CompositeType) resolved;
            inherited.addAll(inheritedFields(above));
            inherited.addAll(above.getFields());
        }
        return inherited;
    }

    private static TypeDefinition findType(List<TypeDefinition> types, String name) {
        for (TypeDefinition type : types) {
            if (type.getName() != null && type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }

    /**
     * Resolves a reference to the error it names.
     * <p>
     * Errors are not types, so they are looked up separately. The name is qualified by
     * the area alone: the schema requires error names to be unique across a whole
     * specification, and the specifications rely on that - an error declared inside a
     * service is referred to by its area, with no service named. So both the area's own
     * errors and those of every service in it are searched.
     *
     * @param ref The reference, already linked.
     * @return the error definition, or null if it cannot be resolved.
     */
    public ErrorDefinition resolveError(TypeRef ref) {
        if (ref == null) {
            return null;
        }
        Area area = findArea(ref.getArea(), ref.getAreaVersion());
        if (area == null) {
            return null;
        }
        ErrorDefinition found = findError(area.getErrors(), ref.getName());
        if (found != null) {
            return found;
        }
        for (Service service : area.getServices()) {
            found = findError(service.getErrors(), ref.getName());
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    private static ErrorDefinition findError(List<ErrorDefinition> errors, String name) {
        for (ErrorDefinition error : errors) {
            if (error.getName() != null && error.getName().equals(name)) {
                return error;
            }
        }
        return null;
    }

    /**
     * Resolves a COM object reference to the object it names.
     *
     * @param ref The reference, already linked.
     * @return the COM object or event, or null if it cannot be resolved.
     */
    public COMObject resolve(ObjectReference ref) {
        if (ref == null) {
            return null;
        }
        Area area = findArea(ref.getArea(), ref.getAreaVersion());
        if (area == null) {
            return null;
        }
        Service service = area.getService(ref.getService());
        if (service == null || service.getCom() == null) {
            return null;
        }
        COMObject found = findObject(service.getCom().getObjects(), ref.getNumber());
        return found != null ? found : findObject(service.getCom().getEvents(), ref.getNumber());
    }

    private static COMObject findObject(List<COMObject> objects, int number) {
        for (COMObject object : objects) {
            if (object.getNumber() == number) {
                return object;
            }
        }
        return null;
    }
}
