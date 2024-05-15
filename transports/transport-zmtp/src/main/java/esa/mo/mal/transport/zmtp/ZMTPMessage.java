/* ----------------------------------------------------------------------------
 * Copyright (C) 2017      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO ZMTP Transport Framework
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
package esa.mo.mal.transport.zmtp;

import esa.mo.mal.encoder.zmtp.header.ZMTPHeaderStreamFactory;
import esa.mo.mal.transport.gen.GENMessage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * ZMTP message class.
 */
public class ZMTPMessage extends GENMessage {

    private final ZMTPHeaderStreamFactory hdrStreamFactory;

    /**
     * Constructor.
     *
     * @param hdrStreamFactory The stream factory to use for message header
     * encoding.
     * @param wrapBodyParts True if the encoded body parts should be wrapped in
     * BLOBs.
     * @param header The message header to use.
     * @param qosProperties The QoS properties for this message.
     * @param encFactory The stream factory to use for message body encoding.
     * @param body the body of the message.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException If the operation
     * is unknown.
     */
    public ZMTPMessage(final ZMTPHeaderStreamFactory hdrStreamFactory, boolean wrapBodyParts,
            MALMessageHeader header, Map qosProperties, MALElementStreamFactory encFactory,
            Object... body) throws MALInteractionException {
        super(wrapBodyParts, header, qosProperties, encFactory, body);
        this.hdrStreamFactory = hdrStreamFactory;
    }

    /**
     * Constructor.
     *
     * @param hdrStreamFactory The stream factory to use for message header
     * encoding.
     * @param header An instance of the header class to use.
     * @param qosProperties The QoS properties for this message.
     * @param bais The ByteArrayInputStream in encoded form.
     * @param encFactory The stream factory to use for message body encoding.
     * @throws MALException On decoding error.
     */
    public ZMTPMessage(final ZMTPHeaderStreamFactory hdrStreamFactory,
            MALMessageHeader header, Map qosProperties, ByteArrayInputStream bais,
            MALElementStreamFactory encFactory) throws MALException {
        this.header = header;
        final MALElementInputStream enc = encFactory.createInputStream(bais);
        this.body = super.createMessageBody(enc);
        this.hdrStreamFactory = hdrStreamFactory;
    }

    @Override
    public void encodeMessage(final MALElementStreamFactory streamFactory,
            final MALElementOutputStream enc,
            final OutputStream lowLevelOutputStream,
            final boolean writeHeader) throws MALException {
        try {
            final ByteArrayOutputStream hdrBaos = new ByteArrayOutputStream();
            ((ZMTPMessageHeader) header).encode(hdrStreamFactory.getHeaderEncoder(hdrBaos));
            lowLevelOutputStream.write(hdrBaos.toByteArray());

            final ByteArrayOutputStream bodyBaos = new ByteArrayOutputStream();
            final MALElementOutputStream bodyEnc = streamFactory.createOutputStream(bodyBaos);
            super.encodeMessage(streamFactory, bodyEnc, bodyBaos, false);
            byte[] bodyData = bodyBaos.toByteArray();
            lowLevelOutputStream.write(bodyData);
        } catch (IOException ex) {
            throw new MALException("Internal error encoding message", ex);
        }
    }
}
