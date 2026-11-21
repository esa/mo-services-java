package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ObjectRef;

/**
 * E3: An ItemRevision represents the changes that were made to a single planned
 * item inside a revision.
 */
public final class ItemRevision implements Composite {

    private static final long serialVersionUID = 1407374900331002L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900331002L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Object Type: ActivityInstance | EventInstance. Reference to a planned ActivityInstance
     * or EventInstance that is new or modified in the current Plan, or has been
     * deleted with respect to the referenced revisedPlan.
     */
    private ObjectRef<Element> itemRef;

    /**
     * Revision status of the referenced item.  May be one of New, Modified, Deleted,
     * or Undefined.
     */
    private RevisionStatusEnum revisionStatus;

    /**
     * Default constructor for ItemRevision.
     * 
     */
    public ItemRevision() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param itemRef Object Type: ActivityInstance | EventInstance. Reference to a planned ActivityInstance or EventInstance that is new or modified in the current Plan, or has been deleted with respect to the referenced revisedPlan.
     * @param revisionStatus Revision status of the referenced item.  May be one of New, Modified, Deleted, or Undefined.
     */
    public ItemRevision(ObjectRef<Element> itemRef,
            RevisionStatusEnum revisionStatus) {
        this.itemRef = itemRef;
        this.revisionStatus = revisionStatus;
    }

    @Override
    public Element createElement() {
        return new ItemRevision();
    }

    /**
     * Returns the field itemRef.
     * 
     * @return The field itemRef
     */
    public ObjectRef<Element> getItemRef() {
        return itemRef;
    }

    /**
     * Returns the field revisionStatus.
     * 
     * @return The field revisionStatus
     */
    public RevisionStatusEnum getRevisionStatus() {
        return revisionStatus;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ItemRevision) {
            ItemRevision other = (ItemRevision) obj;
            if (itemRef == null) {
                if (other.itemRef != null) {
                    return false;
                }
            } else {
                if (! itemRef.equals(other.itemRef)) {
                    return false;
                }
            }
            if (revisionStatus == null) {
                if (other.revisionStatus != null) {
                    return false;
                }
            } else {
                if (! revisionStatus.equals(other.revisionStatus)) {
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
        hash = 83 * hash + (itemRef != null ? itemRef.hashCode() : 0);
        hash = 83 * hash + (revisionStatus != null ? revisionStatus.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ItemRevision: ");
        buf.append("itemRef=").append(itemRef);
        buf.append(", revisionStatus=").append(revisionStatus);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (itemRef == null) {
            throw new MALException("The field 'itemRef' cannot be null!");
        }
        if (revisionStatus == null) {
            throw new MALException("The field 'revisionStatus' cannot be null!");
        }
        encoder.encodeAbstractElement(itemRef);
        encoder.encodeElement(revisionStatus);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        itemRef = (ObjectRef<Element>) decoder.decodeAbstractElement();
        revisionStatus = (RevisionStatusEnum) decoder.decodeElement(RevisionStatusEnum.NEW);
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
