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
import org.ccsds.moims.mo.mal.structures.StringList;

/**
 * E1: PartialPlanFilter is a data structure input to the getPartialPlan operation
 * of the Plan Distribution Service that contains a reference to the source
 * Plan, and specifies the criteria used to select the partial plan.
 */
public final class PartialPlanFilter implements Composite {

    private static final long serialVersionUID = 1407374900331013L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900331013L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Reference to the Plan of which the partial plan is a selected subset.
     */
    private ObjectRef<Plan> sourcePlan;

    /**
     * Selection criterion based on the domain of contained ActivityInstances.
     * An ordered list representing a domain hierarchy, ‘*’ can be used to represent
     * a wildcard at that level.
     */
    private IdentifierList domain;

    /**
     * Selection criterion based on the subPlan of contained ActivityInstances.
     */
    private Identifier subPlan;

    /**
     * Selection criterion based on tags associated with contained ActivityInstances.
     */
    private StringList tags;

    /**
     * Selection criterion indicating the start of a range of time, position,
     * or events associated with contained ActivityInstances.  If no actual time
     * is known for a Trigger, its predicted time may be used instead to derive
     * the relevant range. .
     */
    private Trigger partialPlanStart;

    /**
     * Selection criterion indicating the end of a range of time, position, or
     * events associated with contained ActivityInstances.  If no actual time
     * is known for a Trigger, its predicted time may be used instead to derive
     * the relevant range.
     */
    private Trigger partialPlanEnd;

    /**
     * Default constructor for PartialPlanFilter.
     * 
     */
    public PartialPlanFilter() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param sourcePlan Reference to the Plan of which the partial plan is a selected subset.
     * @param domain Selection criterion based on the domain of contained ActivityInstances. An ordered list representing a domain hierarchy, ‘*’ can be used to represent a wildcard at that level.
     * @param subPlan Selection criterion based on the subPlan of contained ActivityInstances.
     * @param tags Selection criterion based on tags associated with contained ActivityInstances
     * @param partialPlanStart Selection criterion indicating the start of a range of time, position, or events associated with contained ActivityInstances.  If no actual time is known for a Trigger, its predicted time may be used instead to derive the relevant range. 
     * @param partialPlanEnd Selection criterion indicating the end of a range of time, position, or events associated with contained ActivityInstances.  If no actual time is known for a Trigger, its predicted time may be used instead to derive the relevant range.
     */
    public PartialPlanFilter(ObjectRef<Plan> sourcePlan,
            IdentifierList domain,
            Identifier subPlan,
            StringList tags,
            Trigger partialPlanStart,
            Trigger partialPlanEnd) {
        this.sourcePlan = sourcePlan;
        this.domain = domain;
        this.subPlan = subPlan;
        this.tags = tags;
        this.partialPlanStart = partialPlanStart;
        this.partialPlanEnd = partialPlanEnd;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param sourcePlan Reference to the Plan of which the partial plan is a selected subset.
     */
    public PartialPlanFilter(ObjectRef<Plan> sourcePlan) {
        this.sourcePlan = sourcePlan;
        this.domain = null;
        this.subPlan = null;
        this.tags = null;
        this.partialPlanStart = null;
        this.partialPlanEnd = null;
    }

    @Override
    public Element createElement() {
        return new PartialPlanFilter();
    }

    /**
     * Returns the field sourcePlan.
     * 
     * @return The field sourcePlan
     */
    public ObjectRef<Plan> getSourcePlan() {
        return sourcePlan;
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
     * Returns the field subPlan.
     * 
     * @return The field subPlan
     */
    public Identifier getSubPlan() {
        return subPlan;
    }

    /**
     * Returns the field tags.
     * 
     * @return The field tags
     */
    public StringList getTags() {
        return tags;
    }

    /**
     * Returns the field partialPlanStart.
     * 
     * @return The field partialPlanStart
     */
    public Trigger getPartialPlanStart() {
        return partialPlanStart;
    }

    /**
     * Returns the field partialPlanEnd.
     * 
     * @return The field partialPlanEnd
     */
    public Trigger getPartialPlanEnd() {
        return partialPlanEnd;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PartialPlanFilter) {
            PartialPlanFilter other = (PartialPlanFilter) obj;
            if (sourcePlan == null) {
                if (other.sourcePlan != null) {
                    return false;
                }
            } else {
                if (! sourcePlan.equals(other.sourcePlan)) {
                    return false;
                }
            }
            if (domain == null) {
                if (other.domain != null) {
                    return false;
                }
            } else {
                if (! domain.equals(other.domain)) {
                    return false;
                }
            }
            if (subPlan == null) {
                if (other.subPlan != null) {
                    return false;
                }
            } else {
                if (! subPlan.equals(other.subPlan)) {
                    return false;
                }
            }
            if (tags == null) {
                if (other.tags != null) {
                    return false;
                }
            } else {
                if (! tags.equals(other.tags)) {
                    return false;
                }
            }
            if (partialPlanStart == null) {
                if (other.partialPlanStart != null) {
                    return false;
                }
            } else {
                if (! partialPlanStart.equals(other.partialPlanStart)) {
                    return false;
                }
            }
            if (partialPlanEnd == null) {
                if (other.partialPlanEnd != null) {
                    return false;
                }
            } else {
                if (! partialPlanEnd.equals(other.partialPlanEnd)) {
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
        hash = 83 * hash + (sourcePlan != null ? sourcePlan.hashCode() : 0);
        hash = 83 * hash + (domain != null ? domain.hashCode() : 0);
        hash = 83 * hash + (subPlan != null ? subPlan.hashCode() : 0);
        hash = 83 * hash + (tags != null ? tags.hashCode() : 0);
        hash = 83 * hash + (partialPlanStart != null ? partialPlanStart.hashCode() : 0);
        hash = 83 * hash + (partialPlanEnd != null ? partialPlanEnd.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(PartialPlanFilter: ");
        buf.append("sourcePlan=").append(sourcePlan);
        buf.append(", domain=").append(domain);
        buf.append(", subPlan=").append(subPlan);
        buf.append(", tags=").append(tags);
        buf.append(", partialPlanStart=").append(partialPlanStart);
        buf.append(", partialPlanEnd=").append(partialPlanEnd);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (sourcePlan == null) {
            throw new MALException("The field 'sourcePlan' cannot be null!");
        }
        encoder.encodeElement(sourcePlan);
        encoder.encodeNullableElement(domain);
        encoder.encodeNullableIdentifier(subPlan);
        encoder.encodeNullableElement(tags);
        encoder.encodeNullableAbstractElement(partialPlanStart);
        encoder.encodeNullableAbstractElement(partialPlanEnd);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        sourcePlan = (ObjectRef<Plan>) decoder.decodeElement(new ObjectRef<Plan>());
        domain = (IdentifierList) decoder.decodeNullableElement(new IdentifierList());
        subPlan = decoder.decodeNullableIdentifier();
        tags = (StringList) decoder.decodeNullableElement(new StringList());
        partialPlanStart = (Trigger) decoder.decodeNullableAbstractElement();
        partialPlanEnd = (Trigger) decoder.decodeNullableAbstractElement();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
