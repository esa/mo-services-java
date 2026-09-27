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
 * An error the MAL area defines, which any interaction can return whether or not its
 * operation declares it: the MAL and the transport raise these as well as providers.
 * <p>
 * The class generated for each MAL error extends this one. A consumer stub declares it
 * next to the errors of the operation, so that a caller catches the MAL errors together,
 * or one of them by its own class.
 */
public abstract class MALStandardError extends MOErrorException {

    private static final long serialVersionUID = Attribute.ABSOLUTE_AREA_SERVICE_NUMBER + 101;

    /**
     * Constructs a MAL standard error.
     *
     * @param errorName The name of the error.
     * @param errorNumber The number of the error.
     * @param extraInformation The extra information of the error.
     */
    protected MALStandardError(final String errorName, final UInteger errorNumber,
            final Object extraInformation) {
        super(errorName, errorNumber, extraInformation);
    }

    /**
     * Relays an error that an interaction returned, once the caller has thrown the errors
     * its operation declares: a MAL standard error is thrown as its own class, and any
     * other error is returned wrapped in a MALException, for the caller to throw.
     * <p>
     * Returning the MALException rather than throwing it lets a caller write
     * {@code throw MALStandardError.relayOrWrap(ex);}, so that the compiler sees the
     * catch always end in a throw.
     *
     * @param ex The exception the interaction raised.
     * @return The MALException wrapping an error that is not a MAL standard error.
     * @throws MALStandardError If the error is a MAL standard error.
     */
    public static MALException relayOrWrap(final MALInteractionException ex) throws MALStandardError {
        final MOErrorException error = ex.getStandardError();
        if (error instanceof MALStandardError) {
            throw (MALStandardError) error;
        }
        return new MALException("The operation does not declare the error " + error, ex);
    }
}
