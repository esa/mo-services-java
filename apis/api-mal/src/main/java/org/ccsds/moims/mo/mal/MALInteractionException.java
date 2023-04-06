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
package org.ccsds.moims.mo.mal;

/**
 * The MALInteractionException class allows a MAL standard error to be raised as
 * an Exception.
 */
public class MALInteractionException extends Exception {

    private final MALStandardError standardError;

    /**
     * The constructor calls the java.lang.Exception constructor with the String
     * representation of the MALStandardError extraInformation field or NULL if
     * the extraInformation is NULL.
     *
     * @param error The MAL standard error to wrap.
     */
    public MALInteractionException(final MALStandardError error) {
        super(
                ((error != null) && (error.getExtraInformation() != null))
                ? error.getExtraInformation().toString() : null
        );

        standardError = error;
    }

    /**
     * Returns the MAL standard error that caused this exception.
     *
     * @return The MAL standard error.
     */
    public MALStandardError getStandardError() {
        return standardError;
    }

    @Override
    public String toString() {
        return "MALInteractionException{" + "standardError=" + standardError + '}';
    }
}
