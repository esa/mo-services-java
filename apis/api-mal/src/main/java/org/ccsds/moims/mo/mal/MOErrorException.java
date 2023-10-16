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
import org.ccsds.moims.mo.mal.transport.MALEncodedBody;
import org.ccsds.moims.mo.mal.transport.MALEncodedElement;
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
            throw new IllegalArgumentException("The errorNumber argument cannot not be NULL!");
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
    public MALEncodedElement getEncodedBodyElement(int index) throws MALException {
        return null;
    }

    @Override
    public MALEncodedBody getEncodedBody() throws MALException {
        return null;
    }

    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append("(");
        buf.append("errorNumber=").append(errorNumber);
        buf.append(",extraInformation=").append(extraInformation);
        buf.append(")");
        return buf.toString();
    }
}
