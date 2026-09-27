/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Generic Transport Framework
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
package esa.mo.mal.transport.gen.body;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.NotFoundException;
import org.ccsds.moims.mo.mal.UndefinedError;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.encoding.MALEncodingContext;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;

/**
 * Implementation of the MALErrorBody interface.
 */
public class ErrorBody extends LazyMessageBody implements MALErrorBody {

    private static final long serialVersionUID = 222222222222225L;


    /**
     * Constructor.
     *
     * @param ctx The encoding context to use.
     * @param encFactory The encoder stream factory to use.
     * @param messageParts The message parts that compose the body.
     */
    public ErrorBody(final MALEncodingContext ctx,
            final MALElementStreamFactory encFactory,
            final Object[] messageParts) {
        super(ctx, encFactory, messageParts);
    }

    /**
     * Constructor.
     *
     * @param ctx The encoding context to use.
     * @param encFactory The encoder stream factory to use.
     * @param encBodyElements The input stream that holds the encoded body
     * parts.
     */
    public ErrorBody(final MALEncodingContext ctx,
            final MALElementStreamFactory encFactory,
            final MALElementInputStream encBodyElements) {
        super(ctx, encFactory, encBodyElements);
    }

    @Override
    public MOErrorException getError() throws MALException {
        UInteger errorNumber = getErrorNumber();
        Object extraInfo = getExtraInformation();
        MOErrorException error;
        try {
            error = ctx.getHeader().getServiceInfo().errorOf(
                    ctx.getHeader().getOperation().getValue(), errorNumber, extraInfo);
        } catch (NotFoundException ex) {
            Logger.getLogger(ErrorBody.class.getName()).log(Level.SEVERE,
                    "The serviceInfo for this message was not found!", ex);
            error = new UndefinedError(errorNumber, extraInfo);
        }
        if (error instanceof UndefinedError) {
            Logger.getLogger(ErrorBody.class.getName()).log(Level.FINE,
                    "The error number {0} is not defined by the operation of this message, "
                    + "its area or the MAL.", errorNumber);
        }
        return error;
    }

    @Override
    public UInteger getErrorNumber() throws MALException {
        decodeMessageBody();
        return (UInteger) messageParts[0];
    }

    @Override
    public Object getExtraInformation() throws MALException {
        decodeMessageBody();
        return (messageParts.length > 1) ? messageParts[1] : null;
    }
}
