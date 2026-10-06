package org.ccsds.moims.mo.mc.group.structures;

import org.ccsds.moims.mo.com.structures.ObjectType;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.LongList;

/**
 * The GroupDetails structure holds the object type, domain, and set of object
 * instance identifiers for a set of objects from another service.
 */
public final class GroupDetails implements Composite {

    private static final long serialVersionUID = 1125934283358209L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125934283358209L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Description of the group.
     */
    private String description;

    /**
     * The object type of the objects referenced by this group.
     */
    private ObjectType objectType;

    /**
     * The domain of the objects being referenced by this group.
     */
    private IdentifierList domain;

    /**
     * The list of object instance identifiers of the objects being referenced
     * by this group.
     */
    private LongList instanceIds;

    /**
     * Default constructor for GroupDetails.
     * 
     */
    public GroupDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param description Description of the group.
     * @param objectType The object type of the objects referenced by this group.
     * @param domain The domain of the objects being referenced by this group.
     * @param instanceIds The list of object instance identifiers of the objects being referenced by this group.
     */
    public GroupDetails(String description,
            ObjectType objectType,
            IdentifierList domain,
            LongList instanceIds) {
        this.description = description;
        this.objectType = objectType;
        this.domain = domain;
        this.instanceIds = instanceIds;
    }

    @Override
    public Element createElement() {
        return new GroupDetails();
    }

    /**
     * Returns the field description.
     * 
     * @return The field description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the field objectType.
     * 
     * @return The field objectType
     */
    public ObjectType getObjectType() {
        return objectType;
    }

    /**
     * Returns the field domain.
     * 
     * @return The field domain
     */
    public IdentifierList getDomain() {
        return domain;
    }

    /**
     * Returns the field instanceIds.
     * 
     * @return The field instanceIds
     */
    public LongList getInstanceIds() {
        return instanceIds;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GroupDetails) {
            GroupDetails other = (GroupDetails) obj;
            if (description == null) {
                if (other.description != null) {
                    return false;
                }
            } else {
                if (! description.equals(other.description)) {
                    return false;
                }
            }
            if (objectType == null) {
                if (other.objectType != null) {
                    return false;
                }
            } else {
                if (! objectType.equals(other.objectType)) {
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
            if (instanceIds == null) {
                if (other.instanceIds != null) {
                    return false;
                }
            } else {
                if (! instanceIds.equals(other.instanceIds)) {
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
        hash = 83 * hash + (description != null ? description.hashCode() : 0);
        hash = 83 * hash + (objectType != null ? objectType.hashCode() : 0);
        hash = 83 * hash + (domain != null ? domain.hashCode() : 0);
        hash = 83 * hash + (instanceIds != null ? instanceIds.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(GroupDetails: ");
        buf.append("description=").append(description);
        buf.append(", objectType=").append(objectType);
        buf.append(", domain=").append(domain);
        buf.append(", instanceIds=").append(instanceIds);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (description == null) {
            throw new MALException("The field 'description' cannot be null!");
        }
        if (objectType == null) {
            throw new MALException("The field 'objectType' cannot be null!");
        }
        if (domain == null) {
            throw new MALException("The field 'domain' cannot be null!");
        }
        if (instanceIds == null) {
            throw new MALException("The field 'instanceIds' cannot be null!");
        }
        encoder.encodeString(description);
        encoder.encodeElement(objectType);
        encoder.encodeElement(domain);
        encoder.encodeElement(instanceIds);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        description = decoder.decodeString();
        objectType = (ObjectType) decoder.decodeElement(new ObjectType());
        domain = (IdentifierList) decoder.decodeElement(new IdentifierList());
        instanceIds = (LongList) decoder.decodeElement(new LongList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
