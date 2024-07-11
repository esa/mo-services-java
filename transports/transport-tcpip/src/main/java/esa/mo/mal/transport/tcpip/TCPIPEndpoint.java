/* ----------------------------------------------------------------------------
 * Copyright (C) 2014      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO TCP/IP Transport Framework
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
package esa.mo.mal.transport.tcpip;

import esa.mo.mal.transport.gen.Endpoint;
import esa.mo.mal.transport.gen.Transport;
import esa.mo.mal.transport.gen.body.LazyMessageBody;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * TCPIP Transport binding MAL endpoint implementation. Creates messages and
 * generates the correct message header.
 *
 * @author Rian van Gijlswijk
 *
 */
public class TCPIPEndpoint extends Endpoint {

    public TCPIPEndpoint(Transport transport, String localName,
            String routingName, String uri, NamedValueList supplements) {
        super(transport, localName, routingName, uri, supplements);
    }

    @Override
    public MALMessage createMessage(final Blob authenticationId,
            final URI uriTo, final Time timestamp,
            final InteractionType interactionType,
            final UOctet interactionStage, final Long transactionId,
            final UShort serviceArea, final UShort service,
            final UShort operation, final UOctet serviceVersion,
            final Boolean isErrorMessage, final NamedValueList supplements,
            final Map qosProperties, final Object... body) throws MALException {
        TCPIPMessageHeader hdr = (TCPIPMessageHeader) createMessageHeader(
                getURI(), authenticationId,
                uriTo, timestamp, interactionType,
                interactionStage, transactionId, serviceArea,
                service, operation, serviceVersion,
                isErrorMessage, supplements, qosProperties);

        MALElementStreamFactory encFactory = transport.getStreamFactory();
        LazyMessageBody lazyBody = LazyMessageBody.createMessageBody(hdr, encFactory, body);
        return new TCPIPMessage(hdr, lazyBody, encFactory, qosProperties);
    }

    /**
     * Create a message header with all header properties set. The serviceFrom
     * and serviceTo parameters will be explicitly set in the header, so that
     * they include only the routing part of their respective urls. The routing
     * part is the part of an URL that comes after the service delimiter:
     * maltcp://host:port/routingpart
     */
    @Override
    public MALMessageHeader createMessageHeader(final URI uriFrom,
            Blob authenticationId, final URI uriTo, final Time timestamp,
            final InteractionType interactionType, final UOctet interactionStage,
            final Long transactionId, final UShort serviceArea, final UShort service,
            final UShort operation, final UOctet serviceVersion,
            final Boolean isErrorMessage, final NamedValueList supplements,
            final Map qosProperties) {
        String serviceFrom = transport.getRoutingPart(uriFrom.toString());
        String serviceTo = transport.getRoutingPart(uriTo.toString());

        return new TCPIPMessageHeader(new Identifier(uriFrom.getValue()),
                serviceFrom, authenticationId, new Identifier(uriTo.getValue()),
                serviceTo, timestamp, interactionType, interactionStage,
                transactionId, serviceArea, service, operation,
                serviceVersion, isErrorMessage, this.getEndpointSupplements());
    }

}
