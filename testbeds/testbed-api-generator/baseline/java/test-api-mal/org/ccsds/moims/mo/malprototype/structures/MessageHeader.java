package org.ccsds.moims.mo.malprototype.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 * The MessageHeader structure is used to hold all fields that are passed
 * for each message exchanged between a consumer and provider. See 4.1 for
 * more information.
 */
public final class MessageHeader implements Composite {

    private static final long serialVersionUID = 28147497687842829L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687842829L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Message Source URI.
     */
    private URI URIfrom;

    /**
     * Source Authentication Credentials.
     */
    private Blob authenticationId;

    /**
     * Message Destination URI.
     */
    private URI URIto;

    /**
     * Message generation timestamp.
     */
    private Time timestamp;

    /**
     * The QoS level of the message.
     */
    private QoSLevel QoSlevel;

    /**
     * The QoS priority of the message.
     */
    private UInteger priority;

    /**
     * Domain of the message.
     */
    private IdentifierList domain;

    /**
     * Network zone of the message.
     */
    private Identifier networkZone;

    /**
     * Type of session of the message.
     */
    private SessionType session;

    /**
     * Name of the session of the message. Shall be ‘LIVE’ if session type is
     * LIVE.
     */
    private Identifier sessionName;

    /**
     * Interaction Pattern Type.
     */
    private InteractionType interactionType;

    /**
     * Interaction Pattern Stage.
     */
    private UOctet interactionStage;

    /**
     * Unique to consumer.
     */
    private Identifier transactionId;

    /**
     * Service Area Identifier.
     */
    private Identifier area;

    /**
     * Service Identifier.
     */
    private Identifier service;

    /**
     * Service Operation Identifier.
     */
    private Identifier operation;

    /**
     * Service version.
     */
    private UOctet version;

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
    public MessageHeader(URI URIfrom,
            Blob authenticationId,
            URI URIto,
            Time timestamp,
            QoSLevel QoSlevel,
            UInteger priority,
            IdentifierList domain,
            Identifier networkZone,
            SessionType session,
            Identifier sessionName,
            InteractionType interactionType,
            UOctet interactionStage,
            Identifier transactionId,
            Identifier area,
            Identifier service,
            Identifier operation,
            UOctet version,
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
    public Element createElement() {
        return new MessageHeader();
    }

    /**
     * Returns the field URIfrom.
     * 
     * @return The field URIfrom
     */
    public URI getURIfrom() {
        return URIfrom;
    }

    /**
     * Returns the field authenticationId.
     * 
     * @return The field authenticationId
     */
    public Blob getAuthenticationId() {
        return authenticationId;
    }

    /**
     * Returns the field URIto.
     * 
     * @return The field URIto
     */
    public URI getURIto() {
        return URIto;
    }

    /**
     * Returns the field timestamp.
     * 
     * @return The field timestamp
     */
    public Time getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the field QoSlevel.
     * 
     * @return The field QoSlevel
     */
    public QoSLevel getQoSlevel() {
        return QoSlevel;
    }

    /**
     * Returns the field priority.
     * 
     * @return The field priority
     */
    public UInteger getPriority() {
        return priority;
    }

    /**
     * Returns the field domain.
     * 
     * @return The field domain
     */
    public IdentifierList getDomain() {
        return domain;
    }

    /**
     * Returns the field networkZone.
     * 
     * @return The field networkZone
     */
    public Identifier getNetworkZone() {
        return networkZone;
    }

    /**
     * Returns the field session.
     * 
     * @return The field session
     */
    public SessionType getSession() {
        return session;
    }

    /**
     * Returns the field sessionName.
     * 
     * @return The field sessionName
     */
    public Identifier getSessionName() {
        return sessionName;
    }

    /**
     * Returns the field interactionType.
     * 
     * @return The field interactionType
     */
    public InteractionType getInteractionType() {
        return interactionType;
    }

    /**
     * Returns the field interactionStage.
     * 
     * @return The field interactionStage
     */
    public UOctet getInteractionStage() {
        return interactionStage;
    }

    /**
     * Returns the field transactionId.
     * 
     * @return The field transactionId
     */
    public Identifier getTransactionId() {
        return transactionId;
    }

    /**
     * Returns the field area.
     * 
     * @return The field area
     */
    public Identifier getArea() {
        return area;
    }

    /**
     * Returns the field service.
     * 
     * @return The field service
     */
    public Identifier getService() {
        return service;
    }

    /**
     * Returns the field operation.
     * 
     * @return The field operation
     */
    public Identifier getOperation() {
        return operation;
    }

    /**
     * Returns the field version.
     * 
     * @return The field version
     */
    public UOctet getVersion() {
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
    public void encode(MALEncoder encoder) throws MALException {
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
    public Element decode(MALDecoder decoder) throws MALException {
        URIfrom = decoder.decodeNullableURI();
        authenticationId = decoder.decodeNullableBlob();
        URIto = decoder.decodeNullableURI();
        timestamp = decoder.decodeNullableTime();
        QoSlevel = (QoSLevel) decoder.decodeNullableElement(QoSLevel.BESTEFFORT);
        priority = decoder.decodeNullableUInteger();
        domain = (IdentifierList) decoder.decodeNullableElement(new IdentifierList());
        networkZone = decoder.decodeNullableIdentifier();
        session = (SessionType) decoder.decodeNullableElement(SessionType.LIVE);
        sessionName = decoder.decodeNullableIdentifier();
        interactionType = (InteractionType) decoder.decodeNullableElement(InteractionType.SEND);
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
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
