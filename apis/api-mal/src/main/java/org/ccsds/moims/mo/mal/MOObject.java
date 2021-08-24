/* ----------------------------------------------------------------------------
 * Copyright (C) 2021      European Space Agency
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
package org.ccsds.moims.mo.mal;

import org.ccsds.moims.mo.mal.structures.ObjectReference;

/**
 * Class representing an MO Object.
 */
public class MOObject<T> {

    /**
     * Identity of the MO Object.
     */
    private final ObjectReference identity;

    /**
     * Body of the MO Object.
     */
    private final T body;

    /**
     * Initialises an MO Object.
     *
     * @param identity Identity of the MO Object.
     * @param body Body of the MO Object.
     * @throws java.lang.IllegalArgumentException If any argument is null.
     */
    public MOObject(final ObjectReference identity, final T body)
            throws java.lang.IllegalArgumentException {
        if (identity == null) {
            throw new IllegalArgumentException("The identity argument cannot be null!");
        }
        if (body == null) {
            throw new IllegalArgumentException("The body argument cannot be null!");
        }

        this.identity = identity;
        this.body = body;
    }

    /**
     * Returns the Identity of the MO Object.
     *
     * @return The Identity.
     */
    public ObjectReference getIdentity() {
        return identity;
    }

    /**
     * Returns the Body of the MO Object.
     *
     * @return The Body.
     */
    public T getBody() {
        return body;
    }

}
