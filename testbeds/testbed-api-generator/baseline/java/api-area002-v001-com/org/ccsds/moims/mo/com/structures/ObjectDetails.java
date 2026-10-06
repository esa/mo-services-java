package org.ccsds.moims.mo.com.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * The ObjectDetails type is used to hold the extra information associated
 * with an object instance, namely the related and source links.
 */
public final class ObjectDetails implements Composite {

    private static final long serialVersionUID = 562949970198532L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 562949970198532L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

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
    private ObjectId source;

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
            ObjectId source) {
        this.related = related;
        this.source = source;
    }

    @Override
    public Element createElement() {
        return new ObjectDetails();
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
    public ObjectId getSource() {
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
    public void encode(MALEncoder encoder) throws MALException {
        encoder.encodeNullableLong(related);
        encoder.encodeNullableElement(source);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        related = decoder.decodeNullableLong();
        source = (ObjectId) decoder.decodeNullableElement(new ObjectId());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
