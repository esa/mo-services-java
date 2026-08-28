package org.ccsds.moims.mo.com.activitytracking.structures;

/**
 * The structure is used to report the execution status of an activity in
 * the final destination.
 */
public final class ActivityExecution implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 562962855100419L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 562962855100419L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The success result of this stage, TRUE if successful, FALSE otherwise.
     */
    private Boolean success;

    /**
     * The execution stage of the operation.
     */
    private org.ccsds.moims.mo.mal.structures.UInteger executionStage;

    /**
     * The total number of execution stages that will be reported.
     */
    private org.ccsds.moims.mo.mal.structures.UInteger stageCount;

    /**
     * Default constructor for ActivityExecution.
     * 
     */
    public ActivityExecution() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param success The success result of this stage, TRUE if successful, FALSE otherwise.
     * @param executionStage The execution stage of the operation.
     * @param stageCount The total number of execution stages that will be reported.
     */
    public ActivityExecution(Boolean success,
            org.ccsds.moims.mo.mal.structures.UInteger executionStage,
            org.ccsds.moims.mo.mal.structures.UInteger stageCount) {
        this.success = success;
        this.executionStage = executionStage;
        this.stageCount = stageCount;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.com.activitytracking.structures.ActivityExecution();
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
     * Returns the field executionStage.
     * 
     * @return The field executionStage
     */
    public org.ccsds.moims.mo.mal.structures.UInteger getExecutionStage() {
        return executionStage;
    }

    /**
     * Returns the field stageCount.
     * 
     * @return The field stageCount
     */
    public org.ccsds.moims.mo.mal.structures.UInteger getStageCount() {
        return stageCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ActivityExecution) {
            ActivityExecution other = (ActivityExecution) obj;
            if (success == null) {
                if (other.success != null) {
                    return false;
                }
            } else {
                if (! success.equals(other.success)) {
                    return false;
                }
            }
            if (executionStage == null) {
                if (other.executionStage != null) {
                    return false;
                }
            } else {
                if (! executionStage.equals(other.executionStage)) {
                    return false;
                }
            }
            if (stageCount == null) {
                if (other.stageCount != null) {
                    return false;
                }
            } else {
                if (! stageCount.equals(other.stageCount)) {
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
        hash = 83 * hash + (executionStage != null ? executionStage.hashCode() : 0);
        hash = 83 * hash + (stageCount != null ? stageCount.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ActivityExecution: ");
        buf.append("success=").append(success);
        buf.append(", executionStage=").append(executionStage);
        buf.append(", stageCount=").append(stageCount);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (success == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'success' cannot be null!");
        }
        if (executionStage == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'executionStage' cannot be null!");
        }
        if (stageCount == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'stageCount' cannot be null!");
        }
        encoder.encodeBoolean(success);
        encoder.encodeUInteger(executionStage);
        encoder.encodeUInteger(stageCount);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        success = decoder.decodeBoolean();
        executionStage = decoder.decodeUInteger();
        stageCount = decoder.decodeUInteger();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
