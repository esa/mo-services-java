/* ----------------------------------------------------------------------------
 * Copyright (C) 2026      European Space Agency
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

import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.UInteger;

/**
 * An error received as a number that has not been resolved to the error it stands for.
 * <p>
 * A transport decodes every error as its number and returns it as this class; the MAL
 * then resolves it against the errors the operation declares, those of its area and the
 * MAL standard errors. An error that stays unresolved has a number none of these define,
 * as from a provider built against another version of the specification.
 */
public final class UnresolvedError extends MOErrorException {

    private static final long serialVersionUID = Attribute.ABSOLUTE_AREA_SERVICE_NUMBER + 102;

    /**
     * The name given to an error whose number has not been resolved.
     */
    public static final String NAME = "UNRESOLVED";

    /**
     * Creates an unresolved error.
     *
     * @param errorNumber The number of the error, must not be null.
     * @param extraInformation The extra information of the error, may be null.
     */
    public UnresolvedError(final UInteger errorNumber, final Object extraInformation) {
        super(NAME, errorNumber, extraInformation);
    }
}
