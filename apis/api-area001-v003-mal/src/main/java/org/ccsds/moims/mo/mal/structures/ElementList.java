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
 * List interface for Elements.
 *
 * @param <T> The type of the list, no requirement to extend Element so that
 * native type can be used in Attribute lists.
 */
public interface ElementList<T> extends Element, java.util.List<T> {

    /**
     * The enforcement of non-nullable entries is hard-coded to be disabled
     * because it is not backwards compatible and it breaks the COM Archive
     * query operation. Note: All the testbeds are passing even when the
     * enforcement is enabled!
     */
    public final static boolean ENFORCE_NON_NULLABLE_ENTRIES = false;
}
