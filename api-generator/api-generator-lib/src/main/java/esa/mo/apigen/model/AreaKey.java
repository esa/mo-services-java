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

/**
 * The identity of an area: name, number and version together.
 * <p>
 * None of the three is unique on its own. The specifications contain areas that share a
 * name under different numbers, and numbers under different names, so anything indexing
 * areas has to key on all three - see the design document, section 4.1.
 */
public final class AreaKey implements Comparable<AreaKey> {

    private final String name;
    private final int number;
    private final int version;

    public AreaKey(String name, int number, int version) {
        this.name = name;
        this.number = number;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AreaKey)) {
            return false;
        }
        AreaKey other = (AreaKey) o;
        return number == other.number && version == other.version
                && (name == null ? other.name == null : name.equals(other.name));
    }

    @Override
    public int hashCode() {
        int result = name == null ? 0 : name.hashCode();
        result = 31 * result + number;
        result = 31 * result + version;
        return result;
    }

    @Override
    public int compareTo(AreaKey o) {
        int c = Integer.compare(number, o.number);
        if (c != 0) {
            return c;
        }
        c = Integer.compare(version, o.version);
        if (c != 0) {
            return c;
        }
        return String.valueOf(name).compareTo(String.valueOf(o.name));
    }

    @Override
    public String toString() {
        return name + " [" + number + "." + version + "]";
    }
}
