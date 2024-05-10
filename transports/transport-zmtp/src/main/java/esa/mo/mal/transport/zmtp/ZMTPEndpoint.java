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

import esa.mo.mal.transport.gen.Endpoint;
import esa.mo.mal.transport.gen.Transport;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 *
 */
public class ZMTPEndpoint extends Endpoint {

    private final ZMTPConfiguration configuration;

    public ZMTPEndpoint(Transport transport,
            ZMTPConfiguration configuration,
            String localName,
            String routingName,
            String uri,
            boolean wrapBodyParts,
            NamedValueList supplements,
            final Map properties) {
        super(transport, localName, routingName, uri, wrapBodyParts, supplements);
        this.configuration = new ZMTPConfiguration(configuration, properties);
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
            final Object... body) throws MALException {
        try {
            ZMTPMessageHeader hdr = (ZMTPMessageHeader) createMessageHeader(getURI(),
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
                    this.getEndpointSupplements(),
                    qosProperties);
            return new ZMTPMessage(
                    ((ZMTPTransport) transport).getHeaderStreamFactory(),
                    wrapBodyParts, hdr, qosProperties,
                    transport.getStreamFactory(), body);
        } catch (MALInteractionException ex) {
            throw new MALException("Error creating message", ex);
        }
    }

    @Override
    public MALMessageHeader createMessageHeader(URI uriFrom,
            Blob authenticationId,
            URI uriTo,
            Time timestamp,
            InteractionType interactionType,
            UOctet interactionStage,
            Long transactionId,
            UShort serviceArea,
            UShort service,
            UShort operation,
            UOctet serviceVersion,
            Boolean isErrorMessage,
            NamedValueList supplements,
            Map qosProperties) {
        ZMTPMessageHeader header = new ZMTPMessageHeader(
                new ZMTPConfiguration(configuration, qosProperties),
                null,
                new Identifier(getURI().getValue()),
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
                this.getEndpointSupplements());
        ((ZMTPTransport) transport).getBodyEncodingSelector().applyEncodingIdToHeader(header);
        return header;
    }
}
