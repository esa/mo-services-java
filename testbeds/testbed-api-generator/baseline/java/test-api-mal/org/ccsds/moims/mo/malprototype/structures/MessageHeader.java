package org.ccsds.moims.mo.malprototype.structures;

/**
 * The MessageHeader structure is used to hold all fields that are passed
 * for each message exchanged between a consumer and provider. See 4.1 for
 * more information.
 */
public final class MessageHeader implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 28147497687842829L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687842829L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Message Source URI.
     */
    private org.ccsds.moims.mo.mal.structures.URI URIfrom;

    /**
     * Source Authentication Credentials.
     */
    private org.ccsds.moims.mo.mal.structures.Blob authenticationId;

    /**
     * Message Destination URI.
     */
    private org.ccsds.moims.mo.mal.structures.URI URIto;

    /**
     * Message generation timestamp.
     */
    private org.ccsds.moims.mo.mal.structures.Time timestamp;

    /**
     * The QoS level of the message.
     */
    private org.ccsds.moims.mo.mal.structures.QoSLevel QoSlevel;

    /**
     * The QoS priority of the message.
     */
    private org.ccsds.moims.mo.mal.structures.UInteger priority;

    /**
     * Domain of the message.
     */
    private org.ccsds.moims.mo.mal.structures.IdentifierList domain;

    /**
     * Network zone of the message.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier networkZone;

    /**
     * Type of session of the message.
     */
    private org.ccsds.moims.mo.mal.structures.SessionType session;

    /**
     * Name of the session of the message. Shall be ‘LIVE’ if session type is
     * LIVE.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier sessionName;

    /**
     * Interaction Pattern Type.
     */
    private org.ccsds.moims.mo.mal.structures.InteractionType interactionType;

    /**
     * Interaction Pattern Stage.
     */
    private org.ccsds.moims.mo.mal.structures.UOctet interactionStage;

    /**
     * Unique to consumer.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier transactionId;

    /**
     * Service Area Identifier.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier area;

    /**
     * Service Identifier.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier service;

    /**
     * Service Operation Identifier.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier operation;

    /**
     * Service version.
     */
    private org.ccsds.moims.mo.mal.structures.UOctet version;

    /**
     * True if this is an error message else False.
     */
    private Boolean isError;

    /**
     * Default constructor for MessageHeader.
     * 
     */
    public MessageHeader() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param URIfrom Message Source URI
     * @param authenticationId Source Authentication Credentials
     * @param URIto Message Destination URI
     * @param timestamp Message generation timestamp
     * @param QoSlevel The QoS level of the message
     * @param priority The QoS priority of the message
     * @param domain Domain of the message
     * @param networkZone Network zone of the message
     * @param session Type of session of the message
     * @param sessionName Name of the session of the message. Shall be ‘LIVE’ if session type is LIVE.
     * @param interactionType Interaction Pattern Type
     * @param interactionStage Interaction Pattern Stage
     * @param transactionId Unique to consumer
     * @param area Service Area Identifier
     * @param service Service Identifier
     * @param operation Service Operation Identifier
     * @param version Service version
     * @param isError True if this is an error message else False.
     */
    public MessageHeader(org.ccsds.moims.mo.mal.structures.URI URIfrom,
            org.ccsds.moims.mo.mal.structures.Blob authenticationId,
            org.ccsds.moims.mo.mal.structures.URI URIto,
            org.ccsds.moims.mo.mal.structures.Time timestamp,
            org.ccsds.moims.mo.mal.structures.QoSLevel QoSlevel,
            org.ccsds.moims.mo.mal.structures.UInteger priority,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.Identifier networkZone,
            org.ccsds.moims.mo.mal.structures.SessionType session,
            org.ccsds.moims.mo.mal.structures.Identifier sessionName,
            org.ccsds.moims.mo.mal.structures.InteractionType interactionType,
            org.ccsds.moims.mo.mal.structures.UOctet interactionStage,
            org.ccsds.moims.mo.mal.structures.Identifier transactionId,
            org.ccsds.moims.mo.mal.structures.Identifier area,
            org.ccsds.moims.mo.mal.structures.Identifier service,
            org.ccsds.moims.mo.mal.structures.Identifier operation,
            org.ccsds.moims.mo.mal.structures.UOctet version,
            Boolean isError) {
        this.URIfrom = URIfrom;
        this.authenticationId = authenticationId;
        this.URIto = URIto;
        this.timestamp = timestamp;
        this.QoSlevel = QoSlevel;
        this.priority = priority;
        this.domain = domain;
        this.networkZone = networkZone;
        this.session = session;
        this.sessionName = sessionName;
        this.interactionType = interactionType;
        this.interactionStage = interactionStage;
        this.transactionId = transactionId;
        this.area = area;
        this.service = service;
        this.operation = operation;
        this.version = version;
        this.isError = isError;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.malprototype.structures.MessageHeader();
    }

    /**
     * Returns the field URIfrom.
     * 
     * @return The field URIfrom
     */
    public org.ccsds.moims.mo.mal.structures.URI getURIfrom() {
        return URIfrom;
    }

    /**
     * Returns the field authenticationId.
     * 
     * @return The field authenticationId
     */
    public org.ccsds.moims.mo.mal.structures.Blob getAuthenticationId() {
        return authenticationId;
    }

    /**
     * Returns the field URIto.
     * 
     * @return The field URIto
     */
    public org.ccsds.moims.mo.mal.structures.URI getURIto() {
        return URIto;
    }

    /**
     * Returns the field timestamp.
     * 
     * @return The field timestamp
     */
    public org.ccsds.moims.mo.mal.structures.Time getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the field QoSlevel.
     * 
     * @return The field QoSlevel
     */
    public org.ccsds.moims.mo.mal.structures.QoSLevel getQoSlevel() {
        return QoSlevel;
    }

    /**
     * Returns the field priority.
     * 
     * @return The field priority
     */
    public org.ccsds.moims.mo.mal.structures.UInteger getPriority() {
        return priority;
    }

    /**
     * Returns the field domain.
     * 
     * @return The field domain
     */
    public org.ccsds.moims.mo.mal.structures.IdentifierList getDomain() {
        return domain;
    }

    /**
     * Returns the field networkZone.
     * 
     * @return The field networkZone
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getNetworkZone() {
        return networkZone;
    }

    /**
     * Returns the field session.
     * 
     * @return The field session
     */
    public org.ccsds.moims.mo.mal.structures.SessionType getSession() {
        return session;
    }

    /**
     * Returns the field sessionName.
     * 
     * @return The field sessionName
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getSessionName() {
        return sessionName;
    }

    /**
     * Returns the field interactionType.
     * 
     * @return The field interactionType
     */
    public org.ccsds.moims.mo.mal.structures.InteractionType getInteractionType() {
        return interactionType;
    }

    /**
     * Returns the field interactionStage.
     * 
     * @return The field interactionStage
     */
    public org.ccsds.moims.mo.mal.structures.UOctet getInteractionStage() {
        return interactionStage;
    }

    /**
     * Returns the field transactionId.
     * 
     * @return The field transactionId
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getTransactionId() {
        return transactionId;
    }

    /**
     * Returns the field area.
     * 
     * @return The field area
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getArea() {
        return area;
    }

    /**
     * Returns the field service.
     * 
     * @return The field service
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getService() {
        return service;
    }

    /**
     * Returns the field operation.
     * 
     * @return The field operation
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getOperation() {
        return operation;
    }

    /**
     * Returns the field version.
     * 
     * @return The field version
     */
    public org.ccsds.moims.mo.mal.structures.UOctet getVersion() {
        return version;
    }

    /**
     * Returns the field isError.
     * 
     * @return The field isError
     */
    public Boolean getIsError() {
        return isError;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MessageHeader) {
            MessageHeader other = (MessageHeader) obj;
            if (URIfrom == null) {
                if (other.URIfrom != null) {
                    return false;
                }
            } else {
                if (! URIfrom.equals(other.URIfrom)) {
                    return false;
                }
            }
            if (authenticationId == null) {
                if (other.authenticationId != null) {
                    return false;
                }
            } else {
                if (! authenticationId.equals(other.authenticationId)) {
                    return false;
                }
            }
            if (URIto == null) {
                if (other.URIto != null) {
                    return false;
                }
            } else {
                if (! URIto.equals(other.URIto)) {
                    return false;
                }
            }
            if (timestamp == null) {
                if (other.timestamp != null) {
                    return false;
                }
            } else {
                if (! timestamp.equals(other.timestamp)) {
                    return false;
                }
            }
            if (QoSlevel == null) {
                if (other.QoSlevel != null) {
                    return false;
                }
            } else {
                if (! QoSlevel.equals(other.QoSlevel)) {
                    return false;
                }
            }
            if (priority == null) {
                if (other.priority != null) {
                    return false;
                }
            } else {
                if (! priority.equals(other.priority)) {
                    return false;
                }
            }
            if (domain == null) {
                if (other.domain != null) {
                    return false;
                }
            } else {
                if (! domain.equals(other.domain)) {
                    return false;
                }
            }
            if (networkZone == null) {
                if (other.networkZone != null) {
                    return false;
                }
            } else {
                if (! networkZone.equals(other.networkZone)) {
                    return false;
                }
            }
            if (session == null) {
                if (other.session != null) {
                    return false;
                }
            } else {
                if (! session.equals(other.session)) {
                    return false;
                }
            }
            if (sessionName == null) {
                if (other.sessionName != null) {
                    return false;
                }
            } else {
                if (! sessionName.equals(other.sessionName)) {
                    return false;
                }
            }
            if (interactionType == null) {
                if (other.interactionType != null) {
                    return false;
                }
            } else {
                if (! interactionType.equals(other.interactionType)) {
                    return false;
                }
            }
            if (interactionStage == null) {
                if (other.interactionStage != null) {
                    return false;
                }
            } else {
                if (! interactionStage.equals(other.interactionStage)) {
                    return false;
                }
            }
            if (transactionId == null) {
                if (other.transactionId != null) {
                    return false;
                }
            } else {
                if (! transactionId.equals(other.transactionId)) {
                    return false;
                }
            }
            if (area == null) {
                if (other.area != null) {
                    return false;
                }
            } else {
                if (! area.equals(other.area)) {
                    return false;
                }
            }
            if (service == null) {
                if (other.service != null) {
                    return false;
                }
            } else {
                if (! service.equals(other.service)) {
                    return false;
                }
            }
            if (operation == null) {
                if (other.operation != null) {
                    return false;
                }
            } else {
                if (! operation.equals(other.operation)) {
                    return false;
                }
            }
            if (version == null) {
                if (other.version != null) {
                    return false;
                }
            } else {
                if (! version.equals(other.version)) {
                    return false;
                }
            }
            if (isError == null) {
                if (other.isError != null) {
                    return false;
                }
            } else {
                if (! isError.equals(other.isError)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (URIfrom != null ? URIfrom.hashCode() : 0);
        hash = 83 * hash + (authenticationId != null ? authenticationId.hashCode() : 0);
        hash = 83 * hash + (URIto != null ? URIto.hashCode() : 0);
        hash = 83 * hash + (timestamp != null ? timestamp.hashCode() : 0);
        hash = 83 * hash + (QoSlevel != null ? QoSlevel.hashCode() : 0);
        hash = 83 * hash + (priority != null ? priority.hashCode() : 0);
        hash = 83 * hash + (domain != null ? domain.hashCode() : 0);
        hash = 83 * hash + (networkZone != null ? networkZone.hashCode() : 0);
        hash = 83 * hash + (session != null ? session.hashCode() : 0);
        hash = 83 * hash + (sessionName != null ? sessionName.hashCode() : 0);
        hash = 83 * hash + (interactionType != null ? interactionType.hashCode() : 0);
        hash = 83 * hash + (interactionStage != null ? interactionStage.hashCode() : 0);
        hash = 83 * hash + (transactionId != null ? transactionId.hashCode() : 0);
        hash = 83 * hash + (area != null ? area.hashCode() : 0);
        hash = 83 * hash + (service != null ? service.hashCode() : 0);
        hash = 83 * hash + (operation != null ? operation.hashCode() : 0);
        hash = 83 * hash + (version != null ? version.hashCode() : 0);
        hash = 83 * hash + (isError != null ? isError.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(MessageHeader: ");
        buf.append("URIfrom=").append(URIfrom);
        buf.append(", authenticationId=").append(authenticationId);
        buf.append(", URIto=").append(URIto);
        buf.append(", timestamp=").append(timestamp);
        buf.append(", QoSlevel=").append(QoSlevel);
        buf.append(", priority=").append(priority);
        buf.append(", domain=").append(domain);
        buf.append(", networkZone=").append(networkZone);
        buf.append(", session=").append(session);
        buf.append(", sessionName=").append(sessionName);
        buf.append(", interactionType=").append(interactionType);
        buf.append(", interactionStage=").append(interactionStage);
        buf.append(", transactionId=").append(transactionId);
        buf.append(", area=").append(area);
        buf.append(", service=").append(service);
        buf.append(", operation=").append(operation);
        buf.append(", version=").append(version);
        buf.append(", isError=").append(isError);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        encoder.encodeNullableURI(URIfrom);
        encoder.encodeNullableBlob(authenticationId);
        encoder.encodeNullableURI(URIto);
        encoder.encodeNullableTime(timestamp);
        encoder.encodeNullableElement(QoSlevel);
        encoder.encodeNullableUInteger(priority);
        encoder.encodeNullableElement(domain);
        encoder.encodeNullableIdentifier(networkZone);
        encoder.encodeNullableElement(session);
        encoder.encodeNullableIdentifier(sessionName);
        encoder.encodeNullableElement(interactionType);
        encoder.encodeNullableUOctet(interactionStage);
        encoder.encodeNullableIdentifier(transactionId);
        encoder.encodeNullableIdentifier(area);
        encoder.encodeNullableIdentifier(service);
        encoder.encodeNullableIdentifier(operation);
        encoder.encodeNullableUOctet(version);
        encoder.encodeNullableBoolean(isError);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        URIfrom = decoder.decodeNullableURI();
        authenticationId = decoder.decodeNullableBlob();
        URIto = decoder.decodeNullableURI();
        timestamp = decoder.decodeNullableTime();
        QoSlevel = (org.ccsds.moims.mo.mal.structures.QoSLevel) decoder.decodeNullableElement(org.ccsds.moims.mo.mal.structures.QoSLevel.BESTEFFORT);
        priority = decoder.decodeNullableUInteger();
        domain = (org.ccsds.moims.mo.mal.structures.IdentifierList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.IdentifierList());
        networkZone = decoder.decodeNullableIdentifier();
        session = (org.ccsds.moims.mo.mal.structures.SessionType) decoder.decodeNullableElement(org.ccsds.moims.mo.mal.structures.SessionType.LIVE);
        sessionName = decoder.decodeNullableIdentifier();
        interactionType = (org.ccsds.moims.mo.mal.structures.InteractionType) decoder.decodeNullableElement(org.ccsds.moims.mo.mal.structures.InteractionType.SEND);
        interactionStage = decoder.decodeNullableUOctet();
        transactionId = decoder.decodeNullableIdentifier();
        area = decoder.decodeNullableIdentifier();
        service = decoder.decodeNullableIdentifier();
        operation = decoder.decodeNullableIdentifier();
        version = decoder.decodeNullableUOctet();
        isError = decoder.decodeNullableBoolean();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
