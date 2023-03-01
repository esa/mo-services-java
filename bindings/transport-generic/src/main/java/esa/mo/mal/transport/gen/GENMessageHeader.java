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

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * A generic implementation of the message header interface.
 */
public class GENMessageHeader extends MALMessageHeader implements Composite {

    protected static final long serialVersionUID = 111111111111111L;

    /**
     * Constructor.
     */
    public GENMessageHeader() {
    }

    /**
     * Constructor.
     *
     * @param uriFrom URI of the message source
     * @param authenticationId Authentication identifier of the message
     * @param uriTo URI of the message destination
     * @param timestamp Timestamp of the message
     * @param qosLevel QoS level of the message
     * @param priority Priority of the message
     * @param domain Domain of the service provider
     * @param networkZone Network zone of the service provider
     * @param session Session of the service provider
     * @param sessionName Session name of the service provider
     * @param interactionType Interaction type of the operation
     * @param interactionStage Interaction stage of the interaction
     * @param transactionId Transaction identifier of the interaction, may be
     * null.
     * @param serviceArea Area number of the service
     * @param service Service number
     * @param operation Operation number
     * @param serviceVersion Service version number
     * @param isErrorMessage Flag indicating if the message conveys an error
     */
    public GENMessageHeader(final URI uriFrom,
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
        super(uriFrom, authenticationId, uriTo, timestamp, qosLevel, priority,
                domain, networkZone, session, sessionName, interactionType, interactionStage,
                transactionId, serviceArea, service, operation, serviceVersion, isErrorMessage);
    }

    @Override
    public Element createElement() {
        return new GENMessageHeader();
    }

    @Override
    public void encode(final MALEncoder encoder) throws MALException {
        encoder.encodeNullableURI(URIFrom);
        encoder.encodeNullableBlob(authenticationId);
        encoder.encodeNullableURI(URITo);
        encoder.encodeNullableTime(timestamp);
        encoder.encodeNullableElement(QoSlevel);
        encoder.encodeNullableUInteger(priority);
        encoder.encodeNullableElement(domain);
        encoder.encodeNullableIdentifier(networkZone);
        encoder.encodeNullableElement(session);
        encoder.encodeNullableIdentifier(sessionName);
        encoder.encodeNullableElement(interactionType);
        encoder.encodeNullableUOctet(interactionStage);
        encoder.encodeNullableLong(transactionId);
        encoder.encodeNullableUShort(serviceArea);
        encoder.encodeNullableUShort(service);
        encoder.encodeNullableUShort(operation);
        encoder.encodeNullableUOctet(serviceVersion);
        encoder.encodeNullableBoolean(isErrorMessage);
    }

    @Override
    public Element decode(final MALDecoder decoder) throws MALException {
        URIFrom = decoder.decodeNullableURI();
        authenticationId = decoder.decodeNullableBlob();
        URITo = decoder.decodeNullableURI();
        timestamp = decoder.decodeNullableTime();
        QoSlevel = (QoSLevel) decoder.decodeNullableElement(QoSLevel.BESTEFFORT);
        priority = decoder.decodeNullableUInteger();
        domain = (IdentifierList) decoder.decodeNullableElement(new IdentifierList());
        networkZone = decoder.decodeNullableIdentifier();
        session = (SessionType) decoder.decodeNullableElement(SessionType.LIVE);
        sessionName = decoder.decodeNullableIdentifier();
        interactionType = (InteractionType) decoder.decodeNullableElement(InteractionType.SEND);
        interactionStage = decoder.decodeNullableUOctet();
        transactionId = decoder.decodeNullableLong();
        serviceArea = decoder.decodeNullableUShort();
        service = decoder.decodeNullableUShort();
        operation = decoder.decodeNullableUShort();
        serviceVersion = decoder.decodeNullableUOctet();
        isErrorMessage = decoder.decodeNullableBoolean();

        return this;
    }

    @Override
    public UShort getAreaNumber() {
        return new UShort(0);
    }

    @Override
    public UOctet getAreaVersion() {
        return new UOctet(0);
    }

    @Override
    public UShort getServiceNumber() {
        return new UShort(0);
    }

    @Override
    public Long getShortForm() {
        return 0L;
    }

    @Override
    public Integer getTypeShortForm() {
        return 0;
    }

    @Override
    public String toString() {
        final StringBuilder str = new StringBuilder("GENMessageHeader{");
        str.append("URIFrom=").append(URIFrom);
        str.append(", authenticationId=").append(authenticationId);
        str.append(", URITo=").append(URITo);
        str.append(", timestamp=").append(timestamp);
        str.append(", QoSlevel=").append(QoSlevel);
        str.append(", priority=").append(priority);
        str.append(", domain=").append(domain);
        str.append(", networkZone=").append(networkZone);
        str.append(", session=").append(session);
        str.append(", sessionName=").append(sessionName);
        str.append(", interactionType=").append(interactionType);
        str.append(", interactionStage=").append(interactionStage);
        str.append(", transactionId=").append(transactionId);
        str.append(", serviceArea=").append(serviceArea);
        str.append(", service=").append(service);
        str.append(", operation=").append(operation);
        str.append(", serviceVersion=").append(serviceVersion);
        str.append(", isErrorMessage=").append(isErrorMessage);
        str.append('}');
        return str.toString();
    }
}
