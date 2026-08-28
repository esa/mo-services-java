package org.ccsds.moims.mo.com.structures;

/**
 * The ObjectDetails type is used to hold the extra information associated
 * with an object instance, namely the related and source links.
 */
public final class ObjectDetails implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 562949970198532L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 562949970198532L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Contains the object instance identifier of a related object (e.g. the ActionDefinition
     * that an Action uses). This is service specific. The ObjectType of the related
     * object is specified in the service specification. The related object must
     * exist in the same domain as this object.
     */
    private Long related;

    /**
     * An object which is at the origin of the object creation (e.g. the procedure
     * from which an action was triggered).
     */
    private org.ccsds.moims.mo.com.structures.ObjectId source;

    /**
     * Default constructor for ObjectDetails.
     * 
     */
    public ObjectDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param related Contains the object instance identifier of a related object (e.g. the ActionDefinition that an Action uses). This is service specific. The ObjectType of the related object is specified in the service specification. The related object must exist in the same domain as this object.
     * @param source An object which is at the origin of the object creation (e.g. the procedure from which an action was triggered).
     */
    public ObjectDetails(Long related,
            org.ccsds.moims.mo.com.structures.ObjectId source) {
        this.related = related;
        this.source = source;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.com.structures.ObjectDetails();
    }

    /**
     * Returns the field related.
     * 
     * @return The field related
     */
    public Long getRelated() {
        return related;
    }

    /**
     * Returns the field source.
     * 
     * @return The field source
     */
    public org.ccsds.moims.mo.com.structures.ObjectId getSource() {
        return source;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ObjectDetails) {
            ObjectDetails other = (ObjectDetails) obj;
            if (related == null) {
                if (other.related != null) {
                    return false;
                }
            } else {
                if (! related.equals(other.related)) {
                    return false;
                }
            }
            if (source == null) {
                if (other.source != null) {
                    return false;
                }
            } else {
                if (! source.equals(other.source)) {
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
        hash = 83 * hash + (related != null ? related.hashCode() : 0);
        hash = 83 * hash + (source != null ? source.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ObjectDetails: ");
        buf.append("related=").append(related);
        buf.append(", source=").append(source);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        encoder.encodeNullableLong(related);
        encoder.encodeNullableElement(source);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        related = decoder.decodeNullableLong();
        source = (org.ccsds.moims.mo.com.structures.ObjectId) decoder.decodeNullableElement(new org.ccsds.moims.mo.com.structures.ObjectId());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
