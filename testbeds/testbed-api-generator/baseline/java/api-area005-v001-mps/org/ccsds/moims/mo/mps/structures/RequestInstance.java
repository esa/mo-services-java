package org.ccsds.moims.mo.mps.structures;

/**
 * E1: A RequestInstance is an MO object that contains the specification of
 * a planning request.  This may change over time if the request is updated
 * by the user, each comprising a separate version of the request with the
 * same object key. In the context of a hierarchical or federated planning
 * system, a RequestInstance can be used to submit a Plan  (4.5.6) to a planning
 * function, either embedding the Plan in the RequestInstance or passing it
 * by reference.  If passed by reference, the Plan can be retrieved using
 * the Plan Distribution Service (3.6).  Patch plans are not permitted in
 * this context. NOTE – RequestInstances may be created from a RequestDefinition
 * that has defined arguments (as ArgDefs) and will in this case have the
 * associated Arguments.  An ad-hoc RequestInstance is not anticipated to
 * hold any Arguments.  The values that can be parameterized through the arguments
 * of a re-usable RequestDefinition can be directly entered in a RequestInstance,
 * and there would be no corresponding ArgDef associated with any Arguments
 * supplied.
 */
public final class RequestInstance extends org.ccsds.moims.mo.mal.structures.MOObject {

    private static final long serialVersionUID = 1407374900330898L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330898L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The contents of the planning request.
     */
    private org.ccsds.moims.mo.mps.structures.PlanningRequestDetails requestDetails;

    /**
     * Creation date and time of the RequestInstance version.
     */
    private org.ccsds.moims.mo.mal.structures.Time creationTime;

    /**
     * Current status of the ActivityInstance (see planning request state model
     * in 4.5.5.2).
     */
    private org.ccsds.moims.mo.mps.structures.RequestStatusEnum status;

    /**
     * Reference to the output Plan(s) that contains the activities resulting
     * from the planning request.  Where multiple alternate plans have been generated,
     * these may be listed here.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRefList outputPlanRefs;

    /**
     * Optional return data from the planning process, provided as a list of ID-Value
     * pairs.  This can be used to provide additional information required by
     * the User to interpret the planned operations.
     */
    private org.ccsds.moims.mo.mal.structures.NamedValueList returnData;

    /**
     * StatusInfo provides the reason for termination and is customizable, but
     * if the following conditions exist then the specified text shall be used:
     * - Completed (all constituent activities completed successfully); - Expired
     * (constituent activities expired prior to execution); - Failed (constituent
     * activities failed during execution); - Deleted (constituent activities
     * were deleted); - Partially Completed. It may also be used to provide the
     * reason for rejection.
     */
    private String statusInfo;

    /**
     * Error Code optional in the case of a failure status for the planning request
     * (for example Terminated state with statusInfo Failed).  The codes are implementation
     * specific.
     */
    private Integer errorCode;

    /**
     * Supplementary error information.
     */
    private String errorInfo;

