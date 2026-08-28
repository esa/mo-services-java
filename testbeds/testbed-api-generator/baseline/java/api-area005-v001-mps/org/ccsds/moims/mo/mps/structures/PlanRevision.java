package org.ccsds.moims.mo.mps.structures;

/**
 * E3: Each PlanRevision comprises an ordered set of ItemRevisions that document
 * the change to individual planned items (planning events and activities).
 * Each ItemRevision references an individual EventInstance or ActivityInstance
 * and indicates whether the planned item is new, modified or deleted in the
 * current Plan.
 */
public final class PlanRevision implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1407374900331001L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900331001L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Reference to the Plan with respect to which the plan revisions are detailed.
     * Typically, this is the precursor Plan, but any other Plan can be used.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> revisedPlan;

    /**
     * Start time of the earliest revision.
     */
    private org.ccsds.moims.mo.mal.structures.Time revisionStart;

    /**
     * End time of the latest revision.
     */
    private org.ccsds.moims.mo.mal.structures.Time revisionEnd;

    /**
     * Ordered list (earliest to latest) of revisions to planned items (activity
     * and event instances).
     */
    private org.ccsds.moims.mo.mps.structures.ItemRevisionList itemRevisions;

    /**
     * Default constructor for PlanRevision.
     * 
     */
    public PlanRevision() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param revisedPlan Reference to the Plan with respect to which the plan revisions are detailed.  Typically, this is the precursor Plan, but any other Plan can be used.
     * @param revisionStart Start time of the earliest revision.
     * @param revisionEnd End time of the latest revision.
     * @param itemRevisions Ordered list (earliest to latest) of revisions to planned items (activity and event instances).
     */
    public PlanRevision(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> revisedPlan,
            org.ccsds.moims.mo.mal.structures.Time revisionStart,
            org.ccsds.moims.mo.mal.structures.Time revisionEnd,
            org.ccsds.moims.mo.mps.structures.ItemRevisionList itemRevisions) {
        this.revisedPlan = revisedPlan;
        this.revisionStart = revisionStart;
        this.revisionEnd = revisionEnd;
        this.itemRevisions = itemRevisions;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param revisedPlan Reference to the Plan with respect to which the plan revisions are detailed.  Typically, this is the precursor Plan, but any other Plan can be used.
     * @param revisionStart Start time of the earliest revision.
     * @param revisionEnd End time of the latest revision.
     */
    public PlanRevision(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> revisedPlan,
            org.ccsds.moims.mo.mal.structures.Time revisionStart,
            org.ccsds.moims.mo.mal.structures.Time revisionEnd) {
        this.revisedPlan = revisedPlan;
        this.revisionStart = revisionStart;
        this.revisionEnd = revisionEnd;
        this.itemRevisions = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.PlanRevision();
    }

    /**
     * Returns the field revisedPlan.
     * 
     * @return The field revisedPlan
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> getRevisedPlan() {
        return revisedPlan;
    }

    /**
     * Returns the field revisionStart.
     * 
     * @return The field revisionStart
     */
    public org.ccsds.moims.mo.mal.structures.Time getRevisionStart() {
        return revisionStart;
    }

    /**
     * Returns the field revisionEnd.
     * 
     * @return The field revisionEnd
     */
    public org.ccsds.moims.mo.mal.structures.Time getRevisionEnd() {
        return revisionEnd;
    }

    /**
     * Returns the field itemRevisions.
     * 
     * @return The field itemRevisions
     */
    public org.ccsds.moims.mo.mps.structures.ItemRevisionList getItemRevisions() {
        return itemRevisions;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlanRevision) {
            PlanRevision other = (PlanRevision) obj;
            if (revisedPlan == null) {
                if (other.revisedPlan != null) {
                    return false;
                }
            } else {
                if (! revisedPlan.equals(other.revisedPlan)) {
                    return false;
                }
            }
            if (revisionStart == null) {
                if (other.revisionStart != null) {
                    return false;
                }
            } else {
                if (! revisionStart.equals(other.revisionStart)) {
                    return false;
                }
            }
            if (revisionEnd == null) {
                if (other.revisionEnd != null) {
                    return false;
                }
            } else {
                if (! revisionEnd.equals(other.revisionEnd)) {
                    return false;
                }
            }
            if (itemRevisions == null) {
                if (other.itemRevisions != null) {
                    return false;
                }
            } else {
                if (! itemRevisions.equals(other.itemRevisions)) {
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
        hash = 83 * hash + (revisedPlan != null ? revisedPlan.hashCode() : 0);
        hash = 83 * hash + (revisionStart != null ? revisionStart.hashCode() : 0);
        hash = 83 * hash + (revisionEnd != null ? revisionEnd.hashCode() : 0);
        hash = 83 * hash + (itemRevisions != null ? itemRevisions.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(PlanRevision: ");
        buf.append("revisedPlan=").append(revisedPlan);
        buf.append(", revisionStart=").append(revisionStart);
        buf.append(", revisionEnd=").append(revisionEnd);
        buf.append(", itemRevisions=").append(itemRevisions);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (revisedPlan == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'revisedPlan' cannot be null!");
        }
        if (revisionStart == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'revisionStart' cannot be null!");
        }
        if (revisionEnd == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'revisionEnd' cannot be null!");
        }
        encoder.encodeElement(revisedPlan);
        encoder.encodeTime(revisionStart);
        encoder.encodeTime(revisionEnd);
        encoder.encodeNullableElement(itemRevisions);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        revisedPlan = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan>());
        revisionStart = decoder.decodeTime();
        revisionEnd = decoder.decodeTime();
        itemRevisions = (org.ccsds.moims.mo.mps.structures.ItemRevisionList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mps.structures.ItemRevisionList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
