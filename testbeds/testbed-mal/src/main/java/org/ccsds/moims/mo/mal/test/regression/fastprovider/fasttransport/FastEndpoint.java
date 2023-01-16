/* ----------------------------------------------------------------------------
 * Copyright (C) 2016      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Test bed
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
package org.ccsds.moims.mo.mal.test.regression.fastprovider.fasttransport;

import esa.mo.mal.transport.gen.GENMessageHeader;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.transport.MALEncodedBody;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageListener;
import org.ccsds.moims.mo.mal.transport.MALTransmitErrorException;

/**
 *
 */
public class FastEndpoint implements MALEndpoint {

    private final FastTransport transport;
    private final String localName;
    private MALMessageListener ml;

    public FastEndpoint(FastTransport transport, String localName) {
        this.transport = transport;
        this.localName = localName;
    }

    public void startMessageDelivery() throws MALException {
    }

    public void stopMessageDelivery() throws MALException {
    }

    @Override
    public MALMessage createMessage(final Blob authenticationId,
            final URI uriTo,
            final Time timestamp,
            final QoSLevel qosLevel,
            final UInteger priority,
            final IdentifierList domain,
            final Identifier networkZone,
            final SessionType session,
            final Identifier sessionName,
            final InteractionType interactionType,
            final UOctet interactionStage,
            final Long transactionId,
            final UShort serviceArea,
            final UShort service,
            final UShort operation,
            final UOctet serviceVersion,
            final Boolean isErrorMessage,
            final Map qosProperties,
            final Object... body) throws IllegalArgumentException, MALException {
        return new FastMessage(createMessageHeader(getURI(),
                authenticationId,
                uriTo,
                timestamp,
                qosLevel,
                priority,
                domain,
                networkZone,
                session,
                sessionName,
                interactionType,
                interactionStage,
                transactionId,
                serviceArea,
                service,
                operation,
                serviceVersion,
                isErrorMessage),
                qosProperties,
                body);
    }

    @Override
    public MALMessage createMessage(final Blob authenticationId,
            final URI uriTo,
            final Time timestamp,
            final QoSLevel qosLevel,
            final UInteger priority,
            final IdentifierList domain,
            final Identifier networkZone,
            final SessionType session,
            final Identifier sessionName,
            final Long transactionId,
            final Boolean isErrorMessage,
            final MALOperation op,
            final UOctet interactionStage,
            final Map qosProperties,
            final Object... body) throws IllegalArgumentException, MALException {
        return new FastMessage(createMessageHeader(getURI(),
                authenticationId,
                uriTo,
                timestamp,
                qosLevel,
                priority,
                domain,
                networkZone,
                session,
                sessionName,
                op.getInteractionType(),
                interactionStage,
                transactionId,
                op.getService().getAreaNumber(),
                op.getService().getServiceNumber(),
                op.getNumber(),
                op.getService().getServiceVersion(),
                isErrorMessage),
                qosProperties,
                body);
    }

    public MALMessage createMessage(Blob blob, URI uri, Time time, QoSLevel qsl, UInteger ui, IdentifierList il, Identifier idntfr, SessionType st, Identifier idntfr1, InteractionType it, UOctet uoctet, Long l, UShort ushort, UShort ushort1, UShort ushort2, UOctet uoctet1, Boolean bln, Map map, MALEncodedBody maleb) throws IllegalArgumentException, MALException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public MALMessage createMessage(Blob blob, URI uri, Time time, QoSLevel qsl, UInteger ui, IdentifierList il, Identifier idntfr, SessionType st, Identifier idntfr1, Long l, Boolean bln, MALOperation malo, UOctet uoctet, Map map, MALEncodedBody maleb) throws IllegalArgumentException, MALException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendMessage(MALMessage malm) throws IllegalArgumentException, MALTransmitErrorException, MALException {
        transport.internalSendMessage(malm);
    }

    public void internalSendMessage(MALMessage malm) {
        ml.onMessage(this, malm);
    }

    public void sendMessages(MALMessage[] malms) throws IllegalArgumentException, MALException {
    }

    public void setMessageListener(MALMessageListener ml) throws MALException {
        this.ml = ml;
    }

    public URI getURI() {
        return new URI("fast://" + localName);
    }

    public String getLocalName() {
        return localName;
    }

    public void close() throws MALException {
    }

    public GENMessageHeader createMessageHeader(final URI uriFrom,
            final Blob authenticationId,
            final URI uriTo,
            final Time timestamp,
            final QoSLevel qosLevel,
            final UInteger priority,
            final IdentifierList domain,
            final Identifier networkZone,
            final SessionType session,
            final Identifier sessionName,
            final InteractionType interactionType,
            final UOctet interactionStage,
            final Long transactionId,
            final UShort serviceArea,
            final UShort service,
            final UShort operation,
            final UOctet serviceVersion,
            final Boolean isErrorMessage) {
        return new GENMessageHeader(uriFrom,
                authenticationId,
                uriTo,
                timestamp,
                qosLevel,
                priority,
                domain,
                networkZone,
                session,
                sessionName,
                interactionType,
                interactionStage,
                transactionId,
                serviceArea,
                service,
                operation,
                serviceVersion,
                isErrorMessage);
    }
}
