/* ----------------------------------------------------------------------------
 * Copyright (C) 2026      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java Implementation
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
package esa.mo.mal.impl;

import java.util.logging.Level;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.NotFoundException;
import org.ccsds.moims.mo.mal.UndefinedError;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * The body of an error message, whose error is answered as its own class.
 * <p>
 * The error is built here from the number and extra information the transport decoded,
 * against the operation of the message, once for every transport: a consumer receives the
 * error the specifications define whichever transport delivered it, or an UndefinedError
 * where none defines the number.
 */
public final class ResolvedErrorBody implements MALErrorBody {

    private final MALMessageHeader header;
    private final MALErrorBody body;

    /**
     * Wraps the body of an error message.
     *
     * @param header The header of the message.
     * @param body The body of the message, as the transport decoded it.
     */
    public ResolvedErrorBody(final MALMessageHeader header, final MALErrorBody body) {
        this.header = header;
        this.body = body;
    }

    @Override
    public MOErrorException getError() throws MALException {
        return errorOf(header, body);
    }

    @Override
    public UInteger getErrorNumber() throws MALException {
        return body.getErrorNumber();
    }

    @Override
    public Object getExtraInformation() throws MALException {
        return body.getExtraInformation();
    }

    @Override
    public int getElementCount() {
        return body.getElementCount();
    }

    @Override
    public Object getBodyElement(final int index, final Object element) throws MALException {
        return body.getBodyElement(index, element);
    }

    /**
     * Returns the error an error message carries, as its own class.
     * <p>
     * A body that is itself an error was created where the message was, and is that
     * error. Any other is built from its number and extra information, against the
     * operation of the message.
     *
     * @param header The header of the message, may be null.
     * @param body The body of the message.
     * @return The error.
     * @throws MALException If the body cannot be decoded.
     */
    public static MOErrorException errorOf(final MALMessageHeader header,
            final MALErrorBody body) throws MALException {
        if (body instanceof MOErrorException) {
            return (MOErrorException) body;
        }
        final UInteger errorNumber = body.getErrorNumber();
        final Object extraInfo = body.getExtraInformation();
        MOErrorException error = null;
        if (header != null) {
            try {
                error = header.getServiceInfo().errorOf(header.getOperation().getValue(),
                        errorNumber, extraInfo);
            } catch (NotFoundException ex) {
                // No service to resolve against: the number is defined by nothing known
            }
        }
        if (error == null) {
            error = new UndefinedError(errorNumber, extraInfo);
        }
        if (error instanceof UndefinedError) {
            MALContextFactoryImpl.LOGGER.log(Level.FINE,
                    "The error number {0} is not defined by the operation of this message, "
                    + "its area or the MAL.", errorNumber);
        }
        return error;
    }
}
