package org.ccsds.moims.mo.com.activitytracking.structures;

/**
 * The structure holds details for a Release, Reception, or Forward event
 * of an activity.
 */
public final class ActivityTransfer implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 562962855100417L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 562962855100417L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The success result of this stage, TRUE if successful, FALSE otherwise.
     * If this is TRUE then the next two fields should be populated.
     */
    private Boolean success;

    /**
     * The estimated amount of time it will take to be received by the next destination
     * if Release or Forward. Contains the estimated amount of time it will take
     * to be forwarded by this relay if Reception event. May be NULL if unknown
     * or cannot be calculated.
     */
    private org.ccsds.moims.mo.mal.structures.Duration estimateDuration;

    /**
     * This contains the URI of the next destination, either another relay or
     * the provider. It is protocol specific how this value is derived.
     */
    private org.ccsds.moims.mo.mal.structures.URI nextDestination;

    /**
     * Default constructor for ActivityTransfer.
     * 
     */
    public ActivityTransfer() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param success The success result of this stage, TRUE if successful, FALSE otherwise. If this is TRUE then the next two fields should be populated.
     * @param estimateDuration The estimated amount of time it will take to be received by the next destination if Release or Forward. Contains the estimated amount of time it will take to be forwarded by this relay if Reception event. May be NULL if unknown or cannot be calculated.
     * @param nextDestination This contains the URI of the next destination, either another relay or the provider. It is protocol specific how this value is derived.
     */
    public ActivityTransfer(Boolean success,
            org.ccsds.moims.mo.mal.structures.Duration estimateDuration,
            org.ccsds.moims.mo.mal.structures.URI nextDestination) {
        this.success = success;
        this.estimateDuration = estimateDuration;
        this.nextDestination = nextDestination;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param success The success result of this stage, TRUE if successful, FALSE otherwise. If this is TRUE then the next two fields should be populated.
     */
    public ActivityTransfer(Boolean success) {
        this.success = success;
        this.estimateDuration = null;
        this.nextDestination = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.com.activitytracking.structures.ActivityTransfer();
    }

    /**
     * Returns the field success.
     * 
     * @return The field success
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * Returns the field estimateDuration.
     * 
     * @return The field estimateDuration
     */
    public org.ccsds.moims.mo.mal.structures.Duration getEstimateDuration() {
        return estimateDuration;
    }

    /**
     * Returns the field nextDestination.
     * 
     * @return The field nextDestination
     */
    public org.ccsds.moims.mo.mal.structures.URI getNextDestination() {
        return nextDestination;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ActivityTransfer) {
            ActivityTransfer other = (ActivityTransfer) obj;
            if (success == null) {
                if (other.success != null) {
                    return false;
                }
            } else {
                if (! success.equals(other.success)) {
                    return false;
                }
            }
            if (estimateDuration == null) {
                if (other.estimateDuration != null) {
                    return false;
                }
            } else {
                if (! estimateDuration.equals(other.estimateDuration)) {
                    return false;
                }
            }
            if (nextDestination == null) {
                if (other.nextDestination != null) {
                    return false;
                }
            } else {
                if (! nextDestination.equals(other.nextDestination)) {
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
        hash = 83 * hash + (estimateDuration != null ? estimateDuration.hashCode() : 0);
        hash = 83 * hash + (nextDestination != null ? nextDestination.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ActivityTransfer: ");
        buf.append("success=").append(success);
        buf.append(", estimateDuration=").append(estimateDuration);
        buf.append(", nextDestination=").append(nextDestination);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (success == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'success' cannot be null!");
        }
        encoder.encodeBoolean(success);
        encoder.encodeNullableDuration(estimateDuration);
        encoder.encodeNullableURI(nextDestination);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        success = decoder.decodeBoolean();
        estimateDuration = decoder.decodeNullableDuration();
        nextDestination = decoder.decodeNullableURI();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
