package org.ccsds.moims.mo.mps.structures;

/**
 * E1: RequestFilter is a data structure used in the context of MPS Planning
 * Request Service operations to specify a filtered set of planning requests.
 * NOTE – All fields are nullable and it is valid to specify a RequestFilter
 * with no filter criteria; this corresponds to an open filter in which all
 * available planning requests are returned.
 */
public final class RequestFilter implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1407374900330904L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330904L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Domain of the RequestInstance.  An ordered list representing a domain hierarchy,
     * ‘*’ can be used to represent a wildcard at that level.
     */
    private org.ccsds.moims.mo.mal.structures.IdentifierList domain;

    /**
     * Reference to the RequestInstance.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.RequestInstance> instanceID;

    /**
     * Query for request instances with a creation date and time in the specified
     * range.
     */
    private org.ccsds.moims.mo.mps.structures.TimeWindow creationTime;

    /**
     * Reference to the RequestDefinition from which the RequestInstance was created.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.RequestDefinition> definitionID;

    /**
     * Reference of the User who initiated the RequestInstance.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.PlanningUser> userID;

    /**
     * Reference supplied by User when submitting the RequestInstance.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier userReference;

    /**
     * Current status (enum) of the RequestInstance.
     */
    private org.ccsds.moims.mo.mps.structures.RequestStatusEnum status;

    /**
     * Reference to the output Plan(s) generated in response to the RequestInstance.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRefList outputPlanRefs;

    /**
     * Default constructor for RequestFilter.
     * 
     */
    public RequestFilter() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param domain Domain of the RequestInstance.  An ordered list representing a domain hierarchy, ‘*’ can be used to represent a wildcard at that level.
     * @param instanceID Reference to the RequestInstance.
     * @param creationTime Query for request instances with a creation date and time in the specified range.
     * @param definitionID Reference to the RequestDefinition from which the RequestInstance was created.
     * @param userID Reference of the User who initiated the RequestInstance.
     * @param userReference Reference supplied by User when submitting the RequestInstance.
     * @param status Current status (enum) of the RequestInstance.
     * @param outputPlanRefs Reference to the output Plan(s) generated in response to the RequestInstance.
     */
    public RequestFilter(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.RequestInstance> instanceID,
            org.ccsds.moims.mo.mps.structures.TimeWindow creationTime,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.RequestDefinition> definitionID,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.PlanningUser> userID,
            org.ccsds.moims.mo.mal.structures.Identifier userReference,
            org.ccsds.moims.mo.mps.structures.RequestStatusEnum status,
            org.ccsds.moims.mo.mal.structures.ObjectRefList outputPlanRefs) {
        this.domain = domain;
        this.instanceID = instanceID;
        this.creationTime = creationTime;
        this.definitionID = definitionID;
        this.userID = userID;
        this.userReference = userReference;
        this.status = status;
        this.outputPlanRefs = outputPlanRefs;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.RequestFilter();
    }

    /**
     * Returns the field domain.
     * 
     * @return The field domain
     */
    public org.ccsds.moims.mo.mal.structures.IdentifierList getDomain() {
        return domain;
    }

    /**
     * Returns the field instanceID.
     * 
     * @return The field instanceID
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.RequestInstance> getInstanceID() {
        return instanceID;
    }

    /**
     * Returns the field creationTime.
     * 
     * @return The field creationTime
     */
    public org.ccsds.moims.mo.mps.structures.TimeWindow getCreationTime() {
        return creationTime;
    }

    /**
     * Returns the field definitionID.
     * 
     * @return The field definitionID
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.RequestDefinition> getDefinitionID() {
        return definitionID;
    }

    /**
     * Returns the field userID.
     * 
     * @return The field userID
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.PlanningUser> getUserID() {
        return userID;
    }

    /**
     * Returns the field userReference.
     * 
     * @return The field userReference
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getUserReference() {
        return userReference;
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RequestFilter) {
            RequestFilter other = (RequestFilter) obj;
            if (domain == null) {
                if (other.domain != null) {
                    return false;
                }
            } else {
                if (! domain.equals(other.domain)) {
                    return false;
                }
            }
            if (instanceID == null) {
                if (other.instanceID != null) {
                    return false;
                }
            } else {
                if (! instanceID.equals(other.instanceID)) {
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
            if (definitionID == null) {
                if (other.definitionID != null) {
                    return false;
                }
            } else {
                if (! definitionID.equals(other.definitionID)) {
                    return false;
                }
            }
            if (userID == null) {
                if (other.userID != null) {
                    return false;
                }
            } else {
                if (! userID.equals(other.userID)) {
                    return false;
                }
            }
            if (userReference == null) {
                if (other.userReference != null) {
                    return false;
                }
            } else {
                if (! userReference.equals(other.userReference)) {
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
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (domain != null ? domain.hashCode() : 0);
        hash = 83 * hash + (instanceID != null ? instanceID.hashCode() : 0);
        hash = 83 * hash + (creationTime != null ? creationTime.hashCode() : 0);
        hash = 83 * hash + (definitionID != null ? definitionID.hashCode() : 0);
        hash = 83 * hash + (userID != null ? userID.hashCode() : 0);
        hash = 83 * hash + (userReference != null ? userReference.hashCode() : 0);
        hash = 83 * hash + (status != null ? status.hashCode() : 0);
        hash = 83 * hash + (outputPlanRefs != null ? outputPlanRefs.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(RequestFilter: ");
        buf.append("domain=").append(domain);
        buf.append(", instanceID=").append(instanceID);
        buf.append(", creationTime=").append(creationTime);
        buf.append(", definitionID=").append(definitionID);
        buf.append(", userID=").append(userID);
        buf.append(", userReference=").append(userReference);
        buf.append(", status=").append(status);
        buf.append(", outputPlanRefs=").append(outputPlanRefs);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        encoder.encodeNullableElement(domain);
        encoder.encodeNullableElement(instanceID);
        encoder.encodeNullableElement(creationTime);
        encoder.encodeNullableElement(definitionID);
        encoder.encodeNullableElement(userID);
        encoder.encodeNullableIdentifier(userReference);
        encoder.encodeNullableElement(status);
        encoder.encodeNullableElement(outputPlanRefs);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        domain = (org.ccsds.moims.mo.mal.structures.IdentifierList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.IdentifierList());
        instanceID = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.RequestInstance>) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.RequestInstance>());
        creationTime = (org.ccsds.moims.mo.mps.structures.TimeWindow) decoder.decodeNullableElement(new org.ccsds.moims.mo.mps.structures.TimeWindow());
        definitionID = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.RequestDefinition>) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.RequestDefinition>());
        userID = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.PlanningUser>) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.PlanningUser>());
        userReference = decoder.decodeNullableIdentifier();
        status = (org.ccsds.moims.mo.mps.structures.RequestStatusEnum) decoder.decodeNullableElement(org.ccsds.moims.mo.mps.structures.RequestStatusEnum.REQUESTED);
        outputPlanRefs = (org.ccsds.moims.mo.mal.structures.ObjectRefList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.ObjectRefList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
