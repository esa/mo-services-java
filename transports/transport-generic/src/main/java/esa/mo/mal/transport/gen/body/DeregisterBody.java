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

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.encoding.MALEncodingContext;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.transport.MALDeregisterBody;

/**
 * Implementation of the MALDeregisterBody interface.
 */
public class DeregisterBody extends LazyMessageBody implements MALDeregisterBody {

    private static final long serialVersionUID = 222222222222224L;

    /**
     * Constructor.
     *
     * @param ctx The encoding context to use.
     * @param encFactory The encoder stream factory to use.
     * @param messageParts The message parts that compose the body.
     */
    public DeregisterBody(final MALEncodingContext ctx,
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
    public DeregisterBody(final MALEncodingContext ctx,
            final MALElementStreamFactory encFactory,
            final MALElementInputStream encBodyElements) {
        super(ctx, encFactory, encBodyElements);
    }

    @Override
    public IdentifierList getSubscriptionIds() throws MALException {
        return (IdentifierList) getBodyElement(0, new IdentifierList());
    }
}
