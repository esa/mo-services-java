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
package esa.mo.apigen.model.types;

/**
 * A reference to a type, resolved at link time.
 * <p>
 * The XML form carries no version - {@code <mal:type area="MAL" name="Blob"/>} does not
 * say which MAL - so the importer fills {@code areaVersion} in from the area actually
 * loaded. Keeping it inside the reference is what lets the same name in two versions of
 * an area compare unequal, and is why resolution needs no lookup context. See the design
 * document, section 4.1.
 * <p>
 * {@code service} exists only in v001 specifications, {@code objectRef} only in v003.
 */
public final class TypeRef {

    private final String area;
    private final int areaVersion;
    private final String service;
    private final String name;
    private final boolean list;
    private final boolean objectRef;

    public TypeRef(String area, int areaVersion, String service, String name,
            boolean list, boolean objectRef) {
        this.area = area;
        this.areaVersion = areaVersion;
        this.service = service;
        this.name = name;
        this.list = list;
        this.objectRef = objectRef;
    }

    public String getArea() {
        return area;
    }

    /**
     * @return the version of the referenced area, or 0 if it has not been linked yet.
     */
    public int getAreaVersion() {
        return areaVersion;
    }

    /**
     * @return the service that owns the type, or null for an area-level type.
     */
    public String getService() {
        return service;
    }

    public String getName() {
        return name;
    }

    public boolean isList() {
        return list;
    }

    public boolean isObjectRef() {
        return objectRef;
    }

    /**
     * Returns the reference this one really names, unwrapping the older spelling of an
     * object reference.
     * <p>
     * Most specifications mark an object reference with {@code objectRef="true"}, but the
     * Mission Product Distribution area writes the target into the name instead, as
     * {@code ObjectRef(Product)}. Both mean the same thing.
     *
     * @return this reference, or the reference it spells out.
     */
    public TypeRef unwrapped() {
        if (name != null && name.startsWith("ObjectRef(") && name.endsWith(")")) {
            return new TypeRef(area, areaVersion, service,
                    name.substring("ObjectRef(".length(), name.length() - 1), list, true);
        }
        return this;
    }

    /**
     * Returns a copy of this reference with the area version filled in.
     *
     * @param version The version of the referenced area.
     * @return the linked reference.
     */
    public TypeRef withAreaVersion(int version) {
        return new TypeRef(area, version, service, name, list, objectRef);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeRef)) {
            return false;
        }
        TypeRef other = (TypeRef) o;
        return areaVersion == other.areaVersion
                && list == other.list
                && objectRef == other.objectRef
                && eq(area, other.area) && eq(service, other.service) && eq(name, other.name);
    }

    private static boolean eq(String a, String b) {
        return a == null ? b == null : a.equals(b);
    }

    @Override
    public int hashCode() {
        int result = area == null ? 0 : area.hashCode();
        result = 31 * result + areaVersion;
        result = 31 * result + (service == null ? 0 : service.hashCode());
        result = 31 * result + (name == null ? 0 : name.hashCode());
        result = 31 * result + (list ? 1 : 0);
        result = 31 * result + (objectRef ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        if (list) {
            buf.append("List<");
        }
        if (objectRef) {
            buf.append("ObjectRef<");
        }
        buf.append(area);
        if (areaVersion > 0) {
            buf.append('.').append(areaVersion);
        }
        buf.append("::");
        if (service != null) {
            buf.append(service).append('.');
        }
        buf.append(name);
        if (objectRef) {
            buf.append('>');
        }
        if (list) {
            buf.append('>');
        }
        return buf.toString();
    }
}
