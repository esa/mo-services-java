package org.ccsds.moims.mo.com.structures;

/**
 * The ObjectType structure uniquely identifies the type of an object. It
 * is the combination of the area number, service number, area version, and
 * service object type number. The combined parts are able to fit inside a
 * MAL::Long (for implementations that prefer to index on a single numeric
 * field rather than a structure).
 */
public final class ObjectType implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 562949970198529L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 562949970198529L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Area Number where the object type is defined. Must not be &quot;0&quot;
     * for values as this is the wildcard.
     */
    private org.ccsds.moims.mo.mal.structures.UShort area;

    /**
     * Service Number of the service where the object type is defined. Must not
     * be &quot;0&quot; for values as this is the wildcard.
     */
    private org.ccsds.moims.mo.mal.structures.UShort service;

    /**
     * Area Version of the service where the object type is defined. Must not
     * be &quot;0&quot; for values as this is the wildcard.
     */
    private org.ccsds.moims.mo.mal.structures.UOctet version;

    /**
     * The service specific object number. Must not be &quot;0&quot; for values
     * as this is the wildcard.
     */
    private org.ccsds.moims.mo.mal.structures.UShort number;

    /**
     * Default constructor for ObjectType.
     * 
     */
    public ObjectType() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param area Area Number where the object type is defined. Must not be '0' for values as this is the wildcard.
     * @param service Service Number of the service where the object type is defined. Must not be '0' for values as this is the wildcard.
     * @param version Area Version of the service where the object type is defined. Must not be '0' for values as this is the wildcard.
     * @param number The service specific object number. Must not be '0' for values as this is the wildcard.
     */
    public ObjectType(org.ccsds.moims.mo.mal.structures.UShort area,
            org.ccsds.moims.mo.mal.structures.UShort service,
            org.ccsds.moims.mo.mal.structures.UOctet version,
            org.ccsds.moims.mo.mal.structures.UShort number) {
        this.area = area;
        this.service = service;
        this.version = version;
        this.number = number;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.com.structures.ObjectType();
    }

    /**
     * Returns the field area.
     * 
     * @return The field area
     */
    public org.ccsds.moims.mo.mal.structures.UShort getArea() {
        return area;
    }

    /**
     * Returns the field service.
     * 
     * @return The field service
     */
    public org.ccsds.moims.mo.mal.structures.UShort getService() {
        return service;
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
     * Returns the field number.
     * 
     * @return The field number
     */
    public org.ccsds.moims.mo.mal.structures.UShort getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ObjectType) {
            ObjectType other = (ObjectType) obj;
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
            if (version == null) {
                if (other.version != null) {
                    return false;
                }
            } else {
                if (! version.equals(other.version)) {
                    return false;
                }
            }
            if (number == null) {
                if (other.number != null) {
                    return false;
                }
            } else {
                if (! number.equals(other.number)) {
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
        hash = 83 * hash + (area != null ? area.hashCode() : 0);
        hash = 83 * hash + (service != null ? service.hashCode() : 0);
        hash = 83 * hash + (version != null ? version.hashCode() : 0);
        hash = 83 * hash + (number != null ? number.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ObjectType: ");
        buf.append("area=").append(area);
        buf.append(", service=").append(service);
        buf.append(", version=").append(version);
        buf.append(", number=").append(number);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (area == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'area' cannot be null!");
        }
        if (service == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'service' cannot be null!");
        }
        if (version == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'version' cannot be null!");
        }
        if (number == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'number' cannot be null!");
        }
        encoder.encodeUShort(area);
        encoder.encodeUShort(service);
        encoder.encodeUOctet(version);
        encoder.encodeUShort(number);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        area = decoder.decodeUShort();
        service = decoder.decodeUShort();
        version = decoder.decodeUOctet();
        number = decoder.decodeUShort();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
