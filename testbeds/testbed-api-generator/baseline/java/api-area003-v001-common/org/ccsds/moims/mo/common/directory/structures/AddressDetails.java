package org.ccsds.moims.mo.common.directory.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.QoSLevelList;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 * The AddressDetails structure holds all information required by the Directory
 * service about a service providers URI and attributes relating to QoS.
 */
public final class AddressDetails implements Composite {

    private static final long serialVersionUID = 844429241876484L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 844429241876484L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The set of possible QoS levels this service can provide.
     */
    private QoSLevelList supportedLevels;

    /**
     * Any QoS properties relevant to this address URIs and the specified transport.
     */
    private NamedValueList QoSproperties;

    /**
     * The number of QoS priority levels that this provider supports.
     */
    private UInteger priorityLevels;

    /**
     * The Service URI that identifies the physical location of this service.
     * NULL if represents a shared data provider (Broker).
     */
    private URI serviceURI;

    /**
     * The broker URI that identifies the physical location of the publish and
     * subscribe interface. NULL if service does not use publish and subscribe
     * operations or if a shared broker is to be used.
     */
    private URI brokerURI;

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
    public AddressDetails(QoSLevelList supportedLevels,
            NamedValueList QoSproperties,
            UInteger priorityLevels,
            URI serviceURI,
            URI brokerURI,
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
    public AddressDetails(QoSLevelList supportedLevels,
            NamedValueList QoSproperties,
            UInteger priorityLevels) {
        this.supportedLevels = supportedLevels;
        this.QoSproperties = QoSproperties;
        this.priorityLevels = priorityLevels;
        this.serviceURI = null;
        this.brokerURI = null;
        this.brokerProviderObjInstId = null;
    }

    @Override
    public Element createElement() {
        return new AddressDetails();
    }

    /**
     * Returns the field supportedLevels.
     * 
     * @return The field supportedLevels
     */
    public QoSLevelList getSupportedLevels() {
        return supportedLevels;
    }

    /**
     * Returns the field QoSproperties.
     * 
     * @return The field QoSproperties
     */
    public NamedValueList getQoSproperties() {
        return QoSproperties;
    }

    /**
     * Returns the field priorityLevels.
     * 
     * @return The field priorityLevels
     */
    public UInteger getPriorityLevels() {
        return priorityLevels;
    }

    /**
     * Returns the field serviceURI.
     * 
     * @return The field serviceURI
     */
    public URI getServiceURI() {
        return serviceURI;
    }

    /**
     * Returns the field brokerURI.
     * 
     * @return The field brokerURI
     */
    public URI getBrokerURI() {
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
    public void encode(MALEncoder encoder) throws MALException {
        if (supportedLevels == null) {
            throw new MALException("The field 'supportedLevels' cannot be null!");
        }
        if (QoSproperties == null) {
            throw new MALException("The field 'QoSproperties' cannot be null!");
        }
        if (priorityLevels == null) {
            throw new MALException("The field 'priorityLevels' cannot be null!");
        }
        encoder.encodeElement(supportedLevels);
        encoder.encodeElement(QoSproperties);
        encoder.encodeUInteger(priorityLevels);
        encoder.encodeNullableURI(serviceURI);
        encoder.encodeNullableURI(brokerURI);
        encoder.encodeNullableLong(brokerProviderObjInstId);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        supportedLevels = (QoSLevelList) decoder.decodeElement(new QoSLevelList());
        QoSproperties = (NamedValueList) decoder.decodeElement(new NamedValueList());
        priorityLevels = decoder.decodeUInteger();
        serviceURI = decoder.decodeNullableURI();
        brokerURI = decoder.decodeNullableURI();
        brokerProviderObjInstId = decoder.decodeNullableLong();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
