package org.ccsds.moims.mo.malprototype.structures;

/**
 * This abstract structure is inherited by all the IP test definition structures.
 */
public final class IPTestDefinition implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 28147497687842820L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687842820L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Name of the test procedure.
     */
    private String procedureName;

    /**
     * The consumer&quot;s URI.
     */
    private org.ccsds.moims.mo.mal.structures.URI consumerURI;

    /**
     * The authentication identifier used by the consumer.
     */
    private org.ccsds.moims.mo.mal.structures.Blob authenticationId;

    /**
     * The QoS level required by the consumer.
     */
    private org.ccsds.moims.mo.mal.structures.QoSLevel Qos;

    /**
     * The priority level required by the consumer.
     */
    private org.ccsds.moims.mo.mal.structures.UInteger Priority;

    /**
     * The domain used by the consumer.
     */
    private org.ccsds.moims.mo.mal.structures.IdentifierList Domain;

    /**
     * The network zone used by the consumer.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier networkZone;

    /**
     * The type of the session used by the consumer.
     */
    private org.ccsds.moims.mo.mal.structures.SessionType Session;

    /**
     * The identifier of the session used by the consumer.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier sessionName;

    /**
     * The supplements field used by the consumer.
     */
    private org.ccsds.moims.mo.mal.structures.NamedValueList supplements;

    /**
     * The transitions that are requested by the consumer.
     */
    private org.ccsds.moims.mo.malprototype.structures.IPTestTransitionList transitions;

    /**
     * The time the consumer initiated the interaction.
     */
    private org.ccsds.moims.mo.mal.structures.Time timestamp;

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
            org.ccsds.moims.mo.mal.structures.URI consumerURI,
            org.ccsds.moims.mo.mal.structures.Blob authenticationId,
            org.ccsds.moims.mo.mal.structures.QoSLevel Qos,
            org.ccsds.moims.mo.mal.structures.UInteger Priority,
            org.ccsds.moims.mo.mal.structures.IdentifierList Domain,
            org.ccsds.moims.mo.mal.structures.Identifier networkZone,
            org.ccsds.moims.mo.mal.structures.SessionType Session,
            org.ccsds.moims.mo.mal.structures.Identifier sessionName,
            org.ccsds.moims.mo.mal.structures.NamedValueList supplements,
            org.ccsds.moims.mo.malprototype.structures.IPTestTransitionList transitions,
            org.ccsds.moims.mo.mal.structures.Time timestamp) {
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
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.malprototype.structures.IPTestDefinition();
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
    public org.ccsds.moims.mo.mal.structures.URI getConsumerURI() {
        return consumerURI;
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
     * Returns the field Qos.
     * 
     * @return The field Qos
     */
    public org.ccsds.moims.mo.mal.structures.QoSLevel getQos() {
        return Qos;
    }

    /**
     * Returns the field Priority.
     * 
     * @return The field Priority
     */
    public org.ccsds.moims.mo.mal.structures.UInteger getPriority() {
        return Priority;
    }

    /**
     * Returns the field Domain.
     * 
     * @return The field Domain
     */
    public org.ccsds.moims.mo.mal.structures.IdentifierList getDomain() {
        return Domain;
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
     * Returns the field Session.
     * 
     * @return The field Session
     */
    public org.ccsds.moims.mo.mal.structures.SessionType getSession() {
        return Session;
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
     * Returns the field supplements.
     * 
     * @return The field supplements
     */
    public org.ccsds.moims.mo.mal.structures.NamedValueList getSupplements() {
        return supplements;
    }

    /**
     * Returns the field transitions.
     * 
     * @return The field transitions
     */
    public org.ccsds.moims.mo.malprototype.structures.IPTestTransitionList getTransitions() {
        return transitions;
    }

    /**
     * Returns the field timestamp.
     * 
     * @return The field timestamp
     */
    public org.ccsds.moims.mo.mal.structures.Time getTimestamp() {
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
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
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
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        procedureName = decoder.decodeNullableString();
        consumerURI = decoder.decodeNullableURI();
        authenticationId = decoder.decodeNullableBlob();
        Qos = (org.ccsds.moims.mo.mal.structures.QoSLevel) decoder.decodeNullableElement(org.ccsds.moims.mo.mal.structures.QoSLevel.BESTEFFORT);
        Priority = decoder.decodeNullableUInteger();
        Domain = (org.ccsds.moims.mo.mal.structures.IdentifierList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.IdentifierList());
        networkZone = decoder.decodeNullableIdentifier();
        Session = (org.ccsds.moims.mo.mal.structures.SessionType) decoder.decodeNullableElement(org.ccsds.moims.mo.mal.structures.SessionType.LIVE);
        sessionName = decoder.decodeNullableIdentifier();
        supplements = (org.ccsds.moims.mo.mal.structures.NamedValueList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.NamedValueList());
        transitions = (org.ccsds.moims.mo.malprototype.structures.IPTestTransitionList) decoder.decodeNullableElement(new org.ccsds.moims.mo.malprototype.structures.IPTestTransitionList());
        timestamp = decoder.decodeNullableTime();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
