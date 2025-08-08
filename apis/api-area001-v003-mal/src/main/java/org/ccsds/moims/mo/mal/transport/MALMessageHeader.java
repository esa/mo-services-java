/* ----------------------------------------------------------------------------
 * Copyright (C) 2023      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java API
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
package org.ccsds.moims.mo.mal.transport;

import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInvokeOperation;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALProgressOperation;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.NotFoundException;
import org.ccsds.moims.mo.mal.UnsupportedAreaException;
import org.ccsds.moims.mo.mal.UnsupportedServiceException;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.NamedValue;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * The MALMessageHeader structure is used to hold the header fields that exist
 * in the header of MAL messages.
 */
public class MALMessageHeader {

    protected Identifier from;
    protected Blob authenticationId;
    protected Identifier to;
    protected Time timestamp;
    protected InteractionType interactionType;
    protected UOctet interactionStage;
    protected Long transactionId;
    protected UShort serviceArea;
    protected UShort service;
    protected UShort operation;
    protected UOctet areaVersion;
    protected Boolean isErrorMessage;
    protected NamedValueList supplements;
    private MALOperation malOperation;

    /**
     * Constructor.
     */
    public MALMessageHeader() {
    }

    /**
     * Constructor.
     *
     * @param from URI of the message source
     * @param authenticationId Authentication identifier of the message
     * @param to URI of the message destination
     * @param timestamp Timestamp of the message
     * @param interactionType Interaction type of the operation
     * @param interactionStage Interaction stage of the interaction
     * @param transactionId Transaction identifier of the interaction, may be
     * null.
     * @param serviceArea Area number of the service
     * @param service Service number
     * @param operation Operation number
     * @param areaVersion Area version number
     * @param isErrorMessage Flag indicating if the message conveys an error
     * @param supplements The header supplements
     */
    public MALMessageHeader(final Identifier from,
            final Blob authenticationId,
            final Identifier to,
            final Time timestamp,
            final InteractionType interactionType,
            final UOctet interactionStage,
            final Long transactionId,
            final UShort serviceArea,
            final UShort service,
            final UShort operation,
            final UOctet areaVersion,
            final Boolean isErrorMessage,
            final NamedValueList supplements) {
        if (supplements == null) {
            throw new IllegalArgumentException("The supplements field cannot be NULL!");
        }

        this.from = from;
        this.authenticationId = authenticationId;
        this.to = to;
        this.timestamp = timestamp;
        this.interactionType = interactionType;
        this.interactionStage = interactionStage;
        this.transactionId = transactionId;
        this.serviceArea = serviceArea;
        this.service = service;
        this.operation = operation;
        this.areaVersion = areaVersion;
        this.isErrorMessage = isErrorMessage;
        this.supplements = supplements;
    }

    /**
     * Returns the field from.
     *
     * @return the field from.
     */
    @Deprecated
    public URI getFromURI() {
        return new URI(from.getValue());
    }

    /**
     * Returns the field from.
     *
     * @return the field from.
     */
    public Identifier getFrom() {
        return this.from;
    }

    /**
     * Sets the field from.
     *
     * @param from The from value to set.
     */
    @Deprecated
    public void setFrom(final Identifier from) {
        this.from = from;
    }

    /**
     * Returns the field authenticationId.
     *
     * @return the field authenticationId.
     */
    public Blob getAuthenticationId() {
        return authenticationId;
    }

    /**
     * Sets the field authenticationId.
     *
     * @param newValue The new value to set.
     */
    public void setAuthenticationId(final Blob newValue) {
        this.authenticationId = newValue;
    }

    /**
     * Returns the field to.
     *
     * @return the field to.
     */
    @Deprecated
    public URI getToURI() {
        return new URI(to.getValue());
    }

    /**
     * Returns the field to.
     *
     * @return the field to.
     */
    public Identifier getTo() {
        return to;
    }

    /**
     * Returns the field timestamp.
     *
     * @return the field timestamp.
     */
    public Time getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the field interactionType.
     *
     * @return the field interactionType.
     */
    public InteractionType getInteractionType() {
        return interactionType;
    }

    /**
     * Returns the field interactionStage.
     *
     * @return the field interactionStage.
     */
    public UOctet getInteractionStage() {
        return interactionStage;
    }

    /**
     * Returns the field transactionId.
     *
     * @return the field transactionId.
     */
    public Long getTransactionId() {
        return transactionId;
    }

    /**
     * Returns the field area.
     *
     * @return the field area.
     */
    public UShort getServiceArea() {
        return serviceArea;
    }

    /**
     * Returns the field service.
     *
     * @return the field service.
     */
    public UShort getService() {
        return service;
    }

    /**
     * Returns the field operation.
     *
     * @return the field operation.
     */
    public UShort getOperation() {
        return operation;
    }

    /**
     * Returns the area version.
     *
     * @return the area version.
     */
    public UOctet getAreaVersion() {
        return areaVersion;
    }

    /**
     * Returns the field isError.
     *
     * @return the field isError.
     */
    public Boolean getIsErrorMessage() {
        return isErrorMessage;
    }

    /**
     * Returns the field supplements.
     *
     * @return the field supplements.
     */
    public NamedValueList getSupplements() {
        return supplements;
    }

