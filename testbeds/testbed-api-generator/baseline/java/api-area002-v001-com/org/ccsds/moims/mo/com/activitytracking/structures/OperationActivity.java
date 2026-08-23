package org.ccsds.moims.mo.com.activitytracking.structures;

/**
 * The OperationActivity structure contains the details of a MAL operation
 * activity.
 */
public final class OperationActivity implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 562962855100420L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 562962855100420L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The interaction type of the original operation message header.
     */
    private org.ccsds.moims.mo.mal.structures.InteractionType interactionType;

    /**
     * Default constructor for OperationActivity.
     * 
     */
    public OperationActivity() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param interactionType The interaction type of the original operation message header.
     */
    public OperationActivity(org.ccsds.moims.mo.mal.structures.InteractionType interactionType) {
        this.interactionType = interactionType;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.com.activitytracking.structures.OperationActivity();
    }

    /**
     * Returns the field interactionType.
     * 
     * @return The field interactionType
     */
    public org.ccsds.moims.mo.mal.structures.InteractionType getInteractionType() {
        return interactionType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OperationActivity) {
            OperationActivity other = (OperationActivity) obj;
            if (interactionType == null) {
                if (other.interactionType != null) {
                    return false;
                }
            } else {
                if (! interactionType.equals(other.interactionType)) {
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
        hash = 83 * hash + (interactionType != null ? interactionType.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(OperationActivity: ");
        buf.append("interactionType=").append(interactionType);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (interactionType == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'interactionType' cannot be null!");
        }
        encoder.encodeElement(interactionType);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        interactionType = (org.ccsds.moims.mo.mal.structures.InteractionType) decoder.decodeElement(org.ccsds.moims.mo.mal.structures.InteractionType.SEND);
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
