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

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.transport.MALEncodedBody;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
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

    @Override
    public void startMessageDelivery() throws MALException {
    }

    @Override
    public void stopMessageDelivery() throws MALException {
    }

    @Override
    public MALMessage createMessage(final Blob authenticationId,
            final URI uriTo,
            final Time timestamp,
            final InteractionType interactionType,
            final UOctet interactionStage,
            final Long transactionId,
            final UShort serviceArea,
            final UShort service,
            final UShort operation,
            final UOctet serviceVersion,
            final Boolean isErrorMessage,
            final NamedValueList supplements,
            final Map qosProperties,
            final Object... body) throws IllegalArgumentException, MALException {
        return new FastMessage(createMessageHeader(getURI(),
                authenticationId,
                uriTo,
                timestamp,
                interactionType,
                interactionStage,
                transactionId,
                serviceArea,
                service,
                operation,
                serviceVersion,
                isErrorMessage,
                supplements),
                qosProperties,
                body);
    }

    @Override
    public MALMessage createMessage(final Blob authenticationId,
            final URI uriTo,
            final Time timestamp,
            final Long transactionId,
            final Boolean isErrorMessage,
            final NamedValueList supplements,
            final MALOperation op,
            final UOctet interactionStage,
            final Map qosProperties,
            final Object... body) throws IllegalArgumentException, MALException {
        return new FastMessage(createMessageHeader(getURI(),
                authenticationId,
                uriTo,
                timestamp,
                op.getInteractionType(),
                interactionStage,
                transactionId,
                op.getService().getAreaNumber(),
                op.getService().getServiceNumber(),
                op.getNumber(),
                op.getService().getServiceVersion(),
                isErrorMessage,
                supplements),
                qosProperties,
                body);
    }

    @Override
    public void sendMessage(MALMessage malm) throws IllegalArgumentException, MALTransmitErrorException, MALException {
        transport.internalSendMessage(malm);
    }

    public void internalSendMessage(MALMessage malm) {
        ml.onMessage(this, malm);
    }

    @Override
    public void sendMessages(MALMessage[] malms) throws IllegalArgumentException, MALException {
    }

    @Override
    public void setMessageListener(MALMessageListener ml) throws MALException {
        this.ml = ml;
    }

    @Override
    public URI getURI() {
        return new URI("fast://" + localName);
    }

    @Override
    public String getLocalName() {
        return localName;
    }

    @Override
    public void close() throws MALException {
    }

    public MALMessageHeader createMessageHeader(final URI uriFrom,
            final Blob authenticationId,
            final URI uriTo,
            final Time timestamp,
            final InteractionType interactionType,
            final UOctet interactionStage,
            final Long transactionId,
            final UShort serviceArea,
            final UShort service,
            final UShort operation,
            final UOctet serviceVersion,
            final Boolean isErrorMessage,
            final NamedValueList supplements) {
        return new MALMessageHeader(new Identifier(uriFrom.getValue()),
                authenticationId,
                new Identifier(uriTo.getValue()),
                timestamp,
                interactionType,
                interactionStage,
                transactionId,
                serviceArea,
                service,
                operation,
                serviceVersion,
                isErrorMessage,
                supplements);
    }

    @Override
    public MALMessage createMessage(Blob authenticationId, URI uriTo, Time timestamp,
            InteractionType interactionType, UOctet interactionStage, Long transactionId,
            UShort serviceAreaNumber, UShort serviceNumber, UShort operationNumber,
            UOctet areaVersion, Boolean isErrorMessage, NamedValueList supplements,
            Map qosProperties, MALEncodedBody body) throws IllegalArgumentException, MALException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
