package org.ccsds.moims.mo.mps.structures;

/**
 * E3: An ItemRevision represents the changes that were made to a single planned
 * item inside a revision.
 */
public final class ItemRevision implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1407374900331002L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900331002L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Object Type: ActivityInstance | EventInstance. Reference to a planned ActivityInstance
     * or EventInstance that is new or modified in the current Plan, or has been
     * deleted with respect to the referenced revisedPlan.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mal.structures.Element> itemRef;

    /**
     * Revision status of the referenced item.  May be one of New, Modified, Deleted,
     * or Undefined.
     */
    private org.ccsds.moims.mo.mps.structures.RevisionStatusEnum revisionStatus;

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
    public ItemRevision(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mal.structures.Element> itemRef,
            org.ccsds.moims.mo.mps.structures.RevisionStatusEnum revisionStatus) {
        this.itemRef = itemRef;
        this.revisionStatus = revisionStatus;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.ItemRevision();
    }

    /**
     * Returns the field itemRef.
     * 
     * @return The field itemRef
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mal.structures.Element> getItemRef() {
        return itemRef;
    }

    /**
     * Returns the field revisionStatus.
     * 
     * @return The field revisionStatus
     */
    public org.ccsds.moims.mo.mps.structures.RevisionStatusEnum getRevisionStatus() {
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
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (itemRef == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'itemRef' cannot be null!");
        }
        if (revisionStatus == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'revisionStatus' cannot be null!");
        }
        encoder.encodeAbstractElement(itemRef);
        encoder.encodeElement(revisionStatus);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        itemRef = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mal.structures.Element>) decoder.decodeAbstractElement();
        revisionStatus = (org.ccsds.moims.mo.mps.structures.RevisionStatusEnum) decoder.decodeElement(org.ccsds.moims.mo.mps.structures.RevisionStatusEnum.NEW);
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
