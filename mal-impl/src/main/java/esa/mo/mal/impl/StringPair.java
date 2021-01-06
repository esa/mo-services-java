/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java Implementation
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
package esa.mo.mal.impl;

/**
 * Small comparable string pair class.
 */
public class StringPair implements Comparable {

    /**
     * First string component, MSB.
     */
    public final String first;
    /**
     * Second string component, LSB.
     */
    public final String second;
    private static final int HASH_MAGIC_NUMBER = 79;

    /**
     * Constructor.
     *
     * @param first First string part.
     * @param second Second string part.
     */
    public StringPair(final String first, final String second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public int compareTo(final Object o) {
        final StringPair p = (StringPair) o;

        final int i = first.compareTo(p.first);
        if (0 == i) {
            return second.compareTo(p.second);
        }

        return i;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof StringPair) {
            final StringPair p = (StringPair) obj;
            return first.equals(p.first) && second.equals(p.second);
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = HASH_MAGIC_NUMBER * hash + (this.first != null ? this.first.hashCode() : 0);
        hash = HASH_MAGIC_NUMBER * hash + (this.second != null ? this.second.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "(" + this.first + " : " + this.second + ")";
    }
}
