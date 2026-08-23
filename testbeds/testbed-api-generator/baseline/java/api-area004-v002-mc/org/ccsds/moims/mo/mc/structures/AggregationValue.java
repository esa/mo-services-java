package org.ccsds.moims.mo.mc.structures;

/**
 * The AggregationValue structure shall be used to hold the values of the
 * aggregation parameters.
 */
public final class AggregationValue implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125899940397117L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397117L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The aggregationRef field.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mc.structures.AggregationDefinition> aggregationRef;

    /**
     * The timestamp field.
     */
    private org.ccsds.moims.mo.mal.structures.Time timestamp;

    /**
     * The parameterValues field.
     */
    private org.ccsds.moims.mo.mc.structures.ParameterValueDataList parameterValues;

    /**
     * Default constructor for AggregationValue.
     * 
     */
    public AggregationValue() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param aggregationRef The aggregationRef field.
     * @param timestamp The timestamp field.
     * @param parameterValues The parameterValues field.
     */
    public AggregationValue(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mc.structures.AggregationDefinition> aggregationRef,
            org.ccsds.moims.mo.mal.structures.Time timestamp,
            org.ccsds.moims.mo.mc.structures.ParameterValueDataList parameterValues) {
        this.aggregationRef = aggregationRef;
        this.timestamp = timestamp;
        this.parameterValues = parameterValues;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.structures.AggregationValue();
    }

    /**
     * Returns the field aggregationRef.
     * 
     * @return The field aggregationRef
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mc.structures.AggregationDefinition> getAggregationRef() {
        return aggregationRef;
    }

    /**
     * Returns the field timestamp.
     * 
     * @return The field timestamp
     */
    public org.ccsds.moims.mo.mal.structures.Time getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the field parameterValues.
     * 
     * @return The field parameterValues
     */
    public org.ccsds.moims.mo.mc.structures.ParameterValueDataList getParameterValues() {
        return parameterValues;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AggregationValue) {
            AggregationValue other = (AggregationValue) obj;
            if (aggregationRef == null) {
                if (other.aggregationRef != null) {
                    return false;
                }
            } else {
                if (! aggregationRef.equals(other.aggregationRef)) {
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
            if (parameterValues == null) {
                if (other.parameterValues != null) {
                    return false;
                }
            } else {
                if (! parameterValues.equals(other.parameterValues)) {
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
        hash = 83 * hash + (aggregationRef != null ? aggregationRef.hashCode() : 0);
        hash = 83 * hash + (timestamp != null ? timestamp.hashCode() : 0);
        hash = 83 * hash + (parameterValues != null ? parameterValues.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(AggregationValue: ");
        buf.append("aggregationRef=").append(aggregationRef);
        buf.append(", timestamp=").append(timestamp);
        buf.append(", parameterValues=").append(parameterValues);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (aggregationRef == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'aggregationRef' cannot be null!");
        }
        if (timestamp == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'timestamp' cannot be null!");
        }
        if (parameterValues == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'parameterValues' cannot be null!");
        }
        encoder.encodeElement(aggregationRef);
        encoder.encodeTime(timestamp);
        encoder.encodeElement(parameterValues);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        aggregationRef = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mc.structures.AggregationDefinition>) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mc.structures.AggregationDefinition>());
        timestamp = decoder.decodeTime();
        parameterValues = (org.ccsds.moims.mo.mc.structures.ParameterValueDataList) decoder.decodeElement(new org.ccsds.moims.mo.mc.structures.ParameterValueDataList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
