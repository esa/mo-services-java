package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ObjectRef;

/**
 * E4: A ResourceProfile provides the evolution of a value for a single planning
 * resource over time as a set of ProfileSegments.
 */
public final class ResourceProfile implements Composite {

    private static final long serialVersionUID = 1407374900330799L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330799L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Reference to a Resource.
     */
    private ObjectRef<Resource> resource;

    /**
     * Set of Profile Segments; if these segments are not contiguous, the value
     * of the profile in those places is undefined.  The resulting behavior may
     * be defined by the planning system.
     */
    private ProfileSegmentList profileSegments;

    /**
     * Default constructor for ResourceProfile.
     * 
     */
    public ResourceProfile() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param resource Reference to a Resource.
     * @param profileSegments Set of Profile Segments; if these segments are not contiguous, the value of the profile in those places is undefined.  The resulting behavior may be defined by the planning system.
     */
    public ResourceProfile(ObjectRef<Resource> resource,
            ProfileSegmentList profileSegments) {
        this.resource = resource;
        this.profileSegments = profileSegments;
    }

    @Override
    public Element createElement() {
        return new ResourceProfile();
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
    public ProfileSegmentList getProfileSegments() {
        return profileSegments;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ResourceProfile) {
            ResourceProfile other = (ResourceProfile) obj;
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
        buf.append("(ResourceProfile: ");
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
        profileSegments = (ProfileSegmentList) decoder.decodeElement(new ProfileSegmentList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
