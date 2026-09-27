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
 * An error whose number none of the specifications in use defines: not the operation
 * that returned it, nor its area, nor the MAL.
 * <p>
 * It is typically returned by a provider built against another version of a
 * specification. A consumer stub throws it wrapped in a MALException, since the operation
 * does not declare it.
 */
public final class UndefinedError extends MOErrorException {

    private static final long serialVersionUID = Attribute.ABSOLUTE_AREA_SERVICE_NUMBER + 102;

    /**
     * The name given to an error whose number no specification defines.
     */
    public static final String NAME = "UNDEFINED";

    /**
     * Creates an undefined error.
     *
     * @param errorNumber The number of the error, must not be null.
     * @param extraInformation The extra information of the error, may be null.
     */
    public UndefinedError(final UInteger errorNumber, final Object extraInformation) {
        super(NAME, errorNumber, extraInformation);
    }
}
