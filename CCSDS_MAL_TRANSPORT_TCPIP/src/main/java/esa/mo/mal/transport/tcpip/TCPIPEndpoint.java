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
package esa.mo.mal.transport.tcpip;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.*;

/**
 * A generic implementation of the end point interface.
 */
public class TCPIPEndpoint implements MALEndpoint {
    private final TCPIPTransport transport;
    private final String localName;
    private final String localURI;
    private final boolean wrapBodyParts;
    private boolean active = false;
    private MALMessageListener listener = null;

    /**
     * Constructor.
     *
     * @param transport Parent transport.
     * @param localName Endpoint local name.
     * @param uri The URI string for this end point.
     * @param wrapBodyParts True if the encoded body parts should be wrapped in
     *            BLOBs.
     */
    public TCPIPEndpoint(final TCPIPTransport transport, final String localName, final String uri, final boolean wrapBodyParts) {
	this.transport = transport;
	this.localName = localName;
	this.localURI = uri;
	this.wrapBodyParts = wrapBodyParts;
    }

    @Override
    public void startMessageDelivery() throws MALException {
	TCPIPTransport.LOGGER.log(Level.INFO, "GENEndpoint ({0}) Activating message delivery", localName);
	active = true;
    }

    @Override
    public void stopMessageDelivery() throws MALException {
	TCPIPTransport.LOGGER.log(Level.INFO, "GENEndpoint ({0}) Deactivating message delivery", localName);
	active = false;
    }

    @Override
    public String getLocalName() {
	return localName;
    }

    @Override
    public URI getURI() {
	return new URI(localURI);
    }

    @Override
    public MALMessage createMessage(final Blob authenticationId, final URI uriTo, final Time timestamp, final QoSLevel qosLevel, final UInteger priority, final IdentifierList domain, final Identifier networkZone, final SessionType session, final Identifier sessionName, final InteractionType interactionType, final UOctet interactionStage, final Long transactionId, final UShort serviceArea, final UShort service, final UShort operation, final UOctet serviceVersion, final Boolean isErrorMessage, final Map qosProperties, final Object... body) throws IllegalArgumentException, MALException {
	try {
	    return new TCPIPMessage(wrapBodyParts, new TCPIPMessageHeader(getURI(), authenticationId, uriTo, timestamp, qosLevel, priority, domain, networkZone, session, sessionName, interactionType, interactionStage, transactionId, serviceArea, service, operation, serviceVersion, isErrorMessage), qosProperties, null, body);
	} catch (MALInteractionException ex) {
	    throw new MALException("Error creating message", ex);
	}
    }

    @Override
    public MALMessage createMessage(final Blob authenticationId, final URI uriTo, final Time timestamp, final QoSLevel qosLevel, final UInteger priority, final IdentifierList domain, final Identifier networkZone, final SessionType session, final Identifier sessionName, final InteractionType interactionType, final UOctet interactionStage, final Long transactionId, final UShort serviceArea, final UShort service, final UShort operation, final UOctet serviceVersion, final Boolean isErrorMessage, final Map qosProperties, final MALEncodedBody body) throws IllegalArgumentException, MALException {
	try {
	    return new TCPIPMessage(wrapBodyParts, new TCPIPMessageHeader(getURI(), authenticationId, uriTo, timestamp, qosLevel, priority, domain, networkZone, session, sessionName, interactionType, interactionStage, transactionId, serviceArea, service, operation, serviceVersion, isErrorMessage), qosProperties, null, body);
	} catch (MALInteractionException ex) {
	    throw new MALException("Error creating message", ex);
	}
    }

    @Override
    public MALMessage createMessage(final Blob authenticationId, final URI uriTo, final Time timestamp, final QoSLevel qosLevel, final UInteger priority, final IdentifierList domain, final Identifier networkZone, final SessionType session, final Identifier sessionName, final Long transactionId, final Boolean isErrorMessage, final MALOperation op, final UOctet interactionStage, final Map qosProperties, final MALEncodedBody body) throws IllegalArgumentException, MALException {
	try {
	    return new TCPIPMessage(wrapBodyParts, new TCPIPMessageHeader(getURI(), authenticationId, uriTo, timestamp, qosLevel, priority, domain, networkZone, session, sessionName, op.getInteractionType(), interactionStage, transactionId, op.getService().getArea().getNumber(), op.getService().getNumber(), op.getNumber(), op.getService().getArea().getVersion(), isErrorMessage), qosProperties, op, body);
	} catch (MALInteractionException ex) {
	    throw new MALException("Error creating message", ex);
	}
    }

    @Override
    public MALMessage createMessage(final Blob authenticationId, final URI uriTo, final Time timestamp, final QoSLevel qosLevel, final UInteger priority, final IdentifierList domain, final Identifier networkZone, final SessionType session, final Identifier sessionName, final Long transactionId, final Boolean isErrorMessage, final MALOperation op, final UOctet interactionStage, final Map qosProperties, final Object... body) throws IllegalArgumentException, MALException {
	try {
	    return new TCPIPMessage(wrapBodyParts, new TCPIPMessageHeader(getURI(), authenticationId, uriTo, timestamp, qosLevel, priority, domain, networkZone, session, sessionName, op.getInteractionType(), interactionStage, transactionId, op.getService().getArea().getNumber(), op.getService().getNumber(), op.getNumber(), op.getService().getArea().getVersion(), isErrorMessage), qosProperties, op, body);
	} catch (MALInteractionException ex) {
	    throw new MALException("Error creating message", ex);
	}
    }

    @Override
    public void sendMessage(final MALMessage msg) throws MALTransmitErrorException {
	transport.sendMessage(this, (TCPIPMessage) msg);
    }

    @Override
    public void sendMessages(final MALMessage[] msgList) throws MALTransmitMultipleErrorException {
	transport.sendMessages(this, (TCPIPMessage[]) msgList);
    }

    @Override
    public void setMessageListener(final MALMessageListener list) throws MALException {
	this.listener = list;
    }

    /**
     * Callback method when a message is received for this endpoint.
     *
     * @param pmsg The received message.
     * @throws MALException on an error.
     */
    public void receiveMessage(final MALMessage pmsg) throws MALException {
	if (active && (null != listener)) {
	    listener.onMessage(this, pmsg);
	} else {
	    TCPIPTransport.LOGGER.log(Level.WARNING, "GENEndpoint ({0}) Discarding message active({1}) listener({2}) {3}", new Object[] { localName, active, listener, pmsg.toString() });
	}
    }

    /**
     * Callback method when multiple messages are received for this endpoint.
     *
     * @param pmsgs The received messages.
     * @throws MALException on an error.
     */
    public void receiveMessages(final TCPIPMessage[] pmsgs) throws MALException {
	if (active && (null != listener)) {
	    listener.onMessages(this, pmsgs);
	} else {
	    TCPIPTransport.LOGGER.log(Level.WARNING, "GENEndpoint ({0}) Discarding messages active({1}) listener({2})", new Object[] { localName, active, listener });
	}
    }

    @Override
    public void close() throws MALException {
	// does nothing
    }

}
