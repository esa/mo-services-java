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

/**
 * Class representing a Tag.
 */
public class Tag {

    /**
     * Tag of the MAL Element.
     */
    private final String domain;

    /**
     * The short form part of the MAL Element.
     */
    private final long shortFormPart;

    /**
     * The instanceId of the MAL Element.
     */
    private final long instanceId;

    /**
     * Initialises a tagged MAL Element.
     *
     * @param domain The domain of the Tag.
     * @param shortFormPart The shortFormPart of the Tag.
     * @param instanceId The instanceId of the Tag.
     * @throws java.lang.IllegalArgumentException If any argument is null.
     */
    public Tag(final String domain, final long shortFormPart,
            final long instanceId) throws IllegalArgumentException {
        if (domain == null) {
            throw new IllegalArgumentException("The identity argument cannot be null!");
        }

        this.domain = domain;
        this.shortFormPart = shortFormPart;
        this.instanceId = instanceId;
    }

    /**
     * Returns the domain of the MAL Element.
     *
     * @return The domain.
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Returns the short form part of the MAL Element.
     *
     * @return The short form part.
     */
    public long getShortFormPart() {
        return shortFormPart;
    }

    /**
     * Returns the instanceId of the MAL Element.
     *
     * @return The instanceId.
     */
    public long getInstanceId() {
        return instanceId;
    }

}
