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
package esa.mo.apigen.model.com;

/**
 * A reference to a COM object, by number.
 * <p>
 * The XML form - {@code <com:objectType area="COM" number="6" service="ActivityTracking"/>}
 * - identifies the target by number and carries no version, so the importer fills
 * {@code areaVersion} in at link time exactly as it does for a type reference. The number
 * is the truth; a name is a lookup, never the other way round, or a specification whose
 * target area is absent could not be represented at all.
 */
public final class ObjectReference {

    private final String area;
    private final int areaVersion;
    private final String service;
    private final int number;

    public ObjectReference(String area, int areaVersion, String service, int number) {
        this.area = area;
        this.areaVersion = areaVersion;
        this.service = service;
        this.number = number;
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

    public String getService() {
        return service;
    }

    public int getNumber() {
        return number;
    }

    /**
     * Returns a copy of this reference with the area version filled in.
     *
     * @param version The version of the referenced area.
     * @return the linked reference.
     */
    public ObjectReference withAreaVersion(int version) {
        return new ObjectReference(area, version, service, number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ObjectReference)) {
            return false;
        }
        ObjectReference other = (ObjectReference) o;
        return number == other.number && areaVersion == other.areaVersion
                && (area == null ? other.area == null : area.equals(other.area))
                && (service == null ? other.service == null : service.equals(other.service));
    }

    @Override
    public int hashCode() {
        int result = area == null ? 0 : area.hashCode();
        result = 31 * result + areaVersion;
        result = 31 * result + (service == null ? 0 : service.hashCode());
        result = 31 * result + number;
        return result;
    }

    @Override
    public String toString() {
        return area + "::" + service + "#" + number;
    }
}
