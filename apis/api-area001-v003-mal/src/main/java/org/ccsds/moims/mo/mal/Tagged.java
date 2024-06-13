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

import org.ccsds.moims.mo.mal.structures.Element;

/**
 * Class representing a tagged MAL Element.
 * @param <T> The MAL Element of the tag.
 */
public class Tagged<T> {

    /**
     * Tag of the MAL Element.
     */
    private final Tag tag;

    /**
     * The MAL Element.
     */
    private final T body;

    /**
     * Initialises a tagged MAL Element.
     *
     * @param tag Tag of the MAL Element.
     * @param body Body of the MAL Element.
     * @throws java.lang.IllegalArgumentException If any argument is null.
     */
    public Tagged(final Tag tag, final T body) throws IllegalArgumentException {
        if (tag == null) {
            throw new IllegalArgumentException("The identity argument cannot be null!");
        }
        if (body == null) {
            throw new IllegalArgumentException("The body argument cannot be null!");
        }
        if (!(body instanceof Element)) {
            throw new IllegalArgumentException("The body is not an MAL Element!");
        }

        this.tag = tag;
        this.body = body;
    }

    /**
     * Returns the Tag the MAL Element.
     *
     * @return The Tag.
     */
    public Tag getTag() {
        return tag;
    }

    /**
     * Returns the Body of the MAL Element.
     *
     * @return The Body.
     */
    public T getBody() {
        return body;
    }

}
