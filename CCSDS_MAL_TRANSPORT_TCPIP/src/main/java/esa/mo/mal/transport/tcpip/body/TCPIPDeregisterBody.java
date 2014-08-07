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
package esa.mo.mal.transport.tcpip.body;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.encoding.MALEncodingContext;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.transport.MALDeregisterBody;

/**
 * Implementation of the MALDeregisterBody interface.
 */
public class TCPIPDeregisterBody extends TCPIPMessageBody implements MALDeregisterBody {
    private static final long serialVersionUID = 222222222222224L;

    /**
     * Constructor.
     *
     * @param ctx The encoding context to use.
     * @param messageParts The message parts that compose the body.
     */
    public TCPIPDeregisterBody(final MALEncodingContext ctx, final Object[] messageParts) {
	super(ctx, messageParts);
    }

    /**
     * Constructor.
     *
     * @param ctx The encoding context to use.
     * @param wrappedBodyParts True if the encoded body parts are wrapped in
     *            BLOBs.
     * @param encFactory The encoder stream factory to use.
     * @param encBodyElements The input stream that holds the encoded body
     *            parts.
     */
    public TCPIPDeregisterBody(final MALEncodingContext ctx, final boolean wrappedBodyParts, final MALElementStreamFactory encFactory, final MALElementInputStream encBodyElements) {
	super(ctx, wrappedBodyParts, encFactory, encBodyElements);
    }

    @Override
    public IdentifierList getIdentifierList() throws MALException {
	return (IdentifierList) getBodyElement(0, new IdentifierList());
    }
}
