package org.ccsds.moims.mo.malprototype.structures;

/**
 * This abstract structure is a publish context.
 */
public abstract class TestPublish implements org.ccsds.moims.mo.mal.structures.Composite {

    /**
     * The QoS level to be used by the provider.
     */
    private org.ccsds.moims.mo.mal.structures.QoSLevel Qos;

    /**
     * The priority to be used by the provider.
     */
    private org.ccsds.moims.mo.mal.structures.UInteger Priority;

    /**
     * The domain to be used by the provider.
     */
    private org.ccsds.moims.mo.mal.structures.IdentifierList domain;

    /**
     * The network zone to be used by the provider.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier networkZone;

    /**
     * The session type to be used by the provider.
     */
    private org.ccsds.moims.mo.mal.structures.SessionType Session;

    /**
     * The session name to be used by the provider.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier sessionName;

    /**
     * Whether to use the multi type version of the PubSub operation.
     */
    private Boolean testMultiType;

    /**
     * Default constructor for TestPublish.
     * 
     */
    public TestPublish() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param Qos The QoS level to be used by the provider.
     * @param Priority The priority to be used by the provider.
     * @param domain The domain to be used by the provider.
     * @param networkZone The network zone to be used by the provider.
     * @param Session The session type to be used by the provider.
     * @param sessionName The session name to be used by the provider.
     * @param testMultiType Whether to use the multi type version of the PubSub operation.
     */
    public TestPublish(org.ccsds.moims.mo.mal.structures.QoSLevel Qos,
            org.ccsds.moims.mo.mal.structures.UInteger Priority,
            org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.Identifier networkZone,
            org.ccsds.moims.mo.mal.structures.SessionType Session,
            org.ccsds.moims.mo.mal.structures.Identifier sessionName,
            Boolean testMultiType) {
        this.Qos = Qos;
        this.Priority = Priority;
        this.domain = domain;
        this.networkZone = networkZone;
        this.Session = Session;
        this.sessionName = sessionName;
        this.testMultiType = testMultiType;
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
     * Returns the field testMultiType.
     * 
     * @return The field testMultiType
     */
    public Boolean getTestMultiType() {
        return testMultiType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TestPublish) {
            TestPublish other = (TestPublish) obj;
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
            if (testMultiType == null) {
                if (other.testMultiType != null) {
                    return false;
                }
            } else {
                if (! testMultiType.equals(other.testMultiType)) {
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
        hash = 83 * hash + (Qos != null ? Qos.hashCode() : 0);
        hash = 83 * hash + (Priority != null ? Priority.hashCode() : 0);
        hash = 83 * hash + (domain != null ? domain.hashCode() : 0);
        hash = 83 * hash + (networkZone != null ? networkZone.hashCode() : 0);
        hash = 83 * hash + (Session != null ? Session.hashCode() : 0);
        hash = 83 * hash + (sessionName != null ? sessionName.hashCode() : 0);
        hash = 83 * hash + (testMultiType != null ? testMultiType.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(TestPublish: ");
        buf.append("Qos=").append(Qos);
        buf.append(", Priority=").append(Priority);
        buf.append(", domain=").append(domain);
        buf.append(", networkZone=").append(networkZone);
        buf.append(", Session=").append(Session);
        buf.append(", sessionName=").append(sessionName);
        buf.append(", testMultiType=").append(testMultiType);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        encoder.encodeNullableElement(Qos);
        encoder.encodeNullableUInteger(Priority);
        encoder.encodeNullableElement(domain);
        encoder.encodeNullableIdentifier(networkZone);
        encoder.encodeNullableElement(Session);
        encoder.encodeNullableIdentifier(sessionName);
        encoder.encodeNullableBoolean(testMultiType);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        Qos = (org.ccsds.moims.mo.mal.structures.QoSLevel) decoder.decodeNullableElement(org.ccsds.moims.mo.mal.structures.QoSLevel.BESTEFFORT);
        Priority = decoder.decodeNullableUInteger();
        domain = (org.ccsds.moims.mo.mal.structures.IdentifierList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.IdentifierList());
        networkZone = decoder.decodeNullableIdentifier();
        Session = (org.ccsds.moims.mo.mal.structures.SessionType) decoder.decodeNullableElement(org.ccsds.moims.mo.mal.structures.SessionType.LIVE);
        sessionName = decoder.decodeNullableIdentifier();
        testMultiType = decoder.decodeNullableBoolean();
        return this;
    }

}
