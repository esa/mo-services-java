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
     */
    public GENMessageHeader(final URI uriFrom,
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
        super(uriFrom, authenticationId, uriTo, timestamp, interactionType,
                interactionStage, transactionId, serviceArea, service, operation,
                serviceVersion, isErrorMessage, supplements);
    }

    @Override
    public Element createElement() {
        return new GENMessageHeader();
    }

    @Override
    public void encode(final MALEncoder encoder) throws MALException {
        encoder.encodeNullableURI(from);
        encoder.encodeNullableBlob(authenticationId);
        encoder.encodeNullableURI(to);
        encoder.encodeNullableTime(timestamp);
        encoder.encodeNullableElement(interactionType);
        encoder.encodeNullableUOctet(interactionStage);
        encoder.encodeNullableLong(transactionId);
        encoder.encodeNullableUShort(serviceArea);
        encoder.encodeNullableUShort(service);
        encoder.encodeNullableUShort(operation);
        encoder.encodeNullableUOctet(serviceVersion);
        encoder.encodeNullableBoolean(isErrorMessage);
        encoder.encodeNullableElement(supplements);
    }

    @Override
    public Element decode(final MALDecoder decoder) throws MALException {
        from = decoder.decodeNullableURI();
        authenticationId = decoder.decodeNullableBlob();
        to = decoder.decodeNullableURI();
        timestamp = decoder.decodeNullableTime();
        interactionType = (InteractionType) decoder.decodeNullableElement(InteractionType.SEND);
        interactionStage = decoder.decodeNullableUOctet();
        transactionId = decoder.decodeNullableLong();
        serviceArea = decoder.decodeNullableUShort();
        service = decoder.decodeNullableUShort();
        operation = decoder.decodeNullableUShort();
        serviceVersion = decoder.decodeNullableUOctet();
        isErrorMessage = decoder.decodeNullableBoolean();
        supplements = (NamedValueList) decoder.decodeNullableElement(new NamedValueList());

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
        str.append("from=").append(from);
        str.append(", authenticationId=").append(authenticationId);
        str.append(", to=").append(to);
        str.append(", timestamp=").append(timestamp);
        str.append(", interactionType=").append(interactionType);
        str.append(", interactionStage=").append(interactionStage);
        str.append(", transactionId=").append(transactionId);
        str.append(", serviceArea=").append(serviceArea);
        str.append(", service=").append(service);
        str.append(", operation=").append(operation);
        str.append(", serviceVersion=").append(serviceVersion);
        str.append(", isErrorMessage=").append(isErrorMessage);
        str.append(", supplements=").append(supplements);
        str.append('}');
        return str.toString();
    }
}
