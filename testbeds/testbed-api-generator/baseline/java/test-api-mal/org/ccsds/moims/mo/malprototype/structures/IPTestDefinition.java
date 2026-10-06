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
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 * This abstract structure is inherited by all the IP test definition structures.
 */
public final class IPTestDefinition implements Composite {

    private static final long serialVersionUID = 28147497687842820L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687842820L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Name of the test procedure.
     */
    private String procedureName;

    /**
     * The consumer&quot;s URI.
     */
    private URI consumerURI;

    /**
     * The authentication identifier used by the consumer.
     */
    private Blob authenticationId;

    /**
     * The QoS level required by the consumer.
     */
    private QoSLevel Qos;

    /**
     * The priority level required by the consumer.
     */
    private UInteger Priority;

    /**
     * The domain used by the consumer.
     */
    private IdentifierList Domain;

    /**
     * The network zone used by the consumer.
     */
    private Identifier networkZone;

    /**
     * The type of the session used by the consumer.
     */
    private SessionType Session;

    /**
     * The identifier of the session used by the consumer.
     */
    private Identifier sessionName;

    /**
     * The supplements field used by the consumer.
     */
    private NamedValueList supplements;

    /**
     * The transitions that are requested by the consumer.
     */
    private IPTestTransitionList transitions;

    /**
     * The time the consumer initiated the interaction.
     */
    private Time timestamp;

    /**
     * Default constructor for IPTestDefinition.
     * 
     */
    public IPTestDefinition() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param procedureName Name of the test procedure
     * @param consumerURI The consumer's URI
     * @param authenticationId The authentication identifier used by the consumer
     * @param Qos The QoS level required by the consumer
     * @param Priority The priority level required by the consumer
     * @param Domain The domain used by the consumer
     * @param networkZone The network zone used by the consumer
     * @param Session The type of the session used by the consumer
     * @param sessionName The identifier of the session used by the consumer
     * @param supplements The supplements field used by the consumer
     * @param transitions The transitions that are requested by the consumer
     * @param timestamp The time the consumer initiated the interaction.
     */
    public IPTestDefinition(String procedureName,
            URI consumerURI,
            Blob authenticationId,
            QoSLevel Qos,
            UInteger Priority,
            IdentifierList Domain,
            Identifier networkZone,
            SessionType Session,
            Identifier sessionName,
            NamedValueList supplements,
            IPTestTransitionList transitions,
            Time timestamp) {
        this.procedureName = procedureName;
        this.consumerURI = consumerURI;
        this.authenticationId = authenticationId;
        this.Qos = Qos;
        this.Priority = Priority;
        this.Domain = Domain;
        this.networkZone = networkZone;
        this.Session = Session;
        this.sessionName = sessionName;
        this.supplements = supplements;
        this.transitions = transitions;
        this.timestamp = timestamp;
    }

    @Override
    public Element createElement() {
        return new IPTestDefinition();
    }

    /**
     * Returns the field procedureName.
     * 
     * @return The field procedureName
     */
    public String getProcedureName() {
        return procedureName;
    }

