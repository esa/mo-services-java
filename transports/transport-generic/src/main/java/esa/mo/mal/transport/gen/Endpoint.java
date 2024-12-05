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
package esa.mo.mal.transport.gen;

import esa.mo.mal.transport.gen.body.LazyMessageBody;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.InternalException;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.*;

/**
 * A generic implementation of the end point interface.
 */
public class Endpoint implements MALEndpoint {

    protected final Transport transport;
    protected final String localName;
    protected final String routingName;
    protected final String localURI;
    private final NamedValueList endpointSupplements;
    private boolean active = false;
    private MALMessageListener messageListener = null;

    /**
     * Constructor.
     *
     * @param transport Parent transport.
     * @param localName Endpoint local MAL name.
     * @param routingName Endpoint local routing name.
     * @param uri The URI string for this end point.
     * @param supplements Endpoint supplements.
     */
    public Endpoint(final Transport transport, final String localName,
            final String routingName, final String uri, final NamedValueList supplements) {
        this.transport = transport;
        this.localName = localName;
        this.routingName = routingName;
        this.localURI = uri;
        this.endpointSupplements = (supplements != null) ? supplements : new NamedValueList();
    }

    @Override
    public void startMessageDelivery() throws MALException {
        Transport.LOGGER.log(Level.FINE,
                "Endpoint ({0}) Activating message delivery", localName);
        active = true;
    }

    @Override
    public void stopMessageDelivery() throws MALException {
        Transport.LOGGER.log(Level.FINE,
                "Endpoint ({0}) Deactivating message delivery", localName);
        active = false;
    }

    @Override
    public String getLocalName() {
        return localName;
    }

    /**
     * Returns the routing name used by this endpoint.
     *
     * @return the routing name.
     */
    public String getRoutingName() {
        return routingName;
    }

    @Override
    public URI getURI() {
        return new URI(localURI);
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
        MALMessageHeader header = createMessageHeader(getURI(),
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
                supplements,
                qosProperties);
        MALElementStreamFactory encFactory = transport.getStreamFactory();
        LazyMessageBody lazyBody = LazyMessageBody.createMessageBody(header, encFactory, body);
        return new GENMessage(header, lazyBody, encFactory, qosProperties);
    }

    @Override
    public void sendMessage(final MALMessage msg) throws MALTransmitErrorException {
        this.internalSendMessage(null, true, (GENMessage) msg);
    }

    @Override
    public void sendMessages(final MALMessage[] msgList) throws MALTransmitMultipleErrorException {
        final List<MALTransmitErrorException> exceptions = new LinkedList<>();

        try {
            final Object multiSendHandle = internalCreateMultiSendHandle(msgList);

            for (int i = 0; i < msgList.length; i++) {
                try {
                    boolean isLast = (i == (msgList.length - 1));
                    this.internalSendMessage(multiSendHandle, isLast, (GENMessage) msgList[i]);
                } catch (MALTransmitErrorException ex) {
                    exceptions.add(ex);
                }
            }

            internalCloseMultiSendHandle(multiSendHandle, msgList);
        } catch (Exception ex) {
            exceptions.add(new MALTransmitErrorException(null,
                    new InternalException(ex.getMessage()),
                    null));
        }

        if (!exceptions.isEmpty()) {
            throw new MALTransmitMultipleErrorException(exceptions.toArray(new MALTransmitErrorException[exceptions.size()]));
        }
    }

    @Override
    public void setMessageListener(final MALMessageListener list) throws MALException {
        this.messageListener = list;
    }

    /**
     * Callback method when a message is received for this endpoint.
     *
     * @param msg The received message.
     * @throws MALException on an error.
     */
    public void receiveMessage(final MALMessage msg) throws MALException {
        if (active && (messageListener != null)) {
            messageListener.onMessage(this, msg);
        } else {
            Transport.LOGGER.log(Level.WARNING,
                    "GENEndpoint ({0}) Discarding message active({1}) listener({2}) {3}",
                    new Object[]{localName, active, messageListener, msg.toString()});
        }
    }

    public NamedValueList getEndpointSupplements() {
        return endpointSupplements;
    }

    /**
     * Callback method when multiple messages are received for this endpoint.
     *
     * @param msgs The received messages.
     * @throws MALException on an error.
     */
    public void receiveMessages(final GENMessage[] msgs) throws MALException {
        if (active && (messageListener != null)) {
            messageListener.onMessages(this, msgs);
        } else {
            Transport.LOGGER.log(Level.WARNING,
                    "GENEndpoint ({0}) Discarding messages active({1}) listener({2})",
                    new Object[]{localName, active, messageListener});
        }
    }

    @Override
    public void close() throws MALException {
        // does nothing
    }

    /**
     * Used to send a message from this end point.
     *
     * @param multiSendHandle Multi send context handle object that is passed to
     * the transport.
     * @param lastForHandle Is this the last message in a multi message send?
     * @param msg the message to send.
     * @throws MALTransmitErrorException On a transmit error.
     */
    protected void internalSendMessage(final Object multiSendHandle,
            final boolean lastForHandle, final GENMessage msg) throws MALTransmitErrorException {
        transport.sendMessage(multiSendHandle, lastForHandle, msg);
    }

    /**
     * Create a send context handle for a multi message send.
     *
     * @param msgList The list of messages being sent.
     * @return The send context handle or null by default.
     * @throws Exception On error.
     */
    protected Object internalCreateMultiSendHandle(final MALMessage[] msgList) throws Exception {
        // implemented in derived transport if it uses multi-send handles. 
        return null;
    }

    /**
     * Closes a multi send context handle.
     *
     * @param multiSendHandle The multi send context handle.
     * @param msgList The sent message list.
     * @throws Exception On error.
     */
    protected void internalCloseMultiSendHandle(final Object multiSendHandle,
            final MALMessage[] msgList) throws Exception {
        // implemented in derived transport if it uses multi-send handles. 
    }

    /**
     * Internal method for creating the correct message header type. Expected to
     * be overridden in derived classes.
     *
     * @param uriFrom URI of the message source
     * @param authenticationId Authentication identifier of the message
     * @param uriTo URI of the message destination
     * @param timestamp Timestamp of the message
     * @param interactionType Interaction type of the operation
     * @param interactionStage Interaction stage of the interaction
     * @param transactionId Transaction identifier of the interaction, may be
     * null.
     * @param serviceArea Area number of the service
     * @param service Service number
     * @param operation Operation number
     * @param serviceVersion Service version number
     * @param isErrorMessage Flag indicating if the message conveys an error
     * @param supplements The header supplements
     * @param qosProperties QoS properties of the message, may be null.
     * @return the new message header.
     */
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
            final NamedValueList supplements,
            final Map qosProperties) {
        return new MALMessageHeader(
                new Identifier(uriFrom.getValue()),
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
                endpointSupplements);
    }
}
