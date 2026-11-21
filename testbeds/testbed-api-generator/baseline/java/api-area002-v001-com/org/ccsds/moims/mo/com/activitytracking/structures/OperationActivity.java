package org.ccsds.moims.mo.com.activitytracking.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.InteractionType;

/**
 * The OperationActivity structure contains the details of a MAL operation
 * activity.
 */
public final class OperationActivity implements Composite {

    private static final long serialVersionUID = 562962855100420L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 562962855100420L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The interaction type of the original operation message header.
     */
    private InteractionType interactionType;

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
    public OperationActivity(InteractionType interactionType) {
        this.interactionType = interactionType;
    }

    @Override
    public Element createElement() {
        return new OperationActivity();
    }

    /**
     * Returns the field interactionType.
     * 
     * @return The field interactionType
     */
    public InteractionType getInteractionType() {
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
    public void encode(MALEncoder encoder) throws MALException {
        if (interactionType == null) {
            throw new MALException("The field 'interactionType' cannot be null!");
        }
        encoder.encodeElement(interactionType);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        interactionType = (InteractionType) decoder.decodeElement(InteractionType.SEND);
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
