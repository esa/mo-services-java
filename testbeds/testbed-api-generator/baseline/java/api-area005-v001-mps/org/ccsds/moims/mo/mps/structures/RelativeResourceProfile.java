package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ObjectRef;

/**
 * E4: A variation on ResourceProfile, the RelativeResourceProfile uses relative
 * timestamps of type Duration (indicating an offset from a reference time,
 * such as the start time of an Activity).  RelativeResourceProfiles are used
 * in the context of a complex resource constraint.
 */
public final class RelativeResourceProfile implements Composite {

    private static final long serialVersionUID = 1407374900330803L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330803L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Reference to a Resource.
     */
    private ObjectRef<Resource> resource;

    /**
     * Set of RelativeProfileSegments.
     */
    private RelativeProfileSegmentList profileSegments;

    /**
     * Default constructor for RelativeResourceProfile.
     * 
     */
    public RelativeResourceProfile() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param resource Reference to a Resource.
     * @param profileSegments Set of RelativeProfileSegments.
     */
    public RelativeResourceProfile(ObjectRef<Resource> resource,
            RelativeProfileSegmentList profileSegments) {
        this.resource = resource;
        this.profileSegments = profileSegments;
    }

    @Override
    public Element createElement() {
        return new RelativeResourceProfile();
    }

    /**
     * Returns the field resource.
     * 
     * @return The field resource
     */
    public ObjectRef<Resource> getResource() {
        return resource;
    }

    /**
     * Returns the field profileSegments.
     * 
     * @return The field profileSegments
     */
    public RelativeProfileSegmentList getProfileSegments() {
        return profileSegments;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RelativeResourceProfile) {
            RelativeResourceProfile other = (RelativeResourceProfile) obj;
            if (resource == null) {
                if (other.resource != null) {
                    return false;
                }
            } else {
                if (! resource.equals(other.resource)) {
                    return false;
                }
            }
            if (profileSegments == null) {
                if (other.profileSegments != null) {
                    return false;
                }
            } else {
                if (! profileSegments.equals(other.profileSegments)) {
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
        hash = 83 * hash + (resource != null ? resource.hashCode() : 0);
        hash = 83 * hash + (profileSegments != null ? profileSegments.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(RelativeResourceProfile: ");
        buf.append("resource=").append(resource);
        buf.append(", profileSegments=").append(profileSegments);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (resource == null) {
            throw new MALException("The field 'resource' cannot be null!");
        }
        if (profileSegments == null) {
            throw new MALException("The field 'profileSegments' cannot be null!");
        }
        encoder.encodeElement(resource);
        encoder.encodeElement(profileSegments);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        resource = (ObjectRef<Resource>) decoder.decodeElement(new ObjectRef<Resource>());
        profileSegments = (RelativeProfileSegmentList) decoder.decodeElement(new RelativeProfileSegmentList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
