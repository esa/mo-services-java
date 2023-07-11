/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java API
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
package org.ccsds.moims.mo.mal.structures;

/**
 * The abstract class represents the MAL Enumeration type. The class is extended
 * by the classes representing MAL enumeration types.
 *
 */
public abstract class Enumeration implements Element {

    /**
     * The index of the enumerated item, i.e. its position in the enumeration
     * declaration starting from zero.
     */
    protected Integer ordinal;

    /**
     * The ordinal constructor, takes as a parameter the index of the enumerated
     * item, i.e. its position in the enumeration declaration starting from
     * zero.
     *
     * @param ordinal The index of the enumerated item.
     */
    protected Enumeration(final int ordinal) {
        this.ordinal = ordinal;
    }

    /**
     * Returns the index of the enumerated item.
     *
     * @return the index of the enumerated item.
     */
    public final int getOrdinal() {
        return ordinal;
    }

    /**
     * Returns the numeric value of the enumerated item.
     *
     * @return the numeric value of the enumerated item.
     */
    public abstract UInteger getNumericValue();

    @Override
    public int hashCode() {
        return ordinal;
    }

    @Override
    public boolean equals(final Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other instanceof Enumeration) {
            return 0 == ordinal.compareTo(((Enumeration) other).ordinal);
        }
        return false;
    }
}
