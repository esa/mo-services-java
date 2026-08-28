package org.ccsds.moims.mo.mal.structures;

/**
 * The ServiceId structure shall represent a specific service in MO.
 */
public final class ServiceId implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 281475027043313L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475027043313L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The area of this service taken from the numeric Area identifier of the
     * service specification.
     */
    private org.ccsds.moims.mo.mal.structures.UShort keyArea;

    /**
     * The service taken from the numeric Service identifier of the service specification.
     */
    private org.ccsds.moims.mo.mal.structures.UShort keyService;

    /**
     * The Area Version of the service.
     */
    private org.ccsds.moims.mo.mal.structures.UOctet keyAreaVersion;

    /**
     * Default constructor for ServiceId.
     * 
     */
    public ServiceId() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param keyArea The area of this service taken from the numeric Area identifier of the service specification.
     * @param keyService The service taken from the numeric Service identifier of the service specification.
     * @param keyAreaVersion The Area Version of the service.
     */
    public ServiceId(org.ccsds.moims.mo.mal.structures.UShort keyArea,
            org.ccsds.moims.mo.mal.structures.UShort keyService,
            org.ccsds.moims.mo.mal.structures.UOctet keyAreaVersion) {
        this.keyArea = keyArea;
        this.keyService = keyService;
        this.keyAreaVersion = keyAreaVersion;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mal.structures.ServiceId();
    }

    /**
     * Returns the field keyArea.
     * 
     * @return The field keyArea
     */
    public org.ccsds.moims.mo.mal.structures.UShort getKeyArea() {
        return keyArea;
    }

    /**
     * Returns the field keyService.
     * 
     * @return The field keyService
     */
    public org.ccsds.moims.mo.mal.structures.UShort getKeyService() {
        return keyService;
    }

    /**
     * Returns the field keyAreaVersion.
     * 
     * @return The field keyAreaVersion
     */
    public org.ccsds.moims.mo.mal.structures.UOctet getKeyAreaVersion() {
        return keyAreaVersion;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ServiceId) {
            ServiceId other = (ServiceId) obj;
            if (keyArea == null) {
                if (other.keyArea != null) {
                    return false;
                }
            } else {
                if (! keyArea.equals(other.keyArea)) {
                    return false;
                }
            }
            if (keyService == null) {
                if (other.keyService != null) {
                    return false;
                }
            } else {
                if (! keyService.equals(other.keyService)) {
                    return false;
                }
            }
            if (keyAreaVersion == null) {
                if (other.keyAreaVersion != null) {
                    return false;
                }
            } else {
                if (! keyAreaVersion.equals(other.keyAreaVersion)) {
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
        hash = 83 * hash + (keyArea != null ? keyArea.hashCode() : 0);
        hash = 83 * hash + (keyService != null ? keyService.hashCode() : 0);
        hash = 83 * hash + (keyAreaVersion != null ? keyAreaVersion.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ServiceId: ");
        buf.append("keyArea=").append(keyArea);
        buf.append(", keyService=").append(keyService);
        buf.append(", keyAreaVersion=").append(keyAreaVersion);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (keyArea == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'keyArea' cannot be null!");
        }
        if (keyService == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'keyService' cannot be null!");
        }
        if (keyAreaVersion == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'keyAreaVersion' cannot be null!");
        }
        encoder.encodeUShort(keyArea);
        encoder.encodeUShort(keyService);
        encoder.encodeUOctet(keyAreaVersion);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        keyArea = decoder.decodeUShort();
        keyService = decoder.decodeUShort();
        keyAreaVersion = decoder.decodeUOctet();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
