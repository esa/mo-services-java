package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;

/**
 * E1: RequestFilter is a data structure used in the context of MPS Planning
 * Request Service operations to specify a filtered set of planning requests.
 * NOTE – All fields are nullable and it is valid to specify a RequestFilter
 * with no filter criteria; this corresponds to an open filter in which all
 * available planning requests are returned.
 */
public final class RequestFilter implements Composite {

    private static final long serialVersionUID = 1407374900330904L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330904L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Domain of the RequestInstance.  An ordered list representing a domain hierarchy,
     * ‘*’ can be used to represent a wildcard at that level.
     */
    private IdentifierList domain;

    /**
     * Reference to the RequestInstance.
     */
    private ObjectRef<RequestInstance> instanceID;

    /**
     * Query for request instances with a creation date and time in the specified
     * range.
     */
    private TimeWindow creationTime;

    /**
     * Reference to the RequestDefinition from which the RequestInstance was created.
     */
    private ObjectRef<RequestDefinition> definitionID;

    /**
     * Reference of the User who initiated the RequestInstance.
     */
    private ObjectRef<PlanningUser> userID;

    /**
     * Reference supplied by User when submitting the RequestInstance.
     */
    private Identifier userReference;

    /**
     * Current status (enum) of the RequestInstance.
     */
    private RequestStatusEnum status;

    /**
     * Reference to the output Plan(s) generated in response to the RequestInstance.
     */
    private ObjectRefList outputPlanRefs;

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
    public RequestFilter(IdentifierList domain,
            ObjectRef<RequestInstance> instanceID,
            TimeWindow creationTime,
            ObjectRef<RequestDefinition> definitionID,
            ObjectRef<PlanningUser> userID,
            Identifier userReference,
            RequestStatusEnum status,
            ObjectRefList outputPlanRefs) {
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
    public Element createElement() {
        return new RequestFilter();
    }

    /**
     * Returns the field domain.
     * 
     * @return The field domain
     */
    public IdentifierList getDomain() {
        return domain;
    }

    /**
     * Returns the field instanceID.
     * 
     * @return The field instanceID
     */
    public ObjectRef<RequestInstance> getInstanceID() {
        return instanceID;
    }

    /**
     * Returns the field creationTime.
     * 
     * @return The field creationTime
     */
    public TimeWindow getCreationTime() {
        return creationTime;
    }

    /**
     * Returns the field definitionID.
     * 
     * @return The field definitionID
     */
    public ObjectRef<RequestDefinition> getDefinitionID() {
        return definitionID;
    }

    /**
     * Returns the field userID.
     * 
     * @return The field userID
     */
    public ObjectRef<PlanningUser> getUserID() {
        return userID;
    }

    /**
     * Returns the field userReference.
     * 
     * @return The field userReference
     */
    public Identifier getUserReference() {
        return userReference;
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
    public void encode(MALEncoder encoder) throws MALException {
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
    public Element decode(MALDecoder decoder) throws MALException {
        domain = (IdentifierList) decoder.decodeNullableElement(new IdentifierList());
        instanceID = (ObjectRef<RequestInstance>) decoder.decodeNullableElement(new ObjectRef<RequestInstance>());
        creationTime = (TimeWindow) decoder.decodeNullableElement(new TimeWindow());
        definitionID = (ObjectRef<RequestDefinition>) decoder.decodeNullableElement(new ObjectRef<RequestDefinition>());
        userID = (ObjectRef<PlanningUser>) decoder.decodeNullableElement(new ObjectRef<PlanningUser>());
        userReference = decoder.decodeNullableIdentifier();
        status = (RequestStatusEnum) decoder.decodeNullableElement(RequestStatusEnum.REQUESTED);
        outputPlanRefs = (ObjectRefList) decoder.decodeNullableElement(new ObjectRefList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
