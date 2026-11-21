package org.ccsds.moims.mo.com.activitytracking.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.UInteger;

/**
 * The structure is used to report the execution status of an activity in
 * the final destination.
 */
public final class ActivityExecution implements Composite {

    private static final long serialVersionUID = 562962855100419L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 562962855100419L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The success result of this stage, TRUE if successful, FALSE otherwise.
     */
    private Boolean success;

    /**
     * The execution stage of the operation.
     */
    private UInteger executionStage;

    /**
     * The total number of execution stages that will be reported.
     */
    private UInteger stageCount;

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
            UInteger executionStage,
            UInteger stageCount) {
        this.success = success;
        this.executionStage = executionStage;
        this.stageCount = stageCount;
    }

    @Override
    public Element createElement() {
        return new ActivityExecution();
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
    public UInteger getExecutionStage() {
        return executionStage;
    }

    /**
     * Returns the field stageCount.
     * 
     * @return The field stageCount
     */
    public UInteger getStageCount() {
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
    public void encode(MALEncoder encoder) throws MALException {
        if (success == null) {
            throw new MALException("The field 'success' cannot be null!");
        }
        if (executionStage == null) {
            throw new MALException("The field 'executionStage' cannot be null!");
        }
        if (stageCount == null) {
            throw new MALException("The field 'stageCount' cannot be null!");
        }
        encoder.encodeBoolean(success);
        encoder.encodeUInteger(executionStage);
        encoder.encodeUInteger(stageCount);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        success = decoder.decodeBoolean();
        executionStage = decoder.decodeUInteger();
        stageCount = decoder.decodeUInteger();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
