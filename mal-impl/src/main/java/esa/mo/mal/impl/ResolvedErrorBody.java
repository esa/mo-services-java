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

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.NotFoundException;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * The body of an error message, whose error is answered as its own class.
 * <p>
 * A transport decodes an error as a number. Resolving it against the operation of the
 * message is done here, once for every transport, so that a consumer receives the error
 * of the operation's specification whichever transport delivered it.
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
        return resolve(header, body.getError());
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
     * Returns the error as its own class, resolved against the operation of the message
     * that carried it.
     *
     * @param header The header of the message, may be null.
     * @param error The error as the transport decoded it.
     * @return The error as its own class, where its number resolves.
     */
    public static MOErrorException resolve(final MALMessageHeader header,
            final MOErrorException error) {
        if (header == null) {
            return error;
        }
        try {
            return header.getServiceInfo().resolveError(header.getOperation().getValue(), error);
        } catch (NotFoundException ex) {
            return error;
        }
    }
}
