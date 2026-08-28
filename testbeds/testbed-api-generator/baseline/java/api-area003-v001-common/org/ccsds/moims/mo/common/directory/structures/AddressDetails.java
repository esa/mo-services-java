package org.ccsds.moims.mo.common.directory.structures;

/**
 * The AddressDetails structure holds all information required by the Directory
 * service about a service providers URI and attributes relating to QoS.
 */
public final class AddressDetails implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 844429241876484L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 844429241876484L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The set of possible QoS levels this service can provide.
     */
    private org.ccsds.moims.mo.mal.structures.QoSLevelList supportedLevels;

    /**
     * Any QoS properties relevant to this address URIs and the specified transport.
     */
    private org.ccsds.moims.mo.mal.structures.NamedValueList QoSproperties;

    /**
     * The number of QoS priority levels that this provider supports.
     */
    private org.ccsds.moims.mo.mal.structures.UInteger priorityLevels;

    /**
     * The Service URI that identifies the physical location of this service.
     * NULL if represents a shared data provider (Broker).
     */
    private org.ccsds.moims.mo.mal.structures.URI serviceURI;

    /**
     * The broker URI that identifies the physical location of the publish and
     * subscribe interface. NULL if service does not use publish and subscribe
     * operations or if a shared broker is to be used.
     */
    private org.ccsds.moims.mo.mal.structures.URI brokerURI;

    /**
     * The object instance identifier of a ServiceProvider COM object that is
     * the shared broker used by this provider.
     */
    private Long brokerProviderObjInstId;

    /**
     * Default constructor for AddressDetails.
     * 
     */
    public AddressDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param supportedLevels The set of possible QoS levels this service can provide.
     * @param QoSproperties Any QoS properties relevant to this address URIs and the specified transport.
     * @param priorityLevels The number of QoS priority levels that this provider supports.
     * @param serviceURI The Service URI that identifies the physical location of this service. NULL if represents a shared data provider (Broker).
     * @param brokerURI The broker URI that identifies the physical location of the publish and subscribe interface. NULL if service does not use publish and subscribe operations or if a shared broker is to be used.
     * @param brokerProviderObjInstId The object instance identifier of a ServiceProvider COM object that is the shared broker used by this provider.
     */
    public AddressDetails(org.ccsds.moims.mo.mal.structures.QoSLevelList supportedLevels,
            org.ccsds.moims.mo.mal.structures.NamedValueList QoSproperties,
            org.ccsds.moims.mo.mal.structures.UInteger priorityLevels,
            org.ccsds.moims.mo.mal.structures.URI serviceURI,
            org.ccsds.moims.mo.mal.structures.URI brokerURI,
            Long brokerProviderObjInstId) {
        this.supportedLevels = supportedLevels;
        this.QoSproperties = QoSproperties;
        this.priorityLevels = priorityLevels;
        this.serviceURI = serviceURI;
        this.brokerURI = brokerURI;
        this.brokerProviderObjInstId = brokerProviderObjInstId;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param supportedLevels The set of possible QoS levels this service can provide.
     * @param QoSproperties Any QoS properties relevant to this address URIs and the specified transport.
     * @param priorityLevels The number of QoS priority levels that this provider supports.
     */
    public AddressDetails(org.ccsds.moims.mo.mal.structures.QoSLevelList supportedLevels,
            org.ccsds.moims.mo.mal.structures.NamedValueList QoSproperties,
            org.ccsds.moims.mo.mal.structures.UInteger priorityLevels) {
        this.supportedLevels = supportedLevels;
        this.QoSproperties = QoSproperties;
        this.priorityLevels = priorityLevels;
        this.serviceURI = null;
        this.brokerURI = null;
        this.brokerProviderObjInstId = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.common.directory.structures.AddressDetails();
    }

    /**
     * Returns the field supportedLevels.
     * 
     * @return The field supportedLevels
     */
    public org.ccsds.moims.mo.mal.structures.QoSLevelList getSupportedLevels() {
        return supportedLevels;
    }

    /**
     * Returns the field QoSproperties.
     * 
     * @return The field QoSproperties
     */
    public org.ccsds.moims.mo.mal.structures.NamedValueList getQoSproperties() {
        return QoSproperties;
    }

    /**
     * Returns the field priorityLevels.
     * 
     * @return The field priorityLevels
     */
    public org.ccsds.moims.mo.mal.structures.UInteger getPriorityLevels() {
        return priorityLevels;
    }

    /**
     * Returns the field serviceURI.
     * 
     * @return The field serviceURI
     */
    public org.ccsds.moims.mo.mal.structures.URI getServiceURI() {
        return serviceURI;
    }

    /**
     * Returns the field brokerURI.
     * 
     * @return The field brokerURI
     */
    public org.ccsds.moims.mo.mal.structures.URI getBrokerURI() {
        return brokerURI;
    }

    /**
     * Returns the field brokerProviderObjInstId.
     * 
     * @return The field brokerProviderObjInstId
     */
    public Long getBrokerProviderObjInstId() {
        return brokerProviderObjInstId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AddressDetails) {
            AddressDetails other = (AddressDetails) obj;
            if (supportedLevels == null) {
                if (other.supportedLevels != null) {
                    return false;
                }
            } else {
                if (! supportedLevels.equals(other.supportedLevels)) {
                    return false;
                }
            }
            if (QoSproperties == null) {
                if (other.QoSproperties != null) {
                    return false;
                }
            } else {
                if (! QoSproperties.equals(other.QoSproperties)) {
                    return false;
                }
            }
            if (priorityLevels == null) {
                if (other.priorityLevels != null) {
                    return false;
                }
            } else {
                if (! priorityLevels.equals(other.priorityLevels)) {
                    return false;
                }
            }
            if (serviceURI == null) {
                if (other.serviceURI != null) {
                    return false;
                }
            } else {
                if (! serviceURI.equals(other.serviceURI)) {
                    return false;
                }
            }
            if (brokerURI == null) {
                if (other.brokerURI != null) {
                    return false;
                }
            } else {
                if (! brokerURI.equals(other.brokerURI)) {
                    return false;
                }
            }
            if (brokerProviderObjInstId == null) {
                if (other.brokerProviderObjInstId != null) {
                    return false;
                }
            } else {
                if (! brokerProviderObjInstId.equals(other.brokerProviderObjInstId)) {
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
        hash = 83 * hash + (supportedLevels != null ? supportedLevels.hashCode() : 0);
        hash = 83 * hash + (QoSproperties != null ? QoSproperties.hashCode() : 0);
        hash = 83 * hash + (priorityLevels != null ? priorityLevels.hashCode() : 0);
        hash = 83 * hash + (serviceURI != null ? serviceURI.hashCode() : 0);
        hash = 83 * hash + (brokerURI != null ? brokerURI.hashCode() : 0);
        hash = 83 * hash + (brokerProviderObjInstId != null ? brokerProviderObjInstId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(AddressDetails: ");
        buf.append("supportedLevels=").append(supportedLevels);
        buf.append(", QoSproperties=").append(QoSproperties);
        buf.append(", priorityLevels=").append(priorityLevels);
        buf.append(", serviceURI=").append(serviceURI);
        buf.append(", brokerURI=").append(brokerURI);
        buf.append(", brokerProviderObjInstId=").append(brokerProviderObjInstId);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (supportedLevels == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'supportedLevels' cannot be null!");
        }
        if (QoSproperties == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'QoSproperties' cannot be null!");
        }
        if (priorityLevels == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'priorityLevels' cannot be null!");
        }
        encoder.encodeElement(supportedLevels);
        encoder.encodeElement(QoSproperties);
        encoder.encodeUInteger(priorityLevels);
        encoder.encodeNullableURI(serviceURI);
        encoder.encodeNullableURI(brokerURI);
        encoder.encodeNullableLong(brokerProviderObjInstId);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        supportedLevels = (org.ccsds.moims.mo.mal.structures.QoSLevelList) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.QoSLevelList());
        QoSproperties = (org.ccsds.moims.mo.mal.structures.NamedValueList) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.NamedValueList());
        priorityLevels = decoder.decodeUInteger();
        serviceURI = decoder.decodeNullableURI();
        brokerURI = decoder.decodeNullableURI();
        brokerProviderObjInstId = decoder.decodeNullableLong();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