    /**
     * Default constructor for RequestInstance.
     * 
     */
    public RequestInstance() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param requestDetails The contents of the planning request.
     * @param creationTime Creation date and time of the RequestInstance version.
     * @param status Current status of the ActivityInstance (see planning request state model in 4.5.5.2).
     * @param outputPlanRefs Reference to the output Plan(s) that contains the activities resulting from the planning request.  Where multiple alternate plans have been generated, these may be listed here.
     * @param returnData Optional return data from the planning process, provided as a list of ID-Value pairs.  This can be used to provide additional information required by the User to interpret the planned operations.
     * @param statusInfo StatusInfo provides the reason for termination and is customizable, but if the following conditions exist then the specified text shall be used: - Completed (all constituent activities completed successfully); - Expired (constituent activities expired prior to execution); - Failed (constituent activities failed during execution); - Deleted (constituent activities were deleted); - Partially Completed. It may also be used to provide the reason for rejection.
     * @param errorCode Error Code optional in the case of a failure status for the planning request (for example Terminated state with statusInfo Failed).  The codes are implementation specific.
     * @param errorInfo Supplementary error information.
     */
    public RequestInstance(org.ccsds.moims.mo.mal.structures.ObjectIdentity objectIdentity,
            org.ccsds.moims.mo.mps.structures.PlanningRequestDetails requestDetails,
            org.ccsds.moims.mo.mal.structures.Time creationTime,
            org.ccsds.moims.mo.mps.structures.RequestStatusEnum status,
            org.ccsds.moims.mo.mal.structures.ObjectRefList outputPlanRefs,
            org.ccsds.moims.mo.mal.structures.NamedValueList returnData,
            String statusInfo,
            Integer errorCode,
            String errorInfo) {
        super(objectIdentity);
        this.requestDetails = requestDetails;
        this.creationTime = creationTime;
        this.status = status;
        this.outputPlanRefs = outputPlanRefs;
        this.returnData = returnData;
        this.statusInfo = statusInfo;
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param requestDetails The contents of the planning request.
     * @param creationTime Creation date and time of the RequestInstance version.
     * @param status Current status of the ActivityInstance (see planning request state model in 4.5.5.2).
     */
    public RequestInstance(org.ccsds.moims.mo.mal.structures.ObjectIdentity objectIdentity,
            org.ccsds.moims.mo.mps.structures.PlanningRequestDetails requestDetails,
            org.ccsds.moims.mo.mal.structures.Time creationTime,
            org.ccsds.moims.mo.mps.structures.RequestStatusEnum status) {
        super(objectIdentity);
        this.requestDetails = requestDetails;
        this.creationTime = creationTime;
        this.status = status;
        this.outputPlanRefs = null;
        this.returnData = null;
        this.statusInfo = null;
        this.errorCode = null;
        this.errorInfo = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.RequestInstance();
    }

    /**
     * Returns the field requestDetails.
     * 
     * @return The field requestDetails
     */
    public org.ccsds.moims.mo.mps.structures.PlanningRequestDetails getRequestDetails() {
        return requestDetails;
    }

    /**
     * Returns the field creationTime.
     * 
     * @return The field creationTime
     */
    public org.ccsds.moims.mo.mal.structures.Time getCreationTime() {
        return creationTime;
    }

    /**
     * Returns the field status.
     * 
     * @return The field status
     */
    public org.ccsds.moims.mo.mps.structures.RequestStatusEnum getStatus() {
        return status;
    }

    /**
     * Returns the field outputPlanRefs.
     * 
     * @return The field outputPlanRefs
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRefList getOutputPlanRefs() {
        return outputPlanRefs;
    }

    /**
     * Returns the field returnData.
     * 
     * @return The field returnData
     */
    public org.ccsds.moims.mo.mal.structures.NamedValueList getReturnData() {
        return returnData;
    }

    /**
     * Returns the field statusInfo.
     * 
     * @return The field statusInfo
     */
    public String getStatusInfo() {
        return statusInfo;
    }

    /**
     * Returns the field errorCode.
     * 
     * @return The field errorCode
     */
    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * Returns the field errorInfo.
     * 
     * @return The field errorInfo
     */
    public String getErrorInfo() {
        return errorInfo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RequestInstance) {
            if (! super.equals(obj)) {
                return false;
            }
            RequestInstance other = (RequestInstance) obj;
            if (requestDetails == null) {
                if (other.requestDetails != null) {
                    return false;
                }
            } else {
                if (! requestDetails.equals(other.requestDetails)) {
                    return false;
                }
            }
            if (creationTime == null) {
                if (other.creationTime != null) {
                    return false;
                }
            } else {
                if (! creationTime.equals(other.creationTime)) {
                    return false;
                }
            }
            if (status == null) {
                if (other.status != null) {
                    return false;
                }
            } else {
                if (! status.equals(other.status)) {
                    return false;
                }
            }
            if (outputPlanRefs == null) {
                if (other.outputPlanRefs != null) {
                    return false;
                }
            } else {
                if (! outputPlanRefs.equals(other.outputPlanRefs)) {
                    return false;
                }
            }
            if (returnData == null) {
                if (other.returnData != null) {
                    return false;
                }
            } else {
                if (! returnData.equals(other.returnData)) {
                    return false;
                }
            }
            if (statusInfo == null) {
                if (other.statusInfo != null) {
                    return false;
                }
            } else {
                if (! statusInfo.equals(other.statusInfo)) {
                    return false;
                }
            }
            if (errorCode == null) {
                if (other.errorCode != null) {
                    return false;
                }
            } else {
                if (! errorCode.equals(other.errorCode)) {
                    return false;
                }
            }
            if (errorInfo == null) {
                if (other.errorInfo != null) {
                    return false;
                }
            } else {
                if (! errorInfo.equals(other.errorInfo)) {
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
        hash = 83 * hash + (requestDetails != null ? requestDetails.hashCode() : 0);
        hash = 83 * hash + (creationTime != null ? creationTime.hashCode() : 0);
        hash = 83 * hash + (status != null ? status.hashCode() : 0);
        hash = 83 * hash + (outputPlanRefs != null ? outputPlanRefs.hashCode() : 0);
        hash = 83 * hash + (returnData != null ? returnData.hashCode() : 0);
        hash = 83 * hash + (statusInfo != null ? statusInfo.hashCode() : 0);
        hash = 83 * hash + (errorCode != null ? errorCode.hashCode() : 0);
        hash = 83 * hash + (errorInfo != null ? errorInfo.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(RequestInstance: ");
        buf.append(super.toString());
        buf.append(", requestDetails=").append(requestDetails);
        buf.append(", creationTime=").append(creationTime);
        buf.append(", status=").append(status);
        buf.append(", outputPlanRefs=").append(outputPlanRefs);
        buf.append(", returnData=").append(returnData);
        buf.append(", statusInfo=").append(statusInfo);
        buf.append(", errorCode=").append(errorCode);
        buf.append(", errorInfo=").append(errorInfo);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (requestDetails == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'requestDetails' cannot be null!");
        }
        if (creationTime == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'creationTime' cannot be null!");
        }
        if (status == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'status' cannot be null!");
        }
        encoder.encodeElement(requestDetails);
        encoder.encodeTime(creationTime);
        encoder.encodeElement(status);
        encoder.encodeNullableElement(outputPlanRefs);
        encoder.encodeNullableElement(returnData);
        encoder.encodeNullableString(statusInfo);
        encoder.encodeNullableInteger(errorCode);
        encoder.encodeNullableString(errorInfo);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        requestDetails = (org.ccsds.moims.mo.mps.structures.PlanningRequestDetails) decoder.decodeElement(new org.ccsds.moims.mo.mps.structures.PlanningRequestDetails());
        creationTime = decoder.decodeTime();
        status = (org.ccsds.moims.mo.mps.structures.RequestStatusEnum) decoder.decodeElement(org.ccsds.moims.mo.mps.structures.RequestStatusEnum.REQUESTED);
        outputPlanRefs = (org.ccsds.moims.mo.mal.structures.ObjectRefList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.ObjectRefList());
        returnData = (org.ccsds.moims.mo.mal.structures.NamedValueList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.NamedValueList());
        statusInfo = decoder.decodeNullableString();
        errorCode = decoder.decodeNullableInteger();
        errorInfo = decoder.decodeNullableString();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
