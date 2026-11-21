package org.ccsds.moims.mo.com.activitytracking.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * The structure is used to hold details of an Acceptance event.
 */
public final class ActivityAcceptance implements Composite {

    private static final long serialVersionUID = 562962855100418L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 562962855100418L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The success result of this stage, TRUE if successful, FALSE otherwise.
     */
    private Boolean success;

    /**
     * Default constructor for ActivityAcceptance.
     * 
     */
    public ActivityAcceptance() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param success The success result of this stage, TRUE if successful, FALSE otherwise.
     */
    public ActivityAcceptance(Boolean success) {
        this.success = success;
    }

    @Override
    public Element createElement() {
        return new ActivityAcceptance();
    }

    /**
     * Returns the field success.
     * 
     * @return The field success
     */
    public Boolean getSuccess() {
        return success;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ActivityAcceptance) {
            ActivityAcceptance other = (ActivityAcceptance) obj;
            if (success == null) {
                if (other.success != null) {
                    return false;
                }
            } else {
                if (! success.equals(other.success)) {
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
        hash = 83 * hash + (success != null ? success.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ActivityAcceptance: ");
        buf.append("success=").append(success);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (success == null) {
            throw new MALException("The field 'success' cannot be null!");
        }
        encoder.encodeBoolean(success);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        success = decoder.decodeBoolean();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
