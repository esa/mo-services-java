package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.Time;

/**
 * E1: RequestStatusUpdate is a data structure that is used to report changes
 * in status of the RequestInstance as it proceeds through both planning and
 * plan execution functions.  Reporting is the responsibility of the planning
 * function. Planning request status updates may be distributed to subscribing
 * applications, including both Users and status displays, to inform them
 * of the latest status of the planning request.  This may be particularly
 * relevant in conjunction with a plan execution function.  Status updates
 * may be stored in planning request history to provide a complete record
 * of evolving status over time.
 */
public final class RequestStatusUpdate implements Composite {

    private static final long serialVersionUID = 1407374900330900L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330900L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Reference to the planning request instance to which the status update relates.
     */
    private ObjectRef<RequestInstance> requestInstance;

    /**
     * Time of status update.
     */
    private Time timestamp;

    /**
     * Current status of the planning request.
     */
    private RequestStatusEnum status;

    /**
     * Reference to the output Plan(s) that contains the activities resulting
     * from the planning request.  Where multiple alternate plans have been generated,
     * these may be listed here.  It should be noted that this is only available
     * once the planning request has been processed and successfully planned.
     * The outputPlanRefs may be updated following iterative planning cycles
     * or re-planning.
     */
    private ObjectRefList outputPlanRefs;

    /**
     * Optional return data from the planning process, provided as a list of ID-Value
     * pairs.  This can be used to provide additional information required by
     * the User to interpret the planned operations.
     */
    private NamedValueList returnData;

    /**
     * StatusInfo provides the reason for termination and is customizable, but
     * if the following conditions exist then the specified text shall be used:
     * - Completed (all constituent activities completed successfully); - Expired
     * (constituent activities expired prior to execution); - Failed (constituent
     * activities failed during execution); - Deleted (constituent activities
     * were deleted); - PartiallyCompleted. It may also be used to provide the
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
     * Default constructor for RequestStatusUpdate.
     * 
     */
    public RequestStatusUpdate() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param requestInstance Reference to the planning request instance to which the status update relates.
     * @param timestamp Time of status update.
     * @param status Current status of the planning request.
     * @param outputPlanRefs Reference to the output Plan(s) that contains the activities resulting from the planning request.  Where multiple alternate plans have been generated, these may be listed here.  It should be noted that this is only available once the planning request has been processed and successfully planned.  The outputPlanRefs may be updated following iterative planning cycles or re-planning.
     * @param returnData Optional return data from the planning process, provided as a list of ID-Value pairs.  This can be used to provide additional information required by the User to interpret the planned operations.
     * @param statusInfo StatusInfo provides the reason for termination and is customizable, but if the following conditions exist then the specified text shall be used: - Completed (all constituent activities completed successfully); - Expired (constituent activities expired prior to execution); - Failed (constituent activities failed during execution); - Deleted (constituent activities were deleted); - PartiallyCompleted. It may also be used to provide the reason for rejection.
     * @param errorCode Error Code optional in the case of a failure status for the planning request (for example Terminated state with statusInfo Failed).  The codes are implementation specific.
     * @param errorInfo Supplementary error information.
     */
    public RequestStatusUpdate(ObjectRef<RequestInstance> requestInstance,
            Time timestamp,
            RequestStatusEnum status,
            ObjectRefList outputPlanRefs,
            NamedValueList returnData,
            String statusInfo,
            Integer errorCode,
            String errorInfo) {
        this.requestInstance = requestInstance;
        this.timestamp = timestamp;
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
     * @param requestInstance Reference to the planning request instance to which the status update relates.
     * @param timestamp Time of status update.
     * @param status Current status of the planning request.
     */
    public RequestStatusUpdate(ObjectRef<RequestInstance> requestInstance,
            Time timestamp,
            RequestStatusEnum status) {
        this.requestInstance = requestInstance;
        this.timestamp = timestamp;
        this.status = status;
        this.outputPlanRefs = null;
        this.returnData = null;
        this.statusInfo = null;
        this.errorCode = null;
        this.errorInfo = null;
    }

    @Override
    public Element createElement() {
        return new RequestStatusUpdate();
    }

    /**
     * Returns the field requestInstance.
     * 
     * @return The field requestInstance
     */
    public ObjectRef<RequestInstance> getRequestInstance() {
        return requestInstance;
    }

    /**
     * Returns the field timestamp.
     * 
     * @return The field timestamp
     */
    public Time getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the field status.
     * 
     * @return The field status
     */
    public RequestStatusEnum getStatus() {
        return status;
    }

    /**
     * Returns the field outputPlanRefs.
     * 
     * @return The field outputPlanRefs
     */
    public ObjectRefList getOutputPlanRefs() {
        return outputPlanRefs;
    }

    /**
     * Returns the field returnData.
     * 
     * @return The field returnData
     */
    public NamedValueList getReturnData() {
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
        if (obj instanceof RequestStatusUpdate) {
            RequestStatusUpdate other = (RequestStatusUpdate) obj;
            if (requestInstance == null) {
                if (other.requestInstance != null) {
                    return false;
                }
            } else {
                if (! requestInstance.equals(other.requestInstance)) {
                    return false;
                }
            }
            if (timestamp == null) {
                if (other.timestamp != null) {
                    return false;
                }
            } else {
                if (! timestamp.equals(other.timestamp)) {
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
        int hash = 7;
        hash = 83 * hash + (requestInstance != null ? requestInstance.hashCode() : 0);
        hash = 83 * hash + (timestamp != null ? timestamp.hashCode() : 0);
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
        buf.append("(RequestStatusUpdate: ");
        buf.append("requestInstance=").append(requestInstance);
        buf.append(", timestamp=").append(timestamp);
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
    public void encode(MALEncoder encoder) throws MALException {
        if (requestInstance == null) {
            throw new MALException("The field 'requestInstance' cannot be null!");
        }
        if (timestamp == null) {
            throw new MALException("The field 'timestamp' cannot be null!");
        }
        if (status == null) {
            throw new MALException("The field 'status' cannot be null!");
        }
        encoder.encodeElement(requestInstance);
        encoder.encodeTime(timestamp);
        encoder.encodeElement(status);
        encoder.encodeNullableElement(outputPlanRefs);
        encoder.encodeNullableElement(returnData);
        encoder.encodeNullableString(statusInfo);
        encoder.encodeNullableInteger(errorCode);
        encoder.encodeNullableString(errorInfo);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        requestInstance = (ObjectRef<RequestInstance>) decoder.decodeElement(new ObjectRef<RequestInstance>());
        timestamp = decoder.decodeTime();
        status = (RequestStatusEnum) decoder.decodeElement(RequestStatusEnum.REQUESTED);
        outputPlanRefs = (ObjectRefList) decoder.decodeNullableElement(new ObjectRefList());
        returnData = (NamedValueList) decoder.decodeNullableElement(new NamedValueList());
        statusInfo = decoder.decodeNullableString();
        errorCode = decoder.decodeNullableInteger();
        errorInfo = decoder.decodeNullableString();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
