package org.ccsds.moims.mo.mc.structures;

/**
 * The ActionExecutionRequest structure holds the information required for
 * a specific execution of an action.
 */
public final class ActionExecutionRequest implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125899940397068L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397068L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The requestId field.
     */
    private Long requestId;

    /**
     * The actionRef field.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mc.structures.ActionDefinition> actionRef;

    /**
     * The source field.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mal.structures.Element> source;

    /**
     * The stageStartedRequired field.
     */
    private Boolean stageStartedRequired;

    /**
     * The stageProgressRequired field.
     */
    private Boolean stageProgressRequired;

    /**
     * The stageCompletedRequired field.
     */
    private Boolean stageCompletedRequired;

    /**
     * The argumentValues field.
     */
    private org.ccsds.moims.mo.mal.structures.NullableAttributeList argumentValues;

    /**
     * Default constructor for ActionExecutionRequest.
     * 
     */
    public ActionExecutionRequest() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param requestId The requestId field.
     * @param actionRef The actionRef field.
     * @param source The source field.
     * @param stageStartedRequired The stageStartedRequired field.
     * @param stageProgressRequired The stageProgressRequired field.
     * @param stageCompletedRequired The stageCompletedRequired field.
     * @param argumentValues The argumentValues field.
     */
    public ActionExecutionRequest(Long requestId,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mc.structures.ActionDefinition> actionRef,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mal.structures.Element> source,
            Boolean stageStartedRequired,
            Boolean stageProgressRequired,
            Boolean stageCompletedRequired,
            org.ccsds.moims.mo.mal.structures.NullableAttributeList argumentValues) {
        this.requestId = requestId;
        this.actionRef = actionRef;
        this.source = source;
        this.stageStartedRequired = stageStartedRequired;
        this.stageProgressRequired = stageProgressRequired;
        this.stageCompletedRequired = stageCompletedRequired;
        this.argumentValues = argumentValues;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param requestId The requestId field.
     * @param actionRef The actionRef field.
     * @param stageStartedRequired The stageStartedRequired field.
     * @param stageProgressRequired The stageProgressRequired field.
     * @param stageCompletedRequired The stageCompletedRequired field.
     */
    public ActionExecutionRequest(Long requestId,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mc.structures.ActionDefinition> actionRef,
            Boolean stageStartedRequired,
            Boolean stageProgressRequired,
            Boolean stageCompletedRequired) {
        this.requestId = requestId;
        this.actionRef = actionRef;
        this.source = null;
        this.stageStartedRequired = stageStartedRequired;
        this.stageProgressRequired = stageProgressRequired;
        this.stageCompletedRequired = stageCompletedRequired;
        this.argumentValues = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.structures.ActionExecutionRequest();
    }

    /**
     * Returns the field requestId.
     * 
     * @return The field requestId
     */
    public Long getRequestId() {
        return requestId;
    }

    /**
     * Returns the field actionRef.
     * 
     * @return The field actionRef
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mc.structures.ActionDefinition> getActionRef() {
        return actionRef;
    }

    /**
     * Returns the field source.
     * 
     * @return The field source
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mal.structures.Element> getSource() {
        return source;
    }

    /**
     * Returns the field stageStartedRequired.
     * 
     * @return The field stageStartedRequired
     */
    public Boolean getStageStartedRequired() {
        return stageStartedRequired;
    }

    /**
     * Returns the field stageProgressRequired.
     * 
     * @return The field stageProgressRequired
     */
    public Boolean getStageProgressRequired() {
        return stageProgressRequired;
    }

    /**
     * Returns the field stageCompletedRequired.
     * 
     * @return The field stageCompletedRequired
     */
    public Boolean getStageCompletedRequired() {
        return stageCompletedRequired;
    }

    /**
     * Returns the field argumentValues.
     * 
     * @return The field argumentValues
     */
    public org.ccsds.moims.mo.mal.structures.NullableAttributeList getArgumentValues() {
        return argumentValues;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ActionExecutionRequest) {
            ActionExecutionRequest other = (ActionExecutionRequest) obj;
            if (requestId == null) {
                if (other.requestId != null) {
                    return false;
                }
            } else {
                if (! requestId.equals(other.requestId)) {
                    return false;
                }
            }
            if (actionRef == null) {
                if (other.actionRef != null) {
                    return false;
                }
            } else {
                if (! actionRef.equals(other.actionRef)) {
                    return false;
                }
            }
            if (source == null) {
                if (other.source != null) {
                    return false;
                }
            } else {
                if (! source.equals(other.source)) {
                    return false;
                }
            }
            if (stageStartedRequired == null) {
                if (other.stageStartedRequired != null) {
                    return false;
                }
            } else {
                if (! stageStartedRequired.equals(other.stageStartedRequired)) {
                    return false;
                }
            }
            if (stageProgressRequired == null) {
                if (other.stageProgressRequired != null) {
                    return false;
                }
            } else {
                if (! stageProgressRequired.equals(other.stageProgressRequired)) {
                    return false;
                }
            }
            if (stageCompletedRequired == null) {
                if (other.stageCompletedRequired != null) {
                    return false;
                }
            } else {
                if (! stageCompletedRequired.equals(other.stageCompletedRequired)) {
                    return false;
                }
            }
            if (argumentValues == null) {
                if (other.argumentValues != null) {
                    return false;
                }
            } else {
                if (! argumentValues.equals(other.argumentValues)) {
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
        hash = 83 * hash + (requestId != null ? requestId.hashCode() : 0);
        hash = 83 * hash + (actionRef != null ? actionRef.hashCode() : 0);
        hash = 83 * hash + (source != null ? source.hashCode() : 0);
        hash = 83 * hash + (stageStartedRequired != null ? stageStartedRequired.hashCode() : 0);
        hash = 83 * hash + (stageProgressRequired != null ? stageProgressRequired.hashCode() : 0);
        hash = 83 * hash + (stageCompletedRequired != null ? stageCompletedRequired.hashCode() : 0);
        hash = 83 * hash + (argumentValues != null ? argumentValues.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ActionExecutionRequest: ");
        buf.append("requestId=").append(requestId);
        buf.append(", actionRef=").append(actionRef);
        buf.append(", source=").append(source);
        buf.append(", stageStartedRequired=").append(stageStartedRequired);
        buf.append(", stageProgressRequired=").append(stageProgressRequired);
        buf.append(", stageCompletedRequired=").append(stageCompletedRequired);
        buf.append(", argumentValues=").append(argumentValues);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (requestId == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'requestId' cannot be null!");
        }
        if (actionRef == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'actionRef' cannot be null!");
        }
        if (stageStartedRequired == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'stageStartedRequired' cannot be null!");
        }
        if (stageProgressRequired == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'stageProgressRequired' cannot be null!");
        }
        if (stageCompletedRequired == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'stageCompletedRequired' cannot be null!");
        }
        encoder.encodeLong(requestId);
        encoder.encodeElement(actionRef);
        encoder.encodeNullableAbstractElement(source);
        encoder.encodeBoolean(stageStartedRequired);
        encoder.encodeBoolean(stageProgressRequired);
        encoder.encodeBoolean(stageCompletedRequired);
        encoder.encodeNullableElement(argumentValues);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        requestId = decoder.decodeLong();
        actionRef = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mc.structures.ActionDefinition>) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mc.structures.ActionDefinition>());
        source = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mal.structures.Element>) decoder.decodeNullableAbstractElement();
        stageStartedRequired = decoder.decodeBoolean();
        stageProgressRequired = decoder.decodeBoolean();
        stageCompletedRequired = decoder.decodeBoolean();
        argumentValues = (org.ccsds.moims.mo.mal.structures.NullableAttributeList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.NullableAttributeList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