    /**
     * Returns the supplement value for a given key name.
     *
     * @param keyName The key name.
     * @return the supplement value for the provided key.
     */
    public Attribute getSupplementValue(String keyName) {
        for (NamedValue pair : supplements) {
            if (pair.getName().getValue().equals(keyName)) {
                return pair.getValue();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        final StringBuilder str = new StringBuilder("MALMessageHeader{");
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
        str.append(", areaVersion=").append(areaVersion);
        str.append(", isErrorMessage=").append(isErrorMessage);
        str.append(", supplements=").append(supplements);
        str.append('}');
        return str.toString();
    }

    public UOctet calculateReturnStage() {
        UOctet iStage = this.getInteractionStage();

        if (iStage == null) {
            return null;
        }

        final short srcInteractionStage = iStage.getValue();

        switch (this.getInteractionType().getValue()) {
            case InteractionType.SUBMIT_VALUE:
                if (MALSubmitOperation._SUBMIT_STAGE == srcInteractionStage) {
                    return MALSubmitOperation.SUBMIT_ACK_STAGE;
                }
            case InteractionType.REQUEST_VALUE:
                if (MALRequestOperation._REQUEST_STAGE == srcInteractionStage) {
                    return MALRequestOperation.REQUEST_RESPONSE_STAGE;
                }
            case InteractionType.INVOKE_VALUE:
                if (MALInvokeOperation._INVOKE_STAGE == srcInteractionStage) {
                    return MALInvokeOperation.INVOKE_ACK_STAGE;
                }
            case InteractionType.PROGRESS_VALUE:
                if (MALProgressOperation._PROGRESS_STAGE == srcInteractionStage) {
                    return MALProgressOperation.PROGRESS_ACK_STAGE;
                }
            case InteractionType.PUBSUB_VALUE:
                switch (srcInteractionStage) {
                    case MALPubSubOperation._REGISTER_STAGE:
                        return MALPubSubOperation.REGISTER_ACK_STAGE;
                    case MALPubSubOperation._PUBLISH_REGISTER_STAGE:
                        return MALPubSubOperation.PUBLISH_REGISTER_ACK_STAGE;
                    case MALPubSubOperation._PUBLISH_STAGE:
                        return MALPubSubOperation.PUBLISH_STAGE;
                    case MALPubSubOperation._DEREGISTER_STAGE:
                        return MALPubSubOperation.DEREGISTER_ACK_STAGE;
                    case MALPubSubOperation._PUBLISH_DEREGISTER_STAGE:
                        return MALPubSubOperation.PUBLISH_DEREGISTER_ACK_STAGE;
                    default:
                    // no op
                }
                break;
            default:
            // no op
        }

        return null;
    }

    public void encode(final MALEncoder encoder) throws MALException {
        encoder.encodeIdentifier(from);
        encoder.encodeBlob(authenticationId);
        encoder.encodeIdentifier(to);
        encoder.encodeTime(timestamp);
        encoder.encodeElement(interactionType);
        encoder.encodeUOctet(interactionStage);
        encoder.encodeLong(transactionId);
        encoder.encodeUShort(serviceArea);
        encoder.encodeUShort(service);
        encoder.encodeUShort(operation);
        encoder.encodeUOctet(areaVersion);
        encoder.encodeBoolean(isErrorMessage);
        encoder.encodeElement(supplements);
    }

    public MALMessageHeader decode(final MALDecoder decoder) throws MALException {
        from = decoder.decodeIdentifier();
        authenticationId = decoder.decodeBlob();
        to = decoder.decodeIdentifier();
        timestamp = decoder.decodeTime();
        interactionType = (InteractionType) decoder.decodeElement(InteractionType.SEND);
        interactionStage = decoder.decodeUOctet();
        transactionId = decoder.decodeLong();
        serviceArea = decoder.decodeUShort();
        service = decoder.decodeUShort();
        operation = decoder.decodeUShort();
        areaVersion = decoder.decodeUOctet();
        isErrorMessage = decoder.decodeBoolean();
        supplements = (NamedValueList) decoder.decodeElement(new NamedValueList());
        return this;
    }

    public ServiceInfo getServiceInfo() throws NotFoundException {
        MALArea malArea = MALContextFactory.lookupArea(this.getServiceArea(), this.getAreaVersion());

        if (malArea == null) {
            throw new NotFoundException(new UnsupportedAreaException(
                    "Operation for unknown area/version received ("
                    + this.getServiceArea() + ", " + this.getAreaVersion() + ")"));
        }

        ServiceInfo serviceInfo = malArea.getServiceByNumber(this.getService());

        if (serviceInfo == null) {
            throw new NotFoundException(new UnsupportedServiceException(
                    "Service for unknown area/version/service received ("
                    + this.getServiceArea() + ", " + this.getAreaVersion()
                    + ", " + this.getService() + ")"));
        }

        return serviceInfo;
    }

    public MALOperation getMALOperation() throws NotFoundException {
        if (malOperation != null) {
            return malOperation;
        }

        ServiceInfo serviceInfo = this.getServiceInfo();
        MALOperation op = serviceInfo.getOperationByNumber(this.getOperation());

        if (op == null) {
            throw new NotFoundException(new UnsupportedOperationException(
                    "Operation for unknown area/version/service/op received ("
                    + this.getServiceArea() + ", " + this.getAreaVersion() + ", "
                    + this.getService() + ", " + this.getOperation() + ")"));
        }

        malOperation = op;
        return malOperation;
    }

}