    /**
     * Returns the field consumerURI.
     * 
     * @return The field consumerURI
     */
    public URI getConsumerURI() {
        return consumerURI;
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
     * Returns the field Qos.
     * 
     * @return The field Qos
     */
    public QoSLevel getQos() {
        return Qos;
    }

    /**
     * Returns the field Priority.
     * 
     * @return The field Priority
     */
    public UInteger getPriority() {
        return Priority;
    }

    /**
     * Returns the field Domain.
     * 
     * @return The field Domain
     */
    public IdentifierList getDomain() {
        return Domain;
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
     * Returns the field Session.
     * 
     * @return The field Session
     */
    public SessionType getSession() {
        return Session;
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
     * Returns the field supplements.
     * 
     * @return The field supplements
     */
    public NamedValueList getSupplements() {
        return supplements;
    }

    /**
     * Returns the field transitions.
     * 
     * @return The field transitions
     */
    public IPTestTransitionList getTransitions() {
        return transitions;
    }

    /**
     * Returns the field timestamp.
     * 
     * @return The field timestamp
     */
    public Time getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IPTestDefinition) {
            IPTestDefinition other = (IPTestDefinition) obj;
            if (procedureName == null) {
                if (other.procedureName != null) {
                    return false;
                }
            } else {
                if (! procedureName.equals(other.procedureName)) {
                    return false;
                }
            }
            if (consumerURI == null) {
                if (other.consumerURI != null) {
                    return false;
                }
            } else {
                if (! consumerURI.equals(other.consumerURI)) {
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
            if (Qos == null) {
                if (other.Qos != null) {
                    return false;
                }
            } else {
                if (! Qos.equals(other.Qos)) {
                    return false;
                }
            }
            if (Priority == null) {
                if (other.Priority != null) {
                    return false;
                }
            } else {
                if (! Priority.equals(other.Priority)) {
                    return false;
                }
            }
            if (Domain == null) {
                if (other.Domain != null) {
                    return false;
                }
            } else {
                if (! Domain.equals(other.Domain)) {
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
            if (Session == null) {
                if (other.Session != null) {
                    return false;
                }
            } else {
                if (! Session.equals(other.Session)) {
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
            if (supplements == null) {
                if (other.supplements != null) {
                    return false;
                }
            } else {
                if (! supplements.equals(other.supplements)) {
                    return false;
                }
            }
            if (transitions == null) {
                if (other.transitions != null) {
                    return false;
                }
            } else {
                if (! transitions.equals(other.transitions)) {
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
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (procedureName != null ? procedureName.hashCode() : 0);
        hash = 83 * hash + (consumerURI != null ? consumerURI.hashCode() : 0);
        hash = 83 * hash + (authenticationId != null ? authenticationId.hashCode() : 0);
        hash = 83 * hash + (Qos != null ? Qos.hashCode() : 0);
        hash = 83 * hash + (Priority != null ? Priority.hashCode() : 0);
        hash = 83 * hash + (Domain != null ? Domain.hashCode() : 0);
        hash = 83 * hash + (networkZone != null ? networkZone.hashCode() : 0);
        hash = 83 * hash + (Session != null ? Session.hashCode() : 0);
        hash = 83 * hash + (sessionName != null ? sessionName.hashCode() : 0);
        hash = 83 * hash + (supplements != null ? supplements.hashCode() : 0);
        hash = 83 * hash + (transitions != null ? transitions.hashCode() : 0);
        hash = 83 * hash + (timestamp != null ? timestamp.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(IPTestDefinition: ");
        buf.append("procedureName=").append(procedureName);
        buf.append(", consumerURI=").append(consumerURI);
        buf.append(", authenticationId=").append(authenticationId);
        buf.append(", Qos=").append(Qos);
        buf.append(", Priority=").append(Priority);
        buf.append(", Domain=").append(Domain);
        buf.append(", networkZone=").append(networkZone);
        buf.append(", Session=").append(Session);
        buf.append(", sessionName=").append(sessionName);
        buf.append(", supplements=").append(supplements);
        buf.append(", transitions=").append(transitions);
        buf.append(", timestamp=").append(timestamp);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        encoder.encodeNullableString(procedureName);
        encoder.encodeNullableURI(consumerURI);
        encoder.encodeNullableBlob(authenticationId);
        encoder.encodeNullableElement(Qos);
        encoder.encodeNullableUInteger(Priority);
        encoder.encodeNullableElement(Domain);
        encoder.encodeNullableIdentifier(networkZone);
        encoder.encodeNullableElement(Session);
        encoder.encodeNullableIdentifier(sessionName);
        encoder.encodeNullableElement(supplements);
        encoder.encodeNullableElement(transitions);
        encoder.encodeNullableTime(timestamp);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        procedureName = decoder.decodeNullableString();
        consumerURI = decoder.decodeNullableURI();
        authenticationId = decoder.decodeNullableBlob();
        Qos = (QoSLevel) decoder.decodeNullableElement(QoSLevel.BESTEFFORT);
        Priority = decoder.decodeNullableUInteger();
        Domain = (IdentifierList) decoder.decodeNullableElement(new IdentifierList());
        networkZone = decoder.decodeNullableIdentifier();
        Session = (SessionType) decoder.decodeNullableElement(SessionType.LIVE);
        sessionName = decoder.decodeNullableIdentifier();
        supplements = (NamedValueList) decoder.decodeNullableElement(new NamedValueList());
        transitions = (IPTestTransitionList) decoder.decodeNullableElement(new IPTestTransitionList());
        timestamp = decoder.decodeNullableTime();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
