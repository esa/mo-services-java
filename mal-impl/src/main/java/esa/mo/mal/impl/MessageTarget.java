/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
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

import java.util.HashMap;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * Simple struct style class for holding details of a message.
 */
public final class MessageTarget {

    /**
     * The MAL endpoint used for this message.
     */
    private final MALEndpoint endpoint;

    private final URI uriTo;
    private final URI brokerUri;
    private final Map qosProps;
    private Blob authenticationId;

    /**
     * Constructor.
     *
     * @param endpoint Endpoint.
     * @param uriTo URITo.
     * @param brokerUri BrokerURI.
     * @param authenticationId Authentication Identifier.
     * @param qosProps QOS properties.
     */
    public MessageTarget(final MALEndpoint endpoint, final URI uriTo,
            final URI brokerUri, final Blob authenticationId, final Map qosProps) {
        this.endpoint = endpoint;
        this.uriTo = uriTo;
        this.brokerUri = brokerUri;
        this.authenticationId = authenticationId;
        this.qosProps = (qosProps == null) ? new HashMap() : qosProps;
    }

    /**
     * Sets the authentication id.
     *
     * @param authenticationId The authentication id.
     */
    public void setAuthenticationId(Blob authenticationId) {
        this.authenticationId = authenticationId;
    }

    /**
     * Returns the authentication id.
     *
     * @return The authentication id.
     */
    public Blob getAuthenticationId() {
        return authenticationId;
    }

    /**
     * Returns the endpoint of this address.
     *
     * @return The endpoint of this address.
     */
    public MALEndpoint getEndpoint() {
        return endpoint;
    }

    /**
     * Creates a MAL Message
     *
     * @param operation The MAL Operation
     * @param transactionId Transaction ID
     * @param interactionStage Interaction Stage
     * @param body The MAL Message Body
     * @return The MAL Message
     * @throws MALException When something goes wrong
     */
    public MALMessage createMessage(final MALOperation operation,
            final Long transactionId,
            final UOctet interactionStage,
            final Object... body) throws MALException {
        URI to = operation.isPubSub() ? this.brokerUri : this.uriTo;

        return endpoint.createMessage(
                authenticationId,
                to,
                Time.now(),
                operation.getInteractionType(),
                interactionStage,
                transactionId,
                operation.getServiceKey().getAreaNumber(),
                operation.getServiceKey().getServiceNumber(),
                operation.getNumber(),
                operation.getServiceKey().getAreaVersion(),
                Boolean.FALSE,
                new NamedValueList(),
                qosProps,
                body);
    }
}
