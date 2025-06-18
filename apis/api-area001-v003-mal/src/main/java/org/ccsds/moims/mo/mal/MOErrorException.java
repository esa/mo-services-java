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

import java.io.Serializable;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;

/**
 * Represents a MAL error.
 */
public class MOErrorException extends Exception implements Serializable, MALErrorBody {

    private final UInteger errorNumber;
    private final Object extraInformation;
    private static final long serialVersionUID = Attribute.ABSOLUTE_AREA_SERVICE_NUMBER + 100;

    /**
     * Creates a standard error object with the supplied error number and extra
     * information.
     *
     * @param errorNumber The MAL error number, must not be null.
     * @param extraInformation Any associated extra information, may be null.
     * @throws java.lang.IllegalArgumentException Thrown if supplied error
     * number is null.
     */
    public MOErrorException(final UInteger errorNumber, final Object extraInformation)
            throws java.lang.IllegalArgumentException {
        super();

        if (errorNumber == null) {
            throw new IllegalArgumentException("The errorNumber argument cannot be NULL!");
        }

        if (errorNumber.getValue() == 0) {
            throw new IllegalArgumentException("The errorNumber argument cannot be zero!");
        }

        this.errorNumber = errorNumber;
        this.extraInformation = extraInformation;
    }

    /**
     * Returns the supplied error number.
     *
     * @return The error number.
     */
    public UInteger getErrorNumber() {
        return errorNumber;
    }

    /**
     * Returns the supplied extra information.
     *
     * @return The extra information.
     */
    public Object getExtraInformation() {
        return extraInformation;
    }

    /**
     * Downcasts a generic MO Error into a specific MO Error for a certain area
     * and version.
     *
     * @param area The area of the specific MO Error.
     * @param areaVersion The area version of the specific MO Error.
     * @return The specific MO Error.
     */
    public MOErrorException downcastError(UShort area, UOctet areaVersion) {
        // The code to downcast the MO Error still needs to be impelemented!
        return this;
    }

    @Override
    public MOErrorException getError() throws MALException {
        return this;
    }

    @Override
    public int getElementCount() {
        return 1;
    }

    @Override
    public Object getBodyElement(int index, Object element) throws MALException {
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append("(");
        buf.append("errorNumber=").append(errorNumber);
        buf.append(", extraInformation=\"").append(extraInformation);
        buf.append("\")");
        return buf.toString();
    }

}
