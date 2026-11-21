package org.ccsds.moims.mo.mc.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.UInteger;

/**
 * The ActionInProgressEvent type is used for publishing an action execution
 * reaching a new execution stage.
 */
public final class ActionInProgressEvent extends ActionEvent {

    private static final long serialVersionUID = 1125899940397070L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397070L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The stageCount field.
     */
    private UInteger stageCount;

    /**
     * The executionStage field.
     */
    private UInteger executionStage;

    /**
     * Default constructor for ActionInProgressEvent.
     * 
     */
    public ActionInProgressEvent() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param success The success field.
     * @param comment The comment field.
     * @param stageCount The stageCount field.
     * @param executionStage The executionStage field.
     */
    public ActionInProgressEvent(Boolean success,
            String comment,
            UInteger stageCount,
            UInteger executionStage) {
        super(success,
            comment);
        this.stageCount = stageCount;
        this.executionStage = executionStage;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param success The success field.
     * @param stageCount The stageCount field.
     * @param executionStage The executionStage field.
     */
    public ActionInProgressEvent(Boolean success,
            UInteger stageCount,
            UInteger executionStage) {
        super(success);
        this.stageCount = stageCount;
        this.executionStage = executionStage;
    }

    @Override
    public Element createElement() {
        return new ActionInProgressEvent();
    }

    /**
     * Returns the field stageCount.
     * 
     * @return The field stageCount
     */
    public UInteger getStageCount() {
        return stageCount;
    }

    /**
     * Returns the field executionStage.
     * 
     * @return The field executionStage
     */
    public UInteger getExecutionStage() {
        return executionStage;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ActionInProgressEvent) {
            if (! super.equals(obj)) {
                return false;
            }
            ActionInProgressEvent other = (ActionInProgressEvent) obj;
            if (stageCount == null) {
                if (other.stageCount != null) {
                    return false;
                }
            } else {
                if (! stageCount.equals(other.stageCount)) {
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
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 83 * hash + (stageCount != null ? stageCount.hashCode() : 0);
        hash = 83 * hash + (executionStage != null ? executionStage.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ActionInProgressEvent: ");
        buf.append(super.toString());
        buf.append(", stageCount=").append(stageCount);
        buf.append(", executionStage=").append(executionStage);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (stageCount == null) {
            throw new MALException("The field 'stageCount' cannot be null!");
        }
        if (executionStage == null) {
            throw new MALException("The field 'executionStage' cannot be null!");
        }
        encoder.encodeUInteger(stageCount);
        encoder.encodeUInteger(executionStage);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        stageCount = decoder.decodeUInteger();
        executionStage = decoder.decodeUInteger();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
