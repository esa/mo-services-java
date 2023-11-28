/* ----------------------------------------------------------------------------
 * Copyright (C) 2015      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO JMS Transport Framework
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
package esa.mo.mal.transport.jms;

import esa.mo.mal.transport.gen.GENMessage;
import esa.mo.mal.transport.gen.PacketToString;
import esa.mo.mal.transport.gen.receivers.IncomingMessageHolder;
import esa.mo.mal.transport.gen.receivers.MessageDecoder;
import java.io.ByteArrayInputStream;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Responsible for decoding newly arrived PS MAL Messages.
 */
final class JMSIncomingPSMessageDecoder implements MessageDecoder {

    private final JMSTransport transport;
    final JMSUpdate jmsUpdate;
    final URI uri;
    final UOctet version;
    final Identifier subId;
    final URI URIFrom;
    final Long transactionId;

    public JMSIncomingPSMessageDecoder(final JMSTransport transport,
            JMSUpdate jmsUpdate, URI uri, UOctet version, Identifier subId,
            URI URIFrom, Long transactionId) {
        this.transport = transport;
        this.jmsUpdate = jmsUpdate;
        this.uri = uri;
        this.version = version;
        this.subId = subId;
        this.URIFrom = URIFrom;
        this.transactionId = transactionId;
    }

    @Override
    public IncomingMessageHolder decodeAndCreateMessage() throws MALException {
        // build header
        MALMessageHeader hdr = new MALMessageHeader(
                new Identifier(URIFrom.getValue()),
                new Blob(JMSTransport.authId),
                new Identifier(uri.getValue()),
                Time.now(),
                InteractionType.PUBSUB,
                MALPubSubOperation.NOTIFY_STAGE,
                transactionId,
                null,
                null,
                null,
                version,
                false,
                new NamedValueList());

        try {
            byte[] data = jmsUpdate.getDat();
            ByteArrayInputStream baos = new ByteArrayInputStream(data);
            MALElementInputStream enc = transport.getStreamFactory().createInputStream(baos);
            UShort lstCount = (UShort) enc.readElement(null, null);
            Object[] new_objs = new Object[lstCount.getValue() + 1];
            new_objs[0] = subId;
            for (int i = 1; i < new_objs.length; i++) {
                new_objs[i] = enc.readElement(null, null);
            }

            GENMessage malMsg = new GENMessage(false, new JMSMessageHeader(hdr, jmsUpdate),
                    null, null, transport.getStreamFactory(), new_objs);
            return new IncomingMessageHolder(malMsg, new PacketToString(data));
        } catch (Throwable ex) {
            throw new MALException("Internal error decoding message", ex);
        }
    }
}
